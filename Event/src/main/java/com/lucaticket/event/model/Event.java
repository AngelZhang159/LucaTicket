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
    
    // Getters & setters
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

    public Date getEventDate() {
        return eventDate;
    }

    public void setEventDate(Date eventDate) {
        this.eventDate = eventDate;
    }

    public LocalTime getEventTime() {
        return eventTime;
    }

    public void setEventTime(LocalTime eventTime) {
        this.eventTime = eventTime;
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

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }
    
    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", eventDate=" + eventDate +
                ", eventTime=" + eventTime +
                ", minPrice=" + minPrice +
                ", maxPrice=" + maxPrice +
                ", location='" + location + '\'' +
                ", venueName='" + venueName + '\'' +
                ", genre='" + genre + '\'' +
                '}';
    }
}

}
