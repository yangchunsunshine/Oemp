package com.wb.framework.commonUtil.calendar;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

/**
 * 
 * 时间类工具
 * 
 * @author 郝洋
 * @version [版本号, 2016-4-17]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class DateUtil
{
    /**
     * 粒度-日
     */
    public static final int GRADULURITY_DAY = 1;
    
    /**
     * 粒度-月
     */
    public static final int GRADULURITY_MONTH = 3;
    
    /**
     * 粒度-年
     */
    public static final int GRADULURITY_YEAR = 6;
    
    /**
     * 根据粒度显示日期(yyyy || yyyy-MM || yyyy-MM-dd)
     * 
     * @param date 日期
     * @param gradulurity 粒度,暂时只支持1(日)、3(月)、6(年)
     * @return String
     */
    public static String display(Date date, int gradulurity)
    {
        if (date == null)
            return "";
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        if (gradulurity == GRADULURITY_DAY)
        {
            return CalendarUtil.format(cal, "yyyy-MM-dd");
        }
        else if (gradulurity == GRADULURITY_MONTH)
        {
            return CalendarUtil.format(cal, "yyyy-MM");
        }
        else if (gradulurity == GRADULURITY_YEAR)
        {
            return CalendarUtil.format(cal, "yyyy");
        }
        return CalendarUtil.format(cal, "yyyy-MM-dd");
    }
    
    /**
     * 得到两个时间之间的间隔天数
     * 
     * @param maxDate
     * @param minDate
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static Integer getDaysDiffOfDate(Date maxDate, Date minDate)
    {
        Date d1 = DateNormalizer.normalize2(maxDate, NormalizeType.DAYZERO);
        Date d2 = DateNormalizer.normalize2(minDate, NormalizeType.DAYZERO);
        TimeSpan ts = new TimeSpan(d2, d1);
        if (ts.getTicks() < 0)
        {
            ts = ts.negate();
        }
        ts = new TimeSpan(ts.getTicks() + 10);
        return Math.abs(ts.getDays());
    }
    
    /**
     * 
     * 得到两个日期的月份差 月份差=（第二年份-第一年份）*12 + 第二月份-第一月份
     * 
     * @param minDate 小的日期
     * @param maxDate 大的日期
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static Integer getMonthDiffOfDate(Date minDate, Date maxDate)
    {
        Calendar minCalendar = Calendar.getInstance();
        minCalendar.setTime(minDate);
        Integer yearOfMinDate = minCalendar.get(Calendar.YEAR);
        Integer monthOfMinDate = minCalendar.get(Calendar.MONTH);
        
        Calendar maxCalendar = Calendar.getInstance();
        maxCalendar.setTime(maxDate);
        Integer yearOfMaxDate = maxCalendar.get(Calendar.YEAR);
        Integer monthOfMaxDate = maxCalendar.get(Calendar.MONTH);
        
        return (yearOfMaxDate - yearOfMinDate) * 12 + monthOfMaxDate - monthOfMinDate;
    }
    
    /**
     * 得到指定日期的下一个月的日期
     * 
     * @param date
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static Date getDateOfNextMonth(Date date)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, 1);
        return calendar.getTime();
    }
    
    /**
     * 得到指定日期的上一个月的日期
     * 
     * @param date 指定日期
     * 
     * @return 返回上一个月的日期
     */
    public static Date getDateOfBeforeMonth(Date date)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, -1);
        return calendar.getTime();
    }
    
    /**
     * 得到某一日期的最后一天 如2009-9-30 23：59：59
     * 
     * @param date 某一日期
     * @return Date 某一日期最后一天
     */
    public static Date getLastDayOfDate(Date date)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, 1);
        calendar.set(Calendar.DATE, 1);
        calendar.add(Calendar.DATE, -1);
        return getDateOfDayEnd(calendar.getTime());
    }
    
    /**
     * 根据时间获得一天的开始日期
     * 
     * @param date
     * @return
     */
    public static Date getDateOfDayStart(Date date)
    {
        Calendar c = Calendar.getInstance();
        if (date == null)
            // 如果传入时间为空,则返回；
            return null;
        // c.setTime(new Date());
        else
            c.setTime(date);
        Calendar sta = DateNormalizer.normalize(c, NormalizeType.DAYZERO);
        return sta.getTime();
    }
    
    /**
     * 
     * 根据传进来的时间得到该日期结束时间
     * 
     * @param date
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static Date getDateOfDayEnd(Date date)
    {
        Calendar c = Calendar.getInstance();
        if (date == null)
        {
            // 如果传入时间为空,则返回；
            return null;
            // c.setTime(d);
        }
        else
        {
            c.setTime(date);
        }
        Calendar sta = DateNormalizer.normalize(c, NormalizeType.DAYEND);
        return sta.getTime();
        
    }
    
    /**
     * 当前日期推延几天
     * 
     * @param date 日期
     * @param days 天数
     * @return
     */
    public static Date afterDay(Date date, int days)
    {
        Calendar c = Calendar.getInstance();
        if (date != null)
            c.setTime(date);
        else
            c.setTime(new Date());
        return CalendarUtil.addDays(c, days).getTime();
    }
    
    /**
     * 当前日期推延几天
     * 
     * @param date 日期
     * @param days 天数
     * @param hours 指定的小时
     * @return
     */
    public static Date afterDay(Date date, int days, int hours)
    {
        Calendar c = Calendar.getInstance();
        if (date != null)
            c.setTime(date);
        else
            c.setTime(new Date());
        c.set(Calendar.HOUR_OF_DAY, hours);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.MILLISECOND, 0);
        return CalendarUtil.addDays(c, days).getTime();
    }
    
    /**
     * 当前日期提前几天
     * 
     * @param date 日期
     * @param days 天数
     * @return
     */
    public static Date beforeDay(Date date, int days)
    {
        return afterDay(date, -days);
    }
    
    /**
     * 当前日期提前几天
     * 
     * @param date 日期
     * @param days 天数
     * @param hours 并制定小时
     * @return
     */
    public static Date beforeDay(Date date, int days, int hours)
    {
        return afterDay(date, -days, hours);
    }
    
    /**
     * 
     * 获取格式化后的时间字符串
     * 
     * @param date 时间
     * @param pattern 格式化模版
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static String getDateString(Date date, String pattern)
    {
        if (date != null)
        {
            SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
            dateFormat.setTimeZone(TimeZone.getTimeZone("GMT+8"));
            String dateString = dateFormat.format(date);
            return dateString;
        }
        else
        {
            return "";
        }
    }
    
    /**
     * 根据传入的日期 返回上月1号
     * 
     * @param date
     * @return
     * @see [类、类#方法、类#成员]
     */
    @SuppressWarnings("deprecation")
    public static Date getFirstDayOfBeforeMonth(Date date)
    {
        int mouth = date.getMonth();
        // 日期变为1号
        Date firstDate = DateNormalizer.normalize2(date, NormalizeType.MONTH);
        firstDate.setMonth(mouth - 1);
        return firstDate;
    }
    
    /**
     * 根据传入时间,得到当月所有天数
     * 
     * @param date
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static int getCountOfMonth(Date date)
    {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.getActualMaximum(Calendar.DAY_OF_MONTH);
        
    }
    
    /**
     * 
     * 日期字符串转换为Date类型
     * 
     * @param str 日期字符串 格式:"yyyy-MM-dd"
     * 
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static Date stringToDate(String str)
    {
        String[] strArr = str.split("-");
        if (str == null || "".equals(str))
        {
            return null;
        }
        
        if (strArr.length != 3)
        {
            return new Date();
        }
        Integer year = Integer.valueOf(strArr[0]);
        Integer month = Integer.valueOf(strArr[1]);
        Integer day = Integer.valueOf(strArr[2]);
        Calendar gc = Calendar.getInstance();
        gc.set(year, month - 1, day);
        return gc.getTime();
    }
    
    /**
     * @Title: getMonthOfSettled
     * @Description: 获取月份
     * @param @return
     * @author 王磊
     * @return int 返回类型
     * @throws
     */
    public static int getMonthOfSettled(int year, int month)
    {
        Calendar cal = Calendar.getInstance();
        cal.clear();
        // 设置年份
        cal.set(Calendar.YEAR, year);
        // 设置月份
        cal.set(Calendar.MONTH, month - 1);
        // 获取某月最大天数
        return cal.get(Calendar.MONTH) + 1;
    }
    
    /**
     * @Title: getYearOfSettled
     * @Description: 获取年份
     * @param @return
     * @author 王磊
     * @return int 返回类型
     * @throws
     */
    public static int getYearOfSettled(int year, int month)
    {
        Calendar cal = Calendar.getInstance();
        cal.clear();
        // 设置年份
        cal.set(Calendar.YEAR, year);
        // 设置月份
        cal.set(Calendar.MONTH, month - 1);
        // 获取某月最大天数
        return cal.get(Calendar.YEAR);
    }
    
    /**
     * @Title: getLastDateOfMonth
     * @Description: TODO(这里用一句话描述这个方法的作用)
     * @param @return
     * @author 王磊
     * @return Date 返回类型
     * @throws
     */
    public static String getLastDateToString()
    {
        Calendar cal = Calendar.getInstance();
        int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        cal.set(Calendar.DAY_OF_MONTH, lastDay);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(cal.getTime());
    }
    
    /**
     * @Title: getFirstDateOfMonth
     * @Description: TODO(这里用一句话描述这个方法的作用)
     * @param @return
     * @author 王磊
     * @return Date 返回类型
     * @throws
     */
    public static String getFirstDateToString()
    {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH, 1);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(cal.getTime());
    }
    
    /**
     * @Title: getLastDateOfMonth
     * @Description: TODO(这里用一句话描述这个方法的作用)
     * @param @param date
     * @param @return
     * @author 王磊
     * @return Date 返回类型
     * @throws
     */
    public static String getLastDateToString(Date date)
    {
        Calendar cal = Calendar.getInstance();
        cal.clear();
        cal.setTime(date);
        int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        cal.set(Calendar.DAY_OF_MONTH, lastDay);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(cal.getTime());
    }
    
    /**
     * @Title: getFirstDateOfMonth
     * @Description: TODO(这里用一句话描述这个方法的作用)
     * @param @param date
     * @param @return
     * @author 王磊
     * @return Date 返回类型
     * @throws
     */
    public static String getFirstDateToString(Date date)
    {
        Calendar cal = Calendar.getInstance();
        cal.clear();
        cal.setTime(date);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(cal.getTime());
    }
    
    /**
     * @Title: getLastDateToString
     * @Description: 获取最后一日期
     * @param @param year
     * @param @param month
     * @param @return
     * @author 王磊
     * @return String 返回类型
     * @throws
     */
    public static String getLastDateToString(int year, int month)
    {
        Calendar cal = Calendar.getInstance();
        cal.clear();
        cal.set(Calendar.YEAR, year);
        // 设置月份
        cal.set(Calendar.MONTH, month - 1);
        // 获取某月最大天数
        int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        // 设置日历中月份的最大天数
        cal.set(Calendar.DAY_OF_MONTH, lastDay);
        // 格式化日期
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(cal.getTime());
    }
    
    /**
     * @Title: getFirstDateToString
     * @Description: 获取第一天日期
     * @param @param year
     * @param @param month
     * @param @return
     * @author 王磊
     * @return String 返回类型
     * @throws
     */
    public static String getFirstDateToString(int year, int month)
    {
        Calendar cal = Calendar.getInstance();
        cal.clear();
        // 设置年份
        cal.set(Calendar.YEAR, year);
        // 设置月份
        cal.set(Calendar.MONTH, month - 1);
        // 获取某月最大天数
        cal.set(Calendar.DAY_OF_MONTH, 1);
        // 格式化日期
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(cal.getTime());
    }
    
    /**
     * @Title: getCurDateTime
     * @Description: TODO(描述此方法作用)
     * @param @return
     * @author 王磊
     * @return Date 返回类型
     * @throws
     */
    public static Date getCurDateTime()
    {
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try
        {
            return sdf2.parse(sdf2.format(new Date()));
        }
        catch (ParseException e)
        {
            return null;
        }
    }
    
    /**
     * @Title: getCurDateTimeForString
     * @Description: TODO(描述此方法作用)
     * @param @return
     * @author 王磊
     * @return String 返回类型
     * @throws
     */
    public static String getCurDateTimeForString()
    {
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf2.format(new Date());
    }
    
    /**
     * @Title: getNOByTime
     * @Description: TODO(描述此方法作用)
     * @param @return
     * @author 王磊
     * @return Date 返回类型
     * @throws
     */
    public static String getNOByTime()
    {
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        return sdf2.format(new Date());
    }
    
    /**
     * @Title: convertCurDateTime
     * @Description: TODO(描述此方法作用)
     * @param @param dateTime
     * @param @return
     * @author 王磊
     * @return String 返回类型
     * @throws
     */
    public static String convertCurDateTime(Object dateTime)
    {
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf2.format(dateTime);
    }
    
    /**
     * @Title: getDateStrOfMobile
     * @Description: 凭证期间用方法
     * @param @param date
     * @param @param flag
     * @param @return
     * @author 王磊
     * @return String 返回类型
     * @throws
     */
    public static String getDateStrOfMobile(String date, boolean flag)
    {
        String year = date.substring(0, 4);
        String month = date.substring(5, 7);
        Calendar cal = Calendar.getInstance();
        cal.clear();
        cal.set(Calendar.YEAR, Integer.parseInt(year));
        if (flag)
        {
            cal.set(Calendar.MONTH, Integer.parseInt(month));
        }
        else
        {
            cal.set(Calendar.MONTH, Integer.parseInt(month) - 1);
        }
        cal.set(Calendar.DAY_OF_MONTH, 1);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(cal.getTime());
    }
    
    /**
     * @Title: getLastDateStrCompareToCurDate
     * @Description: 获取最后一日期
     * @param @param year
     * @param @param month
     * @param @return
     * @author 王磊
     * @return String 返回类型
     * @throws
     */
    public static String getLastDateStrCompareToCurDate(int year, int month)
    {
        Calendar cal = Calendar.getInstance();
        cal.clear();
        cal.set(Calendar.YEAR, year);
        // 设置月份
        cal.set(Calendar.MONTH, month - 1);
        // 获取某月最大天数
        int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        // 设置日历中月份的最大天数
        cal.set(Calendar.DAY_OF_MONTH, lastDay);
        // 格式化日期
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        
        Calendar c = Calendar.getInstance();
        c.clear();
        c.setTime(new Date());
        if (c.compareTo(cal) < 0)
        {
            return sdf.format(c.getTime());
        }
        else
        {
            return sdf.format(cal.getTime());
        }
    }
    
    /**
     * @Title: getCurYearAndMonthToString
     * @Description: TODO(这里用一句话描述这个方法的作用)
     * @param @return
     * @author 王磊
     * @return Date 返回类型
     * @throws
     */
    public static String getCurYearAndMonthToString()
    {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        return sdf.format(cal.getTime());
    }
    
    /**
     * @Title: getPrevTaxDate
     * @Description: TODO(这里用一句话描述这个方法的作用)
     * @param @return
     * @author 王磊
     * @return String 返回类型
     * @throws
     */
    public static String getCurYearFirstDate()
    {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.MONTH, 0);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        return sdf.format(cal.getTime());
    }
    
    /**
     * @Title: getPrevTaxDate
     * @Description: TODO(这里用一句话描述这个方法的作用)
     * @param @return
     * @author 王磊
     * @return String 返回类型
     * @throws
     */
    public static String getPrevTaxDate()
    {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.MONTH, cal.get(Calendar.MONTH));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        return sdf.format(cal.getTime());
    }
    
    /**
     * @Title: getNoticTime
     * @Description: TODO(这里用一句话描述这个方法的作用)
     * @param @param date
     * @param @return
     * @author 王磊
     * @return String 返回类型
     * @throws
     */
    public static String getNoticTime(Date date)
    {
        Calendar c = Calendar.getInstance();
        c.clear();
        c.setTime(date);
        c.set(Calendar.MONTH, c.get(Calendar.MONTH) + 1);
        return c.get(Calendar.YEAR) + "年" + (c.get(Calendar.MONTH) + 1) + "月";
    }
    
    /**
     * @Title: isNeedNotic
     * @Description: TODO(这里用一句话描述这个方法的作用)
     * @param @param date
     * @param @param date2
     * @param @param rate
     * @param @return
     * @author 王磊
     * @return boolean 返回类型
     * @throws
     */
    public static boolean isNeedNotice(Date date, Date date2, Integer rate)
    {
        if (date2 == null)
        {
            return true;
        }
        else
        {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            try
            {
                String dateStr = sdf.format(date);
                String dateStr2 = sdf.format(date2);
                Date dateParse = sdf.parse(dateStr);
                Date dateParse2 = sdf.parse(dateStr2);
                if (rate.equals(0))
                {
                    if (dateParse.compareTo(dateParse2) == 0)
                    {
                        return true;
                    }
                }
                else if (rate.equals(1))
                {
                    long between = (dateParse2.getTime() - dateParse.getTime()) / (1000 * 24 * 3600);
                    if (between == 0 || between == 7 || between == 14 || between == 21)
                    {
                        return true;
                    }
                }
            }
            catch (ParseException e)
            {
                e.printStackTrace();
            }
            return false;
        }
    }
    
    /**
     * @Title: isSettled
     * @Description: TODO(这里用一句话描述这个方法的作用)
     * @param @param year
     * @param @param month
     * @param @param period
     * @param @return
     * @author 王磊
     * @return boolean 返回类型
     * @throws
     */
    public static boolean isSettled(int year, int month, String period)
    {
        Calendar cal = Calendar.getInstance();
        cal.clear();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        
        String[] date = period.split("-");
        Calendar cal2 = Calendar.getInstance();
        cal2.clear();
        cal2.set(Calendar.YEAR, Integer.parseInt(date[0]));
        cal2.set(Calendar.MONTH, (Integer.parseInt(date[1]) - 1));
        cal2.set(Calendar.DAY_OF_MONTH, 1);
        // 比较大小
        if (cal2.getTime().getTime() >= cal.getTime().getTime())
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    
    /**
     * @Title: getMonthArray
     * @Description: TODO(这里用一句话描述这个方法的作用)
     * @param @param period1
     * @param @param period2
     * @param @return
     * @author 王磊
     * @return List<String> 返回类型
     * @throws
     */
    public static List<String> getMonthArray(String period1, String period2)
    {
        List<String> list = new ArrayList<String>();
        String[] date1 = period1.split("-");
        Calendar cal1 = Calendar.getInstance();
        cal1.clear();
        cal1.set(Calendar.YEAR, Integer.parseInt(date1[0]));
        cal1.set(Calendar.MONTH, (Integer.parseInt(date1[1]) - 1));
        cal1.set(Calendar.DAY_OF_MONTH, 1);
        
        String[] date2 = period2.split("-");
        Calendar cal2 = Calendar.getInstance();
        cal2.clear();
        cal2.set(Calendar.YEAR, Integer.parseInt(date2[0]));
        cal2.set(Calendar.MONTH, (Integer.parseInt(date2[1]) - 1));
        cal2.set(Calendar.DAY_OF_MONTH, 1);
        // 比较大小
        if (cal2.getTime().getTime() > cal1.getTime().getTime())
        {
            Integer startMonth = Integer.parseInt(date1[1]) - 1;
            while (isSettled(Integer.parseInt(date1[0]), startMonth, period2))
            {
                startMonth++;
                list.add(getCurYearAndMonthString(Integer.parseInt(date1[0]), startMonth));
            }
        }
        else if (cal2.getTime().getTime() == cal1.getTime().getTime())
        {
            list.add(period1);
        }
        return list;
    }
    
    /**
     * @Title: getCurYearAndMonthString
     * @Description: TODO(这里用一句话描述这个方法的作用)
     * @param @param year
     * @param @param month
     * @param @return
     * @author 王磊
     * @return String 返回类型
     * @throws
     */
    public static String getCurYearAndMonthString(Integer year, Integer month)
    {
        Calendar cal = Calendar.getInstance();
        cal.clear();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month - 1);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        return sdf.format(cal.getTime());
    }
    
    /**
     * @Title: getFirstDateForCompareTo
     * @Description: TODO(这里用一句话描述这个方法的作用)
     * @param @param period
     * @param @return
     * @author 王磊
     * @return Date 返回类型
     * @throws
     */
    public static Date getFirstDateForCompareTo(String period)
    {
        String[] date = period.split("-");
        Calendar cal = Calendar.getInstance();
        cal.clear();
        cal.set(Calendar.YEAR, Integer.parseInt(date[0]));
        cal.set(Calendar.MONTH, Integer.parseInt(date[1]) - 1);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        return cal.getTime();
    }
    
    /**
     * @Title: getFirstDateForCompareTo2
     * @Description: TODO(这里用一句话描述这个方法的作用)
     * @param @param period
     * @param @return
     * @author 王磊
     * @return Date 返回类型
     * @throws
     */
    public static Date getFirstDateForCompareTo2(String period)
    {
        String[] date = period.split("-");
        Calendar cal = Calendar.getInstance();
        cal.clear();
        cal.set(Calendar.YEAR, Integer.parseInt(date[0]));
        cal.set(Calendar.MONTH, Integer.parseInt(date[1]) - 1);
        int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        cal.set(Calendar.DAY_OF_MONTH, lastDay);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        return cal.getTime();
    }
    
    /**
     * @Title: getFirstDateForCompareTo3
     * @Description: TODO(这里用一句话描述这个方法的作用)
     * @param @param period
     * @param @return
     * @author 王磊
     * @return Date 返回类型
     * @throws
     */
    public static Date getFirstDateForCompareTo3(String period)
    {
        String[] date = period.split("-");
        Calendar cal = Calendar.getInstance();
        cal.clear();
        cal.set(Calendar.YEAR, Integer.parseInt(date[0]));
        cal.set(Calendar.MONTH, Integer.parseInt(date[1]) - 2);
        int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        cal.set(Calendar.DAY_OF_MONTH, lastDay);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        return cal.getTime();
    }
    
    /**
     * @Title: getLastDateForCompareTo
     * @Description: TODO(这里用一句话描述这个方法的作用)
     * @param @param period
     * @param @return
     * @author 王磊
     * @return Date 返回类型
     * @throws
     */
    public static Date getLastDateForCompareTo(String period)
    {
        String[] date = period.split("-");
        Calendar cal = Calendar.getInstance();
        cal.clear();
        cal.set(Calendar.YEAR, Integer.parseInt(date[0]));
        cal.set(Calendar.MONTH, Integer.parseInt(date[1]));
        cal.set(Calendar.DAY_OF_MONTH, 1);
        return cal.getTime();
    }
    
    /**
     * @Title: isNeedNotice2
     * @Description: 通知时间是否在本月
     * @param @param date
     * @param @param date2
     * @param @return
     * @author 王磊
     * @return boolean 返回类型
     * @throws
     */
    public static Integer isNeedNotice(Date date1, Date date2)
    {
        Calendar cal1 = Calendar.getInstance();
        cal1.clear();
        cal1.setTime(date1);
        Calendar cal2 = Calendar.getInstance();
        cal2.clear();
        cal2.setTime(date2);
        if (cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR))
        {
            if (cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH))
            {
                return 0;
            }
            else if (cal1.get(Calendar.MONTH) > cal2.get(Calendar.MONTH))
            {
                return 1;
            }
            else
            {
                return -1;
            }
        }
        else if (cal1.get(Calendar.YEAR) > cal2.get(Calendar.YEAR))
        {
            return 1;
        }
        else
        {
            return -1;
        }
    }
    
    /**
     * @Title: getCurMonthLastDate
     * @Description: 获取参数日期当前月份最大日期
     * @param @param date
     * @param @return
     * @author 王磊
     * @return Date 返回类型
     * @throws
     */
    public static Date getCurMonthLastDate(Date date)
    {
        Calendar cal = Calendar.getInstance();
        cal.clear();
        cal.setTime(date);
        int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        cal.set(Calendar.DAY_OF_MONTH, lastDay);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        return cal.getTime();
    }
    
}
