package com.ffzx.order.api.enums;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: 订单状态枚举
 * @author qh.xu
 * @email  qianghui.xu@ffzxnet.com
 * @date 2016年4月22日 下午2:19:23
 * @version V1.0 
 *
 */       
public enum OrderStatusEnum {
	PENDING_PAYMENT("待付款","0"),
	RECEIPT_OF_GOODS("待收货","1"),
	REFUND_APPLICATION("退款申请中","2"),
	TRANSACTION_CLOSED("交易关闭","3"),
	TRANSACTION_COMPLETION("交易完成","4"),
	ORDER_CANCELED("订单已取消","5")
	;
	private String name;
	
	private String value;
	
	private OrderStatusEnum(String name,String value){
		this.name=name;
		this.value=value;
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
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List toList()
	{
		OrderStatusEnum[] ary = OrderStatusEnum.values();
		List list = new ArrayList();
		for(int i=0;i<ary.length;i++){
			Map<String,String> map = new HashMap<String,String>();
			map.put("value", ary[i].getValue());
			map.put("name", ary[i].getName());
			list.add(map);
		}
		return list;
	}
	

}
