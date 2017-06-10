package tobiapplications.com.moviebase.ui.movie_overview;

import android.content.Context;
import android.os.Handler;
import android.util.Log;

import tobiapplications.com.moviebase.model.MovieResponse;
import tobiapplications.com.moviebase.network.DataManager;
import tobiapplications.com.moviebase.utils.Helper;

/**
 * Created by Tobias on 09.06.2017.
 */

public class PopularMoviePresenter implements MoviePresenter {

    private MovieOverview parent;
    private Context context;

    public PopularMoviePresenter(MovieOverview parent, Context context) {
        this.parent = parent;
        this.context = context;
     //   parent.makeToast("YOLO SWAG");
    }

    @Override
    public void loadMovies() {
        if (hasInternetConnection()) {
            requestMovieDownload();
        } else {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    loadMovies();
                }
            }, 3000);
        }
    }

    @Override
    public boolean hasInternetConnection() {
        return Helper.isConnectedToInternet(context);
    }

    @Override
    public void isConnectedToInternet(boolean connected) {
        parent.showNoNetworkError(connected);
    }

    @Override
    public void requestMovieDownload() {
        Log.d("Pop Presenter", "requestMovieDownload");
        DataManager.getInstance().requestMovies(this);
    }

    @Override
    public void displayMovies(MovieResponse movieResponse) {
        parent.showLoading(false);
        parent.setMovies(movieResponse.getMovies());
    }
}
