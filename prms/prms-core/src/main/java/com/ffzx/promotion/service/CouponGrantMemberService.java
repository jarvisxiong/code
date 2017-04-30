package com.ffzx.promotion.service;

import java.util.List;

import com.ffzx.commerce.framework.enums.ServiceCode;
import com.ffzx.commerce.framework.service.BaseCrudService;

/**
 * coupon_grant_member数据库操作接口
 * 
 * @author yuzhao.xu
 * @email  yuzhao.xu@ffzxnet.com
 * @date 2016-05-03 17:58:03
 * @version 1.0.0
 * @copyright www.ffzxnet.com
 */
public interface CouponGrantMemberService extends BaseCrudService {

	 /**
	 * @Description: TODO
	 * @author yuzhao.xu
	 * @email  yuzhao.xu@ffzxnet.com
	 * @date 2016年5月7日 下午2:26:37
	 * @param userList   会员集合id
	 * @param couoponGrantId  优惠券发放id
	 * @param isdelete   是否删除数据后新增
	 * @return  ServiceCode
	 * 批量添加或者批量添加和删除
	 */
	/**
	 */
	public ServiceCode manyInsertOrdelete(String userList,String couoponGrantId,boolean isdelete,boolean isadd);
	

	/**
	 * 查询所有会员id
	 * @param CouponGrantId  优惠券发放id
	 * @return
	 */
	public List<String> selectMemberid(String CouponGrantId);

}