package com.ffzx.order.service;

import java.util.List;

import com.ffzx.commerce.framework.service.BaseCrudService;
import com.ffzx.order.api.vo.OmsOrderVo;

/**
 * oms_order_record数据库操作接口
 * 
 * @author ffzx
 * @date 2016-05-10 14:04:50
 * @version 1.0.0
 * @copyright www.ffzxnet.com
 */
public interface OmsOrderRecordService extends BaseCrudService {
	/**
	 * 
	* @Title: findLoisticsStatusByOrderNos 
	* @Description: 根据订单编号列表查询对应最新物流状态
	* @param orderNoList
	 */
	public List<OmsOrderVo> findLogisticsStatusByOrderNos(List<String> orderNoList);

}