package tobiapplications.com.moviebase.ui.views;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;

import tobiapplications.com.moviebase.R;
import tobiapplications.com.moviebase.databinding.ViewSimpleDialogBinding;

/**
 * Created by Tobias on 12.07.2017.
 */

public class SimpleDialog extends Dialog implements View.OnClickListener {

    private ViewSimpleDialogBinding bind;
    private Context context;

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
        LayoutInflater inflater = LayoutInflater.from(context);
        bind = ViewSimpleDialogBinding.inflate(inflater);
        bind.dialogButtonCancel.setOnClickListener(this);
        bind.dialogButtonOk.setOnClickListener(this);
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
        bind.dialogButtonCancel.setVisibility(View.GONE);
        bind.dialogButtonDivider.setVisibility(View.GONE);
    }

    @Override
    public void setTitle(@Nullable CharSequence title) {
        bind.dialogTitle.setText(title);
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
        bind.dialogMessage.setText(helpMessage);
    }
}
