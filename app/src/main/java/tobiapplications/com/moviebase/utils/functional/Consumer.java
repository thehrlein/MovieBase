package tobiapplications.com.moviebase.utils.functional;

/**
 * Created by Tobias Hehrlein on 23.10.2017.
 */

@FunctionalInterface
public interface Consumer<T> {
    void accept(T arg);
}
