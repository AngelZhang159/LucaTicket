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
import com.lucaticket.event.model.dto.EventCreateDelete;
import com.lucaticket.event.model.dto.EventRequest;
import com.lucaticket.event.model.dto.EventResponse;
import com.lucaticket.event.repository.EventRepository;
import com.lucaticket.event.service.EventService;

/**
 * Implementación de la interfaz {@code EventService}.
 * Proporciona lógica de negocio para gestionar eventos, incluyendo
 * operaciones CRUD y búsquedas.
 * 
 * @version 1.0
 * @since 2024-12-11
 */
@Service
public class EventServiceImpl implements EventService {

    @Autowired
    private EventRepository eventRepository;

    /**
     * Guarda un nuevo evento en la base de datos.
     * 
     * @param eventoRequest Objeto {@code EventRequest} con los datos del evento.
     * @return Un objeto {@code EventResponse} con los datos del evento creado.
     */
    @Override
    public ResponseEntity<EventCreateDelete> saveEvent(EventRequest eventoRequest) {
        comprobarPrecio(eventoRequest.getMinPrice(), eventoRequest.getMaxPrice());
        Event event = eventRepository.save(eventoRequest.toEntity());
        return ResponseEntity.ok(new EventCreateDelete(
                "Created successfully",
                event.getId(),
                event.getName()));
    }

    /**
     * Obtiene una lista de todos los eventos disponibles.
     * 
     * @return Una lista de objetos {@code EventResponse}.
     *         Retorna un estado HTTP 204 si no hay eventos.
     */
    @Override
    public ResponseEntity<List<EventResponse>> getEvents() {
        List<EventResponse> events = eventRepository.findAll()
                .stream()
                .map(Event::toDto)
                .toList();

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

        return ResponseEntity.ok(event.toDetailedDto());
    }

    /**
     * Busca eventos por su nombre.
     * 
     * @param name Nombre del evento.
     * @return Una lista de objetos {@code EventResponse}.
     *         Retorna un estado HTTP 204 si no hay eventos con ese nombre.
     */
    @Override
    public ResponseEntity<List<EventResponse>> findByName(String name) {
        List<EventResponse> eventResponses = eventRepository.findByName(name)
                .stream()
                .map(Event::toDto)
                .toList();

        return eventResponses.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(eventResponses);
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
        Event event = eventRepository.findByName(eventName)
                .stream()
                .findFirst()
                .orElseThrow(() -> new InvalidDataException("El evento con nombre '" + eventName + "' no existe."));

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
    public ResponseEntity<DetailedEventResponse> updateEvent(EventDTO event) {
        Event eventOld = eventRepository.findById(event.getId())
                .orElseThrow(() -> new InvalidDataException("El evento con el ID: " + event.getId() + " no existe."));

        Event eventNew = new Event(eventOld.getId(), eventOld.getName(), eventOld.getDescription(),
                eventOld.getEventDate(), eventOld.getMinPrice(), eventOld.getMaxPrice(), eventOld.getLocation(),
                eventOld.getVenueName(), eventOld.getGenre());

        if (event.getName() != null && !event.getName().isBlank()) {
            eventNew.setName(event.getName());
        }
        if (event.getDescription() != null && !event.getDescription().isBlank()) {
            eventNew.setDescription(event.getDescription());
        }
        if (event.getEventDate() != null) {
            eventNew.setEventDate(event.getEventDate());
        }
        if (event.getMinPrice() > 0) {
            eventNew.setMinPrice(event.getMinPrice());
        }
        if (event.getMaxPrice() > 0) {
            eventNew.setMaxPrice(event.getMaxPrice());
        }
        if (event.getLocation() != null && !event.getLocation().isBlank()) {
            eventNew.setLocation(event.getLocation());
        }
        if (event.getGenre() != null) {
            eventNew.setGenre(event.getGenre());
        }

        comprobarPrecio(eventNew.getMinPrice(), eventNew.getMaxPrice());
        return ResponseEntity.ok(eventRepository.save(eventNew).toDetailedDto());
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
        return ResponseEntity.ok(new EventCreateDelete(
                "Event deleted successfully",
                id,
                event.getName()));
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
