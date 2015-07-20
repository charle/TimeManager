package com.jing.du.common.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by charle-chen on 15/7/1.
 */
public class DateUtils {

    public static String getDateMonth(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return String.valueOf(cal.get(Calendar.MONTH) + 1);
    }

    public static String getDateTian(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int tempDate = cal.get(Calendar.DATE);
        if (tempDate < 10) {
            return "0" + tempDate;
        } else {
            return String.valueOf(tempDate);
        }
    }

    public static String getStringOfDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String str = sdf.format(date);
        return str;
    }

    public static String getSecondOfDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String str = sdf.format(date);
        return str;
    }

    public static String getChieseDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
        String str = sdf.format(date);
        return str;
    }
}
