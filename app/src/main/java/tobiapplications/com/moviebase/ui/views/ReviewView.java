package tobiapplications.com.moviebase.ui.views;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import tobiapplications.com.moviebase.databinding.ViewReviewItemBinding;

/**
 * Created by Tobias on 16.06.2017.
 */

public class ReviewView extends LinearLayout {

    private ViewReviewItemBinding bind;

    public ReviewView(Context context) {
        super(context);
        init(context);
    }

    public ReviewView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ReviewView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        bind = ViewReviewItemBinding.inflate(layoutInflater, this, true);
    }

    public void setReviewText(String author, String content) {
        bind.reviewAuthor.setText(author);
        bind.reviewContent.setText(content);
    }
}
