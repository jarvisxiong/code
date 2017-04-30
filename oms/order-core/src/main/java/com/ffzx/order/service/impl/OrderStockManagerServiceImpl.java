//package com.ffzx.order.service.impl;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import org.apache.commons.lang3.StringUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import com.ffzx.basedata.api.dto.Address;
//import com.ffzx.basedata.api.service.AddressApiService;
//import com.ffzx.commerce.framework.dto.ResultDto;
//import com.ffzx.commerce.framework.exception.ServiceException;
//import com.ffzx.order.api.dto.Commodity;
//import com.ffzx.order.api.dto.StockUsed;
//import com.ffzx.order.service.CalculationStockService;
//import com.ffzx.order.service.CommodityService;
//import com.ffzx.order.service.OrderStockManagerService;
//import com.ffzx.order.service.StockCustomService;
//import com.ffzx.order.service.StockCustomUsedService;
//import com.ffzx.order.service.StockWmsService;
//import com.ffzx.order.service.StockWmsUsedService;
//import com.ffzx.order.utils.Master;
//
//@Service("orderStockManagerService")
//public class OrderStockManagerServiceImpl implements OrderStockManagerService{
//
//	// 记录日志
//	private final static Logger logger = LoggerFactory.getLogger(OrderStockManagerServiceImpl.class);
//
//	@Autowired
//	StockCustomService stockCustomService;
//
//	@Autowired
//	StockCustomUsedService stockCustomUsedService;
//
//	@Autowired
//	StockWmsService stockWmsService;
//
//	@Autowired
//	StockWmsUsedService stockWmsUsedService;
//	
//	@Autowired
//	private CommodityService commodityService;
//	
//    @Autowired
//    private CalculationStockService calculationStockService;
//    
//	@Autowired
//	private AddressApiService addressApiService;
//	
//	/**
//	 * 根据skuCode获取已占用数、用户可购买量、以及当前库存总数
//	 * @param skuCode       skuCode
//	 * @param type       '0：WMS决定商品可销售数量；1：自定义商品可销售数量'
//	 * @return
//	 * data{
//	 * 	 StockUsed对象
//	 * }
//	 * @throws ServiceException
//	 */
//	@Override
//	public StockUsed getStockbyskuCode(String skuCode, String type) throws ServiceException {
//		Map<String, Object> params = new HashMap<String, Object>();
//		StockUsed used = null;
//		if (StringUtils.isNotBlank(skuCode)){
//			params.put("skuCode", skuCode);
//		}
//		if ("0".equals(type)){
//			used = stockWmsService.findStockbyCode(params);
//		}
//		else if ("1".equals(type)){
//			used = stockCustomService.findStockbyCode(params);
//		}
//		return used;
//	}
//
//	/**
//	 * 根据skuCode获取已占用数、用户可购买量、以及当前库存总数
//	 * @param commodityCode       商品Code
//	 * @param type       '0：WMS决定商品可销售数量；1：自定义商品可销售数量'
//	 * @return
//	 * data{
//	 * 	 StockUsed对象
//	 * }
//	 * @throws ServiceException
//	 */
//	@Override
//	public StockUsed getStockbyCommodityCode(String commodityCode, String type) throws ServiceException {
//		Map<String, Object> params = new HashMap<String, Object>();
//		StockUsed used = null;
//		if (StringUtils.isNotBlank(commodityCode)){
//			params.put("commodityCode", commodityCode);
//		}
//		if ("0".equals(type)){
//			used = stockWmsService.findStockbyCode(params);
//		}
//		else if ("1".equals(type)){
//			used = stockCustomService.findStockbyCode(params);
//		}
//		return used;
//	}
//
//	/**
//	 * wms
//	 * 根据skuCode获取已占用数、用户可购买量、以及当前库存总数
//	 * @param skuCode                  skuCode
//	 * @param warehouseCode             县城(如果没有，则为空)
//	 * @param warehouseCenterCode       中央仓（如果没有，则为空）
//	 * @return
//	 * data{
//	 * 	 StockUsed对象
//	 * }
//	 * @throws ServiceException
//	 */
//	@Override
//	@Master
//	public StockUsed getWmsStockbySkuCodeAndwarehouseCode(String skuCode, String warehouseCode,
//			String warehouseCenterCode) throws ServiceException {
//		Map<String, Object> params = new HashMap<String, Object>();
//		StockUsed used = null;
//		if (StringUtils.isNotBlank(skuCode)){ 
//			params.put("skuCode", skuCode);
//			
//		}
//        List<String> list = new ArrayList<String>();
//		if (StringUtils.isNotBlank(warehouseCode)){ 
//			list.add(warehouseCode);
//		}
//		if (StringUtils.isNotBlank(warehouseCenterCode)){ 
//			list.add(warehouseCenterCode);
//		}
//        if (null != list && list.size() > 0 ){
//        	params.put("warehouseCode", list);
//        }
//		used = stockWmsService.findStockbyCodeAndWarehouse(params);
//		return used;
//	}
//
//	/**
//	 * 自定义
//	 * 根据skuCode获取已占用数、用户可购买量、以及当前库存总数
//	 * @param skuCode                  skuCode
//	 * @param addressCode              地址Code(如果没有，则为空)
//	 * @return
//	 * data{
//	 * 	 StockUsed对象
//	 * }
//	 * @throws ServiceException
//	 */
//	@Override
//	@Master
//	public StockUsed getCustomStockbySkuCode(String skuCode, String addressCode) throws ServiceException {
//		Map<String, Object> params = new HashMap<String, Object>();
//		StockUsed used = null;
//		if (StringUtils.isNotBlank(skuCode)){
//			params.put("skuCode", skuCode);
//		}
//		if (StringUtils.isNotBlank(addressCode)){
//			params.put("addressCode", addressCode);
//		}
//		used = stockCustomService.findStockbyCodeAndAddressCode(params);
//		return used;
//	}
//
//	/**
//	 * wms
//	 * 根据商品Code获取已占用数、用户可购买量、以及当前库存总数
//	 * @param skuCode                  skuCode
//	 * @param warehouseCode             县城(如果没有，则为空)
//	 * @param warehouseCenterCode       中央仓（如果没有，则为空）
//	 * @return
//	 * data{
//	 * 	 StockUsed对象
//	 * }
//	 * @throws ServiceException
//	 */
//	@Override
//	public StockUsed getWmsStockbyCommodityCodeAndwarehouseCode(String commodityCode, String warehouseCode,
//			String warehouseCenterCode) throws ServiceException {
//		Map<String, Object> params = new HashMap<String, Object>();
//		StockUsed used = null;
//		if (StringUtils.isNotBlank(commodityCode)){
//			params.put("commodityCode", commodityCode);
//		}
//        
//		List<String> list = new ArrayList<String>();
//		if (StringUtils.isNotBlank(warehouseCode)){ 
//			list.add(warehouseCode);
//		}
//		if (StringUtils.isNotBlank(warehouseCenterCode)){ 
//			list.add(warehouseCenterCode);
//		}
//        if (null != list && list.size() > 0 ){
//        	params.put("warehouseCode", list);
//        }
//		used = stockWmsService.findStockbyCodeAndWarehouse(params);
//		return used;
//	}
//
//	/**
//	 * 自定义
//	 * 根据商品Code获取已占用数、用户可购买量、以及当前库存总数
//	 * @param skuCode                  skuCode
//	 * @param addressCode              地址Code(如果没有，则为空)
//	 * @return
//	 * data{
//	 * 	 StockUsed对象
//	 * }
//	 * @throws ServiceException
//	 */
//	@Override
//	public StockUsed getCustomStockbyCommodityCodeAndAddressCode(String commodityCode, String addressCode)
//			throws ServiceException {
//		Map<String, Object> params = new HashMap<String, Object>();
//		StockUsed used = null;
//		if (StringUtils.isNotBlank(commodityCode)){
//			params.put("commodityCode", commodityCode);
//		}
//		if (StringUtils.isNotBlank(addressCode)){
//			params.put("addressCode", addressCode);
//		}
//		used = stockCustomService.findStockbyCodeAndAddressCode(params);
//		return used;
//	}
//
//	/**
//	 * 获取已占用数、用户可购买量、以及当前库存总数
//	 * 注：如果没有查询到该库存，（非区域性设置为0：表示为没有库存，区域性设置为-1：设置为不买）
//	 * @param skuCode       商品skuCode
//	 * @param commodity     skuCode所对应的商品对象
//	 * @param address       地址对象：该对象
//	 * @return
//	 * data{
//	 * 	 StockUsed对象
//	 * }
//	 * @throws ServiceException
//	 */
//	@Override
//	public ResultDto getInfobyskuCodeAndaddress(String skuCode,Commodity commodity, Address address) throws ServiceException {
//		ResultDto rsDto = null;
//		if (StringUtils.isBlank(skuCode)) {
//			throw new ServiceException("faild: skuCode is null");
//		}
//		// 获取商品信息
//		if (null == commodity){
//			commodity = commodityService.getCommoditybyskuCode(skuCode);
//			if (null == commodity) {
//				throw new ServiceException("faild: commodity is null");
//			}
//		}
//		if (null == address){
//			logger.info("getInfobyskuCodeAndaddress==========================获取地址失败；skuCode=" + skuCode);
//			throw new ServiceException("faild: address is null");
//		}
//		StockUsed used = null;
//		// WMS决定商品可销售数量
//		if (commodity.getStockLimit().equals("0")) {
//			String warehouseCode = address.getWarehouseCode();
//			if (StringUtils.isBlank(warehouseCode)){
//				logger.info("getInfobyskuCodeAndaddress==========================获取仓库为空；skuCode=" + skuCode + "addressId=" + address.getId());
//				throw new ServiceException("faild: warehouseCode(address Object) is null");
//			}
//            String centerWarehouseCode = calculationStockService.getMiddleWarehouse(address.getWarehouseCode()).getCode();
//			if (centerWarehouseCode.equals(warehouseCode)){
//				centerWarehouseCode = null;
//			}
//			used = this.getWmsStockbySkuCodeAndwarehouseCode(skuCode, warehouseCode, centerWarehouseCode);
//		}
//		// 自定义商品可销售数量'
//		else if (commodity.getStockLimit().equals("1")) {
//			String addressCode = address.getAddressCode();
//			if ("1".equals(commodity.getAreaCategory())){
//				addressCode = null;
//			}
//			used = this.getCustomStockbySkuCode(skuCode, addressCode);
//		}
//		
//		//非区域性设置为0：设置为没有库存，区域性设置为-1：设置为不买
//		if (null == used || StringUtils.isBlank(used.getCurrStockNum()))
//		{
//			used = new StockUsed();
//			used.setCurrStockNum("0");
//			used.setStockUsedCount("0");
//			used.setUserCanBuy("0");
//			if ("0".equals(commodity.getAreaCategory()))
//			{
//				used.setCurrStockNum("-1");
//				used.setStockUsedCount("-1");
//				used.setUserCanBuy("-1");
//			}
//		}else {
//			used.setUserCanBuy("0");
//			if (StringUtils.isBlank(used.getStockUsedCount()))
//			{
//				used.setUserCanBuy(used.getCurrStockNum());
//				used.setStockUsedCount("0");
//			}
//			else
//			{
//				long canbuy = Long.parseLong(used.getCurrStockNum())-Long.parseLong(used.getStockUsedCount());
//				used.setUserCanBuy(String.valueOf(canbuy));
//			}
//		}
//		rsDto = new ResultDto(ResultDto.OK_CODE, "success");
//		rsDto.setData(used);
//		return rsDto;
//	}
//
//	/**
//	 * 根据地址id获取符合正确的地址对象
//	 * @param addressId
//	 * @return
//	 * @throws ServiceException
//	 */
//	@Override
//	public ResultDto  verifyAddressbyAddressId(String addressId) throws ServiceException{
//		ResultDto result = addressApiService.getAddressParentAreaById(addressId);
//		Address address = null;
//		if (result.getCode().equals(ResultDto.OK_CODE)) {
//			address = (Address) result.getData();
//			//地址禁用、或者删除返回空值
//			if (null == address || "1".equals(address.getActFlag()) || "1".equals(address.getDelFlag()))
//			{
//				result = new ResultDto(ResultDto.ERROR_CODE, "address is disabled or deleted or is not the address");
//				return result;
//			}
//	     }
//		return result;
//	}
//}
