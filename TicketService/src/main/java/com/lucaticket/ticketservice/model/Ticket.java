package com.lucaticket.ticketservice.model;

import com.lucaticket.ticketservice.model.dto.TicketResponse;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Alberto de la Blanca Representa un Ticket asociado a un evento en el
 *         sistema. Cada ticket tiene un identificador único, un correo
 *         electrónico asociado y un identificador del evento correspondiente.
 * 
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "tickets")
public class Ticket {

	/**
	 * Identificador único del ticket. Generado automáticamente..
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long IdTicket;

	/**
	 * Correo electrónico del usuario asociado al ticket.
	 */
	private String email;

	/**
	 * Identificador del evento al que pertenece este ticket.
	 */
	private Long idEvent;

	/**
	 * Mapper a DTO para la respuesta
	 * 
	 * @author Angel
	 * @return el ticket DTO response
	 */

	public TicketResponse toDTO() {

		TicketResponse response = new TicketResponse();
		response.setEmail(this.email);
		response.setIdEvent(this.idEvent);

		return response;
	}

}
