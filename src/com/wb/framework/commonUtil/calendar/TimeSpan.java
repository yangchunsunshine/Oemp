package com.wb.framework.commonUtil.calendar;

import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial")
public class TimeSpan implements Serializable
{
    long ticks;
    
    public TimeSpan(Date start, Date end)
    {
        this(end.getTime() - start.getTime());
    }
    
    public TimeSpan(long ticks)
    {
        this.ticks = 0L;
        
        this.ticks = ticks;
    }
    
    public TimeSpan(int hours, int minutes, int seconds)
    {
        this(hours, minutes, seconds, 0);
    }
    
    public TimeSpan(int hours, int minutes, int seconds, int milliseconds)
    {
        this(0, hours, minutes, seconds, 0);
    }
    
    public TimeSpan(int days, int hours, int minutes, int seconds, int milliseconds)
    {
        this.ticks = 0L;
        
        long num = (Math.abs(days) * 3600 * 24 + Math.abs(hours) * 3600 + Math.abs(minutes) * 60 + Math.abs(seconds)) * 1000 + Math.abs(milliseconds);
        this.ticks = num;
    }
    
    public long getTicks()
    {
        return this.ticks;
    }
    
    public void setTicks(long ticks)
    {
        this.ticks = Math.abs(ticks);
    }
    
    public TimeSpan duaration()
    {
        return new TimeSpan(Math.abs(this.ticks));
    }
    
    public TimeSpan negate()
    {
        return new TimeSpan(-this.ticks);
    }
    
    public TimeSpan add(TimeSpan ts)
    {
        return new TimeSpan(ts.ticks + this.ticks);
    }
    
    public static Date add(Date date, TimeSpan ts)
    {
        return new Date(date.getTime() + ts.getTotalMilliseconds());
    }
    
    public static Date before(Date date, TimeSpan ts)
    {
        return substract(date, ts);
    }
    
    public static Date after(Date date, TimeSpan ts)
    {
        return add(date, ts);
    }
    
    public static Date substract(Date date, TimeSpan ts)
    {
        return new Date(date.getTime() - ts.getTotalMilliseconds());
    }
    
    public TimeSpan substract(TimeSpan ts)
    {
        return new TimeSpan(this.ticks - ts.ticks);
    }
    
    public static int compare(TimeSpan t1, TimeSpan t2)
    {
        if (t1.ticks > t2.ticks)
        {
            return 1;
        }
        if (t1.ticks < t2.ticks)
        {
            return -1;
        }
        return 0;
    }
    
    private static TimeSpan interval(double value, int scale)
        throws Exception
    {
        if (Double.isNaN(value))
        {
            throw new Exception("Arg cannot be NAN");
        }
        
        double num = value * scale;
        double num2 = num + ((value >= 0.0D) ? 0.5D : -0.5D);
        return new TimeSpan((long)num2);
    }
    
    public static TimeSpan fromDays(double days)
        throws Exception
    {
        return interval(Math.abs(days), 86400000);
    }
    
    public static TimeSpan fromHours(double hours)
        throws Exception
    {
        return interval(Math.abs(hours), 3600000);
    }
    
    public static TimeSpan fromMinutes(double minutes)
        throws Exception
    {
        return interval(Math.abs(minutes), 60000);
    }
    
    public static TimeSpan fromSeconds(double seconds)
        throws Exception
    {
        return interval(Math.abs(seconds), 1000);
    }
    
    public static TimeSpan fromMilliseconds(double milliseconds)
        throws Exception
    {
        return interval(Math.abs(milliseconds), 1);
    }
    
    public int getDays()
    {
        return (int)(this.ticks / 86400000L);
    }
    
    public int getHours()
    {
        return (int)(this.ticks / 3600000L % 24L);
    }
    
    public int getMinutes()
    {
        return (int)(this.ticks / 60000L % 60L);
    }
    
    public int getSeconds()
    {
        return (int)(this.ticks / 1000L % 60L);
    }
    
    public int getMilliseconds()
    {
        return ((int)this.ticks % 1000);
    }
    
    public double getTotalDays()
    {
        return (this.ticks * 1.157407407407407E-008D);
    }
    
    public double getTotalHours()
    {
        return (this.ticks * 2.777777777777778E-007D);
    }
    
    public double getTotalMinutes()
    {
        return (this.ticks * 1.666666666666667E-005D);
    }
    
    public double getTotalSeconds()
    {
        return (this.ticks * 0.001D);
    }
    
    public long getTotalMilliseconds()
    {
        return this.ticks;
    }
    
    protected Object clone()
        throws CloneNotSupportedException
    {
        return new TimeSpan(this.ticks);
    }
    
    public boolean equals(Object obj)
    {
        if (obj == null)
            return false;
        if (!(obj instanceof TimeSpan))
            return false;
        TimeSpan ts = (TimeSpan)obj;
        return (ts.ticks == this.ticks);
    }
    
    public int hashCode()
    {
        return Long.valueOf(this.ticks).hashCode();
    }
    
    public String toString()
    {
        return super.toString();
    }
}