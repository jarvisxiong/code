package com.ffzx.order.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ffzx.commerce.framework.dao.CrudMapper;
import com.ffzx.commerce.framework.service.impl.BaseCrudServiceImpl;
import com.ffzx.order.mapper.PriceSettlementMapper;
import com.ffzx.order.model.PriceSettlement;
import com.ffzx.order.service.PriceSettlementService;

/**
 * @className PriceSettlementServiceImpl
 *
 * @author liujunjun
 * @date 2017-03-23 16:35:45
 * @version 1.0.0
 */
@Service("priceSettlementService")
public class PriceSettlementServiceImpl extends BaseCrudServiceImpl implements PriceSettlementService {

	@Resource
	private PriceSettlementMapper priceSettlementMapper;
	
	@Override
	public CrudMapper init() {
		return priceSettlementMapper;
	}

	/***
	 * 
	 * @param params
	 * @return
	 * @author yinglong.huang
	 * @email yinglong.huang@ffzxnet.com
	 * @date 2017年3月25日 下午6:30:34
	 * @version V1.0
	 * @return 
	 */
	@Override
	public List<PriceSettlement> selectUninoDeatail(Map<String, Object> params) {
		List<PriceSettlement> selectUninoDeatailList = priceSettlementMapper.selectUninoDeatail(params);
		for (PriceSettlement priceSettlement : selectUninoDeatailList) {
			priceSettlement.setPsNo(priceSettlement.getDetailList()==null?"":priceSettlement.getDetailList().size()<1?"":priceSettlement.getDetailList().get(0).getPsNo());
		}
		return selectUninoDeatailList;
	}	
}