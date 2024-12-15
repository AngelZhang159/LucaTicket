package com.lucaticket.ticketservice.error;

public class NoTicketsFoundException extends RuntimeException {
    public NoTicketsFoundException(String message) {
        super(message);
    }
}