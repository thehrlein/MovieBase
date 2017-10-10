package tobiapplications.com.moviebase.ui.overview;

import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;

import tobiapplications.com.moviebase.R;
import tobiapplications.com.moviebase.model.overview.MovieOverviewModel;
import tobiapplications.com.moviebase.utils.Constants;
import tobiapplications.com.moviebase.utils.SQLUtils;

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
    public void onDatabaseLoadFinished(Cursor data, Constants.OverviewType overviewType) {
        if (overviewType == Constants.OverviewType.MOVIES) {
            movieLoadFinished(data);
        } else {
            serieLoadFinished(data);
        }

        parent.showLoading(false);
    }


    private void movieLoadFinished(Cursor data) {
        if (data != null && data.getCount() > 0) {
            parent.hideNoFavoriteAvailable();
            createMovieListFromCursor(data);
        } else {
            parent.showNoFavoriteAvailable(context.getString(R.string.no_favorite_movies));
        }
    }

    private void serieLoadFinished(Cursor data) {
        if (data != null && data.getCount() > 0) {
            parent.hideNoFavoriteAvailable();
            createSerieListFromCursor(data);
        } else {
            parent.showNoFavoriteAvailable(context.getString(R.string.no_favorite_series));
        }
    }

    @Override
    public void createMovieListFromCursor(Cursor data) {
        ArrayList<MovieOverviewModel> movies = new ArrayList<>();
        if (data != null) {
            while (data.moveToNext()) {
                MovieOverviewModel movie = new MovieOverviewModel(data);
                movies.add(movie);
            }
        }

        parent.setMovies(movies);
    }

    private void createSerieListFromCursor(Cursor data) {
        ArrayList<MovieOverviewModel> series = new ArrayList<>();
        if (data != null) {
            while (data.moveToNext()) {
                MovieOverviewModel serie = new MovieOverviewModel(data);
                series.add(serie);
            }
        }

        parent.setSeries(series);
    }
}
