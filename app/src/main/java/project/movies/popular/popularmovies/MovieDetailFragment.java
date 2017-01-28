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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MovieDetailFragment extends Fragment {
    String mTitle;
    String mOverview;
    String mThumbnail;
    String mReleaseDate;
    String mVoteAverage;
    @BindView(R.id.tv_overview) TextView mOverviewTv;
    @BindView(R.id.tv_releaseDate) TextView mReleaseDateTv;
    @BindView(R.id.iv_thumbnail) ImageView mThumbnailIv;
    @BindView(R.id.tv_average_vote) TextView mVoteAverageTv;
    @BindView(R.id.tv_title) TextView mTitleTv;
    private Unbinder unbinder;

    public MovieDetailFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Intent intentThatStartedThisActivity = getActivity().getIntent();
        View view = inflater.inflate(R.layout.fragment_movie_detail, container, false);
        unbinder = ButterKnife.bind(this, view);
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
        mOverviewTv.setText(mOverview);
        mReleaseDateTv.setText(mReleaseDate);
        Picasso.with(getActivity()).load(mThumbnail).into(mThumbnailIv);
        mVoteAverageTv.setText(mVoteAverage);
        mTitleTv.setText(mTitle);
        return view;
    }

    @Override public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
