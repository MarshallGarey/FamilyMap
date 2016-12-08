package com.example.mgarey2.familymap.client;

import android.util.Log;

import com.example.mgarey2.familymap.event.Event;
import com.example.mgarey2.familymap.person.Person;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Marshall on 12/2/2016.
 * Parses responses to http requests.
 */
public class JSONParser {

    private static final String LOG_TAG = "JSON Parser";

    /**
     * @param response Response from the server.
     * @return Authorization data if the login is successful (correct credentials), null otherwise
     */
    public static AuthorizationData parseLoginResponse(String response) {
        Log.d(LOG_TAG, response);

        try {
            JSONObject jsonObject = new JSONObject(response);
            String authorizationToken = jsonObject.getString("Authorization");
            String username = jsonObject.getString("userName");
            String personId = jsonObject.getString("personId");
            return new AuthorizationData(authorizationToken, personId, username);
        } catch (JSONException e) {
            Log.e(LOG_TAG, e.toString(), e);
        }
        return null;
    }

    public static boolean loadPeople(String peopleString) {
        try {
            JSONObject root = new JSONObject(peopleString);
            JSONArray list = root.getJSONArray("data");

            for (int i = 0; i < list.length(); i++) {
                JSONObject element = list.getJSONObject(i);
                String father = null, mother = null, spouse = null;
                if (element.has("father")) {
                    father = element.getString("father");
                }
                if (element.has("mother")) {
                    mother = element.getString("mother");
                }
                if (element.has("spouse")) {
                    spouse = element.getString("spouse");
                }
                Person p = new Person(
                        element.getString("descendant"),
                        element.getString("personID"),
                        element.getString("firstName"),
                        element.getString("lastName"),
                        element.getString("gender"),
                        spouse,
                        father,
                        mother
                );
                Person.addPerson(p);
            }
            return true;

        } catch (Exception e) {
            Log.e(LOG_TAG, e.toString(), e);
        }
        return false;
    }

    public static boolean loadEvents(String eventsString) {
        try {
            JSONObject root = new JSONObject(eventsString);
            JSONArray list = root.getJSONArray("data");

            for (int i = 0; i < list.length(); ++i) {
                JSONObject element = list.getJSONObject(i);
                Event.addEvent(new Event(
                        element.getString("eventID"),
                        element.getString("personID"),
                        element.getDouble("latitude"),
                        element.getDouble("longitude"),
                        element.getString("country"),
                        element.getString("city"),
                        element.getString("description"),
                        element.getString("year"),
                        element.getString("descendant")
                ));
            }
            return true;

        } catch (Exception e) {
            Log.e(LOG_TAG, e.toString(), e);
        }
        return false;
    }
}
