package tobiapplications.com.moviebase.utils;

import tobiapplications.com.moviebase.BuildConfig;

/**
 * Created by Tobias on 09.06.2017.
 */

public class NetworkUtils {

    public static final String MOVIE_BASE_URL = "https://api.themoviedb.org/3/";
    public static final String IMAGE_BASE_URL_HIGH = "http://image.tmdb.org/t/p/w185";
    private static final String API_PREFIX = "?api_key=";
    private static final String MOVIE_DB_API_KEY = BuildConfig.MY_MOVIE_DB_API_KEY;
    private static final String API_KEY_FULL = API_PREFIX + MOVIE_DB_API_KEY;


    public static String appendApiKey() {
        return API_KEY_FULL;
    }

    public static String getKey() {
        return MOVIE_DB_API_KEY;
    }
}
