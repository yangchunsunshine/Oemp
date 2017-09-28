package com.wb.framework.commonUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * 返回星期、工作日、非工作日
 */
public class WorkDateUtil
{
    /**
     * @param date输入的日期
     * @return返回星期
     */
    public static String getDay(String date)
    {
        SimpleDateFormat sformat = new SimpleDateFormat("yyyy-MM-dd");
        final String pattern = "星期";
        // 返回结果
        String ret = "";
        // 星期
        int day = 0;
        // 中文星期
        String zhou = null;
        // 日历类
        Calendar cal = Calendar.getInstance();
        try
        {
            cal.setTime(sformat.parse(date));
        }
        catch (ParseException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        day = cal.get(Calendar.DAY_OF_WEEK);
        // 判断为周几
        switch (day)
        {
            case 1:
                zhou = "日";
                break;
            case 2:
                zhou = "一";
                break;
            case 3:
                zhou = "二";
                break;
            case 4:
                zhou = "三";
                break;
            case 5:
                zhou = "四";
                break;
            case 6:
                zhou = "五";
                break;
            case 7:
                zhou = "六";
                break;
            default:
                zhou = "日";
        }
        ret = pattern + zhou;
        return ret;
    }
    
    @SuppressWarnings("unchecked")
    public static List getYmd(String date1, String date2)
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        List list = new ArrayList();
        try
        {
            Date d1 = sdf.parse(date1);
            Date d2 = sdf.parse(date2);
            Calendar c = Calendar.getInstance();
            c.setTime(d1);
            list.add(sdf.format(c.getTime()));// 打出第一天的
            
            while (!c.getTime().equals(d2))
            {
                c.add(Calendar.DATE, 1);// 日期加1
                list.add(sdf.format(c.getTime()));
            }// 直到和第二个日期相等,跳出循环
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }
        return list;
    }
    
    /**
     * 工作日与非工作日、星期计算
     * 
     * @param year 年份
     * @return
     */
    public static List<String> getWeekends(int year)
    {
        List<String> list = new ArrayList<String>();
        final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance(Locale.CHINA);
        cal.set(year, 0, 1);
        for (int day = 1; day <= cal.getActualMaximum(Calendar.DAY_OF_YEAR); day++)
        {
            cal.set(Calendar.DAY_OF_YEAR, day);
            int weekDay = cal.get(Calendar.DAY_OF_WEEK);
            if (weekDay == Calendar.SATURDAY || weekDay == Calendar.SUNDAY)
            {
                list.add(sdf.format(cal.getTime()) + " " + WorkDateUtil.getDay(sdf.format(cal.getTime())));
            }
            else
            {
                list.add(sdf.format(cal.getTime()) + " " + WorkDateUtil.getDay(sdf.format(cal.getTime())));
            }
        }
        return list;
        
    }
}
