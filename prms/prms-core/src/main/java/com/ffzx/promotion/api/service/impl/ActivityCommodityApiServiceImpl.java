package com.ffzx.promotion.api.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ffzx.commerce.framework.constant.Constant;
import com.ffzx.commerce.framework.dao.CrudMapper;
import com.ffzx.commerce.framework.dto.ResultDto;
import com.ffzx.commerce.framework.exception.ServiceException;
import com.ffzx.commerce.framework.page.Page;
import com.ffzx.commerce.framework.service.impl.BaseCrudServiceImpl;
import com.ffzx.commerce.framework.utils.DateUtil;
import com.ffzx.commerce.framework.utils.StringUtil;
import com.ffzx.commerce.framework.utils.UUIDGenerator;
import com.ffzx.member.api.dto.MemberMessage;
import com.ffzx.member.api.enums.MessTypeEnum;
import com.ffzx.member.api.service.MemberMessageApiService;
import com.ffzx.promotion.api.dto.ActivityCommodity;
import com.ffzx.promotion.api.dto.ActivityGive;
import com.ffzx.promotion.api.dto.ActivityManager;
import com.ffzx.promotion.api.dto.ActivityPreSaleTag;
import com.ffzx.promotion.api.dto.PanicBuyRemind;
import com.ffzx.promotion.api.enums.ActivityTypeEnum;
import com.ffzx.promotion.api.service.ActivityCommodityApiService;
import com.ffzx.promotion.api.service.RemoveActivityIndex;
import com.ffzx.promotion.api.service.UpdateActivityIndex;
import com.ffzx.promotion.api.service.consumer.CommodityApiConsumer;
import com.ffzx.promotion.constant.CouponConstant;
import com.ffzx.promotion.mapper.ActivityCommodityMapper;
import com.ffzx.promotion.mapper.ActivityCommoditySkuMapper;
import com.ffzx.promotion.mapper.ActivityManagerMapper;
import com.ffzx.promotion.mapper.ActivityPreSaleTagMapper;
import com.ffzx.promotion.mapper.PanicBuyRemindMapper;
import com.ffzx.promotion.service.ActivityGiveService;

/**
* @Description: TODO
* @author yuzhao.xu
* @email  yuzhao.xu@ffzxnet.com
* @date 2016年5月11日 下午4:25:32
* @version V1.0 
*
*/
@Service("activityCommodityApiService")
public class ActivityCommodityApiServiceImpl extends BaseCrudServiceImpl implements ActivityCommodityApiService {

    @Autowired
	private RemoveActivityIndex removeActivityIndex;
    @Autowired
    private UpdateActivityIndex updateActivityIndex;
    @Resource
    private ActivityCommodityMapper activityCommodityMapper;

    @Autowired
    private ActivityCommoditySkuMapper activityCommoditySkuMapper;

    @Resource
    private ActivityCommodityMapper activityCommdityMapper;
    @Autowired
    private ActivityGiveService activityGiveService;
    @Resource
    private ActivityCommoditySkuMapper activityCommditySkuMapper;
    @Resource
    private PanicBuyRemindMapper panicBuyRemindMapper;
    @Resource
    private ActivityManagerMapper activityManagerMapper;

    @Autowired
    private CommodityApiConsumer commodityApiConsumer;
	@Autowired
	private MemberMessageApiService memberMessageApiService;
	@Autowired
	private ActivityPreSaleTagMapper activityPreSaleTagMapper;
    
    @Override
    public CrudMapper init() {
        return activityCommodityMapper;
    }

    
	@Override
	public ResultDto findActivityRecommendList(String activityId) {
		ResultDto rsDto = null;
		try{
			rsDto = new ResultDto(ResultDto.OK_CODE, "success");
			Map<String,Object> result = new HashMap<String,Object>();
			Map<String, Object> params = new HashMap<String, Object>();
			ActivityManager activity = new ActivityManager();
			activity.setId(activityId);
			params.put("activity", activity);
			params.put("isRecommend", "1");
			List<ActivityCommodity> bannerList = activityCommodityMapper.selectRecommend(params);
			if(bannerList !=null && bannerList.size()>=1){
	    		result.put("items",bannerList);
	    	}
	    	rsDto.setData(result);
		} catch(Exception e){
			logger.error("ActivityCommodityApiServiceImpl-findActivityRecommendList-Exception=》获取活动Banner列表", e);
//			rsDto = new ResultDto(ResultDto.ERROR_CODE, "faild:" + e);
			throw new ServiceException(e);
		}
		return rsDto;
	}
    
	@Override
	public ResultDto findWholeSaleList(String activityId, String sort, String cityId, int page, int pageSize,
			String addressCode, String warehouseCode) {
		ResultDto rsDto = null;
    	try{
    		rsDto = new ResultDto(ResultDto.OK_CODE, "success");
    		Map<String,Object> result = new HashMap<String,Object>();
	    	Page pageObject = new Page();
	    	pageObject.setPageSize(pageSize);
	    	pageObject.setPageIndex(page);
	    	Map<String,Object> params = new HashMap<String ,Object>();
	    	params.put("activityId", activityId);
	    	params.put("sort", sort);
	    	params.put("cityId", cityId);
	    	params.put("addressCode", addressCode);
	    	params.put("warehouseCode", warehouseCode);
	    	List<ActivityCommodity> commodityList = 
	    			(List<ActivityCommodity>) activityCommodityMapper.selectWholeSaleCommodityByParams(pageObject, params);
	    	if(commodityList !=null && commodityList.size()>=1){
	    		result.put("items",commodityList);
	    		result.put("page", pageObject.getPageCount());
	    		result.put("pageSize",pageObject.getPageSize());
	    		result.put("recordCount", pageObject.getTotalCount());
	    	}
	    	rsDto.setData(result);
	    	
    	} catch(Exception e){
			logger.error("ActivityCommodityApiServiceImpl-getWholeSaleList-Exception=》获取批发商品列表", e);
//			rsDto = new ResultDto(ResultDto.ERROR_CODE, "faild:" + e);
		}
		return rsDto;
	}

	@Override
	public ResultDto getActivityCommodity(Map<String,Object> params) {
		// TODO Auto-generated method stub
		ResultDto rsDto = null;
    	try{
    		rsDto = new ResultDto(ResultDto.OK_CODE, "success");
	    	ActivityCommodity activityCommodity =  activityCommodityMapper.selectByCommodityPrimaryKey(params);
	    	
	    	rsDto.setData(activityCommodity);
	    	
    	} catch(Exception e){
			logger.error("ActivityCommodityApiServiceImpl-getActivityCommodity-Exception=》获取活动商品详情", e);
//			rsDto = new ResultDto(ResultDto.ERROR_CODE, "faild:" + e);
			throw new ServiceException(e);
		}
		return rsDto;
	}

	@Override
	public ResultDto findActivityCommodityList(String actityId, String sort, String desc, String cityId, int page, int pageSize, 
			String addressCode, String warehouseCode) {
		ResultDto rsDto = null;
    	try{
    		rsDto = new ResultDto(ResultDto.OK_CODE, "success");
    		Map<String,Object> result = new HashMap<String,Object>();
	    	Page pageObject = new Page();
	    	pageObject.setPageSize(pageSize);
	    	pageObject.setPageIndex(page);
	    	Map<String,Object> params = new HashMap<String ,Object>();
	    	params.put("activityId", actityId);
	    	params.put("sort", sort);
	    	params.put("desc", desc);
	    	params.put("cityId", cityId);
	    	params.put("addressCode", addressCode);
	    	params.put("warehouseCode", warehouseCode);
	    	List<ActivityCommodity> commodityList = 
	    			(List<ActivityCommodity>) activityCommodityMapper.selectActivityCommodityByParams(pageObject, params);
	    	if(commodityList !=null && commodityList.size()>=1){
	    		result.put("items",commodityList);
	    		result.put("page", pageObject.getPageCount());
	    		result.put("pageSize",pageObject.getPageSize());
	    		result.put("recordCount", pageObject.getTotalCount());
	    	}
	    	rsDto.setData(result);
	    	
    	} catch(Exception e){
			logger.error("ActivityCommodityApiServiceImpl-findActivityCommodityList-Exception=》获取活动商品列表", e);
//			rsDto = new ResultDto(ResultDto.ERROR_CODE, "faild:" + e);
			throw new ServiceException(e);
		}
		return rsDto;
	}

	//抢购预售
	@Override
	public ResultDto selectSaleBuy(Map<String, Object> params,
			int page, int pageSize) {
		// TODO Auto-generated method stub
		ResultDto rsDto = null;
    	try{
    		rsDto = new ResultDto(ResultDto.OK_CODE, "success");
    		Map<String,Object> result = new HashMap<String,Object>();
	    	Page pageObject = new Page();
	    	pageObject.setPageSize(pageSize);
	    	pageObject.setPageIndex(page);
	    	
	    	List<ActivityCommodity> commodityList = 
	    			(List<ActivityCommodity>) activityCommodityMapper.selectSaleBuy(pageObject, params);
	    	if(commodityList !=null && commodityList.size()>=1){
	    		result.put("items",commodityList);
	    		result.put("pageObject", pageObject);
	    	}
	    	rsDto.setData(result);
	    	
    	} catch(Exception e){
			logger.error("ActivityCommodityApiServiceImpl-findActivityCommodityList-Exception=》获取活动商品列表", e);
//			rsDto = new ResultDto(ResultDto.ERROR_CODE, "faild:" + e);
			throw new ServiceException(e);
		}
		return rsDto;
	}


	@Override
	public ResultDto selectNewSaleCatory(Map<String, Object> params) {
		// TODO Auto-generated method stub
				ResultDto rsDto = null;
		    	try{
		    		rsDto = new ResultDto(ResultDto.OK_CODE, "success");
			    	params.put("activityType", "PRE_SALE");
			    	params.put("orderByField", "number");
			    	params.put("orderBy", Constant.ORDER_BY);
			    	List<ActivityPreSaleTag> commodityList = activityPreSaleTagMapper.selectByParams(params);			    	
			    	rsDto.setData(commodityList);
			    	
		    	} catch(Exception e){
					logger.error("ActivityCommodityApiServiceImpl-selectSaleCatory-Exception=》获取预售类目", e);
					throw new ServiceException(e);
				}
				return rsDto;
	}

	@Override
	public ResultDto selectSaleCatory(Map<String, Object> params) {
		// TODO Auto-generated method stub
				ResultDto rsDto = null;
		    	try{
		    		rsDto = new ResultDto(ResultDto.OK_CODE, "success");
			    	
			    	List<ActivityCommodity> commodityList = 
			    			(List<ActivityCommodity>) activityCommodityMapper.selectSaleCatory(params);
			    	rsDto.setData(commodityList);
			    	
		    	} catch(Exception e){
					logger.error("ActivityCommodityApiServiceImpl-selectSaleCatory-Exception=》获取预售类目", e);
//					rsDto = new ResultDto(ResultDto.ERROR_CODE, "faild:" + e);
					throw new ServiceException(e);
				}
				return rsDto;
	}
	@Override
	public ResultDto searchCommodityAndActivity(String cid, String title, String sort, int desc, int page,
			int pageSize,String addressCode,String warehouseCode) {
		ResultDto resultDto = null;
		try{
			resultDto = new ResultDto(ResultDto.OK_CODE, Constant.SUCCESS);
			String whereSql = generatorSearchGoodsSql(title);
			Page pageObj = new Page(page, pageSize);
			Map<String,Object> params = new HashMap<>();
			params.put("sort", sort);
			params.put("cid", cid);
			params.put("desc", desc);
			params.put("addressCode", addressCode);
			params.put("warehouseCode", warehouseCode);
			params.put("whereSql", whereSql);
//			params.put("title", specialCharacterRemover(title));
			List<Map<String,Object>> queryResultList = activityCommodityMapper.searchCommodity(pageObj, params,true);
			Map<String,Object> result = new HashMap<>();
			if(queryResultList!=null &&queryResultList.size()>0){
				result.put("page", pageObj.getPageCount());
				result.put("pageSize", pageObj.getPageSize());
				result.put("recordCount", pageObj.getTotalCount());
				result.put("items", queryResultList);
			}
			resultDto.setData(result);
		}catch(Exception e){
			logger.error("ActivityCommodityApiServiceImpl.searchCommodityAndActivity INVOKE ERROR ===>>>",e);
//			return new ResultDto(ResultDto.ERROR_CODE, e.getMessage());
			throw new ServiceException(e);
		}
		return resultDto;
	}
	
	/**构建查询sql by ying.cai*/
	private  String generatorSearchGoodsSql (String title){
		if(StringUtil.isEmpty(title)){
			return null;
		}
    	StringBuilder result = new StringBuilder();
    	title = specialCharacterRemover(title.trim());
    	String [] titleArr = title.split(" ");
    	//定义要搜索的字段sql
    	String searchTitle = new StringBuilder("CONCAT(")
				.append( "IFNULL(c.NAME,\",\"),\",\"," )
				.append( "IFNULL(c.title,\",\"),\",\"," )
				.append( "IFNULL(c.alias_name,\"\"),\",\"," )
				.append( "IFNULL(c.sub_title,\",\"),\",\"," )
				.append( "IFNULL(c.keyword,\",\"),\",\"," )
				.append( "IFNULL(act.activity_title,\",\"))" ).toString();
    	if(titleArr.length>1){
    		for (int index = 0 ; index < titleArr.length ; index++) {
				String tmpStr = titleArr[index].trim();
    			if(tmpStr.length()==0){
					continue;
				}
    			if(result.length()>0){
    				result.append(" OR ");
    			}
    			result.append(searchTitle + " LIKE '%").append(tmpStr).append("%'");
			}
    	}else{
    		result.append(searchTitle + "LIKE '%").append(title.trim()).append("%'");
    	}
    	return result.toString();
    }
	
	/**过滤特殊字符 by ying.cai*/
	private  String specialCharacterRemover(String specialStr) {
//		if(StringUtils.isEmpty(specialStr)){
//			return null;
//		}
//		String [] illegalStr = {"<", ">", "&", "\"", "'", "!", "@", "#", "\\$", "%", "\\*",
//				"\\^", "\\(", "\\)", "_", "\\+", "\\=", "\\-", ",", "\\.", "，", "。", "\\?", "`", "~"};
//		for(String str :illegalStr){
//    		specialStr = specialStr.replaceAll(str, "");
//    	}
//      return specialStr;
		String regEx="[~·`!！@#￥$%^……&*（()）\\-——\\-_=+【\\[\\]】｛{}｝\\|、\\\\；;：:‘'“”\"，,《<。.》>、/？?]";   
		Pattern p = Pattern.compile(regEx);  
		Matcher m = p.matcher(specialStr);
		return m.replaceAll("");
	}
	
	@Override
	@Transactional(rollbackFor=Exception.class)
	public ResultDto updatePanicBuyRemind(String activityId, String commodityId, String memberId, String isRemind) {
		ResultDto rsDto = null;
		Integer result = 0;
		// 新增、删除抢购提醒（0：删除  1：新增）
		if (StringUtil.isNotNull(isRemind)) {
			Map<String,Object> params = new HashMap<String ,Object>();
			params.put("activityId", activityId);
			params.put("commodityId", commodityId);
			params.put("memberId", memberId);
			int count = panicBuyRemindMapper.selectCount(params);
			
			if ("CANCEL".equals(isRemind) && count > 0) {
				List<PanicBuyRemind> remindList = panicBuyRemindMapper.selectByPage(null, null, null, params);
				if (null != remindList && remindList.size() > 0) {
					// 删除
					result = panicBuyRemindMapper.deleteByPrimarayKeyForModel(remindList.get(0));
				}
				params.clear();
				params.put("activityId", activityId);
				params.put("goodsId", commodityId);
				params.put("memberId", memberId);
				params.put("type", MessTypeEnum.PREORDER);
				try {
					ResultDto message = memberMessageApiService.deleteMessByParams(params);
					if (!ResultDto.OK_CODE.equals(message.getCode())) {
						logger.info("取消抢购提醒操作，删除消息中心数据失败");
						throw new Exception();
					}
				} catch (Exception e) {
					logger.error("取消抢购提醒，删除消息中心数据失败 ===>>>",e);
					throw new ServiceException("消息数据删除失败");
				}
			} else if("REMIND".equals(isRemind)){
				if (count == 0) {
					PanicBuyRemind remind = new PanicBuyRemind();
					remind.setId(UUIDGenerator.getUUID());
					remind.setActivityId(activityId);
					remind.setCommodityId(commodityId);
					remind.setMemberId(memberId);
					remind.setIsRemind("1");
					// 新增
					result = panicBuyRemindMapper.insertSelective(remind);
					//save msg to db
					logger.info("============== 加入会员消息提醒 =============");
					// TODO 通过活动id和商品id查询活动、商品信息
					params.clear();
					params.put("id", activityId);
					// 商品信息
					ActivityCommodity commodity = activityCommodityMapper.selectActivityByParams(params);
					// 活动信息
					ActivityManager activityManager = activityManagerMapper.selectByPrimaryKey(commodity.getActivity().getId());
//					String actTitle = activityManager==null?"":(StringUtil.isNotNull(activityManager.getTitle())?activityManager.getTitle():"");
					String commodityTitle = commodity==null?"":(StringUtil.isNotNull(commodity.getActivityTitle())?commodity.getActivityTitle():"");
					String actTime = activityManager==null?"":(activityManager.getStartDate()==null?"":
							DateUtil.format(activityManager.getStartDate(), DateUtil.FORMAT_DATETIME));
					String sendMsgStr = "您关注的商品于"+actTime+"准时开抢，请准时哦!";
					MemberMessage memberMessage = new MemberMessage();
					memberMessage.setMemberId(memberId);
					memberMessage.setActivityId(activityId);
					memberMessage.setType(MessTypeEnum.PREORDER);
					memberMessage.setGoodsType(ActivityTypeEnum.PANIC_BUY.getValue());
					memberMessage.setTitle("抢购提醒");
					memberMessage.setGoodsId(commodityId);
					memberMessage.setGoodsTitle(commodityTitle);
					memberMessage.setGoodsPrice(commodity.getShowPrice());
					memberMessage.setImgPath(StringUtil.isNotNull(commodity.getPicPath())?commodity.getPicPath():"");
					memberMessage.setStatus("0");
					memberMessage.setContent(sendMsgStr);
					memberMessageApiService.addMess(memberMessage);
				}
			}
		}
		
		if (result > 0) {
			rsDto = new ResultDto(ResultDto.OK_CODE, Constant.SUCCESS);
		} else {
			rsDto = new ResultDto(ResultDto.ERROR_CODE, Constant.ERROR_CODE);
		}
		
		return rsDto;
	}


	@Override
	@Transactional(rollbackFor=Exception.class)
	public ResultDto updatefollowCount(String activityId, Integer followCountAdd) {
		// TODO Auto-generated method stub
		ResultDto rsDto = null;
    	try{
    		rsDto = new ResultDto(ResultDto.OK_CODE, "success");
    		ActivityCommodity activityCommodity=new ActivityCommodity();
    		activityCommodity.setId(activityId);
    		activityCommodity.setFollowCount(followCountAdd);
    		activityCommodityMapper.updatefollowCount(activityCommodity);
	    	
    	} catch(Exception e){
			logger.error("ActivityCommodityApiServiceImpl-updatefollowCount-Exception=》TODO(更新抢购的关注人数）", e);
//			rsDto = new ResultDto(ResultDto.ERROR_CODE, "faild:" + e);
			throw new ServiceException(e);
		}
		return rsDto;
	}


	@Override
	public ResultDto searchCommodityByCidAndSaleCount(Page pageObj,Map<String, Object> params) {
		
		ResultDto resultDto = null;
		try{
			resultDto = new ResultDto(ResultDto.OK_CODE, Constant.SUCCESS);
			List<Map<String,Object>> items = activityCommodityMapper.searchCommodityByCidAndSalecount(pageObj, params);
			pageObj.setRecords(items);
			resultDto.setData(pageObj);
		}catch(Exception e){ //分页会报错
			logger.error("ActivityCommodityApiServiceImpl.searchCommodityByCidAndSaleCount INVOKE ERROR ===>>>",e);
			pageObj.setRecords(new ArrayList<>());
			resultDto.setData(pageObj);
//			return new ResultDto(ResultDto.ERROR_CODE, e.getMessage());
			throw new ServiceException(e);
		}
		return resultDto;
	}	




	@Override
	@Transactional(rollbackFor=Exception.class)
	public ResultDto updatePanicBuyScheduleId(String activityId, String commodityId, String memberId,
			String scheduleId) {
		ResultDto rsDto = null;
		Integer result = 0;
		Map<String,Object> params = new HashMap<String ,Object>();
		params.put("activityId", activityId);
		params.put("commodityId", commodityId);
		params.put("memberId", memberId);
		params.put("scheduleId", scheduleId);
		result = panicBuyRemindMapper.updateScheduleIdByParams(params);
		if (result > 0) {
			rsDto = new ResultDto(ResultDto.OK_CODE, Constant.SUCCESS);
		} else {
			rsDto = new ResultDto(ResultDto.ERROR_CODE, Constant.ERROR_CODE);
		}
		
		return rsDto;
	}


	@Override
	public ResultDto getPanicBuyRemind(String activityId, String commodityId, String memberId) {
		ResultDto resultDto = null;
		resultDto = new ResultDto(ResultDto.OK_CODE, Constant.SUCCESS);
		Map<String,Object> params = new HashMap<String ,Object>();
		params.put("activityId", activityId);
		params.put("commodityId", commodityId);
		params.put("memberId", memberId);
		List<PanicBuyRemind> list = panicBuyRemindMapper.selectByParams(params);
		if(list.size() > 0){
			resultDto.setData(list.get(0));
			return resultDto;
		}
		return null;
	}


	@Override
	public ResultDto countRemindByMember(String memberId, Date startDate) {
		ResultDto resultDto = null;
		resultDto = new ResultDto(ResultDto.OK_CODE, Constant.SUCCESS);
		Map<String,Object> params = new HashMap<String ,Object>();
		params.put("memberId", memberId);
		params.put("startDate", startDate);
		int count = panicBuyRemindMapper.countRemindByMember(params);
		resultDto.setData(count);
		return resultDto;
	}


	@Override
	@Transactional
	public ResultDto deleteActivityCommodity(String  commodityCode) {
		// TODO Auto-generated method stub
		boolean isupdateseach=true;//是否更新搜索引擎
		boolean isMain=false;//是否主商品
		ResultDto rsDto = null;
    	try{
    		rsDto = new ResultDto(ResultDto.OK_CODE, "success");
    		if(StringUtil.isEmpty(commodityCode)){
    			return rsDto;
    		}
			Map<String, Object> params=new HashMap<String, Object>();
			params.put("commodityNo", commodityCode);//根据商品编码查询商品信息
			List<ActivityCommodity> activityCommodities= activityCommodityMapper.selectByParamsAndMianCommodity(params);
			if(activityCommodities==null || activityCommodities.size()==0){
				return rsDto;
			}
			for (ActivityCommodity activityCommodity : activityCommodities) {
				//判断是否主商品
				if(activityCommodity.getCommodity() != null && activityCommodity.getCommodity().getGiveType()!=null &&
						activityCommodity.getCommodity().getGiveType().equals(CouponConstant.one)){
					isMain=true;
				}
				if(isupdateseach){//更新一次搜索引擎和商品状态为普通商品
					updateSeachAndCommodity(activityCommodity);
					if(isMain){
						updateMainCommodityInfo(commodityCode);//更新买赠商品
					}
				}
				deleteActivityAndSku(activityCommodity);//删除管理的活动数据
				isupdateseach=false;
				
			}
//    			rsDto.setData(activityCommodityMapper.deleteActivityAllSku(commoditys));
			logger.info(commodityCode+"-----将此添加到活动商品的商品删除");
    		
    	} catch(Exception e){
			logger.error("", e);
//			rsDto = new ResultDto(ResultDto.ERROR_CODE, "faild:" + e);
			throw new ServiceException(e);
		}
    	logger.info("删除成功数据---"+rsDto.getData());
		return rsDto;
	}
	//删除买赠主商品，更新商品为普通商品
	private void updateMainCommodityInfo(String commoditycode){
		Map<String, Object> params=new HashMap<String, Object>();
		params.put("commodityCode", commoditycode);
		List<ActivityGive> activityCommodities=activityGiveService.findByBiz(params);
		for (ActivityGive activityGive : activityCommodities) {
			activityGive.setDelFlag(Constant.YES);
			activityGiveService.deleteAndhuifu(activityGive);
		}
	}
	private void deleteActivityAndSku(ActivityCommodity activityCommodity){
		activityCommdityMapper.deleteByPrimaryKey(activityCommodity.getId());
		activityCommoditySkuMapper.deleteByPrimaryKeycommodity(activityCommodity.getId());
	}
	private void updateSeachAndCommodity(ActivityCommodity activityCommodity){
		Vector<String> commodityCode=new Vector<>();
		commodityCode.add(activityCommodity.getCommodityNo());
		commodityApiConsumer.updateCommodityTypeyByCode(commodityCode,"COMMON_BUY");
		if(ActivityTypeEnum.NEWUSER_VIP.equals(activityCommodity.getActivityType())
				|| ActivityTypeEnum.WHOLESALE_MANAGER.equals(activityCommodity.getActivityType())){
			updateActivityIndex.removeSpecialActivity(activityCommodity.getCommodityNo(), "COMMON_BUY");//新用户，普通商品，变更商品售卖类型，当成普通商品售卖
		}else{
			removeActivityIndex.sendMqByRemoveActivity(activityCommodity.getId());//删除活动索引，es自己把商品售卖类型改成普通
		}
	}


	@Override
	public ResultDto selectNewSaleBuy(Map<String, Object> map, int page, int pageSize) {
		// TODO Auto-generated method stub
				ResultDto rsDto = null;
		    	try{
		    		rsDto = new ResultDto(ResultDto.OK_CODE, "success");
		    		Map<String,Object> result = new HashMap<String,Object>();
			    	Page pageObject = new Page();
			    	pageObject.setPageSize(pageSize);
			    	pageObject.setPageIndex(page);
			    	
			    	List<ActivityCommodity> commodityList = 
			    			(List<ActivityCommodity>) activityCommodityMapper.selectNewSaleBuy(pageObject, map);
			    	if(commodityList !=null && commodityList.size()>=1){
			    		result.put("items",commodityList);
			    		result.put("pageObject", pageObject);
			    	}
			    	rsDto.setData(result);
			    	
		    	} catch(Exception e){
					logger.error("ActivityCommodityApiServiceImpl-findActivityCommodityList-Exception=》获取活动商品列表", e);
//					rsDto = new ResultDto(ResultDto.ERROR_CODE, "faild:" + e);
					throw new ServiceException(e);
				}
				return rsDto;
	}
	
	@Override
	public ResultDto findActivityCommodity(Map<String,Object> params) {
		// TODO Auto-generated method stub
		ResultDto rsDto = null;
    	try{
    		rsDto = new ResultDto(ResultDto.OK_CODE, "success");
	    	ActivityCommodity activityCommodity =  activityCommodityMapper.selectActivityCommodityByCommodityId(params);
	    	
	    	rsDto.setData(activityCommodity);
	    	
    	} catch(Exception e){
			logger.error("ActivityCommodityApiServiceImpl-getActivityCommodity-Exception=》获取活动商品详情", e);
//			rsDto = new ResultDto(ResultDto.ERROR_CODE, "faild:" + e);
			throw new ServiceException(e);
		}
		return rsDto;
	}

	
}
