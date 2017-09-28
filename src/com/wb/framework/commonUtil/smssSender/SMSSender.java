package com.wb.framework.commonUtil.smssSender;

import java.util.HashMap;
import java.util.Map;

import com.cloopen.rest.sdk.CCPRestSmsSDK;
import com.wb.framework.commonUtil.calendar.DateUtil;
import com.wb.model.entity.computer.MntMember;
import com.wb.model.pojo.computer.OrgDetailMsgDto;

/**
 * 
 * 短信网关
 * 
 * @author 郝洋
 * @version [版本号, 2016-3-17]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class SMSSender
{
    
    // 初始化SDK
    static CCPRestSmsSDK restAPI = new CCPRestSmsSDK();
    
    static String ACCOUNTID = "aaf98f894bc4f9b9014bcde3489b041d";
    
    static String ACCOUNTTOKEN = "0a61644a58184ff698c4c92b1cbddd3e";
    
    static String APPID = "8a48b5514bc4fbbb014bce1b0730041f";
    
    static String OUTTIME = "5";
    
    //public static String REGIST = "14178";
    public static String REGIST = "122590";
    
    
   // public static String PASSWORD = "14181";
    public static String PASSWORD = "122588";
    
    // public static String Tax = "32343";
//    public static String Tax = "40558";
    public static String Tax = "119261";
    
    // public static String Fee = "32344";
//    public static String Fee = "40556";
    public static String Fee = "119262";
    
    //短信发送可编辑
    public static String EditMessage="124994";
    
  //加载状态码
    public static Map<String,String> messCode = new HashMap<String,String>();
    
    static
    {
        // 网关地址端口
        restAPI.init("app.cloopen.com", "8883");
        
        // 网关帐户
        restAPI.setAccount(ACCOUNTID, ACCOUNTTOKEN);
        
        // 网关APPid
        restAPI.setAppId(APPID);
        
        //错误码初始化
        initMessCode();
    }
    
    // 短信可编辑发送 add by hechunyang begin
    public static Map<String, Object> editMessage(String tel, String[] message)
    {
    	return restAPI.sendTemplateSMS(tel, EditMessage, message);
    }
    // 短信可编辑发送 add by hechunyang end
    
    public static Map<String, Object> sendMessage(String template, String toTel, String code)
    {
        return restAPI.sendTemplateSMS(toTel, template, new String[] {code, OUTTIME});
    }
    
    // 发送报税提醒短信升级版
    public static Map<String, Object> sendTaxNotice(String tel, String[] message)
    {
        return restAPI.sendTemplateSMS(tel, Tax, message);
    }
    
    //催费短信发送
    public static Map<String, Object> sendfeeNotice(String tel, String[] message)
    {
        return restAPI.sendTemplateSMS(tel, Fee, message);
    }

    public static void initMessCode()
    {
        messCode.put("000000","发送成功");
        messCode.put("112300","接收短信的手机号码为空");
        messCode.put("112301","短信正文为空");
        messCode.put("112302","群发短信已暂停");
        messCode.put("112303","应用未开通短信功能");
        messCode.put("112304","短信内容的编码转换有误");
        messCode.put("112305","应用未上线，短信接收号码外呼受限");
        messCode.put("112306","接收模板短信的手机号码为空");
        messCode.put("112307","模板短信模板ID为空");
        messCode.put("112308","模板短信模板data参数为空");
        messCode.put("112309","模板短信内容的编码转换有误");
        messCode.put("112310","应用未上线，模板短信接收号码外呼受限");
        messCode.put("160031","【短信】参数解析失败");
        messCode.put("160032","【短信】短信模板无效");
        messCode.put("160033","【短信】短信存在黑词");
        messCode.put("160034","【短信】号码黑名单");
        messCode.put("160035","【短信】短信下发内容为空");
        messCode.put("160036","【短信】短信模板类型未知");
        messCode.put("160037","【短信】短信内容长度限制");
        messCode.put("160038","【短信】短信验证码发送过频繁");
        messCode.put("160039","【短信】发送数量超出同模板同号天发送次数上限");
        messCode.put("160040","【短信】验证码超出同模板同号码天发送上限");
        messCode.put("160041","【短信】通知超出同模板同号码天发送上限");
        messCode.put("160042","【短信】号码格式有误");
        messCode.put("160043","【短信】应用与模板id不匹配");
        messCode.put("160050","【短信】短信发送失败");
    }
    
}
