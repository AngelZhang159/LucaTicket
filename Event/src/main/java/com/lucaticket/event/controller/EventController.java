package com.lucaticket.event.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lucaticket.event.model.dto.DetailedEventResponse;
import com.lucaticket.event.model.dto.EventRequest;
import com.lucaticket.event.model.dto.EventResponse;
import com.lucaticket.event.service.EventService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@Slf4j
@RequestMapping("/event")
@RequiredArgsConstructor
public class EventController {

	private final EventService eventService;

	/*
	 * @Olivord Guarda el evento en la base de datos
	 */
	@PostMapping
	public ResponseEntity<EventResponse> saveEvent(@RequestBody @Valid EventRequest eventoRequest) {
		log.info("EventController.saveEvent");
		return eventService.saveEvent(eventoRequest);
	}

	/**
	 * @author Raul
	 * @return una lista con todos los eventos
	 */
	@GetMapping("/listAll")
	public ResponseEntity<List<EventResponse>> getEvents() {
		log.info("EventController.getEvents");
		return eventService.getEvents();
	}
	
	/**
	 * @author Angel
	 * @param id
	 * @return el evento con todos los detalles
	 */

	@GetMapping("/detail/{id}")
	public ResponseEntity<DetailedEventResponse> getDetail(@PathVariable long id) {
		return eventService.getDetailedInfoEvent(id);
	}

}
