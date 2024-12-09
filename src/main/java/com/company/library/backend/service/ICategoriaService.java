package com.company.library.backend.service;

import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.company.library.backend.model.Categoria;
import com.company.library.backend.response.CategoriaResponseRest;

public interface ICategoriaService {
	
	public ResponseEntity<CategoriaResponseRest> buscarCategorias();
	public ResponseEntity<CategoriaResponseRest> buscarPorId(Long id);
	public ResponseEntity<CategoriaResponseRest> crear(Categoria categoria);
	public ResponseEntity<CategoriaResponseRest> actualizar(Categoria categoria, Long id);
	public ResponseEntity<CategoriaResponseRest> eliminar(Long id);
	public ResponseEntity<CategoriaResponseRest> actualizarParcial(Map<String, Object> updates, Long id);

}
