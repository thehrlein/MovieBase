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
    public ArrayList<Integer> extractGenres(Cursor data) {
        String genreAsString = data.getString(SQLUtils.INDEX_COLUMN_GENRES);
        String[] genresArray = genreAsString.split("-");
        ArrayList<Integer> genres = new ArrayList<>();
        for (String genre : genresArray) {
            genres.add(Integer.valueOf(genre));
        }

        return genres;
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
                ArrayList<Integer> genres = extractGenres(data);
                boolean adult = data.getString(SQLUtils.INDEX_COLUMN_ADULT).equals("yes");
                MovieOverviewModel movie = buildMovieFromCursor(data, genres, adult);
                movies.add(movie);
            }
        }

        parent.setMovies(movies);
    }

    @Override
    public MovieOverviewModel buildMovieFromCursor(Cursor data, ArrayList<Integer> genres, boolean adult) {
        return new MovieOverviewModel(
                data.getInt(SQLUtils.INDEX_COLUMN_ID),
                data.getString(SQLUtils.INDEX_COLUMN_TITLE),
                data.getString(SQLUtils.INDEX_COLUMN_TITLE_IMAGE_PATH),
                data.getString(SQLUtils.INDEY_COLUMN_BACKDROP_IMAGE_PATH),
                data.getString(SQLUtils.INDEX_COLUMN_YEAR),
                data.getString(SQLUtils.INDEX_COLUMN_RATING),
                data.getString(SQLUtils.INDEX_COLUMN_DESCRIPTION),
                genres,
                adult
        );
    }

    private void createSerieListFromCursor(Cursor data) {
        ArrayList<MovieOverviewModel> series = new ArrayList<>();
        if (data != null) {
            while (data.moveToNext()) {
                MovieOverviewModel serie = buildSerieFromCursor(data);
                series.add(serie);
            }
        }

        parent.setSeries(series);
    }

    private MovieOverviewModel buildSerieFromCursor(Cursor data) {
        return new MovieOverviewModel(
                data.getInt(SQLUtils.INDEX_COLUMN_ID),
                data.getString(SQLUtils.INDEX_COLUMN_TITLE),
                data.getString(SQLUtils.INDEX_COLUMN_TITLE_IMAGE_PATH));
    }

}
