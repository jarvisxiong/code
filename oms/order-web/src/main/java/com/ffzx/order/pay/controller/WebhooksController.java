package com.ffzx.order.pay.controller;

import java.io.BufferedReader;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ffzx.basedata.api.service.DictApiService;
import com.ffzx.commerce.framework.constant.RedisPrefix;
import com.ffzx.commerce.framework.controller.BaseController;
import com.ffzx.commerce.framework.dto.ResultDto;
import com.ffzx.commerce.framework.enums.ServiceResultCode;
import com.ffzx.commerce.framework.exception.ServiceException;
import com.ffzx.commerce.framework.utils.DateUtil;
import com.ffzx.commerce.framework.utils.StringUtil;
import com.ffzx.order.api.dto.OmsOrder;
import com.ffzx.order.api.enums.PayTypeEnum;
import com.ffzx.order.service.OmsOrderService;
import com.ffzx.order.utils.OmsConstant;
import com.pingplusplus.model.Event;
import com.pingplusplus.model.Webhooks;

import net.sf.json.JSONObject;

/**
 * 支付回调控制器
 * 
 * @className PayController.java
 * @author hyl
 * @date 2016年4月28日 
 * @version 1.0
 */
@Controller
@RequestMapping("webhooks/*")
public class WebhooksController extends BaseController {
	private final static Logger logger = LoggerFactory.getLogger(Webhooks.class);
	//@Resource
	//private RedisUtil redisUtil;
	@Autowired
	private OmsOrderService omsOrderService;
	@Autowired
	private RedisTemplate<String, String> redisTemplate;
	@Autowired
	private DictApiService dictApiService;
		//支付结果回调webhooks机制。。。
		@RequestMapping(value="pingxxNotify")
		public void pingxxNotify(HttpServletRequest request,HttpServletResponse response) throws Exception{
			try{
			logger.info("pingxx支付结果回调Start");
		      request.setCharacterEncoding("UTF8");
		        //获取头部所有信息
		        Enumeration headerNames = request.getHeaderNames();
		        while (headerNames.hasMoreElements()) {
		            String key = (String) headerNames.nextElement();
		            String value = request.getHeader(key);
		            System.out.println(key+" "+value);
		        }
		        // 获得 http body 内容
		        BufferedReader reader = request.getReader();
		        StringBuffer buffer = new StringBuffer();
		        String string;
		        while ((string = reader.readLine()) != null) {
		            buffer.append(string);
		        }
		        reader.close();
		        // 解析异步通知数据
		        logger.info("pingxx webhooks 回调解析<<<<<<<<"+buffer.toString()+">>>>>>>");
		        Event event = Webhooks.eventParse(buffer.toString());
		        if ("charge.succeeded".equals(event.getType())) {
		        	logger.info("pingxx支付成功结果回调");
		        	Object data = event.getData().get("object");
		        	JSONObject jsonObj = JSONObject.fromObject(data);
		        	//支付对象 id ，由 Ping++ 生成，27 位长度字符串。
		        	String charge_id  =  jsonObj.getString("id");
		        	//商户订单号，适配每个渠道对此参数的要求，必须在商户系统内唯一。(alipay: 1-64 位， wx: 1-32 位，bfb: 1-20 位，upacp: 8-40 位，yeepay_wap:1-50 位，jdpay_wap:1-30 位，cnp_u:8-20 位，cnp_f:8-20 位，推荐使用 8-20 位，要求数字或字母，不允许特殊字符)。
		        	String order_no = jsonObj.getString("order_no");
		        	String channel = jsonObj.getString("channel");
		        	//订单支付完成时间，用 Unix 时间戳表示。
		        	//String time_paid_str = jsonObj.getString("time_paid");
		        	Long time_paid = jsonObj.getLong("time_paid");
		        	Date time_paid_date = DateUtil.unixTimeStamp2Date(time_paid.toString());
		        	
		        	//to do something
		        	Map<String,Object> params =  new HashMap<String,Object>();
		        	params.put("orderNo",order_no);
		        	String lockKey  = "pingxx_webhooks_"+order_no;
		        	if(redisTemplate.opsForValue().setIfAbsent(lockKey, "1")){
		    		try {
		    			//设置时间，防止程序挂掉，导致其他线程无法获得锁
		    			redisTemplate.expire(lockKey, 5L, TimeUnit.MINUTES);
		        	OmsOrder order =  omsOrderService.getOrderByWebhooks(params);
		        	
		        	if(order==null){
		        		logger.info("本系统【"+order_no+"】查无此单");
		        		throw new Exception("本系统【"+order_no+"】查无此单");
		        	}
		        	if(StringUtil.isNotNull(order.getPayTime())){//若支付
		        		logger.info("【"+order_no+"】,此单已经支付回调处理");
		        		response.setStatus(200);	
		        	}else{
		        	PayTypeEnum payType =null;
		        	if(channel.equals("alipay")){
		        		payType = PayTypeEnum.ALIPAYAPP; 
		        	}else if(channel.equals("wx")){
		        		payType = PayTypeEnum.WXPAY; 
		        	}
		        	//支付结果扩展信息
		        	String payExtra = "";	
		        	if(jsonObj.containsKey("extra")){
		        		 payExtra = jsonObj.get("extra").toString();
		    		}
		        	omsOrderService.orderPaySuccess(order_no,charge_id,payExtra,time_paid_date,payType);//支付成功
		        	omsOrderService.otherHandel(order_no);
		        	response.setStatus(200);
		        	}
		    	
		    	}catch(Exception e){
		    				logger.error("订单支成功回调pingxx_webhooks_r error ===>>>",e);
		    				throw e;
		    	}finally{
		    	//释放锁
		    		redisTemplate.delete(lockKey);
		    		}
		         }		
		        } else if ("refund.succeeded".equals(event.getType())) {
		        	logger.info("pingxx退款成功结果回调");
		        	Object data = event.getData().get("object");
		        	JSONObject jsonObj = JSONObject.fromObject(data);
		        	//注意退款返回数据与支付成功返回数据不一样 ，退款成功返回数据重的jsonObj.getString("charge");为charge_id
		        	//支付订单charge_id
		        	String charge_id  =  jsonObj.getString("charge");
		        	//商户订单号，适配每个渠道对此参数的要求，必须在商户系统内唯一。(alipay: 1-64 位， wx: 1-32 位，bfb: 1-20 位，upacp: 8-40 位，yeepay_wap:1-50 位，jdpay_wap:1-30 位，cnp_u:8-20 位，cnp_f:8-20 位，推荐使用 8-20 位，要求数字或字母，不允许特殊字符)。
		        	String order_no = jsonObj.getString("order_no");
		        	//退款成功的时间，用 Unix 时间戳表示。
		        	//String time_succeed_str = jsonObj.getString("time_succeed");
		        	Long time_succeed = jsonObj.getLong("time_succeed");
		        	Date time_succeed_date = DateUtil.unixTimeStamp2Date(time_succeed.toString());
		        	
		        	//to do something
		        	int status = omsOrderService.orderRefundSuccess(order_no, time_succeed_date);//退款成功
		        	if(status==1){
		        		response.setStatus(200);
		        	}else{
		        		response.setStatus(500);
		        	}
		        } else {
		        	throw new Exception("pingxx webhooks 返回非支付对象或退款对象 ： "+event.getType());
		        }
		        logger.info("pingxx支付结果回调End");
			}catch(Exception e){
				response.setStatus(500);
				logger.error("pingxx webhooks 调用异常信息 ... ",e);
			}
			
		}
		//手动调度  支付成功或者退款
		@RequestMapping(value="mtNotify")	
		public void mtNotify(HttpServletRequest request,HttpServletResponse response) throws Exception{
			logger.info("mtNotify支付结果回调Start");
			String mtNotify_startFlag = System.getProperty("MTNOTIFYFLAG");
			mtNotify_startFlag="YES";
			try{
			if(mtNotify_startFlag==null||!mtNotify_startFlag.equals("YES")){
				throw new ServiceException("手动调用未开启");
			}
			String order_no =  getString("orderNo");
			String lockKey  = "pingxx_webhooks_"+order_no;
			if(redisTemplate.opsForValue().setIfAbsent(lockKey, "1")){
	    		try {
	    			//设置时间，防止程序挂掉，导致其他线程无法获得锁
	    			redisTemplate.expire(lockKey, 5L, TimeUnit.MINUTES);
			
	    			
		    if(!StringUtil.isNotNull(order_no)){
		    	throw new ServiceException("订单编号不能为空异常");
		    }
			String mtNotifyType = getString("mtNotifyType");
			if(!StringUtil.isNotNull(mtNotifyType)){
		    	throw new ServiceException("回调类型不能为空异常");
		    }
			Map<String,Object> params =  new HashMap<String,Object>();
        	params.put("orderNo",order_no);
        	OmsOrder order =  omsOrderService.getOrderByWebhooks(params);
        	if(mtNotifyType.equals("PAY")){
        	if(StringUtil.isNotNull(order.getPayTime())){//若支付
        		response.setStatus(200);	
        	}
        	omsOrderService.orderPaySuccess(order_no,null,null,new Date(),null);//支付成功
        	//其他处理
        	omsOrderService.otherHandel(order_no);
        	}else{
        		//to do something
        		omsOrderService.orderRefundSuccess(order_no, new Date());
        	}
				getOutputMsg().put("STATUS",ServiceResultCode.SUCCESS);
				getOutputMsg().put("MSG", "操作成功");
				
	    		}catch(Exception e){
					logger.error("订单支成功回调pingxx_webhooks_r error ===>>>",e);
					throw e;
	    		}finally{
	    			//释放锁
	    			redisTemplate.delete(lockKey);
	    		}
			}	
			} catch (ServiceException e) {
				logger.error("mtNotify-ServiceException", e);
				getOutputMsg().put("STATUS",ServiceResultCode.FAIL);
				getOutputMsg().put("MSG", e.getMessage());
				throw e;
			} catch (Exception e) {
				getOutputMsg().put("STATUS",ServiceResultCode.FAIL);
				logger.error("mtNotify-Exception", e);
				throw new ServiceException(e);
			}
			outPrint(response, this.getJsonByObject(this.getOutputMsg()));
			}
		private String  getDictByLabel(String label) throws ServiceException {
			logger.debug("OrderApiServiceImpl-getDictByType=》数据字典dubbo调用-列表 - BEGIN");

		    String value = null;
			//从缓存中获取
			String key  = RedisPrefix.DATA_DICT +label;
			//value = (String) redisUtil.get(key);
			if(null != value){
				return value;
			}
			
			ResultDto result = dictApiService.getDictByParams("OMSORDERCONFIG", label);;
			if(result.getCode().equals(ResultDto.OK_CODE) ){
				value =  (String) result.getData();
				//redisUtil.set(key, value,new Long(3600));
			}else{
				return null;
			}
			logger.debug("OrderApiServiceImpl-getDictByType=》数据字典dubbo调用-列表 " + result.getMessage());
			logger.debug("OrderApiServiceImpl-getDictByType=》数据字典dubbo调用-列表 - END");
			return value;
		}
		//手动调度  支付
		@RequestMapping(value="order2wms")
		private  void toWms(HttpServletRequest request,HttpServletResponse response){
			logger.info("mtNotify支付结果回调Start");
			String mtNotify_startFlag = System.getProperty("MTNOTIFYFLAG");
			mtNotify_startFlag="YES";
			try{
			if(mtNotify_startFlag==null||!mtNotify_startFlag.equals("YES")){
				throw new ServiceException("手动调用未开启");
			}
			String order_no =  getString("orderNo");
		} catch (ServiceException e) {
			logger.error("toWms-ServiceException", e);
			getOutputMsg().put("STATUS",ServiceResultCode.FAIL);
			getOutputMsg().put("MSG", e.getMessage());
		} catch (Exception e) {
			getOutputMsg().put("STATUS",ServiceResultCode.FAIL);
			getOutputMsg().put("MSG", e.getMessage());
			logger.error("mtNotify-Exception", e);
		}
		outPrint(response, this.getJsonByObject(this.getOutputMsg()));
	}
		public static void main(String[] args) {
		     String str = "{\"id\":\"evt_401170417102908393847403\",\"created\":1492396148,\"livemode\":true,\"type\":\"charge.succeeded\",\"data\":{\"object\":{\"id\":\"ch_a50S4GPKqPeDTC0C08ibj9uT\",\"object\":\"charge\",\"created\":1492396139,\"livemode\":true,\"paid\":true,\"refunded\":false,\"app\":\"app_CqPmnHjPO88CzvX1\",\"channel\":\"wx\",\"order_no\":\"492396133933488\",\"client_ip\":\"127.0.0.1\",\"amount\":1200,\"amount_settle\":1200,\"currency\":\"cny\",\"subject\":\"大麦场在线支付\",\"body\":\"大麦场竭诚为您服务\",\"extra\":{\"open_id\":\"o3dL5ssZA2JbNF7rX539EBBNaKKI\",\"bank_type\":\"CFT\"},\"time_paid\":1492396148,\"time_expire\":\"1492397933\",\"time_settle\":null,\"transaction_no\":\"4006222001201704177303096221\",\"refunds\":{\"object\":\"list\",\"url\":\"/v1/charges/ch_a50S4GPKqPeDTC0C08ibj9uT/refunds\",\"has_more\":false,\"data\":[]},\"amount_refunded\":0,\"failure_code\":null,\"failure_msg\":null,\"metadata\":{},\"credential\":{},\"description\":null}},\"object\":\"event\",\"request\":\"iar_frbvjDTujjrDD4ejfHm9qzbH\",\"pending_webhooks\":0}";
			 Event event = Webhooks.eventParse(str);
			 JSONObject jsonObj = JSONObject.fromObject(event.getData().get("object"));
			 if(jsonObj.get("channel").toString().startsWith("wx")){
			 JSONObject extraObj = (JSONObject) jsonObj.get("extra");
			 System.out.println(extraObj.get("open_id"));
			 }
		}
}