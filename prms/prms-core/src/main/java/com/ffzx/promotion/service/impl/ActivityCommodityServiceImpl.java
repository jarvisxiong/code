package com.ffzx.promotion.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.annotation.Resource;

import net.sf.json.JSONArray;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ffzx.bms.api.dto.StockUsed;
import com.ffzx.commerce.framework.constant.RedisPrefix;
import com.ffzx.commerce.framework.dao.CrudMapper;
import com.ffzx.commerce.framework.enums.ServiceCode;
import com.ffzx.commerce.framework.enums.ServiceResultCode;
import com.ffzx.commerce.framework.exception.ServiceException;
import com.ffzx.commerce.framework.page.Page;
import com.ffzx.commerce.framework.service.impl.BaseCrudServiceImpl;
import com.ffzx.commerce.framework.utils.DateUtil;
import com.ffzx.commerce.framework.utils.RedisLock;
import com.ffzx.commerce.framework.utils.RedisUtil;
import com.ffzx.commerce.framework.utils.StringUtil;
import com.ffzx.commerce.framework.vo.ResultVo;
import com.ffzx.order.api.enums.BuyTypeEnum;
import com.ffzx.promotion.api.dto.ActivityCommodity;
import com.ffzx.promotion.api.dto.ActivityCommoditySku;
import com.ffzx.promotion.api.dto.ActivityCommodityVo;
import com.ffzx.promotion.api.dto.ActivityEsVo;
import com.ffzx.promotion.api.dto.ActivityManager;
import com.ffzx.promotion.api.dto.constant.Constant;
import com.ffzx.promotion.api.dto.constant.PrmsConstant;
import com.ffzx.promotion.api.enums.ActivityTypeEnum;
import com.ffzx.promotion.api.service.RemoveActivityIndex;
import com.ffzx.promotion.api.service.UpdateActivityIndex;
import com.ffzx.promotion.api.service.consumer.CommodityApiConsumer;
import com.ffzx.promotion.api.service.consumer.CommoditySkuApiConsumer;
import com.ffzx.promotion.api.service.consumer.StockManagerApiConsumer;
import com.ffzx.promotion.io.ExcelCall;
import com.ffzx.promotion.io.ExcelCallForNewUserActivity;
import com.ffzx.promotion.io.ExcelCallForOrdinaryActivity;
import com.ffzx.promotion.io.ExcelCallForWholesale;
import com.ffzx.promotion.mapper.ActivityCommodityMapper;
import com.ffzx.promotion.mapper.ActivityCommoditySkuMapper;
import com.ffzx.promotion.mapper.ActivityManagerMapper;
import com.ffzx.promotion.model.CommoditySkuTransform;
import com.ffzx.promotion.service.ActivityCommodityService;
import com.ffzx.promotion.service.ActivityCommoditySkuService;
import com.ffzx.promotion.service.ActivityManagerService;
import com.ffzx.promotion.service.ActivityPreSaleTagService;
import com.ffzx.promotion.service.GiftCommodityService;

/**
 * 
 * @author yuzhao.xu
 * @email  yuzhao.xu@ffzxnet.com
 * @date 2016年5月11日 下午4:25:32
 * @copyright www.ffzxnet.com
 */
@Service("activityCommdityService")
public class ActivityCommodityServiceImpl extends BaseCrudServiceImpl implements ActivityCommodityService {

	@Autowired
	public ActivityManagerService activityManagerService;
	@Autowired
	private StockManagerApiConsumer stockManagerApiConsumer;

	@Autowired
	private ActivityCommoditySkuService activityCommoditySkuService;
    @Resource
    private ActivityCommodityMapper activityCommdityMapper;
    
    @Autowired
    private ActivityCommoditySkuMapper activityCommoditySkuMapper;

    @Resource
    private ActivityCommoditySkuMapper activityCommditySkuMapper;

    @Autowired
    private ActivityPreSaleTagService activityPreSaleTagService;
    @Autowired
    private ActivityCommoditySkuService ctivityCommoditySkuService;
    @Autowired
    private ActivityManagerMapper activityManagerMapper;
    
    @Autowired
    private CommodityApiConsumer commodityApiConsumer;
    @Autowired
    private CommoditySkuApiConsumer commoditySkuApiConsumer;
	@Autowired
	private GiftCommodityService giftCommodityService;
    @Autowired
    private RedisUtil redis;
    
    @Autowired
	private RemoveActivityIndex removeActivityIndex;
    @Autowired
    private UpdateActivityIndex updateActivityIndex;
    @Override
    public CrudMapper init() {
        return activityCommdityMapper;
    }
    
	@Override
	public List<ActivityCommodity> findActivityCommdity(Page page, Map<String, Object> params) throws ServiceException {
		String orderByField=null;
		String orderBy=null;
		if(StringUtil.isNotNull(params.get("orderByField"))){
			orderByField=params.get("orderByField").toString();
		}
		if(StringUtil.isNotNull(params.get("orderBy"))){
			orderBy=params.get("orderBy").toString();
		}
		return this.activityCommdityMapper.selectByPage(page, orderByField, orderBy, params);
	}
	/**
	 * @author 雷
	 * @date 2016年6月23日
	 * (非 Javadoc)
	 * <p>Title: selectByAdvert</p>
	 * <p>Description:活动商品详情选择器 </p>
	 * @param pageObj
	 * @param params
	 * @return
	 * @see com.ffzx.promotion.service.ActivityCommodityService#selectByAdvert(com.ffzx.commerce.framework.page.Page, java.util.Map)
	 */
	@Override
	public List<ActivityCommodityVo> selectByAdvert(Page pageObj, Map<String, Object> params) {
		return this.activityCommdityMapper.selectByAdvert(pageObj,params);
	}
	
	@Override
	public List<ActivityCommodity> findActivityList(Page page, Map<String, Object> params) throws ServiceException {
		String orderByField=null;
		String orderBy=null;
		if(StringUtil.isNotNull(params.get("orderByField"))){
			orderByField=params.get("orderByField").toString();
		}
		if(StringUtil.isNotNull(params.get("orderBy"))){
			orderBy=params.get("orderBy").toString();
		}
		return this.activityCommdityMapper.getActivityList(page, orderByField, orderBy, params);
	}

	@Override
	@Transactional(rollbackFor=Exception.class)
	public Map<String, Object> toSetCommdity(String activityId,String activityCommodityId) {
		Map<String, Object> map=new HashMap<String, Object>();
		// TODO Auto-generated method stub
		if(StringUtil.isNotNull(activityId)){
			try {
				//活动信息
				ActivityManager activity=this.activityManagerService.findById(activityId);
				map.put("activity", activity);
				if(StringUtil.isNotNull(activityCommodityId)){
					//编辑对象商品信息
					ActivityCommodity activityCommodity=findById(activityCommodityId);
					map.put("activityCommodity", activityCommodity);
					//编辑对象商品对应的sku
					Map<String, Object> params=new HashMap<>();
					params.put("activityCommodity", activityCommodity);
					List<ActivityCommoditySku> activityCommoditySkuList=this.activityCommoditySkuService.findByBiz(params);
					List<CommoditySkuTransform> skuTransformList= new ArrayList<CommoditySkuTransform>();
					List<String> skuCodeList = new ArrayList<String>();
					for(ActivityCommoditySku sku : activityCommoditySkuList){
						skuCodeList.add(sku.getCommoditySkuNo());
					}

					Map<String, StockUsed> resultMap = this.stockManagerApiConsumer.getStockbySkuCode(skuCodeList);
					for (String skuCode : resultMap.keySet()) {
					  StockUsed stockUsed = (StockUsed) resultMap.get(skuCode);
					  for(ActivityCommoditySku sku : activityCommoditySkuList){
						  if(sku.getCommoditySkuNo().equals(skuCode)){
							  CommoditySkuTransform skuTransform = new CommoditySkuTransform();
							  skuTransform.setBarcode(sku.getCommoditySkuBarcode());
							  skuTransform.setCommodityAttributeValues(sku.getAttrGroup());
							  skuTransform.setFavourablePrice(sku.getCommoditySkuPrice());
							  skuTransform.setId(sku.getCommoditySkuId());
							  skuTransform.setSkuCode(sku.getCommoditySkuNo());
							  skuTransform.setUserCanBuy(stockUsed.getUserCanBuy());
							  skuTransform.setActivityPrice(sku.getActivityPrice());
							  skuTransform.setLimitCount(sku.getLimitCount());
							  skuTransform.setCommoditySkuTitle(sku.getCommoditySkuTitle());
							  skuTransformList.add(skuTransform);
							  break;
						  }
					  }

					}
					map.put("activityCommoditySkuList", skuTransformList);
				}
			} catch (ServiceException e) {
				logger.error("ActivityCommodityServiceImpl-toSetCommdity-ServiceException=》抢购管理-弹窗选择商品页面-ServiceException", e);
				throw new ServiceException(e);
			}

		}
		return map;
	}

	@Override
	@Transactional(rollbackFor=Exception.class)
	public ServiceCode saveCommdity(ActivityCommodity activityCommdity,String viewStatus) throws ServiceException {
		int result = 0;
		try {
			if(activityCommdity.getIsNeworder()==null){
				activityCommdity.setIsNeworder("0");
			}
			if(activityCommdity.getEnableSpecialCount()==null){
				activityCommdity.setEnableSpecialCount("0");
			}
			if(activityCommdity.getIslimit()==null){
				activityCommdity.setIslimit("0");
			}
			String showPric=activityCommdity.getShowPrice();
			if(showPric.indexOf("-")>=0){
				String[] maxmain=showPric.split("-");
				activityCommdity.setMinPrice(new BigDecimal(maxmain[0].trim()));
				activityCommdity.setMaxPrice(new BigDecimal(maxmain[1].trim()));
			}else{
				activityCommdity.setMinPrice(new BigDecimal(showPric.trim()));
				activityCommdity.setMaxPrice(new BigDecimal(showPric.trim()));
			}
			if (StringUtil.isNotNull(activityCommdity.getId())) {
				//修改
				ActivityCommodity	commdity=this.findById(activityCommdity.getId());
				if(commdity!=null){
					//开始时间;结束时间;发货时间
					getDate(activityCommdity, commdity);
					//是否推荐商品
					if(commdity.getIsRecommend()!=activityCommdity.getIsRecommend()){
						activityCommdity.setIsRecommend(activityCommdity.getIsRecommend());
					}
					//推荐商品排序
					if(commdity.getRecommendSort()!=activityCommdity.getRecommendSort()){
						activityCommdity.setRecommendSort(activityCommdity.getRecommendSort());
					}
					//推荐商品设置图片
					if (commdity.getPicPath() != activityCommdity.getPicPath()) {
						activityCommdity.setPicPath(activityCommdity.getPicPath());
					}
					//是否显示下单用户
					if (StringUtil.isNotNull(activityCommdity.getIsNeworder())) {
						if(!activityCommdity.getIsNeworder().equals(commdity.getIsNeworder())){
							activityCommdity.setIsNeworder(activityCommdity.getIsNeworder());
						}
					}
					//用户限购量为空默认为0
					if(null==activityCommdity.getIdLimitCount()){
						activityCommdity.setIdLimitCount(0);
					}
					result=this.activityCommdityMapper.updateByPrimaryKeySelective(activityCommdity);
					String oldLimitCount=activityCommdity.getOldlimitCount();//老限购量
					Integer limitCount=activityCommdity.getLimitCount();//当前设置的限购量
					String activityCommodityStatus=activityCommdity.getActivityCommodityStatus();//是否已经抢完标志
					//编辑的时候如果当前设置的商品已经抢完，
					if(StringUtils.isNotEmpty(activityCommodityStatus)&&activityCommodityStatus.equals("3")){
						//当前设置的限购量大于老限购量的时候回滚该商品为进行中
						if(Integer.valueOf(oldLimitCount)<limitCount){
							this.activityCommdityMapper.updateActivityCommodityStatusIng(activityCommdity.getId());
						}
					}		
					// 普通活动、批发没有限购量
					if (!ActivityTypeEnum.ORDINARY_ACTIVITY.equals(activityCommdity.getActivityType()) 
							&& !ActivityTypeEnum.WHOLESALE_MANAGER.equals(activityCommdity.getActivityType())) {
						// 缓存限购量
						setRedisDate(activityCommdity);
					}
					if(result > 0){    //将原活动商品调用dubbo接口修改原商品售卖类型
						Vector<String> commodityCode=new Vector<>();
						commodityCode.add(commdity.getCommodityNo());
						int update = this.commodityApiConsumer.updateCommodityTypeyByCode(commodityCode,BuyTypeEnum.COMMON_BUY.getValue());
						if(update!=ServiceCode.SUCCESS){
							logger.error("commodityApiConsumer-updateCommodityBuyType-Exception=》商品购买类型修改-Exception", "商品购买类型修改失败");
							throw new Exception();
						}
					}else{
						logger.error("activityCommdityMapper-updateByPrimaryKeySelective-Exception=》更新活动商品设置信息失败-Exception", "活动商品sku保存失败");
						throw new Exception();
					}
				}else{
					//开始时间;结束时间;发货时间
					getDate(activityCommdity, commdity);
					
					result=this.activityCommdityMapper.insertSelective(activityCommdity);
				}				
			}
			ServiceCode skustatus =null;
			if(result>0){
				//新增sku
				String activityitemDate=StringUtils.chomp(String.valueOf(activityCommdity.getActivityitemDate()));
				List<ActivityCommoditySku> commoditySkuList=null;
				if(!StringUtils.isEmpty(activityitemDate)){
					commoditySkuList = new ArrayList<ActivityCommoditySku>(JSONArray.toCollection(JSONArray.fromObject(activityitemDate), ActivityCommoditySku.class));
					skustatus = this.ctivityCommoditySkuService.saveCommdity(activityCommdity.getActivity(),activityCommdity,commoditySkuList,viewStatus,activityCommdity.getActivityType());
				}
				// 普通活动、批发没有限购量
				if (!ActivityTypeEnum.ORDINARY_ACTIVITY.equals(activityCommdity.getActivityType()) 
						&& !ActivityTypeEnum.WHOLESALE_MANAGER.equals(activityCommdity.getActivityType())) {
					// 缓存限购量
					setRedisDate(activityCommdity);
				}
				if(skustatus.SUCCESS == 0){
					Vector<String> commodityCode=new Vector<>();
					commodityCode.add(activityCommdity.getCommodityNo());
					int update = this.commodityApiConsumer.updateCommodityTypeyByCode(commodityCode,activityCommdity.getActivityType().getValue());
					if(update!=ServiceCode.SUCCESS){
						logger.error("commodityApiConsumer-updateCommodityBuyType-Exception=》商品购买类型修改-Exception", "商品购买类型修改失败");
						throw new Exception();
					}
				}else{
					logger.error("ctivityCommoditySkuService-saveCommdity-Exception=》活动商品sku保存-Exception", "活动商品sku保存失败");
					throw new Exception();
				}
				
			}	
		} catch (Exception e) {
			logger.error("CouponAdminServiceImpl-saveCommdity-Exception=》活动商品新增-Exception", e);
			throw new ServiceException(e);
		}
		return result > 0 ? ServiceResultCode.SUCCESS : ServiceResultCode.FAIL;
	}

	private void setRedisDate(ActivityCommodity commodity) {
		int limitCount=commodity.getLimitCount();//限购量
		String commodityNo=commodity.getCommodityNo();//商品编码
		String activityCommodityId=commodity.getId();//活动管理商品ID					
		//商品ID限购量存入缓存
		//if(commodity.getIdLimitCount()!=null && commodity.getIdLimitCount()!=0){
			redis.set(PrmsConstant.getCommodityIdLimitKey(activityCommodityId, commodityNo),commodity.getIdLimitCount());
		//}					
		//如果是预售把发货时间存入缓存
		if(commodity.getActivityType().equals(ActivityTypeEnum.PRE_SALE)){
			redis.set(PrmsConstant.getPreSaleDevliveDateKey(activityCommodityId, commodityNo), commodity.getDeliverDate());
		}
		//把该商品所有总限购量存入缓存
		redis.set(PrmsConstant.getCommodityLimitKey(activityCommodityId, commodityNo),limitCount);
		//该活动商品对象存入
		redis.set(PrmsConstant.getActivityCommodityObj(activityCommodityId, commodityNo), commodity);
	}
	
	/*****
	 * 时间转换
	 * @param activityCommdity
	 * @param commdity
	 */
	private void getDate(ActivityCommodity activityCommdity, ActivityCommodity commdity) {
			try {
				if(StringUtil.isNotNull(activityCommdity.getEndDateStr())){
					activityCommdity.setEndDate(DateUtil.parseTime(activityCommdity.getEndDateStr()));
				}
				if(StringUtil.isNotNull(activityCommdity.getStartDateStr())){
					activityCommdity.setStartDate(DateUtil.parseTime(activityCommdity.getStartDateStr()));
				}
				if(StringUtil.isNotNull(activityCommdity.getDeliverDateStr())){
					activityCommdity.setDeliverDate(DateUtil.parseTime(activityCommdity.getDeliverDateStr()));
				}
			} catch (Exception e) {
				logger.error("CouponAdminServiceImpl-save-Exception=》促销管理-优惠券新增-Exception", e);
				throw new ServiceException(e);
			}
	}

	

	@Override
	@Transactional(rollbackFor=Exception.class)
	public int deleteCommandSku(String id, String No) throws ServiceException,Exception {
		int result = 0;
		int res = 0;
		
		ActivityCommodity activityCommodity = activityCommdityMapper.selectByPrimaryKey(id);
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("commodityId",activityCommodity.getCommodityId());
		List<ActivityCommodity> activityCommodityList = activityCommdityMapper.selectByParams(params);
		activityCommdityMapper.deleteByPrimaryKey(id);
		result = activityCommoditySkuMapper.deleteByPrimaryKeycommodity(id);
		if(activityCommodityList!=null && activityCommodityList.size() == 1){
			//更新商品状态
			Vector<String> commodityCode=new Vector<>();
			commodityCode.add(No);
			//更新商品状态
			if (result > 0) {
				res = this.commodityApiConsumer.updateCommodityTypeyByCode(commodityCode,"COMMON_BUY");
			}
		}
		// 判断doubble接口是否调用成功，否则：事务回滚
		if (res == ServiceCode.FAIL) {
			logger.error("commodityApiConsumer-updateCommodityTypeyByCode-ServiceException=》商品购买类型修改-Exception", "商品购买类型修改失败");
			throw new Exception();
		}
		//2016-9-14 add by ying.cai reason 删除活动商品数据，则还原搜索引擎仓库商品数据
		//2016-11-16 改成 传活动id
//		removeActivityIndex.sendMqByRemoveActivity(activityCommodity.getCommodityId());
		//add by ying.cai 2016-11-18 给ES推送变更信息
		if(ActivityTypeEnum.NEWUSER_VIP.equals(activityCommodity.getActivityType())
				|| ActivityTypeEnum.WHOLESALE_MANAGER.equals(activityCommodity.getActivityType())){
			updateActivityIndex.removeSpecialActivity(activityCommodity.getCommodityNo(), "COMMON_BUY");//新用户，普通商品，变更商品售卖类型，当成普通商品售卖
		}else{
			removeActivityIndex.sendMqByRemoveActivity(activityCommodity.getId());//删除活动索引，es自己把商品售卖类型改成普通
		}
		return result;
	}
	

	@Override
	@Transactional(rollbackFor=Exception.class)
	public int deleteCommandSkus(String[] ids) throws ServiceException,Exception {
		int result = 0;
		int res = 0;
		List<String> commodityIdList = new ArrayList<>();
		Vector<String> commodityCode=new Vector<>();
		ActivityTypeEnum activityType = null;
		for (String id : ids) {    //循环获取商品code
			ActivityCommodity activityCommodity = activityCommdityMapper.selectByPrimaryKey(id);
			if(null==activityType){
				activityType = activityCommodity.getActivityType();
			}
			Map<String,Object> params = new HashMap<String,Object>();
			params.put("commodityId",activityCommodity.getCommodityId());
			List<ActivityCommodity> activityCommodityList = activityCommdityMapper.selectByParams(params);
			if(activityCommodityList!=null && activityCommodityList.size() == 1){
				commodityCode.add(activityCommodity.getCommodityNo());
			}
			activityCommdityMapper.deleteByPrimaryKey(id);
			result += activityCommoditySkuMapper.deleteByPrimaryKeycommodity(id);
			//edit by ying.cai，改成取活动id
			commodityIdList.add(activityCommodity.getId());
		}
		//更新商品状态
		if (result > 0 && commodityCode != null && commodityCode.size() > 0) {
			res = commodityApiConsumer.updateCommodityTypeyByCode(commodityCode,"COMMON_BUY");
		}
		// 判断doubble接口是否调用成功，否则：事务回滚
		if (res == ServiceCode.FAIL) {
			logger.error("commodityApiConsumer-updateCommodityTypeyByCode-ServiceException=》商品购买类型修改-Exception", "商品购买类型修改失败");
			throw new Exception();
		}
		
		//add by ying.cai 2016-11-18 给ES推送变更信息
		if(ActivityTypeEnum.NEWUSER_VIP.equals(activityType)
				|| ActivityTypeEnum.WHOLESALE_MANAGER.equals(activityType)){
			for(String code : commodityCode){
				updateActivityIndex.removeSpecialActivity(code, "COMMON_BUY");
			}
		}else{
			//2016-9-14 add by ying.cai reason 批量删除活动商品数据，则还原搜索引擎仓库商品数据
			removeActivityIndex.sendMqByRemoveActivity(commodityIdList);
		}
		return result;
	}
	

	@Override
	public List<ActivityCommodity> findList(Page pageObj, String orderByField, String orderBy,
			ActivityCommodity activityCommdity) throws ServiceException {
		Map<String, Object> params = new HashMap<String, Object>();

		//筛选添加活动标题
		if(StringUtil.isNotNull(activityCommdity.getActivityTitle())){
			params.put("activityTitle", activityCommdity.getActivityTitle());
		}
		//筛选添加商品条形码
		if(StringUtil.isNotNull(activityCommdity.getCommodityBarcode())){
			params.put("commodityBarcode", activityCommdity.getCommodityBarcode());
		}
		//筛选添加活动id
		if(activityCommdity.getActivity()!=null){
			params.put("activity", activityCommdity.getActivity());
			if(StringUtil.isNotNull(activityCommdity.getActivity().getId())){
				params.put("activity.id", activityCommdity.getActivity().getId());
			}
		}
		// 筛选添加是否推荐商品
		if (StringUtil.isNotNull(activityCommdity.getIsRecommend())) {
			params.put("isRecommend", activityCommdity.getIsRecommend());
		}
		
		return activityCommdityMapper.selectByPage(pageObj, orderByField, orderBy, params);
	}

	private final String lockkey="ActivityCommodityServiceImpl-importExcelPanicbuy";
	@Override
	@Transactional(rollbackFor=Exception.class)
	public Map<String,Object> importExcelPanicbuy(List<String[]> listRow,ActivityTypeEnum enum1,String id) throws Exception {
		// TODO Auto-generated method stub
		Map<String,Object> result = new HashMap<>();
		RedisLock redisLock=new RedisLock(redis, lockkey, 30 * 1000, 30 * 1000, 2000);
		try{
			if(redisLock.acquire()){
				ActivityManager activityManager= activityManagerMapper.selectByPrimaryKey(id);
				verificationData(listRow,activityManager,enum1);
			}
		}catch(Exception e){
			logger.error("",e);
			throw new  Exception(e);
		}finally{
			redisLock.release();
		}
		result.put("ACTIVITY_ITEMS", activityCommditys);
		result.put("MSG", "此次导入成功匹配有效数据"+comparisonList.size()+"条");
		return result;
	}

	
	protected static Vector<String> comparisonList ;
	protected static Vector<String> comparisonListcode;
	protected static List<ActivityCommodity> activityCommditys;
	protected static List<ActivityCommoditySku> activityCommoditySkus;
	protected static StringBuffer errorVaildate;//错误验证
	
	@Transactional(rollbackFor=Exception.class)
	private void verificationData(List<String[]> listRow,ActivityManager activityManager,ActivityTypeEnum enum1) throws Exception{
		
		activityCommditys=new ArrayList<ActivityCommodity>();
		activityCommoditySkus=new ArrayList<ActivityCommoditySku>();
		comparisonListcode= new Vector<String>();// 商品编码
		errorVaildate=new StringBuffer();
		if(listRow.size()<=1){
			throw new Exception("至少两行数据，一行标题");
		}
		comparisonList = new Vector<String>();// 比较条形码
		if(ActivityTypeEnum.PANIC_BUY.equals(enum1) || ActivityTypeEnum.PRE_SALE.equals(enum1)){  //预售抢购excel验证
			validatepanicbuyActivity(listRow, activityManager, enum1);
		}else if(ActivityTypeEnum.NEWUSER_VIP.equals(enum1)){  //新用户专享excel验证
			validateNewUserActivity(listRow, activityManager, enum1);
		}else if(ActivityTypeEnum.WHOLESALE_MANAGER.equals(enum1)){  //批发活动excel验证
			validateWholesale(listRow, activityManager, enum1);
		}else if(ActivityTypeEnum.ORDINARY_ACTIVITY.equals(enum1)){  //普通活动excel验证
			validateOrdinaryActivity(listRow, activityManager, enum1);
		}
		if(errorVaildate!=null && errorVaildate.toString()!=null && !StringUtil.isEmpty(errorVaildate.toString()) ){
			throw new Exception(errorVaildate.toString());
		}
		// 导入活动商品表、sku表
		for (ActivityCommoditySku activityCommoditySku : activityCommoditySkus) {
			activityCommditySkuMapper.insertSelective(activityCommoditySku);
		}
		for (ActivityCommodity activityCommodity : activityCommditys) {
			activityCommdityMapper.insertSelective(activityCommodity);
		}
		// 修改商品系统的商品类型
		int result = commodityApiConsumer.updateCommodityTypeyByCode(comparisonListcode, enum1.getValue());
		// 修改失败，抛出异常
		if (ServiceCode.FAIL == result) {
			logger.error("商品类型修改", "商品类型修改失败");
			throw new Exception();
		}
	}

	private void validatepanicbuyActivity(List<String[]> listRow,ActivityManager activityManager,ActivityTypeEnum enum1) throws  Exception{
		
		int ionum=30;
		if(listRow.size()<30){
			ionum=listRow.size();
		}
		// 创建一个执行任务的服务
		ExecutorService es = Executors.newFixedThreadPool(ionum);
		try{
			List<ExcelCall> excelCalls=new ArrayList<ExcelCall>();
			List<Future<String>> fList = new ArrayList<Future<String>>();
		
			ExcelCall excelCall=null;
			for(int i=0;i<listRow.size();i++){
				excelCall=new ExcelCall(i,listRow.get(i),activityManager,enum1
						,ctivityCommoditySkuService,commoditySkuApiConsumer,activityCommoditySkuService
						,activityPreSaleTagService);//这个多线程可以接受异常
				excelCalls.add(excelCall);
			}
			fList=es.invokeAll(excelCalls);
			for (Future<String> future : fList) {
				future.get();
			}
		}catch(Exception e){
			throw new Exception(e);
		}finally{
			es.shutdown();
		}
	}
	
	private void validateNewUserActivity(List<String[]> listRow,ActivityManager activityManager,ActivityTypeEnum enum1) throws Exception{
		int ionum=30;
		if(listRow.size()<30){
			ionum=listRow.size();
		}
		// 创建一个执行任务的服务
		ExecutorService es = Executors.newFixedThreadPool(ionum);

		try{
			List<ExcelCallForNewUserActivity> excelCalls=new ArrayList<ExcelCallForNewUserActivity>();
			List<Future<String>> fList = new ArrayList<Future<String>>();
	
			ExcelCallForNewUserActivity excelCall=null;
			for(int i=0;i<listRow.size();i++){
				excelCall=new ExcelCallForNewUserActivity(i,listRow.get(i),activityManager,enum1
						,ctivityCommoditySkuService,commoditySkuApiConsumer,activityCommoditySkuService);//这个多线程可以接受异常
				excelCalls.add(excelCall);
			}
	
			for (ExcelCallForNewUserActivity excelCall1 : excelCalls) {
				Future<String> future=es.submit(excelCall1);
				fList.add(future);
			}
			for (Future<String> future : fList) {
				future.get();
			}
		}catch(Exception e){
			throw new Exception(e);
		}finally{
			es.shutdown();
		}
	}
	
	private void validateOrdinaryActivity(List<String[]> listRow,ActivityManager activityManager,ActivityTypeEnum enum1) throws Exception{
		int ionum=30;
		if(listRow.size()<30){
			ionum=listRow.size();
		}
		// 创建一个执行任务的服务
		ExecutorService es = Executors.newFixedThreadPool(ionum);

		try{
			List<ExcelCallForOrdinaryActivity> excelCalls=new ArrayList<ExcelCallForOrdinaryActivity>();
			List<Future<String>> fList = new ArrayList<Future<String>>();
	
			ExcelCallForOrdinaryActivity excelCall=null;
			for(int i=0;i<listRow.size();i++){
				excelCall=new ExcelCallForOrdinaryActivity(i,listRow.get(i),activityManager,enum1
						,ctivityCommoditySkuService,commoditySkuApiConsumer);//这个多线程可以接受异常
				excelCalls.add(excelCall);
			}
	
			for (ExcelCallForOrdinaryActivity excelCall1 : excelCalls) {
				Future<String> future=es.submit(excelCall1);
				fList.add(future);
			}
			for (Future<String> future : fList) {
				future.get();
			}
		}catch(Exception e){
			throw new Exception(e);
		}finally{
			es.shutdown();
		}
	}
	
	private void validateWholesale(List<String[]> listRow,ActivityManager activityManager,ActivityTypeEnum enum1) throws Exception{
		int ionum=20;
		if(listRow.size()<20){
			ionum=listRow.size();
		}
		// 创建一个执行任务的服务
		ExecutorService es = Executors.newFixedThreadPool(ionum);

		try{
			List<ExcelCallForWholesale> excelCalls=new ArrayList<ExcelCallForWholesale>();
			List<Future<String>> fList = new ArrayList<Future<String>>();
	
			ExcelCallForWholesale excelCall=null;
			for(int i=0;i<listRow.size();i++){
				excelCall=new ExcelCallForWholesale(i,listRow.get(i),activityManager,enum1
						,ctivityCommoditySkuService,commodityApiConsumer,commoditySkuApiConsumer,giftCommodityService);//这个多线程可以接受异常
				excelCalls.add(excelCall);
			}
	
			for (ExcelCallForWholesale excelCall1 : excelCalls) {
				Future<String> future=es.submit(excelCall1);
				fList.add(future);
			}
			for (Future<String> future : fList) {
				future.get();
			}
		}catch(Exception e){
			throw new Exception(e);
		}finally{
			es.shutdown();
		}
	}
	
	/**
	 * 更新推荐商品数据
	 */
	@Override
	@Transactional(rollbackFor=Exception.class)
	public ServiceCode updateRecommendInfo(List<ActivityCommodity> commodityList, String id) throws ServiceException {
		// 返回结果
		int result = 0;
		// 清空活动对应的推荐商品数据
		if (StringUtil.isNotNull(id)) {
			// 创建实体对象
			ActivityCommodity activityCommodity = new ActivityCommodity();
			
			ActivityManager activity = new ActivityManager();
			activity.setId(id);
			
			activityCommodity.setActivity(activity); 
			activityCommodity.setPicPath(""); // 推荐商品设置图片
			activityCommodity.setIsRecommend("0");
			activityCommodity.setRecommendSort(0);
			
			result = activityCommdityMapper.updateRecommendInfo(activityCommodity);
			
			// 遍历：更新推荐商品
			for (ActivityCommodity act : commodityList) {
				if (StringUtil.isNotNull(act.getId())) {
					// 修改
					result=this.activityCommdityMapper.updateByPrimaryKeySelective(act);
				}
				
			}
		}
		
		return result > 0 ? ServiceResultCode.SUCCESS : ServiceResultCode.FAIL;
	}

	@Override
	public Map<String, Object> validateActivityCommodity(ActivityCommodity activityCommodity, ResultVo resultVo,
			List<ActivityCommodity> activitycommodityList,ActivityTypeEnum enums,String viewStatus) throws Exception {
		Map<String, Object> map=null;
		//是否新增，编辑
			//ActivityCommodity ac=this.activityCommdityMapper.selectByPrimaryKey(activityCommodity.getId());			
				//编辑--开始或者结束时间已经修改				
					if(StringUtil.isNotNull(activitycommodityList)){
						for(ActivityCommodity co:activitycommodityList){
							//添加的商品是否已经参加了其他活动
							if(!co.getActivityType().equals(enums)){
								map=new HashMap<>();
								map.put("flag", false);
								map.put("msg", com.ffzx.promotion.api.dto.constant.Constant.ERROR_RE);
							}
							
							if (ActivityTypeEnum.PRE_SALE.equals(enums) || ActivityTypeEnum.PANIC_BUY.equals(enums)) {
								//判断该商品是否已经存在同一个批次
									if(co.getActivity().getId().equals(activityCommodity.getActivityId())){		
										if(viewStatus.equals("save") || viewStatus.equals("saveandedit")){
											map=new HashMap<>();
											map.put("flag", false);
											map.put("msg", com.ffzx.promotion.api.dto.constant.Constant.ERROR_PC);
										}										
									}						
								//判断当前设置的商品开始结束时间在不同批次是否重合
								if(co.getActivityType().equals(enums) && activityCommodity.getCommodityId().equals(co.getCommodityId()) ){		
									if(!co.getId().equals(activityCommodity.getId())){
										Date startDate=DateUtil.parseTime(activityCommodity.getStartDateStr());//当前设置商品开始时间
										Date endDate=DateUtil.parseTime(activityCommodity.getEndDateStr());//当前设置商品结束时间
										Date coStartDate=co.getStartDate();//已经存在的批次商品开始时间
										Date coEndDate=co.getEndDate();//已经存在的批次商品结束时间
										if(endDate.getTime()<coStartDate.getTime()){}
										else if(startDate.getTime()>coEndDate.getTime()){}
										else{
											map=new HashMap<>();
											map.put("flag", false);
											map.put("msg", com.ffzx.promotion.api.dto.constant.Constant.ERROR_TIME);
										}
									}
								}				
							}
						}
					}
		return map;
	}

	@Override
	@Transactional(rollbackFor=Exception.class)
	public ServiceCode updateCommodityDate(Map<String, Object> params) throws ServiceException {
		int result = this.activityCommdityMapper.updateCommodityDate(params);
		return result > 0 ? ServiceResultCode.SUCCESS : ServiceResultCode.FAIL;
	}
	@Override
	public List<ActivityEsVo> findActivityGoodsItemByMid(String activityManagerId,String activityCommodityId) {
		Map<String,Object> params = new HashMap<>();
		params.put("activityManagerId", activityManagerId);
		params.put("activityCommodityId", activityCommodityId);
		return activityCommdityMapper.findActivityGoodsItemByMid(params);
	}

	@Override
	public List<ActivityCommodity> findCommodityByDate(Map<String, Object> params) throws ServiceException {
		return this.activityCommdityMapper.selectCommodityByDate(params);
	}
}