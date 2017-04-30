package com.ffzx.promotion.api.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ffzx.bms.api.dto.OrderParam;
import com.ffzx.bms.api.dto.StockUsed;
import com.ffzx.bms.api.service.OrderStockManagerApiService;
import com.ffzx.commerce.framework.dao.CrudMapper;
import com.ffzx.commerce.framework.dto.ResultDto;
import com.ffzx.commerce.framework.exception.ServiceException;
import com.ffzx.commerce.framework.service.impl.BaseCrudServiceImpl;
import com.ffzx.commerce.framework.utils.DateUtil;
import com.ffzx.commerce.framework.utils.RedisLock;
import com.ffzx.commerce.framework.utils.RedisUtil;
import com.ffzx.commerce.framework.utils.StringUtil;
import com.ffzx.commerce.framework.utils.UUIDGenerator;
import com.ffzx.order.api.dto.OmsOrder;
import com.ffzx.order.api.dto.OmsOrderdetail;
import com.ffzx.order.api.enums.OrderStatusEnum;
import com.ffzx.order.api.service.OrderApiService;
import com.ffzx.promotion.api.dto.ActivityGive;
import com.ffzx.promotion.api.dto.ActivityGiveOrder;
import com.ffzx.promotion.api.dto.CouponAdmin;
import com.ffzx.promotion.api.dto.CouponReceive;
import com.ffzx.promotion.api.dto.GiftCommodity;
import com.ffzx.promotion.api.dto.GiftCommoditySku;
import com.ffzx.promotion.api.dto.GiftCoupon;
import com.ffzx.promotion.api.dto.GiveOrderCoupon;
import com.ffzx.promotion.api.dto.constant.Constant;
import com.ffzx.promotion.api.service.ActivityGiveApiService;
import com.ffzx.promotion.mapper.ActivityGiveMapper;
import com.ffzx.promotion.mapper.CouponAdminMapper;
import com.ffzx.promotion.mapper.CouponReceiveMapper;
import com.ffzx.promotion.mapper.GiftCommodityMapper;
import com.ffzx.promotion.mapper.GiftCommoditySkuMapper;
import com.ffzx.promotion.mapper.GiftCouponMapper;
import com.ffzx.promotion.mapper.GiveOrderCouponMapper;
import com.ffzx.promotion.service.ActivityGiveService;

@Service("activityGiveApiService")
public class ActivityGiveApiServiceImpl extends BaseCrudServiceImpl implements ActivityGiveApiService {

	@Autowired
	private ActivityGiveMapper activityGiveMapper;
	@Autowired
	private GiftCommodityMapper giftCommodityMapper;
	@Autowired
	private GiftCommoditySkuMapper giftCommoditySkuMapper;
	@Autowired
	private GiftCouponMapper giftCouponMapper;
	@Autowired
	private RedisUtil redis;
	@Autowired
	private OrderStockManagerApiService orderStockManagerApiService;
	@Autowired
	private OrderApiService orderApiService;
	@Autowired
	private GiveOrderCouponMapper giveOrderCouponMapper;
	@Autowired
	private CouponAdminMapper couponAdminMapper;
	@Autowired
	private CouponReceiveMapper couponReceiveMapper;
	@Autowired
	private ActivityGiveService activityGiveService;
	@Override
	public CrudMapper init() {
		return activityGiveMapper;
	}

	@Override
	public ResultDto getActivityGive(Map<String, Object> params) {
		ResultDto resDto = null;
		Object giveCommodityId = params.get("commodityId");
		try {
			if (giveCommodityId == null) {
				resDto = new ResultDto(ResultDto.ERROR_CODE, "主商品ID不可以为空");
			}
			logger.info("根据主商品ID 获取主商品信息" + giveCommodityId);
			ActivityGive give = getGive(giveCommodityId.toString());
			if (give == null) {
				resDto = new ResultDto(ResultDto.ERROR_CODE, "不存在该主商品");
			} else {
				resDto = new ResultDto(ResultDto.OK_CODE, "success");
				boolean memberFlag = this.checkObject(params.get("memberId"));
				if (memberFlag) {
					String memeberId = params.get("memberId").toString();
					// 获取该用户已经购买数量
					Integer memberOldBuyNum = this.getUserCommodityLimitNum(give.getId(), give.getCommodityCode(),
							memeberId);
					// 该商品ID限购量
					Integer idLimit = give.getIdLimit();
					if (memberOldBuyNum != null && idLimit != null) {
						give.setNewMemberCount(idLimit - getTransBuyNum(memberOldBuyNum));
					} else {
						give.setNewMemberCount(idLimit);
					}
				}
				resDto.setData(give);
			}
		} catch (Exception e) {
			logger.error("获取买赠主商品信息异常【" + giveCommodityId + "】", e);
			throw new ServiceException(e);
		}
		return resDto;
	}

	/**
	 * 验证map value 值 是否为空
	 * 
	 * @param value
	 * @return
	 */
	private boolean checkObject(Object value) {
		boolean flag = false;
		if (null != value) {
			flag = true;
		}
		return flag;
	}

	/***
	 * 根据主商品ID获取主商品信息
	 * 
	 * @param commodityId
	 * @return
	 */
	private ActivityGive getGive(String commodityId) {
		return this.activityGiveMapper.selectByCommodityId(commodityId);
	}

	@Override
	public ResultDto getActivityGifts(Map<String, Object> params) {
		ResultDto resDto = null;
		Object giveCommodityId = params.get("commodityId");
		try {
			Map<String, Object> resultData = new HashMap<String, Object>();

			boolean commodityIdFlag = this.checkObject(giveCommodityId);
			if (commodityIdFlag) {
				// 赠品map
				logger.info("根据主商品ID 获取主商品信息" + giveCommodityId);
				ActivityGive give = getGive(giveCommodityId.toString());
				// 验证give是否为空
				boolean giveFlag = this.checkObject(give);
				if (giveFlag) {
					logger.info("根据主商品ID 获取赠品集合" + give.getId());
					params = new HashMap<String, Object>();
					params.put("giveId", give.getId());
					List<GiftCommodity> giftCommodityList = this.giftCommodityMapper.selectCommodityByParams(params);
					// 优惠券map
					logger.info("根据主商品ID 获取优惠券集合" + give.getId());
					List<GiftCoupon> giftCounponList = this.giftCouponMapper.selectByParams(params);
					// 封装数据
					logger.info("封装数据到resultData");
					resultData.put("give", give);
					resultData.put("goodsMap", giftCommodityList.size() == 0 ? "" : giftCommodityList);
					resultData.put("couponsMap", giftCounponList.size() == 0 ? "" : giftCounponList);
					resDto = new ResultDto(ResultDto.OK_CODE, "success");
					resDto.setData(resultData);
				}
			}
		} catch (Exception e) {
			logger.error("获取买赠赠品信息异常【" + giveCommodityId + "】", e);
			throw new ServiceException(e);
		}
		return resDto;
	}

	@Override
	public ResultDto checkGiveOmsOrder(Map<String, Object> params) {
		ResultDto resDto = null;
		try {
			OmsOrder order = (OmsOrder) params.get("order");
			List<Map<String, Integer>> redisSuccessMap = new ArrayList<Map<String, Integer>>();
			resDto = getActivityGiftsByOrderdetail(order, redisSuccessMap);
			if (null == resDto || !resDto.getCode().equals(ResultDto.OK_CODE)) {
				logger.warn("failed when checking id and sku limit. the reason is " + resDto);
				// 验证失败回滚缓存
				boolean mapFlag = this.checkList(redisSuccessMap);
				if (mapFlag) {
					redisDecrease(redisSuccessMap);
				}
			}
		} catch (Exception e) {
			logger.error("确认下单计算赠品getActivityGiftsByOrderdetail", e);
		}

		return resDto;
	}

	/***
	 * 回滚异常缓存数据
	 * @param redisSuccessMap
	 */
	private void redisDecrease(List<Map<String, Integer>> redisSuccessMap) {
		for (Map<String, Integer> redisValue : redisSuccessMap) {
			Iterator<String> it = redisValue.keySet().iterator();
			while (it.hasNext()) {
				logger.error("success get redisValue!");
				Object key = it.next();
				redis.decrease(String.valueOf(key), redisValue.get(key).longValue());
			}
		}
	}

	/**
	 * 验证主品，赠品是否可以下单
	 * 
	 * @param order
	 * @param redisSuccessMap
	 * @return
	 */
	private ResultDto getActivityGiftsByOrderdetail(OmsOrder order, List<Map<String, Integer>> redisSuccessMap) {
		ResultDto resDto = null;
		try {
			resDto = new ResultDto(ResultDto.OK_CODE, "success");
			resDto.setData(order);
			if (order != null) {
				List<OmsOrderdetail> list = order.getDetailList();
				Map<String, Integer> triggerMap = new HashMap<String, Integer>();
				for (OmsOrderdetail detail : list) {
					// 主商品验证
					if (detail.getBuyGifts() != null && detail.getBuyGifts().equals("1")) {

						logger.info("获取ID限购量" + detail.getCommodityNo());
						String idLimitKey = Constant.getIdLimitKey(detail.getGiftCommodityItemId(), detail.getCommodityNo());
						String limitKey = Constant.getLimitKey(detail.getGiftCommodityItemId(), detail.getCommodityNo());
						String triggerKey = Constant.getTriggerKey(detail.getGiftCommodityItemId(), detail.getCommodityNo());
						// 不存在缓存key从数据库取值并存入缓存
						if (!redis.exists(idLimitKey) || !redis.exists(limitKey) || !redis.exists(triggerKey)) {
							this.setRedisGive(detail.getCommodity().getId());
						}
						Integer idLimit = getTransBuyNum(redis.get(idLimitKey));
						logger.info("获取限购量" + detail.getCommodityNo());
						Integer limit = getTransBuyNum(redis.get(limitKey));
						logger.info("获取触发数量" + detail.getCommodityNo());
						Integer triggerCount = getTransBuyNum(redis.get(triggerKey));

						// 特殊情况下如果没有获取到缓存数据，从数据库查询获取
						if (null == idLimit || null == limit || null == triggerCount) {
							this.setRedisGive(detail.getCommodity().getId());
							idLimit = getTransBuyNum(redis.get(idLimitKey));
							limit = getTransBuyNum(redis.get(limitKey));
							triggerCount = getTransBuyNum(redis.get(triggerKey));
						}
						logger.info("获取限购量" + detail.getCommodityNo()+"====="+limit+"获取触发数量"+triggerCount+"===="+"获取ID限购量"+idLimit);
						detail.setTriggerNum(triggerCount);
						// 把主商品触发数量存入map集合留待赠品获取触发量
						triggerMap.put(detail.getGiftCommodityItemId(), detail.getTriggerNum());
						logger.info("开始验证主品" + detail.getCommodityNo());
						resDto = giveCommodityCheck(order, detail, idLimit, limit);
						if (null == resDto || !resDto.getCode().equals(ResultDto.OK_CODE)) {
							logger.warn("failed when checking id and sku limit. the reason is " + resDto);
							return resDto;
						}
						if (resDto.getData() != null) {
							// 商品验证成功存入集合留待回滚缓存
							boolean idLimitFlag = this.checklimit(idLimit);
							getSuccessMap(redisSuccessMap, Constant.getMemberGivePayKey(detail.getGiftCommodityItemId(),
									detail.getCommodityNo(), order.getMemberId()),detail.getBuyNum(),idLimitFlag);
							boolean limitFlag = this.checklimit(limit);
							getSuccessMap(redisSuccessMap, Constant.getGivePayKey(detail.getGiftCommodityItemId(), detail.getCommodityNo()),detail.getBuyNum(),limitFlag);
						}
					}
					// 赠品验证
					if (detail.getBuyGifts() != null && detail.getBuyGifts().equals("2")) {
						logger.info("获取赠品限购量" + detail.getCommodityNo());
						String key = Constant.getGiftLimit(detail.getGiftCommodityItemId(), detail.getSkuCode());
						String oneKey = Constant.getOneGiftKey(detail.getGiftCommodityItemId(), detail.getSkuCode());
						// 不存在缓存key从数据库取值并存入缓存
						if (!redis.exists(key) || !redis.exists(oneKey)) {
							setRedisGift(detail.getCommoditySku().getId(), detail.getGiftCommodityItemId());
						}
						Integer limitCount = (Integer) redis.get(key);
						logger.info("获取单次赠送数量" + detail.getCommodityNo());
						Integer awardCount = (Integer) redis.get(oneKey);
						// 特殊情况 刚存入缓存 并未取到值，但是存在，再次查询数据库
						if (limitCount == null || awardCount == null) {
							setRedisGift(detail.getCommoditySku().getId(), detail.getGiftCommodityItemId());
							limitCount = (Integer) redis.get(key);
							awardCount = (Integer) redis.get(oneKey);
						}
						logger.info("已经获取赠品"+detail.getCommodityNo()+"限购量:" + limitCount+";单次赠送数量:"+awardCount);
						detail.setSingleGiftNum(awardCount);
						logger.info("开始验证 赠品" + detail.getCommodityNo());
						resDto = GiftCommodityCheck(order, detail, limitCount);
						if (null == resDto || !resDto.getCode().equals(ResultDto.OK_CODE)) {
							logger.warn("failed when checking id and sku limit. the reason is " + resDto);
							return resDto;
						}
						if (resDto.getData() != null) {
							// 为赠品获取触发数量留待售后用
							detail.setTriggerNum(getTriggerValue(triggerMap, detail.getGiftCommodityItemId()));
							// 赠品有限定数量
							boolean limitFlag=this.checklimit(limitCount);
							// 赠品验证成功存入集合留待回滚缓存
							getSuccessMap(redisSuccessMap, Constant.getGiftPayKey(detail.getGiftCommodityItemId(), detail.getSkuCode()),detail.getBuyNum(),limitFlag);
						}
					}
				}
			}
		} catch (Exception e) {
			logger.error("确认下单计算赠品getActivityGiftsByOrderdetail", e);
		}
		return resDto;
	}

	private void getSuccessMap(List<Map<String, Integer>> redisSuccessMap, String key,Integer buyNum,boolean flag) {
		Map<String, Integer> redisMap = new HashMap<String, Integer>();
		if (flag) {	
			// 如果验证成功把商品已经购买数量和当前购买量以map存入集合
			redisMap.put(key, buyNum);			
			redisSuccessMap.add(redisMap);
		}
		
	}

	/***
	 * 根据主商品主键ID获取触发数量
	 * 
	 * @param triggerMap
	 * @param id
	 * @return
	 */
	private Integer getTriggerValue(Map<String, Integer> triggerMap, String id) {
		Integer count = null;
		if (id != null) {
			count = triggerMap.get(id);
		}
		return count;
	}

	/***
	 * 赠品验证
	 * 
	 * @param detail
	 * @return
	 */
	private ResultDto GiftCommodityCheck(OmsOrder order, OmsOrderdetail detail, Integer limitCount) {
		ResultDto resDto = new ResultDto(ResultDto.OK_CODE, "");
		Integer buyCount = detail.getBuyNum();// 赠品数量

		boolean limitFlag = checklimit(limitCount);// 是否存在限购量
		if (limitFlag) {
			String lockKey = Constant.getLockKey(detail.getGiftCommodityItemId(), detail.getSkuCode(), Constant.GIFTYPE);
			String transactionProcessingVarName = lockKey.concat("_processing");
			RedisLock jedisLock = new RedisLock(redis, lockKey, 5000, 60000, 10);
			try {
				if (jedisLock.acquire() && redis.setNX(transactionProcessingVarName, "0")) {
					jedisLock.setParentHashCode(1);
					logger.info("获取已经赠送数量" + detail.getCommodityNo());
					BigDecimal oldBuyNum = null;// 商品剩余可购买量
					if (limitFlag) {
						oldBuyNum =getRedisCount(detail.getSkuCode(),limitCount,detail.getGiftCommodityItemId());
						logger.info("赠品"+detail.getCommodityNo()+"剩余赠送数量:" + oldBuyNum);
						if (oldBuyNum != null && oldBuyNum.intValue() <= 0 && buyCount > oldBuyNum.intValue()) {
							resDto.setCode(ResultDto.BUSINESS_ERROR_CODE);
							resDto.setMessage(Constant.getGiftLimitStr(detail.getCommodityTitle() ));
							return resDto;
						}
					}
					if (limitFlag) {
						// 该商品已经购买数量统计						
						String key = Constant.getGiftPayKey(detail.getGiftCommodityItemId(), detail.getSkuCode());
						redis.increment(key, buyCount);
						logger.info("赠品"+detail.getCommodityNo()+"去统计赠送数量" );
					}
					redis.remove(transactionProcessingVarName);
					jedisLock.release();
				} else {
					logger.error("lock time out");
					resDto.setCode(ResultDto.BUSINESS_ERROR_CODE);
					resDto.setMessage(Constant.getStr(detail.getCommodityTitle()));
				}
			} catch (Exception e) {
				resDto.setCode(ResultDto.BUSINESS_ERROR_CODE);
				resDto.setMessage(Constant.getStr(detail.getCommodityTitle()));
				// 异常时当锁对象的父hashCode不为空时，则释放业务执行标识。
				deleteReisLock(transactionProcessingVarName, jedisLock);
				// 异常解锁
				releaseLock(jedisLock,Constant.EXCEPTIONLOCK);
			} finally {
				// 超时解锁。
				releaseLock(jedisLock,Constant.CHAOSHILOCK);
			}
		}
		resDto.setData(order);
		return resDto;
	}

	private void releaseLock(RedisLock jedisLock,String value) {
		if (jedisLock != null && jedisLock.isLocked()) {
			jedisLock.release();
			logger.info(value);
		}
	}

	private boolean checklimit(Integer value) {
		boolean flag = true;
		if (value == null || value == -1) {
			flag = false;
		}
		return flag;
	}

	
	
	/***
	 * 主品验证
	 * 
	 * @param order
	 * @param detail
	 * @return
	 */
	private ResultDto giveCommodityCheck(OmsOrder order, OmsOrderdetail detail, Integer idLimit, Integer limit) {
		ResultDto resDto = new ResultDto(ResultDto.OK_CODE, "");
		Integer buyCount = detail.getBuyNum();// 购买数量
		boolean idLimitFlag = checklimit(idLimit);// 是否存在ID限购量
		boolean limitFlag = checklimit(limit);// 是否存在限购量
		// 用户限购量或者商品限购量存在就要放入锁验证
		if (idLimitFlag || limitFlag) {
			String lockKey =Constant.getLockKey(detail.getGiftCommodityItemId(), detail.getCommodityNo(), Constant.GIVETYPE);
			String transactionProcessingVarName = lockKey.concat("_processing");
			RedisLock jedisLock = new RedisLock(redis, lockKey, 5000, 60000, 10);
			try {
				if (jedisLock.acquire() && redis.setNX(transactionProcessingVarName, "0")) {
					jedisLock.setParentHashCode(1);
					Integer userBuyNum = null;// 用户剩余可购买量
					if (idLimitFlag) {
						logger.info("获取单个用户剩余购买数量" + detail.getCommodityNo());
						userBuyNum = idLimit - getUserCommodityLimitNum(detail.getGiftCommodityItemId(), detail.getCommodityNo(), order.getMemberId());
						logger.info("去判断单个用户剩余限购量" + detail.getCommodityNo()+"："+userBuyNum);
						if (userBuyNum != null && userBuyNum <= 0) {
							resDto.setCode(ResultDto.BUSINESS_ERROR_CODE);
							resDto.setMessage(Constant.getGiveIdLimistStr(detail.getCommodityTitle() ));
							redis.remove(transactionProcessingVarName);
							return resDto;
						}
						if (buyCount > userBuyNum) {
							resDto.setCode(ResultDto.BUSINESS_ERROR_CODE);
							resDto.setMessage(Constant.getGiveLimitStr(detail.getCommodityTitle(), userBuyNum));
							redis.remove(transactionProcessingVarName);
							return resDto;
						}
					}
					Integer oldBuyNum = null;// 商品剩余可购买量
					if (limitFlag) {
						logger.info("获取该主品购买数量" + detail.getCommodityNo());
						oldBuyNum = limit - getCommoditySkuBuyNum(detail);
						logger.info("去判断主商品剩余购买数量" + detail.getCommodityNo()+"："+oldBuyNum);
						if (oldBuyNum != null && oldBuyNum <= 0) {
							resDto.setCode(ResultDto.BUSINESS_ERROR_CODE);
							resDto.setMessage(Constant.getGiveEndLimitStr(detail.getCommodityTitle() ));
							redis.remove(transactionProcessingVarName);
							return resDto;
						}

						if (buyCount > oldBuyNum) {
							resDto.setCode(ResultDto.BUSINESS_ERROR_CODE);
							resDto.setMessage(Constant.getGiveLimitStr(detail.getCommodityTitle(), oldBuyNum));
							redis.remove(transactionProcessingVarName);
							return resDto;
						}
					}
					if (idLimitFlag) {
						// 该订单用户商品购买量统计存入缓存						
						redis.increment(Constant.getMemberGivePayKey(detail.getGiftCommodityItemId(),
								detail.getCommodityNo(), order.getMemberId()), detail.getBuyNum());
						logger.info("去统计用户限购量" + detail.getCommodityNo());
					}
					if (limitFlag) {
						// 该商品已经购买数量统计
						logger.info("去统计商品限定数量" + detail.getCommodityNo());
						logger.info("自增数量之前商品已售数量："+redis.incGet(Constant.getGivePayKey(detail.getGiftCommodityItemId(), detail.getCommodityNo()))+"商品购买数量："+detail.getBuyNum());
						redis.increment(
								Constant.getGivePayKey(detail.getGiftCommodityItemId(), detail.getCommodityNo()),
								detail.getBuyNum());
						logger.info("自增数量之后商品已售数量："+redis.incGet(Constant.getGivePayKey(detail.getGiftCommodityItemId(), detail.getCommodityNo())));
					}
					redis.remove(transactionProcessingVarName);
					jedisLock.release();
				} else {
					logger.error("lock time out");
					resDto.setCode(ResultDto.BUSINESS_ERROR_CODE);
					resDto.setMessage(Constant.getStr(detail.getCommodityTitle()));
				}
			} catch (Exception e) {
				logger.error("redis error",e);
				resDto.setCode(ResultDto.BUSINESS_ERROR_CODE);
				resDto.setMessage(Constant.getStr(detail.getCommodityTitle()));
				// 异常时当锁对象的父hashCode不为空时，则释放业务执行标识。
				deleteReisLock(transactionProcessingVarName, jedisLock);
				// 异常解锁
				releaseLock(jedisLock,Constant.EXCEPTIONLOCK);
			} finally {
				// 超时解锁。
				releaseLock(jedisLock,Constant.CHAOSHILOCK);
			}
		}
		resDto.setData(order);
		return resDto;
	}

	private void deleteReisLock(String transactionProcessingVarName, RedisLock jedisLock) {
		if (redis != null && jedisLock.getParentHashCode() != 0) {
			redis.remove(transactionProcessingVarName);
		}
	}

	/***
	 * 获取该用户主商品购买量
	 * 
	 * @param order
	 * @param detail
	 * @return
	 */
	private Integer getUserCommodityLimitNum(String giftCommodityId, String commodityNo, String memberId) {
		// 该用户对该活动的商品已经购买的数量
		String key = Constant.getMemberGivePayKey(giftCommodityId, commodityNo, memberId);
		logger.info("方法获取用户已购买量" + commodityNo);
		Object oldBuyNum = redis.incGet(key);
		Integer memberBuyNum = null;
		if (null != oldBuyNum) {
			memberBuyNum = getTransBuyNum(oldBuyNum);
		} else {
			// 从缓存没有取到当前用户该商品购买量 从数据库取数据
			if (!redis.exists(key)) {
				logger.info("不存在缓存sql获取用户已购买量" + commodityNo);
				logger.info("参数："+giftCommodityId+"==="+memberId);
				Map<String, Object> map = getGiveMap(giftCommodityId, commodityNo, Constant.YES, memberId);
				this.getRedisBuyNum(map);
			}
			oldBuyNum = redis.incGet(key);
			memberBuyNum = getTransBuyNum(oldBuyNum);
		}

		logger.info("主商品用户购买量：" +commodityNo+"："+ memberBuyNum);
		return memberBuyNum;
	}

	@Override
	public ResultDto getCommodityBuyNum(String commodityId) {
		ResultDto resDto = null;
		try {
			ActivityGive give = getGive(commodityId);
			Integer commodityBuyNum = 0;
			if (give != null) {
				// 如果限购量不为空
				if (null != give.getLimitCount() && !"".equals(give.getLimitCount())) {
					String key = Constant.getGivePayKey(give.getId(), give.getCommodityCode());
					Object oldBuyNum = redis.incGet(key);
					if (oldBuyNum != null) {
						commodityBuyNum = getTransBuyNum(oldBuyNum);
					} else {
						if (!redis.exists(key)) {
							Map<String, Object> map = getGiveMap(give.getId(), give.getCommodityCode(), Constant.YES, null);
							this.getRedisBuyNum(map);
						}
						oldBuyNum = redis.incGet(key);
						commodityBuyNum = getTransBuyNum(oldBuyNum);
					}
				} else {
					Map<String, Object> map = getGiveMap(give.getId(), give.getCommodityCode(), Constant.YES, null);
					commodityBuyNum = this.getOldBuyCount(map);
				}
			}
			logger.info("商品已购买量：" + commodityBuyNum);
			resDto = new ResultDto(ResultDto.OK_CODE, "success");
			resDto.setData(commodityBuyNum);
		} catch (Exception e) {
			// TODO: handle exception
  			logger.error("获取买赠主商品已售数量", e);
		}
		return resDto;
	}

	private Map<String, Object> getGiveMap(String giveId, String commodityCode, String code, String memberId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("giftCommodityItemId", giveId);
		map.put("commodityNo", commodityCode);
		map.put("buyGifts", code);
		map.put("order_memberId", memberId);
		return map;
	}
	
	private Map<String, Object> getGiftMap(String giveId, String skuCode, String code) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("giftCommodityItemId", giveId);
		map.put("skuCode", skuCode);
		map.put("buyGifts", code);
		return map;
	}

	/***
	 * 缓存获取主商品购买量
	 * 
	 * @param detail
	 * @return
	 */
	private Integer getCommoditySkuBuyNum(OmsOrderdetail detail) {
		String key = Constant.getGivePayKey(detail.getGiftCommodityItemId(), detail.getCommodityNo());
		Object oldBuyNum = redis.incGet(key);
		Integer commodityBuyNum = null;
		if (oldBuyNum != null) {
			commodityBuyNum = getTransBuyNum(oldBuyNum);
		} else {
			if (!redis.exists(key)) {
				Map<String, Object> map = new HashMap<String, Object>();
				map = getGiveMap(detail.getGiftCommodityItemId(), detail.getCommodityNo(), Constant.YES, null);
				this.getRedisBuyNum(map);
			}
			oldBuyNum = redis.incGet(key);
			commodityBuyNum = getTransBuyNum(oldBuyNum);
		}
		logger.info("商品已购买量：" + commodityBuyNum);
		return commodityBuyNum;
	}

	private Integer getTransBuyNum(Object value) {
		if (value != null) {
			return Integer.parseInt(value.toString());
		}
		return null;
	}

	/***
	 * 获取赠品已经赠送数量
	 * 
	 * @param detail
	 * @return
	 */
	private Integer getGiftCommoditySkuBuyNum(String giftCommodityItemId, String skuCode) {
		String key = Constant.getGiftPayKey(giftCommodityItemId, skuCode);
		Object oldBuyNum = redis.incGet(key);
		Integer commodityBuyNum = null;
		if (oldBuyNum != null) {
			commodityBuyNum = getTransBuyNum(oldBuyNum);
		} else {
			if (!redis.exists(key)) {
				Map<String, Object> map = new HashMap<String, Object>();
				map = getGiftMap(giftCommodityItemId, skuCode, Constant.GoodsAndCoupon);
				this.getRedisGiftBuyNum(map);
			}
			oldBuyNum = redis.incGet(key);
			commodityBuyNum = getTransBuyNum(oldBuyNum);
		}
		logger.info("赠品已经赠送数量：" +skuCode+ commodityBuyNum);
		return commodityBuyNum;
	}

	/**
	 * 根据主商品ID集合获取主商品集合
	 * 
	 * @param params
	 * @return
	 */
	private List<ActivityGive> getGiveList(List<ActivityGiveOrder> params) {
		List<String> giveId = new ArrayList<String>();
		for (ActivityGiveOrder order : params) {
			giveId.add(order.getGoodsId());
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("commodityIdList", giveId);
		map.put("delFlag", Constant.NO);
		map.put("actFlag", Constant.YES);
		List<ActivityGive> giveList = this.activityGiveMapper.selectByParams(map);
		return giveList;
	}

	/***
	 * 根据主商品ID匹配主商品对象
	 * 
	 * @param giveList
	 * @param commodityId
	 * @return
	 */
	private ActivityGive getGive(List<ActivityGive> giveList, String commodityId) {
		for (ActivityGive give : giveList) {
			if (commodityId.equals(give.getCommodityId())) {
				return give;
			}
		}
		return null;
	}

	/**
	 * 验证list集合是否为空
	 * 
	 * @param list
	 * @return
	 */
	private boolean checkList(List<?> list) {
		boolean flag = false;
		if (list != null && list.size() != 0) {
			flag = true;
		}
		return flag;
	}

	@Override
	public ResultDto getActivityGiftsByCommodity(List<ActivityGiveOrder> params) {
		ResultDto resDto = null;
		try {
			boolean listFlag = this.checkList(params);
			if (listFlag) {
				// 获取主商品集合
				List<ActivityGive> giveList = this.getGiveList(params);
				for (ActivityGiveOrder giveorder : params) {
					String commodityId = giveorder.getGoodsId();// 商品ID
					// 获取匹配主商品对象
					if (StringUtils.isNotEmpty(giveorder.getGiveType())
							&& Constant.YES.equals(giveorder.getGiveType())) {
						ActivityGive give = this.getGive(giveList, commodityId);
						giveorder.setWarehouseAppoint(give.getStorageType());
						giveorder.setGiveId(give.getId());
						// 如果购买数量大于触发数量才会赠送赠品
						if (give.getTriggerCount() <= giveorder.getBuycount()) {
							logger.info("根据主商品ID获取赠品集合" + commodityId);
							List<GiftCommoditySku> lastSkuList = getGiftSkuList(giveorder, give);
							// 去处理该主商品的赠品是否显示
							boolean lastFlag = this.checkList(lastSkuList);
							if (lastFlag) {
								logger.info("有赠品去处理赠送数量");
								lastSkuList = this.getEndList(giveorder, lastSkuList);
							}
							// 计算之后的赠品集合从新赋值
							giveorder.setGiftCommodityList(lastSkuList);
						}
					}
				}
				resDto = new ResultDto(ResultDto.OK_CODE, "success");
				resDto.setData(params);
			}
		} catch (Exception e) {
			logger.error("确认下单页面是否显示赠品getActivityGiftsByCommodity", e);
			throw new ServiceException(e);
		}
		return resDto;
	}

	/***
	 * 获取某个主品的赠品集合
	 * @param giveorder
	 * @param give
	 * @return
	 */
	private List<GiftCommoditySku> getGiftSkuList(ActivityGiveOrder giveorder, ActivityGive give) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("giveId", give.getId());
		map.put("delFlag", Constant.NO);
		List<GiftCommodity> giftCommodityList = this.giftCommodityMapper.selectByParams(map);
		List<GiftCommoditySku> lastSkuList = new ArrayList<GiftCommoditySku>();
		for (GiftCommodity gift : giftCommodityList) {
			logger.info("根据赠品ID获取赠品SKU集合" + gift.getId());
			Map<String, Object> tempMap = new HashMap<String, Object>();
			tempMap.put("giftCommodityId", gift.getId());
			tempMap.put("delFlag", Constant.NO);
			List<GiftCommoditySku> skuList = this.giftCommoditySkuMapper.selectByOrder(tempMap);
			// 获取该赠品的某个SKU作为赠品展示
			GiftCommoditySku sku = this.getLastGiftCommoditySku(skuList, giveorder);
			if (sku != null) {
				logger.info("赠品SKU:"+sku.getGiftCommoditySkuBarcode());
				lastSkuList.add(sku);
			}
		}
		return lastSkuList;
	}

	/***
	 * 给赠品集合的SKU赋值剩余限定数量
	 * @param skuList
	 * @param giveOrder
	 * @return
	 */
	private List<GiftCommoditySku> setSkuLastLimit(List<GiftCommoditySku> skuList, ActivityGiveOrder giveOrder){
		for(GiftCommoditySku sku:skuList){
			//获取赠品SKU剩余限定数量
			BigDecimal newCount=getRedisCount(sku.getGiftCommoditySkuCode(), sku.getGiftLimtCount(), giveOrder.getGiveId());
			logger.info("赠品"+sku.getGiftCommoditySkuCode()+"是否显示的剩余限定数量:"+newCount);
			sku.setLastCount(newCount.intValue());
		}
		return skuList;
	}
	
	/**
	 * 获取该赠品某个SKU作为赠品
	 * 
	 * @param skuList
	 * @param giveOrder
	 * @return
	 */
	private GiftCommoditySku getLastGiftCommoditySku(List<GiftCommoditySku> skuList, ActivityGiveOrder giveOrder) {
		boolean skuListFlag = this.checkList(skuList);
		if (skuListFlag) {
			// 有多个SKU
			if (skuList.size() > 1) {
				// 多个SKU设置限定数量 ，取剩余限定数量大的SKU作为赠品 从大到小排序
				if (skuList.get(0).getGiftLimtCount() != null) {
					this.setSkuLastLimit(skuList, giveOrder);
					logger.info("该主品的赠品有多个SKU 按剩余限定数量大小排序" + giveOrder.getGoodsId());
					Collections.sort(skuList, new Comparator<GiftCommoditySku>() {
						@Override
						public int compare(GiftCommoditySku o1, GiftCommoditySku o2) {
							logger.info("已经获取赠品剩余限定数量排序"+o1.getGiftCommoditySkuCode()+o1.getLastCount());
							BigDecimal o2p = new BigDecimal(o2.getLastCount());
							BigDecimal o1p = new BigDecimal(o1.getLastCount());
							return o2p.compareTo(o1p);
						}
					});
					return skuList.get(0);
				} else {
					// 多个SKU没有设置限定数量，随机 取有用户可购买量的SKU作为赠品
					Map<String, StockUsed> rtMap = this.getStockMap(skuList, giveOrder);
					if (rtMap != null) {
						logger.info("该主品的赠品有多个SKU 按可购买数量大小排序" + giveOrder.getGoodsId());
						for (Map.Entry<String, StockUsed> entry : rtMap.entrySet()) {
							StockUsed used = entry.getValue();
							Integer userCanBuy = Integer.parseInt(used.getUserCanBuy());// 用户可购买量
							logger.info("赠品库存:"+entry.getKey()+userCanBuy.toString());
							if (userCanBuy > 0) {
								logger.info("有库存的:"+entry.getKey()+userCanBuy.toString());
								return this.getLastSku(skuList, entry.getKey());
								//return skuList.get(0);
							}
						}
					}

				}
			} else if (skuList.size() == 1) {
				return skuList.get(0);
			}
		}
		return null;
	}

	private GiftCommoditySku getLastSku(List<GiftCommoditySku> skuList,String skuCode){
		for(GiftCommoditySku sku:skuList){
			if(skuCode.equals(sku.getGiftCommoditySkuCode())){
				return sku;
			}
		}
		return null;
	}
	
	/****
	 * 处理赠品是否显示
	 * 
	 * @param skuList
	 * @return
	 */
	private List<GiftCommoditySku> getEndList(ActivityGiveOrder giveOrder, List<GiftCommoditySku> skuList) {
		logger.info("获取作为赠品的SKU的用户购买量");
		Map<String, StockUsed> rtMap = this.getStockMap(skuList, giveOrder);
		// 存放已经赠完的赠品
		List<GiftCommoditySku> awardList = new ArrayList<GiftCommoditySku>();
		for (GiftCommoditySku sku : skuList) {
			Integer triggerCount = sku.getActivityGive().getTriggerCount();// 主商品触发数量
			Integer giftCount = sku.getGiftCount();// 单次增送数量
			int buyCount = giveOrder.getBuycount();// 主商品购买数量
			// 计算赠品数量取值
			logger.info("计算公式获取赠品数量" + sku.getGiftCommoditySkuCode());
			BigDecimal bigCount = new BigDecimal(buyCount).divide(new BigDecimal(triggerCount))
					.setScale(0, BigDecimal.ROUND_DOWN).multiply(new BigDecimal(giftCount));

			// 获取该赠品用户可购买量
			logger.info("获取该赠品用户可购买量" + sku.getGiftCommoditySkuCode());
			BigDecimal stockCount = this.getSkuStock(sku.getGiftCommoditySkuCode(), rtMap);
			logger.info("获取到的该赠品用户可购买量" + sku.getGiftCommoditySkuCode()+"====="+stockCount);
			// 剩余限定数量
			logger.info("缓存获取赠品赠送数量" + sku.getGiftCommoditySkuCode());
			BigDecimal redisCount = getRedisCount(sku.getGiftCommoditySkuCode(),sku.getGiftLimtCount(), giveOrder.getGiveId());
			logger.info("缓存获取到的赠品赠送数量" + sku.getGiftCommoditySkuCode()+"======"+redisCount);
			// 表示该赠品没有限定数量 只比较计算量和可购买量
			logger.info("赠品没有限定数量" + sku.getGiftCommoditySkuCode()+"====="+sku.getGiftLimtCount());
			if (sku.getGiftLimtCount() == null) {
				BigDecimal[] sort = { bigCount, stockCount };
				// 排序比较取最小的赠品数量
				getSortCount(awardList, sku, sort);
				sku.setGiftCount(sort[0].intValue());
			}
			logger.info("赠品有限定数量但是赠完了" + sku.getGiftCommoditySkuCode());
			// 表示有限定数量，剩余限定数量为0 赠完了 该赠品不显示
			if (redisCount != null && redisCount.compareTo(new BigDecimal(0)) == 0) {
				awardList.add(sku);
			}
			// 表示还有剩余限定数量
			logger.info("赠品还有剩余限定数量" + sku.getGiftCommoditySkuCode());
			if (redisCount != null && redisCount.compareTo(new BigDecimal(0)) == 1) {
				BigDecimal[] sort = { bigCount, redisCount, stockCount };
				// 排序比较取最小的赠品数量
				getSortCount(awardList, sku, sort);
				sku.setGiftCount(sort[0].intValue());
			}
		}

		// 去掉已经赠完的商品集合 保留可以赠送的商品
		logger.info("保留可以赠送的商品集合" + skuList);
		skuList.removeAll(awardList);
		return skuList;
	}

	/***
	 * 排序比较取最小的赠品数量
	 * 
	 * @param awardList
	 * @param sku
	 * @param sort
	 */
	private void getSortCount(List<GiftCommoditySku> awardList, GiftCommoditySku sku, BigDecimal[] sort) {
		logger.info("排序比较赠品数量取最小值" + sku.getGiftCommoditySkuCode());
		Integer giftCount;
		Arrays.sort(sort); // 从小到大排序
		giftCount = sort[0].intValue();// 比较之后的赠送数量结果
		// 如果赠送数量小于等于0 表示 该赠品已经被赠完 去掉该商品
		if (giftCount <= 0) {
			awardList.add(sku);
		}
	}

	/***
	 * 获取赠品剩余限定数量
	 * 
	 * @param sku
	 * @return
	 */
	private BigDecimal getRedisCount(String skuCode,Integer giftLimitCount, String giveId) {
		BigDecimal count = null;
		Integer payCount = getGiftCommoditySkuBuyNum(giveId,skuCode);
		// 剩余赠品数量
		// 限定数量不可以为空表示存在限定数量 要获取剩余限定数量
		if (giftLimitCount != null) {
			// 如果售卖过 要获取剩余限定数量
			if (payCount != null) {
				count = new BigDecimal(giftLimitCount).subtract(new BigDecimal(payCount.toString()));
			} else {
				count = new BigDecimal(giftLimitCount);
			}
			return count;
		}
		logger.info("赠品剩余赠送数量：" +skuCode+ count);
		return count;
	}

	/***
	 * 获取赠品集合的可购买量
	 * 
	 * @param skuList
	 * @param giveOrder
	 * @return
	 */
	private Map<String, StockUsed> getStockMap(List<GiftCommoditySku> skuList, ActivityGiveOrder giveOrder) {
		List<OrderParam> orderParamList = new ArrayList<OrderParam>();
		getOrderParamList(skuList, orderParamList);
		logger.info(orderParamList.toString());
		ResultDto resDto = this.orderStockManagerApiService.getGiftStockNum(orderParamList, giveOrder.getRegionId());
		if (resDto.getData() != null) {
			return (Map<String, StockUsed>) resDto.getData();
		}
		return null;
	}

	/***
	 * 根据sku获取用户可购买量
	 * 
	 * @param skuCode
	 * @param stockMap
	 * @return
	 */
	private BigDecimal getSkuStock(String skuCode, Map<String, StockUsed> stockMap) {
		StockUsed used = stockMap.get(skuCode);
		BigDecimal count = null;
		if (used != null) {
			count = new BigDecimal(used.getUserCanBuy());
		}
		return count;
	}

	/**
	 * 按库存集合封装数据
	 * 
	 * @param skuList
	 * @param orderParamList
	 */
	private void getOrderParamList(List<GiftCommoditySku> skuList, List<OrderParam> orderParamList) {
		for (GiftCommoditySku sku : skuList) {
			String storageType = sku.getActivityGive().getStorageType();// 0
																		// 中央仓1地址匹配仓
			// 获取该赠品用户可购买量
			OrderParam param = new OrderParam();
			param.setGiftType("1");
			param.setWarehouseType(storageType);
			param.setSkuCode(sku.getGiftCommoditySkuCode());
			orderParamList.add(param);
		}
	}

	/**
	 * 获取赠品SKU
	 * 
	 * @param params
	 * @return
	 */
	@Override
	public ResultDto getGiftCommoditySku(Map<String, Object> params) {
		ResultDto resDto = null;
		try {
			List<GiftCommoditySku> skuList = this.giftCommoditySkuMapper.selectByParams(params);
			resDto = new ResultDto(ResultDto.OK_CODE, "success");
			resDto.setData(skuList);
		} catch (Exception e) {
			logger.error("", e);
			throw new ServiceException(e);
		}
		return resDto;
	}

	/***
	 * 主商品购买缓存获取不到，sql获取，再次存入缓存
	 * 
	 * @throws ServiceException
	 */
	private void getRedisBuyNum(Map<String, Object> map) {
		Integer count = null;
		try {
			count = this.getOldBuyCount(map);
			if (count != null) {
				if (this.checkObject(map.get("order_memberId"))) {
					logger.info("用户已购买量自增" + map.get("commodityNo").toString());
					redis.increment(Constant.getMemberGivePayKey(map.get("giftCommodityItemId").toString(),
							map.get("commodityNo").toString(), map.get("order_memberId").toString()), count);
				} else {
					logger.info("商品已购买量自增" + map.get("commodityNo").toString());
					redis.increment(Constant.getGivePayKey(map.get("giftCommodityItemId").toString(),
							map.get("commodityNo").toString()), count);
				}
			}
		} catch (Exception e) {
			logger.error("", e);
		}
	}

	/***
	 * 获取已经购买数量
	 * 
	 * @param map
	 * @return
	 */
	private Integer getOldBuyCount(Map<String, Object> map) {
		Integer count = 0;
		try {
			map.put("status", "YES");
			ResultDto res = this.orderApiService.selectBuyCount(map);
			if (res.getData() != null) {
				count = (Integer) res.getData();
			}
		} catch (Exception e) {
			logger.error("", e);
		}
		return count;
	}

	/***
	 * 
	 * 赠品已赠送数量缓存获取不到，sql获取，再次存入缓存
	 * 
	 * @throws ServiceException
	 */
	private void getRedisGiftBuyNum(Map<String, Object> map) {
		Integer count = null;
		try {
			count = this.getOldBuyCount(map);
			if (count != null) {
				if(this.checkObject(map.get("skuCode"))){
					logger.info("商品赠送数量自增" + map.get("skuCode").toString());
					redis.increment(Constant.getGiftPayKey(map.get("giftCommodityItemId").toString(),
							map.get("skuCode").toString()), count);
				}			
			}
		} catch (Exception e) {
			logger.error("", e);
		}
	}

	/***
	 * 缓存主商品(限定数量，触发数量，ID限购量)
	 * 
	 * @param commodityId
	 */
	private void setRedisGive(String commodityId) {
		ActivityGive give = this.getGive(commodityId);
		boolean giveFlag = this.checkObject(give);
		if (giveFlag) {
			Integer idLimit = give.getIdLimit();// ID限购量
			Integer limit = give.getLimitCount();// 限定数量
			Integer triggerCount = give.getTriggerCount();// 触发数量
			boolean idFlag = this.checklimit(idLimit);
			boolean limitFlag = this.checklimit(limit);
			boolean triggerFlag = this.checklimit(triggerCount);
			// 缓存ID限购量
			if (!idFlag) {
				redis.set(Constant.getIdLimitKey(give.getId(), give.getCommodityCode()), -1);
			} else {
				redis.set(Constant.getIdLimitKey(give.getId(), give.getCommodityCode()), idLimit);
			}
			// 缓存限定数量
			if (!limitFlag) {
				redis.set(Constant.getLimitKey(give.getId(), give.getCommodityCode()), -1);
			} else {
				redis.set(Constant.getLimitKey(give.getId(), give.getCommodityCode()), limit);
			}
			// 缓存触发数量
			if (!triggerFlag) {
				redis.set(Constant.getTriggerKey(give.getId(), give.getCommodityCode()), -1);
			} else {
				redis.set(Constant.getTriggerKey(give.getId(), give.getCommodityCode()), triggerCount);
			}

		}
	}

	/***
	 * 缓存赠品限定数量，单次赠送量
	 */
	private void setRedisGift(String commodityId, String giveCommodityId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("giftCommoditySkuid", commodityId);
		params.put("giveId", giveCommodityId);
		List<GiftCommoditySku> list = this.giftCommoditySkuMapper.selectByParams(params);
		boolean listFlag = this.checkList(list);
		if (listFlag) {
			GiftCommoditySku sku = list.get(0);
			Integer limitCount = sku.getGiftLimtCount();// 限定数量
			Integer oneAward = sku.getGiftCount();// 单次赠送数量
			boolean oneFlag = this.checklimit(oneAward);
			boolean limitFlag = this.checklimit(limitCount);
			// 缓存限定数量
			if (!limitFlag) {
				redis.set(Constant.getGiftLimit(giveCommodityId, sku.getGiftCommoditySkuCode()), -1);
			} else {
				redis.set(Constant.getGiftLimit(giveCommodityId, sku.getGiftCommoditySkuCode()), limitCount);
			}
			// 缓存单次赠送数量
			if (!oneFlag) {
				redis.set(Constant.getOneGiftKey(giveCommodityId, sku.getGiftCommoditySkuCode()), -1);
			} else {
				redis.set(Constant.getOneGiftKey(giveCommodityId, sku.getGiftCommoditySkuCode()), oneAward);
			}
		}
	}
	
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public ResultDto saveGiveOrderCoupon(GiveOrderCoupon giveOrder) {
		logger.info("开始保存待发优惠券信息");
		logger.info("giveOrder对象："+giveOrder.toString());
		ResultDto resDto = null;
		try {
			String giveId = giveOrder.getGiveId();// 买赠活动ID
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("giveId", giveId);
			List<GiftCoupon> giftCouponList = this.giftCouponMapper.selectByParams(params);
			boolean couponFlag = this.checkList(giftCouponList);
			if (couponFlag) {
				// add by ying.cai  可以赠送多次
				int buyNum = giveOrder.getBuyNum(); //购买数量
				logger.info("购买数量为："+buyNum);
				buyNum = buyNum==0?1:buyNum;
				ActivityGive activityGive = activityGiveService.findById(giveId);
				if(null==activityGive){
					return resDto;
				}
				int triggerCount = activityGive.getTriggerCount().intValue();
				int sendNum = buyNum/triggerCount ;//需要送的数量
				if(0==sendNum){
					return resDto;
				}
				//add by end...
				
				for (GiftCoupon coupon : giftCouponList) {
					String couponId = coupon.getCouponId();
					CouponAdmin couponadmin = this.couponAdminMapper.selectByPrimaryKey(couponId);
					if (couponadmin != null) {
						if (couponadmin.getEffectiveDateNum() != null) {
							giveOrder.setLimitDay(new BigDecimal(couponadmin.getEffectiveDateNum().toString()).intValue()+"");
						}else{
							giveOrder.setSendDate(couponadmin.getEffectiveDateEnd());
						}
					}
					// 计算交易完成时间 15天后 的日期时间 ,到了这个时间就需要发放优惠券 TODO 记得改回
					Date endTime = DateUtil.getNextDay(new Date(), 5);
					//Date endTime = getNextDay(new Date(), 15);
					giveOrder.setEnddate(endTime);
					giveOrder.setCouponId(coupon.getCouponId());
					giveOrder.setGiftCouponId(coupon.getId());
					giveOrder.setCreateDate(new Date());
					giveOrder.setId(UUIDGenerator.getUUID());
					giveOrder.setSendNum(sendNum+"");
					this.giveOrderCouponMapper.insertSelective(giveOrder);
				}
				resDto = new ResultDto(ResultDto.OK_CODE, "success");
			}
		} catch (Exception e) {
			logger.error("交易完成的订单发放优惠券信息异常", e);
			throw new ServiceException(e);
		}
		return resDto;
	}
	/***
	 * 取日期后多少分钟
	 * @param d
	 * @param day
	 * @return
	 */
	private Date getNextDay(Date d,int day){
		if(d==null){
			d = new Date();//参数日期为空 默认当前日期
		 }
		 Calendar cal = Calendar.getInstance();
		 cal.setTime(d);
		 cal.add(Calendar.MINUTE, day);//下一天
		return cal.getTime();
	}
	
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public ResultDto sendGiveOrderCoupon() {
		ResultDto resDto = null;
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("selectDelFlag", "true");
			List<GiveOrderCoupon> orderCouponList = this.giveOrderCouponMapper.selectByParams(params);
			boolean couponFlag = this.checkList(orderCouponList);
			if (couponFlag) {
				List<CouponReceive> receiveList = new ArrayList<CouponReceive>();
				List<String> orderNoList = getOrderGiveList(orderCouponList);
				params = new HashMap<String, Object>();
				params.put("orderIdList", orderNoList);
				ResultDto res =this.orderApiService.findOrderBuyGive(params);
				if(res==null || !ResultDto.OK_CODE.equals(res.getCode()) || res.getData()==null){
					return resDto;
				}
				List<OmsOrder> orderList = (List<OmsOrder>) res.getData();
				for (GiveOrderCoupon coupon : orderCouponList) {
					if (checkSend(coupon, orderList)) {
						// 去给该订单的会员发放优惠券
						CouponReceive receive = new CouponReceive();
						getCouponReceive(orderList, coupon, receive);
						receiveList.add(receive);
						if(StringUtils.isNotBlank(coupon.getSendNum()) && Integer.parseInt(coupon.getSendNum())>1 ){
							for(int i = 0 ; i<Integer.parseInt(coupon.getSendNum())-1;i++){
								CouponReceive newReceive = (CouponReceive) receive.clone();
								newReceive.setId(UUIDGenerator.getUUID());
								receiveList.add(newReceive);
							}
						}
					}
				}
				// 批量去插入优惠券
				if(receiveList!=null && receiveList.size()>0){
					this.couponReceiveMapper.insertManyValue(receiveList);
					//更新字段
					this.giveOrderCouponMapper.updateDelFlag(orderCouponList);
				}
				
			}
		} catch (Exception e) {
			logger.error("交易完成的订单发放优惠券信息异常", e);
			throw new ServiceException(e);
		}

		return resDto;
	}

	/***
	 * 组装优惠券领取数据
	 * 
	 * @param orderList
	 * @param coupon
	 * @param receive
	 */
	private void getCouponReceive(List<OmsOrder> orderList, GiveOrderCoupon coupon, CouponReceive receive) {
		OmsOrder order = getOrder(coupon, orderList);
		CouponAdmin admin = new CouponAdmin();
		admin.setId(coupon.getCouponId());
		receive.setId(UUIDGenerator.getUUID());
		receive.setCouponAdmin(admin);
		receive.setMemberId(order.getMemberId());
		receive.setNickName(order.getMemberName());
		receive.setPhone(order.getMemberPhone());
		receive.setCreateDate(new Date());
		receive.setReceiveDate(new Date());
		receive.setUseState("0");
		if (coupon.getLimitDay() != null) {
			receive.setOverDate(DateUtil.getNextDay(new Date(), Integer.parseInt(coupon.getLimitDay())));
		}else{
			receive.setOverDate(coupon.getSendDate());
		}
	}

	/**
	 * 得到订单ID集合
	 * 
	 * @param orderCouponList
	 * @return
	 */
	private List<String> getOrderGiveList(List<GiveOrderCoupon> orderCouponList) {
		List<String> orderNoList = new ArrayList<String>();
		// 把所有订单ID存放集合去订单系统查询这些订单物流状态
		for (GiveOrderCoupon coupon : orderCouponList) {
			orderNoList.add(coupon.getOrderId());
		}
		return orderNoList;
	}

	/**
	 * 去判断该订单是否发放优惠券
	 * 
	 * @param coupon
	 * @param orderList
	 * @return
	 */
	private boolean checkSend(GiveOrderCoupon coupon, List<OmsOrder> orderList) {
		boolean flag = false;
		if (orderList != null && orderList.size() != 0) {
			for (OmsOrder order : orderList) {
				if (coupon.getOrderId().equals(order.getId())) {
					String status = order.getStatus();// 订单物流状态
					Date time = coupon.getEnddate();// 交易15天后发放时间
					// 订单是交易完成并且当前时间超过15天的日期时间 发放优惠券
					if (status.equals(OrderStatusEnum.TRANSACTION_COMPLETION.getValue())
							&& this.compareDate(new Date(), time) == 1) {
						flag = true;
					}
				}
			}
		}
		return flag;
	}

	/**
	 * 获取单个订单对象
	 * 
	 * @param coupon
	 * @param orderList
	 * @return
	 */
	private OmsOrder getOrder(GiveOrderCoupon coupon, List<OmsOrder> orderList) {
		boolean orderFlag = this.checkList(orderList);
		if (orderFlag) {
			for (OmsOrder order : orderList) {
				if (coupon.getOrderId().equals(order.getId())) {
					return order;
				}
			}
		}
		return null;
	}

	/**
	 * 判断2个时间大小
	 * 
	 * @param d1
	 * @param d2
	 * @return
	 */
	private int compareDate(Date d1, Date d2) {
		if (d1.getTime() > d2.getTime()) {
			System.out.println("dt1 在dt2前");
			return 1;
		} else if (d1.getTime() < d2.getTime()) {
			System.out.println("dt1在dt2后");
			return -1;
		} else {// 相等
			return 0;
		}
	}

}
