package com.wb.framework.commonUtil;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.ParserConfigurationException;

import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;
import org.xml.sax.SAXException;

public class MobileUtil
{
    /**
     * 归属地查询
     * 
     * @param mobile
     * @return mobileAddress
     */
    private static String getLocationByMobile(final String mobile)
        throws ParserConfigurationException, SAXException, IOException
    {
        String URL = "http://www.ip138.com:8080/search.asp?mobile=" + mobile + "&action=mobile";
        NetworkUntil util = new NetworkUntil();
        util.setEnCode("GBK");
        String result = "";
        try
        {
            result = util.doGet(new URI(URL));
        }
        catch (URISyntaxException e)
        {
            e.printStackTrace();
        }
        try
        {
            Parser parser = new Parser(result);
            NodeFilter filter = new TagNameFilter("table");
            NodeList tableList = parser.extractAllNodesThatMatch(filter);
            Node tableNode = tableList.elementAt(1);
            parser = new Parser(tableNode.toHtml());
            filter = new TagNameFilter("td");
            NodeList tdList = parser.extractAllNodesThatMatch(filter);
            return tdList.elementAt(4).toPlainTextString().replace("&nbsp;", "");
        }
        catch (ParserException e)
        {
            e.printStackTrace();
        }
        return null;
    }
    
    /**
     * 
     * 是否是电话号
     * 
     * @param str
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static boolean isMobile(String str)
    {
        if (str == null)
        {
            return false;
        }
        Pattern p = null;
        Matcher m = null;
        boolean b = false;
        p = Pattern.compile("^[1][3,4,5,7,8][0-9]{9}$");// 验证手机号
        m = p.matcher(str);
        b = m.matches();
        return b;
    }
}
