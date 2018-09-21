package tobiapplications.com.moviebase.ui.viewholder.detail.movie;

import android.content.Context;
import android.view.View;

import com.bumptech.glide.Glide;
import com.stfalcon.frescoimageviewer.ImageViewer;

import androidx.recyclerview.widget.RecyclerView;
import tobiapplications.com.moviebase.R;
import tobiapplications.com.moviebase.databinding.DetailMovieInfoHolderBinding;
import tobiapplications.com.moviebase.model.detail.items.movie.MovieInfoItem;
import tobiapplications.com.moviebase.utils.Constants;
import tobiapplications.com.moviebase.utils.DateUtils;
import tobiapplications.com.moviebase.utils.NetworkUtils;

/**
 * Created by Tobias on 15.06.2017.
 */

public class MovieInfoHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private DetailMovieInfoHolderBinding bind;
    private Context context;
    private MovieInfoItem movie;

    public MovieInfoHolder(View itemView, Context context) {
        super(itemView);
        this.context = context;

        bind = DetailMovieInfoHolderBinding.bind(itemView);
        bind.image.setOnClickListener(this);
    }

    public void setInformation(MovieInfoItem view) {
        this.movie = view;
        if (view.getImagePath() != null) {
            Glide.with(context).load(NetworkUtils.getFullImageUrlLow(view.getImagePath())).into(bind.image);
        } else {
            bind.image.setImageResource(R.drawable.no_picture);
        }
        bind.ratingAverage.setText(String.valueOf(view.getVoteAverage() + context.getString(R.string.info_max_rating)));
        bind.ratingCount.setText(String.valueOf(view.getVoteCount()));
        bind.release.setText(DateUtils.getDMYFromYMD(view.getReleaseDate()));
        bind.adult.setText(getAdultString(view.isAdult()));
        bind.runtime.setText(DateUtils.getHourMinuteStringFromInt(view.getRuntime()));
        if (view.getStatus().equalsIgnoreCase(Constants.RELEASED))
            bind.status.setVisibility(View.VISIBLE);
            bind.status.setText(view.getStatus());
    }

    private String getAdultString(boolean adult) {
        return adult ? context.getString(R.string.yes) : context.getString(R.string.no);
    }

    @Override
    public void onClick(View v) {
        if (movie.getImagePath() != null) {
            new ImageViewer.Builder(context, new String[]{NetworkUtils.getFullImageUrlHigh(movie.getImagePath())})
                    .setStartPosition(0)
                    .show();
        }
    }
}

