package tobiapplications.com.moviebase.utils;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v4.content.LocalBroadcastManager;

import java.util.ArrayList;

import tobiapplications.com.moviebase.database.MoviesContract;
import tobiapplications.com.moviebase.model.detail.Genre;
import tobiapplications.com.moviebase.model.detail.MovieDetailResponse;
import tobiapplications.com.moviebase.model.overview.MovieOverviewModel;

/**
 * Created by Tobias on 12.06.2017.
 */

public class SQLUtils {

    public static String[] selectAll = new String[] {
            MoviesContract.MovieEntry.COLUMN_ID,
            MoviesContract.MovieEntry.COLUMN_TITLE,
            MoviesContract.MovieEntry.COLUMN_TITLE_IMAGE_PATH,
            MoviesContract.MovieEntry.COLUMN_BACKDROP_IMAGE_PATH,
            MoviesContract.MovieEntry.COLUMN_YEAR,
            MoviesContract.MovieEntry.COLUMN_RATING,
            MoviesContract.MovieEntry.COLUMN_DESCRIPTION,
            MoviesContract.MovieEntry.COLUMN_GENRES,
            MoviesContract.MovieEntry.COLUMN_ADULT

    };

    public static final int INDEX_COLUMN_ID = 0;
    public static final int INDEX_COLUMN_TITLE = 1;
    public static final int INDEX_COLUMN_TITLE_IMAGE_PATH = 2;
    public static final int INDEY_COLUMN_BACKDROP_IMAGE_PATH = 3;
    public static final int INDEX_COLUMN_YEAR = 4;
    public static final int INDEX_COLUMN_RATING = 5;
    public static final int INDEX_COLUMN_DESCRIPTION = 6;
    public static final int INDEX_COLUMN_GENRES = 7;
    public static final int INDEX_COLUMN_ADULT = 8;

    public static void insertIntoDatabase(Context context, ContentValues values) {
        context.getContentResolver().insert(MoviesContract.MovieEntry.CONTENT_URI, values);
        Intent informOwnFavoriteActivity = new Intent(Constants.MOVIE_INSERT_TO_DATABASE);
        LocalBroadcastManager.getInstance(context).sendBroadcast(informOwnFavoriteActivity);
    }

    public static void insertIntoDatabase(Context context, MovieDetailResponse clickedMovie) {
        String genresString = "";
        ArrayList<Genre> genreArrayList = clickedMovie.getGenres();

        for (int i = 0; i < genreArrayList.size(); i++) {
            if (i == 0) {
                genresString = genresString + genreArrayList.get(i).getId();
            } else {
                genresString = genresString + "-" + genreArrayList.get(i).getId();
            }
        }

        ContentValues values = new ContentValues();
        values.put(MoviesContract.MovieEntry.COLUMN_ID, clickedMovie.getId());
        values.put(MoviesContract.MovieEntry.COLUMN_TITLE, clickedMovie.getTitle());
        values.put(MoviesContract.MovieEntry.COLUMN_TITLE_IMAGE_PATH, clickedMovie.getTitleImagePath());
        values.put(MoviesContract.MovieEntry.COLUMN_BACKDROP_IMAGE_PATH, clickedMovie.getBackgroundImagePath());
        values.put(MoviesContract.MovieEntry.COLUMN_YEAR, clickedMovie.getReleaseDate());
        values.put(MoviesContract.MovieEntry.COLUMN_RATING, clickedMovie.getVoteAverage());
        values.put(MoviesContract.MovieEntry.COLUMN_DESCRIPTION, clickedMovie.getDescription());
        values.put(MoviesContract.MovieEntry.COLUMN_GENRES, genresString);
        values.put(MoviesContract.MovieEntry.COLUMN_ADULT, clickedMovie.isAdult() ? "yes" : "no");

        insertIntoDatabase(context, values);
    }

    public static void deleteCurrentMovieFromFavoriteDatabase(Context context, int movieId) {
        context.getContentResolver().delete(
                MoviesContract.MovieEntry.CONTENT_URI,
                MoviesContract.MovieEntry.COLUMN_ID + " = ?",
                new String[]{String.valueOf(movieId)});
    }

    public static Cursor getAllFavoriteMovies(Context context) {
        return context.getContentResolver().query(
                MoviesContract.MovieEntry.CONTENT_URI,
                SQLUtils.selectAll,
                null,
                null,
                null);
    }

    public static boolean checkIfMovieIsMarkedAsFavorite(Context context, int detailMovieId) {
        Cursor cursor = getAllFavoriteMovies(context);
        boolean isMarkedAsFavorite = false;
        if (cursor.getCount() == 0) {
            return false;
        }

        while(cursor.moveToNext()) {
            int movieId = cursor.getInt(SQLUtils.INDEX_COLUMN_ID);
            if (detailMovieId == movieId) {
                isMarkedAsFavorite = true;
            }
        }
        return isMarkedAsFavorite;
    }
}
