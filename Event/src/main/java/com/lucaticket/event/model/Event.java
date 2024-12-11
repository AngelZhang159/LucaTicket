package com.lucaticket.event.model;

import java.time.LocalDateTime;

import com.lucaticket.event.model.dto.DetailedEventResponse;
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

//@Alberto

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "events")
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
	@Enumerated(EnumType.STRING)
	private Genre genre;

	// @AngelZhang159
	public DetailedEventResponse toDetailedDto() {
		DetailedEventResponse eventResponse = new DetailedEventResponse();

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
	
	public EventResponse toDto() {
		EventResponse eventResponse = new EventResponse();
		
		eventResponse.setName(this.name);
		eventResponse.setMinPrice(this.minPrice);
		eventResponse.setMaxPrice(this.maxPrice);
		eventResponse.setLocation(this.location);
		
		return eventResponse;
	}
}
