package com.company.library.backend.response;

import java.util.List;
import com.company.library.backend.model.Prestamo;

public class PrestamoResponse {

	private List<Prestamo> prestamo;
	
	public List<Prestamo> getPrestamo() {
		return prestamo;
	}

	public void setPrestamo(List<Prestamo> prestamo) {
		this.prestamo = prestamo;
	}
	
}
