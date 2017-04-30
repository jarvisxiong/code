package com.ffzx.promotion.util;

import java.util.Random;
import java.util.UUID;

/***
 * App端工具类
 * @author ying.cai
 * @date 2016年6月7日 下午12:00:37
 * @email ying.cai@ffzxnet.com
 * @version V1.0
 *
 */
public class CodeUtils {
	/**
	 * ture成功执行，false不执行
	 * @param data
	 * @return
	 */
	public static String generatorNumber(Random random){
		String str = UUID.randomUUID().toString().replace("-", "");
		str = str.replace("a", "1")
				 .replace("b", "2")
				 .replace("c", "3")
				 .replace("d", "4")
				 .replace("e", "5")
				 .replace("f", "6");
		long l1 = Long.parseLong(str.substring(0, 16));
		long l2 = Long.parseLong(str.substring(16, 32));
		l1 = l1>>10;
		l2 = l2>>10;
		String finalRes = (l2-l1)+"";
		if(finalRes.contains("-")){
			finalRes = finalRes.replace("-", "");
		}
		if(finalRes.length()<12){
			finalRes += random.nextInt(10);
		}else if(finalRes.length()>12){
			finalRes = finalRes.substring(0,12);
		}
		return finalRes;
	}
	
	
}
