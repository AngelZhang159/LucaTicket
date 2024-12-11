package com.lucaticket.event.model;

import java.time.LocalDateTime;

import com.lucaticket.event.model.dto.EventResponse;
import com.lucaticket.event.model.enums.Genre;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entidad que representa un evento en el sistema LucaTicket.
 * Esta clase almacena la información básica de un evento, incluyendo
 * su nombre, descripción, ubicación, precios y género musical.
 * 
 * @version 1.0
 * @author Alberto
 * @since 2024-12-11
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "events")
public class Event {

    /** Identificador único del evento. */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    /** Nombre del evento. */
    private String name;

    /** Descripción detallada del evento. */
    private String description;

    /** Fecha y hora en la que se llevará a cabo el evento. */
    private LocalDateTime eventDate;

    /** Precio mínimo de entrada al evento. */
    private double minPrice;

    /** Precio máximo de entrada al evento. */
    private double maxPrice;

    /** Localización del evento, como ciudad o dirección. */
    private String location;

    /** Nombre del recinto donde se llevará a cabo el evento. */
    private String venueName;

    /** Género musical o tipo de evento, representado como un enum. */
    @Enumerated(EnumType.STRING)
    private Genre genre;

    /**
     * Convierte la entidad {@code Event} en un objeto de transferencia de datos (DTO).
     * Este método se utiliza para enviar información del evento al cliente sin exponer
     * directamente la entidad.
     * 
	 * @author Angel
     * @return Un objeto {@code EventResponse} que contiene los datos del evento.
     * @since 2024-12-11
     */
    public EventResponse toDto() {
        EventResponse eventResponse = new EventResponse();

        eventResponse.setName(this.name);
        eventResponse.setDescription(this.description);
        eventResponse.setEventDate(this.eventDate);
        eventResponse.setMinPrice(this.minPrice);
        eventResponse.setMaxPrice(this.maxPrice);
        eventResponse.setLocation(this.location);
        eventResponse.setVenueName(this.venueName);
        eventResponse.setGenre(this.genre);

        return eventResponse;
    }
}
