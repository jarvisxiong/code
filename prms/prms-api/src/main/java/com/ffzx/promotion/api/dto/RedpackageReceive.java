package com.ffzx.promotion.api.dto;

import com.ffzx.commerce.framework.common.persistence.DataEntity;
import com.ffzx.commerce.framework.constant.Constant;
import com.ffzx.commerce.framework.utils.DateUtil;
import com.ffzx.commerce.framework.utils.StringUtil;
import com.ffzx.promotion.api.dto.constant.PrmsConstant;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 
 * @author ffzx
 * @date 2016-11-07 09:41:50
 * @version 1.0.0
 * @copyright www.ffzxnet.com
 */
public class RedpackageReceive extends DataEntity<RedpackageReceive> {

    private static final long serialVersionUID = 1L;

    /**
     * 红包策略创建编码.
     */
    private String redpackageNumber;

    /**
     * 面值.
     */
    private BigDecimal fackValue;

    /**
     * 领取会员账户.
     */
    private String phone;

    /**
     * 领取时间.
     */
    private Date receiveDate;

    /**
     * 有效开始时间.
     */
    private Date startDate;

    /**
     * 有效结束时间.
     */
    private Date endDate;

    /**
     * 使用状态(1使用，0未使用）.
     */
    private String useState;

    /**
     * 使用时间.
     */
    private Date useDate;

    /**
     * 领取方式（1消息推送，2天降）.
     */
    private String receiveMode;

    /**
     * 红包策略id.
     */
    private String redpackageId;
    /**
     * 是否发放（0未发放，1发放）.目前无用到此字段
     */
    private String isGrant;

    /**
     * 是否领取（1领取，0未领取）.
     */
    private String isReceive;

    /**
     * 红包发放id.
     */
    private String redpackageGrantId;
    /**
     * 会员id.
     */
    private String memberId;
    /**
     * 是否支付成功(0否，1是).
     */
    private String isPay;
    /**
     * 订单付款金额.
     */
    private BigDecimal orderPrice;
    
    private String bestPrice;//省多少元
    //冗余字段
    private String receiveDateStartStr;//领取开始时间
    private String receiveDateEndStr;
    private String useStateStr;//使用状态  0未使用 1使用 2过期

    private String useDateStartStr;//使用开始时间
    private String useDateEndStr;
    private String effectiveStr;   //有效期
	private Redpackage redpackage;
	private RedpackageGrant grant;
    private String isSelected;//是否被选中(1 选中 0 未选中)
	
	
    
    public String getIsSelected() {
		return isSelected;
	}



	public void setIsSelected(String isSelected) {
		this.isSelected = isSelected;
	}



	public String getBestPrice() {
		return bestPrice;
	}



	public void setBestPrice(String bestPrice) {
		this.bestPrice = bestPrice;
	}



	public RedpackageGrant getGrant() {
		return grant;
	}



	public void setGrant(RedpackageGrant grant) {
		this.grant = grant;
	}



	public String getEffectiveStr() {
		return DateUtil.formatDateTime(startDate) +"到"+DateUtil.formatDateTime(startDate);
	}

    

    public String getUseStateStr() {
    	if(!StringUtil.isEmpty(useState)){
    		if(Constant.NO.equals(useState)){//未使用
    			if(DateUtil.diffDateTime(new Date(), endDate)<=0){
    				return PrmsConstant.UseNo;//未使用
    			}
    			return PrmsConstant.Useoverdue;//过期
    		}else{//已使用
    			return PrmsConstant.UseYse;
    		}
    	}
		return useStateStr;
	}



	public void setUseStateStr(String useStateStr) {
		this.useStateStr = useStateStr;
	}



	public BigDecimal getOrderPrice() {
		return orderPrice;
	}



	public String getIsPay() {
		return isPay;
	}



	public void setIsPay(String isPay) {
		this.isPay = isPay;
	}



	public void setOrderPrice(BigDecimal orderPrice) {
		this.orderPrice = orderPrice;
	}



	public void setEffectiveStr(String effectiveStr) {
		this.effectiveStr = effectiveStr;
	}



	public Redpackage getRedpackage() {
		return redpackage;
	}

	public String getRedpackageGrantId() {
		return redpackageGrantId;
	}

	public void setRedpackageGrantId(String redpackageGrantId) {
		this.redpackageGrantId = redpackageGrantId;
	}

	public void setRedpackage(Redpackage redpackage) {
		this.redpackage = redpackage;
	}

	public String getIsGrant() {
		return isGrant;
	}

	public void setIsGrant(String isGrant) {
		this.isGrant = isGrant;
	}

	public String getIsReceive() {
		return isReceive;
	}

	public void setIsReceive(String isReceive) {
		this.isReceive = isReceive;
	}

	public String getReceiveDateStartStr() {
		return receiveDateStartStr;
	}

	public void setReceiveDateStartStr(String receiveDateStartStr) {
		this.receiveDateStartStr = receiveDateStartStr;
	}

	public String getReceiveDateEndStr() {
		return receiveDateEndStr;
	}

	public void setReceiveDateEndStr(String receiveDateEndStr) {
		this.receiveDateEndStr = receiveDateEndStr;
	}

	public String getUseDateStartStr() {
		return useDateStartStr;
	}

	public void setUseDateStartStr(String useDateStartStr) {
		this.useDateStartStr = useDateStartStr;
	}

	public String getUseDateEndStr() {
		return useDateEndStr;
	}

	public void setUseDateEndStr(String useDateEndStr) {
		this.useDateEndStr = useDateEndStr;
	}

	/**
     * 
     * {@linkplain #redpackageNumber}
     *
     * @return the value of redpackage_receive.redpackage_number
     */
    public String getRedpackageNumber() {
        return redpackageNumber;
    }

    /**
     * {@linkplain #redpackageNumber}
     * @param redpackageNumber the value for redpackage_receive.redpackage_number
     */
    public void setRedpackageNumber(String redpackageNumber) {
        this.redpackageNumber = redpackageNumber == null ? null : redpackageNumber.trim();
    }

    /**
     * 
     * {@linkplain #fackValue}
     *
     * @return the value of redpackage_receive.fack_value
     */
    public BigDecimal getFackValue() {
        return fackValue;
    }

    /**
     * {@linkplain #fackValue}
     * @param fackValue the value for redpackage_receive.fack_value
     */
    public void setFackValue(BigDecimal fackValue) {
        this.fackValue = fackValue;
    }

    /**
     * 
     * {@linkplain #phone}
     *
     * @return the value of redpackage_receive.phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     * {@linkplain #phone}
     * @param phone the value for redpackage_receive.phone
     */
    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    /**
     * 
     * {@linkplain #receiveDate}
     *
     * @return the value of redpackage_receive.receive_date
     */
    public Date getReceiveDate() {
        return receiveDate;
    }

    /**
     * {@linkplain #receiveDate}
     * @param receiveDate the value for redpackage_receive.receive_date
     */
    public void setReceiveDate(Date receiveDate) {
        this.receiveDate = receiveDate;
    }

    /**
     * 
     * {@linkplain #startDate}
     *
     * @return the value of redpackage_receive.start_date
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * {@linkplain #startDate}
     * @param startDate the value for redpackage_receive.start_date
     */
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    /**
     * 
     * {@linkplain #endDate}
     *
     * @return the value of redpackage_receive.end_date
     */
    public Date getEndDate() {
        return endDate;
    }

    /**
     * {@linkplain #endDate}
     * @param endDate the value for redpackage_receive.end_date
     */
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    /**
     * 
     * {@linkplain #useState}
     *
     * @return the value of redpackage_receive.use_state
     */
    public String getUseState() {
        return useState;
    }

    /**
     * {@linkplain #useState}
     * @param useState the value for redpackage_receive.use_state
     */
    public void setUseState(String useState) {
        this.useState = useState == null ? null : useState.trim();
    }

    /**
     * 
     * {@linkplain #useDate}
     *
     * @return the value of redpackage_receive.use_date
     */
    public Date getUseDate() {
        return useDate;
    }

    /**
     * {@linkplain #useDate}
     * @param useDate the value for redpackage_receive.use_date
     */
    public void setUseDate(Date useDate) {
        this.useDate = useDate;
    }

    /**
     * 
     * {@linkplain #receiveMode}
     *
     * @return the value of redpackage_receive.receive_mode
     */
    public String getReceiveMode() {
        return receiveMode;
    }

    /**
     * {@linkplain #receiveMode}
     * @param receiveMode the value for redpackage_receive.receive_mode
     */
    public void setReceiveMode(String receiveMode) {
        this.receiveMode = receiveMode == null ? null : receiveMode.trim();
    }

    /**
     * 
     * {@linkplain #redpackageId}
     *
     * @return the value of redpackage_receive.redpackage_id
     */
    public String getRedpackageId() {
        return redpackageId;
    }

    /**
     * {@linkplain #redpackageId}
     * @param redpackageId the value for redpackage_receive.redpackage_id
     */
    public void setRedpackageId(String redpackageId) {
        this.redpackageId = redpackageId == null ? null : redpackageId.trim();
    }

    /**
     * 
     * {@linkplain #memberId}
     *
     * @return the value of redpackage_receive.member_id
     */
    public String getMemberId() {
        return memberId;
    }

    /**
     * {@linkplain #memberId}
     * @param memberId the value for redpackage_receive.member_id
     */
    public void setMemberId(String memberId) {
        this.memberId = memberId == null ? null : memberId.trim();
    }
}