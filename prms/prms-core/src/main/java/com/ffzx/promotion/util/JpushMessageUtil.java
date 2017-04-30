package com.ffzx.promotion.util;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONObject;
import com.ffzx.commerce.framework.utils.DateUtil;
import com.ffzx.commerce.framework.utils.JPushCode;
import com.ffzx.commerce.framework.utils.JPushUtils;
import com.ffzx.commerce.framework.utils.StringUtil;
import com.ffzx.promotion.api.dto.constant.PrmsConstant;

import cn.jpush.api.JPushClient;
import cn.jpush.api.common.resp.APIConnectionException;
import cn.jpush.api.common.resp.APIRequestException;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.Notification;

public class JpushMessageUtil {

	/**
	 * app key
	 */
	private static String _appKey = "371643322995d5e119fd5729";
	/**
	 * master secret
	 */
	private static String _masterSecret = "5de3d1913f851c54bcaae34b";
	private static JPushClient jpush;
	static {
		jpush = new JPushClient(_masterSecret, _appKey, 3);
	}
	private static Logger logger = Logger.getLogger(JpushMessageUtil.class);

	public static void sendjpushMessage(String phoneAndiosan, String title, String content, String id, String url,
			String type) {
		String[] phoneAndiosanstrings = phoneAndiosan.split(",");
		String phone = phoneAndiosanstrings[0];
		String iosan = phoneAndiosanstrings.length == 2 ? phoneAndiosanstrings[1] : "";
		JSONObject contentJson = new JSONObject();
		contentJson.put("id", id);
		contentJson.put("title", title);
		contentJson.put("content", content);
		contentJson.put("url", "");
		JSONObject valueJson = new JSONObject();
		valueJson.put("type", StringUtil.isEmpty(type) ? "REWARD" : type);
		valueJson.put("jsonObj", "");
		contentJson.put("value", valueJson);
		String finalContent = contentJson.toJSONString();
//		 System.out.println(finalContent);
		 if (iosan.equals(PrmsConstant.ios)) {
		 // 推送给ios
			 send_ios_alias_notification(phone, content, finalContent);
		 } else if (iosan.equals(PrmsConstant.android)) {
		 // 推送给安卓(自定义的，敏杰所需）
			 JPushUtils.send_all_alias_message(phone, title, finalContent);
		 } else {

		 // 推送给安卓(自定义的，敏杰所需）
		 JPushUtils.send_all_alias_message(phone, title, finalContent);
		 // 推送给ios
//		 JPushUtils.send_ios_alias_notification(phone, title, finalContent);
		 }
//		send_ios_alias_notification(phone, title, finalContent);
	}


	/**
	 * 向所ios客户端推送通知 ，需要指定别名
	 * @param alias 要发送给的设备别名
	 * @param title 标题
	 * @param content 内容
	 * add by 柯典佑
	 */
	public static int send_ios_alias_notification(String alias ,String title, String content){
		int code = JPushCode.C_0000;
		IosNotification notificationIos =IosNotification.newBuilder().addExtra("json", content).build();
		Notification notification =Notification.newBuilder().setAlert(title).addPlatformNotification(notificationIos).build();
		PushPayload payload = PushPayload.newBuilder()
		.setPlatform(Platform.android_ios())
		.setAudience(Audience.alias(alias))
		.setNotification(notification).build();
		try {
			PushResult result = jpush.sendPush(payload);
			log(1,"send_all_alias_notification",result.toString());
		} catch (APIConnectionException e) {
			code = JPushCode.C_999 ;
//			e.printStackTrace();
			logger.error("", e);
			log(2,"send_all_alias_notification",e.getMessage());
		} catch (APIRequestException e) {
			code = e.getErrorCode();
//			e.printStackTrace();
			logger.error("", e);
			log(2,"send_all_alias_notification",e.getErrorMessage());
		}
		return code ;
	}
	

	/**
	 * 
	 * @param level
	 *            日志级别：0：debug，1：info，2：error
	 */
	private static void log(int level, String method, String msg) {
		if (level == 0) {
			logger.debug(DateUtil.getCurrentDateTime2Str() + ":" + method + ":debug=" + msg);
			// System.out.println(DateUtil.convertDateToStr(DateUtil.getCurDate(),DateUtil.GENERAL_FORMHMS)+":"+method+":debug="
			// + msg);
		} else if (level == 1) {
			logger.info(DateUtil.getCurrentDateTime2Str() + ":" + method + ":info=" + msg);
			// System.err.println(DateUtil.convertDateToStr(DateUtil.getCurDate(),DateUtil.GENERAL_FORMHMS)+":"+method+":info="
			// + msg);
		} else if (level == 2) {
			// System.out.println(DateUtil.convertDateToStr(DateUtil.getCurDate(),DateUtil.GENERAL_FORMHMS)+":"+method+":error="
			// + msg);
			logger.error(DateUtil.getCurrentDateTime2Str() + ":" + method + ":error=" + msg);
		}
	}
}
