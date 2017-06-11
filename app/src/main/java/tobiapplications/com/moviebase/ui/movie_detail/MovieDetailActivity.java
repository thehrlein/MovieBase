package tobiapplications.com.moviebase.ui.movie_detail;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import tobiapplications.com.moviebase.R;
import tobiapplications.com.moviebase.adapter.ViewPagerAdapter;
import tobiapplications.com.moviebase.model.MovieOverviewModel;

public class MovieDetailActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView mDetailMovieBackgroundImage;
    private FloatingActionButton mFabFavorite;
    private Toolbar mToolbar;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private boolean isMarkedAsFavorite = false;
    private ViewPagerAdapter mAdapter;
    private MovieDetailPresenter presenter;

    private int movieId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        setUpActionBar();

        findMyViews();

        movieId = getMovieId();

        presenter = new MovieDetailPresenter(this, movieId);
        presenter.requestSingleMovieDownload();

        setupViewPager();
        mTabLayout.setupWithViewPager(mViewPager);

    }


    private void setUpActionBar() {
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    private void findMyViews() {
        mTabLayout = (TabLayout) findViewById(R.id.tabs);
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mDetailMovieBackgroundImage = (ImageView) findViewById(R.id.toolbarBackgroundImage);
        mDetailMovieBackgroundImage.setOnClickListener(this);
        mFabFavorite = (FloatingActionButton) findViewById(R.id.detail_button_mark_favorite);
        mFabFavorite.setOnClickListener(this);
    }

    private int getMovieId() {
        Intent intent = getIntent();
        if (intent != null) {
            return intent.getIntExtra("clickedMovie", -1);
        }

        return -1;
    }

    private void setupViewPager()
    {
        mAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        Bundle bundle = new Bundle();
        bundle.putInt("clickedMovie", movieId);


        Fragment infoFragment = new DetailInfoFragment();
        infoFragment.setArguments(bundle);
        mAdapter.addFragment(infoFragment, getString(R.string.info));

        Fragment trailerFragment = new DetailTrailerFragment();
        trailerFragment.setArguments(bundle);
        mAdapter.addFragment(trailerFragment, getString(R.string.trailers));

        Fragment reviewFragment = new DetailReviewFragment();
        reviewFragment.setArguments(bundle);
        mAdapter.addFragment(reviewFragment, getString(R.string.reviews));

        Fragment actorFragment = new DetailActorsFragment();
        actorFragment.setArguments(bundle);
        mAdapter.addFragment(actorFragment, getString(R.string.actors));

        Fragment similarMovies = new DetailSimilarMoviesFragment();
        similarMovies.setArguments(bundle);
        mAdapter.addFragment(similarMovies, getString(R.string.similar_movies));

        mViewPager.setAdapter(mAdapter);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.detail_button_mark_favorite:
                handleFabClick();
                break;
            case R.id.toolbarBackgroundImage:
                presenter.openToolbarImage();
                break;
        }
    }

    public void setMovieInformation(String title, String moviePath) {
        mToolbar.setTitle(title);
        Picasso.with(this).load(moviePath).into(mDetailMovieBackgroundImage);

    }

    private void handleFabClick() {

    }
}
