package com.ffzx.promotion.service.impl;

import com.ffzx.basedata.api.service.CodeRuleApiService;
import com.ffzx.commerce.framework.constant.Constant;
import com.ffzx.commerce.framework.dao.CrudMapper;
import com.ffzx.commerce.framework.dto.ResultDto;
import com.ffzx.commerce.framework.enums.ServiceCode;
import com.ffzx.commerce.framework.enums.ServiceResultCode;
import com.ffzx.commerce.framework.exception.ServiceException;
import com.ffzx.commerce.framework.page.Page;
import com.ffzx.commerce.framework.service.impl.BaseCrudServiceImpl;
import com.ffzx.commerce.framework.system.entity.SysUser;
import com.ffzx.commerce.framework.utils.DateUtil;
import com.ffzx.commerce.framework.utils.RedisWebUtils;
import com.ffzx.commerce.framework.utils.StringUtil;
import com.ffzx.commerce.framework.utils.UUIDGenerator;
import com.ffzx.promotion.api.dto.CouponAdmin;
import com.ffzx.promotion.common.PrmsServiceResultCode;
import com.ffzx.promotion.mapper.CouponAdminMapper;
import com.ffzx.promotion.service.CouponAdminCategoryService;
import com.ffzx.promotion.service.CouponAdminService;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 
 * @author ffzx
 * @date 2016-05-03 17:57:39
 * @version 1.0.0
 * @copyright www.ffzxnet.com
 */
@Service("couponAdminService")
public class CouponAdminServiceImpl extends BaseCrudServiceImpl implements CouponAdminService {
	// 记录日志
	private final static Logger logger = LoggerFactory.getLogger(CouponAdminServiceImpl.class);
    @Resource
    private CouponAdminMapper couponAdminMapper;
    
	@Autowired
	private CodeRuleApiService codeRuleApiService;

	@Autowired
	private CouponAdminCategoryService couponAdminCategorySevice;
    @Override
    public CrudMapper init() {
        return couponAdminMapper;
    }

	@Override
	public List<CouponAdmin> getCouponList(Page page, Map<String, Object> params)throws ServiceException{
		//有效期开始时间
		if(StringUtil.isNotNull(params.get("effectiveDateStartStr"))){
			try {
				params.put("effectiveDateStart",DateUtil.parseTime(params.get("effectiveDateStartStr").toString()));
			} catch (Exception e) {
				logger.error("CouponAdminServiceImpl-getCouponList-Exception=》促销管理-优惠券新增-Exception", e);
				throw new ServiceException(e);
			}
		}
		//有效期结束时间
		if(StringUtil.isNotNull(params.get("effectiveDateEndStr"))){
			try {
				params.put("effectiveDateEnd",DateUtil.parseTime(params.get("effectiveDateEndStr").toString()));
			} catch (Exception e) {
				logger.error("CouponAdminServiceImpl-getCouponList-Exception=》促销管理-优惠券新增-Exception", e);
				throw new ServiceException(e);
			}
		}
		return this.couponAdminMapper.selectByPage(page, params.get("orderByField").toString(), params.get("orderBy").toString(), params);
	}

	@Override
	@Transactional(rollbackFor=Exception.class)
	public ServiceCode save(CouponAdmin coupon) throws ServiceException {
		
		SysUser user = RedisWebUtils.getLoginUser();
		int result = 0;
		if (StringUtil.isNotNull(coupon.getId())) {
			//修改
			CouponAdmin	cou=this.findById(coupon.getId());
			if(cou!=null){
				//有效期开始时间
				if(StringUtil.isNotNull(coupon.getEffectiveDateStartStr())){
					try {
						coupon.setEffectiveDateStart(DateUtil.parseTime(coupon.getEffectiveDateStartStr()));
					} catch (Exception e) {
						logger.error("CouponAdminServiceImpl-save-Exception=》促销管理-优惠券新增-Exception", e);
						throw new ServiceException(e);
					}
				}
				//有效期结束时间
				if(StringUtil.isNotNull(coupon.getEffectiveDateEndStr())){
					try {
						coupon.setEffectiveDateEnd(DateUtil.parseTime(coupon.getEffectiveDateEndStr()));
					} catch (Exception e) {
						logger.error("CouponAdminServiceImpl-save-Exception=》促销管理-优惠券新增-Exception", e);
						throw new ServiceException(e);
					}
				}

				if(!cou.getOtherLimit().equals(coupon.getOtherLimit())){
					if(!StringUtil.isNotNull(coupon.getOtherLimit())){
						coupon.setOtherLimit("0");
					}
				}
				//有效天数
				if(StringUtil.isNotNull(coupon.getEffectiveDateNum())){
					if(!coupon.getEffectiveDateNum().equals(cou.getEffectiveDateNum())){
						cou.setEffectiveDateNum(coupon.getEffectiveDateNum());

					}
					
				}	
				if(coupon.getEffectiveDateState().equals(Constant.DATE_FIXED)){//指定有效期
					coupon.setEffectiveDateNum(null);
				}else if(coupon.getEffectiveDateState().equals(Constant.DATE_CUSTOM)){//自定义有效期
					coupon.setEffectiveDateStart(null);
					coupon.setEffectiveDateEnd(null);
				}
				coupon.setLastUpdateBy(user);
				coupon.setCreateBy(user);			
				coupon.setLastUpdateDate(new Date());
				result=this.couponAdminMapper.updateByPrimaryKeySelective(coupon);
			}else{
				//有效期开始时间
				if(StringUtil.isNotNull(coupon.getEffectiveDateStartStr())){
					try {
						coupon.setEffectiveDateStart(DateUtil.parseTime(coupon.getEffectiveDateStartStr()));
					} catch (Exception e) {
						logger.error("CouponAdminServiceImpl-save-Exception=》促销管理-优惠券新增-Exception", e);
						throw new ServiceException(e);
					}
				}
				//有效期结束时间
				if(StringUtil.isNotNull(coupon.getEffectiveDateEndStr())){
					try {
						coupon.setEffectiveDateEnd(DateUtil.parseTime(coupon.getEffectiveDateEndStr()));
					} catch (Exception e) {
						logger.error("CouponAdminServiceImpl-save-Exception=》促销管理-优惠券新增-Exception", e);
						throw new ServiceException(e);
					}
				}
				coupon.setCreateBy(user);			
				coupon.setCreateDate(new Date());
				coupon.setLastUpdateBy(user);
				coupon.setLastUpdateDate(new Date());
				ResultDto numberDto = codeRuleApiService.getCodeRule("prms", "prms_coupon_number");
				coupon.setNumber(numberDto.getData().toString());
				result=this.couponAdminMapper.insertSelective(coupon);
			}

			Map<String, Object> map;
			if(StringUtil.isNotNull(coupon.getCategoryId())||StringUtil.isNotNull(coupon.getGoodsId()) || StringUtil.isNotNull(coupon.getActivityManageIds())){
				map=new HashMap<>();
				map.put("category_id", coupon.getCategoryId());
				map.put("coupon_admin_id", coupon);
				map.put("commodity_id", coupon.getGoodsId());
				map.put("activityManageIds", coupon.getActivityManageIds());
				couponAdminCategorySevice.save(map);
			}
		}
		return result > 0 ? ServiceResultCode.SUCCESS : ServiceResultCode.FAIL;
	}
	
	
	/**
	 * 数据校验
	 * 
	 * @param coupon
	 * @return ServiceCode
	 * @throws ServiceException
	 */
	public ServiceCode isExist(CouponAdmin coupon) throws ServiceException {
		ServiceCode code = null;
		Map<String, Object> params = null;
		if (StringUtil.isNotNull(coupon.getName())) {
			params = new HashMap<String, Object>();
			params.put("name", coupon.getName());
			if (this.findCount(params) > 0) {
				return PrmsServiceResultCode.PRMS_COUPON_11011;
			}
		}
		return code;
	}

	@Override
	@Transactional(rollbackFor=Exception.class)
	public ServiceCode runCoupon(CouponAdmin coupon) throws ServiceException {
		int result = 0;
		SysUser user = RedisWebUtils.getLoginUser();
		coupon.setLastUpdateBy(user);
		coupon.setLastUpdateDate(new Date());
		coupon.setDelFlag("1");
		result=this.couponAdminMapper.updateByPrimaryKeySelective(coupon);
		return result > 0 ? ServiceResultCode.SUCCESS : ServiceResultCode.FAIL;
	}

	@Override
	public List<CouponAdmin> getCouponAdminList(Page page, String orderByField, String orderBy, Map<String, Object> map)
			throws ServiceException {
		return couponAdminMapper.findCouponAdmin(page, orderByField, orderBy, map);
	}
}