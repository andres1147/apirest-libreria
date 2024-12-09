package com.company.library.backend.service;

import org.springframework.http.ResponseEntity;

import com.company.library.backend.model.Prestamo;
import com.company.library.backend.response.PrestamoResponseRest;

public interface IPrestamoService {
	
	public ResponseEntity<PrestamoResponseRest> buscarPrestamos();
	public ResponseEntity<PrestamoResponseRest> buscarPorId(Long id);
	public ResponseEntity<PrestamoResponseRest> crear(Prestamo prestamo);
	public ResponseEntity<PrestamoResponseRest> eliminar(Long id);

}
