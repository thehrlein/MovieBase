package tobiapplications.com.moviebase.ui.info;

import android.content.Intent;

import tobiapplications.com.moviebase.utils.mvp.BaseMvpPresenter;
import tobiapplications.com.moviebase.utils.mvp.BaseView;

/**
 * Created by Tobias Hehrlein on 13.10.2017.
 */

public interface AboutContract {

    interface Presenter extends BaseMvpPresenter<View> {
        void init();
        void fabButtonClicked();
    }

    interface View extends BaseView {
        void setNavigationSelected();
        void sendEmail(Intent mailer);
    }
}
