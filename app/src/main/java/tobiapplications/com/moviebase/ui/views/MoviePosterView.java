package tobiapplications.com.moviebase.ui.views;

import android.content.Context;
import androidx.annotation.Nullable;

import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import androidx.swiperefreshlayout.widget.CircularProgressDrawable;
import tobiapplications.com.moviebase.R;
import tobiapplications.com.moviebase.databinding.ItemMoviePosterBinding;
import tobiapplications.com.moviebase.model.general_items.MoviePosterItem;
import tobiapplications.com.moviebase.utils.NetworkUtils;
import tobiapplications.com.moviebase.utils.Store;

/**
 * Created by Tobias on 18.06.2017.
 */

public class MoviePosterView extends LinearLayout {

    private ItemMoviePosterBinding bind;
    private Context context;

    public MoviePosterView(Context context) {
        super(context);
        init(context);
    }

    public MoviePosterView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public MoviePosterView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        this.context = context;
        LayoutInflater inflater = LayoutInflater.from(context);
        bind = ItemMoviePosterBinding.inflate(inflater, this, true);
        resetTile(context);
    }

    private void resetTile(Context context) {
        Glide.with(context).clear(bind.movieImage);
        bind.movieImage.setVisibility(View.INVISIBLE);
    }

    public void setMovieInformation(MoviePosterItem movieInformation) {
        int width = Store.getInstance().getScreenWidth() / 2 - 10;
        int height = (int) (width * 1.5);
        resetTile(context);
        setMovieInformation(movieInformation, height, width);
    }

    public void setMovieInformation(MoviePosterItem movieInformation, int height, int width){
        if (movieInformation != null) {
            if (height > 0) {
                getLayoutParams().height = LayoutParams.WRAP_CONTENT;
                getLayoutParams().width = width;
                bind.movieImage.getLayoutParams().height = height;
                bind.movieImage.getLayoutParams().width = width;
            }

            if (!TextUtils.isEmpty(movieInformation.getImagePath())) {
                Glide.with(context)
                        .load(NetworkUtils.getFullImageUrlLow(movieInformation.getImagePath()))
                        .listener(new RequestListener<Drawable>() {
                            @Override
                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                bind.progressBar.setVisibility(View.GONE);
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                bind.progressBar.setVisibility(View.GONE);
                                bind.movieImage.setVisibility(View.VISIBLE);
                                return false;
                            }
                        })
                        .into(bind.movieImage);
                bind.movieImage.setScaleType(ImageView.ScaleType.FIT_XY);
            } else {
                bind.movieImage.setImageResource(R.drawable.no_picture);
            }
            bind.movieTitle.setText(movieInformation.getTitle());
        }
    }
}
