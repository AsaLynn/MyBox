package com.zxning.library.tool;

import android.text.TextUtils;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 用于数据转换
 */
public class TransformUtils {

    public static String keepTwoPoint(String b) {
        if (!TextUtils.isEmpty(b)) {
            DecimalFormat df = new DecimalFormat("#####0.00");
            String format = df.format(Float.parseFloat(b));
            return format;
        } else {
            return "0";
        }
    }

    public static String getCurrentTimeByDate() {
        // 得到long类型当前时间
        long l = System.currentTimeMillis();
        // new日期对象
        Date date = new Date(l);
        // 转换提日期输出格式
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        //System.out.println(dateFormat.format(date));
        String time = dateFormat.format(date);
        return time;
    }

    //double类型的数据,保留两位小数点.
    //KeepTwoDecimalPlaces();
    public static double keepTwoDecimalPlaces(double b) {
        DecimalFormat df = new DecimalFormat("#.##");
        //double interest = Double.parseDouble(df.format(b));
        //return String.valueOf(interest);
        return Double.parseDouble(df.format(b));
    }

    // 根据日期取得星期几
    public static String getWeek(String strDate) {

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 定义日期格式
        Date date = null;
        try {
            date = format.parse(strDate);// 将字符串转换为日期
        } catch (ParseException e) {
            System.out.println("输入的日期格式不合理！");
        }

        String[] weeks = {"周日", "周一", "周二", "周三", "周四", "周五", "周六"};
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int week_index = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (week_index < 0) {
            week_index = 0;
        }
        return weeks[week_index];
    }

    public static String getYearMonth(String strDate) {
        return strDate.substring(0, 7);
    }

    public static String getMonthDay(String strDate) {
        return strDate.substring(5, 10);
    }

}
