package tobiapplications.com.moviebase.utils;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Created by Tobias Hehrlein on 12.11.2017.
 */

public class DateUtilsTest {

    private final String EMPTY_STRING = "";

    /**
     * Testing getDMYFromYMD. Parses date from yyyy-mm-dd into dd.mm.yyyy.
     * If input is null, empty or can not be parsed, empty string will be returned.
     */
    @Test
    public void getDMYFromYMD_nullInput_returns_emptyString() {
        assertThat(DateUtils.getDMYFromYMD(null), is(EMPTY_STRING));
    }

    @Test
    public void getDMYFromYMD_emptyInput_returns_emptyString() {
        assertThat(DateUtils.getDMYFromYMD(null), is(EMPTY_STRING));
    }

    @Test
    public void getDMYFromYMD_20181007dashed_returns_07102017dotted() {
        assertThat(DateUtils.getDMYFromYMD("2017-10-07"), is("07.10.2017"));
    }

    @Test
    public void getDMYFromYMD_19960706dashed_returns_06071996dotted() {
        assertThat(DateUtils.getDMYFromYMD("1996-07-06"), is("06.07.1996"));
    }

    @Test
    public void getDMYFromYMD_19960706dotted_returns_emptyString() {
        assertThat(DateUtils.getDMYFromYMD("1996.07.06"), is(EMPTY_STRING));
    }

    @Test
    public void getDMYFromYMD_invalidString_returns_emptyString() {
        assertThat(DateUtils.getDMYFromYMD("invalidDate"), is(EMPTY_STRING));
    }

    @Test
    public void getDMYFromYMD_07101994dotted_returns_emptyString() {
        assertThat(DateUtils.getDMYFromYMD("07.10.1993"), is(EMPTY_STRING));
    }

    /**
     * Testing getHourMinuteStringFromInt. Returns formatted String with
     * hours + 'h' + minutes + "min".
     */
    @Test
    public void getHourMinuteStringFromInt_60_returns_1h_0min() {
        assertThat(DateUtils.getHourMinuteStringFromInt(60), is("1h 0min"));
    }

    @Test
    public void getHourMinuteStringFromInt_59_returns_0h_59min() {
        assertThat(DateUtils.getHourMinuteStringFromInt(59), is("0h 59min"));
    }

    @Test
    public void getHourMinuteStringFromInt_0_returns_0h_0min() {
        assertThat(DateUtils.getHourMinuteStringFromInt(0), is("0h 0min"));
    }

    @Test
    public void getHourMinuteStringFromInt_6024_returns_100h_24min() {
        assertThat(DateUtils.getHourMinuteStringFromInt(6024), is("100h 24min"));
    }

    @Test
    public void getHourMinuteStringFromInt_negativeValue_returns_unknown() {
        assertThat(DateUtils.getHourMinuteStringFromInt(-1), is("Unknown"));
    }
}
