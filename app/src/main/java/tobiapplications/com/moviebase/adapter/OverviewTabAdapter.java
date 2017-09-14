package tobiapplications.com.moviebase.adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.hannesdorfmann.adapterdelegates3.AdapterDelegatesManager;

import java.util.ArrayList;
import java.util.List;

import tobiapplications.com.moviebase.adapter.delegates.LoadingMovieDelegate;
import tobiapplications.com.moviebase.adapter.delegates.MovieDelegate;
import tobiapplications.com.moviebase.listener.OnLoadMoreMoviesListener;
import tobiapplications.com.moviebase.listener.OnMovieClickListener;
import tobiapplications.com.moviebase.model.DisplayableItem;
import tobiapplications.com.moviebase.model.overview.LoadingItem;
import tobiapplications.com.moviebase.model.overview.MovieOverviewModel;
import tobiapplications.com.moviebase.utils.Constants;

/**
 * Created by Tobias on 09.06.2017.
 */

public class OverviewTabAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final RecyclerView mRecyclerView;
    private OnLoadMoreMoviesListener movieLoadListener;
    private final GridLayoutManager mGridLayoutManager;
    private boolean triggerLoadMoreMovies = true;
    private final int MOVIE_SPAN = 1;
    private final int LOADING_SPAN = 2;

    private AdapterDelegatesManager<List<DisplayableItem>> delegatesManager;
    private List<DisplayableItem> items;

    public OverviewTabAdapter(Context context, RecyclerView recyclerView, OnMovieClickListener movieClickListener) {
        this.mRecyclerView = recyclerView;
        this.items = new ArrayList<>();
        setRecyclerViewScrollListener();
        mGridLayoutManager = (GridLayoutManager) mRecyclerView.getLayoutManager();
        mGridLayoutManager.setSpanSizeLookup(spanSizeLookupBuilder());

        delegatesManager = new AdapterDelegatesManager<>();
        delegatesManager.addDelegate(new MovieDelegate(context, movieClickListener));
        delegatesManager.addDelegate(new LoadingMovieDelegate(context));
    }

    private GridLayoutManager.SpanSizeLookup spanSizeLookupBuilder() {
        return new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                DisplayableItem item = items.get(position);
                if (item instanceof MovieOverviewModel) {
                    return MOVIE_SPAN;
                } else if (item instanceof LoadingItem){
                    return LOADING_SPAN;
                }

                return 0;
            }
        };
    }

    private void setRecyclerViewScrollListener() {
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (isListEndReached()) {
                    if (triggerLoadMoreMovies) {
                        loadMoreMovies();
                    }
                }
            }
        });
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
        if (items != null && !items.isEmpty()) {
            return items.size();
        }
        return 0;
    }

    @Override
    public int getItemViewType(int position) {
        return delegatesManager.getItemViewType(items, position);
    }

    private void loadMoreMovies() {
        triggerLoadMoreMovies = false;
        if (movieLoadListener != null) {
            movieLoadListener.loadMoreMovies();
        }
    }

    public void insertLoadingItem() {
        items.add(new LoadingItem());
        notifyItemInserted(items.size() - 1);
    }

    private boolean isListEndReached() {
        int visibleItemCount = mGridLayoutManager.getChildCount();
        int totalItemCount = mGridLayoutManager.getItemCount();
        int pastVisibleItems = mGridLayoutManager.findFirstVisibleItemPosition();

        return pastVisibleItems + visibleItemCount >= totalItemCount;
    }

    public void setMovies(ArrayList<MovieOverviewModel> movies) {
        if (movies != null) {
            items.addAll(movies);
            triggerLoadMoreMovies = true;
            notifyItemRangeChanged(items.size() - movies.size(), movies.size());
        } else {
            resetList();
        }
    }

    private void resetList() {
        items.clear();
        notifyDataSetChanged();
    }

    public void setOnLoadMoreMoviesListener(OnLoadMoreMoviesListener loadListener) {
        this.movieLoadListener = loadListener;
    }

    public void removeLoadingItem() {
        for (DisplayableItem item : items) {
            if ((item instanceof LoadingItem)) {
                int index = items.indexOf(item);
                items.remove(index);
                notifyItemRangeChanged(index, items.size() - index);
            }
        }
    }
}
