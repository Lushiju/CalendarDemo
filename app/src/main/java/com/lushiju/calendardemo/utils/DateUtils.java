package com.lushiju.calendardemo.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2017/4/10.
 */

public class DateUtils {

    public static long getTimes(String date){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date parse = format.parse(date);
            long time = parse.getTime();
            return time;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0l;
    }
}
