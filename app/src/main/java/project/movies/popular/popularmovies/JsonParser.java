package project.movies.popular.popularmovies;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonParser {
    static List<MovieDataModel> list;

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

            MovieDataModel movieDataModel = new MovieDataModel();
            movieDataModel.setPoster(poster);
            movieDataModel.setThumbnail(poster);
            movieDataModel.setOverview(overview);
            movieDataModel.setReleaseDate(releaseDate);
            movieDataModel.setVoteAverage(voteAverage);
            movieDataModel.setTitle(title);
            list.add(movieDataModel);
        }
        return list;
    }
}
