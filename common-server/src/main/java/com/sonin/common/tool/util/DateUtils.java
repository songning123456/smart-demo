package com.sonin.common.tool.util;

import org.springframework.util.StringUtils;

import java.beans.PropertyEditorSupport;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author sonin
 * @date 2022/1/18 17:06
 */
public class DateUtils extends PropertyEditorSupport {

    public static final SimpleDateFormat date_sdf = new SimpleDateFormat("yyyy-MM-dd");
    public static final SimpleDateFormat yyyyMM = new SimpleDateFormat("yyyy-MM");
    public static final SimpleDateFormat yyyyMMdd = new SimpleDateFormat("yyyyMMdd");
    public static final SimpleDateFormat date_sdf_wz = new SimpleDateFormat("yyyy年MM月dd日");
    public static final SimpleDateFormat time_sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    public static final SimpleDateFormat yyyymmddhhmmss = new SimpleDateFormat("yyyyMMddHHmmss");
    public static final SimpleDateFormat short_time_sdf = new SimpleDateFormat("HH:mm");
    public static final SimpleDateFormat datetimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final long DAY_IN_MILLIS = 86400000L;
    private static final long HOUR_IN_MILLIS = 3600000L;
    private static final long MINUTE_IN_MILLIS = 60000L;
    private static final long SECOND_IN_MILLIS = 1000L;

    public DateUtils() {
    }

    private static SimpleDateFormat getSDFormat(String pattern) {
        return new SimpleDateFormat(pattern);
    }

    public static Calendar getCalendar() {
        return Calendar.getInstance();
    }

    public static Calendar getCalendar(long millis) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date(millis));
        return cal;
    }

    public static Date getDate() {
        return new Date();
    }

    public static Date getDate(long millis) {
        return new Date(millis);
    }

    public static String timestamptoStr(Timestamp time) {
        Date date = null;
        if (time != null) {
            new Date(time.getTime());
        }

        return date2Str(date_sdf);
    }

    public static Timestamp str2Timestamp(String str) {
        Date date = str2Date(str, date_sdf);
        return new Timestamp(date.getTime());
    }

    public static Date str2Date(String str, SimpleDateFormat sdf) {
        if (str != null && !"".equals(str)) {
            Date date = null;

            try {
                date = sdf.parse(str);
                return date;
            } catch (ParseException var4) {
                var4.printStackTrace();
                return null;
            }
        } else {
            return null;
        }
    }

    public static String date2Str(SimpleDateFormat date_sdf) {
        Date date = getDate();
        return date == null ? null : date_sdf.format(date);
    }

    public static String dateformat(String date, String format) {
        SimpleDateFormat sformat = new SimpleDateFormat(format);
        Date _date = null;

        try {
            _date = sformat.parse(date);
        } catch (ParseException var5) {
            var5.printStackTrace();
        }

        return sformat.format(_date);
    }

    public static String date2Str(Date date, SimpleDateFormat date_sdf) {
        return date == null ? null : date_sdf.format(date);
    }

    public static String getDate(String format) {
        Date date = new Date();
        if (date == null) {
            return null;
        } else {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return sdf.format(date);
        }
    }

    public static Timestamp getTimestamp(long millis) {
        return new Timestamp(millis);
    }

    public static Timestamp getTimestamp(String time) {
        return new Timestamp(Long.parseLong(time));
    }

    public static Timestamp getTimestamp() {
        return new Timestamp(System.currentTimeMillis());
    }

    public static String now() {
        return datetimeFormat.format(getCalendar().getTime());
    }

    public static Timestamp getTimestamp(Date date) {
        return new Timestamp(date.getTime());
    }

    public static Timestamp getCalendarTimestamp(Calendar cal) {
        return new Timestamp(cal.getTime().getTime());
    }

    public static Timestamp gettimestamp() {
        Date dt = new Date();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String nowTime = df.format(dt);
        Timestamp buydate = Timestamp.valueOf(nowTime);
        return buydate;
    }

    public static long getMillis() {
        return System.currentTimeMillis();
    }

    public static long getMillis(Calendar cal) {
        return cal.getTime().getTime();
    }

    public static long getMillis(Date date) {
        return date.getTime();
    }

    public static long getMillis(Timestamp ts) {
        return ts.getTime();
    }

    public static int getDayCountOfMonth(String yearMonth) {
        Calendar calendar = new GregorianCalendar();
        Date date1 = null;

        try {
            date1 = yyyyMM.parse(yearMonth);
        } catch (ParseException var4) {
            var4.printStackTrace();
        }

        calendar.setTime(date1);
        return calendar.getActualMaximum(5);
    }

    public static String getTodayOrMonthDate(String type) {
        return type.equals("day") ? date_sdf.format(getCalendar().getTime()) : yyyyMM.format(getCalendar().getTime());
    }

    public static String getMonthFirstOrLastDay(String date, String type) {
        return type.equals("start") ? date.substring(0, 7) + "-01" : getLastDayOfMonth(date);
    }

    public static String formatAddTime(String src, String pattern, int calendarType, int amount) throws ParseException {
        Calendar cal = parseCalendar(src, pattern);
        cal.add(calendarType, amount);
        return formatDate(cal, pattern);
    }

    public static String format(Date date, String pattern) {
        SimpleDateFormat sd = new SimpleDateFormat(pattern);
        return sd.format(date);
    }

    public static Date parse(String date, String pattern) throws ParseException {
        SimpleDateFormat sd = new SimpleDateFormat(pattern);

        try {
            Date date1 = sd.parse(date);
            return date1;
        } catch (ParseException var4) {
            throw var4;
        }
    }

    public static String getDateFirstTime(String dateStr, String type) throws ParseException {
        String format = "";
        Date date = null;
        Calendar ca = Calendar.getInstance();
        if ("year".equals(type)) {
            date = parse(dateStr, "yyyy");
            ca.setTime(date);
            ca.set(2, 0);
            ca.set(5, 1);
            ca.set(11, 0);
            ca.set(12, 0);
            ca.set(13, 0);
        } else if ("month".equals(type)) {
            date = parse(dateStr, "yyyy-MM");
            ca.setTime(date);
            ca.set(5, 1);
            ca.set(11, 0);
            ca.set(12, 0);
            ca.set(13, 0);
        } else if ("day".equals(type)) {
            date = parse(dateStr, "yyyy-MM-dd");
            ca.setTime(date);
            ca.set(11, 0);
            ca.set(12, 0);
            ca.set(13, 0);
        } else if ("hour".equals(type)) {
            date = parse(dateStr, "yyyy-MM-dd HH");
            ca.setTime(date);
            ca.set(12, 0);
            ca.set(13, 0);
        }

        format = format(ca.getTime(), "yyyy-MM-dd HH:mm:ss");
        return format;
    }

    public static String getDateLastTime(String dateStr, String type) throws ParseException {
        String format = "";
        Date date = null;
        Calendar ca = Calendar.getInstance();
        if ("year".equals(type)) {
            date = parse(dateStr, "yyyy");
            ca.setTime(date);
            ca.set(2, ca.getActualMaximum(2));
            ca.set(5, ca.getActualMaximum(5));
            ca.set(11, ca.getActualMaximum(11));
            ca.set(12, ca.getActualMaximum(12));
            ca.set(13, ca.getActualMaximum(13));
        } else if ("month".equals(type)) {
            date = parse(dateStr, "yyyy-MM");
            ca.setTime(date);
            ca.set(5, ca.getActualMaximum(5));
            ca.set(11, ca.getActualMaximum(11));
            ca.set(12, ca.getActualMaximum(12));
            ca.set(13, ca.getActualMaximum(13));
        } else if ("day".equals(type)) {
            date = parse(dateStr, "yyyy-MM-dd");
            ca.setTime(date);
            ca.set(11, ca.getActualMaximum(11));
            ca.set(12, ca.getActualMaximum(12));
            ca.set(13, ca.getActualMaximum(13));
        } else if ("hour".equals(type)) {
            date = parse(dateStr, "yyyy-MM-dd HH");
            ca.setTime(date);
            ca.set(12, ca.getActualMaximum(12));
            ca.set(13, ca.getActualMaximum(13));
        }

        format = format(ca.getTime(), "yyyy-MM-dd HH:mm:ss");
        return format;
    }

    public static List<String> sliceUpDateRange(String startDate, String endDate, String type) {
        ArrayList rs = new ArrayList();

        try {
            int dt = 5;
            String pattern = "yyyy-MM-dd";
            if ("year".equals(type)) {
                pattern = "yyyy-MM";
                dt = 2;
            } else if ("month".equals(type)) {
                pattern = "yyyy-MM-dd";
                dt = 5;
            } else if ("day".equals(type)) {
                pattern = "yyyy-MM-dd HH:mm:ss";
                dt = 10;
            }

            Calendar sc = Calendar.getInstance();
            Calendar ec = Calendar.getInstance();
            sc.setTime(parse(startDate, pattern));
            ec.setTime(parse(endDate, pattern));

            while (sc.compareTo(ec) < 1) {
                rs.add(format(sc.getTime(), pattern));
                sc.add(dt, 1);
            }
        } catch (ParseException var8) {
            var8.printStackTrace();
        }

        return rs;
    }

    public static boolean isEffectiveDate(Date nowTime, Date startTime, Date endTime) {
        if (nowTime.getTime() != startTime.getTime() && nowTime.getTime() != endTime.getTime()) {
            Calendar date = Calendar.getInstance();
            date.setTime(nowTime);
            Calendar begin = Calendar.getInstance();
            begin.setTime(startTime);
            Calendar end = Calendar.getInstance();
            end.setTime(endTime);
            return date.after(begin) && date.before(end);
        } else {
            return true;
        }
    }

    public static String formatDate() {
        return date_sdf.format(getCalendar().getTime());
    }

    public static String formatDateTime() {
        return datetimeFormat.format(getCalendar().getTime());
    }

    public static String getDataString(SimpleDateFormat formatstr) {
        return formatstr.format(getCalendar().getTime());
    }

    public static String formatDate(Calendar cal) {
        return date_sdf.format(cal.getTime());
    }

    public static String formatDate(Date date) {
        return date_sdf.format(date);
    }

    public static String formatDate(long millis) {
        return date_sdf.format(new Date(millis));
    }

    public static String formatDate(String pattern) {
        return getSDFormat(pattern).format(getCalendar().getTime());
    }

    public static String formatDate(Calendar cal, String pattern) {
        return getSDFormat(pattern).format(cal.getTime());
    }

    public static String formatDate(Date date, String pattern) {
        return getSDFormat(pattern).format(date);
    }

    public static String formatTime() {
        return time_sdf.format(getCalendar().getTime());
    }

    public static String formatTime(long millis) {
        return time_sdf.format(new Date(millis));
    }

    public static String formatSecondTime(long millis) {
        return datetimeFormat.format(new Date(millis));
    }

    public static String formatTime(Calendar cal) {
        return time_sdf.format(cal.getTime());
    }

    public static String formatTime(Date date) {
        return time_sdf.format(date);
    }

    public static String formatShortTime() {
        return short_time_sdf.format(getCalendar().getTime());
    }

    public static String formatShortTime(long millis) {
        return short_time_sdf.format(new Date(millis));
    }

    public static String formatShortTime(Calendar cal) {
        return short_time_sdf.format(cal.getTime());
    }

    public static String formatShortTime(Date date) {
        return short_time_sdf.format(date);
    }

    public static Date parseDate(String src, String pattern) throws ParseException {
        return getSDFormat(pattern).parse(src);
    }

    public static Calendar parseCalendar(String src, String pattern) throws ParseException {
        Date date = parseDate(src, pattern);
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal;
    }

    public static String formatAddDate(String src, String pattern, int amount) throws ParseException {
        Calendar cal = parseCalendar(src, pattern);
        cal.add(5, amount);
        return formatDate(cal);
    }

    public static Timestamp parseTimestamp(String src, String pattern) throws ParseException {
        Date date = parseDate(src, pattern);
        return new Timestamp(date.getTime());
    }

    public static int dateDiff(char flag, Calendar calSrc, Calendar calDes) {
        long millisDiff = getMillis(calSrc) - getMillis(calDes);
        if (flag == 'y') {
            return calSrc.get(1) - calDes.get(1);
        } else if (flag == 'd') {
            return (int) (millisDiff / 86400000L);
        } else if (flag == 'h') {
            return (int) (millisDiff / 3600000L);
        } else if (flag == 'm') {
            return (int) (millisDiff / 60000L);
        } else {
            return flag == 's' ? (int) (millisDiff / 1000L) : 0;
        }
    }

    public void setAsText(String text) throws IllegalArgumentException {
        if (StringUtils.hasText(text)) {
            try {
                if (text.indexOf(":") == -1 && text.length() == 10) {
                    this.setValue(date_sdf.parse(text));
                } else {
                    if (text.indexOf(":") <= 0 || text.length() != 19) {
                        throw new IllegalArgumentException("Could not parse date, date format is error ");
                    }

                    this.setValue(datetimeFormat.parse(text));
                }
            } catch (ParseException var4) {
                IllegalArgumentException iae = new IllegalArgumentException("Could not parse date: " + var4.getMessage());
                iae.initCause(var4);
                throw iae;
            }
        } else {
            this.setValue((Object) null);
        }

    }

    public static int getYear() {
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(getDate());
        return calendar.get(1);
    }

    public static String getLastDayOfMonth(String yearMonth) {
        int year = Integer.parseInt(yearMonth.split("-")[0]);
        int month = Integer.parseInt(yearMonth.split("-")[1]);
        Calendar cal = Calendar.getInstance();
        cal.set(1, year);
        cal.set(2, month);
        int lastDay = cal.getMinimum(5);
        cal.set(5, lastDay - 1);
        return date_sdf.format(cal.getTime());
    }

    public static long dayDiff(String start, String end) {
        long diff = 0L;

        try {
            long d1 = date_sdf.parse(start).getTime();
            long d2 = date_sdf.parse(end).getTime();
            diff = (d2 - d1) / 86400000L;
            return diff;
        } catch (Exception var8) {
            return 0L;
        }
    }

    public static synchronized long secondDiff(String start, String end) {
        long diff = 0L;

        try {
            long d1 = datetimeFormat.parse(start).getTime();
            long d2 = datetimeFormat.parse(end).getTime();
            diff = (d2 - d1) / 1000L;
            return diff;
        } catch (Exception var8) {
            return 0L;
        }
    }

    public static String getLastYearMonthOfMonth(String yearMonth) {
        int year = Integer.parseInt(yearMonth.split("-")[0]);
        String month = yearMonth.split("-")[1];
        int lastYear = year - 1;
        return String.valueOf(lastYear) + "-" + month;
    }

    public static String getLastMonthOfMonth(String yearMonth) {
        try {
            Date currdate = yyyyMM.parse(yearMonth);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(currdate);
            calendar.set(2, calendar.get(2) - 1);
            return yyyyMM.format(calendar.getTime());
        } catch (ParseException var3) {
            return "";
        }
    }

    public static String getPreviousYear(String yearMonth) {
        int year = Integer.parseInt(yearMonth.split("-")[0]);
        int month = Integer.parseInt(yearMonth.split("-")[1]);
        Calendar cal = Calendar.getInstance();
        cal.set(1, year - 1);
        cal.set(2, month - 1);
        return yyyyMM.format(cal.getTime());
    }

    public static String getLastDay() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(5, -1);
        return date_sdf.format(cal.getTime());
    }

    public static String getYesterday() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(5, -1);
        return date_sdf.format(cal.getTime());
    }

    public static String getBeforeYesterday() throws ParseException {
        return formatAddTime(getYesterday(), "yyyy-MM-dd", 5, -1);
    }

}
