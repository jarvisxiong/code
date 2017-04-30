package com.ffzx.promotion.api.dto;

import com.ffzx.commerce.framework.common.persistence.DataEntity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 
 * @author ffzx
 * @date 2016-11-08 10:28:31
 * @version 1.0.0
 * @copyright www.ffzxnet.com
 */
public class RedpackageGrant extends DataEntity<RedpackageGrant> {

    private static final long serialVersionUID = 1L;

    /**
     * 红包发放编码.
     */
    private String number;

    /**
     * 发放方式(1消息 2天降）.
     */
    private String grantMode;

    /**
     * 发放开始时间.
     */
    private Date startDate;

    /**
     * 领取截止时间.
     */
    private Date endDate;

    /**
     * 状态（1启用 0禁用）.
     */
    private String state;

    /**
     * 创建名称.
     */
    private String createName;

    /**
     * 更新者名称.
     */
    private String lastUpdateName;

    /**
     * 红包策略id.
     */
    private String redpackageId;

    /**
     * 红包发放名称.
     */
    private String name;
    /**
     * 是否发放（1发放，0没发放） : 是否发放.
     */
    private String isGrant;
    //冗余字段
    private Redpackage redpackage;
    private String userlableList;
    private String startDateStartStr;//开始开始时间
    private String startDateEndStr;//开始结束时间
    private String endDateStartStr;//结束开始时间
    private String endDateEndStr;//结束结束时间
    private BigDecimal fackValuestart;//红包面值
    private BigDecimal fackValueend;//红包面值
    private String  lastUpdateDateStartStr;//最后开始时间
    private String  lastUpdateDateEndtStr;//最后结束时间
	private List<RedpackageHandle> redpackageHandles;//处理信息
	private List<UserLable> userLables;//用户标签
	private String isReceive;//是否领取（1领取，0未领取）
    private Integer receiveCount;//已放方数量
    



	public Integer getReceiveCount() {
		return receiveCount;
	}

	public void setReceiveCount(Integer receiveCount) {
		this.receiveCount = receiveCount;
	}

	public String getIsReceive() {
		return isReceive;
	}

	public void setIsReceive(String isReceive) {
		this.isReceive = isReceive;
	}

	public List<RedpackageHandle> getRedpackageHandles() {
		return redpackageHandles;
	}

	public void setRedpackageHandles(List<RedpackageHandle> redpackageHandles) {
		this.redpackageHandles = redpackageHandles;
	}

	public List<UserLable> getUserLables() {
		return userLables;
	}

	public void setUserLables(List<UserLable> userLables) {
		this.userLables = userLables;
	}

	public String getUserlableList() {
		return userlableList;
	}

	public void setUserlableList(String userlableList) {
		this.userlableList = userlableList;
	}

	public String getStartDateStartStr() {
		return startDateStartStr;
	}

	public void setStartDateStartStr(String startDateStartStr) {
		this.startDateStartStr = startDateStartStr;
	}

	public String getStartDateEndStr() {
		return startDateEndStr;
	}

	public void setStartDateEndStr(String startDateEndStr) {
		this.startDateEndStr = startDateEndStr;
	}

	public String getEndDateStartStr() {
		return endDateStartStr;
	}

	public void setEndDateStartStr(String endDateStartStr) {
		this.endDateStartStr = endDateStartStr;
	}

	public String getEndDateEndStr() {
		return endDateEndStr;
	}

	public void setEndDateEndStr(String endDateEndStr) {
		this.endDateEndStr = endDateEndStr;
	}

	public BigDecimal getFackValuestart() {
		return fackValuestart;
	}

	public void setFackValuestart(BigDecimal fackValuestart) {
		this.fackValuestart = fackValuestart;
	}

	public String getIsGrant() {
		return isGrant;
	}

	public void setIsGrant(String isGrant) {
		this.isGrant = isGrant;
	}

	public BigDecimal getFackValueend() {
		return fackValueend;
	}

	public void setFackValueend(BigDecimal fackValueend) {
		this.fackValueend = fackValueend;
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

	public Redpackage getRedpackage() {
		return redpackage;
	}

	public void setRedpackage(Redpackage redpackage) {
		this.redpackage = redpackage;
	}

	/**
     * 
     * {@linkplain #number}
     *
     * @return the value of redpackage_grant.number
     */
    public String getNumber() {
        return number;
    }

    /**
     * {@linkplain #number}
     * @param number the value for redpackage_grant.number
     */
    public void setNumber(String number) {
        this.number = number == null ? null : number.trim();
    }

    /**
     * 
     * {@linkplain #grantMode}
     *
     * @return the value of redpackage_grant.grant_mode
     */
    public String getGrantMode() {
        return grantMode;
    }

    /**
     * {@linkplain #grantMode}
     * @param grantMode the value for redpackage_grant.grant_mode
     */
    public void setGrantMode(String grantMode) {
        this.grantMode = grantMode == null ? null : grantMode.trim();
    }

    /**
     * 
     * {@linkplain #startDate}
     *
     * @return the value of redpackage_grant.start_date
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * {@linkplain #startDate}
     * @param startDate the value for redpackage_grant.start_date
     */
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    /**
     * 
     * {@linkplain #endDate}
     *
     * @return the value of redpackage_grant.end_date
     */
    public Date getEndDate() {
        return endDate;
    }

    /**
     * {@linkplain #endDate}
     * @param endDate the value for redpackage_grant.end_date
     */
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    /**
     * 
     * {@linkplain #state}
     *
     * @return the value of redpackage_grant.state
     */
    public String getState() {
        return state;
    }

    /**
     * {@linkplain #state}
     * @param state the value for redpackage_grant.state
     */
    public void setState(String state) {
        this.state = state == null ? null : state.trim();
    }

    /**
     * 
     * {@linkplain #createName}
     *
     * @return the value of redpackage_grant.create_name
     */
    public String getCreateName() {
        return createName;
    }

    /**
     * {@linkplain #createName}
     * @param createName the value for redpackage_grant.create_name
     */
    public void setCreateName(String createName) {
        this.createName = createName == null ? null : createName.trim();
    }

    /**
     * 
     * {@linkplain #lastUpdateName}
     *
     * @return the value of redpackage_grant.last_update_name
     */
    public String getLastUpdateName() {
        return lastUpdateName;
    }

    /**
     * {@linkplain #lastUpdateName}
     * @param lastUpdateName the value for redpackage_grant.last_update_name
     */
    public void setLastUpdateName(String lastUpdateName) {
        this.lastUpdateName = lastUpdateName == null ? null : lastUpdateName.trim();
    }

    /**
     * 
     * {@linkplain #redpackageId}
     *
     * @return the value of redpackage_grant.redpackage_id
     */
    public String getRedpackageId() {
        return redpackageId;
    }

    /**
     * {@linkplain #redpackageId}
     * @param redpackageId the value for redpackage_grant.redpackage_id
     */
    public void setRedpackageId(String redpackageId) {
        this.redpackageId = redpackageId == null ? null : redpackageId.trim();
    }

    /**
     * 
     * {@linkplain #name}
     *
     * @return the value of redpackage_grant.name
     */
    public String getName() {
        return name;
    }

    /**
     * {@linkplain #name}
     * @param name the value for redpackage_grant.name
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }
}