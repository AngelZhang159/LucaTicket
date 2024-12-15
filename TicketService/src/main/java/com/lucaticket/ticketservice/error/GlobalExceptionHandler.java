package com.lucaticket.ticketservice.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @ExceptionHandler(TicketNotFoundException.class)
    public ResponseEntity<Object> handleTicketNotFoundException(TicketNotFoundException ex, WebRequest request) {
        return buildResponseEntity(
                ex.getMessage(),
                HttpStatus.NOT_FOUND,
                "TICKET_NOT_FOUND",
                request);
    }

    @ExceptionHandler(TicketAlreadyExistsException.class)
    public ResponseEntity<Object> handleTicketAlreadyExistsException(TicketAlreadyExistsException ex, WebRequest request) {
        return buildResponseEntity(
                ex.getMessage(),
                HttpStatus.CONFLICT,
                "TICKET_ALREADY_EXISTS",
                request);
    }

    @ExceptionHandler(NoTicketsFoundException.class)
    public ResponseEntity<Object> handleNoTicketsFoundException(NoTicketsFoundException ex, WebRequest request) {
        return buildResponseEntity(
                ex.getMessage(),
                HttpStatus.NO_CONTENT,
                "NO_TICKETS_FOUND",
                request);
    }


    private ResponseEntity<Object> buildResponseEntity(String message, HttpStatus status, String errorCode,
            WebRequest request) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now().format(DATE_TIME_FORMATTER));
        body.put("status", status.value());
        body.put("error", status.getReasonPhrase());
        body.put("message", message);
        body.put("errorCode", errorCode);
        body.put("path", request.getDescription(false).replace("uri=", ""));
        return new ResponseEntity<>(body, status);
    }
}
