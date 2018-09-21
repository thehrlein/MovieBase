package tobiapplications.com.moviebase.ui.viewholder.overview;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;
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