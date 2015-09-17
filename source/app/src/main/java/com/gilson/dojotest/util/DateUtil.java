package com.gilson.dojotest.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created by Gilson Maciel on 17/09/2015.
 */
public class DateUtil {
    public static int getDayDateDifference(Date first, Date second) {
        Calendar date1 = Calendar.getInstance();
        date1.setTime(first);
        date1.set(Calendar.HOUR_OF_DAY, 0);
        date1.set(Calendar.MINUTE, 0);
        date1.set(Calendar.SECOND, 0);
        date1.set(Calendar.MILLISECOND, 0);

        Calendar date2 = Calendar.getInstance();
        date2.setTime(second);
        date2.set(Calendar.HOUR_OF_DAY, 0);
        date2.set(Calendar.MINUTE, 0);
        date2.set(Calendar.SECOND, 0);
        date2.set(Calendar.MILLISECOND, 0);

        return (int) TimeUnit.DAYS.convert(date1.getTimeInMillis() - date2.getTimeInMillis(),
                TimeUnit.MILLISECONDS);
    }

    public static String getDateMonthYear(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("MMM yyyy");

        return format.format(date);
    }
}
