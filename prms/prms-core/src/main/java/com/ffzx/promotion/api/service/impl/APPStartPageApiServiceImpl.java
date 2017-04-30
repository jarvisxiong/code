package com.ffzx.promotion.api.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.ffzx.commerce.framework.dao.CrudMapper;
import com.ffzx.commerce.framework.dto.ResultDto;
import com.ffzx.commerce.framework.service.impl.BaseCrudServiceImpl;
import com.ffzx.commerce.framework.utils.DateUtil;
import com.ffzx.commerce.framework.utils.StringUtil;
import com.ffzx.promotion.api.dto.AppStartPage;
import com.ffzx.promotion.api.service.APPStartPageApiService;
import com.ffzx.promotion.mapper.AppStartPageMapper;

public class APPStartPageApiServiceImpl extends BaseCrudServiceImpl implements APPStartPageApiService {

	@Resource
	private AppStartPageMapper appStratPageMapper;

	@Override
	public CrudMapper init() {
		return appStratPageMapper;
	}

	@Override
	public ResultDto findAPPStartPageList(String termainl, String imgSize) {
		ResultDto rsDto = null;
		try{
			rsDto = new ResultDto(ResultDto.OK_CODE, "success");
			Map<String,Object> data = new HashMap<String,Object>();
			// 入参
			Map<String,Object> params = new HashMap<String,Object>();
			params.put("terminal", termainl);
	    	List<AppStartPage> list = appStratPageMapper.findAPPStartPageList(params);
			
	    	List<Map<String,String>> appStartPageList = new ArrayList<Map<String,String>>();
	    	if (null != list && list.size() > 0) {
	    		int index = 0;
	    		for (AppStartPage appStartPage : list) {
	    			if (index <= 1) {
	    				Map<String,String> result = new HashMap<String,String>();
	    				result.put("id", StringUtil.isNotNull(appStartPage.getId())?appStartPage.getId():"");
	    				result.put("title", StringUtil.isNotNull(appStartPage.getObjName())?appStartPage.getObjName():"");
	    				result.put("type", StringUtil.isNotNull(appStartPage.getAdvertType())?appStartPage.getAdvertType():"");
	    				result.put("objId", StringUtil.isNotNull(appStartPage.getObjId())?appStartPage.getObjId():"");
	    				result.put("url", StringUtil.isNotNull(appStartPage.getUrl())?appStartPage.getUrl():"");
	    				result.put("startTime", appStartPage.getEffectiveDate()==null?"":DateUtil.formatDateTime(appStartPage.getEffectiveDate())); // 生效时间
	    				result.put("endTime", appStartPage.getExpiredDate()==null?"":DateUtil.formatDateTime(appStartPage.getExpiredDate())); // 过期时间
	    				// 图片
	    				String imgPath = "";
	    				if ("Android".equals(termainl)) {
	    					if ("480X800".equals(imgSize)) {
	    						imgPath = StringUtil.isNotNull(appStartPage.getImgPath1())?appStartPage.getImgPath1():"";
							} else if("720X1280".equals(imgSize)){
								imgPath = StringUtil.isNotNull(appStartPage.getImgPath2())?appStartPage.getImgPath2():"";
							} else if("1080X1920".equals(imgSize)){
								imgPath = StringUtil.isNotNull(appStartPage.getImgPath3())?appStartPage.getImgPath3():"";
							}else{
								imgPath = "";
							}
						} else {
							if ("640X960".equals(imgSize)) {
	    						imgPath = StringUtil.isNotNull(appStartPage.getImgPath1())?appStartPage.getImgPath1():"";
							} else if("640X1136".equals(imgSize)){
								imgPath = StringUtil.isNotNull(appStartPage.getImgPath2())?appStartPage.getImgPath2():"";
							} else if("750X1334".equals(imgSize)) {
								imgPath = StringUtil.isNotNull(appStartPage.getImgPath3())?appStartPage.getImgPath3():"";
							} else if("1080X1920".equals(imgSize)){
								imgPath = StringUtil.isNotNull(appStartPage.getImgPath4())?appStartPage.getImgPath4():"";
							}else{
								imgPath = "";
							}
						}
	    				result.put("imgPath", System.getProperty("image.web.server")+imgPath);
	    				
	    				result.put("index", "");
	    				result.put("name", "");
	    				result.put("number", "");
	    				appStartPageList.add(result);
					}
	    			index++ ;
				}
			}
	    	data.put("items", appStartPageList);
	    	rsDto.setData(data);
		} catch(Exception e){
			rsDto = new ResultDto(ResultDto.ERROR_CODE, "不存在启动页数据");
			logger.error("ActivityCommodityApiServiceImpl-getWholeSaleList-Exception=》获取APP启动页数据列表", e);
		}
		
		return rsDto;
	}
}
