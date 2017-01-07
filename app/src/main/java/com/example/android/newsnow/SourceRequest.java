package com.example.android.newsnow;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by fc__j on 04/01/2017.
 */
public final class SourceRequest {

    /** Tag for the log messages */
    private static final String LOG_TAG = SourceRequest.class.getSimpleName();

    private SourceRequest() {}

    public static List<NewsSource> fetchSources(String requestUrl) {
        // Create URL object
        URL url = createUrl(requestUrl);

        // Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }

        // Extract relevant fields from the JSON response and create a list of Sources
        List<NewsSource> sources = extractDataFromJson(jsonResponse);

        // Return the list of Sources
        return sources;
    }

    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Problem building the URL ", e);
        }
        return url;
    }

    private static List<NewsSource> extractDataFromJson(String jsonResponse) {
        // If the JSON string is empty or null, then return early.
        if (TextUtils.isEmpty(jsonResponse)) {
            return null;
        }

        // Create an empty ArrayList that we can start adding earthquakes to
        List<NewsSource> sources = new ArrayList<>();

        // Try to parse the JSON response string. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        try {

            // Create a JSONObject from the JSON response string
            JSONObject baseJsonResponse = new JSONObject(jsonResponse);

            // Extract the JSONArray associated with the key called "sources",
            JSONArray sourcesArray = baseJsonResponse.getJSONArray("sources");

            // For each source in the sourcesArray, create an Source object
            for (int i = 0; i < sourcesArray.length(); i++) {

                // Get a single source at position i within the list of sources
                JSONObject currentSource = sourcesArray.getJSONObject(i);

                // Extract all information used for sources
                String id = currentSource.getString("id");
                String name = currentSource.getString("name");
                String description = currentSource.getString("description");
                String url = currentSource.getString("url");
                String country = currentSource.getString("country");
                String language = currentSource.getString("language");
                JSONObject logos = currentSource.getJSONObject("urlsToLogos");
                JSONArray sortBys = currentSource.getJSONArray("sortBysAvailable");

                // Create a new Source object with all extracted data
                NewsSource source = new NewsSource(id, name, description, url, country, language);
                source.setSLogo(logos.getString("small"));
                source.setMLogo(logos.getString("medium"));
                source.setLLogo(logos.getString("large"));
                // Add the new Source to the list of sources.
                sources.add(source);
            }

        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("SourceRequest", "Problem parsing the sources JSON results", e);
        }

        // Return the list of sources
        return sources;
    }

    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        // If the URL is null, then return early.
        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // If the request was successful (response code 200),
            // then read the input stream and parse the response.
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the Source JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }
}
