package com.ffzx.promotion.api.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ffzx.commerce.framework.constant.Constant;
import com.ffzx.commerce.framework.dao.CrudMapper;
import com.ffzx.commerce.framework.dto.ResultDto;
import com.ffzx.commerce.framework.exception.ServiceException;
import com.ffzx.commerce.framework.service.impl.BaseCrudServiceImpl;
import com.ffzx.commerce.framework.utils.RedisUtil;
import com.ffzx.commerce.framework.utils.UUIDGenerator;
import com.ffzx.promotion.api.dto.AppRecommendAwards;
import com.ffzx.promotion.api.dto.CouponGrant;
import com.ffzx.promotion.api.dto.Redpackage;
import com.ffzx.promotion.api.dto.RedpackageCount;
import com.ffzx.promotion.api.service.CouponGrantApiService;
import com.ffzx.promotion.api.service.RedpackageCountApiService;
import com.ffzx.promotion.mapper.AppRecommendAwardsMapper;
import com.ffzx.promotion.mapper.CouponGrantMapper;
import com.ffzx.promotion.mapper.RedpackageCountMapper;
import com.ffzx.promotion.mapper.RedpackageMapper;
import com.ffzx.promotion.mapper.RedpackageReceiveMapper;
import com.ffzx.promotion.service.CouponGrantService;
import com.ffzx.promotion.service.RedpackageReceiveService;

/**
 * 
 * @author ffzx
 * @date 2016-05-03 17:58:03
 * @version 1.0.0
 * @copyright www.ffzxnet.com
 */
@Service("redpackageCountApiService")
public class RedpackageCountApiServiceImpl extends BaseCrudServiceImpl implements RedpackageCountApiService {

	private final String lockkey="RedpackageCountApiServiceImpl_redpackagecount_null";//一次一条进程
	private final long locktime=30*60*1000;//20分钟
	@Resource
	private RedpackageMapper redpackageMapper;
	@Autowired
	private RedpackageReceiveMapper redpackageReceiveMapper;
	
	@Autowired
	private RedpackageCountMapper redpackageCountMapper;

   @Autowired
   private RedisUtil redisUtil;
	@Override
	public CrudMapper init() {
		// TODO Auto-generated method stub
		return redpackageCountMapper;
	}
	

	@Override
	@Transactional
	public ResultDto redpackagecount() throws ServiceException {
		// TODO Auto-generated method stub
		logger.info("我进来执行红包统计了redpackagecount");
		Map<String, Object> params=new HashMap<String, Object>();
		params.put("isGrant", Constant.YES);
		params.put("useRedpackageDate",new Date());
		params.put("delFlag", Constant.NO);
		List<Redpackage> redpackages=redpackageMapper.selectByParams(params);
		if(redpackages!=null && redpackages.size()>0){
			if(redisUtil.tryLock(lockkey, new Long(locktime))){
				logger.info("我进来tryLock执行红包统计了redpackagecount");
				try{
					List<RedpackageCount> redpackageCounts=new ArrayList<RedpackageCount>();//红包统计
					findRedpackageCount(redpackages,redpackageCounts);//所有红包统计的数据
					saveRedpackageCount(redpackageCounts);
					logger.info("我执行完tryLock执行红包统计了redpackagecount");
				}
				catch(Exception e){
					logger.error("",e);
				}finally {
					redisUtil.remove(lockkey);
				}
			}
		}
		return null;
	}
	private void saveRedpackageCount(List<RedpackageCount> redpackageCounts){
		for (RedpackageCount redpackageCount : redpackageCounts) {
			logger.info("saveorupdate_date"+redpackageCount.toString());
			Map<String, Object> params=new HashMap<String, Object>();
			params.put("redpackageId", redpackageCount.getRedpackageId());
			List<RedpackageCount> rcounts=init().selectByParams(params);
			if(rcounts==null || rcounts.size()==0){
				init().insertSelective(redpackageCount);
			}else{
				logger.info("execsource size"+rcounts.size()+" rcounts.get(0).toString() "+rcounts.get(0).toString());
				redpackageCount.setId(rcounts.get(0).getId());
				init().updateByPrimaryKeySelective(redpackageCount);
			}
		}
		
	}
	private void findRedpackageCount(List<Redpackage> redpackages,List<RedpackageCount> redpackageCounts){
		for (Redpackage redpackage : redpackages) {
			//发放数量
			Map<String, Object> params=new HashMap<String, Object>();
			params.put("isReceive", Constant.YES);//是否领取
			params.put("delFlag", Constant.NO);//是否领取
			params.put("redpackageId", redpackage.getId());
			Integer grantNum = redpackage.getGrantNum();
			Integer receiveNum = redpackageReceiveMapper.selectCount(params);
			Integer receiveTime= receiveNum;//领取次数
			params.put("useState", Constant.YES);//是否使用
			Integer usePerson = redpackageReceiveMapper.selectCount(params);//使用人数
			Integer useNum = usePerson;//使用次数
			//使用率=使用次数/领取次数
			BigDecimal useChange = (useNum==0 || receiveTime==0)?new BigDecimal(0): new BigDecimal(((double)useNum)/((double)receiveTime));

			params.put("isPay", Constant.YES);//是否支付
			//已使用，已经领取 订单总金额 和使用红包总金额
			RedpackageCount rcount=redpackageReceiveMapper.selectSumRedpackage(params);
			BigDecimal useRedpackagePrice = rcount.getUseRedpackagePrice();
			BigDecimal useOrderPrice = rcount.getUseOrderPrice();
			//使用红包客单价=使用红包总订单流水/使用人数
			BigDecimal useOrderPersonPrice = usePerson==0?new BigDecimal(0):
				new BigDecimal(useOrderPrice.doubleValue()/usePerson);
			String redpackageId= redpackage.getId();
			
			RedpackageCount redpackageCount=new RedpackageCount
					(grantNum, receiveNum, receiveTime, usePerson, useNum, useChange, useRedpackagePrice, 
							useOrderPrice, useOrderPersonPrice, redpackageId);
			redpackageCount.setId(UUIDGenerator.getUUID());
			redpackageCounts.add(redpackageCount);

		}
	}
	


}