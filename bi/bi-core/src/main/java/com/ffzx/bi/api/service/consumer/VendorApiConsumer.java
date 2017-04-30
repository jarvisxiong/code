package com.ffzx.bi.api.service.consumer;

import java.util.Collections;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ffzx.commerce.framework.dto.ResultDto;
import com.ffzx.commerce.framework.exception.ServiceException;
import com.ffzx.commerce.framework.page.Page;
import com.ffzx.commerce.framework.utils.RedisUtil;
import com.ffzx.member.api.service.VendorApiService;

/**
 * bsedate dubbo dictApiService 消费者配置
 * 
 * @author shifeng.tang
 * @date 2016年4月26日 上午10:29:01
 * @version 1.0
 */
@Service("vendorApiConsumer")
public class VendorApiConsumer {
	// 记录日志
	private final static Logger logger = LoggerFactory.getLogger(VendorApiConsumer.class);

	@Resource
	private RedisUtil redisUtil;

	@Resource
	private VendorApiService vendorApiService;

	/**
	 * 获取供应商
	 */
	public Map<String, Object> findVendorList(Page page, String orderByField, String orderBy,
			Map<String, Object> params) throws ServiceException {
		ResultDto rd = vendorApiService.findList(page, orderByField, orderBy, params);
		if (rd.getData() != null) {
			return (Map<String, Object>) rd.getData();
		}
		return Collections.emptyMap();
	}
}
