package com.lucaticket.event.model;

import java.time.LocalDateTime;

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

}
