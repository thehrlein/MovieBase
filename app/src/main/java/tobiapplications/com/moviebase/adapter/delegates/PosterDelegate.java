package tobiapplications.com.moviebase.adapter.delegates;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.hannesdorfmann.adapterdelegates3.AdapterDelegate;

import java.util.List;

import tobiapplications.com.moviebase.R;
import tobiapplications.com.moviebase.listener.OnMovieClickListener;
import tobiapplications.com.moviebase.model.DisplayableItem;
import tobiapplications.com.moviebase.model.overview.PosterOverviewItem;
import tobiapplications.com.moviebase.ui.viewholder.overview.MovieHolder;

/**
 * Created by Tobias on 04.09.2017.
 */

public class PosterDelegate extends AdapterDelegate<List<DisplayableItem>> {

    private OnMovieClickListener movieClickListener;

    public PosterDelegate(OnMovieClickListener movieClickListener) {
        this.movieClickListener = movieClickListener;
    }

    @Override
    protected boolean isForViewType(@NonNull List<DisplayableItem> items, int position) {
        return items.get(position) instanceof PosterOverviewItem;
    }

    @NonNull
    @Override
    protected RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        return new MovieHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_movie, parent, false), movieClickListener);
    }

    @Override
    protected void onBindViewHolder(@NonNull List<DisplayableItem> items, int position, @NonNull RecyclerView.ViewHolder holder, @NonNull List<Object> payloads) {
        PosterOverviewItem movie = (PosterOverviewItem) items.get(position);
        MovieHolder movieHolder = (MovieHolder) holder;
        movieHolder.setInformation(movie);
    }
}
