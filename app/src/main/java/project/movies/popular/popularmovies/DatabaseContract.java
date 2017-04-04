package project.movies.popular.popularmovies;

import android.net.Uri;
import android.provider.BaseColumns;

public class DatabaseContract {
    private DatabaseContract() {}

    public static final String AUTHORITY = "project.movies.popular.popularmovies";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);
    public static final String PATH = "movies";

    public static class MoviesEntry implements BaseColumns {
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH).build();
        public static final String TABLE_NAME = "movies";
        public static final String MOVIE_TITLE = "title";
        public static final String MOVIE_POSTER = "poster";
        public static final String MOVIE_OVERVIEW = "overview";
        public static final String MOVIE_RELEASE_DATE = "releaseDate";
        public static final String MOVIE_VOTE_AVERAGE = "movie_average";
        public static final String MOVIE_ID = "movie_id";
    }
}
