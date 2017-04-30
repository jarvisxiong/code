package com.ffzx.promotion.api.service.impl;

import com.ffzx.basedata.api.service.CodeRuleApiService;
import com.ffzx.commerce.framework.constant.Constant;
import com.ffzx.commerce.framework.dao.CrudMapper;
import com.ffzx.commerce.framework.dto.ResultDto;
import com.ffzx.commerce.framework.enums.ServiceCode;
import com.ffzx.commerce.framework.enums.ServiceResultCode;
import com.ffzx.commerce.framework.exception.ServiceException;
import com.ffzx.commerce.framework.page.Page;
import com.ffzx.commerce.framework.service.impl.BaseCrudServiceImpl;
import com.ffzx.commerce.framework.utils.RedisWebUtils;
import com.ffzx.commerce.framework.utils.StringUtil;
import com.ffzx.commerce.framework.utils.UUIDGenerator;
import com.ffzx.promotion.api.dto.ActivityCommoditySku;
import com.ffzx.promotion.api.dto.ActivityManager;
import com.ffzx.promotion.api.dto.CouponReceive;
import com.ffzx.promotion.api.enums.ActivityTypeEnum;
import com.ffzx.promotion.api.service.ActivityCommoditySkuApiService;
import com.ffzx.promotion.api.service.ActivityManagerApiService;
import com.ffzx.promotion.mapper.ActivityCommoditySkuMapper;
import com.ffzx.promotion.mapper.ActivityManagerMapper;
import com.ffzx.promotion.service.ActivityCommoditySkuService;
import com.ffzx.promotion.service.ActivityManagerService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
* @Description: TODO
* @author yuzhao.xu
* @email  yuzhao.xu@ffzxnet.com
* @date 2016年5月11日 下午4:25:32
* @version V1.0 
*
*/
@Service("activityCommoditySkuApiService")
public class ActivityCommoditySkuApiServiceImpl extends BaseCrudServiceImpl implements ActivityCommoditySkuApiService {

    @Resource
    private ActivityManagerMapper activityManagerMapper;

    @Autowired
    private CodeRuleApiService codeRuleApiService;
    
    @Resource
    private ActivityCommoditySkuMapper activityCommoditySkuMapper;
    
    @Autowired
    private ActivityCommoditySkuService activityCommoditySkuService;
    @Override
    public CrudMapper init() {
        return activityManagerMapper;
    }

	@Override
	public ResultDto getActivityCommoditySku(String managerid, String skuid,Date stateenddate) {
		// TODO Auto-generated method stub
		
		
		ResultDto rsDto = null;
		try{
			if(StringUtil.isEmpty(managerid) || StringUtil.isEmpty(skuid)|| stateenddate==null){
			throw new Exception("3个value都不能为null");
			}
			Map<String, Object> map=new HashMap<String, Object>();
			map.put("activitiId", managerid);
			map.put("stateenddate", stateenddate);
			map.put("commoditySkuId", skuid);
			rsDto = new ResultDto(ResultDto.OK_CODE, "success");
			rsDto.setData(activityCommoditySkuMapper.selectByParamsAll(map));
		}catch(Exception e){
			logger.error("ActivityCommoditySkuApiServiceImpl-getCoupReceive-Exception=》机构dubbo调用-ActivityCommoditySkuApiServiceImpl", e);
//			rsDto = new ResultDto(ResultDto.ERROR_CODE, "faild:" + e);
			throw new ServiceException(e);
		}
		return rsDto;
	}

	@Override
	public ResultDto selectActivitySkuPrice(List<Object> activityIdList) {
		// TODO Auto-generated method stub
		ResultDto rsDto = null;
		try{
			if(activityIdList==null || activityIdList.size()==0){
			throw new Exception("activityIdList为null，不存在活动数据");
			}
			rsDto = new ResultDto(ResultDto.OK_CODE, "success");
			rsDto.setData(activityCommoditySkuMapper.selectActivitySkuPrice(activityIdList));
		}catch(Exception e){
			logger.error("ActivityCommoditySkuApiServiceImpl-selectActivitySkuPrice-Exception=》机构dubbo调用-ActivityCommoditySkuApiServiceImpl", e);
//			rsDto = new ResultDto(ResultDto.ERROR_CODE, "faild:" + e);
			throw new ServiceException(e);
		}
		return rsDto;
	}

	@Override
	public ResultDto getPifaDate(String commodityNo) {
		// TODO Auto-generated method stub
		ResultDto rsDto = null;
		try{
			if(StringUtil.isEmpty(commodityNo)){
				throw new Exception("commodityNovalue都不能为null");
				}

			Map<String, Object> map=new HashMap<String, Object>();
			map.put("commodityNo", commodityNo);
			rsDto = new ResultDto(ResultDto.OK_CODE, "success");
			List<ActivityCommoditySku> activityCommoditySkus=activityCommoditySkuMapper.selectByParams(map);
			if(activityCommoditySkus==null || activityCommoditySkus.size()==0){
				throw new Exception("此商品不参与批发");
			}
			
			
			rsDto.setData(activityCommoditySkus);
		}catch(Exception e){
			logger.error("ActivityCommoditySkuApiServiceImpl-getPifaDate-Exception=》机构dubbo调用-ActivityCommoditySkuApiServiceImpl", e);
//			rsDto = new ResultDto(ResultDto.ERROR_CODE, "faild:" + e);
			throw new ServiceException(e);
		}
		return rsDto;
	}

	@Override
	public ResultDto getPifaPrice(String commodityNo, Integer num) {
		// TODO Auto-generated method stub
		BigDecimal commoditySkuPrice=null;
		ResultDto rsDto = null;
		
		try{
			if(StringUtil.isEmpty(commodityNo) || num==null){
				throw new Exception("2个value都不能为null");
				}

			Map<String, Object> map=new HashMap<String, Object>();
			map.put("commodityNo", commodityNo);
			rsDto = new ResultDto(ResultDto.OK_CODE, "success");
			List<ActivityCommoditySku> activityCommoditySkus=activityCommoditySkuMapper.selectByParams(map);
			if(activityCommoditySkus==null || activityCommoditySkus.size()==0){
				throw new Exception("此商品不参与批发");
			}
			for (ActivityCommoditySku activityCommoditySku : activityCommoditySkus) {
				if(activityCommoditySku.getSelectionEnd()==null && activityCommoditySku.getSelectionStart()<=num){
					commoditySkuPrice=activityCommoditySku.getActivityPrice();
					break;
				}
				else if(activityCommoditySku.getSelectionStart()<=num  && activityCommoditySku.getSelectionEnd()>=num){
					commoditySkuPrice=activityCommoditySku.getActivityPrice();
					break;
				}
			}
			if(commoditySkuPrice==null){
				throw new Exception("不满足批发条件 ，请检查");
			}
			
			rsDto.setData(commoditySkuPrice);
		}catch(Exception e){
			logger.error("ActivityCommoditySkuApiServiceImpl-getPifaPrice-Exception=》机构dubbo调用-ActivityCommoditySkuApiServiceImpl", e);
//			rsDto = new ResultDto(ResultDto.ERROR_CODE, "faild:" + e);
			throw new ServiceException(e);
		}
		return rsDto;
		
	}

	 @Override
	    public ResultDto getCommoditySkuList(String uid,String cid,String cityId,int page,int pageSize,ActivityTypeEnum type){
	    	ResultDto rsDto = null;
	    	try{
		    	rsDto = new ResultDto(ResultDto.OK_CODE, "success");
		    	Map<String,Object> result = new HashMap<String,Object>();
		    	Page pageObject = new Page();
		    	pageObject.setPageSize(pageSize);
		    	pageObject.setPageIndex(page);
		    	Map<String,Object> params = new HashMap<String ,Object>();
		    	params.put("activityType", type);
		    	List<ActivityCommoditySku> activityList = activityCommoditySkuMapper.selectByPage(pageObject,null, Constant.ORDER_BY, params);
		    	if(activityList !=null && activityList.size()>=1){
		    		result.put("items",activityList);
		    		result.put("page", pageObject.getPageCount());
		    		result.put("pageSize",pageObject.getPageSize());
		    		result.put("recordCount", pageObject.getTotalCount());
		    	}
		    	rsDto.setData(result);
	    	}catch(Exception e){
				logger.error("AdvertApiServiceImpl-getDataByRegionNumber-Exception=》机构dubbo调用-getDataByRegionNumber", e);
//				rsDto = new ResultDto(ResultDto.ERROR_CODE, "faild:" + e);
				throw new ServiceException(e);
			}
	    	return rsDto;
	    }

	@Override
	public ResultDto findActivitySkuByActivityId(String activityId,String commoditySkuIds,String commodityCode) {
		try{
			List<ActivityCommoditySku> skuList = activityCommoditySkuService.findActivitySkuByActivityId(activityId,commoditySkuIds,commodityCode);
			ResultDto result = new ResultDto(ResultDto.OK_CODE, Constant.SUCCESS);
			result.setData(skuList);
			return result;
		}catch(Exception e){
			logger.error("activityCommoditySkuService.findActivitySkuByActivityId error",e);
//			return new ResultDto(ResultDto.ERROR_CODE, e.getMessage());
			throw new ServiceException(e);
		}
	}
}