package tobiapplications.com.moviebase.adapter;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.hannesdorfmann.adapterdelegates3.AdapterDelegatesManager;

import java.util.ArrayList;
import java.util.List;

import tobiapplications.com.moviebase.adapter.delegates.LoadingMovieDelegate;
import tobiapplications.com.moviebase.adapter.delegates.PosterDelegate;
import tobiapplications.com.moviebase.listener.OnLoadMoreMoviesListener;
import tobiapplications.com.moviebase.listener.OnMovieClickListener;
import tobiapplications.com.moviebase.model.DisplayableItem;
import tobiapplications.com.moviebase.model.overview.LoadingItem;
import tobiapplications.com.moviebase.model.overview.PosterOverviewItem;

/**
 * Created by Tobias on 09.06.2017.
 */

public class OverviewTabAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private OnLoadMoreMoviesListener movieLoadListener;
    private boolean triggerLoadMoreMovies = true;
    private final int MOVIE_SPAN = 1;
    private final int LOADING_SPAN = 2;
    private AdapterDelegatesManager<List<DisplayableItem>> delegatesManager;
    private List<DisplayableItem> itemList;

    public OverviewTabAdapter(RecyclerView recyclerView, OnMovieClickListener movieClickListener) {
        this.itemList = new ArrayList<>();
        GridLayoutManager gridLayoutManager = (GridLayoutManager) recyclerView.getLayoutManager();
        gridLayoutManager.setSpanSizeLookup(spanSizeLookupBuilder());
        setRecyclerViewScrollListener(recyclerView, gridLayoutManager);

        delegatesManager = new AdapterDelegatesManager<>();
        delegatesManager.addDelegate(new PosterDelegate(movieClickListener));
        delegatesManager.addDelegate(new LoadingMovieDelegate());
    }

    private GridLayoutManager.SpanSizeLookup spanSizeLookupBuilder() {
        return new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                DisplayableItem item = itemList.get(position);
                if (item instanceof PosterOverviewItem) {
                    return MOVIE_SPAN;
                } else if (item instanceof LoadingItem){
                    return LOADING_SPAN;
                }

                return 0;
            }
        };
    }

    private void setRecyclerViewScrollListener(RecyclerView recyclerView, GridLayoutManager gridLayoutManager) {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (isListEndReached(gridLayoutManager)) {
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
        delegatesManager.onBindViewHolder(itemList, position, holder);
    }

    @Override
    public int getItemCount() {
        if (itemList == null || itemList.isEmpty()) {
            return 0;
        }
        return itemList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return delegatesManager.getItemViewType(itemList, position);
    }

    private void loadMoreMovies() {
        triggerLoadMoreMovies = false;
        if (movieLoadListener != null) {
            movieLoadListener.loadMoreMovies();
        }
    }

    public void insertLoadingItem() {
        itemList.add(new LoadingItem());
        notifyItemInserted(itemList.size() - 1);
    }

    private boolean isListEndReached(GridLayoutManager gridLayoutManager) {
        int visibleItemCount = gridLayoutManager.getChildCount();
        int totalItemCount = gridLayoutManager.getItemCount();
        int pastVisibleItems = gridLayoutManager.findFirstVisibleItemPosition();

        return pastVisibleItems + visibleItemCount >= totalItemCount;
    }

    public void setPosterItems(ArrayList<PosterOverviewItem> movies) {
        if (movies != null) {
            itemList.addAll(movies);
            triggerLoadMoreMovies = true;
            notifyItemRangeChanged(itemList.size() - movies.size(), movies.size());
        } else {
            resetList();
        }
    }

    private void resetList() {
        itemList.clear();
        notifyDataSetChanged();
    }

    public void setOnLoadMoreMoviesListener(OnLoadMoreMoviesListener loadListener) {
        this.movieLoadListener = loadListener;
    }

    public void removeLoadingItem() {
        for (DisplayableItem item : itemList) {
            if (item instanceof LoadingItem) {
                int index = itemList.indexOf(item);
                itemList.remove(index);
                notifyItemRangeChanged(index, itemList.size() - index);
            }
        }
    }
}
