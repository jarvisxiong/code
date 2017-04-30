package com.ffzx.promotion.service;

import java.util.List;

import com.ffzx.commerce.framework.enums.ServiceCode;
import com.ffzx.commerce.framework.service.BaseCrudService;

/**
 * coupon_admin_coupon_grant数据库操作接口
 * 
 * @author yuzhao.xu
 * @email  yuzhao.xu@ffzxnet.com
 * @date 2016-05-03 17:58:03
 * @version 1.0.0
 * @copyright www.ffzxnet.com
 */
public interface CouponAdminCouponGrantService extends BaseCrudService {

	/**
	 * @Description: 批量添加或者批量添加和删除优惠券
	 * @author luozi
	 * @date 2016年5月7日 下午2:26:37
	 * @param couponAdminList   会员集合id
	 * @param couoponGrantId  优惠券发放id
	 * @param isdelete   是否删除数据后新增
	 * @return  ServiceCode
	 * 
	 */
	/**
	 */
	public ServiceCode manyInsertOrdelete(String couponAdminList,String couoponGrantId,boolean isdelete);
	
	/**
	 * 查询所有优惠券id
	 * @param CouponGrantId  优惠券发放id
	 * @return
	 */
	public List<String> selectCouponAdminId(String CouponGrantId);

}