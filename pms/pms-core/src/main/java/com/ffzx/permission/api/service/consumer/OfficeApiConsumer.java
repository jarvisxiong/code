package com.ffzx.permission.api.service.consumer;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ffzx.basedata.api.service.OfficeApiService;
import com.ffzx.commerce.framework.dto.ResultDto;
import com.ffzx.commerce.framework.exception.ServiceException;
import com.ffzx.commerce.framework.system.entity.SysOffice;

/**
 * base-data office dubbo消费者
 * 
 * @className OfficeApiConsumer.java
 * @author shifeng.tang
 * @date 2016年4月14日 下午6:57:30
 * @version 1.0
 */
@Service("officeApiConsumer")
public class OfficeApiConsumer {

	// 记录日志
	private final static Logger logger = LoggerFactory.getLogger(OfficeApiConsumer.class);

	@Autowired
	private OfficeApiService OfficeApiService;

	/**
	 * 返回所有机构
	 * @return
	 * @throws ServiceException
	 */
	@SuppressWarnings("unchecked")
	public List<SysOffice> getOfficeList(Map<String, Object> officeParams) throws ServiceException{
		logger.debug("OfficeApiConsumer-getOfficeList=》dubbo调用：OfficeApiService.getOfficeList - Start");
		ResultDto resDto = OfficeApiService.getOfficeList(officeParams);
		logger.debug("OfficeApiConsumer-getOfficeList=》dubbo调用：OfficeApiService.getOfficeList -"+resDto.getMessage());
		List<SysOffice> list = null;
		if (resDto.getCode().equals(ResultDto.OK_CODE)) {
			list = (List<SysOffice>) resDto.getData();
		}
		logger.debug("OfficeApiConsumer-getOfficeList=》dubbo调用：OfficeApiService.getOfficeList - End");
		return list;
	}
	
	/**
	 * 返回所有机构Map对象
	 * @return
	 * @throws ServiceException
	 */
	@SuppressWarnings("unchecked")
	public List<Object> getObjectList(Map<String, Object> officeParams) throws ServiceException{
		logger.debug("OfficeApiConsumer-getObjectList=》dubbo调用：OfficeApiService.getObjectList - Start");
		ResultDto resDto = OfficeApiService.getObjectList(officeParams);
		logger.debug("OfficeApiConsumer-getObjectList=》dubbo调用：OfficeApiService.getObjectList -"+resDto.getMessage());
		List<Object> list = null;
		if (resDto.getCode().equals(ResultDto.OK_CODE)) {
			list = (List<Object>) resDto.getData();
		}
		logger.debug("OfficeApiConsumer-getObjectList=》dubbo调用：OfficeApiService.getObjectList - End");
		return list;
	}
	
}
