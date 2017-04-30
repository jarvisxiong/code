package com.ffzx.permission.api.service.consumer;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ffzx.basedata.api.dto.Dict;
import com.ffzx.basedata.api.service.DictApiService;
import com.ffzx.commerce.framework.constant.RedisPrefix;
import com.ffzx.commerce.framework.dto.ResultDto;
import com.ffzx.commerce.framework.exception.ServiceException;
import com.ffzx.commerce.framework.utils.RedisUtil;

/**
 * bsedate dubbo dictApiService 消费者配置
 * 
 * @author liujunjun
 * @date 2016年4月12日 上午10:29:01
 * @version 1.0
 */
@Service("dictApiConsumer")
public class DictApiConsumer {
	// 记录日志
	private final static Logger logger = LoggerFactory.getLogger(DictApiConsumer.class);

	@Resource
	private RedisUtil redisUtil;
	
	@Autowired
	private DictApiService dictApiService;
	
	/**
	 * dubbo调用   - 通过类型编码 获取数据字典dict 
	 * 数据字典dubbo服务接口
	 * @param type
	 * @return
	 * @throws ServiceException
	 */
	public List<Dict> getDictByType(String type) throws ServiceException {
		logger.debug("DictApiServiceConsumer-getDictByType=》数据字典dubbo调用-列表 - BEGIN");

		List<Dict> dictList = null;
		//从缓存中获取
		dictList = (List<Dict>)redisUtil.get(RedisPrefix.DATA_DICT + type);
		if(null != dictList){
			return dictList;
		}

		ResultDto result = dictApiService.getDictByType(type);
		if(result.getCode().equals(ResultDto.OK_CODE) ){
			dictList = (List<Dict>)result.getData();
		}
		logger.debug("DictApiServiceConsumer-getDictByType=》数据字典dubbo调用-列表 " + result.getMessage());
		logger.debug("DictApiServiceConsumer-getDictByType=》数据字典dubbo调用-列表 - END");
		return dictList;
		
	}
}
