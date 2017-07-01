package tobiapplications.com.moviebase.ui.views;

import android.content.Context;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import tobiapplications.com.moviebase.R;
import tobiapplications.com.moviebase.listener.OnFilterRadioClickListener;

/**
 * Created by Tobias on 26.06.2017.
 */

public class FilterRadioButton extends LinearLayout implements CompoundButton.OnCheckedChangeListener{

    private RadioButton filterRadioButton;
    private ImageView filterHelp;
    private LinearLayout filterHelpLayout;
    private OnFilterRadioClickListener filterRadioClickListener;
    private int radioId;
    private boolean programmaticallySet = false;

    public FilterRadioButton(Context context) {
        super(context);
        init(context);
    }

    public FilterRadioButton(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public FilterRadioButton(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        radioId = View.generateViewId();
        LinearLayout rootView = (LinearLayout) inflate(context, R.layout.view_filter_radio_button, this);
        filterRadioButton = (RadioButton) rootView.findViewById(R.id.radio_button);
        filterHelp = (ImageView) rootView.findViewById(R.id.radio_button_help);
        filterHelpLayout = (LinearLayout) rootView.findViewById(R.id.radio_button_help_layout);
        filterRadioButton.setOnCheckedChangeListener(this);
        LayoutParams layoutParams = new LayoutParams(0, LayoutParams.WRAP_CONTENT, 1f);
        setLayoutParams(layoutParams);
    }

    public void disableHelpImage() {
        filterHelpLayout.setVisibility(View.GONE);
    }

    public void setChecked(boolean checked, boolean programmaticallySet) {
        if (checked != filterRadioButton.isChecked()) {
            this.programmaticallySet = programmaticallySet;
        }
        filterRadioButton.setChecked(checked);
    }

    public void setRadioButtonText(String text) {
        filterRadioButton.setText(text);
    }

    public void setFilterRadioClickListener(OnFilterRadioClickListener onFilterRadioClickListener) {
        this.filterRadioClickListener = onFilterRadioClickListener;
    }

    public int getRadioId() {
        return radioId;
    }

    public String getRadioText() {
        return filterRadioButton.getText().toString();
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (!programmaticallySet) {
            if (filterRadioClickListener != null) {
                filterRadioClickListener.onRadioClick(radioId);
            }
        } else {
            programmaticallySet = false;
        }
    }
}
