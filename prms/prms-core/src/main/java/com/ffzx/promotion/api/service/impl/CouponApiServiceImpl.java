package com.ffzx.promotion.api.service.impl;

import com.ffzx.basedata.api.service.CodeRuleApiService;
import com.ffzx.commerce.framework.constant.Constant;
import com.ffzx.commerce.framework.dao.CrudMapper;
import com.ffzx.commerce.framework.dto.ResultDto;
import com.ffzx.commerce.framework.exception.ServiceException;
import com.ffzx.commerce.framework.page.Page;
import com.ffzx.commerce.framework.service.impl.BaseCrudServiceImpl;
import com.ffzx.commerce.framework.utils.StringUtil;
import com.ffzx.promotion.api.dto.CouponAdmin;
import com.ffzx.promotion.api.service.CouponApiService;
import com.ffzx.promotion.mapper.CouponAdminMapper;
import com.ffzx.promotion.mapper.CouponGrantMapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
* @Description: TODO
* @author yuzhao.xu
* @email  yuzhao.xu@ffzxnet.com
* @date 2016年5月11日 下午4:25:32
* @version V1.0 
*
*/
@Service("couponApiServiceImpl")
public class CouponApiServiceImpl extends BaseCrudServiceImpl implements CouponApiService {

    @Resource
    private CouponAdminMapper couponAdminMapper;
    
    @Override
    public CrudMapper init() {
        return couponAdminMapper;
    }
	@Override
	public ResultDto findCoupon(Page page,String orderByField,String orderBy,CouponAdmin couponAdmin) throws ServiceException {
		// TODO Auto-generated method stub
		ResultDto rsDto = null;
		Map<String, Object> params=new HashMap<>();
		try{
			getParamDate(params,couponAdmin);
			List<CouponAdmin> couponAdmins=  init().selectByPage(page, StringUtil.isEmpty(orderByField)?Constant.ORDER_BY_FIELD_CREATE:orderByField,
					StringUtil.isEmpty(orderBy)?Constant.ORDER_BY:orderBy, params);
			page.setRecords(couponAdmins);
			rsDto = new ResultDto(ResultDto.OK_CODE, "success");
			rsDto.setData(page);
		}catch(Exception e){
			logger.error("", e);
//			rsDto = new ResultDto(ResultDto.ERROR_CODE, "faild:" + e);
			throw new ServiceException(e);
		}
		return rsDto;
	}
	
	private void getParamDate(Map<String, Object> params,CouponAdmin couponAdmin){
		if(StringUtil.isNotNull(couponAdmin.getDelFlag())){
			params.put("delFlag", couponAdmin.getDelFlag());
		}
		if(StringUtil.isNotNull(couponAdmin.getEffectiveDateStr())){
			params.put("effectiveDateStr", couponAdmin.getEffectiveDateStr());
		}
		if(StringUtil.isNotNull(couponAdmin.getName())){
			params.put("name", couponAdmin.getName());
		}
		if(StringUtil.isNotNull(couponAdmin.getNumber())){
			params.put("number", couponAdmin.getNumber());
		}
		if(StringUtil.isNotNull(couponAdmin.getId())){
			params.put("id", couponAdmin.getId());
		}
	}


}