package tobiapplications.com.moviebase.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.hannesdorfmann.adapterdelegates3.AdapterDelegatesManager;

import java.util.ArrayList;
import java.util.List;

import tobiapplications.com.moviebase.adapter.delegates.movie.ActorsDelegate;
import tobiapplications.com.moviebase.adapter.delegates.movie.AdditionalMovieInfoDelegate;
import tobiapplications.com.moviebase.adapter.delegates.movie.MovieInfoDelegate;
import tobiapplications.com.moviebase.adapter.delegates.movie.ReviewsDelegate;
import tobiapplications.com.moviebase.adapter.delegates.serie.AdditionalSerieInfoDelegate;
import tobiapplications.com.moviebase.adapter.delegates.serie.SeasonsDelegate;
import tobiapplications.com.moviebase.adapter.delegates.serie.SerieInfoDelegate;
import tobiapplications.com.moviebase.adapter.delegates.SimilarMovieDelegate;
import tobiapplications.com.moviebase.adapter.delegates.SummaryViewDelegate;
import tobiapplications.com.moviebase.adapter.delegates.movie.TrailersDelegate;
import tobiapplications.com.moviebase.model.DisplayableItem;
import tobiapplications.com.moviebase.model.detail.items.SimilarMoviesItem;
import tobiapplications.com.moviebase.ui.viewholder.detail.serie.AdditionalSerieInfoViewHolder;
import tobiapplications.com.moviebase.utils.Constants;

/**
 * Created by Tobias on 14.06.2017.
 */

public class DetailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<DisplayableItem> itemList;
    private AdapterDelegatesManager<List<DisplayableItem>> delegatesManager;

    public DetailAdapter(int overviewType) {
        itemList = new ArrayList<>();

        delegatesManager = new AdapterDelegatesManager<>();
        delegatesManager.addDelegate(new MovieInfoDelegate());
        delegatesManager.addDelegate(new SerieInfoDelegate());
        delegatesManager.addDelegate(new SummaryViewDelegate());
        delegatesManager.addDelegate(new AdditionalMovieInfoDelegate());
        delegatesManager.addDelegate(new AdditionalSerieInfoDelegate());
        delegatesManager.addDelegate(new SimilarMovieDelegate(overviewType));
        delegatesManager.addDelegate(new ReviewsDelegate());
        delegatesManager.addDelegate(new ActorsDelegate());
        delegatesManager.addDelegate(new TrailersDelegate());
        delegatesManager.addDelegate(new SeasonsDelegate());
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

    public void addUiViews(ArrayList<DisplayableItem> detailItems) {
        if (itemList != null) {
            itemList.addAll(detailItems);
            notifyItemRangeChanged(itemList.size() - detailItems.size(), itemList.size());
        }
    }

    public void addUiView(DisplayableItem item) {
        if (item == null || itemList == null) {
            return;
        }
        
        itemList.add(item);
        notifyItemInserted(itemList.size() - 1);
    }
}
