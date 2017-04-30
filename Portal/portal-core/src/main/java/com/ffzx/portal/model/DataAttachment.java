package com.ffzx.portal.model;

import java.util.Date;

import com.ffzx.commerce.framework.common.persistence.DataEntity;
import com.ffzx.portal.enums.FileType;

/***
 * 数据附件
 * 
 * @author ying.cai
 * @date 2017年4月12日上午11:20:19
 * @email ying.cai@ffzxnet.com
 * @version V1.0
 */
public class DataAttachment extends DataEntity<DataAttachment>{
	
	private static final long serialVersionUID = -121634217440046973L;
	public static final String MAPPER ="com.ffzx.website.dao.DataAttachmentDao";
	
	//名字
	private String name ; 
	//路径
	private String path ; 
	//文件后缀
	private String fileSuffix;
	//文件类型
	private FileType fileType;
	//上传时间
	private Date createTime ; 
	//关联数据
	private String object;
	//顺序
	private int sort;
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getFileSuffix() {
		return fileSuffix;
	}
	public void setFileSuffix(String fileSuffix) {
		this.fileSuffix = fileSuffix;
	}
	public FileType getFileType() {
		return fileType;
	}
	public void setFileType(FileType fileType) {
		this.fileType = fileType;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getObject() {
		return object;
	}
	public void setObject(String object) {
		this.object = object;
	}
	public int getSort() {
		return sort;
	}
	public void setSort(int sort) {
		this.sort = sort;
	}
	
	
}
