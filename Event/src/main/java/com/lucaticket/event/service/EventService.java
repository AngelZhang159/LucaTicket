package com.lucaticket.event.service;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.lucaticket.event.model.dto.DetailedEventResponse;
import com.lucaticket.event.model.dto.EventDTO;
import com.lucaticket.event.model.dto.EventCreateDelete;
import com.lucaticket.event.model.dto.EventRequest;
import com.lucaticket.event.model.dto.EventResponse;

/**
 * @version 7.0
 * @since 11-12-2024
 * @author Raul
 */
public interface EventService {

	/**
	 * @author Raul
	 * @param eventoRequest
	 * @return un DTO de respuesta de "evento" con datos de la creacion
	 */
	ResponseEntity<EventCreateDelete> saveEvent(EventRequest eventoRequest);
	
	/**
	 * @author Raul
	 * @return una lista con todos los eventos
	 */
	ResponseEntity<List<EventResponse>> getEvents();
	
	/**
	 * @author Alberto de la Blanca
     * Obtiene información detallada de un evento.
     * 
     * @param eventId El identificador del evento.
     * @return Un DTO con la información detallada del evento.
     */
	ResponseEntity<DetailedEventResponse> getDetailedInfoEvent (Long eventId);
	
	/**
	 * @author Angel
	 * @param name
	 * @return Lista con todos los eventos que coincidan con el nombre
	 */
	ResponseEntity<List<EventResponse>> findByName(String name);
	
	/**
	 * @author Alberto de la Blanca
     * Obtiene información detallada de un evento buscada por nombre.
     * 
     * @param eventName
     * @return Un DTO con la información detallada del evento.
     */
	ResponseEntity<DetailedEventResponse> getDetailedInfoEventByName(String eventName);
	
	/**
	 * @author Angel
	 * @param eventRequest
	 * @return El evento actualizado
	 */
	ResponseEntity<Map<String, Object>> updateEvent(EventDTO event);

	
	/**
	 * @author Angel
	 * @param id
	 * @return El evento borrado
	 */
	ResponseEntity<EventCreateDelete> deleteEvent(long id);
	
}
