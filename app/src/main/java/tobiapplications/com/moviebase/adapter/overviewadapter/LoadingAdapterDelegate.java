package tobiapplications.com.moviebase.adapter.overviewadapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.hannesdorfmann.adapterdelegates3.AdapterDelegate;

import java.util.List;

import tobiapplications.com.moviebase.R;
import tobiapplications.com.moviebase.model.DisplayableItem;
import tobiapplications.com.moviebase.model.RecyclerItem;
import tobiapplications.com.moviebase.model.overview.LoadingItem;
import tobiapplications.com.moviebase.ui.viewholder.overview.LoadingHolder;

/**
 * Created by Tobias on 04.09.2017.
 */

public class LoadingAdapterDelegate extends AdapterDelegate<List<DisplayableItem>> {

    private Context context;
    private LayoutInflater inflater;

    public LoadingAdapterDelegate(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    protected boolean isForViewType(@NonNull List<DisplayableItem> items, int position) {
        return items.get(position) instanceof LoadingItem;
    }

    @NonNull
    @Override
    protected RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        return new LoadingHolder(inflater.inflate(R.layout.item_movie_loading, parent, false));
    }

    @Override
    protected void onBindViewHolder(@NonNull List<DisplayableItem> items, int position, @NonNull RecyclerView.ViewHolder holder, @NonNull List<Object> payloads) {

    }
}
