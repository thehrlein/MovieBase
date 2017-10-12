package tobiapplications.com.moviebase.utils;

import android.content.Context;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import java.text.NumberFormat;
import java.util.LinkedHashMap;
import java.util.Locale;

import tobiapplications.com.moviebase.R;

/**
 * Created by Tobias on 23.03.2017.
 */

public class GeneralUtils {

    private static final int LANDSCAPE = Configuration.ORIENTATION_LANDSCAPE;

    public static int getHowMuchColumnsForOverviewMovies(Context context) {
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

    public static int getGoBackCounter(int currentActor, boolean[] fullListWithAndWithOutPictures) {
        int goBackCounter = 0;
        for (int i = 0; i < currentActor; i++) {
            if (!fullListWithAndWithOutPictures[i]) {
                goBackCounter++;
            }
        }

        return goBackCounter;
    }

    public static String formatThousands(int number) {
        return NumberFormat.getNumberInstance(Locale.GERMAN).format(number);
    }

    public static String formatThousands(String number) {
        int value = Integer.valueOf(number);
        return NumberFormat.getNumberInstance(Locale.GERMAN).format(value);
    }

    public static boolean isMovie(int type) {
        return type == Constants.Type.MOVIES;
    }

    public static boolean isSerie(int type) {
        return type == Constants.Type.SERIES;
    }
}
