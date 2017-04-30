package com.ffzx.order.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.ffzx.commerce.framework.annotation.MyBatisDao;
import com.ffzx.commerce.framework.dao.CrudMapper;

/**
 * order_recycle数据库操作接口
 * 
 * @author ffzx
 * @date 2016-08-04 17:19:04
 * @version 1.0.0
 * @copyright www.ffzxnet.com
 */
@MyBatisDao
public interface OrderRecycleMapper extends CrudMapper {

	public int updateStatusByBarCodeSigned(@Param("barCodeSigned") String barCodeSigned, @Param("status") String status,
			@Param("logisticsRecyleStatus") String logisticsRecyleStatus,
			@Param("financeRecyleStatus") String financeRecyleStatus);

	public <T> List<T> selectByOrderNoSigned(@Param("orderNoSigned") String orderNoSigned);

	public <T> List<T> queryByParamsForpeat(@Param("params") Map<String, Object> params);
}