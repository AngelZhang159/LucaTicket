package com.lucaticket.ticketservice.service.impl;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.lucaticket.ticketservice.error.NoTicketsFoundException;
import com.lucaticket.ticketservice.error.TicketAlreadyExistsException;
import com.lucaticket.ticketservice.model.Ticket;
import com.lucaticket.ticketservice.model.dto.TicketRequest;
import com.lucaticket.ticketservice.model.dto.TicketResponse;
import com.lucaticket.ticketservice.repository.TicketRepository;
import com.lucaticket.ticketservice.service.TicketService;

import feignclients.EventFeignClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class TicketServiceImpl implements TicketService {

	private final TicketRepository ticketRepository;

	private final EventFeignClient eventFeignClient;

	/**
	 * @author Angel
	 * @param ticketRequest
	 * @return ticketRespon
	 * @throws TicketAlreadyExistsException
	 */
	public ResponseEntity<TicketResponse> save(TicketRequest ticketRequest) {
		// update v.1.1
		// Yuji
		log.info("Service: Guardando nuevo ticket: " + ticketRequest.toString());
		return new ResponseEntity<>(ticketRepository.save(ticketRequest.toEntity()).toDTO(), HttpStatus.CREATED);
	}

	/**
	 * @author Angel
	 * @return List<Ticket>
	 * @throws NoTicketsFoundException
	 */
	public ResponseEntity<List<TicketResponse>> listTickets() {
		// REcupera todos los tickets de la bdd
		// Yuji
		// update v.1.1
		log.info("Service: Listando todos los tickets");
		List<Ticket> tickets = ticketRepository.findAll();
		if (tickets.isEmpty()) {
			throw new NoTicketsFoundException("No hay tickets registrados en la base de datos");
		}

		log.info("Service: Devolviendo tickets, tamaño: " + tickets.size());
		return ResponseEntity.ok(tickets.stream().map(Ticket::toDTO).toList());
	}

	/**
	 * @author Angel
	 * @return List<Ticket>
	 * @throws NoTicketsFoundException
	 */
	public ResponseEntity<DetailedTicketResponse> listTicketsByEmail(String email) {
		log.info("Service: Obteniendo tickets de: " + email);
		List<DetailedTicketResponse> tickets = ticketRepository.findByEmail(email);
		if (tickets.isEmpty()) {
			throw new NoTicketsFoundException("No hay tickets registrados para el usuario: " + email);
		}
		
		log.info("Service: Devolviendo tickets, tamaño: " + tickets.size());
		return ResponseEntity.ok(tickets.stream().map(Ticket::toDetailedDTO).toList());
	}
}
