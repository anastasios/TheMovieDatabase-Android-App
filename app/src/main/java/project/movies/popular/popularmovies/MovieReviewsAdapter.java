package project.movies.popular.popularmovies;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieReviewsAdapter extends RecyclerView.Adapter<MovieReviewsAdapter.MovieReviewsViewHolder> {
    private List<MovieReviewsDataModel> dataModelList;
    private Context context;

    public MovieReviewsAdapter(Context context, List<MovieReviewsDataModel> dataModelList) {
        this.context = context;
        this.dataModelList = dataModelList;
    }

    @Override
    public MovieReviewsViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForMovieListItem = R.layout.movie_reviews_item;
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = layoutInflater.inflate(layoutIdForMovieListItem, viewGroup, shouldAttachToParentImmediately);
        MovieReviewsViewHolder movieReviewsViewHolder = new MovieReviewsViewHolder(view);
        return movieReviewsViewHolder;
    }

    @Override
    public void onBindViewHolder(MovieReviewsViewHolder holder, int position) {
        MovieReviewsDataModel dataTest = dataModelList.get(position);
        holder.movieReview.setText(dataTest.getContent());
    }

    @Override
    public int getItemCount() {
        return dataModelList == null ? 0 : dataModelList.size();
    }

    class MovieReviewsViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.movie_review)
        public TextView movieReview;

        public MovieReviewsViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
