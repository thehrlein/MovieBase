package tobiapplications.com.moviebase.viewholder.detail;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import tobiapplications.com.moviebase.R;
import tobiapplications.com.moviebase.model.detail.views.SimilarMoviesView;
import tobiapplications.com.moviebase.model.overview.MovieOverviewModel;
import tobiapplications.com.moviebase.ui.detail.DetailActivity;
import tobiapplications.com.moviebase.utils.Constants;
import tobiapplications.com.moviebase.utils.NetworkUtils;

/**
 * Created by Tobias on 15.06.2017.
 */

public class SimilarMoviesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    private LinearLayout similarMoviesLayout;
    private Context context;

    public SimilarMoviesViewHolder(View itemView, Context context) {
        super(itemView);
        this.context = context;
        similarMoviesLayout = (LinearLayout) itemView.findViewById(R.id.similar_movies_layout);
    }

    public void setSimilarMovies(SimilarMoviesView similarMovies) {
        for (MovieOverviewModel model : similarMovies.getMovies()) {
            ImageView moviePoster = new ImageView(context);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(200, 300);
            moviePoster.setScaleType(ImageView.ScaleType.FIT_XY);
            moviePoster.setLayoutParams(params);
            Picasso.with(context).load(NetworkUtils.getFullImageUrl(model.getTitleImagePath())).into(moviePoster);
            moviePoster.setOnClickListener(this);
            similarMoviesLayout.addView(moviePoster);
        }
    }

    @Override
    public void onClick(View v) {
        Intent openMovieDetails = new Intent(context, DetailActivity.class);
        openMovieDetails.putExtra(Constants.CLICKED_MOVIE, getAdapterPosition());
        context.startActivity(openMovieDetails);
    }
}
