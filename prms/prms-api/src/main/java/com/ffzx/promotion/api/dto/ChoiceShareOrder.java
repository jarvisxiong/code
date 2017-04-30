package com.ffzx.promotion.api.dto;

import com.ffzx.commerce.framework.common.persistence.DataEntity;
import java.util.Date;

/**
 * 
 * @author ffzx
 * @date 2016-10-24 11:24:09
 * @version 1.0.0
 * @copyright www.ffzxnet.com
 */
public class ChoiceShareOrder extends DataEntity<ChoiceShareOrder> {

    private static final long serialVersionUID = 1L;

    /**
     * 操作人名字.
     */
    private String createName;

    /**
     * 操作时间.
     */
    private Date updateDate;

    /**
     * 精选晒单图片.
     */
    private String choiceImage;

    /**
     * 
     * {@linkplain #createName}
     *
     * @return the value of choice_share_order.create_name
     */
    public String getCreateName() {
        return createName;
    }

    /**
     * {@linkplain #createName}
     * @param createName the value for choice_share_order.create_name
     */
    public void setCreateName(String createName) {
        this.createName = createName == null ? null : createName.trim();
    }

    /**
     * 
     * {@linkplain #updateDate}
     *
     * @return the value of choice_share_order.update_date
     */
    public Date getUpdateDate() {
        return updateDate;
    }

    /**
     * {@linkplain #updateDate}
     * @param updateDate the value for choice_share_order.update_date
     */
    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    /**
     * 
     * {@linkplain #choiceImage}
     *
     * @return the value of choice_share_order.choice_image
     */
    public String getChoiceImage() {
        return choiceImage;
    }

    /**
     * {@linkplain #choiceImage}
     * @param choiceImage the value for choice_share_order.choice_image
     */
    public void setChoiceImage(String choiceImage) {
        this.choiceImage = choiceImage == null ? null : choiceImage.trim();
    }
}