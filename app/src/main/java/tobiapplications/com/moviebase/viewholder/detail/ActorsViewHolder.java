package tobiapplications.com.moviebase.viewholder.detail;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import tobiapplications.com.moviebase.R;
import tobiapplications.com.moviebase.model.detail.Actor;
import tobiapplications.com.moviebase.utils.NetworkUtils;

/**
 * Created by Tobias on 16.06.2017.
 */

public class ActorsViewHolder extends RecyclerView.ViewHolder {
    private Context context;
    private GridLayout gridLayout;

    public ActorsViewHolder(View itemView, Context context) {
        super(itemView);
        this.context = context;

        gridLayout = (GridLayout) itemView.findViewById(R.id.actors_grid_layout);
    }

    public void setActorInformation(ArrayList<Actor> actors) {
        if (actors != null && !actors.isEmpty()) {
            for (Actor actor : actors) {
                ImageView imageView = new ImageView(context);
                gridLayout.addView(imageView);
                imageView.getLayoutParams().width = 300;
                imageView.getLayoutParams().height = 450;
                if (actor.getProfilePath() != null) {
                    Picasso.with(context).load(NetworkUtils.getFullImageUrlLow(actor.getProfilePath())).into(imageView);
                    imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                } else {
                    imageView.setImageResource(R.drawable.no_image_available);
                }

            }
        }
    }
}
