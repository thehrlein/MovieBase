package tobiapplications.com.moviebase.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;

import tobiapplications.com.moviebase.BuildConfig;

/**
 * Created by Tobias on 09.06.2017.
 */

public class NetworkUtils {

    private static final String MOVIE_BASE_URL = "https://api.themoviedb.org/3/";
    private static final String IMAGE_BASE_URL = "https://image.tmdb.org/t/p/";
    private static final String YOUTUBE_API_BASE_URL = "https://www.googleapis.com/youtube/v3/";
    private static final String YOUTUBE_WATCH_BASE_URL = "https://www.youtube.com/watch?v=";
    private static final String IMAGE_HIGH_SUFFIX = "w500";
    private static final String IMAGE_LOW_SUFFIX = "w185";
    private static final String API_PREFIX = "?api_key=";
    private static final String MOVIE_DB_KEY = BuildConfig.MY_MOVIE_DB_KEY;
    private static final String KEY_FULL = API_PREFIX + MOVIE_DB_KEY;
    private static final String YOUTUBE_KEY = BuildConfig.MY_YOUTUBE_KEY;

    public static String getApiBaseUrl(String apiKey) {
        if (apiKey.equals(Constants.THE_MOVIE_DB)) {
            return MOVIE_BASE_URL;
        } else if (apiKey.equals(Constants.YOUTUBE)) {
            return YOUTUBE_API_BASE_URL;
        }
        return "";
    }

    public static String appendKey() {
        return KEY_FULL;
    }

    public static String getMovieKey() {
        return MOVIE_DB_KEY;
    }

    public static String getYoutubeKey() { return YOUTUBE_KEY; }

    public static String getImageBaseUrlHigh() {
        return IMAGE_BASE_URL + IMAGE_HIGH_SUFFIX;
    }

    public static String getImageBaseUrlLow() {
        return IMAGE_BASE_URL + IMAGE_LOW_SUFFIX;
    }

    public static boolean isConnectedToInternet(Context context) {
        if (context == null) {
            return false;
        }
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        //Check Mobile data or Wifi net is present
        return connectivityManager != null && connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE) != null
                && connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI) != null
                && (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED
                    || connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED);
    }

    public static String getFullImageUrlLow(String url) {
        return getImageBaseUrlLow() + url + appendKey();
    }

    public static String getFullImageUrlHigh(String url) {
        return getImageBaseUrlHigh() + url + appendKey();
    }

    public static Uri buildYoutubeIntent(String trailerId) {
        return Uri.parse(YOUTUBE_WATCH_BASE_URL + trailerId).buildUpon().build();
    }
}
