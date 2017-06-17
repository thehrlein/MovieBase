package tobiapplications.com.moviebase.adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;

import tobiapplications.com.moviebase.R;
import tobiapplications.com.moviebase.listener.OnLoadMoreMoviesListener;
import tobiapplications.com.moviebase.listener.OnMovieClickListener;
import tobiapplications.com.moviebase.model.overview.MovieOverviewModel;
import tobiapplications.com.moviebase.model.RecyclerItem;
import tobiapplications.com.moviebase.viewholder.overview.LoadingHolder;
import tobiapplications.com.moviebase.viewholder.overview.MovieHolder;

/**
 * Created by Tobias on 09.06.2017.
 */

public class OverviewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final RecyclerView mRecyclerView;
    private final Context context;
    private ArrayList<RecyclerItem> itemList;
    private OnLoadMoreMoviesListener movieLoadListener;
    private OnMovieClickListener movieClickListener;
    private final GridLayoutManager mGridLayoutManager;

    private boolean triggerLoadMoreMovies = true;
    private final int MOVIE_SPAN = 1;
    private final int LOADING_SPAN = 2;
    private String fragmentName;
    public static final int VIEW_TYPE_MOVIE = 100;
    public static final int VIEW_TYPE_LOADING = 101;


    public OverviewAdapter(Context context, RecyclerView recyclerView, String name) {
        this.context = context;
        this.mRecyclerView = recyclerView;
        this.itemList = new ArrayList<>();
        this.fragmentName = name;
        setRecyclerViewScrollListener();
        mGridLayoutManager = (GridLayoutManager) mRecyclerView.getLayoutManager();
        mGridLayoutManager.setSpanSizeLookup(spanSizeLookupBuilder());
    }

    private GridLayoutManager.SpanSizeLookup spanSizeLookupBuilder() {
        return new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                int type = getItemViewType(position);
                if (type == OverviewAdapter.VIEW_TYPE_MOVIE) {
                    return MOVIE_SPAN;
                } else if (type == OverviewAdapter.VIEW_TYPE_LOADING) {
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
                        Log.d("MovieAdapter", "load next movies");
                        loadMoreMovies();
                    }
                }
            }
        });
    }

    private void loadMoreMovies() {
        triggerLoadMoreMovies = false;
        if (movieLoadListener != null) {
            movieLoadListener.loadMoreMovies();
        }
    }

    public void insertLoadingItem() {
        itemList.add(new RecyclerItem(VIEW_TYPE_LOADING, null));
        notifyItemInserted(itemList.size() - 1);
    }

    private boolean isListEndReached() {
        int visibleItemCount = mGridLayoutManager.getChildCount();
        int totalItemCount = mGridLayoutManager.getItemCount();
        int pastVisibleItems = mGridLayoutManager.findFirstVisibleItemPosition();

        return pastVisibleItems + visibleItemCount >= totalItemCount;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case VIEW_TYPE_MOVIE:
                return new MovieHolder(LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false), movieClickListener, context);
            case VIEW_TYPE_LOADING:
                return new LoadingHolder(LayoutInflater.from(context).inflate(R.layout.item_movie_loading, parent, false));
        }

        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        RecyclerItem item = itemList.get(position);
        switch (item.getItemType()) {
            case VIEW_TYPE_MOVIE:
                bindMovieItem((MovieHolder) holder, item);
                break;
            case VIEW_TYPE_LOADING:
                bindLoadingItem((LoadingHolder) holder);
                break;
        }
    }

    private void bindLoadingItem(LoadingHolder loadingholder) {

    }

    private void bindMovieItem(MovieHolder movieHolder, RecyclerItem item) {
        MovieOverviewModel movie = (MovieOverviewModel) item.getItem();
        movieHolder.setInformation(movie, context);
     }

    @Override
    public int getItemCount() {
        if (itemList != null && !itemList.isEmpty()) {
            return itemList.size();
        }
        return 0;
    }

    @Override
    public int getItemViewType(int position) {
        if (itemList != null && !itemList.isEmpty()) {
            return itemList.get(position).getItemType();
        }

        return -1;
    }

    public void setMovies(ArrayList<MovieOverviewModel> movies) {
        if (movies != null) {
            addMovies(movies);
            triggerLoadMoreMovies = true;

            notifyItemRangeChanged(itemList.size() - movies.size(), movies.size());
        } else {
            resetList();
        }
    }

    private void addMovies(ArrayList<MovieOverviewModel> movies) {
        for (MovieOverviewModel movie : movies) {
            RecyclerItem item = new RecyclerItem(VIEW_TYPE_MOVIE, movie);
            itemList.add(item);
        }
    }

    private void resetList() {
        itemList.clear();
        notifyDataSetChanged();
    }

    public void setOnLoadMoreMoviesListener(OnLoadMoreMoviesListener loadListener) {
        this.movieLoadListener = loadListener;
    }

    public void setOnMovieClickListener(OnMovieClickListener clickListener) {
        this.movieClickListener = clickListener;
    }

    public void removeLoadingItem() {
        for (RecyclerItem item : itemList) {
            if (item.getItemType() == VIEW_TYPE_LOADING) {
                int index = itemList.indexOf(item);
                itemList.remove(index);
                notifyItemRangeChanged(index, itemList.size() - index);
            }
        }
    }
}
