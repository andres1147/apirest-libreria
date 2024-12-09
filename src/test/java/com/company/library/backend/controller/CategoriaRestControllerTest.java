package com.company.library.backend.controller;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyMap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.company.library.backend.model.Categoria;
import com.company.library.backend.model.dao.ICategoriaDao;
import com.company.library.backend.response.CategoriaResponse;
import com.company.library.backend.response.CategoriaResponseRest;
import com.company.library.backend.service.ICategoriaService;

public class CategoriaRestControllerTest {
	
	@InjectMocks
	CategoriaRestController categoriaController;
	
	@Mock
	private ICategoriaService service;
	
	@Mock
	ICategoriaDao categoriaDao;
	  
	List<Categoria> list = new ArrayList<Categoria>();
	
	@BeforeEach
	public void init() {
		MockitoAnnotations.openMocks(this);
		this.cargarCategorias();
	}
	
	@Test
	public void crearTest() {
		
		MockHttpServletRequest request = new MockHttpServletRequest();
		RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

		Categoria categoria = new Categoria(Long.valueOf(1), "Clásicos", "libros clásicos de literatura mdderna");
		
		when(service.crear(any(Categoria.class))).thenReturn(new ResponseEntity<CategoriaResponseRest>(HttpStatus.OK));
		
		ResponseEntity<CategoriaResponseRest> respuesta = categoriaController.crear(categoria);
		
		assertThat(respuesta.getStatusCodeValue()).isEqualTo(200);		
	}
	
	@Test
	public void buscarCategoriasTest() {
	    MockHttpServletRequest request = new MockHttpServletRequest();
	    RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

	    CategoriaResponseRest categoriaResponseRest = new CategoriaResponseRest();
	    CategoriaResponse categoriaResponse = new CategoriaResponse();
	    
	    categoriaResponse.setCategoria(list);
	    categoriaResponseRest.setCategoriaResponse(categoriaResponse);

	    when(service.buscarCategorias()).thenReturn(new ResponseEntity<>(categoriaResponseRest, HttpStatus.OK));

	    ResponseEntity<CategoriaResponseRest> respuesta = categoriaController.consultaCat();

	    assertThat(respuesta.getStatusCodeValue()).isEqualTo(200);
	    assertThat(respuesta.getBody().getCategoriaResponse().getCategoria()).hasSize(3);
	    assertThat(respuesta.getBody().getCategoriaResponse().getCategoria().get(0).getNombre()).isEqualTo("Clásicos");
	}
	
	@Test
	public void consultaPorIdTest() {

	    CategoriaResponseRest categoriaResponseRest = new CategoriaResponseRest();
	    CategoriaResponse categoriaResponse = new CategoriaResponse();
	    categoriaResponse.setCategoria(list);
	    categoriaResponseRest.setCategoriaResponse(categoriaResponse);

	    when(service.buscarPorId(1L)).thenReturn(new ResponseEntity<>(categoriaResponseRest, HttpStatus.OK));

	    ResponseEntity<CategoriaResponseRest> respuesta = categoriaController.consultaPorId(1L);

	    assertThat(respuesta.getStatusCodeValue()).isEqualTo(200);
	    assertThat(respuesta.getBody().getCategoriaResponse().getCategoria()).hasSize(3);
	    assertThat(respuesta.getBody().getCategoriaResponse().getCategoria().get(0).getNombre()).isEqualTo("Clásicos");
	    assertThat(respuesta.getBody().getCategoriaResponse().getCategoria().get(0).getDescripcion()).isEqualTo("Literatura universal atemporal");
	}
	
	@Test
	public void eliminarTest() {
	    
	    Long idCategoria = 1L;

	    when(service.eliminar(idCategoria)).thenReturn(new ResponseEntity<>(HttpStatus.NO_CONTENT));

	    ResponseEntity<CategoriaResponseRest> respuesta = categoriaController.eliminar(idCategoria);

	    assertThat(respuesta.getStatusCodeValue()).isEqualTo(204);
	}
	
	@Test
	public void actualizarTest() {
	    
	    Long idCategoria = 1L;
	    
	    CategoriaResponseRest categoriaResponseRest = new CategoriaResponseRest();
	    CategoriaResponse categoriaResponse = new CategoriaResponse();
	    categoriaResponse.setCategoria(list);
	    categoriaResponseRest.setCategoriaResponse(categoriaResponse);

	    when(service.actualizar(categoriaResponse.getCategoria().get(0), idCategoria)).thenReturn(new ResponseEntity<>(categoriaResponseRest, HttpStatus.OK));

	    ResponseEntity<CategoriaResponseRest> respuesta = categoriaController.actualizar(categoriaResponse.getCategoria().get(0), idCategoria);

	    assertThat(respuesta.getStatusCodeValue()).isEqualTo(200);
	    assertThat(respuesta.getBody().getCategoriaResponse().getCategoria().get(0).getNombre()).isEqualTo("Clásicos");
	}
	
	@Test
	public void actualizarParcialTest() {
	    MockHttpServletRequest request = new MockHttpServletRequest();
	    RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

	    Categoria categoriaActualizada = new Categoria(Long.valueOf(1), "Clásicos", "Nueva descripción");

	    CategoriaResponseRest categoriaResponseRest = new CategoriaResponseRest();
	    CategoriaResponse categoriaResponse = new CategoriaResponse();
	    List<Categoria> listaCategorias = new ArrayList<>();
	    listaCategorias.add(categoriaActualizada);

	    categoriaResponse.setCategoria(listaCategorias);
	    categoriaResponseRest.setCategoriaResponse(categoriaResponse);

	    when(service.actualizarParcial(anyMap(), anyLong())).thenReturn(new ResponseEntity<>(categoriaResponseRest, HttpStatus.OK));

	    Map<String, Object> updates = new HashMap<>();
	    updates.put("descripcion", "Nueva descripción");

	    ResponseEntity<CategoriaResponseRest> respuesta = categoriaController.actualizarParcial(updates, 1L);

	    assertEquals(200, respuesta.getStatusCodeValue());
	    assertThat(respuesta.getBody().getCategoriaResponse().getCategoria()).hasSize(1);
	    assertThat(respuesta.getBody().getCategoriaResponse().getCategoria().get(0).getDescripcion()).isEqualTo("Nueva descripción");
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
