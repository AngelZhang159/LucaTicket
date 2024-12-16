package com.lucaticket.compraservice.error;

public class DatosCompraInvalidosException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public DatosCompraInvalidosException() {
		super();
	}

	public DatosCompraInvalidosException(String message) {
		super(message);
	}
}
