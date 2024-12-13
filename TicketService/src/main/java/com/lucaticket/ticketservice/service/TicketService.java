package com.lucaticket.ticketservice.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.lucaticket.ticketservice.model.dto.TicketRequest;
import com.lucaticket.ticketservice.model.dto.TicketResponse;
import com.lucaticket.ticketservice.repository.TicketRepository;

import lombok.RequiredArgsConstructor;

public interface TicketService {

	public ResponseEntity<TicketResponse> save(TicketRequest ticketRequest);
}
