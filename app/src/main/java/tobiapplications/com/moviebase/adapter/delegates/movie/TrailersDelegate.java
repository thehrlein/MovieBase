package tobiapplications.com.moviebase.adapter.delegates.movie;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.hannesdorfmann.adapterdelegates3.AdapterDelegate;

import java.util.ArrayList;
import java.util.List;

import tobiapplications.com.moviebase.R;
import tobiapplications.com.moviebase.model.DisplayableItem;
import tobiapplications.com.moviebase.model.detail.FullTrailerItems;
import tobiapplications.com.moviebase.model.detail.items.movie.TrailerItem;
import tobiapplications.com.moviebase.ui.viewholder.detail.movie.TrailersViewHolder;

/**
 * Created by Tobias on 06.09.2017.
 */

public class TrailersDelegate extends AdapterDelegate<List<DisplayableItem>> {

    @Override
    protected boolean isForViewType(@NonNull List<DisplayableItem> items, int position) {
        return items.get(position) instanceof FullTrailerItems;
    }

    @NonNull
    @Override
    protected RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        return new TrailersViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.detail_trailers_holder, parent, false), parent.getContext());
    }

    @Override
    protected void onBindViewHolder(@NonNull List<DisplayableItem> items, int position, @NonNull RecyclerView.ViewHolder holder, @NonNull List<Object> payloads) {
        FullTrailerItems fullTrailerItems = (FullTrailerItems) items.get(position);
        ArrayList<TrailerItem> trailerItems = fullTrailerItems.getTrailerItems();
        TrailersViewHolder trailersViewHolder = (TrailersViewHolder) holder;
        trailersViewHolder.setTrailers(trailerItems);
    }
}