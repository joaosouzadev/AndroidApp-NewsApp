package com.example.android.newsapp;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JOAO on 25-Apr-18.
 */

class QueryUtils {
    /**
     * Tag for the log messages
     */
    private static final String LOG_TAG = QueryUtils.class.getSimpleName();

    // Keys used for the JSON response
    private static final String response = "response";

    private static final String results = "results";

    private static final String section = "sectionName";

    private static final String date = "webPublicationDate";

    private static final String title = "webTitle";

    private static final String url = "webUrl";

    private static final String tags = "tags";

    private static final String author = "webTitle";

    private static final String image = "image";

    /**
     * Create a private constructor because no one should ever create a {@link QueryUtils} object.
     * This class is only meant to hold static variables and methods, which can be accessed
     * directly from the class name QueryUtils (and an object instance of QueryUtils is not needed).
     */
    private QueryUtils() {
    }

    /**
     * Return a list of {@link News} objects that has been built up from
     * parsing the given JSON response.
     */
    private static List<News> extractFeatureFromJson(String newsJSON) {
        // If the JSON string is empty or null, then return early.
        if (TextUtils.isEmpty(newsJSON)) {
            return null;
        }

        // Create an empty ArrayList that we can start adding earthquakes to
        List<News> newsList = new ArrayList<>();

        // Try to parse the JSON response string.
        // In case of any problem with the way the JSON is formatted,
        // a JSONException exception object will be thrown.
        // To avoid app is crashed error message is printed into logs.

        try {
            // Create a JSONObject from the JSON response string
            JSONObject baseJsonNewsResponse = new JSONObject(newsJSON);

            // Extract the JSONObject associated with the key called "response",
            JSONObject responseJsonNews = baseJsonNewsResponse.getJSONObject(response);

            // Extract the JSONArray associated with the key called "results"
            JSONArray newsArray = responseJsonNews.getJSONArray(results);

            // For each news in the JsonNewsArray create an {@link News} object
            for (int i = 0; i < newsArray.length(); i++) {

                // Get a single news article at position i within the list of news
                JSONObject currentNews = newsArray.getJSONObject(i);

                // Extract the section name for the key called "sectionName"
                String newsSection = currentNews.getString(section);

                // Check if newsDate exist and than extract the date for the key called "webPublicationDate"
                String newsDate = "Date not available";

                if (currentNews.has(date)) {
                    newsDate = currentNews.getString(date);
                }

                // Extract the article name for the key called "webTitle"
                String newsTitle = currentNews.getString(title);

                // Extract the value for the key called "webUrl"
                String newsUrl = currentNews.getString(url);

                // Extract the image
                String newsImage = currentNews.getString(image);

                //Extract the JSONArray associated with the key called "tags",
                JSONArray currentNewsAuthorArray = currentNews.getJSONArray(tags);

                String newsAuthor = "Author not available";

                //Check if "tags" array contains data
                int tagsLenght = currentNewsAuthorArray.length();


                if (tagsLenght == 1) {
                    // Create a JSONObject for author
                    JSONObject currentNewsAuthor = currentNewsAuthorArray.getJSONObject(0);

                    String newsAuthor1 = currentNewsAuthor.getString(author);

                    newsAuthor = newsAuthor1;

                }

                // Create a new News object with the title, category, author, date, url ,
                // from the JSON response.
                News newNews = new News(newsTitle, newsSection, newsAuthor, newsDate, newsUrl, newsImage);

                // Add the new {@link News} to the list of News.
                newsList.add(newNews);
            }

        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("QueryUtils", "JSON results parsing problem.");
        }

        // Return the list of earthquakes
        return newsList;
    }
}