/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package tobiapplications.com.moviebase.ui.settings;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.preference.ListPreference;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.support.v7.preference.PreferenceScreen;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import tobiapplications.com.moviebase.R;
import tobiapplications.com.moviebase.listener.DialogBuilderListener;
import tobiapplications.com.moviebase.ui.NavigationActivity;
import tobiapplications.com.moviebase.utils.Constants;
import tobiapplications.com.moviebase.utils.DialogBuilderUtil;
import tobiapplications.com.moviebase.utils.SQLUtils;
import tobiapplications.com.moviebase.utils.SettingsUtils;

public class SettingsFragment extends PreferenceFragmentCompat implements
        SharedPreferences.OnSharedPreferenceChangeListener {


    public static SettingsFragment newInstance() {
        SettingsFragment settingsFragment = new SettingsFragment();
        Bundle bundle = new Bundle();
        settingsFragment.setArguments(bundle);
        return settingsFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle(R.string.action_settings);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setNavigationSelected();
    }

    private void setNavigationSelected() {
        NavigationActivity navigationActivity = (NavigationActivity) getActivity();
        navigationActivity.setMenuItemChecked(R.id.menu_settings);
    }

    @Override
    public void onCreatePreferences(Bundle bundle, String s) {
        // Add 'general' preferences, defined in the XML file
        addPreferencesFromResource(R.xml.pref_general);

        SharedPreferences sharedPreferences = getPreferenceScreen().getSharedPreferences();
        PreferenceScreen prefScreen = getPreferenceScreen();
        int count = prefScreen.getPreferenceCount();
        for (int i = 0; i < count; i++) {
            Preference p = prefScreen.getPreference(i);
            if (p instanceof ListPreference) {
                String value = sharedPreferences.getString(p.getKey(), SettingsUtils.getAppLanguage());
                setPreferenceSummary(p, value);
            } else {
                setPreferenceEnabled(p);
                setPreferenceListener(p);
            }
        }
    }

    private void setPreferenceEnabled(Preference preference) {
        Context context = preference.getContext();
        if (preference.getKey().equals(context.getString(R.string.pref_key_delete_movies))) {
            boolean enabled = SQLUtils.areFavoritesAvailable(context, Constants.Type.MOVIES);
            preference.setEnabled(enabled);
            preference.setSummary(enabled ? context.getString(R.string.pref_summary_delete) : context.getString(R.string.pref_summary_no_favorites));
        } else if (preference.getKey().equals(context.getString(R.string.pref_key_delete_series))) {
            boolean enabled = SQLUtils.areFavoritesAvailable(context, Constants.Type.SERIES);
            preference.setEnabled(enabled);
            preference.setSummary(enabled ? context.getString(R.string.pref_summary_delete) : context.getString(R.string.pref_summary_no_favorites));
        }
    }

    private void setPreferenceListener(Preference preference) {
        Context context = preference.getContext();
        String moviesEntry = context.getString(R.string.pref_delete_dialog_movies_entry);
        String seriesEntry = context.getString(R.string.pref_delete_dialog_series_entry);
        if (preference.getKey().equals(context.getString(R.string.pref_key_delete_movies))) {
            preference.setOnPreferenceClickListener(preference1 -> {
                showDeleteDialog(Constants.Type.MOVIES, preference, moviesEntry, seriesEntry);
                return true;
            });
        } else if (preference.getKey().equals(context.getString(R.string.pref_key_delete_series))) {
            preference.setOnPreferenceClickListener(preference1 -> {
                showDeleteDialog(Constants.Type.SERIES, preference, seriesEntry, moviesEntry);
                return true;
            });
        }
    }

    private void showDeleteDialog(@Constants.Type int type, Preference preference, String firstEntry, String secondEntry) {
        Context context = preference.getContext();
        DialogBuilderUtil.createDialog(context, context.getString(R.string.pref_delete_dialog_title),
                context.getString(R.string.pref_delete_dialog_text, firstEntry, secondEntry), new DialogBuilderListener() {
                    @Override
                    public void onConfirm() {
                        SQLUtils.deleteAllFavorites(context, type);
                        if (getView() != null) {
                            Snackbar.make(getView(), context.getString(R.string.pref_all_favorites_deleted_sucessfully, firstEntry), Snackbar.LENGTH_LONG).show();
                        }
                        setPreferenceEnabled(preference);
                    }

                    @Override
                    public void onCancel() {
                        // do nothing
                    }
                }).show();
    }

    private static void setPreferenceSummary(Preference preference, Object value) {
        String stringValue = value.toString();

        if (preference instanceof ListPreference) {
            // For list preferences, look up the correct display value in
            // the preference's 'entries' list (since they have separate labels/values).
            ListPreference listPreference = (ListPreference) preference;
            int prefIndex = listPreference.findIndexOfValue(stringValue);
            if (prefIndex >= 0) {
                preference.setSummary(listPreference.getEntries()[prefIndex]);
                ((ListPreference) preference).setValueIndex(prefIndex);
            }
        } else {
            // For other preferences, set the summary to the value's simple string representation.
            preference.setSummary(stringValue);
        }
    }

    private void reloadSettings() {
        Intent intent = new Intent(getContext(), NavigationActivity.class);
        startActivity(intent);
        getActivity().finish();
    }

    @Override
    public void onStart() {
        super.onStart();
        // register the preference change listener
        getPreferenceScreen().getSharedPreferences()
                .registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        // unregister the preference change listener
        getPreferenceScreen().getSharedPreferences()
                .unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        Activity activity = getActivity();

        if (key.equals(getString(R.string.pref_language_key))) {
            SettingsUtils.updateApplicationLanguage(activity);
            reloadSettings();
        }

        Preference preference = findPreference(key);
        if (null != preference) {
            if (preference instanceof ListPreference) {
                setPreferenceSummary(preference, sharedPreferences.getString(key, ""));
            }
        }
    }
}
