package com.ffzx.order.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StopWatch;

import com.alibaba.fastjson.JSONObject;
import com.ffzx.basedata.api.dto.Address;
import com.ffzx.basedata.api.dto.Partner;
import com.ffzx.basedata.api.dto.PartnerServiceStation;
import com.ffzx.basedata.api.service.AddressApiService;
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
import com.ffzx.commerce.framework.page.Page;
import com.ffzx.commerce.framework.service.impl.BaseCrudServiceImpl;
import com.ffzx.commerce.framework.thirdparty.ShortMsgEnum;
import com.ffzx.commerce.framework.utils.DateUtil;
import com.ffzx.commerce.framework.utils.JPushUtils;
import com.ffzx.commerce.framework.utils.JsonMapper;
import com.ffzx.commerce.framework.utils.RedisUtil;
import com.ffzx.commerce.framework.utils.RedisWebUtils;
import com.ffzx.commerce.framework.utils.StringUtil;
import com.ffzx.commerce.framework.utils.UUIDGenerator;
import com.ffzx.member.api.dto.Member;
import com.ffzx.member.api.dto.MemberAddress;
import com.ffzx.member.api.dto.MemberMessage;
import com.ffzx.member.api.enums.MessTypeEnum;
import com.ffzx.member.api.service.MemberAddressApiService;
import com.ffzx.member.api.service.MemberApiService;
import com.ffzx.member.api.service.MemberMessageApiService;
import com.ffzx.order.api.dto.AftersaleApply;
import com.ffzx.order.api.dto.AftersaleApplyItem;
import com.ffzx.order.api.dto.Commodity;
import com.ffzx.order.api.dto.CommoditySku;
import com.ffzx.order.api.dto.GoodsVo;
import com.ffzx.order.api.dto.ImportBuildOrderVo;
import com.ffzx.order.api.dto.OmsOrder;
import com.ffzx.order.api.dto.OmsOrderRecord;
import com.ffzx.order.api.dto.OmsOrderdetail;
import com.ffzx.order.api.dto.ReplenishmentOrderVo;
import com.ffzx.order.api.enums.BuyTypeEnum;
import com.ffzx.order.api.enums.OrderDetailStatusEnum;
import com.ffzx.order.api.enums.OrderOperationRecordEnum;
import com.ffzx.order.api.enums.OrderTypeEnum;
import com.ffzx.order.api.enums.PayTypeEnum;
import com.ffzx.order.api.service.OutboundDeliveryStatusMqApiService;
import com.ffzx.order.api.service.OutboundSalesDeliveryMqApiService;
import com.ffzx.order.api.vo.OmsOrderVo;
import com.ffzx.order.api.vo.OrderBiVo;
import com.ffzx.order.constant.OrderDetailStatusConstant;
import com.ffzx.order.constant.OrderStatusConstant;
import com.ffzx.order.mapper.AftersaleApplyItemMapper;
import com.ffzx.order.mapper.CommodityMapper;
import com.ffzx.order.mapper.OmsOrderMapper;
import com.ffzx.order.mapper.OmsOrderdetailMapper;
import com.ffzx.order.model.PaymentRecord;
import com.ffzx.order.service.AftersaleApplyService;
import com.ffzx.order.service.CommodityService;
import com.ffzx.order.service.CommoditySkuService;
import com.ffzx.order.service.OmsCancelOrderService;
import com.ffzx.order.service.OmsOrderRecordService;
import com.ffzx.order.service.OmsOrderService;
import com.ffzx.order.service.OmsOrderSplitService;
import com.ffzx.order.service.OmsOrderdetailService;
import com.ffzx.order.service.PaymentRecordService;
import com.ffzx.order.utils.OmsConstant;
import com.ffzx.order.utils.pingxx.Pay;
import com.ffzx.promotion.api.dto.GiveOrderCoupon;
import com.ffzx.promotion.api.service.ActivityGiveApiService;
import com.ffzx.promotion.api.service.ActivityManagerApiService;
import com.ffzx.promotion.api.service.CouponReceiveApiService;
import com.ffzx.promotion.api.service.RedPackageApiService;
import com.ffzx.wms.api.dto.Warehouse;
import com.ffzx.wms.api.service.OutboundSalesDeliveryApiService;
import com.pingplusplus.model.Charge;


/**
 * 
 * @author ffzx
 * @date 2016-05-11 12:45:52
 * @version 1.0.0
 * @copyright www.ffzxnet.com
 */
@SuppressWarnings({ "unchecked", "unused" })
@Service("omsOrderService")
public class OmsOrderServiceImpl extends BaseCrudServiceImpl implements OmsOrderService {
	@Autowired
	RabbitTemplate rabbitTemplate;
	@Autowired
	private RedisTemplate<String, String> redisTemplate;
    @Resource
    private OmsOrderMapper omsOrderMapper;
    @Resource
    private OmsOrderdetailMapper omsOrderdetailMapper;
    @Resource
    private CommodityMapper commodityMapper;
    @Resource
	private AftersaleApplyItemMapper aftersaleApplyItemMapper;
    @Autowired
    PaymentRecordService paymentRecordService;
    @Autowired
    private OmsOrderdetailService omsOrderdetailService;
    @Autowired
    private CommodityService commodityService;//商品
	@Autowired 
	private CommoditySkuService commoditySkuService;//商品sku
	@Autowired
	private CouponReceiveApiService couponReceiveApiService;//优惠券
	@Autowired
	private OmsOrderRecordService omsOrderRecordService;
	@Autowired
	private MemberMessageApiService memberMessageApiService;
	@Autowired
	private OutboundSalesDeliveryMqApiService OutboundSalesDeliveryMqApiService;
	@Autowired
	private OutboundDeliveryStatusMqApiService outboundDeliveryStatusMqApiService;//订单取消消息队列推送
	@Autowired
	private OutboundSalesDeliveryApiService outboundSalesDeliveryApiService;//获取物流状态
	@Autowired
	private AddressApiService addressApiService;
	@Autowired 
	private MemberApiService  memberApiService;
	@Autowired 
	private MemberAddressApiService memberAddressApiService;
	@Autowired
	private AftersaleApplyService aftersaleApplyService;
	@Autowired
	private OmsCancelOrderService omsCancelOrderService;
	@Autowired
	private ActivityManagerApiService activityManagerApiService;
	@Autowired
	private OrderProcessManagerApiService orderProcessManagerApiService;//库存操作逻辑新增
	@Autowired
	private OmsOrderSplitService omsOrderSplitService;//拆单逻辑
	@Autowired
	private ActivityGiveApiService activityGiveApiService;
	@Autowired
	private RedisUtil redisUtil;
	@Autowired
	private DictApiService dictApiService;
	@Autowired
	private RedPackageApiService redPackageApiService;
	@Autowired 
	private PartnerApiServiceStationService partnerApiServiceStationService;
	@Autowired
	private PartnerApiService partnerApiService;
    @Override
    public CrudMapper init() {
        return omsOrderMapper;
    }
	@Override
	public ResultDto findOrderByParams(Map<String, Object> params)
			throws ServiceException {
		StringBuilder logBuilder = new StringBuilder("invoke orderApiServiceImpl.findOrderByParams  == >>>\r\n");
		logBuilder.append("params{params:").append(params.toString()).append("}");
		logger.info(logBuilder.toString());
		return null;
	}
	
	
    /***
     * 统一订单操作日志处理
     */
	@Override
	public void operateOrderByType(String omsOrderNo, String operateType)throws Exception {
		StringBuilder builder = new StringBuilder();
		builder.append("invoke OmsOrderServiceImpl.operateOrderByType ===>>>\r\n")
			   .append("params{orderNo:").append(omsOrderNo)
			   .append(",operateType:").append(operateType);
		logger.info(builder.toString());
		OmsOrder order = null;
		Map<String,Object> params =  new HashMap<String,Object>();
    	params.put("orderNo",omsOrderNo);
		
		if( operateType.equals(OrderOperationRecordEnum.MANUAL_CANCEL.getValue())|| operateType.equals(OrderOperationRecordEnum.AUTO_CANCEL.getValue())){
			String lockKey = omsOrderNo + "_cancelOrder";
			if(redisTemplate.opsForValue().setIfAbsent(lockKey, "1")){
			try {
					order = omsCancelOrderService.getOrderByWebhooks(params);
			} catch (Exception e) {
					// TODO Auto-generated catch block
					throw new ServiceException(e);
			}
			if(order==null){
					throw new ServiceException();
			}
			//支付处理中
			if(order.getStatus().equals(OrderStatusConstant.ORDER_PROCESSING)){
				logger.error("【"+order.getOrderNo()+"】,订单支付处理中，不能取消订单");
				throw new ServiceException("该订单支付处理中，不能取消");
			}
			try {
			//设置时间，防止程序挂掉，导致其他线程无法获得锁
			redisTemplate.expire(lockKey, 2L, TimeUnit.MINUTES);
			logger.info("交易锁定");
			Map<String,Object> orderDetailQueryMap = new HashMap<>();
			orderDetailQueryMap.put("orderNo", order.getOrderNo());
			List<OmsOrderdetail> detailList = omsOrderdetailService.findByBiz(orderDetailQueryMap);	
			order.setDetailList(detailList);
			omsCancelOrderService.cancelOrder(order,OrderOperationRecordEnum.valueOf(operateType));
			logger.info("【"+order.getOrderNo()+"】取消订单,订单明细:"+JsonMapper.toJsonString(order.getDetailList()));
			
			//处理红包业务Start 将红包标示为未使用状态
			order.setRedPacketUseIds(this.getRedPacketUseIdsByDetailList(detailList));
			if(StringUtil.isNotNull(order.getRedPacketUseIds())){
				 logger.info("取消订单【"+detailList.get(0).getOrderNo()+"】,使用的红包使用ids："+order.getRedPacketUseIds());
				try{
				this.oprRedPacket(order, "2");
				}catch(Exception e){
					logger.error("【"+order.getOrderNo()+"】取消订单，红包调用接口失败",e);
				}
			}
			//处理红包业务End
			
			for (OmsOrderdetail omsOrderdetail : order.getDetailList()) {
				int buyNum  = omsOrderdetail.getBuyNum();
				//买赠
				if(null!=omsOrderdetail.getPromotions()&&omsOrderdetail.getPromotions().equals("1")){
					//买赠活动相关缓存
					if(null!=omsOrderdetail.getBuyGifts()&&omsOrderdetail.getBuyGifts().equals("1")){
						String userIDKey=com.ffzx.promotion.api.dto.constant.Constant.getMemberGivePayKey(omsOrderdetail.getGiftCommodityItemId(),omsOrderdetail.getCommodityNo(), order.getMemberId());
						rollBackRedisActivityNum_auto(userIDKey,buyNum);
						logger.info("【"+order.getOrderNo()+"】【买赠用户购买量】取消订单,回滚【"+userIDKey+"】缓存");
						String commodityKey=com.ffzx.promotion.api.dto.constant.Constant.getGivePayKey(omsOrderdetail.getGiftCommodityItemId(),omsOrderdetail.getCommodityNo());		
						rollBackRedisActivityNum_auto(commodityKey,buyNum);
						logger.info("【"+order.getOrderNo()+"】【买赠商品购买数量】取消订单,回滚【"+commodityKey+"】缓存");
					}
					if(null!=omsOrderdetail.getBuyGifts()&&omsOrderdetail.getBuyGifts().equals("2")){
						String giftCommodityKey=com.ffzx.promotion.api.dto.constant.Constant.getGiftPayKey(omsOrderdetail.getGiftCommodityItemId(), omsOrderdetail.getSkuCode());
						rollBackRedisActivityNum_auto(giftCommodityKey,buyNum);
						logger.info("【"+order.getOrderNo()+"】【买赠活动赠品购买数量】取消订单,回滚【"+giftCommodityKey+"】缓存");
					}
				} 
				if(!omsOrderdetail.equals(BuyTypeEnum.COMMON_BUY)&&!omsOrderdetail.equals(BuyTypeEnum.ORDINARY_ACTIVITY.getValue())
						//非普通活动以及批发
						&& !omsOrderdetail.equals(BuyTypeEnum.WHOLESALE_MANAGER.getValue())){
				String memberRedisKey = RedisPrefix.APP_PAY_BUYNUM+order.getMemberId()+"_"+omsOrderdetail.getActivityCommodityItemId()+"_"+omsOrderdetail.getCommodityNo()+"_buyNum";
				//回滚用户的购买量
				rollBackRedisActivityNum_auto(memberRedisKey,buyNum);
				logger.info("【"+order.getOrderNo()+"】取消订单,回滚用户的购买量【"+memberRedisKey+"】缓存");
				
				String activityredisKey = RedisPrefix.APP_PAY_BUYNUM+omsOrderdetail.getActivityCommodityItemId()+"_"+omsOrderdetail.getCommodityNo()+"_buyNum";
				//取消定订单回滚缓存之前该商品缓存的购买量
				Object obj_buycount =  redisUtil.incGet(activityredisKey);
				logger.info("【"+order.getOrderNo()+"】取消订单,回滚缓存之前该商品缓存的购买量"+obj_buycount);
				//回滚活动的购买量
				rollBackRedisActivityNum_auto(activityredisKey,buyNum);
				logger.info("【"+order.getOrderNo()+"】取消订单,回滚活动的购买量【"+activityredisKey+"】缓存");
				String skuredisKey = RedisPrefix.APP_PAY_BUYNUM+omsOrderdetail.getActivityCommodityItemId()+"_"+omsOrderdetail.getSkuCode()+"_buyNum";
				//回滚活动的购买量
				rollBackRedisActivityNum_auto(skuredisKey,buyNum);
				logger.info("【"+order.getOrderNo()+"】取消订单,回滚活动的购买量【"+skuredisKey+"】缓存");
				//库存回滚将相关缓存状态修改
				try{				
					String limitKey = RedisPrefix.ACTIVITY + RedisPrefix.ACTIVITY_LIMIT + omsOrderdetail.getActivityCommodityItemId() + "_" + omsOrderdetail.getCommodityNo() + "limit";
					Object obj_limitCount = redisUtil.get(limitKey);
					logger.info("【"+order.getOrderNo()+"】取消订单,回滚缓存限定数量"+obj_limitCount);
					//当回滚缓存之前购买量和限定量数量一样表示该商品已经抢完，因此取消订单要把商品改为进行中（未抢完）
					if(obj_buycount!=null && obj_limitCount!=null){
						logger.info("【"+omsOrderdetail.getCommodityNo()+"】取消订单,总限购量"+obj_limitCount);
						logger.info("【"+omsOrderdetail.getCommodityNo()+"】取消订单,回滚之前购买量"+obj_buycount);	
						if (Integer.parseInt(obj_buycount.toString())== Integer.parseInt(obj_limitCount.toString())) {
							//取消定订单回滚缓存之后该商品缓存的购买量
							logger.info("【"+order.getOrderNo()+"】取消订单,获取回滚缓存之后的购买量");
							Object back_buycount =  redisUtil.incGet(activityredisKey);
							logger.info("【"+order.getOrderNo()+"】取消订单,回滚缓存之后购买量"+back_buycount);
							if(Integer.valueOf(back_buycount.toString())<Integer.valueOf(obj_limitCount.toString())){
								logger.info("【"+omsOrderdetail.getCommodityNo()+"】取消订单,要更新是否抢完");
								// 修改商品状态为进行中
								logger.info("【"+order.getOrderNo()+"】取消订单,是否去更新已抢完状态");
								this.activityManagerApiService.updateActivityCommodityStatusIng(omsOrderdetail.getActivityCommodityItemId());
							}							
						}
					}
					
				}catch(Exception e){
					logger.error("取消订单【"+order.getOrderNo()+"】回滚活动商品"+omsOrderdetail.getCommodityNo()+"是否已经抢完",e);
				}
				
				}
				}
				 
			}catch(Exception e){
					logger.error("订单状态操作異常",e);
					throw e;
			}finally{
					//释放锁
					redisTemplate.delete(lockKey);
			}
			}else{
				throw new ServiceException("请勿重复提交");
			}
		}else if( operateType.equals(OmsOrder.DELETE) ){
			//此操作为用户自行删除订单操作，与后台订单删除操作不相关
		try {
				order = omsCancelOrderService.getOrderByWebhooks(params);
		} catch (Exception e) {
				// TODO Auto-generated catch block
				throw new ServiceException(e);
		}
		if(order==null){
				throw new ServiceException();
		}
			omsCancelOrderService.deleteOrderByUser(order);;
		}else{
			throw new ServiceException("请检查operateType值");
		}
		
		/*if( operateType.equals(OmsOrder.SIGN) ){
			singOrder(order);
		}else if( operateType.equals(OmsOrder.BACK) ){
			backOrder(order);
		}else if( operateType.equals(OmsOrder.RETURN_BACK) ){
			returnBackOrder(order);
		}else if( operateType.equals(OmsOrder.CANCEL) ){
			cancelOrder(order);
		}else if( operateType.equals(OmsOrder.DELETE) ){
			deleteOrder(order);
		}else if( operateType.equals(OmsOrder.PAYSUCCESS) ){
			paySuccessOrder(order);
		}else{
			throw new ServiceException("请检查operateType值");
		}*/
		logger.info("over OmsOrderServiceImpl.operateOrderByType ===>>>");
	}

	/**签收订单
	 * @throws ServiceException */
	private void singOrder(OmsOrder order) throws ServiceException{
		//状态检查(是否允许签收),必须在待收货状态才允许签收
//		if( !OrderStatusEnum.RECEIPT_OF_GOODS.equals(order.getStatus()) ){
//			throw new ServiceException("当前订单状态非法,无法完成签收动作");
//		}
		//TODO 下面的逻辑貌似不用写了,因为如果要有收货操作,当前OmsOrder实体少字段,少状态
	}
	/*****
	 *  0：待付款，1：待收货，2：退款申请中，3：交易关闭，4：交易完成，5：订单已取消
	 */
	/**退单申请(申请售后)*/
	@Transactional(rollbackFor=Exception.class)
	private void backOrder(OmsOrder order) throws ServiceException{
		//TODO 其它逻辑不应该放到api子系统中写 ; 既然你要对订单申请售后,就应该全部由订单系统负责,包括去调用其它dubbo服务等也应该全部封装至此
		//数据校验
		judgeBackOrder(order.getBuyType());
		//如果处于已付款状态,则直接改为   交易关闭   状态
		if( "0".equals(order.getStatus()) ){
			order.setStatus("3"); //变更状态
			Date tmpDate = new Date();
			order.setChargeBackTime(tmpDate);//退单时间
			order.setFinishTime(tmpDate);//完成订单时间
		}else{
			//TODO 生成退款单(包括一主表,退款单详情等一系列数据)
			order.setStatus("2");//退款申请中
		}
		omsOrderMapper.updateOrderByShouhou(order);
	}
	/**检验数据*/
	private void judgeBackOrder(BuyTypeEnum type) throws ServiceException{
		//判断,如果是预售商品,则不允许申请退单 --修改 "退款" 和   "退货"  这个描述, 由测试提出bug...
		if( BuyTypeEnum.PRE_SALE.equals(type) ){
			throw new ServiceException("预售商品非质量问题，暂不支持退款，如有商品质量问题，请联系客服400-717-6800");
		}
		//判断,如果是抢购商品,则不允许申请退单 --修改 "退款" 和   "退货"  这个描述, 由测试提出bug...
		if( BuyTypeEnum.PANIC_BUY.equals(type) ){
			throw new ServiceException("抢购商品非质量问题，暂不支持退款，如有商品质量问题，请联系客服400-717-6800");
		}
	}
	
	/**撤销退单*/
	private void returnBackOrder(OmsOrder order) throws ServiceException{
		//TODO 需要根据退单申请逻辑来定
	}
	
	/**取消订单*/
	@Transactional(rollbackFor=Exception.class)
	public void cancelOrder(OmsOrder order,OrderOperationRecordEnum operationRecord) throws ServiceException{
		logger.info("订单取消操作开始==============="+order.getOrderNo());
		//如果不是代付款状态和交易完成状态,则不允许取消
		if( !OrderStatusConstant.STATUS_PENDING_PAYMENT.equals(order.getStatus()) 
				&& !OrderStatusConstant.TRANSACTION_COMPLETION.equals(order.getStatus())){
			throw new ServiceException("取消订单失败，您的订单不处于下单状态,无法完成此操作"); 
		}
		//判断订单是否已支付,如果已支付,则不允许取消
		//TODO 先注释，否则测试无法进行！！！
		if( judgeIsPayed(order.getChargeId(),order.getOrderNo()) ){
			throw new ServiceException("取消订单失败，您的订单正处于付款确认阶段,无法完成此操作");
		}
	
		
		//订单状态的更改
		order.setStatus("5");
		order.setOperationDate(new Date());//记录订单操作时间
		order.setOperationRecord(operationRecord); //记录订单操作原因
		omsOrderMapper.updateOrderStatus(order);
		//优惠券回滚操作,促销系统调用
		if( StringUtil.isNotNull(order.getCouponId()) ){
			invokeCrmsByCancelOrder(order.getCouponId(),order.getMemberId());
		}
		//库存回滚操作;调用WMS系统
		invokeWmsByCancelOrder(order);
		//取消订单数据插入到消息队列
		/*logger.info("订单取消操作调用推送消息到wms==============="+order.getOrderNo()+order.getStatus());
		outboundDeliveryStatusMqApiService.outboundDeliveryStatus(order.getOrderNo(),5);
		logger.info("订单取消操作调用推送消息到wms==============="+order.getOrderNo()+order.getStatus());*/
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
			List<OrderParam> orderParamList = initOrderParamInfo(order.getDetailList());
			ResultDto wmsResultDto =  orderProcessManagerApiService.cancelOrder(orderParamList, order.getRegionId(), order.getOrderNo());
			if(!ResultDto.OK_CODE.equals(wmsResultDto.getCode())){
						logger.error("【"+order.getOrderNo()+"】取消订单,库存回滚：===========orderProcessManagerService.cancelOrderorderProcessManagerService.pendingPayment("+JsonMapper.toJsonString(orderParamList)+","+order.getRegionId()+","+order.getOrderNo()+",订单取消\"),取消订单操作库存失败");
						throw new ServiceException(wmsResultDto.getMessage());
			}
		}catch(Exception e){
			logger.error("【"+order.getOrderNo()+"】库存回滚,OmsOrderServiceImpl.invokeWmsByCancelOrder.cancel error ,reason invoke by WMS stockManagerApiService.cancelOrder error", e);
			throw new ServiceException("接口调用失败，库存回滚报错",e);
		}
	}
	
	//活动商品数量 回滚缓存 
	public void rollBackRedisActivityNum_auto(String redisKey,int buyNum){
			if(redisUtil.exists(redisKey)){
				redisUtil.decrease(redisKey, buyNum);
				logger.error("取消订单，redisKey:【"+redisKey+"】回滚成功，且buyNum:【"+buyNum+"】");
			}else{
				logger.error("取消订单，redisKey:【"+redisKey+"】不存在！");
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
					redisUtil.set(redisKey, (curBuyNum>0?curBuyNum:0)+"");
					logger.info("取消订单,回滚【"+redisKey+"】缓存成功，curBuyNum：【"+curBuyNum+"】");
				}
		}
	private void invokeCrmsByCancelOrder(String couponId,String memberId){
		//优惠券回滚操作,促销系统调用
		try{
			//ResultDto couponResultDto = couponReceiveApiService.updateReceiveState(couponId, "0");
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
	/**删除订单 调用时注意此操作为订单逻辑删除区分与用户自行删除订单操作*/
	@Transactional(rollbackFor=Exception.class)
	private void deleteOrder(OmsOrder order) throws ServiceException{
		//TODO 所有业务逻辑都在cancelOrder中完成,因为不允许直接删除订单,必须先取消订单
		order.setDelFlag(Constant.DELTE_FLAG_YES);
		omsOrderMapper.updateOrderDelFlag(order);
	}
	
	/**支付成功调用*/
	private void paySuccessOrder(OmsOrder order) throws ServiceException{
		//条件判断,是否满足继续往下走的条件;避免第三方多次回调触发重复业务更改
		if( order==null || StringUtil.isEmpty(order.getChargeId()) || order.getPayTime()==null ){
			throw new ServiceException("当前订单状态非法,无法确认支付信息");
		}
		//订单的拆分操作(包括普通订单拆分,预售订单的拆分)
		boolean splitFlag = false; //标识是否被拆分过,如果被拆分过,那么原始订单将被移除,不需要再进行下一步业务操作
		try{ //订单的拆分
			//splitFlag = orderSplit(order);
		}catch(Exception e){
			logger.error("订单拆分 : invoke omsOrderServiceImpl.paySuccessOrder error ===>>>",e);
			throw new ServiceException(e);
		}
		if(!splitFlag){
			//分配送货人以及拣货人,错误不能影响支付成功业务
//			try{
//				order = fpshr(order);
//				order = fpjhr(order);
//			}catch(Exception e){
//				logger.error("合伙人分配 : invoke omsOrderServiceImpl.paySuccessOrder error ===>>>",e);
//			}
			//更改支付成功业务需修改的属性值
			order.setStatus("1");//变更为待收货状态
			order.setPayTime(new Date());//支付时间
			omsOrderMapper.updateByPrimaryKeySelective(order);
		}
		//支付记录增加数据
		PaymentRecord paymentRecord = buildPaymentRecord(order.getOrderNo(), order.getActualPrice());
		paymentRecordService.add(paymentRecord);
		//TODO 推送信息的发送
		//TODO 预留,支付成功事件触发,比如送优惠券什么的;调用相关子系统
	}
	
	private PaymentRecord buildPaymentRecord(String payOrderNo,BigDecimal amount){
		PaymentRecord paymentRecord = new PaymentRecord();
		paymentRecord.setPayStatus(1+"");
		paymentRecord.setUpdateDate(new Date());
		return paymentRecord;
	}
	/***
	 * 订单的拆分(包含普通订单拆分以及预售订单拆分两种拆分策略)
	 * @param order 订单实体
	 * @throws ServiceException
	 * @author ying.cai
	 * @email ying.cai@ffzxnet.com
	 * @date 2016-5-10 下午06:15:38
	 * @version V1.0
	 * @return boolean 是否拆单
	 * @throws CloneNotSupportedException 
	 */
	private boolean orderSplit(OmsOrder order,List<OmsOrder> splitOrderList)throws ServiceException, CloneNotSupportedException{
		//找出订单下的订单明细数据
		List<OmsOrderdetail> orderDetailList = new ArrayList<OmsOrderdetail>();
		if(orderDetailList.size()<=1){ //如果订单明细只有一个,那肯定是不需要拆分的
			return false;
		}
		//拆分归类算法
		Map<String, List<OmsOrderdetail>> orderDetailMap =  null;
		if( BuyTypeEnum.PRE_SALE.equals(order.getBuyType())){//预售订单归类不同
			
		}else{//普通订单归类
			orderDetailMap = sortCommonOrderMap(orderDetailList);
		}
		if(orderDetailMap.size()<=1){//如果都是同一类,则不进行拆单
			return false;
		}
		Date payTime = new Date();
		List<OmsOrder> saveOrders = new ArrayList<OmsOrder>();//要保存的新订单
		for (Map.Entry<String, List<OmsOrderdetail>> entry : orderDetailMap.entrySet()) {
			BigDecimal totalPrice = new BigDecimal(0D);//订单总价
			BigDecimal actualPrice = new BigDecimal(0D);//实际支付金额
			int buyCount = 0 ;//新订单购买商品数量
			OmsOrder newOrder =  (OmsOrder) order.clone();//克隆订单
			newOrder.setId(UUID.randomUUID().toString());
			newOrder.setOrderNo( order.getOrderNo()+"_"+(splitOrderList.size()+1) );//新订单号
			for(OmsOrderdetail orderDetail : entry.getValue()){
				totalPrice = totalPrice.add( orderDetail.getCommodityPrice().multiply( new BigDecimal(orderDetail.getBuyNum())));
				actualPrice = totalPrice.add( orderDetail.getActPayAmount().multiply( new BigDecimal(orderDetail.getBuyNum())));
				buyCount += orderDetail.getBuyNum();
				orderDetail.setOrderNo(newOrder.getOrderNo());
				orderDetail.setOrderId(newOrder.getId());
				omsOrderdetailMapper.updateDetBySplit(orderDetail);//更新数据
			}
			newOrder.setId(UUID.randomUUID().toString());
			newOrder.setPayOrderNo(order.getOrderNo());//支付订单号保证一样
			newOrder.setTotalPrice(totalPrice);
			newOrder.setActualPrice(actualPrice);
			newOrder.setStatus("1");//变更为待收货状态
			newOrder.setPayTime(payTime);//支付时间
			newOrder.setBuyCount(buyCount);	
			saveOrders.add(newOrder);//保存新单
			//支付记录增加数据
			PaymentRecord paymentRecord = buildPaymentRecord(newOrder.getOrderNo(), newOrder.getActualPrice());
			paymentRecordService.add(paymentRecord);
		}
		//优惠券均分
		if(order.getFavorablePrice()!=null && order.getFavorablePrice().doubleValue()>0){
			List<BigDecimal> couponPriceList = averagePrice(order.getFavorablePrice(), saveOrders);
			for (int i = 0; i < saveOrders.size(); i++) {
				OmsOrder tmpOmsOrder = saveOrders.get(i);
				//实际支付价格= 总价 - 新优惠价
				tmpOmsOrder.setActualPrice(tmpOmsOrder.getTotalPrice().subtract(couponPriceList.get(i)));
				tmpOmsOrder.setFavorablePrice(couponPriceList.get(i)); //新优惠价格
				saveOrders.set(i, tmpOmsOrder);
			}
		}
		//运费均分
		if(order.getSendCost()!=null && order.getSendCost().doubleValue()>0){
			List<BigDecimal> sendPriceList = averagePrice(order.getSendCost(),saveOrders);
			for (int i = 0; i < saveOrders.size(); i++) {
				OmsOrder tmpOmsOrder = saveOrders.get(i);
				//总价= 实际价格+运费
				tmpOmsOrder.setTotalPrice(tmpOmsOrder.getActualPrice().add(sendPriceList.get(i)));
				tmpOmsOrder.setSendCost(sendPriceList.get(i));
				saveOrders.set(i, tmpOmsOrder);
			}
		}
		//将旧单废弃
		order.setDelFlag(Constant.DELTE_FLAG_YES);
		omsOrderMapper.updateOrderDelFlag(order);
		//取得订单明细更新操作sql
		//持久化操作
		addBatchOrder(saveOrders);
		return true;
	}
	
	private void addBatchOrder(List<OmsOrder> saveOrders) throws ServiceException{
		for (OmsOrder omsOrder : saveOrders) {
			add(omsOrder);
		}
	}
	/***
	 * 对普通订单进行归类
	 * @return
	 * @author ying.cai
	 * @email ying.cai@ffzxnet.com
	 * @date 2016-5-11 下午03:42:29
	 * @version V1.0
	 */
	private Map<String, List<OmsOrderdetail>> sortCommonOrderMap(List<OmsOrderdetail> orderDetailList){
		//拆分逻辑, 1:找出订单详细  2:将详细中的商品进行归类  3:归类后再将商品供货类型为供应商类的进行再一次归类,按照供应商是否相同的条件进行归类
		//map 分3类, 1:由DMC自己供货,  2:不同的供应商     3:错误的商品数据(没有供应商的数据)
		Map<String,List<OmsOrderdetail>> orderDetailMap = new HashMap<String, List<OmsOrderdetail>>();
		//开始对订单详细数据进行分类
		//2016-4-19 第一次归类,将区域性保护商品以及非区域性保护商品进行归类
		Map<String,List<OmsOrderdetail>> waitSplitOrderDetailMap = new HashMap<String, List<OmsOrderdetail>>();
		for (OmsOrderdetail orderDetail : orderDetailList) {
			//TODO 需要从orderDetail中拿出商品是否受保护
			if(1 == 1 /**TODO 判断是否受保护*/){//受保护
				waitSplitOrderDetailMap = fillOrderMap(waitSplitOrderDetailMap, orderDetail, "PROTECT_TRUE");
			}else{
				waitSplitOrderDetailMap = fillOrderMap(waitSplitOrderDetailMap, orderDetail, "PROTECT_FALSE");
			}
		}
		//第二步,进行详细归类
		int count = 1;//标识,用于区别保护和非保护商品归类
		for(List<OmsOrderdetail> detailList : waitSplitOrderDetailMap.values()){
			for (OmsOrderdetail orderDetail : detailList) {
//				Goods goods = orderDetail.getGoods();
//				//第一规则,如果该商品是需要拆单的,那么就直接是一个新单
//				if(goods.getIsNeedSplit()){
//					orderDetailMap = fillOrderMap(orderDetailMap, orderDetail,count+"split_" + goods.getId());
//				}else if(SupplyGoodsEnum.DMC.equals(goods.getSupplyType())){ //由DMC供货
//					orderDetailMap = fillOrderMap(orderDetailMap,orderDetail,count+"dmc");
//				}else if(SupplyGoodsEnum.SUPPLIER.equals(goods.getSupplyType()) && goods.getProvideExt()!=null){//由供应商供货
//					//将供应商ID作为key
//					orderDetailMap = fillOrderMap(orderDetailMap,orderDetail,count+goods.getProvideExt().getId());
//				}else{//错误数据, 可能以前的商品没有这个枚举值
//					orderDetailMap = fillOrderMap(orderDetailMap,orderDetail,count+"error");
//				}
				//TODO 拆单规则
			}
			++count;
		}
		return orderDetailMap;
	}
	
	/***
	 * 对预售订单进行归类,直接就是一个详情一个订单
	 * @return
	 * @author ying.cai
	 * @email ying.cai@ffzxnet.com
	 * @date 2016-5-11 下午03:42:29
	 * @version V1.0
	 */
	private Map<String, List<OmsOrderdetail>> sortPreOrderMap(List<OmsOrderdetail> orderDetailList){
		Map<String,List<OmsOrderdetail>> orderDetailMap = new HashMap<String, List<OmsOrderdetail>>();
		for(OmsOrderdetail omsOrderdetail:orderDetailList){
			fillOrderMap(orderDetailMap, omsOrderdetail, omsOrderdetail.getId());
		}
		return orderDetailMap;
	}
	
	/**填充map数据*/
	private Map<String,List<OmsOrderdetail>> fillOrderMap(Map<String,List<OmsOrderdetail>> orderDetailMap,OmsOrderdetail orderDetail,String key){
		if(orderDetailMap.containsKey(key)){
			orderDetailMap.get(key).add(orderDetail);
		}else{
			List<OmsOrderdetail> details = new ArrayList<OmsOrderdetail>();
			details.add(orderDetail);
			orderDetailMap.put(key,details );
		}
		return orderDetailMap;
	}
	
	//均分操作
	/**
	 * 对某个金额按照集合数进行均分操作
	 * @param price 总金额
	 * @param orders 要均分的订单集合对象
	 * @return
	 */
	public List<BigDecimal> averagePrice(BigDecimal price,List<OmsOrder> orders){
		List<BigDecimal> result = new ArrayList<BigDecimal>();
		BigDecimal addDec = new BigDecimal(0);
		//取出金额
		BigDecimal total = new BigDecimal(0);
		for (int i = 0; i < orders.size(); i++) {
			total = total.add((orders.get(i)).getActualPrice());//添加每个订单的实际金额
		}
		for (int i = 0; i < orders.size(); i++) {
			OmsOrder tempO = orders.get(i);
			BigDecimal resultPrice = new BigDecimal(0);
			if(i==orders.size()-1){//如果为最后一项则不需要计算
				resultPrice = price.subtract(addDec);//减去之前计算过的。
			}else{
				resultPrice = price.multiply(tempO.getActualPrice().divide(total,2, BigDecimal.ROUND_HALF_UP)).setScale(2, BigDecimal.ROUND_HALF_UP);
				addDec = addDec.add(resultPrice);
			}
			result.add(resultPrice);
		}
		return result;
	}
	
//	private String concatSqlToUpdateOrderDetail(List<OmsOrderdetail> orderDetail,String orderNo,String orderId){
//		StringBuilder sqlBuild = new StringBuilder();
//		sqlBuild.append("UPDATE oms_orderdetail det SET det.order_id = '")
//				.append(orderId).append("',det.order_no = '")
//				.append(orderNo).append(",WHERE det.id IN (");
//		for (OmsOrderdetail orderdetail : orderDetail) {
//			sqlBuild.append("'").append(orderdetail.getId()).append("'");
//		}
//		sqlBuild.append(")");
//		return sqlBuild.toString();
//	}

	@Override
	public OmsOrder fpshr(String orderNo) throws ServiceException {
		OmsOrder order = new OmsOrder();
		return fpshr(order);
	}

	@Override
	public OmsOrder fpshr(OmsOrder order) throws ServiceException {
		logger.info("invoke orderApiServiceImpl.fpshr === >>>\r\n params{orderEntity:"+order.toString());
		//无需再去调用wms系统计算合伙人,因为下单时已经计算好
		//TODO 配送时间计算,默认付款后1天
		Date sendDate = computeSendDate(order,order.getBuyType());
		order.setPreSendCommodityTime(sendDate);//发货时间
		order.setSendPerson(order.getPartnerId());//配送员如果没有特殊分配情况,那么就是下单时保存的合伙人
		return order;
	}
	
	private Date computeSendDate(OmsOrder order, BuyTypeEnum buyType){
		Date sendDate = new Date();
		//如果是预售购买,则直接取下单时保存的预计发货时间
		if(BuyTypeEnum.PRE_SALE.equals(buyType)){
			sendDate = order.getPreSendCommodityTime();
		}else{//其它购买都是一样的规则
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(new Date());
			calendar.add(Calendar.DAY_OF_MONTH, 2);
			sendDate = calendar.getTime();
		}
		return sendDate;
	}

	@Override
	public OmsOrder fpjhr(String orderNo) throws ServiceException{
		OmsOrder order = new OmsOrder();
		return fpjhr(order);
	}

	@Override
	public OmsOrder fpjhr(OmsOrder order) throws ServiceException {
		logger.info("invoke orderApiServiceImpl.fpjhrr === >>>\r\n params{orderEntity:"+order.toString());		
		//TODO 目前没有看到任何有关于拣货人的属性
		return order;
	}
	@Override
	public void notSendArea(OmsOrder orderNo) {
		logger.info("invoke orderApiServiceImpl.notSendArea === >>>\r\n params{orderNo:"+orderNo);	
		OmsOrder order = new OmsOrder();
		//TODO 设置不是我配送范围的属性值, 此属性暂无
	}

	@Override
	public OmsOrder findOrderInfo(String orderNo) {
		logger.info("invoke orderApiServiceImpl.findOrderInfo === >>>\r\n params{orderNo:"+orderNo);
		Map<String, Object> params  = new HashMap<String, Object>();
		params.put("orderNo", orderNo);
		OmsOrder omsOrder = this.omsOrderMapper.selectByKey(params);
		List<OmsOrderdetail> odList  =  this.omsOrderdetailService.findByBiz(params);
		omsOrder.setDetailList(odList);
		return omsOrder;
	}
	@Override
	public OmsOrder findOrderInfoById(String orderId) {
		logger.info("invoke orderApiServiceImpl.findOrderInfoById === >>>\r\n params{orderId:"+orderId);
		Map<String, Object> params  = new HashMap<String, Object>();
		params.put("id", orderId);
		OmsOrder omsOrder = this.omsOrderMapper.selectByKey(params);
		params.clear();
		params.put("orderId", orderId);
		List<OmsOrderdetail> odList  =  this.omsOrderdetailService.findByBiz(params);
		omsOrder.setDetailList(odList);
		return omsOrder;
	}
	@Override
	public List<OmsOrder> queryByPage(Page page,Map<String, Object> params) throws Exception {
		logger.info("invoke orderApiServiceImpl.findOrderInfo === >>>\r\n params:"+params.toString()+" === >>>\r\n page"+page.toString());
		/**
		 * 2016-09-21-雷--不补货时查待付款的
		 */
		if (params.containsKey("supplyFlag") && OrderDetailStatusConstant.NO.equals(params.get("supplyFlag"))) {
			params.put("status", OrderStatusConstant.STATUS_RECEIPT_OF_GOODS);
			
		}
		List<OmsOrder> list = omsOrderMapper.selectPageOrder(page, params,true);
		List<String> orderNoList=new ArrayList<String>();
		for(OmsOrder oms:list){ 
			orderNoList.add(oms.getOrderNo());
		}
		Map<String,Object> newPar=new HashMap<>();
		newPar.put("orderNoList", orderNoList);
		
		//支撑分销系统业务查询关键字 Start
		if(params.containsKey("od_title_bar_like")){
			newPar.put("od_title_bar_like", params.get("od_title_bar_like"));
		}
		
		if(params.containsKey("od_orderDetailStatusList")){
			newPar.put("od_orderDetailStatusList", params.get("od_orderDetailStatusList"));
		}
		
		if(params.containsKey("od_buyTypeList")){
			newPar.put("od_buyTypeList", params.get("od_buyTypeList"));
		}
		//排除掉虚拟商品
		if(params.containsKey("commodityTypeCode")){
			newPar.put("commodityTypeCode", Constant.NO);
		}
		

		//支撑分销系统业务查询关键字 End
		/**
		 * 查询详情信息
		 */
		if(CollectionUtils.isNotEmpty(orderNoList))
		{
			List<OmsOrderdetail> delist = omsOrderdetailMapper.selectByParams(newPar);
			for (OmsOrder order : list)
			{
				List<OmsOrderdetail> detailList = new ArrayList<>();
				for(OmsOrderdetail de:delist){
					if(order.getOrderNo().equals(de.getOrderNo())){
						detailList.add(de);
					}
				}
				order.setDetailList(detailList);
			}
		}
 
		return list;
	}
	@Override
	public List<OmsOrder> queryByList(Map<String, Object> params) throws Exception {
		// TODO Auto-generated method stub
		logger.info("invoke orderApiServiceImpl.findOrderInfo === >>>\r\n params:"+params.toString());
		List<OmsOrder> list = omsOrderMapper.select(params);
		Map<String, Object> odParams  = new HashMap<String, Object>();
		for (OmsOrder omsOrder : list) {
			odParams.clear();
			odParams.put("orderId", omsOrder.getId());
			List<OmsOrderdetail> odList  =  omsOrder.getDetailList();
			if(odList!=null){
				omsOrder.setDetailList(odList);
				
				for(OmsOrderdetail omsOrderdetail : odList){
					Commodity commodity = commodityService.getCommodityByCodeFromRedis(omsOrderdetail.getCommodityNo());
					omsOrderdetail.setCommodity(commodity);
				}
			}
				
			
		}
		return list;
	}
	@Override
	public List<OmsOrder> queryListExport(Map<String, Object> params) throws Exception {
		// TODO Auto-generated method stub
				logger.info("invoke orderApiServiceImpl.queryListExport === >>>\r\n params:"+params.toString());
				StopWatch sw_queryListExport = new StopWatch("queryListExport");
				sw_queryListExport.start("queryListExport==》》订单数据查询");
				List<String> commodityCodeList=new ArrayList<String>();
				List<OmsOrder> list = omsOrderMapper.select(params);
				sw_queryListExport.stop();
				sw_queryListExport.start("queryListExport==》》订单关联商品信息查询");
				Map<String, Object> commodityParams  = new HashMap<String, Object>();
				for (OmsOrder omsOrder : list) {
					List<OmsOrderdetail> odList  =  omsOrder.getDetailList();
					if(odList!=null){
						for(OmsOrderdetail omsOrderdetail : odList){
							commodityCodeList.add(omsOrderdetail.getCommodityNo());
						}
					}
				}
				commodityParams.put("commodityCodeList", commodityCodeList);
				List<Commodity> commodityList = commodityMapper.selectByParams(commodityParams);
				for (OmsOrder omsOrder : list) {
					List<OmsOrderdetail> odList  =  omsOrder.getDetailList();
					if(odList!=null){
						omsOrder.setDetailList(odList);
						for(OmsOrderdetail omsOrderdetail : odList){
							for(Commodity commodity :commodityList){
								if(omsOrderdetail.getCommodityNo().equals(commodity.getCode())){
									omsOrderdetail.setCommodity(commodity);
								}
							}
						}
					}
				}
				sw_queryListExport.stop();
				logger.error("导出订单数据查询耗时："+sw_queryListExport.prettyPrint());
				return list;
	}
	@Override
	@Transactional(rollbackFor=Exception.class)
	public void updatePrice(OmsOrder order, List<OmsOrderdetail> detailList) throws Exception {
		// TODO Auto-generated method stub
		for (OmsOrderdetail omsOrderdetail : detailList) {
			this.omsOrderdetailService.modifyById(omsOrderdetail);
		}
		this.modifyById(order);
		
	}
	
	@Override
	public List<OmsOrder> searchExchangeOrderList(Page page, Map<String, Object> params) throws Exception {
		logger.info("invoke orderApiServiceImpl.searchExchangeOrderList === >>>\r\n params:"+params.toString()+" === >>>\r\n page"+page.toString());
		// 获取换货订单列表
		List<OmsOrder> list = omsOrderMapper.selectExchangeOrder(page, params);
		Map<String, Object> odParams  = new HashMap<String, Object>();
		for (OmsOrder omsOrder : list) {
			odParams.clear();
			odParams.put("orderId", omsOrder.getId());
			// 获取订单详情
			List<OmsOrderdetail> odList  =  this.omsOrderdetailService.findByBiz(odParams);
			if(odList!=null)
				omsOrder.setDetailList(odList);
		}
		
		return list;
	}

	@Override
	@Transactional(rollbackFor=Exception.class)
	public int updateStatus(OmsOrder order) throws ServiceException {
		int result=this.omsOrderMapper.updateByPrimaryKeySelective(order);
		return result;
	}
	@Override
	@Transactional(rollbackFor=Exception.class)
	public void orderPaySuccess(String orderNo,String charge_id,String payExtra, Date paidTime,PayTypeEnum payType) throws ServiceException {
		// TODO Auto-generated method stub
		OmsOrder  omsOrder  = findOrderInfo(orderNo);
		omsOrder.setRedPacketUseIds(this.getRedPacketUseIdsByDetailList(omsOrder.getDetailList()));
		if(!OrderStatusConstant.STATUS_PENDING_PAYMENT.equals(omsOrder.getStatus())&&!OrderStatusConstant.ORDER_PROCESSING.equals(omsOrder.getStatus())){
			logger.info("oms-OmsOrderServiceImpl==>>orderPaySuccess==》》【"+omsOrder.getOrderNo()+"】该单不属于待付款状态");
			throw  new ServiceException("该单不属于待付款状态");
		}
		omsOrder.setId(omsOrder.getId());
		omsOrder.setStatus("1");//待收货状态
		omsOrder.setPayTime(paidTime);//支付成功的时间
		if(null!=payType){
			omsOrder.setPayType(payType);
		}
		if(null!=charge_id){
			omsOrder.setChargeId(charge_id);
		}
		//暂且不知支付成功后的业务只更改状态
		OmsOrder toUpdateOrder = new OmsOrder();
		toUpdateOrder.setId(omsOrder.getId());
		toUpdateOrder.setStatus(omsOrder.getStatus());//待收货状态
		toUpdateOrder.setPayTime(omsOrder.getPayTime());//支付成功的时间
		if(StringUtil.isNotNull(payExtra)){
		toUpdateOrder.setPayExtra(payExtra);
		}
		if(null!=payType){
		toUpdateOrder.setPayType(payType);
		}
		if(null!=charge_id){
			toUpdateOrder.setChargeId(charge_id);
		}
		this.modifyById(toUpdateOrder);
		String msg = "付款成功，您的订单开始处理";
		this.insertOmsOrderRecord(omsOrder, omsOrder.getMemberId(), omsOrder.getMemberName()==null?omsOrder.getMemberName():omsOrder.getMemberPhone(), msg, "1");
		logger.info("oms-OmsOrderServiceImpl==>>orderPaySuccess==》》【"+omsOrder.getOrderNo()+"】支付订单成功："+JsonMapper.toJsonString(omsOrder));
		//拆单业务
		logger.info("oms-OmsOrderServiceImpl==>>orderPaySuccess==》》【"+omsOrder.getOrderNo()+"】开始检验是否拆单");
		List<OmsOrder> splitOrderList = new ArrayList<OmsOrder>();//拆分后订单集合
		try {
			//this.orderSplitSingle(omsOrder,splitOrderList);
			splitOrderList = omsOrderSplitService.split(omsOrder);
			if(splitOrderList!=null &&splitOrderList.size()>1){
				logger.info("oms-OmsOrderServiceImpl==>>orderPaySuccess==》》【"+omsOrder.getOrderNo()+"】拆分splitOrderList:"+JsonMapper.toJsonString(splitOrderList));
				//更新子单
				for (OmsOrder newOrder : splitOrderList) {
					this.copyOrderOperateRecord(omsOrder.getOrderNo(), newOrder);
					this.add(newOrder);
					for(OmsOrderdetail orderDetail : newOrder.getDetailList() ){
						omsOrderdetailMapper.updateDetBySplit(orderDetail);//更新数据
					}
				}
				this.deleteOrder(omsOrder); //移除父单
			}else{
				List<OmsOrderdetail> _omsOrderdetailList = splitOrderList.get(0).getDetailList();
				if(null!=_omsOrderdetailList&&_omsOrderdetailList.size()>0){
					for (int i=0;i<_omsOrderdetailList.size();i++) {
						OmsOrderdetail omsOrderdetail = _omsOrderdetailList.get(i);
						if(null!=omsOrderdetail.getGiftList()&&omsOrderdetail.getGiftList().size()>0){
							logger.info("oms-OmsOrderServiceImpl==>>orderPaySuccess==》》【"+omsOrder.getOrderNo()+"】添加赠品："+omsOrderdetail.getId()+",giftList："+JsonMapper.toJsonString(omsOrderdetail.getGiftList()));
							_omsOrderdetailList.addAll(omsOrderdetail.getGiftList());
						}
					}
				logger.info("oms-OmsOrderServiceImpl==>>orderPaySuccess==》》【"+omsOrder.getOrderNo()+"】拆分omsOrderdetailList:"+JsonMapper.toJsonString(_omsOrderdetailList));
				omsOrder.setDetailList(_omsOrderdetailList);
				}
			}
		} catch (Exception e) {
			logger.error("【"+omsOrder.getOrderNo()+"】拆单失败",e);
			throw new ServiceException(e);
		}
		sendMsg(omsOrder.getId(),omsOrder.getOrderNo(), omsOrder.getMemberPhone(),omsOrder.getMemberId());
		//2消息队列  给 wms推送订单新发货 
		if(null!=splitOrderList && splitOrderList.size()>1){
			for (OmsOrder order : splitOrderList) {
					logger.info("oms-OmsOrderServiceImpl==>>orderPaySuccess==》》主订单【"+order.getOrderNo()+"】====>>子订单【"+order.getOrderNo()+"】推送订单信息到wms ====================== >"+JsonMapper.toJsonString(order));
					OutboundSalesDeliveryMqApiService.outboundSalesDelivery(order);
			}
		}else{
			logger.info("oms-OmsOrderServiceImpl==>>orderPaySuccess==》》主订单【"+omsOrder.getOrderNo()+"】====>>推送订单信息到wms ====================== >"+JsonMapper.toJsonString(omsOrder));
			OutboundSalesDeliveryMqApiService.outboundSalesDelivery(omsOrder);
		}
		//推送消费金额
		pushMemberCost(omsOrder);
		try {
			Member member = new Member();
			member.setId(omsOrder.getMemberId());
			member.setIsNew("0");
			memberApiService.updateMember(member);
			} catch (Exception e) {
				logger.error("将新用户标示修改为老用户，调用接口失败",e);
			}
		this.sendOrderPaidRadio(omsOrder);
		
		if(StringUtil.isNotNull(omsOrder.getRedPacketUseIds())){
			try{
		    logger.info("订单支付成功【"+omsOrder.getOrderNo()+"】,使用的红包使用ids："+omsOrder.getRedPacketUseIds());
			this.oprRedPacket(omsOrder, "1");
			}catch(Exception e){
				logger.error("【"+omsOrder.getOrderNo()+"】支付成功，红包调用接口失败",e);
			}
		}
	}

	/**
	 * 
	 * @param detailList
	 * @return
	 * @author yinglong.huang
	 * @email yinglong.huang@ffzxnet.com
	 * @date 2016年11月22日 上午11:19:24
	 * @version V1.0
	 * @return
	 */
	public String getRedPacketUseIdsByDetailList(List<OmsOrderdetail> detailList){
			List<String> redPacketIdList = new ArrayList<String>();
			for (OmsOrderdetail omsOrderdetail : detailList) {
				if(StringUtil.isNotNull(omsOrderdetail.getRedPacketUseIds())){
					String[] ids = omsOrderdetail.getRedPacketUseIds().split(";");
					for (String string : ids) {
						if(!redPacketIdList.contains(string)){
							redPacketIdList.add(string);
						}
					}
				}
			}
			Iterator<String> it = redPacketIdList.iterator();  
			String redPacketIds = "";
	        while(it.hasNext()){ 
	        	String id =it.next();
	        	id+=it.hasNext()?";":"";
	        	redPacketIds+=id.trim();
	        } 
	        logger.info("【"+detailList.get(0).getOrderNo()+"】,使用的红包使用ids："+redPacketIds);
			return redPacketIds;
	}

	//向消费用户推送消费金额
	private void pushMemberCost(OmsOrder  omsOrder){
		Map<String, Object> object=new HashMap<String, Object>();
		object.put("memberId", omsOrder.getMemberId());
		object.put("price", omsOrder.getActualPrice());
		rabbitTemplate.convertAndSend("PRICE_ADD_PAYMENT_ORDER", object);
	}
	//订单支付成功 发送广播
	private void sendOrderPaidRadio(OmsOrder omsOrder){
		if(null!=omsOrder){
			try{
				rabbitTemplate.convertAndSend("OMS_PAID_CHANGE_EXCHANGE", "", omsOrder);
				}catch (Exception e) {
					logger.error("【"+omsOrder.getOrderNo()+"】订单支付成功交易完成发送广播失败",e);
				}
			}
		}
	/**发送与保存消息 ying.cai*/
	private void sendMsg (String id,String orderNo,String memberPhone,String memberId){
		final String finalOrderNo = orderNo; 
		final String finalId = id; 
		final String finalMemberPhone = memberPhone;
		final String finalMemberId = memberId;
		Thread thread = new Thread(new Runnable() {
			public void run() {
				//send msg by mxt
				//MxtSmsUtil.sendMsg(finalMemberPhone,sendMsgStr,0, new Date());
				
				
				//需求变更  用户支付成功不需要给用户发送支付成功通知  add by  hyl 2016/07/18
				/*try
				{
					ShortMsgFactory.getInstance().getShortMsg().send(ShortMsgEnum.send_order_info, finalMemberPhone, finalOrderNo);
				}catch(Exception e)
				{
					logger.error("send message error",e);
				}*/
				//app推送
/*				String title = "";    //标题
				title = (String) redisUtil.get("APPSETTINGOS_PAY_SUCCESS_TITLE");
				if(!StringUtil.isNotNull(title)){
					try{
						ResultDto dictResultDto = dictApiService.getDictByParams("APPSETTING", "OS_PAY_SUCCESS_TITLE");
						if(!ResultDto.OK_CODE.equals(dictResultDto.getCode())){
							title = "";
						}
						title =  (String) dictResultDto.getData();
					}catch(Exception e){
						logger.error(" app获取数据字典",e);
						title = "";
					}
				}*/
				String text = "";    //内容
				text = (String) redisUtil.get("APPSETTINGOS_PAY_SUCCESS_CONTENT");
				if(!StringUtil.isNotNull(text)){
					try{
						ResultDto dictResultDto = dictApiService.getDictByParams("APPSETTING", "OS_PAY_SUCCESS_CONTENT");
						if(!ResultDto.OK_CODE.equals(dictResultDto.getCode())){
							text = "";
						}
						text =  (String) dictResultDto.getData();
						redisUtil.set("APPSETTINGOS_PAY_SUCCESS_CONTENT", text,new Long(60));//缓存60秒
					}catch(Exception e){
						logger.error(" app获取数据字典",e);
						text = "";
					}
				}
				//封装数据
				JSONObject res=new JSONObject();
				JSONObject value=new JSONObject();
				JSONObject jsonObj=new JSONObject();
				jsonObj.put("id", finalId);
				value.put("type", "ORDER_PAY_SUCCESS");
				value.put("jsonObj", jsonObj);
				res.put("value", value);
				res.put("title", "");
				res.put("id", "1");
				res.put("content", text);//内容
				res.put("url", "");
				String jsonStr = res.toString();
				OmsOrder order = getOmsOrderByOrderNo(finalOrderNo);
				String orderSource = order.getOrderSource();
				//推送
				if("1".equals(orderSource)){
					//android
					JPushUtils.send_all_alias_message(finalMemberPhone, "", jsonStr);
				}else if("2".equals(orderSource)){
					//IOS
					JPushUtils.send_ios_alias_notification(finalMemberPhone, text, jsonStr);
				}
				//save msg to db
				String sendMsgStr = ShortMsgEnum.send_order_info.getRealContent(finalOrderNo);
				MemberMessage memberMessage = new MemberMessage();
				memberMessage.setMemberId(finalMemberId);
				memberMessage.setType(MessTypeEnum.ORDERNOTICE);
				memberMessage.setTitle("订单通知");
				memberMessage.setStatus("0");
				memberMessage.setContent(sendMsgStr);
				memberMessage.setPhone(finalMemberPhone);
				memberMessageApiService.addMess(memberMessage);
			}
		});
		thread.start();
	}
	@Override
	public int orderRefundSuccess(String orderNo, Date time_succeed) throws ServiceException {
		// TODO Auto-generated method stub
		Map<String, Object> params  = new HashMap<String, Object>();
		params.put("orderNo",orderNo);
		OmsOrder order =  this.omsOrderMapper.selectByKey(params);
		if(null==order){
			logger.info("本系统【"+orderNo+"】查无此单");
			return 0;
		}
		//暂且不知支付成功后的业务只更改状态
		OmsOrder toUpdateOrder = new OmsOrder();
		toUpdateOrder.setId(order.getId());
		toUpdateOrder.setStatus("3");//交易关闭
		toUpdateOrder.setChargeBackTime(time_succeed);//支付成功的时间
		toUpdateOrder.setLastUpdateDate(new Date());
		return this.modifyById(toUpdateOrder);
	}
	@Override
	public OmsOrder getOrderByKey(Map<String, Object> params) throws Exception {
		// TODO Auto-generated method stub
		return this.omsOrderMapper.selectByKey(params);
	}
	@Override
	@Transactional(rollbackFor=Exception.class)
	public void orderSplitSingle(OmsOrder order,List<OmsOrder> splitOrderList) throws ServiceException, CloneNotSupportedException {
		// TODO Auto-generated method stub
		List<OmsOrderdetail> omsOrderdetailList  =  order.getDetailList();
		//初始化订单关联的 商品信息 ,sku信息
		this.initOrderDetail(omsOrderdetailList);
		//当订单明细大于1条时做拆单处理
		if(omsOrderdetailList.size()>1){
			//第一步  根据商 拆单标示拆单、区域性商品、供应商仓库
			this.orderSplitSingleBySplitSingleFlag(order,omsOrderdetailList,splitOrderList);
			//第二步  将余下明细按照   区域性商品 
			this.buildSplistNoSplitSingleFlag(order,omsOrderdetailList,splitOrderList);
//			OmsOrder targetOrder = new OmsOrder();
//			targetOrder.setOrderNo(order.getOrderNo());;
//			this.deleteOrder(targetOrder);
		}
//		else{
//			splitOrderList.add(order);
//		}
		logger.info(order.getOrderNo());
	}
	private  void initOrderDetail(List<OmsOrderdetail> omsOrderdetailList){
		for (OmsOrderdetail omsOrderdetail : omsOrderdetailList) {
		    Commodity  commodity = commodityService.getCommodityByCodeFromRedis(omsOrderdetail.getCommodityNo());
			CommoditySku commoditySku =commoditySkuService.getCommoditySkuByIdFromRedis(omsOrderdetail.getSkuId());
		    omsOrderdetail.setCommodity(commodity);
		    omsOrderdetail.setCommoditySku(commoditySku);
		}
	}
	/** 
	 * *
	 * 
	 * @param omsOrder
	 * @author yinglong.huang
	 * @email yinglong.huang@ffzxnet.com
	 * @date 2016年11月23日 上午10:36:30
	 * @version V1.0
	 * @return
	 * @throws Exception 
	 */
	@Override
	@Transactional(rollbackFor=Exception.class)
	public void virtualItemsHandle(OmsOrder omsOrder) throws ServiceException{
		ResultDto rto = null;
		List<OmsOrderdetail> detailList = omsOrder.getDetailList();
		if (detailList != null && detailList.size() > 0) {
			List<OrderParam> orderParamList = null;
			try {
			orderParamList = initOrderParamInfo(detailList);
			logger.info("【"+omsOrder.getOrderNo()+"】虚拟商品直接出库=============orderProcessManagerService.inbound("+JsonMapper.toJsonString(orderParamList)+","+omsOrder.getRegionId()+","+omsOrder.getOrderNo()+",\"待收货(已出库交接)\")");
			rto = orderProcessManagerApiService.inbound(orderParamList,omsOrder.getRegionId(),omsOrder.getOrderNo());
			if (!rto.getCode().equals(ResultDto.OK_CODE)) {
				logger.error("【"+omsOrder.getOrderNo()+"】虚拟商品直接出库==》》库存扣减失败 ："+rto.getMessage());
				throw new ServiceException(rto.getMessage());
			}
			} catch (Exception e) {
				logger.error("虚拟商品直接出库==》》【"+omsOrder.getOrderNo()+"】出库库存扣减异常", e);
				throw new ServiceException(e);
			}
		} else {
			logger.error("虚拟商品直接出库【"+omsOrder.getOrderNo()+"】订单明细为空");
			throw new ServiceException("虚拟商品直接出库【"+omsOrder.getOrderNo()+"】订单明细为空");
		}
		logger.info("【"+omsOrder.getOrderNo()+"】虚拟商品直接出库成功");
		//发送成功后将订单修改为已出货
		OmsOrder order = new OmsOrder();
		omsOrder.setId(omsOrder.getId());
		omsOrder.setCurLoisticsStatus("7");
		//0：配送APP确认收货
		omsOrder.setOutOrderStatus("0");
		
		omsOrderMapper.updateOrderStatus(omsOrder);
		//插入订单操作记录
		OmsOrderRecord record = new OmsOrderRecord();
		record.setId(UUIDGenerator.getUUID());
		record.setOrderNo(omsOrder.getOrderNo());
		record.setOrderId(omsOrder.getId());
		record.setDescription("您的购买的商品已发送至您的账户中");
		record.setOutboundStatus("7");
		Date now = new  Date();
		record.setCreateDate(now);
		record.setRecordType("1");
		// 新增订单操作日志
		omsOrderRecordService.add(record); 
		this.pushVG2prms(omsOrder);
	}
	//虚拟商品支付成功推pr
	private void pushVG2prms(OmsOrder  omsOrder){
	    rabbitTemplate.convertAndSend("ORDER_PAYMENT_VIRTUAL_ITEMS", omsOrder);
	    logger.info("【"+omsOrder.getOrderNo()+"】订单推送到prms成功 ========================order>>"+JsonMapper.toJsonString(omsOrder));
	}
	/**
	 * 除开 拆单标示以外  拆单集合
	 * 1，筛选出预售订单，==》》同一发货日期分类，在此基础上，再把同一供应商分类，再区域性与非区域性划分
	 * 2.非预售订单，==》》供应商分类，再区域性与非区域性划分
	 * @throws CloneNotSupportedException 
	 * **/
  private void buildSplistNoSplitSingleFlag(OmsOrder order,List<OmsOrderdetail> omsOrderdetailList,List<OmsOrder> splitOrderList) throws CloneNotSupportedException{
	  
	  logger.info("OmsOrderServiceImpl==>>buildSplistNoSplitSingleFlag==>>无拆单标示  明细分组【"+order.getOrderNo()+"】:"+JsonMapper.toJsonString(omsOrderdetailList));
	  //预售拆分 
	  Map<String,Object> sameReadySendTimeMap = this.biuldSameReadySendTime(omsOrderdetailList);
	  logger.info("OmsOrderServiceImpl==>>buildSplistNoSplitSingleFlag==>>预售拆单  根据【发货日期】分组 【"+order.getOrderNo()+"】sameReadySendTimeMap:"+JsonMapper.toJsonString(sameReadySendTimeMap));
	  // ============================预售继续拆分Start==========================
	  if(!sameReadySendTimeMap.isEmpty()){
	  for (String key : sameReadySendTimeMap.keySet()){
		  //供应商拆分
		  List<OmsOrderdetail> mapItemList =  (List<OmsOrderdetail>) sameReadySendTimeMap.get(key);
		  logger.info("OmsOrderServiceImpl==>>buildSplistNoSplitSingleFlag==>>预售拆单  【"+order.getOrderNo()+"】发货日期【"+key+"】mapItemList_【"+mapItemList.size()+"】条:"+JsonMapper.toJsonString(mapItemList));
		  //如果集合数量只有一个 则不用继续拆分 构建新订单
		  if(mapItemList.size()==1){
			  this.biuldOrderBySpilit(order,mapItemList ,splitOrderList);
		  }else{
			  //供应商和区域性 再次拆分
			  logger.info("OmsOrderServiceImpl==>>buildSplistNoSplitSingleFlag==>>预售拆单  根据【"+key+"】分组==>>  供应商和区域性 再次拆分【"+order.getOrderNo()+"】");
			 // this.sameVender_warehouse_split(order,mapItemList);
			  this.warehouse_sameVender_split(order,mapItemList,splitOrderList);
		   } 
		 }
	  }
	  // ============================预售继续拆分End==========================
	  //=============================非预售部分Start==========================
	  //供应商和区域性 再次拆分
	  if(omsOrderdetailList.size()>0){
		  //this.sameVender_warehouse_split(order,omsOrderdetailList);
		  this.warehouse_sameVender_split(order,omsOrderdetailList,splitOrderList);
	  }
	//=============================非预售部分End==========================
	  
  }
  
  
  
  //供应商和区域性 再次拆分
  private void sameVender_warehouse_split(OmsOrder order,List<OmsOrderdetail> omsOrderdetailList,List<OmsOrder> splitOrderList) throws CloneNotSupportedException{
	//========================有供应商商品 Start===================================
	  Map<String,Object> sameVenderMap  = this.getSameVendor(omsOrderdetailList);
	  logger.info("OmsOrderServiceImpl==>>sameVender_warehouse_split==>> 根据【供应商】分组==>> 【"+order.getOrderNo()+"】sameVenderMap:"+JsonMapper.toJsonString(sameVenderMap));
	  for (String sameVender_key : sameVenderMap.keySet()) {
		  //供应商拆分
		  List<OmsOrderdetail> ssameVender_mapItemList =  (List<OmsOrderdetail>) sameVenderMap.get(sameVender_key);
		  logger.info("OmsOrderServiceImpl==>>sameVender_warehouse_split==>> 根据【供应商】分组==>> 供应商和区域性 再次拆分【"+order.getOrderNo()+"】同一供应商【"+sameVender_key+"】sameVenderMap:"+JsonMapper.toJsonString(sameVenderMap));
		  if(ssameVender_mapItemList.size()==1){
			  this.biuldOrderBySpilit(order,ssameVender_mapItemList,splitOrderList);
		  }else{
			  
			  //======================区域性 非需区域性 拆分==================
			  //商品在供应商仓库
			  List<OmsOrderdetail> warehouse_omsOrderdetailList =  getOmsOrderdetailList_warehouse(ssameVender_mapItemList);
			  logger.info("OmsOrderServiceImpl==>>sameVender_warehouse_split==>> 根据【商品在供应商】分组==>>商品在供应商再次拆分【"+order.getOrderNo()+"】【仓库在供应商】分组warehouse_omsOrderdetailList_【"+warehouse_omsOrderdetailList.size()+"】条:"+JsonMapper.toJsonString(warehouse_omsOrderdetailList));
			  if(warehouse_omsOrderdetailList.size()>0){
				  if(warehouse_omsOrderdetailList.size()==1){
				  this.biuldOrderBySpilit(order, warehouse_omsOrderdetailList,splitOrderList);
				  }else{
					  this.areaSplitBuilOrder(order,warehouse_omsOrderdetailList,splitOrderList);
				  } 
			  }
			  //商品不在供应商仓库
			  List<OmsOrderdetail> no_warehouse_omsOrderdetailList =ssameVender_mapItemList;
			  logger.info("OmsOrderServiceImpl==>>sameVender_warehouse_split==>> 根据【商品不在供应商】分组==>>商品不在供应商品再次拆分【"+order.getOrderNo()+"】【仓库在wms】分组no_warehouse_omsOrderdetailList_【"+no_warehouse_omsOrderdetailList.size()+"】条:"+JsonMapper.toJsonString(no_warehouse_omsOrderdetailList));
			  if(no_warehouse_omsOrderdetailList.size()>0){
				  if(no_warehouse_omsOrderdetailList.size()==1){
				  this.biuldOrderBySpilit(order,no_warehouse_omsOrderdetailList,splitOrderList); 
				  }else{
				  this.areaSplitBuilOrder(order,no_warehouse_omsOrderdetailList,splitOrderList);
				  }
			  }
			  
		  }
		}
	//========================同一区域商品 End===================================
	  
	//========================无供应商商品Start===================================
	  if(omsOrderdetailList.size()>0){
		  this.areaSplitBuilOrder(order,omsOrderdetailList,splitOrderList);
	  }
	//========================无供应商商品 End=====================================  
	  
	  
  }
  
  //先拆分出 仓库在供应商的数据 再拆 add  2016-07-12
  private void warehouse_sameVender_split(OmsOrder order,List<OmsOrderdetail> omsOrderdetailList,List<OmsOrder> splitOrderList) throws CloneNotSupportedException{
		//========================有供应商商品 Start===================================
	//商品在供应商仓库Start
	  List<OmsOrderdetail> warehouse_omsOrderdetailList =  getOmsOrderdetailList_warehouse(omsOrderdetailList);
	  logger.info("warehouse_sameVender_split==>>【"+order.getOrderNo()+"】,【商品在供应商仓库】的商品共【"+warehouse_omsOrderdetailList.size()+"】条,【商品[不]在供应商仓库】的商品共【"+omsOrderdetailList.size()+"】条");
	  logger.info("OmsOrderServiceImpl==>>sameVender_warehouse_split==>> 根据【商品在供应商】分组==>>商品在供应商再次拆分【"+order.getOrderNo()+"】【仓库在供应商】分组warehouse_omsOrderdetailList_【"+warehouse_omsOrderdetailList.size()+"】条:"+JsonMapper.toJsonString(warehouse_omsOrderdetailList));
	  if(warehouse_omsOrderdetailList.size()>0){
		  if(warehouse_omsOrderdetailList.size()==1){
		  this.biuldOrderBySpilit(order, warehouse_omsOrderdetailList,splitOrderList);
		  }else{
			  Map<String,Object> sameVenderMap  = this.getSameVendor(warehouse_omsOrderdetailList);
			  logger.info("OmsOrderServiceImpl==>>sameVender_warehouse_split==>> 根据【供应商】分组==>> 【"+order.getOrderNo()+"】sameVenderMap:"+JsonMapper.toJsonString(sameVenderMap));
			  for (String sameVender_key : sameVenderMap.keySet()) {
				  //同一个供应商 拆分
				  List<OmsOrderdetail> ssameVender_mapItemList =  (List<OmsOrderdetail>) sameVenderMap.get(sameVender_key);
				  logger.info("OmsOrderServiceImpl==>>sameVender_warehouse_split==>> 根据【供应商】分组==>> 供应商和区域性 再次拆分【"+order.getOrderNo()+"】同一供应商【"+sameVender_key+"】sameVenderMap:"+JsonMapper.toJsonString(sameVenderMap));
				  if(ssameVender_mapItemList.size()==1){
					  this.biuldOrderBySpilit(order,ssameVender_mapItemList,splitOrderList);
				  }else{
					  //======================区域性 非需区域性 拆分==================
					  this.areaSplitBuilOrder(order,ssameVender_mapItemList,splitOrderList);
					  
				  }
				}
			  if(warehouse_omsOrderdetailList.size()>0){
				  if(warehouse_omsOrderdetailList.size()==1){
					  this.biuldOrderBySpilit(order,warehouse_omsOrderdetailList,splitOrderList);
				  }else{
					  this.areaSplitBuilOrder(order,warehouse_omsOrderdetailList, splitOrderList);
				  }
				  
			  }
			  
		  } 
	  }
	//商品在供应商仓库End
	//商品不在供应商仓库Start
	  // List<OmsOrderdetail> no_warehouse_omsOrderdetailList =omsOrderdetailList;
	  logger.info("OmsOrderServiceImpl==>>sameVender_warehouse_split==>> 根据【商品不在供应商】分组==>>商品不在供应商品再次拆分【"+order.getOrderNo()+"】【仓库在wms】分组no_warehouse_omsOrderdetailList_【"+omsOrderdetailList.size()+"】条:"+JsonMapper.toJsonString(omsOrderdetailList));
	  if(omsOrderdetailList.size()>0){
		  if(omsOrderdetailList.size()==1){
		  this.biuldOrderBySpilit(order,omsOrderdetailList,splitOrderList); 
		  }else{
		  this.areaSplitBuilOrder(order,omsOrderdetailList,splitOrderList);
		  }
	  }  
	//商品不在供应商仓库End
	  }

  
  //区域商品标示拆分
  private void areaSplitBuilOrder(OmsOrder order,List<OmsOrderdetail> omsOrderdetailList,List<OmsOrder> splitOrderList) throws CloneNotSupportedException{
	//区域性商品
	  List<OmsOrderdetail> area_omsOrderdetailList =  getOmsOrderdetailList_area(omsOrderdetailList);
	  logger.info("OmsOrderServiceImpl==>>areaSplitBuilOrder==>> 根据【区域性商品】分组==>>区域性商品再次拆分【"+order.getOrderNo()+"】【区域商品】分组area_omsOrderdetailList_【"+area_omsOrderdetailList.size()+"】条:"+JsonMapper.toJsonString(area_omsOrderdetailList));
	  if(area_omsOrderdetailList.size()>0){
		  this.biuldOrderBySpilit(order, area_omsOrderdetailList,splitOrderList); 
	  }
	  //非区域性商品
	  List<OmsOrderdetail> no_area_omsOrderdetailList =omsOrderdetailList;
	  logger.info("OmsOrderServiceImpl==>>areaSplitBuilOrder==>> 根据【非区域商品】分组==>>区域性商品再次拆分【"+order.getOrderNo()+"】【非区域商品】分组no_area_omsOrderdetailList_【"+no_area_omsOrderdetailList.size()+"】条:"+JsonMapper.toJsonString(no_area_omsOrderdetailList));
	  if(no_area_omsOrderdetailList.size()>0){
		  this.biuldOrderBySpilit(order,no_area_omsOrderdetailList,splitOrderList); 
	  }
  }
 
	//商品是否在供应商仓库
   private List<OmsOrderdetail> getOmsOrderdetailList_warehouse(List<OmsOrderdetail> omsOrderdetailList ){
	   List<Integer> indexRecord = new ArrayList<>();
	   List<OmsOrderdetail> omsOrderdetailList_warehouse =  new ArrayList<>();
	   for (int i=0;i<omsOrderdetailList.size();i++) {
		   OmsOrderdetail omsOrderdetail =  omsOrderdetailList.get(i);
		   if(omsOrderdetail.getCommodity().getWarehouse().equals("0")){
			   logger.info("OmsOrderServiceImpl==>>getOmsOrderdetailList_warehouse==>> 根据【区域性商品】分组==>>发现区域商品【"+omsOrderdetail.getSkuCode()+"】将其分组,omsOrderdetail:"+JsonMapper.toJsonString(omsOrderdetail));
			   omsOrderdetailList_warehouse.add(omsOrderdetail);
//			   omsOrderdetailList.remove(i);
			   indexRecord.add(i);
		   }
	   }
	   removeOmsorderdetail(indexRecord, omsOrderdetailList);
	   return omsOrderdetailList_warehouse;
   }
   
   //拆分  预售同发布日期订单
   private  Map<String,Object> biuldSameReadySendTime(List<OmsOrderdetail> omsOrderdetailList){
	   List<Integer> indexRecord = new ArrayList<>();
	   Map<String,Object> sameReadySendTimeMap = new HashMap<String,Object>();
	   for (int i=0;i<omsOrderdetailList.size();i++) {
		   OmsOrderdetail orderdetail =  omsOrderdetailList.get(i);
		if(orderdetail.getBuyType().equals(BuyTypeEnum.PRE_SALE)){
			String  readySendTimeStr =  DateUtil.format(orderdetail.getReadySendTime(), DateUtil.FORMAT_MONTH_DATE);
			 List<OmsOrderdetail> sameReadySendTimeList = null;
        	 if(sameReadySendTimeMap.containsKey(readySendTimeStr)){
        		 sameReadySendTimeList = (List<OmsOrderdetail>) sameReadySendTimeMap.get(readySendTimeStr);
        		 sameReadySendTimeList.add(orderdetail);
        		 sameReadySendTimeMap.put(readySendTimeStr, sameReadySendTimeList);
        	 }else{
        		 sameReadySendTimeList = new ArrayList<OmsOrderdetail>();
        		 sameReadySendTimeList.add(orderdetail);
        		 sameReadySendTimeMap.put(readySendTimeStr, sameReadySendTimeList);
        		 
        	 }
//        	 omsOrderdetailList.remove(i);
        	 indexRecord.add(i);
		 }
	   }
	   removeOmsorderdetail(indexRecord, omsOrderdetailList);
	   return sameReadySendTimeMap;
   }
   
   //拆分同供应商
   private Map<String,Object> getSameVendor(List<OmsOrderdetail> omsOrderdetailList){
	   	   List<Integer> indexRecord = new ArrayList<>();
	       Map<String,Object> hadVendorMap = new HashMap<String,Object>();
	       for (int i=0;i<omsOrderdetailList.size();i++) {
			   OmsOrderdetail orderdetail =  omsOrderdetailList.get(i);
	        	 List<OmsOrderdetail> hadsameVendorList = null;
	        	 if(StringUtil.isNotNull(orderdetail.getCommodity().getVendorId())){//有供应商的商品
	        	 String venderId = orderdetail.getCommodity().getVendorId();
	        	 if(hadVendorMap.containsKey(venderId)){
	        		 hadsameVendorList = (List<OmsOrderdetail>) hadVendorMap.get(venderId);
	        		 hadsameVendorList.add(orderdetail);
	        	 }else{
	        		 hadsameVendorList = new ArrayList<>();
	        		 hadsameVendorList.add(orderdetail);
	        		 hadVendorMap.put(venderId, hadsameVendorList);
	        	 }
//	        	 omsOrderdetailList.remove(i);
	        	 indexRecord.add(i);
	        	 }
			}
	       removeOmsorderdetail(indexRecord, omsOrderdetailList);
	       return   hadVendorMap;
   }
   //筛选区域性商品
	private  List<OmsOrderdetail> getOmsOrderdetailList_area(List<OmsOrderdetail> omsOrderdetailList ){
		List<Integer> indexRecord = new ArrayList<>();
		List<OmsOrderdetail> omsOrderdetailList_area =  new ArrayList<>();
		for (int i=0;i<omsOrderdetailList.size();i++) {
			   OmsOrderdetail omsOrderdetail =  omsOrderdetailList.get(i);
			//区域性商品
		   if(omsOrderdetail.getCommodity().getAreaCategory().equals("0")){
			   omsOrderdetailList_area.add(omsOrderdetail);//添加到拆单集合中
//			   omsOrderdetailList.remove(i);//将该明细在源原集合中删除
			   indexRecord.add(i);
		   }
		}
		removeOmsorderdetail(indexRecord, omsOrderdetailList);
		return omsOrderdetailList_area;
	}
	//拆单标示拆单   一个明细拆成一个单
	private  void orderSplitSingleBySplitSingleFlag(OmsOrder order,List<OmsOrderdetail> omsOrderdetailList,List<OmsOrder> splitOrderList) throws CloneNotSupportedException{
		
		
		List<Integer> indexRecord = new ArrayList<>(); //为了记录要移除的ID！！！
		for (int i=0;i<omsOrderdetailList.size();i++) {
			   OmsOrderdetail omsOrderdetail =  omsOrderdetailList.get(i);
			//拆单标示拆单
		   if(StringUtil.isNotNull(omsOrderdetail.getCommodity().getSplitSingle())&&omsOrderdetail.getCommodity().getSplitSingle().equals("0")){
			   logger.info("OmsOrderServiceImpl==>>orderSplitSingleBySplitSingleFlag==>>根据拆单标示拆单【"+order.getOrderNo()+"】:"+JsonMapper.toJsonString(omsOrderdetail));
			   List<OmsOrderdetail> omsOrderdetailList_splitSingleFlag = new  ArrayList<>();
			   omsOrderdetailList_splitSingleFlag.add(omsOrderdetail);//添加到拆单集合中
			   this.biuldOrderBySpilit(order, omsOrderdetailList_splitSingleFlag,splitOrderList);
//			   omsOrderdetailList.remove(i);//将该明细在源原集合中删除
			   indexRecord.add(i);
		   }
		}
		removeOmsorderdetail(indexRecord, omsOrderdetailList);
	}
	
	/*** add by ying.cai **/
	private void removeOmsorderdetail(List<Integer> indexRecord , List<OmsOrderdetail> detailList){
		if(indexRecord.size()==0){
			return;
		}
		for(int i = indexRecord.size()-1 ;i>=0 ; i--){
			detailList.remove(indexRecord.get(i).intValue());
		}
	}
	//加载订单明细商品关联
/*	private void  initOrderDetail(List<OmsOrderdetail> omsOrderdetailList ){
		Map<String, Object> skuParams = new HashMap<String, Object>();
		for (OmsOrderdetail omsOrderdetail : omsOrderdetailList) {
			skuParams.clear();
			skuParams.put("sku_code ", omsOrderdetail.getCommodityNo());
			CommoditySku commoditySku = (CommoditySku) commoditySkuService.findByBiz(skuParams).get(0);
			Commodity commodity = commodityService.getCommodityByCodeFromRedis(commoditySku.getCommodityCode());
			omsOrderdetail.setCommodity(commodity);
			omsOrderdetail.setCommoditySku(commoditySku);
		}
	}*/
	@Transactional(rollbackFor=Exception.class)
    private void biuldOrderBySpilit(OmsOrder order,List<OmsOrderdetail> omsOrderdetailList,List<OmsOrder> splitOrderList) throws CloneNotSupportedException{
    	BigDecimal totalPrice = new BigDecimal(0D);//订单总价
		BigDecimal actualPayAmount = new BigDecimal(0D);//实际支付金额
		BigDecimal favouredAmount =  new BigDecimal(0D);
		int buyCount = 0 ;//新订单购买商品数量
		OmsOrder newOrder =  (OmsOrder) order.clone();//克隆订单
		newOrder.setId(UUID.randomUUID().toString());
		newOrder.setOrderNo( order.getOrderNo()+"_"+(splitOrderList.size()+1) );//新订单号
		newOrder.setId(UUIDGenerator.getUUID());
		newOrder.setPayOrderNo(order.getOrderNo());//支付订单号保证一样
		newOrder.setStatus("1");//变更为待收货状态
		//订单初始化仓库信息Start
		String  warehouseCode = omsOrderdetailList.get(0).getWarehouseCode();
		String  warehouseId = omsOrderdetailList.get(0).getWarehouseId();
		String  warehouseName = omsOrderdetailList.get(0).getWarehouseName();
		//默认获取第一个订单明细的的供应商仓库所在标示
		String isSupplier = omsOrderdetailList.get(0).getCommodity().getWarehouse();
		newOrder.setIsSupplier(isSupplier);
		newOrder.setBuyType(omsOrderdetailList.get(0).getBuyType());
		newOrder.setStorageId(warehouseId);
		newOrder.setWarehouseCode(warehouseCode);
		newOrder.setStorageName(warehouseName);
		//订单初始化仓库信息End
		for(OmsOrderdetail orderDetail : omsOrderdetailList){
			
			if(StringUtil.isNotNull(orderDetail.getFavouredAmount())){
			favouredAmount = favouredAmount.add(orderDetail.getFavouredAmount());
			}
			int buyNum = orderDetail.getBuyNum();
			buyCount += buyNum;
			orderDetail.setOrderNo(newOrder.getOrderNo());
			orderDetail.setOrderId(newOrder.getId());
			totalPrice = totalPrice.add( orderDetail.getActSalePrice().multiply(new BigDecimal(buyNum)));
			actualPayAmount = actualPayAmount.add( orderDetail.getActPayAmount());
//			omsOrderdetailMapper.updateDetBySplit(orderDetail);//更新数据
		}
		newOrder.setBuyCount(buyCount);
		//支付价格
		newOrder.setActualPrice(actualPayAmount);
		newOrder.setTotalPrice(totalPrice);
		newOrder.setFavorablePrice(favouredAmount);
		newOrder.setCouponAmount(favouredAmount);
		newOrder.setDetailList(omsOrderdetailList);//设置订单明细数据，用来做拆分完毕之后明细数据的update操作
//		this.copyOrderOperateRecord(order.getOrderNo(), newOrder);
//		this.add(newOrder);
		splitOrderList.add(newOrder);
		logger.info("OmsOrderServiceImpl==>>biuldOrderBySpilit==>>新订单拆分结果【"+newOrder.getOrderNo()+"】:"+JsonMapper.toJsonString(newOrder));
    }
	@Transactional(rollbackFor=Exception.class)
	private void copyOrderOperateRecord(String oldOrderNo,OmsOrder newOrder){
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("orderNo", oldOrderNo);
		List<OmsOrderRecord> omsOrderRecordList = null;
		String key="copyOrderOperateRecord_"+oldOrderNo;
		omsOrderRecordList = (List<OmsOrderRecord>) redisUtil.get(key);
		if(!StringUtil.isNotNull(omsOrderRecordList)){
			omsOrderRecordList =  omsOrderRecordService.findByBiz(params);
		}else{
			redisUtil.set(key,omsOrderRecordList,OmsConstant.REDIS_EXPIRETIME_1MIN);
		}
		for (OmsOrderRecord omsOrderRecord : omsOrderRecordList) {
			OmsOrderRecord newRecord =  new  OmsOrderRecord();
			newRecord.setId(UUIDGenerator.getUUID());
			newRecord.setCreateDate(omsOrderRecord.getCreateDate());
			newRecord.setLastUpdateDate(omsOrderRecord.getLastUpdateDate());
			newRecord.setDescription(omsOrderRecord.getDescription());
			newRecord.setRecordType(omsOrderRecord.getRecordType());
			newRecord.setOrderNo(newOrder.getOrderNo());
			newRecord.setOrderId(newOrder.getId());
			omsOrderRecordService.add(newRecord);
		}
		
	}
	@Override
	public Double getTotalMoney(Map<String, Object> params) throws ServiceException {
		return omsOrderMapper.getTotalMoney(params);
	}
	@Override
	public void dealAllocationError(OmsOrder order) {
		// TODO Auto-generated method stub
		OmsOrder originOrder = this.findById(order.getId());
		if(!order.getSendPerson().equals(originOrder.getSendPerson())){//如果在订单分配错误的情况下并且配送人与之前配送人不一致
			//暂时无逻辑
			//1.重新让该订单参与调拨，按新分配合伙人发货
			//2.生成取货单，去上次分配合伙人取货、入库
		}
	}

	@Override
	public Map<String, Object> getStatusCount(Map<String, Object> params) throws ServiceException {
		// TODO Auto-generated method stub
		return this.omsOrderMapper.selectStatusCount(params);
	}
	
	@Override
	public Map<String, Object> getAfterSaleCount(Map<String, Object> params) throws ServiceException {
		// TODO Auto-generated method stub
		return this.omsOrderMapper.selectAfterSaleCount(params);
	}
	@Override
	public List<OmsOrder> findLatestOrderuser(Page page, String commodityId,String activityStartTime) {
		Map<String, Object> params  = new HashMap<String, Object>();
		params.put("commodityId", commodityId);
		params.put("activityStartTime",activityStartTime);
		return this.omsOrderMapper.findLastOrderUser(page, params);
	}
	
	@Override
	public List<OmsOrder> findLatestOrderByACId(Page page, String commodityId,String acitivityCommodityId) {
		Map<String, Object> params  = new HashMap<String, Object>();
		params.put("commodityId", commodityId);
		params.put("acitivityCommodityId",acitivityCommodityId);
		return this.omsOrderMapper.findLastOrderUser(page, params);
	}
	
	@Override
	@Transactional(rollbackFor=Exception.class)
	public void buildOrder(OmsOrder omsOrder) throws ServiceException {
		logger.info("==【" + omsOrder.getOrderNo() + "】====>>>订单入参信息"+JsonMapper.toJsonString(omsOrder));
		ResultDto stockManagerResultDto = null;
		ResultDto couponReceiveResultDto = null;
		//当判断该单为合伙人为分销站合伙人时其下单价格重新计算
		initPifaPrice4serviceStation(omsOrder);
		// 如果使用了优惠券 * 使用状态(0未使用，1使用).
		if (StringUtil.isNotNull(omsOrder.getCouponId())) {
			logger.info("======【" + omsOrder.getOrderNo() + "】====>>>使用优惠券：【" + omsOrder.getCouponId()
					+ "】<<<====更新优惠券状态begin====");
			try {
				couponReceiveResultDto = couponReceiveApiService.updateReceiveState(omsOrder.getCouponId(), "1",
						omsOrder.getMemberId());
			} catch (Exception e) {
				logger.error(
						"【" + omsOrder.getOrderNo()
								+ "】invoke OmsOrderServiceImpl===>>buildOrder====>> couponReceiveApiService.updateReceiveState===>>调用异常:couponReceiveApiService.updateReceiveState("
						+ omsOrder.getCouponId() + ",\"1\")优惠券更新异常",
						e);
				throw new ServiceException("您的优惠券暂时不能使用");
			}
			if (!couponReceiveResultDto.getCode().equals(ResultDto.OK_CODE)) {
				logger.error(
						"【" + omsOrder.getOrderNo()
								+ "】invoke OmsOrderServiceImpl===>>buildOrder====>> couponReceiveApiService.updateReceiveState===>>调用异常:couponReceiveApiService.updateReceiveState("
						+ omsOrder.getCouponId() + ",\"1\")优惠券更新异常"+couponReceiveResultDto.getMessage()
						);
				throw new ServiceException("您的优惠券暂时不能使用");
			}
			logger.info("======【" + omsOrder.getOrderNo() + "】====>>>使用优惠券：【" + omsOrder.getCouponId()
					+ "】<<<====更新优惠券状态end====");
		}

		logger.info("==========>>>订单持久化完毕，开始进行库存预占操作<<<========");
		List<OrderParam> orderParamList = null;
		// 库存预占操作
		try {
			orderParamList = initOrderParamInfo(omsOrder.getDetailList());
			stockManagerResultDto = orderProcessManagerApiService.pendingPayment(orderParamList, omsOrder.getRegionId(),
					omsOrder.getOrderNo());
		} catch (Exception e) {
			logger.error(
					"invoke OmsOrderServiceImpl===>>buildOrder====>> orderProcessManagerService.pendingPayment===>>【" + omsOrder.getOrderNo()
					+ "】===========orderProcessManagerService.pendingPayment(" + JsonMapper.toJsonString(orderParamList)
					+ "," + omsOrder.getRegionId() + "," + omsOrder.getOrderNo() + ",待付款\")库存更新异常",
					e);
			//throw new ServiceException("抱歉，您购买的商品暂时无法下单");
			throw new ServiceException(e.getMessage());
		}
		if (!stockManagerResultDto.getCode().equals(ResultDto.OK_CODE)) {
			logger.error("invoke orderApiServiceImpl.buildOrder === >>>\r\n params:" + omsOrder.getOrderNo()
					+ "=== >>>库存预占失败");
			throw new ServiceException(stockManagerResultDto.getMessage());
		}
		if (null == stockManagerResultDto.getData()) {
			logger.error("invoke orderApiServiceImpl.buildOrder === >>>\r\n params:" + omsOrder.getOrderNo()
					+ "=== >>>库存预占返回信息为空");
			throw new ServiceException("抱歉，您购买的商品暂时无法下单");
		}

		logger.info("【" + omsOrder.getOrderNo() + "】==========>>>库存预占操作完毕<<<========");
		Map<String, Object> pendingMap = (Map<String, Object>) stockManagerResultDto.getData();
		if (pendingMap.isEmpty()) {
			logger.error("invoke orderApiServiceImpl.buildOrder === >>>【" + omsOrder.getOrderNo()
					+ "】=== >>>库存预占操作返回订单明细仓库分配信息返回为空");
			throw new ServiceException("抱歉，您购买的商品暂时无法下单");
		}
		logger.info("==【" + omsOrder.getOrderNo() + "】====>>>预占操作返回订单明细仓库分配信息：" + JsonMapper.toJsonString(pendingMap));
		logger.info("==【" + omsOrder.getOrderNo() + "】====>>>准备保存订单<<<======");
		List<OmsOrderdetail> omsOrderdetailList = null;
		try {
			omsOrderdetailList = splitOrderDetailList(omsOrder, omsOrder.getDetailList(), pendingMap);
		} catch (Exception e) {
			logger.error("invoke orderApiServiceImpl.buildOrder === >>>【" + omsOrder.getOrderNo() + "】=== >>>明细拆分异常",e);
			throw new ServiceException("抱歉，您购买的商品暂时无法下单");
		}
		// 保存订单明细数据
		logger.info("=====【" + omsOrder.getOrderNo() + "】=====>>>开始持久化订单明细数据<<<========");
		logger.info("==【" + omsOrder.getOrderNo() + "】====>>>订单明细持久化以前：" + JsonMapper.toJsonString(omsOrderdetailList));
		for (OmsOrderdetail omsOrderdetail : omsOrderdetailList) {
			logger.info("=====【" + omsOrder.getOrderNo() + "】明细【"+omsOrderdetail.getSkuCode()+"】批发价格:"+omsOrderdetail.getPifaPrice());
			omsOrderdetailService.add(omsOrderdetail);
		}
		logger.info("=====【" + omsOrder.getOrderNo() + "】=====>>>开始持久化订单实体<<<========");
		//重新分配订单仓库信息
		omsOrder.setStorageId(omsOrderdetailList.get(0).getWarehouseId());
		omsOrder.setStorageName(omsOrderdetailList.get(0).getWarehouseName());
		omsOrder.setWarehouseCode(omsOrderdetailList.get(0).getWarehouseCode());
		this.add(omsOrder);
		//将明细塞入订单中并发布下单成功广播
		omsOrder.setDetailList(omsOrderdetailList);
		this.sendOrderBuildRadio(omsOrder);
		//判断是否使用红包
		if(StringUtil.isNotNull(omsOrder.getRedPacketUseIds())){
			 logger.info("下单支付成功【"+omsOrder.getOrderNo()+"】,使用的红包使用ids："+omsOrder.getRedPacketUseIds());
				
			this.oprRedPacket(omsOrder, "0");
		}
		logger.info("【" + omsOrder.getOrderNo() + "】==========>>>相关操作完毕<<<========");
	}
	private void initPifaPrice4serviceStation(OmsOrder omsOrder){
		List<OmsOrderdetail>omsOrderdetailList =  omsOrder.getDetailList();
		for (OmsOrderdetail omsOrderdetail : omsOrderdetailList) {
			CommoditySku sku = commoditySkuService.getCommoditySkuByIdFromRedis(omsOrderdetail.getSkuId());
			if(null!=sku.getTradePriceSku()){
			omsOrderdetail.setPifaPrice(sku.getTradePriceSku());
			BigDecimal balance = (omsOrderdetail.getActSalePrice().subtract(omsOrderdetail.getPifaPrice())).multiply(new BigDecimal(omsOrderdetail.getBuyNum()));
			omsOrderdetail.setBalance(balance);
			}
			}
		//当判断该单为合伙人为分销站合伙人时其下单价格取批发价格重新计算	
		BigDecimal totalActualPrice = new BigDecimal(0D);
		PartnerServiceStation partnerServiceStation = this.getServiceStationInfoByMeberPhone(omsOrder.getMemberPhone());
		if(null!=partnerServiceStation){
			logger.info("【"+omsOrder.getOrderNo()+"】该单为分销站合伙人下单,实际支付价调整为批发价X数量");
			for (OmsOrderdetail omsOrderdetail : omsOrderdetailList) {
				//去掉优惠金额
				omsOrderdetail.setFavouredAmount(null);
				//去掉红包金额
				omsOrderdetail.setRedPacketAmount(null);
				omsOrderdetail.setActSalePrice(omsOrderdetail.getPifaPrice());
				//重新计算实付金额
				BigDecimal detailActPayAmount = omsOrderdetail.getActSalePrice().multiply(new BigDecimal(omsOrderdetail.getBuyNum()));
				//累加总实付金额
				totalActualPrice = totalActualPrice.add(detailActPayAmount);
				omsOrderdetail.setActPayAmount(detailActPayAmount);
			}
			//设置总实付金额
			logger.info("【"+omsOrder.getOrderNo()+"】该单为分销站合伙人下单,实际支付价调整为批发价X数量");
			omsOrder.setTotalPrice(totalActualPrice);//总金额
			omsOrder.setActualPrice(totalActualPrice);//总的支付金额
			logger.info("【"+omsOrder.getOrderNo()+"】该单为分销站合伙人下单,明细结果"+JsonMapper.toJsonString(omsOrder));
		}
				
		
	}
	//订单下单成功 发送广播
	private void sendOrderBuildRadio(OmsOrder omsOrder){
		if(null!=omsOrder){
			try{
				rabbitTemplate.convertAndSend("OMS_BUILD_CHANGE_EXCHANGE", "", omsOrder);
				}catch (Exception e) {
					logger.error("【"+omsOrder.getOrderNo()+"】下单成功交易完成发送广播失败",e);
				}
			}
		}
	
	/**
	 * 红包操作
	 * 
	 * @param omsOrder,
	 * @param type 类型（0 下单成功 1 支付成功2取消）
	 * @author yinglong.huang
	 * @email yinglong.huang@ffzxnet.com
	 * @date 2016年11月22日 上午10:57:55
	 * @version V1.0
	 * @return
	 */
	@Override
	public void oprRedPacket(OmsOrder omsOrder,String type){
		logger.info("红包操作记录【"+omsOrder.getOrderNo()+"】,type:"+type);
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("redpackageUseIds", omsOrder.getRedPacketUseIds());
		params.put("type", type);
		params.put("orderPrice", omsOrder.getActualPrice());
		ResultDto redPackageResultDto = null;
		try{
			redPackageResultDto = redPackageApiService.updateReceiveStatus(params);
		}catch(Exception e){
			logger.error(
					"【" + omsOrder.getOrderNo()
							+ "】红包使用参数："+params.toString()+"，异常信息：",e);
			throw new ServiceException("抱歉，您的红包不能使用了哟");
		}
		if (!redPackageResultDto.getCode().equals(ResultDto.OK_CODE)) {
			logger.error(
					"【" + omsOrder.getOrderNo()
							+ "】红包使用参数："+params.toString()+"，异常信息："+redPackageResultDto.getMessage()
					);
			throw new ServiceException("抱歉，您的红包不能使用了哟");
		}
	}
	private List<OmsOrderdetail> splitOrderDetailList(OmsOrder omsOrder, List<OmsOrderdetail> omsOrderdetailList,
			Map<String, Object> pendingMap) throws CloneNotSupportedException {
		List<OmsOrderdetail> splitOrderDetailList = new ArrayList<OmsOrderdetail>();

		for (OmsOrderdetail omsOrderdetail : omsOrderdetailList) {
			if(StringUtil.isNotNull(omsOrderdetail.getVirtualGdId())){
				   omsOrderdetail.setCommodityType("1");;
			}
			String skuCode = omsOrderdetail.getSkuCode();
			Map<String, String> skuMap = (Map<String, String>) pendingMap.get(skuCode);
			logger.info("==【" + omsOrder.getOrderNo() + "】skuCode【"+skuCode+"】预占操作返回订单明细仓库分配信息：" + JsonMapper.toJsonString(skuMap));
			if(skuMap.containsKey("giftType")&&skuMap.get("giftType").toString().equals("2")){
				splitOrderDetailList.add(omsOrderdetail);
				continue;
			}
			Iterator<String> it = skuMap.keySet().iterator();
			if (skuMap.keySet().size() > 1) {// 判断订单明细是否拆分
				// 实际售价.
				BigDecimal actSalePrice = omsOrderdetail.getActSalePrice();
				// 优惠金额.
				BigDecimal favouredAmount_before = new BigDecimal(0D);
				BigDecimal totalRedAmount_before = new BigDecimal(0D); 
				if(null!=omsOrderdetail.getFavouredAmount()){
					favouredAmount_before = omsOrderdetail.getFavouredAmount();
				}
				if(null!=omsOrderdetail.getRedPacketAmount()){
					totalRedAmount_before = omsOrderdetail.getRedPacketAmount();
				}
				logger.info("【" + omsOrder.getOrderNo() + "】skuCode"+skuCode+"预占操作返回订单明细仓库分配信息：拆分前优惠金额【favouredAmount_before】:"+favouredAmount_before);
				// 实际支付金额.
				BigDecimal actPayAmount_before = new BigDecimal(0D);
				if(null!=omsOrderdetail.getActPayAmount()){
					actPayAmount_before = omsOrderdetail.getActPayAmount();
				}
				logger.info("【" + omsOrder.getOrderNo() + "】skuCode"+skuCode+"预占操作返回订单明细仓库分配信息：拆分前支付金额【actPayAmount_before】:"+actPayAmount_before);
				// 购买数量.
				int buyNum_before = omsOrderdetail.getBuyNum();
				while (it.hasNext()) {
					OmsOrderdetail omsOrderdetail_clone = (OmsOrderdetail) omsOrderdetail.clone();// 克隆订单明细
					omsOrderdetail_clone.setId(UUIDGenerator.getUUID());
					String warehouseCode = it.next().toString();
					String buyNum_split = skuMap.get(warehouseCode);
					logger.info("【" + omsOrder.getOrderNo() + "】skuCode"+skuCode+"预占操作返回订单明细仓库分配信息：warehouseCode" + warehouseCode+";拆分购买量【buyNum_split】:"+buyNum_split);
					if (it.hasNext()) {
						//计算占用比例
						BigDecimal rate = new BigDecimal(buyNum_split).divide(new BigDecimal(Double.parseDouble(String.valueOf(buyNum_before))),20,BigDecimal.ROUND_HALF_DOWN);
						logger.info("【" + omsOrder.getOrderNo() + "】skuCode"+skuCode+"预占操作返回订单明细仓库分配信息：warehouseCode" + warehouseCode+";拆分占用比例:"+rate.doubleValue());
						//计算优惠占比金额
						BigDecimal favouredAmount_split = favouredAmount_before.multiply(rate).setScale(2,
								BigDecimal.ROUND_HALF_DOWN );// 保留两位小数向下取整
						favouredAmount_before = favouredAmount_before.subtract(favouredAmount_split);
					
						//计算红包占比金额
						BigDecimal redAmount_split = totalRedAmount_before.multiply(rate).setScale(2,
								BigDecimal.ROUND_HALF_DOWN );// 保留两位小数向下取整
						totalRedAmount_before = totalRedAmount_before.subtract(redAmount_split);
						
						//实际支付金额
						/*BigDecimal actPayAmount_split = actPayAmount_before.multiply(rate).setScale(2,
								BigDecimal.ROUND_HALF_DOWN );// 保留两位小数向下取整*/
						
						//实际支付金额=实际售价*购买数量-优惠金额-红包金额
						BigDecimal actPayAmount_split = actSalePrice.multiply(new BigDecimal(String.valueOf(buyNum_split))).subtract(favouredAmount_split).subtract(redAmount_split);
						actPayAmount_before = actPayAmount_before.subtract(actPayAmount_split);
						omsOrderdetail_clone.setFavouredAmount(favouredAmount_split);
						omsOrderdetail_clone.setBuyNum(Integer.parseInt(buyNum_split));
						omsOrderdetail_clone.setActPayAmount(actPayAmount_split);
						logger.info("【" + omsOrder.getOrderNo() + "】【拆分中】skuCode"+skuCode+"预占操作返回订单明细仓库分配信息：warehouseCode" + warehouseCode+";拆分购买量【buyNum_split】:"+buyNum_split+",拆分对象,"+JsonMapper.toJsonString(omsOrderdetail_clone));
					} else {
						//若是最后一个 将余下优惠金额、红包金额设置给最后一个
						omsOrderdetail_clone.setFavouredAmount(favouredAmount_before);
						omsOrderdetail_clone.setBuyNum(Integer.parseInt(buyNum_split));
						omsOrderdetail_clone.setRedPacketAmount(totalRedAmount_before);
						omsOrderdetail_clone.setActPayAmount(actPayAmount_before);
						logger.info("【" + omsOrder.getOrderNo() + "】【最后一个分配仓库】skuCode"+skuCode+"预占操作返回订单明细仓库分配信息：warehouseCode" + warehouseCode+";拆分购买量【buyNum_split】:"+buyNum_split+",拆分对象,"+JsonMapper.toJsonString(omsOrderdetail_clone));
					}
					initOdWarehouseInfo(omsOrder, omsOrderdetail_clone, warehouseCode);
					splitOrderDetailList.add(omsOrderdetail_clone);
				}
			} else {// 判断订单明细未拆分
				String warehouseCode = it.next().toString();
				initOdWarehouseInfo(omsOrder, omsOrderdetail, warehouseCode);
				logger.info("【" + omsOrder.getOrderNo() + "】【单条明细】：【"+skuCode+"】，明细分配后的仓库编码：warehouseCode：【" +omsOrderdetail.getWarehouseCode()+"】");
				splitOrderDetailList.add(omsOrderdetail);
			}

		}
		logger.info("【" + omsOrder.getOrderNo() + "】订单仓库分配后的订单明细信息：" + JsonMapper.toJsonString(splitOrderDetailList));
		return splitOrderDetailList;
	}
	private  void initOdWarehouseInfo(OmsOrder omsOrder,OmsOrderdetail omsOrderdetail,String warehouseCode){
		com.ffzx.basedata.api.dto.Warehouse centerWarehouse = omsOrder.getCenterWarehouse();
		com.ffzx.basedata.api.dto.Warehouse areaWarehouse = omsOrder.getAreaWarehouse();
		if(null!=centerWarehouse&&centerWarehouse.getCode().equals(warehouseCode)){
			omsOrderdetail.setWarehouseId(centerWarehouse.getId());
			omsOrderdetail.setWarehouseCode(centerWarehouse.getCode());
			omsOrderdetail.setWarehouseName(centerWarehouse.getName());
			logger.info("【" + omsOrder.getOrderNo() + "】【成单——仓库分配】，skuCode【"+omsOrderdetail.getSkuCode()+"】，warehouseCode【" + warehouseCode+"】；分配到匹配县仓");
		}else if(null!=areaWarehouse&&areaWarehouse.getCode().equals(warehouseCode)){
			omsOrderdetail.setWarehouseId(areaWarehouse.getId());
			omsOrderdetail.setWarehouseCode(areaWarehouse.getCode());
			omsOrderdetail.setWarehouseName(areaWarehouse.getName());
			logger.info("【" + omsOrder.getOrderNo() + "】【成单——仓库分配】，skuCode【"+omsOrderdetail.getSkuCode()+"】，warehouseCode【" + warehouseCode+"】；分配到匹配中央仓");
		}else{
			omsOrderdetail.setWarehouseId(null);
			omsOrderdetail.setWarehouseCode(warehouseCode);
			omsOrderdetail.setWarehouseName(null);
			logger.info("【" + omsOrder.getOrderNo() + "】【成单——仓库分配】，skuCode【"+omsOrderdetail.getSkuCode()+"】，warehouseCode【" + warehouseCode+"】；分配到匹配未知仓");
		}
		logger.info("【" + omsOrder.getOrderNo() + "】【成单——仓库分配】，skuCode【"+omsOrderdetail.getSkuCode()+"】，warehouseCode【" + warehouseCode+"】；分配后的明细信息："+JsonMapper.toJsonString(omsOrderdetail));
		
	}

	/**
	 * 初始化库存操作传递参数
	 * @param orderdetailList
	 * @return
	 * @author yinglong.huang
	 * @email yinglong.huang@ffzxnet.com
	 * @date 2016年9月6日 上午11:18:50
	 * @version V1.0
	 * @return
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	@Override
	public List<OrderParam> initOrderParamInfo(List<OmsOrderdetail> orderdetailList) throws IllegalAccessException, InvocationTargetException {
		if (null != orderdetailList && orderdetailList.size() > 0) {
			List<OrderParam> orderParamList = new ArrayList<OrderParam>();
			for (OmsOrderdetail omsOrderdetail : orderdetailList) {
				OrderParam op = new OrderParam();
				//op.setCommodity((com.ffzx.bms.api.dto.Commodity) JSONObject.parse(JSONObject.toJSONString(omsOrderdetail.getCommodity())));
				if(null!=omsOrderdetail.getCommodity()){
					com.ffzx.bms.api.dto.Commodity bms_com = new com.ffzx.bms.api.dto.Commodity();
					BeanUtils.copyProperties(bms_com,omsOrderdetail.getCommodity());
					op.setCommodity(bms_com);
					}
				op.setNumber(new Long(omsOrderdetail.getBuyNum()));
				op.setSkuCode(omsOrderdetail.getSkuCode());
				Warehouse warehouse = new Warehouse();
				warehouse.setId(omsOrderdetail.getWarehouseId());
				warehouse.setCode(omsOrderdetail.getWarehouseCode());
				warehouse.setName(omsOrderdetail.getWarehouseName());
				op.setWarehouseObject(warehouse);
				// 买赠标示信息新增
				if (null != omsOrderdetail.getBuyGifts()&&!omsOrderdetail.getBuyGifts().equals("")) {
					op.setGiftType("1");
					if (null != omsOrderdetail.getWarehouseAppoint()) {
						op.setWarehouseType(omsOrderdetail.getWarehouseAppoint());
					}
				}
				//设置 虚拟商品标示
				if(StringUtil.isNotNull(omsOrderdetail.getVirtualGdId())){
					op.setGiftType("2");//标示为虚拟商品
				}
				orderParamList.add(op);
			}
			return orderParamList;
		} else {
			return null;
		}
	}
	@Override
	@Transactional(rollbackFor=Exception.class)
	public void confirmOrder(String uid, String oid) {
		logger.info("确认送达oid:【"+oid+"】,uid:【"+uid+"】");
		//验证该订单是否属于该合伙人
		Map<String,Object> params = new HashMap<>();
		params.put("partnerId", uid);
		params.put("id", oid);
		params.put("outOrderStatus", 0);
		int validRes = omsOrderMapper.validOrderByParams(params);
		if(validRes <= 0){
			throw new ServiceException("该订单与该合伙人信息匹配错误");
		}
		//确认送达
		OmsOrder order = new OmsOrder();
		order.setId(oid);
		order.setStatus("4");
		order.setTranTime(new Date());
		order.setOutOrderStatus("1");
		omsOrderMapper.updateOrderStatus(order);
		//插入订单日志
		this.insertOmsOrderRecordByConfirmOrder(oid);
		//查看订单类型 并更改相关信息
		this.updateOldOrderAfterSale(oid);
		//交易完成的订单 发送广播
		this.sendGiveOrderCoupon(oid);
	}

	// 买赠活动交易完成的订单发放优惠券
	private void sendGiveOrderCoupon(String orderId) {
		OmsOrder omsOrder = this.findOrderInfoById(orderId);
		ResultDto resDto = null;
		if (null != omsOrder.getDetailList() && omsOrder.getDetailList().size() > 0) {
			for (OmsOrderdetail omsOrderdetail : omsOrder.getDetailList()) {
				if (null!=omsOrderdetail.getBuyGifts()&&omsOrderdetail.getBuyGifts().equals("1")) {
					if (null != omsOrderdetail.getGiftCommodityItemId()) {
						GiveOrderCoupon giveOrderCoupon = new GiveOrderCoupon();
						giveOrderCoupon.setOrderId(orderId);
						giveOrderCoupon.setOrderNo(omsOrder.getOrderNo());
						giveOrderCoupon.setGiveId(omsOrderdetail.getGiftCommodityItemId());
						giveOrderCoupon.setBuyNum(omsOrderdetail.getBuyNum());
						try {
							resDto = activityGiveApiService.saveGiveOrderCoupon(giveOrderCoupon);
							if (null != resDto) {
								if (!resDto.getCode().equals(ResultDto.OK_CODE)) {
									logger.error("【" + omsOrder.getOrderNo() + "】==买赠活动交易完成的订单发放优惠券==》==异常"
											+ resDto.getMessage() + ";activityGiveApiService.saveGiveOrderCoupon("
											+ JsonMapper.toJsonString(giveOrderCoupon) + ")");
								} else {
									logger.info("【" + omsOrder.getOrderNo()
											+ "】==买赠活动交易完成的订单发放优惠券==》成功，;activityGiveApiService.saveGiveOrderCoupon("
											+ JsonMapper.toJsonString(giveOrderCoupon) + ")");
								}
							}
						} catch (Exception e) {
							logger.error("【" + omsOrder.getOrderNo()
									+ "】==买赠活动交易完成的订单发放优惠券==》异常;activityGiveApiService.saveGiveOrderCoupon("
									+ JsonMapper.toJsonString(giveOrderCoupon) + ")", e);
						}

						break;
					}
					// end
				}
			}
		}
	}
	
	//查看该订单是不是换货单,若是换货单 将该换货订单的原订单的售后状态改成 换货成功
	private  void  updateOldOrderAfterSale(String oid){
		OmsOrder  omsOrder = this.findOrderInfoById(oid);
		if(omsOrder.getOrderType().equals(OrderTypeEnum.EXCHANGE_ORDER)){
			List<OmsOrderdetail> omsOrderdetailList = omsOrder.getDetailList();
			for (OmsOrderdetail omsOrderdetail : omsOrderdetailList) {
				try {
					this.updateOrderDetailStatus(omsOrder.getOrderNo(), omsOrderdetail.getSkuId());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					throw new ServiceException(e);
				}
			}
		}
	}
	private  void  insertOmsOrderRecordByConfirmOrder(String oid){
		OmsOrder omsOrder = this.findById(oid);
		String msg="您已完成签收，感谢使用非凡大麦场，期待再次为您服务。";
		/*if(omsOrder.getBuyType().equals(BuyTypeEnum.VIRTUAL_ITEMS)){
			msg="已为您发货，感谢使用非凡大麦场，期待再次为您服务。";
		}*/
		logger.info("确认送达订单号【"+omsOrder.getOrderNo()+"】,msg:"+msg);
		this.saveOmsOrderRecord(omsOrder,new Date(),omsOrder.getSendPerson(),omsOrder.getSendPersonName() == null ? null : omsOrder.getSendPersonName(), msg, "1");
	}
	@Override
	public OmsOrder getOmsOrderByOrderNo(String orderNo) throws ServiceException {
		
		return this.omsOrderMapper.selectByPrimayOrderNo(orderNo);
	}
	
	@Override
	public OmsOrder findOrderInfoToSendApp(String id) {
		OmsOrder order = omsOrderMapper.findOrderInfoToSendApp(id);
		if( null == order ){
			return null;
		}
		List<OmsOrderdetail> details = omsOrderdetailService.findDetailToSendApp(order.getOrderNo());
		//构建必须要的订单商品详细信息
		order.setDetailList(details);
		return order;
	}
	@Override
	public List<OmsOrderVo> queryDistributionPage(Page page, Map<String, Object> params) throws Exception {
		
		return this.omsOrderMapper.queryDistributionPage(page, params,true);
	}
	/**
	 * @author 雷
	 * @date 2016年6月15日
	 * 统计会员各订单状态的支付总金额
	 * (non-Javadoc)
	 * @see com.ffzx.order.service.OmsOrderService#getSumMemberPay(java.util.Map)
	 */
	@Override
	public OmsOrderVo getSumMemberPay(Map<String, Object> params) {
		return omsOrderMapper.queryMemberPay(params);
	}
	
	@Override
	public OmsOrderVo queryEachDistCount(Map<String, Object> params) throws Exception {
		
		return this.omsOrderMapper.queryEachDistCount(params);
	}
	@Override
	public List<OrderBiVo> findSaleOrderBi(String partnerId) {
		return omsOrderMapper.findSaleOrderBi(partnerId);
	}
	
	@Override
	@Transactional(rollbackFor=Exception.class)
	public void updateDistributionDate(String orderNos) {
		String inSql = buildInSql(orderNos);
		omsOrderMapper.updateDistributionDate(inSql);
		this.insertOmsOrderRecordByDistribution(orderNos);
	}
	
	/**构建sql中in的语句*/
	private static String buildInSql(String ids){
		String [] arr = ids.split(",");
		StringBuilder builder = new StringBuilder();
		builder.append("(");
		for (String id : arr) {
			builder.append("'").append(id).append("'").append(",");
		}
		if(builder.length()>0){
			builder.deleteCharAt(builder.length()-1);
		}
		builder.append(")");
		return builder.toString();
	}
	//确认收货 是插入订单操作记录 
	private void insertOmsOrderRecordByDistribution(String orderNos){
		String [] nos = orderNos.split(",");
		for (String orderNo : nos) {
			Map<String,Object> params = new HashMap<>();
			params.put("orderNo", orderNo);
			List<OmsOrder> omsOrderList = this.findByBiz(params);
			if(omsOrderList==null || omsOrderList.size()==0){
				return ;
			}
			OmsOrder omsOrder = omsOrderList.get(0);
			
			String servicePoint = "";
			if(omsOrder.getServicePoint()==null){
				servicePoint = "合伙人配送点";
			}else{
				servicePoint = omsOrder.getServicePoint();
			}
			String sendPersonName = omsOrder.getSendPersonPhone();
			if(omsOrder.getSendPersonName()!=null){
				sendPersonName = omsOrder.getSendPersonName();
			}
			String msg1="快件已到达【"+servicePoint+"】，合伙人【"+sendPersonName+"】("+omsOrder.getSendPersonPhone()+")开始配送";
			this.saveOmsOrderRecord(omsOrder,new Date(),omsOrder.getSendPerson(),omsOrder.getSendPersonName() == null ? null : omsOrder.getSendPersonName(), msg1, "1");
		}
	}
	 /*
     * 订单操作类型 0:订单操作 1：物流状态,2:订单分配错误异常
     */
	private void saveOmsOrderRecord(OmsOrder omsOrder,Date oprDate,String oprId,String oprName,String msg,String type)throws ServiceException{
		OmsOrderRecord omsOrderRecord = new OmsOrderRecord();
		Date now = oprDate;
		omsOrderRecord.setId(UUIDGenerator.getUUID());
		omsOrderRecord.setOrderId(omsOrder.getId());
		omsOrderRecord.setOrderNo(omsOrder.getOrderNo());
		omsOrderRecord.setOprId(oprId);
		omsOrderRecord.setOprName(oprName);
		omsOrderRecord.setCreateDate(now);
		omsOrderRecord.setLastUpdateDate(now);
		omsOrderRecord.setDescription(msg);
		omsOrderRecord.setRecordType(type);
		omsOrderRecordService.add(omsOrderRecord);
	}
	@Override
	public Map<String, Object> getAddressInfo(String addressId) throws Exception {
		// TODO Auto-generated method stub
		ResultDto resultDubboDto = addressApiService.getAddressParent(addressId);
		if (!StringUtil.isNotNull(resultDubboDto.getData())) {
			throw new Exception("无法从基础数据系统中获取数据，请检查数据是否正确或者出现脏数据");
		}
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap = (Map<String, Object>) resultDubboDto.getData();
		if (resultMap != null) {
			if(resultMap.containsKey("msg")){
				String msg =  resultMap.get("msg").toString();
				if(!msg.equals("0")){
					throw new Exception(resultMap.get("msg").toString());
				}
			}else {
				throw new Exception("无法查到收货地址所对应的合伙人,请检查所选地址是否配置了合伙人");
			}
		}
	   return resultMap;
	}
	@Override
	@Transactional(rollbackFor=Exception.class)
	public void updateOrderPartnerInfo(OmsOrder omsOrder) throws Exception {
		// TODO Auto-generated method stub
		logger.info("重新分配合伙人订单==》》"+omsOrder.getId());
		OmsOrder oldOmsOrder = this.findById(omsOrder.getId());
		logger.info("重新分配合伙人订单==》》"+oldOmsOrder.getOrderNo());
		OmsOrder toUpdateOrder =  new  OmsOrder();
		toUpdateOrder.setId(oldOmsOrder.getId());
		toUpdateOrder.setAllocationError(Constant.NO);//将分配错误标示修改为否
		//获取地址相关的  仓库信息  合伙人信息  
		Map<String, Object> addressInfoMap = this.getAddressInfo(oldOmsOrder.getRegionId());
			Partner partner = (Partner) addressInfoMap.get("partner");
			// 当配送人地址修改 则 将其相对应的合伙人信息以及配送人信息改掉
			if (partner != null && StringUtil.isNotNull(partner.getId())) {
			} else {
				throw new Exception("无法查到收货地址所对应的合伙人,请检查所选地址是否配置了合伙人");
			}
			if(oldOmsOrder.getPartnerId().equals(partner.getId())){
				logger.info("【"+oldOmsOrder.getOrderNo()+"】重新分配合伙人订单==》》重新分配的合伙人去原合伙人一致"+oldOmsOrder.getPartnerId());
				inteOrderPartnerInfo(toUpdateOrder,partner);
				//与上次分配一致 将订单分配错误修改为正常分配
				this.modifyById(toUpdateOrder);
			}else{
				logger.info("【"+oldOmsOrder.getOrderNo()+"】重新分配合伙人订单==》》重新分配的合伙人去原合伙人不一致，原合伙人："+oldOmsOrder.getPartnerId()+",新合伙人："+partner.getId());
				inteOrderPartnerInfo(toUpdateOrder,partner);
				String logisticsStatus = getLogisticsStatus(oldOmsOrder.getOrderNo());//若0代表 物流未分配
				logger.info("【"+oldOmsOrder.getOrderNo()+"】重新分配合伙人订单==》》logisticsStatus"+logisticsStatus);
				//判断 物流状态 是否已处理 （是否已调拨）
				if(StringUtil.isNotNull(logisticsStatus)&&logisticsStatus.equals("0")){
					//未处理（未调拨） 重新推送订单信息到仓库 
					inteOrderPartnerInfo(toUpdateOrder,partner);
					this.modifyById(toUpdateOrder);
					//重新推送订单到wms
					logger.info("【"+oldOmsOrder.getOrderNo()+"】重新分配合伙人订单==》》重新推送数据到wms");
					this.rePushOrder2Wms(oldOmsOrder.getOrderNo());
				}else{
					//已经处理 （已调拨）  仅做合伙人信息修改
					this.modifyById(toUpdateOrder);
				}
			}
			
			
	}
	@Override
	@Transactional(rollbackFor=Exception.class)
	public void updateOrderAddrInfo(OmsOrder omsOrder) throws Exception {
		// TODO Auto-generated method stub
		this.initOrderInfoByAddressId(omsOrder);
		omsOrder.setAllocationError(Constant.NO);//将分配错误标示修改为否
		this.modifyById(omsOrder);
		
		
		OmsOrder queryOmsOrder = this.findOrderInfo(omsOrder.getOrderNo());
		String msg = "修改地址，"+omsOrder.getConsignName()+","+omsOrder.getConsignPhone()+","+omsOrder.getAddressInfo();
		this.saveOmsOrderRecord(queryOmsOrder, new Date(), RedisWebUtils.getLoginUser().getId(), RedisWebUtils.getLoginUser().getName()==null?RedisWebUtils.getLoginUser().getLoginName():RedisWebUtils.getLoginUser().getName(), msg, "0");
		//向wms 重新推送
		if(queryOmsOrder.getPayTime()!=null){//判断此单 是否支付 如果支付了则 重新向wms推订单数据
			rePushOrder2Wms(omsOrder.getOrderNo());
		}
	}
	//初始化订单收货信息
		private  void initOrderInfoByAddressId(OmsOrder omsOrder) throws Exception{
			    Map<String, Object> resultMap  = this.getAddressInfo(omsOrder.getRegionId());
			    if (resultMap != null) {
				Address address = (Address) resultMap.get("address");
				Partner partner = (Partner) resultMap.get("partner");
				//区域仓库 
				Warehouse aeraWarehouse = (Warehouse) resultMap.get("warehousecode");
				//中央仓库
				Warehouse centerWarehouse = (Warehouse) resultMap.get("warehouse");
				// 当配送人地址修改 则 将其相对应的合伙人信息以及配送人信息改掉
				if (partner != null && StringUtil.isNotNull(partner.getId())) {
					omsOrder.setPartnerId(partner.getId());// 合伙人ID
				} else {
					throw new Exception("无法查到所选地址所对应的合伙人,请检查所选地址是否配置了合伙人");
				}
				if(aeraWarehouse==null){
					throw new Exception("无法查到所选地址对应的区域级仓库,请检查所选地址是否配置了区域级仓库");
				}
				if(centerWarehouse==null){
					throw new Exception("无法查到所选地址对应的中央仓库,请检查所选地址是否配置了中央仓库");
				}
				if (StringUtil.isNotNull(partner.getPartnerCode())) {
					omsOrder.setPartnerCode(partner.getPartnerCode());// 合伙人编码
				}
				omsOrder.setSendPerson(partner.getId());// 配送员id即合伙人
				omsOrder.setSendPersonName(partner.getName());// 配送员姓名即合伙人
				omsOrder.setServicePoint(partner.getAddressName() + (partner.getAddressDeal()==null?"":partner.getAddressDeal()));// 配送信息是合伙人的具体地址
				//初始化仓库信息 根据订单明细的商品的区域性标示初始化其仓库信息
				OmsOrder queryOmsOrder =  this.findOrderInfoById(omsOrder.getId());
				List<OmsOrderdetail> omsOrderdetailList  =  queryOmsOrder.getDetailList();
				this.initOrderDetail(omsOrderdetailList);
				OmsOrderdetail omsOrderdetail = omsOrderdetailList.get(0);
					if(omsOrderdetail.getCommodity().getAreaCategory().equals(Constant.NO)){//区域性商品
						omsOrder.setStorageId(aeraWarehouse.getId());
						omsOrder.setStorageName(aeraWarehouse.getName());
						omsOrder.setWarehouseCode(aeraWarehouse.getCode());
					}else{
						omsOrder.setStorageId(centerWarehouse.getId());
						omsOrder.setStorageName(centerWarehouse.getName());
						omsOrder.setWarehouseCode(centerWarehouse.getCode());
					}
				//初始化所在县仓信息
				omsOrder.setCountyStoreId(aeraWarehouse.getId());
				omsOrder.setCountyStoreName(aeraWarehouse.getName());
				omsOrder.setCountyStoreCode(aeraWarehouse.getCode());	
			}else{
				throw new Exception("地址查询异常");	
			}
		}
	//重新推送订单到wms
	private  void  rePushOrder2Wms(String orderNo){
		OmsOrder  omsOrder = this.findOrderInfo(orderNo);
		if(StringUtil.isNotNull(omsOrder.getDetailList().get(0).getVirtualGdId())){
			logger.info("rePushOrder2Wms，【"+orderNo+"】,此单为虚拟商品不进行补偿");
		}else{
			logger.info("rePushOrder2Wms，【"+orderNo+"】=》》重新推送数据到wms，begin");
			OutboundSalesDeliveryMqApiService.outboundSalesDelivery(omsOrder);
			logger.info("rePushOrder2Wms，【"+orderNo+"】==》》重新推送数据到wms，end");
		}
	}
	private  void inteOrderPartnerInfo(OmsOrder toUpdateOrder,Partner partner){
		//初始化合伙人信息
		toUpdateOrder.setSendPerson(partner.getId());// 配送员id即合伙人
		toUpdateOrder.setSendPersonName(partner.getName());// 配送员姓名即合伙人
		toUpdateOrder.setServicePoint(partner.getAddressName() + (partner.getAddressDeal()==null?"":partner.getAddressDeal()));// 配送信息是合伙人的具体地址
		toUpdateOrder.setPartnerId(partner.getId());
		toUpdateOrder.setPartnerCode(partner.getPartnerCode());
	}
	@Override
	public String getLogisticsStatus(String omsOrderNo) throws Exception {
		// TODO Auto-generated method stub
		ResultDto resultDubboDto =  outboundSalesDeliveryApiService.getSalesDeliveryStatus(omsOrderNo);
		if(resultDubboDto.getCode().equals(ResultDto.BUSINESS_ERROR_CODE)){
			logger.error("从wms获取物流状态接口条用失败outboundSalesDeliveryApiService.getSalesDeliveryStatus("+omsOrderNo+")"
					,resultDubboDto);
			return null;
			//throw new Exception("从wms获取物流状态接口条用失败:"+resultDubboDto);
		}else if(resultDubboDto.getCode().equals(ResultDto.ERROR_CODE)){
			logger.error("从wms获取物流状态接口条用失败outboundSalesDeliveryApiService.getSalesDeliveryStatus("+omsOrderNo+")"
					,resultDubboDto);
			throw new Exception("从wms获取物流状态接口条用失败:"+resultDubboDto);
		}
		if(StringUtil.isNotNull(resultDubboDto.getData())){
			return  resultDubboDto.getData().toString();	
		}
		return null;
	}
	@Override
	public void insertOmsOrderRecord(OmsOrder omsOrder,String oprId,String oprName, String msg, String type) {
		// TODO Auto-generated method stub
		this.saveOmsOrderRecord(omsOrder,new Date(),oprId,oprName,msg,type);
	}
	
	@Override
	@Transactional(rollbackFor=Exception.class)
	public Map<String,Object> updateOrderDetailStatus(String exChangeorderNo, String skuId) throws Exception {
		// 操作结果
		Map<String,Object> result = new HashMap<String,Object>();
		// 查询条件
		Map<String,Object> params=new HashMap<String,Object>();
		params.put("exchangeOrderNo", exChangeorderNo);//换货订单编号
		// 查询售后管理换货订单关联的订单号
		List<AftersaleApply> aftersaleList = aftersaleApplyService.findByBiz(params);
		if (null != aftersaleList && aftersaleList.size() > 0) {
			// 修改售后申请单状态
			Map<String,Object> param=new HashMap<String,Object>();
			String aftersaleId = aftersaleList.get(0).getId();
			param.put("applyId", aftersaleId);
			List<AftersaleApplyItem> aftersaleItem = aftersaleApplyItemMapper.selectByParams(param);
			for (AftersaleApplyItem item : aftersaleItem) {
				// 修改对应skuId的售后申请详情商品状态
				if (skuId.equals(item.getSkuId())) {
					item.setAftersaleStatus(OrderDetailStatusConstant.STATUS_CHANGESUC);
					aftersaleApplyItemMapper.updateByPrimaryKeySelective(item);
				}
			}
			
			String orderNo = aftersaleList.get(0).getOrderNo();
			// 判断查询的订单号是否为空
			if (StringUtils.isNotEmpty(orderNo.trim())) {
				params.clear();
				params.put("orderNo", orderNo);//关联的订单编号
				params.put("skuId", skuId);//商品skuId
				params.put("orderDetailStatus", OrderDetailStatusEnum.HHCG.getValue());
				logger.info("通过查询的关联订单号("+orderNo+")和商品skuId("+skuId+")修改关联订单详情的状态");
				omsOrderdetailMapper.updateOrderDetailStatus(params);
				result.put("date", 1);
				result.put("code", Constant.SUCCESS);
				result.put("message", "修改成功");
			} else {
				logger.info("通过换货订单("+exChangeorderNo+")查询的关联订单编号值为空");
				result.put("code", Constant.ERROR_CODE);
				result.put("date", 0);
				result.put("message", "通过换货订单("+exChangeorderNo+")查询的关联订单编号值为空");
			}
		} else {
			logger.info("通过换货订单("+exChangeorderNo+")查不到的关联订单记录");
			result.put("code", Constant.ERROR_CODE);
			result.put("date", 0);
			result.put("message", "通过换货订单("+exChangeorderNo+")查不到的关联订单记录");
		}
		return result;
	}
	@Override
	@Transactional(rollbackFor=Exception.class)
	public OmsOrder getOrderByWebhooks(Map<String, Object> params) throws Exception {
		// TODO Auto-generated method stub
		return this.omsOrderMapper.selectByKey(params);
	}
	
	@Override
	@Transactional(rollbackFor=Exception.class)
	public List<OmsOrder> findOrderToBatchCancel() {
		Map<String,Object> params = new HashMap<>();
		List<OmsOrder> orderList = omsOrderMapper.selectToAutoCancelOrder(params);
		return orderList;
	}
	@Override
	public void pushOrder2Wms(String orderNo) {
		// TODO Auto-generated method stub
		rePushOrder2Wms(orderNo);
	}
	
	/**
	 * 雷-----2016年9月20日
	 * (非 Javadoc)
	 * <p>Title: replenishmentOrders</p>
	 * <p>Description:补货修改订单信息 </p>
	 * @param message
	 * @return
	 * @see com.ffzx.order.service.OmsOrderService#replenishmentOrders(com.ffzx.order.api.dto.ReplenishmentOrderVo)
	 */
	@Override
	@Transactional(rollbackFor=Exception.class)
	public int replenishmentOrders(ReplenishmentOrderVo message)throws ServiceException {
		Map<String,Object> params = new HashMap<>();
		params.put("receiptType", message.getReceiptType());
		params.put("orderNo", message.getOrderNo());
		params.put("receiptDate", message.getReceiptDate());
		return this.omsOrderMapper.replenishmentOrders(params);
	}
	
	
	@Override
	@Transactional(rollbackFor=Exception.class)
	public ImportBuildOrderVo importBuildOrderExcel(List<String[]> listRow) {
		StringBuilder msg=new StringBuilder();
		String errorCode = "0";
		List<GoodsVo> goodsVoList = new ArrayList<GoodsVo>();
		String memberId = null;
		String memberAddressId = null;
		Map<String,Object> resultMap = new HashMap<String,Object>();
		String[] headStrArray=listRow.get(0);
		String phone = (headStrArray[1] == null ? headStrArray[1] : headStrArray[1].trim());
		
		try{
		if(!StringUtil.isNotNull(phone)){
			msg.append(".导入失败,第 2行,【用户电话号码】不能为空, 请核对</br>");
		}
		Member member = this.getMemberByPhone(phone);
		memberId = member.getId();
		MemberAddress memberAddress = this.getMemberDefAddress(memberId);
		memberAddressId = memberAddress.getId();
		for(int i=2; i<listRow.size(); i++){
			String[] strArray=listRow.get(i);
			String itemName = (strArray[0] == null ? strArray[0] : strArray[0].trim());//商品名称
			String skuBarCode = (strArray[1] == null ? strArray[1] : strArray[1].trim());//条形码
			String buyNum = (strArray[2] == null ? strArray[2] : strArray[2].trim());//购买数量
			String salePrice = (strArray[3] == null ? strArray[3] : strArray[3].trim());//销售价格
			
			/*if(i==0){
			memberId = (strArray[4] == null ? strArray[4] : strArray[4].trim());//会员id
			memberAddressId = (strArray[5] == null ? strArray[5] : strArray[5].trim());//收货地址id
			if(!StringUtil.isNotNull(memberId)){
				msg.append(".导入失败,第"+ (i+1) + "行,【用户(memberId)】不能为空, 请核对</br>");
				continue;
			}
			if(!StringUtil.isNotNull(memberAddressId)){
				msg.append(".导入失败,第"+ (i+1) + "行,【收货地址(memberAddressId)】不能为空, 请核对</br>");
				continue;
			}
			}*/
			
			if(!StringUtil.isNotNull(itemName)){
				msg.append(".导入失败,第"+ (i+1) + "行,【名称】不能为空, 请核对</br>");
				continue;
			}
			if(!StringUtil.isNotNull(skuBarCode)){
				msg.append(".导入失败,第"+ (i+1) + "行,【条形码】不能为空, 请核对</br>");
				continue;
			}
			if(!StringUtil.isNotNull(buyNum)){
				msg.append(".导入失败,第"+ (i+1) + "行,【购买】不能为空, 请核对</br>");
				continue;
			}
			if(!StringUtil.isNotNull(salePrice)){
				msg.append(".导入失败,第"+ (i+1) + "行,【购买价格】不能为空, 请核对</br>");
				continue;
			}
			
			CommoditySku commoditySku = commoditySkuService.findByBarCode(skuBarCode);
			if(null==commoditySku){
				msg.append(".导入失败,第"+ (i+1) + "行,条形码【"+skuBarCode+"】无法获取商品信息, 请核对该商品条形码是否正确以及是否处于禁用状态</br>");
				continue;
			}
			GoodsVo goodsVo = new GoodsVo();
			goodsVo.setId(commoditySku.getId());
			NumberFormat nf00=NumberFormat.getNumberInstance();
			nf00.setMaximumFractionDigits(0);
			BigDecimal buyNum_big = new BigDecimal(buyNum);
			goodsVo.setCount(buyNum_big.intValue());
			goodsVo.setBuyType(BuyTypeEnum.COMMON_BUY);
			goodsVo.setSalePrice(new BigDecimal(salePrice));
			goodsVoList.add(goodsVo);
		}
		if(msg.length()>0){
			errorCode = "1";
		}
		}catch(ServiceException se){
			logger.error("导入成单异常", se);
			errorCode = "1";
			msg = msg.append(se.getMessage().toString());
		}
		ImportBuildOrderVo importBuildOrderVo = new ImportBuildOrderVo();
		importBuildOrderVo.setErrorCode(errorCode);
		importBuildOrderVo.setMsg(msg.toString());
		importBuildOrderVo.setMemberAddressId(memberAddressId);
		importBuildOrderVo.setMemberId(memberId);
		importBuildOrderVo.setGoodsVoList(goodsVoList);
		return importBuildOrderVo;
	}
	
	private Member getMemberByPhone(String phone){
		ResultDto result = null;
		try {
			//调用验证接口
			result = memberApiService.getMember(phone);
		} catch (Exception e) {
			logger.error("getMemberByPhone " , e);
			throw new ServiceException(1,"会员信息获取失败");
		}
		if(result.getCode().equals(ResultDto.OK_CODE) ){
			Member member = (Member) result.getData();
			if(member==null){
				logger.error("getMemberByPhone:member==null ");
			throw new ServiceException(1,"会员信息获取失败");
			}
			return  member;
		}else{
			logger.error("getMemberByPhone" + result.getMessage());
			throw new ServiceException(1,result.getMessage());
		}
	}
	
	private MemberAddress getMemberDefAddress(String uid){
		ResultDto result = null;
		try {
			//调用验证接口
			result = memberAddressApiService.findReceiptAddress(1, uid);
		} catch (Exception e) {
			logger.error("getMemberDefAddress" , e);
			throw new ServiceException(1,"会员收获地址获取失败");
		}
		if(result.getCode().equals(ResultDto.OK_CODE) ){
			List<MemberAddress> memberAddressList =  (List<MemberAddress>) result.getData();
		    if(null==memberAddressList||memberAddressList.size()==0){
		    	logger.error("findReceiptAddress:该会员无收获地址,memberAddressList为空");
		    	throw new ServiceException(1,"该会员无收获地址");
		    }
			return  memberAddressList.get(0);
		}else{
			logger.error("getMemberDefAddress" + result.getMessage());
			throw new ServiceException(1,result.getMessage());
		}
	}
	/***
	 * 
	 * @param orderNo
	 * @param handleResult：【PROCESSING】支付处理中，【FAIL】支付失败
	 * @throws ServiceException  code==-1 操作异常，code==0:业务异常，查无此单
	 * @author yinglong.huang
	 * @email yinglong.huang@ffzxnet.com
	 * @date 2016年11月4日 下午3:32:02
	 * @version V1.0
	 * @return 
	 */
	@Override
	@Transactional(rollbackFor=Exception.class)
	public void payHandle(String orderNo, String handleResult) throws ServiceException {
		// TODO Auto-generated method stub
		Map<String,Object> params =  new HashMap<String,Object>();
    	params.put("orderNo",orderNo);
    	OmsOrder order = null;
    	try {
			order =  this.getOrderByWebhooks(params);
		} catch (Exception e) {
			logger.error("【"+orderNo+"】支付处理查询订单异常：",e);
			throw new ServiceException(-1,e);
		}
    	if(null==order){
    		logger.error("【"+orderNo+"】支付处理查询异常：该系统查无此单");
			throw new ServiceException(0,"该系统查无此单");
    	}
    	
    	OmsOrder order2update = new OmsOrder();
    	order2update.setId(order.getId());
    	if(handleResult.equals("PROCESSING")){
    	//支付处理中
    	//当状态 处于待付款状态时，设置为支付处理中
    	if(order.getStatus().equals("0")){
    		order2update.setStatus("6");
			omsOrderMapper.updateOrderStatus(order2update);
			logger.error("【"+orderNo+"】payHandle：支付处理中，设置为支付处理中");
    	}	
    	}else if(handleResult.equals("FAIL")){
    	//支付失败
    	//当状态处于支付处理时，将订单状态设置为 0 ：代付款状态
    		if(order.getStatus().equals("6")){
    			order2update.setStatus("0");
    			omsOrderMapper.updateOrderStatus(order2update);
    			logger.error("【"+orderNo+"】payHandle：支付失败，设置为支付处理中");
    		}
    	}
	}
	/***
	 * 
	 * @param omsOrderNo
	 * @author yinglong.huang
	 * @email yinglong.huang@ffzxnet.com
	 * @date 2016年12月12日 下午4:04:50
	 * @version V1.0
	 * @return 
	 */
	@Override
	@Transactional(rollbackFor=Exception.class)
	public void otherHandel(String omsOrderNo) {
		Map<String,Object> params = new HashMap<>();
		params.put("orderNoLike", omsOrderNo);
		params.put("commodityType", "1");
		logger.info("otherHandel支付其他处理方式params:"+params.toString());
		List<OmsOrderdetail> omsOrderdetailList= omsOrderdetailMapper.selectByParams(params);
		if(null!=omsOrderdetailList&&omsOrderdetailList.size()>0){
		logger.info("otherHandel支付其他处理方式omsOrderdetailList:"+omsOrderdetailList.size()+"条"+JsonMapper.toJsonString(omsOrderdetailList));
		List<OmsOrder> omsOrderList = new ArrayList<>();
		for (OmsOrderdetail omsOrderdetail : omsOrderdetailList) {
			OmsOrder omsOrder= this.findOrderInfo(omsOrderdetail.getOrderNo());
			omsOrderList.add(omsOrder);
		}
		for (OmsOrder omsOrder : omsOrderList) {
			logger.info("otherHandel支付其他处理方式【"+omsOrder.getOrderNo()+"】:"+JsonMapper.toJsonString(omsOrder));
			this.virtualItemsHandle(omsOrder);
		}}
	}
	/***
	 * 
	 * @param modelType
	 * @return
	 * @throws ServiceException
	 * @author yinglong.huang
	 * @email yinglong.huang@ffzxnet.com
	 * @date 2017年3月26日 下午5:48:24
	 * @version V1.0
	 * @return 
	 */

	@Transactional
	public <T> int modifyByOrderNo(OmsOrder order) throws ServiceException {
		try {
			return omsOrderMapper.updateByPrimaryKeySelectiveByOrderNo(order);
		} catch (Exception e) {
			throw new ServiceException("",e);
		}
	}
	/***
	 * 
	 * @param params
	 * @return
	 * @author yinglong.huang
	 * @email yinglong.huang@ffzxnet.com
	 * @date 2017年3月26日 下午6:13:08
	 * @version V1.0
	 * @return 
	 */
	@Override
	public int updateSettlement(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return this.omsOrderMapper.updateSettlement(params);
	}
	/***
	 * 
	 * @param memberPhone
	 * @return
	 * @author yinglong.huang
	 * @email yinglong.huang@ffzxnet.com
	 * @date 2017年3月29日 上午11:31:48
	 * @version V1.0
	 * @return 
	 */
	@Override
	public Partner findPartnerByMemberPhone(String memberPhone) {
		try{
			Map<String,Object> params = new HashMap<String,Object>();
			params.put("memberAccount", memberPhone);
			ResultDto resultDto = partnerApiService.getPartnerList(params);
			if(null==resultDto){return null;}
			List<Partner> pList = (List<Partner>) resultDto.getData();
			return pList.isEmpty()?null:pList.get(0);
		}catch(Exception e){
			logger.error("",e);
			throw new ServiceException("网络超时");
		}
	}
	/***
	 * 
	 * @param omsOrder
	 * @author yinglong.huang
	 * @email yinglong.huang@ffzxnet.com
	 * @date 2017年3月29日 上午11:31:48
	 * @version V1.0
	 * @return 
	 */
	@Override
	public void initServiceStationInfo(OmsOrder omsOrder) {
		logger.info("【"+omsOrder.getOrderNo()+"】=====获取合伙人关联配送站=====");
		PartnerServiceStation partnerServiceStation = findPartnerServiceStationByPartnerId(omsOrder.getPartnerId());
		if(null!=partnerServiceStation){
			logger.info("【"+omsOrder.getOrderNo()+"】=====合伙人关联配送站====="+JsonMapper.toJsonString(partnerServiceStation));
			omsOrder.setServiceStationCode(partnerServiceStation.getCode());
			omsOrder.setServiceStationId(partnerServiceStation.getId());
			omsOrder.setServiceStationName(partnerServiceStation.getName());
		}
	}
	/***
	 * 
	 * @param partnerId
	 * @return
	 * @author yinglong.huang
	 * @email yinglong.huang@ffzxnet.com
	 * @date 2017年3月29日 上午11:31:48
	 * @version V1.0
	 * @return 
	 */
	@Override
	public PartnerServiceStation findPartnerServiceStationByPartnerId(String partnerId) {
		try{
			Map<String,Object> params = new HashMap<String,Object>();
			params.put("partnerId", partnerId);
			ResultDto resultDto = partnerApiServiceStationService.getPartnerApiServiceStationList(params);		
			List<PartnerServiceStation> pssList = (List<PartnerServiceStation>) resultDto.getData();
			logger.info("【"+partnerId+"】=====查询合伙人关联配送站为空=====");
			return pssList.isEmpty()?null:pssList.get(0);
		}catch(Exception e){
			logger.error("",e);
			throw new ServiceException("网络超时");
		}
	}
	/***
	 * 
	 * @param memberPhone
	 * @return
	 * @author yinglong.huang
	 * @email yinglong.huang@ffzxnet.com
	 * @date 2017年3月29日 上午11:42:25
	 * @version V1.0
	 * @return 
	 */
	@Override
	public PartnerServiceStation getServiceStationInfoByMeberPhone(String memberPhone) {
		Partner partner = findPartnerByMemberPhone(memberPhone);
		if(null!=partner){
		PartnerServiceStation partnerServiceStation = findPartnerServiceStationByPartnerId(partner.getId());
		if(null!=partnerServiceStation){
		return partnerServiceStation;
		}
		}
		return null;
	}
	/***
	 * 
	 * @param partnerId
	 * @return
	 * @author yinglong.huang
	 * @email yinglong.huang@ffzxnet.com
	 * @date 2017年3月29日 下午4:49:19
	 * @version V1.0
	 * @return 
	 */
	@Override
	public Partner getPartnerById(String partnerId) {
		try{
			ResultDto resultDto = partnerApiService.getPartnerId(partnerId);
			if(null==resultDto){return null;}
			Map param =  (Map) resultDto.getData();
			Partner data = (Partner) param.get("partner");
			return data;
		}catch(Exception e){
			logger.error("",e);
			return null;
		}
	}
	

}