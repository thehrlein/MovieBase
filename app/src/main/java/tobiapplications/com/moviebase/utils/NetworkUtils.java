package tobiapplications.com.moviebase.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import tobiapplications.com.moviebase.BuildConfig;

/**
 * Created by Tobias on 09.06.2017.
 */

public class NetworkUtils {

    public static final String MOVIE_BASE_URL = "https://api.themoviedb.org/3/";
    private static final String IMAGE_BASE_URL = "http://image.tmdb.org/t/p/";
    private static final String IMAGE_HIGH_SUFFIX = "w500";
    private static final String IMAGE_LOW_SUFFIX = "w185";
    private static final String API_PREFIX = "?api_key=";
    private static final String MOVIE_DB_API_KEY = BuildConfig.MY_MOVIE_DB_API_KEY;
    private static final String API_KEY_FULL = API_PREFIX + MOVIE_DB_API_KEY;


    public static String appendApiKey() {
        return API_KEY_FULL;
    }

    public static String getKey() {
        return MOVIE_DB_API_KEY;
    }

    public static String getImageBaseUrlHigh() {
        return IMAGE_BASE_URL + IMAGE_HIGH_SUFFIX;
    }

    public static String getImageBaseUrlLow() {
        return IMAGE_BASE_URL + IMAGE_LOW_SUFFIX;
    }

    public static boolean isConnectedToInternet(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager != null) {
            //Check Mobile data or Wifi net is present
            return connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                    connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED;
        } else  {
            return false;
        }
    }
}
