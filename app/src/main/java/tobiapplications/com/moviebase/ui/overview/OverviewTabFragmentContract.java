package tobiapplications.com.moviebase.ui.overview;

import android.database.Cursor;
import android.support.v4.app.LoaderManager;

import java.util.ArrayList;

import tobiapplications.com.moviebase.listener.OnLoadMoreMoviesListener;
import tobiapplications.com.moviebase.listener.OnMovieClickListener;
import tobiapplications.com.moviebase.listener.OnOverviewMovieLoadListener;
import tobiapplications.com.moviebase.model.overview.MovieOverviewModel;
import tobiapplications.com.moviebase.utils.Constants;

/**
 * Created by Tobias on 09.06.2017.
 */

public interface OverviewTabFragmentContract {

    interface View extends OnLoadMoreMoviesListener, OnMovieClickListener {
        void setGridViewAndAdapter();
        void showNetworkError(boolean noNetwork);
        void setMovies(ArrayList<MovieOverviewModel> movies);
        void insertLoadingItem();
        void showLoading(boolean load);
        int getCurrentMovieSize();
    }

    interface Presenter extends OnOverviewMovieLoadListener {
        void load(Constants.OverviewType overviewType);
        void requestDownload();
        boolean noMoviesShown();
        boolean hasInternetConnection();
        void isConnectedToInternet(boolean connected);
        void loadMore();
    }

    interface DatabaseView extends LoaderManager.LoaderCallbacks<Cursor>, OnMovieClickListener {
        void setGridViewAndAdapter();
        void setPosterItems(ArrayList<MovieOverviewModel> movies);
        void showLoading(boolean load);
        void startLoader(Constants.OverviewType overviewType);
        void hideNoFavoriteAvailable();
        void showNoFavoriteAvailable(String text);
    }

    interface DatabasePresenter {
        void createPosterItemsFromCursor(Cursor data);
        void onDatabaseLoadFinished(Cursor data);
    }
}
