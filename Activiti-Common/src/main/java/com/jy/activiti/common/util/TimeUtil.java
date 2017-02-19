package com.jy.activiti.common.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtil {

    private static final SimpleDateFormat YYYYMMMDD = new SimpleDateFormat("yyyy-MM-dd");
    private static final SimpleDateFormat HHMMSS = new SimpleDateFormat("HH:mm:ss");
    private static final SimpleDateFormat YYYYMMMDD_HHMMSS = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static synchronized String formatYYYYMMMDDHHMMSS(Date date) {
        if (date == null) {
            return "";
        }
        return YYYYMMMDD_HHMMSS.format(date);
    }

    public static synchronized String formatYYYYMMMDD(Date date) {
        if (date == null) {
            return "";
        }
        return YYYYMMMDD.format(date);
    }

}
