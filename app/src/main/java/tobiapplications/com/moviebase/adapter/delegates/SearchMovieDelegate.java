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
import tobiapplications.com.moviebase.model.search.SearchMovieItem;
import tobiapplications.com.moviebase.ui.viewholder.SearchMovieViewHolder;

/**
 * Created by Tobias Hehrlein on 11.10.2017.
 */

public class SearchMovieDelegate extends AdapterDelegate<List<DisplayableItem>> {

    private OnMovieClickListener mClickListener;

    public SearchMovieDelegate(OnMovieClickListener mClickListener) {
        this.mClickListener = mClickListener;
    }

    @Override
    protected boolean isForViewType(@NonNull List<DisplayableItem> items, int position) {
        return items.get(position) instanceof SearchMovieItem;
    }

    @NonNull
    @Override
    protected RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        return new SearchMovieViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.search_list_item, parent, false), parent.getContext(), mClickListener);
    }

    @Override
    protected void onBindViewHolder(@NonNull List<DisplayableItem> items, int position, @NonNull RecyclerView.ViewHolder holder, @NonNull List<Object> payloads) {
        SearchMovieViewHolder viewHolder = (SearchMovieViewHolder) holder;
        SearchMovieItem item = (SearchMovieItem) items.get(position);
        if (item == null) {
            return;
        }

        viewHolder.setSearchMovieInformation(item);
    }
}