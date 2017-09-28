/*
 * 文 件 名:  SortNameCN.java
 * 版    权:  gomyck
 * 描    述:  <描述>
 * 修 改 人:  郝洋
 * 修改时间:  2016-3-24
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.wb.component.computer.login.util;

import net.sourceforge.pinyin4j.PinyinHelper;

import org.apache.log4j.Logger;

/**
 * 生成汉字缩写工具类
 * 
 * @author 郝洋
 * @version [版本号, 2016-3-24]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class SortNameCN
{
    
    /**
     * 日志服务
     */
    private static final Logger log = Logger.getLogger(SortNameCN.class);
    
    /**
     * 生成汉字缩写
     * 
     * @param str 企业名称
     * @return 企业简称
     */
    public static String getAcronym(String str)
    {
        StringBuffer acronym = new StringBuffer();
        if (str != null && !str.equals(""))
        {
            final char[] name = str.toCharArray();
            for (int i = 0; i < name.length; i++)
            {
                final String[] letters = PinyinHelper.toHanyuPinyinStringArray(name[i]);
                if (letters != null && letters.length > 0)
                {
                    final char[] initial = letters[0].toCharArray();
                    acronym.append(initial[0]);
                }
            }
        }
        return acronym.toString();
    }
}
