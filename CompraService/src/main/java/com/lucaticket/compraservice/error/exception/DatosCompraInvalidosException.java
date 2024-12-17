package com.lucaticket.compraservice.error.exception;

public class DatosCompraInvalidosException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public DatosCompraInvalidosException() {
		super();
	}

	public DatosCompraInvalidosException(String message) {
		super(message);
	}
}
