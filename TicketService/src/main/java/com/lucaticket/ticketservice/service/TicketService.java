package com.lucaticket.ticketservice.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.lucaticket.ticketservice.model.dto.DetailedTicketResponse;
import com.lucaticket.ticketservice.model.dto.TicketRequest;
import com.lucaticket.ticketservice.model.dto.TicketResponse;

public interface TicketService {

	/**
	 * Guarda un nuevo ticket
	 * 
	 * @author Angel
	 * @param ticketRequest
	 * @return
	 */

	ResponseEntity<TicketResponse> save(TicketRequest ticketRequest);

	/**
	 * @author Alberto de la Blanca 
	 * Recupera una lista de todos los tickets disponibles en el sistema.
	 * 
	 * @return ResponseEntity que contiene una lista de tickets.
	 */

	ResponseEntity<List<TicketResponse>> listTickets();

	/**
	 * Guarda un nuevo ticket
	 * 
	 * @author Yuji
	 * @param email
	 * @return ResponseEntity<DetailedTicketResponse>
	 */
	ResponseEntity<List<DetailedTicketResponse>> listTicketsByEmail(String email);
}
