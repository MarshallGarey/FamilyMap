package com.example.mgarey2.familymap.client;

import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Marshall on 12/2/2016.
 * Create http requests and handle responses.
 */
public class Client {

    private static String urlString = null;
    private static AuthorizationData authorizationData = null;
    private static final String LOG_TAG = "HttpClient";

    public static void openUrl(String destUrl) {
        try {
            urlString = destUrl;
        } catch (Exception e) {
            Log.e(LOG_TAG, "Error connecting to server.\n" + e.getMessage(), e);
        }
    }

    public static boolean login(String username, String password) {
        try {
            // Connect to /user/login
            URL url = new URL(urlString + "/user/login");
            Log.d(LOG_TAG, url.toString());
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

            // Create a request:
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);

            OutputStreamWriter writer = new OutputStreamWriter(
                    httpURLConnection.getOutputStream());

            // Set the request body:
            String requestBody = String.format("{username:\"%s\",password:\"%s\"}", username, password);
            Log.d(LOG_TAG, requestBody);
            writer.write(requestBody);
            writer.close();

            // Get the response
            String response = getResponse(httpURLConnection);
            if (response == null) {
                return false;
            }

            // Convert response body bytes to a string and parse it.
            authorizationData = JSONParser.parseLoginResponse(response);
            if (authorizationData != null) {
                Log.w(LOG_TAG, authorizationData.toString());
                // Login successful. Now try to sync.
                return sync();
            }

        } catch (Exception e) {
            Log.e(LOG_TAG, "Error logging in.\n" + e.getMessage(), e);
        }
        return false;
    }

    public static boolean sync() {
        return getEvents() && getPeople();
    }

    public static boolean getEvents() {
        boolean result = false;
        try {
            URL url = new URL(urlString + "/event");
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

            // Create a request:
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);

            // Set authorization.
            httpURLConnection.addRequestProperty("authorization",authorizationData.authroizationToken);

            OutputStreamWriter writer = new OutputStreamWriter(
                    httpURLConnection.getOutputStream());

            // The request body is empty.
            writer.write("");
            writer.close();

            // Get the response
            String response = getResponse(httpURLConnection);
            if (response != null) {
                result = JSONParser.loadEvents(response);
            }

        } catch (Exception e) {
            Log.e(LOG_TAG, e.toString(), e);
        }

        return result;
    }

    public static boolean getPeople() {
        boolean result = false;
        try {
            URL url = new URL(urlString + "/person");
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

            // Create a request:
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);

            // Set authorization.
            httpURLConnection.addRequestProperty("authorization",authorizationData.authroizationToken);

            OutputStreamWriter writer = new OutputStreamWriter(
                    httpURLConnection.getOutputStream());

            // The request body is empty.
            writer.write("");
            writer.close();

            // Get the response
            String response = getResponse(httpURLConnection);
            if (response != null) {
                result = JSONParser.loadPeople(response);
            }

        } catch (Exception e) {
            Log.e(LOG_TAG, e.toString(), e);
        }

        return result;
    }

    private static String getResponse(HttpURLConnection httpURLConnection) {
        try {
            int responseCode = httpURLConnection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {

                // Get response body input stream
                InputStream responseBody = httpURLConnection.getInputStream();

                // Read response body bytes
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                byte[] buffer = new byte[10024];
                int length;
                while ((length = responseBody.read(buffer)) != -1) {
                    baos.write(buffer, 0, length);
                }
                responseBody.close();

                // Return the response.
                return baos.toString();

            } else {
                Log.d(LOG_TAG, "Failed getting response; code = " + responseCode + "\n");
            }
        } catch (Exception e) {
            Log.e(LOG_TAG, e.toString(), e);
        }
        return null;
    }

}
