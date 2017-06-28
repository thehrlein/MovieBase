package tobiapplications.com.moviebase.ui.views;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import tobiapplications.com.moviebase.R;

/**
 * Created by Tobias on 26.06.2017.
 */

public class FilterCheckboxGroup extends LinearLayout {

    private TextView checkboxGroupTitle;
    private LinearLayout checkboxGroupContent;
    private ArrayList<FilterCheckbox> filterCheckboxes;

    public FilterCheckboxGroup(Context context) {
        super(context);
        init(context);
    }

    public FilterCheckboxGroup(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public FilterCheckboxGroup(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        LinearLayout rootView = (LinearLayout) inflate(context, R.layout.view_filter_checkbox_group, this);
        checkboxGroupContent = (LinearLayout) rootView.findViewById(R.id.checkbox_content);
        checkboxGroupTitle = (TextView) rootView.findViewById(R.id.checkbox_group_title);
        filterCheckboxes = new ArrayList<>();
    }

    public void setTitle(String title) {
        checkboxGroupTitle.setText(title);
    }

    public void addCheckbox(FilterCheckbox filterCheckbox) {
        checkboxGroupContent.addView(filterCheckbox);
        filterCheckboxes.add(filterCheckbox);
    }

    public void addVerticalDivider(Context context){
        VerticalDivider divider = new VerticalDivider(context);
        checkboxGroupContent.addView(divider);
    }

    public void setOrientation(int orientation) {
        setOrientation(orientation);
    }
}
