package tobiapplications.com.moviebase.ui.movie_detail;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
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
import tobiapplications.com.moviebase.utils.SQLUtils;

public class DetailActivity extends AppCompatActivity implements View.OnClickListener, DetailActivityContract.View {

    private ImageView mDetailMovieBackgroundImage;
    private FloatingActionButton mFabFavorite;
    private Toolbar mToolbar;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private ProgressBar mImageProgressIndicator;
    private ViewPagerAdapter mAdapter;
    private DetailActivityPresenter presenter;

    private int movieId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        findMyViews();

        setUpActionBar();

        getMovieId();

        presenter = new DetailActivityPresenter(this, movieId);
        presenter.requestSingleMovieDownload();

        setupViewPager();
        mTabLayout.setupWithViewPager(mViewPager);

    }

    @Override
    public void setUpActionBar() {
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public void findMyViews() {
        mTabLayout = (TabLayout) findViewById(R.id.tabs);
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mDetailMovieBackgroundImage = (ImageView) findViewById(R.id.toolbarBackgroundImage);
        mDetailMovieBackgroundImage.setOnClickListener(this);
        mFabFavorite = (FloatingActionButton) findViewById(R.id.detail_fab_button_favorite);
        mFabFavorite.setOnClickListener(this);
        mImageProgressIndicator = (ProgressBar) findViewById(R.id.detail_background_image_progress_indicator);
    }

    @Override
    public void getMovieId() {
        Intent intent = getIntent();
        if (intent != null) {
            movieId = intent.getIntExtra(Constants.CLICKED_MOVIE, -1);
        }
    }

    @Override
    public void setupViewPager() {
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
            case R.id.detail_fab_button_favorite:
                presenter.handleFabClick();
                break;
            case R.id.toolbarBackgroundImage:
                presenter.openToolbarImage();
                break;
        }
    }

    @Override
    public void setMovieInformation(String title, String moviePath) {
        mToolbar.setTitle(title);
        mImageProgressIndicator.setVisibility(View.GONE);
        Picasso.with(this).load(moviePath).into(mDetailMovieBackgroundImage);
    }

    @Override
    public void onFabClickedToast(boolean marked) {
        if (marked) {
            Toast.makeText(this, getString(R.string.marked_as_favorite), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, getString(R.string.unmarked_as_favorite), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void insertMovieIntoDatabase(ContentValues values) {
        getContentResolver().insert(MoviesContract.MovieEntry.CONTENT_URI, values);
    }

    @Override
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

    @Override
    public Cursor getAllFavoriteMovies() {
        return getContentResolver().query(
                MoviesContract.MovieEntry.CONTENT_URI,
                SQLUtils.selectAll,
                null,
                null,
                null);
    }

    @Override
    public void markFabAsFavorite(boolean isFavorite) {
        if (isFavorite) {
            mFabFavorite.setImageResource(R.drawable.fab_heart_fav);
        } else {
            mFabFavorite.setImageResource(R.drawable.fab_heart);
        }
    }

    @Override
    public void setFabButtonVisible() {
        mFabFavorite.setVisibility(View.VISIBLE);
    }
}
