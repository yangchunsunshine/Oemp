/**
 *@Copyright:Copyright (c) 2014 - 2100
 *@Company:MicroBank
 */
package com.wb.component.mobile.common.constant;

import java.util.Calendar;
import java.util.Date;

/**
 * 
 * mobile
 * 
 * @author 郝洋
 * @version [版本号, 2016-4-12]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class ConstantMobile
{
    
    public static String PACKETKEY       = "d2ufi7soem40kn9s";
    
    public static String COMMUNICATONKEY = "n5h3ss4lm3nn1n2i";
    
    public static final int HEAT_BEAT_TIME = 480000;// 心跳频率
    
    public static final int HEAT_DEAD_LINE = 600000;// 死亡边界
    
    public static final int HEAT_BEAT_VIEW = 480000;// 心跳视角
    
    /**
     * 
     * 心跳判断 如果当前时间减去登陆时间大于死亡边界,则说明用户下线了
     * 
     * @param date
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static boolean isOnline(Date date)
    {
        Calendar startCal = Calendar.getInstance();
        Calendar curCal = Calendar.getInstance();
        startCal.setTime(date);
        long dvalue = (curCal.getTime().getTime() - startCal.getTime().getTime());
        if (dvalue >= HEAT_DEAD_LINE)
        {
            return false;
        }
        else
        {
            return true;
        }
    }
}
