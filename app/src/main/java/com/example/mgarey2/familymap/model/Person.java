package com.example.mgarey2.familymap.model;

import android.util.Log;

import java.io.Serializable;

/**
 * Created by Marshall on 12/2/2016.
 * Information about a person.
 */
public class Person implements Serializable {

    private final String LOG_TAG = "Person";

    // Required data:
    private String descendant;
    private String personId;
    private String firstName;
    private String lastName;
    private String gender;
    private String spouseId;

    // Optional data:
    private String fatherId;
    private String motherId;

    public Person(String descendant, String personID, String firstName, String lastName, String gender, String
            spouse, String father, String mother) {
        this.descendant = descendant;
        this.personId = personID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.spouseId = spouse;
        this.fatherId = father;
        this.motherId = mother;
//        Log.d(LOG_TAG, toString());
    }

    public String toString() {
        return ("Descendant: " + descendant +
                "\nPersonID: " + personId +
                "\nName: " + firstName + " " + lastName +
                "\nGender: " + gender +
                "\nSpouse: " + spouseId +
                "\nFather: " + fatherId + " , Mother: " + motherId + "\n");
    }

    public String getDescendant() {
        return descendant;
    }

    public String getPersonId() {
        return personId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getName() {
        return firstName + " " + lastName;
    }

    public String getGender() {
        return gender;
    }

    public Person getSpouse() {
        if (spouseId == null) {
            return null;
        }
        Person spouse = null;
        try {
            spouse = LocalData.findPerson(spouseId);
        } catch (Exception e) {
            Log.e(LOG_TAG, e.toString(), e);
        }
        return spouse;
    }

    public Person getFather() {
        if (fatherId == null) {
            return null;
        }
        Person father = null;
        try {
            father = LocalData.findPerson(fatherId);
        } catch (Exception e) {
            Log.e(LOG_TAG, e.toString(), e);
        }
        return father;
    }

    public Person getMother() {
        if (motherId == null) {
            return null;
        }
        Person mother = null;
        try {
            mother = LocalData.findPerson(motherId);
        } catch (Exception e) {
            Log.e(LOG_TAG, e.toString(), e);
        }
        return mother;
    }

    // Find Spouse's name by searching events
    public String getSpouseName() {
        Person p = getSpouse();
        return p != null ? p.getName() : null;
    }

    // Find Father's name by searching events
    public String getFatherName() {
        Person p = getFather();
        return p != null ? p.getName() : null;
    }

    // Find Mother's name by searching events
    public String getMotherName() {
        Person p = getMother();
        return p != null ? p.getName() : null;
    }

    public String getFatherId() {
        return fatherId;
    }

    public String getMotherId() {
        return motherId;
    }

    public String getSpouseId() {
        return spouseId;
    }
}
