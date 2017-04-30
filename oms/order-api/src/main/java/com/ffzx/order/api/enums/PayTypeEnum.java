package com.ffzx.order.api.enums;


 /**
 * @Description: 订单支付类型
 * @author qh.xu
 * @email  qianghui.xu@ffzxnet.com
 * @date 2016年4月22日 下午3:11:25
 * @version V1.0 
 *
 */
public enum PayTypeEnum {
	ALIPAYPC("支付宝PC","ALIPAYPC"),
	ALIPAYSM("支付宝扫码","ALIPAYSM"),
	WXPAY("微信支付","WXPAY"),
	WX_PUB("微信公众号支付","WX_PUB"),
	ALIPAYAPP("支付宝APP","ALIPAYAPP"),
	TFTFASTPAY("腾付通快捷支付","TFTFASTPAY");
	
	private String name;
	private String value;
	
	private PayTypeEnum(String name,String value){
		this.name = name;
		this.value = value;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
}
