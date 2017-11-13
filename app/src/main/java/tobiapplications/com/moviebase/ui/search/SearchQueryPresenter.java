package tobiapplications.com.moviebase.ui.search;

import android.content.Context;
import android.view.inputmethod.EditorInfo;
import android.widget.CompoundButton;

import java.lang.ref.WeakReference;

import tobiapplications.com.moviebase.R;
import tobiapplications.com.moviebase.databinding.FragmentSearchQueryBinding;
import tobiapplications.com.moviebase.utils.Constants;
import tobiapplications.com.moviebase.utils.GeneralUtils;
import tobiapplications.com.moviebase.utils.mvp.BasePresenter;

/**
 * Created by Tobias Hehrlein on 14.10.2017.
 */

public class SearchQueryPresenter extends BasePresenter<SearchQueryContract.View> implements SearchQueryContract.Presenter {

    private WeakReference<SearchQueryFragment> fragment;
    private Context context;
    private @Constants.Type int type;

    public SearchQueryPresenter(SearchQueryFragment searchQueryFragment, Context context) {
        this.fragment = new WeakReference<>(searchQueryFragment);
        this.context = context;
    }

    @Override
    public void init() {
        getType();
        setTitle();
        setNavigationSelected();
        setSearchButton();
        if (GeneralUtils.weakReferenceIsValid(fragment)) {
            fragment.get().setListener();
            fragment.get().setSearchEditText();
        }
    }

    private void getType() {
        if (GeneralUtils.weakReferenceIsValid(fragment)) {
            if (fragment.get().isMovieChecked()) {
                type = Constants.Type.MOVIES;
            } else if (fragment.get().isSerieChecked()) {
                type = Constants.Type.SERIES;
            }
        }
    }

    private void setSearchButton() {
        String searchString = context.getString(R.string.search_title);
        if (GeneralUtils.weakReferenceIsValid(fragment)) {
            fragment.get().setSearchButton(searchString);
        }
    }

    private void setNavigationSelected() {
        if (GeneralUtils.weakReferenceIsValid(fragment)) {
            fragment.get().setNavigationSelected(R.id.menu_search);
        }
    }

    private void setTitle() {
        String title = context.getString(R.string.search_title);
        if (GeneralUtils.weakReferenceIsValid(fragment)) {
            fragment.get().setTitle(title);
        }
    }

    @Override
    public void onRadioButtonCheckedChange(CompoundButton compoundButton, FragmentSearchQueryBinding bind, boolean checked) {
        if (GeneralUtils.weakReferenceIsValid(fragment)) {
            if (compoundButton == bind.radioMovies) {
                fragment.get().setMoviesSelected(checked);
            } else if (compoundButton == bind.radioSeries) {
                fragment.get().setSeriesSelected(checked);
            }
        }

        setSearchButton();
    }

    @Override
    public boolean onAction(int id) {
        getType();
        if (id == EditorInfo.IME_ACTION_SEARCH) {
            if (GeneralUtils.weakReferenceIsValid(fragment)) {
                fragment.get().openSearchResults(type);
            }
            return true;
        }
        return false;
    }

    @Override
    public void onSearchButtonClicked() {
        getType();
        if (GeneralUtils.weakReferenceIsValid(fragment)) {
            fragment.get().openSearchResults(type);
        }
    }
}
