package com.ffzx.promotion.util;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.dubbo.config.spring.ReferenceBean;
import com.alibaba.dubbo.rpc.service.GenericService;
import com.ffzx.commerce.framework.utils.SingletonDubboApplication;
import com.ffzx.commerce.framework.utils.StringUtil;


/***
 * App端工具类
 * @author ying.cai
 * @date 2016年6月7日 下午12:00:37
 * @email ying.cai@ffzxnet.com
 * @version V1.0
 *
 */
public class AppUtils {
	//app 首页tag标签约定 ；首页专用！
	public static final Map<String,String> appTagMap;
	static{
		appTagMap = new HashMap<>();
		appTagMap.put("SHYP", "APPSETTING,SHOW_SHYP");//生活用品
		appTagMap.put("MZHF", "APPSETTING,SHOW_MZHF");//美妆护肤
		appTagMap.put("DQC", "APPSETTING,SHOW_DQC");//电器城
		appTagMap.put("SJSM", "APPSETTING,SHOW_SJSM");//手机数码
		appTagMap.put("PPFS", "APPSETTING,SHOW_PPFS");//品牌服饰
		appTagMap.put("JJJF", "APPSETTING,SHOW_JJJF");//家居家纺
		appTagMap.put("MSH", "APPSETTING,SHOW_MSH");//美食汇
	}

	public static final String BASE_DATA = "20976";
	public static final String APP_SERVICE = "21018";
	public static final String CIMS = "20982";
	public static final String OMS = "20988";
	public static final String PMS = "20970";
	public static final String PRMS = "21006";
	public static final String UC = "21012";
	private static final Logger logger = LoggerFactory.getLogger(AppUtils.class);

	/***
	 * 快捷服务调用 ，默认调用本机IP服务
	 * @param t 类型
	 * @return
	 * @date 2016年6月14日 下午3:49:47
	 * @author ying.cai
	 * @email ying.cai@ffzxnet.com
	 */
	public static<T> T superRpc(Class clazz,String zooKeeperPort){
		InetAddress addr;
		try {
			addr = InetAddress.getLocalHost();
			String localIp = addr.getHostAddress();
			return superRpc(clazz, zooKeeperPort, localIp);
		} catch (UnknownHostException e) {
			logger.error(e.getMessage(),e);
		}
		return null;
	}
	
	/***
	 * 指定IP进行服务调用
	 * @param clazz
	 * @param zooKeeperPort
	 * @param ip
	 * @return
	 * @date 2016年6月14日 下午4:51:36
	 * @author ying.cai
	 * @email ying.cai@ffzxnet.com
	 */
	public static<T> T superRpc(Class clazz,String zooKeeperPort,String ip){
		try{
			String className = clazz.getName(); 
			String firstChar = (className.charAt(0)+"").toLowerCase();
			String dubboServiceId = firstChar+className.substring(1, className.length());
			ReferenceBean<GenericService> referenceConfig = new ReferenceBean<GenericService>();
			referenceConfig.setInterface(className);
			referenceConfig.setGeneric(Boolean.FALSE);
			referenceConfig.setVersion("1.0");
			referenceConfig.setUrl("dubbo://"+ip +":"+ zooKeeperPort);
			referenceConfig.setId(dubboServiceId);
//			SingletonDubboApplication.getInstance().destroyBean(referenceConfig.getId());
			SingletonDubboApplication.getInstance().addReferenceBean(referenceConfig.getId(), referenceConfig);
			T resService = (T) SingletonDubboApplication.getInstance().getReferenceObject(referenceConfig.getId());
			return resService;
		}catch(Exception e){
			logger.error(e.getMessage(),e);
		}
		return null;
	}
	/**取得系统图片服务器根路径 ying.cai*/
	public static String findImageWebPath(){
		return System.getProperty("image.web.server");
	}
	
	/***
	 * 将数据库查询出来的图片地址进行处理，返回最终能显示图片的地址路径
	 * @param path
	 * @return
	 * @date 2016年6月13日 下午6:00:15
	 * @author ying.cai
	 * @email ying.cai@ffzxnet.com
	 */
	public static String concatImagePath(String path){
		if(StringUtil.isEmpty(path)){
			return "";
		}
		if( "/".equals((path.charAt(0)+""))){
			return findImageWebPath()+path;
		}else{
			return findImageWebPath()+"/"+path;
		}
	}
	
}
