package com.lucaticket.compraservice.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompraRequest {
	
	@Email @NotBlank
	private String email; 
	@NotBlank
	private String nombreTitular;
	private Long numeroTarjeta; 
	private int mesCaducidad;
	private int yearCaducidad;
	private int cvv;
	private String emisor;
	private String concepto;
	private double cantidad;
	private Long idEvento;
	
	
}
