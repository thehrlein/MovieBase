package tobiapplications.com.moviebase.ui.info;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.crashlytics.android.answers.Answers;
import com.crashlytics.android.answers.ContentViewEvent;

import tobiapplications.com.moviebase.R;
import tobiapplications.com.moviebase.databinding.FragmentAboutBinding;
import tobiapplications.com.moviebase.ui.NavigationActivity;

/**
 * Created by Tobias on 10.09.2017.
 */

public class AboutFragment extends Fragment implements AboutContract.View {

    private FragmentAboutBinding bind;
    private AboutPresenter presenter;

    public static AboutFragment newInstance() {
        return new AboutFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        bind = FragmentAboutBinding.inflate(inflater);

        return bind.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter = new AboutPresenter(this);
        presenter.attach(this);
        presenter.init();
        initialize();
    }

    private void initialize() {
        getActivity().setTitle(R.string.about);
        bind.fab.setOnClickListener(v -> presenter.fabButtonClicked());
    }

    @Override
    public void setNavigationSelected() {
        NavigationActivity navigationActivity = (NavigationActivity) getActivity();
        navigationActivity.setMenuItemChecked(R.id.menu_info);
    }

    @Override
    public void sendEmail(Intent email) {
        Intent mailer  = Intent.createChooser(email, getContext().getString(R.string.about_email_chooser_title));
        try {
            startActivity(mailer);
            Toast.makeText(getContext(), getContext().getString(R.string.about_send_email_loading), Toast.LENGTH_SHORT).show();
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(getContext(), getContext().getString(R.string.about_no_email_clients), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (presenter != null) {
            presenter.detach();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
