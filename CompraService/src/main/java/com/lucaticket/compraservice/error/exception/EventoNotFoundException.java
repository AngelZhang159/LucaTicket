package com.lucaticket.compraservice.error.exception;

/**
 * Utilizada cuando el evento buscado no existe
 */
public class EventoNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public EventoNotFoundException() {
		super();
	}

	public EventoNotFoundException(String message) {
		super(message);
	}

}
