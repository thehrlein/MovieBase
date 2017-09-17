package tobiapplications.com.moviebase.ui.detail;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.stfalcon.frescoimageviewer.ImageViewer;

import tobiapplications.com.moviebase.model.detail.MovieDetailResponse;
import tobiapplications.com.moviebase.model.detail.SeriesDetailResponse;
import tobiapplications.com.moviebase.network.DataManager;
import tobiapplications.com.moviebase.utils.Constants;
import tobiapplications.com.moviebase.utils.NetworkUtils;
import tobiapplications.com.moviebase.utils.SQLUtils;

/**
 * Created by Tobias on 11.06.2017.
 */

public class DetailActivityPresenter implements DetailActivityContract.Presenter {

    private DetailActivity parent;
    private int movieId;
    private MovieDetailResponse clickedMovie;
    private SeriesDetailResponse clickedSerie;
    private boolean isMarkedAsFavorite = false;
    private static final int PERCENTAGE_TO_SHOW_IMAGE = 60;
    private int mMaxScrollSize;
    private boolean mIsImageHidden;
    private Context context;
    private Constants.OverviewType overviewType;

    public DetailActivityPresenter(DetailActivity activity, int movieId, Context context, Constants.OverviewType overviewType) {
        this.parent = activity;
        this.movieId = movieId;
        this.context = context;
        this.overviewType = overviewType;

        requestDownload();

    }

    private void requestDownload() {
        if (overviewType == Constants.OverviewType.MOVIES) {
            requestSingleMovieDownload();
        } else {
            requestSingleSerieDownload();
        }
    }

    private void requestSingleSerieDownload() {
        DataManager.getInstance().requestSingleSerie(this, movieId);
    }

    @Override
    public void requestSingleMovieDownload() {
        DataManager.getInstance().requestSingleMovie(this, movieId);
    }

    @Override
    public void displayMovieResponse(MovieDetailResponse detailResponse) {
        clickedMovie = detailResponse;
        parent.setInformation(clickedMovie.getTitle(), NetworkUtils.getFullImageUrlHigh(clickedMovie.getBackgroundImagePath()));
        parent.setUpMovieTabFragment(clickedMovie);
        setFabDependingOnFavoriteStatus();
    }

    @Override
    public void displaySeriesResponse(SeriesDetailResponse body) {
        clickedSerie = body;
        parent.setInformation(clickedSerie.getName(), NetworkUtils.getFullImageUrlHigh(clickedSerie.getBackgroundImagePath()));
        parent.setUpSeriesTabFragment(clickedSerie);
        setFabDependingOnFavoriteStatus();
    }

    @Override
    public void displayError() {
        Log.d("DetailActivityPresenter", "ERROR");
        Toast.makeText(parent, "Error DetailActivityPresenter", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void openToolbarImage() {
        if (clickedMovie != null && clickedMovie.getBackgroundImagePath() != null){
            new ImageViewer.Builder(parent, new String[]{NetworkUtils.getFullImageUrlHigh(clickedMovie.getBackgroundImagePath())})
                    .setStartPosition(0)
                    .show();
        }
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
        if (clickedMovie != null) {
            if (!isMarkedAsFavorite) {
                isMarkedAsFavorite = true;
                parent.markFabAsFavorite();
                parent.showMarkAsFavoriteToast(clickedMovie.getTitle());
                insertIntoDatabase(overviewType);
            } else {
                isMarkedAsFavorite = false;
                parent.unMarkFabFromFavorite();
                parent.showRemovedFromFavoriteToast(clickedMovie.getTitle());

                SQLUtils.deleteCurrentMovieFromFavoriteDatabase(context, clickedMovie.getId());
            }
        }
    }

    private void handleFabClickForSerie() {
        if (clickedSerie != null) {
            if (!isMarkedAsFavorite) {
                isMarkedAsFavorite = true;
                parent.markFabAsFavorite();
                parent.showMarkAsFavoriteToast(clickedSerie.getName());
                insertIntoDatabase(overviewType);
            } else {
                isMarkedAsFavorite = false;
                parent.unMarkFabFromFavorite();
                parent.showRemovedFromFavoriteToast(clickedSerie.getName());

                SQLUtils.deleteCurrentMovieFromFavoriteDatabase(context, clickedSerie.getId());
            }
        }
    }

    @Override
    public void insertIntoDatabase(Constants.OverviewType overviewType) {
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
            isMarkedAsFavorite = SQLUtils.checkIfMovieIsMarkedAsFavorite(context, movieId);
        } else {
            isMarkedAsFavorite = SQLUtils.checkIfSerieIsMarkedAsFavorite(context, movieId);
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
}
