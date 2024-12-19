package com.lucaticket.ticketservice.model.dto;

import com.lucaticket.ticketservice.model.Ticket;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TicketRequest {

	/**
	 * @author Angel
	 * Correo electrónico del usuario asociado al ticket.
	 */
	@Email(message = "El email no es válido")
	@NotBlank(message = "El email no debe estar vacío")
	private String email;

	/**
	 * Identificador del evento al que pertenece este ticket.
	 */
	@Positive(message = "El id del evento debe de ser positivo")
	@NotNull(message = "El id del evento no debe de ser nulo")
	private Long idEvent;

	@Positive(message = "El precio debe ser positivo")
	@NotNull(message = "El precio no puede estar vacio")
	private Double price;
	
	/**
	 * Precio del ticket
	 */
	@Positive(message = "El precio debe ser positivo")
	@NotNull(message = "El precio no puede estar vacio")
	private Double price;

	/**
	 * Mapper request a entidad
	 * 
	 * @author Angel
	 * @return la entidad
	 */
	public Ticket toEntity() {
		Ticket ticket = new Ticket();
		ticket.setEmail(this.email);
		ticket.setIdEvent(this.idEvent);
		ticket.setPrice(this.price);
		return ticket;
	}

}
