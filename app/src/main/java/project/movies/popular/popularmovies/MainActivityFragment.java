package project.movies.popular.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

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
    private MoviesAdapter mAdapter;
    private RecyclerView mMoviesList;
    private Context mContext;
    private ProgressBar mLoadingIndicator;
    private Toast mToast;
    private static final String API_KEY = "YOUR_API_KEY_HERE";
    static final String MOVIE_URL_POPULAR = String.format("http://api.themoviedb.org/3/movie/popular?api_key=%s", API_KEY);
    static final String MOVIE_URL_TOP_RATED = String.format("http://api.themoviedb.org/3/movie/top_rated?api_key=%s", API_KEY);

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        mContext = getActivity();
        mMoviesList = (RecyclerView) view.findViewById(R.id.rv_movies);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 2);
        mMoviesList.setLayoutManager(gridLayoutManager);
        mMoviesList.setHasFixedSize(true);
        mLoadingIndicator = (ProgressBar) view.findViewById(R.id.loading_indicator);
        new MovieQueryTask().execute(MOVIE_URL_TOP_RATED);
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_movie_detail, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.most_popular) {
            new MovieQueryTask().execute(MOVIE_URL_POPULAR);
        }
        if (id == R.id.top_rated) {
            new MovieQueryTask().execute(MOVIE_URL_TOP_RATED);
        }
        return super.onOptionsItemSelected(item);
    }

    class MovieQueryTask extends AsyncTask<String, Void, List<MovieDataModel>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mLoadingIndicator.setVisibility(View.VISIBLE);
        }

        @Override
        protected List<MovieDataModel> doInBackground(String... URL) {
            HttpURLConnection httpURLConnection = null;
            BufferedReader bufferedReader = null;
            URL url = null;
            String jsonString = null;

            try {
                url = new URL(URL[0]);
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
        protected void onPostExecute(final List<MovieDataModel> dataModelList) {
            mLoadingIndicator.setVisibility(View.INVISIBLE);
            if (dataModelList == null) {
                String toastMessage = "Please check your internet connection";
                mToast = Toast.makeText(mContext, toastMessage, Toast.LENGTH_LONG);
                mToast.show();
            } else {
                super.onPostExecute(dataModelList);
                mAdapter = new MoviesAdapter(mContext, dataModelList, new MoviesAdapter.ListItemClickListener() {
                    @Override
                    public void onListItemClick(int clickedItemIndex) {
                        Intent intent = new Intent(mContext, MovieDetail.class);
                        intent.putExtra("title", dataModelList.get(clickedItemIndex).getTitle());
                        intent.putExtra("overview", dataModelList.get(clickedItemIndex).getOverview());
                        intent.putExtra("thumbnail", dataModelList.get(clickedItemIndex).getThumbnail());
                        intent.putExtra("releaseDate", dataModelList.get(clickedItemIndex).getReleaseDate());
                        intent.putExtra("voteAverage", dataModelList.get(clickedItemIndex).getVoteAverage());
                        startActivity(intent);
                    }
                });
                mMoviesList.setAdapter(mAdapter);
                mLoadingIndicator.setVisibility(View.INVISIBLE);
            }
        }
    }
}
