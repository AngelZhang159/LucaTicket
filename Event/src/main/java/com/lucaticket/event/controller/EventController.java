package com.lucaticket.event.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lucaticket.event.model.dto.DetailedEventResponse;
import com.lucaticket.event.model.dto.EventCreateDelete;
import com.lucaticket.event.model.dto.EventDTO;
import com.lucaticket.event.model.dto.EventRequest;
import com.lucaticket.event.model.dto.EventResponse;
import com.lucaticket.event.service.EventService;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
/*
 * @since 11-12-2024
 * @version 6.0
 */
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
	@Operation(description = "Guarda un evento en la base de datos de Eventos a partir de una peticion de evento con todos los atributos necesarios")
	public ResponseEntity<EventCreateDelete> saveEvent(@RequestBody @Valid EventRequest eventoRequest) {
		log.info("EventController.saveEvent");
		return eventService.saveEvent(eventoRequest);
	}

	/**
	 * @author Raul
	 * @return una lista con todos los eventos
	 */
	@GetMapping("/listAll")
	@Operation(description = "Lista todos los eventos registrados en la base de datos de Eventos")
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
	@Operation(description = "Devuelve información adicional de un evento en específico")
	public ResponseEntity<DetailedEventResponse> getDetail(@PathVariable long id) {
		return eventService.getDetailedInfoEvent(id);
	}

	/**
	 * @author Raul
	 * @param el nombre del evento
	 * @return una lista con los eventos que tengan ese nombre
	 */
	@GetMapping("/list/{name}")
	@Operation(description = "Devuelve una lista de eventos filtrando por el nombre proporcionado")
	public ResponseEntity<List<EventResponse>> listByName(@PathVariable String name) {
		return eventService.findByName(name);
	}

	/**
	 * @author Angel
	 * @param event
	 * @return evento detallado
	 */

	@PutMapping("/update/{id}")
	@Operation(description = "Actualiza un evento existente a partir de un ID de evento y una peticion con los atributos que se quieran cambiar")
	public ResponseEntity<Map<String, Object>> updateEvent(@PathVariable long id, @RequestBody EventDTO event) {
		event.setId(id);
		return eventService.updateEvent(event);
	}

	/**
	 * @author Angel
	 * @param id
	 * @return evento borrado
	 */

	@DeleteMapping("/delete/{id}")
	@Operation(description = "Borra un evento de la base de datos a partir de un ID")
	public ResponseEntity<EventCreateDelete> deleteEvent(@PathVariable long id) {
		return eventService.deleteEvent(id);
	}

}
