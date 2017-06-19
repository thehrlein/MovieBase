package tobiapplications.com.moviebase.ui.viewholder.overview;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import tobiapplications.com.moviebase.R;

/**
 * Created by Tobias on 15.06.2017.
 */

public class LoadingHolder extends RecyclerView.ViewHolder {
    private ProgressBar mProgressLoading;

    public LoadingHolder(View itemView) {
        super(itemView);
        mProgressLoading = (ProgressBar) itemView.findViewById(R.id.movie_loading_progress_indicator);
    }
}