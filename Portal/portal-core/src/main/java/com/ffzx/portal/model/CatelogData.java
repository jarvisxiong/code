package com.ffzx.portal.model;

import java.util.Date;

import com.ffzx.commerce.framework.common.persistence.DataEntity;


/***
 * 栏目数据
 * 
 * @author ying.cai
 * @date 2017年4月12日上午11:18:35
 * @email ying.cai@ffzxnet.com
 * @version V1.0
 */
public class CatelogData extends DataEntity<CatelogData>{
	
	private static final long serialVersionUID = 1665793272477373039L;
	//所属栏目
	private Catelog catelog;
	//栏目数据id
	private String id;
	//是否发布 0 否 1 是
	private int isLaunch; 
	//是否置顶 0 否 1是
	private int isTop ; 
	//失效日期
	private Date loseTime;
	//标题
	private String title ;
	//内容
	private String content;
	//链接1
	private String url1;
	//链接2
	private String url2;
	//发布日期
	private Date createTime ; 
	//关键字
	private String key ;
	//图片信息
	private String imageUrl;
	
	//冗余
	private String catelogId;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public int getIsLaunch() {
		return isLaunch;
	}
	public void setIsLaunch(int isLaunch) {
		this.isLaunch = isLaunch;
	}
	public int getIsTop() {
		return isTop;
	}
	public void setIsTop(int isTop) {
		this.isTop = isTop;
	}
	public Date getLoseTime() {
		return loseTime;
	}
	
	public void setLoseTime(Date loseTime) {
		this.loseTime = loseTime;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	
	public Catelog getCatelog() {
		return catelog;
	}
	public void setCatelog(Catelog catelog) {
		this.catelog = catelog;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getUrl1() {
		return url1;
	}
	public void setUrl1(String url1) {
		this.url1 = url1;
	}
	public String getUrl2() {
		return url2;
	}
	public void setUrl2(String url2) {
		this.url2 = url2;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	/**
	 * @return the catelogId
	 */
	public String getCatelogId() {
		return catelogId;
	}
	/**
	 * @param catelogId the catelogId to set
	 */
	public void setCatelogId(String catelogId) {
		this.catelogId = catelogId;
	}
	
}
