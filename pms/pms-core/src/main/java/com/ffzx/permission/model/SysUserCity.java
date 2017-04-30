package com.ffzx.permission.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @author Generator
 * @date 2015-12-31 18:52:35
 * @version 1.0.0
 * @copyright facegarden.com
 */
public class SysUserCity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * .
     */
    private String id;

    /**
     * 用户编号.
     */
    private String userId;

    /**
     * .
     */
    private String cityId;

 
    /**
     * 
     * {@linkplain #id}
     *
     * @return the value of t_user_city.id
     */
    public String getId() {
        return id;
    }

    /**
     * {@linkplain #id}
     * @param id the value for t_user_city.id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 
     * {@linkplain #userId}
     *
     * @return the value of t_user_city.user_id
     */
    public String getUserId() {
        return userId;
    }

    /**
     * {@linkplain #userId}
     * @param userId the value for t_user_city.user_id
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * 
     * {@linkplain #cityId}
     *
     * @return the value of t_user_city.city_id
     */
    public String getCityId() {
        return cityId;
    }

    /**
     * {@linkplain #cityId}
     * @param cityId the value for t_user_city.city_id
     */
    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

   
}