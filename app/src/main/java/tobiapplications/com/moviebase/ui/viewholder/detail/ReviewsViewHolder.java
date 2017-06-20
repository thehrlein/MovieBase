package tobiapplications.com.moviebase.ui.viewholder.detail;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import tobiapplications.com.moviebase.R;
import tobiapplications.com.moviebase.model.detail.Review;
import tobiapplications.com.moviebase.model.detail.ReviewResponse;
import tobiapplications.com.moviebase.ui.views.DividerView;
import tobiapplications.com.moviebase.ui.views.ReviewView;

/**
 * Created by Tobias on 16.06.2017.
 */

public class ReviewsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    private Context context;
    private LinearLayout reviewWrapper;
    private TextView showNextReview;
    private int shownReviews = 1;
    private ArrayList<ReviewView> inflatedReviewItems;

    public ReviewsViewHolder(View itemView, Context context) {
        super(itemView);
        this.context = context;
        this.reviewWrapper = (LinearLayout) itemView.findViewById(R.id.reviews_layout);
        this.showNextReview = (TextView) itemView.findViewById(R.id.show_next_review);
        showNextReview.setOnClickListener(this);
    }

    public void setReviews(ReviewResponse reviewResponse) {
        ArrayList<Review> reviews = reviewResponse.getReviews();
        inflatedReviewItems = new ArrayList<>();

        for (int i = 0; i < reviews.size(); i++) {
            ReviewView view = new ReviewView(context);
            view.setReviewText(reviews.get(i).getAuthor(), reviews.get(i).getContent());
            reviewWrapper.addView(view);

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
            showNextReview.setVisibility(View.GONE);
        } else {
            showNextReview.setText(context.getString(R.string.review_show_more, inflatedReviewItems.size() - shownReviews));
        }
    }

    private void addDivider() {
        if (shownReviews < inflatedReviewItems.size()) {
            DividerView divider = new DividerView(context);
            reviewWrapper.addView(divider, getDividerPosition());
        }
    }

    public int getDividerPosition() {
        return shownReviews * 2 - 1;
    }
}
