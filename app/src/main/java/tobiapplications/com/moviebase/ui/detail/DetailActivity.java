package tobiapplications.com.moviebase.ui.detail;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import tobiapplications.com.moviebase.R;
import tobiapplications.com.moviebase.databinding.ActivityDetailBinding;
import tobiapplications.com.moviebase.model.detail.MovieDetailResponse;
import tobiapplications.com.moviebase.model.detail.SeriesDetailResponse;
import tobiapplications.com.moviebase.utils.Constants;

public class DetailActivity extends AppCompatActivity implements View.OnClickListener, DetailActivityContract.View {

    private ActivityDetailBinding bind;
    private DetailActivityPresenter presenter;
    private int movieId;
    private Constants.OverviewType overviewType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bind = DataBindingUtil.setContentView(this, R.layout.activity_detail);

        init();

        setUpActionBar();

        getMovieIdAndType();

        presenter = new DetailActivityPresenter(this, movieId, this, overviewType);
    }

    @Override
    public void setUpMovieTabFragment(MovieDetailResponse clickedMovie) {
        DetailFragment detailFragment = DetailFragment.newInstance(clickedMovie, overviewType);
        showTabFragment(detailFragment);
    }

    @Override
    public void setUpSeriesTabFragment(SeriesDetailResponse clickedSerie) {
        DetailFragment detailFragment = DetailFragment.newInstance(clickedSerie, overviewType);
        showTabFragment(detailFragment);
    }

    private void showTabFragment(DetailFragment detailFragment) {
        bind.progressBarContent.setVisibility(View.GONE);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        transaction.add(R.id.detail_content, detailFragment);
        transaction.commit();
    }

    private void init() {
        bind.toolbarBackgroundImage.setOnClickListener(this);
        bind.detailFabButtonFavorite.setOnClickListener(this);
        bind.appBarLayout.addOnOffsetChangedListener(this);
    }

    @Override
    public void setUpActionBar() {
        setSupportActionBar(bind.toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public void getMovieIdAndType() {
        Intent intent = getIntent();
        if (intent != null) {
            movieId = intent.getIntExtra(Constants.CLICKED_MOVIE, -1);
            overviewType = (Constants.OverviewType) intent.getSerializableExtra(Constants.OVERVIEW_TYPE);
        }
    }

    @Override
    public void onClick(View v) {
        if (v == bind.detailFabButtonFavorite) {
            presenter.handleFabClick();
        } else if (v == bind.toolbarBackgroundImage) {
            presenter.openToolbarImage();
        }
    }

    @Override
    public void setInformation(String title, String moviePath) {
        bind.toolbar.setTitle(title);
        bind.progressBar.setVisibility(View.GONE);
        if (moviePath != null) {
            Picasso.with(this).load(moviePath).into(bind.toolbarBackgroundImage);
            bind.noPictureAvailable.setVisibility(View.GONE);
        } else {
            bind.noPictureAvailable.setVisibility(View.VISIBLE);
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
        bind.detailFabButtonFavorite.setImageResource(R.drawable.fab_heart_fav);
    }

    @Override
    public void unMarkFabFromFavorite() {
        bind.detailFabButtonFavorite.setImageResource(R.drawable.fab_heart);
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
        bind.detailFabButtonFavorite.setVisibility(View.VISIBLE);
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        presenter.setAppBarOffsetChanged(appBarLayout.getTotalScrollRange(), verticalOffset);
    }

    @Override
    public void animateFabDown(int value) {
        ViewCompat.animate(bind.detailFabButtonFavorite).translationY(value).start();
    }

    @Override
    public void animateFabUp(int value) {
        ViewCompat.animate(bind.detailFabButtonFavorite).translationY(value).start();
    }
}
