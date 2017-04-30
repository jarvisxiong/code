package com.ffzx.promotion.service;

import java.util.List;
import java.util.Map;

import com.ffzx.commerce.framework.exception.ServiceException;
import com.ffzx.commerce.framework.page.Page;
import com.ffzx.commerce.framework.service.BaseCrudService;
import com.ffzx.member.api.dto.Member;
import com.ffzx.promotion.api.dto.CouponGrant;
import com.ffzx.promotion.api.dto.CouponReceive;

/**
 * coupon_receive数据库操作接口
 * 
 * @author yuzhao.xu
 * @email  yuzhao.xu@ffzxnet.com
 * @date 2016-05-03 17:58:03
 * @version 1.0.0
 * @copyright www.ffzxnet.com
 */
public interface CouponReceiveService extends BaseCrudService {

	
	/**
	 * 优惠券发放分页查询
	 * @param page
	 * @param orderByField
	 * @param orderBy
	 * @param couponReceive  CouponReceive对象
	 * @return
	 * @throws ServiceException
	 * @author zi.luo
	 * @date 2016-05-05 18:03
	 */
	public List<CouponReceive> findList(Page page, String orderByField, String orderBy, CouponReceive couponReceive) throws ServiceException;

	/**
	 * 一次性insert多条数据
	 * @param couponReceives
	 */
	public void insertAllCoupon(List<CouponReceive> couponReceives)throws ServiceException;
	/**
	 * 新用户专享插入,并且更新
	 */
	public void insertUserCoponUpdateUser(List<CouponReceive> couponReceives,List<Member> updateMembers)throws ServiceException;
	/**
	 * 新用户专享发放插入,并且更新,falg true更新会员
	 */
	public void insertUserGrantCoponUpdateUser(List<CouponReceive> couponReceives,List<Member> updateMembers,boolean flag)throws ServiceException;
	/**
	 * 用户下单购买优惠券
	 */
	public void insertBuyCoponUpdateUser(List<CouponReceive> couponReceives)throws ServiceException;
	
	/**
	 * 一次insert，查看是否存在
	 */
	public void insertIsExAllCoupon(List<CouponReceive> couponReceives)throws ServiceException;
	/**
	 * 删除关联发放id的数据
	 */
	public int deleteByPrimaryKeyGrantId(String grantId,int deleteNum);
	
	/**
	 * 更新发放条件
	 */
	public int updateGrant(CouponGrant gran);
	
	public int selectReceiveCount( CouponReceive couponReceive);
}