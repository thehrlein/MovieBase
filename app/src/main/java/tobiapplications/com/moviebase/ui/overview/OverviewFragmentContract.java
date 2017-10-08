package tobiapplications.com.moviebase.ui.overview;

import tobiapplications.com.moviebase.listener.OnOverviewResponseLoadedListener;

/**
 * Created by Tobias on 13.06.2017.
 */

public interface OverviewFragmentContract {

    interface View extends OnOverviewResponseLoadedListener {
        void setupViewPager();
    }
}
