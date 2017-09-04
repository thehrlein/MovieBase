package tobiapplications.com.moviebase.adapter.overviewadapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.hannesdorfmann.adapterdelegates3.AdapterDelegate;

import java.util.List;

import tobiapplications.com.moviebase.R;
import tobiapplications.com.moviebase.listener.OnMovieClickListener;
import tobiapplications.com.moviebase.model.DisplayableItem;
import tobiapplications.com.moviebase.model.RecyclerItem;
import tobiapplications.com.moviebase.model.overview.MovieOverviewModel;
import tobiapplications.com.moviebase.ui.viewholder.overview.MovieHolder;

/**
 * Created by Tobias on 04.09.2017.
 */

public class MovieAdapterDelegate extends AdapterDelegate<List<DisplayableItem>> {

    private Context context;
    private LayoutInflater inflater;
    private OnMovieClickListener movieClickListener;

    public MovieAdapterDelegate(Context context, OnMovieClickListener movieClickListener) {
        this.context = context;
        this.movieClickListener = movieClickListener;
        inflater = LayoutInflater.from(context);
    }

    @Override
    protected boolean isForViewType(@NonNull List<DisplayableItem> items, int position) {
        return items.get(position) instanceof MovieOverviewModel;
    }

    @NonNull
    @Override
    protected RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        return new MovieHolder(inflater.inflate(R.layout.item_movie, parent, false), movieClickListener); //, movieClickListener, context);

    }

    @Override
    protected void onBindViewHolder(@NonNull List<DisplayableItem> items, int position, @NonNull RecyclerView.ViewHolder holder, @NonNull List<Object> payloads) {
        MovieOverviewModel movie = (MovieOverviewModel) items.get(position);
        MovieHolder movieHolder = (MovieHolder) holder;
        movieHolder.setInformation(movie);
    }
}
