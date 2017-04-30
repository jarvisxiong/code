package com.ffzx.bi.api.service.consumer;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ffzx.commerce.framework.dto.ResultDto;
import com.ffzx.commerce.framework.exception.ServiceException;
import com.ffzx.commerce.framework.utils.RedisUtil;
import com.ffzx.commodity.api.dto.Category;
import com.ffzx.commodity.api.service.CategoryApiService;

/**
 * bsedate dubbo dictApiService 消费者配置
 * 
 * @author shifeng.tang
 * @date 2016年4月26日 上午10:29:01
 * @version 1.0
 */
@Service("categoryApiConsumer")
public class CategoryApiConsumer {
	// 记录日志
	private final static Logger logger = LoggerFactory.getLogger(CategoryApiConsumer.class);

	@Resource
	private RedisUtil redisUtil;

	@Resource
	private CategoryApiService categoryApiService;

	public List<Category> findCategoryList() throws ServiceException  {
		Map<String, Object> params = new HashMap<String, Object>();
		ResultDto rd = categoryApiService.getList(params);
		if (rd.getData() != null) {
			return (List<Category>) rd.getData();
		}
		return Collections.emptyList();
	}
}
