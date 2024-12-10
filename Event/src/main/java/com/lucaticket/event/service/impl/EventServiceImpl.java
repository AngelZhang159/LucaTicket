package com.lucaticket.event.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
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

	@Override
	public ResponseEntity<EventResponse> saveEvento(EventRequest eventoRequest) {
		return ResponseEntity.ok(eventRepository.save(eventoRequest.toEntity()).toDto());
	}

}
