package com.lucaticket.ticketservice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TicketController {
	
	/**
	 * @author Raul
	 * @param un objeto con los parametros necesarios para guardar un ticket
	 * @return una entidad de respuesta con un codigo http y el objeto ticket que se ha guardado
	 */
	@PostMapping("/ticket") 
	ResponseEntity<TicketResponse> save(TicketRequest ticket) {
		return ticketService.save(ticket);
	}
	
	/**
	 * @author Raul
	 * @return una lista con todos los tickets registrados
	 */
	@GetMapping("/ticket")
	ResponseEntity<List<TicketResponse>> listTickets() {
		return ticketService.listTickets();
	}
	
}
