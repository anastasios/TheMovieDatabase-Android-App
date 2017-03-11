package project.movies.popular.popularmovies;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MovieDetailFragment extends Fragment {
    String mTitle;
    String mOverview;
    String mPoster;
    String mReleaseDate;
    String mVoteAverage;
    @BindView(R.id.tv_overview)
    TextView mOverviewTv;
    @BindView(R.id.tv_releaseDate)
    TextView mReleaseDateTv;
    @BindView(R.id.iv_thumbnail)
    ImageView mPosterIv;
    @BindView(R.id.tv_average_vote)
    TextView mVoteAverageTv;
    @BindView(R.id.tv_title)
    TextView mTitleTv;
    private Unbinder unbinder;
    private SQLiteDatabase db;

    public MovieDetailFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Intent intentThatStartedThisActivity = getActivity().getIntent();
        setHasOptionsMenu(true);
        View view = inflater.inflate(R.layout.fragment_movie_detail, container, false);
        unbinder = ButterKnife.bind(this, view);

        MoviesDbHelper moviesDbHelper = new MoviesDbHelper(getActivity());
        db = moviesDbHelper.getWritableDatabase();

        if (intentThatStartedThisActivity.hasExtra("title")) {
            mTitle = intentThatStartedThisActivity.getStringExtra("title");
        }
        if (intentThatStartedThisActivity.hasExtra("overview")) {
            mOverview = intentThatStartedThisActivity.getStringExtra("overview");
        }
        if (intentThatStartedThisActivity.hasExtra("poster")) {
            mPoster = intentThatStartedThisActivity.getStringExtra("poster");
        }
        if (intentThatStartedThisActivity.hasExtra("releaseDate")) {
            mReleaseDate = intentThatStartedThisActivity.getStringExtra("releaseDate");
        }
        if (intentThatStartedThisActivity.hasExtra("voteAverage")) {
            mVoteAverage = intentThatStartedThisActivity.getStringExtra("voteAverage");
        }
        mOverviewTv.setText(mOverview);
        mReleaseDateTv.setText(mReleaseDate);
        Picasso.with(getActivity()).load(mPoster).into(mPosterIv);
        mVoteAverageTv.setText(mVoteAverage + "/10");
        mTitleTv.setText(mTitle);

        //addMovieToDb();
        Cursor c = db.rawQuery("SELECT * FROM movies ", null);
        if (c.moveToFirst()) {
            do {
                String column1 = c.getString(1);
                System.out.println(column1);
                String column12 = c.getString(2);
                System.out.println(column12);
                String id = c.getString(0);
                System.out.println(id);
                //Do something Here with values
            } while (c.moveToNext());
        }
        c.close();
        return view;
    }

    private boolean movieExistsToDatabase() {
        Cursor c = db.rawQuery("SELECT * FROM movies ", null);
        if (c.moveToFirst()) {
            do {
                //assing values
                String columnTitle = c.getString(1);
                if (columnTitle.equals(mTitle)) {
                    return true;
                }
            } while (c.moveToNext());
        }
        c.close();
        return false;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        db.close();
    }

    private long addMovieToDb() {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseContract.MoviesEntry.MOVIE_TITLE, mTitle);
        contentValues.put(DatabaseContract.MoviesEntry.MOVIE_POSTER, mPoster);
        contentValues.put(DatabaseContract.MoviesEntry.MOVIE_OVERVIEW, mOverview);
        contentValues.put(DatabaseContract.MoviesEntry.MOVIE_RELEASE_DATE, mReleaseDate);
        contentValues.put(DatabaseContract.MoviesEntry.MOVIE_VOTE_AVERAGE, mVoteAverage);
        return db.insert(DatabaseContract.MoviesEntry.TABLE_NAME, null, contentValues);
    }

    private void removeMovieFromDb() {
        String title = String.format("\"%s\"", mTitle);
        db.delete(DatabaseContract.MoviesEntry.TABLE_NAME, DatabaseContract.MoviesEntry.MOVIE_TITLE + "=" + title, null);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_movie_details, menu);
        super.onCreateOptionsMenu(menu, inflater);
        menu.findItem(R.id.non_favorite_icon).setVisible(!movieExistsToDatabase());
        menu.findItem(R.id.favorite_icon).setVisible(movieExistsToDatabase());

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.non_favorite_icon) {
            if (!movieExistsToDatabase()) {
                addMovieToDb();
                Toast.makeText(getActivity(), "Added movie to favorites", Toast.LENGTH_SHORT).show();
            }
        }
        if (id == R.id.favorite_icon) {
            removeMovieFromDb();
            Toast.makeText(getActivity(), "Removed movie from favorites", Toast.LENGTH_SHORT).show();
        }
        getActivity().supportInvalidateOptionsMenu();
        MainActivityFragment.mAdapter.notifyDataSetChanged();
        return super.onOptionsItemSelected(item);
    }
}
