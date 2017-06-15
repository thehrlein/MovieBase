package tobiapplications.com.moviebase.viewholder.detail;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.stfalcon.frescoimageviewer.ImageViewer;

import tobiapplications.com.moviebase.R;
import tobiapplications.com.moviebase.model.detail.views.InfoView;
import tobiapplications.com.moviebase.utils.DateUtils;
import tobiapplications.com.moviebase.utils.NetworkUtils;

/**
 * Created by Tobias on 15.06.2017.
 */

public class InfoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private Context context;
    private InfoView infoView;
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

    public void setInformation(InfoView view) {
        this.infoView = view;
        Picasso.with(context).load(NetworkUtils.getFullImageUrl(view.getImagePath())).into(infoImage);
        infoRatingAverage.setText(String.valueOf(view.getVoteAverage() + context.getString(R.string.info_max_rating)));
        infoRatingCount.setText(String.valueOf(view.getVoteCount()));
        infoRelease.setText(DateUtils.getDMYFromYMD(view.getReleaseDate()));
        infoAdult.setText(String.valueOf(view.isAdult()));
        infoRuntime.setText(DateUtils.getHourMinuteStringFromInt(view.getRuntime()));
        infoStatus.setText(view.getStatus());
        if (view.getStatus().equalsIgnoreCase("released")) {
            infoStatus.setTextColor(ContextCompat.getColor(context, R.color.colorStatusOk));
        }
    }

    @Override
    public void onClick(View v) {
        new ImageViewer.Builder(context, new String[]{NetworkUtils.getFullImageUrl(infoView.getImagePath())})
                .setStartPosition(0)
                .show();
    }
}

