package com.ffzx.promotion.util;

import com.ffzx.commerce.framework.utils.StringUtil;


/***
 * App端工具类
 * @author ying.cai
 * @date 2016年6月7日 下午12:00:37
 * @email ying.cai@ffzxnet.com
 * @version V1.0
 *
 */
public class ExcelUtils {
	/**
	 * ture成功执行，false不执行
	 * @param data
	 * @return
	 */
	public static boolean getIsSuccess(String[] data){
		boolean isFlag=false;//是否成功执行
		for(int i=0;i<10;i++){
			if(!StringUtil.isEmpty(data[i])){
				isFlag=true;
			}
		}
		return isFlag;
	}
	
}
