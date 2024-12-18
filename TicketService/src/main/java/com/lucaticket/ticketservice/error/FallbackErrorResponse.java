package com.lucaticket.ticketservice.error;

import lombok.AllArgsConstructor;
import lombok.Data;
/**
 *@author Raul 
 **/
@Data
@AllArgsConstructor
public class FallbackErrorResponse {
    private int status;
    private String error;
    private String message;
    private Object fallbackData;
}

