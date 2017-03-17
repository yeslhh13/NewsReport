package com.example.android.newsreport;

import android.text.TextUtils;

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

import static android.R.attr.y;

/**
 * Created by gowon on 2017-02-26.
 */

public final class QueryUtils {
    private QueryUtils() {
    }

    /**
     * @param requestedURL used to parse and make a list of {@link News} objects
     * @return a list of {@link News} objects that has been built up from parsing a JSON response.
     */
    private static ArrayList<News> extractNews(String requestedURL) {
        // If the JSON string is empty or null, then return early.
        if (TextUtils.isEmpty(requestedURL))
            return null;

        ArrayList<News> news = new ArrayList<>();

        try {
            JSONObject jsonObject = new JSONObject(requestedURL);
            JSONArray results = jsonObject.getJSONObject("response").getJSONArray("results");

            for (int i = 0; i < results.length(); i++) {
                JSONObject object = results.getJSONObject(i);

                String title = object.getString("webTitle");
                String publishDate = object.getString("webPublicationDate");
                String sectionName = object.getString("sectionName");
                String webURL = object.getString("webUrl");

                news.add(new News(title, publishDate, sectionName, webURL));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return news;
    }

    /**
     * @param requestedURL used to query and make a {@link List} of {@link News} objects
     * @return {@link List} of {@link News} objects
     */
    public static List<News> fetchNewsData(String requestedURL) {
        URL url = createURL(requestedURL);
        String jsonResponse = null;

        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return extractNews(jsonResponse);
    }

    /**
     * @param requestedURL used to make a new {@link URL} object
     * @return new {@link URL} object from the given string URL.
     */
    private static URL createURL(String requestedURL) {
        URL url = null;
        try {
            url = new URL(requestedURL);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    /**
     * @param url used to make an HTTP request
     * @return {@link String} as the response to the given {@link URL}
     * @throws IOException
     */
    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        if (url == null)
            return jsonResponse;

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // If the request was successful (response code 200),
            // then read the input stream and parse the response.
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null)
                urlConnection.disconnect();

            try {
                if (inputStream != null)
                    inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return jsonResponse;
    }

    /**
     * @param inputStream to convert to new {@link String} object
     * @return {@link String} converted from a {@link InputStream} which contains whole JSON response from the server.
     * @throws IOException
     */
    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();

        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String line = bufferedReader.readLine();
            while (line != null) {
                output.append(line);
                line = bufferedReader.readLine();
            }
        }
        return output.toString();
    }
}
