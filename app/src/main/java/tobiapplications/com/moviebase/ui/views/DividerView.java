package tobiapplications.com.moviebase.ui.views;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import tobiapplications.com.moviebase.R;

/**
 * Created by Tobias on 16.06.2017.
 */

public class DividerView extends LinearLayout {

    public DividerView(Context context) {
        super(context);
        init(context);
    }

    public DividerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public DividerView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }


    private void init(Context context) {
        inflate(context, R.layout.view_divider_item, this);
    }
}
