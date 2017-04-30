package com.ffzx.permission.model;

import com.ffzx.commerce.framework.common.persistence.DataEntity;

/**
 * 
 * @author Generator
 * @date 2015-12-31 18:52:35
 * @version 1.0.0
 * @copyright facegarden.com
 */
public class SysCity extends DataEntity<SysCity> {

    private static final long serialVersionUID = 1L;

    /**
     * .
     */
    private String cityId;

    /**
     * .
     */
    private String cityName;

    /**
     * 标识该城市与哪个用户关联
     */
    private String userId;
    
	/**
     * 是否被选中
     */
    private boolean isChecked;
    
    public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
    public boolean isChecked() {
		return isChecked;
	}

	public void setChecked(boolean isChecked) {
		this.isChecked = isChecked;
	}

    
    /**
     * 
     * {@linkplain #cityId}
     *
     * @return the value of t_city.city_id
     */
    public String getCityId() {
        return cityId;
    }

    /**
     * {@linkplain #cityId}
     * @param cityId the value for t_city.city_id
     */
    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    /**
     * 
     * {@linkplain #cityName}
     *
     * @return the value of t_city.city_name
     */
    public String getCityName() {
        return cityName;
    }

    /**
     * {@linkplain #cityName}
     * @param cityName the value for t_city.city_name
     */
    public void setCityName(String cityName) {
        this.cityName = cityName == null ? null : cityName.trim();
    }
}