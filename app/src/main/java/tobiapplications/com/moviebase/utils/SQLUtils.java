package tobiapplications.com.moviebase.utils;

import tobiapplications.com.moviebase.database.MoviesContract;

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
}
