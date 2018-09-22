package tobiapplications.com.moviebase.ui;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.graphics.Point;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.core.view.GravityCompat;

import android.view.Display;
import android.view.MenuItem;

import com.crashlytics.android.answers.Answers;
import com.crashlytics.android.answers.ContentViewEvent;
import com.google.android.material.navigation.NavigationView;


import tobiapplications.com.moviebase.R;
import tobiapplications.com.moviebase.databinding.ActivityNavigationBinding;
import tobiapplications.com.moviebase.network.DataManager;
import tobiapplications.com.moviebase.ui.info.AboutFragment;
import tobiapplications.com.moviebase.ui.overview.OverviewFragment;
import tobiapplications.com.moviebase.ui.search.SearchResultsFragment;
import tobiapplications.com.moviebase.ui.search.SearchQueryFragment;
import tobiapplications.com.moviebase.ui.settings.SettingsFragment;
import tobiapplications.com.moviebase.utils.Constants;
import tobiapplications.com.moviebase.utils.SettingsUtils;
import tobiapplications.com.moviebase.utils.Store;

/**
 * Created by Tobias on 10.09.2017.
 */

public class NavigationActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private ActivityNavigationBinding bind;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SettingsUtils.updateApplicationLanguage(this);

        bind = DataBindingUtil.setContentView(this, R.layout.activity_navigation);

        init();
    }

    private void init() {
        Display display = getWindowManager(). getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        Store.getInstance().setScreenWidth(size.x);

        DataManager.getInstance().buildApi(Constants.THE_MOVIE_DB);
        DataManager.getInstance().buildApi(Constants.YOUTUBE);

        setSupportActionBar(bind.appBar.toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, bind.drawerLayout, bind.appBar.toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        bind.drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        bind.navView.setNavigationItemSelectedListener(this);

        openMovies(false);
    }


    public void setMenuItemChecked(int id) {
        bind.navView.setCheckedItem(id);
    }

    @Override
    public void onBackPressed() {
        if (bind.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            bind.drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();


        if (id == R.id.menu_movies) {
            openMovies(true);
        } else if (id == R.id.menu_series) {
            openSeries();
        } else if (id == R.id.menu_settings) {
            openSettings();
        } else if (id == R.id.menu_info) {
            openAbout();
        } else if (id == R.id.menu_search) {
            openSearchRequestFragment();
        }


        bind.drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void openSearchRequestFragment() {
        trackViewOpening(getString(R.string.search_query_fragment_identifier));
        String searchTag = getString(R.string.search_tag);
        SearchQueryFragment searchQueryFragment = SearchQueryFragment.newInstance();
        replaceFragment(searchQueryFragment, searchTag, true);
    }

    public void openSearchResults(String text, int overviewType) {
        trackViewOpening(getString(R.string.search_results_fragment_identifier));
        String searchTag = getString(R.string.search_tag);
        Bundle bundle = new Bundle();
        bundle.putString(Constants.SEARCH_QUERY, text);
        bundle.putInt(Constants.TYPE, overviewType);
        SearchResultsFragment fragment = SearchResultsFragment.newInstance(bundle);
        replaceFragment(fragment, searchTag, true);
    }

    private void openSeries() {
        trackViewOpening(getString(R.string.series_fragment_identifier));
        String seriesTag = getString(R.string.series_tag);
        OverviewFragment overviewFragment = OverviewFragment.newInstance(Constants.Type.SERIES);
        replaceFragment(overviewFragment, seriesTag, true);
    }

    private void openMovies(boolean addToBackStack) {
        trackViewOpening(getString(R.string.movies_fragment_identifier));
        String movieTag = getString(R.string.movie_tag);
        OverviewFragment overviewFragment = OverviewFragment.newInstance(Constants.Type.MOVIES);
        replaceFragment(overviewFragment, movieTag, addToBackStack);
    }

    private void openSettings() {
        trackViewOpening(getString(R.string.settings_fragment_identifier));
        String settingsTag = getString(R.string.action_settings);
        SettingsFragment settingsFragment = SettingsFragment.newInstance();
        replaceFragment(settingsFragment, settingsTag, true);
    }

    private void openAbout() {
        trackViewOpening(getString(R.string.about_fragment_identifier));
        String aboutTag = getString(R.string.about);
        AboutFragment aboutFragment = AboutFragment.newInstance();
        replaceFragment(aboutFragment, aboutTag, true);
    }

    private void replaceFragment(Fragment fragment, String fragmentTag, boolean addToBackStack) {
        String navigationTag = getString(R.string.navigation_tag);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (addToBackStack) {
            transaction.replace(R.id.fragment_container, fragment, fragmentTag).addToBackStack(navigationTag);
        } else {
            transaction.replace(R.id.fragment_container, fragment, fragmentTag);
        }
        transaction.commit();
    }

    private void trackViewOpening(String tag) {
        Answers.getInstance().logContentView(new ContentViewEvent()
                .putContentName(tag));
    }
}
