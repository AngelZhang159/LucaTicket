package com.lucaticket.ticketservice.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lucaticket.ticketservice.model.dto.DetailedTicketResponse;
import com.lucaticket.ticketservice.model.dto.TicketRequest;
import com.lucaticket.ticketservice.model.dto.TicketResponse;
import com.lucaticket.ticketservice.service.TicketService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@RequestMapping("/ticket")
@Slf4j
public class TicketController {

	private final TicketService ticketService;

	/**
	 * @author Raul
	 * @param un objeto con los parametros necesarios para guardar un ticket
	 * @return una entidad de respuesta con un codigo http y el objeto ticket que se
	 *         ha guardado
	 */
	@PostMapping
	ResponseEntity<TicketResponse> save(@RequestBody @Valid TicketRequest ticketRequest ) {
		log.info("Controller: Guardando nuevo ticket: " + ticketRequest.toString());
		return ticketService.save(ticketRequest);
	}

	/**
	 * @author Raul
	 * @return una lista con todos los tickets registrados
	 */
	@GetMapping
	public
	ResponseEntity<List<TicketResponse>> listTickets() {
		log.info("Controller: Listando todos los tickets:");
		return ticketService.listTickets();
	}
	
	/**
	 * @author Alberto de la Blanca
	 * obtiene una lista de tickets asociados a un correo.
	 * 
	 * @param mail Correo electronico del usuario.
	 * @return una lista de objetos DetailedTicketResponse
	 */
	
	@GetMapping("/mail/{mail}")
	public ResponseEntity<List<DetailedTicketResponse>> getTickets(@PathVariable String mail){
		log.info("Controller: Obteniendo nuevo ticket para el correo: " + mail);
		return ticketService.listTicketByMail(mail);
	}

}
