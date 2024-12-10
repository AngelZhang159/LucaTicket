package com.lucaticket.event.model;


//@Alberto

public class Event {
	
	private long id;
	private String name;
	private String description;
	private Date evantDate;
	private LocalTime eventTime;
	private double minPrice;
	private double maxPrice;
	private String location;
	private String venueName
	private String genere;
	
	//Empty constructor
	public Event() {
		
	}
	
	//Constructor
    public Event(long id, String name, String description, Date eventDate, LocalTime eventTime, 
            double minPrice, double maxPrice, String location, String venueName, String genre) {
	   this.id = id;
	   this.name = name;
	   this.description = description;
	   this.eventDate = eventDate;
	   this.eventTime = eventTime;
	   this.minPrice = minPrice;
	   this.maxPrice = maxPrice;
	   this.location = location;
	   this.venueName = venueName;
	   this.genre = genre;
    }
    
    

}
