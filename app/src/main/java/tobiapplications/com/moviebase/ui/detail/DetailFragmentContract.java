package tobiapplications.com.moviebase.ui.detail;

import java.util.ArrayList;

import tobiapplications.com.moviebase.model.DisplayableItem;
import tobiapplications.com.moviebase.utils.mvp.BaseMvpPresenter;
import tobiapplications.com.moviebase.utils.mvp.BaseView;

/**
 * Created by Tobias on 13.06.2017.
 */

public interface DetailFragmentContract {

    interface View extends BaseView{
        void setAdapter(int overviewType);
        void displayUiViews(ArrayList<DisplayableItem> detailItems);
        void displayUiView(DisplayableItem item);
    }

    interface Presenter extends BaseMvpPresenter<View> {
        void init();
    }
}
