package tobiapplications.com.moviebase.ui.movie_overview;

import android.content.Context;
import android.database.Cursor;
import android.widget.Toast;

import java.util.ArrayList;

import tobiapplications.com.moviebase.model.MovieOverviewModel;
import tobiapplications.com.moviebase.model.MovieOverviewResponse;
import tobiapplications.com.moviebase.utils.NetworkUtils;
import tobiapplications.com.moviebase.utils.SQLUtils;

/**
 * Created by Tobias on 11.06.2017.
 */

public class OwnFavoriteMoviePresenter implements MoviePresenter {

    private OwnFavoriteMovieFragment parent;
    private Context context;

    public OwnFavoriteMoviePresenter(MovieOverview parent, Context context) {
        this.parent = (OwnFavoriteMovieFragment) parent;
        this.context = context;
    }

    @Override
    public void loadMovies() {
        parent.startLoader();
    }

    @Override
    public boolean noMoviesShown() {
        return parent.getCurrentMovieSize() == 0;
    }

    @Override
    public boolean hasInternetConnection() {
        return NetworkUtils.isConnectedToInternet(context);
    }

    @Override
    public void isConnectedToInternet(boolean connected) {
        parent.showNetworkError(connected);
    }


    @Override
    public void requestMovieDownload() {
        // no download
    }

    @Override
    public void displayMovies(MovieOverviewResponse movieOverviewResponse) {
        parent.showLoading(false);
        parent.setMovies(movieOverviewResponse.getMovies());
    }

    @Override
    public void loadMoreMovies() {
        // all movies are loaded at start
    }

    @Override
    public void displayError() {
        Toast.makeText(context, "Failed to load movies, please check network connection", Toast.LENGTH_SHORT).show();
        // TODO AlertDialog or something like that
    }

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

    private ArrayList<Integer> extractGenres(Cursor data) {
        String genreAsString = data.getString(SQLUtils.INDEX_COLUMN_GENRES);
        String[] genresArray = genreAsString.split("-");
        ArrayList<Integer> genres = new ArrayList<>();
        for (String genre : genresArray) {
            genres.add(Integer.valueOf(genre));
        }

        return genres;
    }

    private MovieOverviewModel buildMovieFromCursor(Cursor data, ArrayList<Integer> genres, boolean adult) {
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
}
