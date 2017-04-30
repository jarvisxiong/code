package com.ffzx.promotion.model;

import com.ffzx.commerce.framework.common.persistence.DataEntity;

/**
 * 
 * @author ffzx
 * @date 2016-08-17 17:55:51
 * @version 1.0.0
 * @copyright www.ffzxnet.com
 */
public class PreSaleTag extends DataEntity<PreSaleTag> {

    private static final long serialVersionUID = 1L;

    /**
     * 序号.
     */
    private Integer number;

    /**
     * 标签名称.
     */
    private String name;
    
    private String presaleTagId;
    
    

    public String getPresaleTagId() {
		return presaleTagId;
	}

	public void setPresaleTagId(String presaleTagId) {
		this.presaleTagId = presaleTagId;
	}

	/**
     * 
     * {@linkplain #number}
     *
     * @return the value of activity_pre_sale_tag.number
     */
    public Integer getNumber() {
        return number;
    }

    /**
     * {@linkplain #number}
     * @param number the value for activity_pre_sale_tag.number
     */
    public void setNumber(Integer number) {
        this.number = number;
    }

    /**
     * 
     * {@linkplain #name}
     *
     * @return the value of activity_pre_sale_tag.name
     */
    public String getName() {
        return name;
    }

    /**
     * {@linkplain #name}
     * @param name the value for activity_pre_sale_tag.name
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }
}