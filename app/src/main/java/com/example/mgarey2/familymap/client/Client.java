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

    private static URL url = null;
    private static String urlString = null;
    private static AuthorizationData authorizationData = null;

    public static void openUrl(String destUrl) {
        try {
            urlString = destUrl;
//            url = new URL(destUrl);
            // TODO: don't hard code. This is just for testing.
//            url = new URL("http://bernoulli.app.byu.edu:8080");
        } catch (Exception e) {
            Log.e("HttpClient", "Error connecting to server.\n" + e.getMessage(), e);
        }
    }

    public static boolean login(String username, String password) {
        try {
            // Connect to /user/login
            // TODO: don't hard code
            URL url = new URL("http://bernoulli.app.byu.edu:8080/user/login");
//            URL url = new URL(urlString + "/user/login");
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

            // Create a request:
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);

            OutputStreamWriter writer = new OutputStreamWriter(
                    httpURLConnection.getOutputStream());

            // TODO: I'll need to add authorization for future interactions
            // The authorization string will be stored in an AuthorizationData object
            // httpURLConnection.addRequestProperty("authorization","r4up06h9-y397-0");

            // Set the request body:
            // TODO: don't hard code. This is just for testing.
            String requestBody = String.format("{username:\"%s\",password:\"%s\"", username, password);
//            writer.write(requestBody);
            writer.write("{username:\"mgarey2\",password:\"familymap\"}");
            writer.close();

            // Get the response
            if (httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                Log.w("HttpClient", "We are connected. Now we can try to log in.\n");

                // Get response body input stream
                InputStream responseBody = httpURLConnection.getInputStream();

                // Read response body bytes
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];
                int length = 0;
                while ((length = responseBody.read(buffer)) != -1) {
                    baos.write(buffer, 0, length);
                }
                responseBody.close();

                // Convert response body bytes to a string, parse it, and return.
                authorizationData = JSONParser.parseLoginResponse(baos.toString());
                return (authorizationData != null);
            }
            else {
                Log.w("HttpClient", "We are not connected.\n");
            }

//            if (httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
//                // Get response body input stream
//                InputStream responseBody = httpURLConnection.getInputStream();
//
//                // Read response body bytes
//                ByteArrayOutputStream baos = new ByteArrayOutputStream();
//                byte[] buffer = new byte[1024];
//                int length = 0;
//                while ((length = responseBody.read(buffer)) != -1) {
//                    baos.write(buffer, 0, length);
//                }
//
//                // Convert response body bytes to a string, parse it, and return.
//                return JSONParser.parseLoginResponse(baos.toString());
//            }

        } catch (Exception e) {
            Log.e("HttpClient", "Error logging in.\n" + e.getMessage(), e);
        }
        return false;
    }
}
