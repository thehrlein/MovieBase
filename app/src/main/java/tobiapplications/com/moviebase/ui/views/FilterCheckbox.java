package tobiapplications.com.moviebase.ui.views;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;

import tobiapplications.com.moviebase.R;

/**
 * Created by Tobias on 26.06.2017.
 */

public class FilterCheckbox extends LinearLayout {

    private CheckBox filterCheckbox;
    private ImageView filterHelp;
    private LinearLayout filterHelpLayout;

    public FilterCheckbox(Context context) {
        super(context);
        init(context);
    }

    public FilterCheckbox(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public FilterCheckbox(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        LinearLayout rootView = (LinearLayout) inflate(context, R.layout.view_filter_checkbox, this);
        filterCheckbox = (CheckBox) rootView.findViewById(R.id.checkbox);
        filterHelp = (ImageView) rootView.findViewById(R.id.checkbox_help);
        filterHelpLayout = (LinearLayout) rootView.findViewById(R.id.checkbox_help_layout);
    }

    public void disableHelpImage() {
        filterHelpLayout.setVisibility(View.GONE);
    }

    public void setChecked(boolean checked) {
        filterCheckbox.setChecked(checked);
    }

    public void setCheckboxText(String text) {
        filterCheckbox.setText(text);
    }

}
