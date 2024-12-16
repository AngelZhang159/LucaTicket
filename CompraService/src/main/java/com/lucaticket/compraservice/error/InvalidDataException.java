package com.lucaticket.compraservice.error;

/**
 * Excepción personalizada para indicar que los datos proporcionados son inválidos.
 */
public class InvalidDataException extends RuntimeException {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * Constructor que acepta un mensaje de error.
     *
     * @param message El mensaje detallado de la excepción.
     */
    public InvalidDataException(String message) {
        super(message);
    }

    /**
     * Constructor que acepta un mensaje de error y una causa.
     *
     * @param message El mensaje detallado de la excepción.
     * @param cause   La causa original de la excepción.
     */
    public InvalidDataException(String message, Throwable cause) {
        super(message, cause);
    }
}