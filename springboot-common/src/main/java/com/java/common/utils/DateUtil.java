package com.java.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Mr.BH
 */
public class DateUtil {


    public static Date getCurrentDate(){
        return new Date();
    }


    /**
     * 获取当前时间字符串
     *
     * @return yyyy-MM-dd HH:mm:ss
     */
    public static String getCurrDate() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String now_date = simpleDateFormat.format(new Date());
        return now_date;
    }

    /**
     * 获得某个时间段内的随机时间
     *
     * @param beginDate yyyy-MM-dd HH:mm:ss
     * @param endDate yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static Date randomDate(String beginDate, String endDate) {
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date start = format.parse(beginDate);// 开始日期
            Date end = format.parse(endDate);// 结束日期
            if (start.getTime() >= end.getTime()) {
                return null;
            }
            long date = random(start.getTime(), end.getTime());

            return new Date(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static long random(long begin, long end) {
        long rtnn = begin + (long) (Math.random() * (end - begin));
        if (rtnn == begin || rtnn == end) {
            return random(begin, end);
        }
        return rtnn;
    }

    /**
     * 获取两个日期相减得到的天数
     *
     * @param beginDateStr yyyy-MM-dd
     * @param endDateStr yyyy-MM-dd
     * @return
     */
    public static long getDaySub(String beginDateStr,String endDateStr)
    {
        long day=0;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date beginDate;
        Date endDate;
        try
        {
            beginDate = format.parse(beginDateStr);
            endDate= format.parse(endDateStr);
            day=(endDate.getTime()-beginDate.getTime())/(24*60*60*1000);
            //System.out.println("相隔的天数="+day);
        } catch (ParseException e)
        {
            // TODO 自动生成 catch 块
            e.printStackTrace();
        }
        return day;
    }

    /**
     * 将字符串转换成日期格式
     *
     * @param time yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static Date parseString(String time) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        try {
            date = simpleDateFormat.parse(time);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 将字符串转换成日期格式
     *
     * @param time yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static Date parseStringHM(String time) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
        Date date = new Date();
        try {
            date = simpleDateFormat.parse(time);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 将字符串转换成日期格式
     *
     * @param time yyyy-MM-dd
     * @return
     */
    public static Date parseStringYMD(String time) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        try {
            date = simpleDateFormat.parse(time);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 将日期格式转换成字符串 yyyy-MM-dd
     *
     * @param date
     * @return yyyy-MM-dd
     */
    public static String parseDateYMD(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String now_date = simpleDateFormat.format(date);
        return now_date;
    }

    /**
     * 将日期转换成字符串 yyyy-MM-dd HH:mm:ss
     *
     * @param date
     * @return yyyy-MM-dd HH:mm:ss
     */
    public static String parseDate(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String now_date = simpleDateFormat.format(date);
        return now_date;
    }

    /**
     * 将日期转换成字符串 yyyy年MM月dd日 HH:mm:ss
     *
     * @param date
     * @return yyyy-MM-dd HH:mm:ss
     */
    public static String parseDateStr(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        String now_date = simpleDateFormat.format(date);
        return now_date;
    }

    /**
     * 将日期转换成字符串 yyyy年MM月dd日 HH:mm:ss
     *
     * @param date
     * @return yyyy-MM-dd HH:mm:ss
     */
    public static String parseDateYMDStr(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日");
        String now_date = simpleDateFormat.format(date);
        return now_date;
    }

    /**
     * 获取当前时间的时分秒
     *
     * @return HH:mm:ss
     */
    public static String getTime() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
        String now_date = simpleDateFormat.format(new Date());
        return now_date;
    }

    /**
     * 获取当前时间的年月日
     *
     * @return yyyy-MM-dd
     */
    public static String getDateYMD() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String now_date = simpleDateFormat.format(new Date());
        return now_date;
    }

    /**
     * 获取当前时间字符串
     *
     * @return yyyyMMddHHmmss
     */
    public static String getDateStr() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String now_date = simpleDateFormat.format(new Date());
        return now_date;
    }

    /**
     *
     * 将日期格式化为<字符串类型>
     *
     * @param date 要格式化的日期
     * @param dateFormat 日期格式
     * @return 当前日期<字符串类型>
     */
    public static String dateToStr(Date date, String dateFormat) {
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        return sdf.format(date);
    }

    /**
     * 得到当前日期<字符串类型>
     *
     * @param dateFormat 日期格式
     * @return 当前日期<字符串类型>
     */
    public static String getCurrDate(String dateFormat) {
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        return sdf.format(new Date());
    }


    /**
     * 判断两个时间是否超过24小时
     *
     * @param date1
     * @param date2
     * @return
     * @throws Exception
     */
    public static boolean jisuan(String date1, String date2) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date start = sdf.parse(date1);
        Date end = sdf.parse(date2);
        long cha = end.getTime() - start.getTime();
        double result = cha * 1.0 / (1000 * 60 * 60);
        if (result <= 24) {
            //System.out.println("可用");
            return false;
        } else {
            //System.out.println("已过期");
            return true;
        }
    }

    public static List<Date> findDates(Date dBegin, Date dEnd) {
        List dateList = new ArrayList();
        dateList.add(dBegin);
        Calendar calBegin = Calendar.getInstance();
        // 使用给定的 Date 设置此 Calendar 的时间
        calBegin.setTime(dBegin);
        Calendar calEnd = Calendar.getInstance();
        // 使用给定的 Date 设置此 Calendar 的时间
        calEnd.setTime(dEnd);
        // 测试此日期是否在指定日期之后
        while (dEnd.after(calBegin.getTime())) {
            // 根据日历的规则，为给定的日历字段添加或减去指定的时间量
            calBegin.add(Calendar.DAY_OF_MONTH, 1);
            dateList.add(calBegin.getTime());
        }
        return dateList;
    }

    public static List<Date> findMonths(Date dBegin, Date dEnd) {
        List dateList = new ArrayList();
        dateList.add(dBegin);
        Calendar calBegin = Calendar.getInstance();
        // 使用给定的 Date 设置此 Calendar 的时间
        calBegin.setTime(dBegin);
        Calendar calEnd = Calendar.getInstance();
        // 使用给定的 Date 设置此 Calendar 的时间
        calEnd.setTime(dEnd);
        // 测试此日期是否在指定日期之后
        while (dEnd.after(calBegin.getTime())) {
            // 根据日历的规则，为给定的日历字段添加或减去指定的时间量
            calBegin.add(Calendar.MONTH, 1);
            dateList.add(calBegin.getTime());
        }
        return dateList;
    }

    public static List<Date> findHours(Date dBegin, Date dEnd) {
        List dateList = new ArrayList();
        dateList.add(dBegin);
        Calendar calBegin = Calendar.getInstance();
        // 使用给定的 Date 设置此 Calendar 的时间
        calBegin.setTime(dBegin);
        Calendar calEnd = Calendar.getInstance();
        // 使用给定的 Date 设置此 Calendar 的时间
        calEnd.setTime(dEnd);
        // 测试此日期是否在指定日期之后
        while (dEnd.after(calBegin.getTime())) {
            // 根据日历的规则，为给定的日历字段添加或减去指定的时间量
            calBegin.add(Calendar.HOUR, 1);
            dateList.add(calBegin.getTime());
        }
        return dateList;
    }

    /**
     * 根据日期取得星期几
     *
     * @param date
     * @return Mon Tue Wed Thu Fri Sat Sun
     */
    public static String getWeek(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("E", Locale.US);
        String week = sdf.format(date);
        return week;
    }

    /**
     * 得到几天前的时间
     *
     * @param d 日期
     * @param day 天数
     * @return
     */
    public static String getDateBefore(Date d, int day) {
        Calendar now = Calendar.getInstance();
        now.setTime(d);
        now.set(Calendar.DATE, now.get(Calendar.DATE) - day);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return simpleDateFormat.format(now.getTime());
    }

    /**
     * 得到几天前的时间2
     *
     * @param d 日期<字符串>
     * @param day 天数
     * @return
     */
    public static String getDateBefore2(String d, int day) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = sdf.parse(d);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Calendar now = Calendar.getInstance();//实例化Celendar对象
        now.setTime(date);
        now.set(Calendar.DATE, now.get(Calendar.DATE) - day);
        return sdf.format(now.getTime());
    }

    /**
     * 得到几个月前的时间
     *
     * @param d 日期
     * @param mouth 月数
     * @return
     */
    public static String getMouBefore(Date d, int mouth) {
        Calendar now = Calendar.getInstance();
        now.setTime(d);
        now.set(Calendar.MONTH, now.get(Calendar.MONTH) - mouth);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM");
        return simpleDateFormat.format(now.getTime());
    }

    /**
     * 得到几天后的时间
     *
     * @param d 日期
     * @param day 天数
     * @return
     */
    public static String getDateAfter(Date d, int day) {
        Calendar now = Calendar.getInstance();
        now.setTime(d);
        now.set(Calendar.DATE, now.get(Calendar.DATE) + day);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return simpleDateFormat.format(now.getTime());
    }

    /**
     * 得到几天后的时间2
     *
     * @param d 日期<字符串>
     * @param day 天数
     * @return
     */
    public static String getDateAfter2(String d, int day) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = sdf.parse(d);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Calendar now = Calendar.getInstance();//实例化Celendar对象
        now.setTime(date);
        now.set(Calendar.DATE, now.get(Calendar.DATE) + day);
        return sdf.format(now.getTime());
    }

    /**
     * 比较时间的大小（按照日期比较）
     *
     * @param date1
     * @param date2
     * @return
     */
    public static int compareDate(String date1, String date2) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            // 获取7天以内的数据
            date1 = getDateBefore2(date1, 6);
            Date dt1 = simpleDateFormat.parse(date1);
            Date dt2 = simpleDateFormat.parse(date2);
            if (dt1.getTime() > dt2.getTime()) {
                return 1;
            } else if (dt1.getTime() <= dt2.getTime()) {
                return 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 比较时间的大小 按照完整的时间
     *
     * @param date1
     * @param date2
     * @return
     */
    public static int compateAppointDate(String date1, String date2) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        try {
            Date dt1 = simpleDateFormat.parse(date1);
            Date dt2 = simpleDateFormat.parse(date2);
            if (dt1.getTime() >= dt2.getTime()) {
                return 1;
            } else if (dt1.getTime() < dt2.getTime()) {
                return -1;
            } else {
                return 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }


    /**
     * 得到某年某月的某一周的第一天的日期
     *
     * @param year
     * @param month
     * @param week
     * @return
     */
    public static String getWeekFirstday(String year, String month, String week) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        c = Calendar.getInstance();
        c.set(c.YEAR, Integer.parseInt(year));//设置年
        c.set(c.MONTH, Integer.parseInt(month) - 1);//设置月
        c.set(c.WEEK_OF_MONTH, Integer.parseInt(week));//设置星期
        c.set(Calendar.DAY_OF_WEEK, 1);//本周第一天，以星期日开始
        return (sdf.format(c.getTime()));
    }

    /**
     * 获得起始月份和结束月份之间的月份
     *
     * @param date1
     * @param date2
     * @return
     */
    public static List getYearMonth(Date date1, Date date2) {
        Calendar dd = Calendar.getInstance();//定义日期实例
        dd.setTime(date1);//设置日期起始时间
        String str = "";
        List<Object> datelist = new ArrayList<Object>();
        while (dd.getTime().before(date2)) {//判断是否到结束日期
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
            str = sdf.format(dd.getTime());
            dd.add(Calendar.MONTH, 1);//进行当前日期月份加1
            datelist.add(str);
        }
        return datelist;
    }

    /**
     * 获取某年某月的第一天
     *
     * @param year
     * @param month
     * @return
     */
    public static String getfirstday(String year, String month) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        //获取前月的第一天
        Calendar cal_1 = Calendar.getInstance();//获取当前日期

        cal_1.set(Calendar.YEAR, Integer.parseInt(year));
        cal_1.set(Calendar.MONTH, Integer.parseInt(month));

        cal_1.add(Calendar.MONTH, -1);
        cal_1.set(Calendar.DAY_OF_MONTH, 1);//设置为1号,当前日期既为本月第一天
        return format.format(cal_1.getTime());
    }

    /**
     * 获取某年某月的最后一天
     *
     * @param year
     * @param month
     * @return
     */
    public static String getLastDay(String year, String month) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar ca = Calendar.getInstance();
        ca.set(Calendar.YEAR, Integer.parseInt(year));
        ca.set(Calendar.MONTH, Integer.parseInt(month));

        ca.add(Calendar.MONTH, -1);
        ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH));
        String last = format.format(ca.getTime());
        return last;
    }

    /**
     * 判断所给日期是星期几
     *
     * @param pTime
     * @return
     * @throws Exception
     */
    public static int dayForWeek(String pTime) throws Exception {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        c.setTime(format.parse(pTime));
        int dayForWeek = 0;
        if (c.get(Calendar.DAY_OF_WEEK) == 1) {
            dayForWeek = 7;
        } else {
            dayForWeek = c.get(Calendar.DAY_OF_WEEK) - 1;
        }
        return dayForWeek;
    }

    /**
     * 将String类型的格林威治时间转成Date类型的格林威治时间
     *
     * @param time
     * @return
     */
    public static Date stringToDate(String time) {
        SimpleDateFormat sdf = new SimpleDateFormat("E MMM dd HH:mm:ss z yyyy", Locale.US);
        Date date = new Date();
        try {
            date = sdf.parse(time);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     *
     * @param date
     * @param formate
     * @return
     */
    public static String getFormatDate(long date, String formate) {
        SimpleDateFormat sdf = new SimpleDateFormat(formate);
        return sdf.format(new Date(date));
    }
    /**
     * 13、10位时间戳转成String类型的日期
     *
     * @param str_num
     * @return 转换异常返回 0
     */
    public static String timestamp2Date(String str_num) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (str_num.length() == 13) {
            String date = sdf.format(new Date(toLong(str_num)));
            return date;
        } else {
            String date = sdf.format(new Date(toInt(str_num) * 1000L));
            return date;
        }
    }

    /**
     * 将日期转成时间戳
     *
     * @param date
     * @return 转换异常返回 0
     */
    public static long getTime(Date date){
        long lSysTime1 = date.getTime();
        return lSysTime1;
    }

    /**
     * String转long
     *
     * @param obj
     * @return 转换异常返回 0
     */
    public static long toLong(String obj) {
        try {
            return Long.parseLong(obj);
        } catch (Exception e) {
        }
        return 0;
    }

    /**
     * 将字符串转换成日期格式(毫秒)
     *
     * @return
     */
    public static Date getDateTimeHM() {
        String dateStr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S").format(new Date());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
        Date date = new Date();
        try {
            date = simpleDateFormat.parse(dateStr);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 对象转整
     *
     * @param obj
     * @return 转换异常返回 0
     */
    public static int toInt(Object obj) {
        if (obj == null)
            return 0;
        return toInt(obj.toString());
    }

    /**
     * 把符合日期格式的字符串转换为日期类型
     */
    public static Date stringtoDate(String dateStr, String format) {
        Date d = null;
        SimpleDateFormat formater = new SimpleDateFormat(format);
        try {
            formater.setLenient(false);
            d = formater.parse(dateStr);
        } catch (Exception e) {
            // log.error(e);
            d = null;
        }
        return d;
    }

    /**
     * 获取当前时间到格林威治时间的秒数
     * UTC：格林威治时间1970年01月01日00时00分00秒（UTC+8北京时间1970年01月01日08时00分00秒）
     * @return
     */
    public static long getSecond(){
        Calendar calendar = Calendar.getInstance();

        calendar.clear();
        Date datetime = getCurrentDate();
        calendar.setTime(datetime);

        TimeZone timeZone = TimeZone.getTimeZone("GMT+08:00");
        calendar.setTimeZone(timeZone);
        return calendar.getTimeInMillis()/1000;
    }

    /**
     * 计算两个时间相差的秒数
     * @param startDate
     * @param endDate
     * @return
     */
    public static int calLastedTime(Date startDate, Date endDate) {
        long a = endDate.getTime();
        long b = startDate.getTime();
        int c = (int)((a - b) / 1000);
        return c;
    }


    public  static void main(String[] args){

        System.out.print(getTime());
    }

}
