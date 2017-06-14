package tobiapplications.com.moviebase.utils;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Tobias on 14.06.2017.
 */

public class DateUtils {

    public static String getDMYFromYMD(String oldDateString) {
        SimpleDateFormat oldFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.GERMAN);
        SimpleDateFormat newFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.GERMAN);
        String newDateString;
        try {
            Date oldDate = oldFormat.parse(oldDateString);
            newDateString = newFormat.format(oldDate);
        } catch (ParseException e) {
            Log.d("DateParseException", "Error parsing " + oldDateString + " to the new format!");
            newDateString = oldDateString;
        }

        return newDateString;
    }
}
