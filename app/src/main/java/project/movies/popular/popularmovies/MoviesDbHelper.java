package project.movies.popular.popularmovies;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import static project.movies.popular.popularmovies.DatabaseContract.*;

public class MoviesDbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "movies.db";
    private static final int DATABASE_VERSION = 1;

    public MoviesDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_WAITLIST_TABLE = "CREATE TABLE " +
                MoviesEntry.TABLE_NAME + "(" +
                MoviesEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                MoviesEntry.MOVIE_TITLE + " TEXT NOT NULL," +
                MoviesEntry.MOVIE_POSTER + " TEXT NOT NULL," +
                MoviesEntry.MOVIE_OVERVIEW + " TEXT NOT NULL," +
                MoviesEntry.MOVIE_RELEASE_DATE + " TEXT NOT NULL," +
                MoviesEntry.MOVIE_ID + " TEXT NOT NULL," +
                MoviesEntry.MOVIE_VOTE_AVERAGE + " TEXT NOT NULL" + " );";
        db.execSQL(SQL_CREATE_WAITLIST_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + MoviesEntry.TABLE_NAME);
        onCreate(db);
    }
}
