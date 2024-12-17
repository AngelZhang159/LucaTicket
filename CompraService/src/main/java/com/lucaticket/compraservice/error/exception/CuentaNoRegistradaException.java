package com.lucaticket.compraservice.error.exception;

public class CuentaNoRegistradaException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * Constructor que acepta un mensaje de error.
     *
     * @param message El mensaje detallado de la excepción.
     */
    public CuentaNoRegistradaException(String message) {
        super(message);
    }

    /**
     * Constructor que acepta un mensaje de error y una causa.
     *
     * @param message El mensaje detallado de la excepción.
     * @param cause   La causa original de la excepción.
     */
    public CuentaNoRegistradaException(String message, Throwable cause) {
        super(message, cause);
    }
}
