package com.lucaticket.compraservice.model.dto;

import java.time.Month;
import java.time.Year;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
	private String ownerName;
	private Long cardNumber; 
	private Month expirationMonth;
	private Year expirationYear;
	private int cvv;
	private String emisor;
	private String concepto;
	private double cantidad;
	private Long idEvento;
	
	
}
