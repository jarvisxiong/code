package com.ffzx.promotion.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.ffzx.commerce.framework.common.persistence.BaseEntity;
import com.ffzx.promotion.api.dto.constant.Constant;

/**
 * 买赠活动导出对象
 * @author ying.cai
 * @date 2016年9月19日 上午11:34:24
 * @email ying.cai@ffzxnet.com
 * @version V1.0
 *
 */
@SuppressWarnings("rawtypes")
public class GiveExportVo extends BaseEntity implements Serializable{

	private static final long serialVersionUID = -9136687505959276871L;
	
	private String id ;//主键ID
	
	private String giveId ; //买赠活动id
	
	private String giveTitle ; //买赠活动名称
	
	private String commodityBarcode ; //主商品条形码
	
	private String idLimit ; //主商品ID限购量
	
	private String limitCount;//主商品限定数量
	
	private String preferentialPrice ; //主商品优惠价
	
	private String createBy ; //优惠券创建人
	
	private Date createDate ; //优惠券创建时间
	
	private String giftType ; //赠品类型 ; 商品/优惠券
	
	//用作导出构建
	private CouponExportVo couponExportVo;
	//用作导出构建
	private GiftExportVo giftExportVo;
	
	//优惠券赠品集合
	private List<CouponExportVo> couponList = new ArrayList<>();
	//商品赠品集合
	private List<GiftExportVo> giftList = new ArrayList<>();
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLimitCount() {
		return limitCount;
	}

	public void setLimitCount(String limitCount) {
		this.limitCount = limitCount;
	}

	public CouponExportVo getCouponExportVo() {
		return couponExportVo;
	}

	public void setCouponExportVo(CouponExportVo couponExportVo) {
		this.couponExportVo = couponExportVo;
	}

	public GiftExportVo getGiftExportVo() {
		return giftExportVo;
	}

	public void setGiftExportVo(GiftExportVo giftExportVo) {
		this.giftExportVo = giftExportVo;
	}

	public String getGiveId() {
		return giveId;
	}

	public void setGiveId(String giveId) {
		this.giveId = giveId;
	}

	public String getGiveTitle() {
		return giveTitle;
	}

	public void setGiveTitle(String giveTitle) {
		this.giveTitle = giveTitle;
	}

	public String getCommodityBarcode() {
		return commodityBarcode;
	}

	public void setCommodityBarcode(String commodityBarcode) {
		this.commodityBarcode = commodityBarcode;
	}

	public String getIdLimit() {
		return idLimit;
	}

	public void setIdLimit(String idLimit) {
		this.idLimit = idLimit;
	}

	public String getPreferentialPrice() {
		return preferentialPrice;
	}

	public void setPreferentialPrice(String preferentialPrice) {
		this.preferentialPrice = preferentialPrice;
	}
	
	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getGiftType() {
		if(StringUtils.isBlank(giftType)){
			return null;
		}
		if(Constant.Goods.equals(giftType)){
			return "商品";
		}else if(Constant.Coupon.equals(giftType)){
			return "优惠券";
		}else{
			return "商品/优惠券";
		}
	}

	public void setGiftType(String giftType) {
		this.giftType = giftType;
	}

	public List<CouponExportVo> getCouponList() {
		return couponList;
	}

	public void setCouponList(List<CouponExportVo> couponList) {
		this.couponList = couponList;
	}

	public List<GiftExportVo> getGiftList() {
		return giftList;
	}

	public void setGiftList(List<GiftExportVo> giftList) {
		this.giftList = giftList;
	}
	
}
