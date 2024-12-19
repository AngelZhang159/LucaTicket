package com.lucaticket.event.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.lucaticket.event.model.dto.DetailedEventResponse;
import com.lucaticket.event.model.dto.EventResponse;
import com.lucaticket.event.model.enums.Genre;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entidad que representa un evento en el sistema LucaTicket. Contiene
 * información básica y detallada del evento, como su nombre, descripción,
 * ubicación, precios y género.
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
	@GeneratedValue(strategy = GenerationType.IDENTITY)
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
	@Enumerated(EnumType.STRING)
	private Genre genre;

	/**
	 * Convierte la entidad {@code Event} en un objeto de transferencia de datos
	 * detallado. Este método se utiliza para enviar información completa del evento
	 * al cliente.
	 * 
	 * @author Angel
	 * @return Un objeto {@code DetailedEventResponse} que contiene los datos
	 *         detallados del evento.
	 * @since 2024-12-11
	 */
	public DetailedEventResponse toDetailedDto() {
		DetailedEventResponse eventResponse = new DetailedEventResponse();

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

		eventResponse.setName(this.name);
		eventResponse.setDescription(this.description);
		eventResponse.setEventDate(this.eventDate.format(formatter));
		eventResponse.setMinPrice(this.minPrice);
		eventResponse.setMaxPrice(this.maxPrice);
		eventResponse.setLocation(this.location);
		eventResponse.setVenueName(this.venueName);
		eventResponse.setGenre(this.genre);

		return eventResponse;
	}

	/**
	 * Convierte la entidad {@code Event} en un objeto de transferencia de datos
	 * resumido. Este método se utiliza para enviar información básica del evento al
	 * cliente.
	 * 
	 * @author Raul
	 * @return Un objeto {@code EventResponse} que contiene los datos básicos del
	 *         evento.
	 * @since 2024-12-11
	 */

	public EventResponse toDto() {
		EventResponse eventResponse = new EventResponse();

		eventResponse.setId(this.id);
		eventResponse.setName(this.name);
		eventResponse.setMinPrice(this.minPrice);
		eventResponse.setMaxPrice(this.maxPrice);
		eventResponse.setLocation(this.location);

		return eventResponse;
	}
}
