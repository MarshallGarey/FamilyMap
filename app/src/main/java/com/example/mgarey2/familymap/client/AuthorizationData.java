package com.example.mgarey2.familymap.client;

/**
 * Created by Marshall on 12/2/2016.
 * Returned data from server login.
 */
public class AuthorizationData {
    protected String authroizationToken;
    protected String personId;
    protected String username;

    public AuthorizationData(String authroizationToken, String personId, String username) {
        this.authroizationToken = authroizationToken;
        this.personId = personId;
        this.username = username;
    }

    public String toString() {
        return "Authorization token: " + authroizationToken +
                "\nPerson ID: " + personId +
                "\nUsername: " + username + "\n";
    }
}
