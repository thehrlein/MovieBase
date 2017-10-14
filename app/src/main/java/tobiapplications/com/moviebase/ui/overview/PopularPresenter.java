package tobiapplications.com.moviebase.ui.overview;

import android.content.Context;

import tobiapplications.com.moviebase.utils.Constants;

/**
 * Created by Tobias on 09.06.2017.
 */

public class PopularPresenter extends BaseTabPresenter implements OverviewTabContract.Presenter {

    public PopularPresenter(OverviewTabContract.View parent, Context context) {
        super(parent, context, Constants.Category.POPULAR);
    }
}
