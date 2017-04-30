package com.ffzx.portal.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ffzx.commerce.framework.page.Page;
import com.ffzx.commerce.framework.service.BaseCrudService;
import com.ffzx.portal.model.CatelogData;

/**
 * @className CatelogDataService
 *
 * @author liujunjun
 * @date 2017-04-12 11:37:17
 * @version 1.0.0
 */
public interface CatelogDataService extends BaseCrudService{

	/**
	 * @param number
	 * @param sort
	 * @return
	 * @author ying.cai
	 * @date 2017年4月12日下午5:35:41
	 * @email ying.cai@ffzxnet.com
	 */
	JSONArray getDataJson(String number, String sort);

	/**
	 * @param number
	 * @param sort
	 * @param pageObj
	 * @return
	 * @author ying.cai
	 * @date 2017年4月13日下午1:50:37
	 * @email ying.cai@ffzxnet.com
	 */
	JSONObject getDataJsonPag(String number, String sort, Page pageObj);

	/**
	 * @param id
	 * @return
	 * @author ying.cai
	 * @date 2017年4月13日下午2:02:40
	 * @email ying.cai@ffzxnet.com
	 */
	JSONObject newGetCatelogDataInfo(String id);

	/**
	 * @param pageTabId
	 * @return
	 * @author ying.cai
	 * @date 2017年4月13日下午2:11:38
	 * @email ying.cai@ffzxnet.com
	 */
	JSONArray getPageTabData(String pageTabId);

	/**
	 * @param cld
	 * @author ying.cai
	 * @date 2017年4月14日上午8:41:57
	 * @email ying.cai@ffzxnet.com
	 */
	void updateTop(CatelogData cld);
	
}
