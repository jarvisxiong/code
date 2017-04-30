package com.ffzx.promotion.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.ffzx.commerce.framework.annotation.MyBatisDao;
import com.ffzx.commerce.framework.dao.CrudMapper;
import com.ffzx.commerce.framework.page.Page;
import com.ffzx.promotion.api.dto.CouponReceive;

/**
 * coupon_receive数据库操作接口
 * 
 * @author yuzhao.xu
 * @email  yuzhao.xu@ffzxnet.com
 * @date 2016年5月3日 下午6:17:35
 * @version 1.0.0
 * @copyright www.ffzxnet.com
 */
@MyBatisDao
public interface CouponReceiveMapper extends CrudMapper {

	/**
	 * 根据查询条件查询优惠券领取列表
	 * 
	 * @param map
	 * @return
	 */
	 public List<CouponReceive> findByPage(@Param("page") Page page,
				@Param("orderByField") String orderByField,
				@Param("orderBy") String orderBy,
				@Param("params") Map<String, Object> params,
				@Param("simpleCountSql") Boolean simpleCountSql
			 	);
	 public int updateiosRecive(CouponReceive couponReceive);
	 public List<CouponReceive>findListByMember(@Param("page") Page page,
			 	@Param("orderByField") String orderByField,
				@Param("orderBy") String orderBy,
				@Param("params") Map<String, Object> params);
	 

	public int selectReceiveCount(@Param("params") Map<String, Object> params);
	 /**
	  * 根据id获取用户关联优惠券的所有value
	 * @param id   优惠券领取id
	  */
	 public CouponReceive selectByPrimaryKeyAll(@Param("id")String id);
	 
	 public CouponReceive selectByPrimaryKeyApp(@Param("id")String id);
	 
	 public List<CouponReceive> selectReceiveCoupon(@Param("params")Map<String, Object> params);
	 
	 public int insertManyValue(@Param("list")List<CouponReceive> list);
	 
	 public int deleteByPrimaryKeyGrantId(@Param("grantId")String grantId,@Param("deleteNum")Integer deleteNum);
	 /**
	 * 获取我的优惠券列表
	 * @return List<CouponReceive>    返回类型
	  */
	 public List<CouponReceive> findMyCouponList(@Param("page") Page page,@Param("params") Map<String, Object> params);
	 
	 /**
	 * 获取即将过期的优惠券
	 * @param params
	 * @return List<CouponReceive>    返回类型
	  */
	 public List<CouponReceive> findListByOverDate(@Param("params") Map<String, Object> params);
	 
	 public List<CouponReceive> selectReceiveCountGroup(@Param("grantId")String grantId);
	 
	 public List<CouponReceive> selectCouponGrantGroup(@Param("params")Map<String, Object>params);
	 
}