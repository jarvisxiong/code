package com.ffzx.promotion.api.dto;

import java.util.Date;

import com.ffzx.commerce.framework.annotation.Comment;
import com.ffzx.commerce.framework.common.persistence.DataEntity;
import com.ffzx.promotion.api.enums.AdvertStatus;

/**
 * @Description: 广告 实体
 * @author qh.xu
 * @email  qianghui.xu@ffzxnet.com
 * @date 2016年4月29日 上午10:03:47
 * @version V1.0 
 *
 */
public class Advert extends DataEntity<Advert>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3324051402817236071L;
	@Comment("广告名称/标题")
	private String title;
	
	@Comment("广告所属区域")
	private AdvertRegion region;
	
	@Comment("开始时间")
	private Date startDate;
	
	@Comment("结束时间")
	private Date endDate;
	
	@Comment("是否是系统数据：1：是，0否")
	private Integer isSystem;
	
	@Comment("是否是替补广告：1：是，0否")
	private Integer isBackup;
	
	@Comment("位置序号")
    private Integer locationIndex;
	
	@Comment("广告链接地址")
	private String url; 
	
	@Comment("广告外键")
	private String objId;
	
	@Comment("关联数据名称")
	private String objName;
	
	@Comment("活动外键")
	private String activityId;
	
	@Comment("广告类型")
	private String advertType;
	
	@Comment("广告图片路径1")
    private String photoPath;
	
	@Comment("广告图片路径2") 
    private String photoPath2;
	
	@Comment("广告发布状态") 
    private AdvertStatus status; 
	
	private String advertTypeName;
	
	private String statusName;

	
	public String getAdvertTypeName() {
		return advertTypeName;
	}

	public String getStatusName() {
		return statusName;
	}
	@Comment("发布人名称") 
    private String  createByName;
	
	public String getCreateByName() {
		return createByName;
	}

	public void setCreateByName(String createByName) {
		this.createByName = createByName;
	}

	public void setAdvertTypeName(String advertTypeName) {
		this.advertTypeName = advertTypeName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public AdvertRegion getRegion() {
		return region;
	}

	public void setRegion(AdvertRegion region) {
		this.region = region;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Integer getIsSystem() {
		return isSystem;
	}

	public void setIsSystem(Integer isSystem) {
		this.isSystem = isSystem;
	}

	public Integer getIsBackup() {
		return isBackup;
	}

	public void setIsBackup(Integer isBackup) {
		this.isBackup = isBackup;
	}

	public Integer getLocationIndex() {
		return locationIndex;
	}

	public void setLocationIndex(Integer locationIndex) {
		this.locationIndex = locationIndex;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getObjId() {
		return objId;
	}

	public void setObjId(String objId) {
		this.objId = objId;
	}

	public String getAdvertType() {
		return advertType;
	}

	public void setAdvertType(String advertType) {
		this.advertType = advertType;
	}

	public String getPhotoPath() {
		return photoPath;
	}

	public void setPhotoPath(String photoPath) {
		this.photoPath = photoPath;
	}

	public String getPhotoPath2() {
		return photoPath2;
	}

	public void setPhotoPath2(String photoPath2) {
		this.photoPath2 = photoPath2;
	}

	public AdvertStatus getStatus() {
		return status;
	}

	public void setStatus(AdvertStatus status) {
		this.status = status;
	}

	public String getObjName() {
		return objName;
	}

	public void setObjName(String objName) {
		this.objName = objName;
	}

	public String getActivityId() {
		return activityId;
	}

	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}
	
	
}
