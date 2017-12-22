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
import tobiapplications.com.moviebase.databinding.ItemMoviePosterBinding;
import tobiapplications.com.moviebase.model.general_items.MoviePosterItem;
import tobiapplications.com.moviebase.utils.GeneralUtils;
import tobiapplications.com.moviebase.utils.NetworkUtils;

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
    }

    public void setMovieInformation(MoviePosterItem movieInformation) {
        setMovieInformation(movieInformation, -1, -1);
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
                Picasso.with(context).load(NetworkUtils.getFullImageUrlLow(movieInformation.getImagePath())).into(bind.movieImage);
                bind.movieImage.setScaleType(ImageView.ScaleType.FIT_XY);
            } else {
                bind.movieImage.setImageResource(R.drawable.no_picture);
            }
            bind.movieTitle.setText(movieInformation.getTitle());
        }
    }
}
