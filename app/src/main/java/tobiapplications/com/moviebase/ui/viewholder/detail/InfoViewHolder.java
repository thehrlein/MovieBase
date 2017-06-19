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
import tobiapplications.com.moviebase.model.detail.items.InfoItem;
import tobiapplications.com.moviebase.utils.Constants;
import tobiapplications.com.moviebase.utils.DateUtils;
import tobiapplications.com.moviebase.utils.NetworkUtils;

/**
 * Created by Tobias on 15.06.2017.
 */

public class InfoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private Context context;
    private InfoItem infoItem;
    private ImageView infoImage;
    private RatingBar infoRatingBar;
    private TextView infoRatingAverage;
    private TextView infoRatingCount;
    private TextView infoRelease;
    private TextView infoAdult;
    private TextView infoRuntime;
    private TextView infoStatus;

    public InfoViewHolder(View itemView, Context context) {
        super(itemView);
        this.context = context;
        infoImage = (ImageView) itemView.findViewById(R.id.info_image);
        infoRatingBar = (RatingBar) itemView.findViewById(R.id.info_rating_bar);
        infoRatingAverage = (TextView) itemView.findViewById(R.id.info_rating_average);
        infoRatingCount = (TextView) itemView.findViewById(R.id.info_rating_count);
        infoRelease = (TextView) itemView.findViewById(R.id.info_release);
        infoAdult = (TextView) itemView.findViewById(R.id.info_adult);
        infoRuntime = (TextView) itemView.findViewById(R.id.info_runtime);
        infoStatus = (TextView) itemView.findViewById(R.id.info_status);

        infoImage.setOnClickListener(this);
    }

    public void setInformation(InfoItem view) {
        this.infoItem = view;
        if (view.getImagePath() != null) {
            Picasso.with(context).load(NetworkUtils.getFullImageUrlLow(view.getImagePath())).into(infoImage);
        } else {
            infoImage.setImageResource(R.drawable.no_picture);
        }
        infoRatingAverage.setText(String.valueOf(view.getVoteAverage() + context.getString(R.string.info_max_rating)));
        infoRatingCount.setText(String.valueOf(view.getVoteCount()));
        infoRelease.setText(DateUtils.getDMYFromYMD(view.getReleaseDate()));
        infoAdult.setText(getAdultString(view.isAdult()));
        infoRuntime.setText(DateUtils.getHourMinuteStringFromInt(view.getRuntime()));
        if (view.getStatus().equalsIgnoreCase(Constants.RELEASED))
            infoStatus.setVisibility(View.VISIBLE);
            infoStatus.setText(view.getStatus());
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

