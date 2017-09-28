package com.wb.component.mobile.push;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.wb.component.mobile.push.android.AndroidUnicast;
import com.wb.component.mobile.push.ios.IOSUnicast;



public class PushUtil {
	private static String appkey = "56f37c6367e58e0d4500025d";
	private static String appMasterSecret = "1dzlcvzgzcawksykdrujk2vpsdusxazt";
	private static String iosAppkey = "575d1cf5e0f55a2b800018e7";
	private static String iosAppMasterSecret = "5qgvpplpshzihhhfu7ceb8vrexnxph9r";
	/*
	 * Android 单播
	 * deviceToken         设备唯一标识
	 * ticker              通知栏提示文字
	 * title               通知标题
	 * text                通知文字描述
	 * ArrayList<Map> list 传参
	 */
	public static void sendAndroidUnicast(String deviceToken,String ticker,String title,String text,ArrayList<Map> list) throws Exception {
		AndroidUnicast unicast = new AndroidUnicast(appkey,appMasterSecret);
		// TODO Set your device token
		unicast.setDeviceToken(deviceToken);
		unicast.setTicker(ticker);
		unicast.setTitle(title);
		unicast.setText(text);
		unicast.goAppAfterOpen();
		unicast.setDisplayType(AndroidNotification.DisplayType.NOTIFICATION);
		// TODO Set 'production_mode' to 'false' if it's a test device. 
		// For how to register a test device, please see the developer doc.
		unicast.setProductionMode();
		// Set customized fields
		for (Map map : list) {
			Set set = map.keySet();
			Iterator it = set.iterator();
			while (it.hasNext()) {
				Object key = it.next();
				Object value = map.get(key);
				unicast.setExtraField(key.toString(), value.toString());
			}
		}
		PushClient client = new PushClient();
		client.send(unicast);
	}
	/*
	 * ios 单播
	 * deviceToken         设备唯一标识
	 * alert               通知文字描述
	 * badge               消息总数
	 * ArrayList<Map> list 传参
	 */
	public static void sendIOSUnicast(String deviceToken,String alert,ArrayList<Map> list,Integer badge) throws Exception {
		IOSUnicast unicast = new IOSUnicast(iosAppkey,iosAppMasterSecret);
		// TODO Set your device token
		unicast.setDeviceToken(deviceToken);
		unicast.setAlert(alert);
		unicast.setBadge(badge);
		unicast.setSound("default");
		// TODO set 'production_mode' to 'true' if your app is under production mode
		unicast.setTestMode();
		// Set customized fields
		for (Map map : list) {
			Set set = map.keySet();
			Iterator it = set.iterator();
			while (it.hasNext()) {
				Object key = it.next();
				Object value = map.get(key);
				unicast.setCustomizedField(key.toString(), value.toString());
			}
		}
		PushClient client = new PushClient();
		client.send(unicast);
		
	}
public static void main(String[] args) {
	ArrayList<Map> listArgs = new ArrayList<Map>();
	Map temp = new HashMap();
	temp.put("type", "1");
	listArgs.add(temp);
	try {
		sendAndroidUnicast("AvliGYu7AwSFbLKFnCDj0J0nLoN2NyCAg1P0D7gIisRc","催收","android","测试测试测试",listArgs);
//		sendIOSUnicast("703e4d2cf67195aa7f1f752c6bb4660092cbc460c1a9ed93ce9f64e50c4b7206", "您已欠费", listArgs,2);
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}
}
