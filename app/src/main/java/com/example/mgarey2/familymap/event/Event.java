package com.example.mgarey2.familymap.event;

import android.util.Log;

import com.example.mgarey2.familymap.person.Person;

import java.io.Serializable;
import java.util.Comparator;
import java.util.HashSet;
import java.util.TreeSet;

/**
 * Created by Marshall on 12/2/2016.
 * Information about an event in a person's life.
 * All data in JSON is required - there are no optional fields.
 */
public class Event implements Serializable, Comparable<Event> {

    private final int LESS_THAN = -1;
    private final int GREATER_THAN = 1;
    private final String LOG_TAG = "Event";
    private final static String LOG_TAG_STATIC = "Event";
    protected static TreeSet<Event> events;

    private String EVENT_TYPES[] = {
            "birth", "death", "baptism", "census", "christening"
    };

    private float EVENT_HUES[] = {
            30, 100, 170, 240, 310
    };
    private float DEFAULT_HUE = 75;

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
        } else {
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
            events = new TreeSet<>();
        }
        events.add(event);
    }

    public static void removeEvent(Event event) {
        if (events == null) {
            return;
        }
        events.remove(event);
    }

    public static TreeSet<Event> getEvents() {
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

    public static TreeSet<Event> getPersonEvents(String personId) {
        TreeSet<Event> result = new TreeSet<>();
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
//                Log.d(LOG_TAG, "Event: " + getEventSummary() + " , hue: " + EVENT_HUES[i]);
                return EVENT_HUES[i];
            }
        }
        return DEFAULT_HUE;
    }

    /**
     * 1. Birth events come first.
     * 2. Events with years are sorted primarily by year and secondarily by description.
     * 3. Death events come last.
     * <p/>
     * This operation is case insensitive.
     *
     * @param e2 event 2
     * @return LESS_THAN if event 1 (this) should come before event 2,
     * GREATER_THAN if event 1 should come after event 2,
     * 0 if they are equal.
     */
    @Override
    public int compareTo(Event e2) {
//        if (e2 == null) {
//            return LESS_THAN;
//        }

        // 1. Birth event comes first.
        String e1Description = this.getDescription().toLowerCase();
        String e2Description = e2.getDescription().toLowerCase();
        if (e1Description.toLowerCase().equals("birth")) {
            // Event 1 is a birth.
            if (e2Description.toLowerCase().equals("birth")) {
                // Both are births; sort by year.
                return sortByYear(e2);
            }
            // Event 2 is not a birth. Event 1 comes before.
            return LESS_THAN;
        }

        // 3. Neither event is a birth. If one is a death, it comes last.
        if (e1Description.toLowerCase().equals("death")) {
            // Event 1 is a death.
            if (e2Description.toLowerCase().equals("death")) {
                // Event 2 is a death.
                return sortByYear(e2);
            }
            // Event 2 is not a death. Event 1 comes after.
            return GREATER_THAN;
        }

        // Sort by year.
        return sortByYear(e2);
    }

    /**
     * @param e2 event 2
     * @return LESS_THAN if this happens before e2; GREATER_THAN otherwise.
     */
    private int sortByYear(Event e2) {
        if (this.getYearInt() < e2.getYearInt()) {
            return LESS_THAN;
        } else {
            return GREATER_THAN;
        }
    }

    private int getYearInt() {
        return Integer.parseInt(year);
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Event)) {
            return false;
        }

        // Compare each field.
        Event e = (Event) other;
        if (e.eventId.equals(this.eventId)) {
            if (e.personId.equals(this.personId)) {
                if (e.latitude == this.latitude) {
                    if (e.longitutde == this.longitutde) {
                        if (e.country.toLowerCase().equals(this.country.toLowerCase())) {
                            if (e.city.toLowerCase().equals(this.city.toLowerCase())) {
                                if (e.description.toLowerCase().equals(this.description.toLowerCase())) {
                                    if (e.getYearInt() == this.getYearInt()) {
                                        if (e.descendant.equals(this.descendant)) {
                                            return true;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        return false;
    }

    @Override
    public int hashCode() {
        return getYearInt();
    }
}
