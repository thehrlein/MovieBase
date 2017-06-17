package tobiapplications.com.moviebase.ui.detail;

import android.content.ContentValues;
import android.database.Cursor;
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

    public DetailActivityPresenter(DetailActivity activity, int movieId) {
        this.parent = activity;
        this.movieId = movieId;
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
        checkIfMovieIsMarkedAsFavorite();
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
                parent.showMarkAsFavoriteToast();
                insertCurrentMovieToFavoriteDatabase();
            } else {
                isMarkedAsFavorite = false;
                parent.unMarkFabFromFavorite();
                parent.showRemovedFromFavoriteToast();
                parent.deleteCurrentMovieFromFavoriteDatabase(clickedMovie.getId());
            }
        }
    }

    @Override
    public void insertCurrentMovieToFavoriteDatabase() {
        if (clickedMovie != null) {
            String genresString = "";
            ArrayList<Genre> genreArrayList = clickedMovie.getGenres();

            for (int i = 0; i < genreArrayList.size(); i++) {
                if (i == 0) {
                    genresString = genresString + genreArrayList.get(i).getId();
                } else {
                    genresString = genresString + "-" + genreArrayList.get(i).getId();
                }
            }

            ContentValues values = new ContentValues();
            values.put(MoviesContract.MovieEntry.COLUMN_ID, clickedMovie.getId());
            values.put(MoviesContract.MovieEntry.COLUMN_TITLE, clickedMovie.getTitle());
            values.put(MoviesContract.MovieEntry.COLUMN_TITLE_IMAGE_PATH, clickedMovie.getTitleImagePath());
            values.put(MoviesContract.MovieEntry.COLUMN_BACKDROP_IMAGE_PATH, clickedMovie.getBackgroundImagePath());
            values.put(MoviesContract.MovieEntry.COLUMN_YEAR, clickedMovie.getReleaseDate());
            values.put(MoviesContract.MovieEntry.COLUMN_RATING, clickedMovie.getVoteAverage());
            values.put(MoviesContract.MovieEntry.COLUMN_DESCRIPTION, clickedMovie.getDescription());
            values.put(MoviesContract.MovieEntry.COLUMN_GENRES, genresString);
            values.put(MoviesContract.MovieEntry.COLUMN_ADULT, clickedMovie.isAdult() ? "yes" : "no");

            parent.insertMovieIntoDatabase(values);
        }
    }

    @Override
    public void checkIfMovieIsMarkedAsFavorite() {
        if (clickedMovie != null) {
            parent.setFabButtonVisible();
            Cursor cursor = parent.getAllFavoriteMovies();

            if (cursor.getCount() == 0) {
                return;
            }

            int mDetailMovieId = clickedMovie.getId();

            while(cursor.moveToNext()) {
                int movieId = cursor.getInt(SQLUtils.INDEX_COLUMN_ID);
                if (mDetailMovieId == movieId) {
                    isMarkedAsFavorite = true;
                }
            }

            if (isMarkedAsFavorite) {
                parent.markFabAsFavorite();
            } else {
                parent.unMarkFabFromFavorite();
            }
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
                //  ViewCompat.animate(mFabFavorite).scaleY(0).scaleX(0).start();
                parent.animateFabDown(100);
            }
        }
        if (currentScrollPercentage < PERCENTAGE_TO_SHOW_IMAGE) {
            if (mIsImageHidden) {
                mIsImageHidden = false;
                //  ViewCompat.animate(mFabFavorite).scaleY(1).scaleX(1).start();
                parent.animateFabUp(0);
            }
        }
    }
}
