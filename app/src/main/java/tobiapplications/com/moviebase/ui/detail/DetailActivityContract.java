package tobiapplications.com.moviebase.ui.detail;

import android.content.ContentValues;
import android.database.Cursor;
import android.support.design.widget.AppBarLayout;

import tobiapplications.com.moviebase.model.detail.MovieDetailResponse;
import tobiapplications.com.moviebase.model.detail.SeriesDetailResponse;
import tobiapplications.com.moviebase.utils.Constants;

/**
 * Created by Tobias on 13.06.2017.
 */

public interface DetailActivityContract {

    interface View extends AppBarLayout.OnOffsetChangedListener{
        void setUpActionBar();
        void getMovieIdAndType();
        void setUpMovieTabFragment(MovieDetailResponse response);
        void setUpSeriesTabFragment(SeriesDetailResponse clickedSerie);
        void setInformation(String title, String moviePath);
        void markFabAsFavorite();
        void unMarkFabFromFavorite();
        void setFabButtonVisible();
        void animateFabDown(int value);
        void animateFabUp(int value);
        void showMarkAsFavoriteToast(String movieTitle);
        void showRemovedFromFavoriteToast(String movieTitle);
    }

    interface Presenter {
        void requestSingleMovieDownload();
        void displayMovieResponse(MovieDetailResponse detailResponse);
        void displayError(String message);
        void openToolbarImage();
        void handleFabClick();
        void insertIntoDatabase(Constants.OverviewType overviewType);
        void setFabDependingOnFavoriteStatus();
        void setAppBarOffsetChanged(int totalScrollRange, int verticalOffset);
        void displaySeriesResponse(SeriesDetailResponse body);
    }

}
