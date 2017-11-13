package tobiapplications.com.moviebase.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import timber.log.Timber;

/**
 * Created by Tobias on 18.09.2017.
 */

public class SeriesDbHelper extends SQLiteOpenHelper {
    private static final String TAG = SeriesDbHelper.class.getSimpleName();

    private static final String DATABASE_NAME = "series.db";
    private static final int DATABASE_VERSION = 1;

    public SeriesDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_SERIES_TABLE = "CREATE TABLE " +
                SeriesContract.SeriesEntry.TABLE_SERIES + "(" +
                SeriesContract.SeriesEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                SeriesContract.SeriesEntry.COLUMN_ID + " TEXT NOT NULL, " +
                SeriesContract.SeriesEntry.COLUMN_TITLE + " TEXT NOT NULL, " +
                SeriesContract.SeriesEntry.COLUMN_TITLE_POSTER_PATH + " TEXT NOT NULL);";

        db.execSQL(SQL_CREATE_SERIES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Timber.d("Upgrading database from version " + oldVersion + " to " + newVersion +
                ". OLD DATA WILL BE DESTROYED");

        db.execSQL("DROP TABLE IF EXISTS " + SeriesContract.SeriesEntry.TABLE_SERIES);
        db.execSQL("DELETE FROM SQLITE_SEQUENCE WHERE NAME = '" +
                SeriesContract.SeriesEntry.TABLE_SERIES + "'");

        onCreate(db);
    }

}