package com.lucaticket.compraservice.model.dto;

import java.time.Month;
import java.time.Year;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class CompraRequest {
	
	@Email @NotBlank
	private String email; 
	@NotBlank
	private String ownerName;
	@NotNull @Size(max = 16, min = 16)
	private Long cardNumber; 
	@NotBlank
	private Month expirationMonth;
	@NotBlank
	private Year expirationYear;
	@NotBlank @Size(max = 3, min = 3)
	private int cvv;
	
}
