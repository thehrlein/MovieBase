package tobiapplications.com.moviebase.ui.general_views;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.PopupMenu;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import tobiapplications.com.moviebase.R;
import tobiapplications.com.moviebase.model.detail.Actor;
import tobiapplications.com.moviebase.model.general_items.MoviePosterItem;
import tobiapplications.com.moviebase.utils.NetworkUtils;

/**
 * Created by Tobias on 18.06.2017.
 */

public class ActorsPosterView extends LinearLayout {

    private LinearLayout rootView;
    private ImageView actorImage;
    private TextView actorCharacter;
    private TextView actorName;
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
        rootView = (LinearLayout) inflate(context, R.layout.item_actors_poster, this);
        actorImage = (ImageView) rootView.findViewById(R.id.actors_image);
        actorCharacter = (TextView) rootView.findViewById(R.id.actor_character);
        actorName = (TextView) rootView.findViewById(R.id.actor_name);
    }

    public void setActorInformation(Actor actor) {
        if (actor != null) {

            if (!TextUtils.isEmpty(actor.getProfilePath())) {
                Picasso.with(context).load(NetworkUtils.getFullImageUrlLow(actor.getProfilePath())).into(actorImage);
                actorImage.setScaleType(ImageView.ScaleType.FIT_XY);
            } else {
                actorImage.setImageResource(R.drawable.no_picture);
            }
            actorImage.getLayoutParams().height = 450;
            actorImage.getLayoutParams().width = 300;
            getLayoutParams().height = LayoutParams.WRAP_CONTENT;
            getLayoutParams().width = 300;
            actorCharacter.setText(actor.getCharacter());
            actorName.setText(actor.getName());
        }
    }
}