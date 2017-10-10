package tobiapplications.com.moviebase.database;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Tobias on 23.03.2017.
 */

public class MoviesContract {

    public static final String CONTENT_AUTHORITY = "tobiapplications.com.moviebase.movies";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final class MovieEntry implements BaseColumns {

        public static final String TABLE_MOVIES = "movie";
        public static final String _ID = "_ID";
        public static final String COLUMN_ID = "movieid";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_TITLE_POSTER_PATH = "titleimagepath";

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                .appendPath(TABLE_MOVIES).build();

        public static final String CONTENT_DIR_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE
                + "/" + CONTENT_AUTHORITY + "/" + TABLE_MOVIES;
        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE
                + "/" + CONTENT_AUTHORITY + "/" + TABLE_MOVIES;

        public static Uri buildMovieUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }

}
