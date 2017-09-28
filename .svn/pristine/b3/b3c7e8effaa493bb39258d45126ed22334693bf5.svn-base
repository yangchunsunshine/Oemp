package com.wb.framework.commonUtil.calendar;

import java.util.Calendar;
import java.util.Date;

public class DateNormalizer
{
    NormalizeType type;
    
    public DateNormalizer(NormalizeType type)
    {
        this.type = type;
    }
    
    public DateNormalizer()
    {
        this.type = NormalizeType.DAY;
    }
    
    public NormalizeType getType()
    {
        return this.type;
    }
    
    public void setType(NormalizeType type)
    {
        this.type = type;
    }
    
    public static Calendar normalize(Calendar org, NormalizeType type)
    {
        if (org == null)
            return null;
        Calendar cal = Calendar.getInstance();
        cal.setTime(org.getTime());
        int week;
        if ((type == NormalizeType.WEEK) || (type == NormalizeType.WEEKEND))
        {
            week = cal.get(7);
            --week;
            if (week > 0)
            {
                cal.add(5, 7 - week);
            }
        }
        else if (type == NormalizeType.WEEKZERO)
        {
            week = cal.get(7);
            --week;
            if (week > 0)
            {
                cal.add(5, -week + 1);
            }
        }
        else if ((type == NormalizeType.MONTH) || (type == NormalizeType.MONTHZERO))
        {
            cal.set(5, 1);
        }
        else if (type == NormalizeType.MONTHEND)
        {
            cal.set(5, 1);
            cal = CalendarUtil.addMonths(cal, 1);
            cal = CalendarUtil.addDays(cal, -1);
        }
        else
        {
            int month;
            if ((type == NormalizeType.SEASON) || (type == NormalizeType.SEASONZERO))
            {
                month = cal.get(2);
                month /= 3;
                month *= 3;
                cal.set(2, month);
                cal.set(5, 1);
            }
            else if (type == NormalizeType.SEASONEND)
            {
                month = cal.get(2);
                month /= 3;
                month *= 3;
                cal.set(2, month);
                cal.set(5, 1);
                cal = CalendarUtil.addMonths(cal, 3);
                cal = CalendarUtil.addDays(cal, -1);
            }
            else if ((type == NormalizeType.HALFYEAR) || (type == NormalizeType.HALFYEARZERO))
            {
                month = cal.get(2);
                month /= 6;
                month *= 6;
                cal.set(2, month);
                cal.set(5, 1);
            }
            else if (type == NormalizeType.HALFYEAREND)
            {
                month = cal.get(2);
                month /= 6;
                month *= 6;
                cal.set(2, month);
                cal.set(5, 1);
                cal = CalendarUtil.addMonths(cal, 6);
                cal = CalendarUtil.addDays(cal, -1);
            }
            else if ((type == NormalizeType.YEAR) || (type == NormalizeType.YEARZERO))
            {
                cal.set(2, 0);
                cal.set(5, 1);
            }
            else if (type == NormalizeType.YEAREND)
            {
                cal.set(2, 0);
                cal.set(5, 1);
                cal = CalendarUtil.addMonths(cal, 12);
                cal = CalendarUtil.addDays(cal, -1);
            }
        }
        
        int tValue = type.getValue();
        if ((tValue >= NormalizeType.DAY.getValue()) && (tValue <= NormalizeType.YEAR.getValue()))
            setMidDay(cal);
        else if ((tValue >= NormalizeType.DAYZERO.getValue()) && (tValue <= NormalizeType.YEARZERO.getValue()))
            setZeroDay(cal);
        else if ((tValue >= NormalizeType.DAYEND.getValue()) && (tValue <= NormalizeType.YEAREND.getValue()))
        {
            setEndDay(cal);
        }
        return cal;
    }
    
    public static Date normalize2(Date org, NormalizeType type)
    {
        if (org == null)
            return null;
        Calendar cal = Calendar.getInstance();
        cal.setTime(org);
        return normalize(cal, type).getTime();
    }
    
    public static Calendar[] splitRange(Calendar org, NormalizeType type)
    {
        if ((org == null) || (type == null))
        {
            return null;
        }
        Calendar[] result = new Calendar[2];
        result[0] = Calendar.getInstance();
        result[1] = Calendar.getInstance();
        if ((type == NormalizeType.DAY) || (type == NormalizeType.DAYZERO) || (type == NormalizeType.DAYEND))
        {
            result[0].setTime(normalize(org, NormalizeType.DAYZERO).getTime());
            result[1].setTime(normalize(org, NormalizeType.DAYEND).getTime());
        }
        else
        {
            Calendar cal;
            if ((type == NormalizeType.WEEK) || (type == NormalizeType.WEEKZERO) || (type == NormalizeType.WEEKEND))
            {
                cal = normalize(org, NormalizeType.WEEKEND);
                result[1].setTime(cal.getTime());
                cal.add(5, -6);
                result[0].setTime(normalize(cal, NormalizeType.DAYZERO).getTime());
            }
            else if ((type == NormalizeType.MONTH) || (type == NormalizeType.MONTHZERO) || (type == NormalizeType.MONTHEND))
            {
                cal = normalize(org, NormalizeType.MONTHZERO);
                result[0].setTime(cal.getTime());
                cal.add(2, 1);
                cal.add(5, -1);
                result[1].setTime(normalize(cal, NormalizeType.DAYEND).getTime());
            }
            else if ((type == NormalizeType.SEASON) || (type == NormalizeType.SEASONZERO) || (type == NormalizeType.SEASONEND))
            {
                cal = normalize(org, NormalizeType.SEASONZERO);
                result[0].setTime(cal.getTime());
                cal.add(2, 3);
                cal.add(5, -1);
                result[1].setTime(normalize(cal, NormalizeType.DAYEND).getTime());
            }
            else if ((type == NormalizeType.HALFYEAR) || (type == NormalizeType.HALFYEARZERO) || (type == NormalizeType.HALFYEAREND))
            {
                cal = normalize(org, NormalizeType.HALFYEARZERO);
                result[0].setTime(cal.getTime());
                cal.add(2, 6);
                cal.add(5, -1);
                result[1].setTime(normalize(cal, NormalizeType.DAYEND).getTime());
            }
            else if ((type == NormalizeType.YEAR) || (type == NormalizeType.YEARZERO) || (type == NormalizeType.YEAREND))
            {
                cal = normalize(org, NormalizeType.YEARZERO);
                result[0].setTime(cal.getTime());
                cal.add(1, 1);
                cal.add(5, -1);
                result[1].setTime(normalize(cal, NormalizeType.DAYEND).getTime());
            }
            else
            {
                return null;
            }
        }
        return result;
    }
    
    public Calendar[] splitRange(Calendar org)
    {
        return splitRange(org, this.type);
    }
    
    public static Date[] splitRange2(Date org, NormalizeType type)
    {
        if ((org == null) || (type == null))
            return null;
        Calendar cal = Calendar.getInstance();
        cal.setTime(org);
        Calendar[] r1 = splitRange(cal, type);
        if (r1 == null)
            return null;
        Date[] result = new Date[r1.length];
        for (int i = 0; i < result.length; ++i)
            result[i] = r1[i].getTime();
        r1 = (Calendar[])null;
        return result;
    }
    
    public Date[] splitRange2(Date org)
    {
        return splitRange2(org, this.type);
    }
    
    public Calendar normalize(Calendar org)
    {
        return normalize(org, this.type);
    }
    
    private static void setMidDay(Calendar day)
    {
        day.set(11, 12);
        day.set(12, 0);
        day.set(13, 0);
        day.set(14, 0);
    }
    
    private static void setZeroDay(Calendar day)
    {
        day.set(11, 0);
        day.set(12, 0);
        day.set(13, 0);
        day.set(14, 0);
    }
    
    private static void setEndDay(Calendar day)
    {
        day.set(11, 23);
        day.set(12, 59);
        day.set(13, 59);
        day.set(14, 996);
    }
    
    public static boolean compareNormalizeType(String normalize, NormalizeType type)
    {
        return ((type.toString().equalsIgnoreCase(normalize)) || (String.valueOf(type.getValue()).equalsIgnoreCase(normalize)));
    }
    
    public static NormalizeType getNormalizeType(String normalize)
    {
        if ((normalize == null) || (normalize.length() == 0))
            return null;
        
        if (compareNormalizeType(normalize, NormalizeType.DAY))
            return NormalizeType.DAY;
        if (compareNormalizeType(normalize, NormalizeType.DAYZERO))
            return NormalizeType.DAYZERO;
        if (compareNormalizeType(normalize, NormalizeType.DAYEND))
            return NormalizeType.DAYEND;
        if (compareNormalizeType(normalize, NormalizeType.MONTH))
            return NormalizeType.MONTH;
        if (compareNormalizeType(normalize, NormalizeType.MONTHZERO))
            return NormalizeType.MONTHZERO;
        if (compareNormalizeType(normalize, NormalizeType.MONTHEND))
            return NormalizeType.MONTHEND;
        if (compareNormalizeType(normalize, NormalizeType.SEASON))
            return NormalizeType.SEASON;
        if (compareNormalizeType(normalize, NormalizeType.SEASONZERO))
            return NormalizeType.SEASONZERO;
        if (compareNormalizeType(normalize, NormalizeType.SEASONEND))
            return NormalizeType.SEASONEND;
        if (compareNormalizeType(normalize, NormalizeType.HALFYEAR))
            return NormalizeType.HALFYEAR;
        if (compareNormalizeType(normalize, NormalizeType.HALFYEARZERO))
            return NormalizeType.HALFYEARZERO;
        if (compareNormalizeType(normalize, NormalizeType.HALFYEAREND))
            return NormalizeType.HALFYEAREND;
        if (compareNormalizeType(normalize, NormalizeType.YEAR))
            return NormalizeType.YEAR;
        if (compareNormalizeType(normalize, NormalizeType.YEARZERO))
            return NormalizeType.YEARZERO;
        if (compareNormalizeType(normalize, NormalizeType.YEAREND))
            return NormalizeType.YEAREND;
        return null;
    }
}