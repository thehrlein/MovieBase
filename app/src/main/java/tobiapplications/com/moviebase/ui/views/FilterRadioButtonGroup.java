package tobiapplications.com.moviebase.ui.views;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import tobiapplications.com.moviebase.R;
import tobiapplications.com.moviebase.listener.OnFilterRadioClickListener;

/**
 * Created by Tobias on 26.06.2017.
 */

public class FilterRadioButtonGroup extends LinearLayout implements OnFilterRadioClickListener, View.OnClickListener {

    private TextView radioButtonGroupTitle;
    private LinearLayout radioButtonGroupContent;
    private ArrayList<FilterRadioButton> filterRadioButtons;
    private Context context;
    private ImageView radioGroupHelpIcon;
    private String helpMessage;
    private ArrayList<View> content;

    public FilterRadioButtonGroup(Context context) {
        super(context);
        init(context);
    }

    public FilterRadioButtonGroup(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        this.context = context;
        LinearLayout rootView = (LinearLayout) inflate(context, R.layout.view_filter_radio_button_group, this);
        radioButtonGroupContent = (LinearLayout) rootView.findViewById(R.id.radio_button_content);
        radioButtonGroupTitle = (TextView) rootView.findViewById(R.id.radio_button_group_title);
        radioGroupHelpIcon = (ImageView) rootView.findViewById(R.id.radio_button_group_help);
        filterRadioButtons = new ArrayList<>();
    }

    public void setTitle(String title) {
        radioButtonGroupTitle.setText(title);
    }

    public void addRadioButton(FilterRadioButton filterRadioButton) {
        radioButtonGroupContent.addView(filterRadioButton);
        filterRadioButtons.add(filterRadioButton);
    }

    public void addVerticalDivider(Context context){
        VerticalDivider divider = new VerticalDivider(context);
        radioButtonGroupContent.addView(divider);
    }

    public void initializeSimpleYesNoGroup() {
        FilterRadioButton radioYes = new FilterRadioButton(context);
        radioYes.disableHelpImage();
        radioYes.setRadioButtonText("yes");
        radioYes.setFilterRadioClickListener(this);
        radioYes.setChecked(true, false);
        FilterRadioButton radioNo = new FilterRadioButton(context);
        radioNo.setRadioButtonText("no");
        radioNo.disableHelpImage();
        radioNo.setFilterRadioClickListener(this);

        addRadioButton(radioYes);
        addVerticalDivider(context);
        addRadioButton(radioNo);
    }

    public FilterRadioButton findRadioById(int radioId) {
        FilterRadioButton filterRadioButton = null;
        for (FilterRadioButton button : filterRadioButtons) {
            if (button.getRadioId() == radioId) {
                filterRadioButton = button;
            }
        }

        return filterRadioButton;
    }

    @Override
    public void onRadioClick(int radioId) {
        FilterRadioButton radioButton = findRadioById(radioId);
        deselectAllRadiosExceptOf(radioButton);
    }

    private void deselectAllRadiosExceptOf(FilterRadioButton radioButton) {
        for (FilterRadioButton button : filterRadioButtons) {
            if (button != radioButton) {
                button.setChecked(false, true);
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.radio_button_group_help:
                openHelpDialog();
        }
    }

    private void openHelpDialog() {
        if (TextUtils.isEmpty(helpMessage)) {
            helpMessage = "Not supported yet";
        }

        SimpleDialog dialog = new SimpleDialog(context);
        dialog.setTitle(helpMessage);
        dialog.setOnlyOneButtonEnabled();

        for (View v : content) {
            dialog.addView(v);
        }

        dialog.show();
    }

    public void setHelpIcon(String title, ArrayList<View> content) {
        this.helpMessage = title;
        this.content = content;
        radioGroupHelpIcon.setVisibility(View.VISIBLE);
        radioGroupHelpIcon.setOnClickListener(this);
    }
}
