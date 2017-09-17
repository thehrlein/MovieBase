package tobiapplications.com.moviebase.ui.views;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.squareup.picasso.Picasso;

import tobiapplications.com.moviebase.databinding.ViewTrailerItemBinding;
import tobiapplications.com.moviebase.model.detail.items.movie.TrailerItem;
import tobiapplications.com.moviebase.utils.NetworkUtils;

/**
 * Created by Tobias on 20.06.2017.
 */

public class TrailerView extends LinearLayout implements View.OnClickListener{

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
        bind.trailerImage.setOnClickListener(this);
    }

    public void setTrailerInformation(TrailerItem trailerItem) {
        bind.trailerTitle.setText(trailerItem.getTitle());
        this.trailerKey = trailerItem.getKey();

        Picasso.with(context).load(trailerItem.getThumbnails().getDefaultThumb().getUrl()).into(bind.trailerImage);

        divider = new DividerView(context);
        bind.trailerViewLayout.addView(divider);
    }

    public void hideDivider() {
        divider.setVisibility(View.INVISIBLE);
    }

    public void showDivider() {
        divider.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(View v) {
        Intent openYoutubeTrailerIntent = new Intent(Intent.ACTION_VIEW);
        openYoutubeTrailerIntent.setData(NetworkUtils.buildYoutubeIntent(trailerKey));
        context.startActivity(openYoutubeTrailerIntent);
    }
}
