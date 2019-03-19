package com.mhdss.ggs.utils;

import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

    public static final String FORMAT_Y_M_D_H_M = "yyyy-MM-dd HH:mm";

    public static final String FORMAT_Y_M_D_H_M_S = "yyyy-MM-dd HH:mm:ss";

    public static final String FORMAT_YMDHMS = "yyyyMMddHHmmss";

    public static long getCurrentTimeStamp() {
        return System.currentTimeMillis() / 1000;
    }

    public static long getDateTimeStamp(Date date) {
        return date.getTime() / 1000;
    }

    public static long getDateTimeStamp(String date) {

        Date date1 = LocalDate.parse(date, DateTimeFormat.forPattern(FORMAT_Y_M_D_H_M)).toDate();

        return date1.getTime() / 1000;
    }

    public static String getYMDHMSDate(Long date) {

        return getFormatDate(new Date(date * 1000), FORMAT_Y_M_D_H_M_S);
    }

    public static final Long DEFAULT_SECOND_DATETIME = -1893484800L;

    private static String getFormatDate(Date date, String format) {

        if (date == null || date.getTime() / 1000 == DEFAULT_SECOND_DATETIME) {
            return "";
        }

        return new SimpleDateFormat(format).format(date);
    }

    public static String getNowYMDHMSM() {

        Long cuurentTime = System.currentTimeMillis();
        String YMDHMS = getFormatDate(new Date(cuurentTime), FORMAT_YMDHMS);

        String millis = String.valueOf(cuurentTime);
        millis = millis.substring(millis.length() - 3, millis.length());
        return YMDHMS + millis;
    }

    public static String getNowYMDHMS() {

        return getFormatDate(new Date(System.currentTimeMillis()), FORMAT_YMDHMS);
    }
}
