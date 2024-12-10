package com.lucaticket.event.model.dto;

import java.time.LocalDateTime;

import com.lucaticket.event.model.Event;
import com.lucaticket.event.model.enums.Genre;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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

	@NotNull
	@NotEmpty
	@Size(max = 20)
	private String name;

	@NotNull
	@NotEmpty
	@Size(max = 200)
	private String description;

	private LocalDateTime eventDate;

	@NotNull
	@Positive
	private double minPrice;

	@NotNull
	@Positive
	private double maxPrice;

	@NotNull
	@NotEmpty
	@Size(max = 20)
	private String location;

	@NotNull
	@NotEmpty
	@Size(max = 20)
	private String venueName;

	@Enumerated(EnumType.STRING)
	private Genre genre;

	public Event toEntity() {
		Event event = new Event();

		event.setName(this.name);
		event.setDescription(this.description);
		event.setEventDate(this.eventDate);
		event.setMinPrice(this.minPrice);
		event.setMaxPrice(this.maxPrice);
		event.setLocation(this.location);
		event.setGenre(this.genre);

		return event;
	}
}
