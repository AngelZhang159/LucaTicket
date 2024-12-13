package com.lucaticket.ticketservice.model.dto;

import com.lucaticket.ticketservice.model.Ticket;

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
	 * Mapper request a entidad
	 * @author Angel
	 * @return la entidad
	 */
	public Ticket toEntity() {
		Ticket ticket = new Ticket();
		ticket.setEmail(this.email);
		ticket.setIdEvent(this.idEvent);
		return ticket;
	}

}
