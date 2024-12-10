package com.lucaticket.event.model;

import java.time.LocalDateTime;

import com.lucaticket.event.model.dto.EventResponse;
import com.lucaticket.event.model.enums.Genre;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//@Alberto

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Event {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
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
		eventResponse.setVenueName(this.venueName);
		eventResponse.setGenre(this.genre);

		return eventResponse;
	}
}
