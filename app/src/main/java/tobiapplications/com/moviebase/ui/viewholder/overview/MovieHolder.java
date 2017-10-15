package tobiapplications.com.moviebase.ui.viewholder.overview;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import tobiapplications.com.moviebase.R;
import tobiapplications.com.moviebase.databinding.ItemMovieBinding;
import tobiapplications.com.moviebase.listener.OnMovieClickListener;
import tobiapplications.com.moviebase.model.general_items.MoviePosterItem;
import tobiapplications.com.moviebase.model.overview.PosterOverviewItem;
import tobiapplications.com.moviebase.ui.views.MoviePosterView;

/**
 * Created by Tobias on 15.06.2017.
 */

public class MovieHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private ItemMovieBinding bind;
    private OnMovieClickListener movieClickListener;
    private int movieId;

    public MovieHolder(View itemView, OnMovieClickListener movieClickListener) {
        super(itemView);
        this.movieClickListener = movieClickListener;
        bind = ItemMovieBinding.bind(itemView);
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (movieClickListener != null) {
            movieClickListener.onMovieClick(movieId);
        }
    }

    public void setInformation(PosterOverviewItem movie) {
        movieId = movie.getId();
        String title = movie.getTitle();
        if (title == null || TextUtils.isEmpty(title)) {
            title = movie.getName();
        }
        MoviePosterItem item = new MoviePosterItem(movie.getId(), movie.getTitleImagePath(), title);
        bind.moviePosterItem.setMovieInformation(item);
    }
}