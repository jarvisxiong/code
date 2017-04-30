package com.ffzx.order.service;

import java.util.List;
import java.util.Map;

import com.ffzx.commerce.framework.page.Page;
import com.ffzx.commerce.framework.service.BaseCrudService;
import com.ffzx.order.model.OrderRecycle;

/**
 * order_recycle数据库操作接口
 * 
 * @author ffzx
 * @date 2016-08-04 17:19:04
 * @version 1.0.0
 * @copyright www.ffzxnet.com
 */
public interface OrderRecycleService extends BaseCrudService {
	
	//分页显示列表
	List<OrderRecycle> queryByPage(Page page, Map<String, Object> params, String orderByField, String orderBy)
			throws Exception;
	
	//参数查询，无分页
	List<OrderRecycle> queryByParams(Map<String, Object> params) throws Exception;
	
	//通过签收单号查询
	List<OrderRecycle> queryByOrderNoSigned(String orderNoSigned) throws Exception;
	
	//通过签收单号查询销售单号
	List<String> querySalesNoByOrderNoSigned(String orderNoSigned) throws Exception;
	
	//查询签收单是否回收
	boolean checkStatusRecovered(OrderRecycle orderRecycle, String flag) throws Exception;
	
	//更新回收状态
	void updateStatusByBarCodeSigned(String barCodeSigned, String status, String flag) throws Exception;
	
	//查询是否有重复记录
	List<OrderRecycle> queryByParamsForpeat(Map<String, Object> params) throws Exception;

}