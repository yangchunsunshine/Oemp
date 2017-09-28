package com.wb.framework.commonUtil;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

/**
 * 
 * Httpclient解析工具类
 * 
 * @author 郝洋
 * @version [版本号, 2015-3-4]
 */
@SuppressWarnings("deprecation")
public class NetworkUntil
{
    
    public RequestConfig defaultRequestConfig = RequestConfig.custom().setSocketTimeout(100000).setConnectTimeout(100000).setConnectionRequestTimeout(100000).setStaleConnectionCheckEnabled(true).build();
    
    private String enCode = "";
    
    private final CookieStore cookieStore;
    
    private final HttpGet get = new HttpGet();
    
    private final HttpPost post = new HttpPost();
    
    private final HttpClientContext localContext;
    
    public DefaultHttpClient dhttpclient = new DefaultHttpClient();
    
    public CloseableHttpClient httpclient = HttpClients.custom().setDefaultRequestConfig(defaultRequestConfig).build();
    
    public String getEnCode()
    {
        return enCode;
    }
    
    public void setEnCode(String enCode)
    {
        this.enCode = enCode;
    }
    
    public CloseableHttpClient getHttpclient()
    {
        return httpclient;
    }
    
    public DefaultHttpClient getDhttpclient()
    {
        return dhttpclient;
    }
    
    public HttpPost getPost()
    {
        return post;
    }
    
    public HttpGet getGet()
    {
        return get;
    }
    
    public CookieStore getCookieStore()
    {
        return cookieStore;
    }
    
    public HttpClientContext getLocalContext()
    {
        return localContext;
    }
    
    public NetworkUntil()
    {
        dhttpclient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 15000);
        dhttpclient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 15000);
        // 创建Cookie存储本地实例
        cookieStore = new BasicCookieStore();
        // Create local HTTP context
        localContext = HttpClientContext.create();
        // 创建本地HTTP上下文
        localContext.setCookieStore(cookieStore);
    }
    
    /**
     * 
     * 返回结果GZIP压缩POST
     * 
     * @param url
     * @param params
     * @return
     * @throws ClientProtocolException
     * @throws IOException
     * @see [类、类#方法、类#成员]
     */
    public String doPostGZIP(URI url, Map<String, String> params)
        throws ClientProtocolException, IOException
    {
        post.setURI(url);
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        Set<String> keySet = params.keySet();
        for (String key : keySet)
        {
            nvps.add(new BasicNameValuePair(key, params.get(key)));
        }
        try
        {
            post.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
        }
        catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }
        HttpResponse response = dhttpclient.execute(post, localContext);
        String encodeConten = "";
        HttpEntity entity = response.getEntity();
        if (entity != null)
        {
            encodeConten = EntityUtils.toString(entity);
        }
        return encodeConten;
    }
    
    /**
     * 
     * 返回结果GZIP压缩GET请求
     * 
     * @param uri
     * @return
     * @throws ClientProtocolException
     * @throws IOException
     * @see [类、类#方法、类#成员]
     */
    public String doGetGZIP(URI uri)
        throws ClientProtocolException, IOException
    {
        get.setURI(uri);
        HttpResponse response = dhttpclient.execute(get, localContext);
        String encodeConten = "";
        HttpEntity entity = response.getEntity();
        if (entity != null)
        {
            encodeConten = EntityUtils.toString(entity);
        }
        return encodeConten;
    }
    
    /**
     * 通用POST(带参数)
     * 
     * @param url 地址
     * @param params 请求的参数
     * @return
     * @throws ClientProtocolException
     * @throws IOException
     */
    public String doPost(URI url, Map<String, String> params)
        throws ClientProtocolException, IOException
    {
        post.setURI(url);
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        Set<String> keySet = params.keySet();
        for (String key : keySet)
        {
            nvps.add(new BasicNameValuePair(key, params.get(key)));
        }
        try
        {
            post.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));
        }
        catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }
        CloseableHttpResponse response = httpclient.execute(post, localContext);
        String encodeConten = encodeResponse(response);
        return encodeConten;
    }
    
    /**
     * 
     * 通用POST(不带参数)
     * 
     * @param url
     * @return
     * @throws ClientProtocolException
     * @throws IOException
     * @see [类、类#方法、类#成员]
     */
    public String doPost(URI url)
        throws ClientProtocolException, IOException
    {
        post.setURI(url);
        CloseableHttpResponse response = httpclient.execute(post, localContext);
        String encodeConten = encodeResponse(response);
        return encodeConten;
    }
    
    /**
     * 
     * 通用GET
     * 
     * @param uri
     * @return
     * @throws ClientProtocolException
     * @throws IOException
     * @see [类、类#方法、类#成员]
     */
    public String doGet(URI uri)
        throws ClientProtocolException, IOException
    {
        get.setURI(uri);
        CloseableHttpResponse response = httpclient.execute(get, localContext);
        String encodeConten = encodeResponse(response);
        return encodeConten;
    }
    
    /**
     * 
     * 实体解析
     * 
     * @param responseBody
     * @return
     * @throws IOException
     * @see [类、类#方法、类#成员]
     */
    @SuppressWarnings("unused")
    public String encodeResponse(CloseableHttpResponse responseBody)
        throws IOException
    {
        String content = "";
        InputStream in = responseBody.getEntity().getContent();
        byte[] b = new byte[1024];
        int i = b.length;
        while ((i = in.read(b, 0, 1024)) != -1)
        {
            if (getEnCode().equals(""))
            {
                content += new String(b);
            }
            else
            {
                content += new String(b, getEnCode());
            }
        }
        in.close();
        return content;
    }
    
    public static String[] returnAnalytical(String returnContent)
    {
        String regEx = "[//[//]<>/a-zA-Z\":_]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(returnContent);
        returnContent = m.replaceAll("").trim();
        String[] returnContents = returnContent.split(",");
        return returnContents;
    }
    
    /**
     * 
     * 是否是中文
     * 
     * @param str
     * @return 是否
     */
    public static boolean isChineseChar(String str)
    {
        boolean temp = false;
        Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
        Matcher m = p.matcher(str);
        if (m.find())
        {
            temp = true;
        }
        return temp;
    }
    
    /**
     * 
     * 获取验证码图片
     * 
     * @param url 地址
     * @return 图片流
     * @throws URISyntaxException
     * @throws IOException
     * @throws ClientProtocolException
     * @throws IllegalStateException
     * @throws Exception
     */
    public byte[] getIMG(String url)
        throws URISyntaxException, IllegalStateException, ClientProtocolException, IOException
    {
        HttpGet httpGet = new HttpGet(new URI(url));
        InputStream in = dhttpclient.execute(httpGet, localContext).getEntity().getContent();
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while ((len = in.read(buffer)) != -1)
        {
            outStream.write(buffer, 0, len);
        }
        in.close();
        return outStream.toByteArray();
    }
    
    /**
     * 
     * 设置代理(8888端口)
     * 
     * @return 代理配置
     */
    public RequestConfig getHostConfig()
    {
        RequestConfig config;
        HttpHost proxy = new HttpHost("127.0.0.1", 8888);
        config = RequestConfig.custom().setProxy(proxy).setCookieSpec(CookieSpecs.BEST_MATCH).build();
        return config;
    }
}
