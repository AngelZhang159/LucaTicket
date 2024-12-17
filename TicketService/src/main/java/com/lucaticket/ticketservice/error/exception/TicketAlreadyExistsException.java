package com.lucaticket.ticketservice.error.exception;

public class TicketAlreadyExistsException extends RuntimeException {
    public TicketAlreadyExistsException(String message) {
        super(message);
    }
}
