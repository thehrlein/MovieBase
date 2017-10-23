package tobiapplications.com.moviebase.ui.search;

import android.widget.CompoundButton;

import tobiapplications.com.moviebase.databinding.FragmentSearchQueryBinding;
import tobiapplications.com.moviebase.utils.mvp.BaseMvpPresenter;
import tobiapplications.com.moviebase.utils.mvp.BaseView;

/**
 * Created by Tobias Hehrlein on 14.10.2017.
 */

public interface SearchQueryContract {

    interface Presenter extends BaseMvpPresenter<View> {
        void init();
        void onRadioButtonCheckedChange(CompoundButton compoundButton, FragmentSearchQueryBinding bind, boolean checked);
        boolean onAction(int id);
        void onSearchButtonClicked();
    }

    interface View extends BaseView {
        void setSearchEditText();
        void setSearchButton(String text);
        void setListener();
        void openSearchResults(int type);
        void setNavigationSelected(int menuId);
        void setTitle(String title);
        void setMoviesSelected(boolean checked);
        void setSeriesSelected(boolean checked);
        boolean isMovieChecked();
        boolean isSerieChecked();
    }
}
