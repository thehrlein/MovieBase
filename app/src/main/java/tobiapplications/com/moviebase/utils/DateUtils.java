package tobiapplications.com.moviebase.utils;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import timber.log.Timber;

/**
 * Created by Tobias on 14.06.2017.
 */

public class DateUtils {

    private static final int HOUR = 60;
    private static final String HOUR_TEXT = "h";
    private static final String MINUTE_TEXT = "min";
    private static final String EMPTY_STRING = "";

    public static String getDMYFromYMD(String oldDateString) {
        if (StringUtils.nullOrEmpty(oldDateString)) {
            return EMPTY_STRING;
        }
        SimpleDateFormat oldFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.GERMAN);
        SimpleDateFormat newFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.GERMAN);
        String newDateString;
        try {
            Date oldDate = oldFormat.parse(oldDateString);
            newDateString = newFormat.format(oldDate);
        } catch (ParseException e) {
            Timber.d("Error parsing " + oldDateString + " to the new format!");
            newDateString = EMPTY_STRING;
        }

        return newDateString;
    }

    public static String getHourMinuteStringFromInt(int minutes) {
        if (minutes < 0) {
            return "Unknown";
        }
        int hours = minutes / HOUR;
        int min = minutes - (hours * HOUR);
        return String.valueOf(hours + HOUR_TEXT + " " + min + MINUTE_TEXT);
    }
}
