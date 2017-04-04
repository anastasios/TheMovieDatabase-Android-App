package project.movies.popular.popularmovies;


import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;

import static project.movies.popular.popularmovies.DatabaseContract.MoviesEntry.TABLE_NAME;

public class TaskContentProvider extends ContentProvider {
    private MoviesDbHelper moviesDbHelper;

    @Override
    public boolean onCreate() {
        Context context = getContext();
        moviesDbHelper = new MoviesDbHelper(context);
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        final SQLiteDatabase db = moviesDbHelper.getWritableDatabase();
        Cursor cursor;
        cursor = db.query(TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null);
        if (cursor != null) {
            cursor.setNotificationUri(getContext().getContentResolver(), DatabaseContract.MoviesEntry.CONTENT_URI);
        } else {
            throw new UnsupportedOperationException("Unknown uri: " + DatabaseContract.MoviesEntry.CONTENT_URI);
        }
        return cursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        final SQLiteDatabase db = moviesDbHelper.getWritableDatabase();
        Uri returnUri;
        long id = db.insert(TABLE_NAME, null, values);
        if (id > 0) {
            returnUri = ContentUris.withAppendedId(DatabaseContract.MoviesEntry.CONTENT_URI, id);
        } else {
            throw new SQLException("Failed to insert row");
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = moviesDbHelper.getWritableDatabase();
        int movieDelete = db.delete(TABLE_NAME, selection, null);
        if (movieDelete != 0) {
            getContext().getContentResolver().notifyChange(DatabaseContract.MoviesEntry.CONTENT_URI, null);
        } else {
            throw new UnsupportedOperationException("Unknown uri: " + DatabaseContract.MoviesEntry.CONTENT_URI);
        }
        return movieDelete;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }
}
