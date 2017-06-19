package tobiapplications.com.moviebase.ui.detail;

import android.content.ContentValues;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.stfalcon.frescoimageviewer.ImageViewer;

import java.util.ArrayList;

import tobiapplications.com.moviebase.database.MoviesContract;
import tobiapplications.com.moviebase.model.detail.MovieDetailResponse;
import tobiapplications.com.moviebase.model.detail.Genre;
import tobiapplications.com.moviebase.network.DataManager;
import tobiapplications.com.moviebase.utils.NetworkUtils;
import tobiapplications.com.moviebase.utils.SQLUtils;

/**
 * Created by Tobias on 11.06.2017.
 */

public class DetailActivityPresenter implements DetailActivityContract.Presenter {

    private DetailActivity parent;
    private int movieId;
    private MovieDetailResponse clickedMovie;
    private boolean isMarkedAsFavorite = false;
    private static final int PERCENTAGE_TO_SHOW_IMAGE = 60;
    private int mMaxScrollSize;
    private boolean mIsImageHidden;
    private Context context;

    public DetailActivityPresenter(DetailActivity activity, int movieId, Context context) {
        this.parent = activity;
        this.movieId = movieId;
        this.context = context;

        requestSingleMovieDownload();
    }

    @Override
    public void requestSingleMovieDownload() {
        DataManager.getInstance().requestSingleMovie(this, movieId);
    }

    @Override
    public void displayMovieResponse(MovieDetailResponse detailResponse) {
        clickedMovie = detailResponse;
        parent.setMovieInformation(clickedMovie.getTitle(), NetworkUtils.getFullImageUrlHigh(clickedMovie.getBackgroundImagePath()));
        parent.setUpTabFragment(clickedMovie);
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
        if (clickedMovie != null) {
            if (!isMarkedAsFavorite) {
                isMarkedAsFavorite = true;
                parent.markFabAsFavorite();
                parent.showMarkAsFavoriteToast(clickedMovie.getTitle());
                insertCurrentMovieToFavoriteDatabase();
            } else {
                isMarkedAsFavorite = false;
                parent.unMarkFabFromFavorite();
                parent.showRemovedFromFavoriteToast(clickedMovie.getTitle());

                SQLUtils.deleteCurrentMovieFromFavoriteDatabase(context, clickedMovie.getId());
            }
        }
    }

    @Override
    public void insertCurrentMovieToFavoriteDatabase() {
        if (clickedMovie != null) {
            SQLUtils.insertIntoDatabase(context, clickedMovie);
        }
    }

    @Override
    public void setFabDependingOnFavoriteStatus() {
        isMarkedAsFavorite = SQLUtils.checkIfMovieIsMarkedAsFavorite(context, movieId);

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
