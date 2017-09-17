package tobiapplications.com.moviebase.ui.viewholder.detail.movie;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import tobiapplications.com.moviebase.R;
import tobiapplications.com.moviebase.databinding.DetailReviewsHolderBinding;
import tobiapplications.com.moviebase.model.detail.Review;
import tobiapplications.com.moviebase.model.detail.ReviewResponse;
import tobiapplications.com.moviebase.ui.views.DividerView;
import tobiapplications.com.moviebase.ui.views.ReviewView;

/**
 * Created by Tobias on 16.06.2017.
 */

public class ReviewsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    private DetailReviewsHolderBinding bind;
    private Context context;
    private int shownReviews = 1;
    private ArrayList<ReviewView> inflatedReviewItems;

    public ReviewsViewHolder(View itemView, Context context) {
        super(itemView);
        this.context = context;
        bind = DetailReviewsHolderBinding.bind(itemView);

        bind.showNextReview.setOnClickListener(this);
    }

    public void setReviews(ReviewResponse reviewResponse) {
        ArrayList<Review> reviews = reviewResponse.getReviews();
        inflatedReviewItems = new ArrayList<>();

        for (int i = 0; i < reviews.size(); i++) {
            ReviewView view = new ReviewView(context);
            view.setReviewText(reviews.get(i).getAuthor(), reviews.get(i).getContent());
            bind.reviewsLayout.addView(view);

            if (i > 0) {
                view.setVisibility(View.GONE);
            }
            inflatedReviewItems.add(view);
        }

        hideLoadMoreTextViewIfLastReviewIsShown();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.show_next_review) {
            addDivider();
            inflatedReviewItems.get(shownReviews).setVisibility(View.VISIBLE);
            shownReviews++;
            hideLoadMoreTextViewIfLastReviewIsShown();
        }
    }

    private void hideLoadMoreTextViewIfLastReviewIsShown() {
        if (shownReviews == inflatedReviewItems.size()) {
            bind.showNextReview.setVisibility(View.GONE);
        } else {
            bind.showNextReview.setText(context.getString(R.string.review_show_more, inflatedReviewItems.size() - shownReviews));
        }
    }

    private void addDivider() {
        if (shownReviews < inflatedReviewItems.size()) {
            DividerView divider = new DividerView(context);
            bind.reviewsLayout.addView(divider, getDividerPosition());
        }
    }

    public int getDividerPosition() {
        return shownReviews * 2 - 1;
    }
}
