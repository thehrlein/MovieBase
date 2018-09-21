package tobiapplications.com.moviebase.adapter.delegates;

import androidx.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.hannesdorfmann.adapterdelegates3.AdapterDelegate;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;
import tobiapplications.com.moviebase.R;
import tobiapplications.com.moviebase.model.DisplayableItem;
import tobiapplications.com.moviebase.model.overview.LoadingItem;
import tobiapplications.com.moviebase.ui.viewholder.overview.LoadingHolder;

/**
 * Created by Tobias on 04.09.2017.
 */

public class LoadingMovieDelegate extends AdapterDelegate<List<DisplayableItem>> {

    @Override
    protected boolean isForViewType(@NonNull List<DisplayableItem> items, int position) {
        return items.get(position) instanceof LoadingItem;
    }

    @NonNull
    @Override
    protected RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        return new LoadingHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_movie_loading, parent, false));
    }

    @Override
    protected void onBindViewHolder(@NonNull List<DisplayableItem> items, int position, @NonNull RecyclerView.ViewHolder holder, @NonNull List<Object> payloads) {
        // not needed
    }
}
