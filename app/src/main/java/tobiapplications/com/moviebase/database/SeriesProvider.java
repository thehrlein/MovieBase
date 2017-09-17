package tobiapplications.com.moviebase.database;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by Tobias on 18.09.2017.
 */

public class SeriesProvider  extends ContentProvider {
    private static final UriMatcher sUriMatcher = buildUriMatcher();
    private SeriesDbHelper mOpenHelper;

    private static final int SERIE = 200;
    private static final int SERIE_WITH_ID = 201;

    private static UriMatcher buildUriMatcher() {
        final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = SeriesContract.CONTENT_AUTHORITY;

        uriMatcher.addURI(authority, SeriesContract.SeriesEntry.TABLE_SERIES, SERIE);
        uriMatcher.addURI(authority, SeriesContract.SeriesEntry.TABLE_SERIES + "/#", SERIE_WITH_ID);

        return uriMatcher;
    }

    @Override
    public boolean onCreate() {
        mOpenHelper = new SeriesDbHelper(getContext());

        return true;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        final int match = sUriMatcher.match(uri);

        switch (match) {
            case SERIE:
                return SeriesContract.SeriesEntry.CONTENT_DIR_TYPE;
            case SERIE_WITH_ID:
                return SeriesContract.SeriesEntry.CONTENT_ITEM_TYPE;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        Cursor returnCursor;

        switch (sUriMatcher.match(uri)) {
            case SERIE:
                returnCursor = mOpenHelper.getReadableDatabase().query(
                        SeriesContract.SeriesEntry.TABLE_SERIES,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);

                return returnCursor;
            case SERIE_WITH_ID:
                returnCursor = mOpenHelper.getReadableDatabase().query(
                        SeriesContract.SeriesEntry.TABLE_SERIES,
                        projection,
                        SeriesContract.SeriesEntry._ID + " = ?",
                        new String[]{String.valueOf(ContentUris.parseId(uri))},
                        null,
                        null,
                        sortOrder);
                return returnCursor;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        Uri returnUri;

        switch (sUriMatcher.match(uri)) {
            case SERIE:
                long _id = db.insert(SeriesContract.SeriesEntry.TABLE_SERIES, null, values);
                if (_id > 0) {
                    returnUri = SeriesContract.SeriesEntry.buildSerieUri(_id);
                } else {
                    throw new SQLException("Failed to insert row into: " + uri);
                }
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;
    }

    @Override
    public int bulkInsert(@NonNull Uri uri, @NonNull ContentValues[] values) {
        return super.bulkInsert(uri, values);
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int numDeleted;

        switch (match) {
            case SERIE:
                numDeleted = db.delete(SeriesContract.SeriesEntry.TABLE_SERIES, selection, selectionArgs);
                db.execSQL("DELETE FROM SQLITE_SEQUENCE WHERE NAME = '" + SeriesContract.SeriesEntry.TABLE_SERIES + "'");
                break;
            case SERIE_WITH_ID:
                numDeleted = db.delete(SeriesContract.SeriesEntry.TABLE_SERIES,
                        SeriesContract.SeriesEntry._ID + " = ?",
                        new String[]{String.valueOf(ContentUris.parseId(uri))});
                db.execSQL("DELETE FROM SQLITE_SEQUENCE WHERE NAME = '" + SeriesContract.SeriesEntry.TABLE_SERIES + "'");
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        return numDeleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}
