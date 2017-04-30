/**
 * 
 */
package com.ffzx.promotion.api.service.consumer;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ffzx.commerce.framework.constant.Constant;
import com.ffzx.commerce.framework.dto.ResultDto;
import com.ffzx.commerce.framework.utils.RedisUtil;
import com.ffzx.promotion.api.dto.CouponReceive;
import com.ffzx.promotion.api.service.ActivityManagerApiService;
import com.ffzx.promotion.api.service.CouponGrantApiService;
import com.ffzx.promotion.api.service.CouponReceiveApiService;
import com.ffzx.promotion.exception.CallInterfaceException;

/**
 * @Description: 优惠券领取
 * @author zi.luo
 * @email  zi.luo@ffzxnet.com
 * @date 2016年5月25日 上午11:29:11
 * @version V1.0 
 *
 */
@Service("couponReceiveApiConsumer")
public class CouponReceiveApiConsumer {
	
	// 记录日志
		private final static Logger logger = LoggerFactory.getLogger(CouponReceiveApiConsumer.class);
		
		@Autowired
		private CouponReceiveApiService couponReceiveApiService;
		
		@Autowired
		private CouponGrantApiService couponGrantApiService;
		
		@Autowired
		private CategoryApiConsumer categoryApiConsumer;
		
		@Autowired
		private ActivityManagerApiService activityManagerApiService;
		
	    @Resource
	    private RedisUtil redisUtil;
	    
	    /**
		 * 获取我的优惠券列表(指定状态)2016-09-26
		 * @param uid    会员id
		 * @param couponStatus   优惠券状态
		 * @return
		 */
		public List<CouponReceive> findCouponList(String uid,String status){
			logger.debug("findCouponList,获取我的优惠券(指定状态) - BEGIN");
			ResultDto result = null;
			Map<String, Object> map=new HashMap<String, Object>();
			try {
				map.put("memberId", uid);
				map.put("status", status);
			 	result =couponReceiveApiService.getMyCouponList(map);
			} catch (Exception e) {
				logger.error("findCouponList,获取我的优惠券(指定状态)" , e);
				throw new CallInterfaceException(e.getMessage());
			}
			if(result.getCode().equals(ResultDto.OK_CODE) ){
				return (List<CouponReceive>) result.getData();
				
			}else{
				logger.error("findCouponList,获取我的优惠券(指定状态)" , result.getMessage());
				throw new CallInterfaceException(result.getMessage());
			}
		}

		/***
		 * 获取我的优惠券List
		 * @return
		 */
		public String getCommodityName(List<String> goodsIds, String couponAdminId){
			logger.debug("CouponReceiveApiConsumer-getCommodityName=》获取商品名称 - BEGIN");
			ResultDto result = null;
			try {
			 	 result =couponReceiveApiService.getCommodityName(goodsIds, couponAdminId);
			} catch (Exception e) {
				logger.error("CouponReceiveApiConsumer-getCommodityName=》获取商品名称" , e);
				throw new CallInterfaceException(e.getMessage());
			}
			
			List<String> list = null;
			if (result.getCode().equals(ResultDto.OK_CODE)) {
				list= (List<String>) result.getData();
				if(list==null || list.size()==0)
					return null;
				return list.toString().substring(1, list.toString().length()-1).replaceAll(" ", "");
			} else{
				logger.error("CouponReceiveApiConsumer-findCouponCount=》获取商品名称 " , result.getMessage());
				throw new CallInterfaceException(result.getMessage());
			}
		}
		/***
		 * 获取活动名称
		 * @return
		 */
		public String getActivityName(List<String> activityIds){
			logger.debug("获取活动名称 - BEGIN");
			ResultDto result = null;
			try {
			 	 result =activityManagerApiService.getActivityNameList(activityIds);
			} catch (Exception e) {
				logger.error("获取活动名称" , e);
				throw new CallInterfaceException(e.getMessage());
			}
			
			List<String> list = null;
			if (result.getCode().equals(ResultDto.OK_CODE)) {
				list= (List<String>) result.getData();
				if(list==null || list.size()==0)
					return null;
				return list.toString().substring(1, list.toString().length()-1).replaceAll(" ", "");
			} else{
				logger.error("获取活动名称 " , result.getMessage());
				throw new CallInterfaceException( result.getMessage());
			}
		}
	    /***
		 * 获取我的优惠券List
		 * @return
		 */
		public CouponReceive getCouponReceive(String id){
			logger.debug("CouponReceiveApiConsumer-findCouponCount=》获取指定用户预售抢购同一批次剩余可购买数 - BEGIN");
			ResultDto result = null;
			try {
			 	 result =couponReceiveApiService.getCoupReceive(id);
			} catch (Exception e) {
				logger.error("CouponReceiveApiConsumer-findCouponCount=》获取指定用户预售抢购同一批次剩余可购买数" , e);
				throw new CallInterfaceException(e.getMessage());
			}
			if(result.getCode().equals(ResultDto.OK_CODE) ){
				return (CouponReceive) result.getData();
			}else{
				logger.error("CouponReceiveApiConsumer-findCouponCount=》获取指定用户预售抢购同一批次剩余可购买数 " , result.getMessage());
				throw new CallInterfaceException( result.getMessage());
			}
		}
		
	/**
	 * 获取我的优惠券
	 * @param uid    会员id
	 * @param lMaps    商品基本信息
	 * @return
	 */
	public List<CouponReceive> findCoupon(String uid,List<Map<String, Object>> lMaps){
		logger.debug("CouponReceiveApiConsumer-findCoupon=》获取我的优惠券 - BEGIN");
		ResultDto result = null;
		Map<String, Object> map=new HashMap<String, Object>();
		try {
			if(lMaps!=null && lMaps.size()>0){
				
				map.put("useState", Constant.NO);//未使用
				map.put("stateenddate", new Date());//非过期
			}
			map.put("memberId", uid);
		 	 result =couponReceiveApiService.getCouponReceiveList(map);
		} catch (Exception e) {
			logger.error("CouponReceiveApiConsumer-findCoupon=》获取我的优惠券" , e);
			throw new CallInterfaceException(e.getMessage());
		}
		if(result.getCode().equals(ResultDto.OK_CODE) ){
			return (List<CouponReceive>) result.getData();
			
		}else{
			logger.error("CouponReceiveApiConsumer-findCoupon=》获取我的优惠券 " , result.getMessage());
			throw new CallInterfaceException(result.getMessage());
		}
	}
}
