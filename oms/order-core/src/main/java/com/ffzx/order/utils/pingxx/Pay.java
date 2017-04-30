package com.ffzx.order.utils.pingxx;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.ffzx.commerce.framework.exception.ServiceException;
import com.ffzx.commerce.framework.utils.StringUtil;
import com.ffzx.order.api.dto.OmsOrder;
import com.ffzx.order.api.enums.PayTypeEnum;
import com.pingplusplus.Pingpp;
import com.pingplusplus.exception.APIConnectionException;
import com.pingplusplus.exception.APIException;
import com.pingplusplus.exception.AuthenticationException;
import com.pingplusplus.exception.ChannelException;
import com.pingplusplus.exception.InvalidRequestException;
import com.pingplusplus.exception.PingppException;
import com.pingplusplus.model.App;
import com.pingplusplus.model.Charge;
import com.pingplusplus.model.Refund;


public class Pay {

	private static Logger logger=Logger.getLogger(Pay.class);
	


	
	//发送支付请求
	public static void refundApply(OmsOrder order) throws Exception{
		logger.error("订单【"+order.getOrderNo()!=null?order.getOrderNo():"不详"+"】发起退款服务==》》pingxx-chargeId="+order.getChargeId());
		if(!StringUtil.isEmpty(order.getChargeId())){
			BigDecimal d100 = new BigDecimal(100);
			Pingpp.apiKey =  PayUtil.APIKEY; 
			//想支付方发起退款申请 
			Charge ch = Charge.retrieve(order.getChargeId());
			Map<String, Object> refundMap = new HashMap<String, Object>();
			refundMap.put("amount", order.getActualPrice().multiply(d100).intValue());//支付金额
			refundMap.put("description", "大麦场为您退款");
			Refund re = ch.getRefunds().create(refundMap);
		}
	}
	
	public static Map createCharge(OmsOrder order,String payType,String time_expire,String value) {
		Map<String,Object> resultMap = new HashMap<String,Object>();
		logger.info("Pay=====>createCharge=====PayUtil.APP_ID>"+PayUtil.APP_ID+"begin");;
		Pingpp.apiKey =  PayUtil.APIKEY; //api key
		Charge charge = null;
		Map<String, Object> chargeMap = new HashMap<String, Object>();
		int amount = 0;
		amount = order.getActualPrice().multiply(new BigDecimal(100)).intValue();
		Map<String, String> app = new HashMap<String, String>();
		app.put("id",PayUtil.APP_ID);//支付使用的 app标识。 用做微信公众号支付
		chargeMap.put("app",app);
		chargeMap.put("order_no", order.getOrderNo()); //订单编号
		chargeMap.put("channel", payType);	//支付模式 
		if(payType.equals("wx_pub")){
			Map<String, String> extra = new HashMap<String, String>();
			extra.put("open_id", value);
			chargeMap.put("extra", extra);
		}
		chargeMap.put("amount", amount); //以分为单位的总金额
		chargeMap.put("client_ip", "127.0.0.1");//客户机IP
		chargeMap.put("currency", "cny"); //人民币货币代码
		chargeMap.put("subject", "大麦场在线支付"); //标题
		chargeMap.put("body", "大麦场竭诚为您服务"); //描述信息		
		chargeMap.put("time_expire",time_expire);//超时时间...
//		chargeMap.put("extra",getExtra(payType));
		Exception ex = null ; 
		try {
			charge = Charge.create(chargeMap);
			PayUtil.log(1, "createCharge", "支付凭证："+charge.getId());
			logger.info("订单号:"+order.getOrderNo()+";chargeId:"+charge.getId());
		} catch (AuthenticationException e) {
			ex = e;
			logger.error("AuthenticationException", e);
			//e.printStackTrace();
		} catch (InvalidRequestException e) {
			ex = e;
			logger.error("InvalidRequestException", e);
			//e.printStackTrace();
		} catch (APIConnectionException e) {
			ex = e;
			logger.error("APIConnectionException", e);
			//e.printStackTrace();
		} catch (APIException e) {
			ex = e;
			logger.error("APIException", e);
			//e.printStackTrace();
		} catch (ChannelException e) {
			ex = e;
			logger.error("ChannelException", e);
			//e.printStackTrace();
		}
		if(ex!=null){
			if(ex.getMessage().contains("time_expire")){
				logger.error("【"+order.getOrderNo()+"】pingxx时间和本地时间不一致。。。");
				throw new ServiceException("支付超时，请重新发起交易！");
			}else{
				logger.error("【"+order.getOrderNo()+"】pingxx支付异常。。。"+ex.getMessage());
				throw new ServiceException(ex.getMessage());
			}
		}
		
		//需要将支付凭证ID存入订单中，以便退款使用
		//如果第一次支付模式和第二次支付模式相同，则继续使用原来的chargeID
		//为了避免可能出现的一种情况：第一次使用微信支付，但放弃支付，接下来再使用支付宝支付，这时候继续使用原来的凭证会有误
		if((order.getPayType()==PayTypeEnum.ALIPAYAPP && payType.equals("alipay")) || (order.getPayType()==PayTypeEnum.WXPAY && payType.equals("wx"))|| (order.getPayType()==PayTypeEnum.WX_PUB && payType.equals("wx_pub"))){
		charge.setId(order.getChargeId());
		}
		
		//如果有，判断是否支付过
		if(StringUtil.isNotNull(order.getChargeId())){//不为空
			//判断改订单是否支付成功
			Charge oldCharge=Pay.retrieve(order.getChargeId());//在回调之前校验
			if(oldCharge.getPaid()){//如果为true表示已经支付了
				//不需要改变。。
				throw new  ServiceException("该订单已支付！");
			}
		}
		
		order.setChargeId(charge.getId());//如果chargid未空使用新创建的.如果不为空使用旧的chargid
		order.setPayType(getPayType(payType));
		resultMap.put("order",order);
		resultMap.put("charge",charge);
		return resultMap;
	}
	
	private static PayTypeEnum getPayType(String payType){
		if(payType.equals("alipay")){
			return PayTypeEnum.ALIPAYAPP;
		}else if(payType.equals("wx")){
			return PayTypeEnum.WXPAY;
		}else if(payType.equals("wx_pub")){
			return PayTypeEnum.WX_PUB;
		}
		return null;
	}
	/**
	 * 查询 Charge
	 * 
	 * 该接口根据 charge Id 查询对应的 charge 。
	 * 参考文档：https://pingxx.com/document/api#api-c-inquiry
	 * 
	 * 该接口可以传递一个 expand ， 返回的 charge 中的 app 会变成 app 对象。 参考文档：
	 * https://pingxx.com/document/api#api-expanding
	 * 
	 * @param id
	 */
	public static Charge retrieve(String id) {
		Pingpp.apiKey = PayUtil.APIKEY; //api key
		try {
			Map<String, Object> param = new HashMap<String, Object>();
			List<String> expande = new ArrayList<String>();
			expande.add("app");
			param.put("expand", expande);
			// Charge charge = Charge.retrieve(id);
			// Expand app
			Charge charge = Charge.retrieve(id, param);
			if (charge.getApp() instanceof App) {
				// App app = (App) charge.getApp();
				// System.out.println("App Object ,appId = " + app.getId());
			} else {
				// System.out.println("String ,appId = " + charge.getApp());
			}
			System.out.println(charge);
			logger.error("根据chargeId查询订单信息【"+id+"】"+charge.toString());
			return charge;
		} catch (PingppException e) {
			logger.error("",e);
		}
		return null;
	}
	
	/***
	 * 根据支付方式的不同构建不同的参数
	 * @param payType
	 * @return
	 */
	private static Map<String , Object> getExtra(String payType){
		Map<String,Object> extra = new HashMap<String, Object>();
//		extra.put("success_url", "http://www.javasuc.com/pay"); //后期根据不同支付方式设置不同的参数
		return extra;
	}
	
	public static void main(String[] args) throws IOException {
		Pingpp.apiKey = "sk_live_ny7vQzn0X8e7v0BFRskkmIWw";
		try {
			Charge charge = Charge.retrieve("ch_Cqvz54XbbPaTnXHSmTXPuTq1");
			System.out.println(charge.toString());
		} catch (AuthenticationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidRequestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (APIConnectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (APIException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ChannelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
