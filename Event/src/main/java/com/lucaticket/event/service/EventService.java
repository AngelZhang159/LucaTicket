package com.lucaticket.event.service;

import org.springframework.http.ResponseEntity;

import com.lucaticket.event.model.dto.EventRequest;
import com.lucaticket.event.model.dto.EventResponse;

public interface EventService {

	/**
	 * @author Raul
	 * @param eventoRequest
	 * @return un DTO de respuesta de "evento" con datos de la creacion
	 */
	ResponseEntity<EventResponse> saveEvent(EventRequest eventoRequest);
	
}
