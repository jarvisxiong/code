package com.ffzx.order.api.enums;

/***
 * 订单操作原因记录
 * @author ying.cai
 * @date 2016年6月18日 下午2:18:39
 * @email ying.cai@ffzxnet.com
 * @version V1.0
 *
 */
public enum OrderOperationRecordEnum {
    MANUAL_CANCEL("手动取消订单","MANUAL_CANCEL"),
    AUTO_CANCEL("系统自动取消订单","AUTO_CANCEL");
	//TODO 后期可增加新的操作记录类型
	private String name;
	private String value;
	
	private OrderOperationRecordEnum(String name,String value){
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
	
	

}
