package com.company.library.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.company.library.backend.model.Prestamo;
import com.company.library.backend.response.PrestamoResponseRest;
import com.company.library.backend.service.IPrestamoService;

@RestController
@RequestMapping("/v1")
public class PrestamoRestController {

	@Autowired
	private IPrestamoService service;
	
	@GetMapping("/prestamos")
	public ResponseEntity<PrestamoResponseRest> consultaCat() {
		ResponseEntity<PrestamoResponseRest> response = service.buscarPrestamos();
		return response;
	}
	
	@GetMapping("/prestamos/{id}")
	public ResponseEntity<PrestamoResponseRest> consultaPorId(@PathVariable Long id) {
		ResponseEntity<PrestamoResponseRest> response = service.buscarPorId(id);
		return response;
	}
	
	@PostMapping("/prestamos")
	public ResponseEntity<PrestamoResponseRest> crear(@RequestBody Prestamo request) {
		ResponseEntity<PrestamoResponseRest> response = service.crear(request);
		return response;
	}
	
	@DeleteMapping("/prestamos/{id}")
	public ResponseEntity<PrestamoResponseRest> eliminar(@PathVariable Long id) {
		ResponseEntity<PrestamoResponseRest> response = service.eliminar(id);
		return response;
	}
	
}
