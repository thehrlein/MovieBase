package tobiapplications.com.moviebase.ui.overview;

import android.os.Bundle;

import java.lang.ref.WeakReference;

import tobiapplications.com.moviebase.R;
import tobiapplications.com.moviebase.utils.Constants;
import tobiapplications.com.moviebase.utils.GeneralUtils;
import tobiapplications.com.moviebase.utils.mvp.BasePresenter;

import static tobiapplications.com.moviebase.utils.GeneralUtils.*;

/**
 * Created by Tobias Hehrlein on 13.10.2017.
 */

public class OverviewPresenter extends BasePresenter<OverviewContract.View> implements OverviewContract.Presenter {

    private WeakReference<OverviewFragment> fragment;
    private int type;

    public OverviewPresenter(OverviewFragment overviewFragment, Bundle arguments) {
        this.fragment = new WeakReference<>(overviewFragment);

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
        if (GeneralUtils.weakReferenceIsValid(fragment)) {
            fragment.get().init(type);
            int menuId = isMovie(type) ? R.id.menu_movies : R.id.menu_series;
            fragment.get().setNavigationSelected(menuId);
            fragment.get().setTitle(isMovie(type));
        }
    }
}
