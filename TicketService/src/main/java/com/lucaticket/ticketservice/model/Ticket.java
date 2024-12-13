package com.lucaticket.ticketservice.model;



import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author Alberto de la Blanca
 * Representa un Ticket asociado a un evento en el sistema.
 * Cada ticket tiene un identificador único, un correo electrónico asociado y 
 * un identificador del evento correspondiente.
 * 
 */

@Data
@AllArgsConstructor
@Entity(name= "tickets")
public class Ticket {
	
	/**
     * Identificador único del ticket.
     * Generado automáticamente..
     */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long IdTicket;
	
	 /**
     * Correo electrónico del usuario asociado al ticket.
     */
	private String email;
	
	/**
     * Identificador del evento al que pertenece este ticket.
     */
	private Long idEvent;

}
