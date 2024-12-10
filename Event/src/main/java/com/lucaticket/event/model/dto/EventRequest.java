package com.lucaticket.event.model.dto;

import java.time.LocalDateTime;

import com.lucaticket.event.model.Event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventRequest {

	private String name;

	private String description;

	private LocalDateTime eventDate;

	private double minPrice;

	private double maxPrice;

	private String location;

	private String venueName;

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
