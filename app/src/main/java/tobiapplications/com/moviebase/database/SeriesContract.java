package tobiapplications.com.moviebase.database;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Tobias on 17.09.2017.
 */

public class SeriesContract {

    public static final String CONTENT_AUTHORITY = "tobiapplications.com.moviebase.series";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final class SeriesEntry implements BaseColumns {

        public static final String TABLE_SERIES = "serie";
        public static final String _ID = "_ID";
        public static final String COLUMN_ID = "serieId";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_TITLE_POSTER_PATH = "titlePosterPath";

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                .appendPath(TABLE_SERIES).build();

        public static final String CONTENT_DIR_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE
                + "/" + CONTENT_AUTHORITY + "/" + TABLE_SERIES;
        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE
                + "/" + CONTENT_AUTHORITY + "/" + TABLE_SERIES;

        public static Uri buildSerieUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }

}
