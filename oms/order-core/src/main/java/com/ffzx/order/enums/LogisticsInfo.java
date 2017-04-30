/**   
 * @Title: LogisticsInfo.java
 * @Package com.ffzx.order.enums
 * @Description: TODO
 * @author 雷  
 * @date 2016年6月28日 
 * @version V1.0   
 */
package com.ffzx.order.enums;

/**
 * @ClassName: LogisticsInfo
 * @Description: 物流信息
 * @author 雷
 * @date 2016年6月28日
 * 
 */
public enum LogisticsInfo {
	ZERO("【", "0"),THREE("】开始拣货", "3"), FOUR("】完成拣货", "4"),FIVE("】开始打包", "5"), SIX("】打包完成", "6"),SEVEN("】正发往【", "7"),LAST("】", "10");
	// 成员变量
	private String name;
	private String index;

	// 构造方法
	private LogisticsInfo(String name, String index) {
		this.name = name;
		this.index = index;
	}

	// 普通方法
	public static String getName(String index) {
		for (LogisticsInfo u : LogisticsInfo.values()) {
			if (u.getIndex() == index) {
				return u.name;
			}
		}
		return null;
	}

	// get set 方法
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIndex() {
		return index;
	}

	public void setIndex(String index) {
		this.index = index;
	}
}
