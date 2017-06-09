package tobiapplications.com.moviebase.utils;

import android.content.Context;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import tobiapplications.com.moviebase.R;

/**
 * Created by Tobias on 23.03.2017.
 */

public class Helper {
    private static final int LANDSCAPE = Configuration.ORIENTATION_LANDSCAPE;

    public static int getHowMuchColumnsForMovies(Context context) {
        boolean isTablet = context.getResources().getBoolean(R.bool.isTablet);
        int orientation = context.getResources().getConfiguration().orientation;
        boolean isLandscape = orientation == LANDSCAPE;

        if (isTablet && isLandscape) {
            return 6;
        } else if (isTablet) {
            return 4;
        } else if (isLandscape) {
            return 4;
        } else {
            return 2;
        }
    }

    public static int getHowMuchColumnsForSimilarMovies(Context context) {
        boolean isTablet = context.getResources().getBoolean(R.bool.isTablet);
        int orientation = context.getResources().getConfiguration().orientation;
        boolean isLandscape = orientation == LANDSCAPE;

        if (isTablet && isLandscape) {
            return 6;
        } else if (isTablet) {
            return 4;
        } else if (isLandscape) {
            return 4;
        } else {
            return 3;
        }
    }

    public static int getHowMuchColumnsForTrailers(Context context) {
        boolean isTablet = context.getResources().getBoolean(R.bool.isTablet);
        int orientation = context.getResources().getConfiguration().orientation;
        boolean isLandscape = orientation == LANDSCAPE;

        if (isTablet && isLandscape) {
            return 2;
        } else if (isTablet) {
            return 2;
        } else if (isLandscape) {
            return 2;
        } else {
            return 1;
        }
    }

    public static int getHochMuchColumnsForActors(Context context) {
        boolean isTablet = context.getResources().getBoolean(R.bool.isTablet);
        int orientation = context.getResources().getConfiguration().orientation;
        boolean isLandscape = orientation == LANDSCAPE;

        if (isTablet && isLandscape) {
            return 3;
        } else if (isTablet) {
            return 2;
        } else if (isLandscape) {
            return 2;
        } else {
            return 2;
        }
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
