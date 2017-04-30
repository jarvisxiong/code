package com.ffzx.order.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.ffzx.commerce.framework.annotation.MyBatisDao;
import com.ffzx.commerce.framework.dao.CrudMapper;
import com.ffzx.commerce.framework.exception.ServiceException;
import com.ffzx.commerce.framework.page.Page;
import com.ffzx.order.api.dto.Commodity;

/**
 * commodity数据库操作接口
 * 
 * @author ffzx
 * @date 2016-05-13 09:34:39
 * @version 1.0.0
 * @copyright www.ffzxnet.com
 */
@MyBatisDao
public interface CommodityMapper extends CrudMapper {
	
	public <T> T selectByCode(String code);

	public Commodity getCommoditybyskuCode(@Param("skuCode")String skuCode);
	
	/** 
	 * @param barCode
	 * @return
	 * @author wg  
	 * @time 2016年5月21日 下午6:06:10 
	 */ 
	Commodity selectByBarCode(@Param("barCode")String barCode);
	
	/** 
	 * @param params
	 * @return 查找自定义非区域商品在自定义库存表没有存在的商品记录
	 * @author wg  
	 * @time 2016年5月21日 下午6:06:10 
	 */ 
	List<Commodity> findCommodityStock(@Param("params")Map<String,Object> params);
	
	/** 
	 * @param params
	 * @return 查找自定义非区域商品在自定义库存表没有存在的商品总数
	 * @author wg  
	 * @time 2016年5月21日 下午6:06:10 
	 */ 
	int countCommodityStock(@Param("params")Map<String,Object> params);
	
	public  List<Commodity> findByPageBySwitch(@Param("page")Page page,@Param("orderByField")String orderByField,@Param("orderBy")String orderBy,@Param("params")Map<String,Object> params) throws ServiceException;
}