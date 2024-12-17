package com.lucaticket.event.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object (DTO) que representa una respuesta resumida de un evento.
 * Este objeto se utiliza para enviar información básica sobre un evento al cliente,
 * como su nombre, precios y ubicación.
 * @author Angel
 * @version 1.0
 * @since 2024-12-11
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventResponse {

    /** Identificador único del evento. */
    private long id;

    /** Nombre del evento. */
    private String name;

    /** Precio mínimo de entrada al evento. */
    private double minPrice;

    /** Precio máximo de entrada al evento. */
    private double maxPrice;

    /** Ubicación del evento, como ciudad o dirección específica. */
    private String location;

}
