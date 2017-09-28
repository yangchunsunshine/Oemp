package com.wb.framework.commonUtil.calendar;

import java.util.Calendar;
import java.util.Date;

/**
 * 
 * @作者 付天有
 * @编写日期 2013-1-23
 * @功能描述 封装报文数据类型转换类,针对C++等低位优先字节序 并提供BCD日期类型转换
 */
public class DataChange
{
    
    /**
     * short转换到字节数组
     * 
     * @param number
     * @return
     */
    public static byte[] shortToByte(short number)
    {
        byte[] b = new byte[2];
        for (int i = 0; i < b.length; i++)
        {
            b[i] = (byte)(number & 0xFF);
            number >>= 8;
        }
        return b;
    }
    
    /**
     * 字节到short转换
     * 
     * @param b
     * @return
     * @throws DataTranslateException
     */
    public static short byteToShort(byte[] b)
        throws Exception
    {
        if (b.length != 2)
            throw new Exception("字节数不匹配转换类型");
        
        return (short)((((b[1] & 0xff) << 8) | b[0] & 0xff));
    }
    
    /**
     * 字节到short转换
     * 
     * @param b 字节缓冲区, start 转换起始索引
     * @return
     * @throws DataTranslateException
     */
    public static short byteToShort(byte[] b, int start)
    {
        return (short)((((b[start + 1] & 0xff) << 8) | b[start] & 0xff));
    }
    
    /**
     * 整型转换到字节数组
     * 
     * @param number
     * @return
     */
    public static byte[] intToByte(int number)
    {
        byte[] b = new byte[4];
        for (int i = 0; i < b.length; i++)
        {
            b[i] = (byte)(number & 0xFF);
            number >>= 8;
        }
        return b;
    }
    
    /**
     * 字节数组到整型转换
     * 
     * @param b
     * @return
     * @throws DataTranslateException
     */
    public static int byteToInt(byte[] b)
        throws Exception
    {
        if (b.length != 4)
            throw new Exception("字节数不匹配转换类型");
        return (int)((((b[3] & 0xff) << 24) | ((b[2] & 0xff) << 16) | ((b[1] & 0xff) << 8) | ((b[0] & 0xff) << 0)));
    }
    
    /**
     * 字节到int转换
     * 
     * @param b 字节缓冲区, start 转换起始索引
     * @return
     * @throws DataTranslateException
     */
    public static int byteToInt(byte[] b, int start)
    {
        return (int)((((b[start + 3] & 0xff) << 24) | ((b[start + 2] & 0xff) << 16) | ((b[start + 1] & 0xff) << 8) | ((b[start] & 0xff) << 0)));
    }
    
    /**
     * long转换到字节数组
     * 
     * @param number
     * @return
     */
    public static byte[] longToByte(long number)
    {
        byte[] b = new byte[8];
        for (int i = 0; i < b.length; i++)
        {
            b[i] = (byte)(number & 0xFF);
            number >>= 8;
        }
        return b;
    }
    
    /**
     * 字节数组到整型的转换
     * 
     * @param b
     * @return
     * @throws DataTranslateException
     */
    public static long byteToLong(byte[] b)
        throws Exception
    {
        if (b.length != 8)
            throw new Exception("字节数不匹配转换类型");
        return ((((long)b[7] & 0xff) << 56) | (((long)b[6] & 0xff) << 48) | (((long)b[5] & 0xff) << 40) | (((long)b[4] & 0xff) << 32) | (((long)b[3] & 0xff) << 24) | (((long)b[2] & 0xff) << 16) | (((long)b[1] & 0xff) << 8) | (((long)b[0] & 0xff) << 0));
    }
    
    /**
     * double转换到字节数组
     * 
     * @param d
     * @return
     */
    public static byte[] doubleToByte(double d)
    {
        long l = (long)(d * 100);
        return longToByte(l);
    }
    
    /**
     * 字节数组到double转换
     * 
     * @param b
     * @return
     * @throws DataTranslateException
     */
    public static double byteToDouble(byte[] b)
        throws Exception
    {
        if (b.length != 8)
            throw new Exception("字节数不匹配转换类型");
        return ((double)byteToLong(b)) / 100;
    }
    
    /**
     * float转换到字节数组
     * 
     * @param d
     * @return
     */
    public static byte[] floatToByte(float d)
    {
        int l = (int)(d * 100);
        return intToByte(l);
    }
    
    /**
     * 字节数组到float的转换
     * 
     * @param b
     * @return
     * @throws DataTranslateException
     */
    public static float byteToFloat(byte[] b)
        throws Exception
    {
        if (b.length != 4)
            throw new Exception("字节数不匹配转换类型");
        
        return ((float)byteToLong(b)) / 100;
    }
    
    /**
     * 字符串到字节数组转换
     * 
     * @param s
     * @return
     */
    public static byte[] stringToByte(String s)
    {
        return s.getBytes();
    }
    
    /**
     * 字节数组带字符串的转换
     * 
     * @param b
     * @return
     */
    public static String byteToString(byte[] b)
    {
        return new String(b);
        
    }
    
    /**
     * 将字符串转成unicode
     * 
     * @param str 待转字符串
     * @return unicode字符串
     */
    public static byte[] Unicode2Byte(String s)
    {
        int len = s.length();
        byte abyte[] = new byte[len << 1];
        int j = 0;
        for (int i = 0; i < len; i++)
        {
            char c = s.charAt(i);
            abyte[j++] = (byte)(c & 0xff);
            abyte[j++] = (byte)(c >> 8);
        }
        
        return abyte;
    }
    
    public static String Byte2Unicode(byte abyte[], int st, int bEnd) // 不包含bEnd
    {
        StringBuffer sb = new StringBuffer("");
        for (int j = st; j < bEnd;)
        {
            int lw = abyte[j++];
            if (lw < 0)
                lw += 256;
            int hi = abyte[j++];
            if (hi < 0)
                hi += 256;
            char c = (char)(lw + (hi << 8));
            sb.append(c);
        }
        
        return sb.toString();
    }
    
    public static String Byte2Unicode(byte abyte[], int len)
    {
        return Byte2Unicode(abyte, 0, len);
    }
    
    public static String Byte2Unicode(byte abyte[])
    {
        if (abyte != null)
        {
            return Byte2Unicode(abyte, 0, abyte.length);
        }
        else
        {
            return null;
        }
    }
    
    @SuppressWarnings("deprecation")
    public static byte[] date2BCD7(Date date)
    {
        byte[] buffer = new byte[7];
        int year = date.getYear() + 1900;
        int month = date.getMonth() + 1;
        int day = date.getDate();
        int hour = date.getHours();
        int min = date.getMinutes();
        int second = date.getSeconds();
        byte y1 = (byte)((year / 1000) % 10);
        byte y2 = (byte)((year / 100) % 10);
        byte y3 = (byte)((year / 10) % 10);
        byte y4 = (byte)(year % 10);
        byte m1 = (byte)((month / 10) % 10);
        byte m2 = (byte)(month % 10);
        byte d1 = (byte)((day / 10) % 10);
        byte d2 = (byte)(day % 10);
        byte h1 = (byte)((hour / 10) % 10);
        byte h2 = (byte)(hour % 10);
        byte min1 = (byte)((min / 10) % 10);
        byte min2 = (byte)(min % 10);
        byte s1 = (byte)((second / 10) % 10);
        byte s2 = (byte)(second % 10);
        buffer[0] = (byte)(((y1 << 4) & 0xf0) | (y2));
        buffer[1] = (byte)(((y3 << 4) & 0xf0) | (y4));
        buffer[2] = (byte)(((m1 << 4) & 0xf0) | (m2));
        buffer[3] = (byte)(((d1 << 4) & 0xf0) | (d2));
        buffer[4] = (byte)(((h1 << 4) & 0xf0) | (h2));
        buffer[5] = (byte)(((min1 << 4) & 0xf0) | (min2));
        buffer[6] = (byte)(((s1 << 4) & 0xf0) | (s2));
        return buffer;
    }
    
    @SuppressWarnings("deprecation")
    public static byte[] date2BCD4(Date date)
    {
        byte[] buffer = new byte[4];
        int year = date.getYear() + 1900;
        int month = date.getMonth() + 1;
        int day = date.getDate();
        byte y1 = (byte)((year / 1000) % 10);
        byte y2 = (byte)((year / 100) % 10);
        byte y3 = (byte)((year / 10) % 10);
        byte y4 = (byte)(year % 10);
        byte m1 = (byte)((month / 10) % 10);
        byte m2 = (byte)(month % 10);
        byte d1 = (byte)((day / 10) % 10);
        byte d2 = (byte)(day % 10);
        buffer[0] = (byte)(((y1 << 4) & 0xf0) | (y2));
        buffer[1] = (byte)(((y3 << 4) & 0xf0) | (y4));
        buffer[2] = (byte)(((m1 << 4) & 0xf0) | (m2));
        buffer[3] = (byte)(((d1 << 4) & 0xf0) | (d2));
        return buffer;
    }
    
    public static byte[] date2BCD4(String date)
    {
        return date2BCD4(DateUtil.stringToDate(date));
    }
    
    public static Date BCD7ToDate(byte[] data)
    {
        if (data != null && data.length == 7)
        {
            Calendar calendar = Calendar.getInstance();
            int y1 = (data[0] >> 4) & 0x0f;
            int y2 = data[0] & 0x0f;
            int y3 = (data[1] >> 4) & 0x0f;
            int y4 = data[1] & 0x0f;
            int m1 = (data[2] >> 4) & 0x0f;
            int m2 = data[2] & 0x0f;
            int d1 = (data[3] >> 4) & 0x0f;
            int d2 = data[3] & 0x0f;
            int h1 = (data[4] >> 4) & 0x0f;
            int h2 = data[4] & 0x0f;
            int min1 = (data[5] >> 4) & 0x0f;
            int min2 = data[5] & 0x0f;
            int s1 = (data[6] >> 4) & 0x0f;
            int s2 = data[6] & 0x0f;
            int year = y1 * 1000 + y2 * 100 + y3 * 10 + y4;
            int day = d1 * 10 + d2;
            int month = m1 * 10 + m2;
            int hour = h1 * 10 + h2;
            int min = min1 * 10 + min2;
            int second = s1 * 10 + s2;
            calendar.set(year, month - 1, day, hour, min, second);
            return calendar.getTime();
        }
        return null;
    }
    
    public static Date BCD4ToDate(byte[] data)
    {
        if (data != null && data.length == 4)
        {
            Calendar calendar = Calendar.getInstance();
            int y1 = (data[0] >> 4) & 0x0f;
            int y2 = data[0] & 0x0f;
            int y3 = (data[1] >> 4) & 0x0f;
            int y4 = data[1] & 0x0f;
            int m1 = (data[2] >> 4) & 0x0f;
            int m2 = data[2] & 0x0f;
            int d1 = (data[3] >> 4) & 0x0f;
            int d2 = data[3] & 0x0f;
            int year = y1 * 1000 + y2 * 100 + y3 * 10 + y4;
            int day = d1 * 10 + d2;
            int month = m1 * 10 + m2;
            calendar.set(year, month - 1, day);
            return calendar.getTime();
        }
        return null;
    }
}
