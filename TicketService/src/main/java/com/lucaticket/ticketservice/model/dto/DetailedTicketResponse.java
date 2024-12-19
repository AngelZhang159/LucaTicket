package com.lucaticket.ticketservice.model.dto;

import com.lucaticket.event.model.dto.DetailedEventResponse;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Alberto de la Blanca
 * DTO para representar los detalles completos de un ticket.
 * Proporciona información sobre el evento, su ubicación y los precios de los boletos.
 */

 
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DetailedTicketResponse {

    private Long ticketId;
    private String email;
    private DetailedEventResponse eventDetails;
    private double price;

}
