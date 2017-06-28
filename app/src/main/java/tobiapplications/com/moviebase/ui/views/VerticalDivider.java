package tobiapplications.com.moviebase.ui.views;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import tobiapplications.com.moviebase.R;

/**
 * Created by Tobias on 27.06.2017.
 */

public class VerticalDivider extends LinearLayout {

    public VerticalDivider(Context context) {
        super(context);
        init(context);
    }

    public VerticalDivider(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public VerticalDivider(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        inflate(context, R.layout.view_divider_vertical, this);
    }
}
