package com.ffzx.promotion.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ffzx.commerce.framework.annotation.MyBatisDao;
import com.ffzx.commerce.framework.dao.CrudMapper;

/**
 * coupon_grant_member数据库操作接口
 * 
 * @author yuzhao.xu
 * @email  yuzhao.xu@ffzxnet.com
 * @date 2016年5月3日 下午6:17:35
 * @version 1.0.0
 * @copyright www.ffzxnet.com
 */
@MyBatisDao
public interface CouponGrantMemberMapper extends CrudMapper {

	/**
	 * 根据发放券id删除对应所有数据
	 * @param CouponGrantId
	 * @return 数量
	 */
	public int deleteByCouponGrantId(@Param("couponGrantId") String couponGrantId);
	/**
	 * 查询所有会员id
	 * @param CouponGrantId  优惠券发放id
	 * @return
	 */
	public List<String> selectMemberid(@Param("couponGrantId")  String couponGrantId);
}