package project.movies.popular.popularmovies;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonParser {
    static List<MovieDataModel> list;
    static List<MovieReviewsDataModel> movieReviewsDataModels;

    public static List<MovieDataModel> jsonParser(String inputJson) throws JSONException {
        list = new ArrayList<>();
        JSONObject jsonObject = new JSONObject(inputJson);
        JSONArray results = jsonObject.getJSONArray("results");
        for (int i = 0; i < results.length(); i++) {
            JSONObject movie = results.getJSONObject(i);
            String poster = movie.getString("poster_path");
            String releaseDate = movie.getString("release_date");
            String voteAverage = movie.getString("vote_average");
            String overview = movie.getString("overview");
            String title = movie.getString("title");
            String id = movie.getString("id");

            MovieDataModel movieDataModel = new MovieDataModel();
            movieDataModel.setPoster(poster);
            movieDataModel.setPoster(poster);
            movieDataModel.setOverview(overview);
            movieDataModel.setReleaseDate(releaseDate);
            movieDataModel.setVoteAverage(voteAverage);
            movieDataModel.setTitle(title);
            movieDataModel.setId(id);
            list.add(movieDataModel);
        }
        return list;
    }

    public static String jsonParserMovieTrailer(String inputJsonMovieTrailer) throws JSONException {
        JSONObject jsonObject = new JSONObject(inputJsonMovieTrailer);
        JSONArray results = jsonObject.getJSONArray("results");
        JSONObject trailerDetails = results.getJSONObject(0);
        String trailerKey = trailerDetails.getString("key");
        return trailerKey;
    }

    public static List<MovieReviewsDataModel> jsonParserMovieReviews(String inputJsonMovieReviews) throws JSONException {
        movieReviewsDataModels = new ArrayList<>();
        JSONObject jsonObject = new JSONObject(inputJsonMovieReviews);
        JSONArray results = jsonObject.getJSONArray("results");
        for (int i = 0; i < results.length(); i++) {
            JSONObject movie = results.getJSONObject(i);
            String movieReview = movie.getString("content");

            MovieReviewsDataModel movieReviewsDataModel = new MovieReviewsDataModel();
            movieReviewsDataModel.setContent(movieReview);
            movieReviewsDataModels.add(movieReviewsDataModel);
        }
        return movieReviewsDataModels;
    }
}
