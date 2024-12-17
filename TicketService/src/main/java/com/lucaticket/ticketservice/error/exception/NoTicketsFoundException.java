package com.lucaticket.ticketservice.error.exception;

public class NoTicketsFoundException extends RuntimeException {
    public NoTicketsFoundException(String message) {
        super(message);
    }
}