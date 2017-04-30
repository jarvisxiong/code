package com.ffzx.promotion.api.dto;

import com.ffzx.commerce.framework.common.persistence.DataEntity;
import java.math.BigDecimal;

/**
 * 
 * @author ffzx
 * @date 2016-11-14 11:31:33
 * @version 1.0.0
 * @copyright www.ffzxnet.com
 */
public class RedpackageCount extends DataEntity<RedpackageCount> {

    private static final long serialVersionUID = 1L;

    public RedpackageCount(Integer grantNum, Integer receiveNum, Integer receiveTime, Integer usePerson, Integer useNum,
			BigDecimal useChange, BigDecimal useRedpackagePrice, BigDecimal useOrderPrice,
			BigDecimal useOrderPersonPrice, String redpackageId) {
		super();
		this.grantNum = grantNum;
		this.receiveNum = receiveNum;
		this.receiveTime = receiveTime;
		this.usePerson = usePerson;
		this.useNum = useNum;
		this.useChange = useChange;
		this.useRedpackagePrice = useRedpackagePrice;
		this.useOrderPrice = useOrderPrice;
		this.useOrderPersonPrice = useOrderPersonPrice;
		this.redpackageId = redpackageId;
	}

	public RedpackageCount() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
     * 发放数量.
     */
    private Integer grantNum;

    /**
     * 领取张数.
     */
    private Integer receiveNum;

    /**
     * 领取次数.
     */
    private Integer receiveTime;

    /**
     * 使用人数.
     */
    private Integer usePerson;

    /**
     * 使用次数.
     */
    private Integer useNum;

    /**
     * 使用率.
     */
    private BigDecimal useChange;

    /**
     * 使用红包总金额.
     */
    private BigDecimal useRedpackagePrice;

    /**
     * 使用红包总订单流水.
     */
    private BigDecimal useOrderPrice;

    /**
     * 使用红包客单价（客单=销售总额/顾客总数).
     */
    private BigDecimal useOrderPersonPrice;

    /**
     * 红包策略id.
     */
    private String redpackageId;

    /**
     * 
     * {@linkplain #grantNum}
     *
     * @return the value of redpackage_count.grant_num
     */
    public Integer getGrantNum() {
        return grantNum;
    }

    /**
     * {@linkplain #grantNum}
     * @param grantNum the value for redpackage_count.grant_num
     */
    public void setGrantNum(Integer grantNum) {
        this.grantNum = grantNum;
    }

    /**
     * 
     * {@linkplain #receiveNum}
     *
     * @return the value of redpackage_count.receive_num
     */
    public Integer getReceiveNum() {
        return receiveNum;
    }

    /**
     * {@linkplain #receiveNum}
     * @param receiveNum the value for redpackage_count.receive_num
     */
    public void setReceiveNum(Integer receiveNum) {
        this.receiveNum = receiveNum;
    }

    /**
     * 
     * {@linkplain #receiveTime}
     *
     * @return the value of redpackage_count.receive_time
     */
    public Integer getReceiveTime() {
        return receiveTime;
    }

    /**
     * {@linkplain #receiveTime}
     * @param receiveTime the value for redpackage_count.receive_time
     */
    public void setReceiveTime(Integer receiveTime) {
        this.receiveTime = receiveTime;
    }

    /**
     * 
     * {@linkplain #usePerson}
     *
     * @return the value of redpackage_count.use_person
     */
    public Integer getUsePerson() {
        return usePerson;
    }

    /**
     * {@linkplain #usePerson}
     * @param usePerson the value for redpackage_count.use_person
     */
    public void setUsePerson(Integer usePerson) {
        this.usePerson = usePerson;
    }

    /**
     * 
     * {@linkplain #useNum}
     *
     * @return the value of redpackage_count.use_num
     */
    public Integer getUseNum() {
        return useNum;
    }

    /**
     * {@linkplain #useNum}
     * @param useNum the value for redpackage_count.use_num
     */
    public void setUseNum(Integer useNum) {
        this.useNum = useNum;
    }

    /**
     * 
     * {@linkplain #useChange}
     *
     * @return the value of redpackage_count.use_change
     */
    public BigDecimal getUseChange() {
        return useChange;
    }

    /**
     * {@linkplain #useChange}
     * @param useChange the value for redpackage_count.use_change
     */
    public void setUseChange(BigDecimal useChange) {
        this.useChange = useChange;
    }

    /**
     * 
     * {@linkplain #useRedpackagePrice}
     *
     * @return the value of redpackage_count.use_redpackage_price
     */
    public BigDecimal getUseRedpackagePrice() {
        return useRedpackagePrice;
    }

    /**
     * {@linkplain #useRedpackagePrice}
     * @param useRedpackagePrice the value for redpackage_count.use_redpackage_price
     */
    public void setUseRedpackagePrice(BigDecimal useRedpackagePrice) {
        this.useRedpackagePrice = useRedpackagePrice;
    }

    /**
     * 
     * {@linkplain #useOrderPrice}
     *
     * @return the value of redpackage_count.use_order_price
     */
    public BigDecimal getUseOrderPrice() {
        return useOrderPrice;
    }

    /**
     * {@linkplain #useOrderPrice}
     * @param useOrderPrice the value for redpackage_count.use_order_price
     */
    public void setUseOrderPrice(BigDecimal useOrderPrice) {
        this.useOrderPrice = useOrderPrice;
    }

    /**
     * 
     * {@linkplain #useOrderPersonPrice}
     *
     * @return the value of redpackage_count.use_order_person_price
     */
    public BigDecimal getUseOrderPersonPrice() {
        return useOrderPersonPrice;
    }

    /**
     * {@linkplain #useOrderPersonPrice}
     * @param useOrderPersonPrice the value for redpackage_count.use_order_person_price
     */
    public void setUseOrderPersonPrice(BigDecimal useOrderPersonPrice) {
        this.useOrderPersonPrice = useOrderPersonPrice;
    }

    /**
     * 
     * {@linkplain #redpackageId}
     *
     * @return the value of redpackage_count.redpackage_id
     */
    public String getRedpackageId() {
        return redpackageId;
    }

    /**
     * {@linkplain #redpackageId}
     * @param redpackageId the value for redpackage_count.redpackage_id
     */
    public void setRedpackageId(String redpackageId) {
        this.redpackageId = redpackageId == null ? null : redpackageId.trim();
    }

	@Override
	public String toString() {
		return "RedpackageCount [grantNum=" + grantNum + ", receiveNum=" + receiveNum + ", receiveTime=" + receiveTime
				+ ", usePerson=" + usePerson + ", useNum=" + useNum + ", useChange=" + useChange
				+ ", useRedpackagePrice=" + useRedpackagePrice + ", useOrderPrice=" + useOrderPrice
				+ ", useOrderPersonPrice=" + useOrderPersonPrice + ", redpackageId=" + redpackageId + ", id=" + id
				+ "]";
	}
}