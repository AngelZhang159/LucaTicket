package com.lucaticket.compraservice.model.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class CompraResponse {
	
	private final String mensaje = "Compra realizada !";
	private String ownerName;
	private String email;
	private Long idEvento;
	private String nombreEvento;
	private Double price;
	private LocalDateTime fechaEvento;
	
}
