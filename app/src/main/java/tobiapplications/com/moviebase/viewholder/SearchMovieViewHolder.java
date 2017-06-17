package tobiapplications.com.moviebase.viewholder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import tobiapplications.com.moviebase.R;
import tobiapplications.com.moviebase.listener.OnMovieClickListener;
import tobiapplications.com.moviebase.model.overview.MovieOverviewModel;
import tobiapplications.com.moviebase.utils.DateUtils;
import tobiapplications.com.moviebase.utils.NetworkUtils;

/**
 * Created by Tobias on 16.06.2017.
 */

public class SearchMovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public ImageView mSearchMovieImage;
    public TextView mSearchMovieTitle;
    public TextView mSearchMovieReleaseDate;
    private OnMovieClickListener movieClickListener;
    private int movieId;
    private Context context;


    public SearchMovieViewHolder(View itemView, Context context, OnMovieClickListener movieClickListener) {
        super(itemView);
        this.context = context;
        this.movieClickListener = movieClickListener;
        mSearchMovieImage = (ImageView) itemView.findViewById(R.id.searchMovieImageView);
        mSearchMovieTitle = (TextView) itemView.findViewById(R.id.searchMovieTitle);
        mSearchMovieReleaseDate = (TextView) itemView.findViewById(R.id.searchMovieReleaseDate);
        itemView.setOnClickListener(this);
    }

    public void setSearchMovieInformation(MovieOverviewModel movie) {
        this.movieId = movie.getId();
        mSearchMovieTitle.setText(movie.getTitle());
        if (movie.getTitleImagePath() != null) {
            Picasso.with(context).load(NetworkUtils.getFullImageUrlLow(movie.getTitleImagePath())).into(mSearchMovieImage);
        } else {
            mSearchMovieImage.setImageResource(R.drawable.no_image_available);
        }
        mSearchMovieReleaseDate.setText(DateUtils.getDMYFromYMD(movie.getReleaseDate()));
    }

    @Override
    public void onClick(View view) {
        if (movieClickListener != null) movieClickListener.onMovieClick(movieId);
    }
}