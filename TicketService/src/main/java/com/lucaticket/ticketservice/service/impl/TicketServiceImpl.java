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
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class TicketServiceImpl implements TicketService {

	private final TicketRepository ticketRepository;

	/**
	 * @author Angel
	 * @param ticketRequest
	 * @return ticketResponse
	 */
	public ResponseEntity<TicketResponse> save(TicketRequest ticketRequest) {
		log.info("Service: Guardando nuevo ticket: " + ticketRequest.toString());
		return new ResponseEntity<>(ticketRepository.save(ticketRequest.toEntity()).toDTO(),
				HttpStatus.CREATED);
	}

	public ResponseEntity<List<TicketResponse>> listTickets() {
		// REcupera todos los tickets de la bdd
		log.info("Service: Listando todos los tickets");
		List<Ticket> tickets = ticketRepository.findAll();
		if (tickets.isEmpty()) {
			log.info("Service: No se han encontrado tickets. 204");
			return ResponseEntity.noContent().build();
		}
		log.info("Service: Devolviendo tickets, tamaño: " + tickets.size() + ". 200");
		return ResponseEntity.ok(tickets.stream().map(Ticket::toDTO).toList());
	}
}
