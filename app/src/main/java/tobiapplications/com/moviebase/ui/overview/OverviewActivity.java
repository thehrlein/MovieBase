package tobiapplications.com.moviebase.ui.overview;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import tobiapplications.com.moviebase.R;
import tobiapplications.com.moviebase.adapter.ViewPagerAdapter;
import tobiapplications.com.moviebase.databinding.ActivityOverviewBinding;
import tobiapplications.com.moviebase.network.DataManager;
import tobiapplications.com.moviebase.utils.Constants;
import tobiapplications.com.moviebase.utils.SettingsUtils;

public class OverviewActivity extends AppCompatActivity implements OverviewActivityContract.View {

    private ActivityOverviewBinding bind;
    private String currentLanguage = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bind = DataBindingUtil.setContentView(this, R.layout.activity_overview);

        init();

        currentLanguage = SettingsUtils.getAppLanguage();
        disableActionBarTabLayoutDivider();
        setStatusBarColor();
        setupViewPager();
        bind.tabs.setupWithViewPager(bind.viewpager);
        hideViewsOnLoading();
    }

    private void init() {
        SettingsUtils.updateApplicationLanguage(this);
        DataManager.getInstance().buildApi(Constants.THE_MOVIE_DB);
        DataManager.getInstance().buildApi(Constants.YOUTUBE);
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
    public void setupViewPager() {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(PopularFragment.newInstance(this), getString(R.string.title_popular));
        adapter.addFragment(TopRatedFragment.newInstance(), getString(R.string.title_top_rated));
        adapter.addFragment(OwnFavoriteFragment.newInstance(), getString(R.string.title_favorite));
        bind.viewpager.setOffscreenPageLimit(3);
        bind.viewpager.setAdapter(adapter);
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
            case R.id.action_about:
                openAbout();
        }
        return super.onOptionsItemSelected(item);
    }

    private void openAbout() {

    }

    @Override
    public boolean onSearchRequested() {
        // if search view gets focus
        // doStuff()
        return super.onSearchRequested();
    }

    private void openSettings() {

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
        restart.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        restart.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        restart.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(restart);
        finish();
    }

    private void hideViewsOnLoading() {
        bind.viewpager.disableSwipe(true);
        bind.tabs.setVisibility(View.INVISIBLE);
        bind.appBarLayout.setVisibility(View.INVISIBLE);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(ContextCompat.getColor(this, android.R.color.transparent));
        }
    }

    @Override
    public void showAllViews() {
        bind.viewpager.disableSwipe(false);
        bind.appBarLayout.setVisibility(View.VISIBLE);
        bind.tabs.setVisibility(View.VISIBLE);
        getSupportActionBar().show();
        setStatusBarColor();
    }
}
