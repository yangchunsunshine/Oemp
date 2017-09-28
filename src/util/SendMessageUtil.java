package util;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Query;

import com.wb.framework.commonDao.BaseDao;
public class SendMessageUtil extends BaseDao {
	
	public static void main(String[] args) {
		String user="王艳福";
		String pass="weibao2004";
		StringBuffer context=new StringBuffer();
		context.append("恭喜您注册成为中国领先的企业在线财务服务对象。快速下载手机端产品，随时");
		context.append("随地掌握企业一手财务金融状况。"+"\r\n");
		context.append("永久免费的企业在线财务管理软件。"+"\r\n");
		context.append("http://www.weibaobeijing.com/Accounting/financeDownload.jspx"+"\r\n");
		context.append("专业便捷的企业金融和理财顾问。"+"\r\n");
		//context.append("http://a.app.qq.com/o/s3imple.jsp?pkgname=com.weibao.finance.app.ne"+"\r\n");
		context.append("http://120.25.223.132:8980/Oemp/html/download.html"+"\r\n");
		context.append("服务公司移动办公的不二之选。"+"\r\n");
		context.append("http://www.weibaobeijing.com/Accounting/agencyDownload.jspx"+"\r\n");
		context.append("。"+"\r\n");
		String result=SendMessageUtil.SendMsg(user, pass, context.toString(), "15101666126");
		System.out.println("content="+context);
		System.out.println("result="+result);
	}
	//发送短信接口
	public static String SendMsg(String user,String pass,String context,String tels)
	{
		String url = "http://www.chinaweimei.com/apihttp/SMSSend.aspx";
		String param = null;
		try {
			param = "user="+URLEncoder.encode(user, "gb2312")+"&pass="+pass+"&context="+URLEncoder.encode(context, "gb2312")+"&mobile="+tels;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return SendGet(url, param);
	}  
	//发送Get请求
	private static String SendGet(String url, String param) {
	        String result = "";
	        BufferedReader in = null;
	        try {
	            String urlNameString = url + "?" + param;
	            URL realUrl = new URL(urlNameString);
	            // 打开和URL之间的连接
	            URLConnection connection = realUrl.openConnection();
	            // 设置通用的请求属性
	            connection.setRequestProperty("accept", "*/*");
	            connection.setRequestProperty("connection", "Keep-Alive");
	            connection.setRequestProperty("user-agent",
	                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
	            // 建立实际的连接
	            connection.connect();
	            // 获取所有响应头字段
	            Map<String, List<String>> map = connection.getHeaderFields();
	            // 遍历所有的响应头字段
	            for (String key : map.keySet()) {
	                //System.out.println(key + "--->" + map.get(key));
	            }
	            // 定义 BufferedReader输入流来读取URL的响应
	            in = new BufferedReader(new InputStreamReader(
	                    connection.getInputStream(),"gb2312"));
	            String line;
	            while ((line = in.readLine()) != null) {
	                result += line;
	            }
	        } catch (Exception e) {
	            System.out.println("发送GET请求出现异常！" + e);
	            e.printStackTrace();
	        }
	        // 使用finally块来关闭输入流
	        finally {
	            try {
	                if (in != null) {
	                    in.close();
	                }
	            } catch (Exception e2) {
	                e2.printStackTrace();
	            }
	        }
	        return result;
	    }
	//查询是否包含特殊字符
	public  int validation(String str){
		int num = 0;
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT count(*) AS num");
        sql.append(" FROM mnt_validate_sms");
        sql.append(" WHERE status=1 and text="+str);
        Query query = this.getSession().createSQLQuery(sql.toString());
        query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
        List<Map<String, Object>> list = query.list();
        for (Map<String, Object> map : list)
        {
            num = Integer.parseInt(map.get("num").toString());
        }
		return num;
	}

}
