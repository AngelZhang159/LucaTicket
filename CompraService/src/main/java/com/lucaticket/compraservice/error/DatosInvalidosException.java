package com.lucaticket.compraservice.error;

public class DatosInvalidosException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public DatosInvalidosException() {
		super();
	}

	public DatosInvalidosException(String message) {
		super(message);
	}

}
