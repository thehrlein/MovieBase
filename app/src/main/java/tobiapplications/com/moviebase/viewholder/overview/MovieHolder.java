package tobiapplications.com.moviebase.viewholder.overview;

import android.content.Context;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import tobiapplications.com.moviebase.R;
import tobiapplications.com.moviebase.listener.OnMovieClickListener;
import tobiapplications.com.moviebase.model.overview.MovieOverviewModel;
import tobiapplications.com.moviebase.utils.NetworkUtils;

/**
 * Created by Tobias on 15.06.2017.
 */

public class MovieHolder extends RecyclerView.ViewHolder implements View.OnClickListener, PopupMenu.OnMenuItemClickListener {
    public ImageView mPosterImage;
    public TextView mMovieTitleNoPicture;
    public TextView mMovieTitle;
    public ImageView mMovieCardDots;
    private OnMovieClickListener movieClickListener;
    private int movieId;

    public MovieHolder(View itemView, OnMovieClickListener movieClickListener) {
        super(itemView);
        this.movieClickListener = movieClickListener;
        mPosterImage = (ImageView) itemView.findViewById(R.id.movie_image);
        mMovieTitle = (TextView) itemView.findViewById(R.id.movie_title);
        mMovieTitleNoPicture = (TextView) itemView.findViewById(R.id.movie_title_no_picture);
        mMovieCardDots = (ImageView) itemView.findViewById(R.id.movie_card_dots);
        mMovieCardDots.setOnClickListener(this);

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
        mMovieTitleNoPicture.setText(movie.getTitle());
        mMovieTitle.setText(movie.getTitle());
        Picasso.with(context).load(NetworkUtils.getFullImageUrl(movie.getTitleImagePath())).into(mPosterImage);
        mMovieCardDots.setOnClickListener((View v) -> showPopMenu(v, context));
    }

    private void showPopMenu(View view, Context context) {
        PopupMenu popupMenu = new PopupMenu(context, view);
        MenuInflater inflater = popupMenu.getMenuInflater();
        inflater.inflate(R.menu.menu_movie_card, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(this);
        popupMenu.show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        if (item.getItemId() == R.id.action_add_favorite) {
            Log.d("MovieHolder", "onMenuItemClick");
            return true;
        }
        return false;
    }
}

