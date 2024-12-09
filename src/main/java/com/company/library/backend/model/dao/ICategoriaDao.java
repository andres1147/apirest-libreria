package com.company.library.backend.model.dao;

import org.springframework.data.repository.CrudRepository;

import com.company.library.backend.model.Categoria;

public interface ICategoriaDao extends CrudRepository<Categoria,Long>{

}
