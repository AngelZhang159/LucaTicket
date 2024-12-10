package com.lucaticket.event.model.dto;

import java.time.LocalDateTime;

import com.lucaticket.event.model.Event;

import lombok.Data;

@Data
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
