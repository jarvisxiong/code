package com.ffzx.promotion.vo;

import java.io.Serializable;

/***
 * 赠品导出vo
 * @author ying.cai
 * @date 2016年9月30日 上午9:47:22
 * @email ying.cai@ffzxnet.com
 * @version V1.0
 *
 */
public class GiftExportVo implements Serializable{

	private static final long serialVersionUID = -6529433193773849767L;
	
	private String giveId;//买赠活动的主键id
	
	private String giftCommodityBarcode ; // 赠品商品条形码
	
	private String commodityName ; //赠品商品名称
	
	private String commoditySkuBarcode ; //赠品sku条形码
	
	private String giftLimtCount ; //赠品限定数量
	
	private String giftCount ; //单次赠送数量

	public String getGiveId() {
		return giveId;
	}

	public void setGiveId(String giveId) {
		this.giveId = giveId;
	}

	public String getGiftCommodityBarcode() {
		return giftCommodityBarcode;
	}

	public void setGiftCommodityBarcode(String giftCommodityBarcode) {
		this.giftCommodityBarcode = giftCommodityBarcode;
	}

	public String getCommodityName() {
		return commodityName;
	}

	public void setCommodityName(String commodityName) {
		this.commodityName = commodityName;
	}

	public String getCommoditySkuBarcode() {
		return commoditySkuBarcode;
	}

	public void setCommoditySkuBarcode(String commoditySkuBarcode) {
		this.commoditySkuBarcode = commoditySkuBarcode;
	}

	public String getGiftLimtCount() {
		return giftLimtCount;
	}

	public void setGiftLimtCount(String giftLimtCount) {
		this.giftLimtCount = giftLimtCount;
	}

	public String getGiftCount() {
		return giftCount;
	}

	public void setGiftCount(String giftCount) {
		this.giftCount = giftCount;
	}
	
	
}
