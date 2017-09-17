package tobiapplications.com.moviebase.utils;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v4.content.LocalBroadcastManager;

import java.util.ArrayList;

import tobiapplications.com.moviebase.database.MoviesContract;
import tobiapplications.com.moviebase.database.SeriesContract;
import tobiapplications.com.moviebase.model.detail.Genre;
import tobiapplications.com.moviebase.model.detail.MovieDetailResponse;
import tobiapplications.com.moviebase.model.detail.SeriesDetailResponse;

/**
 * Created by Tobias on 12.06.2017.
 */

public class SQLUtils {

    public static String[] selectAllMovies = new String[] {
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

    public static String[] selectAllSeries = new String[] {
            SeriesContract.SeriesEntry.COLUMN_ID
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

    public static void insertMovieIntoDatabase(Context context, ContentValues values) {
        context.getContentResolver().insert(MoviesContract.MovieEntry.CONTENT_URI, values);
        Intent informOwnFavoriteActivity = new Intent(Constants.MOVIE_INSERT_TO_DATABASE);
        LocalBroadcastManager.getInstance(context).sendBroadcast(informOwnFavoriteActivity);
    }

    public static void insertSerieIntoDatabase(Context context, ContentValues values) {
        context.getContentResolver().insert(SeriesContract.SeriesEntry.CONTENT_URI, values);
        Intent informOwnFavoriteActivity = new Intent(Constants.SERIE_INSERT_TO_DATABASE);
        LocalBroadcastManager.getInstance(context).sendBroadcast(informOwnFavoriteActivity);
    }

    public static void insertMovieIntoDatabase(Context context, MovieDetailResponse movie) {
        String genresString = "";
        ArrayList<Genre> genreArrayList = movie.getGenres();

        for (int i = 0; i < genreArrayList.size(); i++) {
            if (i == 0) {
                genresString = genresString + genreArrayList.get(i).getId();
            } else {
                genresString = genresString + "-" + genreArrayList.get(i).getId();
            }
        }

        ContentValues values = new ContentValues();
        values.put(MoviesContract.MovieEntry.COLUMN_ID, movie.getId());
        values.put(MoviesContract.MovieEntry.COLUMN_TITLE, movie.getTitle());
        values.put(MoviesContract.MovieEntry.COLUMN_TITLE_IMAGE_PATH, movie.getTitleImagePath());
        values.put(MoviesContract.MovieEntry.COLUMN_BACKDROP_IMAGE_PATH, movie.getBackgroundImagePath());
        values.put(MoviesContract.MovieEntry.COLUMN_YEAR, movie.getReleaseDate());
        values.put(MoviesContract.MovieEntry.COLUMN_RATING, movie.getVoteAverage());
        values.put(MoviesContract.MovieEntry.COLUMN_DESCRIPTION, movie.getDescription());
        values.put(MoviesContract.MovieEntry.COLUMN_GENRES, genresString);
        values.put(MoviesContract.MovieEntry.COLUMN_ADULT, movie.isAdult() ? "yes" : "no");

        insertMovieIntoDatabase(context, values);
    }

    public static void insertSerieIntoDatabase(Context context, SeriesDetailResponse serie) {
//        String genresString = "";
//        ArrayList<Genre> genreArrayList = serie.getGenres();
//
//        for (int i = 0; i < genreArrayList.size(); i++) {
//            if (i == 0) {
//                genresString = genresString + genreArrayList.get(i).getId();
//            } else {
//                genresString = genresString + "-" + genreArrayList.get(i).getId();
//            }
//        }

        ContentValues values = new ContentValues();
        values.put(SeriesContract.SeriesEntry.COLUMN_ID, serie.getId());
//        values.put(SeriesContract.SeriesEntry.COLUMN_TITLE, serie.getName());
//        values.put(SeriesContract.SeriesEntry.COLUMN_BACKDROP_IMAGE_PATH, serie.getBackgroundImagePath());
//        values.put(SeriesContract.SeriesEntry.COLUMN_YEAR, serie.getReleaseDate());
//        values.put(SeriesContract.SeriesEntry.COLUMN_RATING, serie.getVoteAverage());
//        values.put(SeriesContract.SeriesEntry.COLUMN_DESCRIPTION, serie.getDescription());
//        values.put(SeriesContract.SeriesEntry.COLUMN_GENRES, genresString);
//        values.put(SeriesContract.SeriesEntry.COLUMN_ADULT, serie.isAdult() ? "yes" : "no");

        insertSerieIntoDatabase(context, values);
    }

    public static void deleteCurrentMovieFromFavoriteDatabase(Context context, int movieId) {
        context.getContentResolver().delete(
                MoviesContract.MovieEntry.CONTENT_URI,
                MoviesContract.MovieEntry.COLUMN_ID + " = ?",
                new String[]{String.valueOf(movieId)});
    }

    public static void deleteCurrentSerieFromFavoriteDatabase(Context context, int serieId) {
        context.getContentResolver().delete(
                SeriesContract.SeriesEntry.CONTENT_URI,
                SeriesContract.SeriesEntry.COLUMN_ID + " = ?",
                new String[]{String.valueOf(serieId)});
    }

    public static Cursor getAllFavoriteMovies(Context context) {
        return context.getContentResolver().query(
                MoviesContract.MovieEntry.CONTENT_URI,
                SQLUtils.selectAllMovies,
                null,
                null,
                null);
    }

    public static Cursor getAllFavoriteSerie(Context context) {
        return context.getContentResolver().query(
                SeriesContract.SeriesEntry.CONTENT_URI,
                SQLUtils.selectAllSeries,
                null,
                null,
                null);
    }

    public static boolean checkIfMovieIsMarkedAsFavorite(Context context, int detailMovieId) {
        Cursor cursor = getAllFavoriteMovies(context);
        boolean isMarkedAsFavorite = false;
        if (cursor == null || cursor.getCount() == 0) {
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

    public static boolean checkIfSerieIsMarkedAsFavorite(Context context, int detailMovieId) {
        Cursor cursor = getAllFavoriteSerie(context);
        boolean isMarkedAsFavorite = false;
        if (cursor == null || cursor.getCount() == 0) {
            return false;
        }

        while(cursor.moveToNext()) {
            int serieId = cursor.getInt(SQLUtils.INDEX_COLUMN_ID);
            if (detailMovieId == serieId) {
                isMarkedAsFavorite = true;
            }
        }
        return isMarkedAsFavorite;
    }
}
