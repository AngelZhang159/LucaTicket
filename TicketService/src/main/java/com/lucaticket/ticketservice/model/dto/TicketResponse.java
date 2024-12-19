package com.lucaticket.ticketservice.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TicketResponse {

	/**
	 * @author Alberto de la Blanca
	 * **/
	private Long id;
	/**
	 * Correo electrónico del usuario asociado al ticket.
	 */
	private String email;

	/**
	 * Identificador del evento al que pertenece este ticket.
	 */
	private Long idEvent;
	
	/**
	 * precio del evento
	 */
	private Double price;

	/*
	 * Precio del ticket
	 */
	private double price;
}
