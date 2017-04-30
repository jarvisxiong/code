package com.ffzx.order.service;

import java.util.List;
import java.util.Map;

import com.ffzx.basedata.api.dto.Address;
import com.ffzx.commerce.framework.enums.ServiceCode;
import com.ffzx.commerce.framework.exception.ServiceException;
import com.ffzx.commerce.framework.page.Page;
import com.ffzx.commerce.framework.service.BaseCrudService;
import com.ffzx.commodity.api.dto.Category;
import com.ffzx.order.api.dto.Commodity;



/**
* @Description: TODO
* @author yuzhao.xu
* @email  yuzhao.xu@ffzxnet.com
* @date 2016年5月11日 下午4:25:32
* @version V1.0 
*
*/
public interface PurchaseAreaService extends BaseCrudService {

	
	/**
	 * 分页查询
	 * @param pageObj
	 * @param params
	 * @return
	 * @throws ServiceException
	 */
	public List<Commodity> findByPage(Page pageObj,Map<String,Object> params)throws ServiceException;
	
	/**
	 * 分页查询
	 * @param pageObj
	 * @param params
	 * @return
	 * @throws ServiceException
	 */
	public Map<String,Object> findByPage2(Page pageObj,Map<String,Object> params)throws ServiceException;
	
	/**
	 * 获取供应商集合
	 * @param page
	 * @param orderByField
	 * @param orderBy
	 * @param params
	 * @return
	 * @throws ServiceException
	 */
	public Map<String, Object> findVendorList(Page page, String orderByField, String orderBy, Map<String, Object> params) throws ServiceException;
	
	/**
	 * 获取商品集合
	 * @param pageObj
	 * @param params
	 * @return
	 * @throws ServiceException
	 */
	public List<Category> getCategoryList(Map<String,Object> params)throws ServiceException;
	
	/***
	 * 根据地址级别或者父级别获取一级子集合
	 * @param address
	 * @return
	 * @throws ServiceException
	 */
	public List<Address> getAddressApiByType(String type,String id) throws ServiceException;
	
	/***
	 * 根据地址级别或者父级别获取二级子集合
	 * @param address
	 * @return
	 * @throws ServiceException
	 */
	public List<Address> getAddressApiByTypetwo(String[] ids) throws ServiceException;
	
	/**
	 * 根据所选的地址添加到多对多表，并且更新库存的
	 * @param customAddressCodes
	 * @param wmsAddressCodes
	 * @param customUsedAddressCodes
	 * @param commodityCode
	 * @return
	 * @throws ServiceException
	 */
	public ServiceCode setArea(String customAddressCodes,String wmsAddressCodes,String customUsedAddressCodes,String commodityCode)throws ServiceException;
	
	/**
	 * 根据所选的地址生成共享的库存记录
	 * @param customAddressCodes
	 * @param wmsAddressCodes
	 * @param customUsedAddressCodes
	 * @param commodityCode
	 * @return
	 * @throws ServiceException
	 */
	public ServiceCode setAreaIsMany(String customAddressCodes,String wmsAddressCodes,String customUsedAddressCodes,String commodityCode)throws ServiceException;
	
	/**
	 * 根据导入的商品条形码和地址，自定义数量添加到多对多表，并且更新库存的
	 * @param customAddressCodes
	 * @param wmsAddressCodes
	 * @param customUsedAddressCodes
	 * @param commodityCode
	 * @return
	 * @throws ServiceException
	 */
	public ServiceCode setAreaByImportExcel(String customAddressCodes,String commodityCode,String num,int row) throws ServiceException;
	
	/**
	 * 库车初始化生成自定义，非区域行商品 
	 * @param customAddressCodes
	 * @param wmsAddressCodes
	 * @param customUsedAddressCodes
	 * @param commodityCode
	 * @return
	 * @throws ServiceException
	 */
	public ServiceCode setAreaByStockLimit(String commodityCode,String num) throws ServiceException;
	
	public ServiceCode setAreaByStockLimitBySwitch(String skuCode,String CommodityCode) throws ServiceException; 
	
}