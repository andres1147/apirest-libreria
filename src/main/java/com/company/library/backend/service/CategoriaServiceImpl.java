package com.company.library.backend.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.company.library.backend.model.dao.ICategoriaDao;
import com.company.library.backend.response.CategoriaResponseRest;
import com.company.library.backend.utils.Constants;
import com.company.library.backend.model.Categoria;

@Service
public class CategoriaServiceImpl implements ICategoriaService {

	private static final Logger log = LoggerFactory.getLogger(CategoriaServiceImpl.class);

	@Autowired
	private ICategoriaDao categoriaDao;

	@Override
	@Transactional(readOnly = true)
	public ResponseEntity<CategoriaResponseRest> buscarCategorias() {

		log.info("inicio metodo buscarCategorias");

		CategoriaResponseRest response = new CategoriaResponseRest();

		try {

			List<Categoria> categoria = (List<Categoria>) categoriaDao.findAll();
			response.getCategoriaResponse().setCategoria(categoria);
			response.setMetadata(Constants.getOkMessage(), "00", Constants.getSuccessMessage());

		} catch (Exception e) {
			response.setMetadata(Constants.getErrorMessage(), "-1", Constants.getFailedMessage());
			log.error("error al consultar categorias", e.getMessage());
			e.getStackTrace();
			return new ResponseEntity<CategoriaResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<CategoriaResponseRest>(response, HttpStatus.OK);
	}

	@Override
	@Transactional(readOnly = true)
	public ResponseEntity<CategoriaResponseRest> buscarPorId(Long id) {
		log.info("inicio metodo buscarPorId");

		CategoriaResponseRest response = new CategoriaResponseRest();

		List<Categoria> list = new ArrayList<>();

		try {

			Optional<Categoria> categoria = categoriaDao.findById(id);

			if (categoria.isPresent()) {
				list.add(categoria.get());
				response.getCategoriaResponse().setCategoria(list);
			} else {
				log.error("Error en consultar categoria");
				response.setMetadata(Constants.getErrorMessage(), "-1", Constants.getCategoriaNoEncontrada());
				return new ResponseEntity<CategoriaResponseRest>(response, HttpStatus.NOT_FOUND);
			}

		} catch (Exception e) {
			log.error("Error en consultar categoria");
			response.setMetadata(Constants.getErrorMessage(), "-1", "Error al consultar categoria");
			return new ResponseEntity<CategoriaResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.setMetadata("Respuesta ok", "00", "Respuesta exitosa");
		return new ResponseEntity<CategoriaResponseRest>(response, HttpStatus.OK);
	}

	@Override
	@Transactional
	public ResponseEntity<CategoriaResponseRest> crear(Categoria categoria) {
		log.info("inicio metodo crear categoria");
		
		CategoriaResponseRest response = new CategoriaResponseRest();
		List<Categoria> list = new ArrayList<>();
		
		try {

			Categoria categoriaGuardada = categoriaDao.save(categoria);

			if (categoriaGuardada != null) {
				list.add(categoriaGuardada);
				response.getCategoriaResponse().setCategoria(list);
			} else {
				log.error("Error en crear categoria");
				response.setMetadata(Constants.getFailedMessage(), "-1", "Categoria no guardada");
				return new ResponseEntity<CategoriaResponseRest>(response, HttpStatus.BAD_REQUEST);
			}

		} catch (Exception e) {
			log.error("Error en crear categoria");
			response.setMetadata(Constants.getFailedMessage(), "-1", "Error al crear categoria");
			return new ResponseEntity<CategoriaResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.setMetadata(Constants.getOkMessage(), "00", "Categoria creada");
		return new ResponseEntity<CategoriaResponseRest>(response, HttpStatus.OK);
	}

	@Override
	@Transactional
	public ResponseEntity<CategoriaResponseRest> actualizar(Categoria categoria, Long id) {
		log.info("inicio metodo actualizar categoria");
		CategoriaResponseRest response = new CategoriaResponseRest();
		List<Categoria> list = new ArrayList<>();
		
		try {

			Optional<Categoria> categoriaBuscada = categoriaDao.findById(id);

			if (categoriaBuscada.isPresent()) {
				categoriaBuscada.get().setNombre(categoria.getNombre());
				categoriaBuscada.get().setDescripcion(categoria.getDescripcion());
				
				Categoria categoriaActualizar = categoriaDao.save(categoriaBuscada.get());
				
				if(categoriaActualizar != null) {
					response.setMetadata("Respuesta ok", "00", "Categoria actualizada");
					list.add(categoriaActualizar);
					response.getCategoriaResponse().setCategoria(list);
				} else {
					log.error("Error en actualizar categoria");
					response.setMetadata("Respuesta nok", "-1", "Categoria no actualizada");
					return new ResponseEntity<CategoriaResponseRest>(response, HttpStatus.BAD_REQUEST);
				}
			} else {
				log.error("Error en actualizar categoria");
				response.setMetadata("Respuesta nok", "-1", "Categoria no actualizada");
				return new ResponseEntity<CategoriaResponseRest>(response, HttpStatus.NOT_FOUND);
			}

		} catch (Exception e) {
			log.error("Error en actualizar categoria");
			response.setMetadata("Respuesta nok", "-1", "Categoria no actualizada");
			return new ResponseEntity<CategoriaResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<CategoriaResponseRest>(response, HttpStatus.OK);
	}

	@Override
	@Transactional
	public ResponseEntity<CategoriaResponseRest> eliminar(Long id) {

		log.info("inicio metodo eliminar categoria");
		
		CategoriaResponseRest response = new CategoriaResponseRest();
		
		try {
			
			categoriaDao.deleteById(id);
			response.setMetadata("Respuesta ok", "00", "Categoria eliminada");
			
		} catch (Exception e) {
			log.error("Error en eliminar categoria");
			e.getStackTrace();
			response.setMetadata("Respuesta nok", "-1", "Categoria no eliminada");
			return new ResponseEntity<CategoriaResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<CategoriaResponseRest>(response, HttpStatus.OK);
	}
	
	@Override
	@Transactional
	public ResponseEntity<CategoriaResponseRest> actualizarParcial(Map<String, Object> updates, Long id) {
	    log.info("Inicio método actualizarParcial");
	    CategoriaResponseRest response = new CategoriaResponseRest();
	    List<Categoria> list = new ArrayList<>();

	    try {
	        Optional<Categoria> categoriaBuscada = categoriaDao.findById(id);

	        if (categoriaBuscada.isPresent()) {
	            Categoria categoria = categoriaBuscada.get();

	            updates.forEach((key, value) -> {
	                switch (key) {
	                    case "nombre":
	                        categoria.setNombre((String) value);
	                        break;
	                    case "descripcion":
	                        categoria.setDescripcion((String) value);
	                        break;
	                    default:
	                        log.warn("Campo no reconocido: {}", key);
	                }
	            });

	            Categoria categoriaActualizada = categoriaDao.save(categoria);

	            if (categoriaActualizada != null) {
	                response.setMetadata("Respuesta ok", "00", "Categoría actualizada parcialmente");
	                list.add(categoriaActualizada);
	                response.getCategoriaResponse().setCategoria(list);
	            } else {
	                log.error("Error al guardar los cambios de la categoría");
	                response.setMetadata("Respuesta nok", "-1", "No se pudo actualizar la categoría");
	                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	            }
	        } else {
	            log.error("Categoría no encontrada");
	            response.setMetadata("Respuesta nok", "-1", "Categoría no encontrada");
	            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
	        }
	    } catch (Exception e) {
	        log.error("Error en actualizarParcial", e);
	        response.setMetadata("Respuesta nok", "-1", "Error al actualizar parcialmente la categoría");
	        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	    }

	    return new ResponseEntity<>(response, HttpStatus.OK);
	}

}
