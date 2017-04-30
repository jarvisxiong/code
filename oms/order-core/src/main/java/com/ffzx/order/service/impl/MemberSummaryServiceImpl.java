package com.ffzx.order.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ffzx.commerce.framework.constant.Constant;
import com.ffzx.commerce.framework.constant.RedisPrefix;
import com.ffzx.commerce.framework.dao.CrudMapper;
import com.ffzx.commerce.framework.page.Page;
import com.ffzx.commerce.framework.service.impl.BaseCrudServiceImpl;
import com.ffzx.commerce.framework.utils.DateUtil;
import com.ffzx.commerce.framework.utils.RedisUtil;
import com.ffzx.commerce.framework.utils.UUIDGenerator;
import com.ffzx.member.api.dto.GrowthSetting;
import com.ffzx.order.api.dto.MemberSummary;
import com.ffzx.order.mapper.MemberSummaryMapper;
import com.ffzx.order.service.MemberSummaryService;

/**
 * @className MemberSummaryServiceImpl
 *
 * @author liujunjun
 * @date 2016-10-24 18:24:32
 * @version 1.0.0
 */
@Service("memberSummaryService")
public class MemberSummaryServiceImpl extends BaseCrudServiceImpl implements MemberSummaryService {
	@Resource
	private RedisUtil redisUtil;
	@Resource
	private MemberSummaryMapper memberSummaryMapper;
	@Autowired
	RabbitTemplate rabbitTemplate;
	@Override
	public CrudMapper init() {
		return memberSummaryMapper;
	}

	/***
	 * 
	 * @author yinglong.huang
	 * @email yinglong.huang@ffzxnet.com
	 * @date 2016年10月24日 下午6:50:28
	 * @version V1.0
	 * @return 
	 */
	@Override
	public void taskSummaryFromOrder() {
		GrowthSetting growthSetting = redisUtil.get(RedisPrefix.MEMBER_GROWTH_SETTING, GrowthSetting.class);
		//每月每个订单最低交易金额
		BigDecimal monthlyAmount =  growthSetting.getMonthlyAmount();
		Integer monthlyOrderNum = growthSetting.getMonthlyOrderNum();
		if(null!=growthSetting){
		logger.info("定时任务统计会员月消费数据===start===");
		Map<String,Object> params = new HashMap<String,Object>();
		int pageSize = 1000;
		int pageIndex = 1;
		Page page = new Page(pageIndex, pageSize);
		//任务调度计划为每个月的1号触发，故统计上个月的数据将当前日期提前一天再格式化为月份即可
		String payMonth = DateUtil.format(DateUtil.getPrevDay(new Date(), 1), DateUtil.FORMAT_MONTH);
		
		params.put("payMonth", payMonth);
		params.put("monthlyAmount", payMonth);
		List<MemberSummary> memberSummaryList = queryByPage4Task(params,page);	
		if(null!=memberSummaryList&&memberSummaryList.size()>0){
		this.insertBatch(memberSummaryList,payMonth,monthlyOrderNum);
		}
		int pageCount = page.getPageCount();
		for(int i=2;i<=pageCount;i++){
		  page.setPageIndex(i);
		  memberSummaryList= queryByPage4Task(params,page);
		  pageCount = page.getPageCount();
		  this.insertBatch(memberSummaryList,payMonth,monthlyOrderNum);
		}
		logger.info("定时任务统计会员月消费数据===end===");
		}
	}	
	
	private List<MemberSummary> queryByPage4Task(Map<String,Object> params,Page page){
		List<MemberSummary> memberSummaryList= this.memberSummaryMapper.selectByPageFromOrder(page, params);
		return memberSummaryList;
	}
	@Transactional
	private void insertBatch(List<MemberSummary> memberSummaryList,String payMonth,Integer monthlyOrderNum){
		for (MemberSummary memberSummary : memberSummaryList) {
			if(null!=monthlyOrderNum&&memberSummary.getTotalOrderCount()>=monthlyOrderNum){
			try{
			Date now = new Date();
			memberSummary.setLastUpdateDate(now);
			memberSummary.setCreateDate(now);
			memberSummary.setPayMonth(payMonth);
			memberSummary.setDelFlag(Constant.DELTE_FLAG_NO);
			memberSummary.setId(UUIDGenerator.getUUID());
			this.add(memberSummary);
			}catch(Exception e){
				logger.error("统计会员月消费额度异常【"+memberSummary.getMemberId()+"】",e);
			}
			}
		}
	}

	/***
	 * 
	 * @author yinglong.huang
	 * @email yinglong.huang@ffzxnet.com
	 * @date 2016年10月25日 下午2:53:12
	 * @version V1.0
	 * @return 
	 */
	@Override
	public void taskPushMs2Mq() {
		logger.info("定时任务推送消息到会员中心统计会员月消费数据===start===");
		//任务调度计划为每个月的1号触发，故统计上个月的数据将当前日期提前一天再格式化为月份即可
		String payMonth = DateUtil.format(DateUtil.getPrevDay(new Date(), 1), DateUtil.FORMAT_MONTH);
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("payMonth", payMonth);
		int pageSize = 1000;
		int pageIndex = 1;
		Page page = new Page(pageIndex, pageSize);
		List<MemberSummary> memberSummaryList = this.findByPage(page, Constant.ORDER_BY_FIELD_CREATE, Constant.ORDER_BY, params);
		if(null!=memberSummaryList&&memberSummaryList.size()>0){
			logger.info("定时任务推送消息到会员中心统计会员月消费数据===推送第【1】页数据===【"+memberSummaryList.size()+"】条");
			rabbitTemplate.convertAndSend("QUEUE_OMS_MEMBER_SALES_SUMMARY", memberSummaryList);
		}
		int pageCount = page.getPageCount();
		for(int i=2;i<=pageCount;i++){
		  page.setPageIndex(i);
		  memberSummaryList=  this.findByPage(page, Constant.ORDER_BY_FIELD_CREATE, Constant.ORDER_BY, params);
		  pageCount = page.getPageCount();
		  if(null!=memberSummaryList&&memberSummaryList.size()>0){
				logger.info("定时任务推送消息到会员中心统计会员月消费数据===推送第【"+i+"】页数据===【"+memberSummaryList.size()+"】条");
				rabbitTemplate.convertAndSend("QUEUE_OMS_MEMBER_SALES_SUMMARY", memberSummaryList);
			}
		}
		logger.info("定时任务推送消息到会员中心统计会员月消费数据===end===");
	}
}