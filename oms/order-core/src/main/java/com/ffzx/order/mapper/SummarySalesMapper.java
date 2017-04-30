package com.ffzx.order.mapper;

import com.ffzx.commerce.framework.annotation.MyBatisDao;
import com.ffzx.commerce.framework.dao.CrudMapper;
import com.ffzx.order.model.SummarySales;

/**
 * summary_sales数据库操作接口
 * 
 * @author ffzx
 * @date 2016-06-03 14:33:38
 * @version 1.0.0
 * @copyright www.ffzxnet.com
 */
@MyBatisDao
public interface SummarySalesMapper extends CrudMapper {
	public int dailyUpdateSummary(SummarySales summary);
}