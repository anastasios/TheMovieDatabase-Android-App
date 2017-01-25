package project.movies.popular.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class MovieDetailFragment extends Fragment {
    String mTitle;
    String mOverview;
    String mThumbnail;
    String mReleaseDate;
    String mVoteAverage;
    TextView mOverviewTv;
    TextView mReleaseDateTv;
    ImageView mThumbnailIv;
    TextView mVoteAverageTv;
    TextView mTitleTv;

    public MovieDetailFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Intent intentThatStartedThisActivity = getActivity().getIntent();
        View view = inflater.inflate(R.layout.fragment_movie_detail, container, false);
        if (intentThatStartedThisActivity.hasExtra("title")) {
            mTitle = intentThatStartedThisActivity.getStringExtra("title");
        }
        if (intentThatStartedThisActivity.hasExtra("overview")) {
            mOverview = intentThatStartedThisActivity.getStringExtra("overview");
        }
        if (intentThatStartedThisActivity.hasExtra("thumbnail")) {
            mThumbnail = intentThatStartedThisActivity.getStringExtra("thumbnail");
        }
        if (intentThatStartedThisActivity.hasExtra("releaseDate")) {
            mReleaseDate = intentThatStartedThisActivity.getStringExtra("releaseDate");
        }
        if (intentThatStartedThisActivity.hasExtra("voteAverage")) {
            mVoteAverage = intentThatStartedThisActivity.getStringExtra("voteAverage");
        }
        mOverviewTv = (TextView) view.findViewById(R.id.tv_overview);
        mOverviewTv.setText(mOverview);

        mReleaseDateTv = (TextView) view.findViewById(R.id.tv_releaseDate);
        mReleaseDateTv.setText(mReleaseDate);

        mThumbnailIv = (ImageView) view.findViewById(R.id.iv_thumbnail);
        Picasso.with(getActivity()).load(mThumbnail).into(mThumbnailIv);

        mVoteAverageTv = (TextView) view.findViewById(R.id.tv_average_vote);
        mVoteAverageTv.setText(mVoteAverage);

        mTitleTv = (TextView) view.findViewById(R.id.tv_title);
        mTitleTv.setText(mTitle);
        return view;
    }
}
