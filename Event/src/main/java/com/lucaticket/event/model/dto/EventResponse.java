package com.lucaticket.event.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventResponse {

	private String name;

	private double minPrice;
	private double maxPrice;

	private String location;


}
