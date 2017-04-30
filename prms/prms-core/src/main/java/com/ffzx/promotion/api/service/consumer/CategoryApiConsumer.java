package com.ffzx.promotion.api.service.consumer;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ffzx.commerce.framework.dto.ResultDto;
import com.ffzx.commerce.framework.exception.ServiceException;
import com.ffzx.commerce.framework.utils.RedisUtil;
import com.ffzx.commodity.api.dto.Category;
import com.ffzx.commodity.api.service.CategoryApiService;
import com.ffzx.promotion.exception.CallInterfaceException;

@Service("categoryApiConsumer")
public class CategoryApiConsumer {
	// 记录日志
			private final static Logger logger = LoggerFactory.getLogger(CommodityApiConsumer.class);
			@Resource
			private RedisUtil redisUtil;
			@Autowired
			private CategoryApiService categoryApiService;
			
			/*******
			 * 返回商品类别树
			 * @param params
			 * @param id
			 * @return
			 * @throws ServiceException
			 */
			public List<Category> getTreeTableList(Map<String, Object> params, String id) throws ServiceException{
				logger.debug("CategoryApiConsumer-getTreeTableList=》dubbo调用：CategoryApiService.getTreeTableList - Start");
				ResultDto resDto = categoryApiService.getList(params);
				logger.debug("CategoryApiConsumer-getTreeTableList=》dubbo调用：CategoryApiService.getTreeTableList -",resDto.getMessage());
				List<Category> list = null;
				if (resDto.getCode().equals(ResultDto.OK_CODE)) {
					list = (List<Category>) resDto.getData();
				}
				logger.debug("CategoryApiConsumer-getTreeTableList=》dubbo调用：CategoryApiService.getTreeTableList - End");
				return list;
			}
			
			/*******
			 * 返回类目名称
			 * @param params
			 * @param id
			 * @return
			 * @throws ServiceException
			 */
			public String getCategoryName(List<String> ids) throws ServiceException{
				logger.debug("CategoryApiConsumer-getCategoryName=》dubbo调用：CategoryApiService.getCategoryName - Start");
				ResultDto result = null;
				try {
				 	 result =categoryApiService.getCategory(ids);
				} catch (Exception e) {
					logger.error("CategoryApiConsumer-getCategoryName=》dubbo调用：CategoryApiService.getCategoryName" , e);
					throw new CallInterfaceException(e.getMessage());
				}
				logger.debug("CategoryApiConsumer-getCategoryName=》dubbo调用：CategoryApiService.getCategoryName -",result.getMessage());
				List<String> list = null;
				if (result.getCode().equals(ResultDto.OK_CODE)) {
					list= (List<String>) result.getData();
					if(list==null || list.size()==0)
						return null;
					return list.toString().substring(1, list.toString().length()-1).replaceAll(" ", "");
				}
				else{
					logger.error("dubbo调用：CategoryApiService.getCategoryName  " , result.getMessage());
					throw new CallInterfaceException(result.getMessage());
				}
				
			}
			
}
