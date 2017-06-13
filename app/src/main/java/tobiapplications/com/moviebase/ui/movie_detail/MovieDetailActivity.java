package tobiapplications.com.moviebase.ui.movie_detail;

import android.content.ContentValues;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import tobiapplications.com.moviebase.R;
import tobiapplications.com.moviebase.adapter.ViewPagerAdapter;
import tobiapplications.com.moviebase.database.MoviesContract;
import tobiapplications.com.moviebase.utils.Constants;

public class MovieDetailActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView mDetailMovieBackgroundImage;
    private FloatingActionButton mFabFavorite;
    private Toolbar mToolbar;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private ProgressBar mImageProgressIndicator;
    private ViewPagerAdapter mAdapter;
    private MovieDetailPresenter presenter;

    private int movieId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        findMyViews();

        setUpActionBar();

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
        mImageProgressIndicator = (ProgressBar) findViewById(R.id.detail_background_image_progress_indicator);
    }

    private int getMovieId() {
        Intent intent = getIntent();
        if (intent != null) {
            return intent.getIntExtra(Constants.CLICKED_MOVIE, -1);
        }

        return -1;
    }

    private void setupViewPager()
    {
        mAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        Bundle bundle = new Bundle();
        bundle.putInt(Constants.CLICKED_MOVIE, movieId);


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
                presenter.handleFabClick();
                break;
            case R.id.toolbarBackgroundImage:
                presenter.openToolbarImage();
                break;
        }
    }

    public void setMovieInformation(String title, String moviePath) {
        mToolbar.setTitle(title);
        mImageProgressIndicator.setVisibility(View.GONE);
        Picasso.with(this).load(moviePath).into(mDetailMovieBackgroundImage);
    }

    public void setFabButtonImage(int drawable) {
        mFabFavorite.setImageResource(drawable);
    }

    public void onFabClickedToast(boolean marked) {
        if (marked) {
            Toast.makeText(this, getString(R.string.marked_as_favorite), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, getString(R.string.unmarked_as_favorite), Toast.LENGTH_SHORT).show();
        }
    }

    public void insertMovieIntoDatabase(ContentValues values) {
        getContentResolver().insert(MoviesContract.MovieEntry.CONTENT_URI, values);
    }

    public void deleteCurrentMovieFromFavoriteDatabase(int movieId) {
        getContentResolver().delete(
                MoviesContract.MovieEntry.CONTENT_URI,
                MoviesContract.MovieEntry.COLUMN_ID + " = ?",
                new String[]{String.valueOf(movieId)});

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if(menuItem.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return true;
    }
}
