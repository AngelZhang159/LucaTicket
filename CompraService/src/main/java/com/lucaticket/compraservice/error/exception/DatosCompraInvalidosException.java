package com.lucaticket.compraservice.error.exception;

/**
 * Utilizada cuando los datos de la compra son erroneos
 */
public class DatosCompraInvalidosException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public DatosCompraInvalidosException() {
		super();
	}

	public DatosCompraInvalidosException(String message) {
		super(message);
	}
}
