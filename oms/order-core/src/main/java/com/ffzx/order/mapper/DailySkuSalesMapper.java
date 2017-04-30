package com.ffzx.order.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.ffzx.commerce.framework.annotation.MyBatisDao;
import com.ffzx.commerce.framework.dao.CrudMapper;
import com.ffzx.order.model.DailySkuSales;

/**
 * daily_sku_sales数据库操作接口
 * 
 * @author ffzx
 * @date 2016-06-07 14:21:51
 * @version 1.0.0
 * @copyright www.ffzxnet.com
 */
@MyBatisDao
public interface DailySkuSalesMapper extends CrudMapper {
   
	/**
	 * 获取最新一条统计记录
	 * @return
	 */
   public DailySkuSales selectLastOne();
   
   
   public List<DailySkuSales> selectSkuCodeGroup(@Param("params") Map<String, Object> params);
   
   public int updateByParam(DailySkuSales one);
}