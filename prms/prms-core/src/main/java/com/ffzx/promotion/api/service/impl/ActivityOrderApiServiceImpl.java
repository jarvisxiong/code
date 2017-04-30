package com.ffzx.promotion.api.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ffzx.commerce.framework.constant.RedisPrefix;
import com.ffzx.commerce.framework.dao.CrudMapper;
import com.ffzx.commerce.framework.dto.ResultDto;
import com.ffzx.commerce.framework.exception.ServiceException;
import com.ffzx.commerce.framework.service.impl.BaseCrudServiceImpl;
import com.ffzx.commerce.framework.utils.RedisLock;
import com.ffzx.commerce.framework.utils.RedisUtil;
import com.ffzx.commerce.framework.utils.StringUtil;
import com.ffzx.order.api.dto.OmsOrder;
import com.ffzx.order.api.dto.OmsOrderdetail;
import com.ffzx.order.api.enums.BuyTypeEnum;
import com.ffzx.order.api.service.OrderApiService;
import com.ffzx.promotion.api.dto.ActivityCommodity;
import com.ffzx.promotion.api.dto.ActivityCommoditySku;
import com.ffzx.promotion.api.dto.CouponAdmin;
import com.ffzx.promotion.api.dto.CouponAdminCategory;
import com.ffzx.promotion.api.dto.CouponReceive;
import com.ffzx.promotion.api.dto.RedpackageReceive;
import com.ffzx.promotion.api.dto.constant.PrmsConstant;
import com.ffzx.promotion.api.enums.ActivityTypeEnum;
import com.ffzx.promotion.api.service.ActivityManagerApiService;
import com.ffzx.promotion.api.service.ActivityOrderApiService;
import com.ffzx.promotion.api.service.CouponReceiveApiService;
import com.ffzx.promotion.mapper.ActivityCommodityMapper;
import com.ffzx.promotion.mapper.ActivityCommoditySkuMapper;
import com.ffzx.promotion.service.RedpackageReceiveService;

/********
 * 下单活动 验证 计算订单价格
 * 
 * @author lushi.guo
 *
 */
@Service("activityOrderApiService")
public class ActivityOrderApiServiceImpl extends BaseCrudServiceImpl implements ActivityOrderApiService {
	@Resource
	private ActivityCommodityMapper activityCommodityMapper;
	@Autowired
	private CouponReceiveApiService couponReceiveApiService;
	@Autowired
	private ActivityManagerApiService activityManagerApiService;
	@Autowired
	private OrderApiService orderApiService;
	@Autowired
	private ActivityCommoditySkuMapper activityCommoditySkuMapper;
	@Autowired
	private RedpackageReceiveService redpackageReceiveService;
	@Autowired
	private RedisUtil redis;

	@Override
	public CrudMapper init() {

		return activityCommodityMapper;
	}

	@Override
	public ResultDto getOmsOrder(Map<String, Object> params) throws ServiceException {
		ResultDto resDto = null;
		boolean flag = this.isObject(params.get("order"));
		if (flag) {
			OmsOrder order = (OmsOrder) params.get("order");
			List<Map<String, Integer>> redisSuccessMap = new ArrayList<Map<String, Integer>>();
			resDto = isCheckOmsOrder(order, redisSuccessMap);
			if (null == resDto || !resDto.getCode().equals(ResultDto.OK_CODE)) {
				logger.warn("failed when checking id and sku limit. the reason is " + resDto);
				// 验证失败回滚缓存
				if (redisSuccessMap != null && redisSuccessMap.size() != 0) {
					for (Map<String, Integer> redisValue : redisSuccessMap) {
						Iterator<String> it = redisValue.keySet().iterator();
						while (it.hasNext()) {
							logger.error("success get redisValue!");
							Object key = it.next();
							redis.decrease(String.valueOf(key), redisValue.get(key).longValue());
						}
					}
				}
			}
		}
		return resDto;
	}

	/**
	 * 获取批发商品价格区间
	 * 
	 * @param commodityNo
	 * @param wholeSaleCount
	 * @return
	 */
	private BigDecimal getWhosaleCommodityPrice(String commodityNo, Integer wholeSaleCount) {
		ResultDto resDto = this.activityManagerApiService.getWhosaleCommodityPrice(commodityNo, wholeSaleCount);
		if (this.isObject(resDto.getData())) {
			return new BigDecimal(resDto.getData().toString());
		}
		return null;
	}

	/**
	 * 验证是否属于抢购，预售，新用户专享活动
	 * 
	 * @param buyType
	 * @return
	 */
	private boolean isBuyType(String buyType) {
		boolean flag = false;
		if (buyType.equals(BuyTypeEnum.PANIC_BUY.getValue()) || buyType.equals(BuyTypeEnum.PRE_SALE.getValue())
				|| buyType.equals(BuyTypeEnum.NEWUSER_VIP.getValue())) {
			flag = true;
		}
		return flag;
	}

	private List<String> getListStr(String redpackageId){
		List<String> listStr=new ArrayList<String>();
		if(StringUtils.isNotEmpty(redpackageId)){
			for(String id:redpackageId.split(";")){
				if(StringUtils.isNotEmpty(id)){
					listStr.add(id);
				}
			}
		}
		return listStr;
	}
	
	private List<RedpackageReceive> getReceiveList(List<String> idStr){
			Map<String, Object> params=new HashMap<String, Object>();
			params.put("receiveIds", idStr);
			List<RedpackageReceive> list=this.redpackageReceiveService.findByBiz(params);
			return list;
	}
	
	private ResultDto isCheckOmsOrder(OmsOrder order, List<Map<String, Integer>> redisSuccessMap) {
		ResultDto resDto = null;
		try {
			List<OmsOrderdetail> detailList = order.getDetailList();// 订单详情
			if (!StringUtil.isNotNull(detailList)) {
				resDto = new ResultDto(ResultDto.ERROR_CODE, "faile");
				resDto.setMessage("该订单没有详情！！");
				return resDto;
			}
		
			
			String couponId = order.getCouponId();// 优惠券id
			String uid = order.getMemberId();// 用户ID
			BigDecimal totalPrice = new BigDecimal(0);// 订单总金额
			BigDecimal favoutAmount = new BigDecimal(0);// 优惠券金额
			// 计算订单总价格和订单商品详情支付金额
			for (OmsOrderdetail detail : detailList) {
				int buyNum = detail.getBuyNum();// 商品购买数量
				String buyType = detail.getBuyType().getValue();// 购买类型
				String activityCommodityId = detail.getActivityCommodityItemId();
				int wholeSaleCount = detail.getWholeSaleCount();// 批发时商品购买sku数量的总和
				String commodityNo = detail.getCommodityNo();// 商品编号
				String value = detail.getCommodityAttributeValues() == null ? "" : detail.getCommodityAttributeValues();
				// 计算订单明细商品支付金额
				BigDecimal actPayAmount = new BigDecimal(0);
				// 批发商品获取区间价格
				if (buyType.equals(BuyTypeEnum.WHOLESALE_MANAGER.getValue())) {
					BigDecimal activityCommodityPrice = this.getWhosaleCommodityPrice(commodityNo, wholeSaleCount);// 批发商品的实际售价
					if (activityCommodityPrice != null) {
						detail.setActSalePrice(activityCommodityPrice);
					}
					logger.info("批发商品:" + commodityNo + "价格区间:" + activityCommodityPrice);
				}
				if (!buyType.equals(BuyTypeEnum.COMMON_BUY.getValue())) {
					// 非普通活动和批发活动要判断活动限购量
					boolean buyTypeFlag = this.isBuyType(buyType);
					if (buyTypeFlag) {
						logger.info("验证商品:" + commodityNo + "是否可以购买");
						resDto = checkLimit(order, detail, value);
						if (null == resDto || !resDto.getCode().equals(ResultDto.OK_CODE)) {
							logger.warn("failed when checking id and sku limit. the reason is " + resDto);
							return resDto;
						}
						// 活动商品验证成功存入集合留待回滚缓存
						getSuccessMap(order.getMemberId(), redisSuccessMap, detail);
					}
					// 活动标题
					getActivityTitle(detail);
					// 预售商品发货时间
					if (buyType.equals(BuyTypeEnum.PRE_SALE.getValue())) {
						getPreSaleDevliveDate(detail);
					}
					// 活动商品计算价格
					if (buyType.equals(BuyTypeEnum.WHOLESALE_MANAGER.getValue())) {
						if (detail.getFavouredAmount() != null) {
							actPayAmount = detail.getActSalePrice().multiply(new BigDecimal(buyNum)).setScale(2, BigDecimal.ROUND_HALF_UP).subtract(detail.getFavouredAmount());
						} else {
							actPayAmount = detail.getActSalePrice().multiply(new BigDecimal(buyNum)).setScale(2,BigDecimal.ROUND_HALF_UP);
						}
						totalPrice = totalPrice.add(detail.getActSalePrice().multiply(new BigDecimal(buyNum)).setScale(2, BigDecimal.ROUND_HALF_UP));// 计算订单总价格
					} else {
						BigDecimal actPrice = new BigDecimal(0);
						String priceKey=RedisPrefix.ACTIVITY + RedisPrefix.ACTIVITY_PRICE + activityCommodityId + "_"+ detail.getSkuCode() + "price";
						Object pp = (BigDecimal) redis.get(priceKey);// 取出活动商品的实际价格
						logger.info("缓存活动商品价格："+pp);
						if (pp!=null) {
							actPrice = (BigDecimal) redis.get(priceKey);// 取出活动商品的实际价格
						}else{
							actPrice=getActivityActprice(activityCommodityId, detail.getSkuCode());
						}
						BigDecimal actSalePrice = new BigDecimal(0);
						if (actPrice != null && !actPrice.equals(new BigDecimal(0))) {
							actSalePrice = actSalePrice.add(actPrice);
						} else {
							resDto = getActivityCommoditySku(detail, activityCommodityId);
							if (resDto.getData() != null) {
								actSalePrice = getActSalePrice(resDto, detail, activityCommodityId, actSalePrice);
							}
						}
						totalPrice = totalPrice.add(actSalePrice.multiply(new BigDecimal(buyNum)).setScale(2, BigDecimal.ROUND_HALF_UP));// 计算订单总价格
						if (detail.getFavouredAmount() != null) {
							actPayAmount = actSalePrice.multiply(new BigDecimal(buyNum)).setScale(2, BigDecimal.ROUND_HALF_UP).subtract(detail.getFavouredAmount());
						} else {
							actPayAmount = actSalePrice.multiply(new BigDecimal(buyNum)).setScale(2,BigDecimal.ROUND_HALF_UP);
						}
						detail.setActSalePrice(actSalePrice);
					}
					detail.setActPayAmount(actPayAmount);

				} else {
					// 普通商品购买价格计算
					totalPrice = calculCommonPrice(detail, actPayAmount, buyNum, totalPrice);
				}
			}
			// 计算优惠券价格

			if (StringUtil.isNotNull(couponId)) {
				ResultDto res = this.couponReceiveApiService.findCoupon(uid, couponId);
				if (res.getData() != null) {
					CouponReceive receive = (CouponReceive) res.getData();
					if (receive == null) {
						resDto = new ResultDto(ResultDto.ERROR_CODE, "faile");
						resDto.setMessage("该用户没有领取该优惠券！！");
						return resDto;
					}
					// 开始计算订单详情优惠金额

					// 计算优惠券价格
					favoutAmount = getCouponPrice(detailList, receive.getCouponAdmin().getId(), favoutAmount, receive);
					order.setCouponAmount(favoutAmount);
					//优惠券金额
					order.setFavorablePrice(favoutAmount);
					order.setCouponReceiveId(receive.getId());
				} else {
					resDto = new ResultDto(ResultDto.ERROR_CODE, "该优惠券已失效，无法使用");
					return resDto;
				}
			}						
			
			//计算红包金额
			BigDecimal redpackageprice=this.getRedpackageOmsOrder(order);
			order.setTotalRedAmount(redpackageprice);
			// 订单总金额
			order.setTotalPrice(totalPrice);
			//优惠券和红包金额的和
			BigDecimal couponRedpackAmount=favoutAmount.add(redpackageprice);
			if (couponRedpackAmount.equals(new BigDecimal(0))) {
				order.setActualPrice(totalPrice);
			} else {				
				if (totalPrice.compareTo(couponRedpackAmount) > 0) {
					order.setActualPrice(totalPrice.subtract(couponRedpackAmount));
				} else {
					order.setActualPrice(new BigDecimal(0.01).setScale(2, BigDecimal.ROUND_HALF_UP));
				}
			}
			resDto = new ResultDto(ResultDto.OK_CODE, "success");
			resDto.setData(order);

		} catch (Exception e) {
			logger.error("ActivityOrderServiceImpl-getOmsOrder=》", e);
			resDto = new ResultDto(ResultDto.ERROR_CODE, "faile");
			resDto.setData(order);
		}
		return resDto;
	}

	/**
	 * 计算红包金额
	 * @param order
	 * @return
	 */
	private BigDecimal getRedpackageOmsOrder(OmsOrder order){
		String redpackUserIds=order.getRedPacketUseIds();//红包使用ID
		BigDecimal redpackagePrice=new BigDecimal(0);//红包总金额			
		//计算红包金额
		if(StringUtils.isNotEmpty(redpackUserIds)){
			List<String> useIds=this.getListStr(redpackUserIds);//获取使用的红包ID集合
			//获取所有领取记录
			List<RedpackageReceive> list=this.getReceiveList(useIds);
			//先把使用的红包总金额计算
			redpackagePrice=this.getredPackagePrice(list);
			//获取红包ID 和领取ID 以;隔开
			Map<String, Object> mapValue=this.getRedpackValue(list);
			//把使用的红包金额按规则最优分配个商品详情
			boolean flag=this.setRedpackagePrice(order.getDetailList(), redpackagePrice, mapValue);
			//表示需要均分红包
			if(!flag){
				logger.info("需要均分红包了");
				this.mathRedpackage(order.getDetailList(), redpackagePrice);
			}
		}
		return redpackagePrice;
	}
	
	private void mathRedpackage(List<OmsOrderdetail> detailList,  BigDecimal faceValue) {
			// 按比例分配优惠券金额
			BigDecimal totalPrice = new BigDecimal(0);// 订单商品总金额
			BigDecimal addDec = new BigDecimal(0);// 每次均分的金额和
			for (OmsOrderdetail detail : detailList) {
				totalPrice = totalPrice.add((detail.getActPayAmount()));
			}
			for (int i = 0; i < detailList.size(); i++) {
				BigDecimal tempPro = new BigDecimal(0);
				if (i == detailList.size() - 1) {// 如果为最后一项则不需要计算
					tempPro = faceValue.subtract(addDec);// 减去之前计算过的。
				} else {
					// 按比例分配红包金额
					tempPro = faceValue.multiply( detailList.get(i).getActPayAmount().divide(totalPrice, 2, BigDecimal.ROUND_DOWN))
							.setScale(2, BigDecimal.ROUND_DOWN);
					addDec = addDec.add(tempPro);
				}
				// 该商品的实际支付金额（去掉优惠券的）
				detailList.get(i).setRedPacketAmount(tempPro);
				detailList.get(i).setActPayAmount(detailList.get(i).getActPayAmount().subtract(tempPro));
			}

	}
	
	private boolean setRedpackagePrice(List<OmsOrderdetail> list,BigDecimal redpackagePrice,Map<String, Object> mapValue){
		for(OmsOrderdetail detail:list){
			//当某单个订单明细满足该红包的条件 直接把红包分在该商品明细
			if(detail.getActPayAmount().compareTo(redpackagePrice)==1){
				logger.info("商品"+detail.getSkuCode()+"的金额大于红包金额="+redpackagePrice+"，直接把红包金额分到改商品");
				detail.setRedPacketAmount(redpackagePrice);
				detail.setActPayAmount(detail.getActPayAmount().subtract(redpackagePrice));
				if(mapValue!=null){
					detail.setRedPacketIds(mapValue.get("redpackageIds")!=null?mapValue.get("redpackageIds").toString():"");
					detail.setRedPacketUseIds(mapValue.get("redpackageUseIds")!=null?mapValue.get("redpackageUseIds").toString():"");
				}	
				return true;
			}
		}
		return false;
	}
	
	private Map<String, Object> getRedpackValue(List<RedpackageReceive> list){
		Map<String, Object> map=null;
		if(list!=null && list.size()!=0){
			map=new HashMap<String, Object>();
			StringBuffer redpackageIds=new StringBuffer();//红包ID;号隔开
			StringBuffer redpackageUseIds=new StringBuffer();//领取ID;号隔开
			for(RedpackageReceive receive:list){
				redpackageIds.append(receive.getRedpackageId()+";");
				redpackageUseIds.append(receive.getId()+";");
			}			
			map.put("redpackageIds", this.deleteStringBufferLastStr(redpackageIds));
			map.put("redpackageUseIds",  this.deleteStringBufferLastStr(redpackageUseIds));
		}
		return map;
	}
	
	private String deleteStringBufferLastStr(StringBuffer value){
		String str=null;
		if(value!=null){
			 if (';' == value.charAt(value.length() - 1)){
				 value = value.deleteCharAt(value.length() - 1);  
				 str=value.toString();
			 }			
		}
		return str;
	}
	
	private BigDecimal getredPackagePrice(List<RedpackageReceive> list){
		BigDecimal price=new BigDecimal(0);
		if(list!=null && list.size()!=0){
			for(RedpackageReceive receive:list){
				price=price.add(receive.getFackValue());
			}
		}
		return price;
	}

	
	private BigDecimal getActivityActprice(String activityCommodityId,String skuCode){
		BigDecimal price=new BigDecimal(0);
		Map<String, Object> params=new HashMap<String, Object>();
		ActivityCommodity commodity=new ActivityCommodity();
		commodity.setId(activityCommodityId);
		params.put("activityCommodity", commodity);
		params.put("commoditySkuNo", skuCode);
		List<ActivityCommoditySku> list=this.activityCommoditySkuMapper.selectByParams(params);
		if(list!=null && list.size()!=0){
			ActivityCommoditySku sku=list.get(0);
			price= sku.getActivityPrice();
		}
		logger.info("DB活动商品价格："+price);
		return price;
	}
	
	private void getSuccessMap(String memberId, List<Map<String, Integer>> redisSuccessMap, OmsOrderdetail detail) {
		logger.info("商品验证通过缓存购买量留待异常回滚:" + detail.getCommodityNo());
		Map<String, Integer> redisMap = new HashMap<String, Integer>();
		String userIDKey = PrmsConstant.getCommodityUserBuyNum(detail.getCommodityNo(),
				detail.getActivityCommodityItemId(), memberId);
		String commodityKey = PrmsConstant.getCommodityBuyNum(detail.getCommodityNo(),
				detail.getActivityCommodityItemId());
		String skuKey = PrmsConstant.getCommoditySkyBuyNum(detail.getActivityCommodityItemId(), detail.getSkuCode());
		redisMap.put(userIDKey, detail.getBuyNum());// 如果验证成功把用户已经购买数量和当前购买量以map存入集合
		redisMap.put(commodityKey, detail.getBuyNum());// 如果验证成功把商品已经购买数量和当前购买量以map存入集合
		redisMap.put(skuKey, detail.getBuyNum());// 如果验证成功把商品SKU已经购买数量和当前购买量以map存入集合
		redisSuccessMap.add(redisMap);
	}

	/**
	 * @param order
	 * @param resDto
	 * @param detail
	 * @param buyNum
	 * @param activityCommodityId
	 * @param commodityskuNo
	 * @param commodityNo
	 * @param value
	 * @return
	 */
	private ResultDto checkLimit(OmsOrder order, OmsOrderdetail detail, String value) {

		ResultDto resDto = new ResultDto(ResultDto.OK_CODE, "");
		int limitCount = 0;
		int idlimitCount = 0;
		String activityCommodityId = detail.getActivityCommodityItemId();
		String commodityNo = detail.getCommodityNo();
		String commodityskuNo = detail.getSkuCode();
		Integer buyNum = detail.getBuyNum();
		idlimitCount = getCommodityIdLimit(activityCommodityId, commodityNo, commodityskuNo);
		limitCount = getCommoditySkuLimit(activityCommodityId, commodityskuNo, detail);
		// 存在用户限购量

		// 当用户限购量小商品SKU限购量

		if (idlimitCount > 0) {
			if (idlimitCount < limitCount) {
				if (buyNum > idlimitCount) {
					// 用户购买量大于用户限购量
					resDto.setCode(ResultDto.BUSINESS_ERROR_CODE);
					resDto.setMessage(PrmsConstant.isIdLimit(detail.getCommodityTitle(), idlimitCount));
					return resDto;
				}
			} else {
				if (buyNum > limitCount) {
					resDto.setCode(ResultDto.BUSINESS_ERROR_CODE);
					resDto.setMessage(PrmsConstant.isLimit(detail.getCommodityTitle(), limitCount, value));
					return resDto;
				}
			}
		}

		if (buyNum > limitCount) {
			resDto.setCode(ResultDto.BUSINESS_ERROR_CODE);
			resDto.setMessage(PrmsConstant.isLimit(detail.getCommodityTitle(), limitCount, value));
			return resDto;
		}

		String lockKey = activityCommodityId + "_" + commodityNo;
		String transactionProcessingVarName = lockKey.concat("_processing");
		RedisLock jedisLock = new RedisLock(redis, lockKey, 5000, 60000, 10);

		try {
			if (jedisLock.acquire() && redis.setNX(transactionProcessingVarName, "0")) {
				jedisLock.setParentHashCode(1);
				// 该用户对该活动的商品已经购买的数量

				// watch.stop();
				// 获取该商品限购量和Id 限购量

				// 当前用户该商品已经购买的数量
				int userBuyNum = 0;
				// 判断购买量和该活动商品限购量
				int oldBuyNum = 0;
				oldBuyNum = getCommoditySkuBuyNum(detail);
				if (idlimitCount > 0) {
					userBuyNum = getUserCommodityLimitNum(order, detail, activityCommodityId, commodityNo);

					if ((idlimitCount - userBuyNum) < (limitCount - oldBuyNum)) {
						if (userBuyNum == idlimitCount) {
							resDto.setCode(ResultDto.BUSINESS_ERROR_CODE);
							resDto.setMessage(PrmsConstant.passIdLimit(detail.getCommodityTitle() ));
							redis.remove(transactionProcessingVarName);
							return resDto;
						}

						if ((buyNum + userBuyNum) > idlimitCount) {
							resDto.setCode(ResultDto.BUSINESS_ERROR_CODE);
							resDto.setMessage(PrmsConstant.isLimit(detail.getCommodityTitle(), (idlimitCount - userBuyNum) , value));
							redis.remove(transactionProcessingVarName);
							return resDto;
						}
					} else {
						if (oldBuyNum == limitCount) {
							resDto.setCode(ResultDto.BUSINESS_ERROR_CODE);
							resDto.setMessage(PrmsConstant.overSkuLimit(detail.getCommodityTitle() , value));
							redis.remove(transactionProcessingVarName);
							return resDto;
						}

						if ((buyNum + oldBuyNum) > limitCount) {
							resDto.setCode(ResultDto.BUSINESS_ERROR_CODE);
							resDto.setMessage(PrmsConstant.isLimit(detail.getCommodityTitle(),(limitCount - oldBuyNum) , value));
							redis.remove(transactionProcessingVarName);
							return resDto;
						}
					}

				}

				if (oldBuyNum == limitCount) {
					resDto.setCode(ResultDto.BUSINESS_ERROR_CODE);
					resDto.setMessage(PrmsConstant.overSkuLimit(detail.getCommodityTitle() , value));
					redis.remove(transactionProcessingVarName);
					return resDto;
				}

				if ((buyNum + oldBuyNum) > limitCount) {
					resDto.setCode(ResultDto.BUSINESS_ERROR_CODE);
					resDto.setMessage(PrmsConstant.isLimit(detail.getCommodityTitle(),(limitCount - oldBuyNum) , value));
					redis.remove(transactionProcessingVarName);
					return resDto;
				}
				// 该订单用户活动商品购买量统计存入缓存

				statiBuyNum(order, detail);
				// 该活动商品已经购买数量统计

				statiBuyCommodityNum(detail, commodityNo, activityCommodityId);

				redis.remove(transactionProcessingVarName);
				jedisLock.release();

			} else {
				logger.error("lock time out");
				resDto.setCode(ResultDto.BUSINESS_ERROR_CODE);
				resDto.setMessage(PrmsConstant.exception(detail.getCommodityTitle(), value));
			}
		} catch (Exception e) {

			resDto.setCode(ResultDto.BUSINESS_ERROR_CODE);
			resDto.setMessage(PrmsConstant.exception(detail.getCommodityTitle(), value));

			logger.error("用户剩余限购量", e);
			// 异常时当锁对象的父hashCode不为空时，则释放业务执行标识。
			if (redis != null && jedisLock.getParentHashCode() != 0) {
				redis.remove(transactionProcessingVarName);
				System.err.println("删除标识");
			}
			// 释放锁。
			if (jedisLock != null && jedisLock.isLocked()) {
				jedisLock.release();
				System.err.println("异常解锁");
			}
		} finally {
			// 释放锁。
			if (jedisLock != null && jedisLock.isLocked()) {
				jedisLock.release();
				System.err.println("超时解锁");
			}

		}

		return resDto;
	}

	private BigDecimal calculCommonPrice(OmsOrderdetail detail, BigDecimal actPayAmount, int buyNum,
			BigDecimal totalPrice) {

		if (null == detail.getBuyGifts() || !"2".equals(detail.getBuyGifts())) {
			if (detail.getFavouredAmount() != null) {
				actPayAmount = detail.getActSalePrice().multiply(new BigDecimal(buyNum))
						.setScale(2, BigDecimal.ROUND_HALF_UP).subtract(detail.getFavouredAmount());
			} else {
				actPayAmount = detail.getActSalePrice().multiply(new BigDecimal(buyNum)).setScale(2,
						BigDecimal.ROUND_HALF_UP);
			}
			detail.setActPayAmount(actPayAmount);
			totalPrice = totalPrice.add(
					detail.getActSalePrice().multiply(new BigDecimal(buyNum)).setScale(2, BigDecimal.ROUND_HALF_UP));// 计算订单总价格
		}
		return totalPrice;
	}

	public int getCommoditySkuLimit(String activityCommodityId, String commodityskuNo, OmsOrderdetail detail) {
		String limitKey = PrmsConstant.getCommoditySkuLimitKey(activityCommodityId, commodityskuNo);
		Object obj = redis.get(limitKey);

		int limitCount = 0;
		if (null != obj) {
			limitCount = (int) obj;
		} else {
			ResultDto resDto = getActivityCommoditySku(detail, activityCommodityId);
			limitCount = setRedisLimit(resDto, activityCommodityId, commodityskuNo, limitCount);
		}

		return limitCount;
	}

	private int getUserCommodityLimitNum(OmsOrder order, OmsOrderdetail detail, String activityCommodityId,
			String commodityNo) {
		// 该用户对该活动的商品已经购买的数量
		Object oldBuyNum = redis.incGet(PrmsConstant.getCommodityUserBuyNum(commodityNo, activityCommodityId, order.getMemberId()));
		int memberBuyNum = 0;
		if (null != oldBuyNum) {

			memberBuyNum = Integer.valueOf(oldBuyNum.toString());
		} else {
			// 从缓存没有取到当前用户该商品购买量 从数据库取数据
			memberBuyNum = getUserCommodityLimitNumFromDB(order, detail);
		}

		logger.info("用户剩下购买量：" + memberBuyNum);
		return memberBuyNum;
	}

	private int getCommodityIdLimit(String activityCommodityId, String commodityNo, String commodityskuNo) {
		String idLimitKey=PrmsConstant.getCommodityIdLimitKey(activityCommodityId, commodityNo);
		Object idObj = redis.get(idLimitKey);
		int idlimitCount = 0;
		if (null != idObj) {
			idlimitCount = (Integer) idObj;
		} else {
			// 缓存失效或者从缓存没有取到值首先去活动拿该商品用户限购量（要判断是否有用户限购量）
			ActivityCommodity co = getActivityCommodity(activityCommodityId);
			if (this.isObject(co)) {
				idlimitCount = getIdLimit(co, activityCommodityId, commodityNo);
			}
		}
		return idlimitCount;
	}

	private int getCommoditySkuBuyNum(OmsOrderdetail detail) {
		Object oldBuyNum = redis.incGet(PrmsConstant.getCommoditySkyBuyNum(detail.getActivityCommodityItemId(), detail.getSkuCode()) );
		logger.info("商品购买量key：" + PrmsConstant.getCommoditySkyBuyNum(detail.getActivityCommodityItemId(), detail.getSkuCode()));

		int commodityBuyNum = 0;
		if (oldBuyNum != null) {
			commodityBuyNum = Integer.valueOf(oldBuyNum.toString());
		} else {
			commodityBuyNum = getCommoditySkuBuyNumFromDB(detail);
		}
		logger.error("商品已购买量：" + commodityBuyNum);
		return commodityBuyNum;
	}

	/** ======================================== */
	/***
	 * ying.cai 重载方法，用于app端获取属性组合的时候精确返回该活动属性剩余可购买的数量
	 * 
	 * @param activityCommodityId
	 *            活动ID
	 * @param commodityskuNo
	 *            商品sku编码
	 * @param skuId
	 *            商品sku id
	 * @param limitCount
	 *            该活动的限购量
	 * @return
	 * @date 2016年6月30日 上午11:13:05
	 * @author ying.cai
	 * @email ying.cai@ffzxnet.com
	 */
	public int getActivityAbleCountBySku(String activityCommodityId, String commodityskuNo, String skuId,
			int limitCount) {
		int newNum = 0;
		if (redis.exists(PrmsConstant.getCommoditySkyBuyNum(activityCommodityId, commodityskuNo))) {
			int oldBuyNum = Integer.valueOf(
					redis.incGet(PrmsConstant.getCommoditySkyBuyNum(activityCommodityId, commodityskuNo)).toString());
			// 还没有购买的数量
			newNum = limitCount - oldBuyNum;
		} else {
			// 去查询已经购买了多少该商品
			newNum = getActivityBuyNum(skuId, activityCommodityId, limitCount);
		}
		logger.info("获取活动属性剩余可购买的数量getActivityAbleCountBySku===>>>" + newNum);
		return newNum;
	}

	@SuppressWarnings("unused")
	private int getActivityBuyNum(String skuId, String activityCommodityId, int limitCount) {
		int newNum = 0;
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("activityCommodityItemId", activityCommodityId);
		params.put("skuId", skuId);
		params.put("status", "YES");
		ResultDto res = this.orderApiService.selectBuyCount(params);
		System.out.println(res);
		if (res.getData() != null) {
			int oldBuyNum = (int) res.getData();// 购买活动商品的数量和
			// 还没有购买的数量
			newNum = limitCount - oldBuyNum;
		} else {
			newNum = limitCount;
		}
		return newNum;
	}

	/** ============================================ */

	private void getPreSaleDevliveDate(OmsOrderdetail detail) {
		// 该商品发货时间
		String dateKey = PrmsConstant.getPreSaleDevliveDateKey(detail.getActivityCommodityItemId(),
				detail.getCommodityNo());
		if (redis.exists(dateKey)) {
			detail.setReadySendTime((Date) redis.get(dateKey));
		} else {
			ActivityCommodity co = getActivityCommodity(detail.getActivityCommodityItemId());
			if (ActivityTypeEnum.PRE_SALE.equals(co.getActivityType())) {
				redis.set(dateKey, co.getDeliverDate());
				detail.setReadySendTime(co.getDeliverDate());
			}
		}
	}

	private BigDecimal getActSalePrice(ResultDto resDto, OmsOrderdetail detail, String activityCommodityId,
			BigDecimal actSalePrice) {
		BigDecimal actPrice;
		List<ActivityCommoditySku> activitySkuList = (List<ActivityCommoditySku>) resDto.getData();
		if (activitySkuList != null && activitySkuList.size() != 0) {
			ActivityCommoditySku activitySku = activitySkuList.get(0);
			actPrice = activitySku.getActivityPrice();
			if (actPrice != null) {
				redis.set(RedisPrefix.ACTIVITY + RedisPrefix.ACTIVITY_PRICE + activityCommodityId + "_"
						+ detail.getSkuCode() + "price", actPrice);// 取出活动商品的实际价格
				actSalePrice = actSalePrice.add(actPrice);
			}
		}
		return actSalePrice;
	}

	private int getIdLimit(ActivityCommodity activityCommodity, String activityCommodityId, String commodityNo) {
		int idlimitCount;
		if (activityCommodity.getIdLimitCount() != null && activityCommodity.getIdLimitCount() != 0) {
			// 商品用户限购量
			idlimitCount = activityCommodity.getIdLimitCount();
			// 商品用户限购量再次存入缓存
			redis.set(PrmsConstant.getCommodityIdLimitKey(activityCommodityId, commodityNo),
					activityCommodity.getIdLimitCount());
		} else {
			idlimitCount = -1;// 表示没有用户限购量
		}
		// 如果购买的该活动是预售把发货时间存入缓存
		if (activityCommodity.getActivityType().equals(ActivityTypeEnum.PRE_SALE)) {
			redis.set(PrmsConstant.getPreSaleDevliveDateKey(activityCommodityId, commodityNo),
					activityCommodity.getDeliverDate());
		}
		return idlimitCount;
	}

	private int setRedisLimit(ResultDto resDto, String activityCommodityId, String commodityskuNo, int limitCount) {
		List<ActivityCommoditySku> activityCommoditySkuList = (List<ActivityCommoditySku>) resDto.getData();
		if (activityCommoditySkuList != null && activityCommoditySkuList.size() != 0) {
			ActivityCommoditySku activitySku = activityCommoditySkuList.get(0);
			limitCount = activitySku.getLimitCount();
			// 再次把商品限购量存入缓存
			redis.set(PrmsConstant.getCommoditySkuLimitKey(activityCommodityId, commodityskuNo), limitCount);
		}
		return limitCount;
	}

	private int getCommoditySkuBuyNumFromDB(OmsOrderdetail detail) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("activityCommodityItemId", detail.getActivityCommodityItemId());
		params.put("skuId", detail.getSkuId());
		params.put("status", "YES");
		ResultDto res = this.orderApiService.selectBuyCount(params);

		int oldBuyNumber = 0;
		if (res.getData() != null) {
			oldBuyNumber = (int) res.getData();// 购买活动商品的数量和
			redis.increment(PrmsConstant.getCommoditySkyBuyNum(detail.getActivityCommodityItemId(), detail.getSkuCode()) , oldBuyNumber);
		}
		return oldBuyNumber;
	}

	private int getUserCommodityLimitNumFromDB(OmsOrder order, OmsOrderdetail detail) {
		int oldIdBuyNum = 0;
		// 有用户限购量 就要去查询改用户已经购买了多少该商品
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("activityCommodityItemId", detail.getActivityCommodityItemId());
		params.put("memberIds", order.getMemberId());
		params.put("status", "YES");
		ResultDto res = this.orderApiService.selectBuyCount(params);
		if (res.getData() != null) {
			int oldBuyNum = (int) res.getData();// 该用户购买的该活动商品的数量和
			redis.increment(PrmsConstant.getCommodityUserBuyNum(detail.getCommodityNo(),
					detail.getActivityCommodityItemId(), order.getMemberId()), oldBuyNum);
			oldIdBuyNum = oldBuyNum;
		}
		return oldIdBuyNum;
	}

	private BigDecimal getCouponPrice(List<OmsOrderdetail> detailList, String couponId, BigDecimal favoutAmount,
			CouponReceive receive) {
		CouponAdmin coupon = receive.getCouponAdmin();

		 List<OmsOrderdetail> orderdetailListSource=new ArrayList<OmsOrderdetail>();
		favoutAmount = favoutAmount.add(coupon.getFaceValue());
		String goodsSelect = coupon.getGoodsSelect();// 选择的商品状态
		int detailcount = 0;// 该订单可以使用优惠券商品数量

		BigDecimal faceValue = coupon.getFaceValue();// 面值
		// 全部商品都可以用的优惠券
		if ("0".equals(goodsSelect)) {
			// 其他类型商品集合
			orderdetailListSource = new ArrayList<OmsOrderdetail>();
			for (OmsOrderdetail detail : detailList) {
				if (!detail.getBuyType().getValue().equals(BuyTypeEnum.PANIC_BUY.getValue())
						&& !detail.getBuyType().getValue().equals(BuyTypeEnum.PRE_SALE.getValue())
						&& !"2".equals(detail.getBuyGifts())) {
					orderdetailListSource.add(detail);
				} else {
					// 不能使用优惠券的商品优惠金额是0
					detail.setFavouredAmount(new BigDecimal(0));
				}
			}
			// 该订单详情每个商品均分的优惠券金额
			detailcount = orderdetailListSource.size();
//			mathAllGoods(putonglist, detailcount, faceValue);
		} else if ("2".equals(goodsSelect)) {
			// 指定商品计算优惠金额
			ResultDto dt = this.activityManagerApiService.selectActivityCategory(couponId);
			List<CouponAdminCategory> categoryList = (List<CouponAdminCategory>) dt.getData();
			// 排除掉不能使用该优惠券的商品
			orderdetailListSource = removeNoCoupon(detailList, categoryList);
			detailcount = orderdetailListSource.size();
//			mathAllGoods(orderdetailList, detailcount, faceValue);
			// 取订单原商品详情集合和可以使用优惠券的商品集合并集
			// detailList.addAll(orderdetailList);
		} else if ("3".equals(goodsSelect)) {
			// 指定商品类目
			ResultDto dt = this.activityManagerApiService.selectActivityCategory(couponId);
			List<CouponAdminCategory> categoryList = (List<CouponAdminCategory>) dt.getData();
			// 去除掉不能使用优惠券的
			orderdetailListSource = removeNocouponCategory(detailList, categoryList);
			detailcount = orderdetailListSource.size();
//			mathAllGoods(orderdetailList, detailcount, faceValue);
			// 取订单原商品详情集合和可以使用优惠券的商品集合并集
		} else if ("1".equals(goodsSelect)) {
			// 指定活动
			// 获取指定活动对应的商品集合
			ResultDto receiveDto = couponReceiveApiService.getCoupReceive(receive.getId());
			CouponReceive cReceive = (CouponReceive) receiveDto.getData();
			List<String> activityManagerIds = cReceive.getCouponAdmin().getList();
			List<ActivityCommodity> activityCommodityList = new ArrayList<ActivityCommodity>();
			ResultDto rd = this.activityManagerApiService.selectActCommodityList(activityManagerIds);
			if (rd != null && ResultDto.OK_CODE.equals(rd.getCode())) {
				activityCommodityList = (List<ActivityCommodity>) rd.getData();
			} else {
				logger.error("获取指定活动对应的商品集合出错", rd.getMessage());
				throw new ServiceException(PrmsConstant.RESULT_TYPE_2);
			}
			// 去除掉不能使用优惠券的
			orderdetailListSource = removeNocouponActivity(detailList, activityCommodityList);
		}

		//剔除虚拟商品
		logger.info("一个日志");
		List<OmsOrderdetail> orderdetailList = removeVirtual(orderdetailListSource);
		detailcount = orderdetailList.size();
		mathAllGoods(orderdetailList, detailcount, faceValue);
		return favoutAmount;
	}
	/**
	 * 去除掉不能使用优惠券的
	 * @param orderdetailListSource
	 * @return
	 */
	private List<OmsOrderdetail>  removeVirtual(List<OmsOrderdetail> orderdetailListSource){
		
		 List<OmsOrderdetail> orderdetailList=new ArrayList<OmsOrderdetail>();
		 for (OmsOrderdetail omsOrderdetail : orderdetailListSource) {
				if(StringUtil.isEmpty(omsOrderdetail.getVirtualGdId())){//不是虚拟商品  null
					orderdetailList.add(omsOrderdetail);
				}
			}
		 return orderdetailList;
	}
	private void getActivityTitle(OmsOrderdetail detail) {
		String objKey = PrmsConstant.getActivityCommodityObj(detail.getActivityCommodityItemId(),
				detail.getCommodityNo());
		ActivityCommodity activityCommodity = null;
		if (redis.exists(objKey)) {
			activityCommodity = (ActivityCommodity) redis.get(objKey);
			if (this.isObject(activityCommodity)) {
				detail.setCommodityTitle(activityCommodity.getActivityTitle());
			}
		} else {
			activityCommodity = getActivityCommodity(detail.getActivityCommodityItemId());
			if (this.isObject(activityCommodity)) {
				redis.set(objKey, activityCommodity);
				detail.setCommodityTitle(activityCommodity.getActivityTitle());
			}
		}
	}

	private ResultDto getActivityCommoditySku(OmsOrderdetail detail, String activityCommodityId) {
		ResultDto resDto;
		resDto = this.activityManagerApiService.getActivityCommoditySkuList(activityCommodityId, detail.getSkuId());
		return resDto;
	}

	/***
	 * 获取活动商品对象信息
	 * 
	 * @param activityCommodityId
	 * @return
	 */
	private ActivityCommodity getActivityCommodity(String activityCommodityId) {
		ResultDto resDto = this.activityManagerApiService.getActivityCommodity(activityCommodityId);
		if (this.isObject(resDto.getData())) {
			ActivityCommodity co = (ActivityCommodity) resDto.getData();
			if (this.isObject(co)) {
				return co;
			}

		}
		return null;
	}

	private List<OmsOrderdetail> removeNocouponCategory(List<OmsOrderdetail> detailList,
			List<CouponAdminCategory> categoryList) {
		List<OmsOrderdetail> orderdetailList = new ArrayList<OmsOrderdetail>();
		for (OmsOrderdetail detail : detailList) {
			// 首先把抢购，预售排除掉
			if (!detail.getBuyType().getValue().equals(BuyTypeEnum.PANIC_BUY.getValue())
					&& !detail.getBuyType().getValue().equals(BuyTypeEnum.PRE_SALE.getValue())
					&& !"2".equals(detail.getBuyGifts())) {
				for (CouponAdminCategory ca : categoryList) {
					if (detail.getCommodity().getCategoryId().equals(ca.getCategoryId())) {
						// 把可以使用优惠券的商品从新一个集合
						orderdetailList.add(detail);
					} else {
						// 不能使用优惠券的商品优惠金额是0
						detail.setFavouredAmount(new BigDecimal(0));
					}
				}
			}

		}
		return orderdetailList;
	}

	private List<OmsOrderdetail> removeNocouponActivity(List<OmsOrderdetail> detailList,
			List<ActivityCommodity> activityCommodityList) {
		List<OmsOrderdetail> orderdetailList = new ArrayList<OmsOrderdetail>();
		for (OmsOrderdetail detail : detailList) {
			// 首先把抢购，预售排除掉
			if (!detail.getBuyType().getValue().equals(BuyTypeEnum.PANIC_BUY.getValue())
					&& !detail.getBuyType().getValue().equals(BuyTypeEnum.PRE_SALE.getValue())
					&& !"2".equals(detail.getBuyGifts())) {
				for (ActivityCommodity ac : activityCommodityList) {
					if (detail.getCommodity().getId().equals(ac.getCommodityId())) {
						// 把可以使用优惠券的商品从新一个集合
						orderdetailList.add(detail);
					} else {
						// 不能使用优惠券的商品优惠金额是0
						detail.setFavouredAmount(new BigDecimal(0));
					}
				}
			}
		}
		return orderdetailList;
	}

	private List<OmsOrderdetail> removeNoCoupon(List<OmsOrderdetail> detailList,
			List<CouponAdminCategory> categoryList) {
		List<OmsOrderdetail> orderdetailList = new ArrayList<OmsOrderdetail>();
		for (int i = 0; i < detailList.size(); i++) {// modify by hyl
			OmsOrderdetail detail = detailList.get(i);
			// 首先把抢购，预售排除掉
			if (!detail.getBuyType().getValue().equals(BuyTypeEnum.PANIC_BUY.getValue())
					&& !detail.getBuyType().getValue().equals(BuyTypeEnum.PRE_SALE.getValue())
					&& !"2".equals(detail.getBuyGifts())) {
				for (CouponAdminCategory ca : categoryList) {
					if (detail.getCommodity().getId().equals(ca.getCommodityId())) {
						// 把可以使用优惠券的商品从新一个集合
						orderdetailList.add(detail);
					} else {
						// 不能使用优惠券的商品优惠金额是0
						detail.setFavouredAmount(new BigDecimal(0));
					}
				}
			}

		}
		return orderdetailList;
	}

	private void statiBuyCommodityNum(OmsOrderdetail detail, String commodityNo, String activityCommodityId) {
		Integer limitCount = 0;
		String oldBuyNum = null;
		// 该活动商品已经购买的数量
		limitCount = getCommoditySkuLimit(activityCommodityId, commodityNo, detail);// 商品总限购量
		String key = PrmsConstant.getCommodityBuyNum(commodityNo, activityCommodityId);
		// 更新sdk 缓存
		setRedisSkuBuyNum(detail);

		// 更新商品缓存
		if (redis.incGet(key) != null) {
			oldBuyNum = redis.incGet(key).toString();
			redis.increment(key, detail.getBuyNum());
			// 活动商品SKU新的统计存入缓存

		} else {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("activityCommodityId", activityCommodityId);
			map.put("commodityNo", commodityNo);
			int count = this.getActivityCommodityBuyNum(map);
			oldBuyNum = String.valueOf(count);
			redis.increment(PrmsConstant.getCommodityBuyNum(commodityNo, activityCommodityId) , count + detail.getBuyNum());

		}
		logger.info("商品" + commodityNo + "已经被购买量：" + oldBuyNum + ",限购量" + limitCount + ",当前购买的数量" + detail.getBuyNum());
		if ((Integer.valueOf(oldBuyNum) + detail.getBuyNum()) == limitCount) {
			logger.info("要修改商品" + commodityNo + "已经被购买量：" + oldBuyNum + ",限购量" + limitCount + ",当前购买的数量"
					+ detail.getBuyNum());
			// 剩下的限购数量为0时表示已抢完
			this.activityManagerApiService.updateActivityCommodityStatus(activityCommodityId);
		}
	}


	private void setRedisSkuBuyNum(OmsOrderdetail detail) {
		redis.increment(PrmsConstant.getCommoditySkyBuyNum(detail.getActivityCommodityItemId(), detail.getSkuCode()),
				detail.getBuyNum());
	}

	private void statiBuyNum(OmsOrder order, OmsOrderdetail detail) {
		// 该用户对该活动的商品已经购买的数量
		int oldBuyNum = this.getUserCommodityLimitNum(order, detail, detail.getActivityCommodityItemId(), detail.getCommodityNo());
		redis.increment(PrmsConstant.getCommodityUserBuyNum(detail.getCommodityNo(),
				detail.getActivityCommodityItemId(), order.getMemberId()), detail.getBuyNum());
	}

	private void mathAllGoods(List<OmsOrderdetail> detailList, int detailcount, BigDecimal faceValue) {
		if (detailcount > 1) {
			// 按比例分配优惠券金额
			BigDecimal totalPrice = new BigDecimal(0);// 订单商品总金额
			BigDecimal addDec = new BigDecimal(0);// 每次均分的金额和
			for (OmsOrderdetail detail : detailList) {
				totalPrice = totalPrice.add((detail.getActPayAmount()));
			}
			for (int i = 0; i < detailList.size(); i++) {
				BigDecimal tempPro = new BigDecimal(0);
				if (i == detailList.size() - 1) {// 如果为最后一项则不需要计算
					tempPro = faceValue.subtract(addDec);// 减去之前计算过的。
				} else {
					// 按比例分配优惠券金额
					tempPro = faceValue.multiply(
									detailList.get(i).getActPayAmount().divide(totalPrice, 2, BigDecimal.ROUND_DOWN))
							.setScale(2, BigDecimal.ROUND_DOWN);
					addDec = addDec.add(tempPro);
				}
				// 该商品的实际支付金额（去掉优惠券的）
				detailList.get(i).setFavouredAmount(tempPro);
				detailList.get(i).setActPayAmount(detailList.get(i).getActPayAmount().subtract(tempPro));
			}
		} else if (detailcount == 1) {
			// 当该订单只有一个商品时候 并且商品总金额比优惠券金额大
			if (detailList.get(0).getActPayAmount().compareTo(faceValue) > 0) {
				detailList.get(0).setActPayAmount(detailList.get(0).getActPayAmount().subtract(faceValue));
				detailList.get(0).setFavouredAmount(faceValue);
			} else {
				detailList.get(0).setActPayAmount(new BigDecimal(0.01).setScale(2, BigDecimal.ROUND_DOWN));
				detailList.get(0).setFavouredAmount(faceValue);
			}
		}

	}

	private String getObjectString(Object value){
		if(null!=value){
			return value.toString();
		}
		return null;
		
	}
	
	@Override
	public int getActivityCommodityBuyNum(Map<String, Object> map) throws ServiceException {
		int count = 0;
		try {
			map.put("status", "YES");
			ResultDto res = this.orderApiService.selectBuyCount(map);
			if (StringUtil.isNotNull(res.getData())) {
				count = (int) res.getData();
				logger.info(this.getObjectString(map.get("commodityNo")+"DB缓存商品已售数量"+count));
				if (this.isObject(map.get("memberId"))) {
					redis.increment(PrmsConstant.getCommodityUserBuyNum(this.getObjectString(map.get("commodityNo")), this.getObjectString(map.get("activityCommodityItemId")), this.getObjectString(map.get("memberId"))), count);
				} else {
					redis.increment(PrmsConstant.getCommodityBuyNum(this.getObjectString(map.get("commodityNo")), this.getObjectString(map.get("activityCommodityItemId"))) , count);
				}
				logger.info(this.getObjectString(map.get("commodityNo")+"DB缓存商品已售数量"+redis.incGet(PrmsConstant.getCommodityBuyNum(this.getObjectString(map.get("commodityNo")), this.getObjectString(map.get("activityCommodityItemId"))))));
			}
		} catch (Exception e) {
			logger.error("", e);
		}
		return count;
	}

	@Override
	/***
	 * activityCommodityItemId 活动商品ID memberId 用户id commodityNo 活动商品编码
	 */
	public Object getRedisCount(Map<String, Object> map) throws ServiceException {
		Object count = null;
		try {
			String activityCommodityItemId = null;
			String memeberId = null;
			String commodityNo = null;
			if (this.isObject(map.get("activityCommodityItemId"))) {
				activityCommodityItemId =this.getObjectString(map.get("activityCommodityItemId"));
			}

			if ( this.isObject(map.get("commodityNo"))) {
				commodityNo = this.getObjectString(map.get("commodityNo")); 
			}
			if (map.get("memberId") != null) {
				memeberId = map.get("memberId").toString();
				if (redis.exists(PrmsConstant.getCommodityUserBuyNum(commodityNo, activityCommodityItemId, memeberId)) ) {
					count = redis.incGet(PrmsConstant.getCommodityUserBuyNum(commodityNo, activityCommodityItemId, memeberId));
				}
			} else {
				if (redis.exists(PrmsConstant.getCommodityBuyNum(commodityNo, activityCommodityItemId))) {
					count = redis.incGet(PrmsConstant.getCommodityBuyNum(commodityNo, activityCommodityItemId));
				}
			}
			
			if(count==null){
				count=this.getActivityCommodityBuyNum(map);
			}
			logger.info(commodityNo+"缓存商品已售数量"+count);
		} catch (Exception e) {
			logger.error("", e);
		}
		
		return count;
	}

	private boolean isObject(Object value) {
		boolean flag = false;
		if (null != value) {
			flag = true;
		}
		return flag;
	}
	
	@Override
	public ResultDto checkOrder(Object order)
	{
		Map<String, Object> params=new HashMap<>();
		params.put("order", order);
		ResultDto dto = getOmsOrder(params);
		return dto;
	}
	
	public static void main(String[] args) {
		BigDecimal a=new BigDecimal(10);
		BigDecimal b=new BigDecimal(6);
		System.out.println(a.divide(b, 2, BigDecimal.ROUND_DOWN));
	}

}
