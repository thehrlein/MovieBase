package tobiapplications.com.moviebase.viewholder.detail;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import tobiapplications.com.moviebase.R;
import tobiapplications.com.moviebase.model.detail.items.SimilarMoviesItem;
import tobiapplications.com.moviebase.model.general_items.MoviePosterItem;
import tobiapplications.com.moviebase.model.overview.MovieOverviewModel;
import tobiapplications.com.moviebase.ui.detail.DetailActivity;
import tobiapplications.com.moviebase.ui.general_views.MoviePosterView;
import tobiapplications.com.moviebase.utils.Constants;
import tobiapplications.com.moviebase.utils.NetworkUtils;

/**
 * Created by Tobias on 15.06.2017.
 */

public class SimilarMoviesViewHolder extends RecyclerView.ViewHolder {

    private LinearLayout similarMoviesLayout;
    private Context context;
    private ArrayList<MoviePosterItem> movies;

    public SimilarMoviesViewHolder(View itemView, Context context) {
        super(itemView);
        this.context = context;
        similarMoviesLayout = (LinearLayout) itemView.findViewById(R.id.similar_movies_layout);
    }

    public void setSimilarMovies(SimilarMoviesItem similarMovies) {
        this.movies = similarMovies.getMovies();
        for (MoviePosterItem model : movies) {

            MoviePosterView posterView = new MoviePosterView(context);
            similarMoviesLayout.addView(posterView);

            posterView.setMovieInformation(model, 600, 400);
            posterView.disablePopupDots();
            posterView.setOnClickListener((View v)-> openDetails(model.getId()));
        }
}

    private void openDetails(int id) {
        Intent openMovieDetails = new Intent(context, DetailActivity.class);
        openMovieDetails.putExtra(Constants.CLICKED_MOVIE, id);
        context.startActivity(openMovieDetails);
    }
}
