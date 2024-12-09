package com.company.library.backend.response;

import java.util.List;
import com.company.library.backend.model.Categoria;

public class CategoriaResponse {

	private List<Categoria> categoria;

	public List<Categoria> getCategoria() {
		return categoria;
	}

	public void setCategoria(List<Categoria> categoria) {
		this.categoria = categoria;
	}
	
}
