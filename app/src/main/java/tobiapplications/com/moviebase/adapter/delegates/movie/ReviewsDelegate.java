package tobiapplications.com.moviebase.adapter.delegates.movie;

import androidx.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.hannesdorfmann.adapterdelegates3.AdapterDelegate;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;
import tobiapplications.com.moviebase.R;
import tobiapplications.com.moviebase.model.DisplayableItem;
import tobiapplications.com.moviebase.model.detail.ReviewResponse;
import tobiapplications.com.moviebase.ui.viewholder.detail.movie.ReviewsViewHolder;

/**
 * Created by Tobias on 06.09.2017.
 */

public class ReviewsDelegate extends AdapterDelegate<List<DisplayableItem>> {

    @Override
    protected boolean isForViewType(@NonNull List<DisplayableItem> items, int position) {
        return items.get(position) instanceof ReviewResponse;
    }

    @NonNull
    @Override
    protected RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        return new ReviewsViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.detail_reviews_holder, parent, false), parent.getContext());
    }

    @Override
    protected void onBindViewHolder(@NonNull List<DisplayableItem> items, int position, @NonNull RecyclerView.ViewHolder holder, @NonNull List<Object> payloads) {
        ReviewResponse reviewResponse = (ReviewResponse) items.get(position);
        ReviewsViewHolder reviewsViewHolder = (ReviewsViewHolder) holder;
        reviewsViewHolder.setReviews(reviewResponse);
    }
}
