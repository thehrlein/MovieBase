package tobiapplications.com.moviebase.ui.movie_overview;

import android.content.Context;
import android.os.Handler;
import android.util.Log;

import tobiapplications.com.moviebase.network.DataController;
import tobiapplications.com.moviebase.utils.Helper;

/**
 * Created by Tobias on 09.06.2017.
 */

public class PopularMoviePresenter {

    private MovieOverview parent;
    private Context context;

    public PopularMoviePresenter(MovieOverview parent, Context context) {
        this.parent = parent;
        this.context = context;
        parent.makeToast("YOLO SWAG");
    }

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

    private boolean hasInternetConnection() {
        return Helper.isConnectedToInternet(context);
    }

    public void isConnectedToInternet(boolean connected) {
        parent.showNoNetworkError(connected);
    }

    private void requestMovieDownload() {
        Log.d("Pop Presenter", "requestMovieDownload");
        DataController dataController = DataController.newInstance();
        dataController.requestMovies();
    }


}
