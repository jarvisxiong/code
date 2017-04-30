package com.ffzx.promotion.util;

import java.math.BigDecimal;
import java.util.List;

import com.ffzx.commerce.framework.utils.StringUtil;

public class StringprmsUtil {
	
	/**
	 * 获取string
	 * @param str
	 * @return
	 */
	public static String getStr(String str){
		return StringUtil.isEmpty(str)?"":str;
	}
	
	/**
	 * 获取int
	 * @param val
	 * @return
	 */
	public static int getInt(Integer val){
		if(null != val){
			return val.intValue();
		}
		return 0;
	}
	
	/**
	 * 获取big  不为空，最小0
	 * @param big
	 * @return
	 */
	public static BigDecimal getBigDecimal(BigDecimal big){
		if(null != big){
			return big;
		}
		return new BigDecimal("0");
	}
	/**
	 * 剔除空格，不返回null,返回""
	 * @param name
	 * @return
	 */
	public static String timeNullStr(String name){
		return StringUtil.isEmpty(name)?"":name;
	}
	/**
	 * 剔除null，返回空字符串,小于0就返回0
	 * @return
	 */
	public static String timeNullInt(Integer num){
		return num==null?"0":num<=0?"0":num.toString();
	}
	/**
	 * @depr 字符串不为null
	 * @param string 字符串
	 * @param message  信息
	 */
	public static void stringException(String string,String message)
	{
		if(StringUtil.isEmpty(string)){
			throw new RuntimeException(message+"不能为空");
		}
	}

	/**
	 * name不为空，返回0
	 * @param name
	 * @return
	 */
	public static String StringReturnInt(String name){
		return StringUtil.isEmpty(name)?"0":name.toString().trim();
	}
	/**
	 * object类型验证不为null
	 * @param object
	 * @param message
	 */
	public static void objectException(Object object,String message){

		if(object==null){
			throw new RuntimeException(message+"不能为空");
		}
	}
	/**
	 * list类型验证不为null
	 * @param object
	 * @param message
	 */
	public static <T> void listExceptionNumm(List<T> list,String message){
		if(list==null || list.size()==0){
			throw new RuntimeException(message+"不能为null");
		}
	}
}
