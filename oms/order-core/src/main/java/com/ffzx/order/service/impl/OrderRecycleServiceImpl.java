package com.ffzx.order.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ffzx.commerce.framework.dao.CrudMapper;
import com.ffzx.commerce.framework.page.Page;
import com.ffzx.commerce.framework.service.impl.BaseCrudServiceImpl;
import com.ffzx.order.constant.OrderDetailStatusConstant;
import com.ffzx.order.mapper.OrderRecycleMapper;
import com.ffzx.order.model.OrderRecycle;
import com.ffzx.order.service.OrderRecycleService;

/**
 * 
 * @author ffzx
 * @date 2016-08-04 17:19:04
 * @version 1.0.0
 * @copyright www.ffzxnet.com
 */
@Service("orderRecycleService")
public class OrderRecycleServiceImpl extends BaseCrudServiceImpl implements OrderRecycleService {

	private final static Logger logger = LoggerFactory.getLogger(OrderRecycleServiceImpl.class);
	@Resource
	private OrderRecycleMapper orderRecycleMapper;

	@Override
	public CrudMapper init() {
		return orderRecycleMapper;
	}

	@Override
	public List<OrderRecycle> queryByPage(Page page, Map<String, Object> params, String orderByField, String orderBy)
			throws Exception {
		return orderRecycleMapper.selectByPage(page, orderByField, orderBy, params);
	}

	@Override
	public List<OrderRecycle> queryByParams(Map<String, Object> params) throws Exception {
		return orderRecycleMapper.selectByParams(params);
	}

	@Override
	public List<OrderRecycle> queryByOrderNoSigned(String orderNoSigned) throws Exception {
		return orderRecycleMapper.selectByOrderNoSigned(orderNoSigned);
	}
	
	@Override
	public List<String> querySalesNoByOrderNoSigned(String orderNoSigned) throws Exception {
		List<String> salesList = new ArrayList<String>();
		
		List<OrderRecycle> orderRecycleList = orderRecycleMapper.selectByOrderNoSigned(orderNoSigned);
		for (OrderRecycle orderRecycle : orderRecycleList) {
			salesList.add(orderRecycle.getSaleNo());
		}
		return salesList;
	}

	@Override
	public boolean checkStatusRecovered(OrderRecycle orderRecycle, String flag) throws Exception {
		if ("F".equals(flag)) {
			// 查询订单财务状态
			return ("已回收".equals(orderRecycle.getFinanceRecyleStatus()))
					|| (OrderDetailStatusConstant.RECOVERED_ALREADY.equals(orderRecycle.getFinanceRecyleStatus()));
		} else {
			// 查询订单物流状态
			return ("已回收".equals(orderRecycle.getLogisticsRecyleStatus()))
					|| (OrderDetailStatusConstant.RECOVERED_ALREADY
							.equals(orderRecycle.getLogisticsRecyleStatus()));
		}
	}

	@Override
	@Transactional(rollbackFor=Exception.class)
	public void updateStatusByBarCodeSigned(String barCodeSigned, String status, String flag) throws Exception {
		logger.info("barCodeSigned={},status={},flag={}", new Object[]{barCodeSigned, status, flag});
		String logisticsRecyleStatus = null;
		String financeRecyleStatus = null;
		if("L".equals(flag)){
			logisticsRecyleStatus = status;
		}else if("F".equals(flag)){
			financeRecyleStatus = status;
		}
		orderRecycleMapper.updateStatusByBarCodeSigned(barCodeSigned, status, logisticsRecyleStatus, financeRecyleStatus);
	}
	
	@Override
	public List<OrderRecycle> queryByParamsForpeat(Map<String, Object> params) throws Exception{
		return orderRecycleMapper.queryByParamsForpeat(params);
	}
}