package tobiapplications.com.moviebase.utils;

import android.content.Context;
import android.content.res.Configuration;

import java.text.NumberFormat;
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

        return getHowMuchColumnsForOverviewMovies(isTablet, isLandscape);
    }

    public static int getHowMuchColumnsForOverviewMovies(boolean isTablet, boolean isLandscape) {
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

    public static String formatThousands(String number) {
        if (StringUtils.nullOrEmpty(number)) {
            return "";
        }
        int value = Integer.valueOf(number);
        return NumberFormat.getNumberInstance(Locale.GERMAN).format(value);
    }

    public static String formatThousands(long number) {
        return NumberFormat.getNumberInstance(Locale.GERMAN).format(number);
    }

    public static String formatThousands(int number) {
        return NumberFormat.getNumberInstance(Locale.GERMAN).format(number);
    }

    public static boolean isMovie(int type) {
        return type == Constants.Type.MOVIES;
    }

    public static boolean isSerie(int type) {
        return type == Constants.Type.SERIES;
    }

    public static boolean isPopular(int category) {
        return category == Constants.Category.POPULAR;
    }

    public static boolean isTopRated(int category) {
        return category == Constants.Category.TOP_RATED;
    }

    public static int pxFromDp(Context context, int dp) {
        return (int) (dp * context.getResources().getDisplayMetrics().density);
    }

    public static String getTypeString(Context context, @Constants.Category int type) {
        if (isMovie(type)) {
            return context.getString(R.string.movie_identifier);
        } else if(isSerie(type)) {
            return context.getString(R.string.serie_identifier);
        }
        return context.getString(R.string.unknown_identifier);
    }
}
