package com.lucaticket.ticketservice.error;

public class TicketAlreadyExistsException extends RuntimeException {
    public TicketAlreadyExistsException(String message) {
        super(message);
    }
}
