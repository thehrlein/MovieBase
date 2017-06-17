package tobiapplications.com.moviebase.viewholder.overview;

import android.content.ContentValues;
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

import java.util.ArrayList;

import tobiapplications.com.moviebase.R;
import tobiapplications.com.moviebase.database.MoviesContract;
import tobiapplications.com.moviebase.listener.OnMovieClickListener;
import tobiapplications.com.moviebase.model.detail.Genre;
import tobiapplications.com.moviebase.model.overview.MovieOverviewModel;
import tobiapplications.com.moviebase.utils.NetworkUtils;
import tobiapplications.com.moviebase.utils.SQLUtils;

/**
 * Created by Tobias on 15.06.2017.
 */

public class MovieHolder extends RecyclerView.ViewHolder implements View.OnClickListener, PopupMenu.OnMenuItemClickListener {
    public ImageView mPosterImage;
    public TextView mMovieTitleNoPicture;
    public TextView mMovieTitle;
    public ImageView mMovieCardDots;
    private OnMovieClickListener movieClickListener;
    private MovieOverviewModel movie;
    private int movieId;
    private Context context;
    private PopupMenu popupMenu;
    private boolean isFavorite;

    public MovieHolder(View itemView, OnMovieClickListener movieClickListener, Context context) {
        super(itemView);
        this.movieClickListener = movieClickListener;
        this.context = context;
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
        this.movie = movie;
        movieId = movie.getId();
        mMovieTitleNoPicture.setText(movie.getTitle());
        mMovieTitle.setText(movie.getTitle());
        if (movie.getTitleImagePath() != null) {
            Picasso.with(context).load(NetworkUtils.getFullImageUrlLow(movie.getTitleImagePath())).into(mPosterImage);
        } else {
            mPosterImage.setImageResource(R.drawable.no_image_available);
        }
        mMovieCardDots.setOnClickListener((View v) -> showPopMenu(v, context));
    }

    private void showPopMenu(View view, Context context) {
        popupMenu = new PopupMenu(context, view);
        MenuInflater inflater = popupMenu.getMenuInflater();
        inflater.inflate(R.menu.menu_movie_card, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(this);
        popupMenu.show();
    }

    private void setPopupItemText() {
        popupMenu.getMenu().getItem(0).setTitle(context.getString(R.string.menu_delete_from_favorite));
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        if (item.getItemId() == R.id.action_add_favorite) {
            prepareForDatabase();
            return true;
        }
        return false;
    }

    private void prepareForDatabase() {
        if (movie != null) {
            String genresString = "";
            ArrayList<Integer> genreArrayList = movie.getGenres();

            for (int i = 0; i < genreArrayList.size(); i++) {
                if (i == 0) {
                    genresString = genresString + genreArrayList.get(i);
                } else {
                    genresString = genresString + "-" + genreArrayList.get(i);
                }
            }

            ContentValues values = new ContentValues();
            values.put(MoviesContract.MovieEntry.COLUMN_ID, movie.getId());
            values.put(MoviesContract.MovieEntry.COLUMN_TITLE, movie.getTitle());
            values.put(MoviesContract.MovieEntry.COLUMN_TITLE_IMAGE_PATH, movie.getTitleImagePath());
            values.put(MoviesContract.MovieEntry.COLUMN_BACKDROP_IMAGE_PATH, movie.getBackgroundImagePath());
            values.put(MoviesContract.MovieEntry.COLUMN_YEAR, movie.getReleaseDate());
            values.put(MoviesContract.MovieEntry.COLUMN_RATING, movie.getRating());
            values.put(MoviesContract.MovieEntry.COLUMN_DESCRIPTION, movie.getDescription());
            values.put(MoviesContract.MovieEntry.COLUMN_GENRES, genresString);
            values.put(MoviesContract.MovieEntry.COLUMN_ADULT, movie.getAdult() ? "yes" : "no");

            SQLUtils.insertIntoDatabase(context, values);
        }
    }
}

