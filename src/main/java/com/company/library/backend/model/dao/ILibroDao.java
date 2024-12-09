package com.company.library.backend.model.dao;

import org.springframework.data.repository.CrudRepository;

import com.company.library.backend.model.Libro;

public interface ILibroDao extends CrudRepository<Libro, Long>{

}