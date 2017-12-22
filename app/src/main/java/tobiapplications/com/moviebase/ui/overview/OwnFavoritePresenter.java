package tobiapplications.com.moviebase.ui.overview;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.content.Loader;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import tobiapplications.com.moviebase.R;
import tobiapplications.com.moviebase.model.overview.PosterOverviewItem;
import tobiapplications.com.moviebase.utils.Constants;
import tobiapplications.com.moviebase.utils.mvp.BasePresenter;

import static tobiapplications.com.moviebase.utils.GeneralUtils.*;

/**
 * Created by Tobias on 11.06.2017.
 */

public class OwnFavoritePresenter extends BasePresenter<OverviewTabContract.DatabaseView> implements OverviewTabContract.DatabasePresenter {

    private WeakReference<OverviewTabContract.DatabaseView> parent;
    private Context context;
    private int type;
    private static final int MOVIE_CURSOR_LOADER_ID = 123;
    private static final int SERIE_CURSOR_LOADER_ID = 456;

    public OwnFavoritePresenter(OverviewTabContract.DatabaseView parent, Context context) {
        this.parent = new WeakReference<>(parent);
        this.context = context;
    }

    @Override
    public void parseType(Bundle arguments) {
        if (arguments == null) {
            return;
        }

        if (arguments.containsKey(Constants.TYPE)) {
            type = arguments.getInt(Constants.TYPE);
        }
    }

    @Override
    public void onDatabaseLoadFinished(Cursor data) {
        loadFinished(data);

        if (isAttached()) {
            getView().showLoading(false);
        }
    }

    private void loadFinished(Cursor data) {
        if (data != null && data.getCount() > 0) {
            if (isAttached()) {
                getView().hideNoFavoriteAvailable();
            }
            createPosterItemsFromCursor(data);
        } else {
            if (isAttached()) {
                getView().showNoFavoriteAvailable(context.getString(R.string.no_favorite_movies));
            }
        }
    }

    @Override
    public void createPosterItemsFromCursor(Cursor data) {
        ArrayList<PosterOverviewItem> posterItems = new ArrayList<>();
        if (data != null) {
            while (data.moveToNext()) {
                PosterOverviewItem movie = new PosterOverviewItem(data);
                posterItems.add(movie);
            }
        }

        if (isAttached()) {
            getView().setPosterItems(posterItems);
        }
    }

    @Override
    public void onMovieClick(int id) {
        if (isAttached()) {
            getView().startDetailActivity(id, type);
        }
    }

    @Override
    public void onResume() {
        if (isMovie(type)) {
            startLoader(MOVIE_CURSOR_LOADER_ID);
        } else if (isSerie(type)) {
            startLoader(SERIE_CURSOR_LOADER_ID);
        }
    }

    private void startLoader(int type) {
        if (isAttached()) {
            getView().startLoader(type);
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id) {
        if (isAttached()) {
            if (id == MOVIE_CURSOR_LOADER_ID) {
                return getView().onCreateMovieLoader();
            } else if (id == SERIE_CURSOR_LOADER_ID) {
                return getView().onCreateSerieLoader();
            }
        }

        return null;
    }
}
