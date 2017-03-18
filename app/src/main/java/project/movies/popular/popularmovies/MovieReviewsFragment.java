package project.movies.popular.popularmovies;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MovieReviewsFragment extends Fragment {
    @BindView(R.id.rv_movie_reviews)
    RecyclerView recyclerViewMovieReviews;
    MovieReviewsAdapter movieReviewsAdapter;
    private Unbinder unbinder;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie_reviews, container, false);
        unbinder = ButterKnife.bind(this, view);
       /* LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerViewMovieReviews.setLayoutManager(linearLayoutManager);
        recyclerViewMovieReviews.setHasFixedSize(true);*/
        recyclerViewMovieReviews.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerViewMovieReviews.setAdapter(movieReviewsAdapter);
        new MovieDetailsReviews().execute();

        return view;
    }

    class MovieDetailsReviews extends AsyncTask<Void, Void, List<MovieReviewsDataModel>> {

        @Override
        protected List<MovieReviewsDataModel> doInBackground(Void... params) {
            String jsonResponseString = new NetworkUtils().run("http://api.themoviedb.org/3/movie/" + MovieDetailFragment.mId +
                    "/reviews?api_key=48b116b4a2db9076fc612beb2e93aa6d");
            List<MovieReviewsDataModel> movieReviewsDataModels = new ArrayList<>();
            try {
                movieReviewsDataModels = JsonParser.jsonParserMovieReviews(jsonResponseString);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return movieReviewsDataModels;
        }

        @Override
        protected void onPostExecute(List<MovieReviewsDataModel> movieReviews) {
            if (!movieReviews.isEmpty()) {
                movieReviewsAdapter = new MovieReviewsAdapter(getActivity(), movieReviews);
                recyclerViewMovieReviews.setAdapter(movieReviewsAdapter);
            }
        }
    }
}
