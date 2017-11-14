package tobiapplications.com.moviebase.utils;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import static tobiapplications.com.moviebase.utils.StringUtils.*;

/**
 * Created by Tobias Hehrlein on 14.11.2017.
 */

public class StringUtilsTest {

    @Test
    public void nullOrEmpty_null_returns_true() {
        assertThat(nullOrEmpty(null), is(true));
    }

    @Test
    public void nullOrEmpty_emptyString_returns_true() {
        assertThat(nullOrEmpty(""), is(true));
    }

    @Test
    public void nullOrEmpty_a_returns_false() {
        assertThat(nullOrEmpty("a"), is(false));
    }

    @Test
    public void nullOrEmpty_abcdefghijklmnopqrstuvwxyz_returns_false() {
        assertThat(nullOrEmpty("abcdefghijklmnopqrstuvwxyz"), is(false));
    }

}


