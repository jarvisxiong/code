package com.ffzx.permission.api.service.consumer;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ffzx.basedata.api.dto.SubSystemConfig;
import com.ffzx.basedata.api.service.SubSystemConfigApiService;
import com.ffzx.commerce.framework.dto.ResultDto;
import com.ffzx.commerce.framework.exception.ServiceException;
import com.ffzx.commerce.framework.utils.RedisUtil;

/**
 * bsedate dubbo subSystemConfigApiConsumer 子系统 消费者配置
 * 
 * @author liujunjun
 * @date 2016年4月12日 上午10:29:01
 * @version 1.0
 */
@Service("subSystemConfigApiConsumer")
public class SubSystemConfigApiConsumer {
	// 记录日志
	private final static Logger logger = LoggerFactory.getLogger(SubSystemConfigApiConsumer.class);

	@Resource
	private RedisUtil redisUtil;
	
	@Autowired
	private SubSystemConfigApiService subSystemConfigApiService;
	
	/**
	 * dubbo调用   - 获取子系统SubSystemConfig 
	 * 子系统dubbo服务接口
	 * @param type
	 * @return List<SubSystemConfig>子系统列表
	 * @throws ServiceException
	 */
	public List<SubSystemConfig> getSubSystemConfig(Map<String, Object> params) throws ServiceException {
		logger.debug("SubSystemConfigApiConsumer-getSubSystemConfig=》子系统dubbo调用-列表 - BEGIN");

		List<SubSystemConfig> dictList = null;
		
		ResultDto result = subSystemConfigApiService.getSubSystemConfig(params);
		if(result.getCode().equals(ResultDto.OK_CODE) ){
			dictList = (List<SubSystemConfig>)result.getData();
		}
		logger.debug("SubSystemConfigApiConsumer-getSubSystemConfig=》子系统dubbo调用-列表 " + result.getMessage());
		logger.debug("SubSystemConfigApiConsumer-getSubSystemConfig=》子系统dubbo调用-列表 - END");
		return dictList;
		
	}
}
