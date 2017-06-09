package tobiapplications.com.moviebase.ui.movie_overview;

/**
 * Created by Tobias on 09.06.2017.
 */

public class PopularMoviePresenter {

    private MovieOverview parent;

    public PopularMoviePresenter(MovieOverview parent) {
        this.parent = parent;

        parent.makeToast("YOLO SWAG");
    }
}
