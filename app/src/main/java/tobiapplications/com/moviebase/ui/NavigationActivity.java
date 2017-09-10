package tobiapplications.com.moviebase.ui;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import tobiapplications.com.moviebase.R;
import tobiapplications.com.moviebase.databinding.ActivityNavigationBinding;
import tobiapplications.com.moviebase.ui.settings.SettingsFragment;
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
        setSupportActionBar(bind.appBar.toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, bind.drawerLayout, bind.appBar.toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        bind.drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        bind.navView.setNavigationItemSelectedListener(this);

        init();
    }

    private void init() {
        SettingsUtils.updateApplicationLanguage(this);
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

        } else if (id == R.id.menu_series) {

        } else if (id == R.id.menu_settings) {
            openSettings();
        } else if (id == R.id.menu_info) {
            openAbout();
        }


        bind.drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void openSettings() {
        String settingsTag = getString(R.string.action_settings);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        SettingsFragment settingsFragment = SettingsFragment.newInstance(settingsTag);
        transaction.replace(R.id.fragment_container, settingsFragment, settingsTag).addToBackStack("fragment");
        transaction.commit();

    }

    private void openAbout() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        AboutFragment aboutFragment = AboutFragment.newInstance();
        transaction.replace(R.id.fragment_container, aboutFragment, getString(R.string.about)).addToBackStack("fragment");
        transaction.commit();
    }

}
