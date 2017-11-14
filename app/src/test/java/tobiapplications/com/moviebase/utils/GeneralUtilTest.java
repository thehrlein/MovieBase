package tobiapplications.com.moviebase.utils;

import android.app.Dialog;
import android.content.Context;

import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mock;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import static tobiapplications.com.moviebase.utils.GeneralUtils.*;

/**
 * Created by Tobias Hehrlein on 14.11.2017.
 */

public class GeneralUtilTest {

    @Test
    public void getHowMuchColumnsForOverviewMovies_tabletLandscape_returns_6() {
        boolean isTablet = true;
        boolean isLandscape = true;
        int columns = getHowMuchColumnsForOverviewMovies(isTablet, isLandscape);
        assertThat(columns, is(6));
    }

    @Test
    public void getHowMuchColumnsForOverviewMovies_tabletPortrait_returns_4() {
        boolean isTablet = true;
        boolean isLandscape = false;
        int columns = getHowMuchColumnsForOverviewMovies(isTablet, isLandscape);
        assertThat(columns, is(4));
    }

    @Test
    public void getHowMuchColumnsForOverviewMovies_phoneLandscape_returns_4() {
        boolean isTablet = false;
        boolean isLandscape = true;
        int columns = getHowMuchColumnsForOverviewMovies(isTablet, isLandscape);
        assertThat(columns, is(4));
    }

    @Test
    public void getHowMuchColumnsForOverviewMovies_phonePortrait_returns_2() {
        boolean isTablet = false;
        boolean isLandscape = false;
        int columns = getHowMuchColumnsForOverviewMovies(isTablet, isLandscape);
        assertThat(columns, is(2));
    }

    @Test
    public void getGoBackCounter_4_allTrue_return_0() {
        boolean[] list = new boolean[4];
        list[0] = true;
        list[1] = true;
        list[2] = true;
        list[3] = true;

        assertThat(getGoBackCounter(4, list), is(0));
    }

    @Test
    public void getGoBackCounter_4_first3True_return_1() {
        boolean[] list = new boolean[4];
        list[0] = true;
        list[1] = true;
        list[2] = true;
        list[3] = false;

        assertThat(getGoBackCounter(4, list), is(1));
    }

    @Test
    public void getGoBackCounter_4_firstAndLastTrue_return_2() {
        boolean[] list = new boolean[4];
        list[0] = true;
        list[1] = false;
        list[2] = false;
        list[3] = true;

        assertThat(getGoBackCounter(4, list), is(2));
    }

    @Test
    public void getGoBackCounter_4_allFalse_return_4() {
        boolean[] list = new boolean[4];
        list[0] = false;
        list[1] = false;
        list[2] = false;
        list[3] = false;

        assertThat(getGoBackCounter(4, list), is(4));
    }

    @Test
    public void getGoBackCounter_4_lastTrue_return_3() {
        boolean[] list = new boolean[4];
        list[0] = false;
        list[1] = false;
        list[2] = false;
        list[3] = true;

        assertThat(getGoBackCounter(4, list), is(3));
    }

    @Test
    public void formatThousands_1000_return_1Dot000() {
        assertThat(formatThousands(1000), is("1.000"));
    }

    @Test
    public void formatThousands_24155Long_return_24Dot155() {
        assertThat(formatThousands(24155L), is("24.155"));
    }

    @Test
    public void formatThousands_12String_return_12() {
        assertThat(formatThousands("12"), is("12"));
    }

    @Test
    public void formatThousands_negative5412_return_negative5412() {
        assertThat(formatThousands(-5412), is("-5.412"));
    }

    @Test
    public void isMovie_0_returns_true() {
        assertThat(isMovie(0), is(true));
    }

    @Test
    public void isMovie_1_returns_false() {
        assertThat(isMovie(1), is(false));
    }

    @Test
    public void isMovie_101_returns_false() {
        assertThat(isMovie(101), is(false));
    }

    @Test
    public void isSerie_1_returns_true() {
        assertThat(isSerie(1), is(true));
    }

    @Test
    public void isSerie_0_returns_false() {
        assertThat(isSerie(0), is(false));
    }

    @Test
    public void isSerie_101_returns_false() {
        assertThat(isSerie(101), is(false));
    }

    @Test
    public void isPopular_10_returns_true() {
        assertThat(isPopular(10), is(true));
    }

    @Test
    public void isPopular_11_returns_false() {
        assertThat(isPopular(11), is(false));
    }

    @Test
    public void isPopular_101_returns_false() {
        assertThat(isPopular(101), is(false));
    }

    @Test
    public void isTopRated_11_returns_true() {
        assertThat(isTopRated(11), is(true));
    }

    @Test
    public void isTopRated_10_returns_false() {
        assertThat(isTopRated(10), is(false));
    }

    @Test
    public void isTopRated_101_returns_false() {
        assertThat(isTopRated(101), is(false));
    }
}
