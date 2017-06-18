package tobiapplications.com.moviebase.viewholder.overview;

import android.content.Context;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import tobiapplications.com.moviebase.R;
import tobiapplications.com.moviebase.listener.OnMovieClickListener;
import tobiapplications.com.moviebase.model.general_items.MoviePosterItem;
import tobiapplications.com.moviebase.model.overview.MovieOverviewModel;
import tobiapplications.com.moviebase.ui.general_views.MoviePosterView;
import tobiapplications.com.moviebase.utils.NetworkUtils;
import tobiapplications.com.moviebase.utils.SQLUtils;

/**
 * Created by Tobias on 15.06.2017.
 */

public class MovieHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private OnMovieClickListener movieClickListener;
    private int movieId;
    private Context context;
    private PopupMenu popupMenu;
    private MoviePosterView moviePosterView;

    public MovieHolder(View itemView, OnMovieClickListener movieClickListener, Context context) {
        super(itemView);
        this.movieClickListener = movieClickListener;
        this.context = context;
        moviePosterView = (MoviePosterView) itemView.findViewById(R.id.movie_poster_item);
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (movieClickListener != null) {
            movieClickListener.onMovieClick(movieId);
        }
    }

    public void setInformation(MovieOverviewModel movie, Context context) {
        movieId = movie.getId();
        MoviePosterItem item = new MoviePosterItem(movie.getId(), movie.getTitleImagePath(), movie.getTitle());
        moviePosterView.setMovieInformation(item);
    }
}