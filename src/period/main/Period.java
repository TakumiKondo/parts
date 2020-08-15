package period.main;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Period {
    private Date _start;
    private Date _end;

    public Period(Date start, Date end) {
        if(end.before(start)) {
            throw new IllegalArgumentException(
                    "Start :" + start + " - End :" + end + " -> 終了日は開始日を含む未来日として下さい。");
        }
        _start = start;
        _end = end;
    }

    public String toString() {
        String format = "yyyy-MM-dd";
        return String.format("[%s - %s]", DateToString(_start, format), DateToString(_end, format));
    }

    public boolean equals(Period other) {
        return _start.equals(other._start) && _end.equals(other._end);
    }

    public boolean contains(Date when) {
        boolean isAfterIncludingStart = _start.equals(when) || _start.before(when);
        boolean isBeforeIncludingEnd = _end.after(when) || _end.equals(when);
        return isAfterIncludingStart && isBeforeIncludingEnd;
    }

    public boolean contains(Period other) {
        return contains(other._start) && contains(other._end);
    }

    public boolean containsPart(Period other) {
        if(_start.after(other._end)) return false;
        if(_end.before(other._start)) return false;
        return true;
    }

    private String DateToString(Date date, String format) {
        return new SimpleDateFormat(format).format(date);
    }
}
