package com.lucaticket.event.model;

import java.time.LocalDateTime;

import com.lucaticket.event.model.dto.EventResponse;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//@Alberto

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Event {

	private long id;
	private String name;
	private String description;
	private LocalDateTime eventDate;
	private double minPrice;
	private double maxPrice;
	private String location;
	private String venueName;
	private Genre genre;

	public EventResponse toDto() {
		EventResponse eventResponse = new EventResponse();

		eventResponse.setName(this.name);
		eventResponse.setDescription(this.description);
		eventResponse.setEventDate(this.eventDate);
		eventResponse.setMinPrice(this.minPrice);
		eventResponse.setMaxPrice(this.maxPrice);
		eventResponse.setLocation(this.location);
		eventResponse.setGenre(this.genre);

		return eventResponse;
	}
}
