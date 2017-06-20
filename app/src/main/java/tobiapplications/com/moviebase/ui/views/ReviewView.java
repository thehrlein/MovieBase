package tobiapplications.com.moviebase.ui.views;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import tobiapplications.com.moviebase.R;

/**
 * Created by Tobias on 16.06.2017.
 */

public class ReviewView extends LinearLayout {

    private LinearLayout rootView;
    private TextView author;
    private TextView content;

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
        rootView = (LinearLayout) inflate(context, R.layout.view_review_item, this);
        author = (TextView) rootView.findViewById(R.id.review_author);
        content = (TextView) rootView.findViewById(R.id.review_content);
    }

    public void setReviewText(String author, String content) {
        this.author.setText(author);
        this.content.setText(content);
    }
}
