package tobiapplications.com.moviebase.ui.views;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import tobiapplications.com.moviebase.databinding.ViewClearableEdittextBinding;

/**
 * Created by Tobias Hehrlein on 10.10.2017.
 */

public class ClearableEditText extends RelativeLayout {

    private ViewClearableEdittextBinding bind;

    public ClearableEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initViews(context);
    }

    public ClearableEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        initViews(context);
    }

    public ClearableEditText(Context context) {
        super(context);
        initViews(context);
    }
    private void initViews(Context context) {
        LayoutInflater inflater = LayoutInflater.from(context);
        bind = ViewClearableEdittextBinding.inflate(inflater, this, true);
        bind.clearableButtonClear.setVisibility(View.INVISIBLE);
        setClearListener();
        setTextWatcher();
    }

    private void setClearListener() {
        bind.clearableButtonClear.setOnClickListener(view -> bind.clearableEdit.setText(""));
    }

    private void setTextWatcher() {
        bind.clearableEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0)
                    bind.clearableButtonClear.setVisibility(RelativeLayout.VISIBLE);
                else
                    bind.clearableButtonClear.setVisibility(RelativeLayout.INVISIBLE);
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public Editable getText() {
        return bind.clearableEdit.getText();
    }

    public void setOnEditorActionListener(TextView.OnEditorActionListener actionListener) {
        bind.clearableEdit.setOnEditorActionListener((actionListener));
    }

    public void setError(String errorMessage) {
        bind.clearableEdit.setError(errorMessage);
    }

    public void setMaxLines(int maxLines) {
        bind.clearableEdit.setMaxLines(maxLines);
    }

    public void setHint(String hintMessage) {
        bind.clearableEdit.setHint(hintMessage);
    }

    public void setImeAction(int editorInfo) {
        bind.clearableEdit.setImeOptions(editorInfo);
    }

    public void setInputType(int inputType) {
        bind.clearableEdit.setInputType(inputType);
    }
}