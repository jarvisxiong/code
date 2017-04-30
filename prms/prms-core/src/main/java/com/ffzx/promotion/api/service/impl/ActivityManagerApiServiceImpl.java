package com.ffzx.promotion.api.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ffzx.bms.api.dto.StockUsed;
import com.ffzx.bms.api.dto.enums.CommodityStatusEnum;
import com.ffzx.bms.api.service.OrderStockManagerApiService;
import com.ffzx.commerce.framework.constant.Constant;
import com.ffzx.commerce.framework.dao.CrudMapper;
import com.ffzx.commerce.framework.dto.ResultDto;
import com.ffzx.commerce.framework.exception.ServiceException;
import com.ffzx.commerce.framework.page.Page;
import com.ffzx.commerce.framework.service.impl.BaseCrudServiceImpl;
import com.ffzx.commerce.framework.utils.DateUtil;
import com.ffzx.commerce.framework.utils.RedisUtil;
import com.ffzx.commerce.framework.utils.StringUtil;
import com.ffzx.order.api.service.OrderApiService;
import com.ffzx.promotion.api.dto.ActivityCommodity;
import com.ffzx.promotion.api.dto.ActivityCommoditySku;
import com.ffzx.promotion.api.dto.ActivityManager;
import com.ffzx.promotion.api.dto.Commodity;
import com.ffzx.promotion.api.dto.CouponAdminCategory;
import com.ffzx.promotion.api.dto.ShoppingCartVo;
import com.ffzx.promotion.api.dto.WholeSaleVo;
import com.ffzx.promotion.api.dto.constant.PrmsConstant;
import com.ffzx.promotion.api.enums.ActivityTypeEnum;
import com.ffzx.promotion.api.service.ActivityManagerApiService;
import com.ffzx.promotion.mapper.ActivityCommodityMapper;
import com.ffzx.promotion.mapper.ActivityManagerMapper;
import com.ffzx.promotion.mapper.CommoditySkuMapper;
import com.ffzx.promotion.mapper.CouponAdminCategoryMapper;
import com.ffzx.promotion.mapper.PanicBuyRemindMapper;
import com.ffzx.promotion.model.ActivitySaleApp;
import com.ffzx.promotion.model.ActivitySaleAppCategory;
import com.ffzx.promotion.model.ActivitySaleAppDate;
import com.ffzx.promotion.service.ActivityCommoditySkuService;
import com.ffzx.promotion.util.AppMapUtils;
import com.ffzx.promotion.util.RedisCountUtil;
import com.ffzx.promotion.util.StringprmsUtil;

/**
* @author yuzhao.xu
* @email  yuzhao.xu@ffzxnet.com
* @date 2016年5月11日 下午4:25:32
* @version V1.0 
*
*/
@Service("activityManagerApiService")
public class ActivityManagerApiServiceImpl extends BaseCrudServiceImpl implements ActivityManagerApiService {

    @Resource
    private ActivityManagerMapper activityManagerMapper;
    
    @Resource
    private CommoditySkuMapper commoditySkuMapper;
    @Resource
    private CouponAdminCategoryMapper couponAdminCategoryMapper;
    @Resource
    private ActivityCommodityMapper activityCommodityMapper;
    
    @Autowired
    private PanicBuyRemindMapper panicBuyRemindMapper;

	@Autowired
	private OrderApiService orderApiService;

	@Autowired
	private OrderStockManagerApiService orderStockManagerApiService;
    
	@Autowired
    private ActivityCommoditySkuService activityCommoditySkuService;

	@Autowired
	private RedisUtil redis;

    private final String promptJingxing="限时限量，特价疯抢";//提示进行中
    private final String promptweiJingxing="提前设置提醒，好货不错过";//提示未开始
    private final String promptjiesu="本场次已结束，去其他场次看看吧";//提示结束
    private final String rangeDateStringJingxing="距结束";
    private final String rangeDateStringweiJingxing="距开始";
    private final String activitySaleAppDateString="activitySaleAppDate";
    @Override
    public CrudMapper init() {
        return activityManagerMapper;
    }

	@Override
    public ResultDto getActivityList(int page,int pageSize,ActivityTypeEnum type, String actStatus){
    	ResultDto rsDto = null;
    	try{
	    	rsDto = new ResultDto(ResultDto.OK_CODE, "success");
	    	Map<String,Object> result = new HashMap<String,Object>();
	    	Page pageObject = new Page();
	    	pageObject.setPageSize(pageSize);
	    	pageObject.setPageIndex(page);
	    	Map<String,Object> params = new HashMap<String ,Object>();
	    	params.put("releaseStatus", Constant.YES); // 启用状态的活动
	    	params.put("activityType", type); // 活动类型
	    	params.put("actStatus", actStatus); // 活动状态
	    	List<ActivityManager> activityList = activityManagerMapper.selectDMCByPage(pageObject, params);
	    	if(activityList !=null && activityList.size()>=1){
	    		result.put("items",activityList);
	    		result.put("page", pageObject.getPageCount());
	    		result.put("pageSize",pageObject.getPageSize());
	    		result.put("recordCount", pageObject.getTotalCount());
	    	}
	    	rsDto.setData(result);
    	}catch(Exception e){
			logger.error("ActivityManagerApiServiceImpl-getActivityList-Exception=》机构dubbo调用-getActivityList", e);
//			rsDto = new ResultDto(ResultDto.ERROR_CODE, "faild:" + e);
			throw new ServiceException(e);
		}
    	return rsDto;
    }

	@Override
	public ResultDto refreshCartStatus(List<ShoppingCartVo> cartList,String cityId) {
		ResultDto rsDto = null;
		try{
			rsDto = new ResultDto(ResultDto.OK_CODE, "success");
			List<String> skuList=new ArrayList<String>();//sku集合(添加购物车时 没有参加活动的sku)
			List<String> activityCommodityList=new ArrayList<String>();
			for(ShoppingCartVo vo:cartList){
				if(!StringUtil.isEmpty(vo.getActivityCommodityId())){
					activityCommodityList.add(vo.getActivityCommodityId());
				}
				skuList.add(vo.getSkuId());
			}
			Map<String,Object> params=new HashMap<String,Object>();
			params.put("skuIds", skuList);
			List<Commodity> commodityInfo=commoditySkuMapper.getCommodityInfoBySku(params);
			List<ActivityManager> activityInfo=null;
			if(activityCommodityList.size()>0){//有活动相关购物车记录
				params.clear();
				params.put("activityCommodityList", activityCommodityList);
				activityInfo=activityManagerMapper.getActivityDetailList(params);
			}
			refreshCartDetailStatus(cartList, commodityInfo, activityInfo);
			rsDto.setData(getLastRes(cartList,cityId));
		}catch(Exception e){
			logger.error("ActivityManagerApiServiceImpl-refreshCartStatus-Exception=》机构dubbo调用-refreshCartStatus", e);
//			rsDto = new ResultDto(ResultDto.ERROR_CODE, "faild:" + e);
			throw new ServiceException(e);
		}
		return rsDto;
	}
	
	
	/**
	 * 设置库存数据
	 * @param cartList
	 * @param cityid
	 */
	@SuppressWarnings("unchecked")
	public void setCartInventoryData(List<ShoppingCartVo> cartList,String cityid){
		List<String> skuCodeList=new ArrayList<String>();
		for(ShoppingCartVo vo:cartList){
			for(ShoppingCartVo item:vo.getVoList()){
				if(item.getIsExistToPutong() == 1 && "1".equals(item.getStatus())){//有效的记录才查询库存
					if(!skuCodeList.contains(item.getSkuCode())){
						skuCodeList.add(item.getSkuCode());
					}
				}
			}
		}
		ResultDto rsDto=null;
		try {
			rsDto=this.orderStockManagerApiService.getInfobyskuCodeAndaddressId(skuCodeList, cityid);
			if(rsDto.getCode().equals(ResultDto.OK_CODE)){
				List<Map<String,StockUsed>> res=(List<Map<String, StockUsed>>) rsDto.getData();
				for(ShoppingCartVo cart:cartList){
					for(ShoppingCartVo item:cart.getVoList()){
						for(Map<String,StockUsed> one:res){
							for(String skuCode:one.keySet()){
								if(skuCode.equals(item.getSkuCode())){
									StockUsed stock=one.get(skuCode);
									item.setStorageNum(StringUtil.isEmpty(stock.getUserCanBuy())?0:Integer.parseInt(stock.getUserCanBuy()));
								}
							}
						}
					}
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			throw new ServiceException(e);
		}
	}
	
	/**
	 * 设置最后返回结果集
	 * @param cartList
	 * @param wholeSkuList
	 * @return
	 */
	public List<ShoppingCartVo> getLastRes(List<ShoppingCartVo> cartList, String cityId){
		List<ShoppingCartVo> res=new ArrayList<ShoppingCartVo>();
		/**
		 * 批发信息
		 */
		Map<String,List<ShoppingCartVo>> unique=new HashMap<String,List<ShoppingCartVo>>();
		for(ShoppingCartVo vo:cartList){
			/**
			 * 判断是否是批发数据
			 */
			if("WHOLESALE_MANAGER".equals(vo.getActivityType())){//批发类型
                if(!unique.containsKey(vo.getCommodityCode())){
                	/**
                	 * 新增一个 批发商品map
                	 */
                	List<ShoppingCartVo> list=new ArrayList<ShoppingCartVo>();
                	list.add(vo);
                	unique.put(vo.getCommodityCode(), list);
                }else{//累加
                	List<ShoppingCartVo> list=unique.get(vo.getCommodityCode());
                	list.add(vo);
                	unique.put(vo.getCommodityCode(), list);
                }
			}else{//非批发 直接添加
				ShoppingCartVo resVo=new ShoppingCartVo();
				resVo.setCommodityId(vo.getCommodityId());
				resVo.setBatchCount(vo.getBatchCount());
				resVo.setActivityType(vo.getActivityType());
				resVo.setAddDate(vo.getAddDate());
				List<ShoppingCartVo> itemList=new ArrayList<ShoppingCartVo>();
				itemList.add(vo);
				resVo.setVoList(itemList);
				res.add(resVo);
			}
		}
		for(String commodityCode:unique.keySet()){
			ShoppingCartVo resVo=new ShoppingCartVo();
			List<ShoppingCartVo> list=unique.get(commodityCode);//对应批发 相同商品不同sku list
			resVo.setCommodityId(list.get(0).getCommodityId());
			resVo.setBatchCount(list.get(0).getBatchCount());
			resVo.setActivityType(list.get(0).getActivityType());
			resVo.setAddDate(list.get(0).getAddDate());
			resVo.setVoList(list);
			res.add(resVo);
		}
		setCartInventoryData(res, cityId);
		//add by 柯典佑 ；需要按加入时间倒序排序
		if(null!=res){
			Collections.sort(res);
		}
		for(ShoppingCartVo vo : res)
		{
			if(null != vo && null != vo.getVoList())
			{
				for(ShoppingCartVo cart : vo.getVoList())
				{
					int num = 0;
					if (null != cart.getStorageNum())
					{
						num = cart.getStorageNum();
					}
					int status = 0;
					if (cart.getIsExistToPutong() == 0 || num <= 0)
					{
						status = 0;
					} else
					{
						status = null != cart.getStatus() ? Integer.parseInt(cart.getStatus()) : 0;// 是否失效
																									// 0失效,1有效
					}
					cart.setStatus(String.valueOf(status));
				}
			}
		}
		return res;
	}
	
	
	public List<ShoppingCartVo> getBatchInfo(List<ShoppingCartVo> wholeSale){
		List<ShoppingCartVo> wholeSkuList=new ArrayList<ShoppingCartVo>();
		if(null != wholeSale && wholeSale.size()>0){
			List<String> commodityCodes=new ArrayList<String>();//批发商品编码
			Map<String,Object> params=new HashMap<String,Object>();
			for(ShoppingCartVo cart:wholeSale){
				commodityCodes.add(cart.getCommodityCode());//商品编码
			}
			params.put("commodityCodes", commodityCodes);
		     /**
			 * 查询批发商品sku明细
			 */
			List<Commodity> wholdCommodityInfo=commoditySkuMapper.getCommodityInfoBySku(params);
			
			for(Commodity comm:wholdCommodityInfo){
				ShoppingCartVo cart=new ShoppingCartVo();
				setCommodityInfo(comm,cart,null,null);
				wholeSkuList.add(cart);
			}
		}
        return wholeSkuList;
 
	}
	
	/**
	 * 更新 购物车记录 是否失效状态
	 * @param cartList
	 * @param commodityInfo
	 * @param activityInfo
	 * @return wholeSale 返回 批发list集合
	 */
	public List<ShoppingCartVo> refreshCartDetailStatus(List<ShoppingCartVo> cartList, List<Commodity> commodityInfo,
			List<ActivityManager> activityInfo) {
		 List<ShoppingCartVo> wholeSale=new ArrayList<>();//批发类型购物车记录
         for(ShoppingCartVo cart:cartList){
    		 for(Commodity comm:commodityInfo){
    			 if(cart.getSkuId().equals(comm.getCommoditySku().getId())){
    				    setCommodityInfo(comm,cart,activityInfo,wholeSale);
    			 }
    		 }
         }
         /**
          * 设置批发信息
          */
         if(wholeSale != null && wholeSale.size()>0){
        	 setWholeSaleInfo(cartList, wholeSale);
         }
         return wholeSale;

	}
	
	/**
	 * 设置商品信息
	 * @param comm
	 * @param cart
	 * @param activityInfo
	 * @param wholeSale
	 */
	public void setCommodityInfo(Commodity comm, ShoppingCartVo cart, List<ActivityManager> activityInfo,
			List<ShoppingCartVo> wholeSale) {
		 cart.setCommodityId(comm.getId());//商品ID
		 cart.setBuyType(comm.getBuyType());//售卖类型
		 if("COMMODITY_STATUS_ADDED".equals(comm.getStatus())){//上架状态
			 cart.setIsExistToPutong(1);
		 }else{//其他为已下架
			 cart.setIsExistToPutong(0);
		 }
		 cart.setSkuCode(comm.getCommoditySku().getSkuCode());
		 cart.setSkuAttrs(comm.getCommoditySku().getCommodityAttributeValues());
		 cart.setCommodityCode(comm.getCode());//商品编号
		 cart.setPrice(comm.getCommoditySku().getPrice());//售价
		 cart.setCommoditySkuPic(comm.getCommoditySku().getSmallImage());//商品logo图
		 cart.setGiveType(Integer.valueOf(comm.getGiveType()));
		 if(StringUtil.isEmpty(cart.getActivityCommodityId())){//普通购物车记录(未参加活动)
			//如果 从db中读取出来该sku当前参加了活动 则该购物车记录无失效
			 if(!comm.getBuyType().equals("COMMON_BUY")){
				 cart.setStatus("0");
			 }else{
				 cart.setStatus("1");
			 }
			 cart.setFavourablePrice(comm.getCommoditySku().getFavourablePrice());//优惠价
			 cart.setCommodityTitle(comm.getTitle());//商品标题
		 }else{//加入购物车时 商品参加了活动
			 if(null != activityInfo){
				 for(ActivityManager act:activityInfo){
					 ////////批发 没有存 sku信息 只有commodity_code///////////////////////////////
					 boolean flag=false;
					 if(ActivityTypeEnum.WHOLESALE_MANAGER.equals(act.getActivityType())){//批发类型
						 //批发 用活动ID和 商品code判断
					     flag = cart.getActivityCommodityId().equals(act.getActivityCommodity().getId());
					 }else{//其他用 skuid和 活动ID判断
						 //如果此对象为NULL，则不能往下走，否则整个列表将无法加载，此对象为空的原因是 cims与prms的数据未及时同步
						 //add by ying.cai
						 if(null==act.getActivityCommoditySku()){
							 cart.setStatus("1");
							 continue;
						 }
						 flag=cart.getSkuId().equals(act.getActivityCommoditySku().getCommoditySkuId()) && 
								 cart.getActivityCommodityId().equals(act.getActivityCommodity().getId());
					 }
					 if(flag){//sku id & 活动商品设置id匹配
						 cart.setActivityCommodityId(act.getActivityCommodity().getId());//活动商品设置ID
						 cart.setActivityType(act.getActivityType().getValue());
						 if(ActivityTypeEnum.WHOLESALE_MANAGER.equals(act.getActivityType())){//批发类型
							 wholeSale.add(cart);
						 }
						 /**
						  * 判断购物车记录是否有效
						  */
						 cart.setStatus(returnStatusByActivityType(act.getActivityType(), comm.getBuyType(),
								act.getStartDate(), act.getEndDate(), act.getActivityCommodity().getStartDate(), act.getActivityCommodity().getEndDate()));
						 /**
						  * 设置活动价格标题等
						  */
						 cart.setFavourablePrice(act.getActivityCommoditySku().getActivityPrice());//活动价格
						 //如果参加活动的商品被删除，取不到活动标题时，取商品系统标题
						 if(StringUtils.isNotBlank(act.getActivityCommodity().getActivityTitle())){
							 cart.setCommodityTitle(act.getActivityCommodity().getActivityTitle());//活动标题
						 }else{
							 cart.setCommodityTitle(comm.getTitle());//活动标题
						 }
						 
					 }
				 } 
			 }

		 }

	}
	
	/**
	 * 设置 批发信息
	 * @param cartList
	 * @param wholeSale
	 */
	public void setWholeSaleInfo(List<ShoppingCartVo> cartList,List<ShoppingCartVo> wholeSale){
		List<String> activityCommodityIds=new ArrayList<String>();//批发活动IDS
		List<String> commodityCodes=new ArrayList<String>();//批发商品编码
		Map<String,Object> params=new HashMap<String,Object>();
		for(ShoppingCartVo cart:wholeSale){
			activityCommodityIds.add(cart.getActivityCommodityId());
			commodityCodes.add(cart.getCommodityCode());//商品编码
		}
		params.put("activityCommodityIds", activityCommodityIds);
		params.put("commodityCodes", commodityCodes);
		params.put("wholeSaleSort", "YES");//批发明细排序
		/**
		 * 获取批发信息
		 */
		List<ActivityManager> wholeList=activityManagerMapper.getActivityDetailList(params);
		
		
		for(ShoppingCartVo vo:cartList){
			if("WHOLESALE_MANAGER".equals(vo.getActivityType())){//是批发活动
				List<WholeSaleVo> wholeItem=new ArrayList<WholeSaleVo>();
				int batchCount=0;//起批数量
				int index = 0 ;
				for(ActivityManager act:wholeList){
					if(vo.getActivityCommodityId().equals(act.getActivityCommodity().getId())
							&& vo.getCommodityCode().equals(act.getActivityCommoditySku().getCommodityNo())){
						WholeSaleVo wvo=new WholeSaleVo();
						wvo.setStartNumber(act.getActivityCommoditySku().getSelectionStart());//开始区间数
						wvo.setEndNumber(act.getActivityCommoditySku().getSelectionEnd());//结束区间数
						wvo.setPrice(act.getActivityCommoditySku().getActivityPrice());//对应价格
						wholeItem.add(wvo);
						if(index == 0){ /**源代码是batchCount == 0  ，但这会存在bug，如果批发起批就是0的话，问题就出来了！ add by ying.cai */
							batchCount=wvo.getStartNumber();
						}else{
							if(batchCount > wvo.getStartNumber()){
								batchCount=wvo.getStartNumber();
							}
						}
						++index;
					}
				}
				vo.setBatchCount(batchCount);//起批数量
				vo.setWholeSaleList(wholeItem);//批发明细
			}
		}
	}
	
	/**
	 * 根据不同 活动类型 以及当前商品售卖类型 获取 该购物车记录是否失效
	 * @param activityType
	 * @param curType
	 * @param activityStart
	 * @param activityEnd
	 * @param itemStart
	 * @param itemEnd
	 * @return 0:失效1:有效
	 */
	public String returnStatusByActivityType(ActivityTypeEnum activityType, String curType, Date activityStart, Date activityEnd,
			Date itemStart, Date itemEnd) {
		Date now=new Date();//系统当前时间
		if(ActivityTypeEnum.ORDINARY_ACTIVITY.equals(activityType)){//普通活动 活动有开始结束时间
			if(activityStart.compareTo(now) <= 0 && activityEnd.compareTo(now) > 0){
				return "1";
			}
		}else if(ActivityTypeEnum.PRE_SALE.equals(activityType) || ActivityTypeEnum.PANIC_BUY.equals(activityType)){//活动商品 有 开始结束时间
			if(itemStart.compareTo(now) <= 0 && itemEnd.compareTo(now) > 0){
				return "1";
			}
		}else if(ActivityTypeEnum.NEWUSER_VIP.equals(activityType) || ActivityTypeEnum.WHOLESALE_MANAGER.equals(activityType)){//没有开始结束时间
			if(activityType.getValue().equals(curType)){//当前商品售卖类型 和 活动类型 一致
				return "1";
			}
		}
		return "0";
	}
	
	@Override
	public ResultDto selectActivityCategory(String couponId) {
		ResultDto resDto=null;
		try {
			resDto = new ResultDto(ResultDto.OK_CODE, "success");
			Map<String, Object> params=new HashMap<String, Object>();
			params.put("couponId", couponId);
			List<CouponAdminCategory> list=this.couponAdminCategoryMapper.selectByParams(params);
			resDto.setData(list);
		} catch (Exception e) {
			logger.error("ActivityCategoryApiServiceImpl-selectActivityCategory-Exception=》获取优惠券对应的商品或者类目列表", e);
//			resDto = new ResultDto(ResultDto.ERROR_CODE, "faild:" + e);
			throw new ServiceException(e);
		}
		return resDto;
	}

	@Override
	@Transactional(rollbackFor=Exception.class)
	public ResultDto updateActivityCommodityStatus(String activityCommodityId) {
		ResultDto resDto=null;
		try {
			int result=this.activityCommodityMapper.updateActivityCommodityStatus(activityCommodityId);
			if(result>0){
				resDto = new ResultDto(ResultDto.OK_CODE, "success");
			}
		} catch (Exception e) {
			logger.error("ActivityCategoryApiServiceImpl-updateActivityCommodityStatus-Exception=》修改商品是否已抢完", e);
//			resDto = new ResultDto(ResultDto.ERROR_CODE, "faild:" + e);
			throw new ServiceException(e);
		}
		return resDto;
	}

	@Override
	public ResultDto getWhosaleCommodityPrice(String commodityNo, int whosaleCount) {
		ResultDto resDto=null;
		try {
			Map<String, Object> params=new HashMap<String, Object>();
			params.put("commodityNo", commodityNo);
			List<ActivityCommoditySku> activityCommoditySkuList=this.activityCommoditySkuService.findActivityCommoditySkuList(params);
			if(StringUtil.isNotNull(activityCommoditySkuList)){
				if(activityCommoditySkuList.size()==1){
					resDto = new ResultDto(ResultDto.OK_CODE, "success");
					resDto.setData(activityCommoditySkuList.get(0).getActivityPrice());
					return resDto;
				}else if(activityCommoditySkuList.size()>1){
					resDto = new ResultDto(ResultDto.OK_CODE, "success");
					for(ActivityCommoditySku sku:activityCommoditySkuList){
						int selectionStart=sku.getSelectionStart();//批发数量开始区间
						int selectionEnd=0;//批发数量结束区间
						if(sku.getSelectionEnd()!=null){
							selectionEnd=sku.getSelectionEnd();
						}
						BigDecimal activityPrice=sku.getActivityPrice();
						if(selectionEnd!=0){
							if(whosaleCount>=selectionStart && whosaleCount<=selectionEnd){
								resDto.setData(activityPrice);
								return resDto;
							}
						}else{
							if(whosaleCount>=selectionStart){
								resDto.setData(activityPrice);
								return resDto;
							 }
						}						
					}
				}				
			}
		} catch (Exception e) {
			logger.error("ActivityCategoryApiServiceImpl-getWhosaleCommodityPrice-Exception=》修改商品是否已抢完", e);
//			resDto = new ResultDto(ResultDto.ERROR_CODE, "faild:" + e);
			throw new ServiceException(e);
		}
		return resDto;
	}

	@Override
	public ResultDto getActivityCommodity(String activityCommodityId) {
		ResultDto resDto=null;
		try {
			ActivityCommodity activityCommodity=this.activityCommodityMapper.selectByPrimaryKey(activityCommodityId);
			resDto = new ResultDto(ResultDto.OK_CODE, "success");
			resDto.setData(activityCommodity);
		} catch (Exception e) {
			logger.error("ActivityCategoryApiServiceImpl-getActivityCommodity-Exception=", e);
//			resDto = new ResultDto(ResultDto.ERROR_CODE, "faild:" + e);
			throw new ServiceException(e);
		}
		return resDto;
	}
	
	@Override
	public ResultDto selectActCommodityList(List<String> activityManagerIds) {
		ResultDto resDto=null;
		try {
			Map<String, Object> params= new HashMap<String, Object>();
			params.put("activityIds", activityManagerIds);
			List<ActivityCommodity> activityCommodityList = this.activityCommodityMapper.selectByParams(params);
			resDto = new ResultDto(ResultDto.OK_CODE, "success");
			resDto.setData(activityCommodityList);
		} catch (Exception e) {
			logger.error("ActivityCategoryApiServiceImpl-selectActCommodityList-Exception=", e);
			throw new ServiceException(e);
		}
		return resDto;
	}

	@Override
	public ResultDto getActivityCommoditySkuList(String activityCommodityId,String skuId) {
		ResultDto resDto=null;
		try {
			ActivityCommodity com=new ActivityCommodity();
			com.setId(activityCommodityId);
			Map<String, Object> params= new HashMap<String, Object>();
			params.put("activityCommodity", com);
			params.put("commoditySkuId", skuId);
			List<ActivityCommoditySku> activityCommoditySkuList=this.activityCommoditySkuService.findActivityCommoditySkuList(params);
			resDto = new ResultDto(ResultDto.OK_CODE, "success");
			resDto.setData(activityCommoditySkuList);
		} catch (Exception e) {
			logger.error("ActivityCategoryApiServiceImpl-getActivityCommodity-Exception=", e);
//			resDto = new ResultDto(ResultDto.ERROR_CODE, "faild:" + e);
			throw new ServiceException(e);
		}
		return resDto;
	}

	@Override
	@Transactional(rollbackFor=Exception.class)
	public ResultDto updateActivityCommodityStatusIng(String activityCommodityId) {
		ResultDto resDto=null;
		try {
			int result=this.activityCommodityMapper.updateActivityCommodityStatusIng(activityCommodityId);
			if(result>0){
				resDto = new ResultDto(ResultDto.OK_CODE, "success");
			}
		} catch (Exception e) {
			logger.error("ActivityCategoryApiServiceImpl-updateActivityCommodityStatus-Exception=》修改商品是否已抢完", e);
//			resDto = new ResultDto(ResultDto.ERROR_CODE, "faild:" + e);
			throw new ServiceException(e);
		}
		return resDto;
	}
	private final String orderFiled="orderFiled";
	private final String sortdateOrder="sortdateOrder";
	private final String releaseStatus="releaseStatus";
	private final String appRecommend="appRecommend";
	@Override
	public ResultDto findAppSaleCategory() {
		logger.info("返回抢购类目时间数据");
		// TODO Auto-generated method stub
		ResultDto resDto=null;
		try {
			resDto = new ResultDto(ResultDto.OK_CODE, "success");
			Map<String, Object> param=new HashMap<String,Object>();
			param.put(orderFiled, PrmsConstant.ASC);
			param.put(sortdateOrder, Constant.ORDER_BY);
			param.put(releaseStatus, Constant.YES);//是否已发布
			param.put(appRecommend, Constant.YES);//是否已推荐
			param.put("activityType", ActivityTypeEnum.PANIC_BUY);
			List<ActivityManager> activityManagers=activityManagerMapper.findAppSaleCategory(param);
			if(activityManagers==null ||  activityManagers.size()==0){
				return resDto;
			}
			//app专用对象
			List<ActivitySaleAppCategory> activitySaleAppCategories=new ArrayList<ActivitySaleAppCategory>();
			getactivitySaleAppCategoriesList(
					activityManagers,activitySaleAppCategories);//把数据转成正规app使用格式
			List<Object> listResult=null;
			if(activitySaleAppCategories!=null && activitySaleAppCategories.size()>0){
				listResult=AppMapUtils.objectsToMap(activitySaleAppCategories);
				logger.info("有数据"+activitySaleAppCategories.size()+"listResult"+listResult);
			}else{
				logger.info("无数据");
			}
			resDto.setData(listResult);
		} catch (Exception e) {
			logger.error("", e);
			throw new ServiceException(e);
		}
		return resDto;
	}
	/**已结束——进行中——即将开始
	 * ②其中已结束的：再次按照结束时间越早，排序越靠前
	 * ③其中进行中的：再次按照开始时间越早，排序越靠前
	 * 
	 * ④其中即将开始的：再次按照开始时间越早，排序越靠前
	 * 
	 * 进行中——即将开始---已结束——
	 * return String 就是选的activitiId的值
	 * ◆当前有处于抢购中的抢购活动时，最中间的页签默认为最近开始的抢购中的活动（状态为：抢购中，开始时间最晚）；
	 * 当前没有抢购中或即将开始的抢购活动，则最中间的页签默认为最近已结束的抢购活动（状态为：已结束，结束时间最晚）
	 * 
	 * 当前没有抢购中的抢购活动，但有处于即将开始的抢购活动，则最中间的页签默认为最近即将开始的抢购活动（状态为：即将开始，开始时间最早）；
	 */
	private String getCheck(List<ActivityManager> activityManagers){
		String activitiId=PrmsConstant.NullString;
		int num=activityManagers.size()-1;
		for(int i=num;i>=0;i--){
			ActivityManager activityManager=activityManagers.get(i);
			if(!StringUtil.isEmpty(activityManager.getSortdate())){//是否为null
				if(activityManager.getSortdate().equals(PrmsConstant.sortdateJingxing)){//进行中的
					activitiId=activityManager.getId();
					return activitiId;
				}
			}else{
				throw new RuntimeException("activityId="+activityManager.getId()+""+activityManager.getSortdate()+"为null");
			}
		}
		for(int i=0;i<activityManagers.size();i++){//即将开始
			ActivityManager activityManager=activityManagers.get(i);
			if(!StringUtil.isEmpty(activityManager.getSortdate())){//是否为null
				if(activityManager.getSortdate().equals(PrmsConstant.sortdateweiJingxing)){//未进行的
					return activityManager.getId();
				}
			}else{
				throw new RuntimeException("activityId="+activityManager.getId()+""+activityManager.getSortdate()+"为null");
			}
		}
		for(int i=num;i>=0;i--){//结束的
			ActivityManager activityManager=activityManagers.get(i);
			if(!StringUtil.isEmpty(activityManager.getSortdate())){//是否为null
				if(activityManager.getSortdate().equals(PrmsConstant.sortdatejiesu)){//结束的
					return activityManager.getId();
				}
			}else{
				throw new RuntimeException("activityId="+activityManager.getId()+""+activityManager.getSortdate()+"为null");
			}
		}
		return activitiId;
	}
	
	
	/**
	 * 把activityManagers转app专用activitySaleAppCategories
	 * @param activityManagers
	 * @param activitySaleAppCategories
	 */
	private void  getactivitySaleAppCategoriesList(List<ActivityManager> activityManagers
			,List<ActivitySaleAppCategory> activitySaleAppCategories){
		String activitiId=getCheck(activityManagers);//获取选中的数据
		for (ActivityManager activityManager : activityManagers) {
			String startDateString=DateUtil.format(activityManager.getStartDate(), DateUtil.FORMAT_DATETIME);//开始时间
			String minuteSecond=DateUtil.format(activityManager.getStartDate(), PrmsConstant.MinSecond);//时分
			
			String states=PrmsConstant.NullString;//结束
			
			String isCheck=Constant.NO;
			if(!StringUtil.isEmpty(activityManager.getSortdate())){//是否为null
				if(activityManager.getSortdate().equals(PrmsConstant.sortdateJingxing)){
					states=PrmsConstant.sortdateJingxingString;//进行中
				}else if(activityManager.getSortdate().equals(PrmsConstant.sortdateweiJingxing)){
					states=PrmsConstant.sortdateweiJingxingString;//未开始
				}else{
					states=PrmsConstant.sortdatejiesuString;//结束
				}
				if(!StringUtil.isEmpty(activitiId)){
					if(activityManager.getId().equals(activitiId)){
							isCheck=Constant.YES;
						}
				}else{
					throw new RuntimeException("getCheck返回id为null，请检查");
				}
			}else{
				throw new RuntimeException("activityId="+activityManager.getId()+""+activityManager.getSortdate()+"为null");
			}
			
			ActivitySaleAppCategory activitySaleAppCategory=new ActivitySaleAppCategory
					(startDateString, states, isCheck, activityManager.getId());
			activitySaleAppCategory.setMinuteSecond(minuteSecond);
			activitySaleAppCategories.add(activitySaleAppCategory);
		}
	}

	/**
	 * 根据参数查询出list
	 * @param startDate
	 * @param activityId
	 * @param uid
	 * @param cityId
	 * @param page
	 * @param pageSize
	 * @return
	 */
	private List<ActivityCommodity> getActivityCommodityList(String startDate, String activityId, String uid, String cityId
			,int page,int pageSize,Page pageObject){
	     pageObject.setPageSize(pageSize);
	     pageObject.setPageIndex(page);
		Map<String, Object> map=new HashMap<String, Object>();
		 map.put("releaseStatus", Constant.YES);
		 map.put("activityType", ActivityTypeEnum.PANIC_BUY);
		 map.put("uid", uid);
		 map.put("status", CommodityStatusEnum.COMMODITY_STATUS_ADDED);
		 map.put("activityId", activityId);
	     return activityCommodityMapper.selectSaleList(pageObject, map);
		
	}
	

	/**
	 * 一种商品
	 * @param goodsItem
	 * @param imagePath
	 */
	private void getImgPath(ActivitySaleApp itemMain, String activityImage,String goodsImage){
		String img=activityImage;
		if(StringUtil.isEmpty(activityImage)){
			img=goodsImage;
		}
		itemMain.setActivityImgPath(StringUtil.isEmpty(img)?"":System.getProperty("image.web.server") + img);//商品LOGO图片路径，
		
	}
	/**
	 * 最低价格  根据区间价格拆分
	 */
	private void getdiscountPrice(ActivitySaleApp itemMain, String showPrice){
		if(!StringUtil.isEmpty(showPrice)){
			if(showPrice.indexOf("-")>=0){
				String[] maxmain=showPrice.split("-");
				itemMain.setDiscountPrice(maxmain[0].trim());//最低价格
			}else
				itemMain.setDiscountPrice(showPrice.trim());//最低价格
		}
	}
	/**
	 * 更改抢购总限购和id限购增量值
	 * @param activityCommodity
	 * @param itemMain
	 */
	private void getLimtSaleCount(ActivityCommodity activityCommodity,
			ActivitySaleApp itemMain){
		if(activityCommodity.getStartDate()!=null && activityCommodity.getEndDate()!=null){
			//如果即将开始大于当前时间，就销售了,加特殊增value,非即将开始加增量
			if(DateUtil.diffDateTime(activityCommodity.getStartDate(), new Date())<=0){
				getLimitcalculation(itemMain,activityCommodity.getLimitCount(),activityCommodity.getSaleIncrease(),getBuyCount(activityCommodity));	

			}else{
				getLimitcalculation(itemMain,activityCommodity.getLimitCount(),null,getBuyCount(activityCommodity));	
			}
		}
		
	}
	/**
	 * 获取用户购买数量+增量值+即将开始不加
	 * @param startDate  开始时间
	 * @param endDate  结束时间
	 * @param commodityNo  activityCommodity.getCommodity().getCode()  
	 * @param activityCommodityItemId   activityCommodity.getId()
	 * @param saleIncrease增量value
	 */
	@Override
	public String getPresaleCount(Date startDate,Date endDate,String commodityNo,
			String activityCommodityItemId,Integer saleIncrease){
		try{
			logger.info("获取用户数量参数startDate "+startDate+"end "+endDate +"commodityNo"+commodityNo+"activityCommodityItemId"+
				"saleIncrease"+saleIncrease);
			ActivitySaleApp itemMain=new ActivitySaleApp();
			if(startDate!=null && endDate!=null){
				//查询数量参数
				ActivityCommodity activityCommodity=new ActivityCommodity();
				 Commodity commodity=new Commodity();
				 commodity.setCode(commodityNo);
				activityCommodity.setCommodity(commodity);
				activityCommodity.setId(activityCommodityItemId);
				//如果即将开始大于当前时间，就销售了,加特殊增value,非即将开始加增量
				if(DateUtil.diffDateTime(startDate, new Date())<=0){
					getLimitcalculation(itemMain,null,saleIncrease,getBuyCount(activityCommodity));	
	
				}else{
					getLimitcalculation(itemMain,null,null,getBuyCount(activityCommodity));	
				}
			}
			logger.info("itemMain.getPresaleCount()"+itemMain.getPresaleCount());
			return StringReturnInt(itemMain.getPresaleCount());
		}catch(Exception e){
			logger.error("",e);
			throw new RuntimeException(e);
		}
	}

	private void getPriceAmongValue(ActivityCommodity activityCommodity,
			ActivitySaleApp itemMain){
		if(!StringUtil.isEmpty(activityCommodity.getCommodity().getSaleAttributeIds())){//是辅助属性
			itemMain.setPriceAmong( activityCommodity.getCommodity().getPreferentialPrice());
		}else{
			itemMain.setPriceAmong(PrmsConstant.NullString);
		}
	}
	private void getIsPANICBUY(ActivityCommodity activityCommodity,
			ActivitySaleApp itemMain){
		if(DateUtil.getMillis(activityCommodity.getStartDate())<DateUtil.getMillis(new Date())){//开始1月，当前7月，已开始
			itemMain.setIsPANICBUY(Constant.YES);//是否已开抢（抢购专用）
			if(DateUtil.getMillis(activityCommodity.getEndDate())<DateUtil.getMillis(new Date())){
				itemMain.setIsPANICBUY(PrmsConstant.TWO);//结束（抢购专用）
			}
		}
		else{//未开始
			itemMain.setIsPANICBUY(Constant.NO);//是否已开抢（抢购专用）
		}
	}
	private void getactivitySaleAppDate(ActivityCommodity activityCommodity,
			ActivitySaleAppDate activitySaleAppDate){
		
		String startDateMillis = Long.toString(DateUtil.getMillis(activityCommodity.getStartDate()));
		String endDateMillis = Long.toString(DateUtil.getMillis(activityCommodity.getEndDate()));
		String prompt=PrmsConstant.NullString;//提示
		String rangeDate=Constant.NO;//距离时间，默认0
		String rangeDateString=PrmsConstant.NullString;//距离时间string
		
		String status=PrmsConstant.NullString;
		if (Long.valueOf(DateUtil.getTime()) > activityCommodity.getEndDate().getTime()) {
			status=PrmsConstant.sortdatejiesu;
			 // 已结束
			prompt=promptjiesu;
		}  else if (Long.valueOf(DateUtil.getTime()) >= activityCommodity.getStartDate().getTime() && 
				Long.valueOf(DateUtil.getTime()) <= activityCommodity.getEndDate().getTime()) {
			status=PrmsConstant.sortdateJingxing;
			prompt=promptJingxing;
			rangeDate=(DateUtil.diffDateTime(activityCommodity.getEndDate(), new Date())).toString();//距离结束
			 // 进行中
			rangeDateString=rangeDateStringJingxing;
		} else if (Long.valueOf(DateUtil.getTime()) < activityCommodity.getStartDate().getTime()) {
			status=PrmsConstant.sortdateweiJingxing;
			 // 即将开始
			prompt=promptweiJingxing;
			rangeDate=(DateUtil.diffDateTime(activityCommodity.getStartDate(), new Date())).toString();//距离开始
			rangeDateString=rangeDateStringweiJingxing;
		} 

		activitySaleAppDate.setStatus(status);
		activitySaleAppDate.setStartDateMillis(startDateMillis);
		activitySaleAppDate.setEndDateMillis(endDateMillis);
		activitySaleAppDate.setPrompt(prompt);
		activitySaleAppDate.setRangeDate(rangeDate);
		activitySaleAppDate.setRangeDateString(rangeDateString);
		
	}
	/**
	 * 根据list查询出移动端需要的list
	 * @param commodityList
	 * @param activitySaleApps
	 */
	private void getactivitySaleApps(List<ActivityCommodity> commodityList,
			List<ActivitySaleApp> activitySaleApps,String cityId,
			ActivitySaleAppDate activitySaleAppDate){
		int i=0;
		for (ActivityCommodity activityCommodity : commodityList) {
			if(i==0){//外围数据刷一次
				getactivitySaleAppDate(activityCommodity,activitySaleAppDate);
				i++;
			}
			ActivitySaleApp itemMain=new ActivitySaleApp();
			itemMain.setCategoryId(activityCommodity.getCommodity().getCategoryId());//类目id
			itemMain.setGoodsId(StringprmsUtil.getStr(activityCommodity.getCommodity().getId())); // 商品id
			itemMain.setActivityId(StringprmsUtil.getStr(activityCommodity.getId())); // 活动id（中间表Id）
			getImgPath(itemMain,activityCommodity.getPicPath(), activityCommodity.getCommodity().getThumbnail());
			itemMain.setPrice(activityCommodity.getCommodity().getRetailPrice().toString());//原价=零售价
			getdiscountPrice(itemMain, activityCommodity.getShowPrice());//最低价格

			itemMain.setIsRemind(StringReturnInt(activityCommodity.getIsRemind()));
			itemMain.setRemindCount(timeNullInt(activityCommodity.getFollowCount()));//已提醒/关注人数
			itemMain.setUnit(activityCommodity.getCommodity().getUnitName());
			itemMain.setActivityTime(DateUtil.format(activityCommodity.getStartDate(),PrmsConstant.MINUTE_DATA_FORMAT));//活动时间==冬冬，不明确
			itemMain.setActivityEndTime(DateUtil.format(activityCommodity.getEndDate(),PrmsConstant.MINUTE_DATA_FORMAT));//活动结束时间==冬冬，不明确
			itemMain.setIdLimit( timeNullInt(activityCommodity.getIdLimitCount()));
			getLimtSaleCount(activityCommodity, itemMain);//总限购量，用户数量 增量+saleIncrease销售增量
			itemMain.setSaleIncrease(timeNullInt(activityCommodity.getSaleIncrease()));
			itemMain.setCommodityNo(activityCommodity.getCommodity().getCode());
			itemMain.setType(ActivityTypeEnum.PANIC_BUY.getValue());
			itemMain.setExistExtra(StringUtil.isEmpty(activityCommodity.getCommodity().getSaleAttributeIds())?"0":"1");//是否存在辅助属性
			//如果existExtra为1,那么priceAmong肯定存在(前提,不能在一个商品已经处于预售抢购活动中的时候,还去设置这个商品的辅助属性!!!)
			getPriceAmongValue(activityCommodity, itemMain);
			int storageNum=getStorageNum(activityCommodity.getCommodity().getCode(), cityId);//库存可购买数量
			//缓存
			itemMain.setIsAllowFlag(storageNum>=0?Constant.YES:Constant.NO);//根据cityId判断当前地区是否允许购买
			itemMain.setActivityTitle( activityCommodity.getActivityTitle());
			getIsPANICBUY(activityCommodity, itemMain);//是否开抢
			
			activitySaleApps.add(itemMain);
		}
		
		
	}

	private Integer getStorageNum(String commodityCode,String addressId){
		try{

			Map<String, Object> params =new HashMap<String, Object>();
			params.put("commodityCode", commodityCode);
			ResultDto result =orderStockManagerApiService.getStockInfoByAddress(commodityCode, addressId);
			List<com.ffzx.bms.api.dto.CommoditySku> skus=(List<com.ffzx.bms.api.dto.CommoditySku>) result.getData();
			Integer storageNum=0;
			for (com.ffzx.bms.api.dto.CommoditySku commoditySku : skus) {
				String stockNum=commoditySku.getStockUsed().getCurrStockNum();
				if(!StringUtil.isEmpty(stockNum)){
					storageNum+=Integer.parseInt(stockNum);
				}
			}
			return storageNum;
		}catch(Exception e){
			logger.error("",e);
			return 0;
		}
	}

	/**
	 * 总限购量，+saleIncrease销售增量
	 * @param goodsItem
	 * @param limit  总共卖出多少件
	 * @param saleIncrease
	 * @param buycount  改商品卖出多少件
	 */
	private void getLimitcalculation(ActivitySaleApp itemMain,Integer limit,Integer saleIncrease,Integer buycount){
		if(limit==null){
			
		}else if(saleIncrease==null || saleIncrease==0){
			itemMain.setLimit(timeNullInt(limit));//限制信息如：限200件/限50个/限30只
		}else{
			itemMain.setLimit(timeNullInt(limit+saleIncrease));//限制信息如：限200件/限50个/限30只
		}
		if(buycount==null){
			
		}else if(saleIncrease==null || saleIncrease==0){
			itemMain.setPresaleCount(timeNullInt(buycount));//抢购预售卖出多少件==小龙，不明确
		}else{
			itemMain.setPresaleCount(timeNullInt(buycount+saleIncrease));//抢购预售卖出多少件==小龙，不明确
		}
	}

	/**
	 * 获取活动商品购买数量
	 *@param 	activityCommodityItemId 活动商品ID
	 *@param	memberId 用户id
	 *@param	commodityNo 活动商品编码
	 * @param activityCommodity
	 * @return
	 */
	private int getBuyCount(ActivityCommodity activityCommodity){

		int oldBuy=0;
			//该活动商品已经购买的数量
			Map<String, Object> params=new HashMap<String, Object>();
			params.put("commodityNo", activityCommodity.getCommodity().getCode());
			params.put("activityCommodityItemId", activityCommodity.getId());
			Object oldBuyNum=RedisCountUtil.getRedisCount(params,redis);//   redisUtil.get(RedisPrefix.APP_PAY_BUYNUM+activityCommodity.getId()+"_"+activityCommodity.getCommodity().getCode()+"_buyNum");
			if(oldBuyNum!=null)
				oldBuy=Integer.parseInt(oldBuyNum.toString());
			else{
				Map<String, Object> map=new HashMap<String, Object>();
	/*			map.put("commodityNo", activityCommodity.getCommodity().getCode());
				map.put("activityCommodityItemId", activityCommodity.getId());*/
				oldBuy=RedisCountUtil.getActivityCommodityBuyNum(params,redis,orderApiService);
			}
			
		return oldBuy;
	}
	
	public List<String> getIsRemindList(String activityMangerId,String memberId){
		if(StringUtil.isEmpty(memberId)){
			return null;
		}
		try{
			Map<String, Object> params=new HashMap<String,Object>();
			params.put("activityManageId", activityMangerId);
			params.put("memberId", memberId);
			List<String> list =panicBuyRemindMapper.getActivityList(params);
			return list;
		}catch(Exception e){
			logger.error("",e);
			throw new ServiceException(e);
		}
	}
	
	
	@Override
	public ResultDto findSaleList(String startDate, String activityId, String uid, String cityId
			,int page,int pageSize) {
		ResultDto resDto=null;
		try {
			logger.info("startfindSaleList");
			resDto = new ResultDto(ResultDto.OK_CODE, "success");
			//根据参数查询出list

			 Page pageObject = new Page();
			List<ActivityCommodity> commodityList =
					getActivityCommodityList(startDate, activityId, uid, cityId, page, pageSize,pageObject);
			if(commodityList==null || commodityList.size()==0){
				return  resDto;
			}
			logger.info("根据参数查询出commodityListlist"+commodityList.size());
			// TODO Auto-generated method stub
			 List<ActivitySaleApp> activitySaleApps=new ArrayList<ActivitySaleApp>();
			 ActivitySaleAppDate activitySaleAppDate=new ActivitySaleAppDate();//抢购列表外围数据
			 getactivitySaleApps(commodityList, activitySaleApps,cityId,activitySaleAppDate);
				logger.info("根据参数查询出activitySaleAppslist"+activitySaleApps.size());
			 List<Object> listResult=null;
			if(activitySaleApps!=null && activitySaleApps.size()>0){
				listResult=AppMapUtils.objectsToMap(activitySaleApps);
			}
			Map<String, Object> map=new HashMap<String, Object>();
			map.put(PrmsConstant.ITEMS, listResult);
			map.put(activitySaleAppDateString, AppMapUtils.toMap(activitySaleAppDate));
			map.put(PrmsConstant.RECORDCOUNT, pageObject.getTotalCount());
			logger.info(map.toString());//打印出返回的数据
			 resDto.setData(map);
		} catch (Exception e) {
			logger.error("", e);
			throw new ServiceException(e);
		}
		return resDto;
	}
	
	@Override
	public ResultDto getActivityNameList(List<String> ids){
		ResultDto rsDto = null;
		List<String> name=null;
		//根据参数查询出活动名称list
		try{
			if(ids != null && ids.size() > 0){
		      name = activityManagerMapper.findActivityNameList(ids);
			}
			rsDto = new ResultDto(ResultDto.OK_CODE, "success");
			rsDto.setData(name);
		}catch(Exception e){
			logger.error("ActivityManagerApiServiceImpl-getActivityNameList-Exception=》dubbo调用-getActivityNameList", e);
			throw new ServiceException(e);
		}
		return rsDto;
	}


	/**
	 * 剔除null，返回0
	 * @return
	 */
	private String StringReturnInt(String name){
		return StringprmsUtil.StringReturnInt(name);
	}

	/**
	 * 剔除null，返回空字符串,小于0就返回0
	 * @return
	 */
	private String timeNullInt(Integer num){
		return StringprmsUtil.timeNullInt(num);
	}

	
}