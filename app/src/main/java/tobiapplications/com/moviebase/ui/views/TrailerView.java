package tobiapplications.com.moviebase.ui.views;

import android.content.Context;
import android.content.Intent;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;

import tobiapplications.com.moviebase.databinding.ViewTrailerItemBinding;
import tobiapplications.com.moviebase.model.detail.YtTrailerStatistic;
import tobiapplications.com.moviebase.model.detail.items.movie.TrailerItem;
import tobiapplications.com.moviebase.utils.GeneralUtils;
import tobiapplications.com.moviebase.utils.NetworkUtils;
import tobiapplications.com.moviebase.utils.StringUtils;

/**
 * Created by Tobias on 20.06.2017.
 */

public class TrailerView extends LinearLayout {

    private ViewTrailerItemBinding bind;
    private DividerView divider;
    private Context context;
    private String trailerKey;

    public TrailerView(Context context) {
        super(context);
        init(context);
    }

    public TrailerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public TrailerView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        this.context = context;
        LayoutInflater inflater = LayoutInflater.from(context);
        bind = ViewTrailerItemBinding.inflate(inflater, this, true);
        bind.trailerImage.setOnClickListener(v -> openYoutubeTrailer());
    }

    private void openYoutubeTrailer() {
        Intent openYoutubeTrailerIntent = new Intent(Intent.ACTION_VIEW);
        openYoutubeTrailerIntent.setData(NetworkUtils.buildYoutubeIntent(trailerKey));
        context.startActivity(openYoutubeTrailerIntent);
    }

    public void setTrailerInformation(TrailerItem trailerItem) {
        this.trailerKey = trailerItem.getKey();
        bind.trailerTitle.setText(trailerItem.getTitle());

        setUpStatistics(trailerItem.getStatistics());
        if (trailerItem.getThumbnails() != null && trailerItem.getThumbnails().getDefaultThumb() != null) {
            Glide.with(context).load(trailerItem.getThumbnails().getDefaultThumb().getUrl()).into(bind.trailerImage);
        }

        divider = new DividerView(context);
        bind.trailerViewLayout.addView(divider);
    }

    private void setUpStatistics(YtTrailerStatistic statistics) {
        if (statistics == null) {
            return;
        }

        String thumbsUpCount = GeneralUtils.formatThousands(statistics.getLikeCount());
        String thumbsDownCount = GeneralUtils.formatThousands(statistics.getDislikeCount());
        String watchCount = GeneralUtils.formatThousands(statistics.getViewCount());

        if (StringUtils.nullOrEmpty(thumbsUpCount) || StringUtils.nullOrEmpty(thumbsDownCount)) {
            bind.thumbsLayout.setVisibility(View.GONE);
        } else {
            bind.thumbsLayout.setVisibility(View.VISIBLE);
            bind.thumbsUpCount.setText(GeneralUtils.formatThousands(statistics.getLikeCount()));
            bind.thumbsDownCount.setText(GeneralUtils.formatThousands(statistics.getDislikeCount()));
        }

        if (StringUtils.nullOrEmpty(watchCount)) {
            bind.viewcountLayout.setVisibility(View.GONE);
        } else {
            bind.viewcountLayout.setVisibility(View.VISIBLE);
            bind.watchCount.setText(GeneralUtils.formatThousands(statistics.getViewCount()));
        }
    }

    public void hideDivider() {
        divider.setVisibility(View.INVISIBLE);
    }

    public void showDivider() {
        divider.setVisibility(View.VISIBLE);
    }
}
