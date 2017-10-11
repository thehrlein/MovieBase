package tobiapplications.com.moviebase.adapter.delegates.movie;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.hannesdorfmann.adapterdelegates3.AdapterDelegate;

import java.util.List;

import tobiapplications.com.moviebase.R;
import tobiapplications.com.moviebase.model.DisplayableItem;
import tobiapplications.com.moviebase.model.detail.items.movie.MovieInfoItem;
import tobiapplications.com.moviebase.ui.viewholder.detail.movie.MovieInfoHolder;

/**
 * Created by Tobias on 06.09.2017.
 */

public class MovieInfoDelegate extends AdapterDelegate<List<DisplayableItem>> {

    @Override
    protected boolean isForViewType(@NonNull List<DisplayableItem> items, int position) {
        return items.get(position) instanceof MovieInfoItem;
    }

    @NonNull
    @Override
    protected RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        return new MovieInfoHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.detail_movie_info_holder, parent, false), parent.getContext());
    }

    @Override
    protected void onBindViewHolder(@NonNull List<DisplayableItem> items, int position, @NonNull RecyclerView.ViewHolder holder, @NonNull List<Object> payloads) {
        MovieInfoHolder movieInfoHolder = (MovieInfoHolder) holder;
        MovieInfoItem movieInfoItem = (MovieInfoItem) items.get(position);
        movieInfoHolder.setInformation(movieInfoItem);
    }
}