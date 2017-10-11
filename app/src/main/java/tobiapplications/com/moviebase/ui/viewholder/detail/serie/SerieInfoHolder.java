package tobiapplications.com.moviebase.ui.viewholder.detail.serie;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import tobiapplications.com.moviebase.R;
import tobiapplications.com.moviebase.databinding.DetailSerieInfoHolderBinding;
import tobiapplications.com.moviebase.model.detail.items.serie.SerieInfoItem;
import tobiapplications.com.moviebase.utils.Constants;
import tobiapplications.com.moviebase.utils.DateUtils;

/**
 * Created by Tobias on 15.06.2017.
 */

public class SerieInfoHolder extends RecyclerView.ViewHolder {

    private DetailSerieInfoHolderBinding bind;
    private Context context;
    private SerieInfoItem serie;

    public SerieInfoHolder(View itemView, Context context) {
        super(itemView);
        this.context = context;

        bind = DetailSerieInfoHolderBinding.bind(itemView);
    }

    public void setInformation(SerieInfoItem view) {
        this.serie = view;

        bind.ratingAverage.setText(String.valueOf(view.getVoteAverage() + context.getString(R.string.info_max_rating)));
        bind.ratingCount.setText(String.valueOf(view.getVoteCount()));
        bind.firstAirDate.setText(DateUtils.getDMYFromYMD(view.getFirstAirDate()));
        bind.adult.setText(getAdultString(view.isAdult()));
        bind.lastAirDate.setText(DateUtils.getDMYFromYMD(view.getLastAirDate()));
    }

    private String getAdultString(boolean adult) {
        return adult ? context.getString(R.string.yes) : context.getString(R.string.no);
    }
}

