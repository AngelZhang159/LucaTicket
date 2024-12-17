package com.lucaticket.ticketservice.model.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO que representa la información detallada de un evento.
 * Esta clase se utiliza para transferir datos entre microservicios,
 * especialmente cuando se recupera información del servicio de eventos.
 *
 * @author Yuji
 * @version 2.0
 * @since 17-12-2022
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DetailedEventResponse {

    /** Nombre del evento. */
    private String name;

    /** Descripción del evento. */
    private String description;

    /** Fecha y hora en la que se celebrará el evento. */
    private LocalDateTime eventDate;

    /** Precio mínimo de entrada para el evento. */
    private double minPrice;

    /** Precio máximo de entrada para el evento. */
    private double maxPrice;

    /** Ubicación del evento, como una ciudad o dirección específica. */
    private String location;

    /** Nombre del recinto o lugar donde se celebrará el evento. */
    private String venueName;

    /** Género musical o tipo de evento (ROCK, JAZZ, POP, etc.). */
    private String genre;
}
