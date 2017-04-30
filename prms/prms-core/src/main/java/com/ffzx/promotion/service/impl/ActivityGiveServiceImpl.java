package com.ffzx.promotion.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
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
import com.ffzx.commerce.framework.dao.CrudMapper;
import com.ffzx.commerce.framework.dto.ResultDto;
import com.ffzx.commerce.framework.exception.ServiceException;
import com.ffzx.commerce.framework.page.Page;
import com.ffzx.commerce.framework.service.impl.BaseCrudServiceImpl;
import com.ffzx.commerce.framework.system.entity.SysUser;
import com.ffzx.commerce.framework.utils.DateUtil;
import com.ffzx.commerce.framework.utils.RedisUtil;
import com.ffzx.commerce.framework.utils.RedisWebUtils;
import com.ffzx.commerce.framework.utils.StringUtil;
import com.ffzx.commerce.framework.utils.UUIDGenerator;
import com.ffzx.commodity.api.dto.Commodity;
import com.ffzx.commodity.api.dto.CommoditySku;
import com.ffzx.commodity.api.service.CommodityApiService;
import com.ffzx.commodity.api.service.CommoditySkuApiService;
import com.ffzx.order.api.enums.CommodityStatusEnum;
import com.ffzx.promotion.api.dto.ActivityGive;
import com.ffzx.promotion.api.dto.CouponAdmin;
import com.ffzx.promotion.api.dto.GiftCommodity;
import com.ffzx.promotion.api.dto.GiftCommoditySku;
import com.ffzx.promotion.api.dto.GiftCoupon;
import com.ffzx.promotion.mapper.ActivityGiveMapper;
import com.ffzx.promotion.mapper.GiftCommodityMapper;
import com.ffzx.promotion.mapper.GiftCommoditySkuMapper;
import com.ffzx.promotion.mapper.GiftCouponMapper;
import com.ffzx.promotion.model.MainCommodityGive;
import com.ffzx.promotion.model.SkuCommodityGive;
import com.ffzx.promotion.service.ActivityGiveService;
import com.ffzx.promotion.service.CouponAdminService;
import com.ffzx.promotion.service.GiftCommodityService;
import com.ffzx.promotion.service.GiftCommoditySkuService;
import com.ffzx.promotion.service.GiftCouponService;
import com.ffzx.promotion.vo.CouponExportVo;
import com.ffzx.promotion.vo.GiftExportVo;
import com.ffzx.promotion.vo.GiveExportVo;

/**
 * 
 * @author ffzx
 * @date 2016-09-12 11:25:09
 * @version 1.0.0
 * @copyright www.ffzxnet.com
 */
@Service("activityGiveService")
public class ActivityGiveServiceImpl extends BaseCrudServiceImpl implements ActivityGiveService {
	@Resource
	private RedisUtil redisUtil;
    @Autowired
    private CodeRuleApiService codeRuleApiService;
    @Resource
    private ActivityGiveMapper activityGiveMapper;
    @Autowired
    private GiftCommodityService giftCommodityService;
    @Autowired
    private GiftCommoditySkuService giftCommoditySkuService;
    @Autowired
    private GiftCommodityMapper giftCommodityMapper;
    @Autowired
    private GiftCommoditySkuMapper giftCommoditySkuMapper;
    @Autowired
    private GiftCouponService giftCouponService;
    @Autowired
    private GiftCouponMapper giftCouponMapper;
	@Autowired
	private CommodityApiService commodityApiService;
	@Autowired
	private CommoditySkuApiService commoditySkuApiService;


	private final String goodsnullExcetion="不可只对赠品某几个SKU限定数量";
	private final String fuyi="-1";
	
	@Autowired
	private CouponAdminService couponAdminService;

    @Override
    public CrudMapper init() {
        return activityGiveMapper;
    }

    @Override
	public Map<String, Object> getActivityGiveDate(String id) {
		// TODO Auto-generated method stub


		Map<String, Object>  mapdate=new HashMap<String, Object> ();
    	try{
		ActivityGive activityGive= activityGiveMapper.selectByPrimaryKey(id);
		MainCommodityGive mainCommodityGive=getMainCommodityGive(activityGive);
		List<GiftCoupon> giftCoupons=getgiftCoupons(activityGive);
		List<SkuCommodityGive> giftCommoditySkus=getgiftCommoditySkus(activityGive);
		mapdate.put("activityGive", activityGive);
		mapdate.put("couponList", giftCoupons);
		mapdate.put("skuList", giftCommoditySkus);
		mapdate.put("mainCommodityGive", mainCommodityGive);
    	}catch(RuntimeException e){
    		logger.error("",e);
    	}
		return mapdate;
	}
    private MainCommodityGive getMainCommodityGive(ActivityGive activityGive){
    	MainCommodityGive mainCommodityGive=new MainCommodityGive();
	    Map<String, Object> params = new HashMap<String, Object>();
		params.put(com.ffzx.promotion.api.dto.constant.Constant.ID, activityGive.getCommodityId());
		ResultDto resultDto=commodityApiService.findList(null, null, null, params);

		Map<String, Object> returndate=(Map<String, Object>) resultDto.getData();
		List<Commodity> list=(List<Commodity>) returndate.get("list");
		if(!getIsValue(list)){
			logger.error("无匹配的上架主商品商品"+params);
			throw new RuntimeException(errorIsMain);
		}
		Commodity commodity=list.get(0);
    	mainCommodityGive.setBarCode(commodity.getBarCode());
    	mainCommodityGive.setName(commodity.getName());
    	mainCommodityGive.setLimitCount(activityGive.getLimitCount()==null ?"":activityGive.getLimitCount().toString());
    	mainCommodityGive.setPreferentialPrice(commodity.getPreferentialPrice()==null ?"":commodity.getPreferentialPrice().toString());
    	
    	return mainCommodityGive;
    }
    private List<SkuCommodityGive> getgiftCommoditySkus(ActivityGive activityGive){

    	List<SkuCommodityGive>  skuCommodityGives=new ArrayList<SkuCommodityGive>();
	    Map<String, Object> params = new HashMap<String, Object>();
	    params.put("giveId", activityGive.getId());
	    List<GiftCommoditySku> giftCommoditySkus=giftCommoditySkuService.findByBiz(params);
	    StringBuilder commodityCommoditySku=new StringBuilder();
	    List<String> skuList=new ArrayList<String>();
	    for (GiftCommoditySku giftCommoditySku : giftCommoditySkus) {
	    	commodityCommoditySku.append(giftCommoditySku.getGiftCommoditySkuid()+",");
	    	skuList.add(giftCommoditySku.getGiftCommoditySkuid());
		}
	    if(!getIsValue(skuList)){
	    	return null;
	    }
	    activityGive.setCommodityCommoditySku(commodityCommoditySku.toString());
	    params.clear();
	    ResultDto resultDto=commoditySkuApiService.findSku(skuList, Constant.YES);
	    List<CommoditySku> list=(List<CommoditySku>) resultDto.getData();
	    if(getIsValue(list)){
	    	for (CommoditySku commoditySku : list) {
				for (GiftCommoditySku giftCommoditySku :  giftCommoditySkus) {
					if(giftCommoditySku.getGiftCommoditySkuid().equals(commoditySku.getId())){
						skuCommodityGives.add(addSkuCommodityGive(giftCommoditySku,commoditySku));
						break;
					}
					
				}
			}
	    }
	    
    	return skuCommodityGives;
    }
    private SkuCommodityGive addSkuCommodityGive(GiftCommoditySku giftCommoditySku,CommoditySku commoditySku){
    	SkuCommodityGive skuCommodityGive=new SkuCommodityGive();
    	skuCommodityGive.setBarCode(commoditySku.getCommodity().getBarCode());
    	skuCommodityGive.setName(commoditySku.getCommodity().getName());
    	skuCommodityGive.setCommodityAttributeValues(commoditySku.getCommodityAttributeValues());
    	skuCommodityGive.setSkubarcode(commoditySku.getBarcode());
    	skuCommodityGive.setSkuid(commoditySku.getId());
    	skuCommodityGive.setLimitCount(giftCommoditySku.getGiftLimtCount()==null?"":giftCommoditySku.getGiftLimtCount().toString());
    	skuCommodityGive.setPreferentialPrice(commoditySku.getFavourablePrice()==null?"":commoditySku.getFavourablePrice().toString());
    	skuCommodityGive.setActivityPrice(Constant.NO);
    	skuCommodityGive.setGiftCount(giftCommoditySku.getGiftCount()==null?"":giftCommoditySku.getGiftCount().toString());
    	return skuCommodityGive;
    }
    private List<GiftCoupon> getgiftCoupons(ActivityGive activityGive){
	    Map<String, Object> params = new HashMap<String, Object>();
	    params.put("giveId", activityGive.getId());
    	List<GiftCoupon> giftCoupons= giftCouponService.findByBiz(params);
    	StringBuilder couponAdminList=new StringBuilder();
    	if(getIsValue(giftCoupons)){
    		for (GiftCoupon giftCoupon : giftCoupons) {
    			couponAdminList.append(giftCoupon.getCouponId()+",");
			}
    	}
    	activityGive.setCouponAdminList(couponAdminList.toString());
    	return giftCoupons;
    }

	@Override
	public List<ActivityGive> findList(Page page, String orderByField, String orderBy, ActivityGive activityGive)
			throws ServiceException {
	    Map<String, Object> params = new HashMap<String, Object>();
	    params.put(Constant.DELTE_FLAG, Constant.DELTE_FLAG_NO);
		//筛选 - 发放编码-模糊查询
		if(StringUtil.isNotNull(activityGive.getGiveNo())){
			//params.put("giveNo", activityGive.getGiveNo());
			params.put("giveSeNo", activityGive.getGiveNo());
		}
		if(StringUtil.isNotNull(activityGive.getGiveTitle())){
			params.put("giveTitle", activityGive.getGiveTitle());
		}
		if(StringUtil.isNotNull(activityGive.getCommodityCode())){
			//params.put("commodityCode", activityGive.getCommodityCode());
			params.put("commoditySeCode", activityGive.getCommodityCode());
		}
		if(StringUtil.isNotNull(activityGive.getCommodityBarcode())){
			//params.put("commodityBarcode", activityGive.getCommodityBarcode());
			params.put("commoditySeBarcode", activityGive.getCommodityBarcode());
		}
		// TODO Auto-generated method stub
		return activityGiveMapper.selectByPage(page, orderByField, orderBy, params);
	}

	private Commodity yanzhengisGoods(ActivityGive activityGive){
    	Map<String, Object> params=new HashMap<String, Object>();

		params.put(STATUS, CommodityStatusEnum.COMMODITY_STATUS_ADDED);//上架
		params.put(com.ffzx.promotion.api.dto.constant.Constant.ID, activityGive.getCommodityId());
		ResultDto resultDto=commodityApiService.findList(null, null, null, params);
		Map<String, Object> returndate=(Map<String, Object>) resultDto.getData();
		List<Commodity> list=(List<Commodity>) returndate.get("list");
		if(!getIsValue(list)){
			logger.error("无匹配的上架主商品商品"+params);
			throw new RuntimeException(errorIsMain);
		}
		return list.get(0);
    }
	private final String STATUS="status";
	private final String GIVETYPE="giveType";
	private final String errorIsMain="无匹配的上架商品";
	
	private Commodity isDelteMainGoods(ActivityGive activityGive,ActivityGive sourceactivityGive){
		if(sourceactivityGive.getCommodityId().equals(activityGive.getCommodityId())){//保持原来
			return null;
		}else{//删除原来，更新原来为普通商品
			Commodity maincom=yanzhengisGoods(activityGive);
			if(maincom.getGiveType()==2){
				throw new RuntimeException("该商品为主商品");
			}
			return maincom;
		}
	}
	private void getMainPutongCommodity(Commodity commoditymain,ActivityGive activityGive
			,Commodity commodityputong,ActivityGive sourceactivityGive){
		getCommodity(commoditymain, activityGive, com.ffzx.promotion.api.dto.constant.Constant.main);
		getCommodity(commodityputong, sourceactivityGive, com.ffzx.promotion.api.dto.constant.Constant.putong);
	
	}
	private void giveupdate(Commodity commoditymain,Commodity commodityputong,Commodity isreplaceflag,
			ActivityGive activityGive,List<GiftCommodity> giftCommodities,List<GiftCommoditySku> giftCommoditySkus
			,List<GiftCoupon> giftCoupons){
		getGiftType(activityGive, giftCommodities, giftCoupons);//获取activityGive类型
		
		String giftId=activityGive.getId();
		GiftCoupon giftCoupon2=new GiftCoupon();
		giftCoupon2.setGiveId(giftId);
		giftCouponService.deleteById(giftCoupon2);//删除优惠券
		GiftCommodity giftCommodity2=new GiftCommodity();//删除商品
		giftCommodity2.setGiveId(giftId);
		giftCommodityService.deleteById(giftCommodity2);
		activityGiveMapper.updateByPrimaryKey(activityGive);
		if(getIsValue(giftCoupons)){
			for (GiftCoupon giftCoupon : giftCoupons) {
				giftCouponService.add(giftCoupon);//优惠券
			}
		}
		if(getIsValue(giftCommodities)){
			for (GiftCommodity giftCommodity : giftCommodities) {
				giftCommodityMapper.insert(giftCommodity);//商品
			}
		}
		if(getIsValue(giftCommoditySkus)){
			for (GiftCommoditySku giftCommoditySku : giftCommoditySkus) {//商品sku
				giftCommoditySkuMapper.insert(giftCommoditySku);
			}
		}
		if(isreplaceflag==null){//无替换主商品
		}else{//替换主商品，更新主商品
			ResultDto resultDto=commodityApiService.updateCommodityByKey(commodityputong);
			if(!resultDto.getCode().equals(ResultDto.OK_CODE)){
				throw new ServiceException("更新商品失败，回滚数据");
			}
		}
		ResultDto resultDto=commodityApiService.updateCommodityByKey(commoditymain);
		if(!resultDto.getCode().equals(ResultDto.OK_CODE)){
			throw new ServiceException("更新商品失败，回滚数据");
		}

		
		setRedistGiveLimitKey(activityGive.getId(),activityGive.getCommodityCode(), activityGive.getLimitCount());
		setRedistGiveIdLimitKey(activityGive.getId(),activityGive.getCommodityCode(),activityGive.getIdLimit());
		setRedisTriggerCount(activityGive.getId(),activityGive.getCommodityCode(), activityGive.getTriggerCount());
		if(getIsValue(giftCommoditySkus)){
			for (GiftCommoditySku giftCommoditySku : giftCommoditySkus) {//商品sku
				
				setRedistGivePayKey(activityGive.getId(), giftCommoditySku.getGiftCommoditySkuCode()
						, intisNullfuyi(giftCommoditySku.getGiftLimtCount()));
				setOneGiftKey(activityGive.getId(), giftCommoditySku.getGiftCommoditySkuCode()
						, intisNullfuyi(giftCommoditySku.getGiftCount()));
				
			}
		}
		
	
		
	}
	/**
	 * int是空返回-1
	 * @return
	 */
	private Integer intisNullfuyi(Integer num){
		return (num==null?-1:num);
	}
	private String vaidateLimit(ActivityGive activityGive){

		 Map<String, String> map=new HashMap<String, String>();//判断是否重复
		if(StringUtil.isEmpty(activityGive.getCommodityCommoditySkuNum())){
			return null;
		}
		String[] commodityCommoditySkuNum=activityGive.getCommodityCommoditySkuNum().split(";");
		for (String keyvalue : commodityCommoditySkuNum) {//map转入，多个，好隔开
			String[] commodity=keyvalue.split(",");
			String barcode=commodity[3];
			String giftLimtCount=commodity[1];
			if(map.keySet().contains(barcode)){
				map.put(barcode,map.get(barcode)+","+giftLimtCount);
			}else{
				map.put(barcode,giftLimtCount);
			}
		}
		for(String key : map.keySet()) {
			String value=map.get(key);
			String[] strings=value.split(",");
			if(strings.length>=2){//是否有两个
				if(strings[0].equals(fuyi)){//-1
					for(String stringvalue : strings){//判断value
						if(!stringvalue.equals(fuyi)){
							return goodsnullExcetion;
						}
					}
				}else{
					for(String stringvalue : strings){//判断value
						if(stringvalue.equals(fuyi)){
							return goodsnullExcetion;
						}
					}
				}
				
			}
		}
		return null;
	}
	@Transactional(rollbackFor=Exception.class)
	@Override
	public String saveGiveDate(ActivityGive activityGive) throws Exception {
		// TODO Auto-generated method stub
		String returnValue=vaidateLimit(activityGive);
		if(!StringUtil.isEmpty(returnValue)){
			return returnValue;
		};
		if(!StringUtil.isEmpty(activityGive.getId())){//更新
			ActivityGive sourceactivityGive= activityGiveMapper.selectByPrimaryKey(activityGive.getId());
			//是否删除原来的商品,null,不删除
			Commodity isreplaceflag= isDelteMainGoods(activityGive,sourceactivityGive);
			getActivityGiveValue(activityGive,null,isreplaceflag,sourceactivityGive);
			Commodity commoditymain=new Commodity();//主商品
			Commodity commodityputong=new Commodity();//普通商品
			getMainPutongCommodity(commoditymain,activityGive,commodityputong,sourceactivityGive);
			List<GiftCoupon> giftCoupons=giftCoupons(activityGive);
			List<GiftCommodity> giftCommodities=new ArrayList<GiftCommodity>();
			List<GiftCommoditySku> giftCommoditySkus=new ArrayList<GiftCommoditySku>();
			getgiftCommoditiesAndSku(giftCommodities,giftCommoditySkus,activityGive);
			giveupdate(commoditymain, commodityputong, isreplaceflag, activityGive, giftCommodities, giftCommoditySkus, giftCoupons);
			
		}else{//保存
			
			Commodity maincom=yanzhengisGoods(activityGive);
			if(maincom.getGiveType()==com.ffzx.promotion.api.dto.constant.Constant.main){
				return "该商品为主商品";
			}
			String giveId=UUIDGenerator.getUUID();
			getActivityGiveValue(activityGive,giveId,maincom,null);
			Commodity commodity=new Commodity();
			getCommodity(commodity,activityGive,com.ffzx.promotion.api.dto.constant.Constant.main);//商品信息
			List<GiftCoupon> giftCoupons=giftCoupons(activityGive);
			List<GiftCommodity> giftCommodities=new ArrayList<GiftCommodity>();
			List<GiftCommoditySku> giftCommoditySkus=new ArrayList<GiftCommoditySku>();
			getgiftCommoditiesAndSku(giftCommodities,giftCommoditySkus,activityGive);
			giveinnupdateinsert(commodity, activityGive, giftCommodities, giftCommoditySkus, giftCoupons);
			logger.info("保存买赠商品"+maincom.getId()+" 买赠id"+activityGive.getId());
		}
		return null;
	}
	/**
	 * set主商品限定数量
	 */
	private void setRedistGiveLimitKey(String giveId,String commodityCode,Integer limitcount){
		redisUtil.set(com.ffzx.promotion.api.dto.constant.Constant.getLimitKey(giveId,commodityCode),
				limitcount);
	}
	
	private void setRedistGiveIdLimitKey(String giveId,String commodityCode,Integer idLimitCoun){
		redisUtil.set(com.ffzx.promotion.api.dto.constant.Constant.getIdLimitKey(giveId,commodityCode),
				idLimitCoun);
	}
	
	/**
	 * 主品触发数量
	 * @param commodityCode
	 * @param triggerCount
	 */
	private void setRedisTriggerCount(String giveId,String commodityCode,Integer triggerCount){
		redisUtil.set(com.ffzx.promotion.api.dto.constant.Constant.getTriggerKey(giveId,commodityCode),
				triggerCount);
	}
	
	/**
	 * set主赠品限定数量
	 */
	private void setRedistGivePayKey(String giftCommodityId,String skuCode,Integer limitcount){
		redisUtil.set(com.ffzx.promotion.api.dto.constant.Constant.getGiftLimit(giftCommodityId,skuCode),
				limitcount);
	}


	/**
	 * set赠品单次赠送量
	 */
	private void setOneGiftKey(String giftCommodityId,String skuCode,Integer limitcount){
		redisUtil.set(com.ffzx.promotion.api.dto.constant.Constant.getOneGiftKey(giftCommodityId,skuCode),
				limitcount);
	}

	
	private <T> boolean getIsValue(List<T> list){
		if(list==null || list.size()==0){
			return false;
		}
		return true;
	}
	private void getGiftType(ActivityGive activityGive
			,List<GiftCommodity> giftCommodities,List<GiftCoupon> giftCoupons){
		if(getIsValue(giftCoupons)){//如果优惠券有值
			if(getIsValue(giftCommodities)){
				activityGive.setGiftType(com.ffzx.promotion.api.dto.constant.Constant.GoodsAndCoupon);
			}else{
			activityGive.setGiftType(com.ffzx.promotion.api.dto.constant.Constant.Coupon);
			}
		}else if(getIsValue(giftCommodities)){

			activityGive.setGiftType(com.ffzx.promotion.api.dto.constant.Constant.Goods);
		}
	}
	private void  giveinnupdateinsert(Commodity commodity,ActivityGive activityGive
			,List<GiftCommodity> giftCommodities,List<GiftCommoditySku> giftCommoditySkus,List<GiftCoupon> giftCoupons){
		getGiftType(activityGive, giftCommodities, giftCoupons);//获取activityGive类型
		
		activityGiveMapper.insert(activityGive);//赠品
		setRedistGiveLimitKey(activityGive.getId(),activityGive.getCommodityCode(), activityGive.getLimitCount());
		setRedistGiveIdLimitKey(activityGive.getId(),activityGive.getCommodityCode(),activityGive.getIdLimit());
		setRedisTriggerCount(activityGive.getId(),activityGive.getCommodityCode(), activityGive.getTriggerCount());
		if(getIsValue(giftCoupons)){
			for (GiftCoupon giftCoupon : giftCoupons) {
				giftCouponService.add(giftCoupon);//优惠券
			}
		}
	//	List<Commodity> commodityList=new ArrayList<Commodity>();
		if(getIsValue(giftCommodities)){
			for (GiftCommodity giftCommodity : giftCommodities) {
				giftCommodityMapper.insert(giftCommodity);//商品
/*				Commodity c=new Commodity();
				c.setId(giftCommodity.getCommodityId());
				c.setGiveType(2);
				commodityList.add(c);*/
			}
		}
		if(getIsValue(giftCommoditySkus)){
			for (GiftCommoditySku giftCommoditySku : giftCommoditySkus) {//商品sku
				giftCommoditySkuMapper.insert(giftCommoditySku);
				setRedistGivePayKey(activityGive.getId(), giftCommoditySku.getGiftCommoditySkuCode()
						,intisNullfuyi( giftCommoditySku.getGiftLimtCount()));
				setOneGiftKey(activityGive.getId(), giftCommoditySku.getGiftCommoditySkuCode()
						, intisNullfuyi( giftCommoditySku.getGiftCount()));
				
			}
		}
		ResultDto resultDto=commodityApiService.updateCommodityByKey(commodity);//更新主商品在商品系统是否买赠
/*		if(commodityList!=null &&commodityList.size()!=0){
			for(Commodity co:commodityList){
				resultDto=commodityApiService.updateCommodityByKey(co);//更新赠品在商品系统是否买赠
			}
		}*/
		if(!resultDto.getCode().equals(ResultDto.OK_CODE)){
			throw new ServiceException("更新商品失败，回滚数据");
		}
	}
	private void getActivityGiveValue(ActivityGive activityGive,String giveId,Commodity maincom
			,ActivityGive sourceactivityGive){
		if(StringUtil.isEmpty(giveId)){//更新
			SysUser updateBy = RedisWebUtils.getLoginUser();
			activityGive.setLastUpdateBy(updateBy);
			activityGive.setLastUpdateDate(new Date());
			activityGive.setGiveNo(sourceactivityGive.getGiveNo());
			activityGive.setCommodityCode(sourceactivityGive.getCommodityCode());
			activityGive.setCommodityBarcode(sourceactivityGive.getCommodityBarcode());
			activityGive.setActFlag(sourceactivityGive.getActFlag());
		}else{//新增
			activityGive.setActFlag(Constant.YES);
			activityGive.setId(giveId);
			activityGive.setCreateDate(new Date());
			ResultDto numberDto = codeRuleApiService.getCodeRule("prms", "prms_mz_number");
	
			if(!numberDto.getCode().equals(ResultDto.OK_CODE) || StringUtil.isEmpty((String)numberDto.getData()) ){
				logger.error("优惠券编码生成异常，请确认是否添加  规则键值  prms_mz_number");
				throw new  RuntimeException("优惠券编码生成异常，请确认是否添加  规则键值  prms_mz_number");
			}
			activityGive.setGiveNo(numberDto.getData().toString());
			SysUser createdBy = RedisWebUtils.getLoginUser();
			activityGive.setCreateBy(createdBy);
			activityGive.setCreateName(createdBy.getName());
		}
		if(maincom!=null && !StringUtil.isEmpty(maincom.getId())){
			activityGive.setCommodityCode(maincom.getCode());
			activityGive.setCommodityBarcode(maincom.getBarCode());
		}
	}
	/**
	 * 加商品集合和sku集合
	 * @param giftCommodities
	 * @param giftCommoditySkus
	 * @param activityGive
	 */
	private void getgiftCommoditiesAndSku(List<GiftCommodity> giftCommodities,
			List<GiftCommoditySku> giftCommoditySkus,ActivityGive activityGive){
		String commodityCommoditySku=activityGive.getCommodityCommoditySku();
		if(!StringUtil.isEmpty(commodityCommoditySku)){

			List<String> list=Arrays.asList(commodityCommoditySku.split(","));
			ResultDto resultDto=commoditySkuApiService.findSku(list, Constant.YES);
			List<CommoditySku> commoditySkus=(List<CommoditySku>) resultDto.getData();
			if(commoditySkus!=null && commoditySkus.size()>0){
				Map<String, GiftCommodity> containslist=new HashMap<String, GiftCommodity>();//剔除重复商品
				for (CommoditySku commoditySku : commoditySkus) {
					if(!containslist.keySet().contains(commoditySku.getCommodity().getId())){//判断是否重复，不重复add商品
						String uuid=UUIDGenerator.getUUID();
						GiftCommodity giftCommodity=new GiftCommodity();
						addCommodity(commoditySku, giftCommodities, activityGive,uuid,giftCommodity);//添加商品
						containslist.put(commoditySku.getCommodity().getId(),giftCommodity);
					}
					addCommoditySku(commoditySku, giftCommoditySkus, containslist, activityGive);//新增商品sku
					
				}
				
			}
			
		}
	}

	private String[] getSkuList(String[] numStrings,String skuId){
		for (String string : numStrings) {
			String[] skuList=string.split(",");
			if(skuList[0].trim().equals(skuId)){
				return skuList;
			}
		}
		return null;
	}
	/**
	 * 加商品sku
	 * @param commoditySku
	 * @param giftCommoditySkus
	 * @param containslist
	 * @param activityGive
	 */
	private void addCommoditySku(CommoditySku commoditySku,List<GiftCommoditySku> giftCommoditySkus,
			Map<String, GiftCommodity> containslist,ActivityGive activityGive){
		GiftCommodity giftCommodity=containslist.get(commoditySku.getCommodity().getId());//商品
		GiftCommoditySku giftCommoditySku=new GiftCommoditySku();
		giftCommoditySku.setId(UUIDGenerator.getUUID());
		giftCommoditySku.setGiftCommoditySkuBarcode(commoditySku.getBarcode());
		String[] numStrings=activityGive.getCommodityCommoditySkuNum().split(";");
		String[] skuList = getSkuList(numStrings, commoditySku.getId());
		if(skuList==null)
			throw new RuntimeException("匹配不到对应的sku限购数量");
		giftCommoditySku.setGiftCount(fushunullorInteger(skuList[2]));
		giftCommoditySku.setGiftLimtCount(fushunullorInteger(skuList[1]));
		giftCommoditySku.setGiftCommodityId(giftCommodity.getId());
		giftCommoditySku.setGiftCommoditySkuCode(commoditySku.getSkuCode());
		giftCommoditySku.setGiftCommoditySkuid(commoditySku.getId());
		giftCommoditySku.setPrice(new BigDecimal(0));
		giftCommoditySku.setDelFlag(Constant.NO);
		giftCommoditySkus.add(giftCommoditySku);
	}
	/**
	 * 负数为null，否则转int
	 * @param string
	 * @return
	 */
	private Integer fushunullorInteger(String string){
		return string.trim().equals("-1")?null:Integer.parseInt(string.trim());
	}
	private void addCommodity(CommoditySku commoditySku,List<GiftCommodity> giftCommodities,ActivityGive activityGive
			,String uuid,GiftCommodity giftCommodity){
		giftCommodity.setId(uuid);
		giftCommodity.setCommodityBarcode(commoditySku.getCommodity().getBarCode());
		giftCommodity.setCommodityId(commoditySku.getCommodity().getId());
		giftCommodity.setCommodityNo(commoditySku.getCommodity().getCode());
		giftCommodity.setGiveCommodityId(activityGive.getCommodityId());
		giftCommodity.setGiveId(activityGive.getId());
		giftCommodities.add(giftCommodity);
	}
	private List<GiftCoupon> giftCoupons(ActivityGive activityGive){
		String coupons=activityGive.getCouponAdminList();
		List<GiftCoupon> gcList=new ArrayList<GiftCoupon>();
		if(StringUtil.isEmpty(coupons)){
			return null;
		}
		Map<String, Object> params=new HashMap<String, Object>();
		params.put("listid", coupons.split(","));
		List<CouponAdmin> couponAdmins=couponAdminService.findByBiz(params);
		if(couponAdmins!=null && couponAdmins.size()>0){
			for (CouponAdmin couponAdmin : couponAdmins) {
				addCouponAdmin(couponAdmin, gcList, activityGive);
			}
		}
		return gcList;
	}
	/**
	 * 加优惠券
	 * @param couponAdmin
	 * @param gcList
	 * @param activityGive
	 */
	private void addCouponAdmin(CouponAdmin couponAdmin,List<GiftCoupon> gcList,ActivityGive activityGive){
		GiftCoupon giftCoupon=new GiftCoupon();
		String couponNum;//优惠券有效期
		if(!StringUtil.isEmpty(couponAdmin.getEffectiveDateState()) && 
				couponAdmin.getEffectiveDateState().equals(Constant.NO)){//*天
			couponNum=couponAdmin.getEffectiveDateNum()+"天";
		}else{
			couponNum=DateUtil.format(couponAdmin.getEffectiveDateStart(), DateUtil.FORMAT_DATETIME)
					+" 至 "+DateUtil.format(couponAdmin.getEffectiveDateEnd(), DateUtil.FORMAT_DATETIME);
		}
		giftCoupon.setCommodityId(activityGive.getCommodityId());
		giftCoupon.setCouponCode(couponAdmin.getNumber());
		giftCoupon.setCouponFace(couponAdmin.getFaceValue().toString());
		giftCoupon.setCouponId(couponAdmin.getId());
		giftCoupon.setCouponLimit(couponAdmin.getConsumptionLimit().toString());
		giftCoupon.setCouponName(couponAdmin.getName());
		giftCoupon.setCouponNum(couponNum);
		giftCoupon.setGiveId(activityGive.getId());
		giftCoupon.setId(UUIDGenerator.getUUID());
		giftCoupon.setDelFlag(Constant.NO);
		gcList.add(giftCoupon);
	}
	private void getCommodity(Commodity commodity,ActivityGive activityGive,int type){
		commodity.setId(activityGive.getCommodityId());
		commodity.setCode(activityGive.getCommodityCode());
		commodity.setGiveType(type);
	}

	@Override
	public List<GiveExportVo> findListToExport(Map<String,Object> params) {
		//主商品集合
		List<GiveExportVo> giveList = this.activityGiveMapper.findGiveListToExport(params);
		//优惠券赠品集合
		List<CouponExportVo> couponList = this.activityGiveMapper.findCouponListToExport(params);
		//商品赠品集合
		List<GiftExportVo> giftList = this.activityGiveMapper.findGiftListToExport(params);
		for (CouponExportVo couponExportVo : couponList) {
			for (GiveExportVo giveExportVo : giveList) {
				if(couponExportVo.getGiveId().equals(giveExportVo.getId())){
					giveExportVo.getCouponList().add(couponExportVo);
					break;
				}
			}
		}
		
		for (GiftExportVo giftExportVo : giftList) {
			for (GiveExportVo giveExportVo : giveList) {
				if(giftExportVo.getGiveId().equals(giveExportVo.getId())){
					giveExportVo.getGiftList().add(giftExportVo);
					break;
				}
			}
		}
		
		return giveList;
	}
	
	
	@Transactional(rollbackFor=Exception.class)
	@Override
	public void deleteAndhuifu(ActivityGive activityGive) throws RuntimeException {
		// TODO Auto-generated method stub	
		logger.info("删除买赠商品"+activityGive.getId());
		activityGiveMapper.updateByPrimaryKeySelective(activityGive);
		//删除主商品连带赠品集合删除
		if(Constant.DELTE_FLAG_YES.equals(activityGive.getDelFlag())){
			Map<String,Object> params=new HashMap<String,Object>();
			params.put("giveId", activityGive.getId());
			List<GiftCommodity> giftList=this.giftCommodityMapper.selectByParams(params);
			if(giftList!=null && giftList.size()!=0){
				for(GiftCommodity gift:giftList){
					gift.setDelFlag(Constant.DELTE_FLAG_YES);
					this.giftCommodityMapper.updateByPrimaryKeySelective(gift);
				}
			}
		}
		
		ActivityGive give= activityGiveMapper.selectByPrimaryKey(activityGive.getId());
		Commodity commodityputong=new Commodity();//普通商品
		/*//启用的时候变回主品
		if(Constant.ACT_FLAG_NO.equals(activityGive.getActFlag())){
			getCommodity(commodityputong, give, com.ffzx.promotion.api.dto.constant.Constant.main);
		}*/
		//删除或者禁用的时候变回普通
		/*if(Constant.DELTE_FLAG_YES.equals(activityGive.getDelFlag()) || Constant.ACT_FLAG_YES.equals(activityGive.getActFlag())){
			getCommodity(commodityputong, give, com.ffzx.promotion.api.dto.constant.Constant.putong);
		}*/
		if(Constant.DELTE_FLAG_YES.equals(activityGive.getDelFlag())){
			getCommodity(commodityputong, give, com.ffzx.promotion.api.dto.constant.Constant.putong);
		}
		GiftCoupon giftCoupon=new GiftCoupon();
		giftCoupon.setDelFlag(Constant.YES);
		giftCoupon.setGiveId(activityGive.getId());
		giftCouponMapper.updateByGiveDelFlag(giftCoupon);
		commodityApiService.updateCommodityByKey(commodityputong);//商品更新可能返回null对象，不做判断
		
		
	}
}