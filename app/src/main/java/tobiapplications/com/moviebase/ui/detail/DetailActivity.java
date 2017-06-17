package tobiapplications.com.moviebase.ui.detail;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewCompat;
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
import tobiapplications.com.moviebase.model.detail.MovieDetailResponse;
import tobiapplications.com.moviebase.utils.Constants;
import tobiapplications.com.moviebase.utils.SQLUtils;

public class DetailActivity extends AppCompatActivity implements View.OnClickListener, DetailActivityContract.View {

    private ImageView mDetailMovieBackgroundImage;
    private FloatingActionButton mFabFavorite;
    private Toolbar mToolbar;
    private ProgressBar mImageProgressIndicator;
    private DetailActivityPresenter presenter;
    private AppBarLayout appBarLayout;


    private int movieId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        findMyViews();

        setUpActionBar();

        getMovieId();

        presenter = new DetailActivityPresenter(this, movieId, this);
        presenter.requestSingleMovieDownload();
    }

    @Override
    public void setUpTabFragment(MovieDetailResponse response) {

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        DetailFragment fragment = DetailFragment.newInstance(response);
        transaction.add(R.id.detail_content, fragment);
        transaction.commit();
    }

    @Override
    public void findMyViews() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mDetailMovieBackgroundImage = (ImageView) findViewById(R.id.toolbarBackgroundImage);
        mDetailMovieBackgroundImage.setOnClickListener(this);
        mFabFavorite = (FloatingActionButton) findViewById(R.id.detail_fab_button_favorite);
        mFabFavorite.setOnClickListener(this);
        mImageProgressIndicator = (ProgressBar) findViewById(R.id.detail_background_image_progress_indicator);
        appBarLayout = (AppBarLayout) findViewById(R.id.app_bar_layout);
        appBarLayout.addOnOffsetChangedListener(this);
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
    public void getMovieId() {
        Intent intent = getIntent();
        if (intent != null) {
            movieId = intent.getIntExtra(Constants.CLICKED_MOVIE, -1);
        }
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
        if (moviePath != null) {
            Picasso.with(this).load(moviePath).into(mDetailMovieBackgroundImage);
        } else {
            mDetailMovieBackgroundImage.setImageResource(R.drawable.no_image_available);
        }
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if(menuItem.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return true;
    }


    @Override
    public void markFabAsFavorite() {
        mFabFavorite.setImageResource(R.drawable.fab_heart_fav);
    }

    @Override
    public void unMarkFabFromFavorite() {
        mFabFavorite.setImageResource(R.drawable.fab_heart);
    }

    @Override
    public void showMarkAsFavoriteToast(String movieTitle) {
        Toast.makeText(this, getString(R.string.marked_as_favorite, movieTitle), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showRemovedFromFavoriteToast(String movieTitle) {
        Toast.makeText(this, getString(R.string.unmarked_as_favorite, movieTitle), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setFabButtonVisible() {
        mFabFavorite.setVisibility(View.VISIBLE);
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        presenter.setAppBarOffsetChanged(appBarLayout.getTotalScrollRange(), verticalOffset);
    }

    @Override
    public void animateFabDown(int value) {
        ViewCompat.animate(mFabFavorite).translationY(value).start();
    }

    @Override
    public void animateFabUp(int value) {
        ViewCompat.animate(mFabFavorite).translationY(value).start();
    }
}
