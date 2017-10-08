package tobiapplications.com.moviebase.ui.viewholder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import tobiapplications.com.moviebase.R;
import tobiapplications.com.moviebase.databinding.SearchListItemBinding;
import tobiapplications.com.moviebase.listener.OnMovieClickListener;
import tobiapplications.com.moviebase.model.overview.MovieOverviewModel;
import tobiapplications.com.moviebase.utils.Constants;
import tobiapplications.com.moviebase.utils.DateUtils;
import tobiapplications.com.moviebase.utils.NetworkUtils;

/**
 * Created by Tobias on 16.06.2017.
 */

public class SearchMovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private SearchListItemBinding bind;
    private OnMovieClickListener movieClickListener;
    private int movieId;
    private Context context;
    private Constants.OverviewType overviewType;


    public SearchMovieViewHolder(View itemView, Context context, OnMovieClickListener movieClickListener, Constants.OverviewType overviewType) {
        super(itemView);
        this.context = context;
        this.movieClickListener = movieClickListener;
        this.overviewType = overviewType;
        bind = SearchListItemBinding.bind(itemView);
        itemView.setOnClickListener(this);
    }

    public void setSearchMovieInformation(MovieOverviewModel movie) {
        this.movieId = movie.getId();

        bind.searchMovieTitle.setText(getTitle(movie));
        if (movie.getTitleImagePath() != null) {
            Picasso.with(context).load(NetworkUtils.getFullImageUrlLow(movie.getTitleImagePath())).into(bind.searchMovieImageView);
        } else {
            bind.searchMovieImageView.setImageResource(R.drawable.no_picture);
        }
        bind.searchMovieReleaseDate.setText(DateUtils.getDMYFromYMD(movie.getReleaseDate()));
    }

    private String getTitle(MovieOverviewModel movie) {
        if (overviewType == Constants.OverviewType.MOVIES) {
            return movie.getTitle();
        }
        return movie.getName();
    }

    @Override
    public void onClick(View view) {
        if (movieClickListener != null) movieClickListener.onMovieClick(movieId);
    }
}