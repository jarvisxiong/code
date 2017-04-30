package com.ffzx.promotion.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.ffzx.commerce.framework.annotation.MyBatisDao;
import com.ffzx.commerce.framework.dao.CrudMapper;
import com.ffzx.promotion.api.dto.CouponAdminCouponGrant;

/**
 * coupon_admin_coupon_grant数据库操作接口
 * 
 * @author yuzhao.xu
 * @email  yuzhao.xu@ffzxnet.com
 * @date 2016年5月3日 下午6:17:35
 * @version 1.0.0
 * @copyright www.ffzxnet.com
 */
@MyBatisDao
public interface CouponAdminCouponGrantMapper extends CrudMapper {
	
	/**
	 * 根据发放券id删除对应所有优惠券
	 * @param CouponGrantId
	 * @return 数量
	 */
	public int deleteByCouponGrantId(String CouponGrantId);
	
	/**
	 * 查询所有优惠券id
	 * @param CouponGrantId  优惠券发放id
	 * @return
	 */
	public List<String> selectCouponAdminId(@Param("couponGrantId")  String couponGrantId);
	
	/****
	 * 根据发放发ID获取选择的优惠券
	 * @param couponGrantId
	 * @return
	 */
	public List<CouponAdminCouponGrant> selectByGrantId(@Param("couponGrantId")  String couponGrantId);
	
	public List<CouponAdminCouponGrant> selectByGrantIdStr(@Param("params")Map<String, Object> params);
}