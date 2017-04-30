package com.ffzx.promotion.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.ffzx.commerce.framework.annotation.MyBatisDao;
import com.ffzx.commerce.framework.dao.CrudMapper;
import com.ffzx.promotion.api.dto.CouponAdminCategory;

/**
 * coupon_admin_category数据库操作接口
 * 
 * @author yuzhao.xu
 * @email  yuzhao.xu@ffzxnet.com
 * @date 2016年5月3日 下午6:17:35
 * @version 1.0.0
 * @copyright www.ffzxnet.com
 */
@MyBatisDao
public interface CouponAdminCategoryMapper extends CrudMapper {

	public List<CouponAdminCategory> findCommodity(@Param("params")Map<String, Object> params);
	
	public List<String> selectByCategoryCommodity(@Param("params")Map<String, Object> params);
	
	public List<String> findCommodityName(@Param("params")Map<String, Object> params);
	
	public int deletesome(@Param("list")List<String>list);

}