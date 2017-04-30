package com.ffzx.order.api.vo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.ffzx.commerce.framework.common.persistence.BaseEntity;

/**
 * @Description: 订单vo
 * @author qh.xu
 * @email qianghui.xu@ffzxnet.com
 * @date 2016年6月14日 下午3:41:10
 * @version V1.0
 * 
 */
public class OmsOrderVo extends BaseEntity<OmsOrderVo> {

	/**
	 * 返回参数
	 */
	private static final long serialVersionUID = -1993327146233387542L;

	private String orderNo;// 订单号

	private String consignName;// 收货人姓名

	private String consignPhone;// 收货人电话

	private String addressInfo;// 详细收货地址

	private String partnerId;// 合伙人ID
	
	private Integer todayUnDisCount;
	
	private Integer historyUnDisCount;
	
	private Integer alreadyDisCount;
	
	private Integer backCount;//退款订单数量
	
	/**
	 * 下单时间
	 */
	private String createDate;
	/**
	 * 支付时间
	 */
	private String payTime;
	/**
	 * 购买商品数量
	 */
	private int buyCount;
	/**
	 * 订单支付金额
	 */
	private BigDecimal actualPrice;
	
	/**
	 * 查询参数
	 */
	private List<String> orderNoList;// 订单编号list

	private int waterline;// 涨位数

	private String keyWords;// 搜索关键字

	private Date todayUnDist;// 今日待配送 (配送单收货时间 24小时以内)

	private Date missingDist;// 遗漏配送 (配送单收货时间 超过 24小时)

	// 该字段必传 要不然查 为收货订单
	private String outOrderStatus;// 订单配送状态 0:确认收货,1:确认送达 , null未确认收货
	
	private List<String> statusList;//查询订单状态
	/**
	 * 2016-06-15新增：会员ID
	 */
	private String memberID;
	/**
	 * 2016-06-15新增：订单状态
	 */
	private String orderState;
	
	/**
	 * 2016-08-19 新增：物流最新状态
	 */
	private String logisticsStatus;
	/**
	 * 2016-08-19 配送单确认收货时间
	 */
	private Date distributionDate;
	
	/**
	 * 2016-08-24 确认送达开始时间
	 */
	private String deliveryStartDate;
	
	/**
	 * 2016-08-24 确认送达结束时间
	 */
	private String deliveryEndDate;
	
	
	public String getDeliveryStartDate() {
		return deliveryStartDate;
	}

	public void setDeliveryStartDate(String deliveryStartDate) {
		this.deliveryStartDate = deliveryStartDate;
	}

	public String getDeliveryEndDate() {
		return deliveryEndDate;
	}

	public void setDeliveryEndDate(String deliveryEndDate) {
		this.deliveryEndDate = deliveryEndDate;
	}

	public Date getDistributionDate() {
		return distributionDate;
	}

	public void setDistributionDate(Date distributionDate) {
		this.distributionDate = distributionDate;
	}
	

	public Integer getBackCount() {
		return backCount;
	}

	public void setBackCount(Integer backCount) {
		this.backCount = backCount;
	}

	public List<String> getStatusList() {
		return statusList;
	}

	public void setStatusList(List<String> statusList) {
		this.statusList = statusList;
	}

	public Integer getTodayUnDisCount() {
		return todayUnDisCount;
	}

	public void setTodayUnDisCount(Integer todayUnDisCount) {
		this.todayUnDisCount = todayUnDisCount;
	}

	public Integer getHistoryUnDisCount() {
		return historyUnDisCount;
	}

	public void setHistoryUnDisCount(Integer historyUnDisCount) {
		this.historyUnDisCount = historyUnDisCount;
	}

	public Integer getAlreadyDisCount() {
		return alreadyDisCount;
	}

	public void setAlreadyDisCount(Integer alreadyDisCount) {
		this.alreadyDisCount = alreadyDisCount;
	}

	public String getOutOrderStatus() {
		return outOrderStatus;
	}

	public void setOutOrderStatus(String outOrderStatus) {
		this.outOrderStatus = outOrderStatus;
	}

	public Date getTodayUnDist() {
		return todayUnDist;
	}

	public void setTodayUnDist(Date todayUnDist) {
		this.todayUnDist = todayUnDist;
	}

	public Date getMissingDist() {
		return missingDist;
	}

	public void setMissingDist(Date missingDist) {
		this.missingDist = missingDist;
	}

	public String getPartnerId() {
		return partnerId;
	}

	public void setPartnerId(String partnerId) {
		this.partnerId = partnerId;
	}

	public String getKeyWords() {
		return keyWords;
	}

	public void setKeyWords(String keyWords) {
		this.keyWords = keyWords;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getConsignName() {
		return consignName;
	}

	public void setConsignName(String consignName) {
		this.consignName = consignName;
	}

	public String getConsignPhone() {
		return consignPhone;
	}

	public void setConsignPhone(String consignPhone) {
		this.consignPhone = consignPhone;
	}

	public String getAddressInfo() {
		return addressInfo;
	}

	public void setAddressInfo(String addressInfo) {
		this.addressInfo = addressInfo;
	}

	public List<String> getOrderNoList() {
		return orderNoList;
	}

	public void setOrderNoList(List<String> orderNoList) {
		this.orderNoList = orderNoList;
	}

	public int getWaterline() {
		return waterline;
	}

	public void setWaterline(int waterline) {
		this.waterline = waterline;
	}

	/**
	 * 雷-----2016年6月15日
	 * 
	 * @return the memberID
	 */
	public String getMemberID() {
		return memberID;
	}

	/**
	 * 雷-------2016年6月15日
	 * 
	 * @param memberID
	 *            the memberID to set
	 */
	public void setMemberID(String memberID) {
		this.memberID = memberID;
	}

	/**
	 * 雷-----2016年6月15日
	 * 
	 * @return the orderState
	 */
	public String getOrderState() {
		return orderState;
	}

	/**
	 * 雷-------2016年6月15日
	 * 
	 * @param orderState
	 *            the orderState to set
	 */
	public void setOrderState(String orderState) {
		this.orderState = orderState;
	}

	/**
	 * 雷-----2016年6月15日
	 * @return the createDate
	 */
	public String getCreateDate() {
		return createDate;
	}

	/**
	 * 雷-------2016年6月15日
	 * @param createDate the createDate to set
	 */
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	/**
	 * 雷-----2016年6月15日
	 * @return the payTime
	 */
	public String getPayTime() {
		return payTime;
	}

	/**
	 * 雷-------2016年6月15日
	 * @param payTime the payTime to set
	 */
	public void setPayTime(String payTime) {
		this.payTime = payTime;
	}

	/**
	 * 雷-----2016年6月15日
	 * @return the buyCount
	 */
	public int getBuyCount() {
		return buyCount;
	}

	/**
	 * 雷-------2016年6月15日
	 * @param buyCount the buyCount to set
	 */
	public void setBuyCount(int buyCount) {
		this.buyCount = buyCount;
	}

	/**
	 * 雷-----2016年6月15日
	 * @return the actualPrice
	 */
	public BigDecimal getActualPrice() {
		return actualPrice;
	}

	/**
	 * 雷-------2016年6月15日
	 * @param actualPrice the actualPrice to set
	 */
	public void setActualPrice(BigDecimal actualPrice) {
		this.actualPrice = actualPrice;
	}

	public String getLogisticsStatus() {
		return logisticsStatus;
	}

	public void setLogisticsStatus(String logisticsStatus) {
		this.logisticsStatus = logisticsStatus;
	}

}
