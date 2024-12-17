package com.lucaticket.ticketservice.model.dto;

import java.time.LocalDateTime;

import com.lucaticket.event.model.enums.Genre;

/**
 * @author Alberto de la Blanca
 * DTO para representar los detalles completos de un ticket.
 * Proporciona información sobre el evento, su ubicación y los precios de los boletos.
 */
public class DetailedTicketResponse {
	
    private long id;
    
    private String name;

    private String description;

    private LocalDateTime eventDate;

    private double minPrice;

    private double maxPrice;

    private String location;

    private String venueName;

    private Genre genre;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public LocalDateTime getEventDate() {
		return eventDate;
	}

	public void setEventDate(LocalDateTime eventDate) {
		this.eventDate = eventDate;
	}

	public double getMinPrice() {
		return minPrice;
	}

	public void setMinPrice(double minPrice) {
		this.minPrice = minPrice;
	}

	public double getMaxPrice() {
		return maxPrice;
	}

	public void setMaxPrice(double maxPrice) {
		this.maxPrice = maxPrice;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getVenueName() {
		return venueName;
	}

	public void setVenueName(String venueName) {
		this.venueName = venueName;
	}

	public Genre getGenre() {
		return genre;
	}

	public void setGenre(Genre genre) {
		this.genre = genre;
	}

}
