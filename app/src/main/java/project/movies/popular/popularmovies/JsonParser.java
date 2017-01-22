package project.movies.popular.popularmovies;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonParser {
    static List<MovieDataModel> list = new ArrayList<>();

    public static List<MovieDataModel> jsonParser(String inputJson) throws JSONException {
        JSONObject jsonObject = new JSONObject(inputJson);
        JSONArray results = jsonObject.getJSONArray("results");
        for (int i = 0; i < results.length(); i++) {
            JSONObject movie = results.getJSONObject(i);
            String poster = movie.getString("poster_path");
            MovieDataModel movieDataModel = new MovieDataModel();
            movieDataModel.setPoster(poster);
            list.add(movieDataModel);
            System.out.println(poster);
        }
        System.out.println(results.length());
        return list;
    }
}
