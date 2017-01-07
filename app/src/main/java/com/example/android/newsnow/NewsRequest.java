package com.example.android.newsnow;

import android.content.Intent;
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

public final class NewsRequest {

    private NewsRequest() {}

    public static List<News> fetchNews(String requestUrl) {
        // Create URL object
        URL url = createUrl(requestUrl);

        // Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e("NewsRequest", "Problem making the HTTP request.", e);
        }

        // Extract relevant fields from the JSON response and create a list of Sources
        List<News> news = extractDataFromJson(jsonResponse);

        // Return the list of Sources
        return news;
    }

    private static List<News> extractDataFromJson(String jsonResponse) {
        // If the JSON string is empty or null, then return early.
        if (TextUtils.isEmpty(jsonResponse)) {
            return null;
        }

        // Create an empty ArrayList that we can start adding earthquakes to
        List<News> newsList = new ArrayList<>();

        try {

            // Create a JSONObject from the JSON response string
            JSONObject baseJsonResponse = new JSONObject(jsonResponse);
            String source = baseJsonResponse.getString("source");
            // Extract the JSONArray associated with the key called "sources",
            JSONArray newsArray = baseJsonResponse.getJSONArray("articles");

            // For each source in the newsArray, create an Source object
            for (int i = 0; i < newsArray.length(); i++) {

                // Get a single source at position i within the list of sources
                JSONObject currentNews = newsArray.getJSONObject(i);

                // Extract all information used for sources
                String author = currentNews.getString("author");
                String title = currentNews.getString("title");
                String description = currentNews.getString("description");
                String url = currentNews.getString("url");
                String urlToImage = currentNews.getString("urlToImage");
                String date = currentNews.getString("publishedAt");

                // Create a new Source object with all extracted data
                News news = new News(author, title, description, url, urlToImage, date, source);
                // Add the new Source to the list of sources.
                newsList.add(news);
            }

        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("NewsRequest", "Problem parsing the news JSON results", e);
        }

        // Return the list of sources
        return newsList;
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
                Log.e("NewsRequest", "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e("NewsRequest", "Problem retrieving the News JSON results.", e);
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

    private static String readFromStream(InputStream inputStream) throws IOException{
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

    private static URL createUrl(String requestUrl) {
        URL url = null;
        try {
            url = new URL(requestUrl);
        } catch (MalformedURLException e) {
            Log.e("NewsRequest", "Problem building the URL ", e);
        }
        return url;
    }

}
