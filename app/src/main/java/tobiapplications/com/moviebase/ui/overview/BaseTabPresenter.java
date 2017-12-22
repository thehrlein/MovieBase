package tobiapplications.com.moviebase.ui.overview;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;

import java.lang.ref.WeakReference;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;
import tobiapplications.com.moviebase.model.overview.MovieOverviewResponse;
import tobiapplications.com.moviebase.network.DataManager;
import tobiapplications.com.moviebase.utils.Constants;
import tobiapplications.com.moviebase.utils.NetworkUtils;
import tobiapplications.com.moviebase.utils.mvp.BasePresenter;

import static tobiapplications.com.moviebase.utils.GeneralUtils.isMovie;
import static tobiapplications.com.moviebase.utils.GeneralUtils.isSerie;
import static tobiapplications.com.moviebase.utils.GeneralUtils.isPopular;

/**
 * Created by Tobias Hehrlein on 13.10.2017.
 */

public class BaseTabPresenter extends BasePresenter<BaseTabContract.View> implements BaseTabContract.Presenter {

    public int type;
    private WeakReference<OverviewTabContract.View> parent;
    private Context context;
    private int pageToLoadNext = 1;
    private int category;
    private DataManager dataManager;

    public BaseTabPresenter(OverviewTabContract.View parent, Context context, int category) {
        this.parent = new WeakReference<>(parent);
        this.context = context;
        this.category = category;
        this.dataManager = DataManager.getInstance();
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
            if (isAttached()) {
                if (noMoviesShown()) {
                    getView().showLoading(true);
                }
                getView().showNetworkError(false);
                requestDownload();
            }
        } else {
            if (isAttached()) {
                getView().showLoading(false);
                getView().showNetworkError(true);
                new Handler().postDelayed(this::load, 3000);
            }
        }
    }

    @Override
    public boolean noMoviesShown() {
        if (isAttached()) {
            return getView().getCurrentMovieSize() == 0;
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

    private void requestMoviesDownload() {
        if (isPopular(category)) {
            dataManager.requestPopularMovies(pageToLoadNext)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(this::displayPosterItems,
                            throwable -> Timber.d("Error: Overview Popular Movies"));
        } else {
            dataManager.requestTopRatedMovies(pageToLoadNext)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(this::displayPosterItems,
                            throwable -> Timber.d("Error: Overview Top Movies"));
        }
        pageToLoadNext++;
    }

    private void requestSeriesDownload() {
        if (isPopular(category)) {
            dataManager.requestPopularSeries(pageToLoadNext)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(this::displayPosterItems,
                            throwable -> Timber.d("Error: Overview Popular Series"));
        } else {
            dataManager.requestTopRatedSeries(pageToLoadNext)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(this::displayPosterItems,
                            throwable -> Timber.d("Error: Overview Top Series"));
        }
        pageToLoadNext++;
    }

    @Override
    public void onMovieClick(int id) {
        if (isAttached()) {
            getView().startDetailActivity(id, type);
        }
    }

    private void displayPosterItems(MovieOverviewResponse movieOverviewResponse) {
        if (isAttached()) {
            getView().showLoading(false);
            getView().setMovies(movieOverviewResponse.getMovies());
        }
    }

    @Override
    public void loadMore() {
        if (isAttached()) {
            getView().insertLoadingItem();
            requestDownload();
        }
    }
}
