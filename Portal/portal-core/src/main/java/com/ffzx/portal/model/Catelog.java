package com.ffzx.portal.model;

import java.util.List;

import com.ffzx.commerce.framework.common.persistence.TreeEntity;
import com.ffzx.commerce.framework.utils.Reflections;
import com.ffzx.commerce.framework.utils.StringUtil;
import com.ffzx.portal.enums.CatelogDataType;
import com.ffzx.portal.enums.CatelogSortType;

/***
 * 栏目
 * @author ying.cai
 * @date 2017年4月12日上午11:16:00
 * @email ying.cai@ffzxnet.com
 * @version V1.0
 */
public class Catelog extends TreeEntity<Catelog>{
	private static final long serialVersionUID = 8783647157650424439L;

	//所属页签
	private PageTab pageTab ;
	//数据类型
	private CatelogDataType catelogDataType;
	//排序方式
	private CatelogSortType catelogSortType;
	//图片地址
	private String imageUrl;
	//是否启用 0否 1是
	private int isEnable;
	//链接URL
	private String url;
	//冗余字段
	private CatelogData catelogData;
	//编号
	private String number;
	
	private String enName;
	
	private String jsonStr;

	/**临时字段*/
	//内容集合
	private List<CatelogData> catelogDataList ;
	
	private String nameOrNumber;
	
	public List<CatelogData> getCatelogDataList() {
		return catelogDataList;
	}
	public void setCatelogDataList(List<CatelogData> catelogDataList) {
		this.catelogDataList = catelogDataList;
	}
	public PageTab getPageTab() {
		return pageTab;
	}
	public void setPageTab(PageTab pageTab) {
		this.pageTab = pageTab;
	}
	public CatelogDataType getCatelogDataType() {
		return catelogDataType;
	}
	public void setCatelogDataType(CatelogDataType catelogDataType) {
		this.catelogDataType = catelogDataType;
	}
	public CatelogSortType getCatelogSortType() {
		return catelogSortType;
	}
	public void setCatelogSortType(CatelogSortType catelogSortType) {
		this.catelogSortType = catelogSortType;
	}
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public int getIsEnable() {
		return isEnable;
	}
	public void setIsEnable(int isEnable) {
		this.isEnable = isEnable;
	}
	
	public String getCatelogDataTypeName(){
		if(this.catelogDataType==null){
			return "";
		}
		return this.catelogDataType.getLabel();
	}
	public String getCatelogSortTypeName(){
		if(this.catelogSortType==null){
			return "";
		}
		return this.catelogSortType.getLabel();
	}
	public CatelogData getCatelogData() {
		return catelogData;
	}
	public void setCatelogData(CatelogData catelogData) {
		this.catelogData = catelogData;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	/***
	 * 
	 * @return
	 * @author yinglong.huang
	 * @email yinglong.huang@ffzxnet.com
	 * @date 2017年4月13日 上午11:35:19
	 * @version V1.0
	 * @return 
	 */
	@Override
	public Catelog getParent() {
		// TODO Auto-generated method stub
		return this.parent;
	}
	/***
	 * 
	 * @param parent
	 * @author yinglong.huang
	 * @email yinglong.huang@ffzxnet.com
	 * @date 2017年4月13日 上午11:35:19
	 * @version V1.0
	 * @return 
	 */
	@Override
	public void setParent(Catelog parent) {
		// TODO Auto-generated method stub
		this.parent = parent;
	}
	
	public String getPId() {
		String id = null;
		if (parent != null){
			id = (String)Reflections.getFieldValue(parent, "id");
		}
		return StringUtil.isNotBlank(id) ? id : "0";
	}
	/**
	 * @return the enName
	 */
	public String getEnName() {
		return enName;
	}
	/**
	 * @param enName the enName to set
	 */
	public void setEnName(String enName) {
		this.enName = enName;
	}
	/**
	 * @return the jsonStr
	 */
	public String getJsonStr() {
		return jsonStr;
	}
	/**
	 * @param jsonStr the jsonStr to set
	 */
	public void setJsonStr(String jsonStr) {
		this.jsonStr = jsonStr;
	}
	/**
	 * @return the nameOrNumber
	 */
	public String getNameOrNumber() {
		return nameOrNumber;
	}
	/**
	 * @param nameOrNumber the nameOrNumber to set
	 */
	public void setNameOrNumber(String nameOrNumber) {
		this.nameOrNumber = nameOrNumber;
	}
	
	
}
