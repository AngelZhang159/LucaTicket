package com.lucaticket.compraservice.error.exception;

/**
 * Utilizada cuando el servicio de banco no está disponible
 */
public class BancoNoDisponibleException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public BancoNoDisponibleException() {
		super();
	}

	public BancoNoDisponibleException(String message) {
		super(message);
	}
}
