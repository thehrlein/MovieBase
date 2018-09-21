package tobiapplications.com.moviebase.ui.views;

import android.content.Context;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatButton;
import tobiapplications.com.moviebase.R;


/**
 * Created by Tobias on 18.05.2017.
 */

public class PrimaryButton extends AppCompatButton {

    private int textColor = R.color.colorWhite;
    private int backgroundColor = R.color.colorPrimary;

    public PrimaryButton(Context context) {
        super(context);
        init();
    }

    public PrimaryButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PrimaryButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setTextColor(getResources().getColor(textColor));
        setBackgroundColor(getResources().getColor(backgroundColor));
        setAllCaps(false);
    }
}
