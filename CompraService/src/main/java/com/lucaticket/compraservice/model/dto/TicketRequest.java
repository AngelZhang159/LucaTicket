package com.lucaticket.compraservice.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TicketRequest {

	/**
	 * Correo electrónico del usuario asociado al ticket.
	 */
	private String email;

	/**
	 * Identificador del evento al que pertenece este ticket.
	 */
	private Long idEvent;
	
	/**
	 *  precio del ticket
	 */
	private Double price;
}