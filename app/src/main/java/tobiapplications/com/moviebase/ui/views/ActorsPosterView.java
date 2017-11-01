package tobiapplications.com.moviebase.ui.views;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.squareup.picasso.Picasso;

import tobiapplications.com.moviebase.R;
import tobiapplications.com.moviebase.databinding.ItemActorsPosterBinding;
import tobiapplications.com.moviebase.listener.OnImageClickListener;
import tobiapplications.com.moviebase.model.detail.Actor;
import tobiapplications.com.moviebase.utils.GeneralUtils;
import tobiapplications.com.moviebase.utils.NetworkUtils;

/**
 * Created by Tobias on 18.06.2017.
 */

public class ActorsPosterView extends LinearLayout {

    private ItemActorsPosterBinding bind;
    private Context context;

    public ActorsPosterView(Context context) {
        super(context);
        init(context);
    }

    public ActorsPosterView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ActorsPosterView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        this.context = context;
        LayoutInflater inflater = LayoutInflater.from(context);
        bind = ItemActorsPosterBinding.inflate(inflater, this, true);
    }

    public void setActorInformation(Actor actor, int position, OnImageClickListener listener) {
        if (actor != null) {

            if (!TextUtils.isEmpty(actor.getProfilePath())) {
                Picasso.with(context).load(NetworkUtils.getFullImageUrlLow(actor.getProfilePath())).into(bind.actorsImage);
                bind.actorsImage.setScaleType(ImageView.ScaleType.FIT_XY);
            } else {
                bind.actorsImage.setImageResource(R.drawable.no_picture);
            }

            int width = GeneralUtils.pxFromDp(context, 100);
            int height = GeneralUtils.pxFromDp(context, 150);
            bind.actorsImage.getLayoutParams().height = height;
            bind.actorsImage.getLayoutParams().width = width;
            bind.actorCharacter.setWidth(width);
            bind.actorCharacter.setText(actor.getCharacter());
            bind.actorName.setText(actor.getName());
            bind.actorName.setWidth(width);

            bind.actorsImage.setOnClickListener(v -> listener.onImageClick(position));
        }
    }
}