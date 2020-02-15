package com.example.cz2006se;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.Buffer;
import java.util.concurrent.ThreadLocalRandom;

public class gym_config {
    public static final String TAG = "gplaces";

    public static final String RESULTS = "results";
    public static final String STATUS = "status";

    public static final String OK = "OK";
    public static final String ZERO_RESULTS = "ZERO_RESULTS";
    public static final String REQUEST_DENIED = "REQUEST_DENIED";
    public static final String OVER_QUERY_LIMIT = "OVER_QUERY_LIMIT";
    public static final String UNKNOWN_ERROR = "UNKNOWN_ERROR";
    public static final String INVALID_REQUEST = "INVALID_REQUEST";

    //    Key for nearby places json from google
    public static final String GEOMETRY = "geometry";
    public static final String LOCATION = "location";
    public static final String LATITUDE = "lat";
    public static final String LONGITUDE = "lng";
    public static final String NAME = "name";
    public static final String PLACE_ID = "place_id";
    public static final String REFERENCE = "reference";
    public static final String VICINITY = "vicinity";
    public static final String RATING = "rating";
    public static final String PRICE_LEVEL = "price_level";
    public static final String PLACE_NAME = "place_name";
    public static final String PHOTOS = "photos";
    public static final String INTERNATIONAL_PHONE_NUMBER = "international_phone_number";
    public static final String FORMATTED_ADDRESS = "formatted_address";
    public static final String OPENING_HOURS = "opening_hours";
    public static final String OPEN_NOW = "open_now";
    public static final String WEEKDAY_TEXT = "weekday_text";
    public static final String URL = "url";


    public static final String[] GOOGLE_API_KEY_LIST = new String[]{
            "AIzaSyAeTd-V-llhMDMUZ76jnJLFMgsxX6zNaIc"}; //DAKOH KEY
    public static final String GOOGLE_API_KEY = Array.get(GOOGLE_API_KEY_LIST, getRandomNumber(1)).toString();

    public static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    public static final int PROXIMITY_RADIUS = 1000;
    // The minimum distance to change Updates in meters
    public static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10; // 10 meters
    // The minimum time between updates in milliseconds
    public static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 1; // 1 minute


    public static String readUrl(String urlString) throws Exception {
        BufferedReader reader = null;
        try {
            URL url = new URL(urlString);
            reader = new BufferedReader(new InputStreamReader(url.openStream()));
            StringBuffer buffer = new StringBuffer();
            int read;
            char[] chars = new char[1024];
            while ((read = reader.read(chars)) != -1)
                buffer.append(chars, 0, read);

            return buffer.toString();
        } finally {
            if (reader != null)
                reader.close();
        }
    }


    public static int getRandomNumber(int noOfKeys){
        int output = ThreadLocalRandom.current().nextInt(0, noOfKeys);
        return output;
    }
}