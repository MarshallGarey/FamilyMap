package com.example.mgarey2.familymap.model;

import android.util.Log;

import com.example.mgarey2.familymap.main.LoginFragment;

/**
 * Created by Marshall on 12/2/2016.
 * Information about a person.
 */
public class Person {

    private final String LOG_TAG = "Person";

    // Required data:
    private String descendant;
    private String personId;
    private String firstName;
    private String lastName;
    private String gender;
    private String spouse;

    // Optional data:
    private String father;
    private String mother;

    public Person(String descendant, String personID, String firstName, String lastName, String gender, String
            spouse, String father, String mother) {
        this.descendant = descendant;
        this.personId = personID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.spouse = spouse;
        this.father = father;
        this.mother = mother;
//        Log.d(LOG_TAG, toString());
    }

    public String toString() {
        return ("Descendant: " + descendant +
        "\nPersonID: " + personId +
        "\nName: " + firstName + " " + lastName +
        "\nGender: " + gender +
        "\nSpouse: " + spouse +
        "\nFather: " + father + " , Mother: " + mother + "\n");
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

    public String getSpouse() {
        return spouse;
    }

    public String getFather() {
        return father;
    }

    public String getMother() {
        return mother;
    }
}
