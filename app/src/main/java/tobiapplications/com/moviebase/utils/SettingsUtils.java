package tobiapplications.com.moviebase.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;

import java.util.Locale;

import tobiapplications.com.moviebase.R;

/**
 * Created by Tobias on 18.06.2017.
 */

public class SettingsUtils {

    private static String appLanguage = "";

    public static String getAppLanguage() {
        if (TextUtils.isEmpty(appLanguage)) {
            return getPhoneDefaultLanguage();
        }
        return appLanguage;
    }

    private static String getLanguageFromSharedPrefs(Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        String pref_language_key = context.getString(R.string.pref_language_key);
        appLanguage = sp.getString(pref_language_key, getPhoneDefaultLanguage());

        return appLanguage;
    }

    public static String getPhoneDefaultLanguage() {
        return Locale.getDefault().getLanguage();
    }
    
    public static void updateApplicationLanguage(Context context) {
        String language = getLanguageFromSharedPrefs(context);
        Resources resources = context.getResources();
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        Configuration configuration = resources.getConfiguration();
        configuration.locale = new Locale(language);
        resources.updateConfiguration(configuration, displayMetrics);
    }
}
