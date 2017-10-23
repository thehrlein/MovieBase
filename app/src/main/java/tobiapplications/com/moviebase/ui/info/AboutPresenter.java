package tobiapplications.com.moviebase.ui.info;

import android.content.Intent;

import tobiapplications.com.moviebase.R;
import tobiapplications.com.moviebase.utils.mvp.BasePresenter;

/**
 * Created by Tobias Hehrlein on 13.10.2017.
 */

public class AboutPresenter extends BasePresenter<AboutContract.View> implements AboutContract.Presenter {

    private AboutFragment fragment;

    public AboutPresenter(AboutFragment fragment) {
        this.fragment = fragment;
    }

    @Override
    public void init() {
        fragment.setNavigationSelected();
    }

    @Override
    public void fabButtonClicked() {
        String text = fragment.getString(R.string.hello_tobias);
        String email = fragment.getString(R.string.tobiapplications);

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("message/rfc822");
        intent.putExtra(Intent.EXTRA_SUBJECT, fragment.getString(R.string.app_name));
        intent.putExtra(Intent.EXTRA_TEXT, text);
        intent.putExtra(Intent.EXTRA_EMAIL, new String[] {email});
        Intent mailer = Intent.createChooser(intent, null);

        fragment.sendEmail(mailer);
    }
}
