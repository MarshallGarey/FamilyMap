package com.example.mgarey2.familymap.model;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * Created by Marshall on 12/3/2016.
 * Storage for the model classes.
 */
public class LocalData {

    private final static String LOG_TAG = "LocalData";
    protected static ArrayList<Person> people;
    protected static HashSet<Event> events;

    public static void addPerson(Person person) {
        if (people == null) {
            people = new ArrayList<>();
        }
        people.add(person);
    }

    public static void removePerson(Person person) {
        if (people == null) {
            return;
        }
        people.remove(person);
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

    public static ArrayList<Person> getPeople() {
        return people;
    }

    public static HashSet<Event> getEvents() {
        return events;
    }

    public static Person findPerson(String personId) {
        for (Person person : people) {
            if (person.getPersonId().equals(personId)) {
                return person;
            }
        }
        Log.w(LOG_TAG, "Unable to find person with id " + personId);
        return null;
    }

    public static Event findEvent(String eventId) {
        for (Event event : events) {
            if (event.getEventId().equals(eventId)) {
                return event;
            }
        }
        Log.w(LOG_TAG, "Unable to find event with id " + eventId);
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
}
