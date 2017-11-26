package tobiapplications.com.moviebase;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import tobiapplications.com.moviebase.utils.DateUtilsTest;
import tobiapplications.com.moviebase.utils.GeneralUtilTest;
import tobiapplications.com.moviebase.utils.StringUtilsTest;

/**
 * Created by Tobias Hehrlein on 15.11.2017.
 */

@RunWith(Suite.class)
@Suite.SuiteClasses({
        DateUtilsTest.class,
        GeneralUtilTest.class,
        StringUtilsTest.class
})
public class AllTests {
}
