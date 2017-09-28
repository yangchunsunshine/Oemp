package com.wb.component.computer.login.util;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.net.ssl.HttpsURLConnection;
import javax.servlet.http.HttpServletRequest;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wb.component.computer.login.exception.Network404Exception;


public class HttpUtil {

	final static Logger logger = LoggerFactory.getLogger(HttpUtil.class);
	private final static String DEFAULT_ENCODING = "UTF-8";

	
	public static String  doFormPost(String strUrl,String formData) throws ConnectException {
		try {
			URL url = new URL(strUrl);
			HttpURLConnection httpcon = (HttpURLConnection) url.openConnection();

			httpcon.setDoOutput(true);
			httpcon.setDoInput(true);
			httpcon.setUseCaches(false);
			httpcon.setInstanceFollowRedirects(true);
			httpcon.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
			httpcon.setRequestMethod("POST");

			httpcon.setConnectTimeout(30 * 1000);
			httpcon.setReadTimeout(30 * 1000);
			
			httpcon.setRequestProperty("Cookie", "time_zone=" + java.net.URLEncoder.encode("Asia/Shanghai (GMT+8) offset 28800", "UTF-8").trim());

			httpcon.connect();
			OutputStream os = httpcon.getOutputStream();
			os.write(formData.getBytes("utf-8"));
			os.flush();
			
			if (httpcon.getResponseCode() == 404) {
				throw new Network404Exception("找不到服务器目标地址");
			}
			
			InputStream is = httpcon.getInputStream();
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			byte[] buf = new byte[4096];
			for (int len = 0; (len = is.read(buf)) != -1;) {
				baos.write(buf, 0, len);
			}
			is.close();

			byte[] resData = baos.toByteArray();
			baos.close();
			httpcon.disconnect();
			return new String(resData,"utf-8");
		} catch (ConnectException e){
			throw e;
		} catch(Network404Exception e) {
			throw e;
		} catch (Exception ex) {
			logger.error(ex.toString(), ex);
			return null;
		}		
		
	}

	public static String  doFormPost(String strUrl,String formData, Map<String, String> header) throws ConnectException {
		try {
			URL url = new URL(strUrl);
			HttpURLConnection httpcon = (HttpURLConnection) url.openConnection();
			
			httpcon.setDoOutput(true);
			httpcon.setDoInput(true);
			httpcon.setUseCaches(false);
			httpcon.setInstanceFollowRedirects(true);
			httpcon.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
			httpcon.setRequestProperty("x-forwarded-for", header.get("x-forwarded-for"));
			httpcon.setRequestProperty("Proxy-Client-IP", header.get("Proxy-Client-IP"));
			httpcon.setRequestProperty("WL-Proxy-Client-IP", header.get("WL-Proxy-Client-IP"));
			httpcon.setRequestMethod("POST");
			
			// 锟斤拷锟斤拷30锟斤拷锟接筹拷时
			httpcon.setConnectTimeout(30 * 1000);
			httpcon.setReadTimeout(30 * 1000);
			
			httpcon.setRequestProperty("Cookie", "time_zone=" + java.net.URLEncoder.encode("Asia/Shanghai (GMT+8) offset 28800", "UTF-8").trim());
			
			httpcon.connect();
			OutputStream os = httpcon.getOutputStream();
			os.write(formData.getBytes("utf-8"));
			os.flush();
			
			if (httpcon.getResponseCode() == 404) {
				throw new Network404Exception("找不到服务器目标地址");
			}
			
			InputStream is = httpcon.getInputStream();
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			byte[] buf = new byte[4096];
			for (int len = 0; (len = is.read(buf)) != -1;) {
				baos.write(buf, 0, len);
			}
			is.close();
			
			byte[] resData = baos.toByteArray();
			baos.close();
			httpcon.disconnect();
			return new String(resData,"utf-8");
		} catch (ConnectException e){
			throw e;
		} catch(Network404Exception e) {
			throw e;
		} catch (Exception ex) {
			logger.error(ex.toString(), ex);
			return null;
		}		
		
	}
	
	
	public static byte[] doPost(String strUrl, byte[] reqData) {
		try {
			URL url = new URL(strUrl);
			HttpURLConnection httpcon = (HttpURLConnection) url.openConnection();

			httpcon.setDoOutput(true);
			httpcon.setDoInput(true);
			httpcon.setUseCaches(false);
			httpcon.setInstanceFollowRedirects(true);

			httpcon.setRequestMethod("POST");

			// 锟斤拷锟斤拷30锟斤拷锟接筹拷时
			httpcon.setConnectTimeout(30 * 1000);
			httpcon.setReadTimeout(30 * 1000);
			
			httpcon.setRequestProperty("Cookie", "time_zone=" + java.net.URLEncoder.encode("Asia/Shanghai (GMT+8) offset 28800", "UTF-8").trim());

			httpcon.connect();
			OutputStream os = httpcon.getOutputStream();
			os.write(reqData);
			os.flush();
			InputStream is = httpcon.getInputStream();
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			byte[] buf = new byte[4096];
			for (int len = 0; (len = is.read(buf)) != -1;) {
				baos.write(buf, 0, len);
			}
			is.close();

			byte[] resData = baos.toByteArray();
			baos.close();
			httpcon.disconnect();
			return resData;
		} catch (Exception ex) {
			logger.error(ex.toString(), ex);
			return null;
		}
	}

	public static byte[] doGet(String strUrl) {
		try {
			URL url = new URL(strUrl);
			HttpURLConnection httpcon = (HttpURLConnection) url.openConnection();

			httpcon.setDoOutput(false);
			httpcon.setDoInput(true);
			httpcon.setUseCaches(false);
			httpcon.setInstanceFollowRedirects(true);

			httpcon.setRequestMethod("GET");

			httpcon.setConnectTimeout(10 * 60 * 1000);
			httpcon.setReadTimeout(10 * 60 * 1000);
			httpcon.connect();
			InputStream is = httpcon.getInputStream();

			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			// DataInputStream dis = new DataInputStream(is);
			// short len1 = dis.readShort();
			// System.out.println(len1);
			byte[] buf = new byte[4096];
			for (int len = 0; (len = is.read(buf)) != -1;) {
				baos.write(buf, 0, len);
			}
			is.close();

			byte[] resData = baos.toByteArray();
			baos.close();
			httpcon.disconnect();
			return resData;
		} catch (Exception ex) {
			logger.error("HttpUtil request " + strUrl + " error", ex);
			return null;
		}
	}

	public static byte[] doGet(String strUrl, int timepout) {
		try {
			URL url = new URL(strUrl);
			HttpURLConnection httpcon = (HttpURLConnection) url.openConnection();

			httpcon.setDoOutput(false);
			httpcon.setDoInput(true);
			httpcon.setUseCaches(false);
			httpcon.setInstanceFollowRedirects(true);

			httpcon.setRequestMethod("GET");

			httpcon.setConnectTimeout(timepout * 1000);
			httpcon.setReadTimeout(timepout * 1000);
			httpcon.connect();
			InputStream is = httpcon.getInputStream();
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			byte[] buf = new byte[4096];
			for (int len = 0; (len = is.read(buf)) != -1;) {
				baos.write(buf, 0, len);
			}
			is.close();

			byte[] resData = baos.toByteArray();
			baos.close();
			httpcon.disconnect();
			return resData;
		} catch (Exception ex) {
			logger.error(ex.toString());
			return null;
		}
	}

	public static double getDoubleParameter(HttpServletRequest request,String paraName) {
		String paramValue = request.getParameter(paraName);
		if(paramValue.equals("null") || paramValue.isEmpty() ){
			return 0.0d;
		}else {
			return Double.parseDouble(paramValue);
		}
	}
	
	public static String postJSONByClient(String url, String jsonStr){
		CloseableHttpClient client = HttpClients.createDefault();
		HttpPost post = new HttpPost(url);
		StringEntity myEntity = new StringEntity(jsonStr,
		                 ContentType.APPLICATION_JSON);// 构造请求数据
		post.setEntity(myEntity);// 设置请求体
		String responseContent = null; // 响应内容
		CloseableHttpResponse response = null;
		try {
			response = client.execute(post);
			if (response.getStatusLine().getStatusCode() == 200) {
				HttpEntity entity = response.getEntity();
				responseContent = EntityUtils.toString(entity, "UTF-8");
				return responseContent;
			}else{
				return response.toString();
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} finally {
			try {
				if (response != null)
					response.close();

			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					if (client != null)
						client.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public static String getEntityString (HttpServletRequest request){
		BufferedReader br;
		try {
			br = request.getReader();
			StringBuilder jsonStrBuilder = new StringBuilder();
			String tmpStr;
			while((tmpStr = br.readLine())!=null){
				jsonStrBuilder.append(tmpStr);
			}
			String jsonStr = jsonStrBuilder.toString();
			return jsonStr;
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	public static String getMapByClient(String url,Map<String,String> paramMap){
		CloseableHttpClient client = HttpClients.createDefault();
//		HttpPost post = new HttpPost(url);
//		StringEntity myEntity = new StringEntity(jsonStr,
//		                 ContentType.APPLICATION_JSON);// 构造请求数据
//		post.setEntity(myEntity);// 设置请求体
				
		URI uri = null;
		try {
			URIBuilder uriBulder = new URIBuilder(url);
			Set<String> set = paramMap.keySet();
			Iterator<String> it = set.iterator();
			while(it.hasNext())
			{
			    String key = it.next();
			    uriBulder.setParameter(key, paramMap.get(key));
			}
			uri = uriBulder.build();
		} catch (URISyntaxException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	
		HttpGet get = new HttpGet(uri);
		CloseableHttpResponse httpResponse = null;
		try {
			httpResponse = client.execute(get);
			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				HttpEntity entity = httpResponse.getEntity();
				String responseContent = EntityUtils.toString(entity, "UTF-8");
				return responseContent;
			}else{
				return httpResponse.toString();
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}finally {
			try {
				if (httpResponse != null)
					httpResponse.close();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					if (client != null)
						client.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}


	}
	
	public static byte[] doSslGet(String httpsUrl) throws Exception {
		HttpsURLConnection urlCon = null;
		try {
			urlCon = (HttpsURLConnection) (new URL(httpsUrl)).openConnection();
			urlCon.setDoInput(true);  
            urlCon.setDoOutput(true);  
            urlCon.setRequestMethod("GET");  
            urlCon.setUseCaches(false);
            //设置为gbk可以解决服务器接收时读取的数据中文乱码问题
            urlCon.connect();
            InputStream is = urlCon.getInputStream();
            
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
			byte[] buf = new byte[4096];
			for (int len = 0; (len = is.read(buf)) != -1;) {
				baos.write(buf, 0, len);
			}
			is.close();

			byte[] resData = baos.toByteArray();
			baos.close();
			urlCon.disconnect();
			return resData;
		} catch (Exception e) {
			throw new Exception();
		}
	}
}
