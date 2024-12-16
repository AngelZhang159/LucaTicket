package com.lucaticket.event.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lucaticket.event.model.Event;
import com.lucaticket.event.model.dto.DetailedEventResponse;
import com.lucaticket.event.model.dto.EventDTO;
import com.lucaticket.event.model.dto.EventCreateDelete;
import com.lucaticket.event.model.dto.EventRequest;
import com.lucaticket.event.model.dto.EventResponse;
import com.lucaticket.event.service.EventService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

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
	public ResponseEntity<EventCreateDelete> saveEvent(@RequestBody @Valid EventRequest eventoRequest) {
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

	/**
	 * @author Raul
	 * @param el nombre del evento
	 * @return una lista con los eventos que tengan ese nombre
	 */
	@GetMapping("/list/{name}")
	public ResponseEntity<List<EventResponse>> listByName(@PathVariable String name) {
		return eventService.findByName(name);
	}
	
	/**
	 * @author Angel
	 * @param event
	 * @return evento detallado
	 */

	@PutMapping("/update/{id}")
	public ResponseEntity<Map<String, Object>> updateEvent(@PathVariable long id, @RequestBody @Valid EventDTO event) {
		event.setId(id);
		return eventService.updateEvent(event);
	}

	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<EventCreateDelete> deleteEvent(@PathVariable long id) {
		return eventService.deleteEvent(id);
	}
	
}
