package tobiapplications.com.moviebase.ui.info;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import tobiapplications.com.moviebase.R;
import tobiapplications.com.moviebase.databinding.FragmentAboutBinding;
import tobiapplications.com.moviebase.ui.NavigationActivity;

/**
 * Created by Tobias on 10.09.2017.
 */

public class AboutFragment extends Fragment {

    private FragmentAboutBinding bind;

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

        initialize();
    }

    private void initialize() {
        getActivity().setTitle(R.string.about);
        bind.fab.setOnClickListener((View v) -> sendEmail());

        setNavigationSelected();
    }

    private void setNavigationSelected() {
        NavigationActivity navigationActivity = (NavigationActivity) getActivity();
        navigationActivity.setMenuItemChecked(R.id.menu_info);
    }

    private void sendEmail()
    {
        String text = getString(R.string.hello_tobias);
        String email = getString(R.string.tobiapplications);

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("message/rfc822");
        intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_name));
        intent.putExtra(Intent.EXTRA_TEXT, text);
        intent.putExtra(Intent.EXTRA_EMAIL, new String[] {email});
        Intent mailer = Intent.createChooser(intent, null);
        startActivity(mailer);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
