package com.example.mgarey2.familymap.model;

/**
 * Created by Marshall on 12/2/2016.
 * Information about an event in a person's life.
 * All data in JSON is required - there are no optional fields.
 */
public class Event {

    private String eventId;
    private String personId;
    private double latitude;
    private double longitutde;
    private String country;
    private String city;
    private String description;
    private String year;
    private String descendant;

    public Event(String eventId, String personId, double latitude, double longitutde, String country, String city,
                 String description, String year, String descendant) {
        this.eventId = eventId;
        this.personId = personId;
        this.latitude = latitude;
        this.longitutde = longitutde;
        this.country = country;
        this.city = city;
        this.description = description;
        this.year = year;
        this.descendant = descendant;
    }

    @Override
    public String toString() {
        return "Event{" +
                "eventId='" + eventId + '\'' +
                ", personId='" + personId + '\'' +
                ", latitude=" + latitude +
                ", longitutde=" + longitutde +
                ", country='" + country + '\'' +
                ", city='" + city + '\'' +
                ", description='" + description + '\'' +
                ", year='" + year + '\'' +
                ", descendant='" + descendant + '\'' +
                '}';
    }
}
