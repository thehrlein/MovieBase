package tobiapplications.com.moviebase.ui.overview;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import timber.log.Timber;
import tobiapplications.com.moviebase.model.overview.MovieOverviewResponse;
import tobiapplications.com.moviebase.network.DataManager;
import tobiapplications.com.moviebase.utils.Constants;
import tobiapplications.com.moviebase.utils.NetworkUtils;

/**
 * Created by Tobias on 09.06.2017.
 */

public class PopularPresenter implements OverviewTabFragmentContract.Presenter {

    private OverviewTabFragmentContract.View parent;
    private Context context;
    private int pageToLoadNext = 1;
    private Constants.OverviewType overviewType;

    public PopularPresenter(OverviewTabFragmentContract.View parent, Context context) {
        this.parent = parent;
        this.context = context;
    }

    @Override
    public void load(Constants.OverviewType overviewType) {
        this.overviewType = overviewType;
        if (hasInternetConnection()) {
            if (noMoviesShown()) {
                parent.showLoading(true);
            }
            parent.showNetworkError(false);
            requestDownload();
        } else {
            parent.showLoading(false);
            parent.showNetworkError(true);
            new Handler().postDelayed(()-> load(overviewType), 3000);
        }
    }

    @Override
    public void requestDownload() {
        if (overviewType == Constants.OverviewType.MOVIES) {
            requestMoviesDownload();
        } else if (overviewType == Constants.OverviewType.SERIES) {
            requestSeriesDownload();
        } else {
            Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
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
    public void requestMoviesDownload() {
        DataManager.getInstance().requestPopularMovies(this, pageToLoadNext);
        pageToLoadNext++;
    }

    @Override
    public void requestSeriesDownload() {
        DataManager.getInstance().requestPopularSeries(this, pageToLoadNext);
        pageToLoadNext++;
    }

    @Override
    public void displayPosterItems(MovieOverviewResponse movieOverviewResponse) {
        parent.showLoading(false);
        parent.setMovies(movieOverviewResponse.getMovies());
    }

    @Override
    public void loadMore() {
        parent.insertLoadingItem();

        requestDownload();
    }

    @Override
    public void displayError(String message) {
        Timber.d("Error: " +  message);
    }
}
