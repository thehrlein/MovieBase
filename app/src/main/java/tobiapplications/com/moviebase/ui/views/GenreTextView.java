package tobiapplications.com.moviebase.ui.views;

import android.content.Context;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.ContextCompat;
import android.util.AttributeSet;
import android.view.ViewGroup;

import com.google.android.flexbox.FlexboxLayout;

import tobiapplications.com.moviebase.R;

/**
 * Created by Tobias on 15.06.2017.
 */

public class GenreTextView extends AppCompatTextView {

    public GenreTextView(Context context) {
        super(context);
        init(context);
    }

    public GenreTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public GenreTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        setBackgroundDrawable(context.getResources().getDrawable(R.drawable.genre_background_color));
        FlexboxLayout.LayoutParams params = new FlexboxLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(5, 5 , 5, 5);
        setLayoutParams(params);
        setPadding(25, 10, 25, 10);
        setTextColor(ContextCompat.getColor(context, R.color.colorWhite));
        setTextSize(12);
    }
}
