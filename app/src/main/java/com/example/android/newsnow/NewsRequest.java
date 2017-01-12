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
 * Auxiliar class responsible to make the request and set all properties needed to make the connection to the api
 */
final class NewsRequest {

    private NewsRequest() {}

    static List<News> fetchNews(String requestUrl, String sourceImage) {
        // Create URL object
        URL url = createUrl(requestUrl);

        // Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e("NewsRequest", "Problem making the HTTP request.", e);
        }

        // Return the list of Sources
        return extractDataFromJson(jsonResponse, sourceImage);
    }

    /**
     * Gets all data from the request and fill the object array with all important info fetched
     * @param jsonResponse
     * @param sourceImage
     * @return newsList
     */
    private static List<News> extractDataFromJson(String jsonResponse, String sourceImage) {
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
                String title = currentNews.getString("title");
                String date = currentNews.getString("publishedAt");
                String url = currentNews.getString("url");

                // Create a new Source object with all extracted data
                News news = new News(title, date, source, sourceImage, url);
                // Add the new Source to the list of sources.
                newsList.add(news);
            }

        } catch (JSONException e) {
            Log.e("NewsRequest", "Problem parsing the news JSON results", e);
        }

        // Return the list of sources
        return newsList;
    }

    /**
     * Sets connection properties and gets response in a formatted jsonresponse
     * @param url
     * @return
     * @throws IOException
     */
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

    /**
     * Formats request response using first InputStreamReader and then BufferedReader in order to get all response formatted
     * @param inputStream
     * @return
     * @throws IOException
     */
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

    /**
     * Gets string url passed from AsyncTask and converts in a URL object
     * @param requestUrl
     * @return
     */
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
