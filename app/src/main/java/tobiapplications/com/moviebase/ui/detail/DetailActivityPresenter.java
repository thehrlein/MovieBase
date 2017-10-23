package tobiapplications.com.moviebase.ui.detail;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.view.MenuItem;

import com.stfalcon.frescoimageviewer.ImageViewer;

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

    private DetailActivity parent;
    private int id;
    private MovieDetailResponse clickedMovie;
    private SeriesDetailResponse clickedSerie;
    private boolean isMarkedAsFavorite = false;
    private static final int PERCENTAGE_TO_SHOW_IMAGE = 60;
    private int maxScrollSize;
    private boolean isImageHidden;
    private Context context;
    private int type;

    public DetailActivityPresenter(DetailActivity detailActivity, Context context, Intent intent) {
        this.parent = detailActivity;
        this.context = context;
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
        DataManager.getInstance().requestSingleSerie(this, id);
    }

    @Override
    public void requestSingleMovieDownload() {
        DataManager.getInstance().requestSingleMovie(this, id);
    }

    @Override
    public void displayMovieResponse(MovieDetailResponse detailResponse) {
        if (detailResponse == null) {
            return;
        }

        clickedMovie = detailResponse;
        parent.displayTitle(clickedMovie.getTitle());
        String imageUrl = NetworkUtils.getFullImageUrlHigh(clickedMovie.getBackgroundImagePath());
        if (StringUtils.nullOrEmpty(imageUrl)) {
            parent.showNoPictureAvailable(true);
        } else {
            parent.showNoPictureAvailable(false);
            parent.showPosterImage(imageUrl);
        }
        parent.setUpMovieTabFragment(clickedMovie, type);
        setFabDependingOnFavoriteStatus();
    }

    @Override
    public void displaySeriesResponse(SeriesDetailResponse detailResponse) {
        if (detailResponse == null) {
            return;
        }

        clickedSerie = detailResponse;
        parent.displayTitle(clickedSerie.getName());
        String imageUrl = clickedSerie.getBackgroundImage();
        if (StringUtils.nullOrEmpty(imageUrl)) {
            parent.showNoPictureAvailable(true);
        } else {
            parent.showNoPictureAvailable(false);
            imageUrl = NetworkUtils.getFullImageUrlHigh(clickedSerie.getBackgroundImage());
            parent.showPosterImage(imageUrl);
        }
        parent.setUpSeriesTabFragment(clickedSerie, type);
        setFabDependingOnFavoriteStatus();
    }

    @Override
    public boolean hasInternetConnection() {
        return NetworkUtils.isConnectedToInternet(context);
    }

    @Override
    public void displayError(String message) {
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
                parent.onBackPressed();
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

        new ImageViewer.Builder(parent, new String[]{NetworkUtils.getFullImageUrlHigh(imageUrl)})
                .setStartPosition(0)
                .show();
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
        parent.markFabAsFavorite();
        parent.showMarkAsFavorite(message);
        insertIntoDatabase(type);
    }

    private void unmarkFromFavorite(String message, int id) {
        parent.unMarkFabFromFavorite();
        parent.showRemovedFromFavorite(message);

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

        parent.setFabButtonVisible();
        if (isMarkedAsFavorite) {
            parent.markFabAsFavorite();
        } else {
            parent.unMarkFabFromFavorite();
        }
    }

    @Override
    public void setAppBarOffsetChanged(int totalScrollRange, int verticalOffset) {
        if (maxScrollSize == 0) {
            maxScrollSize = totalScrollRange;
        }

        int currentScrollPercentage = (Math.abs(verticalOffset)) * 100
                / maxScrollSize;

        if (currentScrollPercentage >= PERCENTAGE_TO_SHOW_IMAGE) {
            if (!isImageHidden) {
                isImageHidden = true;
                parent.animateFabDown(100);
            }
        }
        if (currentScrollPercentage < PERCENTAGE_TO_SHOW_IMAGE) {
            if (isImageHidden) {
                isImageHidden = false;
                parent.animateFabUp(0);
            }
        }
    }

    @Override
    public void onMenuItemClicked(MenuItem menuItem) {
        if(menuItem.getItemId() == android.R.id.home) {
            parent.onBackPressed();
        }
    }
}
