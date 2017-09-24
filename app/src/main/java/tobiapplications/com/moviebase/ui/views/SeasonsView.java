package tobiapplications.com.moviebase.ui.views;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.squareup.picasso.Picasso;

import tobiapplications.com.moviebase.R;
import tobiapplications.com.moviebase.databinding.ViewSeasonBinding;
import tobiapplications.com.moviebase.listener.OnImageClickListener;
import tobiapplications.com.moviebase.model.detail.Season;
import tobiapplications.com.moviebase.utils.DateUtils;
import tobiapplications.com.moviebase.utils.NetworkUtils;

/**
 * Created by Tobias on 20.09.2017.
 */

public class SeasonsView extends LinearLayout {

    private ViewSeasonBinding bind;
    private Context context;
    private OnImageClickListener listener;
    private int seasonPosition;

    public SeasonsView(Context context) {
        super(context);
        init(context);
    }

    public SeasonsView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public SeasonsView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        this.context = context;
        LayoutInflater inflater = LayoutInflater.from(context);
        bind = ViewSeasonBinding.inflate(inflater, this, true);
    }

    public void setSeason(Season season) {
        if (season.getPosterPath() != null) {
            Picasso.with(context).load(NetworkUtils.getFullImageUrlLow(season.getPosterPath())).into(bind.image);
            bind.image.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onImageClick(seasonPosition);
                }
            });
        }
        if (season.getAirDate() != null) {
            bind.airDate.setText(context.getString(R.string.seasons_air_date, DateUtils.getDMYFromYMD(season.getAirDate())));
        }

        if (season.getSeasonNumber() != null) {
            bind.seasonNumber.setText(context.getString(R.string.season_number, season.getSeasonNumber()));
        }

        if (season.getEpsisodeCount() != null) {
            bind.episodes.setText(context.getString(R.string.episodes_count, season.getEpsisodeCount()));
        }

    }

    public void setOnImageClickListener(OnImageClickListener onImageClickListener, int seasonPosition) {
        this.listener = onImageClickListener;
        this.seasonPosition = seasonPosition;
    }
}
