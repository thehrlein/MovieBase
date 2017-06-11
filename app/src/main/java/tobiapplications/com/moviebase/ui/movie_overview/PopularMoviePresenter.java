package tobiapplications.com.moviebase.ui.movie_overview;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import tobiapplications.com.moviebase.model.MovieOverviewResponse;
import tobiapplications.com.moviebase.network.DataManager;
import tobiapplications.com.moviebase.utils.MovieDetailUtils;
import tobiapplications.com.moviebase.utils.NetworkUtils;

/**
 * Created by Tobias on 09.06.2017.
 */

public class PopularMoviePresenter implements MoviePresenter{

    private MovieOverview parent;
    private Context context;
    private int pageToLoadNext = 1;

    public PopularMoviePresenter(MovieOverview parent, Context context) {
        this.parent = parent;
        this.context = context;
    }

    @Override
    public void loadMovies() {
        if (hasInternetConnection()) {
            if (noMoviesShown())
            parent.showLoading(true);
            parent.showNetworkError(false);
            requestMovieDownload();
        } else {
            parent.showLoading(false);
            parent.showNetworkError(true);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    loadMovies();
                }
            }, 3000);
        }
    }

    private boolean noMoviesShown() {
        return parent.getCurrentMovieSize() == 0;
    }

    @Override
    public boolean hasInternetConnection() {
        return NetworkUtils.isConnectedToInternet(context);
    }

    @Override
    public void isConnectedToInternet(boolean connected) {
        parent.showNetworkError(connected);
    }

    @Override
    public void requestMovieDownload() {
        Log.d("Pop Presenter", "requestSingleMovieDownload");
        DataManager.getInstance().requestPopularMovies(this, pageToLoadNext);
        pageToLoadNext++;
    }

    @Override
    public void displayMovies(MovieOverviewResponse movieOverviewResponse) {
        parent.showLoading(false);
        parent.setMovies(movieOverviewResponse.getMovies());
    }

    public void loadMoreMovies() {
        requestMovieDownload();
    }

    @Override
    public void displayError() {
        Toast.makeText(context, "Failed to load movies, please check network connection", Toast.LENGTH_SHORT).show();
        // TODO AlertDialog or something like that
    }
}
