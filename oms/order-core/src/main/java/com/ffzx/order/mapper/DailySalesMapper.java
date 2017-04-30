package com.ffzx.order.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.ffzx.commerce.framework.annotation.MyBatisDao;
import com.ffzx.commerce.framework.dao.CrudMapper;
import com.ffzx.order.model.DailySales;

/**
 * daily_sales数据库操作接口
 * 
 * @author ffzx
 * @date 2016-06-03 14:33:38
 * @version 1.0.0
 * @copyright www.ffzxnet.com
 */
@MyBatisDao
public interface DailySalesMapper extends CrudMapper {
	
	public List<DailySales> selectCommodityCodeGroup(@Param("params") Map<String, Object> params);
	
	public int updateByParam(DailySales daily);

}