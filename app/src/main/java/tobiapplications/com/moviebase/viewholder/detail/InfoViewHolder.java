package tobiapplications.com.moviebase.viewholder.detail;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import tobiapplications.com.moviebase.R;
import tobiapplications.com.moviebase.model.detail.views.InfoView;
import tobiapplications.com.moviebase.utils.NetworkUtils;

/**
 * Created by Tobias on 15.06.2017.
 */

public class InfoViewHolder extends RecyclerView.ViewHolder {

    private Context context;
    private ImageView infoImage;
    private RatingBar infoRatingBar;
    private TextView infoRatingAverage;
    private TextView infoRatingCount;

    public InfoViewHolder(View itemView, Context context) {
        super(itemView);
        this.context = context;
        infoImage = (ImageView) itemView.findViewById(R.id.info_image);
        infoRatingBar = (RatingBar) itemView.findViewById(R.id.info_rating_bar);
        infoRatingAverage = (TextView) itemView.findViewById(R.id.info_rating_average);
        infoRatingCount = (TextView) itemView.findViewById(R.id.info_rating_count);
    }

    public void setInformation(InfoView view) {
        Picasso.with(context).load(NetworkUtils.getFullImageUrl(view.getImagePath())).into(infoImage);
        infoRatingAverage.setText(String.valueOf(view.getVoteAverage()));
        infoRatingCount.setText(String.valueOf(view.getVoteCount()));
    }
}
