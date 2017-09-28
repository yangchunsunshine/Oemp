package com.wb.component.computer.common.constant;

import java.util.Calendar;
import java.util.Date;

import org.apache.log4j.Logger;

/**
 * 
 * 管理平台常量类
 * 
 * @author 郝洋
 * @version [版本号, 2016-3-21]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class ConstantComputer
{
    /**
     * 日志服务
     */
    private static final Logger log = Logger.getLogger(ConstantComputer.class);
    
    /**
     * 心跳频率
     */
    public static final int HEAT_BEAT_TIME = 480000;
    
    /**
     * 死亡边界
     */
    public static final int HEAT_DEAD_LINE = 600000;
    
    /**
     * 心跳视角
     */
    public static final int HEAT_BEAT_VIEW = 480000;
    
    /**
     * 心跳判断 如果当前时间减去登陆时间大于死亡边界,则说明用户下线了
     * 
     * @param date 当前时间
     * @return 是否掉线
     */
    public static boolean isOnline(Date date)
    {
        final Calendar startCal = Calendar.getInstance();
        final Calendar curCal = Calendar.getInstance();
        startCal.setTime(date);
        final long dvalue = (curCal.getTime().getTime() - startCal.getTime().getTime());
        return !(dvalue >= HEAT_DEAD_LINE);
    }
}
