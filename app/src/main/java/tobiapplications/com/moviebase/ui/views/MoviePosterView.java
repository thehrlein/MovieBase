package tobiapplications.com.moviebase.ui.views;

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
import tobiapplications.com.moviebase.model.general_items.MoviePosterItem;
import tobiapplications.com.moviebase.utils.NetworkUtils;

/**
 * Created by Tobias on 18.06.2017.
 */

public class MoviePosterView extends LinearLayout implements PopupMenu.OnMenuItemClickListener {

    private LinearLayout rootView;
    private ImageView movieImage;
    private TextView movieTitle;
    private ImageView movieDots;
    private Context context;
    private PopupMenu popupMenu;

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
        rootView = (LinearLayout) inflate(context, R.layout.item_movie_poster, this);
        movieImage = (ImageView) rootView.findViewById(R.id.movie_image);
        movieTitle = (TextView) rootView.findViewById(R.id.movie_title);
        movieDots = (ImageView) rootView.findViewById(R.id.movie_card_dots);
    }

    public void setMovieInformation(MoviePosterItem movieInformation) {
        setMovieInformation(movieInformation, -1, -1);
    }

    public void setMovieInformation(MoviePosterItem movieInformation, int height, int width) {
        if (movieInformation != null) {
            if (height > 0) {
                getLayoutParams().height = LayoutParams.WRAP_CONTENT;
                getLayoutParams().width = width;
                movieImage.getLayoutParams().height = height;
                movieImage.getLayoutParams().width = width;
            }

            if (!TextUtils.isEmpty(movieInformation.getImagePath())) {
                Picasso.with(context).load(NetworkUtils.getFullImageUrlLow(movieInformation.getImagePath())).into(movieImage);
                movieImage.setScaleType(ImageView.ScaleType.FIT_XY);
            } else {
                movieImage.setImageResource(R.drawable.no_picture);
            }
            movieTitle.setText(movieInformation.getTitle());
        }
        movieDots.setOnClickListener((View v) -> showPopMenu(v, context));
    }

    public void disablePopupDots() {
        movieDots.setVisibility(View.GONE);
    }

    private void showPopMenu(View view, Context context) {
        popupMenu = new PopupMenu(context, view);
        MenuInflater inflater = popupMenu.getMenuInflater();
        inflater.inflate(R.menu.popup_menu_movie_card, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(this);
        popupMenu.show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        if (item.getItemId() == R.id.action_add_favorite) {
            Toast.makeText(context, "not implemented yet", Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }
}