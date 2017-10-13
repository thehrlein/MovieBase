package tobiapplications.com.moviebase.ui.overview;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import tobiapplications.com.moviebase.R;
import tobiapplications.com.moviebase.adapter.OverviewAdapter;
import tobiapplications.com.moviebase.databinding.FragmentOverviewBinding;
import tobiapplications.com.moviebase.ui.NavigationActivity;
import tobiapplications.com.moviebase.utils.Constants;

/**
 * Created by Tobias on 10.09.2017.
 */

public class OverviewFragment extends Fragment implements OverviewContract.View {

    private FragmentOverviewBinding bind;
    private OverviewAdapter adapter;
    private Context context;
    private NavigationActivity activity;
    private OverviewPresenter presenter;

    public static OverviewFragment newInstance(int overviewType) {
        OverviewFragment fragment = new OverviewFragment();
        Bundle args = new Bundle();
        args.putSerializable(Constants.TYPE, overviewType);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        bind = FragmentOverviewBinding.inflate(inflater);
        context = bind.getRoot().getContext();
        presenter = new OverviewPresenter(this, getArguments());
        return bind.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter.init();
    }

    @Override
    public void init(int type) {
        activity = (NavigationActivity) getActivity();
        adapter = new OverviewAdapter(getChildFragmentManager(), context, type);
        setupViewPager();
        bind.tabs.setupWithViewPager(bind.viewpager);
    }

    @Override
    public void setNavigationSelected(int menuId) {
        activity.setMenuItemChecked(menuId);
    }

    @Override
    public void setTitle(boolean isMovie) {
        getActivity().setTitle(isMovie ? getString(R.string.movie_title) : getString(R.string.series_title));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void setupViewPager() {
        bind.viewpager.disableSwipe(false);
        bind.viewpager.setOffscreenPageLimit(3);
        bind.viewpager.setAdapter(adapter);
    }
}
