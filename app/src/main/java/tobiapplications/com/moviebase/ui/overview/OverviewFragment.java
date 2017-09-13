package tobiapplications.com.moviebase.ui.overview;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import tobiapplications.com.moviebase.R;
import tobiapplications.com.moviebase.adapter.delegates.OverviewAdapter;
import tobiapplications.com.moviebase.databinding.FragmentOverviewBinding;
import tobiapplications.com.moviebase.utils.Constants;

/**
 * Created by Tobias on 10.09.2017.
 */

public class OverviewFragment extends Fragment implements OverviewFragmentContract.View {

    private FragmentOverviewBinding bind;
    private OverviewAdapter adapter;
    private Context context;
    private Constants.OverviewType overviewType;

    public static OverviewFragment newInstance(Constants.OverviewType overviewType) {
        OverviewFragment fragment = new OverviewFragment();
        Bundle args = new Bundle();
        args.putSerializable(Constants.OVERVIEW_TYPE, overviewType);
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
        overviewType = getOverViewType(getArguments());
        adapter = new OverviewAdapter(getChildFragmentManager(), context, this, overviewType);
        return bind.getRoot();
    }

    private Constants.OverviewType getOverViewType(Bundle arguments) {
        if (arguments == null) {
            return null;
        }

        if (arguments.containsKey(Constants.OVERVIEW_TYPE)) {
            return (Constants.OverviewType) arguments.getSerializable(Constants.OVERVIEW_TYPE);
        }

        return null;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }

    private void init() {
        setupViewPager();
        bind.tabs.setupWithViewPager(bind.viewpager);
        getActivity().setTitle(getString(R.string.movie_title));
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
    public void showAllViews() {
        bind.viewpager.disableSwipe(false);
        bind.appBarLayout.setVisibility(View.VISIBLE);
        bind.tabs.setVisibility(View.VISIBLE);
        setStatusBarColor();
    }

    @Override
    public void setStatusBarColor() {
        android.app.ActionBar actionBar = getActivity().getActionBar();
        if (actionBar != null && Build.VERSION.SDK_INT >= 21) {
            actionBar.setElevation(0);
        }
    }

    @Override
    public void setupViewPager() {
        bind.viewpager.disableSwipe(false);
        bind.viewpager.setOffscreenPageLimit(3);
        bind.viewpager.setAdapter(adapter);
    }

    private void hideViewsOnLoading() {
        bind.viewpager.disableSwipe(true);
        bind.tabs.setVisibility(View.INVISIBLE);
        bind.appBarLayout.setVisibility(View.INVISIBLE);


        if (Build.VERSION.SDK_INT >= 21) {
            getActivity().getWindow().setStatusBarColor(ContextCompat.getColor(context, android.R.color.transparent));
        }
    }
}