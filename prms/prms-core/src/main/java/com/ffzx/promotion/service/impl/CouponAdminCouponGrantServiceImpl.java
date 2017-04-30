package com.ffzx.promotion.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ffzx.commerce.framework.dao.CrudMapper;
import com.ffzx.commerce.framework.enums.ServiceCode;
import com.ffzx.commerce.framework.enums.ServiceResultCode;
import com.ffzx.commerce.framework.service.impl.BaseCrudServiceImpl;
import com.ffzx.commerce.framework.utils.StringUtil;
import com.ffzx.commerce.framework.utils.UUIDGenerator;
import com.ffzx.promotion.api.dto.CouponAdmin;
import com.ffzx.promotion.api.dto.CouponAdminCouponGrant;
import com.ffzx.promotion.api.dto.CouponGrant;
import com.ffzx.promotion.mapper.CouponAdminCouponGrantMapper;
import com.ffzx.promotion.service.CouponAdminCouponGrantService;

/**
 * 
 * @author ffzx
 * @date 2016-05-03 17:58:03
 * @version 1.0.0
 * @copyright www.ffzxnet.com
 */
@Service("couponAdminCouponGrantService")
public class CouponAdminCouponGrantServiceImpl extends BaseCrudServiceImpl implements CouponAdminCouponGrantService {

    @Resource
    private CouponAdminCouponGrantMapper couponAdminCouponGrantMapper;

    @Override
    public CrudMapper init() {
        return couponAdminCouponGrantMapper;
    }
    
    @Override
	public List<String> selectCouponAdminId(String CouponGrantId) {
		// TODO Auto-generated method stub
		List<String> list= couponAdminCouponGrantMapper.selectCouponAdminId(CouponGrantId);
		if(list==null || list.size()==0){
			return null;
		}
		return list;
	}
    
    @Override
	@Transactional(rollbackFor=Exception.class)
	public ServiceCode manyInsertOrdelete(String couponAdminList,String couoponGrantId,boolean isdelete) {
		if(isdelete){
			couponAdminCouponGrantMapper.deleteByCouponGrantId(couoponGrantId);
		}
		// TODO Auto-generated method stub
		int result = 0;
		if(couponAdminList !=null && !"".endsWith(couponAdminList)){
			String[] couponStrings=couponAdminList.split(",");
			for (String couponId : couponStrings) {
				CouponAdminCouponGrant couponAdminCouponGrant=new CouponAdminCouponGrant();
				couponAdminCouponGrant.setId(UUIDGenerator.getUUID());
				CouponGrant couponGrant=new CouponGrant();
				couponGrant.setId(couoponGrantId);
				couponAdminCouponGrant.setCouponGrant(couponGrant);
				CouponAdmin couponAdmin = new CouponAdmin();
				couponAdmin.setId(couponId.trim());
				couponAdminCouponGrant.setCouponAdmin(couponAdmin);
				result+=couponAdminCouponGrantMapper.insert(couponAdminCouponGrant);
			}
		}
		return result > 0 ? ServiceResultCode.SUCCESS : ServiceResultCode.FAIL;
	}
}