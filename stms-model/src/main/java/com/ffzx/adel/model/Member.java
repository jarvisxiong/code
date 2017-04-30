package com.ffzx.adel.model;

import com.ffzx.orm.common.BaseEntity;
import java.util.Date;
import javax.persistence.*;

@Table(name = "adel_member")
public class Member extends BaseEntity {
    private String name;

    private String phone;

    private String headimgurl;

    @Column(name = "nick_name")
    private String nickName;

    private String sex;

    private String mobile;

    private String weixin;

    private String qq;

    private String weibo;

    private String email;

    @Column(name = "delete_flag")
    private String deleteFlag;

    @Column(name = "wx_openid")
    private String wxOpenid;

    @Column(name = "wx_headimgurl")
    private String wxHeadimgurl;

    @Column(name = "wx_unionid")
    private String wxUnionid;

    @Column(name = "wx_nick_name")
    private String wxNickName;

    @Column(name = "wx_subscribe_time")
    private Date wxSubscribeTime;

    @Column(name = "wx_city")
    private String wxCity;

    @Column(name = "wx_country")
    private String wxCountry;

    @Column(name = "wx_province")
    private String wxProvince;

    @Column(name = "id_code")
    private String idCode;

    @Column(name = "id_type")
    private String idType;

    private String password;

    @Column(name = "obj_id_one")
    private String objIdOne;

    @Column(name = "obj_id_two")
    private String objIdTwo;

    /**
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * @return phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     * @param phone
     */
    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    /**
     * @return headimgurl
     */
    public String getHeadimgurl() {
        return headimgurl;
    }

    /**
     * @param headimgurl
     */
    public void setHeadimgurl(String headimgurl) {
        this.headimgurl = headimgurl == null ? null : headimgurl.trim();
    }

    /**
     * @return nick_name
     */
    public String getNickName() {
        return nickName;
    }

    /**
     * @param nickName
     */
    public void setNickName(String nickName) {
        this.nickName = nickName == null ? null : nickName.trim();
    }

    /**
     * @return sex
     */
    public String getSex() {
        return sex;
    }

    /**
     * @param sex
     */
    public void setSex(String sex) {
        this.sex = sex == null ? null : sex.trim();
    }

    /**
     * @return mobile
     */
    public String getMobile() {
        return mobile;
    }

    /**
     * @param mobile
     */
    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    /**
     * @return weixin
     */
    public String getWeixin() {
        return weixin;
    }

    /**
     * @param weixin
     */
    public void setWeixin(String weixin) {
        this.weixin = weixin == null ? null : weixin.trim();
    }

    /**
     * @return qq
     */
    public String getQq() {
        return qq;
    }

    /**
     * @param qq
     */
    public void setQq(String qq) {
        this.qq = qq == null ? null : qq.trim();
    }

    /**
     * @return weibo
     */
    public String getWeibo() {
        return weibo;
    }

    /**
     * @param weibo
     */
    public void setWeibo(String weibo) {
        this.weibo = weibo == null ? null : weibo.trim();
    }

    /**
     * @return email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email
     */
    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    /**
     * @return delete_flag
     */
    public String getDeleteFlag() {
        return deleteFlag;
    }

    /**
     * @param deleteFlag
     */
    public void setDeleteFlag(String deleteFlag) {
        this.deleteFlag = deleteFlag == null ? null : deleteFlag.trim();
    }

    /**
     * @return wx_openid
     */
    public String getWxOpenid() {
        return wxOpenid;
    }

    /**
     * @param wxOpenid
     */
    public void setWxOpenid(String wxOpenid) {
        this.wxOpenid = wxOpenid == null ? null : wxOpenid.trim();
    }

    /**
     * @return wx_headimgurl
     */
    public String getWxHeadimgurl() {
        return wxHeadimgurl;
    }

    /**
     * @param wxHeadimgurl
     */
    public void setWxHeadimgurl(String wxHeadimgurl) {
        this.wxHeadimgurl = wxHeadimgurl == null ? null : wxHeadimgurl.trim();
    }

    /**
     * @return wx_unionid
     */
    public String getWxUnionid() {
        return wxUnionid;
    }

    /**
     * @param wxUnionid
     */
    public void setWxUnionid(String wxUnionid) {
        this.wxUnionid = wxUnionid == null ? null : wxUnionid.trim();
    }

    /**
     * @return wx_nick_name
     */
    public String getWxNickName() {
        return wxNickName;
    }

    /**
     * @param wxNickName
     */
    public void setWxNickName(String wxNickName) {
        this.wxNickName = wxNickName == null ? null : wxNickName.trim();
    }

    /**
     * @return wx_subscribe_time
     */
    public Date getWxSubscribeTime() {
        return wxSubscribeTime;
    }

    /**
     * @param wxSubscribeTime
     */
    public void setWxSubscribeTime(Date wxSubscribeTime) {
        this.wxSubscribeTime = wxSubscribeTime;
    }

    /**
     * @return wx_city
     */
    public String getWxCity() {
        return wxCity;
    }

    /**
     * @param wxCity
     */
    public void setWxCity(String wxCity) {
        this.wxCity = wxCity == null ? null : wxCity.trim();
    }

    /**
     * @return wx_country
     */
    public String getWxCountry() {
        return wxCountry;
    }

    /**
     * @param wxCountry
     */
    public void setWxCountry(String wxCountry) {
        this.wxCountry = wxCountry == null ? null : wxCountry.trim();
    }

    /**
     * @return wx_province
     */
    public String getWxProvince() {
        return wxProvince;
    }

    /**
     * @param wxProvince
     */
    public void setWxProvince(String wxProvince) {
        this.wxProvince = wxProvince == null ? null : wxProvince.trim();
    }

    /**
     * @return id_code
     */
    public String getIdCode() {
        return idCode;
    }

    /**
     * @param idCode
     */
    public void setIdCode(String idCode) {
        this.idCode = idCode == null ? null : idCode.trim();
    }

    /**
     * @return id_type
     */
    public String getIdType() {
        return idType;
    }

    /**
     * @param idType
     */
    public void setIdType(String idType) {
        this.idType = idType == null ? null : idType.trim();
    }

    /**
     * @return password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password
     */
    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    /**
     * @return obj_id_one
     */
    public String getObjIdOne() {
        return objIdOne;
    }

    /**
     * @param objIdOne
     */
    public void setObjIdOne(String objIdOne) {
        this.objIdOne = objIdOne == null ? null : objIdOne.trim();
    }

    /**
     * @return obj_id_two
     */
    public String getObjIdTwo() {
        return objIdTwo;
    }

    /**
     * @param objIdTwo
     */
    public void setObjIdTwo(String objIdTwo) {
        this.objIdTwo = objIdTwo == null ? null : objIdTwo.trim();
    }
}