package period.test;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import period.main.Period;

class PeriodTest {
    String start_20200731 = "20200731";
    String start_20200801 = "20200801";
    String start_20200802 = "20200802";
    String start_20200831 = "20200831";
    String start_20200901 = "20200901";
    String end_20200731 = "20200731";
    String end_20200801 = "20200801";
    String end_20200802 = "20200802";
    String end_20200830 = "20200830";
    String end_20200831 = "20200831";
    String end_20200901 = "20200901";
    SimpleDateFormat sdformat = new SimpleDateFormat("yyyyMMdd");
    Date start = null;
    Date end = null;
    Date otherStart = null;
    Date otherEnd = null;

    @Nested
    class 日付期間を保持するオブジェクトである{

        @Test
        void 開始日と終了日を持つ() {
            start = stringToDate(start_20200801, sdformat);
            end = stringToDate(end_20200831, sdformat);
            Period period = new Period(start, end);
            assertThat(period.toString(), is("[2020-08-01 - 2020-08-31]"));
        }

        @Nested
        class 終了日は開始日を含む未来日である{

            @Test
            void 日付期間は開始日_2020_08_01_と終了日_2020_08_01_を保持できる() {
                start = stringToDate(start_20200801, sdformat);
                end = stringToDate(end_20200801, sdformat);
                Period period = new Period(start, end);
                assertThat(period.toString(), is("[2020-08-01 - 2020-08-01]"));
            }
            @Test
            void 日付期間は開始日_2020_08_01_と終了日_2020_08_02_を保持できる() {
                start = stringToDate(start_20200801, sdformat);
                end = stringToDate(end_20200802, sdformat);
                Period period = new Period(start, end);
                assertThat(period.toString(), is("[2020-08-01 - 2020-08-02]"));
            }
            @Test
            void 日付期間は開始日_2020_08_02_と終了日_2020_08_01_を保持できない() {
                start = stringToDate(start_20200802, sdformat);
                end = stringToDate(end_20200801, sdformat);
                assertThrows(IllegalArgumentException.class, () -> new Period(start, end));
            }
        }
    }

    @Nested
    class 日付期間が等価であること判定できる {
        @Test
        void 開始日_2020_08_01_と終了日_2020_08_31_は開始日_2020_08_01_と終了日_2020_08_31_と一致する(){
            start = stringToDate(start_20200801, sdformat);
            end = stringToDate(end_20200831, sdformat);
            otherStart = stringToDate(start_20200801, sdformat);
            otherEnd = stringToDate(end_20200831, sdformat);
            Period period = new Period(start, end);
            Period otherPeriod = new Period(otherStart, otherEnd);
            assertThat(period.equals(otherPeriod), is(true));
        }
        @Test
        void 開始日_2020_08_01_と終了日_2020_08_31_は開始日_2020_08_01_と終了日_2020_08_30_と一致しない(){
            start = stringToDate(start_20200801, sdformat);
            end = stringToDate(end_20200831, sdformat);
            otherStart = stringToDate(start_20200801, sdformat);
            otherEnd = stringToDate(end_20200830, sdformat);
            Period period = new Period(start, end);
            Period otherPeriod = new Period(otherStart, otherEnd);
            assertThat(period.equals(otherPeriod), is(false));
        }
        @Test
        void 開始日_2020_08_01_と終了日_2020_08_31_は開始日_2020_07_31_と終了日_2020_08_31_と一致しない(){
            start = stringToDate(start_20200801, sdformat);
            end = stringToDate(end_20200831, sdformat);
            otherStart = stringToDate(start_20200731, sdformat);
            otherEnd = stringToDate(end_20200831, sdformat);
            Period period = new Period(start, end);
            Period otherPeriod = new Period(otherStart, otherEnd);
            assertThat(period.equals(otherPeriod), is(false));
        }
    }

    @Nested
    class ある日付が日付期間の範囲内であることを判定できる {
        @Test
        void 開始日_2020_08_01_と終了日_2020_08_31_において日付_2020_08_01_は範囲内である() {
            start = stringToDate(start_20200801, sdformat);
            end = stringToDate(end_20200831, sdformat);
            Date _20200801 = stringToDate("20200801", sdformat);
            Period period = new Period(start, end);
            assertThat(period.contains(_20200801), is(true));
        }
        @Test
        void 開始日_2020_08_01_と終了日_2020_08_31_において日付_2020_08_31_は範囲内である() {
            start = stringToDate(start_20200801, sdformat);
            end = stringToDate(end_20200831, sdformat);
            Date _20200831 = stringToDate("20200831", sdformat);
            Period period = new Period(start, end);
            assertThat(period.contains(_20200831), is(true));
        }
        @Test
        void 開始日_2020_08_01_と終了日_2020_08_31_において日付_2020_08_15_は範囲内である() {
            start = stringToDate(start_20200801, sdformat);
            end = stringToDate(end_20200831, sdformat);
            Date _20200815 = stringToDate("20200815", sdformat);
            Period period = new Period(start, end);
            assertThat(period.contains(_20200815), is(true));
        }
        @Test
        void 開始日_2020_08_01_と終了日_2020_08_31_において日付_2020_07_31_は範囲外である() {
            start = stringToDate(start_20200801, sdformat);
            end = stringToDate(end_20200831, sdformat);
            Date _20200731 = stringToDate("20200731", sdformat);
            Period period = new Period(start, end);
            assertThat(period.contains(_20200731), is(false));
        }
        @Test
        void 開始日_2020_08_01_と終了日_2020_08_31_において日付_2020_09_01_は範囲外である() {
            start = stringToDate(start_20200801, sdformat);
            end = stringToDate(end_20200831, sdformat);
            Date _20200901 = stringToDate("20200901", sdformat);
            Period period = new Period(start, end);
            assertThat(period.contains(_20200901), is(false));
        }
    }

    @Nested
    class ある日付期間が全て含まれていることを判定できる {
        @Test
        void 日付期間_2020_08_01__2020_08_31_は_ある日付期間_2020_08_01__2020_08_31_を全て含むと判定する() {
            start = stringToDate(start_20200801, sdformat);
            end = stringToDate(end_20200831, sdformat);
            otherStart = stringToDate(start_20200801, sdformat);
            otherEnd = stringToDate(end_20200831, sdformat);
            Period period = new Period(start, end);
            Period otherPeriod = new Period(otherStart, otherEnd);
            assertThat(period.contains(otherPeriod), is(true));
        }
        @Test
        void 日付期間_2020_08_01__2020_08_31_は_ある日付期間_2020_08_02__2020_08_30_を全て含むと判定する() {
            start = stringToDate(start_20200801, sdformat);
            end = stringToDate(end_20200831, sdformat);
            otherStart = stringToDate(start_20200802, sdformat);
            otherEnd = stringToDate(end_20200830, sdformat);
            Period period = new Period(start, end);
            Period otherPeriod = new Period(otherStart, otherEnd);
            assertThat(period.contains(otherPeriod), is(true));
        }
        @Test
        void 日付期間_2020_08_01__2020_08_31_は_ある日付期間_2020_07_31__2020_08_01_を全て含まないと判定する() {
            start = stringToDate(start_20200801, sdformat);
            end = stringToDate(end_20200831, sdformat);
            otherStart = stringToDate(start_20200731, sdformat);
            otherEnd = stringToDate(end_20200801, sdformat);
            Period period = new Period(start, end);
            Period otherPeriod = new Period(otherStart, otherEnd);
            assertThat(period.contains(otherPeriod), is(false));
        }
        @Test
        void 日付期間_2020_08_01__2020_08_31_は_ある日付期間_2020_08_31__2020_09_01_を全て含まないと判定する() {
            start = stringToDate(start_20200801, sdformat);
            end = stringToDate(end_20200831, sdformat);
            otherStart = stringToDate(start_20200831, sdformat);
            otherEnd = stringToDate(end_20200901, sdformat);
            Period period = new Period(start, end);
            Period otherPeriod = new Period(otherStart, otherEnd);
            assertThat(period.contains(otherPeriod), is(false));
        }
    }

    @Nested
    class ある日付期間の一部が含まれていることを判定できる {
        @Test
        void 日付期間_2020_08_01__2020_08_31_は_ある日付期間_2020_07_31__2020_08_01_を含むと判定する() {
            start = stringToDate(start_20200801, sdformat);
            end = stringToDate(end_20200831, sdformat);
            Period period = new Period(start, end);
            otherStart = stringToDate(start_20200731, sdformat);
            otherEnd = stringToDate(end_20200801, sdformat);
            Period otherPeriod = new Period(otherStart, otherEnd);
            assertThat(period.containsPart(otherPeriod), is(true));
        }
        @Test
        void 日付期間_2020_08_01__2020_08_31_は_ある日付期間_2020_08_31__2020_09_01_を含むと判定する() {
            start = stringToDate(start_20200801, sdformat);
            end = stringToDate(end_20200831, sdformat);
            Period period = new Period(start, end);
            otherStart = stringToDate(start_20200831, sdformat);
            otherEnd = stringToDate(end_20200901, sdformat);
            Period otherPeriod = new Period(otherStart, otherEnd);
            assertThat(period.containsPart(otherPeriod), is(true));
        }
        @Test
        void 日付期間_2020_08_01__2020_08_31_は_ある日付期間_2020_08_01__2020_08_31_を含むと判定する() {
            start = stringToDate(start_20200801, sdformat);
            end = stringToDate(end_20200831, sdformat);
            Period period = new Period(start, end);
            otherStart = stringToDate(start_20200801, sdformat);
            otherEnd = stringToDate(end_20200831, sdformat);
            Period otherPeriod = new Period(otherStart, otherEnd);
            assertThat(period.containsPart(otherPeriod), is(true));
        }
        @Test
        void 日付期間_2020_08_01__2020_08_31_は_ある日付期間_2020_07_31__2020_07_31_を含まないと判定する() {
            start = stringToDate(start_20200801, sdformat);
            end = stringToDate(end_20200831, sdformat);
            Period period = new Period(start, end);
            otherStart = stringToDate(start_20200731, sdformat);
            otherEnd = stringToDate(end_20200731, sdformat);
            Period otherPeriod = new Period(otherStart, otherEnd);
            assertThat(period.containsPart(otherPeriod), is(false));
        }
        @Test
        void 日付期間_2020_08_01__2020_08_31_は_ある日付期間_2020_09_01__2020_09_01_を含まないと判定する() {
            start = stringToDate(start_20200801, sdformat);
            end = stringToDate(end_20200831, sdformat);
            Period period = new Period(start, end);
            otherStart = stringToDate(start_20200901, sdformat);
            otherEnd = stringToDate(end_20200901, sdformat);
            Period otherPeriod = new Period(otherStart, otherEnd);
            assertThat(period.containsPart(otherPeriod), is(false));
        }
    }


    private Date stringToDate(String dateStr, SimpleDateFormat sdformat) {
        Date date = null;
        try {
            date = sdformat.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

}
