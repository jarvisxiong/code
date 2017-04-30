package com.ffzx.order.mapper;

import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.ffzx.commerce.framework.annotation.MyBatisDao;
import com.ffzx.commerce.framework.dao.CrudMapper;

/**
 * purchase_push数据库操作接口
 * 
 * @author ffzx
 * @date 2016-06-08 17:29:40
 * @version 1.0.0
 * @copyright www.ffzxnet.com
 */
@MyBatisDao
public interface PurchasePushMapper extends CrudMapper {
	
	public <T> T selectMaxPushDate(@Param("params") Map<String, Object> params);

}