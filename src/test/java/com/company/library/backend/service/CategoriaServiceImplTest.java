package com.company.library.backend.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import static org.mockito.ArgumentMatchers.any;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import com.company.library.backend.model.Categoria;
import com.company.library.backend.model.dao.ICategoriaDao;
import com.company.library.backend.response.CategoriaResponseRest;

public class CategoriaServiceImplTest {
	
	@InjectMocks
	CategoriaServiceImpl service;
	
	@Mock
	ICategoriaDao categoriaDao;
	
	List<Categoria> list = new ArrayList<Categoria>();
	
	@BeforeEach
	public void init() {
		MockitoAnnotations.openMocks(this);
		this.cargarCategorias();
	}
	
	@Test
	public void buscarCategoriasTest() {
		
		when(categoriaDao.findAll()).thenReturn(list);
		
		ResponseEntity<CategoriaResponseRest> response = service.buscarCategorias();
		
		assertEquals(3, response.getBody().getCategoriaResponse().getCategoria().size());
		
		verify(categoriaDao, times(1)).findAll();
		
	}
	
	@Test
	public void buscarCategorias_test_Exception() {

	    when(categoriaDao.findAll()).thenThrow(new RuntimeException("Error de base de datos"));

	    ResponseEntity<CategoriaResponseRest> resultado = service.buscarCategorias();

	    assertEquals(500, resultado.getStatusCodeValue());

	    assertEquals("-1", resultado.getBody().getMetadata().get(0).get("codigo"));
	    assertEquals("Respuesta nok", resultado.getBody().getMetadata().get(0).get("tipo"));

	}
	
	@Test
	public void crearTest() {
	    
	    Categoria categoria = new Categoria(Long.valueOf(1), "Clásicos", "libros clásicos de literatura moderna");

	    when(categoriaDao.save(any(Categoria.class))).thenReturn(list.get(1));

	    ResponseEntity<CategoriaResponseRest> resultado = service.crear(list.get(1));

	    assertEquals(1, resultado.getBody().getCategoriaResponse().getCategoria().get(0).getId());
	    assertEquals("Ficción", resultado.getBody().getCategoriaResponse().getCategoria().get(0).getNombre());
	}
	
	@Test
	public void actualizarTest() {

	    Categoria categoriaActualizada = new Categoria(list.get(1).getId(), "Clásicos Modernos", "libros de literatura moderna");

	    when(categoriaDao.findById(list.get(1).getId())).thenReturn(Optional.of(list.get(1)));
	    when(categoriaDao.save(any(Categoria.class))).thenReturn(categoriaActualizada);

	    ResponseEntity<CategoriaResponseRest> resultado = service.actualizar(categoriaActualizada, list.get(1).getId());

	    assertNotNull(resultado);
	    assertEquals(list.get(1).getId(), resultado.getBody().getCategoriaResponse().getCategoria().get(0).getId());
	    assertEquals("Clásicos Modernos", resultado.getBody().getCategoriaResponse().getCategoria().get(0).getNombre());
	    assertEquals("libros de literatura moderna", resultado.getBody().getCategoriaResponse().getCategoria().get(0).getDescripcion());
	}
	
	@Test
	public void buscarPorIdTest() {

	    when(categoriaDao.findById(list.get(0).getId())).thenReturn(Optional.of(list.get(0)));

	    ResponseEntity<CategoriaResponseRest> resultado = service.buscarPorId(list.get(0).getId());

	    assertNotNull(resultado);
	    assertEquals(list.get(1).getId(), resultado.getBody().getCategoriaResponse().getCategoria().get(0).getId());
	    assertEquals("Clásicos", resultado.getBody().getCategoriaResponse().getCategoria().get(0).getNombre());
	    assertEquals("Literatura universal atemporal", resultado.getBody().getCategoriaResponse().getCategoria().get(0).getDescripcion());
	}
	
	@Test
	public void eliminarTest() {

	    when(categoriaDao.findById(list.get(0).getId())).thenReturn(Optional.of(list.get(0)));

	    ResponseEntity<CategoriaResponseRest> resultado = service.eliminar(list.get(0).getId());

	    assertEquals(200, resultado.getStatusCodeValue());

	}
	
	public void cargarCategorias()
	{
		Categoria catUno = new Categoria(Long.valueOf(1), "Clásicos", "Literatura universal atemporal");
		Categoria catdos = new Categoria(Long.valueOf(1), "Ficción", "Historias imaginarias emocionantes");
		Categoria catTercero = new Categoria(Long.valueOf(1), "Románticos", "Amor y sentimientos profundos");
		
		list.add(catUno);
		list.add(catdos);
		list.add(catTercero);
	}
	
}
