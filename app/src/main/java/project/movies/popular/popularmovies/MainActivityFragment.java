package project.movies.popular.popularmovies;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class MainActivityFragment extends Fragment {
    private static final int NUM_LIST_ITEMS = 100;
    private MoviesAdapter mAdapter;
    private RecyclerView mMoviesList;
    private Context mContext;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        mContext = getActivity();
        mMoviesList = (RecyclerView) view.findViewById(R.id.rv_movies);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 2);
        mMoviesList.setLayoutManager(gridLayoutManager);
        mMoviesList.setHasFixedSize(true);

        new MovieQueryTask().execute();
        return view;
    }

    class MovieQueryTask extends AsyncTask<Void, Void, List<MovieDataModel>> {

        @Override
        protected List<MovieDataModel> doInBackground(Void... params) {
            HttpURLConnection httpURLConnection = null;
            BufferedReader bufferedReader = null;
            URL url = null;
            String jsonString = null;

            try {
                url = new URL("http://api.themoviedb.org/3/movie/popular?api_key=48b116b4a2db9076fc612beb2e93aa6d");
                httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("GET");
                httpURLConnection.connect();

                InputStream inputStream = httpURLConnection.getInputStream();
                StringBuffer stringBuffer = new StringBuffer();

                if (inputStream == null) {
                    return null;
                }

                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String line;

                while ((line = bufferedReader.readLine()) != null) {
                    stringBuffer.append(line + "\n");
                }

                if (stringBuffer.length() == 0) {
                    return null;
                }

                jsonString = stringBuffer.toString();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (httpURLConnection != null) {
                    httpURLConnection.disconnect();
                }
                if (bufferedReader != null) {
                    try {
                        bufferedReader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (jsonString != null) {
                    try {
                        return JsonParser.jsonParser(jsonString);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(List<MovieDataModel> dataModelList) {
            super.onPostExecute(dataModelList);
            mAdapter = new MoviesAdapter(mContext, dataModelList);
            mMoviesList.setAdapter(mAdapter);
        }
    }
}
