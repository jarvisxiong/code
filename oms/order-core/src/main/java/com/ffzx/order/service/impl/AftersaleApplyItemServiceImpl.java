package com.ffzx.order.service.impl;

import com.ffzx.commerce.framework.dao.CrudMapper;
import com.ffzx.commerce.framework.enums.ServiceCode;
import com.ffzx.commerce.framework.enums.ServiceResultCode;
import com.ffzx.commerce.framework.exception.ServiceException;
import com.ffzx.commerce.framework.page.Page;
import com.ffzx.commerce.framework.service.impl.BaseCrudServiceImpl;
import com.ffzx.commerce.framework.utils.StringUtil;
import com.ffzx.order.api.dto.AftersaleApplyItem;
import com.ffzx.order.api.dto.OmsOrderdetail;
import com.ffzx.order.mapper.AftersaleApplyItemMapper;
import com.ffzx.order.service.AftersaleApplyItemService;

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
@Service("aftersaleApplyItemService")
public class AftersaleApplyItemServiceImpl extends BaseCrudServiceImpl implements AftersaleApplyItemService {

    @Resource
    private AftersaleApplyItemMapper aftersaleApplyItemMapper;

    @Override
    public CrudMapper init() {
        return aftersaleApplyItemMapper;
    }

	@Override
	public List<AftersaleApplyItem> findAftersaleApplyItemList(Page pageObj, String orderByField, String orderBy,
			AftersaleApplyItem aftersaleApplyItem) throws ServiceException {
		Map<String, Object> params=new HashMap<String, Object>();
		//申请人手机号
		if(StringUtil.isNotNull(aftersaleApplyItem.getApplyId())){
			params.put("applyId",aftersaleApplyItem.getApplyId());
		}
		if(StringUtil.isNotNull(aftersaleApplyItem.getSkuId())){
			params.put("skuId",aftersaleApplyItem.getSkuId());
		}
		if(StringUtil.isNotNull(aftersaleApplyItem.getApplyNo())){
			params.put("applyNo",aftersaleApplyItem.getApplyNo());
		}
		return this.aftersaleApplyItemMapper.selectByParams(params);
	}

	@Override
	@Transactional(rollbackFor=Exception.class)
	public ServiceCode saveAftersaleApplyItem(AftersaleApplyItem aftersaleApplyItem) throws ServiceException {
		int result = 0;
		try {
			result = this.aftersaleApplyItemMapper.insertSelective(aftersaleApplyItem);
		} catch (Exception e) {
			logger.error("", e);
			throw new ServiceException(e);
		}		
		return result > 0 ? ServiceResultCode.SUCCESS : ServiceResultCode.FAIL;
	}

	@Override
	public List<AftersaleApplyItem> findByApplyId(String applyId) throws ServiceException {
		if(StringUtil.isNotNull(applyId)){
			return this.aftersaleApplyItemMapper.selectByApplyId(applyId);
		}
		return null;
	}

	/***
	 * 
	 * @param params
	 * @return
	 * @author yinglong.huang
	 * @email yinglong.huang@ffzxnet.com
	 * @date 2017年3月31日 上午9:34:46
	 * @version V1.0
	 * @return 
	 */
	@Override
	public int getHadRreturnCount(Map<String, Object> params) {
		return this.aftersaleApplyItemMapper.selectHadRreturnCount(params);
	}


}