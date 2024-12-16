package com.lucaticket.compraservice.model.dto;

import java.util.List;

import lombok.Data;

@Data
public class ValidarCompraResponse {

	    private String timestamp;
	    private int status;
	    private String error;
	    private List<String> message;

	    private String nombreTitular;
	    private String numeroTarjeta;
	    private int mesCaducidad;
	    private int yearCaducidad;
	    private int cvv;
	    private String emisor;
	    private String concepto;
	    private double cantidad;
	    
	    private String infoadicional;
}
