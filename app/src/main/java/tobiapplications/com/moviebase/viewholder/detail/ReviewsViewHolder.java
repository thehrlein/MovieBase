package tobiapplications.com.moviebase.viewholder.detail;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import tobiapplications.com.moviebase.R;
import tobiapplications.com.moviebase.model.detail.Review;
import tobiapplications.com.moviebase.model.detail.ReviewResponse;
import tobiapplications.com.moviebase.ui.general_views.DividerView;
import tobiapplications.com.moviebase.ui.general_views.ReviewItem;

/**
 * Created by Tobias on 16.06.2017.
 */

public class ReviewsViewHolder extends RecyclerView.ViewHolder {

    private Context context;
    private LinearLayout reviewWrapper;

    public ReviewsViewHolder(View itemView, Context context) {
        super(itemView);
        this.reviewWrapper = (LinearLayout) itemView.findViewById(R.id.reviews_layout);
        this.context = context;
    }

    public void setReviews(ReviewResponse reviewResponse) {
        ArrayList<Review> reviews = reviewResponse.getReviews();

        for (int i = 0; i < reviews.size(); i++) {
            ReviewItem item = new ReviewItem(context);
            item.setReviewText(reviews.get(i).getAuthor(), reviews.get(i).getContent());

            reviewWrapper.addView(item);

            if (i + 1 < reviews.size()) {
                DividerView divider = new DividerView(context);
                reviewWrapper.addView(divider);
            }
        }
    }
}
