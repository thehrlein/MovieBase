package tobiapplications.com.moviebase.ui.search;

import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.ArrayList;

import tobiapplications.com.moviebase.R;
import tobiapplications.com.moviebase.ui.views.FilterRadioButton;
import tobiapplications.com.moviebase.ui.views.FilterRadioButtonGroup;

/**
 * Created by Tobias on 23.06.2017.
 */

public class SearchFilterFragment extends DialogFragment implements View.OnClickListener {

    private Context context;
    private LinearLayout filterBaseLayout;
    private Toolbar toolbar;
    private Button buttonSubmit;
    private NestedScrollView scrollView;
    private View rootView;
//    private Spinner releaseSpinnerFrom;
//    private Spinner releaseSpinnerUntil;
//    private Button searchButton;
//    releaseSpinnerFrom = (Spinner) view.findViewById(R.id.filter_release_spinner_from);
//    releaseSpinnerUntil = (Spinner) view.findViewById(R.id.filter_release_spinner_until);
//    searchButton = (Button) view.findViewById(R.id.filter_button_search);

    public SearchFilterFragment() {
    }

    public static SearchFilterFragment newInstance() {
        SearchFilterFragment filterFragment = new SearchFilterFragment();

        return filterFragment;
    }

    private void findViews(View view) {
        if (view != null) {
            filterBaseLayout = (LinearLayout) view.findViewById(R.id.filter_content);
            scrollView = (NestedScrollView) view.findViewById(R.id.scrollView);
            toolbar = (Toolbar) view.findViewById(R.id.toolbar);
            buttonSubmit = (Button) view.findViewById(R.id.filter_button_submit);
        }
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        rootView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_fullscreen, null);
        context = getContext();
        findViews(rootView);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.SlidingDialogTheme);
        builder.setOnKeyListener(((dialog, keyCode, event) -> {
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                dismiss();
                return true;
            }
            return false;
        }));

        builder.setView(rootView);
        buildFilterLayout();
        return builder.create();

    }

    @Override
    public void onResume() {
        super.onResume();
        initToolbar();
    }

    private void dismissOverlay() {
        dismiss();
    }

    public void initToolbar() {
        toolbar.setTitle("TEST");
        toolbar.setNavigationIcon(R.drawable.ic_close_white_24dp);
        toolbar.setNavigationOnClickListener(v -> dismissOverlay());
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = getDialog().getWindow();
            if (window != null) {
                window.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
            }
        }
    }

    private void buildFilterLayout() {
        FilterRadioButtonGroup adultGroup = createAdultGroup();
        filterBaseLayout.addView(adultGroup);
    }

    private FilterRadioButtonGroup createAdultGroup() {
        FilterRadioButtonGroup adultGroup = new FilterRadioButtonGroup(context);
        adultGroup.initializeSimpleYesNoGroup();
        adultGroup.setTitle("Include adult videos?");

        return adultGroup;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.filter_button_submit:
                searchBasedOnParameters();
        }
    }

    private void searchBasedOnParameters() {

      //  dismiss();
    }

//    @Override
//    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//        menu.clear();
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        int id = item.getItemId();
//
//        if (id == android.R.id.home) {
//            // handle close button click here
//            Activity activity = getActivity();
//            if (activity instanceof  OnFilterSearchListener) {
//                ((OnFilterSearchListener)activity).onCancelFiltering();
//            }
//            dismiss();
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

//    private void setUpSpinner() {
//        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(context, R.layout.dialog_search_spinner_item);
//        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        String[] values = getSpinnerValues();
//        spinnerAdapter.addAll(values);
//        releaseSpinnerFrom.setAdapter(spinnerAdapter);
//        releaseSpinnerUntil.setAdapter(spinnerAdapter);
//        releaseSpinnerFrom.setSelection(values.length - 1);
//    }
//
//    public String[] getSpinnerValues() {
//        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
//        int firstYear = 1900;
//        int size = (currentYear - firstYear) + 1;
//        String[] values = new String[size];
//
//        int position = 0;
//        for (int i = currentYear; i >= firstYear; i--) {
//            values[position] = String.valueOf(i);
//            position++;
//        }
//
//        return values;
//    }

}
