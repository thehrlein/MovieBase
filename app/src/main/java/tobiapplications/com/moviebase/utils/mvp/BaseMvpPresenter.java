package tobiapplications.com.moviebase.utils.mvp;

/**
 * Created by Tobias Hehrlein on 23.10.2017.
 */

public interface BaseMvpPresenter<V extends BaseView> {

    void attach(V view);
    void detach();
    boolean isAttached();
}
