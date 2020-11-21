//https://github.com/javasampleokiba/BusinessDayCalculator/tree/master/src/javasampleokiba/businessdaycalculator/holiday

package jp.mincra.mathclub.util.holiday;

import java.util.Calendar;

/**
 * 日本の「国民の祝日」を表す列挙型クラス。
 *
 * [注意事項]
 * <ul>
 * <li>今後の法律改正により正常に動作しなくなる可能性があります。</li>
 * <li>2151年以降の「春分の日」、「秋分の日」は求めることができません。</li>
 * </ul>
 */
public enum JapaneseNationalHoliday {
    /** 元日 */
    NewYearsDay                     ("元日") {
        @Override
        public Calendar dateOf(int year) {
            if (1949 <= year) {
                return toCalendar(year, 1, 1);
            }
            return null;
        }
    },
    /** 成人の日 */
    ComingOfAgeDay                  ("成人の日") {
        @Override
        public Calendar dateOf(int year) {
            if (1949 <= year && year <= 1999) {
                return toCalendar(year, 1, 15);
            } else if (2000 <= year) {
                return mondayOf(year, 1, 2);
            }
            return null;
        }
    },
    /** 建国記念の日 */
    NationalFoundationDay           ("建国記念の日") {
        @Override
        public Calendar dateOf(int year) {
            if (1967 <= year) {
                return toCalendar(year, 2, 11);
            }
            return null;
        }
    },
    /** 天皇誕生日 */
    EmperorsBirthday                ("天皇誕生日") {
        @Override
        public Calendar dateOf(int year) {
            if (1949 <= year && year <= 1988) {
                return toCalendar(year, 4, 29);
            } else if (1989 <= year && year <= 2018) {
                return toCalendar(year, 12, 23);
            } else if (2020 <= year) {
                return toCalendar(year, 2, 23);
            }
            return null;
        }
    },
    /** 春分の日 */
    VernalEquinoxDay                ("春分の日") {
        @Override
        public Calendar dateOf(int year) {
            if (1949 <= year) {
                return toCalendar(year, 3, calcVernalEquinoxDay(year));
            }
            return null;
        }
    },
    /** みどりの日 */
    GreeneryDay                     ("みどりの日") {
        @Override
        public Calendar dateOf(int year) {
            if (1989 <= year && year <= 2006) {
                return toCalendar(year, 4, 29);
            } else if (2007 <= year) {
                return toCalendar(year, 5, 4);
            }
            return null;
        }
    },
    /** 昭和の日 */
    ShowaDay                        ("昭和の日") {
        @Override
        public Calendar dateOf(int year) {
            if (2007 <= year) {
                return toCalendar(year, 4, 29);
            }
            return null;
        }
    },
    /** 憲法記念日 */
    ConstitutionMemorialDay         ("憲法記念日") {
        @Override
        public Calendar dateOf(int year) {
            if (1949 <= year) {
                return toCalendar(year, 5, 3);
            }
            return null;
        }
    },
    /** こどもの日 */
    ChildrensDay                    ("こどもの日") {
        @Override
        public Calendar dateOf(int year) {
            if (1949 <= year) {
                return toCalendar(year, 5, 5);
            }
            return null;
        }
    },
    /** 海の日 */
    MarineDay                       ("海の日") {
        @Override
        public Calendar dateOf(int year) {
            if (1996 <= year && year <= 2002) {
                return toCalendar(year, 7, 20);
            } else if (year == 2020) {
                return toCalendar(year, 7, 23);
            } else if (2003 <= year) {
                return mondayOf(year, 7, 3);
            }
            return null;
        }
    },
    /** 山の日 */
    MountainDay                     ("山の日") {
        @Override
        public Calendar dateOf(int year) {
            if (year == 2020) {
                return toCalendar(year, 8, 10);
            } else if (2016 <= year) {
                return toCalendar(year, 8, 11);
            }
            return null;
        }
    },
    /** 敬老の日 */
    RespectForTheAgedDay            ("敬老の日") {
        @Override
        public Calendar dateOf(int year) {
            if (1966 <= year && year <= 2002) {
                return toCalendar(year, 9, 15);
            } else if (2003 <= year) {
                return mondayOf(year, 9, 3);
            }
            return null;
        }
    },
    /** 秋分の日 */
    AutumnalEquinoxDay              ("秋分の日") {
        @Override
        public Calendar dateOf(int year) {
            if (1948 <= year) {
                return toCalendar(year, 9, calcAutumnalEquinoxDay(year));
            }
            return null;
        }
    },
    /** 体育の日 */
    HealthAndSportsDay              ("体育の日") {
        @Override
        public Calendar dateOf(int year) {
            if (1966 <= year && year <= 1999) {
                return toCalendar(year, 10, 10);
            } else if (2000 <= year && year <= 2019) {
                return mondayOf(year, 10, 2);
            }
            return null;
        }
    },
    /** スポーツの日 */
    SportsDay                       ("スポーツの日") {
        @Override
        public Calendar dateOf(int year) {
            if (year == 2020) {
                return toCalendar(year, 7, 24);
            } else if (2021 <= year) {
                return mondayOf(year, 10, 2);
            }
            return null;
        }
    },
    /** 文化の日 */
    NationalCultureDay              ("文化の日") {
        @Override
        public Calendar dateOf(int year) {
            if (1948 <= year) {
                return toCalendar(year, 11, 3);
            }
            return null;
        }
    },
    /** 勤労感謝の日 */
    LaborThanksgivingDay            ("勤労感謝の日") {
        @Override
        public Calendar dateOf(int year) {
            if (1948 <= year) {
                return toCalendar(year, 11, 23);
            }
            return null;
        }
    },
    ;
    private static final double DIFF_DAY_OF_YEAR        = 0.242194;
    private final String name_;

    private JapaneseNationalHoliday(String name) {
        name_ = name;
    }

    /**
     * 指定した年におけるこの祝日の日付を取得します。
     * 祝日が存在しない場合は{@code null}を返す。
     *
     * @param year  年
     * @return 日付
     * @throws IllegalArgumentException   「春分の日」「秋分の日」を計算できない{@code year}を指定した場合
     */
    public abstract Calendar dateOf(int year);

    /**
     * 指定した日付がこの祝日の日付と一致するか判定します。
     *
     * @param cal  判定する日付
     * @return 一致する場合は {@code true}
     */
    public boolean is(Calendar cal) {
        return isSameDate(cal, dateOf(cal.get(Calendar.YEAR)));
    }

    @Override
    public String toString() {
        return name_;
    }

    private static int calcVernalEquinoxDay(int year) {
        int diff1 = year - 1980;
        int diff2 = 0;
        double standard = 0;
        if (year <= 1979) {
            standard = 20.8357;
            diff2 = year - 1983;
        } else if (year <= 2099) {
            standard = 20.8431;
            diff2 = year - 1980;
        } else if (year <= 2150) {
            standard = 21.8510;
            diff2 = year - 1980;
        } else {
            throw new IllegalArgumentException(year + "th year is illegal value.");
        }
        return (int)(standard + DIFF_DAY_OF_YEAR * diff1 - (int)(diff2 / 4));
    }

    private static int calcAutumnalEquinoxDay(int year) {
        int diff1 = year - 1980;
        int diff2 = 0;
        double standard = 0;
        if (year <= 1979) {
            standard = 23.2588;
            diff2 = year - 1983;
        } else if (year <= 2099) {
            standard = 23.2488;
            diff2 = year - 1980;
        } else if (year <= 2150) {
            standard = 24.2488;
            diff2 = year - 1980;
        } else {
            throw new IllegalArgumentException(year + "th year is illegal value.");
        }
        return (int)(standard + DIFF_DAY_OF_YEAR * diff1 - (int)(diff2 / 4));
    }

    private static boolean isSameDate(Calendar cal1, Calendar cal2) {
        if (cal1 == null) {
            throw new NullPointerException("Calendar object is null.");
        }
        if (cal2 == null) {
            return false;
        }
        return cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH) &&
                cal1.get(Calendar.DAY_OF_MONTH) == cal2.get(Calendar.DAY_OF_MONTH);
    }

    private static Calendar toCalendar(int year, int month, int day) {
        Calendar cal = Calendar.getInstance();
        cal.set(year, month - 1, day);
        return cal;
    }

    private static Calendar mondayOf(int year, int month, int ordinal) {
        Calendar cal = Calendar.getInstance();
        cal.set(year, month - 1, 1);
        cal.set(Calendar.DAY_OF_WEEK_IN_MONTH, ordinal);
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        return cal;
    }
}