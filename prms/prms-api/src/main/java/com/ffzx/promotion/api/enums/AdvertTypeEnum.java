package com.ffzx.promotion.api.enums;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: 广告类型枚举
 * @author qh.xu
 * @email  qianghui.xu@ffzxnet.com
 * @date 2016年4月29日 上午10:06:02
 * @version V1.0 
 *
 */
public enum AdvertTypeEnum {
	CATEGORY_LIST("分类列表","CATEGORY_LIST"),
	PRESALE_LIST("预售列表","PRESALE_LIST"),
	PANICBUYING_LIST("抢购列表","PANICBUYING_LIST"),
	ACTIVITY_LIST("活动列表","ACTIVITY_LIST"),
	WHOLESALE_LIST("批发列表","WHOLESALE_LIST"),
	NEWUSER_LIST("新用户专享列表","NEWUSER_LIST"),
	PRESALE_DETAIL("预售详情","PRESALE_DETAIL"),
	PANICBUYING_DETAIL("抢购详情","PANICBUYING_DETAIL"),
	ORDINARYGOODS_DETAIL("普通商品详情","ORDINARYGOODS_DETAIL"),
	MENU_TYPE("菜单类型","MENU_TYPE"),
	BACKGROUND_IMAGE("背景图片","BACKGROUND_IMAGE"),
	WEB_LINK("WEB链接","WEB_LINK"),
	SECOND_VIEW("会场广告","SECOND_VIEW"),
	COUPON_VIEW("优惠券","COUPON_VIEW"),
	REWARD_VIEW("免费抽奖","REWARD_VIEW");
	private String name;
	
	private String value;
	
	private AdvertTypeEnum(String name,String value){
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
	public static List toList(){
		AdvertTypeEnum[] ary = AdvertTypeEnum.values();
		List list = new ArrayList();
		for(int i=0;i<ary.length;i++){
			Map<String,String> map = new HashMap<String,String>();
			map.put("value", ary[i].toString());
			map.put("name", ary[i].getName());
			list.add(map);
		}
		return list;
	}
}
