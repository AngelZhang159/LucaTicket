package com.lucaticket.event.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.lucaticket.event.model.dto.EventRequest;
import com.lucaticket.event.model.dto.EventResponse;
import com.lucaticket.event.repository.EventRepository;
import com.lucaticket.event.service.EventService;

@Service
public class EventServiceImpl implements EventService {
//	<-- Atributos -->
	@Autowired
	EventRepository eventRepository;

	/**
	 * @author Raul
	 * @param eventoRequest
	 * @return un DTO de respuesta de "evento" con datos de la creacion
	 */
	@Override
	public ResponseEntity<EventResponse> saveEvent(EventRequest eventoRequest) {
		return ResponseEntity.ok(eventRepository.save(eventoRequest.toEntity()).toDto());
	}

	/**
	 * @author Raul
	 * @return una lista con todos los eventos
	 */
	@Override
	public ResponseEntity<List<EventResponse>> getEvents() {
		if(eventRepository.findAll().isEmpty()) { return new ResponseEntity<>(HttpStatus.NO_CONTENT); }
		return ResponseEntity.ok(eventRepository.findAll()
				  .stream()
				  .map(a -> a.toDto())
				  .toList());
	}

}
