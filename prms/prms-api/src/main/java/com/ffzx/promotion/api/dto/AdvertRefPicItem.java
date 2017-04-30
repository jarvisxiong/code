package com.ffzx.promotion.api.dto;

import com.ffzx.commerce.framework.common.persistence.DataEntity;


 /**
 * @Description: 广告实体 与 单张图片 绑定 中间表
 * @author qh.xu
 * @email  qianghui.xu@ffzxnet.com
 * @date 2016年4月29日 上午11:21:58
 * @version V1.0 
 *
 */
public class AdvertRefPicItem extends DataEntity<AdvertRefPicItem>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4312909504395450312L;
	
	private Advert advert;//关联广告
	
	private AdvertPicItem picItem;//关联 单张图片对象
	
	private Integer sort;//序号

	public Advert getAdvert() {
		return advert;
	}

	public void setAdvert(Advert advert) {
		this.advert = advert;
	}

	public AdvertPicItem getPicItem() {
		return picItem;
	}

	public void setPicItem(AdvertPicItem picItem) {
		this.picItem = picItem;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}
	
}
