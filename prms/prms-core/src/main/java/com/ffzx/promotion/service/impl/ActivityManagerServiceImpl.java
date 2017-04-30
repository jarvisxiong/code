package com.ffzx.promotion.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ffzx.basedata.api.service.CodeRuleApiService;
import com.ffzx.commerce.framework.constant.Constant;
import com.ffzx.commerce.framework.constant.RedisPrefix;
import com.ffzx.commerce.framework.dao.CrudMapper;
import com.ffzx.commerce.framework.dto.ResultDto;
import com.ffzx.commerce.framework.enums.ServiceCode;
import com.ffzx.commerce.framework.enums.ServiceResultCode;
import com.ffzx.commerce.framework.exception.ServiceException;
import com.ffzx.commerce.framework.page.Page;
import com.ffzx.commerce.framework.service.impl.BaseCrudServiceImpl;
import com.ffzx.commerce.framework.utils.DateUtil;
import com.ffzx.commerce.framework.utils.RedisUtil;
import com.ffzx.commerce.framework.utils.RedisWebUtils;
import com.ffzx.commerce.framework.utils.StringUtil;
import com.ffzx.commerce.framework.utils.UUIDGenerator;
import com.ffzx.promotion.api.dto.ActivityCommodity;
import com.ffzx.promotion.api.dto.ActivityCommoditySku;
import com.ffzx.promotion.api.dto.ActivityManager;
import com.ffzx.promotion.api.enums.ActivityTypeEnum;
import com.ffzx.promotion.mapper.ActivityManagerMapper;
import com.ffzx.promotion.service.ActivityCommodityService;
import com.ffzx.promotion.service.ActivityCommoditySkuService;
import com.ffzx.promotion.service.ActivityManagerService;

/**
* @Description: TODO
* @author yuzhao.xu
* @email  yuzhao.xu@ffzxnet.com
* @date 2016年5月11日 下午4:25:32
* @version V1.0 
*
*/
@Service("activityManagerService")
public class ActivityManagerServiceImpl extends BaseCrudServiceImpl implements ActivityManagerService {

    @Resource
    private ActivityManagerMapper activityManagerMapper;
    
    @Autowired
    private CodeRuleApiService codeRuleApiService;
    
    @Autowired
    private ActivityCommodityService activityCommodityService;
    @Autowired
    private ActivityCommoditySkuService activityCommoditySkuService;
    
    @Autowired
    private RedisUtil redis;
    
    @Override
    public CrudMapper init() {
        return activityManagerMapper;
    }

	@Override
	public List<ActivityManager> findList(Page pageObj, String orderByField, String orderBy, ActivityManager activity) 
			throws ServiceException {
		Map<String, Object> params = new HashMap<String, Object>();

		//筛选添加标题
		if(StringUtil.isNotNull(activity.getTitle())){
			params.put("title", activity.getTitle());
		}
		//筛选添加发布状态
		if(StringUtil.isNotNull(activity.getReleaseStatus())){
			params.put("releaseStatus", activity.getReleaseStatus());
		}
		//筛选排除掉的id
		if(StringUtil.isNotNull(activity.getActivityIds())){
			params.put("activityids", activity.getActivityIds().split(","));
		}
		//筛选排查询的id
		if(StringUtil.isNotNull(activity.getActivityIsIds())){
			params.put("activityIsIds", activity.getActivityIsIds().split(","));
		}
		//筛选添加操作人
		if(StringUtil.isNotNull(activity.getOperator())){
			params.put("operator", activity.getOperator());
		}
		//筛选添加活动状态
		if(StringUtil.isNotNull(activity.getActivityStatus())){
			params.put("activityStatus", activity.getActivityStatus());
			params.put("nowDate", new Date());
		}
		//筛选添加活动类型
		if(StringUtil.isNotNull(activity.getActivityType())){
			params.put("activityType", activity.getActivityType());
		}
		//筛选添加是否app推荐
		if(StringUtil.isNotNull(activity.getAppRecommend())){
			params.put("appRecommend", activity.getAppRecommend());
		}
//		if(activity.getStatus()!=null){
//			params.put("status", activity.getStatus());
//		}
		//列表只查询出删除标志为正常的数据
		params.put("delFlag",Constant.DELTE_FLAG_NO);
		return activityManagerMapper.selectByPage(pageObj, orderByField, orderBy, params);
	}
	
	
	@Override
	@Transactional(rollbackFor=Exception.class)
	public ServiceCode saveOrUpdate(ActivityManager activity) throws ServiceException {
		// TODO Auto-generated method stub
		int result = 0;
		activity.setOperator(RedisWebUtils.getLoginUser().getName()); // 操作人
		if(StringUtil.isNotNull(activity.getId())){   //有ID则为修改
//			isUpdateDate(activity);//抢购的开始时间，结束时间是否符合规范
			activity.setDelFlag(null);//不更新此字段
			result=activityManagerMapper.updateByPrimaryKeySelective(activity);
			try {
				if (result > 0) {
					Map<String, Object> params = new HashMap<String, Object>();
					params.put("startDate", activity.getStartDate());
					params.put("endDate", activity.getEndDate());
					params.put("activityId", activity.getId());
					activityCommodityService.updateCommodityDate(params);
				}
			} catch (Exception e) {
				throw e;
			}
		}else{
			activity.setId(UUIDGenerator.getUUID());
			ResultDto numberDto = codeRuleApiService.getCodeRule("prms", "prms_activity_manager_no");
			activity.setActivityNo(numberDto.getData().toString());
			result=activityManagerMapper.insertSelective(activity);
		}
		return result > 0 ? ServiceResultCode.SUCCESS : ServiceResultCode.FAIL;
	}
	
	@Override
	public void isActicityUpdateDate(ActivityManager activity){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("activity", activity);
		List<ActivityCommodity> activityCommodities=activityCommodityService.findByBiz(params);
		List<String> commodityNo = new ArrayList<String>();
		if (null != activityCommodities && activityCommodities.size() > 0) {
			for (ActivityCommodity commodity : activityCommodities) {
				commodityNo.add(commodity.getCommodityNo());
			}
		}
		
		if(activity.getStartDate()!=null && null != activity.getEndDate() && commodityNo.size() > 0){
			params.clear();
			params.put("startDate", activity.getStartDate());
			params.put("endDate", activity.getEndDate());
			params.put("commodityNos", commodityNo.size()==0?null:commodityNo);
			params.put("activityType", ActivityTypeEnum.PANIC_BUY.getValue());
			List<ActivityCommodity> commodityList = activityCommodityService.findCommodityByDate(params);
			if (null != commodityList && commodityList.size() > 0) {
				Set<String> commIdSet = new HashSet<String>();
				for (ActivityCommodity activityCommodity : commodityList) {
					if(commIdSet.contains(activityCommodity.getCommodityNo()))
					{
						throw new RuntimeException("存在活动商品【"+activityCommodity.getActivityTitle()+"】在不同批次并且时间重叠");
					}
					else
					{
						commIdSet.add(activityCommodity.getCommodityNo());
					}
				}
			}
		}
	}
	
	
	/**
	 * 开始时间，结束时间是否符合规范,在商品设置之内
	 * @param activity
	 */
	private void isUpdateDate(ActivityManager activity){


		Map<String, Object> params = new HashMap<String, Object>();
		params.put("activity", activity);
		List<ActivityCommodity> activityCommodities=activityCommodityService.findByBiz(params);
		if(activity.getStartDate()!=null){
			for (ActivityCommodity activityCommodity : activityCommodities) {
				if(activityCommodity.getActivityType()!=null && 
						( activityCommodity.getActivityType().equals(ActivityTypeEnum.PANIC_BUY)  ||
								activityCommodity.getActivityType().equals(ActivityTypeEnum.PRE_SALE))){
					if(activityCommodity.getStartDate()==null){
	
						throw new RuntimeException("此商品设置的id="+activityCommodity.getId()+"titile="+activityCommodity.getActivityTitle()+"开始时间为"+activityCommodity.getStartDate());
					}
					if( DateUtil.diffDateTime(activity.getStartDate(), activityCommodity.getStartDate())>0){//开始时间要比较小，大于不正常
						
						throw new RuntimeException("开始时间必须在商品设置时间之外 与此商品设置的id="+activityCommodity.getId()+"titile="+activityCommodity.getActivityTitle()+"时间冲突");
						
					}
				}
				
			}
		}
		if(activity.getEndDate()!=null){
			for (ActivityCommodity activityCommodity : activityCommodities) {

				if(activityCommodity.getActivityType()!=null && 
						( activityCommodity.getActivityType().equals(ActivityTypeEnum.PANIC_BUY)  ||
								activityCommodity.getActivityType().equals(ActivityTypeEnum.PRE_SALE))){
					if(activityCommodity.getEndDate()==null){
	
						throw new RuntimeException("此商品设置的id="+activityCommodity.getId()+"titile="+activityCommodity.getActivityTitle()+"开始时间为"+activityCommodity.getEndDate());
					}
					if(DateUtil.diffDateTime(activity.getEndDate(), activityCommodity.getEndDate())<0){//结束时间要大于，小于不正常
					
	
						throw new RuntimeException("结束时间必须在商品设置时间之外 与此商品设置的id="+activityCommodity.getId()+"titile="+activityCommodity.getActivityTitle()+"时间冲突");
					}
				}
				
			}
		}
	}
	@Override
	@Transactional(rollbackFor=Exception.class)
	public int updateActivityStatus(ActivityManager activity) throws ServiceException {
		String releaseStatus=activity.getReleaseStatus();//发布状态
		int result=0;
		result=this.activityManagerMapper.updateByPrimaryKeySelective(activity);
		//发布成功
		if(result>0 && releaseStatus.equals(Constant.YES)){
			//获取该活动下的商品信息
			Map<String, Object> params=new HashMap<String, Object>();
			params.put("activity", activity);
			List<ActivityCommodity> commodityList=this.activityCommodityService.findActivityCommdity(null, params);
			if(StringUtil.isNotNull(commodityList)){
				for(ActivityCommodity commodity:commodityList){
					setRedisDate(commodity);
				}
			}
		}
		return result;
	}	
	

	private void setRedisDate(ActivityCommodity commodity) {
		Map<String, Object> params;
		int limitCount=commodity.getLimitCount();//限购量
		String commodityNo=commodity.getCommodityNo();//商品编码
		String activityCommodityId=commodity.getId();//活动管理商品ID					
		//商品ID限购量存入缓存
		if(commodity.getIdLimitCount()!=null && commodity.getIdLimitCount()!=0){
			redis.set(RedisPrefix.ACTIVITY+RedisPrefix.ACTIVITY_IDLIMIT+activityCommodityId+"_"+commodityNo+"idlimit", commodity.getIdLimitCount());
		}					
		//如果是预售把发货时间存入缓存
		if(commodity.getActivityType().equals(ActivityTypeEnum.PRE_SALE)){
			redis.set(RedisPrefix.ACTIVITY+RedisPrefix.ACTIVITY_DEVLIDATE+activityCommodityId+"_"+commodityNo+"_deliverDate", commodity.getDeliverDate());
		}
		//该活动商品对象存入
		redis.set(RedisPrefix.ACTIVITY+RedisPrefix.ACTIVITY_OBJ+activityCommodityId+"_"+commodityNo, commodity);
		params=new HashMap<String, Object>();
		params.put("commodityNo", commodityNo);
		List<ActivityCommoditySku> skuList=this.activityCommoditySkuService.findActivityCommoditySkuList(params);
		for(ActivityCommoditySku sku:skuList){
			//商品限购量存入缓存
			redis.set(RedisPrefix.ACTIVITY+RedisPrefix.ACTIVITY_LIMIT+activityCommodityId+"_"+sku.getCommoditySkuNo()+"limit", sku.getLimitCount());					
			//活动商品价格存入缓存
			redis.set(RedisPrefix.ACTIVITY+RedisPrefix.ACTIVITY_PRICE+activityCommodityId+"_"+sku.getCommoditySkuNo()+"price", sku.getActivityPrice());	
		}
	}

	@Override
	public List<ActivityManager> findAppRecommendList(Page pageObj, ActivityManager activity) throws ServiceException {
		Map<String, Object> params = new HashMap<String, Object>();

		//筛选添加活动类型
		if(StringUtil.isNotNull(activity.getActivityType())){
			params.put("activityType", activity.getActivityType());
		}
		//筛选添加是否app推荐
		if(StringUtil.isNotNull(activity.getAppRecommend())){
			params.put("appRecommend", activity.getAppRecommend());
		}
		//筛选添加标题
		if(StringUtil.isNotNull(activity.getTitle())){
			params.put("title", activity.getTitle());
		}
		//筛选添加发布状态
		if(StringUtil.isNotNull(activity.getReleaseStatus())){
			params.put("releaseStatus", activity.getReleaseStatus());
		} 
		
		//列表只查询出删除标志为正常的数据
		params.put("delFlag",Constant.DELTE_FLAG_NO);
		return activityManagerMapper.selectAppRecommendList(pageObj, params);
	}

	@Override
	public List<ActivityManager> findPanicBuyActivityList(String activityType, List<String> ids) throws ServiceException {
		Map<String, Object> params = new HashMap<String, Object>();

		//筛选添加活动类型
		if(StringUtil.isNotNull(activityType)){
			params.put("activityType", activityType);
		}
		//筛选添加抢购活动id
		if(StringUtil.isNotNull(ids)){
			params.put("ids", ids);
		}
		//列表只查询出发布的数据
//		params.put("releaseStatus", Constant.YES);
		//列表只查询出删除标志为正常的数据
//		params.put("delFlag",Constant.DELTE_FLAG_NO);
		
		return activityManagerMapper.findPanicBuyActivityList(params);
	}

	@Override
	@Transactional(rollbackFor=Exception.class)
	public int updateAppRecommend(String appRecommend, List<String> ids) throws ServiceException {
		int result = 0;
		Map<String, Object> params = new HashMap<String, Object>();
		//筛选添加活动类型
		if(StringUtil.isNotNull(appRecommend)){
			params.put("appRecommend", appRecommend);
		}
		//筛选添加抢购活动id
		if(StringUtil.isNotNull(ids)){
			params.put("ids", ids);
		}
		// 执行修改操作
		result = activityManagerMapper.updateAppRecommend(params);
		return result;
	}
}