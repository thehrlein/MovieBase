package tobiapplications.com.moviebase.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.hannesdorfmann.adapterdelegates3.AdapterDelegatesManager;

import java.util.ArrayList;
import java.util.List;

import tobiapplications.com.moviebase.adapter.delegates.SearchMovieDelegate;
import tobiapplications.com.moviebase.listener.OnMovieClickListener;
import tobiapplications.com.moviebase.model.DisplayableItem;
import tobiapplications.com.moviebase.model.overview.MovieOverviewModel;
import tobiapplications.com.moviebase.model.search.SearchMovieItem;
import tobiapplications.com.moviebase.utils.Constants;

/**
 * Created by Tobias on 01.04.2017.
 */

public class SearchMovieAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<DisplayableItem> items;
    private AdapterDelegatesManager<List<DisplayableItem>> delegatesManager;

    public SearchMovieAdapter(Context mContext, OnMovieClickListener clickListener) {
        items = new ArrayList<>();
        delegatesManager = new AdapterDelegatesManager<>();
        delegatesManager.addDelegate(new SearchMovieDelegate(clickListener));
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return delegatesManager.onCreateViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        delegatesManager.onBindViewHolder(items, position, holder);
    }


    @Override
    public int getItemCount() {
        if (items == null || items.isEmpty()) {
            return 0;
        }

        return items.size();
    }

    @Override
    public int getItemViewType(int position) {
        return delegatesManager.getItemViewType(items, position);
    }

    public void setSearchMovies(ArrayList<DisplayableItem> movies) {
        this.items = movies;
        notifyDataSetChanged();
    }
}
