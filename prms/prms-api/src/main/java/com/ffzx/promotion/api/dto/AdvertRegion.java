package com.ffzx.promotion.api.dto;

import com.ffzx.commerce.framework.common.persistence.TreeEntity;

/**
 * @Description: 广告对应区域
 * @author qh.xu
 * @email  qianghui.xu@ffzxnet.com
 * @date 2016年4月29日 上午10:56:01
 * @version V1.0 
 *
 */
public class AdvertRegion extends TreeEntity<AdvertRegion>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4826681835531120412L;	
	
	private String number;//编号
	
	private String supportTypes;//支持的广告类型 逗号 隔开  AdvertTypeEnum
		
	private Integer limitCount;//限制 广告数量	

	private Integer isSystem;//是否是系统数据
	
	
	public Integer getIsSystem() {
		return isSystem;
	}

	public void setIsSystem(Integer isSystem) {
		this.isSystem = isSystem;
	}

	public Integer getLimitCount() {
		return limitCount;
	}

	public void setLimitCount(Integer limitCount) {
		this.limitCount = limitCount;
	}


	@Override
	public AdvertRegion getParent() {
		return parent;
	}

	@Override
	public void setParent(AdvertRegion parent) {
		this.parent=parent;
	}
	
	public String getSupportTypes() {
		return supportTypes;
	}

	public void setSupportTypes(String supportTypes) {
		this.supportTypes = supportTypes;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}
	
	
	
	

}
