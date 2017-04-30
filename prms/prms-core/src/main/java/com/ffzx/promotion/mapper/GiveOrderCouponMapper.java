package com.ffzx.promotion.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ffzx.commerce.framework.annotation.MyBatisDao;
import com.ffzx.commerce.framework.dao.CrudMapper;
import com.ffzx.promotion.api.dto.GiveOrderCoupon;

/**
 * give_order_coupon数据库操作接口
 * 
 * @author ffzx
 * @date 2016-09-22 15:11:01
 * @version 1.0.0
 * @copyright www.ffzxnet.com
 */
@MyBatisDao
public interface GiveOrderCouponMapper extends CrudMapper {
	
	/***
	  * 批量更新移除字段
	  * @param giveOrderCouponList
	  * @date 2016年10月17日 下午6:51:00
	  * @author ying.cai
	  * @email ying.cai@ffzxnet.com
	  */
	 public void updateDelFlag(@Param("giveOrderCouponList")List<GiveOrderCoupon> giveOrderCouponList);
}