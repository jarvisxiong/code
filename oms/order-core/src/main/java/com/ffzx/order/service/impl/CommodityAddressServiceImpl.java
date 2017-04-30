/*package com.ffzx.order.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ffzx.commerce.framework.dao.CrudMapper;
import com.ffzx.commerce.framework.exception.ServiceException;
import com.ffzx.commerce.framework.service.impl.BaseCrudServiceImpl;
import com.ffzx.order.api.dto.CommodityAddress;
import com.ffzx.order.api.dto.StockWms;
import com.ffzx.order.mapper.CommodityAddressMapper;
import com.ffzx.order.service.CommodityAddressService;

*//**
 * @className CommodityAddressServiceImpl
 *
 * @author liujunjun
 * @date 2016-05-13 15:20:11
 * @version 1.0.0
 *//*
@Service("commodityAddressService")
public class CommodityAddressServiceImpl extends BaseCrudServiceImpl implements CommodityAddressService {

	@Resource
	private CommodityAddressMapper commodityAddressMapper;
	
	@Override
	public CrudMapper init() {
		return commodityAddressMapper;
	}

	@Override
	public List<CommodityAddress> getCommodityAddressByCommodityCode(String CommodityCode) throws ServiceException {
		return commodityAddressMapper.getCommodityAddressByCommodityCode(CommodityCode);
	}

	@Override
	public int deleteByCommodityCode(String commodityCode) {
		return commodityAddressMapper.deleteByCommodityCode(commodityCode);
	}

	@Override
	public List<CommodityAddress> getCommodityAddressByCommodityCode(String CommodityCode, List<StockWms> stockWmsList)throws ServiceException {
		List<CommodityAddress> newCommodityAddressList=new ArrayList<CommodityAddress>();
		Map<String,String> map=new HashMap<String,String>();
		List<CommodityAddress> commodityAddressList=commodityAddressMapper.getCommodityAddressByCommodityCode(CommodityCode);
		if(stockWmsList!=null && stockWmsList.size()>0){
			for(int i=0;i<stockWmsList.size();i++){
				StockWms wms=stockWmsList.get(i);
				map.put(wms.getAddressCode(), wms.getAddressCode());
			}
		}
		
		if(commodityAddressList!=null && commodityAddressList.size()>0){
			for(int j=0;j<commodityAddressList.size();j++){
				CommodityAddress commodityAddress=commodityAddressList.get(j);
				if(map.get(commodityAddress.getAddressCode())==null){
					newCommodityAddressList.add(commodityAddress);
				}
			}
		}
		
		return newCommodityAddressList;
	}

	@Override
	public String getAddressNames(String codes,String commodityCode) throws ServiceException {
		String pads = null;
		if(null!=codes){
			List<String> list = new ArrayList<String>();
			String array[] = codes.split(",");
			Collections.addAll(list, array);
			pads = commodityAddressMapper.getAddressNames(list,commodityCode);
		}
		return pads;
	}	
}*/