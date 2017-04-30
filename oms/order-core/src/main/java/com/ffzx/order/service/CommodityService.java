package com.ffzx.order.service;

import java.util.List;
import java.util.Map;

import com.ffzx.commerce.framework.exception.ServiceException;
import com.ffzx.commerce.framework.page.Page;
import com.ffzx.commerce.framework.service.BaseCrudService;
import com.ffzx.order.api.dto.Commodity;

/**
 * commodity数据库操作接口
 * 
 * @author ffzx
 * @date 2016-05-13 09:34:39
 * @version 1.0.0
 * @copyright www.ffzxnet.com
 */
public interface CommodityService extends BaseCrudService {
	
	public Commodity getByCode(String code) throws ServiceException;
	
	public Commodity getCommodityByCodeFromRedis(String code);
	
	/******
	 * 根据skuCode获取商品对象
	 * @param skuCode
	 * @return
	 * @throws ServiceException
	 */
	public Commodity getCommoditybyskuCode(String skuCode)throws ServiceException;
	
	/** 
	 * 通过商品条形码查询商品
	 * @param barCode 条形码
 	 * @return
	 * @author wg  
	 * @time 2016年5月21日 下午6:03:34 
	 */ 
	public Commodity findByBarCode(String barCode) throws ServiceException;
	
	/** 
	 * @param params
	 * @return 查找自定义非区域商品在自定义库存表没有存在的商品记录
	 * @author wg  
	 * @time 2016年5月21日 下午6:06:10 
	 */
	List<Commodity> findCommodityStock(Map<String,Object> map);
	
	/** 
	 * @param params
	 * @return 查找自定义非区域商品在自定义库存表没有存在的商品总数
	 * @author wg  
	 * @time 2016年5月21日 下午6:06:10 
	 */ 
	int countCommodityStock(Map<String,Object> params);
	
	public  List<Commodity> findByPageBySwitch(Page page, String orderByField,String orderBy,Map<String,Object> params) throws ServiceException;
}