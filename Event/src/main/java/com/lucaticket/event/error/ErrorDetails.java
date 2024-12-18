package com.lucaticket.event.error;

/**
 * @since 12-12-2024
 * @version 1.0
 * Esta clase representa los detalles de un error en la aplicación. Se utiliza para almacenar
 * información relevante sobre el error, como el código de estado HTTP, el mensaje de error
 * y detalles adicionales que pueden ser útiles para el diagnóstico.
 */
 class ErrorDetails {
    private int statusCode;
    private String message;
    private String details;

    public ErrorDetails(int statusCode, String message, String details) {
        this.statusCode = statusCode;
        this.message = message;
        this.details = details;
    }

    // Getters y setters.
    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}