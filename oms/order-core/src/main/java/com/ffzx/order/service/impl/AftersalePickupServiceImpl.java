package com.ffzx.order.service.impl;

import com.ffzx.commerce.framework.dao.CrudMapper;
import com.ffzx.commerce.framework.enums.ServiceCode;
import com.ffzx.commerce.framework.enums.ServiceResultCode;
import com.ffzx.commerce.framework.exception.ServiceException;
import com.ffzx.commerce.framework.page.Page;
import com.ffzx.commerce.framework.service.impl.BaseCrudServiceImpl;
import com.ffzx.commerce.framework.utils.DateUtil;
import com.ffzx.commerce.framework.utils.RedisWebUtils;
import com.ffzx.commerce.framework.utils.StringUtil;
import com.ffzx.order.api.dto.AftersaleApply;
import com.ffzx.order.api.dto.AftersalePickup;
import com.ffzx.order.constant.OrderDetailStatusConstant;
import com.ffzx.order.mapper.AftersalePickupMapper;
import com.ffzx.order.service.AftersalePickupService;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 
 * @author ffzx
 * @date 2016-05-11 11:50:59
 * @version 1.0.0
 * @copyright www.ffzxnet.com
 */
@Service("aftersalePickupService")
public class AftersalePickupServiceImpl extends BaseCrudServiceImpl implements AftersalePickupService {

	@Resource
	private AftersalePickupMapper aftersalePickupMapper;

	@Override
	public CrudMapper init() {
		return aftersalePickupMapper;
	}

	/**
	 * 
	 * 雷-----2016年8月10日 (非 Javadoc)
	 * <p>
	 * Title: findAftersalePickupList_Like
	 * </p>
	 * <p>
	 * Description: 获取取货单集合(8-5新需求)
	 * </p>
	 * 
	 * @param pageObj
	 * @param orderByField
	 * @param orderBy
	 * @param aftersaleApply
	 * @param pickup
	 * @return
	 * @throws ServiceException
	 * @see com.ffzx.order.service.AftersalePickupService#findAftersalePickupList_Like(com.ffzx.commerce.framework.page.Page, java.lang.String, java.lang.String, com.ffzx.order.api.dto.AftersaleApply, com.ffzx.order.api.dto.AftersalePickup)
	 */
	@Override
	public List<AftersalePickup> findAftersalePickupList_Like(Page pageObj, String orderByField, String orderBy, AftersaleApply aftersaleApply, AftersalePickup pickup) throws ServiceException {
		Map<String, Object> params = new HashMap<String, Object>();
		// 申请人手机号
		if (StringUtil.isNotNull(aftersaleApply.getApplyPersonPhone())) {
			params.put("applyPersonPhone_like", aftersaleApply.getApplyPersonPhone());
		}
		/*
		 * //申请状态 if(StringUtil.isNotNull(aftersaleApply.getApplyStatus())){ params.put("applyStatus", aftersaleApply.getApplyStatus()); }
		 */

		// 申请状态
		if (StringUtil.isNotNull(pickup.getPickupStatus())) {
			params.put("pickupStatus", pickup.getPickupStatus());
		}

		try {
			if (StringUtil.isNotNull(aftersaleApply.getApplyStartDate())) {
				params.put("applyStartDate", DateUtil.parseTime(aftersaleApply.getApplyStartDate()));
			}
			if (StringUtil.isNotNull(aftersaleApply.getApplyEndDate())) {
				params.put("applyEndDate", DateUtil.parseTime(aftersaleApply.getApplyEndDate()));
			}
			if (StringUtil.isNotNull(aftersaleApply.getStorageApproveEndDate())) {
				params.put("storageApproveEndDate", DateUtil.parseTime(aftersaleApply.getStorageApproveEndDate()));
			}
			if (StringUtil.isNotNull(aftersaleApply.getStorageApproveStartDate())) {
				params.put("storageApproveStartDate", DateUtil.parseTime(aftersaleApply.getStorageApproveStartDate()));
			}
			if (StringUtil.isNotNull(aftersaleApply.getServiceApproveEndDate())) {
				params.put("serviceApproveEndDate", DateUtil.parseTime(aftersaleApply.getServiceApproveEndDate()));
			}
			if (StringUtil.isNotNull(aftersaleApply.getServiceApproveStartDate())) {
				params.put("serviceApproveStartDate", DateUtil.parseTime(aftersaleApply.getServiceApproveStartDate()));
			}
		} catch (Exception e) {
			logger.error("AftersalePickupServiceImpl.findAftersalePickupList() Exception=》", e);
			throw new ServiceException(e);
		}
		if (StringUtil.isNotNull(aftersaleApply.getOrderNo())) {
			params.put("orderNo_like", aftersaleApply.getOrderNo());
		}
		if (StringUtil.isNotNull(aftersaleApply.getApplyType())) {
			params.put("applyType", aftersaleApply.getApplyType());
		}
		if (StringUtil.isNotNull(aftersaleApply.getApplyPersonName())) {
			params.put("applyPersonName", aftersaleApply.getApplyPersonName());
		}
		if (StringUtil.isNotNull(aftersaleApply.getApplyNo())) {
			params.put("applyNo_like", aftersaleApply.getApplyNo());
		}
		if (StringUtil.isNotNull(aftersaleApply.getPickupNo())) {
			params.put("pickupNo_like", aftersaleApply.getPickupNo());
		}
		if (StringUtil.isNotNull(aftersaleApply.getRefundNo())) {
			params.put("refundNo_like", aftersaleApply.getRefundNo());
		}
		params.put("delFlag", OrderDetailStatusConstant.NO);
		return this.aftersalePickupMapper.selectByPage(pageObj, orderByField, orderBy, params);
	}

	@Override
	public List<AftersalePickup> findAftersalePickupList(Page pageObj, String orderByField, String orderBy, AftersaleApply aftersaleApply, AftersalePickup pickup) throws ServiceException {
		Map<String, Object> params = new HashMap<String, Object>();
		// 申请人手机号
		if (StringUtil.isNotNull(aftersaleApply.getApplyPersonPhone())) {
			params.put("applyPersonPhone", aftersaleApply.getApplyPersonPhone());
		}
		/*
		 * //申请状态 if(StringUtil.isNotNull(aftersaleApply.getApplyStatus())){ params.put("applyStatus", aftersaleApply.getApplyStatus()); }
		 */

		// 申请状态
		if (StringUtil.isNotNull(pickup.getPickupStatus())) {
			params.put("pickupStatus", pickup.getPickupStatus());
		}

		try {
			if (StringUtil.isNotNull(aftersaleApply.getApplyStartDate())) {
				params.put("applyStartDate", DateUtil.parseTime(aftersaleApply.getApplyStartDate()));
			}
			if (StringUtil.isNotNull(aftersaleApply.getApplyEndDate())) {
				params.put("applyEndDate", DateUtil.parseTime(aftersaleApply.getApplyEndDate()));
			}
			if (StringUtil.isNotNull(aftersaleApply.getStorageApproveEndDate())) {
				params.put("storageApproveEndDate", DateUtil.parseTime(aftersaleApply.getStorageApproveEndDate()));
			}
			if (StringUtil.isNotNull(aftersaleApply.getStorageApproveStartDate())) {
				params.put("storageApproveStartDate", DateUtil.parseTime(aftersaleApply.getStorageApproveStartDate()));
			}
			if (StringUtil.isNotNull(aftersaleApply.getServiceApproveEndDate())) {
				params.put("serviceApproveEndDate", DateUtil.parseTime(aftersaleApply.getServiceApproveEndDate()));
			}
			if (StringUtil.isNotNull(aftersaleApply.getServiceApproveStartDate())) {
				params.put("serviceApproveStartDate", DateUtil.parseTime(aftersaleApply.getServiceApproveStartDate()));
			}
		} catch (Exception e) {
			logger.error("AftersalePickupServiceImpl.findAftersalePickupList() Exception=》", e);
			throw new ServiceException(e);
		}
		if (StringUtil.isNotNull(aftersaleApply.getOrderNo())) {
			params.put("orderNo", aftersaleApply.getOrderNo());
		}
		if (StringUtil.isNotNull(aftersaleApply.getApplyType())) {
			params.put("applyType", aftersaleApply.getApplyType());
		}
		if (StringUtil.isNotNull(aftersaleApply.getApplyPersonName())) {
			params.put("applyPersonName", aftersaleApply.getApplyPersonName());
		}
		if (StringUtil.isNotNull(aftersaleApply.getApplyNo())) {
			params.put("applyNo", aftersaleApply.getApplyNo());
		}
		if (StringUtil.isNotNull(aftersaleApply.getPickupNo())) {
			params.put("pickupNo", aftersaleApply.getPickupNo());
		}
		if (StringUtil.isNotNull(aftersaleApply.getRefundNo())) {
			params.put("refundNo", aftersaleApply.getRefundNo());
		}
		params.put("delFlag", OrderDetailStatusConstant.NO);
		return this.aftersalePickupMapper.selectByPage(pageObj, orderByField, orderBy, params);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public ServiceCode saveAftersalePickup(AftersalePickup aftersalePickup) throws ServiceException {
		int result = 0;
		result = this.aftersalePickupMapper.insertSelective(aftersalePickup);
		return result > 0 ? ServiceResultCode.SUCCESS : ServiceResultCode.FAIL;
	}

	@Override
	public AftersalePickup getAftersalPickupByNo(String pickupNo) throws ServiceException {
		AftersalePickup pickup = null;
		if (StringUtil.isNotNull(pickupNo)) {
			pickup = this.aftersalePickupMapper.selectByPickupNo(pickupNo);
		}
		return pickup;
	}
	/**
	 * 雷-----2016年9月7日
	 * (非 Javadoc)
	 * <p>Title: getAftersalPickupByPickNo</p>
	 * <p>Description: </p>
	 * @param no
	 * @return
	 * @see com.ffzx.order.service.AftersalePickupService#getAftersalPickupByPickNo(java.lang.String)
	 */
	@Override
	public List<AftersalePickup> getAftersalPickupByPickNo(String pickupNo) {
		List<AftersalePickup> pickup =new ArrayList<AftersalePickup>();
		if (StringUtil.isNotNull(pickupNo)) {
			pickup = this.aftersalePickupMapper.selectByAftersalPickupBy(pickupNo);
		}
		return pickup;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public int updatePickStatus(Map<String, Object> params) throws ServiceException {

		return this.aftersalePickupMapper.updateByPickupNo(params);
	}

	@Override
	public AftersalePickup getAftersalePickupById(String id) throws ServiceException {
		AftersalePickup pickup = null;
		if (StringUtil.isNotNull(id)) {
			pickup = this.aftersalePickupMapper.selectById(id);
		}
		return pickup;
	}

	@Override
	public AftersalePickup findAftersalePickupByParams(Map<String, Object> params) throws ServiceException {

		return this.aftersalePickupMapper.selectByIdOrNo(params);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public int deletePickupByPickupNo(String pickupNo) throws ServiceException {

		return this.aftersalePickupMapper.deletePickupByNo(pickupNo);
	}

	/**
	 * 雷-----2016年8月16日
	 * (非 Javadoc)
	 * <p>Title: cancelPick</p>
	 * <p>Description:删除（取消）取货单 </p>
	 * @param params
	 * @return
	 * @see com.ffzx.order.service.AftersalePickupService#cancelPick(java.util.Map)
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public int cancelPick(Map<String, Object> params) {
		return this.aftersalePickupMapper.cancelPick(params);
	}

	/**
	 * 雷-----2016年9月8日
	 * (非 Javadoc)
	 * <p>Title: getPickupStateByPickNo</p>
	 * <p>Description: 售后取消审核，根据取货单查询状态</p>
	 * @param pickupNo
	 * @return
	 * @see com.ffzx.order.service.AftersalePickupService#getPickupStateByPickNo(java.lang.String)
	 */
	@Override
	public AftersalePickup getPickupStateByPickNo(String pickupNo) {
		return this.aftersalePickupMapper.getPickupStateByPickNo(pickupNo);
	}


}