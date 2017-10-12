package tobiapplications.com.moviebase.ui.detail;

import android.content.Context;
import android.content.Intent;
import android.view.MenuItem;

import com.stfalcon.frescoimageviewer.ImageViewer;

import timber.log.Timber;
import tobiapplications.com.moviebase.model.detail.MovieDetailResponse;
import tobiapplications.com.moviebase.model.detail.SeriesDetailResponse;
import tobiapplications.com.moviebase.network.DataManager;
import tobiapplications.com.moviebase.utils.Constants;
import tobiapplications.com.moviebase.utils.NetworkUtils;
import tobiapplications.com.moviebase.utils.SQLUtils;
import tobiapplications.com.moviebase.utils.StringUtils;

/**
 * Created by Tobias on 11.06.2017.
 */

public class DetailActivityPresenter implements DetailActivityContract.Presenter {

    private DetailActivity parent;
    private int id;
    private MovieDetailResponse clickedMovie;
    private SeriesDetailResponse clickedSerie;
    private boolean isMarkedAsFavorite = false;
    private static final int PERCENTAGE_TO_SHOW_IMAGE = 60;
    private int mMaxScrollSize;
    private boolean mIsImageHidden;
    private Context context;
    private int overviewType;

    public DetailActivityPresenter(DetailActivity detailActivity, Context context, Intent intent) {
        this.parent = detailActivity;
        this.context = context;
        parseIntent(intent);
        requestDownload();
    }

    private void parseIntent(Intent intent) {
        if (intent == null) {
            return;
        }

        id = intent.getIntExtra(Constants.CLICKED_MOVIE, -1);
        overviewType = intent.getIntExtra(Constants.OVERVIEW_TYPE, -1);
    }

    private void requestDownload() {
        if (overviewType == Constants.OverviewType.MOVIES) {
            requestSingleMovieDownload();
        } else {
            requestSingleSerieDownload();
        }
    }

    private void requestSingleSerieDownload() {
        DataManager.getInstance().requestSingleSerie(this, id);
    }

    @Override
    public void requestSingleMovieDownload() {
        DataManager.getInstance().requestSingleMovie(this, id);
    }

    @Override
    public void displayMovieResponse(MovieDetailResponse detailResponse) {
        if (detailResponse == null) {
            return;
        }

        clickedMovie = detailResponse;
        parent.displayTitle(clickedMovie.getTitle());
        String imageUrl = NetworkUtils.getFullImageUrlHigh(clickedMovie.getBackgroundImagePath());
        if (StringUtils.nullOrEmpty(imageUrl)) {
            parent.showNoPictureAvailable(true);
        } else {
            parent.showNoPictureAvailable(false);
            parent.showPosterImage(imageUrl);
        }
        parent.setUpMovieTabFragment(clickedMovie, overviewType);
        setFabDependingOnFavoriteStatus();
    }

    @Override
    public void displaySeriesResponse(SeriesDetailResponse body) {
        if (body == null) {
            return;
        }

        clickedSerie = body;
        parent.displayTitle(clickedSerie.getName());
        String imageUrl = NetworkUtils.getFullImageUrlHigh(clickedSerie.getBackgroundImage());
        if (StringUtils.nullOrEmpty(imageUrl)) {
            parent.showNoPictureAvailable(true);
        } else {
            parent.showNoPictureAvailable(false);
            parent.showPosterImage(imageUrl);
        }
        parent.setUpSeriesTabFragment(clickedSerie, overviewType);
        setFabDependingOnFavoriteStatus();
    }

    @Override
    public void displayError(String message) {
        Timber.d("Error: " + message);
    }

    @Override
    public void openToolbarImage() {
        String imageUrl;
        if (overviewType == Constants.OverviewType.MOVIES && clickedMovie != null
                && !StringUtils.nullOrEmpty(clickedMovie.getBackgroundImagePath())) {
            imageUrl = clickedMovie.getBackgroundImagePath();
        } else if (overviewType == Constants.OverviewType.SERIES && clickedSerie != null
                && !StringUtils.nullOrEmpty(clickedSerie.getBackgroundImage())){
            imageUrl = clickedSerie.getBackgroundImage();
        } else {
            return;
        }

        new ImageViewer.Builder(parent, new String[]{NetworkUtils.getFullImageUrlHigh(imageUrl)})
                .setStartPosition(0)
                .show();
    }

    @Override
    public void handleFabClick() {
        if (overviewType == Constants.OverviewType.MOVIES) {
            handleFabClickForMovie();
        } else {
            handleFabClickForSerie();
        }
    }

    private void handleFabClickForMovie() {
        if (clickedMovie == null) {
            return;
        }

        if (!isMarkedAsFavorite) {
            isMarkedAsFavorite = true;
            markAsFavorite(clickedMovie.getTitle());
        } else {
            isMarkedAsFavorite = false;
            unmarkFromFavorite(clickedMovie.getTitle(), clickedMovie.getId());
        }
    }

    private void markAsFavorite(String message) {
        parent.markFabAsFavorite();
        parent.showMarkAsFavoriteToast(message);
        insertIntoDatabase(overviewType);
    }

    private void unmarkFromFavorite(String message, int id) {
        parent.unMarkFabFromFavorite();
        parent.showRemovedFromFavoriteToast(message);

        SQLUtils.deleteFromDataBase(context, id, overviewType);
    }

    private void handleFabClickForSerie() {
        if (clickedSerie == null) {
            return;
        }

        if (!isMarkedAsFavorite) {
            isMarkedAsFavorite = true;
            markAsFavorite(clickedSerie.getName());

        } else {
            isMarkedAsFavorite = false;
            unmarkFromFavorite(clickedSerie.getName(), clickedSerie.getId());
        }
    }

    @Override
    public void insertIntoDatabase(int overviewType) {
        if (overviewType == Constants.OverviewType.MOVIES) {
            if (clickedMovie != null) {
                SQLUtils.insertMovieIntoDatabase(context, clickedMovie);
            }
        } else {
            if (clickedSerie != null) {
                SQLUtils.insertSerieIntoDatabase(context, clickedSerie);
            }
        }
    }

    @Override
    public void setFabDependingOnFavoriteStatus() {
        if (overviewType == Constants.OverviewType.MOVIES) {
            isMarkedAsFavorite = SQLUtils.checkIfMovieIsMarkedAsFavorite(context, id);
        } else {
            isMarkedAsFavorite = SQLUtils.checkIfSerieIsMarkedAsFavorite(context, id);
        }

        parent.setFabButtonVisible();
        if (isMarkedAsFavorite) {
            parent.markFabAsFavorite();
        } else {
            parent.unMarkFabFromFavorite();
        }
    }

    @Override
    public void setAppBarOffsetChanged(int totalScrollRange, int verticalOffset) {
        if (mMaxScrollSize == 0) {
            mMaxScrollSize = totalScrollRange;
        }

        int currentScrollPercentage = (Math.abs(verticalOffset)) * 100
                / mMaxScrollSize;

        if (currentScrollPercentage >= PERCENTAGE_TO_SHOW_IMAGE) {
            if (!mIsImageHidden) {
                mIsImageHidden = true;
                parent.animateFabDown(100);
            }
        }
        if (currentScrollPercentage < PERCENTAGE_TO_SHOW_IMAGE) {
            if (mIsImageHidden) {
                mIsImageHidden = false;
                parent.animateFabUp(0);
            }
        }
    }

    @Override
    public void onMenuItemClicked(MenuItem menuItem) {
        if(menuItem.getItemId() == android.R.id.home) {
            parent.onBackPressed();
        }
    }
}
