package tobiapplications.com.moviebase.adapter.delegates;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.hannesdorfmann.adapterdelegates3.AdapterDelegate;

import java.util.List;

import tobiapplications.com.moviebase.R;
import tobiapplications.com.moviebase.model.DisplayableItem;
import tobiapplications.com.moviebase.model.detail.items.SimilarMoviesItem;
import tobiapplications.com.moviebase.ui.viewholder.detail.SimilarMoviesViewHolder;
import tobiapplications.com.moviebase.utils.Constants;

/**
 * Created by Tobias on 06.09.2017.
 */

public class SimilarMovieDelegate extends AdapterDelegate<List<DisplayableItem>> {

    private Constants.OverviewType overviewType;

    public SimilarMovieDelegate(Constants.OverviewType overviewType) {
        this.overviewType = overviewType;
    }

    @Override
    protected boolean isForViewType(@NonNull List<DisplayableItem> items, int position) {
        return items.get(position) instanceof SimilarMoviesItem;
    }

    @NonNull
    @Override
    protected RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        return new SimilarMoviesViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.detail_similar_movies_holder, parent, false), parent.getContext(), overviewType);
    }

    @Override
    protected void onBindViewHolder(@NonNull List<DisplayableItem> items, int position, @NonNull RecyclerView.ViewHolder holder, @NonNull List<Object> payloads) {
        SimilarMoviesItem similarMoviesItem = (SimilarMoviesItem) items.get(position);
        SimilarMoviesViewHolder similarMoviesViewHolder = (SimilarMoviesViewHolder) holder;
        similarMoviesViewHolder.setSimilarMovies(similarMoviesItem);
    }
}