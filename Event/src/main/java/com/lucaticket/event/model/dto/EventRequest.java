package com.lucaticket.event.model.dto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.lucaticket.event.model.Event;
import com.lucaticket.event.model.enums.Genre;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object (DTO) que representa una solicitud para crear o
 * actualizar un evento. Este objeto se utiliza para recibir información desde
 * el cliente hacia el backend.
 * 
 * Incluye validaciones para garantizar la integridad de los datos enviados.
 * 
 * @version 1.0
 * @since 2024-12-11
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventRequest {

	/**
	 * Nombre del evento. Debe contener un máximo de 20 caracteres y no puede estar
	 * vacío.
	 */
	@NotBlank(message = "El nombre no puede estar vacío")
	@Size(max = 20, message = "El nombre no puede tener más de 20 caracteres")
	private String name;

	/**
	 * Descripción del evento. Debe contener un máximo de 200 caracteres y no puede
	 * estar vacía.
	 */
	@NotBlank(message = "La descripción no puede estar vacía")
	@Size(max = 200, message = "La descripción no puede tener más de 200 caracteres")
	private String description;

	/**
	 * Fecha y hora en la que se llevará a cabo el evento.
	 */
	@NotBlank(message = "La fecha del evento no debe de ser nulo")
	private String eventDate;

	/**
	 * Precio mínimo del evento. Debe ser un valor positivo y no puede ser nulo.
	 */
	@NotNull(message = "El precio mínimo no puede estar vacío")
	@Positive(message = "El precio mínimo no puede ser negativo")
	private Double minPrice;

	/**
	 * Precio máximo del evento. Debe ser un valor positivo y no puede ser nulo.
	 */
	@NotNull(message = "El precio máximo no puede estar vacío")
	@Positive(message = "El precio máximo no puede ser negativo")
	private Double maxPrice;

	/**
	 * Ubicación del evento. Debe contener un máximo de 20 caracteres y no puede
	 * estar vacía.
	 */
	@NotBlank(message = "La ubicación no puede estar vacía")
	@Size(max = 20, message = "La ubicación no puede tener más de 20 caracteres")
	private String location;

	/**
	 * Nombre del lugar donde se llevará a cabo el evento. Debe contener un máximo
	 * de 20 caracteres y no puede estar vacío.
	 */
	@NotBlank(message = "El nombre del lugar no puede estar vacío")
	@Size(max = 20, message = "El nombre del lugar no puede tener más de 20 caracteres")
	private String venueName;

	/**
	 * Género o tipo de música asociado al evento. Debe ser un valor válido del
	 * enumerado {@code Genre}.
	 */
	@NotNull(message = "El género no puede estar vacío")
	private Genre genre;

	/**
	 * Convierte el objeto {@code EventRequest} en una entidad {@code Event}. Este
	 * método se utiliza para mapear los datos recibidos en un formato compatible
	 * con la capa de persistencia.
	 * 
	 * @return Una entidad {@code Event} con los datos mapeados desde este DTO.
	 * @since 2024-12-11
	 */
	public Event toEntity() {
		Event event = new Event();

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

		event.setName(this.name);
		event.setDescription(this.description);
		event.setEventDate(LocalDateTime.parse(this.eventDate, formatter));
		event.setMinPrice(this.minPrice);
		event.setMaxPrice(this.maxPrice);
		event.setVenueName(this.venueName);
		event.setLocation(this.location);
		event.setGenre(this.genre);

		return event;
	}
}
