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

public class DetailActivity extends AppCompatActivity implements DetailActivityContract.View {

    private ActivityDetailBinding bind;
    private DetailActivityPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bind = DataBindingUtil.setContentView(this, R.layout.activity_detail);

        init();
        setUpActionBar();
        presenter = new DetailActivityPresenter(this, this, getIntent());
    }

    @Override
    public void setUpMovieTabFragment(MovieDetailResponse clickedMovie, int overviewType) {
        DetailFragment detailFragment = DetailFragment.newInstance(clickedMovie, overviewType);
        showTabFragment(detailFragment);
    }

    @Override
    public void setUpSeriesTabFragment(SeriesDetailResponse clickedSerie, int overviewType) {
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
        bind.toolbarBackgroundImage.setOnClickListener(v -> presenter.openToolbarImage());
        bind.detailFabButtonFavorite.setOnClickListener(v -> presenter.handleFabClick());
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
    public void displayTitle(String title) {
        bind.toolbar.setTitle(title);
        bind.progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showNoPictureAvailable(boolean noPicture) {
        bind.noPictureAvailable.setVisibility(noPicture ? View.VISIBLE : View.GONE);
    }

    @Override
    public void showPosterImage(String imageUrl) {
        Picasso.with(this).load(imageUrl).into(bind.toolbarBackgroundImage);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        presenter.onMenuItemClicked(menuItem);
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
