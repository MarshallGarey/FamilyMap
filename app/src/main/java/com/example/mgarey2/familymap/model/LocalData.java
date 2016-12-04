package com.example.mgarey2.familymap.model;

import java.util.ArrayList;

/**
 * Created by Marshall on 12/3/2016.
 * Storage for the model classes.
 */
public class LocalData {

    protected static ArrayList<Person> people;
    protected static ArrayList<Event> events;

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
            events = new ArrayList<>();
        }
        events.add(event);
    }

    public static void removeEvent(Event event) {
        if (events == null) {
            return;
        }
        events.remove(event);
    }
}
