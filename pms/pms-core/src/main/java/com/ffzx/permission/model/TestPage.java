package com.ffzx.permission.model;

import com.ffzx.commerce.framework.annotation.ColumnPermission;
import com.ffzx.commerce.framework.common.persistence.DataEntity;
import com.ffzx.commerce.framework.utils.StringUtil;
import org.hibernate.validator.constraints.Email;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.Date;

/**
 * @author Generator
 * @version 1.0.0
 * @date 2016-02-22 11:39:07
 * @copyright facegarden.com
 */

@ColumnPermission(value = "pms_testpage_column")
public class TestPage extends DataEntity<TestPage> {

    private static final long serialVersionUID = 1L;

    /**
     * 名称.
     */
    //@NotNull
    private String name;

    /**
     * 年龄.
     */
    //@NotNull
    @Min(value = 1)
    @Max(value = 100)
    private Integer age;

    /**
     * 邮箱.
     */
    @Email
    private String email;

    /* 用于搜索属性 */
    private String beginCreateDateStr;//开始创建时间
    private String endCreateDateStr;//结束创建时间

    private String beginLastUpdateDateStr;//开始最后修改日期
    private String endLastUpdateDateStr;//结束最后修改日期

    private String beginAgeStr;//开始年龄
    private String endAgeStr;//结束年龄

    /**
     * {@linkplain #name}
     *
     * @return the value of test_page.name
     */
    public String getName() {
        return name;
    }

    /**
     * {@linkplain #name}
     *
     * @param name the value for test_page.name
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * {@linkplain #age}
     *
     * @return the value of test_page.age
     */
    public Integer getAge() {
        return age;
    }

    /**
     * {@linkplain #age}
     *
     * @param age the value for test_page.age
     */
    public void setAge(Integer age) {
        this.age = age;
    }

    /**
     * {@linkplain #email}
     *
     * @return the value of test_page.email
     */
    public String getEmail() {
        return email;
    }

    /**
     * {@linkplain #email}
     *
     * @param email the value for test_page.email
     */
    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    /**
     * @return the beginCreatedDateStr
     */
    public String getBeginCreateDateStr() {
        return beginCreateDateStr;
    }

    /**
     * @param beginCreatedDateStr the beginCreatedDateStr to set
     */
    public void setBeginCreateDateStr(String beginCreateDateStr) {
        this.beginCreateDateStr = beginCreateDateStr;
    }

    /**
     * @return the endCreatedDateStr
     */
    public String getEndCreateDateStr() {
        return endCreateDateStr;
    }

    /**
     * @param endCreatedDateStr the endCreatedDateStr to set
     */
    public void setEndCreateDateStr(String endCreateDateStr) {
        this.endCreateDateStr = endCreateDateStr;
    }

    /**
     * @return the beginAgeStr
     */
    public String getBeginAgeStr() {
        return beginAgeStr;
    }

    /**
     * @param beginAgeStr the beginAgeStr to set
     */
    public void setBeginAgeStr(String beginAgeStr) {
        this.beginAgeStr = beginAgeStr;
    }

    /**
     * @return the endAgeStr
     */
    public String getEndAgeStr() {
        return endAgeStr;
    }

    /**
     * @param endAgeStr the endAgeStr to set
     */
    public void setEndAgeStr(String endAgeStr) {
        this.endAgeStr = endAgeStr;
    }

    /**
     * @return the beginCreatedDateStr
     */
    public Date getBeginCreateDate() {
        Date tempDate = null;
        if (StringUtil.isStrTrimNotEmpty(beginCreateDateStr)) {
            try {
                //tempDate = DateUtil.parseDate(beginCreateDateStr);
            } catch (Exception e) {
            }
        }
        return tempDate;
    }

    /**
     * @return the endCreatedDateStr
     */
    public Date getEndCreateDate() {
        Date tempDate = null;
        if (StringUtil.isStrTrimNotEmpty(endCreateDateStr)) {
            try {
                //tempDate = DateUtil.parseDate(endCreateDateStr);
            } catch (Exception e) {
            }
        }
        return tempDate;
    }

    /**
     * @return the beginLastUpdatedDate
     */
    public Date getBeginLastUpdateDate() {
        Date tempDate = null;
        if (StringUtil.isStrTrimNotEmpty(beginLastUpdateDateStr)) {
            try {
                //tempDate = DateUtil.parseDate(beginLastUpdateDateStr);
            } catch (Exception e) {
            }
        }
        return tempDate;
    }

    /**
     * @return the endLastUpdatedDate
     */
    public Date getEndLastUpdateDate() {
        Date tempDate = null;
        if (StringUtil.isStrTrimNotEmpty(endLastUpdateDateStr)) {
            try {
               // tempDate = DateUtil.parseDate(endLastUpdateDateStr);
            } catch (Exception e) {
            }
        }
        return tempDate;
    }

    /**
     * @return the beginAgeStr
     */
    public Integer getBeginAge() {
        Integer tempInt = null;
        if (StringUtil.isStrTrimNotEmpty(beginAgeStr)) {
            try {
                tempInt = Integer.parseInt(beginAgeStr);
            } catch (Exception e) {
            }
        }
        return tempInt;
    }

    /**
     * @return the endAgeStr
     */
    public Integer getEndAge() {
        Integer tempInt = null;
        if (StringUtil.isStrTrimNotEmpty(endAgeStr)) {
            try {
                tempInt = Integer.parseInt(endAgeStr);
            } catch (Exception e) {
            }
        }
        return tempInt;
    }

    public String getBeginLastUpdateDateStr() {
        return beginLastUpdateDateStr;
    }

    public void setBeginLastUpdateDateStr(String beginLastUpdateDateStr) {
        this.beginLastUpdateDateStr = beginLastUpdateDateStr;
    }

    public String getEndLastUpdateDateStr() {
        return endLastUpdateDateStr;
    }

    public void setEndLastUpdateDateStr(String endLastUpdateDateStr) {
        this.endLastUpdateDateStr = endLastUpdateDateStr;
    }

    @Override
    public String toString() {
        return "TestPage [name=" + name + ", age=" + age + ", email=" + email
                + ", beginCreateDateStr=" + beginCreateDateStr
                + ", endCreateDateStr=" + endCreateDateStr
                + ", beginLastUpdateDateStr=" + beginLastUpdateDateStr
                + ", endLastUpdateDateStr=" + endLastUpdateDateStr
                + ", beginAgeStr=" + beginAgeStr + ", endAgeStr=" + endAgeStr
                + "]";
    }

}