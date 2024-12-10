package com.lucaticket.event.model.dto;

import java.time.LocalDateTime;

import com.lucaticket.event.model.Event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventResponse {

	private String name;

	private String description;

	private LocalDateTime eventDate;

	private double minPrice;

	private double maxPrice;

	private String location;

	private String venueName;

	private Genre genre;

	public static EventResponse toDto(Event event) {
		EventResponse eventResponse = new EventResponse();

		eventResponse.setName(event.getName());
		eventResponse.setDescription(event.getDescription());
		eventResponse.setEventDate(event.getEventDate());
		eventResponse.setMinPrice(event.getMinPrice());
		eventResponse.setMaxPrice(event.getMaxPrice());
		eventResponse.setLocation(event.getLocation());
		eventResponse.setGenre(event.getGenre);

		return eventResponse;
	}
}
