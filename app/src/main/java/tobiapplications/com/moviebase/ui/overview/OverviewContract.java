package tobiapplications.com.moviebase.ui.overview;


/**
 * Created by Tobias on 13.06.2017.
 */

public interface OverviewContract {

    interface Presenter {
        void init();
    }

    interface View {
        void init(int overviewType);

        void setNavigationSelected(int menuId);

        void setTitle(boolean isMovie);

        void setupViewPager();
    }
}
