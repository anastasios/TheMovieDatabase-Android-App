package project.movies.popular.popularmovies;

import android.provider.BaseColumns;

public class DatabaseContract {
    private DatabaseContract() {}

    public static class MoviesEntry implements BaseColumns {
        public static final String TABLE_NAME = "movies";
        public static final String MOVIE_TITLE = "title";
        public static final String MOVIE_POSTER = "poster";
        public static final String MOVIE_OVERVIEW = "overview";
        public static final String MOVIE_RELEASE_DATE = "releaseDate";
        public static final String MOVIE_VOTE_AVERAGE = "movie_average";
    }
}
