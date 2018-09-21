package tobiapplications.com.moviebase.adapter;

import android.content.Context;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import android.util.SparseArray;
import android.view.ViewGroup;

import java.util.ArrayList;

import tobiapplications.com.moviebase.R;
import tobiapplications.com.moviebase.ui.overview.OwnFavoriteFragment;
import tobiapplications.com.moviebase.ui.overview.PopularFragment;
import tobiapplications.com.moviebase.ui.overview.TopRatedFragment;

/**
 * Created by Tobias on 13.09.2017.
 */

public class OverviewAdapter extends FragmentStatePagerAdapter {

    private final static int COUNT = 3;
    private final ArrayList<String> tabTitles = new ArrayList<>();
    private int overviewType;

    public OverviewAdapter(FragmentManager fm, Context context, int overviewType) {
        super(fm);
        this.overviewType = overviewType;
        configureTitles(context);
    }

    private void configureTitles(Context context) {
        tabTitles.add(context.getString(R.string.title_popular));
        tabTitles.add(context.getString(R.string.title_top_rated));
        tabTitles.add(context.getString(R.string.title_favorite));
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment;
        switch (position) {
            case 0:
                fragment = PopularFragment.newInstance(overviewType);
                break;
            case 1:
                fragment = TopRatedFragment.newInstance(overviewType);
                break;
            case 2:
                fragment = OwnFavoriteFragment.newInstance(overviewType);
                break;
            default:
                fragment = PopularFragment.newInstance(overviewType);
                break;
        }

        return fragment;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Object object = super.instantiateItem(container, position);
        SparseArray<Fragment> tabs = new SparseArray<>();
        if (object instanceof PopularFragment) {
            tabs.put(0, (Fragment) object);
        } else if (object instanceof TopRatedFragment) {
            tabs.put(1, (Fragment) object);
        } else if (object instanceof OwnFavoriteFragment) {
            tabs.put(2, (Fragment) object);
        }

        return object;
    }

    @Override
    public int getCount() {
        return COUNT;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles.get(position);
    }
}
