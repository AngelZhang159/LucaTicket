package com.lucaticket.event.model.dto;

import java.time.LocalDateTime;

import com.lucaticket.event.model.enums.Genre;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object (DTO) que representa los datos completos de un evento.
 * Este objeto se utiliza para transferir información entre capas del sistema
 * o hacia el cliente.
 * 
 * @author Angel
 * @version 1.0
 * @since 2024-12-11
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventDTO {

    /** Identificador único del evento. */
    private long id;

    /** Nombre del evento. */
    private String name;

    /** Descripción detallada del evento. */
    private String description;

    /** Fecha y hora en la que se llevará a cabo el evento. */
    private LocalDateTime eventDate;

    /** Precio mínimo de entrada para el evento. */
    private double minPrice;

    /** Precio máximo de entrada para el evento. */
    private double maxPrice;

    /** Localización del evento, como ciudad o dirección específica. */
    private String location;

    /** Nombre del recinto donde se llevará a cabo el evento. */
    private String venueName;

    /** Género musical o tipo de evento, representado como un enumerado. */
    private Genre genre;

}
