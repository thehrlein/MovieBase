package tobiapplications.com.moviebase.ui.overview;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;

import java.util.ArrayList;

import tobiapplications.com.moviebase.listener.OnMovieClickListener;
import tobiapplications.com.moviebase.model.overview.PosterOverviewItem;
import tobiapplications.com.moviebase.utils.mvp.BaseMvpPresenter;
import tobiapplications.com.moviebase.utils.mvp.BaseView;

/**
 * Created by Tobias on 09.06.2017.
 */

public interface OverviewTabContract {

    interface View {
        void showNetworkError(boolean noNetwork);
        void setMovies(ArrayList<PosterOverviewItem> movies);
        void insertLoadingItem();
        void showLoading(boolean load);
        int getCurrentMovieSize();
        void startDetailActivity(int id, int type);
    }

    interface Presenter {

    }

    interface DatabaseView extends BaseView, LoaderManager.LoaderCallbacks<Cursor>, OnMovieClickListener {
        void setGridViewAndAdapter();
        void setPosterItems(ArrayList<PosterOverviewItem> movies);
        void showLoading(boolean load);
        Loader<Cursor> onCreateMovieLoader();
        Loader<Cursor> onCreateSerieLoader();
        void startLoader(int overviewType);
        void hideNoFavoriteAvailable();
        void showNoFavoriteAvailable(String text);
        void startDetailActivity(int id, int type);
    }

    interface DatabasePresenter extends BaseMvpPresenter<DatabaseView> {
        void createPosterItemsFromCursor(Cursor data);
        void onDatabaseLoadFinished(Cursor data);
        void parseType(Bundle arguments);
        void onMovieClick(int id);
        void onResume();
        Loader<Cursor> onCreateLoader(int id);
    }
}
