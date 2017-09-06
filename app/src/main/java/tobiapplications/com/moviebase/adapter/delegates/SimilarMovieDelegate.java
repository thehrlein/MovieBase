package tobiapplications.com.moviebase.adapter.delegates;

import android.content.Context;
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

/**
 * Created by Tobias on 06.09.2017.
 */

public class SimilarMovieDelegate extends AdapterDelegate<List<DisplayableItem>> {

    private Context context;

    public SimilarMovieDelegate(Context context) {
        this.context = context;
    }

    @Override
    protected boolean isForViewType(@NonNull List<DisplayableItem> items, int position) {
        return items.get(position) instanceof SimilarMoviesItem;
    }

    @NonNull
    @Override
    protected RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        return new SimilarMoviesViewHolder(LayoutInflater.from(context).inflate(R.layout.detail_similar_movies_holder, parent, false), context);
    }

    @Override
    protected void onBindViewHolder(@NonNull List<DisplayableItem> items, int position, @NonNull RecyclerView.ViewHolder holder, @NonNull List<Object> payloads) {
        SimilarMoviesItem similarMoviesItem = (SimilarMoviesItem) items.get(position);
        SimilarMoviesViewHolder similarMoviesViewHolder = (SimilarMoviesViewHolder) holder;
        similarMoviesViewHolder.setSimilarMovies(similarMoviesItem);
    }
}
