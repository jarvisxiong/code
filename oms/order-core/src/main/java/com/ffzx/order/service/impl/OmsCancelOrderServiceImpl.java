package com.ffzx.order.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ffzx.bms.api.dto.OrderParam;
import com.ffzx.bms.api.service.OrderProcessManagerApiService;
import com.ffzx.commerce.framework.constant.Constant;
import com.ffzx.commerce.framework.dao.CrudMapper;
import com.ffzx.commerce.framework.dto.ResultDto;
import com.ffzx.commerce.framework.exception.ServiceException;
import com.ffzx.commerce.framework.service.impl.BaseCrudServiceImpl;
import com.ffzx.commerce.framework.utils.JsonMapper;
import com.ffzx.commerce.framework.utils.RedisUtil;
import com.ffzx.commerce.framework.utils.StringUtil;
import com.ffzx.order.api.dto.OmsOrder;
import com.ffzx.order.api.enums.OrderOperationRecordEnum;
import com.ffzx.order.constant.OrderStatusConstant;
import com.ffzx.order.mapper.AftersaleApplyItemMapper;
import com.ffzx.order.mapper.OmsOrderMapper;
import com.ffzx.order.mapper.OmsOrderdetailMapper;
import com.ffzx.order.service.OmsCancelOrderService;
import com.ffzx.order.service.OmsOrderService;
import com.ffzx.order.service.PaymentRecordService;
import com.ffzx.order.utils.pingxx.Pay;
import com.ffzx.promotion.api.service.CouponReceiveApiService;
import com.pingplusplus.model.Charge;


/**
 * 
 * @author ffzx
 * @date 2016-05-11 12:45:52
 * @version 1.0.0
 * @copyright www.ffzxnet.com
 */
@Service("omsCancelOrderService")
public class OmsCancelOrderServiceImpl extends BaseCrudServiceImpl implements OmsCancelOrderService {

	@Resource
	private RedisUtil redisUtil;
    @Resource
    private OmsOrderMapper omsOrderMapper;
    @Resource
    private OmsOrderdetailMapper omsOrderdetailMapper;
    @Resource
	private AftersaleApplyItemMapper aftersaleApplyItemMapper;
    @Autowired
    PaymentRecordService paymentRecordService;
	@Autowired
	private CouponReceiveApiService couponReceiveApiService;//优惠券
	@Resource(name = "orderProcessManagerApiService")
	private OrderProcessManagerApiService orderProcessManagerApiService;
	@Autowired
	private OmsOrderService omsOrderService;
	@Override
	@Transactional(rollbackFor=Exception.class)
	public void cancelOrder(OmsOrder order, OrderOperationRecordEnum operationRecord) throws ServiceException {
		// TODO Auto-generated method stub
		logger.info("订单取消操作开始==============="+order.getOrderNo());
		//如果不是待付款状态状态,则不允许取消
		if(OrderStatusConstant.ORDER_CANCELED.equals(order.getStatus())){
			throw new ServiceException("您的订单已经取消，请勿重复操作"); 
		}
		//如果不是待付款状态状态,则不允许取消
		if(!OrderStatusConstant.STATUS_PENDING_PAYMENT.equals(order.getStatus())){
			throw new ServiceException("取消订单失败，您的订单不处于代付款状态,无法完成此操作"); 
		}
		//判断订单是否已支付,如果已支付,则不允许取消
		//TODO 先注释，否则测试无法进行！！！
		if(judgeIsPayed(order.getChargeId(),order.getOrderNo()) ){
			throw new ServiceException("取消订单失败，您的订单正处于付款确认阶段,无法完成此操作");
		}
	
		Date now = new Date();
		//订单状态的更改
		order.setStatus("5");
		order.setOperationDate(now);//记录订单操作时间
		order.setLastUpdateDate(now);
		order.setOperationRecord(operationRecord); //记录订单操作原因
		omsOrderMapper.updateOrderStatus(order);
		//优惠券回滚操作,促销系统调用
		if( StringUtil.isNotNull(order.getCouponId()) ){
			invokeCrmsByCancelOrder(order.getCouponId(),order.getMemberId());
		}
		//库存回滚操作;调用WMS系统
		invokeWmsByCancelOrder(order);
	}

	/***
	 * 判断订单是否已支付
	 * @param chargeId 第三方支付凭证ID
	 * @return
	 * @author ying.cai
	 * @email ying.cai@ffzxnet.com
	 * @date 2016-5-10 下午06:04:29
	 * @version V1.0
	 */
	private boolean judgeIsPayed(String chargeId,String orderNo) {
		if(StringUtils.isEmpty(chargeId)){
			return false;
		}
		try {
			Charge charge = Pay.retrieve(chargeId);
			if(charge!=null && charge.getPaid()){//已经支付
				return true;
			}
			return false;
		} catch (Exception e) {
			logger.error("请求PING++ 查询订单 "+orderNo+" 支付信息出错",e);
//			throw new ServiceException(e);
			return false;
		}
//		return false;
		
	}
	
	/**调用WMS系统回滚指定订单的库存 by ying.cai*/
	@Transactional(rollbackFor=Exception.class)
	private void invokeWmsByCancelOrder(OmsOrder order){
		try{
			List<OrderParam> orderParamList = omsOrderService.initOrderParamInfo(order.getDetailList());
			ResultDto wmsResultDto =  orderProcessManagerApiService.cancelOrder(orderParamList, order.getRegionId(), order.getOrderNo());
			if(!ResultDto.OK_CODE.equals(wmsResultDto.getCode())){
						logger.error("【"+order.getOrderNo()+"】取消订单,库存回滚：===========orderProcessManagerService.cancelOrder("+JsonMapper.toJsonString(orderParamList)+","+order.getRegionId()+","+order.getOrderNo()+",订单取消\"),取消订单操作库存失败");
						throw new ServiceException(wmsResultDto.getMessage());
			}
		}catch(Exception e){
			logger.error("【"+order.getOrderNo()+"】库存回滚,orderProcessManagerService.cancelOrder error", e);
			throw new ServiceException("接口调用失败，库存回滚报错",e);
		}
	}

	

	// 活动商品数量 回滚缓存
	public void rollBackRedisActivityNum_auto(String redisKey, int buyNum) {
		if (redisUtil.exists(redisKey)) {
			redisUtil.decrease(redisKey, buyNum);
		}
	}
	//活动商品数量 回滚缓存 
	public void rollBackRedisActivityNum(String redisKey,int buyNum){
				if(redisUtil.exists(redisKey)){
					//redisUtil.remove(redisKey);
					logger.info("取消订单,回滚【"+redisKey+"】缓存成功");
					//该用户对该活动的商品已经购买的数量
					String oldBuyNumStr = (String) redisUtil.get(redisKey);
					int oldBuyNum= oldBuyNumStr==null?0:Integer.parseInt(oldBuyNumStr);
					int curBuyNum =  oldBuyNum-buyNum;
					redisUtil.set(redisKey, (curBuyNum>0?curBuyNum:0)+"");
				}
		}
	private void invokeCrmsByCancelOrder(String couponId,String memberId){
		//优惠券回滚操作,促销系统调用
		try{
			// ResultDto couponResultDto = couponReceiveApiService.updateReceiveState(couponId, "0");
			ResultDto couponResultDto =  couponReceiveApiService.updateReceiveState(couponId,"0",memberId);
			if(!ResultDto.OK_CODE.equals(couponResultDto.getCode())){
				logger.error("OmsOrderServiceImpl.cancel error ,reason invoke by WMS stockManagerApiService.cancelOrder fail");
				throw new ServiceException(couponResultDto.getMessage());
			}
		}catch(Exception e){
			logger.error("OmsOrderServiceImpl.invokeCrmsByCancelOrder.cancel error ,reason invoke by PRMS couponReceiveApiService.updateReceiveState error", e);
			throw new ServiceException("接口调用失败，库存回滚报错",e);
		}
	}
	@Override
	public CrudMapper init() {
		// TODO Auto-generated method stub
		return null;
	}
	/**删除订单*/
	@Override
	@Transactional(rollbackFor=Exception.class)
	public void deleteOrder(OmsOrder order) throws ServiceException{
		//TODO 所有业务逻辑都在cancelOrder中完成,因为不允许直接删除订单,必须先取消订单
		order.setDelFlag(Constant.DELTE_FLAG_YES);
		omsOrderMapper.updateOrderDelFlag(order);
	}

	@Override
	@Transactional(rollbackFor=Exception.class)
	/**用户删除订单*/
	public void deleteOrderByUser(OmsOrder order) throws ServiceException {
		// TODO 区分用删除订单操作与后天订单出处操作
				order.setDelForUser(Constant.DELTE_FLAG_YES);
				omsOrderMapper.updateOrderDelFlagByUser(order);
	}
	@Override
	@Transactional(rollbackFor=Exception.class)
	public OmsOrder getOrderByWebhooks(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return this.omsOrderMapper.selectByKey(params);
	}
}