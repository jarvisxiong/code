package com.ffzx.portal.service;

import com.alibaba.fastjson.JSONArray;
import com.ffzx.commerce.framework.service.BaseCrudService;

/**
 * @className PageTabService
 *
 * @author liujunjun
 * @date 2017-04-12 11:38:46
 * @version 1.0.0
 */
public interface PageTabService extends BaseCrudService{

	/**
	 * 获取页签json数据
	 * @return
	 * @author ying.cai
	 * @date 2017年4月12日下午5:11:50
	 * @email ying.cai@ffzxnet.com
	 */
	JSONArray getPageTabJson();
	
}
