package com.lucaticket.ticketservice.error.exception;

public class TicketAlreadyExistsException extends RuntimeException {
	/**
     * Constructor que acepta un mensaje descriptivo sobre la excepción.
     *
     * @param message el mensaje que describe la causa de la excepción.
     */
    public TicketAlreadyExistsException(String message) {
        super(message);
    }
}
