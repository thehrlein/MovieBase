package tobiapplications.com.moviebase.ui.info;

import android.content.Intent;

import java.lang.ref.WeakReference;

import tobiapplications.com.moviebase.R;
import tobiapplications.com.moviebase.utils.GeneralUtils;
import tobiapplications.com.moviebase.utils.mvp.BasePresenter;

/**
 * Created by Tobias Hehrlein on 13.10.2017.
 */

public class AboutPresenter extends BasePresenter<AboutContract.View> implements AboutContract.Presenter {

    private WeakReference<AboutFragment> fragment;

    public AboutPresenter(AboutFragment fragment) {
        this.fragment = new WeakReference<>(fragment);
    }

    @Override
    public void init() {
        if (GeneralUtils.weakReferenceIsValid(fragment)) {
          fragment.get().setNavigationSelected();
        }
    }

    @Override
    public void fabButtonClicked() {
        if (GeneralUtils.weakReferenceIsValid(fragment)) {
            String text = fragment.get().getString(R.string.about_email_text);
            String email = fragment.get().getString(R.string.about_email);
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("message/rfc822");
            intent.putExtra(Intent.EXTRA_SUBJECT, fragment.get().getString(R.string.app_name));
            intent.putExtra(Intent.EXTRA_TEXT, text);
            intent.putExtra(Intent.EXTRA_EMAIL, new String[] {email});
            fragment.get().sendEmail(intent);
        }
    }
}
