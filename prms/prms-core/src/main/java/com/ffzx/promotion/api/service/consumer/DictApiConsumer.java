/**
 * 
 */
package com.ffzx.promotion.api.service.consumer;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ffzx.basedata.api.dto.Dict;
import com.ffzx.basedata.api.service.DictApiService;
import com.ffzx.commerce.framework.dto.ResultDto;
import com.ffzx.commerce.framework.utils.RedisUtil;
import com.ffzx.commerce.framework.utils.StringUtil;
import com.ffzx.commodity.api.dto.Commodity;
import com.ffzx.promotion.api.dto.constant.PrmsConstant;
import com.ffzx.promotion.util.StringappUtil;

/**
 * @Description: 获取活动消费者
 * @author zi.luo
 * @email  zi.luo@ffzxnet.com
 * @date 2016年5月18日 下午5:02:22
 * @version V1.0 
 *
 */
@Service("dictApiConsumer")
public class DictApiConsumer {

	@Resource
	private RedisUtil redisUtil;
	@Autowired
	private DictApiService dictApiService;
	
	// 记录日志
	private final static Logger logger = LoggerFactory.getLogger(DictApiConsumer.class);
	
	

	/**
	 * 1是优惠券  2是红包
	 * @param page
	 * @param pageSize
	 * @return
	 */
	public String getMessage(String type) {
		ResultDto result = null;
		result =dictApiService.getDictByType(PrmsConstant.messageType);
		if(result.getCode().equals(ResultDto.OK_CODE) ){
			List<Dict> dictList=(List<Dict>) result.getData();
		}
		try {
		 	 result =dictApiService.getDictByType(PrmsConstant.messageType);
			if(result.getCode().equals(ResultDto.OK_CODE) ){//如果成功
				List<Dict> dictList=(List<Dict>) result.getData();
				for (Dict dict : dictList) {
					if(dict.getValue().equals(type)){
						return StringappUtil.replaceStringChar(dict.getLabel());
					}
				}
			}else{
				throw new  RuntimeException(result.getMessage()+":调用数据字典接口失败");
			}
		} catch (RuntimeException e) {
			logger.error("" , e);
			throw new  RuntimeException(e);
		}
		return null;
		
	}
	
	/**
	 * 获取行政级别
	 * @param page
	 * @param pageSize
	 * @return
	 */
	public String getCouponLink() {
		String link = null;
		logger.debug("DictApiConsumer-getCouponLink=》dubbo调用：dictApiService.getDictByType - Start");
		ResultDto result =dictApiService.getDictByType("prms_coupon_url");
		logger.debug("DictApiConsumer-getCouponLink=》dubbo调用：dictApiService.getDictByType -",result.getMessage());
		List<Commodity> list = null;
		Map<String, Object> paramValue=null;
		if (result.getCode().equals(ResultDto.OK_CODE)) {
			List<Dict> dictList=(List<Dict>) result.getData();
			if(dictList==null || dictList.size()==0)
				return null;
			else{
				link = dictList.get(0).getValue();
			}
		}
		logger.debug("DictApiConsumer-getCouponLink=》dubbo调用：dictApiService.getDictByType - End");
		return link;
	}
}
