package tobiapplications.com.moviebase.ui;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import tobiapplications.com.moviebase.R;
import tobiapplications.com.moviebase.databinding.ActivityNavigationBinding;
import tobiapplications.com.moviebase.network.DataManager;
import tobiapplications.com.moviebase.ui.overview.OverviewFragment;
import tobiapplications.com.moviebase.ui.settings.SettingsFragment;
import tobiapplications.com.moviebase.utils.Constants;
import tobiapplications.com.moviebase.utils.SettingsUtils;

/**
 * Created by Tobias on 10.09.2017.
 */

public class NavigationActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private ActivityNavigationBinding bind;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bind = DataBindingUtil.setContentView(this, R.layout.activity_navigation);

        init();
    }

    private void init() {
        SettingsUtils.updateApplicationLanguage(this);
        DataManager.getInstance().buildApi(Constants.THE_MOVIE_DB);
        DataManager.getInstance().buildApi(Constants.YOUTUBE);

        setSupportActionBar(bind.appBar.toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, bind.drawerLayout, bind.appBar.toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        bind.drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        bind.navView.setNavigationItemSelectedListener(this);

        // set movies selected on startup
        onNavigationItemSelected(bind.navView.getMenu().findItem(R.id.menu_movies));
        bind.navView.setCheckedItem(R.id.menu_movies);
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
            openMovies();
        } else if (id == R.id.menu_series) {
            openSeries();
        } else if (id == R.id.menu_settings) {
            openSettings();
        } else if (id == R.id.menu_info) {
            openAbout();
        }


        bind.drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void openSeries() {
        String seriesTag = getString(R.string.series_tag);
        OverviewFragment overviewFragment = OverviewFragment.newInstance(Constants.OverviewType.SERIES);
        replaceFragment(overviewFragment, seriesTag);
    }

    private void openMovies() {
        String movieTag = getString(R.string.movie_tag);
        OverviewFragment overviewFragment = OverviewFragment.newInstance(Constants.OverviewType.MOVIES);
        replaceFragment(overviewFragment, movieTag);
    }

    private void openSettings() {
        String settingsTag = getString(R.string.action_settings);
        SettingsFragment settingsFragment = SettingsFragment.newInstance(settingsTag);
        replaceFragment(settingsFragment, settingsTag);
    }

    private void openAbout() {
        String aboutTag = getString(R.string.about);
        AboutFragment aboutFragment = AboutFragment.newInstance();
        replaceFragment(aboutFragment, aboutTag);
    }

    private void replaceFragment(Fragment fragment, String fragmentTag) {
        String navigationTag = getString(R.string.navigation_tag);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment, fragmentTag).addToBackStack(navigationTag);
        transaction.commit();
    }

}
