package tobiapplications.com.moviebase.ui.views;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.gestures.GestureDetector;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import tobiapplications.com.moviebase.R;
import tobiapplications.com.moviebase.model.detail.items.TrailerItem;
import tobiapplications.com.moviebase.utils.NetworkUtils;

/**
 * Created by Tobias on 20.06.2017.
 */

public class TrailerView extends LinearLayout implements View.OnClickListener{

    private LinearLayout rootView;
    private LinearLayout trailerLayout;
    private TextView title;
    private ImageView imageView;
    private Context context;
    private DividerView divider;
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
        rootView = (LinearLayout) inflate(context, R.layout.view_trailer_item, this);
        trailerLayout = (LinearLayout) rootView.findViewById(R.id.trailer_view_layout);
        imageView = (ImageView) rootView.findViewById(R.id.trailer_image);
        title = (TextView) rootView.findViewById(R.id.trailer_title);
        imageView.setOnClickListener(this);
    }

    public void setTrailerInformation(TrailerItem trailerItem) {
        this.title.setText(trailerItem.getTitle());
        this.trailerKey = trailerItem.getKey();

        Picasso.with(context).load(trailerItem.getThumbnails().getDefaultThumb().getUrl()).into(imageView);

        divider = new DividerView(context);
        trailerLayout.addView(divider);
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
