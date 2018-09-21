package tobiapplications.com.moviebase.ui.overview;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import tobiapplications.com.moviebase.utils.Constants;

/**
 * Created by Tobias on 09.06.2017.
 */

public class PopularFragment extends BaseTabFragment implements OverviewTabContract.View {

    private Context context;
    private PopularPresenter presenter;

    public static Fragment newInstance(int overviewType) {
        PopularFragment popularFragment = new PopularFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(Constants.TYPE, overviewType);
        popularFragment.setArguments(bundle);
        return popularFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getContext();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        presenter = new PopularPresenter(this, context);
        presenter.attach(this);
        super.setPresenter(presenter);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter.getTypeAndLoadItems(getArguments());

        setGridViewAndAdapter();
        initCounter();
        initFabScrollUpButton();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (presenter != null) {
            presenter.detach();
        }
    }
}
