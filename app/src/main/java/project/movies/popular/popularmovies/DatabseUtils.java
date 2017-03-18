package project.movies.popular.popularmovies;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class DatabseUtils {

    List<MovieDataModel> generateListFromDB(Context context) {
        List<MovieDataModel> moviesList = new ArrayList<>();
        MoviesDbHelper moviesDbHelper = new MoviesDbHelper(context);
        SQLiteDatabase db = moviesDbHelper.getWritableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + DatabaseContract.MoviesEntry.TABLE_NAME, null);
        if (c.moveToFirst()) {
            do {
                String columnTitle = c.getString(c.getColumnIndex(DatabaseContract.MoviesEntry.MOVIE_TITLE));
                String columnPoster = c.getString(c.getColumnIndex(DatabaseContract.MoviesEntry.MOVIE_POSTER));
                String columnOverview = c.getString(c.getColumnIndex(DatabaseContract.MoviesEntry.MOVIE_OVERVIEW));
                String columnReleaseDate = c.getString(c.getColumnIndex(DatabaseContract.MoviesEntry.MOVIE_RELEASE_DATE));
                String columnVoteAverage = c.getString(c.getColumnIndex(DatabaseContract.MoviesEntry.MOVIE_VOTE_AVERAGE));
                String columnId = c.getString(c.getColumnIndex(DatabaseContract.MoviesEntry.MOVIE_ID));

                MovieDataModel movieDataModel = new MovieDataModel();
                movieDataModel.setTitle(columnTitle);
                movieDataModel.setPosterForDB(columnPoster);
                movieDataModel.setOverview(columnOverview);
                movieDataModel.setReleaseDate(columnReleaseDate);
                movieDataModel.setVoteAverage(columnVoteAverage);
                movieDataModel.setId(columnId);
                moviesList.add(movieDataModel);
            } while (c.moveToNext());
        }
        c.close();
        db.close();
        return moviesList;
    }
}
