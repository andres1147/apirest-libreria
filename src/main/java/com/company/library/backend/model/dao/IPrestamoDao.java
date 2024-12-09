package com.company.library.backend.model.dao;

import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import com.company.library.backend.model.Prestamo;

public interface IPrestamoDao extends CrudRepository<Prestamo,Long>{

	Optional<Prestamo> findByIdentificacionUsuario(String identificacionUsuario);
}
