package project.movies.popular.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MainActivityFragment extends Fragment {
    static public MoviesAdapter mAdapter;
    @BindView(R.id.rv_movies)
    RecyclerView mMoviesRecyclerView;
    private Context mContext;
    @BindView(R.id.loading_indicator)
    ProgressBar mLoadingIndicator;
    private Unbinder unbinder;
    static final String API_KEY = "ENTER_YOUR_KEY_HERE";
    static final String MOVIE_URL_POPULAR = String.format("http://api.themoviedb.org/3/movie/popular?api_key=%s", API_KEY);
    static final String MOVIE_URL_TOP_RATED = String.format("http://api.themoviedb.org/3/movie/top_rated?api_key=%s", API_KEY);

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        unbinder = ButterKnife.bind(this, view);
        mContext = getActivity();
        GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, calculateNoOfColumns(mContext));
        mMoviesRecyclerView.setLayoutManager(gridLayoutManager);
        mMoviesRecyclerView.setHasFixedSize(true);
        if (isOnline()) {
            new MovieQueryTask().execute(MOVIE_URL_TOP_RATED);
        } else displayToastMessage(R.string.connectivity_error);
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_movie_list, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.most_popular && isOnline()) {
            new MovieQueryTask().execute(MOVIE_URL_POPULAR);
        } else displayToastMessage(R.string.connectivity_error);
        if (id == R.id.top_rated && isOnline()) {
            new MovieQueryTask().execute(MOVIE_URL_TOP_RATED);
        } else displayToastMessage(R.string.connectivity_error);
        if (id == R.id.my_favorite_list && isOnline()) {
            final List<MovieDataModel> movieDataModelList = new DatabseUtils().generateListFromDB(mContext);
            mAdapter = createClickableMovieAdapter(mContext, movieDataModelList);
            mMoviesRecyclerView.setAdapter(mAdapter);
        }
        return super.onOptionsItemSelected(item);
    }

    private MoviesAdapter createClickableMovieAdapter(final Context context, final List<MovieDataModel> movieDataModelList) {
        return new MoviesAdapter(mContext, movieDataModelList, new MoviesAdapter.ListItemClickListener() {
            @Override
            public void onListItemClick(int clickedItemIndex) {
                Intent intent = new Intent(context, MovieDetail.class);
                intent.putExtra("title", movieDataModelList.get(clickedItemIndex).getTitle());
                intent.putExtra("overview", movieDataModelList.get(clickedItemIndex).getOverview());
                intent.putExtra("poster", movieDataModelList.get(clickedItemIndex).getPoster());
                intent.putExtra("releaseDate", movieDataModelList.get(clickedItemIndex).getReleaseDate());
                intent.putExtra("voteAverage", movieDataModelList.get(clickedItemIndex).getVoteAverage());
                intent.putExtra("id", movieDataModelList.get(clickedItemIndex).getId());
                startActivity(intent);
            }
        });
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
            Log.d("Network", "onPostExecute");
            mLoadingIndicator.setVisibility(View.INVISIBLE);
            if (dataModelList == null) {
                displayToastMessage(R.string.data_download_error);
            } else {
                super.onPostExecute(dataModelList);
                mAdapter = new MoviesAdapter(mContext, dataModelList, new MoviesAdapter.ListItemClickListener() {
                    @Override
                    public void onListItemClick(int clickedItemIndex) {
                        Intent intent = new Intent(mContext, MovieDetail.class);
                        intent.putExtra("title", dataModelList.get(clickedItemIndex).getTitle());
                        intent.putExtra("overview", dataModelList.get(clickedItemIndex).getOverview());
                        intent.putExtra("poster", dataModelList.get(clickedItemIndex).getPoster());
                        intent.putExtra("releaseDate", dataModelList.get(clickedItemIndex).getReleaseDate());
                        intent.putExtra("voteAverage", dataModelList.get(clickedItemIndex).getVoteAverage());
                        intent.putExtra("id", dataModelList.get(clickedItemIndex).getId());
                        startActivity(intent);
                    }
                });
                mMoviesRecyclerView.setAdapter(mAdapter);
                mLoadingIndicator.setVisibility(View.INVISIBLE);
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    public static int calculateNoOfColumns(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        int noOfColumns = (int) (dpWidth / 180);
        return noOfColumns;
    }

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    public void displayToastMessage(int IdOfString) {
        String toastMessage = getString(IdOfString);
        Toast.makeText(mContext, toastMessage, Toast.LENGTH_LONG).show();
    }
}
