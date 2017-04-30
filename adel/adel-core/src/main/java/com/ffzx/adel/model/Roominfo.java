package com.ffzx.adel.model;

import com.ffzx.orm.common.MsBaseEntity;
import javax.persistence.*;

@Table(name = "ROOMINFO")
public class Roominfo extends MsBaseEntity {
    @Id
    @Column(name = "ROOMNO")
    private String roomno;

    @Column(name = "INDEXNO")
    private Integer indexno;

    @Column(name = "ID")
    private Short id;

    @Column(name = "LDQY")
    private String ldqy;

    @Column(name = "ROOMNAME")
    private String roomname;

    @Column(name = "ROOMTYPE")
    private String roomtype;

    @Column(name = "BEDNUM")
    private Short bednum;

    @Column(name = "INNUM")
    private Short innum;

    @Column(name = "RECNUM")
    private Short recnum;

    @Column(name = "LCQY")
    private String lcqy;

    @Column(name = "LBQY")
    private String lbqy;

    @Column(name = "QJQY")
    private String qjqy;

    @Column(name = "PARENT")
    private String parent;

    @Column(name = "LOCKFLAG")
    private Boolean lockflag;

    @Column(name = "STATUS")
    private Short status;

    @Column(name = "RECSTATUS")
    private Short recstatus;

    @Column(name = "LOCKTYPE")
    private Short locktype;

    @Column(name = "PMSNO")
    private String pmsno;

    @Column(name = "GATEWAYID")
    private Integer gatewayid;

    @Column(name = "ADDRESS")
    private String address;

    @Column(name = "CHANNEL1")
    private Integer channel1;

    @Column(name = "CHANNEL2")
    private Integer channel2;

    @Column(name = "NETLOCKFLAG")
    private Integer netlockflag;

    /**
     * @return ROOMNO
     */
    public String getRoomno() {
        return roomno;
    }

    /**
     * @param roomno
     */
    public void setRoomno(String roomno) {
        this.roomno = roomno == null ? null : roomno.trim();
    }

    /**
     * @return INDEXNO
     */
    public Integer getIndexno() {
        return indexno;
    }

    /**
     * @param indexno
     */
    public void setIndexno(Integer indexno) {
        this.indexno = indexno;
    }

    /**
     * @return ID
     */
    public Short getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Short id) {
        this.id = id;
    }

    /**
     * @return LDQY
     */
    public String getLdqy() {
        return ldqy;
    }

    /**
     * @param ldqy
     */
    public void setLdqy(String ldqy) {
        this.ldqy = ldqy == null ? null : ldqy.trim();
    }

    /**
     * @return ROOMNAME
     */
    public String getRoomname() {
        return roomname;
    }

    /**
     * @param roomname
     */
    public void setRoomname(String roomname) {
        this.roomname = roomname == null ? null : roomname.trim();
    }

    /**
     * @return ROOMTYPE
     */
    public String getRoomtype() {
        return roomtype;
    }

    /**
     * @param roomtype
     */
    public void setRoomtype(String roomtype) {
        this.roomtype = roomtype == null ? null : roomtype.trim();
    }

    /**
     * @return BEDNUM
     */
    public Short getBednum() {
        return bednum;
    }

    /**
     * @param bednum
     */
    public void setBednum(Short bednum) {
        this.bednum = bednum;
    }

    /**
     * @return INNUM
     */
    public Short getInnum() {
        return innum;
    }

    /**
     * @param innum
     */
    public void setInnum(Short innum) {
        this.innum = innum;
    }

    /**
     * @return RECNUM
     */
    public Short getRecnum() {
        return recnum;
    }

    /**
     * @param recnum
     */
    public void setRecnum(Short recnum) {
        this.recnum = recnum;
    }

    /**
     * @return LCQY
     */
    public String getLcqy() {
        return lcqy;
    }

    /**
     * @param lcqy
     */
    public void setLcqy(String lcqy) {
        this.lcqy = lcqy == null ? null : lcqy.trim();
    }

    /**
     * @return LBQY
     */
    public String getLbqy() {
        return lbqy;
    }

    /**
     * @param lbqy
     */
    public void setLbqy(String lbqy) {
        this.lbqy = lbqy == null ? null : lbqy.trim();
    }

    /**
     * @return QJQY
     */
    public String getQjqy() {
        return qjqy;
    }

    /**
     * @param qjqy
     */
    public void setQjqy(String qjqy) {
        this.qjqy = qjqy == null ? null : qjqy.trim();
    }

    /**
     * @return PARENT
     */
    public String getParent() {
        return parent;
    }

    /**
     * @param parent
     */
    public void setParent(String parent) {
        this.parent = parent == null ? null : parent.trim();
    }

    /**
     * @return LOCKFLAG
     */
    public Boolean getLockflag() {
        return lockflag;
    }

    /**
     * @param lockflag
     */
    public void setLockflag(Boolean lockflag) {
        this.lockflag = lockflag;
    }

    /**
     * @return STATUS
     */
    public Short getStatus() {
        return status;
    }

    /**
     * @param status
     */
    public void setStatus(Short status) {
        this.status = status;
    }

    /**
     * @return RECSTATUS
     */
    public Short getRecstatus() {
        return recstatus;
    }

    /**
     * @param recstatus
     */
    public void setRecstatus(Short recstatus) {
        this.recstatus = recstatus;
    }

    /**
     * @return LOCKTYPE
     */
    public Short getLocktype() {
        return locktype;
    }

    /**
     * @param locktype
     */
    public void setLocktype(Short locktype) {
        this.locktype = locktype;
    }

    /**
     * @return PMSNO
     */
    public String getPmsno() {
        return pmsno;
    }

    /**
     * @param pmsno
     */
    public void setPmsno(String pmsno) {
        this.pmsno = pmsno == null ? null : pmsno.trim();
    }

    /**
     * @return GATEWAYID
     */
    public Integer getGatewayid() {
        return gatewayid;
    }

    /**
     * @param gatewayid
     */
    public void setGatewayid(Integer gatewayid) {
        this.gatewayid = gatewayid;
    }

    /**
     * @return ADDRESS
     */
    public String getAddress() {
        return address;
    }

    /**
     * @param address
     */
    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    /**
     * @return CHANNEL1
     */
    public Integer getChannel1() {
        return channel1;
    }

    /**
     * @param channel1
     */
    public void setChannel1(Integer channel1) {
        this.channel1 = channel1;
    }

    /**
     * @return CHANNEL2
     */
    public Integer getChannel2() {
        return channel2;
    }

    /**
     * @param channel2
     */
    public void setChannel2(Integer channel2) {
        this.channel2 = channel2;
    }

    /**
     * @return NETLOCKFLAG
     */
    public Integer getNetlockflag() {
        return netlockflag;
    }

    /**
     * @param netlockflag
     */
    public void setNetlockflag(Integer netlockflag) {
        this.netlockflag = netlockflag;
    }
}