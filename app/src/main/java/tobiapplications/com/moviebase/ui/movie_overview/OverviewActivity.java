package tobiapplications.com.moviebase.ui.movie_overview;

import android.os.Build;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import tobiapplications.com.moviebase.R;
import tobiapplications.com.moviebase.adapter.ViewPagerAdapter;

public class OverviewActivity extends AppCompatActivity implements OverviewActivityContract.View {

    private TabLayout mTabLayout;
    private ViewPager mViewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overview);

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
}
