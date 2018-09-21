package tobiapplications.com.moviebase.ui.views;

import android.content.Context;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import tobiapplications.com.moviebase.databinding.ViewDividerItemBinding;

/**
 * Created by Tobias on 16.06.2017.
 */

public class DividerView extends LinearLayout {

    private ViewDividerItemBinding bind;

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
        LayoutInflater inflater = LayoutInflater.from(context);
        bind = ViewDividerItemBinding.inflate(inflater, this, true);
    }
}
