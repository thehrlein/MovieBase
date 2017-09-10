package tobiapplications.com.moviebase.ui.overview;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import tobiapplications.com.moviebase.R;
import tobiapplications.com.moviebase.adapter.ViewPagerAdapter;
import tobiapplications.com.moviebase.databinding.ActivityOverviewBinding;

/**
 * Created by Tobias on 10.09.2017.
 */

public class OverviewFragment extends Fragment implements OverviewActivityContract.View {

    private ActivityOverviewBinding bind;
    private Context context;

    public static OverviewFragment newInstance() {
        OverviewFragment fragment = new OverviewFragment();
        Bundle args = new Bundle();
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
        bind = ActivityOverviewBinding.inflate(inflater);
        context = bind.getRoot().getContext();

        init();


        return bind.getRoot();
    }

    private void init() {
        disableActionBarTabLayoutDivider();
        setupViewPager();
        bind.tabs.setupWithViewPager(bind.viewpager);
        getActivity().setTitle(getString(R.string.movie_title));
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
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
    public void disableActionBarTabLayoutDivider() {

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
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.addFragment(PopularFragment.newInstance(this), getString(R.string.title_popular));
        adapter.addFragment(TopRatedFragment.newInstance(), getString(R.string.title_top_rated));
        adapter.addFragment(OwnFavoriteFragment.newInstance(), getString(R.string.title_favorite));
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
