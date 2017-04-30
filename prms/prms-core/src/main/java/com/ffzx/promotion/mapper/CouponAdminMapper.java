package com.ffzx.promotion.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.ffzx.commerce.framework.annotation.MyBatisDao;
import com.ffzx.commerce.framework.dao.CrudMapper;
import com.ffzx.commerce.framework.page.Page;
import com.ffzx.promotion.api.dto.CouponAdmin;
import com.ffzx.promotion.api.dto.CouponReceive;

/**
 * coupon_admin数据库操作接口
 * 
 * @author yuzhao.xu
 * @email  yuzhao.xu@ffzxnet.com
 * @date 2016年5月3日 下午6:17:35
 * @version 1.0.0
 * @copyright www.ffzxnet.com
 */
@MyBatisDao
public interface CouponAdminMapper extends CrudMapper {

	/**
	 * 根据查询条件查询优惠券列表
	 * 
	 * @param map
	 * @return
	 */
	 public List<CouponAdmin> findCouponAdmin(@Param("page") Page page,
				@Param("orderByField") String orderByField,
				@Param("orderBy") String orderBy,
				@Param("params") Map<String, Object> params);
	 
	 /**
	 * 根据发放编码获取优惠券列表
	 * @param params
	 * @return List<CouponAdmin>    返回类型
	  */
	 public List<CouponAdmin> findCouponAdminByGrantNumber(@Param("params") Map<String, Object> params);
}