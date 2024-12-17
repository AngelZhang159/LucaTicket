package com.lucaticket.event.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.lucaticket.event.error.InvalidDataException;
import com.lucaticket.event.model.Event;
import com.lucaticket.event.model.dto.DetailedEventResponse;
import com.lucaticket.event.model.dto.EventCreateDelete;
import com.lucaticket.event.model.dto.EventDTO;
import com.lucaticket.event.model.dto.EventRequest;
import com.lucaticket.event.model.dto.EventResponse;
import com.lucaticket.event.repository.EventRepository;
import com.lucaticket.event.service.EventService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Implementación de la interfaz {@code EventService}. Proporciona lógica de
 * negocio para gestionar eventos, incluyendo operaciones CRUD y búsquedas.
 * 
 * @version 1.0
 * @since 2024-12-11
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class EventServiceImpl implements EventService {

	private final EventRepository eventRepository;

	/**
	 * Guarda un nuevo evento en la base de datos.
	 * 
	 * @param eventoRequest Objeto {@code EventRequest} con los datos del evento.
	 * @return Un objeto {@code EventResponse} con los datos del evento creado.
	 */
	@Override
	public ResponseEntity<EventCreateDelete> saveEvent(EventRequest eventoRequest) {
		log.info("Guardando nuevo evento: " + eventoRequest.toString());
		comprobarPrecio(eventoRequest.getMinPrice(), eventoRequest.getMaxPrice());
		Event event = eventRepository.save(eventoRequest.toEntity());
		log.info("Evento guardando con éxito");
		return ResponseEntity.ok(new EventCreateDelete("Created successfully", event.getId(), event.getName()));
	}

	/**
	 * Obtiene una lista de todos los eventos disponibles.
	 * 
	 * @return Una lista de objetos {@code EventResponse}. Retorna un estado HTTP
	 *         204 si no hay eventos.
	 */
	@Override
	public ResponseEntity<List<EventResponse>> getEvents() {
		log.info("Devolviendo el listado de eventos");
		List<EventResponse> events = eventRepository.findAll().stream().map(Event::toDto).toList();
		log.info("Devolviendo listado con " + events.size() + " elementos");
		return events.isEmpty() ? new ResponseEntity<>(HttpStatus.NO_CONTENT) : ResponseEntity.ok(events);
	}

	/**
	 * Obtiene información detallada de un evento por su ID.
	 * 
	 * @param eventId Identificador único del evento.
	 * @return Un objeto {@code DetailedEventResponse} con los datos completos del
	 *         evento.
	 * @throws InvalidDataException Si el evento no existe.
	 */
	@Override
	public ResponseEntity<DetailedEventResponse> getDetailedInfoEvent(Long eventId) {
		Event event = eventRepository.findById(eventId)
				.orElseThrow(() -> new InvalidDataException("El evento con ID: " + eventId + " no existe."));

		log.info("Devolviendo evento detallado con id: " + eventId);
		return ResponseEntity.ok(event.toDetailedDto());
	}

	/**
	 * Busca eventos por su nombre.
	 * 
	 * @param name Nombre del evento.
	 * @return Una lista de objetos {@code EventResponse}. Retorna un estado HTTP
	 *         204 si no hay eventos con ese nombre.
	 */
	@Override
	public ResponseEntity<List<EventResponse>> findByName(String name) {
		log.info("Buscando eventos con nombre: " + name);
		List<EventResponse> eventResponses = eventRepository.findByName(name).stream().map(Event::toDto).toList();
		if (eventResponses.isEmpty()) {
			log.info("No se han encontrado eventos con el nombre: " + name);
			return ResponseEntity.noContent().build();
		}
		log.info("Devolviendo listado con " + eventResponses.size() + " elementos");
		return ResponseEntity.ok(eventResponses);
	}

	/**
	 * Obtiene información detallada de un evento por su nombre.
	 * 
	 * @param eventName Nombre del evento.
	 * @return Un objeto {@code DetailedEventResponse}.
	 * @throws InvalidDataException Si no existe un evento con ese nombre.
	 */
	@Override
	public ResponseEntity<DetailedEventResponse> getDetailedInfoEventByName(String eventName) {
		log.info("Buscando evento detallado con nombre: " + eventName);
		Event event = eventRepository.findByName(eventName).stream().findFirst()
				.orElseThrow(() -> new InvalidDataException("El evento con nombre '" + eventName + "' no existe."));
		log.info("Devolviendo el evento: " + event.toString());
		return ResponseEntity.ok(event.toDetailedDto());
	}

	/**
	 * Actualiza un evento existente con los datos proporcionados.
	 * 
	 * @param event Objeto {@code EventDTO} con los nuevos datos del evento.
	 * @return Un objeto {@code DetailedEventResponse} con los datos del evento
	 *         actualizado.
	 * @throws InvalidDataException Si el evento no existe.
	 */
	@Override
	public ResponseEntity<Map<String, Object>> updateEvent(EventDTO eventDTO) {
		// Buscar evento existente o lanzar excepción si no se encuentra
		log.info("Actualizando el evento: " + eventDTO.toString());
		Event eventOld = eventRepository.findById(eventDTO.getId()).orElseThrow(
				() -> new InvalidDataException("El evento con el ID: " + eventDTO.getId() + " no existe."));

		// Crear un mapa para almacenar los campos actualizados
		Map<String, Object> updatedFields = new HashMap<>();

		// Actualizar campos y registrar cambios solo si son válidos y diferentes
		Optional.ofNullable(eventDTO.getName()).filter(name -> !name.isBlank() && !name.equals(eventOld.getName()))
				.ifPresent(name -> {
					eventOld.setName(name);
					updatedFields.put("name", name);
				});

		Optional.ofNullable(eventDTO.getDescription())
				.filter(description -> !description.isBlank() && !description.equals(eventOld.getDescription()))
				.ifPresent(description -> {
					eventOld.setDescription(description);
					updatedFields.put("description", description);
				});

		Optional.ofNullable(eventDTO.getEventDate()).filter(date -> !date.equals(eventOld.getEventDate()))
				.ifPresent(date -> {
					eventOld.setEventDate(date);
					updatedFields.put("eventDate", date);
				});

		Optional.ofNullable(eventDTO.getMinPrice())
				.filter(minPrice -> minPrice > 0 && minPrice != eventOld.getMinPrice()).ifPresent(minPrice -> {
					eventOld.setMinPrice(minPrice);
					updatedFields.put("minPrice", minPrice);
				});

		Optional.ofNullable(eventDTO.getMaxPrice())
				.filter(maxPrice -> maxPrice > 0 && maxPrice != eventOld.getMaxPrice()).ifPresent(maxPrice -> {
					eventOld.setMaxPrice(maxPrice);
					updatedFields.put("maxPrice", maxPrice);
				});

		Optional.ofNullable(eventDTO.getLocation())
				.filter(location -> !location.isBlank() && !location.equals(eventOld.getLocation()))
				.ifPresent(location -> {
					eventOld.setLocation(location);
					updatedFields.put("location", location);
				});

		Optional.ofNullable(eventDTO.getGenre()).filter(genre -> genre != eventOld.getGenre()).ifPresent(genre -> {
			eventOld.setGenre(genre);
			updatedFields.put("genre", genre);
		});

		// Validación de precios
		comprobarPrecio(eventOld.getMinPrice(), eventOld.getMaxPrice());

		// Guardar cambios
		eventRepository.save(eventOld);

		// Añadir mensaje de éxito
		if (updatedFields.isEmpty()) {
			log.info("No hay nuevos cambios");
			updatedFields.put("message", "No ha habido cambios en el evento");
		} else
			log.info("Evento actualizado con éxito");
		updatedFields.put("message", "El evento ha sido modificado correctamente");

		// Devolver solo los campos actualizados
		return ResponseEntity.ok(updatedFields);
	}

	/**
	 * Elimina un evento existente por su ID.
	 * 
	 * @param id Identificador único del evento.
	 * @return Un objeto {@code EventResponse} con los datos del evento eliminado.
	 * @throws InvalidDataException Si el evento no existe.
	 */
	@Override
	public ResponseEntity<EventCreateDelete> deleteEvent(long id) {
		Event event = eventRepository.findById(id)
				.orElseThrow(() -> new InvalidDataException("No se ha encontrado el evento con el ID: " + id));
		eventRepository.delete(event);
		return ResponseEntity.ok(new EventCreateDelete("Event deleted successfully", id, event.getName()));
	}

	/**
	 * Comprueba que el precio mínimo no supere el precio máximo.
	 * 
	 * @param min Precio mínimo.
	 * @param max Precio máximo.
	 * @throws InvalidDataException Si el precio mínimo es mayor que el máximo.
	 */
	private void comprobarPrecio(double min, double max) {
		if (min > max) {
			throw new InvalidDataException("El precio mínimo no puede superar al máximo.");
		}
	}
}
