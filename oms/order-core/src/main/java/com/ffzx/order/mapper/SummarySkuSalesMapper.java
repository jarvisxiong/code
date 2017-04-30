package com.ffzx.order.mapper;

import com.ffzx.commerce.framework.annotation.MyBatisDao;
import com.ffzx.commerce.framework.dao.CrudMapper;
import com.ffzx.order.model.SummarySkuSales;

/**
 * summary_sku_sales数据库操作接口
 * 
 * @author ffzx
 * @date 2016-06-07 14:21:51
 * @version 1.0.0
 * @copyright www.ffzxnet.com
 */
@MyBatisDao
public interface SummarySkuSalesMapper extends CrudMapper {
	
	public int dailyUpdateSummary(SummarySkuSales sales);

}