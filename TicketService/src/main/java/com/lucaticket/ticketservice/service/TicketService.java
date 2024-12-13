package com.lucaticket.ticketservice.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TicketService {

	private final TicketRepository ticketRepository;

	/**
	 * @author Angel
	 * @param ticketRequest
	 * @return ticketResponse
	 */
	public ResponseEntity<TicketResponse> save(TicketRequest ticketRequest) {
		return new ResponseEntity<TicketResponse>(ticketRepository.save(ticketRequest.toEntity).toDTO, HttpStatus.CREATED);
	}
}
