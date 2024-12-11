package com.lucaticket.event.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.lucaticket.event.error.InvalidDataException;
import com.lucaticket.event.model.Event;
import com.lucaticket.event.model.dto.DetailedEventResponse;
import com.lucaticket.event.model.dto.EventDTO;
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
		comprobarPrecio(eventoRequest.getMinPrice(), eventoRequest.getMaxPrice());
		return ResponseEntity.ok(eventRepository.save(eventoRequest.toEntity()).toDto());
	}

	/**
	 * @author Raul
	 * @return una lista con todos los eventos
	 */
	@Override
	public ResponseEntity<List<EventResponse>> getEvents() {
		if (eventRepository.findAll().isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return ResponseEntity.ok(eventRepository.findAll().stream().map(a -> a.toDto()).toList());
	}

	/**
	 * @author Alberto de la Blanca
	 * @return una DTO con la información detallada del evento.
	 */
	@Override
	public ResponseEntity getDetailedInfoEvent(Long eventId) {
		Event event = eventRepository.findById(eventId)
				.orElseThrow(() -> new InvalidDataException("El evento con ID: " + eventId + " no existe."));

		return ResponseEntity.ok(event.toDetailedDto());
	}

	/**
	 * @author Angel
	 * @param name
	 * @return Lista con todos los eventos que coincidan con el nombre
	 */

	@Override
	public ResponseEntity<List<EventResponse>> findByName(String name) {
		List<EventResponse> eventResponses = eventRepository.findByName(name).stream().map(e -> e.toDto()).toList();
		if (eventResponses.size() == 0) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok(eventResponses);
	}
	
	/**
	 * @author Alberto de la Blanca
	 * @return una DTO con la información detallada del evento buscando por nombre
	 */
	
	@Override
	public ResponseEntity<DetailedEventResponse> getDetailedInfoEventByName(String eventName) {
	    List<Event> events = eventRepository.findByName(eventName);
	    if (events.isEmpty()) {
	        throw new InvalidDataException("El evento con nombre '" + eventName + "' no existe.");
	    }

	    Event event = events.get(0); // Asumiendo que el nombre es único o tomas el primero
	    DetailedEventResponse response = event.toDetailedDto();
	    return ResponseEntity.ok(response);
	}

	@Override
	public ResponseEntity<DetailedEventResponse> updateEvent(EventDTO event) {

		Event eventOld = eventRepository.findById(event.getId())
				.orElseThrow(() -> new InvalidDataException("El evento con el id: " + event.getId() + " no existe"));

		Event eventNew = new Event(eventOld.getId(), eventOld.getName(), eventOld.getDescription(),
				eventOld.getEventDate(), eventOld.getMinPrice(), eventOld.getMaxPrice(), eventOld.getLocation(),
				eventOld.getVenueName(), eventOld.getGenre());

		if (!(event.getName() == null || event.getName().isBlank())) {
			eventNew.setName(event.getName());
		}
		if (!(event.getDescription() == null || event.getDescription().isBlank())) {
			eventNew.setDescription(event.getDescription());
		}
		if (!(event.getEventDate() == null)) {
			eventNew.setEventDate(event.getEventDate());
		}
		if (!(event.getMinPrice() <= 0)) {
			eventNew.setMinPrice(event.getMinPrice());
		}
		if (!(event.getMaxPrice() <= 0)) {
			eventNew.setMaxPrice(event.getMaxPrice());
		}
		if (!(event.getLocation() == null || event.getLocation().isBlank())) {
			eventNew.setLocation(event.getLocation());
		}
		if (!(event.getGenre() == null)) {
			eventNew.setGenre(event.getGenre());
		}
		
		comprobarPrecio(eventNew.getMinPrice(), eventNew.getMaxPrice());

		return ResponseEntity.ok(eventRepository.save(eventNew).toDetailedDto());
	}

	private void comprobarPrecio(double min, double max) {
		if (min > max) {
			throw new InvalidDataException("El precio mínimo no puede superar al máximo");
		}
	}

}
