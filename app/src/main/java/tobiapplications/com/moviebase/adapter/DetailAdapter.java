package tobiapplications.com.moviebase.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;

import tobiapplications.com.moviebase.R;
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
    private ArrayList<RecyclerItem> itemList;

    public static final int VIEW_TYPE_INFO = 200;
    public static final int VIEW_TYPE_SUMMARY = 201;
    public static final int VIEW_TYPE_ADDITIONAL_INFO = 202;
    public static final int VIEW_TYPE_SIMILAR_MOVIES = 203;
    public static final int VIEW_TYPE_ACTORS = 204;
    public static final int VIEW_TYPE_REVIEWS = 205;
    public static final int VIEW_TYPE_TRAILERS = 206;

    public DetailAdapter(Context context) {
        this.context = context;
        itemList = new ArrayList<>();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case VIEW_TYPE_INFO:
                return new InfoViewHolder(LayoutInflater.from(context).inflate(R.layout.detail_info_holder, parent, false), context);
            case VIEW_TYPE_SUMMARY:
                return new SummaryViewHolder(LayoutInflater.from(context).inflate(R.layout.detail_summary_holder, parent, false));
            case VIEW_TYPE_ADDITIONAL_INFO:
                return new AdditionalInfoViewHolder(LayoutInflater.from(context).inflate(R.layout.detail_additional_info_holder, parent, false), context);
            case VIEW_TYPE_SIMILAR_MOVIES:
                return new SimilarMoviesViewHolder(LayoutInflater.from(context).inflate(R.layout.detail_similar_movies_holder, parent, false), context);
            case VIEW_TYPE_REVIEWS:
                return new ReviewsViewHolder(LayoutInflater.from(context).inflate(R.layout.detail_reviews_holder, parent, false), context);
            case VIEW_TYPE_ACTORS:
                return new ActorsViewHolder(LayoutInflater.from(context).inflate(R.layout.detail_actors_holder, parent, false), context);
            case VIEW_TYPE_TRAILERS:
                return new TrailersViewHolder(LayoutInflater.from(context).inflate(R.layout.detail_trailers_holder, parent, false), context);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        RecyclerItem item = itemList.get(position);
        switch (item.getItemType()) {
            case VIEW_TYPE_INFO:
                bindInfoView((InfoViewHolder) holder, item);
                break;
            case VIEW_TYPE_SUMMARY:
                bindSummaryView((SummaryViewHolder) holder, item);
                break;
            case VIEW_TYPE_ADDITIONAL_INFO:
                bindAdditionalInfoView((AdditionalInfoViewHolder) holder, item);
                break;
            case VIEW_TYPE_SIMILAR_MOVIES:
                bindSimilarMoviesView((SimilarMoviesViewHolder) holder, item);
                break;
            case VIEW_TYPE_REVIEWS:
                bindReviewsView((ReviewsViewHolder) holder, item);
                break;
            case VIEW_TYPE_ACTORS:
                bindActorsView((ActorsViewHolder) holder, item);
                break;
            case VIEW_TYPE_TRAILERS:
                bindTrailersItem((TrailersViewHolder) holder, item);
                break;
        }
    }

    private void bindTrailersItem(TrailersViewHolder trailersViewHolder, RecyclerItem item) {
        ArrayList<TrailerItem> trailerItems = (ArrayList<TrailerItem>) item.getItem();
        trailersViewHolder.setTrailers(trailerItems);
    }

    private void bindActorsView(ActorsViewHolder actorsViewHolder, RecyclerItem item) {
        ActorsResponse actorsResponse = (ActorsResponse) item.getItem();
        actorsViewHolder.setActorInformation(actorsResponse.getActors());
    }

    private void bindReviewsView(ReviewsViewHolder reviewsViewHolder, RecyclerItem item) {
        ReviewResponse reviewResponse = (ReviewResponse) item.getItem();
        reviewsViewHolder.setReviews(reviewResponse);
    }

    private void bindSimilarMoviesView(SimilarMoviesViewHolder similarMoviesViewHolder, RecyclerItem item) {
        SimilarMoviesItem similarMoviesItem = (SimilarMoviesItem) item.getItem();
        similarMoviesViewHolder.setSimilarMovies(similarMoviesItem);
    }

    private void bindAdditionalInfoView(AdditionalInfoViewHolder additionalHolder, RecyclerItem item) {
        AdditionalInfoItem additionalInfoItem = (AdditionalInfoItem) item.getItem();
        additionalHolder.setAdditionalInfo(additionalInfoItem);
    }

    private void bindSummaryView(SummaryViewHolder summaryHolder, RecyclerItem item) {
        SummaryItem summaryItem = (SummaryItem) item.getItem();
        summaryHolder.setSummary(summaryItem.getSummary());
    }

    private void bindInfoView(InfoViewHolder infoHolder, RecyclerItem item) {
        InfoItem infoItem = (InfoItem) item.getItem();
        infoHolder.setInformation(infoItem);
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

    public void addUiViews(ArrayList<RecyclerItem> detailItems) {
        if (itemList != null) {
            itemList.addAll(detailItems);
            notifyItemRangeChanged(itemList.size() - detailItems.size(), itemList.size());
        }
    }

    public void addUiView(RecyclerItem item) {
        if (item != null) {
            itemList.add(item);
            notifyItemInserted(itemList.size());
        }
    }
}
