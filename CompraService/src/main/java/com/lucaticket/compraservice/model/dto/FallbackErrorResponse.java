package com.lucaticket.compraservice.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FallbackErrorResponse {
    private int status;
    private String error;
    private String message;
}
