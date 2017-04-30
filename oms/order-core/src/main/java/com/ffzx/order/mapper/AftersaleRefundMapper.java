package com.ffzx.order.mapper;

import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.ffzx.commerce.framework.annotation.MyBatisDao;
import com.ffzx.commerce.framework.dao.CrudMapper;
import com.ffzx.order.api.dto.AftersaleRefund;

/**
 * aftersale_refund数据库操作接口
 * 
 * @author ffzx
 * @date 2016-05-11 11:50:59
 * @version 1.0.0
 * @copyright www.ffzxnet.com
 */
@MyBatisDao
public interface AftersaleRefundMapper extends CrudMapper {

	/**
	* 根据条件查询退款单信息
	* @Title: findRefundInfo 
	* @param params
	* @return AftersaleRefund    返回类型
	 */
	public AftersaleRefund findRefundInfo(@Param("params") Map<String,Object> params);
	
	public int deleteRefundByNo(@Param("refundNo")String refundNo);
	
}