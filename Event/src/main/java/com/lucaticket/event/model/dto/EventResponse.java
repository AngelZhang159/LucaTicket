package com.lucaticket.event.model.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class EventResponse {

    private String name;

    private String description;

    private LocalDateTime eventDate;
    
    private double minPrice;

    private double maxPrice;

    private String location;

    private String venueName;

    private Genre genre;
}
