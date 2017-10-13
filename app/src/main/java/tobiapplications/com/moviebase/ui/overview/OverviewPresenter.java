package tobiapplications.com.moviebase.ui.overview;

import android.os.Bundle;

import tobiapplications.com.moviebase.R;
import tobiapplications.com.moviebase.utils.Constants;

import static tobiapplications.com.moviebase.utils.GeneralUtils.*;

/**
 * Created by Tobias Hehrlein on 13.10.2017.
 */

public class OverviewPresenter implements OverviewContract.Presenter {

    private OverviewFragment fragment;
    private int type;

    public OverviewPresenter(OverviewFragment overviewFragment, Bundle arguments) {
        this.fragment = overviewFragment;

        parseArguments(arguments);
    }

    private void parseArguments(Bundle arguments) {
        if (arguments == null) {
            return;
        }

        if (arguments.containsKey(Constants.TYPE)) {
            type = arguments.getInt(Constants.TYPE);
        }
    }

    @Override
    public void init() {
        fragment.init(type);
        int menuId = isMovie(type) ? R.id.menu_movies : R.id.menu_series;
        fragment.setNavigationSelected(menuId);
        fragment.setTitle(isMovie(type));
    }
}
