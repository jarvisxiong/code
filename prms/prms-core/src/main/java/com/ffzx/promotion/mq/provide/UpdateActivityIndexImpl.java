package com.ffzx.promotion.mq.provide;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ffzx.promotion.api.dto.ActivityEsVo;
import com.ffzx.promotion.api.service.RemoveActivityIndex;
import com.ffzx.promotion.api.service.UpdateActivityIndex;
import com.ffzx.promotion.constant.MqConstant;
import com.ffzx.promotion.service.ActivityCommodityService;

/***
 * 更新活动索引
 * 当预售，抢购，普通活动的数据有新增或任何修改时，都需要调用此类方法推送消息至mq
 * @author ying.cai
 * @date 2016年9月12日 上午10:21:36
 * @email ying.cai@ffzxnet.com
 * @version V1.0
 *
 */
@Service
public class UpdateActivityIndexImpl implements UpdateActivityIndex{
	
	private final static Logger logger = LoggerFactory.getLogger(UpdateActivityIndexImpl.class);
	@Autowired
	private RabbitTemplate rabbitTemplate;
	@Autowired
	private ActivityCommodityService activityCommodityService;
	@Autowired
	private RemoveActivityIndex removeActivityIndex;
	
	@Override
	public void sendMqByUpdateActivity(ActivityEsVo activityEsVo) {
		logger.info("活动信息修改mq推送START===>>>{activityId:"+activityEsVo.getActivityId()+"}");
		if(activityEsVo.getMinPrice()==null || activityEsVo.getMinPrice().doubleValue()<=0){
			logger.info("minprice价格为0");
			String discountPrice = activityEsVo.getDiscountPrice();
			if(discountPrice.indexOf("-")>=0){
				String[] maxmain=discountPrice.split("-");
				activityEsVo.setMinPrice(new BigDecimal(maxmain[0].trim()));
				activityEsVo.setMaxPrice(new BigDecimal(maxmain[1].trim()));
			}else{
				activityEsVo.setMinPrice(new BigDecimal(discountPrice.trim()));
				activityEsVo.setMaxPrice(new BigDecimal(discountPrice.trim()));
			}
				
		}
		validParam(activityEsVo);
		try {
			rabbitTemplate.convertAndSend(MqConstant.PRMS_ACTIVITY_CHANGE,"", activityEsVo);
		} catch (Exception e) {
			logger.error("活动信息修改mq推送 ERROR===>>>" , e);
			throw new RuntimeException("活动信息修改mq推送 ERROR",e);
		}
		logger.info("活动信息修改mq推送END===>>>{activityId:"+activityEsVo.getActivityId()+"}");
	}
	
	@Override
	public void sendMqByUpdateActivity(String goodsId, String title1, String imgPath, String discountPrice, String type,
			String activityId,Date startDate,Date endDate) {
		ActivityEsVo activityEsVo = new ActivityEsVo();
		activityEsVo.setActivityId(activityId);
		activityEsVo.setDiscountPrice(discountPrice);
		activityEsVo.setGoodsId(goodsId);
		activityEsVo.setImgPath(imgPath);
		activityEsVo.setIsVip(1);
		activityEsVo.setTitle1(title1);
		activityEsVo.setType(type);
		activityEsVo.setStartDate(startDate);
		activityEsVo.setEndDate(endDate);
		sendMqByUpdateActivity(activityEsVo);
	}
	
	
	private void validParam(ActivityEsVo activityEsVo){
		if(StringUtils.isEmpty(activityEsVo.getActivityId())){
			throw new RuntimeException("activityId必传");
		}else if(StringUtils.isEmpty(activityEsVo.getDiscountPrice())){
			throw new RuntimeException("discountPrice必传");
		}else if(StringUtils.isEmpty(activityEsVo.getGoodsId())){
			throw new RuntimeException("goodsId必传");
		}else if(StringUtils.isEmpty(activityEsVo.getTitle1())){
			throw new RuntimeException("title1必传");
		}else if(StringUtils.isEmpty(activityEsVo.getType())){
			throw new RuntimeException("type必传");
		}
	}

	@Override
	@Transactional(rollbackFor=Exception.class)
	public void activityResolved(String activityManagerId, String operateType) {
		List<ActivityEsVo> activityEsVoList = activityCommodityService.findActivityGoodsItemByMid(activityManagerId,null);
		logger.info("根据activityManagerId：["+activityManagerId +"]找到数据"+activityEsVoList.size()+"条！");
		for (ActivityEsVo activityEsVo : activityEsVoList) {
			logger.info("操作类型为["+operateType+"],activityId["+activityEsVo.getActivityId()+"]");
			//还原搜索引擎仓库商品数据
//			if("0".equals(operateType)){
//				//edit by 2016 11 16 ,将商品id改为活动Id，因为同一个商品可以同时存在于多个同种类型的活动里面
////				removeActivityIndex.sendMqByRemoveActivity(activityEsVo.getGoodsId());
//				removeActivityIndex.sendMqByRemoveActivity(activityEsVo.getActivityId());
//			}else{//更新搜索引擎仓库商品数据
//				sendMqByUpdateActivity(activityEsVo);
//			}
			sendMqByUpdateActivity(activityEsVo);
		}
	}

	@Override
	@Transactional(rollbackFor=Exception.class)
	public void updateCommodity(String activityCommodityId) {
		logger.info("商品数据变更，开始推送数据activityCommodityId【"+activityCommodityId+"】");
		//判断对应活动管理数据是否为启用状态
		List<ActivityEsVo> activityEsVoList = activityCommodityService.findActivityGoodsItemByMid(null,activityCommodityId);
		logger.info("activityEsVoList：size:[{}]", activityEsVoList.size());
		if(null== activityEsVoList || activityEsVoList.size()==0){
			return;
		}
		ActivityEsVo activityEsVo = activityEsVoList.get(0);
		logger.info("ReleaseStatus【{}】",activityEsVo.getReleaseStatus());
		//如果是在活动撤销的状态下进行的导入，也得推送
//		if( !"1".equals(activityEsVo.getReleaseStatus()) ){
//			return;
//		}
		sendMqByUpdateActivity(activityEsVo);
	}
	
	/***
	 * 当一个商品变成批发或者新用户专享时，调用此方法
	 * 当商品在批发或者新用户专享活动中被移除时，调用此方法
	 * 
	 * @date 2016年11月18日 下午3:50:50
	 * @author ying.cai
	 * @email ying.cai@ffzxnet.com
	 */
	@Override
	public void removeSpecialActivity(String goodsId,String type){
		logger.info("批发或新用户商品数据变更，推送至ES,goodsId【{}】,type【{}】",goodsId,type);
		ActivityEsVo activityEsVo = new ActivityEsVo();
		activityEsVo.setGoodsId(goodsId);
		activityEsVo.setType(type);
		try {
			rabbitTemplate.convertAndSend(MqConstant.PRMS_ACTIVITY_CHANGE,"", activityEsVo);
		} catch (Exception e) {
			logger.error("活动信息修改mq推送 ERROR===>>>" , e);
			throw new RuntimeException("活动信息修改mq推送 ERROR",e);
		}
		logger.info("批发或新用户商品数据变更mq推送END");
	}
}
