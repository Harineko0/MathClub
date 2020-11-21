package jp.mincra.mathclub.util;

import java.util.Calendar;
import java.util.Date;

public class MathClubDate{
    public static String getDayOfWeek(Calendar calendar){
        switch (calendar.get(Calendar.DAY_OF_WEEK)) {
            case Calendar.SUNDAY: return "日";
            case Calendar.MONDAY: return "月";
            case Calendar.TUESDAY: return "火";
            case Calendar.WEDNESDAY: return "水";
            case Calendar.THURSDAY: return "木";
            case Calendar.FRIDAY: return "金";
            case Calendar.SATURDAY: return "土";
        }
        throw new IllegalStateException();
    }
}
