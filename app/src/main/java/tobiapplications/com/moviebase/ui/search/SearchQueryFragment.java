package tobiapplications.com.moviebase.ui.search;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import tobiapplications.com.moviebase.R;
import tobiapplications.com.moviebase.databinding.FragmentSearchQueryBinding;
import tobiapplications.com.moviebase.ui.NavigationActivity;
import tobiapplications.com.moviebase.utils.Constants;
import tobiapplications.com.moviebase.utils.InputManager;
import tobiapplications.com.moviebase.utils.StringUtils;

/**
 * Created by Tobias on 10.09.2017.
 */

public class SearchQueryFragment extends Fragment {

    private FragmentSearchQueryBinding bind;
    private Context context;

    public static SearchQueryFragment newInstance() {
        SearchQueryFragment fragment = new SearchQueryFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        bind = FragmentSearchQueryBinding.inflate(inflater);
        context = bind.getRoot().getContext();
        return bind.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }

    private void init() {
        setTitle();
        setNavigationSelected();
        setListener();
        setSearchButton();
        setSearchEditText();
    }

    private void setSearchEditText() {
        bind.searchEdittext.setOnEditorActionListener(((textView, id, keyEvent) -> onAction(id)));
        bind.searchEdittext.setHint(context.getString(R.string.search_hint));
        bind.searchEdittext.setImeAction(EditorInfo.IME_ACTION_SEARCH);
        bind.searchEdittext.setInputType(InputType.TYPE_CLASS_TEXT);
    }

    private boolean onAction(int id) {
        if (id == EditorInfo.IME_ACTION_SEARCH) {
            openSearchResults(bind.searchEdittext.getText().toString());
            return true;
        }
        return false;
    }

    private void setSearchButton() {
        String searchString = context.getString(R.string.search_title);
        String type = bind.radioMovies.isChecked() ? context.getString(R.string.movie_title) : context.getString(R.string.series_title);
        String buttonText = searchString + " " + type;
        bind.buttonSearch.setText(buttonText);
    }

    private void setListener() {
        bind.radioMovies.setOnCheckedChangeListener((this::onRadioButtonCheckedChange));
        bind.radioSeries.setOnCheckedChangeListener(this::onRadioButtonCheckedChange);
        bind.buttonSearch.setOnClickListener(view -> openSearchResults(bind.searchEdittext.getText().toString()));
    }

    private void openSearchResults(String text) {
        InputManager.hideKeyboardFor(bind.searchEdittext);
        if (inputInvalid()) {
            showErrorMessage();
            return;
        }
        int overviewType = getOverviewType();
        ((NavigationActivity) getActivity()).openSearchResults(text, overviewType);
    }

    private void showErrorMessage() {
        bind.searchEdittext.setError(context.getString(R.string.search_error_not_enough_characters));
    }

    private boolean inputInvalid() {
        String input = bind.searchEdittext.getText().toString();
        if (StringUtils.nullOrEmpty(input)) {
            return true;
        }

        return false;
    }

    private int getOverviewType() {
        int overviewType = -1;
        if (bind.radioMovies.isChecked()) {
            overviewType = Constants.OverviewType.MOVIES;
        } else if (bind.radioSeries.isChecked()) {
            overviewType = Constants.OverviewType.SERIES;
        }

        return overviewType;
    }

    private void setNavigationSelected() {
        NavigationActivity navigationActivity = (NavigationActivity) getActivity();
        navigationActivity.setMenuItemChecked(R.id.menu_search);
    }

    private void setTitle() {
        String title = context.getString(R.string.search_title);
        getActivity().setTitle(title);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public void onRadioButtonCheckedChange(CompoundButton compoundButton, boolean checked) {
        if (compoundButton == bind.radioMovies) {
            bind.radioSeries.setChecked(!checked);
        } else if (compoundButton == bind.radioSeries) {
            bind.radioMovies.setChecked(!checked);
        }

        setSearchButton();
    }
}
