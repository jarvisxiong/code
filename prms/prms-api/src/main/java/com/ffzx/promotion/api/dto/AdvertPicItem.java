package com.ffzx.promotion.api.dto;

import com.ffzx.commerce.framework.common.persistence.DataEntity;
import com.ffzx.promotion.api.enums.AdvertTypeEnum;



 /**
 * @Description: 单张广告图片对象
 * @author qh.xu
 * @email  qianghui.xu@ffzxnet.com
 * @date 2016年4月29日 上午10:57:08
 * @version V1.0 
 *
 */
public class AdvertPicItem extends DataEntity<AdvertPicItem>{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4796333269293441217L;
	
	private AdvertTypeEnum advertType;//广告类型
	
	private String picPath;//图片路径
	
	private String refId;//关联ID (ep:选择的 商品类目id) (跳转到 某一指定页面)
	
	private String linkPath;//链接路径





	public String getLinkPath() {
		return linkPath;
	}

	public void setLinkPath(String linkPath) {
		this.linkPath = linkPath;
	}

	public AdvertTypeEnum getAdvertType() {
		return advertType;
	}

	public void setAdvertType(AdvertTypeEnum advertType) {
		this.advertType = advertType;
	}

	public String getPicPath() {
		return picPath;
	}

	public void setPicPath(String picPath) {
		this.picPath = picPath;
	}

	public String getRefId() {
		return refId;
	}

	public void setRefId(String refId) {
		this.refId = refId;
	}
	
	
	
}
