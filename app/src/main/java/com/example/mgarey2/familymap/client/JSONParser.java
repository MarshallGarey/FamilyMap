package com.example.mgarey2.familymap.client;

import android.util.Log;

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
        Log.w(LOG_TAG, response);

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
}
