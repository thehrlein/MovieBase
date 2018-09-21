package tobiapplications.com.moviebase.ui.search;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.CompoundButton;

import com.crashlytics.android.answers.Answers;
import com.crashlytics.android.answers.SearchEvent;

import tobiapplications.com.moviebase.R;
import tobiapplications.com.moviebase.databinding.FragmentSearchQueryBinding;
import tobiapplications.com.moviebase.ui.NavigationActivity;
import tobiapplications.com.moviebase.utils.GeneralUtils;
import tobiapplications.com.moviebase.utils.InputManager;
import tobiapplications.com.moviebase.utils.StringUtils;

/**
 * Created by Tobias on 10.09.2017.
 */

public class SearchQueryFragment extends Fragment implements SearchQueryContract.View {

    private FragmentSearchQueryBinding bind;
    private Context context;
    private SearchQueryPresenter presenter;
    private NavigationActivity activity;

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
        presenter = new SearchQueryPresenter(this, context);
        presenter.attach(this);
        activity = (NavigationActivity) getActivity();
        return bind.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter.init();
    }

    @Override
    public void setSearchEditText() {
        bind.searchEdittext.setOnEditorActionListener(((textView, id, keyEvent) -> onAction(id)));
        bind.searchEdittext.setHint(context.getString(R.string.search_hint));
        bind.searchEdittext.setImeAction(EditorInfo.IME_ACTION_SEARCH);
        bind.searchEdittext.setInputType(InputType.TYPE_CLASS_TEXT);
    }

    private boolean onAction(int id) {
        return presenter.onAction(id);
    }

    @Override
    public void setSearchButton(String text) {
        String type = bind.radioMovies.isChecked() ? context.getString(R.string.movie_title) : context.getString(R.string.series_title);
        bind.buttonSearch.setText(text  + " " + type);
    }

    @Override
    public void setListener() {
        bind.radioMovies.setOnCheckedChangeListener((this::onRadioButtonCheckedChange));
        bind.radioSeries.setOnCheckedChangeListener(this::onRadioButtonCheckedChange);
        bind.buttonSearch.setOnClickListener(view -> presenter.onSearchButtonClicked());
    }

    @Override
    public void openSearchResults(int type) {
        String text = bind.searchEdittext.getText().toString();
        InputManager.hideKeyboardFor(bind.searchEdittext);
        if (inputInvalid()) {
            showErrorMessage();
            return;
        }

        Answers.getInstance().logSearch(new SearchEvent()
                .putCustomAttribute(context.getString(R.string.type_identifier), GeneralUtils.getTypeString(context, type))
                .putQuery(text));

        activity.openSearchResults(text, type);
    }

    private void showErrorMessage() {
        bind.searchEdittext.setError(context.getString(R.string.search_error_not_enough_characters));
    }

    private boolean inputInvalid() {
        String input = bind.searchEdittext.getText().toString();
        return StringUtils.nullOrEmpty(input);
    }

    @Override
    public void setNavigationSelected(int menuId) {
        activity.setMenuItemChecked(menuId);
    }

    @Override
    public void setTitle(String title) {
        getActivity().setTitle(title);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (presenter != null) {
            presenter.detach();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public void onRadioButtonCheckedChange(CompoundButton compoundButton, boolean checked) {
        presenter.onRadioButtonCheckedChange(compoundButton, bind, checked);
    }

    @Override
    public void setMoviesSelected(boolean checked) {
        bind.radioSeries.setChecked(!checked);
    }

    @Override
    public void setSeriesSelected(boolean checked) {
        bind.radioMovies.setChecked(!checked);
    }

    @Override
    public boolean isMovieChecked() {
        return bind.radioMovies.isChecked();
    }

    @Override
    public boolean isSerieChecked() {
        return bind.radioSeries.isChecked();
    }
}
