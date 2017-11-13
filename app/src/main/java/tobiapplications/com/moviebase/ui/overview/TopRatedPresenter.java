package tobiapplications.com.moviebase.ui.overview;

import android.content.Context;

import tobiapplications.com.moviebase.utils.Constants;

/**
 * Created by Tobias on 11.06.2017.
 */

public class TopRatedPresenter extends BaseTabPresenter implements OverviewTabContract.Presenter {

    public TopRatedPresenter(OverviewTabContract.View parent, Context context) {
        super(parent, context, Constants.Category.TOP_RATED);
    }
}
