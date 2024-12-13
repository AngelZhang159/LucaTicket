package com.lucaticket.ticketservice.service.impl;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.lucaticket.ticketservice.model.dto.TicketRequest;
import com.lucaticket.ticketservice.repository.TicketRepository;
import com.lucaticket.ticketservice.service.TicketService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TicketServiceImpl implements TicketService{

	private final TicketRepository ticketRepository;

	/**
	 * @author Angel
	 * @param ticketRequest
	 * @return ticketResponse
	 */
	public ResponseEntity<TicketResponse> save(TicketRequest ticketRequest) {
		return new ResponseEntity<TicketResponse>(ticketRepository.save(ticketRequest.toEntity()).toDTO(), HttpStatus.CREATED);
	}
}
