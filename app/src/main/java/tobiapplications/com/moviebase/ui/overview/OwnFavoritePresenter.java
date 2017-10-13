package tobiapplications.com.moviebase.ui.overview;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.content.Loader;

import java.util.ArrayList;

import tobiapplications.com.moviebase.R;
import tobiapplications.com.moviebase.model.overview.PosterOverviewItem;
import tobiapplications.com.moviebase.utils.Constants;

import static tobiapplications.com.moviebase.utils.GeneralUtils.*;

/**
 * Created by Tobias on 11.06.2017.
 */

public class OwnFavoritePresenter implements OverviewTabFragmentContract.DatabasePresenter {

    private OverviewTabFragmentContract.DatabaseView parent;
    private Context context;
    private int type;
    private static final int MOVIE_CURSOR_LOADER_ID = 123;
    private static final int SERIE_CURSOR_LOADER_ID = 456;

    public OwnFavoritePresenter(OverviewTabFragmentContract.DatabaseView parent, Context context) {
        this.parent = parent;
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
        parent.showLoading(false);
    }

    private void loadFinished(Cursor data) {
        if (data != null && data.getCount() > 0) {
            parent.hideNoFavoriteAvailable();
            createPosterItemsFromCursor(data);
        } else {
            parent.showNoFavoriteAvailable(context.getString(R.string.no_favorite_movies));
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

        parent.setPosterItems(posterItems);
    }

    @Override
    public void onMovieClick(int id) {
        parent.startDetailActivity(id, type);
    }

    @Override
    public void onResume() {
        if (isMovie(type)) {
            parent.startLoader(MOVIE_CURSOR_LOADER_ID);
        } else if (isSerie(type)) {
            parent.startLoader(SERIE_CURSOR_LOADER_ID);
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id) {
        if (id == MOVIE_CURSOR_LOADER_ID) {
            return parent.onCreateMovieLoader();
        } else if (id == SERIE_CURSOR_LOADER_ID) {
            return parent.onCreateSerieLoader();
        }

        return null;
    }
}
