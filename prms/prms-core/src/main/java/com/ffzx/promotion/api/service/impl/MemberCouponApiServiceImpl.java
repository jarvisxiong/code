package com.ffzx.promotion.api.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ffzx.commerce.framework.constant.Constant;
import com.ffzx.commerce.framework.dto.ResultDto;
import com.ffzx.commerce.framework.exception.ServiceException;
import com.ffzx.commerce.framework.page.Page;
import com.ffzx.commerce.framework.utils.StringUtil;
import com.ffzx.commodity.api.dto.Category;
import com.ffzx.commodity.api.dto.Commodity;
import com.ffzx.promotion.api.dto.CouponAdminCategory;
import com.ffzx.promotion.api.dto.CouponReceive;
import com.ffzx.promotion.api.service.MemberCouponApiService;
import com.ffzx.promotion.api.service.consumer.CategoryApiConsumer;
import com.ffzx.promotion.api.service.consumer.CommodityApiConsumer;
import com.ffzx.promotion.mapper.CouponReceiveMapper;
import com.ffzx.promotion.service.CouponAdminCategoryService;
/******
 * 
 * @author lushi.guo
 *
 */
@Service("memberCouponApiService")
public class MemberCouponApiServiceImpl implements MemberCouponApiService {
	// 记录日志
	private final static Logger logger = LoggerFactory.getLogger(MemberCouponApiServiceImpl.class);

    @Resource
    private CouponReceiveMapper couponReceiveMapper;
    @Autowired
    private CouponAdminCategoryService couponAdminCategoryService;
	@Autowired
	private CategoryApiConsumer categoryApiConsumer;
	@Autowired
	private CommodityApiConsumer commodityApiConsumer;
	@Override
	public ResultDto getMemberCouponList(Page page, String memberId) {
		logger.info("MemberCouponApiServiceImpl-getMemberCouponList=》用户领取优惠券接口-根据用户ID获取已经领取的优惠券");
		ResultDto rsDto = null;
		try {
			Map<String, Object> params=new HashMap<>();
			if(StringUtil.isNotNull(memberId)){
				params.put("memberId", memberId);
			}			
			List<CouponReceive> memberCouponList=this.couponReceiveMapper.findListByMember(page, null, null, params);
			Map<String, Object> param = new HashMap<String,Object>();
			param.put("page", page);
			param.put("list", memberCouponList);
			//优惠券ID 集合
			if(memberCouponList!=null&&memberCouponList.size()!=0){
				getMemberCouponCaName(memberCouponList);					
			}
			rsDto = new ResultDto(ResultDto.OK_CODE, "success");
			rsDto.setData(param);
		} catch (Exception e) {
			logger.error("MemberCouponApiServiceImpl-getMemberCouponList=》用户领取优惠券接口-根据用户ID获取已经领取的优惠券",e);
//			rsDto = new ResultDto(ResultDto.ERROR_CODE, "faild:" + e);
			throw new ServiceException(e);
		}		
		return rsDto;
	}
	private void getMemberCouponCaName(List<CouponReceive> memberCouponList) throws ServiceException {
		Map<String, Object> params;
		//优惠券ID数组
		String [] couponId=new String[memberCouponList.size()];
		for(int i=0;i<memberCouponList.size();i++){
			couponId[i]=memberCouponList.get(i).getCouponAdmin().getId();
		} 
		params=new HashMap<>();
		params.put("couponIds", couponId);
		//查询该会员的所有优惠券关联的商品或者商品类目
		List<CouponAdminCategory> couponAdminCategoryList=this.couponAdminCategoryService.findCommodity(params);
		//优惠券ID对应商品或者类别ID集合
		if(couponAdminCategoryList!=null && couponAdminCategoryList.size()!=0){
			for(CouponReceive cour:memberCouponList){
				List<String> ca=null;
				List<String> co=null;
				for(CouponAdminCategory cate:couponAdminCategoryList){
					if(cour.getCouponAdmin().getId().equals(cate.getCouponAdmin().getId())){
						if(StringUtil.isNotNull(cate.getCategoryId())){
							 ca=new ArrayList<>();
							ca.add(cate.getCategoryId());
						}else if(StringUtil.isNotNull(cate.getCommodityId())){
							co=new ArrayList<>();
							co.add(cate.getCommodityId());
						}	
					}				
				}
				//获取商品类目集合
				if(ca!=null  && ca.size()!=0){
					getCouponCommodityName(cour, ca);
				}
				//获取商品集合
				if(co!=null && co.size()!=0){
					getCouponCategoryName(cour, co);
				}
			}
		}

	}
	private void getCouponCategoryName(CouponReceive cour, List<String> co) throws ServiceException {
		Map<String, Object> params;
		String [] coId=new String [co.size()];						
		for(int i=0;i<co.size();i++){
			coId[i]=co.get(i);
		}
		params=new HashMap<>();
		params.put("inIds", coId);
		Map<String, Object> mapValue=commodityApiConsumer.findList(null, "create_date", Constant.ORDER_BY, params);
		List<Commodity> commodityList = (List<Commodity>) mapValue.get("list");
		if(commodityList!=null && commodityList.size()!=0){
			cour.setGoodsNames(commodityList.get(0).getName());
		}
	}
	private void getCouponCommodityName(CouponReceive cour, List<String> ca) throws ServiceException {
		Map<String, Object> params;
		String [] caId=new String [ca.size()];
		for(int i=0;i<ca.size();i++){
			caId[i]=ca.get(i);
		}
		params=new HashMap<>();
		params.put("inIds", caId);
		List<Category> categoryList = categoryApiConsumer.getTreeTableList(params, null);
		if(categoryList!=null && categoryList.size()!=0){
			cour.setCategoryNames(categoryList.get(0).getName());
		}
	}

}
