package tobiapplications.com.moviebase.ui.detail;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.core.view.ViewCompat;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.bumptech.glide.Glide;
import com.crashlytics.android.answers.Answers;
import com.crashlytics.android.answers.ContentViewEvent;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.snackbar.Snackbar;

import tobiapplications.com.moviebase.R;
import tobiapplications.com.moviebase.databinding.ActivityDetailBinding;
import tobiapplications.com.moviebase.model.detail.MovieDetailResponse;
import tobiapplications.com.moviebase.model.detail.SeriesDetailResponse;
import tobiapplications.com.moviebase.utils.GeneralUtils;

public class DetailActivity extends AppCompatActivity implements DetailActivityContract.View {

    private ActivityDetailBinding bind;
    private DetailActivityPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bind = DataBindingUtil.setContentView(this, R.layout.activity_detail);

        trackViewOpening(getString(R.string.detail_fragment_identifier));

        init();
        setUpActionBar();
        presenter = new DetailActivityPresenter(this, this, getIntent());
        presenter.attach(this);
    }

    private void trackViewOpening(String tag) {
        Answers.getInstance().logContentView(new ContentViewEvent()
                .putContentName(tag));
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
        Glide.with(this).load(imageUrl).into(bind.toolbarBackgroundImage);
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
    public void showMarkAsFavorite(String movieTitle) {
        Snackbar.make(bind.appBarLayout, getString(R.string.marked_as_favorite, movieTitle), Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void showRemovedFromFavorite(String movieTitle) {
        Snackbar.make(bind.appBarLayout, getString(R.string.unmarked_as_favorite, movieTitle), Snackbar.LENGTH_LONG).show();
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
    public void animateFabDown() {
        int translation = GeneralUtils.pxFromDp(this, 35);
        ViewCompat.animate(bind.detailFabButtonFavorite).translationY(translation).start();
    }

    @Override
    public void animateFabUp() {
        ViewCompat.animate(bind.detailFabButtonFavorite).translationY(0).start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (presenter != null) {
            presenter.detach();
        }
    }
}
