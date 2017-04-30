package com.ffzx.order.model;

import com.ffzx.commerce.framework.common.persistence.DataEntity;
import java.util.Date;

/**
 * 
 * @author ffzx
 * @date 2016-06-08 17:29:40
 * @version 1.0.0
 * @copyright www.ffzxnet.com
 */
public class PurchasePush extends DataEntity<PurchasePush> {

    private static final long serialVersionUID = 1L;

    /**
     * 推送时间.
     */
    private Date pushDate;

    /**
     * 
     * {@linkplain #pushDate}
     *
     * @return the value of purchase_push.push_date
     */
    public Date getPushDate() {
        return pushDate;
    }

    /**
     * {@linkplain #pushDate}
     * @param pushDate the value for purchase_push.push_date
     */
    public void setPushDate(Date pushDate) {
        this.pushDate = pushDate;
    }
}