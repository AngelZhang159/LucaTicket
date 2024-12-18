package com.lucaticket.compraservice.error.exception;

public class BancoNoDisponibleException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public BancoNoDisponibleException() {
		super();
	}

	public BancoNoDisponibleException(String message) {
		super(message);
	}
}
