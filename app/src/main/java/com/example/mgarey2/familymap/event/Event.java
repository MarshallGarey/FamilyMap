package com.example.mgarey2.familymap.event;

import android.util.Log;

import com.example.mgarey2.familymap.R;
import com.example.mgarey2.familymap.person.Person;

import java.io.Serializable;
import java.util.HashSet;

/**
 * Created by Marshall on 12/2/2016.
 * Information about an event in a person's life.
 * All data in JSON is required - there are no optional fields.
 */
public class Event implements Serializable {

    private final String LOG_TAG = "Event";
    private final static String LOG_TAG_STATIC = "Event";
    protected static HashSet<Event> events;

    private String EVENT_TYPES[] = {
            "birth", "death", "baptism", "census", "christening"
    };

    private float EVENT_HUES[] = {
            30, 100, 170, 240, 310
    };

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
        Person person = Person.findPerson(personId);
        if (person == null) {
            Log.w(LOG_TAG, "Unable to find person in getEventSummary");
        }
        else {
            name = person.getName();
        }
        return "\t\t" + name + "\n\t\t" +
                description + ": " + city + ", " + country +
                " (" + year + ")";
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

    public static void addEvent(Event event) {
        if (events == null) {
            events = new HashSet<>();
        }
        events.add(event);
    }

    public static void removeEvent(Event event) {
        if (events == null) {
            return;
        }
        events.remove(event);
    }

    public static HashSet<Event> getEvents() {
        return events;
    }

    public static Event findEvent(String eventId) {
        for (Event event : events) {
            if (event.getEventId().equals(eventId)) {
                return event;
            }
        }
        Log.w(LOG_TAG_STATIC, "Unable to find event with id " + eventId);
        return null;
    }

    public static HashSet<Event> getPersonEvents(String personId) {
        HashSet<Event> result = new HashSet<>();
        for (Event event : events) {
            if (event.getPersonId().equals(personId)) {
                result.add(event);
            }
        }
        return result;
    }

    public float getMarkerHue() {
        for (int i = 0; i < EVENT_TYPES.length; ++i) {
            if (EVENT_TYPES[i].equals(description)) {
                Log.d(LOG_TAG, "Event: " + getEventSummary() + " , hue: " + EVENT_HUES[i]);
                return EVENT_HUES[i];
            }
        }
        return 0;
    }

}
