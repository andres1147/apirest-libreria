package com.company.library.backend.model;

import java.io.Serializable;
import javax.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Entity
@Table(name="libros")
@Data
public class Libro implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6992684974740872661L;

	@Id
	@GeneratedValue( strategy =  GenerationType.IDENTITY)
	private Long id;
	
	private String nombre;
	
	private String descripcion;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JsonIgnoreProperties( {"hibernateLazyInitializer", "handler"})
	private Categoria categoria;
	
}
