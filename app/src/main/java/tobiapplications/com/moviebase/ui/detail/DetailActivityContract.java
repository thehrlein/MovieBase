package tobiapplications.com.moviebase.ui.detail;

import android.content.ContentValues;
import android.database.Cursor;
import android.support.design.widget.AppBarLayout;

import tobiapplications.com.moviebase.model.detail.MovieDetailResponse;

/**
 * Created by Tobias on 13.06.2017.
 */

public interface DetailActivityContract {

    interface View extends AppBarLayout.OnOffsetChangedListener{
        void setUpActionBar();
        void findMyViews();
        void getMovieId();
        void setUpTabFragment(MovieDetailResponse response);
        void setMovieInformation(String title, String moviePath);
        void markFabAsFavorite();
        void unMarkFabFromFavorite();
        void setFabButtonVisible();
        void animateFabDown(int value);
        void animateFabUp(int value);
        void showMarkAsFavoriteToast();
        void showRemovedFromFavoriteToast();
    }

    interface Presenter {
        void requestSingleMovieDownload();
        void displayMovieResponse(MovieDetailResponse detailResponse);
        void displayError();
        void openToolbarImage();
        void handleFabClick();
        void insertCurrentMovieToFavoriteDatabase();
        void setFabDependingOnFavoriteStatus();
        void setAppBarOffsetChanged(int totalScrollRange, int verticalOffset);
    }

}
