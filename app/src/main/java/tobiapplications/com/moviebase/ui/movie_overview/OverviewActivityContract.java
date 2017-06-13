package tobiapplications.com.moviebase.ui.movie_overview;

/**
 * Created by Tobias on 13.06.2017.
 */

public interface OverviewActivityContract {

    interface View {
        void disableActionBarTabLayoutDivider();
        void setStatusBarColor();
        void findMyViews();
        void setupViewPager();
    }
}
