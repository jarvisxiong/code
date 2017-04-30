package com.ffzx.portal.service;

import com.alibaba.fastjson.JSONArray;
import com.ffzx.commerce.framework.service.BaseCrudService;
import com.ffzx.portal.model.Catelog;

/**
 * @className CatelogService
 *
 * @author liujunjun
 * @date 2017-04-12 11:36:18
 * @version 1.0.0
 */
public interface CatelogService extends BaseCrudService{

	/**
	 * @param pageTabId
	 * @return
	 * @author ying.cai
	 * @date 2017年4月12日下午5:25:14
	 * @email ying.cai@ffzxnet.com
	 */
	JSONArray getCatelogJson(String pageTabId);

	/**
	 * @param childId
	 * @return
	 * @author ying.cai
	 * @date 2017年4月12日下午5:30:48
	 * @email ying.cai@ffzxnet.com
	 */
	JSONArray getChildCatelogJson(String childId);
	/**
	 * 
	 * 
	 * @param data
	 * @author yinglong.huang
	 * @email yinglong.huang@ffzxnet.com
	 * @date 2017年4月14日 下午8:28:31
	 * @version V1.0
	 * @return
	 */
	void saveData(Catelog data);
	
}
