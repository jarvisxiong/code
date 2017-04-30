package com.ffzx.promotion.util;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ffzx.commerce.framework.constant.RedisPrefix;
import com.ffzx.commerce.framework.dto.ResultDto;
import com.ffzx.commerce.framework.exception.ServiceException;
import com.ffzx.commerce.framework.utils.RedisUtil;
import com.ffzx.commerce.framework.utils.StringUtil;
import com.ffzx.order.api.service.OrderApiService;

public class RedisCountUtil {

	private static Logger logger = LoggerFactory.getLogger(RedisCountUtil.class);
	/***
	 * activityCommodityItemId 活动商品ID memberId 用户id commodityNo 活动商品编码
	 */
	public static Object getRedisCount(Map<String, Object> map,RedisUtil redis) throws ServiceException {
		Object count = null;
		try {
			String activityCommodityItemId = null;
			String memeberId = null;
			String commodityNo = null;
			if (map.get("activityCommodityItemId") != null) {
				activityCommodityItemId = map.get("activityCommodityItemId").toString();
			}

			if (map.get("commodityNo") != null) {
				commodityNo = map.get("commodityNo").toString();
			}
			if (map.get("memberId") != null) {
				memeberId = map.get("memberId").toString();
				if (redis.exists(RedisPrefix.APP_PAY_BUYNUM + memeberId + "_" + activityCommodityItemId + "_"
						+ commodityNo + "_buyNum")) {
					count = redis.incGet(RedisPrefix.APP_PAY_BUYNUM + memeberId + "_" + activityCommodityItemId + "_"
							+ commodityNo + "_buyNum");
				}
			} else {
				if (redis
						.exists(RedisPrefix.APP_PAY_BUYNUM + activityCommodityItemId + "_" + commodityNo + "_buyNum")) {
					count = redis
							.incGet(RedisPrefix.APP_PAY_BUYNUM + activityCommodityItemId + "_" + commodityNo + "_buyNum");
				}
			}

		} catch (Exception e) {
			logger.error("", e);
			// throw e;
		}
		return count;
	}
	
	public static int getActivityCommodityBuyNum(Map<String, Object> map,RedisUtil redis,OrderApiService orderApiService) throws ServiceException {
		int count = 0;
		try {
			map.put("status", "YES");
			ResultDto res = orderApiService.selectBuyCount(map);
			if (StringUtil.isNotNull(res.getData())) {
				count = (int) res.getData();
				if (StringUtil.isNotNull(map.get("memberId"))) {
					redis.increment(RedisPrefix.APP_PAY_BUYNUM + map.get("memberId") + "_"
							+ map.get("activityCommodityItemId") + "_" + map.get("commodityNo") + "_buyNum", count);
					/*redis.incSet(RedisPrefix.APP_PAY_BUYNUM + map.get("memberId") + "_"
							+ map.get("activityCommodityItemId") + "_" + map.get("commodityNo") + "_buyNum",
							String.valueOf(count));*/
				} else {
					/*redis.incSet(RedisPrefix.APP_PAY_BUYNUM + map.get("activityCommodityItemId") + "_"
							+ map.get("commodityNo") + "_buyNum", String.valueOf(count));*/
					redis.increment(RedisPrefix.APP_PAY_BUYNUM + map.get("activityCommodityItemId") + "_"
							+ map.get("commodityNo") + "_buyNum", count);
				}

			}
		} catch (Exception e) {
			logger.error("", e);
		}
		return count;
	}
}
