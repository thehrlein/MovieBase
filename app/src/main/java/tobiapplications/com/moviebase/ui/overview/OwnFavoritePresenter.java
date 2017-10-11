package tobiapplications.com.moviebase.ui.overview;

import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;

import tobiapplications.com.moviebase.R;
import tobiapplications.com.moviebase.model.overview.MovieOverviewModel;
import tobiapplications.com.moviebase.utils.Constants;

/**
 * Created by Tobias on 11.06.2017.
 */

public class OwnFavoritePresenter implements OverviewTabFragmentContract.DatabasePresenter {

    private OverviewTabFragmentContract.DatabaseView parent;
    private Context context;

    public OwnFavoritePresenter(OverviewTabFragmentContract.DatabaseView parent, Context context) {
        this.parent = parent;
        this.context = context;
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
        ArrayList<MovieOverviewModel> posterItems = new ArrayList<>();
        if (data != null) {
            while (data.moveToNext()) {
                MovieOverviewModel movie = new MovieOverviewModel(data);
                posterItems.add(movie);
            }
        }

        parent.setPosterItems(posterItems);
    }
}
