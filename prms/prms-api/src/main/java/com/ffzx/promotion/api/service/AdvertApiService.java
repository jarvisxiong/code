package com.ffzx.promotion.api.service;

import com.ffzx.commerce.framework.dto.ResultDto;
import com.ffzx.commerce.framework.service.BaseCrudService;

/**
 * 广告 数据库操作接口
 * 
 * @author yinglong.huang 
 * @email   yinglong.huang@ffzxnet.com
 * @date 2016年5月17日 下午5:45:16
 * @version 1.0.0
 * @copyright www.ffzxnet.com
 */
public interface AdvertApiService extends BaseCrudService {
	/***
	 * 根据区域编码 查询广告集合
	 * @param regionNum 区域编码
	 * @return ResultDto
	 * @author yinglong.huang
	 * @email yinglong.huang@ffzxnet.com
	 * @date 2016-5-10 下午05:25:26
	 * @version V1.0
	 */
	ResultDto getDataByRegionNumber(String regionNum);
	
	/***
	 * 后去底部标签
	 * @param 空
	 * @return ResultDto
	 * @author yinglong.huang
	 * @email yinglong.huang@ffzxnet.com
	 * @date 2016-5-10 下午05:25:26
	 * @version V1.0
	 */
	ResultDto getBaseTab();
	
	/**
	 * 通过广告编码获取广告对象
	 * @param regionNum
	 * @return
	 */
	ResultDto getAdvertEntity(String regionNum);

}