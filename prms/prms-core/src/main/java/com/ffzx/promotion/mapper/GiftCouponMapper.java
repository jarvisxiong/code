package com.ffzx.promotion.mapper;

import com.ffzx.commerce.framework.annotation.MyBatisDao;
import com.ffzx.commerce.framework.dao.CrudMapper;
import com.ffzx.promotion.api.dto.GiftCoupon;

/**
 * gift_coupon数据库操作接口
 * 
 * @author ffzx
 * @date 2016-09-12 11:25:09
 * @version 1.0.0
 * @copyright www.ffzxnet.com
 */
@MyBatisDao
public interface GiftCouponMapper extends CrudMapper {

	public int updateByGiveDelFlag(GiftCoupon giftCoupon);
}