package com.ffzx.promotion.api.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ffzx.commerce.framework.constant.Constant;
import com.ffzx.commerce.framework.dao.CrudMapper;
import com.ffzx.commerce.framework.dto.ResultDto;
import com.ffzx.commerce.framework.exception.ServiceException;
import com.ffzx.commerce.framework.service.impl.BaseCrudServiceImpl;
import com.ffzx.commerce.framework.utils.DateUtil;
import com.ffzx.commerce.framework.utils.JsonMapper;
import com.ffzx.commerce.framework.utils.RedisLock;
import com.ffzx.commerce.framework.utils.RedisUtil;
import com.ffzx.commerce.framework.utils.StringUtil;
import com.ffzx.commerce.framework.utils.UUIDGenerator;
import com.ffzx.commodity.api.dto.CommoditySku;
import com.ffzx.member.api.dto.Member;
import com.ffzx.member.api.dto.MemberMessage;
import com.ffzx.member.api.enums.MessTypeEnum;
import com.ffzx.member.api.service.MemberApiService;
import com.ffzx.member.api.service.MemberLabelApiService;
import com.ffzx.member.api.service.MemberMessageApiService;
import com.ffzx.promotion.api.dto.ActivityCommoditySku;
import com.ffzx.promotion.api.dto.CouponAdmin;
import com.ffzx.promotion.api.dto.CouponAdminCouponGrant;
import com.ffzx.promotion.api.dto.CouponGrant;
import com.ffzx.promotion.api.dto.CouponReceive;
import com.ffzx.promotion.api.dto.Redpackage;
import com.ffzx.promotion.api.dto.RedpackageGrant;
import com.ffzx.promotion.api.dto.RedpackageReceive;
import com.ffzx.promotion.api.dto.UserLable;
import com.ffzx.promotion.api.dto.constant.PrmsConstant;
import com.ffzx.promotion.api.enums.ActivityTypeEnum;
import com.ffzx.promotion.api.service.RedPackageApiService;
import com.ffzx.promotion.api.service.consumer.ActivityApiConsumer;
import com.ffzx.promotion.api.service.consumer.CommoditySkuApiConsumer;
import com.ffzx.promotion.exception.CallInterfaceException;
import com.ffzx.promotion.mapper.CouponAdminCouponGrantMapper;
import com.ffzx.promotion.mapper.CouponGrantMapper;
import com.ffzx.promotion.mapper.CouponReceiveMapper;
import com.ffzx.promotion.mapper.RedpackageMapper;
import com.ffzx.promotion.mapper.RedpackageReceiveMapper;
import com.ffzx.promotion.mapper.UserLableMapper;
import com.ffzx.promotion.model.MathRedpackagePrice;
import com.ffzx.promotion.model.ResultBestRedpackValue;
import com.ffzx.promotion.service.CouponGrantService;
import com.ffzx.promotion.service.RedpackageGrantService;


@Service("redPackageApiService")
public class RedPackageApiServiceImpl extends BaseCrudServiceImpl implements RedPackageApiService {

	@Resource
	private RedpackageMapper redpackageMapper;
	@Autowired
	private UserLableMapper userLableMapper;
	@Autowired
	private RedpackageGrantService redpackageGrantService;
	@Autowired
	private CouponGrantService couponGrantService;
	@Autowired
	private CouponGrantMapper couponGrantMapper;
	@Autowired
	private MemberLabelApiService memberLabelApiService;
	@Autowired
	private MemberMessageApiService memberMessageApiService;
	@Autowired
	private RedpackageReceiveMapper redpackageReceiveMapper;
	@Autowired
	private MemberApiService memberApiService;
	@Autowired
	private CouponAdminCouponGrantMapper couponAdminCouponGrantMapper;
	@Autowired
	private CouponReceiveMapper couponReceiveMapper;
	@Autowired
	private ActivityApiConsumer activityApiConsumer;
	@Autowired
	private CommoditySkuApiConsumer commoditySkuApiConsumer;
	@Autowired
	private RedisUtil redis;

	@Override
	public CrudMapper init() {

		return redpackageMapper;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public ResultDto saveRedPackageMessage(String memberId) {
		ResultDto resDto = null;
		try {
			// 发放任务必须是启动的 ，发放方式 消息推送的，发放开始时间大于当前时间小于截止时间，未删除的，且没有被推送过该红包
			Map<String, Object> packageparams = getpackageMapValue(memberId, Constant.STATUS_EFFECT);
			// 去验证是否满足条件 然后去插入红包消息和插入领取记录
			resDto = this.getPackageMessageList(memberId, packageparams);
			Map<String, Object> couponparams = getCouponMapValue(memberId, PrmsConstant.messagecoupon);
			// 去验证是否满足条件 然后去插入优惠券消息和插入领取记录
			resDto = this.getCouponGrantList(couponparams, memberId);

		} catch (Exception e) {
			logger.error("获取某个人的推送红包消息", e);
			throw new ServiceException(e);
		}
		return resDto;
	}

	private Map<String, Object> getCouponMapValue(String memberId, String type) {
		Map<String, Object> couponparams = new HashMap<String, Object>();
		// 发放任务必须是启动的 ，发放方式 消息推送的，发放开始时间大于当前时间小于截止时间，未删除的，发放总量
		couponparams.put(Constant.DELTE_FLAG, Constant.DELTE_FLAG_NO);
		couponparams.put("grantMode", type);
		couponparams.put("nowDate", Constant.STATUS_EFFECT);
		couponparams.put("grant", Constant.STATUS_EFFECT);
		couponparams.put("leftReceive", Constant.YES);
		couponparams.put("memberId", memberId);
		return couponparams;
	}

	/***
	 * 验证该用户是否存在改发放任务的用户标签之内
	 * 
	 * @param memberId
	 * @param lableIdStr
	 * @return
	 */
	private boolean memberIsGrant(String memberId, List<String> lableIdStr) {
		boolean mgFlag = false;
		if (this.isList(lableIdStr)) {
			ResultDto dto = this.memberLabelApiService.judgeMemberInLabelList(memberId, lableIdStr);
			Object ids = dto.getData();
			if (ids != null) {
				List<String> newIds = (List<String>) ids;
				if (this.isList(newIds)) {
					mgFlag = true;
				}
			}
		}
		return mgFlag;
	}

	/**
	 * 验证集合是否为空
	 * 
	 * @param list
	 * @return
	 */
	private boolean isList(List<?> list) {
		boolean flag = false;
		if (null != list && list.size() != 0) {
			flag = true;
		}
		return flag;
	}

	/***
	 * 获取满足条件发放任务的用户标签集合
	 * 
	 * @param params
	 * @return
	 */
	private List<UserLable> getLableList(Map<String, Object> params) {

		List<UserLable> lableList = this.userLableMapper.selectByParams(params);
		return lableList;
	}

	/**
	 * 获取某个任务某个人红包领取记录
	 * 
	 * @param params
	 * @return
	 */
	private List<RedpackageReceive> getRedpackageReceiveList(Map<String, Object> params) {
		List<RedpackageReceive> list = this.redpackageReceiveMapper.selectByParams(params);
		return list;
	}

	/**
	 * 获取某个用户的某个任务的领取优惠券记录
	 * 
	 * @param params
	 * @return
	 */
	private List<CouponReceive> getCouponReceiveList(Map<String, Object> params) {
		return this.couponReceiveMapper.selectByParams(params);
	}


	public List<CouponGrant> getCounponEnoughList(List<CouponGrant> list, String memberId) {
		List<CouponGrant> newList = new ArrayList<CouponGrant>();
		// 获取所有发放任务ID集合
		List<String> idStr = getCouponGrantIdStr(list);
		List<String> newidStr = new ArrayList<String>();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("couponIdStr", idStr);
		params.put("memberId", memberId);
		if (this.isList(idStr)) {
			List<CouponReceive> couList = this.couponReceiveMapper.selectCouponGrantGroup(params);
			if (this.isList(couList) && list.size() >= couList.size()) {
				logger.info("获取已经领取的发放ID");
				for (CouponReceive rd : couList) {
					newidStr.add(rd.getCouponGrant().getId());
				}
				logger.info("获取已经领取的发放ID" + JsonMapper.toJsonString(newidStr));
			}
			logger.info("剔除前" + JsonMapper.toJsonString(idStr));
			// 剔除重复的发放ID
			idStr.removeAll(newidStr);
			logger.info("剔除后" + JsonMapper.toJsonString(idStr));
			// 获取未发放的发放任务
			if (this.isList(idStr)) {
				for (String id : idStr) {
					for (CouponGrant g : list) {
						if (id.equals(g.getId())) {
							newList.add(g);
						}
					}
				}
			}
		}

		return newList;
	}

	/**
	 * (消息红包)根据发放ID集合获取红包发放任务集合
	 * 
	 * @param idStr
	 * @return
	 */
	private ResultDto getPackageMessageList(String memberId, Map<String, Object> params) {
		ResultDto resDto = null;
		try {
			resDto = new ResultDto(ResultDto.OK_CODE, "success");
			List<MemberMessage> messageList = new ArrayList<MemberMessage>();
			// 获取满足条件的发放任务
			List<RedpackageGrant> grantList = this.redpackageGrantService.findByBiz(params);
			//发放任务ID集合
			List<String> idStr = getRedpackageGrantIdStr(grantList);
			if (this.isList(idStr)) {
				Map<String, Object> labelMap = new HashMap<String, Object>();
				labelMap.put("aredIdStr", idStr);
				// 获取所有用户标签集合
				List<UserLable> labelList = this.getLableList(labelMap);
				if (this.isList(labelList)) {
					// 验证用户标签集合
					List<RedpackageGrant> isUserList = new ArrayList<RedpackageGrant>();
					for (RedpackageGrant grant : grantList) {
						// 获取到该红包任务的用户标签集合 去验证当前用户是否在该任务
						logger.info("验证该用户是否在该红包发放任务下的用户标签组");
						boolean isMember = this.memberIsGrant(memberId,
								this.getUserLableIdStr(grant.getId(), labelList, Constant.NO));
						if (isMember) {
							//验证通过放入新集合
							isUserList.add(grant);
						}
					}

					// 组装消息数据
					if (this.isList(isUserList)) {
						for (RedpackageGrant grant : isUserList) {
							logger.info("先并发控制同一个任务一个用户只能发一次消息");
							ResultDto resDto1 = this.redisLockMessage(memberId,grant.getId(),  Constant.YES);
							if (null != resDto1 && resDto1.getCode().equals(ResultDto.OK_CODE)) {
								MemberMessage message = this.getMessage(grant, memberId, null);
								messageList.add(message);
							}
						}
					}

					// 去插入消息和领取记录
					if (this.isList(messageList)) {
						logger.info("插入红包消息数据");
						this.saveMessage(messageList, memberId, isUserList, null);
					}
				}
			}
		} catch (Exception e) {
			resDto = new ResultDto(ResultDto.ERROR_CODE, "error");
			throw new ServiceException(e);
		}

		return resDto;
	}

	private boolean isSendMessage(String grantId, String memberId, String type) {
		boolean flag = false;
		if (StringUtils.isNotEmpty(grantId) && StringUtils.isNotEmpty(memberId)) {
			String key = PrmsConstant.getMessageRedis(grantId, memberId);
			if (redis.exists(key)) {
				Object isMessage = redis.get(key);
				if (isMessage != null) {
					// 说明发过消息
					flag = true;
				}
			} else {
				// 缓存没有去DB查询
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("memberId", memberId);
				if (Constant.YES.equals(type)) {
					params.put("pakcetGrantId", grantId);
				} else {
					params.put("couponGrantId", grantId);
				}
				logger.info("用户ID："+params.get("memberId")+"==红包发放ID："+params.get("pakcetGrantId")+"==优惠券发放ID："+params.get("couponGrantId"));
				ResultDto resDto = this.memberMessageApiService.getMsgByParams(params);
				if (resDto.getData() != null) {
					List<MemberMessage> list = (List<MemberMessage>) resDto.getData();
					if (list.size() > 0) {
						redis.set(key, "1");
						flag = true;
					}
				}
			}
		}
		return flag;
	}

	/***
	 * 并发控制同一个用户同时发消息
	 * 
	 * @param memberId
	 * @param grantId
	 * @return
	 */
	private ResultDto redisLockMessage(String memberId, String grantId, String type) {
		ResultDto resDto = new ResultDto(ResultDto.OK_CODE, "");
		String lockKey = PrmsConstant.getMessageLockKey(grantId, memberId);
		String transactionProcessingVarName = lockKey.concat("_processing");
		RedisLock jedisLock = new RedisLock(redis, lockKey, 5000, 60000, 10);
		try {
			if (jedisLock.acquire() && redis.setNX(transactionProcessingVarName, "0")) {
				jedisLock.setParentHashCode(1);
				logger.info("获取该用户" + memberId + "是否已经组装了该任务" + grantId + "的消息");
				String key = PrmsConstant.getMessageRedis(grantId, memberId);
				if (this.isSendMessage(grantId, memberId, type)) {
					resDto.setCode(ResultDto.BUSINESS_ERROR_CODE);
					resDto.setMessage(PrmsConstant.SENDERROR);
					redis.remove(transactionProcessingVarName);
					return resDto;
				}
				redis.set(key, "1");
				redis.remove(transactionProcessingVarName);
				jedisLock.release();
			} else {
				logger.error("lock time out");
				resDto.setCode(ResultDto.BUSINESS_ERROR_CODE);
				resDto.setMessage(PrmsConstant.TWOMESSAGE);
			}
		} catch (Exception e) {
			logger.error("redis error", e);
			resDto.setCode(ResultDto.BUSINESS_ERROR_CODE);
			resDto.setMessage(PrmsConstant.TWOMESSAGE);
			// 异常时当锁对象的父hashCode不为空时，则释放业务执行标识。
			deleteReisLock(transactionProcessingVarName, jedisLock);
			// 异常解锁
			releaseLock(jedisLock, com.ffzx.promotion.api.dto.constant.Constant.EXCEPTIONLOCK);
			throw new ServiceException(e);
		} finally {
			// 超时解锁。
			releaseLock(jedisLock, com.ffzx.promotion.api.dto.constant.Constant.CHAOSHILOCK);
		}
		return resDto;
	}

	private List<String> getRedpackageGrantIdStr(List<RedpackageGrant> newGrantiList) {
		List<String> idStr = null;
		if (this.isList(newGrantiList)) {
			idStr = new ArrayList<String>();
			// 先把所有满足条件的红包发放任务的ID封装成集合留待查询所有的用户标签
			idStr = getPackageGrantList(newGrantiList);
		}
		return idStr;
	}

	/**
	 * 天降红包 满足条件的发放任务
	 * 
	 * @param grantList
	 * @param memberId
	 * @return
	 */
	private RedpackageGrant getSkyPackeGrantValue(String memberId) {
		List<String> idStr = null;
		Map<String, Object> packageparams = getpackageMapValue(memberId, Constant.STATUS_OVERDUR);
		// 获取满足条件的发放任务的红包
		List<RedpackageGrant> grantList = this.redpackageGrantService.findByBiz(packageparams);

		List<RedpackageGrant> newgrantList = new ArrayList<RedpackageGrant>();
		// 验证用户标签是否存在
		if (this.isList(grantList)) {
			idStr = getRedpackageGrantIdStr(grantList);
			if (this.isList(idStr)) {
				Map<String, Object> labelMap = new HashMap<String, Object>();
				labelMap.put("aredIdStr", idStr);
				// 根据满足条件的红包发放ID集合获取所有用户标签集合
				List<UserLable> labelList = this.getLableList(labelMap);
				if (this.isList(labelList)) {
					for (RedpackageGrant grant : grantList) {
						// 验证是否存在选择的用户标签
						boolean isMember = this.memberIsGrant(memberId,
								this.getUserLableIdStr(grant.getId(), labelList, Constant.NO));
						if (isMember) {
							newgrantList.add(grant);
						}
					}
				}
			}
		}

		// 验证这些任务的发放总量是否足够发放
		if (this.isList(newgrantList)) {
			for (RedpackageGrant grant : newgrantList) {
				logger.info("天降红包开始验证红包是否足够发放和是否领取过" + grant.getRedpackageId());
				ResultDto resDto = this.redisLockRedpackage(grant, memberId);
				// 验证成功 放入新集合
				if (null != resDto && resDto.getCode().equals(ResultDto.OK_CODE)) {
					return grant;
				}
			}
		}
		return null;
	}

	private boolean isReceived(String grantId, String memberId, String type) {
		boolean flag = false;
		if (StringUtils.isNotEmpty(grantId) && StringUtils.isNotEmpty(memberId)) {
			String key = PrmsConstant.getRedisMemberIdSend(grantId, memberId);
			if (redis.exists(key)) {
				Object issend = redis.get(key);
				if (issend != null) {
					// 说明发过优惠券
					flag = true;
				}
			} else {
				// 缓存没有去DB查询
				if (Constant.YES.equals(type)) {
					if (this.isReceive(memberId, grantId)) {
						flag = true;
					}
				} else {
					CouponGrant grant = new CouponGrant();
					grant.setId(grantId);
					Map<String, Object> params = new HashMap<String, Object>();
					params.put("memberId", memberId);
					params.put("couponGrant", grant);
					List<CouponReceive> list = this.getCouponReceiveList(params);
					if (this.isList(list)) {
						flag = true;
					}
				}

			}
		}

		return flag;
	}

	/**
	 * 红包发放验证是否发超
	 * 
	 * @param grant
	 * @return
	 */
	private ResultDto redisLockRedpackage(RedpackageGrant grant, String memberId) {
		ResultDto resDto = new ResultDto(ResultDto.OK_CODE, "");
		logger.info("获取红包发放总量" + grant.getRedpackageId());
		Integer count = getRedisRedpackageGrantNum(grant.getRedpackageId());
		if (null != count && 0 != count) {
			String lockKey = PrmsConstant.getRedpackLockKey(grant.getId(), grant.getRedpackageId());
			String transactionProcessingVarName = lockKey.concat("_processing");
			RedisLock jedisLock = new RedisLock(redis, lockKey, 5000, 60000, 10);
			try {
				if (jedisLock.acquire() && redis.setNX(transactionProcessingVarName, "0")) {
					jedisLock.setParentHashCode(1);

					// 该红包是否发放过
					if (this.isReceived(grant.getId(), memberId, Constant.YES)) {
						resDto.setCode(PrmsConstant.SENDEDERROR);
						resDto.setMessage(PrmsConstant.SENDERROR);
						redis.remove(transactionProcessingVarName);
						return resDto;
					}

					logger.info("获取红包已发放数量：" + grant.getRedpackageId());
					Integer oldSendNum = this.getRedisRedpackageSendedNum(grant.getRedpackageId(), grant.getId());
					logger.info("获取红包已发放数量：" + grant.getRedpackageId() + "==" + oldSendNum);
					if (oldSendNum >= count) {
						resDto.setCode(PrmsConstant.SENDCOUNTERROR);
						resDto.setMessage(PrmsConstant.SENDERROR);
						redis.remove(transactionProcessingVarName);
						return resDto;
					}
					logger.info("去统计已经发放数量" + grant.getRedpackageId());
					redis.increment(PrmsConstant.getRedpackSendedNum(grant.getRedpackageId()), 1);
					// 表示可以发发放 存入缓存留待验证
					redis.set(PrmsConstant.getRedisMemberIdSend(grant.getId(), memberId), "1");
					redis.remove(transactionProcessingVarName);
					jedisLock.release();
				} else {
					logger.error("lock time out");
					resDto.setCode(ResultDto.BUSINESS_ERROR_CODE);
					resDto.setMessage(com.ffzx.promotion.api.dto.constant.Constant
							.getRedpackageStr(grant.getRedpackage().getName()));
				}
			} catch (Exception e) {
				logger.error("redis error", e);
				resDto.setCode(ResultDto.BUSINESS_ERROR_CODE);
				resDto.setMessage(
						com.ffzx.promotion.api.dto.constant.Constant.getRedpackageStr(grant.getRedpackage().getName()));
				// 异常时当锁对象的父hashCode不为空时，则释放业务执行标识。
				deleteReisLock(transactionProcessingVarName, jedisLock);
				// 异常解锁
				releaseLock(jedisLock, com.ffzx.promotion.api.dto.constant.Constant.EXCEPTIONLOCK);
				throw new ServiceException(e);
			} finally {
				// 超时解锁。
				releaseLock(jedisLock, com.ffzx.promotion.api.dto.constant.Constant.CHAOSHILOCK);
			}
		}
		return resDto;
	}

	/**
	 * 优惠券发放验证是否发超
	 * 
	 * @param grant
	 * @return
	 */
	private ResultDto redisLockCoupon(CouponGrant grant, String memberId) {
		ResultDto resDto = new ResultDto(ResultDto.OK_CODE, "");
		logger.info("获取优惠券发放总量" + grant.getId());
		Integer count = getRedisCouponGrantNum(grant.getId());
		if (null != count && 0 != count) {
			String lockKey = PrmsConstant.getCouponLockKey(grant.getId());
			String transactionProcessingVarName = lockKey.concat("_processing");
			RedisLock jedisLock = new RedisLock(redis, lockKey, 5000, 60000, 10);
			try {
				if (jedisLock.acquire() && redis.setNX(transactionProcessingVarName, "0")) {
					jedisLock.setParentHashCode(1);

					// 该红包是否发放过和发放数量是否满足

					if (this.isReceived(grant.getId(), memberId, Constant.NO)) {
						resDto.setCode(PrmsConstant.SENDEDERROR);
						resDto.setMessage(PrmsConstant.SENDERROR);
						redis.remove(transactionProcessingVarName);
						return resDto;
					}
					logger.info("获取优惠券已发放数量" + grant.getId());
					Integer oldSendNum = this.getRedisCouponSendedNum(grant.getId());
					logger.info("获取优惠券已发放数量：" + grant.getId() + "==" + oldSendNum);
					if (oldSendNum >= count && !this.isReceived(grant.getId(), memberId, Constant.NO)) {
						resDto.setCode(PrmsConstant.SENDCOUNTERROR);
						resDto.setMessage(PrmsConstant.SENDERROR);
						redis.remove(transactionProcessingVarName);
						return resDto;
					}
					// 表示可以发发放 存入缓存留待验证
					redis.set(PrmsConstant.getRedisMemberIdSend(grant.getId(), memberId), "1");
					logger.info("去统计优惠券已经发放数量" + grant.getId());
					redis.increment(PrmsConstant.getCouponSendedNum(grant.getId()), 1);
					redis.remove(transactionProcessingVarName);
					jedisLock.release();
				} else {
					logger.error("lock time out");
					resDto.setCode(ResultDto.BUSINESS_ERROR_CODE);
					resDto.setMessage(com.ffzx.promotion.api.dto.constant.Constant.getRedpackageStr(grant.getName()));
				}
			} catch (Exception e) {
				logger.error("redis error", e);
				resDto.setCode(ResultDto.BUSINESS_ERROR_CODE);
				resDto.setMessage(com.ffzx.promotion.api.dto.constant.Constant.getRedpackageStr(grant.getName()));
				// 异常时当锁对象的父hashCode不为空时，则释放业务执行标识。
				deleteReisLock(transactionProcessingVarName, jedisLock);
				// 异常解锁
				releaseLock(jedisLock, com.ffzx.promotion.api.dto.constant.Constant.EXCEPTIONLOCK);
				throw new ServiceException(e);
			} finally {
				// 超时解锁。
				releaseLock(jedisLock, com.ffzx.promotion.api.dto.constant.Constant.CHAOSHILOCK);
			}
		}
		return resDto;
	}

	/***
	 * 获取优惠券发放总量
	 * 
	 * @param grantId
	 * @return
	 */
	private Integer getRedisCouponGrantNum(String grantId) {
		Integer sendCount = 0;
		try {
			if (StringUtils.isNotEmpty(grantId)) {
				String key = PrmsConstant.getCouponGrantNum(grantId);
				Object count = redis.get(key);
				if (null != count) {
					sendCount = Integer.parseInt(count.toString());
				} else {
					// DB获取优惠券发放总量
					CouponGrant grant = this.couponGrantMapper.selectByPrimaryKey(grantId);
					if (null != grant) {
						sendCount = grant.getGrantNum();
						redis.set(key, sendCount);
					}
				}
			}
		} catch (Exception e) {
			throw new ServiceException(e);
		}

		return sendCount;
	}

	/**
	 * 获取红包发放总量
	 * 
	 * @param redpackageId
	 * @return
	 */
	private Integer getRedisRedpackageGrantNum(String redpackageId) {
		Integer sendCount = 0;
		try {
			if (StringUtils.isNotEmpty(redpackageId)) {
				String key = PrmsConstant.getRedpackageGrantNum(redpackageId);
				Object count = redis.get(key);
				if (null != count) {
					sendCount = Integer.parseInt(count.toString());
				} else {
					// DB获取红包发放总量
					Redpackage age = this.redpackageMapper.selectByPrimaryKey(redpackageId);
					if (null != age) {
						sendCount = age.getGrantNum();
						redis.set(key, sendCount);
					}
				}
			}
		} catch (Exception e) {
			throw new ServiceException(e);
		}

		return sendCount;
	}

	/**
	 * 获取优惠券已经发放的数量
	 * 
	 * @param grantId
	 * @return
	 */
	private Integer getRedisCouponSendedNum(String grantId) {
		Integer sendedCount = 0;
		try {
			if (StringUtils.isNotEmpty(grantId)) {
				String key = PrmsConstant.getCouponSendedNum(grantId);
				Object count = redis.incGet(key);
				if (null != count) {
					sendedCount = Integer.parseInt(count.toString());
				} else {
					// DB获取优惠券已发放数量
					Integer sendedcount = this.getDBReceiveCountByType(grantId, Constant.YES, null);
					if (null != sendedcount) {
						sendedCount = sendedcount;
						redis.increment(key, sendedCount);
					}
				}
			}
		} catch (Exception e) {
			throw new ServiceException(e);
		}
		return sendedCount;
	}

	/**
	 * 获取红包已经发放的数量
	 * 
	 * @param redpackageId
	 * @param grantId
	 * @return
	 */
	private Integer getRedisRedpackageSendedNum(String redpackageId, String grantId) {
		Integer sendedCount = 0;
		try {
			if (StringUtils.isNotEmpty(redpackageId)) {
				String key = PrmsConstant.getRedpackSendedNum(redpackageId);
				Object count = redis.incGet(key);
				if (null != count) {
					sendedCount = Integer.parseInt(count.toString());
				} else {
					// DB获取红包已发放数量
					Integer sendedcount = this.getDBReceiveCountByType(grantId, Constant.NO, redpackageId);
					if (null != sendedcount) {
						sendedCount = sendedcount;
						redis.increment(key, sendedCount);
					}
				}
			}
		} catch (Exception e) {
			throw new ServiceException(e);
		}

		return sendedCount;
	}

	private void releaseLock(RedisLock jedisLock, String value) {
		if (jedisLock != null && jedisLock.isLocked()) {
			jedisLock.release();
			logger.info(value);
		}
	}

	private void deleteReisLock(String transactionProcessingVarName, RedisLock jedisLock) {
		if (redis != null && jedisLock.getParentHashCode() != 0) {
			redis.remove(transactionProcessingVarName);
		}
	}

	/**
	 * 天降优惠券满足条件的发放任务
	 * 
	 * @param grantList
	 * @param memberId
	 * @return
	 */
	private CouponGrant getSkyCouponGrantValue(String memberId) {
		// 参数
		Map<String, Object> couponparams = getCouponMapValue(memberId, PrmsConstant.skycoupon);
		// 去验证是否在任务中
		List<CouponGrant> grantList = this.couponGrantService.findByBiz(couponparams);
		// 验证是否发放过之后的任务
		List<CouponGrant> newGrantiList = this.getCounponEnoughList(grantList, memberId);
		// 标签集合
		List<String> idStr = getCouponGrantIdStr(newGrantiList);
		if (this.isList(idStr)) {
			Map<String, Object> labelMap = new HashMap<String, Object>();
			labelMap.put("couponIdStr", idStr);
			// 根据满足条件的优惠券发放ID集合获取用户标签集合
			List<UserLable> labelList = this.getLableList(labelMap);
			List<CouponGrant> newgrantList = new ArrayList<CouponGrant>();

			if (this.isList(labelList)) {
				for (CouponGrant grant : newGrantiList) {
					// 验证是否存在选择的用户标签
					boolean isMember = this.memberIsGrant(memberId,
							this.getUserLableIdStr(grant.getId(), labelList, Constant.YES));
					if (isMember) {
						newgrantList.add(grant);
					}
				}
			}

			// 验证发放数量是否足够
			if (this.isList(newgrantList)) {
				for (CouponGrant grant : newgrantList) {
					// 未发放的任务发放数量是否够
					logger.info("开始验证优惠券是否足够发放" + grant.getId());
					ResultDto resDto1 = this.redisLockCoupon(grant, memberId);
					// 验证成功 验证用户标签
					if (null != resDto1 && resDto1.getCode().equals(ResultDto.OK_CODE)) {
						return grant;
					}
				}
			}
		}
		return null;
	}

	private Member getMember(String memberId) {
		if (StringUtils.isNotEmpty(memberId)) {
			ResultDto m = this.memberApiService.getByIdMember(memberId);
			if (ResultDto.OK_CODE.equals(m.getCode()) && null != m.getData()) {
				Member member = (Member) m.getData();
				return member;
			}
		}
		return null;
	}

	/**
	 * (天降红包，优惠券)根据发放ID集合获取红包发放任务集合
	 * 
	 * @param idStr
	 * @return
	 */
	private ResultDto getSkyPackageList(String memberId) {
		ResultDto resDto = null;
		resDto = new ResultDto(ResultDto.OK_CODE, "success");
		// 满足条件的红包
		CouponGrant coupongrant = null;
		RedpackageGrant packgrant = null;
		try {
			packgrant = this.getSkyPackeGrantValue(memberId);
			if (null != packgrant) {
				List<RedpackageReceive> receiveList = new ArrayList<RedpackageReceive>();
				// 组装红包领取数据
				RedpackageReceive receive = this.getRedPackage(packgrant, memberId);
				receive.setRedpackage(packgrant.getRedpackage());
				receive.setIsReceive(Constant.YES);
				receive.setReceiveDate(new Date());
				receive.setReceiveMode(Constant.COMM_FIXED);
				receiveList.add(receive);
				// 去领取表插入红包发放记录 （已领取，未使用状态）
				toSaveRedpackageReceive(memberId, receiveList);
				resDto = new ResultDto(PrmsConstant.PACKAGESUCC, "package");
				resDto.setData(receive);
			}

			else {
				// 满足条件的优惠券
				coupongrant = this.getSkyCouponGrantValue(memberId);
				List<CouponReceive> receiveList = new ArrayList<CouponReceive>();
				if (null != coupongrant) {
					// 根据发放ID获取管理的优惠券信息集合
					List<CouponAdminCouponGrant> adminList = this.getAdminGrantList(coupongrant.getId());
					if (this.isList(adminList)) {
						for (CouponAdminCouponGrant admin : adminList) {
							BigDecimal effective_date_num = admin.getCouponAdmin().getEffectiveDateNum();// 优惠券有效期天数
							Date receiveDate = new Date();// 领取时间
							// 领取多少天后
							if (StringUtil.isNotNull(effective_date_num)) {
								admin.getCouponAdmin().setEffectiveDateEnd(
										DateUtil.getNextDay(receiveDate, effective_date_num.intValue()));
							}
							CouponReceive receive = this.getCouponReceive(coupongrant, admin, memberId);
							receive.setIsReceive(Constant.YES);
							receiveList.add(receive);
						}
						// 去领取表插入优惠券领取记录
						toSaveCouponReceive(memberId, receiveList);
						resDto = new ResultDto(PrmsConstant.COUPONSUCC, "coupon");
						resDto.setData(adminList);
					}
				}
			}
		} catch (Exception e) {
			// 异常回滚缓存
			if (null != packgrant) {
				redis.decrease(PrmsConstant.getRedpackSendedNum(packgrant.getRedpackageId()), 1);
				redis.remove(PrmsConstant.getRedisMemberIdSend(packgrant.getId(), memberId));
			} else {
				redis.decrease(PrmsConstant.getCouponSendedNum(coupongrant.getId()), 1);
				redis.remove(PrmsConstant.getRedisMemberIdSend(coupongrant.getId(), memberId));
			}
			resDto = new ResultDto(ResultDto.ERROR_CODE, "error");
			throw new ServiceException(e);
		}
		return resDto;
	}

	/**
	 * 插入红包领取记录，失败这回滚缓存
	 * 
	 * @param memberId
	 * @param receiveList
	 * @param grantList
	 */
	private void toSaveRedpackageReceive(String memberId, List<RedpackageReceive> receiveList) {
		try {
			redpackageReceiveMapper.insertManyValue(receiveList);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	/***
	 * 组装红包领取对象
	 * 
	 * @param grant
	 * @param memberId
	 * @return
	 */
	private RedpackageReceive getRedPackage(RedpackageGrant grant, String memberId) {
		if (grant.getRedpackage() != null) {
			Redpackage age = grant.getRedpackage();
			RedpackageReceive receive = new RedpackageReceive();
			Member member = this.getMember(memberId);
			receive.setPhone(member.getAccountNo());
			receive.setId(UUIDGenerator.getUUID());
			receive.setCreateDate(new Date());
			receive.setFackValue(age.getFackValue());
			receive.setIsGrant(Constant.YES);
			receive.setIsReceive(Constant.YES);
			receive.setDelFlag(Constant.NO);
			receive.setRedpackageGrantId(grant.getId());
			receive.setRedpackageId(grant.getRedpackageId());
			receive.setRedpackageNumber(age.getNumber());
			receive.setUseState(Constant.NO);
			receive.setReceiveMode(Constant.YES);
			receive.setMemberId(memberId);
			Integer receivedate = age.getReceivedate();// 自领取后多少天有效
			if (receivedate != null) {
				// 表示领取的红包有效期是从领取开始计算
				Date startDate = new Date();
				receive.setStartDate(startDate);
				receive.setEndDate(DateUtil.getNextDay(startDate, receivedate));
			} else {
				Date startDate = age.getStartDate();// 开始时间
				Date endDate = age.getEndDate();// 结束时间
				receive.setStartDate(startDate);
				receive.setEndDate(endDate);
			}
			return receive;
		}
		return null;
	}

	/**
	 * 验证发消息是否成功
	 * 
	 * @param messageList
	 * @return
	 */
	private boolean saveMessage(List<MemberMessage> messageList, String memberId, List<RedpackageGrant> grantList,
			List<CouponGrant> couponList) {
		boolean flag = false;
		try {
			ResultDto messageDto = this.memberMessageApiService.addMess(messageList);
			if (messageDto.getCode().equals(ResultDto.OK_CODE)) {
				flag = true;
			} else {
				logger.error("saveMessage" + messageDto.getMessage());
				// 插入失败去回滚缓存是否发消息
				removeRedis(memberId, grantList, couponList);
				throw new ServiceException();
			}
		} catch (Exception e) {
			logger.error("saveMessage ", e);
			// 插入失败去回滚缓存是否发消息
			removeRedis(memberId, grantList, couponList);
			throw e;
		}
		return flag;
	}

	private void removeRedis(String memberId, List<RedpackageGrant> grantList, List<CouponGrant> couponList) {
		if (this.isList(grantList)) {
			for (RedpackageGrant grant : grantList) {
				String key = PrmsConstant.getMessageRedis(grant.getId(), memberId);
				redis.remove(key);
			}
		}
		if (this.isList(grantList)) {
			for (CouponGrant grant : couponList) {
				String key = PrmsConstant.getMessageRedis(grant.getId(), memberId);
				redis.remove(key);
			}
		}
	}

	/**
	 * 组装发放ID集合
	 * 
	 * @param grantList
	 * @return
	 */
	private List<String> getPackageGrantList(List<RedpackageGrant> grantList) {
		List<String> idStr;
		idStr = new ArrayList<String>();
		for (RedpackageGrant grant : grantList) {
			idStr.add(grant.getId());
		}
		return idStr;
	}

	/***
	 * 组装消息对象
	 * 
	 * @param pack
	 * @param memberId
	 * @param coupon
	 * @return
	 */
	private MemberMessage getMessage(RedpackageGrant pack, String memberId, CouponGrant coupon) {
		MemberMessage message = new MemberMessage();
		message.setId(UUIDGenerator.getUUID());
		if (pack != null) {
			message.setPakcetGrantId(pack.getId());
			message.setTitle(pack.getName());
			message.setContent(pack.getName() + PrmsConstant.RedPackageMessage);
		}
		if (coupon != null) {
			message.setCouponGrantId(coupon.getId());
			message.setTitle(coupon.getName());
			message.setContent(coupon.getName() + PrmsConstant.COUPONMessage);
		}
		message.setMemberId(memberId);
		message.setStatus(Constant.NO);
		message.setCreateDate(new Date());
		message.setType(MessTypeEnum.PACKETASSISTANT);
		return message;
	}

	/**
	 * 根据类型返回红包或者优惠券用户标签ID集合
	 * 
	 * @param grantId
	 * @param labelList
	 * @param type
	 * @return
	 */
	public List<String> getUserLableIdStr(String grantId, List<?> labelList, String type) {
		List<String> idStr = new ArrayList<String>();
		for (Object lable : labelList) {
			UserLable grant = (UserLable) lable;
			// 说明是红包发放ID
			if (Constant.NO.equals(type)) {
				if (grantId.equals(grant.getRedpackageGrantId())) {
					// 把该红包任务的用户标签ID集合返回
					idStr.add(grant.getLableId());
				}
			} else {
				if (grantId.equals(grant.getCouponGrantId())) {
					// 把该优惠券任务的用户标签ID集合返回
					idStr.add(grant.getLableId());
				}
			}
		}
		return idStr;
	}

	/**
	 * 根据发放ID 获取管理的优惠券信息集合
	 * 
	 * @param grantId
	 * @return
	 */
	private List<CouponAdminCouponGrant> getAdminGrantList(String grantId) {

		return this.couponAdminCouponGrantMapper.selectByGrantId(grantId);
	}

	/***
	 * 根据发放ID获取优惠券发放任务集合
	 * 
	 * @param params
	 * @return
	 */
	private ResultDto getCouponGrantList(Map<String, Object> params, String memberId) {
		ResultDto resDto = null;
		try {
			resDto = new ResultDto(ResultDto.OK_CODE, "success");
			List<CouponGrant> grantList = this.couponGrantMapper.selectByParams(params);
			List<MemberMessage> messageList = new ArrayList<MemberMessage>();
			List<CouponReceive> receiveList = new ArrayList<CouponReceive>();
			// 验证是否发放过之后的任务
			List<CouponGrant> newGrantiList = this.getCounponEnoughList(grantList, memberId);
			// 标签集合
			List<String> idStr = getCouponGrantIdStr(newGrantiList);
			// 验证通过的用户发放任务集合
			List<CouponGrant> isSendList = new ArrayList<CouponGrant>();
			if (this.isList(idStr)) {
				Map<String, Object> labelMap = new HashMap<String, Object>();
				labelMap.put("couponIdStr", idStr);
				// 根据满足条件的优惠券发放ID集合获取用户标签集合
				List<UserLable> labelList = this.getLableList(labelMap);
				if (this.isList(labelList)) {
					// 验证是否在用户标签
					for (CouponGrant grant : newGrantiList) {
						logger.info("验证该用户是否在该优惠券发放任务");
						boolean isMember = this.memberIsGrant(memberId,
								this.getUserLableIdStr(grant.getId(), labelList, Constant.YES));
						if (isMember) {
							isSendList.add(grant);
						}
					}

					if (this.isList(isSendList)) {
						// 组装消息和优惠券领取数据
						labelMap = new HashMap<String, Object>();
						labelMap.put("couponIdStr", getCouponGrantIdStr(isSendList));
						List<CouponAdminCouponGrant> gList = this.couponAdminCouponGrantMapper
								.selectByGrantIdStr(labelMap);
						for (CouponGrant grant : isSendList) {
							ResultDto resDto1 = this.redisLockMessage(memberId,grant.getId(),  Constant.NO);
							if (null != resDto1 && resDto1.getCode().equals(ResultDto.OK_CODE)) {
								setMessageOrRecieveList(memberId, messageList, receiveList, gList, grant);
							}
						}
					}
				}

				// 去插入消息和领取记录
				if (this.isList(messageList)) {
					logger.info("插入优惠券消息数据");
					this.saveMessage(messageList, memberId, null, isSendList);
				}
			}
		} catch (Exception e) {
			resDto = new ResultDto(ResultDto.ERROR_CODE, "error");
			throw new ServiceException(e);
		}
		return resDto;

	}

	private void setMessageOrRecieveList(String memberId, List<MemberMessage> messageList,
			List<CouponReceive> receiveList, List<CouponAdminCouponGrant> gList, CouponGrant grant) {
		// 存在组装消息数据
		logger.info("组装优惠券消息发送和领取数据");
		MemberMessage message = this.getMessage(null, memberId, grant);
		messageList.add(message);
	}

	private List<String> getCouponGrantIdStr(List<CouponGrant> newGrantiList) {
		List<String> idStr = null;
		if (this.isList(newGrantiList)) {
			idStr = new ArrayList<String>();
			idStr = getCouponGrantListByList(newGrantiList);
		}
		return idStr;
	}

	/**
	 * 去插入优惠券领取记录，失败回滚缓存
	 * 
	 * @param memberId
	 * @param grantList
	 * @param receiveList
	 */
	private void toSaveCouponReceive(String memberId, List<CouponReceive> receiveList) {
		try {
			this.couponReceiveMapper.insertManyValue(receiveList);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	/**
	 * 组装优惠券领取记录对象
	 * 
	 * @param grant
	 * @param admin
	 * @param memberId
	 * @return
	 */
	private CouponReceive getCouponReceive(CouponGrant grant, CouponAdminCouponGrant admin, String memberId) {

		Member member = this.getMember(memberId);
		CouponReceive receive = new CouponReceive();
		receive.setId(UUIDGenerator.getUUID());
		receive.setMemberId(memberId);
		receive.setNickName(member.getNickName());
		receive.setPhone(member.getPhone());
		receive.setUseState(Constant.NO);
		receive.setCreateDate(new Date());
		receive.setCouponAdmin(admin.getCouponAdmin());
		receive.setCouponGrant(grant);
		receive.setIsReceive(Constant.YES);
		receive.setReceiveDate(new Date());
		if (null != admin.getCouponAdmin()) {
			CouponAdmin coupon = admin.getCouponAdmin();
			Date effective_date_end = coupon.getEffectiveDateEnd();// 优惠券有效期结束时间
			BigDecimal effective_date_num = coupon.getEffectiveDateNum();// 优惠券有效期天数
			Date receiveDate = new Date();// 领取时间
			if (StringUtil.isNotNull(effective_date_end)) {
				receive.setOverDate(effective_date_end);
			}
			// 领取多少天后
			if (StringUtil.isNotNull(effective_date_num)) {
				receive.setOverDate(DateUtil.getNextDay(receiveDate, effective_date_num.intValue()));
			}
		}
		return receive;
	}

	/**
	 * 获取优惠券发放ID集合
	 * 
	 * @param grantList
	 * @return
	 */
	private List<String> getCouponGrantListByList(List<CouponGrant> grantList) {
		List<String> idStr;
		idStr = new ArrayList<String>();
		// 先把所有满足条件的优惠券发放任务的ID封装成集合留待查询所有的用户标签
		for (CouponGrant grant : grantList) {
			idStr.add(grant.getId());
		}
		return idStr;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public ResultDto saveRedPackageOrCoupon(String memberId) {

		ResultDto resDto = null;
		try {
			// 获取天降红包或者优惠券对象
			resDto = this.getSkyPackageList(memberId);
		} catch (Exception e) {
			logger.error("获取天降红包消息", e);
			throw new ServiceException(e);
		}
		return resDto;
	}

	private Map<String, Object> getpackageMapValue(String memberId, String type) {
		Map<String, Object> packageparams = new HashMap<String, Object>();
		packageparams.put(Constant.DELTE_FLAG, Constant.DELTE_FLAG_NO);// 未删除
		packageparams.put("grantMode", type);// 发放方式
		packageparams.put("state", Constant.STATUS_EFFECT);// 启用状态
		packageparams.put("nowDate", Constant.STATUS_EFFECT);// 在发放时间之内
		packageparams.put("package", Constant.STATUS_EFFECT);// 红包一些信息
		packageparams.put("orderByField", PrmsConstant.STARTDATE);// 红包一些信息
		packageparams.put("orderBy", "ASC");// 红包一些信息
		packageparams.put("leftReceive", Constant.YES);
		packageparams.put("memberId", memberId);
		return packageparams;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public ResultDto receiveRedPackage(RedpackageReceive redPackage) {
		ResultDto resDto = null;
		RedpackageGrant grant = null;
		try {
			grant = this.redpackageGrantService.findById(redPackage.getRedpackageGrantId());
			if (grant != null) {
				String state = grant.getState();// 方法状态
				String memberId = redPackage.getMemberId();// 当前用户ID
				// 是否在发放时间之内
				boolean isSendTime = isSendTime(grant);

				logger.info("开始验证该红包是否足够发放" + grant.getRedpackageId());
				resDto = this.redisLockRedpackage(grant, memberId);
				// 是否领取过该红包
				boolean isReceive = false;
				if (null != resDto && resDto.getCode().equals(PrmsConstant.SENDEDERROR)) {
					// 说明已经领取过
					isReceive = true;
				}
				// 用户当前未使用的红包是否小于3
				boolean isredpackNum = this.isredpackNum(memberId);
				// 表示第二次抽
				if (isReceive) {
					logger.info("该红包第二次领取：" + grant.getRedpackageId());
					resDto = new ResultDto(PrmsConstant.TWOGET, PrmsConstant.TWOMESSAGE);
					resDto.setData("");
				} else {
					String key = PrmsConstant.getRedpackSendedNum(grant.getRedpackageId());
					boolean isSendCount = true;
					if (null != resDto && resDto.getCode().equals(PrmsConstant.SENDCOUNTERROR)) {
						// 说明发放数量不够发放
						isSendCount = false;
					}
					// 表示抽中 即为领取了
					if (Constant.YES.equals(state) && isSendTime && isredpackNum && isSendCount) {
						logger.info("可以领取红包：" + grant.getRedpackageId() + "==" + redis.incGet(key));
						RedpackageReceive receive = this.getRedPackage(grant, memberId);
						receive.setReceiveDate(new Date());
						// 可以领取插入一条领取记录
						this.redpackageReceiveMapper.insertSelective(receive);
						// 返回抽中的信息
						resDto = new ResultDto(ResultDto.OK_CODE, "success");
						resDto.setData(receive);
					}
					// 表示没抽中的时候
					if (Constant.YES.equals(state) && isSendTime && isSendCount && !isredpackNum) {
						resDto = new ResultDto(PrmsConstant.NOGET, "error");
						resDto.setData("");
						if (isSendCount) {
							redis.decrease(key, 1);
							logger.info("没抽中回滚红包已发放数量之后：" + grant.getRedpackageId() + "==" + redis.incGet(key));
						}
					}
					// 表示已抢光或者过期
					if (Constant.NO.equals(state) || !isSendTime || Constant.YES.equals(grant.getDelFlag())
							|| !isSendCount) {
						resDto = new ResultDto(PrmsConstant.OVER, "error");
						resDto.setData("");
						if (isSendCount) {
							redis.decrease(key, 1);
							logger.info("已抢光或者过期回滚红包已发放数量之后：" + grant.getRedpackageId() + "==" + redis.incGet(key));
						}
					}
				}
			}
		} catch (Exception e) {
			logger.error("拆红包", e);
			if (grant != null) {
				redis.decrease(PrmsConstant.getRedpackSendedNum(grant.getRedpackageId()), 1);
				logger.info("异常回滚红包已发放数量之后：" + grant.getRedpackageId() + "=="
						+ redis.incGet(PrmsConstant.getRedpackSendedNum(grant.getRedpackageId())));
			}
			throw new ServiceException(e);
		}
		return resDto;
	}

	/**
	 * 是否在发放时间之内
	 * 
	 * @param grant
	 * @return
	 */
	private boolean isSendTime(RedpackageGrant grant) {
		boolean flag = false;
		if (grant != null) {
			Date now = new Date();
			if (now.getTime() >= grant.getStartDate().getTime() && now.getTime() < grant.getEndDate().getTime()) {
				flag = true;
			}
		}
		return flag;
	}



	/**
	 * 是否领取过该红包
	 * 
	 * @param memberId
	 * @param grantId
	 * @return
	 */
	private boolean isReceive(String memberId, String grantId) {
		boolean falg = false;
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("memberId", memberId);
		params.put("redpackageGrantId", grantId);
		List<RedpackageReceive> receiveList = this.getRedpackageReceiveList(params);
		if (this.isList(receiveList)) {
			falg = true;
			/*
			 * for (RedpackageReceive receive : receiveList) { // 表示未领取 if
			 * (Constant.NO.equals(receive.getIsReceive())) { falg = true; } }
			 */
		}
		return falg;
	}

	/**
	 * 当前用户未使用的红包数量是否小于3
	 * 
	 * @param memberId
	 * @return
	 */
	private boolean isredpackNum(String memberId) {
		boolean falg = false;
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("memberId", memberId);
		params.put("useState", Constant.NO);
		params.put("isReceive", Constant.YES);
		params.put("grantdu", Constant.STATUS_EFFECT);// 在发放时间之内
		params.put("packageGrant", Constant.STATUS_EFFECT);// 关联发放任务
		List<RedpackageReceive> receiveList = this.getRedpackageReceiveList(params);
		if (this.isList(receiveList)) {
			if (receiveList.size() < 3) {
				falg = true;
			}
		} else {
			falg = true;
		}
		return falg;
	}

	/**
	 * 优惠券是否到发放时间
	 * 
	 * @param grant
	 * @return
	 */
	private boolean isCouponSend(CouponGrant grant) {
		boolean flag = false;
		if (grant != null) {
			Date now = new Date();
			if (now.getTime() > grant.getGrantDate().getTime() && now.getTime() <= grant.getGrantEndDate().getTime()) {
				flag = true;
			}
		}
		return flag;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public ResultDto receiveCoupon(CouponReceive couponReceive) {

		ResultDto resDto = null;
		CouponGrant grant = null;
		try {
			String memberId = couponReceive.getMemberId();// 用户ID
			grant = couponReceive.getCouponGrant();
			grant = this.couponGrantService.findById(grant.getId());// 发放任务
			// 发放状态
			boolean isCouponSend = this.isCouponSend(grant);
			// 验证发放数量是否足够
			logger.info("开始验证优惠券是否足够发放" + grant.getId());
			ResultDto resDto1 = this.redisLockCoupon(grant, memberId);
			boolean isReceive = false;
			if (null != resDto1 && resDto1.getCode().equals(PrmsConstant.SENDEDERROR)) {
				// 说明已经领取过
				isReceive = true;
			}
			if (!isReceive) {
				boolean isSendCount = true;
				if (null != resDto1 && resDto1.getCode().equals(PrmsConstant.SENDCOUNTERROR)) {
					// 说明发放数量不够
					isSendCount = false;
				}
				// 没有领取记录发放数量足够可以立即领取
				if (isCouponSend && isSendCount) {
					List<CouponAdminCouponGrant> couponList = this.couponAdminCouponGrantMapper
							.selectByGrantId(grant.getId());
					List<CouponReceive> receiveList = new ArrayList<CouponReceive>();
					for (CouponAdminCouponGrant admin : couponList) {
						CouponReceive receive = this.getCouponReceive(grant, admin, memberId);
						receiveList.add(receive);
						setEffdateEnd(admin);
					}
					// 批量插入优惠券领取记录
					this.couponReceiveMapper.insertManyValue(receiveList);
					resDto = new ResultDto(ResultDto.OK_CODE, "success");
					resDto.setData(couponList);
				}
				// 已抢完
				else if (!isSendCount || !isCouponSend) {
					resDto = new ResultDto(PrmsConstant.OVER, "已抢完");
					resDto.setData("");
					if (isSendCount) {
						redis.decrease(PrmsConstant.getCouponSendedNum(grant.getId()), 1);
					}
				}
			}
			// 查询到领取记录说明领取过
			else {
				resDto = new ResultDto(PrmsConstant.TWOGET, PrmsConstant.COUPONMESSAGE);
				resDto.setData("");
			}

		} catch (Exception e) {
			logger.error("优惠券领取", e);
			if (grant != null) {
				redis.decrease(PrmsConstant.getCouponSendedNum(grant.getId()), 1);
				logger.info("异常优惠券回滚已发放数量：" + grant.getId() + "=="
						+ redis.incGet(PrmsConstant.getCouponSendedNum(grant.getId())));
			}
			throw new ServiceException(e);
		}
		return resDto;

	}

	private void setEffdateEnd(CouponAdminCouponGrant admin) {
		if (null != admin.getCouponAdmin()) {
			CouponAdmin coupon = admin.getCouponAdmin();
			BigDecimal effective_date_num = coupon.getEffectiveDateNum();// 优惠券有效期天数
			Date receiveDate = new Date();// 领取时间
			// 领取多少天后
			if (StringUtil.isNotNull(effective_date_num)) {
				admin.getCouponAdmin().setEffectiveDateEnd(DateUtil.getNextDay(receiveDate, effective_date_num.intValue()));
			}
		}
	}

	@Override
	public ResultDto getRedPackageList(Map<String, Object> params) {
		ResultDto resDto = null;
		try {
			params.put("packageReceive", Constant.NO);
			List<RedpackageReceive> list = this.getRedpackageReceiveList(params);
			resDto = new ResultDto(ResultDto.OK_CODE, "success");
			if (this.isList(list)) {
				resDto.setData(list);
			}
		} catch (Exception e) {
			logger.error("获取我的红包", e);
			throw new ServiceException(e);
		}
		return resDto;
	}

	@Override
	public ResultDto getOptimumRedPackage(Map<String, Object> params) {

		ResultDto resDto = null;
		try {
			
			//获取商品总价格
			BigDecimal actPrice = new BigDecimal(0);// 订单金额
			if (null != params.get("goodsList")) {
				actPrice=this.getOrderPrice( (List<Map<String, Object>>) params.get("goodsList"));
				logger.info("计算下单商品总金额：" + actPrice);
			}
			if (null != params.get("couponPrice") && null != params.get("memberId")) {
				String memberId = params.get("memberId").toString();// 用户ID
				// 获取最优方案
				if(actPrice.compareTo(new BigDecimal(params.get("couponPrice").toString()))>0){
					BigDecimal price=actPrice.subtract(new BigDecimal(params.get("couponPrice").toString()));
					logger.info("去掉优惠券金额之后：" + price);
					resDto = getBestRedpackage(memberId, price);
				}
				
			}
		} catch (Exception e) {
			resDto = new ResultDto(ResultDto.ERROR_CODE, "error");
			logger.error("可用红包列表和最优方案", e);
			throw new ServiceException(e);
		}
		return resDto;
	}

	private BigDecimal getOrderPrice(List<Map<String, Object>> lMaps){
		BigDecimal price=new BigDecimal(0);
		try {
			List<String> skuIdList=new ArrayList<String>();//skuId集合表
			Map<String, Object> skuIdkeyValue=new HashMap<String,Object>();
			getSkuIdsAmount(lMaps,skuIdList,skuIdkeyValue);//填充skuId List 和key为skuId，value为Map<String,Object>			
			List<CommoditySku> skusList=commoditySkuApiConsumer.getListCommoditySkus(skuIdList);
			Map<String, Object> mapDouble=new HashMap<String, Object>();//skuId和价格*num
			//获取每个商品对应价格
			logger.info("去获取商品价格map");
			getPriceMapNew(mapDouble,skusList,skuIdkeyValue);
			price=getMapDoubleNum(mapDouble);//总金额
		} catch (Exception e) {
			logger.error("异常"+e);
		}				
		return price;
	}
	
	private BigDecimal getMapDoubleNum(Map<String, Object> mapDouble){
		BigDecimal price=new BigDecimal(0);
		for (String key : mapDouble.keySet()) {
			Map<String, Object> skuvalue=(Map<String, Object>) mapDouble.get(key);
			if(null!=(skuvalue.get("price"))){
				price=price.add(new BigDecimal(skuvalue.get("price").toString()));
			}
			
		}
		return price.setScale(2, BigDecimal.ROUND_HALF_UP);
	}
	
	private void getSkuIdsAmount(List<Map<String, Object>> lMaps,List<String> skuIdList
			,Map<String, Object> skuIdkeyValue){

		if(lMaps!=null && lMaps.size()>0){
			for (Map<String, Object> mapAmonut : lMaps) {
				if(mapAmonut.get("skuId")==null){
					throw new CallInterfaceException("skuId 不可为null");
				}
				skuIdList.add(mapAmonut.get("skuId").toString());
				Map<String, Object> message=new HashMap<String, Object>();
				message.put("num", Integer.parseInt(mapAmonut.get("buycount").toString()));
				message.put("goodsType", mapAmonut.get("goodsType"));
				message.put("activityId", mapAmonut.get("activityId"));
				skuIdkeyValue.put(mapAmonut.get("skuId").toString(), message);
			}
		}
	}
	
	private void getPriceMapNew(Map<String, Object> mapDouble,List<CommoditySku> skusList,
			Map<String, Object> skuIdkeyValue){	
		List<Object> activityIdList=new ArrayList<Object>();//除去批发所有活动id
		Map<String,Object> pifaMap=new HashMap<String,Object>();;//批发所有活动id
		for (CommoditySku commoditySku : skusList) {
			//根据商品skuId，获取数量，还有goodsType，activityId
			Map<String,Object> value=(Map<String, Object>) skuIdkeyValue.get(commoditySku.getId());
			Object goodsTypeObject=value.get("goodsType");
			String goodsType="";
			if(goodsTypeObject!=null)
				goodsType=goodsTypeObject.toString();
			int num=(Integer)value.get("num");
			if(StringUtil.isEmpty(goodsType) || goodsType.equals("COMMON_BUY")){//普通商品
				//skuIdkeyValue  添加商品节本信息 skuId为key，商品id，类目id，价格
				putskuIdkeyValue(commoditySku, num*commoditySku.getFavourablePrice().doubleValue(), mapDouble);
			}else if(goodsType.equals(ActivityTypeEnum.WHOLESALE_MANAGER.getValue())){//如果是批发
				String  activityId=(String)value.get("activityId");
				Map<String, Object> pafavalue=new HashMap<String, Object>();//统计一个商品包含多个sku的总数量
				pafavalue.put("commoditySku", commoditySku);
				pafavalue.put("num", num);
				pifaMap.put(commoditySku.getId(), pafavalue);
			}else{
				String  activityId=(String)value.get("activityId");
				Map<String, String> activitymap=new HashMap<String, String>();
				activitymap.put("activityId", activityId);
				activitymap.put("skuId", commoditySku.getId());
				activityIdList.add(activitymap);
			}
		}
		//获取非活动批发数据的价格集合
		if(activityIdList!=null && activityIdList.size()>0){
			List<ActivityCommoditySku> activityCommoditySkus=  activityApiConsumer.selectActivitySkuPrice(activityIdList);
			//根据sku活动集合获取skuIdkeyValue的数据
			getActivitySkusputskuIdkeyValueNew(activityCommoditySkus, skuIdkeyValue, skusList,mapDouble);
		}
		//获取批发的价格
		if(pifaMap!=null && pifaMap.size()>0){
			getPifaSkuputskuIdkeyValue(pifaMap,mapDouble);
		}
	}
	
	private void  getActivitySkusputskuIdkeyValueNew(List<ActivityCommoditySku> activityCommoditySkus,Map<String, Object> skuIdkeyValue
			,List<CommoditySku> skusList,Map<String, Object> mapDouble){
		for (ActivityCommoditySku activityCommoditySku : activityCommoditySkus) {
			//活动管理id，activityCommoditySku.getActivityCommodity().getActivityId();
			String activityManageId = null;
			CommoditySku csku=null;
			for (CommoditySku commoditySku : skusList) {
				if(activityCommoditySku.getCommoditySkuId().equals(commoditySku.getId())){
					activityManageId=activityCommoditySku.getActivity().getId();//判断正确的商品管理id
					csku=commoditySku;
					break;
				}
			}
			Map<String,Object> value=(Map<String, Object>) skuIdkeyValue.get(activityCommoditySku.getCommoditySkuId());
			int num=(Integer)value.get("num");
			putskuIdkeyValueActivityManage(csku, activityCommoditySku.getActivityPrice().doubleValue()*num, mapDouble,activityManageId);
		}
		
	}
	
	private void putskuIdkeyValueActivityManage(CommoditySku commoditySku,double price,Map<String, Object> mapDouble
			,String activityManageId){

		//skuId为key，商品id，类目id，价格
		Map<String, Object> skuvalue=new HashMap<String, Object>();
		skuvalue.put("goodsId", commoditySku.getCommodity().getId());
		skuvalue.put("cid", commoditySku.getCommodity().getCategoryId());
		skuvalue.put("activityManageId", activityManageId);
		
		skuvalue.put("price", price);
		mapDouble.put(commoditySku.getId(), skuvalue);
	}
	
	private void putskuIdkeyValue(CommoditySku commoditySku,double price,Map<String, Object> mapDouble){

		//skuId为key，商品id，类目id，价格
		Map<String, Object> skuvalue=new HashMap<String, Object>();
		skuvalue.put("goodsId", commoditySku.getCommodity().getId());
		skuvalue.put("cid", commoditySku.getCommodity().getCategoryId());
		
		skuvalue.put("price", price);
		mapDouble.put(commoditySku.getId(), skuvalue);
	}
	
	private void getPifaSkuputskuIdkeyValue(Map<String,Object> pifaMap,Map<String, Object> mapDouble){
		Map<String,Integer> pifaMapGoods=new  HashMap<String, Integer>();//统计同一个商品批发数量,key 商品code
		Map<String,BigDecimal> pifaMapGoodsPrice=new  HashMap<String, BigDecimal>();//统计同一个商品批发价格  key 商品code
		for (String key : pifaMap.keySet()) {
			Map<String,Object> pifaMapitem=(Map<String, Object>) pifaMap.get(key);
			CommoditySku commoditySku=(CommoditySku) pifaMapitem.get("commoditySku");
			Integer num=(Integer) pifaMapitem.get("num");
			if(pifaMapGoods.keySet().contains(commoditySku.getCommodity().getCode())){//判断是否重复的商品编码
				Integer numcontains=pifaMapGoods.get(commoditySku.getCommodity().getCode());//获取存在Map
				pifaMapGoods.put(commoditySku.getCommodity().getCode(), numcontains+num);
			}else{
				pifaMapGoods.put(commoditySku.getCommodity().getCode(), num);//某个商品的数量
			}
		}
		for (String key  : pifaMapGoods.keySet()) {//计算价格，赋予value
			BigDecimal wahlePrice=activityApiConsumer.getPifaPrice(key, pifaMapGoods.get(key));//商品编码和数量
			pifaMapGoodsPrice.put(key, wahlePrice);
		}
		for (String key : pifaMap.keySet()) {
			Map<String,Object> pifaMapitem=(Map<String, Object>) pifaMap.get(key);
			CommoditySku commoditySku=(CommoditySku) pifaMapitem.get("commoditySku");
			BigDecimal wahlePrice=pifaMapGoodsPrice.get(commoditySku.getCommodity().getCode());
			int num=(Integer)pifaMapitem.get("num");
			putskuIdkeyValue(commoditySku, wahlePrice.doubleValue()*num, mapDouble);
		}
	}
	
	/**
	 * 获取最优的金额
	 * 
	 * @param list
	 * @param price
	 * @return
	 */
	private ResultDto getBestRedpackage(String memberId, BigDecimal price) {
		ResultDto dto = null;
		try {
			dto = new ResultDto(ResultDto.OK_CODE, "success");
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("memberId", memberId);
			params.put("useState", Constant.NO);
			params.put("isReceive", Constant.YES);
			params.put("ondue", Constant.STATUS_EFFECT);// 在有效期内
			params.put("packageReceive", Constant.STATUS_EFFECT);
			List<RedpackageReceive> list = this.getRedpackageReceiveList(params);
			if (this.isList(list)) {

				BigDecimal redpackagePrice = this.getredPackagePrice(list);
				BigDecimal math = new BigDecimal(0);// 方案省多少元
				// 第一种组合全部红包验证是否满足使用条件
				if (redpackagePrice.compareTo(price) == -1) {
					math = redpackagePrice;
					this.getOnePaceageBestPrice(math, dto, list);
					logger.info("全部红包使用组合" + math);
					return dto;
				}
				// 第二种方案红包组合验证
				else if (list.size() - 1 > 0) {
					logger.info("其他组合");
					for (int i = list.size() - 1; i <= list.size() - 1; i--) {
						ResultBestRedpackValue resultValue = this.getMathredpackPrice(dto, list, i, price);
						if (resultValue != null) {
							if (Constant.NO.equals(resultValue.getFlag())) {
								logger.info("下单允许使用的红包组合" + JsonMapper.toJsonString(resultValue.getList()));
								dto.setData(resultValue.getList());
								return dto;
							}
						}
					}
				}
			}
		} catch (Exception e) {
			dto = new ResultDto(ResultDto.ERROR_CODE, "error");
			logger.error("获取最优金额", e);
			throw new ServiceException(e);
		}
		return dto;
	}

	private List<RedpackageReceive> getSelectRedpackageRecieve(List<RedpackageReceive> list,
			List<RedpackageReceive> newList, BigDecimal price) {

		for (RedpackageReceive newreceive : newList) {
			newreceive.setBestPrice(price.toString());
			newreceive.setIsSelected(Constant.YES);
		}
		return list;
	}

	private BigDecimal getredPackagePrice(List<RedpackageReceive> list) {
		BigDecimal price = new BigDecimal(0);
		if (list != null && list.size() != 0) {
			for (RedpackageReceive receive : list) {
				price = price.add(receive.getFackValue());
			}
		}
		return price;
	}

	private ResultDto getOnePaceageBestPrice(BigDecimal price, ResultDto dto, List<RedpackageReceive> newReceiveList) {
		for (RedpackageReceive re : newReceiveList) {
			re.setBestPrice(price.toString());
			re.setIsSelected(Constant.YES);
		}
		dto.setData(newReceiveList);
		return dto;
	}

	@Override
	public ResultDto getRedpackageDetail(Map<String, Object> params) {
		ResultDto resDto = null;
		try {
			resDto = new ResultDto(ResultDto.OK_CODE, "success");
			String grantId = params.get("grantId").toString();
			String memberId = params.get("memberId").toString();
			if (Constant.YES.equals(params.get("type").toString())) {
				RedpackageGrant grant = this.redpackageGrantService.findById(grantId);
				if (grant != null) {
					Integer count = this.getRedisRedpackageSendedNum(grant.getRedpackageId(), grantId);
					Redpackage pack = grant.getRedpackage();
					grant.setReceiveCount(count);
					// 验证是否拆过
					if (this.isReceived(grantId, memberId, Constant.YES)) {			
						//拆过看是否抽中
						params.put("redpackageGrantId", grantId);
						List<RedpackageReceive> receiveList = this.getRedpackageReceiveList(params);
						if(this.isList(receiveList)){
							//存在说明已经抽中
							grant.setIsReceive(Constant.YES);
						}else{
							//不存在说明没抽中或者已抢光
							grant.setIsReceive(Constant.NO);
						}						
						grant.setIsGrant(Constant.YES);
					} else {
						grant.setIsReceive(Constant.NO);
						grant.setIsGrant(Constant.NO);
					}
					if (pack.getReceivedate() != null) {
						// 表示领取的红包有效期是从领取开始计算
						Date startDate = new Date();
						pack.setStartDate(startDate);
						pack.setEndDate(DateUtil.getNextDay(startDate, pack.getReceivedate()));
					}
					resDto.setData(grant);
				}
			} else {
				// 优惠券集合
				List<CouponAdminCouponGrant> list = this.getAdminGrantList(grantId);
				CouponGrant g = this.couponGrantService.findById(grantId);
				params.put("couponGrant", g);
				List<CouponReceive> receiveList = this.getCouponReceiveList(params);
				Integer count = this.getRedisCouponSendedNum(grantId);
				logger.info("详情拿到该任务:" + g.getId() + "已经发放数量：" + count);
				for (CouponAdminCouponGrant grant : list) {
					grant.setGrantNum(g.getGrantNum());
					grant.setCouponGrant(g);
					grant.setReceiveNum(count);
					// 验证是否第二次领取
					if (this.isReceived(grantId, memberId, Constant.NO)){
						//拆过验证是否抽中
						if(this.isList(receiveList)){
							//存在领取记录说明抽中
							grant.setIsReceive(Constant.YES);
						}else{
							//说明没抽中或者已抢光
							grant.setIsReceive(Constant.NO);
						}												
					} else {
						grant.setIsReceive(Constant.NO);
					}
					setEffdateEnd(grant);
				}
				if (this.isList(list)) {
					resDto.setData(list);
				}
			}
		} catch (Exception e) {
			logger.error("获取活动详情", e);
			throw new ServiceException(e);
		}
		return resDto;
	}

	@Override
	public ResultDto getReceiveCount(String grantId, String type) {

		ResultDto resDto = null;
		try {
			Integer count = this.getRedisReceiveCountByType(grantId, type);
			resDto = new ResultDto(ResultDto.OK_CODE, "success");
			resDto.setData(count);
		} catch (Exception e) {
			logger.error("获取发放数量", e);
			throw new ServiceException(e);
		}
		return resDto;
	}

	/***
	 * 缓存获取发放数量
	 * 
	 * @param grantId
	 * @param type
	 * @return
	 */
	private Integer getRedisReceiveCountByType(String grantId, String type) {
		Integer count = 0;
		if (StringUtils.isNotEmpty(grantId)) {
			if (Constant.YES.equals(type)) {
				count = getRedisCouponSendedNum(grantId);
			}
		}
		return count;
	}

	/**
	 * DB获取发放数量
	 * 
	 * @param grantId
	 * @param type
	 * @return
	 */
	private Integer getDBReceiveCountByType(String grantId, String type, String redpackageId) {
		Integer count = 0;
		if (StringUtils.isNotEmpty(grantId)) {
			Map<String, Object> params = null;
			if (Constant.YES.equals(type)) {
				params = new HashMap<String, Object>();
				params.put("redpackageGrantId", grantId);
				count = this.redpackageReceiveMapper.selectCount(params);
				redis.increment(PrmsConstant.getRedpackSendedNum(redpackageId), count);
			} else {
				params = new HashMap<String, Object>();
				params.put("redpackageGrantId", grantId);
				List<CouponReceive> list = this.couponReceiveMapper.selectReceiveCountGroup(grantId);
				redis.increment(PrmsConstant.getCouponSendedNum(grantId), list.size());
			}
		}
		return count;
	}

	/**
	 * @param a:组合数组
	 * @param m:生成组合长度
	 * @return :所有可能的组合数组列表
	 */
	private List<int[]> combination(int[] a, int m) {
		List<int[]> list = new ArrayList<int[]>();
		int n = a.length;
		boolean end = false; // 是否是最后一种组合的标记
		// 生成辅助数组。首先初始化，将数组前n个元素置1，表示第一个组合为前n个数。
		int[] tempNum = new int[n];
		for (int i = 0; i < n; i++) {
			if (i < m) {
				tempNum[i] = 1;

			} else {
				tempNum[i] = 0;
			}
		}
		list.add(createResult(a, tempNum, m));// 打印第一种默认组合
		int k = 0;// 标记位
		while (!end) {
			boolean findFirst = false;
			boolean swap = false;
			// 然后从左到右扫描数组元素值的"10"组合，找到第一个"10"组合后将其变为"01"
			for (int i = 0; i < n; i++) {
				int l = 0;
				if (!findFirst && tempNum[i] == 1) {
					k = i;
					findFirst = true;
				}
				if (tempNum[i] == 1 && tempNum[i + 1] == 0) {
					tempNum[i] = 0;
					tempNum[i + 1] = 1;
					swap = true;
					for (l = 0; l < i - k; l++) { // 同时将其左边的所有"1"全部移动到数组的最左端。
						tempNum[l] = tempNum[k + l];
					}
					for (l = i - k; l < i; l++) {
						tempNum[l] = 0;
					}
					if (k == i && i + 1 == n - m) {// 假如第一个"1"刚刚移动到第n-m+1个位置,则终止整个寻找
						end = true;
					}
				}
				if (swap) {
					break;
				}
			}
			list.add(createResult(a, tempNum, m));// 添加下一种默认组合
		}
		return list;
	}

	// 根据辅助数组和原始数组生成结果数组
	private int[] createResult(int[] a, int[] temp, int m) {
		int[] result = new int[m];
		int j = 0;
		for (int i = 0; i < a.length; i++) {
			if (temp[i] == 1) {
				result[j] = a[i];
				j++;
			}
		}
		return result;
	}

	/**
	 * 得到已经计算之后的红包集合的某个元素对象
	 * 
	 * @param j
	 * @param list
	 * @return
	 */
	private RedpackageReceive getS(int j, List<RedpackageReceive> list) {
		for (int i = 0; i < list.size(); i++) {
			if (i == j) {
				return list.get(i);
			}
		}
		return null;
	}

	/**
	 * 根据已经计算之后的下标得到红包集合
	 * 
	 * @param j
	 * @param list
	 * @return
	 */
	private List<RedpackageReceive> getStr(int[] j, List<RedpackageReceive> list) {
		List<RedpackageReceive> rece = new ArrayList<RedpackageReceive>();
		for (int i = 0; i < j.length; i++) {
			rece.add(getS(j[i], list));
		}
		return rece;
	}

	/**
	 * 获取每个数字的红包组合集合（比如9个红包的组合集合，8个红包的组合集合）
	 * 
	 * @param index
	 * @param strList
	 * @param size
	 * @return
	 */
	private List<List<RedpackageReceive>> getMathRedpackageReceiveList(int index, List<RedpackageReceive> strList,
			int size) {
		int[] a = new int[index];
		List<List<RedpackageReceive>> newReceiveList = new ArrayList<List<RedpackageReceive>>();
		for (int i = 0; i < strList.size(); i++) {
			a[i] = i;
		}
		List<int[]> list = this.combination(a, size);
		for (int[] f : list) {
			List<RedpackageReceive> v = getStr(f, strList);
			newReceiveList.add(v);
		}
		return newReceiveList;
	}

	/**
	 * 获得每个数字组合最省钱的红包集合 并且设置为被选中
	 * 
	 * @param dto
	 * @param list
	 * @param size
	 * @param price
	 * @return
	 */
	private ResultBestRedpackValue getMathredpackPrice(ResultDto dto, List<RedpackageReceive> list, int size,
			BigDecimal price) {
		String flag = Constant.YES;
		// 获取某个数字为头的红包组合集合
		List<List<RedpackageReceive>> value = this.getMathRedpackageReceiveList(list.size(), list, size);
		// 计算该组合最省钱的方案组合
		List<MathRedpackagePrice> mathList = new ArrayList<MathRedpackagePrice>();
		// 遍历计算后的满足条件的集合 封装成新的集合
		for (List<RedpackageReceive> a : value) {
			if (this.getredPackagePrice(a).compareTo(price) == -1) {
				MathRedpackagePrice p = new MathRedpackagePrice();
				p.setList(a);
				p.setPrice(this.getredPackagePrice(a));
				mathList.add(p);
			}
		}
		ResultBestRedpackValue best = null;
		// 对新的集合计算的省钱排序
		if (this.isList(mathList)) {
			logger.info(size + "个组合的可用红包组合集合" + JsonMapper.toJsonString(mathList));
			Collections.sort(mathList, new Comparator<MathRedpackagePrice>() {
				@Override
				public int compare(MathRedpackagePrice o1, MathRedpackagePrice o2) {
					logger.info("每个组合红包金额从大到小排序" + o1.getPrice() + o1.getPrice());
					return o2.getPrice().compareTo(o1.getPrice());
				}
			});
			// 返回该组合下最省钱的红包组合
			MathRedpackagePrice math = mathList.get(0);
			flag = Constant.NO;
			// 设置最省钱的组合为被选中
			this.getSelectRedpackageRecieve(list, math.getList(), math.getPrice());
			best = new ResultBestRedpackValue();
			best.setFlag(flag);
			best.setList(math.getList());
			logger.info(size + "个组合的可用红包组合" + JsonMapper.toJsonString(math.getList()));
		}

		return best;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public ResultDto updateReceiveStatus(Map<String, Object> map) {
		ResultDto resDto = null;
		try {
			if (null != map.get("redpackageUseIds")) {
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("receiveIds", this.getListStr(map.get("redpackageUseIds").toString()));
				List<RedpackageReceive> list = this.getRedpackageReceiveList(params);
				if (this.isList(list)) {
					for (RedpackageReceive receive : list) {
						// 支付成功修改是否支付状态
						if (Constant.YES.equals(map.get("type").toString())) {
							logger.info("支付成功的日志：" + receive.getId() + "==" + map.get("redpackageUseIds"));
							receive.setIsPay(Constant.YES);
							receive.setOrderPrice(new BigDecimal(map.get("orderPrice").toString()));
						} else if (Constant.NO.equals(map.get("type").toString())) {
							logger.info("下单成功的日志：" + receive.getId() + "==" + map.get("redpackageUseIds"));
							// 下单修改是否使用状态
							receive.setUseDate(new Date());
							receive.setUseState(Constant.YES);
						} else if (Constant.COMM_FIXED.equals(map.get("type").toString())) {
							logger.info("取消订单的日志：" + receive.getId() + "==" + map.get("redpackageUseIds"));
							// 取消订单
							receive.setIsPay(Constant.NO);
							receive.setUseState(Constant.NO);
							receive.setOrderPrice(new BigDecimal(map.get("orderPrice").toString()));
						}
						this.redpackageReceiveMapper.updateByPrimaryKeySelective(receive);
					}
					resDto = new ResultDto(ResultDto.OK_CODE, "success");
				}
			}
		} catch (Exception e) {
			resDto = new ResultDto(ResultDto.ERROR_CODE, "error");
			logger.error("修改红包使用状态和支付状态", e);
			throw new ServiceException(e);
		}
		return resDto;
	}

	private List<String> getListStr(String redpackageId) {
		List<String> listStr = new ArrayList<String>();
		if (StringUtils.isNotEmpty(redpackageId)) {
			for (String id : redpackageId.split(";")) {
				if (StringUtils.isNotEmpty(id)) {
					listStr.add(id);
				}
			}
		}
		return listStr;
	}

}
