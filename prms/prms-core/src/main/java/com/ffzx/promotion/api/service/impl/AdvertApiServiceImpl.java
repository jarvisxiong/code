package com.ffzx.promotion.api.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ffzx.commerce.framework.constant.RedisConstant;
import com.ffzx.commerce.framework.dao.CrudMapper;
import com.ffzx.commerce.framework.dto.ResultDto;
import com.ffzx.commerce.framework.exception.ServiceException;
import com.ffzx.commerce.framework.service.impl.BaseCrudServiceImpl;
import com.ffzx.commerce.framework.utils.DateUtil;
import com.ffzx.commerce.framework.utils.RedisUtil;
import com.ffzx.promotion.api.dto.Advert;
import com.ffzx.promotion.api.dto.AdvertRegion;
import com.ffzx.promotion.api.enums.AdvertStatus;
import com.ffzx.promotion.api.service.AdvertApiService;
import com.ffzx.promotion.mapper.AdvertMapper;
import com.ffzx.promotion.mapper.AdvertRegionMapper;
import com.ffzx.promotion.service.AdvertService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 
 * @author ffzx
 * @date 2016-05-03 17:58:03
 * @version 1.0.0
 * @copyright www.ffzxnet.com
 */
@Service("advertApiService")
public class AdvertApiServiceImpl extends BaseCrudServiceImpl implements AdvertApiService {
	@Resource
	private AdvertMapper advertMapper;
	@Resource
	private AdvertRegionMapper advertRegionMapper;
	@Autowired
	private AdvertService advertService ;
	@Resource
	private RedisUtil redisUtil;
	@Override
	public CrudMapper init() {
		// TODO Auto-generated method stub
		return advertMapper;
	}
	@Override
	public ResultDto getDataByRegionNumber(String regionNum) {
		ResultDto rsDto = null;
		try{
		rsDto = new ResultDto(ResultDto.OK_CODE, "success");
	    
	    String redisKey = RedisConstant.REDIS_PRMS_PREFIX+"_regionNumber_"+regionNum;
		JSONObject result = null;
		if(redisUtil.exists(redisKey)){
			result = JSONObject.fromObject(redisUtil.get(redisKey));
			rsDto.setData(result);
		}else{
		advertService.autoUpdateStatus();
		result = new JSONObject();
 
		Map<String, Object> params = new HashMap<String, Object>();
		if(regionNum.indexOf("','")!=-1){
			params.put("numbers", regionNum);
		}else{
			params.put("number", regionNum);
		}
		List<AdvertRegion> arList  = advertRegionMapper.selectByParams(params);
		List<AdvertRegion> childList =null;
		if(arList==null||arList.size()==0){
			return null;
		}
		if(params.containsKey("numbers")){
			childList  = arList;
		}else{
			childList  = getChildsList(arList.get(0).getId());
		}
		 
		AdvertRegion advertRegion  =  arList.get(0);
		if(childList==null||childList.size()==0){//由于广告区域只有两级树则 当其有父节点时则其为叶子节点 直接获取广告数据
			result.put("items", getItem(arList));
		}else{
			result.put("items", getItem(childList));
		}
		rsDto.setData(result);
		if(result!=null)
		{
			redisUtil.set(redisKey, result.toString(),20L);
		}
	    }
		}catch(Exception e){
			logger.error("AdvertApiServiceImpl-getDataByRegionNumber-Exception=》机构dubbo调用-getDataByRegionNumber", e);
//			rsDto = new ResultDto(ResultDto.ERROR_CODE, "faild:" + e);
			throw new ServiceException(e);
		}
		return rsDto;
	}
	private List<AdvertRegion> getChildsList(String parentId){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("parentId",parentId);
		params.put("orderBy", "d.sort asc");
		return advertRegionMapper.selectByParams(params);
	}
    private JSONArray getItem(List<AdvertRegion> arList){
    	JSONArray item = new JSONArray();
    	for (AdvertRegion advertRegion : arList) {
    		JSONObject obj = new JSONObject();
			obj.put("id", advertRegion.getId());
			obj.put("name", advertRegion.getName());
			obj.put("number", advertRegion.getNumber());
			obj.put("index", advertRegion.getSort());
			List<Advert> advertList = getAdvertListByRegionId(advertRegion.getId());
			JSONArray adverts = new JSONArray();
			for (Advert advert : advertList) {
				JSONObject adObj = new JSONObject();
				adObj.put("id", advert.getId());
				adObj.put("imgPath", advert.getPhotoPath() == null ? "" :System.getProperty("image.web.server")+"/"+advert.getPhotoPath());
				adObj.put("imgPath2", advert.getPhotoPath2() == null ? "" :System.getProperty("image.web.server")+"/"+advert.getPhotoPath2());
				adObj.put("title",advert.getTitle()== null ? "" : advert.getTitle());
				adObj.put("type", advert.getAdvertType()== null ? "" : advert.getAdvertType());
				adObj.put("url", advert.getUrl() == null ? "" : advert.getUrl());
				adObj.put("objId", advert.getObjId()== null ? "" :advert.getObjId());
				adObj.put("index", advert.getLocationIndex());
				adObj.put("startTime",DateUtil.formatDateTime(advert.getStartDate())); // 开始时间
				adObj.put("endTime",DateUtil.formatDateTime(advert.getEndDate())); // 结束时间
				/**
				 * 应冬冬要求  在明细行 添加两个字段
				 */
				adObj.put("number", advertRegion.getNumber());
				adObj.put("index", advertRegion.getSort());
				adverts.add(adObj);
			}
			obj.put("adverts", adverts);
			item.add(obj);
		}
    	return item;
    }
	private List<Advert> getAdvertListByRegionId(String regionId){
		Map<String, Object> params_2 = new HashMap<String, Object>();
		params_2.put("regionId",regionId);
		params_2.put("status",AdvertStatus.STARTTING);
		params_2.put("isBackup",0);
		params_2.put("orderBy", "d.location_index asc");
		return advertMapper.selectByParams(params_2);
	}
	@Override
	public ResultDto getBaseTab() {
		// TODO Auto-generated method stub
		ResultDto rsDto = null;
		try{
		rsDto = new ResultDto(ResultDto.OK_CODE, "success");
		String redisKey = RedisConstant.REDIS_PRMS_PREFIX+"_getBaseTab";
		JSONObject result = null;
		if(redisUtil.exists(redisKey)){
			result = (JSONObject) redisUtil.get(redisKey);
			rsDto.setData(result);
		}else{
			advertService.autoUpdateStatus();
		result = new JSONObject();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("number", "BASETAB");
		List<AdvertRegion> arList  = advertRegionMapper.selectByParams(params);
		if(arList==null||arList.size()==0){
			throw new ServiceException("底部菜单数据不存在");
		}
		AdvertRegion advertRegion = arList.get(0);
		List<Advert> advertList = getAdvertListByRegionId(advertRegion.getId());
		if(advertList==null||advertList.size()==0){
			throw new ServiceException("底部菜单数据不存在");
		}
		result.put("startTime",DateUtil.formatDate( advertList.get(0).getStartDate()));
		result.put("endTime",DateUtil.formatDate( advertList.get(0).getEndDate()));
		JSONArray adverts = new JSONArray();
		for (Advert advert : advertList) {
			JSONObject adObj = new JSONObject();
			adObj.put("index", advert.getLocationIndex());
			adObj.put("imgPath", advert.getPhotoPath() == null ? "" :System.getProperty("image.web.server")+"/"+advert.getPhotoPath());
			adObj.put("imgPath2", advert.getPhotoPath2() == null ? "" :System.getProperty("image.web.server")+"/"+advert.getPhotoPath2());
			adObj.put("title",advert.getTitle()== null ? "" : advert.getTitle());
			adObj.put("type", advert.getAdvertType()== null ? "" : advert.getAdvertType());
			//adObj.put("url", advert.getUrl() == null ? "" : advert.getUrl());
			adObj.put("objId", advert.getObjId()== null ? "" :advert.getObjId());
			adverts.add(adObj);
		}
		result.put("items", adverts);
		rsDto.setData(result);
		if(result!=null)
		{
			redisUtil.set(redisKey, result,600L);
		}
		}
		}catch(ServiceException se){
			logger.info("底部菜单数据不存在",se);
			rsDto = new ResultDto(ResultDto.ERROR_CODE, "底部菜单数据不存在");
		}catch(Exception e){
			logger.error("AdvertApiServiceImpl-getDataByRegionNumber-Exception=》机构dubbo调用-getDataByRegionNumber", e);
			rsDto = new ResultDto(ResultDto.ERROR_CODE, "faild:" + e);
		}
		return rsDto;
	}
	@Override
	public ResultDto getAdvertEntity(String regionNum) {
		ResultDto rsDto = null;
		try{
			rsDto = new ResultDto(ResultDto.OK_CODE, "success");
			Map<String,Object> params = new HashMap<String,Object>();
			params.put("number", regionNum);
			List<AdvertRegion> advertRegionList = advertRegionMapper.selectByParams(params);
			if(advertRegionList!=null && advertRegionList.size()>0){
				rsDto.setData(advertRegionList.get(0));
			}
		}catch(Exception e){
			logger.error("AdvertApiServiceImpl-getAdvertEntity-Exception=》机构dubbo调用-getAdvertEntity", e);
			throw new ServiceException(e);
		}
		return rsDto;
	}
   

}