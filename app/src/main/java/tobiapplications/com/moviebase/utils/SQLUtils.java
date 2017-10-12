package tobiapplications.com.moviebase.utils;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v4.content.LocalBroadcastManager;

import tobiapplications.com.moviebase.database.MoviesContract;
import tobiapplications.com.moviebase.database.SeriesContract;
import tobiapplications.com.moviebase.model.detail.MovieDetailResponse;
import tobiapplications.com.moviebase.model.detail.SeriesDetailResponse;

/**
 * Created by Tobias on 12.06.2017.
 */

public class SQLUtils {

    public static final int INDEX_COLUMN_ID = 0;
    public static final int INDEX_COLUMN_TITLE = 1;
    public static final int INDEX_COLUMN_TITLE_IMAGE_PATH = 2;

    public static String[] selectAllMovies = new String[] {
            MoviesContract.MovieEntry.COLUMN_ID,
            MoviesContract.MovieEntry.COLUMN_TITLE,
            MoviesContract.MovieEntry.COLUMN_TITLE_POSTER_PATH
    };

    public static String[] selectAllSeries = new String[] {
            SeriesContract.SeriesEntry.COLUMN_ID,
            SeriesContract.SeriesEntry.COLUMN_TITLE,
            SeriesContract.SeriesEntry.COLUMN_TITLE_POSTER_PATH
    };

    public static void insertMovieIntoDatabase(Context context, MovieDetailResponse movie) {
        ContentValues values = new ContentValues();
        values.put(MoviesContract.MovieEntry.COLUMN_ID, movie.getId());
        values.put(MoviesContract.MovieEntry.COLUMN_TITLE, movie.getTitle());
        values.put(MoviesContract.MovieEntry.COLUMN_TITLE_POSTER_PATH, movie.getTitleImagePath());

        insertMovieIntoDatabase(context, values);
    }
    private static void insertMovieIntoDatabase(Context context, ContentValues values) {
        context.getContentResolver().insert(MoviesContract.MovieEntry.CONTENT_URI, values);
        Intent informOwnFavoriteActivity = new Intent(Constants.MOVIE_INSERT_TO_DATABASE);
        LocalBroadcastManager.getInstance(context).sendBroadcast(informOwnFavoriteActivity);
    }

    public static void insertSerieIntoDatabase(Context context, SeriesDetailResponse serie) {
        ContentValues values = new ContentValues();
        values.put(SeriesContract.SeriesEntry.COLUMN_ID, serie.getId());
        values.put(SeriesContract.SeriesEntry.COLUMN_TITLE, serie.getName());
        values.put(SeriesContract.SeriesEntry.COLUMN_TITLE_POSTER_PATH, serie.getPosterPath());

        insertSerieIntoDatabase(context, values);
    }

    private static void insertSerieIntoDatabase(Context context, ContentValues values) {
        context.getContentResolver().insert(SeriesContract.SeriesEntry.CONTENT_URI, values);
        Intent informOwnFavoriteActivity = new Intent(Constants.SERIE_INSERT_TO_DATABASE);
        LocalBroadcastManager.getInstance(context).sendBroadcast(informOwnFavoriteActivity);
    }

    public static void deleteFromDataBase(Context context, int id, int overviewType) {
        if (overviewType == Constants.Type.MOVIES) {
            deleteCurrentMovieFromFavoriteDatabase(context, id);
        } else if (overviewType == Constants.Type.SERIES) {
            deleteCurrentSerieFromFavoriteDatabase(context, id);
        }
    }

    private static void deleteCurrentMovieFromFavoriteDatabase(Context context, int movieId) {
        context.getContentResolver().delete(
                MoviesContract.MovieEntry.CONTENT_URI,
                MoviesContract.MovieEntry.COLUMN_ID + " = ?",
                new String[]{String.valueOf(movieId)});
    }

    private static void deleteCurrentSerieFromFavoriteDatabase(Context context, int serieId) {
        context.getContentResolver().delete(
                SeriesContract.SeriesEntry.CONTENT_URI,
                SeriesContract.SeriesEntry.COLUMN_ID + " = ?",
                new String[]{String.valueOf(serieId)});
    }

    private static Cursor getAllFavoriteMovies(Context context) {
        return context.getContentResolver().query(
                MoviesContract.MovieEntry.CONTENT_URI,
                SQLUtils.selectAllMovies,
                null,
                null,
                null);
    }

    private static Cursor getAllFavoriteSerie(Context context) {
        return context.getContentResolver().query(
                SeriesContract.SeriesEntry.CONTENT_URI,
                SQLUtils.selectAllSeries,
                null,
                null,
                null);
    }

    public static boolean checkIfMovieIsMarkedAsFavorite(Context context, int detailMovieId) {
        Cursor cursor = getAllFavoriteMovies(context);
        if (cursor == null || cursor.getCount() == 0) {
            return false;
        }

        while(cursor.moveToNext()) {
            int movieId = cursor.getInt(SQLUtils.INDEX_COLUMN_ID);
            if (detailMovieId == movieId) {
                return true;
            }
        }
        return false;
    }

    public static boolean checkIfSerieIsMarkedAsFavorite(Context context, int detailMovieId) {
        Cursor cursor = getAllFavoriteSerie(context);
        if (cursor == null || cursor.getCount() == 0) {
            return false;
        }

        while(cursor.moveToNext()) {
            int serieId = cursor.getInt(SQLUtils.INDEX_COLUMN_ID);
            if (detailMovieId == serieId) {
                return true;
            }
        }
        return false;
    }
}