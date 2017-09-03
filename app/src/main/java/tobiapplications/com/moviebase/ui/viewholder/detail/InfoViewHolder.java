package tobiapplications.com.moviebase.ui.viewholder.detail;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.stfalcon.frescoimageviewer.ImageViewer;

import tobiapplications.com.moviebase.R;
import tobiapplications.com.moviebase.databinding.DetailInfoHolderBinding;
import tobiapplications.com.moviebase.databinding.ItemActorsPosterBinding;
import tobiapplications.com.moviebase.model.detail.items.InfoItem;
import tobiapplications.com.moviebase.utils.Constants;
import tobiapplications.com.moviebase.utils.DateUtils;
import tobiapplications.com.moviebase.utils.NetworkUtils;

/**
 * Created by Tobias on 15.06.2017.
 */

public class InfoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private DetailInfoHolderBinding bind;
    private Context context;
    private InfoItem infoItem;

    public InfoViewHolder(View itemView, Context context) {
        super(itemView);
        this.context = context;

        bind = DetailInfoHolderBinding.bind(itemView);
        bind.image.setOnClickListener(this);
    }

    public void setInformation(InfoItem view) {
        this.infoItem = view;
        if (view.getImagePath() != null) {
            Picasso.with(context).load(NetworkUtils.getFullImageUrlLow(view.getImagePath())).into(bind.image);
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
        if (infoItem.getImagePath() != null) {
            new ImageViewer.Builder(context, new String[]{NetworkUtils.getFullImageUrlHigh(infoItem.getImagePath())})
                    .setStartPosition(0)
                    .show();
        }
    }
}

