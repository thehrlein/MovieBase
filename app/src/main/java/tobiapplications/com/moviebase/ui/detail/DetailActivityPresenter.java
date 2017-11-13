package tobiapplications.com.moviebase.ui.detail;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.view.MenuItem;

import com.stfalcon.frescoimageviewer.ImageViewer;

import java.lang.ref.WeakReference;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;
import tobiapplications.com.moviebase.R;
import tobiapplications.com.moviebase.listener.DialogBuilderListener;
import tobiapplications.com.moviebase.model.detail.MovieDetailResponse;
import tobiapplications.com.moviebase.model.detail.SeriesDetailResponse;
import tobiapplications.com.moviebase.network.DataManager;
import tobiapplications.com.moviebase.utils.Constants;
import tobiapplications.com.moviebase.utils.DialogBuilderUtil;
import tobiapplications.com.moviebase.utils.GeneralUtils;
import tobiapplications.com.moviebase.utils.NetworkUtils;
import tobiapplications.com.moviebase.utils.SQLUtils;
import tobiapplications.com.moviebase.utils.StringUtils;
import tobiapplications.com.moviebase.utils.mvp.BasePresenter;

import static tobiapplications.com.moviebase.utils.GeneralUtils.*;

/**
 * Created by Tobias on 11.06.2017.
 */

public class DetailActivityPresenter extends BasePresenter<DetailActivityContract.View> implements DetailActivityContract.Presenter {

    private WeakReference<DetailActivity> parent;
    private int id;
    private MovieDetailResponse clickedMovie;
    private SeriesDetailResponse clickedSerie;
    private boolean isMarkedAsFavorite = false;
    private static final int PERCENTAGE_TO_SHOW_IMAGE = 60;
    private int maxScrollSize;
    private boolean isImageHidden;
    private Context context;
    private int type;
    private DataManager dataManager;

    public DetailActivityPresenter(DetailActivity detailActivity, Context context, Intent intent) {
        this.parent = new WeakReference<>(detailActivity);
        this.context = context;
        this.dataManager = DataManager.getInstance();
        parseIntent(intent);
        requestDownload();
    }

    private void parseIntent(Intent intent) {
        if (intent == null) {
            return;
        }

        id = intent.getIntExtra(Constants.CLICKED_MOVIE, -1);
        type = intent.getIntExtra(Constants.TYPE, -1);
    }

    private void requestDownload() {
        if (isMovie(type)) {
            requestSingleMovieDownload();
        } else if (isSerie(type)) {
            requestSingleSerieDownload();
        }
    }

    private void requestSingleSerieDownload() {
        dataManager.requestSingleSerie(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::displaySeriesResponse,
                        throwable -> displayError("Series"));
    }

    private void requestSingleMovieDownload() {
        dataManager.requestSingleMovie(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::displayMovieResponse,
                        throwable -> displayError("Movies"));
    }

    private void displayMovieResponse(MovieDetailResponse detailResponse) {
        if (detailResponse == null) {
            return;
        }

        clickedMovie = detailResponse;
        if (GeneralUtils.weakReferenceIsValid(parent)) {
            parent.get().displayTitle(clickedMovie.getTitle());
            String imageUrl = NetworkUtils.getFullImageUrlHigh(clickedMovie.getBackgroundImagePath());
            if (StringUtils.nullOrEmpty(imageUrl)) {
                parent.get().showNoPictureAvailable(true);
            } else {
                parent.get().showNoPictureAvailable(false);
                parent.get().showPosterImage(imageUrl);
            }
            parent.get().setUpMovieTabFragment(clickedMovie, type);
        }
        setFabDependingOnFavoriteStatus();
    }

    private void displaySeriesResponse(SeriesDetailResponse detailResponse) {
        if (detailResponse == null) {
            return;
        }

        clickedSerie = detailResponse;
        if (GeneralUtils.weakReferenceIsValid(parent)) {
            parent.get().displayTitle(clickedSerie.getName());
            String imageUrl = clickedSerie.getBackgroundImage();
            if (StringUtils.nullOrEmpty(imageUrl)) {
                parent.get().showNoPictureAvailable(true);
            } else {
                parent.get().showNoPictureAvailable(false);
                imageUrl = NetworkUtils.getFullImageUrlHigh(clickedSerie.getBackgroundImage());
                parent.get().showPosterImage(imageUrl);
            }
            parent.get().setUpSeriesTabFragment(clickedSerie, type);
        }
        setFabDependingOnFavoriteStatus();
    }

    @Override
    public boolean hasInternetConnection() {
        return NetworkUtils.isConnectedToInternet(context);
    }

    private void displayError(String message) {
        Timber.d("Error: " + message);

        String dialogMessage;
        if (hasInternetConnection()) {
            dialogMessage = context.getString(R.string.unknown_error);
        } else {
            dialogMessage = context.getString(R.string.network_error);
        }
        AlertDialog errorDialog = DialogBuilderUtil.createErrorDialog(context, dialogMessage, requestDownloadOnDelay());

        errorDialog.show();
    }

    private DialogBuilderListener requestDownloadOnDelay() {
        return new DialogBuilderListener() {
            @Override
            public void onConfirm() {
                new Handler().postDelayed(() -> {
                if (hasInternetConnection()) {
                    requestDownload();
                } else {
                    displayError("Error");
                }
            }, 2000);
            }

            @Override
            public void onCancel() {
                if (GeneralUtils.weakReferenceIsValid(parent)) {
                    parent.get().onBackPressed();
                }
            }
        };
    }

    @Override
    public void openToolbarImage() {
        String imageUrl;
        if (isMovie(type) && clickedMovie != null
                && !StringUtils.nullOrEmpty(clickedMovie.getBackgroundImagePath())) {
            imageUrl = clickedMovie.getBackgroundImagePath();
        } else if (isSerie(type) && clickedSerie != null
                && !StringUtils.nullOrEmpty(clickedSerie.getBackgroundImage())){
            imageUrl = clickedSerie.getBackgroundImage();
        } else {
            return;
        }

        if (GeneralUtils.weakReferenceIsValid(parent)) {
            new ImageViewer.Builder(parent.get(), new String[]{NetworkUtils.getFullImageUrlHigh(imageUrl)})
                    .setStartPosition(0)
                    .show();
        }
    }

    @Override
    public void handleFabClick() {
        if (isMovie(type)) {
            handleFabClickForMovie();
        } else if (isSerie(type)) {
            handleFabClickForSerie();
        }
    }

    private void handleFabClickForMovie() {
        if (clickedMovie == null) {
            return;
        }

        if (!isMarkedAsFavorite) {
            isMarkedAsFavorite = true;
            markAsFavorite(clickedMovie.getTitle());
        } else {
            isMarkedAsFavorite = false;
            unmarkFromFavorite(clickedMovie.getTitle(), clickedMovie.getId());
        }
    }

    private void handleFabClickForSerie() {
        if (clickedSerie == null) {
            return;
        }

        if (!isMarkedAsFavorite) {
            isMarkedAsFavorite = true;
            markAsFavorite(clickedSerie.getName());

        } else {
            isMarkedAsFavorite = false;
            unmarkFromFavorite(clickedSerie.getName(), clickedSerie.getId());
        }
    }

    private void markAsFavorite(String message) {
        if (GeneralUtils.weakReferenceIsValid(parent)) {
            parent.get().markFabAsFavorite();
            parent.get().showMarkAsFavorite(message);
        }
        insertIntoDatabase(type);
    }

    private void unmarkFromFavorite(String message, int id) {
        if (GeneralUtils.weakReferenceIsValid(parent)) {
            parent.get().unMarkFabFromFavorite();
            parent.get().showRemovedFromFavorite(message);
        }

        SQLUtils.deleteFromDataBase(context, id, type);
    }

    @Override
    public void insertIntoDatabase(int overviewType) {
        if (isMovie(type)) {
            if (clickedMovie != null) {
                SQLUtils.insertMovieIntoDatabase(context, clickedMovie);
            }
        } else if (isSerie(type)){
            if (clickedSerie != null) {
                SQLUtils.insertSerieIntoDatabase(context, clickedSerie);
            }
        }
    }

    @Override
    public void setFabDependingOnFavoriteStatus() {
        if (isMovie(type)) {
            isMarkedAsFavorite = SQLUtils.checkIfMovieIsMarkedAsFavorite(context, id);
        } else if (isSerie(type)){
            isMarkedAsFavorite = SQLUtils.checkIfSerieIsMarkedAsFavorite(context, id);
        }

        if (GeneralUtils.weakReferenceIsValid(parent)) {
            parent.get().setFabButtonVisible();
            if (isMarkedAsFavorite) {
                parent.get().markFabAsFavorite();
            } else {
                parent.get().unMarkFabFromFavorite();
            }
        }
    }

    @Override
    public void setAppBarOffsetChanged(int totalScrollRange, int verticalOffset) {
        if (maxScrollSize == 0) {
            maxScrollSize = totalScrollRange;
        }

        int currentScrollPercentage = (Math.abs(verticalOffset)) * 100
                / maxScrollSize;

        if (GeneralUtils.weakReferenceIsValid(parent)) {
            if (currentScrollPercentage >= PERCENTAGE_TO_SHOW_IMAGE) {
                if (!isImageHidden) {
                    isImageHidden = true;
                    parent.get().animateFabDown();
                }
            }
            if (currentScrollPercentage < PERCENTAGE_TO_SHOW_IMAGE) {
                if (isImageHidden) {
                    isImageHidden = false;
                    parent.get().animateFabUp();
                }
            }
        }
    }

    @Override
    public void onMenuItemClicked(MenuItem menuItem) {
        if(menuItem.getItemId() == android.R.id.home) {
            if (GeneralUtils.weakReferenceIsValid(parent)) {
                parent.get().onBackPressed();
            }
        }
    }
}
