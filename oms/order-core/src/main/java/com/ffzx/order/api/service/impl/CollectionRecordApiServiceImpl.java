package com.ffzx.order.api.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.ffzx.commerce.framework.constant.Constant;
import com.ffzx.commerce.framework.dto.ResultDto;
import com.ffzx.commerce.framework.exception.ServiceException;
import com.ffzx.commerce.framework.page.Page;
import com.ffzx.commerce.framework.utils.JsonMapper;
import com.ffzx.commerce.framework.utils.UUIDGenerator;
import com.ffzx.order.api.dto.CollectionRecord;
import com.ffzx.order.api.service.CollectionRecordApiService;
import com.ffzx.order.mapper.CollectionRecordMapper;
import com.ffzx.order.service.CollectionRecordService;

/**
 * *
 * @author yinglong.huang
 * @email yinglong.huang@ffzxnet.com
 * @date 2017年1月4日 下午1:46:49
 * @version V1.0
 */


public class CollectionRecordApiServiceImpl implements CollectionRecordApiService {
	// 记录日志
	private final static Logger logger = LoggerFactory.getLogger(CollectionRecordApiServiceImpl.class);
	
	@Autowired
	private CollectionRecordService collectionRecordService;
	
	@Resource
	private CollectionRecordMapper collectionRecordMapper;

	/***
	 * 
	 * @param params
	 * @param page
	 * @param pageSize
	 * @param waterline
	 * @return
	 * @throws ServiceException
	 * @author yinglong.huang
	 * @email yinglong.huang@ffzxnet.com
	 * @date 2017年1月4日 下午1:55:28
	 * @version V1.0
	 * @return 
	 */
	@Override
	public ResultDto getDataByPage(Map<String, Object> params, int page, int pageSize, int waterline)
			throws ServiceException {
		logger.info("收银记录查询:params:"+params.toString()+",page:"+page+",pageSize:"+pageSize+",waterline:"+waterline);
		ResultDto rsDto = null;
		Page pageObj = new Page(page, pageSize, waterline);
		try {
			rsDto = new ResultDto(ResultDto.OK_CODE, "success");
			List<CollectionRecord> dataList = collectionRecordService.findByPage(pageObj, "create_date", "desc", params);
			pageObj.setRecords(dataList);
			rsDto.setData(pageObj);
		}catch(Exception e){
			logger.error("收银记录查询异常", e);
			throw new ServiceException(e);
		}
		return rsDto;
	}

	/***
	 * 
	 * @param data
	 * @return
	 * @throws ServiceException
	 * @author yinglong.huang
	 * @email yinglong.huang@ffzxnet.com
	 * @date 2017年1月4日 下午2:13:08
	 * @version V1.0
	 * @return 
	 */
	@Override
	@Transactional
	public ResultDto saveData(CollectionRecord data) throws ServiceException {
		logger.info("保存收银记录:data:"+JsonMapper.toJsonString(data));
		ResultDto rsDto = null;
		try {
			rsDto = new ResultDto(ResultDto.OK_CODE, "success");
			Date now = new Date();
			if(null==data.getId()){
				data.setId(UUIDGenerator.getUUID());
				if(null==data.getCreateDate()){
					data.setCreateDate(now);
				}
				if(null==data.getLastUpdateDate()){
					data.setLastUpdateDate(now);
				}
				data.setDelFlag(Constant.DELTE_FLAG_NO);
				data.setTab("0");
				collectionRecordService.add(data);
			}else{
				if(null==data.getLastUpdateDate()){
					data.setLastUpdateDate(now);
				}
				collectionRecordService.modifyById(data);
			}
		}catch(Exception e){
			logger.error("保存记录查询异常", e);
			throw new ServiceException(e);
		}
		return rsDto;
	}

	/***
	 * 
	 * @param params
	 * @return
	 * @throws ServiceException
	 * @author yinglong.huang
	 * @email yinglong.huang@ffzxnet.com
	 * @date 2017年1月11日 上午9:43:49
	 * @version V1.0
	 * @return 
	 */
	@Override
	public ResultDto getTotalActualFines(Map<String, Object> params) throws ServiceException {
		logger.info("查询实收金额:params:"+params.toString());
		ResultDto rsDto = null;
		try {
			rsDto = new ResultDto(ResultDto.OK_CODE, "success");
			BigDecimal totalActualFines = collectionRecordMapper.selectTotalActualFines(params);
			rsDto.setData(totalActualFines);
		}catch(Exception e){
			logger.error("查询实收金", e);
			throw new ServiceException(e);
		}
		return rsDto;
	}
	
	

}
