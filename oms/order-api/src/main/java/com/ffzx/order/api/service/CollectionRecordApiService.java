package com.ffzx.order.api.service;

import java.util.Map;

import com.ffzx.commerce.framework.dto.ResultDto;
import com.ffzx.commerce.framework.exception.ServiceException;
import com.ffzx.order.api.dto.CollectionRecord;

/**
 * 收银记录dubbo接口
 * @author yinglong.huang
 * @email yinglong.huang@ffzxnet.com
 * @date 2017年1月4日 下午1:45:05
 * @version V1.0
 */
public interface CollectionRecordApiService {
	/**
	 * 分页查询
	 * 默认按时间倒叙
	 * @param params
	 * @param page
	 * @param pageSize
	 * @param waterline
	 * @return ResultDto.data  page对象
	 * @throws ServiceException
	 * @author yinglong.huang
	 * @email yinglong.huang@ffzxnet.com
	 * @date 2017年1月4日 下午1:55:37
	 * @version V1.0
	 * @return
	 */
	ResultDto getDataByPage(Map<String,Object>params,int page, int pageSize, int waterline) throws ServiceException;
	
	
	/**
	 * 添加收银记录
	 * 注意：当data.id不为空时此方法调用修改数据方法，否则调用新增数据方法
	 * @param data
	 * @return
	 * @throws ServiceException
	 * @author yinglong.huang
	 * @email yinglong.huang@ffzxnet.com
	 * @date 2017年1月4日 下午2:12:43
	 * @version V1.0
	 * @return
	 */
	ResultDto saveData(CollectionRecord data)throws ServiceException;
	
	/**
	 * 获取实收金额统计
	 * 
	 * @param params
	 * @return
	 * @author yinglong.huang
	 * @email yinglong.huang@ffzxnet.com
	 * @date 2017年1月11日 上午9:43:17
	 * @version V1.0
	 * @return
	 */
	ResultDto getTotalActualFines(Map<String,Object>params)throws ServiceException;;
}
