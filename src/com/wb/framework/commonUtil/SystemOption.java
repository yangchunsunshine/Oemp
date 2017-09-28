package com.wb.framework.commonUtil;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;

/**
 * 
 * 收集系统参数工具类
 * 
 * @author 郝洋
 * @version [版本号, 2016-4-8]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class SystemOption
{
    
    public static String[] getBaseOption()
    {
        InetAddress ia;
        try
        {
            ia = InetAddress.getLocalHost();
            byte[] mac = NetworkInterface.getByInetAddress(ia).getHardwareAddress();
            StringBuffer sb = new StringBuffer("");
            for (int i = 0; i < mac.length; i++)
            {
                if (i != 0)
                {
                    sb.append("-");
                }
                // 字节转换为整数
                int temp = mac[i] & 0xff;
                String str = Integer.toHexString(temp);
                if (str.length() == 1)
                {
                    sb.append("0" + str);
                }
                else
                {
                    sb.append(str);
                }
            }
            return new String[] {ia.getHostName(), ia.getHostAddress(), sb.toString().toUpperCase()};
        }
        catch (UnknownHostException unknownhostexception)
        {
            // TODO 获取本机IP异常
            unknownhostexception.printStackTrace();
            return null;
        }
        catch (SocketException socketexception)
        {
            // TODO socket异常捕获
            socketexception.printStackTrace();
            return null;
        }
    }
    
    public static void getSysOption()
    {
        // Properties pro = System.getProperties();
        // Set keys = pro.keySet();
        // Iterator it = keys.iterator();
        // while(it.hasNext()){
        // }
    }
}
