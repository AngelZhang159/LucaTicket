package com.lucaticket.ticketservice.service;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.lucaticket.ticketservice.model.Ticket;
import com.lucaticket.ticketservice.model.dto.TicketRequest;
import com.lucaticket.ticketservice.model.dto.TicketResponse;
import com.lucaticket.ticketservice.repository.TicketRepository;

import lombok.RequiredArgsConstructor;

public interface TicketService {

	public ResponseEntity<TicketResponse> save(TicketRequest ticketRequest);
	
    /**
     * @author Alberto de la Blanca
     * Recupera una lista de todos los tickets disponibles en el sistema.
     * 
     * @return ResponseEntity que contiene una lista de tickets.
     */
	
	ResponseEntity<List<Ticket>> listTickets();
}
