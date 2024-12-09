package com.company.library.backend.model;

import java.time.LocalDate;

import javax.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Entity
@Table(name = "prestamo")
@Data
public class Prestamo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
	@JsonIgnoreProperties( {"hibernateLazyInitializer", "handler"})
    private Libro libro;
    
    private String identificacionUsuario;
    
    private int tipoUsuario;
    
    private LocalDate fechaMaximaDevolucion;

}