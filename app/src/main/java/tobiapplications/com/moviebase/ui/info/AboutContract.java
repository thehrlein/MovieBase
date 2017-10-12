package tobiapplications.com.moviebase.ui.info;

import android.content.Intent;

/**
 * Created by Tobias Hehrlein on 13.10.2017.
 */

public interface AboutContract {

    interface Presenter {
        void init();
        void fabButtonClicked();
    }

    interface View {
        void setNavigationSelected();
        void sendEmail(Intent mailer);
    }
}
