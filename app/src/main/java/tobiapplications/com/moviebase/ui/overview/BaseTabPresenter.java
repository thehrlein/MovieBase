package tobiapplications.com.moviebase.ui.overview;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;

import java.lang.ref.WeakReference;

import timber.log.Timber;
import tobiapplications.com.moviebase.model.overview.MovieOverviewResponse;
import tobiapplications.com.moviebase.network.DataManager;
import tobiapplications.com.moviebase.utils.Constants;
import tobiapplications.com.moviebase.utils.GeneralUtils;
import tobiapplications.com.moviebase.utils.NetworkUtils;
import tobiapplications.com.moviebase.utils.mvp.BasePresenter;

import static tobiapplications.com.moviebase.utils.GeneralUtils.isMovie;
import static tobiapplications.com.moviebase.utils.GeneralUtils.isSerie;
import static tobiapplications.com.moviebase.utils.GeneralUtils.isPopular;

/**
 * Created by Tobias Hehrlein on 13.10.2017.
 */

public class BaseTabPresenter extends BasePresenter<BaseTabContract.View> implements BaseTabContract.Presenter{

    public int type;
    private WeakReference<OverviewTabContract.View> parent;
    private Context context;
    private int pageToLoadNext = 1;
    private int category;

    public BaseTabPresenter(OverviewTabContract.View parent, Context context, int category) {
        this.parent = new WeakReference<>(parent);
        this.context = context;
        this.category = category;
    }

    @Override
    public void parseArguments(Bundle arguments) {
        if (arguments == null) {
            return;
        }

        if (arguments.containsKey(Constants.TYPE)) {
            type = arguments.getInt(Constants.TYPE);
        }
    }

    @Override
    public void getTypeAndLoadItems(Bundle arguments) {
        parseArguments(arguments);
        load();
    }

    @Override
    public void load() {
        if (hasInternetConnection()) {
            if (GeneralUtils.weakReferenceIsValid(parent)) {
                if (noMoviesShown()) {
                    parent.get().showLoading(true);
                }
                parent.get().showNetworkError(false);
                requestDownload();
            }
        } else {
            if (GeneralUtils.weakReferenceIsValid(parent)) {
                parent.get().showLoading(false);
                parent.get().showNetworkError(true);
                new Handler().postDelayed(this::load, 3000);
            }
        }
    }

    @Override
    public boolean noMoviesShown() {
        if (GeneralUtils.weakReferenceIsValid(parent)) {
            return parent.get().getCurrentMovieSize() == 0;
        }
        return true;
    }

    @Override
    public boolean hasInternetConnection() {
        return NetworkUtils.isConnectedToInternet(context);
    }

    @Override
    public void requestDownload() {
        if (isMovie(type)) {
            requestMoviesDownload();
        } else if (isSerie(type)) {
            requestSeriesDownload();
        }
    }

    public void requestMoviesDownload() {
        if (isPopular(category)) {
            DataManager.getInstance().requestPopularMovies(this, pageToLoadNext);
        } else {
            DataManager.getInstance().requestTopRatedMovies(this, pageToLoadNext);
        }
        pageToLoadNext++;
    }

    public void requestSeriesDownload() {
        if (isPopular(category)) {
            DataManager.getInstance().requestPopularSeries(this, pageToLoadNext);
        } else {
            DataManager.getInstance().requestTopRatedSeries(this, pageToLoadNext);
        }
        pageToLoadNext++;
    }

    @Override
    public void onMovieClick(int id) {
        if (GeneralUtils.weakReferenceIsValid(parent)) {
            parent.get().startDetailActivity(id, type);
        }
    }

    @Override
    public void displayError(String message) {
        Timber.d("Error: " + message);
    }

    @Override
    public void displayPosterItems(MovieOverviewResponse movieOverviewResponse) {
        if (GeneralUtils.weakReferenceIsValid(parent)) {
            parent.get().showLoading(false);
            parent.get().setMovies(movieOverviewResponse.getMovies());
        }
    }

    @Override
    public void loadMore() {
        if (GeneralUtils.weakReferenceIsValid(parent)) {
            parent.get().insertLoadingItem();
           requestDownload();
        }
    }
}
