//https://github.com/javasampleokiba/BusinessDayCalculator/tree/master/src/javasampleokiba/businessdaycalculator/holiday

package jp.mincra.mathclub.util.holiday;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

/**
 * 日本の「国民の祝日」、「振替休日」、「国民の休日」に関するユーティリティクラス
 *
 * [注意事項]
 * <ul>
 * <li>今後の法律改正により正常に動作しなくなる可能性があります。</li>
 * <li>2151年以降の「春分の日」、「秋分の日」は求めることができません。</li>
 * <li>複数の「国民の祝日」が重なる場合は、いずれか一方の情報しか取得できません。
 * （今後最初に祝日が重複するのは2876年と予測されています）</li>
 * </ul>
 */
public class JapaneseHolidayUtils {

    private static final Calendar AMENDMENT_DATE_2ND    = Calendar.getInstance();
    private static final Calendar AMENDMENT_DATE_3RD    = Calendar.getInstance();
    private static final Calendar AMENDMENT_DATE_8TH    = Calendar.getInstance();
    static {
        AMENDMENT_DATE_2ND.set(1973, 3, 12);
        AMENDMENT_DATE_3RD.set(1985, 11, 27);
        AMENDMENT_DATE_8TH.set(2007, 0, 1);
    }
    private static final String SUBSTITUTE_HOLIDAY      = "振替休日";
    private static final String CITIZENS_HOLIDAY        = "国民の休日";

    private JapaneseHolidayUtils() {
    }

    /**
     * 指定した日付の{@code NationalHoliday}オブジェクトを取得します。
     * 指定した日付が「国民の祝日」ではない場合は{@code null}を返します。
     *
     * @param cal  日付
     * @return 指定した日付の{@code NationalHoliday}オブジェクト
     * @throws NullPointerException  {@code cal}が{@code null}の場合
     */
    public static JapaneseNationalHoliday getNationalHoliday(Calendar cal) {
        checkNotNull(cal);
        for (JapaneseNationalHoliday holiday : JapaneseNationalHoliday.values()) {
            if (holiday.is(cal)) return holiday;
        }
        return null;
    }

    /**
     * 指定した日付の「国民の祝日」の名前を取得します。
     * 「振替休日」、「国民の休日」の場合はそれぞれ"振替休日"、"国民の休日"を返します。
     * 「国民の祝日」、「振替休日」、「国民の休日」のいずれでもない場合は{@code null}を返します。
     *
     * @param cal  日付
     * @return 指定した日付の「国民の祝日」の名前、または"振替休日"、"国民の休日"
     * @throws NullPointerException  {@code cal}が{@code null}の場合
     */
    public static String getHolidayName(Calendar cal) {
        checkNotNull(cal);
        String name = getNationalHolidayName(cal);
        if (name != null) return name;

        // 振替休日判定
        name = getSubstituteHoliday(cal);
        if (name != null) return name;

        // 国民の休日判定
        name = getCitizensHoliday(cal);
        if (name != null) return name;

        return null;
    }

    /**
     * 指定した日付の「国民の祝日」の名前を取得します。
     * 「国民の祝日」ではない場合は{@code null}を返します。
     *
     * @param cal  日付
     * @return 指定した日付の「国民の祝日」の名前
     * @throws NullPointerException  {@code cal}が{@code null}の場合
     */
    public static String getNationalHolidayName(Calendar cal) {
        checkNotNull(cal);
        JapaneseNationalHoliday holiday = getNationalHoliday(cal);
        return holiday != null ? holiday.toString() : null;
    }

    /**
     * 指定した日付が「国民の祝日」、「振替休日」、「国民の休日」のいずれかであるか判定します。
     *
     * @param cal  日付
     * @return 指定した日付が「国民の祝日」、「振替休日」、「国民の休日」のいずれかである場合は {@code true}
     * @throws NullPointerException  {@code cal}が{@code null}の場合
     */
    public static boolean isHoliday(Calendar cal) {
        checkNotNull(cal);
        return getHolidayName(cal) != null;
    }

    /**
     * 指定した日付が「国民の祝日」であるか判定します。
     *
     * @param cal  日付
     * @return 指定した日付が「国民の祝日」である場合は {@code true}
     * @throws NullPointerException  {@code cal}が{@code null}の場合
     */
    public static boolean isNationalHoliday(Calendar cal) {
        checkNotNull(cal);
        return getNationalHoliday(cal) != null;
    }

    /**
     * 指定した日付が「振替休日」であるか判定します。
     *
     * @param cal  日付
     * @return 指定した日付が「振替休日」である場合は {@code true}
     * @throws NullPointerException  {@code cal}が{@code null}の場合
     */
    public static boolean isSubstituteHoliday(Calendar cal) {
        checkNotNull(cal);
        return getHolidayName(cal) == SUBSTITUTE_HOLIDAY;
    }

    /**
     * 指定した日付が「国民の休日」であるか判定します。
     *
     * @param cal  日付
     * @return 指定した日付が「国民の休日」である場合は {@code true}
     * @throws NullPointerException  {@code cal}が{@code null}の場合
     */
    public static boolean isCitizensHoliday(Calendar cal) {
        checkNotNull(cal);
        return getHolidayName(cal) == CITIZENS_HOLIDAY;
    }

    /**
     * 指定した年のすべての「国民の祝日」、「振替休日」、「国民の休日」の日付を取得します。
     *
     * @param year  取得対象年
     * @return 「国民の祝日」、「振替休日」、「国民の休日」の日付のリスト
     */
    public static List<Calendar> getHolidaysOf(int year) {
        List<Calendar> list = new ArrayList<Calendar>();
        for (int i = 1; i <= 12; i++) {
            list.addAll(getHolidaysOf(year, i));
        }
        Collections.sort(list);
        return list;
    }

    /**
     * 指定した年と月のすべての「国民の祝日」、「振替休日」、「国民の休日」の日付を取得します。
     *
     * @param year   取得対象年
     * @param month  取得対象月
     * @return 「国民の祝日」、「振替休日」、「国民の休日」の日付のリスト
     */
    public static List<Calendar> getHolidaysOf(int year, int month) {
        // 国民の祝日取得
        List<Calendar> list = new ArrayList<Calendar>();
        for (JapaneseNationalHoliday holiday : JapaneseNationalHoliday.values()) {
            Calendar cal = holiday.dateOf(year);
            if (cal != null && month == cal.get(Calendar.MONTH) + 1) {
                list.add(cal);
            }
        }

        // 振替休日取得
        List<Calendar> shList = new ArrayList<Calendar>();
        for (Calendar cal : list) {
            Calendar c = tomorrow(cal);
            if (getSubstituteHoliday(c) != null &&
                    !list.contains(c)) {
                shList.add(c);
            }
        }

        // 国民の休日取得
        List<Calendar> chList = new ArrayList<Calendar>();
        for (Calendar cal : list) {
            Calendar c = tomorrow(cal);
            if (getCitizensHoliday(c) != null &&
                    !list.contains(c) &&
                    !shList.contains(c)) {
                chList.add(c);
            }
        }

        list.addAll(shList);
        list.addAll(chList);
        Collections.sort(list);
        return list;
    }

    private static String getSubstituteHoliday(Calendar cal) {
        // 法律改正前は、振替休日にはならない
        if (AMENDMENT_DATE_2ND.compareTo(cal) >= 0) return null;

        // 日曜の場合は、振替休日にはならない
        if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) return null;

        Calendar c = yesterday(cal);
        if (AMENDMENT_DATE_8TH.compareTo(cal) > 0) {
            // 祝日が日曜日の場合はその翌日の月曜日を振替休日とする
            if (isNationalHoliday(c) &&
                    c.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
                return SUBSTITUTE_HOLIDAY;
            }
        } else {
            // 連続する祝日のうち、どれか1日が日曜日と重なった場合は、最後の祝日の翌日が振替休日とする
            while (true) {
                if (isNationalHoliday(c)) {
                    if (c.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
                        return SUBSTITUTE_HOLIDAY;
                    }
                } else {
                    break;
                }
                c = yesterday(c);
            }
        }
        return null;
    }

    private static String getCitizensHoliday(Calendar cal) {
        // 法律改正前は、国民の休日にはならない
        if (AMENDMENT_DATE_3RD.compareTo(cal) >= 0) return null;

        // 日曜の場合は、国民の休日にはならない
        if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) return null;

        if (isNationalHoliday(yesterday(cal)) &&
                isNationalHoliday(tomorrow(cal))) {
            return CITIZENS_HOLIDAY;
        }
        return null;
    }

    private static Calendar yesterday(Calendar cal) {
        Calendar c = (Calendar) cal.clone();
        c.add(Calendar.DAY_OF_YEAR, -1);
        return c;
    }

    private static Calendar tomorrow(Calendar cal) {
        Calendar c = (Calendar) cal.clone();
        c.add(Calendar.DAY_OF_YEAR, 1);
        return c;
    }

    private static void checkNotNull(Calendar cal) {
        if (cal == null) throw new NullPointerException("Calendar object is null.");
    }
}