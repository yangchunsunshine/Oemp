package com.wb.framework.commonUtil.calendar;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public final class CalendarUtil
{
    public static final int Equals = 0;
    
    public static final int LessThan = -1;
    
    public static final int GreaterThan = 1;
    
    public static final String[] weekDayNames = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
    
    public static final String[] shortWeekDayNames = {"日", "一", "二", "三", "四", "五", "六"};
    
    public static final Date MAX_VALUE = new Date(32503651200000L);
    
    public static final Date MIN_VALUE = new Date(-62167420800000L);
    
    public static int compareDate(Calendar d1, Calendar d2)
    {
        if ((d1 == null) && (d2 == null))
            return 0;
        if ((d1 == null) && (d2 != null))
            return -1;
        if ((d1 != null) && (d2 == null))
            return 1;
        return compareDate((d1 == null) ? null : d1.getTime(), d2.getTime());
    }
    
    public static int compareDate(Date d1, Calendar d2)
    {
        if ((d1 == null) && (d2 == null))
            return 0;
        if ((d1 == null) && (d2 != null))
            return -1;
        if ((d1 != null) && (d2 == null))
            return 1;
        return compareDate(d1, d2.getTime());
    }
    
    public static int compareDate(Calendar d1, Date d2)
    {
        if ((d1 == null) && (d2 == null))
            return 0;
        if ((d1 == null) && (d2 != null))
            return -1;
        if ((d1 != null) && (d2 == null))
            return 1;
        return compareDate((d1 == null) ? null : d1.getTime(), d2);
    }
    
    public static int compareDate(Date d1, Date d2)
    {
        if ((d1 == null) && (d2 == null))
            return 0;
        if ((d1 == null) && (d2 != null))
            return -1;
        if ((d1 != null) && (d2 == null))
            return 1;
        long l1 = (d1 == null) ? -9223372036854775808L : d1.getTime();
        long l2 = (d2 == null) ? -9223372036854775808L : d2.getTime();
        if (l1 == l2)
            return 0;
        if (l1 < l2)
            return -1;
        return 1;
    }
    
    public static Date now()
    {
        return Calendar.getInstance().getTime();
    }
    
    public static Calendar nowCalendar()
    {
        return Calendar.getInstance();
    }
    
    public static String format(Date date, String formatString)
    {
        if (date == null)
            return "";
        SimpleDateFormat frmt = new SimpleDateFormat(formatString);
        return frmt.format(date);
    }
    
    public static String format(Calendar calendar, String formatString)
    {
        if (calendar == null)
            return "";
        SimpleDateFormat frmt = new SimpleDateFormat(formatString);
        Date date = calendar.getTime();
        return frmt.format(date);
    }
    
    public static String[] getWeekNames()
    {
        return weekDayNames;
    }
    
    public static String[] getShortWeekNames()
    {
        return shortWeekDayNames;
    }
    
    public static String getWeekName(Calendar date)
    {
        if (date == null)
            return "";
        int dayOfWeek = date.get(7) - 1;
        if (dayOfWeek < 0)
            dayOfWeek = 0;
        
        return weekDayNames[dayOfWeek];
    }
    
    public static String getShortWeekName(Calendar date)
    {
        if (date == null)
            return "";
        int dayOfWeek = date.get(7) - 1;
        if (dayOfWeek < 0)
            dayOfWeek = 0;
        
        return shortWeekDayNames[dayOfWeek];
    }
    
    public static int getYear(Calendar calendar)
    {
        return calendar.get(1);
    }
    
    public static int getMonth(Calendar calendar)
    {
        return calendar.get(2);
    }
    
    public static int getDay(Calendar calendar)
    {
        return calendar.get(5);
    }
    
    public int getWeekOfMonth(Calendar calendar)
    {
        return calendar.get(4);
    }
    
    public static int getWeekOfYear(Calendar calendar)
    {
        return calendar.get(3);
    }
    
    public static int getDayOfYear(Calendar calendar)
    {
        return calendar.get(6);
    }
    
    public static int getDayOfWeek(Calendar calendar)
    {
        return calendar.get(7);
    }
    
    public static int getHour(Calendar calendar)
    {
        return calendar.get(11);
    }
    
    public static int getMinute(Calendar calendar)
    {
        return calendar.get(12);
    }
    
    public static int getSecond(Calendar calendar)
    {
        return calendar.get(13);
    }
    
    public static int getMillisecond(Calendar calendar)
    {
        return calendar.get(14);
    }
    
    public static boolean after(Calendar calendar, Calendar other)
    {
        return calendar.getTime().after(other.getTime());
    }
    
    public static Calendar addYears(Calendar calendar, int amount)
    {
        return add(calendar, 1, amount);
    }
    
    public static Calendar addMonths(Calendar calendar, int amount)
    {
        return add(calendar, 2, amount);
    }
    
    public static Calendar addWeeks(Calendar calendar, int amount)
    {
        return add(calendar, 3, amount);
    }
    
    public static Calendar addDays(Calendar calendar, int amount)
    {
        return add(calendar, 5, amount);
    }
    
    public static Calendar addHours(Calendar calendar, int amount)
    {
        return add(calendar, 11, amount);
    }
    
    public static Calendar addMinutes(Calendar calendar, int amount)
    {
        return add(calendar, 12, amount);
    }
    
    public static Calendar addSeconds(Calendar calendar, int amount)
    {
        return add(calendar, 13, amount);
    }
    
    public Calendar addMilliseconds(Calendar calendar, int amount)
    {
        return add(calendar, 14, amount);
    }
    
    public static Calendar add(Calendar calendar, int calendarField, int amount)
    {
        Calendar c = Calendar.getInstance();
        c.setTime(calendar.getTime());
        c.add(calendarField, amount);
        return c;
    }
    
    public static Date newInstance(int year, int month, int day)
    {
        return newInstance(year, month, day, 0, 0, 0, 0);
    }
    
    public static Date newInstance(int year, int month, int day, int hour, int minute)
    {
        return newInstance(year, month, day, hour, minute, 0, 0);
    }
    
    public static Date newInstance(int year, int month, int day, int hour, int minute, int second)
    {
        return newInstance(year, month, day, hour, minute, second, 0);
    }
    
    public static Date newInstance(int year, int month, int day, int hour, int minute, int second, int misecond)
    {
        Calendar c = Calendar.getInstance();
        c.set(year, month, day, hour, minute, second);
        c.set(14, misecond);
        return c.getTime();
    }
    
    public static Calendar newInstanceCalendar(int year, int month, int day)
    {
        return newInstanceCalendar(year, month, day, 0, 0, 0, 0);
    }
    
    public static Calendar newInstanceCalendar(int year, int month, int day, int hour, int minute)
    {
        return newInstanceCalendar(year, month, day, hour, minute, 0, 0);
    }
    
    public static Calendar newInstanceCalendar(int year, int month, int day, int hour, int minute, int second)
    {
        return newInstanceCalendar(year, month, day, hour, minute, second, 0);
    }
    
    public static Calendar newInstanceCalendar(int year, int month, int day, int hour, int minute, int second, int millisecond)
    {
        Calendar c = Calendar.getInstance();
        c.set(year, month, day, hour, minute, second);
        c.set(14, millisecond);
        return c;
    }
}