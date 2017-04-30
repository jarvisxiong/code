/**
 * 
 * @author yinglong.huang
 * @email yinglong.huang@ffzxnet.com
 * @date 2016年10月26日 下午6:31:26
 * @version 1.0.0
 * @copyright www.ffzxnet.com
 */
package com.ffzx.order.service.impl;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;

import com.alibaba.fastjson.JSONArray;
import com.ffzx.appserver.api.service.ShoppingCartApiService;
import com.ffzx.basedata.api.dto.Address;
import com.ffzx.basedata.api.dto.Partner;
import com.ffzx.basedata.api.dto.PartnerServiceStation;
import com.ffzx.basedata.api.dto.Warehouse;
import com.ffzx.basedata.api.service.AddressApiService;
import com.ffzx.basedata.api.service.CodeRuleApiService;
import com.ffzx.basedata.api.service.DictApiService;
import com.ffzx.basedata.api.service.PartnerApiService;
import com.ffzx.basedata.api.service.PartnerApiServiceStationService;
import com.ffzx.bms.api.dto.OrderParam;
import com.ffzx.bms.api.service.OrderProcessManagerApiService;
import com.ffzx.commerce.framework.constant.Constant;
import com.ffzx.commerce.framework.constant.RedisPrefix;
import com.ffzx.commerce.framework.dao.CrudMapper;
import com.ffzx.commerce.framework.dto.ResultDto;
import com.ffzx.commerce.framework.exception.ServiceException;
import com.ffzx.commerce.framework.service.impl.BaseCrudServiceImpl;
import com.ffzx.commerce.framework.utils.CodeGenerator;
import com.ffzx.commerce.framework.utils.DateUtil;
import com.ffzx.commerce.framework.utils.JsonMapper;
import com.ffzx.commerce.framework.utils.RedisUtil;
import com.ffzx.commerce.framework.utils.StringUtil;
import com.ffzx.commerce.framework.utils.UUIDGenerator;
import com.ffzx.member.api.dto.Member;
import com.ffzx.member.api.dto.MemberAddress;
import com.ffzx.member.api.service.MemberApiService;
import com.ffzx.order.api.dto.Commodity;
import com.ffzx.order.api.dto.CommoditySku;
import com.ffzx.order.api.dto.GoodsVo;
import com.ffzx.order.api.dto.OmsOrder;
import com.ffzx.order.api.dto.OmsOrderdetail;
import com.ffzx.order.api.enums.BuyTypeEnum;
import com.ffzx.order.api.enums.OrderTypeEnum;
import com.ffzx.order.api.service.OrderApiService;
import com.ffzx.order.service.OmsOrderFormService;
import com.ffzx.order.service.OmsOrderService;
import com.ffzx.promotion.api.service.ActivityGiveApiService;
import com.ffzx.promotion.api.service.ActivityManagerApiService;
import com.ffzx.promotion.api.service.ActivityOrderApiService;
/***
 * @author yinglong.huang
 * @email yinglong.huang@ffzxnet.com
 * @date 2016年10月26日 下午6:31:26
 * @version V1.0
 */
@SuppressWarnings({ "unchecked", "unused" })
@Service("omsOrderFormService")
public class OmsOrderFormServiceImpl extends BaseCrudServiceImpl implements OmsOrderFormService {
	@Resource
	private RedisUtil redisUtil;
	@Autowired
	private MemberApiService memberApiService;
	@Autowired
	private AddressApiService addressApiService;
	@Autowired
	public ShoppingCartApiService shoppingCartApiService;
	@Autowired
	private OrderApiService orderApiService;
	@Autowired
	private CodeRuleApiService codeRuleApiService;
	@Autowired
	private DictApiService dictApiService;
	@Autowired
	private ActivityManagerApiService activityManagerApiService;
	@Autowired
	private ActivityGiveApiService activityGiveApiService;//买赠api
	@Autowired
	private OrderProcessManagerApiService orderProcessManagerApiService;
	@Autowired
	private ActivityOrderApiService activityOrderApiService;
	@Autowired
	private OmsOrderService omsOrderService;
	@Autowired 
	PartnerApiServiceStationService partnerApiServiceStationService;
	@Autowired
	private PartnerApiService partnerApiService;
	/***
	 * 
	 * @param sysType
	 * @param uId
	 * @param couponId
	 * @param addressId
	 * @param isInvoice
	 * @param goodsListStr
	 * @return
	 * @throws ServiceException
	 * @author yinglong.huang
	 * @email yinglong.huang@ffzxnet.com
	 * @date 2016年10月26日 下午6:31:26
	 * @version V1.0
	 * @return 
	 */

	@Override
	public OmsOrder placeAnOrder(String sysType, String uId, String couponId, String addressId,
			String isInvoice, String goodsListStr) throws ServiceException {
		
		StopWatch sw_placeAnOrder = new StopWatch("sw_placeAnOrder");
		// TODO Auto-generated method stub
		logger.info("OmsOrderFormServiceImpl==>>placeAnOrder==》》校验用户:uId："+uId);
		logger.info("OmsOrderFormServiceImpl==>>placeAnOrder==》》校验用户:uId："+uId);
		//=============================校验下单用户Start===========================
		sw_placeAnOrder.start("sw_placeAnOrder_checkMember:校验用户"); 
		Member member = checkMember(uId); //新用户，1是0否
		sw_placeAnOrder.stop();
		//=============================校验下单用户End=============================
		
		logger.info("OmsOrderFormServiceImpl==>>placeAnOrder==》》校验收货地址:addressId："+addressId);
		//=============================校验下单收货地址Start===========================
		sw_placeAnOrder.start("sw_placeAnOrder_checkAddress:检验收货地址"); 
		Map<String,Object> addressInfoMap = checkAddress(addressId);
		//会员关联地址信息
		MemberAddress memberAddress =(MemberAddress) addressInfoMap.get("memberAddress"); 
		//配送地址信息
		Address address = (Address) addressInfoMap.get("address");
		//合伙人信息
		Partner partner = (Partner) addressInfoMap.get("partner");
		if(null==partner){
			logger.error("OmsOrderFormServiceImpl==>>placeAnOrder==》》无法获取合伙人");
			throw new ServiceException(1,"你选择的收货地址暂不支持配送，请选择其他收货地址");
		}
		PartnerServiceStation partnerServiceStation= checkPartnerServiceStation(partner.getId());
		if(!addressInfoMap.containsKey("warehousecode")){
			logger.error("OmsOrderFormServiceImpl==>>placeAnOrder==》》无法获取中央仓");
			throw new ServiceException(1,"你选择的收货地址暂不支持配送，请选择其他收货地址"); 
		 }
		//区域仓库 
		Warehouse aeraWarehouse = (Warehouse) addressInfoMap.get("warehousecode");
		if(!addressInfoMap.containsKey("warehouse")){
			logger.error("OmsOrderFormServiceImpl==>>placeAnOrder==》》该地址无法获取区域仓库");
			throw new ServiceException(1,"你选择的收货地址暂不支持配送，请选择其他收货地址"); 
		 }
		//中央仓库
		Warehouse centerWarehouse = (Warehouse) addressInfoMap.get("warehouse");
		sw_placeAnOrder.stop();
		//=============================校验下单收货地址End=============================
		logger.info("OmsOrderFormServiceImpl==>>placeAnOrder==》》初始化订单信息");
		sw_placeAnOrder.start("sw_placeAnOrder_initOrderInfo:初始化订单信息");
		OmsOrder omsOrder = this.initOrderInfo(member, memberAddress, address, partner, isInvoice, sysType,aeraWarehouse,partnerServiceStation);
		if(StringUtil.isNotNull(couponId)){
			omsOrder.setCouponId(couponId);
			logger.info("OmsOrderFormServiceImpl==>>placeAnOrder==》》【"+omsOrder.getOrderNo()+"】该订单使用优惠券,优惠券id:"+omsOrder.getCouponId());
		}
		//初始化仓库信息
		//中央仓
		omsOrder.setCenterWarehouse(centerWarehouse);
		//县仓
		omsOrder.setAreaWarehouse(aeraWarehouse);
		sw_placeAnOrder.stop();
		//记录该单的 关联数据信息
		logger.info("OmsOrderFormServiceImpl==>>placeAnOrder==》》【"+omsOrder.getOrderNo()+"】该订单的收货地址【memberAddress】信息:"+JsonMapper.toJsonString(memberAddress));
		logger.info("OmsOrderFormServiceImpl==>>placeAnOrder==》》【"+omsOrder.getOrderNo()+"】该订单的关联的配送地址【address】信息:"+JsonMapper.toJsonString(address));
		logger.info("OmsOrderFormServiceImpl==>>placeAnOrder==》》【"+omsOrder.getOrderNo()+"】该订单的合伙人【partner】信息:"+JsonMapper.toJsonString(partner));
		logger.info("OmsOrderFormServiceImpl==>>placeAnOrder==》》【"+omsOrder.getOrderNo()+"】该订单的区域仓库【aeraWarehouse】信息:"+JsonMapper.toJsonString(aeraWarehouse));
		logger.info("OmsOrderFormServiceImpl==>>placeAnOrder==》》【"+omsOrder.getOrderNo()+"】该订单的中央仓库【centerWarehouse】信息:"+JsonMapper.toJsonString(centerWarehouse));
		
		logger.info("OmsOrderFormServiceImpl==>>placeAnOrder==》》【"+omsOrder.getOrderNo()+"】初始化订单明细信息,原始订单明细数据："+goodsListStr);
		List<GoodsVo> goodsList = JSONArray.parseArray(goodsListStr,GoodsVo.class);
		//校验新用户下单条件
		
		logger.info("OmsOrderFormServiceImpl==>>placeAnOrder==》》【"+omsOrder.getOrderNo()+"】初始化订单明细信息,原始订单明细数据转为list数据完成:goodsList"+JsonMapper.toJsonString(goodsList));
		logger.info("OmsOrderFormServiceImpl==>>placeAnOrder==》》【"+omsOrder.getOrderNo()+"】开始生成订单明细,执行订单明细方法initOmsOrderdetail()");
		sw_placeAnOrder.start("sw_placeAnOrder_initOmsOrderdetail:初始化订单明细");
		this.initOmsOrderdetail(goodsList, omsOrder,aeraWarehouse,centerWarehouse);
		sw_placeAnOrder.stop();
//		logger.info("OmsOrderFormServiceImpl==>>placeAnOrder==》》【"+omsOrder.getOrderNo()+"】原始订单数据构建完成："+JsonMapper.toJsonString(omsOrder));
		//验证商品购买可行性  
		logger.info("OmsOrderFormServiceImpl==>>placeAnOrder==》》检验商品购买合法性");
		sw_placeAnOrder.start("sw_placeAnOrder_checkCommodity:检验商品购买合法性");
		this.checkCommodity(omsOrder,member.getIsNew());
		sw_placeAnOrder.stop();
		//验证促销管理相数据
		logger.info("OmsOrderFormServiceImpl==>>placeAnOrder==》》【"+omsOrder.getOrderNo()+"】订单开始流入prms系统计算优惠方案");
		sw_placeAnOrder.start("sw_placeAnOrder_checkPrms:促销系统计算优惠方案");
		OmsOrder prmsOrder = this.checkPrms(omsOrder);
		if(StringUtil.isNotNull(prmsOrder)){
			omsOrder = prmsOrder;
		}
		sw_placeAnOrder.stop();
		logger.info("OmsOrderFormServiceImpl==>>placeAnOrder==》》【"+omsOrder.getOrderNo()+"】prms系统对订单计算优惠方案完成"+JsonMapper.toJsonString(omsOrder));
		ResultDto result = null;
		logger.info("OmsOrderFormServiceImpl==>>placeAnOrder==》》【"+omsOrder.getOrderNo()+"】计算下单超时时间");
		initOverTime(omsOrder);
		logger.info("OmsOrderFormServiceImpl==>>placeAnOrder==》》【"+omsOrder.getOrderNo()+"】开始调用orderApiService.buildOrder()并持久化订单");
		
		try{
		sw_placeAnOrder.start("sw_placeAnOrder_buildOrder:成单构建");
	    result = orderApiService.buildOrder(omsOrder);
	    sw_placeAnOrder.stop();
		}catch(Exception e){
			logger.error("APP-client-OmsOrderServiceImpl-placeAnOrder=》 " , e);
			//下单失败回滚购买量缓存
			logger.error("回滚缓存 " , e);
			List<OmsOrderdetail> detailList=omsOrder.getDetailList();//订单详情
			backRedisUtilBuyNum(omsOrder, detailList);
			throw new ServiceException(1,"网络超时，请稍后重试"); 
		}
		 if(!result.getCode().equals(ResultDto.OK_CODE)){
			 logger.error("APP-client-OmsOrderServiceImpl-placeAnOrder=》 " + result.getMessage());
				throw new ServiceException(1,result.getMessage()); 
		 }
		logger.info("OmsOrderFormServiceImpl==>>placeAnOrder==》》【"+omsOrder.getOrderNo()+"】订单已插入到数据库");
		
		/*Map<String, Object> resultMap = new HashMap<String,Object>();
		resultMap.put("orderId", omsOrder.getId());//订单ID
		resultMap.put("orderNumber", omsOrder.getOrderNo());//订单编号
		resultMap.put("buyTime", DateUtil.formatDateTime(omsOrder.getCreateDate()));//购买时间
		resultMap.put("status", omsOrder.getStatus());//购买时间
		resultMap.put("overTime",DateUtil.formatDateTime(omsOrder.getOverTime()));//自动取消该单时间
		resultMap.put("totalPrice", omsOrder.getActualPrice());//需支付总金额
	    
		resultMap.put("addressInfo",omsOrder.getAddressInfo());//收货地址信息
		resultMap.put("consignPhone",omsOrder.getConsignPhone());//收货人电话
		resultMap.put("consignName",omsOrder.getConsignName());//收货人姓名
*/		
		
		//清空购物车
		logger.info("OmsOrderFormServiceImpl==>>placeAnOrder==》》【"+omsOrder.getOrderNo()+"】清除订单明细所对应的购物车");
		sw_placeAnOrder.start("sw_placeAnOrder_clearShoppingCart:清除购物车相关数据");
		clearShoppingCart(member.getId(),goodsList);
		sw_placeAnOrder.stop();
		logger.error("下单执行时间【"+omsOrder.getOrderNo()+"】"+sw_placeAnOrder.prettyPrint());
//		更新会员为老用户
//		member.setIsNew("0");
//		memberApiService.updateMember(member);
		return omsOrder;
	}

	private void backRedisUtilBuyNum(OmsOrder omsOrder, List<OmsOrderdetail> detailList)  {
		for(OmsOrderdetail omsOrderdetail:detailList){	
			int buyNum  = omsOrderdetail.getBuyNum();
			//买赠
			if(null!=omsOrderdetail.getPromotions()&&omsOrderdetail.getPromotions().equals("1")){
				//买赠活动相关缓存
				if(null!=omsOrderdetail.getBuyGifts()&&omsOrderdetail.getBuyGifts().equals("1")){
					String userIDKey=com.ffzx.promotion.api.dto.constant.Constant.getMemberGivePayKey(omsOrderdetail.getGiftCommodityItemId(),omsOrderdetail.getCommodityNo(), omsOrder.getMemberId());
					rollBackRedisActivityNum_auto(userIDKey,buyNum);
					logger.info("【"+omsOrder.getOrderNo()+"】【买赠用户购买量】取消订单,回滚【"+userIDKey+"】缓存");
					String commodityKey=com.ffzx.promotion.api.dto.constant.Constant.getGivePayKey(omsOrderdetail.getGiftCommodityItemId(),omsOrderdetail.getCommodityNo());		
					rollBackRedisActivityNum_auto(commodityKey,buyNum);
					logger.info("【"+omsOrder.getOrderNo()+"】【买赠商品购买数量】取消订单,回滚【"+commodityKey+"】缓存");
				}
				if(null!=omsOrderdetail.getBuyGifts()&&omsOrderdetail.getBuyGifts().equals("2")){
					String giftCommodityKey=com.ffzx.promotion.api.dto.constant.Constant.getGiftPayKey(omsOrderdetail.getGiftCommodityItemId(), omsOrderdetail.getSkuCode());
					rollBackRedisActivityNum_auto(giftCommodityKey,buyNum);
					logger.info("【"+omsOrder.getOrderNo()+"】【买赠买赠活动购买数量】取消订单,回滚【"+giftCommodityKey+"】缓存");
				}
			}
			//非普通活动以及批发
			if(!omsOrderdetail.getBuyType().getValue().equals(BuyTypeEnum.COMMON_BUY)&&!omsOrderdetail.getBuyType().getValue().equals(BuyTypeEnum.ORDINARY_ACTIVITY.getValue())
					&& !omsOrderdetail.getBuyType().getValue().equals(BuyTypeEnum.WHOLESALE_MANAGER.getValue())){
			
			String memberRedisKey = RedisPrefix.APP_PAY_BUYNUM+omsOrder.getMemberId()+"_"+omsOrderdetail.getActivityCommodityItemId()+"_"+omsOrderdetail.getCommodityNo()+"_buyNum";
			//回滚用户的购买量
			rollBackRedisActivityNum_auto(memberRedisKey,buyNum);
			logger.info("【"+omsOrder.getOrderNo()+"】下单异常,回滚【"+memberRedisKey+"】缓存");
			
			String activityredisKey = RedisPrefix.APP_PAY_BUYNUM+omsOrderdetail.getActivityCommodityItemId()+"_"+omsOrderdetail.getCommodityNo()+"_buyNum";
			//取消定订单回滚缓存之前该商品缓存的购买量
			Object obj_buycount =  redisUtil.incGet(activityredisKey);
			//回滚活动的购买量
			rollBackRedisActivityNum_auto(activityredisKey,buyNum);
			logger.info("【"+omsOrder.getOrderNo()+"】下单异常,回滚【"+activityredisKey+"】缓存");
			
			String skuredisKey = RedisPrefix.APP_PAY_BUYNUM+omsOrderdetail.getActivityCommodityItemId()+"_"+omsOrderdetail.getSkuCode()+"_buyNum";
			//回滚活动的购买量
			rollBackRedisActivityNum_auto(skuredisKey,buyNum);
			logger.info("【"+omsOrder.getOrderNo()+"】下单异常,回滚【"+skuredisKey+"】缓存");
			
			//库存回滚将相关缓存状态修改
			try{				
				String limitKey = RedisPrefix.ACTIVITY + RedisPrefix.ACTIVITY_LIMIT + omsOrderdetail.getActivityCommodityItemId() + "_" + omsOrderdetail.getCommodityNo() + "limit";
				if(redisUtil.exists(limitKey)){
				Object obj_limitCount = redisUtil.get(limitKey);
				//当回滚缓存之前购买量和限定量数量一样表示该商品已经抢完，因此取消订单要把商品改为进行中（未抢完）
				if ((int)obj_buycount== (int)obj_limitCount) {
					//取消定订单回滚缓存之后该商品缓存的购买量
					Object back_buycount =  redisUtil.incGet(activityredisKey);
					if((int)back_buycount<(int)obj_limitCount){
						// 修改商品状态为进行中
						this.activityManagerApiService.updateActivityCommodityStatusIng(omsOrderdetail.getActivityCommodityItemId());
					}
					
				}
				}
			}catch(Exception e){
				logger.error("取消订单【"+omsOrder.getOrderNo()+"】回滚活动商品"+omsOrderdetail.getCommodityNo()+"是否已经抢完",e);
			}
			}
		}
	}
	//活动商品数量 回滚缓存 
	public void rollBackRedisActivityNum(String redisKey,int buyNum){
					if(redisUtil.exists(redisKey)){
						//redisUtil.remove(redisKey);
						//该用户对该活动的商品已经购买的数量
						String oldBuyNumStr = (String) redisUtil.get(redisKey);
						int oldBuyNum= oldBuyNumStr==null?0:Integer.parseInt(oldBuyNumStr);
						int curBuyNum =  oldBuyNum-buyNum;
						logger.info("下单异常,回滚【"+redisKey+"】缓存:"+(curBuyNum>0?curBuyNum:0));
						redisUtil.set(redisKey, (curBuyNum>0?curBuyNum:0)+"");
						logger.info("下单异常,回滚【"+redisKey+"】缓存成功:"+(curBuyNum>0?curBuyNum:0));
					}
			}
	public void rollBackRedisActivityNum_auto(String redisKey,int buyNum){
			if(redisUtil.exists(redisKey)){
				redisUtil.decrease(redisKey, buyNum);
			}
	}
	
	
	
	private  void clearShoppingCart(String memberId,List<GoodsVo> goodsList){
		String cartIds = null;
		for (GoodsVo goodsVo : goodsList) {
			if(StringUtil.isNotNull(goodsVo.getCarId())){
				cartIds+=goodsVo.getCarId()+";";
			}
		}
		if(StringUtil.isNotNull(cartIds))
			try {
				this.removeShoppingCart(memberId, cartIds, "SINGLE");
			} catch (Exception e) {
				logger.error("app-client--clearShoppingCart=》 " , e);
				throw new ServiceException(1,e.getMessage());
			}
	}
	/**
	 * 移除购物车接口
	 * 
	 * @param cartIds 购物车ids
	 */
	public void removeShoppingCart(String memberId,String cartId,String type)  throws ServiceException{
		shoppingCartApiService.removeShoppingCart(memberId,cartId,type);
	}
	private Member checkMember(String uId){
		ResultDto result = null;
		try {
			//调用验证接口
			result = memberApiService.placeOrderValidateMember(uId);
			
			//result = memberApiService.getByIdMember(uId);
		} catch (Exception e) {
			logger.error("app-client-OmsOrderServiceImpl-checkMember=》 " , e);
			throw new ServiceException(1,"会员信息获取失败");
		}
		if(result.getCode().equals(ResultDto.OK_CODE) ){
			return  (Member) result.getData();
		}else{
			logger.error("app-client-OmsOrderServiceImpl-checkMember=》" + result.getMessage());
			throw new ServiceException(1,result.getMessage());
		}
	}
	
	private Map<String,Object>  checkAddress(String addressId){
		//TODO 会员dubbo接口返回 data=boolean
				//根据地址获取会员详细地址信息
				ResultDto memberAddressDubboResult = new ResultDto();
				memberAddressDubboResult = memberApiService.getAddressId(addressId);
				if(!memberAddressDubboResult.getCode().equals(ResultDto.OK_CODE )){
					logger.info("OmsOrderFormServiceImpl==>>checkAddress 调用异常===>>>"+memberAddressDubboResult.getMessage());
					throw new ServiceException(1,"收货地址已经失效");
				}
				MemberAddress memberAddress = (MemberAddress) memberAddressDubboResult.getData();
				if(!StringUtil.isNotNull(memberAddress)){
					throw new ServiceException(1,"你选择的收货地址暂不支持配送，请选择其他收货地址");
				}
				if(!StringUtil.isNotNull(memberAddress.getRegionId())){
					throw new ServiceException(1,"该收货地址暂无配送服务点,请联系客户帮您处理");
				}
				//区域地址接口调用
				ResultDto addressDubboResult = new ResultDto();
				String regionId = memberAddress.getRegionId();
				String key ="ORDER_REGION_"+regionId;
				if(redisUtil.exists(key)){
					addressDubboResult =  (ResultDto) redisUtil.get(key);
				}else{
				try {
					//根据用户地址的最下级区域地址id查询区域地址 该接口 可获取到区域地址信息以及相关合伙人信息
					addressDubboResult  = addressApiService.getAddressParent(memberAddress.getRegionId());
					if(!addressDubboResult.getCode().equals(ResultDto.OK_CODE )){
						logger.info("下单根据区域地址："+addressId+"获取信息，addressApiService.getAddressParent("+memberAddress.getRegionId()+"),调用异常");
						throw new ServiceException(1,"你选择的收货地址暂不支持配送，请选择其他收货地址");
					}
					//取得相关数据,用于设置order entity
					Map<String, Object> addressDubboResultMap=(Map<String, Object>) addressDubboResult.getData();
					if(addressDubboResultMap.containsKey("msg")){
						String msg =  addressDubboResultMap.get("msg").toString();
						if(!msg.equals("0")){
							logger.info("区域地址："+addressId+"，基础数据返回信息："+addressDubboResultMap.get("msg").toString());
							throw new ServiceException(1,addressDubboResultMap.get("msg").toString());
						}
					}
					addressDubboResultMap.put("memberAddress", memberAddress);
				} catch (Exception e) {
					// TODO Auto-generated catch block					
					throw new ServiceException(1,"你选择的收货地址暂不支持配送，请选择其他收货地址");
				}
				//调试阶段禁用redis
				//redisUtil.set(key, addressDubboResult, new Long(60));//时间单位秒
				}
				return (Map<String, Object>) addressDubboResult.getData();
	}
	private PartnerServiceStation checkPartnerServiceStation(String partnerId){
		PartnerServiceStation partnerServiceStation = findPartnerServiceStationByPartnerId(partnerId);
		if(null!=partnerServiceStation){
			logger.info("【"+partnerId+"】=====合伙人关联配送站====="+JsonMapper.toJsonString(partnerServiceStation));
			return partnerServiceStation;
		}else{
			logger.error("【"+partnerId+"】合伙人关联配送站为空");
			throw new ServiceException(1,"该地址未关联合伙人配送站，如有需要请联系我们，谢谢");
		}
	}
	/**isNew  : 当前下单会员是否新用户， 0否1是*/
	private void checkCommodity(OmsOrder omsOrder,String isNew){
		StopWatch sw_checkCommodity = new StopWatch("sw_checkCommodity");
		logger.info("当前会员为" + ("1".equals(isNew)?"新用户":"老用户"));
		sw_checkCommodity.start("sw_placeAnOrder_checkCommodity==>>校验是否是含有新用户商品，及购买合法性");
		List<OmsOrderdetail> omsOrderdetailList =  omsOrder.getDetailList();
		Map<String,Long> checkGoodsVoMap = new HashMap<String,Long>();
		for (OmsOrderdetail omsOrderdetail : omsOrderdetailList) {
			//验证新用户购买
			if(BuyTypeEnum.NEWUSER_VIP.equals(omsOrderdetail.getBuyType())){
				if(isNew.equals("0")){
				logger.info("验证购买不通过，"+omsOrderdetail.getCommodityTitle()+"类型为新用户购买，但当前会员不是新用户！");
				throw new ServiceException(1,omsOrderdetail.getCommodityTitle()+"为新用户专享商品，您无法购买！");
				}
				checkAreadyHadNewUserEnjoyOrder(omsOrder.getMemberId());
			}
			checkGoodsVoMap.put(omsOrderdetail.getSkuCode(), new Long(omsOrderdetail.getBuyNum()));
		}
		sw_checkCommodity.stop();
		sw_checkCommodity.start("sw_placeAnOrder_checkCommodity==>>校验商品库存，及购买合法性");
		ResultDto result = null;
		try {
			//调用验证接口
			logger.info("OmsOrderFormServiceImpl==>>placeAnOrder==》》checkCommodity==》》【"+omsOrder.getOrderNo()+"】开始调用stockManagerApiService.verifyOverbooking("+JsonMapper.toJsonString(checkGoodsVoMap)+", "+omsOrder.getRegionId()+")");
			List<OrderParam>  orderParamList = omsOrderService.initOrderParamInfo(omsOrderdetailList);
			result = orderProcessManagerApiService.verifyOverbooking(orderParamList, omsOrder.getRegionId());
			//result = memberApiService.getByIdMember(uId);
		} catch (Exception e) {
			logger.error("AdvertApiConsumer-checkCommodity=》 " , e);
			throw new ServiceException(1,"网络超时");
		}
		logger.info("app-client-OmsOderServiceImpl==》》placeAnOrder==》》checkCommodity==》》【"+omsOrder.getOrderNo()+"】开始调用result = stockManagerApiService.verifyOverbooking: "+result==null?"null":JsonMapper.toJsonString(result));
		if(result.getCode().equals(ResultDto.OK_CODE) ){
		}else{
			logger.error("app-client-OmsOderServiceImpl-checkCommodity=》" + result.getMessage());
			throw new ServiceException(1,result.getMessage());
		}
		sw_checkCommodity.stop();
		logger.error("【"+omsOrder.getOrderNo()+"】sw_placeAnOrder_checkCommodity==>>校验订单商品信息执行时间"+sw_checkCommodity.prettyPrint());
	}
	

	//验证 用户是否存在 商品处于待付款状态的商品 若存在则拦截下单
	private  void checkAreadyHadNewUserEnjoyOrder(String memberId){
		ResultDto result = null;
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("order_memberId", memberId);
		params.put("order_delFlag","0");
		params.put("order_status","0");
		params.put("buyType",BuyTypeEnum.NEWUSER_VIP);
		
		try {
		result = orderApiService.selectBuyCount(params);
		} catch (Exception e) {
			logger.error("app-client--checkAreadyHadNewUserEnjoyOrder=》 " , e);
		}
		if(result.getCode().equals(ResultDto.OK_CODE) ){
			Integer hadCount = (Integer) result.getData(); 
			if(null!=hadCount&&hadCount>0){
				throw new ServiceException(1,"新用户专享优惠商品，每人仅可购买1次，您当前有新用户专享商品处在待付款状态，暂时无法再次购买。");	
			}
		}
	}
	private OmsOrder  checkPrms(OmsOrder omsOrder){
		omsOrder = this.checkGifts(omsOrder);
		ResultDto result = null;
		try {
			//调用验证接口
			result = activityOrderApiService.checkOrder(omsOrder);
			if( null == result){
				logger.error("计算促销信息无返回值===>>>");
				return null;
			}
		} catch (Exception e) {
			logger.error("activityOrderService-getOmsOrder=》 " ,e);
//			List<OmsOrderdetail> detailList=omsOrder.getDetailList();//订单详情
//			backRedisUtilBuyNum(omsOrder, detailList);
			throw new ServiceException(1,"网络超时");
		}
		if(result.getCode().equals(ResultDto.OK_CODE) ){			
			return  (OmsOrder) result.getData();
		}else{
			logger.error("activityOrderService-getOmsOrder=》" + result.getMessage());
//			List<OmsOrderdetail> detailList=omsOrder.getDetailList();//订单详情
//			backRedisUtilBuyNum(omsOrder, detailList);
			throw new ServiceException(1,result.getMessage());
		}
		
	}
	//校验买赠计算
	private OmsOrder checkGifts(OmsOrder omsOrder){
		Map<String, Object> dubboParams = new HashMap<String, Object>();
		dubboParams.put("order",omsOrder);
		ResultDto result = null;
		try {
			//调用验证接口
			result = activityGiveApiService.checkGiveOmsOrder(dubboParams);
			if( null == result){
				logger.error("计算促销信息无返回值===>>>");
				return null;
			}
		} catch (Exception e) {
			logger.error("activityGiveApiService-checkGiveOmsOrder=》 " ,e);
			throw new ServiceException(1,"网络超时");
		}
		if(result.getCode().equals(ResultDto.OK_CODE) ){
			return  (OmsOrder) result.getData();
		}else{
			logger.error("activityGiveApiService-checkGiveOmsOrder=》" + result.getMessage());
			throw new ServiceException(1,result.getMessage());
		}
	}
	//初始化订单信息
	private  OmsOrder initOrderInfo(Member member,MemberAddress memberAddress,Address address,Partner partner,String isInvoice,String sysType,Warehouse aeraWarehouse,PartnerServiceStation partnerServiceStation){
		StopWatch sw_initOrderInfo = new StopWatch("sw_initOrderInfo");
		sw_initOrderInfo.start("初始化订单关联信息信息");
		String memberName = StringUtil.isNotNull(member.getName())?member.getName():(StringUtil.isNotNull(member.getNickName())?member.getNickName():member.getPhone());
		String memberId = member.getId();
		String memberPhone = member.getPhone();
		String consignName = memberAddress.getConsignee();//收货人
		String consignPhone = memberAddress.getPhone();//联系电话
		String addressInfo = memberAddress.getFullAddress();//详细收货地址
		String regionId = memberAddress.getRegionId();//地区Id(选择地址的最低级地址id).
		String regionName = memberAddress.getRegionName();
		String detailedAddress = memberAddress.getDetailedAddress();
		String warehouseCode =address.getWarehouseCode();//仓库Code
		String partnerId =partner.getId();//合伙人ID
		String partnerCode =  partner.getPartnerCode();
		String sendPerson = partner.getId();//配送人 ps:配送人即合伙人
		String sendPersonPhone =  partner.getMobilePhone();
		String sendPersonName=partner.getName();//配送人姓名
		String servicePoint= getStr(partner.getAddressName())+getStr(partner.getAddressDeal()) ;//配送站点
		BigDecimal sendCost = new BigDecimal(0D);//配送费用
		BigDecimal favorablePrice = new BigDecimal(0D);//优惠金额
		OmsOrder omsOrder = new OmsOrder();
		omsOrder.setId(UUIDGenerator.getUUID());
		omsOrder.setCreateDate(new Date());//创建下单日期
		omsOrder.setLastUpdateDate(new Date());//最后更新日期
		omsOrder.setStatus("0");//待付款状态
		omsOrder.setPartnerId(partnerId);
		omsOrder.setMemberId(member.getId());
		omsOrder.setPartnerCode(partnerCode);
		omsOrder.setMemberId(memberId);
		omsOrder.setMemberName(memberName);
		omsOrder.setMemberPhone(memberPhone);
		omsOrder.setSendCost(sendCost);
		omsOrder.setFavorablePrice(favorablePrice);
		//omsOrder.setFavorablePrice(favorablePrice);
		//omsOrder.setActualPrice(actualPrice);
		//omsOrder.setPreSendCommodityTime(preSendCommodityTime);
		//omsOrder.setWarehouseCode(warehouseCode);//仓库编码
		omsOrder.setRegionId(regionId);//区域id
		omsOrder.setRegionName(regionName);//区域名称
		omsOrder.setDetailedAddress(detailedAddress);//详细地址
		//配送相关信息Start
		omsOrder.setSendPerson(sendPerson);//配送人信息
		omsOrder.setSendPersonName(sendPersonName);//配送人姓名
		omsOrder.setServicePoint(servicePoint);//配送地址（即合伙人地址）
		omsOrder.setSendPersonPhone(sendPersonPhone);//配送人电话
		//配送相关信息End
		omsOrder.setAddressInfo(addressInfo);
		omsOrder.setConsignPhone(consignPhone);
		omsOrder.setConsignName(consignName);
		omsOrder.setIsInvoice(isInvoice);
		omsOrder.setOrderSource(sysType);//来源：下单设备
		//订单分配错误标示
		omsOrder.setAllocationError(Constant.NO);//默认分配无错
		omsOrder.setOrderType(OrderTypeEnum.COMMON_ORDER);
		//初始化收货地址县仓仓库信息
		if(aeraWarehouse!=null){
		omsOrder.setCountyStoreId(aeraWarehouse.getId());
		omsOrder.setCountyStoreCode(aeraWarehouse.getCode());
		omsOrder.setCountyStoreName(aeraWarehouse.getName());
		}
		//初始化分销站点信息
		//初始化分销站点信息
		omsOrder.setServiceStationCode(partnerServiceStation.getCode());
		omsOrder.setServiceStationId(partnerServiceStation.getId());
		omsOrder.setServiceStationName(partnerServiceStation.getName());
		sw_initOrderInfo.stop();
		long t=System.currentTimeMillis();
		sw_initOrderInfo.start("getOrderNO:获取订单编号");
		long t1=System.currentTimeMillis();
		omsOrder.setOrderNo(this.getOrderNO());
		t1=System.currentTimeMillis()-t1;
		logger.error("getOrderNO:获取订单编号System.currentTimeMillis()方式统计 inner:"+omsOrder.getOrderNo()+" take 【"+t1+"】ms");
		sw_initOrderInfo.stop();;
		t=System.currentTimeMillis()-t;
		logger.error("getOrderNO:获取订单编号System.currentTimeMillis()方式统计 outer:"+omsOrder.getOrderNo()+" take 【"+t+"】ms");
		logger.error("初始化订单信息执行时间【"+omsOrder.getOrderNo()+"】"+sw_initOrderInfo.prettyPrint());
		return omsOrder;
	}

	public static String getStr(String str){
		return StringUtil.isEmpty(str)?"":str;
	}
	public String code(){
		long t=System.currentTimeMillis();
		String code = codeGenerator.generate("O2O_", "TIMESTAMP", 3);
		if(code!=null){
			code = code.substring(4);
		}
		t=System.currentTimeMillis()-t;
		logger.error("generate code :"+code+" take "+t+"ms");
		return code;
	}
	//初始化订单明细信息
	private void initOmsOrderdetail(List<GoodsVo> goodsList,OmsOrder omsOrder,Warehouse aeraWarehouse,Warehouse centerWarehouse){
		List<OmsOrderdetail> omsOrderdetailList = new ArrayList<>();
		int allCount = 0 ;//总购买数量  ;计算方式:所有商品购买数量之和
		BigDecimal totalPrice = new BigDecimal(0D);//订单总金额
		Map<String,String> giftLabelMap = new HashMap<String,String>();
		PartnerServiceStation partnerServiceStation = omsOrderService.getServiceStationInfoByMeberPhone(omsOrder.getMemberPhone());
			for (GoodsVo goods : goodsList) {
				OmsOrderdetail omsOrderdetail = new OmsOrderdetail();
				//检查所提交必须数据
				if ( StringUtil.isEmpty(goods.getId()) || goods.getBuyType()==null || goods.getCount() ==0 ) {
					throw new ServiceException(1,"商品数据有误,请重新提交!");
				}
				ResultDto rsultCommoditySkuDubboDto  = null;
				
				rsultCommoditySkuDubboDto = orderApiService.getOrderCommoditySkuById(goods.getId());
				if(!rsultCommoditySkuDubboDto.getCode().equals(ResultDto.OK_CODE)){
					logger.info("下单接口，校验商品，skuId:"+goods.getId()+"调用接口失败");
					throw new ServiceException(1,"无法找获取对应商品数据");
				}
				CommoditySku commoditySku =(CommoditySku) rsultCommoditySkuDubboDto.getData();
				
				if(!StringUtil.isNotNull(commoditySku.getCommodity())){
					throw new ServiceException(1,"抱歉,sku【"+commoditySku.getSkuCode()+"】无法获取商品，无法完成购买");
				}
				Commodity commodity = commoditySku.getCommodity();
				//买赠时商品明细可以为下架状态
				if(null==goods.getBuyGifts()||!goods.getBuyGifts().equals("2")){
					//======================商品是否下架Start================================== 
				if(!commodity.getStatus().equals("COMMODITY_STATUS_ADDED")){
					logger.info("下单接口，校验商品，code:"+commodity.getCode()+"已下架："+commodity.getStatus());
					throw new ServiceException(1,"抱歉,商品【"+commodity.getTitle()+"】已下架，无法完成购买");
				}
				}
				//======================商品是否下架E================================== 
				//======================商品相关信息Start================================== 
				String commodityTitle = commodity.getTitle();//商品标题  sku无该字段 不存储标题
				String commodityNo = commodity.getCode();
				String commodityBarcode = commodity.getBarCode();
				String commodityImage = commodity.getThumbnail();//商品图片 sku无该字段 故取主商品图标
				String skuBarcode = commoditySku.getBarcode();//sku条形码
				String commoditySpecifications = commodity.getSpecification();//规格
				String commodityUnit = commodity.getUnitName();//单位
				String commodityAttributeValues = commoditySku.getCommodityAttributeValues();//销售属性
				String skuId = commoditySku.getId();
			    String skuCode  = commoditySku.getSkuCode();
			    String skuImage = commoditySku.getSmallImage();//sku展示图
			    String virtualGdId = commodity.getCouponId();//虚拟商品关联id
				String virtualGdCode = commodity.getCouponCode();//虚拟商品关联code
				String virtualGdName = commodity.getCouponName();//虚拟商品关联name
				
				//======================商品相关信息End================================== 	
				//String activityId = null; //绑定活动数据ID
				String activityCommodityItemId = null;//活动商品详情id
				String giftCommodityItemId = null;
				if(StringUtil.isNotNull(goods.getValue())){
					activityCommodityItemId = goods.getValue();
				}
				if(null!=goods.getGiftCommodityItemId()){
					giftCommodityItemId = goods.getGiftCommodityItemId();
				}
				BigDecimal salePrice = commoditySku.getFavourablePrice();//商品售卖价格(优惠价)
				if(null!=partnerServiceStation){//若不服务站id不能为空 则该用户为服务站合伙人则用批发价
					salePrice = commoditySku.getTradePriceSku();
					omsOrder.setIsPartnerOrder("1");
				}
				if(StringUtil.isNotNull(goods.getSalePrice())){
					//导入成单使用
					salePrice = goods.getSalePrice();
				}
				BigDecimal price = commoditySku.getPrice();//sku商品单价
				BigDecimal actPayAmount = salePrice.multiply(new BigDecimal(goods.getCount()));//实际支付金额
				BigDecimal tradePriceSku = commoditySku.getTradePriceSku();
				omsOrderdetail.setId(UUIDGenerator.getUUID());
				omsOrderdetail.setBuyNum(goods.getCount());//购买数量
				omsOrderdetail.setPifaPrice(tradePriceSku);
				omsOrderdetail.setWholeSaleCount(goods.getWholeSaleCount());//统一商品下面批发sku购买数量总和 该字段在计算批发价格时使用
				omsOrderdetail.setCommodityPrice(price);//sku商品单价
				logger.info("下单接口，校验商品，获取sku优惠价格:【"+commoditySku.getSkuCode()+"】["+salePrice+"]");
				omsOrderdetail.setActSalePrice(salePrice);//实际售卖价格（优惠价）
				omsOrderdetail.setCommodityUnit(commodityUnit);//单位
				omsOrderdetail.setCommodityImage((skuImage==null||skuImage.trim().equals(""))?commodityImage:skuImage);//商品图标
				omsOrderdetail.setCommodityTitle(commodityTitle);//商品标题
				omsOrderdetail.setCommodityBarcode(skuBarcode);//商品sku条形码
				omsOrderdetail.setCommodityNo(commodityNo);//商品编码
				omsOrderdetail.setSkuId(skuId);//skuId
				omsOrderdetail.setSkuCode(skuCode);//sku编码
				//omsOrderdetail.setActivityId(activityId);//活动id
				logger.info("活动商品详情id："+activityCommodityItemId+"======"+goods.getValue());
				omsOrderdetail.setActivityCommodityItemId(activityCommodityItemId);//活动商品详情id
				omsOrderdetail.setGiftCommodityItemId(giftCommodityItemId);//买赠商品详情id
				omsOrderdetail.setBuyType(goods.getBuyType());//购买类型
				omsOrderdetail.setCommodityAttributeValues(commodityAttributeValues);//sku属性说明
				omsOrderdetail.setCommoditySpecifications(commoditySpecifications);//规格
				omsOrderdetail.setOrderId(omsOrder.getId());//订单id
				omsOrderdetail.setOrderNo(omsOrder.getOrderNo());//订单编号
				omsOrderdetail.setCommodity(commodity);//商品信息
				omsOrderdetail.setCommoditySku(commoditySku);//商品sku
				omsOrderdetail.setActPayAmount(actPayAmount);//实际支付金额
				
				omsOrderdetail.setMemberId(omsOrder.getMemberId());
				//初始化仓库信息
				if(StringUtil.isNotNull(commodity.getAreaCategory())&&commodity.getAreaCategory().equals("0")){
					//区域性商品 使用区域仓库
					initWarehouse(omsOrderdetail,aeraWarehouse);
					logger.info("OmsOrderFormServiceImpl==>>【"+omsOrder.getOrderNo()+"】initOmsOrderdetail==》》获得sku【"+omsOrderdetail.getSkuCode()+"】的商品的区域性商品标示为【"+commodity.getAreaCategory()+"】,区域性商品，则该商品在仓库WarehouseCode：【"+omsOrderdetail.getWarehouseCode()+"】");
				}else{
					//非区域性商品 使用中央仓库 
					initWarehouse(omsOrderdetail,centerWarehouse);
					logger.info("OmsOrderFormServiceImpl==>>【"+omsOrder.getOrderNo()+"】initOmsOrderdetail==》》获得sku【"+omsOrderdetail.getSkuCode()+"】的商品的区域性商品标示为【"+(commodity.getAreaCategory()==null?"区域标示为空":commodity.getAreaCategory())+"】,非区域区域性商品，则该商品在仓库WarehouseCode：【"+omsOrderdetail.getWarehouseCode()+"】");
				}
				
				logger.info("买赠标识字段："+goods.getBuyGifts()+"买赠仓库标识字段："+goods.getWarehouseAppoint());
				logger.info("买赠商品："+goods.getSku_commodityCode()+"买赠标识："+goods.getBuyGifts());
				//买赠商品信息初始化Start
				if(StringUtils.isNotBlank(goods.getBuyGifts())&& !Constant.NO.equals(goods.getBuyGifts())){
					omsOrderdetail.setBuyGifts(goods.getBuyGifts());
					omsOrderdetail.setLabel(goods.getLabel());
					omsOrderdetail.setWarehouseAppoint(goods.getWarehouseAppoint());
					if(!StringUtil.isNotNull(omsOrderdetail.getWarehouseAppoint())){
						throw new ServiceException(1,"抱歉，商品【"+commodity.getTitle()+commoditySku.getSkuCode()+"】已下架，请返回后重试");
					}
					//1：收货地址对应的中央仓，2：收货地址对应的县仓,后台在判断库存之前使用
					if(omsOrderdetail.getWarehouseAppoint().equals("1")){
						initWarehouse(omsOrderdetail,centerWarehouse);
					}else{
						initWarehouse(omsOrderdetail,aeraWarehouse);
					}
					//设置买赠方式
					omsOrderdetail.setPromotions("1");
					//设置赠品支付价格
					if(omsOrderdetail.getBuyGifts().equals("2")){
						omsOrderdetail.setActSalePrice(new BigDecimal(0D));
						omsOrderdetail.setCommodityPrice(new BigDecimal(0D));
						omsOrderdetail.setActPayAmount(new BigDecimal(0D));
						actPayAmount = new BigDecimal(0D);
					}else if(omsOrderdetail.getBuyGifts().equals("1")){
						giftLabelMap.put(omsOrderdetail.getLabel(), omsOrderdetail.getId());
					}
				}
				
				//虚拟商品信息初始化
				if(StringUtil.isNotNull(virtualGdId)){
					omsOrderdetail.setVirtualGdId(virtualGdId);
				}
				if(StringUtil.isNotNull(virtualGdId)){
					omsOrderdetail.setVirtualGdCode(virtualGdCode);
				}
				if(StringUtil.isNotNull(virtualGdName)){
					omsOrderdetail.setVirtualGdName(virtualGdName);
				}

				//买赠商品信息初始化End
				omsOrderdetailList.add(omsOrderdetail);
				allCount += goods.getCount(); //累加购买数量
				logger.info("下单接口，校验商品，获取sku支付金额:【"+commoditySku.getSkuCode()+"】数量：["+omsOrderdetail.getBuyNum()+"],实际支付金额["+actPayAmount+"]");
				totalPrice = totalPrice.add(actPayAmount);//累加实付款金额
			}
			omsOrder.setBuyType(goodsList.get(0).getBuyType());//购买类型
			omsOrder.setBuyCount(allCount);//总购买数量
			omsOrder.setTotalPrice(totalPrice);//总金额
			omsOrder.setActualPrice(totalPrice);//总的支付金额
			logger.info("下单接口，校验商品，获取订单支付金额:【"+omsOrder.getOrderNo()+"】金额：["+omsOrder.getActualPrice()+"]");
			//买赠关系构建
			this.initGiftInfo(omsOrderdetailList, giftLabelMap);
			omsOrder.setDetailList(omsOrderdetailList);
			//订单默认将第一个订单明细的 仓库放到订单上面 支付成功之后订单拆分 再将各个明细的仓库信息 放到其订单上
			omsOrder.setStorageId(omsOrderdetailList.get(0).getWarehouseId());
			omsOrder.setStorageName(omsOrderdetailList.get(0).getWarehouseName());
			omsOrder.setWarehouseCode(omsOrderdetailList.get(0).getWarehouseCode());
			//默认获取第一个订单明细的的供应商仓库所在标示
			String isSupplier = omsOrderdetailList.get(0).getCommodity().getWarehouse();
			omsOrder.setIsSupplier(isSupplier);
	}
	/**
	 * 初始化仓库信息
	 * @param omsOrderdetail
	 * @param warehouse
	 * @author yinglong.huang
	 * @email yinglong.huang@ffzxnet.com
	 * @date 2016年9月19日 上午9:21:49
	 * @version V1.0
	 * @return
	 */
	private void initWarehouse(OmsOrderdetail omsOrderdetail, Warehouse warehouse) {
		omsOrderdetail.setWarehouseCode(warehouse.getCode());
		omsOrderdetail.setWarehouseId(warehouse.getId());
		omsOrderdetail.setWarehouseName(warehouse.getName());
	}
	/**
	 * 构建订单明细的买赠关系
	 * @param omsOrderdetailList
	 * @param giftLabelMap
	 * @author yinglong.huang
	 * @email yinglong.huang@ffzxnet.com
	 * @date 2016年9月19日 上午9:46:53
	 * @version V1.0
	 * @return
	 */
	private void initGiftInfo(List<OmsOrderdetail> omsOrderdetailList, Map<String, String> giftLabelMap) {
		if (!giftLabelMap.isEmpty() && omsOrderdetailList.size() > 1) {
			for (OmsOrderdetail omsOrderdetail : omsOrderdetailList) {
				if (null != omsOrderdetail.getBuyGifts() && omsOrderdetail.getBuyGifts().equals("2")) {
					omsOrderdetail.setOrderdetailId(giftLabelMap.get(omsOrderdetail.getLabel()));
				}
			}
		}
	}
	//初始化下单自动关闭时间
	private void  initOverTime(OmsOrder order){
		Date overTime = new Date();
		String key = "ORDER_OUTTIME_LIMIT";
		if(order.getBuyType().equals(BuyTypeEnum.PANIC_BUY)){
			key="SNAP_OUTTIME_LIMIT";
		}
		String value = getParamValue(key);
		logger.info("OmsOrderFormServiceImpl==>>"+key+":【"+value+"】");
		overTime = this.getLimitDate(order.getCreateDate(), value);
		logger.info("OmsOrderFormServiceImpl==>>【"+order.getOrderNo()+"】initOverTime==》》下单时间：【"+DateUtil.formatDateTime(order.getCreateDate())+"】,获取订单超时时间【"+DateUtil.formatDateTime(overTime)+"】");
		order.setOverTime(overTime);
	}
	//初始化仓库信息
	private void initWarehouseInfo(OmsOrder omsOrder,Warehouse arearWarehouse,Warehouse centerWarehouse){
		List<OmsOrderdetail> omsOrderdetailList =  omsOrder.getDetailList();
		for (OmsOrderdetail omsOrderdetail : omsOrderdetailList) {
			Commodity commodity =  omsOrderdetail.getCommodity();
			//仓库分两种信息  区域性商品在本地仓  非区域性商品在中央仓
			if(commodity.getAreaCategory().equals("1")){//区域性商品   将仓库信息设置为  中央仓
				omsOrderdetail.setWarehouseId(centerWarehouse.getId());
				omsOrderdetail.setWarehouseCode(centerWarehouse.getCode());
				omsOrderdetail.setWarehouseName(centerWarehouse.getName());
			}else{//非区域性商品
				omsOrderdetail.setWarehouseId(arearWarehouse.getId());
				omsOrderdetail.setWarehouseCode(arearWarehouse.getCode());
				omsOrderdetail.setWarehouseName(arearWarehouse.getName());
			}
		}
		//订单初始化仓库信息Start
		String  warehouseCode = omsOrderdetailList.get(0).getWarehouseCode();
		String  warehouseId = omsOrderdetailList.get(0).getWarehouseId();
		String  warehouseName = omsOrderdetailList.get(0).getWarehouseName();
		omsOrder.setStorageId(warehouseId);
		omsOrder.setWarehouseCode(omsOrderdetailList.get(0).getWarehouseCode());
		omsOrder.setStorageName(warehouseName);
		omsOrder.setDetailList(omsOrderdetailList);
	}
	@Autowired 
	CodeGenerator codeGenerator;
 	
	//后去商品编码
	private String getOrderNO(){
		logger.info("OmsOrderFormServiceImpl.getOrderNO ===>>>begin");
		String  orderNo = null;
		try{
			orderNo = codeGenerator.generate("O2O_", "TIMESTAMP", 3);
			if(orderNo!=null){
				orderNo = orderNo.substring(4);
			}
		}catch(Exception e){
			logger.info("codeGenerator.generate===>>生成订单号异常>>",e);
			try{
			ResultDto orderCodeDubboResult = codeRuleApiService.getCodeRule("order", "OMS_ORDER_NO");
			if(!orderCodeDubboResult.getCode().equals(ResultDto.OK_CODE )){//若订单号生成异常  此处可临时生成单号
				logger.info("调用codeRuleApiService.getCodeRule===>>生成订单号异常>>"+orderCodeDubboResult.getMessage());
			}
			if(StringUtil.isNotNull(orderCodeDubboResult.getData())){
				orderNo =  orderCodeDubboResult.getData().toString();
			}
			}catch(Exception e1){
				logger.info("codeGenerator.generate===>>生成订单号异常>>",e1);
			}
		}
		if(orderNo==null){
			orderNo = "O2O"+getRandom(3)+getRandom(2)+getRandom(2)+System.currentTimeMillis();
			logger.info("生成订单号异常,已启动临时单号7位随机数+时间戳,生成临时订单【"+orderNo+"】");
		}
		return orderNo;
	}
	private String getRandom(int n){
		Random random = new Random();
		String randomNum = "";
		for (int i = 0; i < n; i++) {
			int temp = random.nextInt(10);
			if(temp==0){temp=1;} //不能累加0，因为接口要求返回int数据，最后转换的时候返回app端0不会显示
			randomNum = randomNum + temp;
		}
		return randomNum;
	}
	//获取超时时间配置量
    private String getParamValue(String key){
    	String value = this.getDictByLabel(key); 
    	if(!StringUtil.isNotNull(value)){
    	if(key=="SNAP_OUTTIME_LIMIT"){//抢购商品限时 支付时间(单位:小时)
    		value="0.5";//默认半小时
    	}if(key=="ORDER_OUTTIME_LIMIT"){//普通购买 限时支付时间 (单位:小时)
    		value="2";////默认两小时
    	}}
    	return value;
    }
	private String  getDictByLabel(String label) throws ServiceException {
		logger.debug("OmsOrderFormServiceImpl-getDictByType=》数据字典dubbo调用-列表 - BEGIN");

	    String value = null;
		//从缓存中获取
		String key  = RedisPrefix.DATA_DICT +label;
		value = (String) redisUtil.get(key);
		if(null != value){
			return value;
		}
		 
		ResultDto result = dictApiService.getDictByParams("OMSORDERCONFIG", label);;
		if(result.getCode().equals(ResultDto.OK_CODE) ){
			value =  (String) result.getData();
			redisUtil.set(key, value,60L);
		}else{
			return null;
		}
		logger.debug("OmsOrderFormServiceImpl-getDictByType=》数据字典dubbo调用-列表 " + result.getMessage());
		logger.debug("OmsOrderFormServiceImpl-getDictByType=》数据字典dubbo调用-列表 - END");
		return value;
	}

    /**
	 * 根据
	 * @param limit
	 * @return
	 */
	public  Date getLimitDate(Date date,String limit){
		BigDecimal target=new BigDecimal(limit);
		return DateUtil.getDateAddMinute(date, getFormatData(target).multiply(new BigDecimal("60")).intValue());
	}
	//格式化 1位小数
		public static BigDecimal getFormatData(BigDecimal target){
			if(null != target){
				DecimalFormat df = new DecimalFormat("#0.00");  
		        return new BigDecimal(df.format(target)); 
			}
			return target;
		}

	/***
	 * 
	 * @return
	 * @author yinglong.huang
	 * @email yinglong.huang@ffzxnet.com
	 * @date 2016年10月26日 下午6:33:36
	 * @version V1.0
	 * @return 
	 */
	@Override
	public CrudMapper init() {
		// TODO Auto-generated method stub
		return null;
	}

	
	/***
	 * 导入成单
	 * @param sysType
	 * @param uId
	 * @param couponId
	 * @param addressId
	 * @param isInvoice
	 * @param goodsListStr
	 * @return
	 * @throws ServiceException
	 * @author yinglong.huang
	 * @email yinglong.huang@ffzxnet.com
	 * @date 2016年10月26日 下午6:31:26
	 * @version V1.0
	 * @return 
	 */
	@Override
	public OmsOrder importBuildOrder(String uId,String addressId,List<GoodsVo> goodsList
		 ) throws ServiceException {
		
		StopWatch sw_placeAnOrder = new StopWatch("sw_placeAnOrder");
		// TODO Auto-generated method stub
		logger.info("OmsOrderFormServiceImpl==>>placeAnOrder==》》校验用户:uId："+uId);
		logger.info("OmsOrderFormServiceImpl==>>placeAnOrder==》》校验用户:uId："+uId);
		//=============================校验下单用户Start===========================
		sw_placeAnOrder.start("sw_placeAnOrder_checkMember:校验用户"); 
		Member member = checkMember(uId); //新用户，1是0否
		sw_placeAnOrder.stop();
		//=============================校验下单用户End=============================
		
		logger.info("OmsOrderFormServiceImpl==>>placeAnOrder==》》校验收货地址:addressId："+addressId);
		//=============================校验下单收货地址Start===========================
		sw_placeAnOrder.start("sw_placeAnOrder_checkAddress:检验收货地址"); 
		Map<String,Object> addressInfoMap = checkAddress(addressId);
		//会员关联地址信息
		MemberAddress memberAddress =(MemberAddress) addressInfoMap.get("memberAddress"); 
		//配送地址信息
		Address address = (Address) addressInfoMap.get("address");
		//合伙人信息
		Partner partner = (Partner) addressInfoMap.get("partner");
		if(null==partner){
			logger.error("OmsOrderFormServiceImpl==>>placeAnOrder==》》无法获取合伙人");
			throw new ServiceException(1,"你选择的收货地址暂不支持配送，请选择其他收货地址");
		}
		PartnerServiceStation partnerServiceStation= checkPartnerServiceStation(partner.getId());
		if(!addressInfoMap.containsKey("warehousecode")){
			logger.error("OmsOrderFormServiceImpl==>>placeAnOrder==》》无法获取中央仓");
			throw new ServiceException(1,"你选择的收货地址暂不支持配送，请选择其他收货地址"); 
		 }
		//区域仓库 
		Warehouse aeraWarehouse = (Warehouse) addressInfoMap.get("warehousecode");
		if(!addressInfoMap.containsKey("warehouse")){
			logger.error("OmsOrderFormServiceImpl==>>placeAnOrder==》》该地址无法获取区域仓库");
			throw new ServiceException(1,"你选择的收货地址暂不支持配送，请选择其他收货地址"); 
		 }
		//中央仓库
		Warehouse centerWarehouse = (Warehouse) addressInfoMap.get("warehouse");
		sw_placeAnOrder.stop();
		//=============================校验下单收货地址End=============================
		logger.info("OmsOrderFormServiceImpl==>>placeAnOrder==》》初始化订单信息");
		sw_placeAnOrder.start("sw_placeAnOrder_initOrderInfo:初始化订单信息");
		//默认不需要发票
		OmsOrder omsOrder = this.initOrderInfo(member, memberAddress, address, partner, "0", null,aeraWarehouse,partnerServiceStation);
		//初始化仓库信息
		//中央仓
		omsOrder.setCenterWarehouse(centerWarehouse);
		//县仓
		omsOrder.setAreaWarehouse(aeraWarehouse);
		sw_placeAnOrder.stop();
		//记录该单的 关联数据信息
		logger.info("OmsOrderFormServiceImpl==>>placeAnOrder==》》【"+omsOrder.getOrderNo()+"】该订单的收货地址【memberAddress】信息:"+JsonMapper.toJsonString(memberAddress));
		logger.info("OmsOrderFormServiceImpl==>>placeAnOrder==》》【"+omsOrder.getOrderNo()+"】该订单的关联的配送地址【address】信息:"+JsonMapper.toJsonString(address));
		logger.info("OmsOrderFormServiceImpl==>>placeAnOrder==》》【"+omsOrder.getOrderNo()+"】该订单的合伙人【partner】信息:"+JsonMapper.toJsonString(partner));
		logger.info("OmsOrderFormServiceImpl==>>placeAnOrder==》》【"+omsOrder.getOrderNo()+"】该订单的区域仓库【aeraWarehouse】信息:"+JsonMapper.toJsonString(aeraWarehouse));
		logger.info("OmsOrderFormServiceImpl==>>placeAnOrder==》》【"+omsOrder.getOrderNo()+"】该订单的中央仓库【centerWarehouse】信息:"+JsonMapper.toJsonString(centerWarehouse));
		
		//校验新用户下单条件	
		logger.info("OmsOrderFormServiceImpl==>>placeAnOrder==》》【"+omsOrder.getOrderNo()+"】初始化订单明细信息,原始订单明细数据转为list数据完成:goodsList"+JsonMapper.toJsonString(goodsList));
		logger.info("OmsOrderFormServiceImpl==>>placeAnOrder==》》【"+omsOrder.getOrderNo()+"】开始生成订单明细,执行订单明细方法initOmsOrderdetail()");
		sw_placeAnOrder.start("sw_placeAnOrder_initOmsOrderdetail:初始化订单明细");
		this.initOmsOrderdetail(goodsList, omsOrder,aeraWarehouse,centerWarehouse);
		sw_placeAnOrder.stop();
//		logger.info("OmsOrderFormServiceImpl==>>placeAnOrder==》》【"+omsOrder.getOrderNo()+"】原始订单数据构建完成："+JsonMapper.toJsonString(omsOrder));
		//验证商品购买可行性  
		logger.info("OmsOrderFormServiceImpl==>>placeAnOrder==》》检验商品购买合法性");
		sw_placeAnOrder.start("sw_placeAnOrder_checkCommodity:检验商品购买合法性");
		this.checkCommodity(omsOrder,member.getIsNew());
		sw_placeAnOrder.stop();
		logger.info("OmsOrderFormServiceImpl==>>placeAnOrder==》》【"+omsOrder.getOrderNo()+"】prms系统对订单计算优惠方案完成"+JsonMapper.toJsonString(omsOrder));
		ResultDto result = null;
		logger.info("OmsOrderFormServiceImpl==>>placeAnOrder==》》【"+omsOrder.getOrderNo()+"】计算下单超时时间");
		initOverTime(omsOrder);
		logger.info("OmsOrderFormServiceImpl==>>placeAnOrder==》》【"+omsOrder.getOrderNo()+"】开始调用orderApiService.buildOrder()并持久化订单");
		
		try{
		sw_placeAnOrder.start("sw_placeAnOrder_buildOrder:成单构建");
	    result = orderApiService.buildOrder(omsOrder);
	    sw_placeAnOrder.stop();
		}catch(Exception e){
			logger.error("APP-client-OmsOrderServiceImpl-placeAnOrder=》 " , e);
			//下单失败回滚购买量缓存
			logger.error("回滚缓存 " , e);
			List<OmsOrderdetail> detailList=omsOrder.getDetailList();//订单详情
			backRedisUtilBuyNum(omsOrder, detailList);
			throw new ServiceException(1,"网络超时，请稍后重试"); 
		}
		 if(!result.getCode().equals(ResultDto.OK_CODE)){
			 logger.error("APP-client-OmsOrderServiceImpl-placeAnOrder=》 " + result.getMessage());
				throw new ServiceException(1,result.getMessage()); 
		 }
		logger.info("OmsOrderFormServiceImpl==>>placeAnOrder==》》【"+omsOrder.getOrderNo()+"】订单已插入到数据库");
		//清空购物车
		logger.info("OmsOrderFormServiceImpl==>>placeAnOrder==》》【"+omsOrder.getOrderNo()+"】清除订单明细所对应的购物车");
		sw_placeAnOrder.start("sw_placeAnOrder_clearShoppingCart:清除购物车相关数据");
		clearShoppingCart(member.getId(),goodsList);
		sw_placeAnOrder.stop();
		logger.error("下单执行时间【"+omsOrder.getOrderNo()+"】"+sw_placeAnOrder.prettyPrint());
		return omsOrder;
	}
	public Partner findPartnerByMemberPhone(String memberPhone){
		try{
			Map<String,Object> params = new HashMap<String,Object>();
			params.put("memberAccount", memberPhone);
			ResultDto resultDto = partnerApiService.getPartnerList(params);
			if(null==resultDto){return null;}
			List<Partner> pList = (List<Partner>) resultDto.getData();
			return pList.isEmpty()?null:pList.get(0);
		}catch(Exception e){
			logger.error("",e);
			throw new ServiceException(1,"网络超时");
		}
	}
	public PartnerServiceStation findPartnerServiceStationByPartnerId(String partnerId){	
		try{
			Map<String,Object> params = new HashMap<String,Object>();
			params.put("partnerId", partnerId);
			ResultDto resultDto = partnerApiServiceStationService.getPartnerApiServiceStationList(params);		
			List<PartnerServiceStation> pssList = (List<PartnerServiceStation>) resultDto.getData();
			return pssList.isEmpty()?null:pssList.get(0);
		}catch(Exception e){
			logger.error("",e);
			throw new ServiceException(1,"网络超时");
		}
	}
}
