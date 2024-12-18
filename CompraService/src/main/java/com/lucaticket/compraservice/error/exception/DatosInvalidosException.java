package com.lucaticket.compraservice.error.exception;

/**
 * Utilizada cuando los datos de usuario son erroneos
 */
public class DatosInvalidosException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public DatosInvalidosException() {
		super();
	}

	public DatosInvalidosException(String message) {
		super(message);
	}

}
