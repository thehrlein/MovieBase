package tobiapplications.com.moviebase.ui.views;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;

import java.util.ArrayList;

/**
 * Created by Tobias on 30.07.2017.
 */

public class DynamicRadioGroup implements View.OnClickListener{

    private ArrayList<RadioButton> radioButtons;
    private Context context;
    private String helpTitle;
    private String helpMessage;
    private SimpleDialog dialog;
    private ImageView helpIcon;

    public DynamicRadioGroup(Context context) {
        radioButtons = new ArrayList<>();
        this.context = context;

    }

    public DynamicRadioGroup(Context context, String helpTitle, String helpMessage, ImageView helpIcon) {
        radioButtons = new ArrayList<>();
        this.helpTitle = helpTitle;
        this.helpMessage = helpMessage;
        this.context = context;
        this.helpIcon = helpIcon;

        initializeDialog();
    }

    private void initializeDialog() {
        dialog = new SimpleDialog(context);
        dialog.setTitle(helpTitle);
        dialog.setMessage(helpMessage);
        helpIcon.setOnClickListener(this);
    }

    public void addRadioLayout(RadioButton button) {
        radioButtons.add(button);
        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == helpIcon) {
            openHelpDialog();
        } else {
            RadioButton radioButton = findRadioById(v.getId());
            radioButton.setChecked(true);

            deselectOtherRadios(radioButton);
        }
    }

    private void deselectOtherRadios(RadioButton radioButton) {
        for (RadioButton button : radioButtons) {
            if (button != radioButton) {
                button.setChecked(false);
            }
        }
    }

    public RadioButton findRadioById(int radioId) {
        RadioButton radioButton = null;
        for (RadioButton button : radioButtons) {
            if (button.getId() == radioId) {
                radioButton = button;
            }
        }

        return radioButton;
    }

    private void openHelpDialog() {
        if (TextUtils.isEmpty(helpMessage)) {
            helpMessage = "Not supported yet";
        }
        dialog.show();
    }

    public void setOnOneButtonEnabled(){
        dialog.setOnlyOneButtonEnabled();
    }
}
