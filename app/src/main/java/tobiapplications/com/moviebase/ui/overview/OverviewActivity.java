package tobiapplications.com.moviebase.ui.overview;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.prefs.Preferences;

import tobiapplications.com.moviebase.R;
import tobiapplications.com.moviebase.adapter.ViewPagerAdapter;
import tobiapplications.com.moviebase.ui.settings.SettingsActivity;
import tobiapplications.com.moviebase.utils.SettingsUtils;

public class OverviewActivity extends AppCompatActivity implements OverviewActivityContract.View {

    private TabLayout mTabLayout;
    private ViewPager mViewPager;

    private String currentLanguage = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overview);

        currentLanguage = SettingsUtils.getAppLanguage();
        disableActionBarTabLayoutDivider();
        setStatusBarColor();
        findMyViews();
        setupViewPager();
        mTabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    public void disableActionBarTabLayoutDivider() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setElevation(0);
        }
    }

    @Override
    public void setStatusBarColor() {
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }
    }

    @Override
    public void findMyViews() {
        mTabLayout = (TabLayout) findViewById(R.id.tabs);
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
    }

    @Override
    public void setupViewPager() {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(PopularFragment.newInstance(), getString(R.string.title_popular));
        adapter.addFragment(TopRatedFragment.newInstance(), getString(R.string.title_top_rated));
        adapter.addFragment(OwnFavoriteFragment.newInstance(), getString(R.string.title_favorite));

        mViewPager.setOffscreenPageLimit(3);
        mViewPager.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu( Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);

        // Get the SearchView and set the searchable configuration
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(true);
        searchView.setSubmitButtonEnabled(true);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                onSearchRequested();
                break;
            case  R.id.action_settings:
                openSettings();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSearchRequested() {
        // if search view gets focus
        // doStuff()
        return super.onSearchRequested();
    }

    private void openSettings() {
        Intent openSettings = new Intent(this, SettingsActivity.class);
        startActivity(openSettings);
    }

    @Override
    protected void onResume() {
        super.onResume();
        String tempLang = currentLanguage;
        currentLanguage = SettingsUtils.getAppLanguage();
        if (!currentLanguage.equals(tempLang)) {
            restartActivity();
            return;
        }
    }

    private void restartActivity() {
        Intent restart = new Intent(this, OverviewActivity.class);
        startActivity(restart);
    }
}
