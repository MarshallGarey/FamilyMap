package com.example.mgarey2.familymap.model;

import android.util.Log;

import java.security.interfaces.DSAKey;

/**
 * Created by Marshall on 12/2/2016.
 * Information about an event in a person's life.
 * All data in JSON is required - there are no optional fields.
 */
public class Event {

    private final String LOG_TAG = "Event";

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
        return description + ": " +
                "\npersonId=" + personId +
                "\neventId=" + eventId +
                "\nlatitude=" + latitude +
                ", longitutde=" + longitutde +
                "\ncountry=" + country +
                ", city=" + city +
                "\nyear=" + year +
                ", descendant='" + descendant;
    }

    public String getEventSummary() {
        String name = null;
        Person person = LocalData.findPerson(personId);
        if (person == null) {
            Log.w(LOG_TAG, "Unable to find person in getEventSummary");
        }
        else {
            name = person.getName();
        }
        String result = "\t\t" + name + "\n\t\t" +
                description + ": " + city + ", " + country +
                "(" + year + ")";
        Log.d(LOG_TAG, result);
        return result;
    }

    public String getEventId() {
        return eventId;
    }

    public String getPersonId() {
        return personId;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitutde() {
        return longitutde;
    }

    public String getCountry() {
        return country;
    }

    public String getCity() {
        return city;
    }

    public String getDescription() {
        return description;
    }

    public String getYear() {
        return year;
    }

    public String getDescendant() {
        return descendant;
    }
}
