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
        if (isAttached()) {
            getView().setListener();
            getView().setSearchEditText();
        }
    }

    private void getType() {
        if (isAttached()) {
            if (getView().isMovieChecked()) {
                type = Constants.Type.MOVIES;
            } else if (getView().isSerieChecked()) {
                type = Constants.Type.SERIES;
            }
        }
    }

    private void setSearchButton() {
        String searchString = context.getString(R.string.search_title);
        if (isAttached()) {
            getView().setSearchButton(searchString);
        }
    }

    private void setNavigationSelected() {
        if (isAttached()) {
            getView().setNavigationSelected(R.id.menu_search);
        }
    }

    private void setTitle() {
        String title = context.getString(R.string.search_title);
        if (isAttached()) {
            getView().setTitle(title);
        }
    }

    @Override
    public void onRadioButtonCheckedChange(CompoundButton compoundButton, FragmentSearchQueryBinding bind, boolean checked) {
        if (isAttached()) {
            if (compoundButton == bind.radioMovies) {
                getView().setMoviesSelected(checked);
            } else if (compoundButton == bind.radioSeries) {
                getView().setSeriesSelected(checked);
            }
        }

        setSearchButton();
    }

    @Override
    public boolean onAction(int id) {
        getType();
        if (id == EditorInfo.IME_ACTION_SEARCH) {
            if (isAttached()) {
                getView().openSearchResults(type);
            }
            return true;
        }
        return false;
    }

    @Override
    public void onSearchButtonClicked() {
        getType();
        if (isAttached()) {
            getView().openSearchResults(type);
        }
    }
}
