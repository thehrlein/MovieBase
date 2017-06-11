package tobiapplications.com.moviebase.ui.movie_overview;

import android.os.Build;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import tobiapplications.com.moviebase.R;
import tobiapplications.com.moviebase.adapter.ViewPagerAdapter;

public class MovieOverviewActivity extends AppCompatActivity {

    private TabLayout mTabLayout;
    private ViewPager mViewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        disableActionBarTabLayoutDivider();
        setStatusBarColor();
        findMyViews();
        setupViewPager();
        mTabLayout.setupWithViewPager(mViewPager);
    }

    private void disableActionBarTabLayoutDivider() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setElevation(0);
        }
    }

    private void setStatusBarColor() {
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }
    }

    private void findMyViews() {
        mTabLayout = (TabLayout) findViewById(R.id.tabs);
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
    }

    private void setupViewPager() {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(PopularMovieFragment.newInstance(), getString(R.string.title_popular));
        adapter.addFragment(TopRatedMovieFragment.newInstance(), getString(R.string.title_top_rated));
        adapter.addFragment(OwnFavoriteMovieFragment.newInstance(), getString(R.string.title_favorite));

        mViewPager.setOffscreenPageLimit(3);
        mViewPager.setAdapter(adapter);
    }



}
