package com.ffzx.order.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.ffzx.commerce.framework.annotation.MyBatisDao;
import com.ffzx.commerce.framework.dao.CrudMapper;
import com.ffzx.order.api.dto.AftersalePickup;

/**
 * aftersale_pickup数据库操作接口
 * 
 * @author ffzx
 * @date 2016-05-11 11:50:59
 * @version 1.0.0
 * @copyright www.ffzxnet.com
 */
@MyBatisDao
public interface AftersalePickupMapper extends CrudMapper {
	
	public AftersalePickup selectByPickupNo(@Param("pickupNo")String pickupNo);
	
	public int updateByPickupNo(@Param("params")Map<String, Object> params);
	
	public AftersalePickup selectById(@Param("id")String id);
	
	public AftersalePickup selectByIdOrNo(@Param("params")Map<String, Object> params);
	
	public int deletePickupByNo(@Param("pickupNo")String pickupNo);
	
	public int cancelPick(@Param("params")Map<String, Object> params);

	/**
	 * 雷------2016年9月7日
	 * @Title: selectByAftersalPickupBy
	 * @Description: 处理selectByPickupNo接口的错误
	 * @param @param pickupNo
	 * @param @return    设定文件
	 * @return List<AftersalePickup>    返回类型
	 * @throws
	 */
	public List<AftersalePickup> selectByAftersalPickupBy(@Param("pickupNo") String pickupNo);

	/**
	 * 雷------2016年9月8日
	 * @Title: getPickupStateByPickNo
	 * @Description: 售后取消审核，根据取货单查询状态
	 * @param @param pickupNo
	 * @param @return    设定文件
	 * @return AftersalePickup    返回类型
	 * @throws
	 */
	public AftersalePickup getPickupStateByPickNo(@Param("pickupNo") String pickupNo);
}