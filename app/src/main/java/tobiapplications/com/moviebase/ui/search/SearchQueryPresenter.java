package tobiapplications.com.moviebase.ui.search;

import android.content.Context;
import android.view.inputmethod.EditorInfo;
import android.widget.CompoundButton;

import tobiapplications.com.moviebase.R;
import tobiapplications.com.moviebase.databinding.FragmentSearchQueryBinding;
import tobiapplications.com.moviebase.utils.Constants;
import tobiapplications.com.moviebase.utils.mvp.BasePresenter;

/**
 * Created by Tobias Hehrlein on 14.10.2017.
 */

public class SearchQueryPresenter extends BasePresenter<SearchQueryContract.View> implements SearchQueryContract.Presenter {

    private SearchQueryFragment fragment;
    private Context context;
    private int type;

    public SearchQueryPresenter(SearchQueryFragment searchQueryFragment, Context context) {
        this.fragment = searchQueryFragment;
        this.context = context;
    }

    @Override
    public void init() {
        getType();
        setTitle();
        setNavigationSelected();
        fragment.setListener();
        setSearchButton();
        fragment.setSearchEditText();
    }

    private void getType() {
        if (fragment.isMovieChecked()) {
            type = Constants.Type.MOVIES;
        } else if (fragment.isSerieChecked()) {
            type = Constants.Type.SERIES;
        }
    }

    private void setSearchButton() {
        String searchString = context.getString(R.string.search_title);
        fragment.setSearchButton(searchString);
    }

    private void setNavigationSelected() {
        fragment.setNavigationSelected(R.id.menu_search);
    }

    private void setTitle() {
        String title = context.getString(R.string.search_title);
        fragment.setTitle(title);
    }

    @Override
    public void onRadioButtonCheckedChange(CompoundButton compoundButton, FragmentSearchQueryBinding bind, boolean checked) {
        if (compoundButton == bind.radioMovies) {
            fragment.setMoviesSelected(checked);
        } else if (compoundButton == bind.radioSeries) {
            fragment.setSeriesSelected(checked);
        }

        setSearchButton();
    }

    @Override
    public boolean onAction(int id) {
        getType();
        if (id == EditorInfo.IME_ACTION_SEARCH) {
            fragment.openSearchResults(type);
            return true;
        }
        return false;
    }

    @Override
    public void onSearchButtonClicked() {
        getType();
        fragment.openSearchResults(type);
    }
}
