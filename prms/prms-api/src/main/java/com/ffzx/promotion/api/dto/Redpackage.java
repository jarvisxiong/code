package com.ffzx.promotion.api.dto;

import com.ffzx.commerce.framework.common.persistence.DataEntity;
import com.ffzx.commerce.framework.utils.DateUtil;
import com.ffzx.promotion.api.dto.constant.PrmsConstant;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 
 * @author ffzx
 * @date 2016-11-07 14:56:07
 * @version 1.0.0
 * @copyright www.ffzxnet.com
 */
public class Redpackage extends DataEntity<Redpackage> {

    private static final long serialVersionUID = 1L;

    public Redpackage() {
		super();
	}

	public Redpackage(String id) {
		super(id);
		// TODO Auto-generated constructor stub
	}

	/**
     * 红包编码.
     */
    private String number;

    /**
     * 红包名称.
     */
    private String name;

    /**
     * 面值.
     */
    private BigDecimal fackValue;

    /**
     * 领取限制（活动期间每人限领1张）.
     */
    private Integer receiveLimit;

    /**
     * (时间限制，是自领取后生效1，自定有效期0）.
     */
    private String dateStart;

    /**
     * （1启用，0禁用）.
     */
    private String state;

    /**
     * 创建人名称.
     */
    private String createName;

    /**
     * 最后更新者名称.
     */
    private String lastUpdateName;

    /**
     * 是否加入红包发放管理(1是，0否）.
     */
    private String isGrant;

    /**
     * 自领取后生效多少天).
     */
    private Integer receivedate;

    /**
     * 开始时间.
     */
    private Date startDate;

    /**
     * 结束时间.
     */
    private Date endDate;

    /**
     * 发放数量.
     */
    private Integer grantNum;
    /**
     * 冗余字段
     */
    
     // (时间限制，是自领取后生效1，自定有效期0）.
    private String dateStartupdate;
    private String startDateStr;//开始时间
    private String endDateStr;//结束时间
    private BigDecimal fackValuestart;//面值开始
    private BigDecimal fackValueend;//面值结束
    private String  lastUpdateDateStartStr;//最后开始时间
    private String  lastUpdateDateEndtStr;//最后结束时间
    private String effectiveStr;   //有效期
    private String validDate;//有效截止时间，红包发放选择红包，截止时间必须大于它
	private List<RedpackageHandle> redpackageHandles;
    
	public String getDateStartupdate() {
		return dateStartupdate;
	}

	public void setDateStartupdate(String dateStartupdate) {
		this.dateStartupdate = dateStartupdate;
	}

	public String getValidDate() {
		return validDate;
	}

	public void setValidDate(String validDate) {
		this.validDate = validDate;
	}

	public String getEffectiveStr() {
    	if(PrmsConstant.RECEIVEEFFECTIVE.equals(dateStart)){
    		return receivedate+"天";
    	}else if(PrmsConstant.CUSTEMEFFECTIVE.equals(dateStart)){
    		return DateUtil.formatDateTime(startDate) +"到"+DateUtil.formatDateTime(endDate);
    	}
		return effectiveStr;
	}

	public void setEffectiveStr(String effectiveStr) {
		this.effectiveStr = effectiveStr;
	}

    public List<RedpackageHandle> getRedpackageHandles() {
		return redpackageHandles;
	}

	public void setRedpackageHandles(List<RedpackageHandle> redpackageHandles) {
		this.redpackageHandles = redpackageHandles;
	}

	public String getLastUpdateDateStartStr() {
		return lastUpdateDateStartStr;
	}

	public void setLastUpdateDateStartStr(String lastUpdateDateStartStr) {
		this.lastUpdateDateStartStr = lastUpdateDateStartStr;
	}

	public String getLastUpdateDateEndtStr() {
		return lastUpdateDateEndtStr;
	}

	public void setLastUpdateDateEndtStr(String lastUpdateDateEndtStr) {
		this.lastUpdateDateEndtStr = lastUpdateDateEndtStr;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public BigDecimal getFackValuestart() {
		return fackValuestart;
	}

	public void setFackValuestart(BigDecimal fackValuestart) {
		this.fackValuestart = fackValuestart;
	}

	public BigDecimal getFackValueend() {
		return fackValueend;
	}

	public void setFackValueend(BigDecimal fackValueend) {
		this.fackValueend = fackValueend;
	}


    public String getStartDateStr() {
		return startDateStr;
	}

	public void setStartDateStr(String startDateStr) {
		this.startDateStr = startDateStr;
	}

	public String getEndDateStr() {
		return endDateStr;
	}

	public void setEndDateStr(String endDateStr) {
		this.endDateStr = endDateStr;
	}

	/**
     * 
     * {@linkplain #number}
     *
     * @return the value of redpackage.number
     */
    public String getNumber() {
        return number;
    }

    /**
     * {@linkplain #number}
     * @param number the value for redpackage.number
     */
    public void setNumber(String number) {
        this.number = number == null ? null : number.trim();
    }

    /**
     * 
     * {@linkplain #name}
     *
     * @return the value of redpackage.name
     */
    public String getName() {
        return name;
    }

    /**
     * {@linkplain #name}
     * @param name the value for redpackage.name
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * 
     * {@linkplain #fackValue}
     *
     * @return the value of redpackage.fack_value
     */
    public BigDecimal getFackValue() {
        return fackValue;
    }

    /**
     * {@linkplain #fackValue}
     * @param fackValue the value for redpackage.fack_value
     */
    public void setFackValue(BigDecimal fackValue) {
        this.fackValue = fackValue;
    }

    /**
     * 
     * {@linkplain #receiveLimit}
     *
     * @return the value of redpackage.receive_limit
     */
    public Integer getReceiveLimit() {
        return receiveLimit;
    }

    /**
     * {@linkplain #receiveLimit}
     * @param receiveLimit the value for redpackage.receive_limit
     */
    public void setReceiveLimit(Integer receiveLimit) {
        this.receiveLimit = receiveLimit;
    }

    /**
     * 
     * {@linkplain #dateStart}
     *
     * @return the value of redpackage.date_start
     */
    public String getDateStart() {
        return dateStart;
    }

    /**
     * {@linkplain #dateStart}
     * @param dateStart the value for redpackage.date_start
     */
    public void setDateStart(String dateStart) {
        this.dateStart = dateStart == null ? null : dateStart.trim();
    }

    /**
     * 
     * {@linkplain #state}
     *
     * @return the value of redpackage.state
     */
    public String getState() {
        return state;
    }

    /**
     * {@linkplain #state}
     * @param state the value for redpackage.state
     */
    public void setState(String state) {
        this.state = state == null ? null : state.trim();
    }

    /**
     * 
     * {@linkplain #createName}
     *
     * @return the value of redpackage.create_name
     */
    public String getCreateName() {
        return createName;
    }

    /**
     * {@linkplain #createName}
     * @param createName the value for redpackage.create_name
     */
    public void setCreateName(String createName) {
        this.createName = createName == null ? null : createName.trim();
    }

    /**
     * 
     * {@linkplain #lastUpdateName}
     *
     * @return the value of redpackage.last_update_name
     */
    public String getLastUpdateName() {
        return lastUpdateName;
    }

    /**
     * {@linkplain #lastUpdateName}
     * @param lastUpdateName the value for redpackage.last_update_name
     */
    public void setLastUpdateName(String lastUpdateName) {
        this.lastUpdateName = lastUpdateName == null ? null : lastUpdateName.trim();
    }

    /**
     * 
     * {@linkplain #isGrant}
     *
     * @return the value of redpackage.is_grant
     */
    public String getIsGrant() {
        return isGrant;
    }

    /**
     * {@linkplain #isGrant}
     * @param isGrant the value for redpackage.is_grant
     */
    public void setIsGrant(String isGrant) {
        this.isGrant = isGrant == null ? null : isGrant.trim();
    }

    /**
     * 
     * {@linkplain #receivedate}
     *
     * @return the value of redpackage.receivedate
     */
    public Integer getReceivedate() {
        return receivedate;
    }

    /**
     * {@linkplain #receivedate}
     * @param receivedate the value for redpackage.receivedate
     */
    public void setReceivedate(Integer receivedate) {
        this.receivedate = receivedate;
    }

    /**
     * 
     * {@linkplain #startDate}
     *
     * @return the value of redpackage.start_date
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * {@linkplain #startDate}
     * @param startDate the value for redpackage.start_date
     */
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    /**
     * 
     * {@linkplain #endDate}
     *
     * @return the value of redpackage.end_date
     */
    public Date getEndDate() {
        return endDate;
    }

    /**
     * {@linkplain #endDate}
     * @param endDate the value for redpackage.end_date
     */
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    /**
     * 
     * {@linkplain #grantNum}
     *
     * @return the value of redpackage.grant_num
     */
    public Integer getGrantNum() {
        return grantNum;
    }

    /**
     * {@linkplain #grantNum}
     * @param grantNum the value for redpackage.grant_num
     */
    public void setGrantNum(Integer grantNum) {
        this.grantNum = grantNum;
    }
}