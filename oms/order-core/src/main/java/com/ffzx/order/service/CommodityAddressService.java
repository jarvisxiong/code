/*package com.ffzx.order.service;

import java.util.List;

import com.ffzx.commerce.framework.exception.ServiceException;
import com.ffzx.commerce.framework.service.BaseCrudService;
import com.ffzx.order.api.dto.CommodityAddress;
import com.ffzx.order.api.dto.StockWms;

*//**
 * @className CommodityAddressService
 *
 * @author liujunjun
 * @date 2016-05-13 15:20:11
 * @version 1.0.0
 *//*
public interface CommodityAddressService extends BaseCrudService{
	
	*//**
	 * 根据商品编码找出所有地址编码
	 * @param CommodityCode
	 * @return
	 * @throws ServiceException
	 *//*
	public List<CommodityAddress>  getCommodityAddressByCommodityCode(String CommodityCode)throws ServiceException;
	
	*//**
	 * 根据商品编码找出所有地址编码 根据stockWmsList进行过滤
	 * @param CommodityCode
	 * @return
	 * @throws ServiceException
	 *//*
	public List<CommodityAddress>  getCommodityAddressByCommodityCode(String CommodityCode,List<StockWms> stockWmsList )throws ServiceException;
	
	*//**
	 * 根据商品编码删除该商品所有地址
	 * @param commodityCode
	 * @return
	 *//*
	public int deleteByCommodityCode(String commodityCode);
	
	*//**
	 * 通过id集,查询地址名称集合
	 * @param menu
	 * @return
	 *//*
	public String getAddressNames(String codes,String commodityCode) throws ServiceException;
	
}
*/