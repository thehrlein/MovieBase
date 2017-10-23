package tobiapplications.com.moviebase.utils.functional;

/**
 * Created by Tobias Hehrlein on 23.10.2017.
 */

public class NullCoalescence$$Lambda$1 implements Supplier {

    private final Callable[] callables;
    private final Supplier supplier;

    public NullCoalescence$$Lambda$1(Callable[] callables, Supplier supplier) {
        this.callables = callables;
        this.supplier = supplier;
    }

    public Object get() {
//        return NullCoalescence.lambda$chain$0(this.callables, this.supplier);
        return new Object();
    }

    public static Supplier lambdaFactory$(Callable[] callables, Supplier supplier) {
        return new NullCoalescence$$Lambda$1(callables, supplier);
    }
}
