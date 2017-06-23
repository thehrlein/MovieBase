package tobiapplications.com.moviebase.ui.overview;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import tobiapplications.com.moviebase.model.overview.MovieOverviewResponse;
import tobiapplications.com.moviebase.network.DataManager;
import tobiapplications.com.moviebase.utils.NetworkUtils;

/**
 * Created by Tobias on 11.06.2017.
 */

public class TopRatedPresenter implements OverviewFragmentContract.Presenter {

    private OverviewFragmentContract.View parent;
    private Context context;
    private int pageToLoadNext = 1;

    public TopRatedPresenter(OverviewFragmentContract.View parent, Context context) {
        this.parent = parent;
        this.context = context;
    }

    @Override
    public void loadMovies() {
        if (hasInternetConnection()) {
            if (noMoviesShown()) {
                parent.showLoading(true);
            }
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

    @Override
    public boolean noMoviesShown() {
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
        Log.d("Top Presenter", "requestSingleMovieDownload");
        DataManager.getInstance().requestTopRatedMovies(this, pageToLoadNext);
        pageToLoadNext++;
    }

    @Override
    public void displayMovies(MovieOverviewResponse movieOverviewResponse) {
        parent.showLoading(false);
        parent.setMovies(movieOverviewResponse.getMovies());
    }

    @Override
    public void loadMoreMovies() {
        parent.insertLoadingItem();
        requestMovieDownload();
    }

    @Override
    public void displayError(String message) {
        Toast.makeText(context, "Error " + message, Toast.LENGTH_LONG).show();
        // TODO AlertDialog or something like that
    }
}
