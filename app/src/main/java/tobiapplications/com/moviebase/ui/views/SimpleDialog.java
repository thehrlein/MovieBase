package tobiapplications.com.moviebase.ui.views;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import tobiapplications.com.moviebase.R;

/**
 * Created by Tobias on 12.07.2017.
 */

public class SimpleDialog extends Dialog implements View.OnClickListener {

    private Context context;
    private Button buttonCancel;
    private Button buttonOk;
    private TextView dialogTitle;
    private TextView dialogMessage;
    private View buttonDivider;

    public SimpleDialog(@NonNull Context context) {
        super(context);
        init(context);
    }

    public SimpleDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
        init(context);
    }

    public SimpleDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        init(context);
    }

    private void init(Context context) {
        this.context = context;

        setContentView(R.layout.view_simple_dialog);
        dialogTitle = (TextView) findViewById(R.id.dialog_title);
        dialogMessage = (TextView) findViewById(R.id.dialog_message);
        buttonCancel = (Button) findViewById(R.id.dialog_button_cancel);
        buttonOk = (Button) findViewById(R.id.dialog_button_ok);
        buttonDivider = findViewById(R.id.dialog_button_divider);
        buttonCancel.setOnClickListener(this);
        buttonOk.setOnClickListener(this);
        setDialogSize();
    }

    private void setDialogSize() {
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindow().getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int width = (int) (displaymetrics.widthPixels * 0.9);
        int height = (int) (displaymetrics.heightPixels * 0.6);
        getWindow().setLayout(width,height);
    }

    public void setOnlyOneButtonEnabled() {
        buttonCancel.setVisibility(View.GONE);
        buttonDivider.setVisibility(View.GONE);
    }

    @Override
    public void setTitle(@Nullable CharSequence title) {
        dialogTitle.setText(title);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dialog_button_cancel:
                dismiss();
                break;
            case R.id.dialog_button_ok:
                dismiss();
                break;
        }
    }

    public void setMessage(String helpMessage) {
        dialogMessage.setText(helpMessage);
    }
}
