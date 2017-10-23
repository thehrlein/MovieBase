package tobiapplications.com.moviebase.ui.overview;


import tobiapplications.com.moviebase.utils.mvp.BaseMvpPresenter;
import tobiapplications.com.moviebase.utils.mvp.BaseView;

/**
 * Created by Tobias on 13.06.2017.
 */

public interface OverviewContract {

    interface Presenter extends BaseMvpPresenter<View> {
        void init();
    }

    interface View extends BaseView{
        void init(int overviewType);

        void setNavigationSelected(int menuId);

        void setTitle(boolean isMovie);

        void setupViewPager();
    }
}
