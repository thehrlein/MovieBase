package tobiapplications.com.moviebase.ui.detail;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.view.MenuItem;
import com.stfalcon.frescoimageviewer.ImageViewer;

import java.lang.ref.WeakReference;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;
import tobiapplications.com.moviebase.R;
import tobiapplications.com.moviebase.listener.DialogBuilderListener;
import tobiapplications.com.moviebase.model.detail.MovieDetailResponse;
import tobiapplications.com.moviebase.model.detail.SeriesDetailResponse;
import tobiapplications.com.moviebase.network.DataManager;
import tobiapplications.com.moviebase.utils.Constants;
import tobiapplications.com.moviebase.utils.DialogBuilderUtil;
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
        if (isAttached()) {
            getView().displayTitle(clickedMovie.getTitle());
            String imageUrl = NetworkUtils.getFullImageUrlHigh(clickedMovie.getBackgroundImagePath());
            if (StringUtils.nullOrEmpty(imageUrl)) {
                getView().showNoPictureAvailable(true);
            } else {
                getView().showNoPictureAvailable(false);
                getView().showPosterImage(imageUrl);
            }
            getView().setUpMovieTabFragment(clickedMovie, type);
        }
        setFabDependingOnFavoriteStatus();
    }

    private void displaySeriesResponse(SeriesDetailResponse detailResponse) {
        if (detailResponse == null) {
            return;
        }

        clickedSerie = detailResponse;
        if (isAttached()) {
            getView().displayTitle(clickedSerie.getName());
            String imageUrl = clickedSerie.getBackgroundImage();
            if (StringUtils.nullOrEmpty(imageUrl)) {
                getView().showNoPictureAvailable(true);
            } else {
                getView().showNoPictureAvailable(false);
                imageUrl = NetworkUtils.getFullImageUrlHigh(clickedSerie.getBackgroundImage());
                getView().showPosterImage(imageUrl);
            }
            getView().setUpSeriesTabFragment(clickedSerie, type);
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
                if (isAttached()) {
                    ((Activity) getView()).onBackPressed();
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

        if (isAttached()) {
            new ImageViewer.Builder(((Activity) getView()), new String[]{NetworkUtils.getFullImageUrlHigh(imageUrl)})
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

        trackFavButtonClick(clickedMovie.getTitle(), isMarkedAsFavorite);
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
        
        trackFavButtonClick(clickedSerie.getName(), isMarkedAsFavorite);
    }

    private void markAsFavorite(String message) {
        if (isAttached()) {
            getView().markFabAsFavorite();
            getView().showMarkAsFavorite(message);
        }
        insertIntoDatabase(type);


    }

    private void unmarkFromFavorite(String message, int id) {
        if (isAttached()) {
            getView().unMarkFabFromFavorite();
            getView().showRemovedFromFavorite(message);
        }

        SQLUtils.deleteFromDataBase(context, id, type);
    }

    private void trackFavButtonClick(String title, boolean isMarkedAsFavorite) {
//        String value = isMarkedAsFavorite ? context.getString(R.string.mark_favorite_identifier) : context.getString(R.string.unmark_favorite_identifier);
//        Answers.getInstance().logCustom(new CustomEvent(value)
//            .putCustomAttribute(context.getString(R.string.favorite_identifier), title));
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

        if (isAttached()) {
            getView().setFabButtonVisible();
            if (isMarkedAsFavorite) {
                getView().markFabAsFavorite();
            } else {
                getView().unMarkFabFromFavorite();
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

        if (isAttached()) {
            if (currentScrollPercentage >= PERCENTAGE_TO_SHOW_IMAGE) {
                if (!isImageHidden) {
                    isImageHidden = true;
                    getView().animateFabDown();
                }
            }
            if (currentScrollPercentage < PERCENTAGE_TO_SHOW_IMAGE) {
                if (isImageHidden) {
                    isImageHidden = false;
                    getView().animateFabUp();
                }
            }
        }
    }

    @Override
    public void onMenuItemClicked(MenuItem menuItem) {
        if(menuItem.getItemId() == android.R.id.home) {
            if (isAttached()) {
                ((Activity) getView()).onBackPressed();
            }
        }
    }
}
