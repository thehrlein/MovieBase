package tobiapplications.com.moviebase.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.hannesdorfmann.adapterdelegates3.AdapterDelegatesManager;

import java.util.ArrayList;
import java.util.List;

import tobiapplications.com.moviebase.R;
import tobiapplications.com.moviebase.adapter.delegates.ActorsDelegate;
import tobiapplications.com.moviebase.adapter.delegates.AdditionalInfoDelegate;
import tobiapplications.com.moviebase.adapter.delegates.InfoViewDelegate;
import tobiapplications.com.moviebase.adapter.delegates.ReviewsDelegate;
import tobiapplications.com.moviebase.adapter.delegates.SimilarMovieDelegate;
import tobiapplications.com.moviebase.adapter.delegates.SummaryViewDelegate;
import tobiapplications.com.moviebase.adapter.delegates.TrailersDelegate;
import tobiapplications.com.moviebase.model.DisplayableItem;
import tobiapplications.com.moviebase.model.RecyclerItem;
import tobiapplications.com.moviebase.model.detail.ActorsResponse;
import tobiapplications.com.moviebase.model.detail.ReviewResponse;
import tobiapplications.com.moviebase.model.detail.items.AdditionalInfoItem;
import tobiapplications.com.moviebase.model.detail.items.InfoItem;
import tobiapplications.com.moviebase.model.detail.items.SimilarMoviesItem;
import tobiapplications.com.moviebase.model.detail.items.SummaryItem;
import tobiapplications.com.moviebase.model.detail.items.TrailerItem;
import tobiapplications.com.moviebase.ui.viewholder.detail.ActorsViewHolder;
import tobiapplications.com.moviebase.ui.viewholder.detail.AdditionalInfoViewHolder;
import tobiapplications.com.moviebase.ui.viewholder.detail.InfoViewHolder;
import tobiapplications.com.moviebase.ui.viewholder.detail.ReviewsViewHolder;
import tobiapplications.com.moviebase.ui.viewholder.detail.SimilarMoviesViewHolder;
import tobiapplications.com.moviebase.ui.viewholder.detail.SummaryViewHolder;
import tobiapplications.com.moviebase.ui.viewholder.detail.TrailersViewHolder;

/**
 * Created by Tobias on 14.06.2017.
 */

public class DetailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<DisplayableItem> itemList;
    private AdapterDelegatesManager<List<DisplayableItem>> delegatesManager;

    public DetailAdapter(Context context) {
        this.context = context;
        itemList = new ArrayList<>();

        delegatesManager = new AdapterDelegatesManager<>();
        delegatesManager.addDelegate(new InfoViewDelegate(context));
        delegatesManager.addDelegate(new SummaryViewDelegate(context));
        delegatesManager.addDelegate(new AdditionalInfoDelegate(context));
        delegatesManager.addDelegate(new SimilarMovieDelegate(context));
        delegatesManager.addDelegate(new ReviewsDelegate(context));
        delegatesManager.addDelegate(new ActorsDelegate(context));
        delegatesManager.addDelegate(new TrailersDelegate(context));
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
        if (itemList != null && !itemList.isEmpty()) {
            return itemList.size();
        }
        return 0;
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
        if (item != null) {
            itemList.add(item);
            notifyItemInserted(itemList.size());
        }
    }
}
