package com.lucaticket.event.model.dto;

import java.time.LocalDateTime;

import com.lucaticket.event.model.Event;
import com.lucaticket.event.model.enums.Genre;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventRequest {

	@NotBlank(message = "El nombre no puede estar vacío")
	@Size(max = 20)
	private String name;

	@NotBlank(message = "La descripción no puede estar vacía")
	@Size(max = 200)
	private String description;

	private LocalDateTime eventDate;

	@NotBlank(message = "El precio mínimo no puede estar vacío")
	@Positive(message = "El precio mínimo no puede ser negativo")
	private double minPrice;

	@NotBlank(message = "El precio máximo no puede estar vacío")
	@Positive(message = "El precio máximo no puede ser negativo")
	private double maxPrice;

	@NotBlank(message = "La ubicación no puede estar vacía")
	@Size(max = 20, message = "La ubicación no puede tener más de 20 caracteres")
	private String location;

	@NotBlank(message = "El nombre del lugar no puede estar vacío")
	@Size(max = 20, message = "El nombre del lugar no puede tener más de 20 caracteres")
	private String venueName;

	@NotBlank(message = "El género no puede estar vacío")
	private Genre genre;

	public Event toEntity() {
		Event event = new Event();

		event.setName(this.name);
		event.setDescription(this.description);
		event.setEventDate(this.eventDate);
		event.setMinPrice(this.minPrice);
		event.setMaxPrice(this.maxPrice);
		event.setVenueName(this.venueName);
		event.setLocation(this.location);
		event.setGenre(this.genre);

		return event;
	}
}
