package com.ffzx.promotion.service;

import java.util.List;
import java.util.Map;

import com.ffzx.commerce.framework.enums.ServiceCode;
import com.ffzx.commerce.framework.exception.ServiceException;
import com.ffzx.commerce.framework.page.Page;
import com.ffzx.commerce.framework.service.BaseCrudService;
import com.ffzx.commerce.framework.system.entity.SysUser;
import com.ffzx.member.api.dto.Member;
import com.ffzx.order.api.dto.OmsOrder;
import com.ffzx.promotion.api.dto.AppRecommendAwards;
import com.ffzx.promotion.api.dto.CouponAdminCouponGrant;
import com.ffzx.promotion.api.dto.CouponGrant;
import com.ffzx.promotion.api.dto.RedpackageGrant;

/**
 * coupon_grant数据库操作接口
 * 
 * @author yuzhao.xu
 * @email  yuzhao.xu@ffzxnet.com
 * @date 2016年5月4日 下午5:27:16
 * @version 1.0.0
 * @copyright www.ffzxnet.com
 */
public interface CouponGrantService extends BaseCrudService {

	/**
	 * 分页查询
	 * @author yuzhao.xu
	 * @email  yuzhao.xu@ffzxnet.com
	 * @date 2016年5月4日 下午5:27:16
	 * @param page
	 * @param orderByField
	 * @param orderBy
	 * @param couponGrant  优惠券发放对象
	 * @return
	 * @throws ServiceException
	 */
	public List<CouponGrant> findList(Page page, String orderByField, String orderBy, CouponGrant couponGrant) throws ServiceException;
	/**
	 * 新增或编辑优惠券
	 * @param couponGrant
	 * @return
	 * @throws ServiceException
	 */
	public ServiceCode saveOrUpdateCoupon(CouponGrant couponGrant,String userList,String couponAdminList)  throws Exception;
	
	/***
	 * 发放优惠券
	 */
	public void sendCouponAdmin()throws ServiceException;
	/**
	 * 更新指定用户优惠券
	 */
	public void updateMember(CouponGrant grant,String granId,SysUser suser,List<CouponAdminCouponGrant> couponGrantList);
	/**
	 * 发放优惠券，仅限新用户
	 * @return 
	 */
	public void updateNewUserGrant()throws ServiceException;
	/**
	 * 发放优惠券，跑批扫描所有新用户
	 * @throws ServiceException
	 */
	public void updateNewUserGrantDate()throws ServiceException;
	
	/**
	 * 反放单个user的优惠券
	 * @throws ServiceException
	 */
	public void updateNewUserCouopon(Member m) throws ServiceException;
	/**
	 * user购买虚拟商品优惠券
	 * @throws ServiceException
	 */
	public void updateBuyCouopon(OmsOrder omsOrder) throws ServiceException;
	/**
	 * 删除所有发放管理的数据
	 */
	public int delete(String id);
	/**
	 * 新增或编辑推荐有奖
	 */
	public ServiceCode saveOrUpdateRecommend(AppRecommendAwards appRecommendAwards)throws Exception;

	
	/**
	 * 导入数据
	 * @param listRow
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> importExcel(List<String[]> listRow) throws Exception;

	/**
	 * 弹窗数据列表
	 */
	public List<CouponGrant> findDataPicker(Page page, String grantStr)throws ServiceException;

	/**
	 * 获取详细信息
	 * @param id
	 * @param isHandleInfo 是否显示处理信息，编辑为false，详情为true
	 * @return
	 */
	public CouponGrant findBydetail(String id);
	
	/**
	 * 更新发放
	 */
	public void updateGrantCoupon(CouponGrant couponGrant);


}