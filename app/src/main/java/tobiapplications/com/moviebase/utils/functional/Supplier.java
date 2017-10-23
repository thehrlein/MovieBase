package tobiapplications.com.moviebase.utils.functional;

/**
 * Created by Tobias Hehrlein on 23.10.2017.
 */

@FunctionalInterface
public interface Supplier<T> {
    T get();
}
