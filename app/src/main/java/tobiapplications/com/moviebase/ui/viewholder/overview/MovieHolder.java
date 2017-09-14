package tobiapplications.com.moviebase.ui.viewholder.overview;

import android.content.Context;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import tobiapplications.com.moviebase.R;
import tobiapplications.com.moviebase.listener.OnMovieClickListener;
import tobiapplications.com.moviebase.model.general_items.MoviePosterItem;
import tobiapplications.com.moviebase.model.overview.MovieOverviewModel;
import tobiapplications.com.moviebase.ui.views.MoviePosterView;
import tobiapplications.com.moviebase.utils.Constants;

/**
 * Created by Tobias on 15.06.2017.
 */

public class MovieHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private OnMovieClickListener movieClickListener;
    private int movieId;
    private MoviePosterView moviePosterView;

    public MovieHolder(View itemView, OnMovieClickListener movieClickListener) {
        super(itemView);
        this.movieClickListener = movieClickListener;
        moviePosterView = (MoviePosterView) itemView.findViewById(R.id.movie_poster_item);
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (movieClickListener != null) {
            movieClickListener.onMovieClick(movieId);
        }
    }

    public void setInformation(MovieOverviewModel movie) {
        movieId = movie.getId();
        String title = movie.getTitle();
        if (title == null || TextUtils.isEmpty(title)) {
            title = movie.getName();
        }
        MoviePosterItem item = new MoviePosterItem(movie.getId(), movie.getTitleImagePath(), title);
        moviePosterView.setMovieInformation(item);
    }
}