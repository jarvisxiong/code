package com.ffzx.promotion.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.ffzx.commerce.framework.annotation.MyBatisDao;
import com.ffzx.commerce.framework.dao.CrudMapper;
import com.ffzx.commerce.framework.page.Page;
import com.ffzx.promotion.api.dto.CouponAdmin;
import com.ffzx.promotion.api.dto.CouponGrant;

/**
 * coupon_grant数据库操作接口
 * 
 * @author yuzhao.xu
 * @email  yuzhao.xu@ffzxnet.com
 * @date 2016年5月3日 下午6:17:35
 * @version 1.0.0
 * @copyright www.ffzxnet.com
 */
@MyBatisDao
public interface CouponGrantMapper extends CrudMapper {
	/**
	 * 根据定时自动发放优惠券
	 * update  is_grant ='1',
	 */
	public int updateByGrant(CouponGrant couponGrant);
	
	/**
	 * 优惠券领取列表
	 * @param page
	 * @param params
	 * @return List<CouponAdmin>    返回类型
	  */
	 public List<Map<String, Object>> findCouponAdminByGrantId(@Param("page") Page page,
			 	@Param("params") Map<String, Object> params);
	 
	 public List<CouponGrant> selectByParamsleftReceive(@Param("params")Map<String, Object>params);
	
}