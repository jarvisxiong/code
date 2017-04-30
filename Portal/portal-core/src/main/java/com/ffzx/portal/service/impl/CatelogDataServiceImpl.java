package com.ffzx.portal.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ffzx.commerce.framework.constant.Constant;
import com.ffzx.commerce.framework.dao.CrudMapper;
import com.ffzx.commerce.framework.page.Page;
import com.ffzx.commerce.framework.service.impl.BaseCrudServiceImpl;
import com.ffzx.portal.mapper.CatelogDataMapper;
import com.ffzx.portal.mapper.CatelogMapper;
import com.ffzx.portal.model.Catelog;
import com.ffzx.portal.model.CatelogData;
import com.ffzx.portal.service.CatelogDataService;

/**
 * @className CatelogDataServiceImpl
 *
 * @author liujunjun
 * @date 2017-04-12 11:37:17
 * @version 1.0.0
 */
@Service("catelogDataService")
public class CatelogDataServiceImpl extends BaseCrudServiceImpl implements CatelogDataService {

	@Resource
	private CatelogDataMapper catelogDataMapper;
	
	@Resource
	private CatelogMapper catelogMapper;
	
	@Override
	public CrudMapper init() {
		return catelogDataMapper;
	}

	/* (non-Javadoc)
	 * @see com.ffzx.portal.service.CatelogDataService#getDataJson(java.lang.String, java.lang.String)
	 */
	@Override
	public JSONArray getDataJson(String catelogNumber, String sort) {
		
		JSONArray dataJson = new JSONArray();
		if(StringUtils.isEmpty(catelogNumber)){
			return dataJson ;
		}
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("number", catelogNumber);
		List<Catelog> catelogList = catelogMapper.selectByParams(param);
		if(catelogList == null || catelogList.size() == 0){
			return dataJson ;
		}
		
		Catelog catelog = catelogList.get(0);
		List<CatelogData> dataList = catelogDataMapper.selectListById(catelog.getId(), sort);
		if(dataList!=null && dataList.size() > 0){
			dataJson = JSONArray.parseArray(JSONObject.toJSONString(dataList)) ;
		}
		return dataJson;
	}

	/* (non-Javadoc)
	 * @see com.ffzx.portal.service.CatelogDataService#getDataJsonPag(java.lang.String, java.lang.String, com.ffzx.commerce.framework.page.Page)
	 */
	@Override
	public JSONObject getDataJsonPag(String catelogNumber, String sort, Page pageObj) {
		JSONObject dataJson = new JSONObject();
		if(StringUtils.isEmpty(catelogNumber)){
			dataJson = JSONObject.parseObject(JSONObject.toJSONString(pageObj)) ;
			return dataJson ;
		}
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("number", catelogNumber);
		List<Catelog> catelogList = catelogMapper.selectByParams(param);
		if(catelogList == null || catelogList.size() == 0){
			dataJson = JSONObject.parseObject(JSONObject.toJSONString(pageObj)) ;
			return dataJson ;
		}
		
		Catelog catelog = catelogList.get(0);
		Map<String,Object> qparam = new HashMap<String,Object>();
		qparam.put("id", catelog.getId());
		qparam.put("sort", sort);
		
		List<CatelogData> cdList = catelogDataMapper.selectByPageList(pageObj, Constant.ORDER_BY_FIELD_CREATE, Constant.ORDER_BY, qparam);
		pageObj.setRecords(cdList);
		dataJson = JSONObject.parseObject(JSONObject.toJSONString(pageObj)) ;
		return dataJson;
	}

	/* (non-Javadoc)
	 * @see com.ffzx.portal.service.CatelogDataService#newGetCatelogDataInfo(java.lang.String)
	 */
	@Override
	public JSONObject newGetCatelogDataInfo(String cid) {
		CatelogData data = catelogDataMapper.getInfoById(cid);
		JSONObject result = JSONObject.parseObject(JSONObject.toJSONString(data));
		buildPreAndNextData( data,result);
		return result;
	}	
	
	/***
	 * 构建附加数据
	 * @param data
	 * @param originData
	 * @author ying.cai
	 * @date 2017年4月13日下午2:03:23
	 * @email ying.cai@ffzxnet.com
	 */
	private void buildPreAndNextData(CatelogData data,JSONObject originData){
		originData.put("preTitle", ""); //上一条内容标题
		originData.put("preId", "");//上一条内容id
		originData.put("nextTitle", "");//下一条内容标题
		originData.put("nextId", "");//上一条内容id
		List<CatelogData> catelogDataList = catelogDataMapper.selectListById(data.getCatelog().getId(),"DATEDESC");
		if(catelogDataList==null || catelogDataList.size()<=1){
			return;
		}
		int index = 0;
		for (CatelogData catelogData : catelogDataList) {
			if(catelogData.getId().equals(data.getId())){
				if(index==0){
					originData.put("nextTitle", catelogDataList.get(index+1).getTitle() );
					originData.put("nextId", catelogDataList.get(index+1).getId());
				}else if(index==catelogDataList.size()-1){
					originData.put("preTitle", catelogDataList.get(index-1).getTitle() );
					originData.put("preId", catelogDataList.get(index-1).getId());
				}else{
					originData.put("preTitle", catelogDataList.get(index-1).getTitle() );
					originData.put("preId", catelogDataList.get(index-1).getId());
					originData.put("nextTitle", catelogDataList.get(index+1).getTitle() );
					originData.put("nextId", catelogDataList.get(index+1).getId());
				}
				return;
			}
			index++;
		}
	}

	/* (non-Javadoc)
	 * @see com.ffzx.portal.service.CatelogDataService#getPageTabData(java.lang.String)
	 */
	@Override
	public JSONArray getPageTabData(String pagetabId) {
		JSONArray pageDataJson = new JSONArray();
		if(!StringUtils.isEmpty(pagetabId)){
			Map<String,Object> param = new HashMap<String,Object>();
			param.put("id", pagetabId);
			List<CatelogData> catelogDataList = catelogDataMapper.selectById(pagetabId);
			if(catelogDataList!=null && catelogDataList.size() > 0){
				Map<String,Catelog> catelogMap = new HashMap<String, Catelog>();
				for(CatelogData data : catelogDataList){
					if(data.getCatelog()!=null && !StringUtils.isEmpty(data.getCatelog().getNumber())){
						String number = data.getCatelog().getNumber();
						if(!catelogMap.containsKey(number)){
							List<CatelogData> _tempCatelogDataList = new ArrayList<CatelogData>();
							Catelog _cloneCatelog = null ;
							try {
								_cloneCatelog = (Catelog) BeanUtils.cloneBean(data.getCatelog());
							} catch (Exception e) {
								e.printStackTrace();
							} 
							data.setCatelog(null);
							_tempCatelogDataList.add(data);
							_cloneCatelog.setCatelogDataList(_tempCatelogDataList);
							catelogMap.put(number,_cloneCatelog);
						}else{
							Catelog _tempCatelog = catelogMap.get(number);
							data.setCatelog(null);
							_tempCatelog.getCatelogDataList().add(data);
						}
					}
				}
				pageDataJson = JSONArray.parseArray(( JSONObject.toJSONString(catelogMap.values())));
			}
		}
		return pageDataJson;
	}

	/* (non-Javadoc)
	 * @see com.ffzx.portal.service.CatelogDataService#updateTop(com.ffzx.portal.model.CatelogData)
	 */
	@Override
	@Transactional
	public void updateTop(CatelogData cld) {
		catelogDataMapper.batchUpdateTop(cld.getCatelog().getId());
		modifyById(cld);
	}
}