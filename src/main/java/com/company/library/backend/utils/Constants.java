package com.company.library.backend.utils;

import lombok.Getter;

@Getter
public class Constants {
	
	private static final String OK_MESSAGE = "Respuesta ok";
	private static final String SUCCESS_MESSAGE = "Respuesta exitosa";
	private static final String ERROR_MESSAGE = "Respuesta failed";
	private static final String FAILED_MESSAGE = "Respuesta incorrecta";
	
	private static final String CATEGORIA_NO_ENCONTRADA = "Categoria no encontrada";
	private static final String CATEGORIA_ELIMINADA = "Categoria eliminada";
	
	private static final String LIBRO_NO_ENCONTRADO = "Libro no encontrado";
	private static final String LIBRO_ELIMINADO = "Libro eliminado";
	
	private static final String PRESTAMO_NO_ENCONTRADO = "Prestamo no encontrado";
	private static final String PRESTAMO_ELIMINADO = "Prestamo eliminado";
	
	private static final int TIPO_USUARIO_AFILIADO = 1;
	private static final int TIPO_USUARIO_EMPLEADO = 2;
	private static final int TIPO_USUARIO_INVITADO = 3;
	
	public static String getOkMessage() {
		return OK_MESSAGE;
	}
	public static String getSuccessMessage() {
		return SUCCESS_MESSAGE;
	}
	public static String getErrorMessage() {
		return ERROR_MESSAGE;
	}
	public static String getFailedMessage() {
		return FAILED_MESSAGE;
	}
	public static String getCategoriaNoEncontrada() {
		return CATEGORIA_NO_ENCONTRADA;
	}
	public static String getCategoriaEliminada() {
		return CATEGORIA_ELIMINADA;
	}
	public static String getLibroNoEncontrado() {
		return LIBRO_NO_ENCONTRADO;
	}
	public static String getLibroEliminado() {
		return LIBRO_ELIMINADO;
	}
	public static String getPrestamoNoEncontrado() {
		return PRESTAMO_NO_ENCONTRADO;
	}
	public static String getPrestamoEliminado() {
		return PRESTAMO_ELIMINADO;
	}
	public static int getTipoUsuarioAfiliado() {
		return TIPO_USUARIO_AFILIADO;
	}
	public static int getTipoUsuarioEmpleado() {
		return TIPO_USUARIO_EMPLEADO;
	}
	public static int getTipoUsuarioInvitado() {
		return TIPO_USUARIO_INVITADO;
	}
	
}
