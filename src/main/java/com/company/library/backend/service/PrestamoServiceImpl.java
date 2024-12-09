package com.company.library.backend.service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.company.library.backend.model.dao.IPrestamoDao;
import com.company.library.backend.response.PrestamoResponseRest;
import com.company.library.backend.utils.Constants;
import com.company.library.backend.model.Prestamo;

@Service
public class PrestamoServiceImpl implements IPrestamoService {

	private static final Logger log = LoggerFactory.getLogger(PrestamoServiceImpl.class);

	@Autowired
	private IPrestamoDao prestamoDao;

	@Override
	@Transactional(readOnly = true)
	public ResponseEntity<PrestamoResponseRest> buscarPrestamos() {

		log.info("inicio metodo buscarCategorias");

		PrestamoResponseRest response = new PrestamoResponseRest();

		try {

			List<Prestamo> prestamo = (List<Prestamo>) prestamoDao.findAll();
			response.getPrestamoResponse().setPrestamo(prestamo);
			response.setMetadata(Constants.getOkMessage(), "00", Constants.getSuccessMessage());

		} catch (Exception e) {
			response.setMetadata(Constants.getErrorMessage(), "-1", Constants.getFailedMessage());
			log.error("error al consultar prestamos", e.getMessage());
			e.getStackTrace();
			return new ResponseEntity<PrestamoResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<PrestamoResponseRest>(response, HttpStatus.OK);
	}

	@Override
	@Transactional(readOnly = true)
	public ResponseEntity<PrestamoResponseRest> buscarPorId(Long id) {
		log.info("inicio metodo buscarPorId");

		PrestamoResponseRest response = new PrestamoResponseRest();

		List<Prestamo> list = new ArrayList<>();

		try {

			Optional<Prestamo> prestamo = prestamoDao.findById(id);

			if (prestamo.isPresent()) {
				list.add(prestamo.get());
				response.getPrestamoResponse().setPrestamo(list);
			} else {
				log.error("Error en consultar prestamo");
				response.setMetadata(Constants.getFailedMessage(), "-1", Constants.getPrestamoNoEncontrado());
				return new ResponseEntity<PrestamoResponseRest>(response, HttpStatus.NOT_FOUND);
			}

		} catch (Exception e) {
			log.error("Error en consultar prestamo");
			response.setMetadata(Constants.getErrorMessage(), "-1", "Error al consultar prestamo");
			return new ResponseEntity<PrestamoResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.setMetadata(Constants.getOkMessage(), "00", Constants.getSuccessMessage());
		return new ResponseEntity<PrestamoResponseRest>(response, HttpStatus.OK);
	}

	@Override
	@Transactional
	public ResponseEntity<PrestamoResponseRest> crear(Prestamo prestamo) {
		log.info("inicio metodo crear prestamo");
		
		PrestamoResponseRest response = new PrestamoResponseRest();
		List<Prestamo> list = new ArrayList<>();
		
		try {
			
	        if (prestamo.getTipoUsuario() < Constants.getTipoUsuarioAfiliado() || prestamo.getTipoUsuario() > Constants.getTipoUsuarioInvitado()) {
	        	response.setMetadata(Constants.getErrorMessage(), "-1", "Tipo de usuario no permitido en la biblioteca");
	        	return new ResponseEntity<PrestamoResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	        }
		    
		    if (prestamo.getTipoUsuario() == Constants.getTipoUsuarioInvitado()) {
	            Optional<Prestamo> prestamoExistente = prestamoDao.findByIdentificacionUsuario(prestamo.getIdentificacionUsuario());
	            if (prestamoExistente.isPresent()) {
	            	response.setMetadata(Constants.getErrorMessage(), "-1", "El usuario con identificación " + prestamo.getIdentificacionUsuario() + " ya tiene un libro prestado por lo cual no se le puede realizar otro préstamo");
		        	return new ResponseEntity<PrestamoResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	            }
	        }
		    
		    prestamo.setFechaMaximaDevolucion(calcularFechaDevolucion(prestamo.getTipoUsuario()));
			Prestamo prestamoGuardado = prestamoDao.save(prestamo);

			if (prestamoGuardado != null) {
				list.add(prestamoGuardado);
				response.getPrestamoResponse().setPrestamo(list);
			} else {
				log.error("Error en crear prestamo");
				response.setMetadata(Constants.getFailedMessage(), "-1", "Prestamo no guardado");
				return new ResponseEntity<PrestamoResponseRest>(response, HttpStatus.BAD_REQUEST);
			}

		} catch (Exception e) {
			log.error("Error en crear prestamo");
			response.setMetadata(Constants.getFailedMessage(), "-1", "Error al crear prestamo");
			return new ResponseEntity<PrestamoResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.setMetadata(Constants.getOkMessage(), "00", "Prestamo creado");
		return new ResponseEntity<PrestamoResponseRest>(response, HttpStatus.OK);
	}

	@Override
	@Transactional
	public ResponseEntity<PrestamoResponseRest> eliminar(Long id) {

		log.info("inicio metodo eliminar categoria");
		
		PrestamoResponseRest response = new PrestamoResponseRest();
		
		try {
			
			prestamoDao.deleteById(id);
			response.setMetadata(Constants.getOkMessage(), "00", Constants.getPrestamoEliminado());
			
		} catch (Exception e) {
			log.error("Error en eliminar prestamo");
			e.getStackTrace();
			response.setMetadata(Constants.getErrorMessage(), "-1", "Prestamo no eliminado");
			return new ResponseEntity<PrestamoResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<PrestamoResponseRest>(response, HttpStatus.OK);
	}
	
    private LocalDate calcularFechaDevolucion(int tipoUsuario) {
        LocalDate fechaActual = LocalDate.now();
        int diasAAgregar;
        switch (tipoUsuario) {
            case 1: diasAAgregar = 10; break;
            case 2: diasAAgregar = 8; break;
            case 3: diasAAgregar = 7; break;
            default: throw new IllegalArgumentException("Tipo de usuario no permitido en la biblioteca");
        }
        return calcularFechaDevolucionInterno(fechaActual, diasAAgregar);
    }
    
    public LocalDate calcularFechaDevolucionInterno(LocalDate fechaInicial, int diasAAgregar) {
        LocalDate fechaDevolucion = fechaInicial;
        int diasAgregados = 0;
        while (diasAgregados < diasAAgregar) {
            fechaDevolucion = fechaDevolucion.plusDays(1);
            if (!(fechaDevolucion.getDayOfWeek() == DayOfWeek.SATURDAY || fechaDevolucion.getDayOfWeek() == DayOfWeek.SUNDAY)) {
                diasAgregados++;
            }
        }
        return fechaDevolucion;
    }

}
