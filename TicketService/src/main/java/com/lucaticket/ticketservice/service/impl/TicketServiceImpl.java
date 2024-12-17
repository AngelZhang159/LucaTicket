package com.lucaticket.ticketservice.service.impl;

import java.util.List;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.lucaticket.event.model.dto.DetailedEventResponse;
import com.lucaticket.feignclients.EventFeignClient;
import com.lucaticket.ticketservice.error.FallbackErrorResponse;
import com.lucaticket.ticketservice.error.NoTicketsFoundException;
import com.lucaticket.ticketservice.error.TicketAlreadyExistsException;
import com.lucaticket.ticketservice.model.Ticket;
import com.lucaticket.ticketservice.model.dto.DetailedTicketResponse;
import com.lucaticket.ticketservice.model.dto.TicketRequest;
import com.lucaticket.ticketservice.model.dto.TicketResponse;
import com.lucaticket.ticketservice.repository.TicketRepository;
import com.lucaticket.ticketservice.service.TicketService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@EnableFeignClients
@Slf4j
public class TicketServiceImpl implements TicketService {

	private final TicketRepository ticketRepository;

	private final EventFeignClient eventFeignClient;

	/**
	 * @author Angel
	 * @param ticketRequest
	 * @return ticketRespon
	 * @throws TicketAlreadyExistsException
	 */
	public ResponseEntity<TicketResponse> save(TicketRequest ticketRequest) {
		// update v.1.1
		// Yuji
		log.info("Service: Guardando nuevo ticket: " + ticketRequest.toString());
		return new ResponseEntity<>(ticketRepository.save(ticketRequest.toEntity()).toDTO(), HttpStatus.CREATED);
	}

	/**
	 * @author Angel
	 * @return List<Ticket>
	 * @throws NoTicketsFoundException
	 */
	public ResponseEntity<List<TicketResponse>> listTickets() {
		// REcupera todos los tickets de la bdd
		// Yuji
		// update v.1.1
		log.info("Service: Listando todos los tickets");
		List<Ticket> tickets = ticketRepository.findAll();
		if (tickets.isEmpty()) {
			throw new NoTicketsFoundException("No hay tickets registrados en la base de datos");
		}

		log.info("Service: Devolviendo tickets, tamaño: " + tickets.size());
		return ResponseEntity.ok(tickets.stream().map(Ticket::toDTO).toList());
	}

	/**
	 * @author Yuji
	 * @return List<Ticket>
	 * @throws NoTicketsFoundException
	 */
	@CircuitBreaker(name = "event", fallbackMethod = "fallbackGetEventDetails")
	public ResponseEntity<List<DetailedTicketResponse>> listTicketsByEmail(String email) {
		log.info("Service: Obteniendo tickets de: " + email);

		// Recuperar los tickets asociados al email
		List<Ticket> tickets = ticketRepository.findByEmail(email);
		if (tickets.isEmpty())
			throw new NoTicketsFoundException("No hay tickets registrados para el usuario: " + email);

		// Usando Feign client para obtener los detalles del evento
		List<DetailedTicketResponse> detailedTickets = tickets.stream().map(ticket -> {
			DetailedEventResponse eventDetails = eventFeignClient.getDetail(ticket.getIdEvent());
			return new DetailedTicketResponse(
					ticket.getId(), // ID del ticket
					ticket.getEmail(), // Correo electrónico
					eventDetails // Información del evento
			);
		}).toList();

		log.info("Service: Devolviendo tickets con detalles, tamaño: " + detailedTickets.size());
		return ResponseEntity.ok(detailedTickets);
	}

	/**
	 * @author Yuji
	 *         Fallback para el método getEventDetails
	 * 
	 * @param email     Correo electronico del usuario.
	 * @param throwable Excepción que se lanzó en el método getEventDetails
	 * @return una lista de objetos DetailedTicketResponse
	 * 
	 * Sirve para cuando el servicio de eventos falla y no se puede obtener
	 * la información del evento
	 */
	public ResponseEntity<FallbackErrorResponse> fallbackGetEventDetails(String email, Throwable throwable) {
		log.error("Service: Fallback activado para tickets del email: " + email, throwable);

		DetailedTicketResponse fallbackResponse = new DetailedTicketResponse();
		fallbackResponse.setEmail(email);
		fallbackResponse.setEventDetails(new DetailedEventResponse(
				"Evento no disponible",
				"No se pudo recuperar la información del evento. Inténtalo más tarde",
				null, 0.0, 0.0, "Ubicación no disponible", "Lugar no disponible", null));

		FallbackErrorResponse errorResponse = new FallbackErrorResponse(
				HttpStatus.SERVICE_UNAVAILABLE.value(),
				"Service Unavailable",
				"El servicio event-service no está disponible. Se devolvió un resultado de fallback",
				List.of(fallbackResponse));

		return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(errorResponse);
	}

}
