package com.ffzx.portal.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ffzx.commerce.framework.dao.CrudMapper;
import com.ffzx.commerce.framework.service.impl.BaseCrudServiceImpl;
import com.ffzx.commerce.framework.utils.StringUtil;
import com.ffzx.commerce.framework.utils.UUIDGenerator;
import com.ffzx.portal.enums.FileType;
import com.ffzx.portal.mapper.CatelogMapper;
import com.ffzx.portal.mapper.DataAttachmentMapper;
import com.ffzx.portal.model.Catelog;
import com.ffzx.portal.model.DataAttachment;
import com.ffzx.portal.model.PageTab;
import com.ffzx.portal.service.CatelogService;
import com.ffzx.portal.service.DataAttachmentService;

/**
 * @className CatelogServiceImpl
 *
 * @author liujunjun
 * @date 2017-04-12 11:36:18
 * @version 1.0.0
 */
@Service("catelogService")
public class CatelogServiceImpl extends BaseCrudServiceImpl implements CatelogService {

	@Resource
	private CatelogMapper catelogMapper;
	
	@Autowired
	private DataAttachmentService dataAttachmentService;
	@Override
	public CrudMapper init() {
		return catelogMapper;
	}

	/* (non-Javadoc)
	 * @see com.ffzx.portal.service.CatelogService#getCatelogJson(java.lang.String)
	 */
	@Override
	public JSONArray getCatelogJson(String pageTabId) {
		JSONArray catelogJson = new JSONArray() ;
		if(StringUtils.isEmpty(pageTabId)){
			return catelogJson;
		}
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("pageTabId", pageTabId);
		param.put("isEnable", 1);
		List<Catelog> list =catelogMapper.selectByParams(param);
		if(list !=null && list.size() > 0){
			catelogJson = JSONArray.parseArray(JSONObject.toJSONString(list));
		}
		return catelogJson;
	}

	/* (non-Javadoc)
	 * @see com.ffzx.portal.service.CatelogService#getChildCatelogJson(java.lang.String)
	 */
	@Override
	public JSONArray getChildCatelogJson(String catelogId) {
		JSONArray childJson = new JSONArray() ;
		if(StringUtils.isEmpty(catelogId)){
			return childJson ;
		}
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("parentId", catelogId);
		param.put("isEnable", 1);
		List<Catelog> list = catelogMapper.selectByParams(param);
		if(list !=null && list.size() > 0){
			childJson = JSONArray.parseArray(JSONObject.toJSONString(list));
		}
		return childJson;
	}

	/***
	 * 
	 * @param data
	 * @author yinglong.huang
	 * @email yinglong.huang@ffzxnet.com
	 * @date 2017年4月13日 下午5:14:24
	 * @version V1.0
	 * @return 
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void saveData(Catelog data) {
		Catelog parent = null;
		if(null!=data.getParent()&&StringUtil.isNotNull(data.getParent().getId())){
			parent = this.findById(data.getParent().getId());
		}
		String parentIds = "";
		if (parent == null) {
			parentIds = "0,";
		} else {
			parentIds = parent.getParentIds() + parent.getId() + ",";
		}
		data.setParentIds(parentIds);
		if(StringUtil.isNotNull(data.getId())){
			Catelog oldData = this.findById(data.getId());
			String oldchildParentIds = oldData.getParentIds()+oldData.getId();
			String newChiledParentIds = data.getParentIds()+data.getId();
			//若更换父级节点，则更换其原有子集父级节点
			if(!oldchildParentIds.equals(newChiledParentIds)){
				Map<String,Object> param = new HashMap<String,Object>();
				param.put("parentidsrlike", oldchildParentIds);
				List<Catelog> list = catelogMapper.selectByParams(param);
				for (Catelog catelog : list) {
					catelog.setParentIds(catelog.getParentIds().replace(oldchildParentIds,newChiledParentIds));
					this.modifyById(catelog);
				}
			}
			this.modifyById(data);
		}else{
			data.setId(UUIDGenerator.getUUID());
			this.add(data);
		}
		
		String jsonStr = data.getJsonStr();
		if(StringUtil.isNotNull(jsonStr)){
			initImgData(data);
		}
		
	}	
	@Transactional(rollbackFor = Exception.class)
	private void initImgData(Catelog catelog){
		String jsonStr =  catelog.getJsonStr();
		dataAttachmentService.deleteByObjId(catelog.getId());
		Date now = new Date();
		jsonStr = jsonStr.replaceAll("&quot;", "'");
		List<DataAttachment> imgList = JSONArray.parseArray(jsonStr,DataAttachment.class);
		for (DataAttachment dataAttachment : imgList) {
			dataAttachment.setId(UUIDGenerator.getUUID());
			dataAttachment.setObject(catelog.getId());
			dataAttachment.setCreateDate(now);
			dataAttachment.setFileType(FileType.IMAGE);
			dataAttachmentService.add(dataAttachment);
		}
	}
	
}