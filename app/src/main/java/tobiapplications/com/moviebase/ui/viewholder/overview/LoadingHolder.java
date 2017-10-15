package tobiapplications.com.moviebase.ui.viewholder.overview;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import tobiapplications.com.moviebase.R;
import tobiapplications.com.moviebase.databinding.ItemMovieLoadingBinding;

/**
 * Created by Tobias on 15.06.2017.
 */

public class LoadingHolder extends RecyclerView.ViewHolder {

    private ItemMovieLoadingBinding bind;

    public LoadingHolder(View itemView) {
        super(itemView);
        bind = ItemMovieLoadingBinding.bind(itemView);
    }
}