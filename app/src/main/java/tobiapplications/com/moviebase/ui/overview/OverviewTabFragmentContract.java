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
        void loadMovies(Constants.OverviewType overviewType);

        void requestDownload();

        boolean noMoviesShown();
        boolean hasInternetConnection();
        void isConnectedToInternet(boolean connected);
        void loadMoreMovies();
    }

    interface DatabaseView extends LoaderManager.LoaderCallbacks<Cursor>, OnMovieClickListener {
        void setGridViewAndAdapter();
        void setMovies(ArrayList<MovieOverviewModel> movies);

        void setSeries(ArrayList<MovieOverviewModel> series);

        void showLoading(boolean load);
        void startLoader(Constants.OverviewType overviewType);
        void hideNoFavoriteAvailable();

        void showNoFavoriteAvailable(String text);
    }

    interface DatabasePresenter {
        void createMovieListFromCursor(Cursor data);
        void onDatabaseLoadFinished(Cursor data, Constants.OverviewType overviewType);
    }
}
