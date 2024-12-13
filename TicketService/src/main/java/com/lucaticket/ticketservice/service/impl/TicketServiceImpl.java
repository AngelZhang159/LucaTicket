package com.lucaticket.ticketservice.service.impl;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.lucaticket.ticketservice.model.Ticket;
import com.lucaticket.ticketservice.model.dto.TicketRequest;
import com.lucaticket.ticketservice.model.dto.TicketResponse;
import com.lucaticket.ticketservice.repository.TicketRepository;
import com.lucaticket.ticketservice.service.TicketService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TicketServiceImpl implements TicketService {

	private final TicketRepository ticketRepository;

	/**
	 * @author Angel
	 * @param ticketRequest
	 * @return ticketResponse
	 */
	public ResponseEntity<TicketResponse> save(TicketRequest ticketRequest) {
		return new ResponseEntity<>(ticketRepository.save(ticketRequest.toEntity()).toDTO(),
				HttpStatus.CREATED);
	}

	public ResponseEntity<List<TicketResponse>> listTickets() {
		// REcupera todos los tickets de la bdd
		List<Ticket> tickets = ticketRepository.findAll();
		if (tickets.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok(tickets.stream().map(Ticket::toDTO).toList());
	}
}
