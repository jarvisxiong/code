package com.ffzx.promotion.service.impl;

import com.ffzx.commerce.framework.dao.CrudMapper;
import com.ffzx.commerce.framework.enums.ServiceCode;
import com.ffzx.commerce.framework.exception.ServiceException;
import com.ffzx.commerce.framework.page.Page;
import com.ffzx.commerce.framework.service.impl.BaseCrudServiceImpl;
import com.ffzx.commerce.framework.system.entity.SysUser;
import com.ffzx.commerce.framework.utils.DateUtil;
import com.ffzx.commerce.framework.utils.RedisWebUtils;
import com.ffzx.commerce.framework.utils.StringUtil;
import com.ffzx.commerce.framework.utils.UUIDGenerator;
import com.ffzx.promotion.api.dto.CouponAdmin;
import com.ffzx.promotion.api.dto.CouponAdminCategory;
import com.ffzx.promotion.api.dto.constant.Constant;
import com.ffzx.promotion.mapper.CouponAdminCategoryMapper;
import com.ffzx.promotion.service.CouponAdminCategoryService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 
 * @author ffzx
 * @date 2016-05-03 17:58:03
 * @version 1.0.0
 * @copyright www.ffzxnet.com
 */
@Service("couponAdminCategoryService")
public class CouponAdminCategoryServiceImpl extends BaseCrudServiceImpl implements CouponAdminCategoryService {

    @Resource
    private CouponAdminCategoryMapper couponAdminCategoryMapper;

    @Override
    public CrudMapper init() {
        return couponAdminCategoryMapper;
    }

	@Override
	@Transactional(rollbackFor=Exception.class)
	public void save(Map<String, Object>map) throws ServiceException {
		Map<String, Object> params=new HashMap<>();
		params.put("couponAdmin", map.get("coupon_admin_id"));
		List<CouponAdminCategory> couponAdminCategoryList=this.couponAdminCategoryMapper.selectByPage(null, null, null, params);
		List<String> cList=null;
		if(couponAdminCategoryList!=null && couponAdminCategoryList.size()!=0){
			cList=new ArrayList<String>();
			for(CouponAdminCategory cat:couponAdminCategoryList){
				if(StringUtil.isNotNull(cat.getId())){					
					String id="'"+cat.getId()+"'";
					cList.add(id);
				}			
			}
		}
		if(cList!=null && cList.size()!=0){
			this.couponAdminCategoryMapper.deletesome(cList);
		}

		//商品类目
		CouponAdminCategory couponCategory=null;
		if(map.get("category_id")!=null){
			String [] categoryId=map.get("category_id").toString().trim().split(",");
			for(String id:categoryId){
				if(StringUtils.isNotEmpty(id)){
					couponCategory=new CouponAdminCategory();
					couponCategory.setCategoryId(id);
					couponCategory.setCouponAdmin((CouponAdmin)map.get("coupon_admin_id"));
					couponCategory.setType(Constant.COMM_CATEGORY);
					couponCategory.setId(UUIDGenerator.getUUID());
					this.couponAdminCategoryMapper.insertSelective(couponCategory);
				}
				
			}
		}
		if(map.get("activityManageIds")!=null){
			String [] activityManageIds=map.get("activityManageIds").toString().trim().split(",");
			for(String id:activityManageIds){
				if(!StringUtil.isEmpty(id)){
					couponCategory=new CouponAdminCategory();
					couponCategory.setActivityManagerId(id);
					couponCategory.setCouponAdmin((CouponAdmin)map.get("coupon_admin_id"));
					couponCategory.setType(Constant.ActivityPT);
					couponCategory.setId(UUIDGenerator.getUUID());
					this.couponAdminCategoryMapper.insertSelective(couponCategory);
				}
			}
		}	
		
		if(map.get("commodity_id")!=null){
			String [] goodsId=map.get("commodity_id").toString().trim().split(",");
			for(String id:goodsId){
				if(StringUtils.isNotEmpty(id)){
					couponCategory=new CouponAdminCategory();
					couponCategory.setCommodityId(id);
					couponCategory.setCouponAdmin((CouponAdmin)map.get("coupon_admin_id"));
					couponCategory.setType(Constant.COMM_FIXED);
					couponCategory.setId(UUIDGenerator.getUUID());
					this.couponAdminCategoryMapper.insertSelective(couponCategory);
				}
			}
		}
	}

	@Override
	public List<CouponAdminCategory> findList(Page page,Map<String, Object> params) throws ServiceException {
		return this.couponAdminCategoryMapper.selectByPage(page, null, null, params);
	}

	@Override
	public List<CouponAdminCategory> findCommodity(Map<String, Object> param) {
	
		return this.couponAdminCategoryMapper.findCommodity(param);
	}
}