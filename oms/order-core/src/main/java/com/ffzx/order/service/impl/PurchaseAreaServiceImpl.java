/*package com.ffzx.order.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ffzx.basedata.api.dto.Address;
import com.ffzx.basedata.api.service.AddressApiService;
import com.ffzx.commerce.framework.constant.Constant;
import com.ffzx.commerce.framework.dao.CrudMapper;
import com.ffzx.commerce.framework.dto.ResultDto;
import com.ffzx.commerce.framework.enums.ServiceCode;
import com.ffzx.commerce.framework.enums.ServiceResultCode;
import com.ffzx.commerce.framework.exception.ServiceException;
import com.ffzx.commerce.framework.page.Page;
import com.ffzx.commerce.framework.service.impl.BaseCrudServiceImpl;
import com.ffzx.commerce.framework.system.entity.SysUser;
import com.ffzx.commerce.framework.utils.RedisWebUtils;
import com.ffzx.commerce.framework.utils.StringUtil;
import com.ffzx.commerce.framework.utils.UUIDGenerator;
import com.ffzx.commodity.api.dto.Category;
import com.ffzx.commodity.api.service.CategoryApiService;
import com.ffzx.commodity.api.service.CommodityApiService;
import com.ffzx.member.api.service.VendorApiService;
import com.ffzx.order.api.dto.Commodity;
import com.ffzx.order.api.dto.CommodityAddress;
import com.ffzx.order.api.dto.CommoditySku;
import com.ffzx.order.api.dto.StockCustom;
import com.ffzx.order.api.dto.StockCustomUsed;
import com.ffzx.order.common.OmsServiceResultCode;
import com.ffzx.order.constant.StockCustomAndWmsConstant;
import com.ffzx.order.service.CommodityAddressService;
import com.ffzx.order.service.CommodityService;
import com.ffzx.order.service.CommoditySkuService;
import com.ffzx.order.service.PurchaseAreaService;
import com.ffzx.order.service.StockCustomService;
import com.ffzx.order.service.StockCustomUsedService;
import com.ffzx.order.service.StockWmsService;
import com.ffzx.wms.api.dto.Warehouse;
import com.ffzx.wms.api.service.WarehouseApiService;

*//**
 * 
 * @author yuzhao.xu
 * @email  yuzhao.xu@ffzxnet.com
 * @date 2016年5月11日 下午4:25:32
 * @copyright www.ffzxnet.com
 *//*
@Service("purchaseAreaService")
public class PurchaseAreaServiceImpl extends BaseCrudServiceImpl implements PurchaseAreaService {


    
    @Autowired
    private AddressApiService addressApiService;
    
    @Autowired
    private CommodityService commodityService;   
    
    @Autowired
    private CommoditySkuService commoditySkuService;  
    
    @Autowired
    private CommodityApiService commodityApiService;
    
    @Autowired
    private CommodityAddressService commodityAddressService;
    
    @Autowired
    private CategoryApiService categoryApiService;
    
    @Autowired
    private StockCustomService stockCustomService;
    
    @Autowired
    private StockCustomUsedService stockCustomUsedService;
    
    @Autowired
    private StockWmsService stockWmsService;
    
    @Resource
	public VendorApiService vendorApiService;
    
    
    @Resource
	private WarehouseApiService warehouseApiService;
    
	*//**
	 * 分页查询
	 * @param pageObj
	 * @param params
	 * @return
	 * @throws ServiceException
	 *//*
	@Override
	public List<Commodity> findByPage(Page pageObj, Map<String, Object> params) throws ServiceException {
		List<Commodity> newCommodityList=new ArrayList<Commodity>();
		
		List<Commodity> commodityList=commodityService.findByPage(pageObj, Constant.ORDER_BY_FIELD, null, params);
		if(commodityList!=null && commodityList.size()>0){
			for(int i=0;i<commodityList.size();i++){
				Commodity commodity=commodityList.get(i);
				if (StringUtil.isNotNull(commodity.getAreaCategory()) && "1".equals(commodity.getAreaCategory())) {
					commodity.setAddressName("所有区域");
				}
				if("0".equals(commodity.getStockLimit()) && StringUtil.isNotNull(commodity.getAreaCategory()) && "0".equals(commodity.getAreaCategory())){//0:wms,1:自定义
					String warehouseName = stockWmsService.getWarehouseName(commodity.getCode());
					commodity.setAddressName(warehouseName);
				}else if("0".equals(commodity.getStockLimit()) && StringUtil.isNotNull(commodity.getAreaCategory()) && "1".equals(commodity.getAreaCategory())){
					commodity.setAddressName("所有区域");
				}
				
				if("0".equals(commodity.getStockLimit())){
					
				}else{
					List<CommodityAddress>	caList=commodityAddressService.getCommodityAddressByCommodityCode(commodity.getCode());
					String addressCodes="";
					if(caList!=null && caList.size()>0){
						for(int j=0;j<caList.size();j++){
							addressCodes+=caList.get(j).getAddressCode()+",";
						}
						addressCodes=addressCodes.substring(0, addressCodes.length()-1);
						String addressNanme=commodityAddressService.getAddressNames(addressCodes,commodity.getCode());
						commodity.setAddressName(addressNanme);
					}
				}
				newCommodityList.add(commodity);
			}
		}
		return newCommodityList;
	}
	
	
	@Override
	public Map<String, Object> findByPage2(Page pageObj, Map<String, Object> params) throws ServiceException {
		
		List<Commodity> newCommodityList=new ArrayList<Commodity>();
		
		ResultDto rd=commodityApiService.findList(pageObj, Constant.ORDER_BY_FIELD, null, params);
		Map<String,Object> map=(Map<String,Object>)rd.getData();
		List<Commodity> commodityList =(List<Commodity>)map.get("list");
		if(commodityList!=null && commodityList.size()>0){
			for(int i=0;i<commodityList.size();i++){
				Commodity commodity=commodityList.get(i);
				if("0".equals(commodity.getStockLimit())){
					
				}else{
					List<CommodityAddress>	caList=commodityAddressService.getCommodityAddressByCommodityCode(commodity.getCode());
					String commodityIds="";
					if(caList!=null && caList.size()>0){
						for(int j=0;j<caList.size();j++){
							commodityIds+=caList.get(j).getAddressCode()+",";
						}
						commodityIds=commodityIds.substring(0, commodityIds.length()-1);
						ResultDto resultDto=addressApiService.getAddressNames(commodityIds);
						String addressNanme=(String)resultDto.getData();
						commodity.setAddressName(addressNanme);
					}
				}
				newCommodityList.add(commodity);
				
			}
		}
		map.put("list", newCommodityList);
		return map;
	}
	
	
	@Override
	public CrudMapper init() {
		return null;
	}
	
	 (non-Javadoc)
	 * @see com.ffzx.order.service.PurchaseAreaService#findVendorList(com.ffzx.commerce.framework.page.Page, java.lang.String, java.lang.String, java.util.Map)
	 
	@Override
	public Map<String, Object> findVendorList(Page page, String orderByField,String orderBy, Map<String, Object> params) throws ServiceException {
		ResultDto rd =	vendorApiService.findList(page, orderByField, orderBy, params);
		Map<String, Object> vendorMap=null;
		if(rd.getData()!=null){
			vendorMap= (Map<String, Object>)rd.getData();
		}
		return vendorMap;
	}

	*//**
	 * 获取商品集合
	 * @param pageObj
	 * @param params
	 * @return
	 * @throws ServiceException
	 *//*
	@Override
	public List<Category> getCategoryList(Map<String, Object> params) throws ServiceException {
		ResultDto rd=categoryApiService.getList(params);  
		List<Category> categoryList=(List<Category>)rd.getData();
		return categoryList;
	}


	@Override
	public List<Address> getAddressApiByType(String type, String id) throws ServiceException {
		ResultDto rd= addressApiService.getAddressApiByType("1","0");
		List<Address> oneressList = (List<Address>)rd.getData();		
		return oneressList;
	}


	@Override
	public List<Address> getAddressApiByTypetwo(String[] ids) throws ServiceException {
		ResultDto rd= addressApiService.getAddressApiByTypetwo(ids);
		List<Address> tworessList = (List<Address>)rd.getData();
		return tworessList;
	}


	*//**
	 * 根据导入的商品条形码和地址，自定义数量添加到多对多表，并且更新库存的
	 * @param customAddressCodes
	 * @param wmsAddressCodes
	 * @param customUsedAddressCodes
	 * @param commodityCode
	 * @return
	 * @throws ServiceException
	 *//*
	@Override
	@Transactional(rollbackFor=Exception.class)
	public ServiceCode setArea(String customAddressCodes, String wmsAddressCodes, String customUsedAddressCodes,String commodityCode) throws ServiceException {
		List<CommodityAddress> commodityAddressList = new ArrayList<CommodityAddress>();
		List<StockCustom> stockCustomInsertList = new ArrayList<StockCustom>();
		List<StockCustom> stockCustomUpdatetList = new ArrayList<StockCustom>();
		List<StockCustomUsed> stockCustomUsedUpdatetList = new ArrayList<StockCustomUsed>();
		
		Map<String,String> currAddressMap=new HashMap<String,String>();
		
		Map<String,Object> params=new HashMap<String,Object>();
		
		String addressCodeIsMany = "";
		String addressNameIsMany = "";
		String warehouseCodesIsMany= "";
		String warehouseNamesIsMany = "";
		String skuCode="";
		
		String addressCodes=""; 
		if(StringUtil.isNotNull(customAddressCodes)){
			addressCodes+=customAddressCodes+",";
		}
		if(StringUtil.isNotNull(wmsAddressCodes)){
			addressCodes+=wmsAddressCodes+",";
		}
		if(StringUtil.isNotNull(customUsedAddressCodes)){
			addressCodes+=customUsedAddressCodes+",";
		}
		SysUser sysUser = RedisWebUtils.getLoginUser();
		
		Date date=new Date();
		
		if(StringUtil.isNotNull(addressCodes)){
			addressCodes=addressCodes.substring(0,addressCodes.length()-1);
			String[] addressCodeArray=addressCodes.split(",");
			Commodity commodity = commodityService.getByCode(commodityCode);
			params.put("commodityCode", commodity.getCode());
			params.put("stockLimit", StockCustomAndWmsConstant.STOCK_LIMIT_CUSTOM);
			params.put("areaCategory", StockCustomAndWmsConstant.AREA_CATEGORY);
			List<CommoditySku> skuList=commoditySkuService.getCommoditySkuByParams(params);
			if(skuList.size()<=0){
				return OmsServiceResultCode.NO_SKU;
			}
			for(int i=0;i<addressCodeArray.length;i++){
				if(currAddressMap.get(addressCodeArray[i])==null){
					
					CommodityAddress commodityAddress=new CommodityAddress();
					commodityAddress.setId(UUIDGenerator.getUUID());
					commodityAddress.setCommodityCode(commodityCode);
					commodityAddress.setAddressCode(addressCodeArray[i]);
					ResultDto rd = addressApiService.getAddressByCode(commodityAddress.getAddressCode());
					Address address=(Address)rd.getData();
					commodityAddress.setAddressName(address.getName());
					commodityAddressList.add(commodityAddress);
					
					addressCodeIsMany+=addressCodeArray[i]+",";
					addressNameIsMany+=address.getName()+",";
					if(!warehouseCodesIsMany.contains(address.getWarehouseCode())){
						warehouseCodesIsMany+=StringUtil.isNotNull(address.getWarehouseCode())==true?address.getWarehouseCode()+",":"";
						warehouseNamesIsMany+=StringUtil.isNotNull(address.getWarehouseName())==true?address.getWarehouseName()+",":"";
					}
					
					currAddressMap.put(addressCodeArray[i], addressCodeArray[i]);
					
					for(int j=0;j<skuList.size();j++){
						CommoditySku sku=skuList.get(j);
						String addressCode=commodityAddress.getAddressCode();
						String commoditySkuCode=sku.getSkuCode();
						StockCustom stockCustomNew=stockCustomService.findStockCustomBySkuCodeAndAddressCode(commoditySkuCode, addressCode);
						//根据当前所选的地址编码和sku编码去自定义库存查找相应记录没有则新增记录
						//如果存在则去检查该记录的状态是否被禁用，如果被禁用就要修改状态为启动
						if(stockCustomNew==null){
							if(Constant.ACT_FLAG_NO.equals(sku.getActFlag())){
								continue;
							}
							StockCustom sc=new StockCustom();
							sc.setId(UUIDGenerator.getUUID());
							sc.getCommodity().setCode(commodityCode); //商品
							sc.getCommoditySku().setSkuCode(commoditySkuCode); //sku编码
							String warehouse="";
							if(address!=null){
								sc.setWarehouseCode(address.getWarehouseCode());
								sc.setWarehouseName(address.getWarehouseName());
								sc.setAddressCode(address.getAddressCode());
								sc.setAddressName(address.getName());
								sc.setAddressNameOrWarehouseName(address.getName());
								warehouse=address.getWarehouseCode();
							}
//							StockWms wms = stockWmsService.findStockWmsBySkuCodeAndWarehouseCode(commoditySkuCode, warehouse);
//							if(wms!=null){
//								sc.setCurrStockNum(wms.getCurrStockNum());
//							}else{
//								sc.setCurrStockNum("0");
//							}
							sc.setCurrStockNum("0");
							sc.setActFlag(Constant.ACT_FLAG_YES);
							StockCustomUsed used=new StockCustomUsed();
							used.setId("");
							sc.setLastUpdateDate(date);
							sc.setLastUpdateBy(sysUser);
							sc.setCreateBy(sysUser);
							sc.setCreateDate(date);
							sc.setStockCustomUsed(used);
							stockCustomInsertList.add(sc);
						}else{
							if(Constant.ACT_FLAG_NO.equals(stockCustomNew.getActFlag())){
								stockCustomNew.setActFlag(Constant.ACT_FLAG_YES);
							}
							stockCustomNew.setLastUpdateDate(date);
							stockCustomNew.setLastUpdateBy(sysUser);
							StockCustomUsed used=stockCustomUsedService.findById(stockCustomNew.getStockCustomUsed().getId());
							if(used!=null){
								if(Constant.ACT_FLAG_NO.equals(used.getActFlag())){
									used.setActFlag(Constant.ACT_FLAG_YES);
									used.setStockUsedCount("0");
								}
								used.setLastUpdateDate(date);
								used.setLastUpdateBy(sysUser);
								stockCustomUsedUpdatetList.add(used);
							}
							stockCustomUpdatetList.add(stockCustomNew);
						}
						
						//查找当前sku共享库存记录，存在则禁用。
						if(currAddressMap.get(commoditySkuCode)==null){
							skuCode+=commoditySkuCode+",";
							currAddressMap.put(commoditySkuCode,commoditySkuCode);
						}
						
					}
				}
			}
		}
		
		//查找当前sku共享库存记录，存在则禁用。
		if(StringUtil.isNotNull(skuCode)){
			skuCode = skuCode.substring(0, skuCode.length()-1);
			String[] skuCodes=skuCode.split(",");
			for(int i=0;i<skuCodes.length;i++){
				String commoditySkuCode=skuCodes[i];
				StockCustom stockCustomIsMany = stockCustomService.findStockCustomByskuCodeAndIsMany(commoditySkuCode);
				if(stockCustomIsMany!=null){
					stockCustomIsMany.setActFlag(Constant.ACT_FLAG_NO);
					stockCustomIsMany.setLastUpdateDate(date);
					stockCustomIsMany.setLastUpdateBy(sysUser);
					stockCustomIsMany.setCurrStockNum("0");
					if(StringUtil.isNotNull(addressCodes)){
						String addressCode=addressCodeIsMany.substring(0, addressCodeIsMany.length()-1);
						stockCustomIsMany.setAddressCode(addressCode);
						
						String addressName=addressNameIsMany.substring(0, addressNameIsMany.length()-1);
						stockCustomIsMany.setAddressName(addressName);
						
						String warehouseCodes=warehouseCodesIsMany.substring(0, warehouseCodesIsMany.length()-1);
						stockCustomIsMany.setWarehouseCode(warehouseCodes);
						
						String warehouseNames=warehouseNamesIsMany.substring(0, warehouseNamesIsMany.length()-1);
						stockCustomIsMany.setWarehouseName(warehouseNames);
					
						String addressNameOrWarehouseName = addressNameIsMany.substring(0, addressNameIsMany.length()-1);
						stockCustomIsMany.setAddressNameOrWarehouseName(addressNameOrWarehouseName);
					}
					stockCustomUpdatetList.add(stockCustomIsMany);
					StockCustomUsed stockCustomUsedIsMany = stockCustomUsedService.findById(stockCustomIsMany.getStockCustomUsed().getId());
					if(stockCustomUsedIsMany!=null){
						stockCustomUsedIsMany.setActFlag(Constant.ACT_FLAG_NO);
						if(StringUtil.isNotNull(stockCustomIsMany.getAddressCode())){
							String warehouseCodesUsed=warehouseCodesIsMany.substring(0, warehouseCodesIsMany.length()-1);
							stockCustomUsedIsMany.setWarehouseCode(warehouseCodesUsed);
							
							String addressCodesUsed=warehouseCodesIsMany.substring(0, warehouseCodesIsMany.length()-1);
							stockCustomUsedIsMany.setAddressCode(addressCodesUsed);
						}
						stockCustomUsedIsMany.setLastUpdateDate(date);
						stockCustomUsedIsMany.setLastUpdateBy(sysUser);
						stockCustomUsedIsMany.setCurrStockNum("0");
						stockCustomUsedUpdatetList.add(stockCustomUsedIsMany);
					}
				}
			}
		}
		
		
		
		//1.先到db查找之前所绑定该商品的所有地址
		//2.遍历所有db的地址，再拿新勾选的地址做比对，如果db的地址，在新勾选的地址里面存在则不操作
		//  如果不存在则查出该商品下的这个地址下的所有自定义库存数据并且把状态修改为禁用
//		Map<String,Object> stockIsManyMap = new HashMap<String,Object>();
		List<CommodityAddress>	dbcommodityAddressList=commodityAddressService.getCommodityAddressByCommodityCode(commodityCode);
		if(dbcommodityAddressList!=null && dbcommodityAddressList.size()>0){
			for(int i=0;i<dbcommodityAddressList.size();i++){
				CommodityAddress dbca=dbcommodityAddressList.get(i);
				if(currAddressMap.get(dbca.getAddressCode())==null){
					List<StockCustom> stockCustomOldList=stockCustomService.findStockCustomByCommodityCodeAndAddressCode(commodityCode, dbca.getAddressCode());
					if(stockCustomOldList!=null && stockCustomOldList.size()>0){
						for(StockCustom stockCustom:stockCustomOldList){
							Map<String,Object> params1 = new HashMap<String,Object>();
							params1.put("skuCode", stockCustom.getCommoditySku().getSkuCode());
							params1.put("actFlag", Constant.ACT_FLAG_YES);
							params1.put("addressCode",stockCustom.getAddressCode());
							int num = stockCustomUsedService.countStockCustomUsed(params1);
							if(num>0){
								return OmsServiceResultCode.REMOVE_ADDRESS;
							}
							stockCustom.setActFlag(Constant.ACT_FLAG_NO);
							stockCustom.setLastUpdateDate(date);
							stockCustom.setLastUpdateBy(sysUser);
							stockCustom.setCurrStockNum("0");
							StockCustomUsed used=stockCustomUsedService.findById(stockCustom.getStockCustomUsed().getId());
							if(used!=null){
								used.setActFlag(Constant.ACT_FLAG_NO);
								used.setLastUpdateDate(date);
								used.setLastUpdateBy(sysUser);
								used.setCurrStockNum("0");
								stockCustomUsedUpdatetList.add(used);
							}
							stockCustomUpdatetList.add(stockCustom);
//							StockCustom stockCustomIsMany = stockCustomService.findStockCustomByskuCodeAndIsMany(stockCustom.getCommoditySku().getSkuCode());
//							if(stockCustomIsMany!=null){
//								ResultDto rd = addressApiService.getAddressByCode(dbca.getAddressCode());
//								Address address=(Address)rd.getData();
//								if(StringUtil.isNotNull(stockCustomIsMany.getAddressCode())){
//									String addressCode=stockCustomIsMany.getAddressCode();
//									addressCode=addressCode.replace(address.getAddressCode(), "");
//								}
//							}
						}
					}
				}
			}
		}
		
		//1.先删该商品下所有旧的地址数据
		commodityAddressService.deleteByCommodityCode(commodityCode);
		
		//2.更新所有状态
		if(stockCustomUpdatetList.size()>0){
			for(StockCustom stockCustom:stockCustomUpdatetList){
				stockCustomService.modifyById(stockCustom);
			}
		}
		
		//如果对应的占用表存在则更改状态
		if(stockCustomUsedUpdatetList.size()>0){
			for(StockCustomUsed used:stockCustomUsedUpdatetList){
				stockCustomUsedService.modifyById(used);
			}
		}
		
		//添加新的sku库存地址
		if(stockCustomInsertList.size()>0){
			for(StockCustom stockCustom:stockCustomInsertList){
				stockCustomService.add(stockCustom);
			}
		}
		
		//拿新勾选的地址重新绑定商品
		if(commodityAddressList.size()>0){
			for(CommodityAddress commodityAddress:commodityAddressList){
				commodityAddressService.add(commodityAddress);
			}
		}
		
		return ServiceResultCode.SUCCESS;
	}
	
	
	*//**
	 * 根据所选的地址添加到多对多表，并且更新库存的
	 * @param customAddressCodes
	 * @param wmsAddressCodes
	 * @param customUsedAddressCodes
	 * @param commodityCode
	 * @return
	 * @throws ServiceException
	 *//*
	@Override
	@Transactional(rollbackFor=Exception.class)
	public ServiceCode setAreaByImportExcel(String customAddressCodes,String commodityCode,String num,int row) throws ServiceException {
		
		StringBuilder sb=new StringBuilder();
		
		List<CommodityAddress> commodityAddressList = new ArrayList<CommodityAddress>();
		List<StockCustom> stockCustomInsertList = new ArrayList<StockCustom>();
		List<StockCustom> stockCustomUpdatetList = new ArrayList<StockCustom>();
		List<StockCustomUsed> stockCustomUsedUpdatetList = new ArrayList<StockCustomUsed>();
		
//		Map<String,String> codeMap=new HashMap<String,String>();
		Map<String,String> currAddressMap=new HashMap<String,String>();
		
		Commodity commodity = null;
		Map<String,Object> params=new HashMap<String,Object>();
		params.put("code", commodityCode);
	 	List<Commodity> commodityList=commodityService.findByBiz(params);
	 	if(commodityList!=null && commodityList.size()>0){
	 		commodity = commodityList.get(0);
	 	}
	 	
		if(commodity!=null && "0".equals(commodity.getAreaCategory())){ // 1:非区域性 ，0 区域性
		
			String addressCodes=""; 
			if(StringUtil.isNotNull(customAddressCodes)){
				addressCodes+=customAddressCodes+",";
			}
			SysUser sysUser = RedisWebUtils.getLoginUser();
			if(StringUtil.isNotNull(addressCodes)){
				addressCodes=addressCodes.substring(0,addressCodes.length()-1);
				String[] addressCodeArray=addressCodes.split(",");
				List<CommoditySku> skuList=commoditySkuService.getCommoditySkuByCode(commodityCode);
				if(skuList.size()<=0){
					return OmsServiceResultCode.NO_SKU;
				}
				for(int i=0;i<addressCodeArray.length;i++){
					if(currAddressMap.get(addressCodeArray[i])==null){
						CommodityAddress commodityAddress=new CommodityAddress();
						commodityAddress.setId(UUIDGenerator.getUUID());
						commodityAddress.setCommodityCode(commodityCode);
						commodityAddress.setAddressCode(addressCodeArray[i]);
						ResultDto rd = addressApiService.getAddressByCode(commodityAddress.getAddressCode());
						Address address=(Address)rd.getData();
						commodityAddress.setAddressName(address.getName());
						commodityAddressList.add(commodityAddress);
						currAddressMap.put(addressCodeArray[i], addressCodeArray[i]);
						
						for(int j=0;j<skuList.size();j++){
							CommoditySku sku=skuList.get(j);
							String addressCode=commodityAddress.getAddressCode();
							String commoditySkuCode=sku.getSkuCode();
							StockCustom stockCustomNew=stockCustomService.findStockCustomBySkuCodeAndAddressCode(commoditySkuCode, addressCode);
							//根据当前所选的地址编码和sku编码去自定义库存查找相应记录没有则新增记录
							//如果存在则去检查该记录的状态是否被禁用，如果被禁用就要修改状态为启动
							if(stockCustomNew==null){
								StockCustom sc=new StockCustom();
								sc.setId(UUIDGenerator.getUUID());
								sc.getCommodity().setCode(commodityCode); //商品
								sc.getCommoditySku().setSkuCode(commoditySkuCode); //sku编码
								String warehouse="";
								if(address!=null){
									sc.setWarehouseCode(address.getWarehouseCode());
									sc.setWarehouseName(address.getWarehouseName());
									sc.setAddressCode(address.getAddressCode());
									sc.setAddressName(address.getName());
									sc.setAddressNameOrWarehouseName(address.getName());
									warehouse=address.getWarehouseCode();
								}
	//							StockWms wms = stockWmsService.findStockWmsBySkuCodeAndWarehouseCode(commoditySkuCode, warehouse);
	//							if(wms!=null){
	//								sc.setCurrStockNum(wms.getCurrStockNum());
	//							}else{
	//								sc.setCurrStockNum(num);
	//							}
								sc.setCurrStockNum(num);
								sc.setActFlag(Constant.ACT_FLAG_YES);
								StockCustomUsed used=new StockCustomUsed();
								used.setId("");
								sc.setLastUpdateDate(new Date());
								sc.setLastUpdateBy(sysUser);
								sc.setCreateBy(sysUser);
								sc.setCreateDate(new Date());
								sc.setStockCustomUsed(used);
								stockCustomInsertList.add(sc);
							}else{
								if(Constant.ACT_FLAG_NO.equals(stockCustomNew.getActFlag())){
									stockCustomNew.setActFlag(Constant.ACT_FLAG_YES);
									stockCustomNew.setLastUpdateDate(new Date());
									stockCustomNew.setLastUpdateBy(sysUser);
									StockCustomUsed used=stockCustomUsedService.findById(stockCustomNew.getStockCustomUsed().getId());
									if(used!=null){
										used.setActFlag(Constant.ACT_FLAG_YES);
										used.setLastUpdateDate(new Date());
										used.setLastUpdateBy(sysUser);
										stockCustomUsedUpdatetList.add(used);
									}
									stockCustomUpdatetList.add(stockCustomNew);
								}
							}
						}
					}
				}
			}
			
			
			//1.先到db查找之前所绑定该商品的所有地址
			//2.遍历所有db的地址，再拿新勾选的地址做比对，如果db的地址，在新勾选的地址里面存在则不操作
			//  如果不存在则查出该商品下的这个地址下的所有自定义库存数据并且把状态修改为禁用
			List<CommodityAddress>	dbcommodityAddressList=commodityAddressService.getCommodityAddressByCommodityCode(commodityCode);
			if(dbcommodityAddressList!=null && dbcommodityAddressList.size()>0){
				for(int i=0;i<dbcommodityAddressList.size();i++){
					CommodityAddress dbca=dbcommodityAddressList.get(i);
					if(currAddressMap.get(dbca.getAddressCode())==null){
						List<StockCustom> stockCustomOldList=stockCustomService.findStockCustomByCommodityCodeAndAddressCode(commodityCode, dbca.getAddressCode());
						if(stockCustomOldList!=null && stockCustomOldList.size()>0){
							for(StockCustom stockCustom:stockCustomOldList){
								stockCustom.setActFlag(Constant.ACT_FLAG_NO);
								stockCustom.setLastUpdateDate(new Date());
								stockCustom.setLastUpdateBy(sysUser);
								StockCustomUsed used=stockCustomUsedService.findById(stockCustom.getStockCustomUsed().getId());
								if(used!=null){
									used.setActFlag(Constant.ACT_FLAG_NO);
									used.setLastUpdateDate(new Date());
									used.setLastUpdateBy(sysUser);
									stockCustomUsedUpdatetList.add(used);
								}
								stockCustomUpdatetList.add(stockCustom);
							}
						}
					}
				}
			}
		}else{//判断是否区域性 end
			setAreaByStockLimit(commodityCode,num);
		}   
		
		
		//1.先删该商品下所有旧的地址数据
		commodityAddressService.deleteByCommodityCode(commodityCode);
		
		//2.更新所有状态
		if(stockCustomUpdatetList.size()>0){
			for(StockCustom stockCustom:stockCustomUpdatetList){
				stockCustomService.modifyById(stockCustom);
			}
		}
		
		//如果对应的占用表存在则更改状态
		if(stockCustomUsedUpdatetList.size()>0){
			for(StockCustomUsed used:stockCustomUsedUpdatetList){
				stockCustomUsedService.modifyById(used);
			}
		}
		
		//添加新的sku库存地址
		if(stockCustomInsertList.size()>0){
			for(StockCustom stockCustom:stockCustomInsertList){
				stockCustomService.add(stockCustom);
				stockCustomService.insertLog(stockCustom.getCurrStockNum(), stockCustom.getId());
			}
		}
		
		//拿新勾选的地址重新绑定商品
		if(commodityAddressList.size()>0){
			for(CommodityAddress commodityAddress:commodityAddressList){
				commodityAddressService.add(commodityAddress);
			}
		}
		
		return ServiceResultCode.SUCCESS;
	}

	private Warehouse getMiddleWarehouse() {
		// 查询中央仓库 传参，对象type传值0，中央仓
		Warehouse warehouse = new Warehouse();
		warehouse.setType("0");
		Warehouse middleWarehouse = null;
		List<Warehouse> dataList = warehouseApiService.getWarehouseDataList(warehouse);
		if (dataList != null && dataList.size() > 0) {
			middleWarehouse = dataList.get(0);
		}
		return middleWarehouse;
	}


	*//**
	 * 库车初始化生成自定义，非区域行商品 
	 * @param customAddressCodes
	 * @param wmsAddressCodes
	 * @param customUsedAddressCodes
	 * @param commodityCode
	 * @return
	 * @throws ServiceException
	 *//*
	@Override
	@Transactional(rollbackFor=Exception.class)
	public ServiceCode setAreaByStockLimit(String commodityCode,String num) throws ServiceException {
		
		List<StockCustom> stockCustomInsertList = new ArrayList<StockCustom>();
		List<CommoditySku> skuList=commoditySkuService.getCommoditySkuByCode(commodityCode);
		if(skuList.size()<=0){
			return OmsServiceResultCode.NO_SKU;
		}
		int successCount=0;
		SysUser sysUser = RedisWebUtils.getLoginUser();
		for(int j=0;j<skuList.size();j++){
			CommoditySku sku=skuList.get(j);
			String commoditySkuCode=sku.getSkuCode();
			StockCustom stockCustomNew=stockCustomService.getStockCustomByskuCode(commoditySkuCode);
			//根据当前所选的地址编码和sku编码去自定义库存查找相应记录没有则新增记录
			if(stockCustomNew==null){
				StockCustom sc=new StockCustom();
				sc.setId(UUIDGenerator.getUUID());
				sc.getCommodity().setCode(commodityCode); //商品
				sc.getCommoditySku().setSkuCode(commoditySkuCode); //sku编码
//				Warehouse middleWarehouse = getMiddleWarehouse();
//				logger.info("=====================获取中央仓对象============："+middleWarehouse);
//				logger.info("=====================获取中央仓对象编码============："+middleWarehouse.getCode());
				sc.setWarehouseCode("");
				sc.setWarehouseName("");
				sc.setAddressCode("");
				sc.setAddressName("");
				sc.setAddressNameOrWarehouseName("");
				
//				StockWms wms = stockWmsService.findStockWmsBySkuCode(commoditySkuCode);
//				if(wms!=null){
//					sc.setCurrStockNum(wms.getCurrStockNum());
//				}else{
//					sc.setCurrStockNum("0");
//				}
				if(StringUtil.isEmpty(num)){
					sc.setCurrStockNum("0");
				}else{
					sc.setCurrStockNum(num);
				}
				sc.setActFlag(Constant.ACT_FLAG_YES);
				StockCustomUsed used=new StockCustomUsed();
				used.setId("");
				sc.setLastUpdateDate(new Date());
				sc.setLastUpdateBy(sysUser);
				sc.setCreateBy(sysUser);
				sc.setCreateDate(new Date());
				sc.setStockCustomUsed(used);
				stockCustomInsertList.add(sc);
				successCount++;
			}
		}
		
		//添加新的sku库存地址
		if(stockCustomInsertList.size()>0){
			for(StockCustom stockCustom:stockCustomInsertList){
				stockCustomService.add(stockCustom);
				if(!StringUtil.isEmpty(num)){
					stockCustomService.insertLog(stockCustom.getCurrStockNum(), stockCustom.getId());
				}
			}
		}
		
		return ServiceResultCode.SUCCESS;
	}
	
	*//**
	 * 库车初始化生成自定义，非区域行商品 
	 * @param customAddressCodes
	 * @param wmsAddressCodes
	 * @param customUsedAddressCodes
	 * @param commodityCode
	 * @return
	 * @throws ServiceException
	 *//*
	@Override
	@Transactional(rollbackFor=Exception.class)
	public ServiceCode setAreaByStockLimitBySwitch(String skuCode,String commodityCode) throws ServiceException {
		
		List<StockCustom> stockCustomInsertList = new ArrayList<StockCustom>();
		SysUser sysUser = RedisWebUtils.getLoginUser();
			StockCustom stockCustomNew=stockCustomService.getStockCustomByskuCode(skuCode);
			//根据当前所选的地址编码和sku编码去自定义库存查找相应记录没有则新增记录
			if(stockCustomNew==null){
				StockCustom sc=new StockCustom();
				sc.setId(UUIDGenerator.getUUID());
				sc.getCommodity().setCode(commodityCode); //商品
				sc.getCommoditySku().setSkuCode(skuCode); //sku编码
				sc.setWarehouseCode("");
				sc.setWarehouseName("");
				sc.setAddressCode("");
				sc.setAddressName("");
				sc.setAddressNameOrWarehouseName("");
				
//				StockWms wms = stockWmsService.findStockWmsBySkuCode(commoditySkuCode);
//				if(wms!=null){
//					sc.setCurrStockNum(wms.getCurrStockNum());
//				}else{
//					sc.setCurrStockNum("0");
//				}
				sc.setCurrStockNum("0");
				sc.setActFlag(Constant.ACT_FLAG_YES);
				StockCustomUsed used=new StockCustomUsed();
				used.setId("");
				sc.setLastUpdateDate(new Date());
				sc.setLastUpdateBy(sysUser);
				sc.setCreateBy(sysUser);
				sc.setCreateDate(new Date());
				sc.setStockCustomUsed(used);
				stockCustomInsertList.add(sc);
			}
		
		//添加新的sku库存地址
		if(stockCustomInsertList.size()>0){
			for(StockCustom stockCustom:stockCustomInsertList){
				stockCustomService.add(stockCustom);
			}
		}
		
		return ServiceResultCode.SUCCESS;
	}


	*//**
	 * 根据所选的地址生成共享的库存记录
	 * @param customAddressCodes
	 * @param wmsAddressCodes
	 * @param customUsedAddressCodes
	 * @param commodityCode
	 * @return
	 * @throws ServiceException
	 *//*
	@Override
	@Transactional(rollbackFor=Exception.class)
	public ServiceCode setAreaIsMany(String customAddressCodes, String wmsAddressCodes, String customUsedAddressCodes,String commodityCode) throws ServiceException {
		Map<String,String> currAddressMap=new HashMap<String,String>();
		List<CommodityAddress> commodityAddressList = new ArrayList<CommodityAddress>();
		List<StockCustom> stockCustomInsertList = new ArrayList<StockCustom>();
		List<StockCustom> stockCustomUpdatetList = new ArrayList<StockCustom>();
		List<StockCustomUsed> stockCustomUsedUpdatetList = new ArrayList<StockCustomUsed>();
		
		String addressCodes="";  
		
		String addressCodeIsMany = "";
		String addressNameIsMany = "";
		String warehouseCodesIsMany= "";
		String warehouseNamesIsMany = "";
		String skuCode="";
		
		if(StringUtil.isNotNull(customAddressCodes)){
			addressCodes+=customAddressCodes+",";
		}
		if(StringUtil.isNotNull(wmsAddressCodes)){
			addressCodes+=wmsAddressCodes+",";
		}
		if(StringUtil.isNotNull(customUsedAddressCodes)){
			addressCodes+=customUsedAddressCodes+",";
		}
		SysUser sysUser = RedisWebUtils.getLoginUser();
		Date date=new Date();
		Map<String,Object> params=new HashMap<String,Object>();
		if(StringUtil.isNotNull(addressCodes)){
			addressCodes=addressCodes.substring(0,addressCodes.length()-1);
			String[] addressCodeArray=addressCodes.split(",");
			Commodity commodity = commodityService.getByCode(commodityCode);
			params.put("commodityCode", commodity.getCode());
			params.put("stockLimit", StockCustomAndWmsConstant.STOCK_LIMIT_CUSTOM);

			params.put("areaCategory", StockCustomAndWmsConstant.AREA_CATEGORY);
			List<CommoditySku> skuList=commoditySkuService.getCommoditySkuByParams(params);
			if(skuList.size()<=0){
				return OmsServiceResultCode.NO_SKU;
			}
			
			for(int i=0;i<addressCodeArray.length;i++){
				if(currAddressMap.get(addressCodeArray[i])==null){
					CommodityAddress commodityAddress=new CommodityAddress();
					commodityAddress.setId(UUIDGenerator.getUUID());
					commodityAddress.setCommodityCode(commodityCode);
					commodityAddress.setAddressCode(addressCodeArray[i]);
					ResultDto rd = addressApiService.getAddressByCode(commodityAddress.getAddressCode());
					Address address=(Address)rd.getData();
					commodityAddress.setAddressName(address.getName());
					commodityAddressList.add(commodityAddress);
					
					addressCodeIsMany+=addressCodeArray[i]+",";
					addressNameIsMany+=address.getName()+",";
					if(!warehouseCodesIsMany.contains(address.getWarehouseCode())){
						warehouseCodesIsMany+=StringUtil.isNotNull(address.getWarehouseCode())==true?address.getWarehouseCode()+",":"";
						warehouseNamesIsMany+=StringUtil.isNotNull(address.getWarehouseName())==true?address.getWarehouseName()+",":"";
					}
					currAddressMap.put(addressCodeArray[i], addressCodeArray[i]);
				}
			}
			
			
			for(int j=0;j<skuList.size();j++){
				CommoditySku sku=skuList.get(j); 
				StockCustom stockCustomIsMany = stockCustomService.findStockCustomByskuCodeAndIsMany(sku.getSkuCode());
				if(stockCustomIsMany==null){
					if(Constant.ACT_FLAG_NO.equals(sku.getActFlag())){
						List<StockCustom> stockCustomList = stockCustomService.findStockCustomByskuCodeIsMany(sku.getSkuCode());
						if(stockCustomList!=null && stockCustomList.size()>0){
							for(StockCustom stockCustom:stockCustomList){
								if(Constant.ACT_FLAG_YES.equals(stockCustom.getActFlag())){
									stockCustom.setActFlag(Constant.ACT_FLAG_NO);
									stockCustom.setLastUpdateDate(date);
									stockCustom.setLastUpdateBy(sysUser);
									stockCustom.setCurrStockNum("0");
									StockCustomUsed used=stockCustomUsedService.findById(stockCustom.getStockCustomUsed().getId());
									if(used!=null){
										used.setActFlag(Constant.ACT_FLAG_NO);
										used.setLastUpdateDate(date);
										used.setLastUpdateBy(sysUser);
										used.setCurrStockNum("0");
										stockCustomUsedUpdatetList.add(used);
									}
									stockCustomUpdatetList.add(stockCustom);
								}
							}
						}
						continue;
					}
					StockCustom sc=new StockCustom();
					sc.setId(UUIDGenerator.getUUID());
					sc.getCommodity().setCode(commodityCode); //商品
					sc.getCommoditySku().setSkuCode(sku.getSkuCode()); //sku编码
					if(StringUtil.isNotNull(addressCodeIsMany)){
						if(StringUtil.isNotNull(addressCodeIsMany)){
							String addressCode=addressCodeIsMany.substring(0, addressCodeIsMany.length()-1);
							sc.setAddressCode(addressCode);
							
							String addressName=addressNameIsMany.substring(0, addressNameIsMany.length()-1);
							sc.setAddressName(addressName);
							
							String warehouseCodes=warehouseCodesIsMany.substring(0, warehouseCodesIsMany.length()-1);
							sc.setWarehouseCode(warehouseCodes);
							
							String warehouseNames=warehouseNamesIsMany.substring(0, warehouseNamesIsMany.length()-1);
							sc.setWarehouseName(warehouseNames);
							
							String addressNameOrWarehouseName = addressNameIsMany.substring(0, addressNameIsMany.length()-1);
							sc.setAddressNameOrWarehouseName(addressNameOrWarehouseName);
						}
					}
					
					sc.setIsMany("1");
					sc.setCurrStockNum("0");
					sc.setActFlag(Constant.ACT_FLAG_YES);
					StockCustomUsed used=new StockCustomUsed();
					used.setId("");
					sc.setLastUpdateDate(date);
					sc.setLastUpdateBy(sysUser);
					sc.setCreateBy(sysUser);
					sc.setCreateDate(date);
					sc.setStockCustomUsed(used);
					stockCustomInsertList.add(sc);
				}else{
					String arr="";
					String[] oldAddressCodes = stockCustomIsMany.getAddressCode().split(",");
					String[] newAddressCodes = addressCodeIsMany.split(",");
					String[] arrAddRess =  arrContrast(oldAddressCodes,newAddressCodes);
					if(arrAddRess!=null && arrAddRess.length>0){
						for(int i=0;i<arrAddRess.length;i++){
							arr+=arrAddRess[i];
						}
					}
					if(StringUtil.isNotNull(arr)){
						Map<String,Object> params1 = new HashMap<String,Object>();
						params1.put("skuCode", stockCustomIsMany.getCommoditySku().getSkuCode());
						params1.put("actFlag", Constant.ACT_FLAG_YES);
						//params.put("isMany", "1");
						int num = stockCustomUsedService.countStockCustomUsed(params1);
						if(num>0){
							return OmsServiceResultCode.REMOVE_ADDRESS;
						}
					}
					if(StringUtil.isNotNull(addressCodeIsMany)){
						String addressName=addressNameIsMany.substring(0, addressNameIsMany.length()-1);
						stockCustomIsMany.setAddressName(addressName);
						
						String addressCode=addressCodeIsMany.substring(0, addressCodeIsMany.length()-1);
						stockCustomIsMany.setAddressCode(addressCode);
						
						String warehouseCodes=warehouseCodesIsMany.substring(0, warehouseCodesIsMany.length()-1);
						stockCustomIsMany.setWarehouseCode(warehouseCodes);
						
						String warehouseNames=warehouseNamesIsMany.substring(0, warehouseNamesIsMany.length()-1);
						stockCustomIsMany.setWarehouseName(warehouseNames);
						
						String addressNameOrWarehouseName = addressNameIsMany.substring(0, addressNameIsMany.length()-1);
						stockCustomIsMany.setAddressNameOrWarehouseName(addressNameOrWarehouseName);
					}
					stockCustomIsMany.setLastUpdateDate(date);
					stockCustomIsMany.setLastUpdateBy(sysUser);
					if(Constant.ACT_FLAG_NO.equals(stockCustomIsMany.getActFlag())){
						stockCustomIsMany.setActFlag(Constant.ACT_FLAG_YES);
					}
					StockCustomUsed used=stockCustomUsedService.findById(stockCustomIsMany.getStockCustomUsed().getId());
					if(used!=null){
						if(Constant.ACT_FLAG_NO.equals(used.getActFlag())){
							used.setActFlag(Constant.ACT_FLAG_YES);
							used.setStockUsedCount("0");
						}
						
						if(StringUtil.isNotNull(stockCustomIsMany.getWarehouseCode())){
							String warehouseCodesUsed=warehouseCodesIsMany.substring(0, warehouseCodesIsMany.length()-1);
							used.setWarehouseCode(warehouseCodesUsed);
							
							String addressCodesUsed=addressCodeIsMany.substring(0, addressCodeIsMany.length()-1);
							used.setAddressCode(addressCodesUsed);
						}
						
						used.setLastUpdateDate(date);
						used.setLastUpdateBy(sysUser);
						stockCustomUsedUpdatetList.add(used);
					}
					stockCustomUpdatetList.add(stockCustomIsMany);
					
				}
				List<StockCustom> stockCustomList = stockCustomService.findStockCustomByskuCodeIsMany(sku.getSkuCode());
				if(stockCustomList!=null && stockCustomList.size()>0){
					for(StockCustom stockCustom:stockCustomList){
						if(Constant.ACT_FLAG_YES.equals(stockCustom.getActFlag())){
							stockCustom.setActFlag(Constant.ACT_FLAG_NO);
							stockCustom.setLastUpdateDate(date);
							stockCustom.setLastUpdateBy(sysUser);
							stockCustom.setCurrStockNum("0");
							StockCustomUsed used=stockCustomUsedService.findById(stockCustom.getStockCustomUsed().getId());
							if(used!=null){
								used.setActFlag(Constant.ACT_FLAG_NO);
								used.setLastUpdateDate(date);
								used.setLastUpdateBy(sysUser);
								used.setCurrStockNum("0");
								stockCustomUsedUpdatetList.add(used);
							}
							stockCustomUpdatetList.add(stockCustom);
						}
					}
				}
			}
		}else{
			params.put("commodityCode", commodityCode);
			params.put("stockLimit", StockCustomAndWmsConstant.STOCK_LIMIT_CUSTOM);
			params.put("areaCategory", StockCustomAndWmsConstant.AREA_CATEGORY);
			List<CommoditySku> skuList=commoditySkuService.getCommoditySkuByParams(params);
			if(skuList.size()<=0){
				return OmsServiceResultCode.NO_SKU;
			}
			
			for(int j=0;j<skuList.size();j++){
				CommoditySku sku=skuList.get(j); 
				StockCustom stockCustomIsMany = stockCustomService.findStockCustomByskuCodeAndIsMany(sku.getSkuCode());
				if(stockCustomIsMany!=null){
					Map<String,Object> params1 = new HashMap<String,Object>();
					params1.put("skuCode", stockCustomIsMany.getCommoditySku().getSkuCode());
					params1.put("actFlag", Constant.ACT_FLAG_YES);
					//params.put("isMany", "1");
					int num = stockCustomUsedService.countStockCustomUsed(params1);
					if(num>0){
						return OmsServiceResultCode.REMOVE_ADDRESS;
					}
					stockCustomIsMany.setActFlag(Constant.ACT_FLAG_NO);
					stockCustomUpdatetList.add(stockCustomIsMany);
					stockCustomIsMany.setAddressName(addressNameIsMany);
					stockCustomIsMany.setAddressCode(addressCodeIsMany);
					stockCustomIsMany.setWarehouseCode(warehouseCodesIsMany);
					stockCustomIsMany.setWarehouseName(warehouseNamesIsMany);
					stockCustomIsMany.setCurrStockNum("0");
					if(StringUtil.isNotNull(addressNameIsMany)){
						String addressNameOrWarehouseName = addressNameIsMany.substring(0, addressNameIsMany.length()-1);
						stockCustomIsMany.setAddressNameOrWarehouseName(addressNameOrWarehouseName);
					}else{
						stockCustomIsMany.setAddressNameOrWarehouseName("");
					}
					stockCustomIsMany.setLastUpdateDate(date);
					stockCustomIsMany.setLastUpdateBy(sysUser);
					StockCustomUsed used=stockCustomUsedService.findById(stockCustomIsMany.getStockCustomUsed().getId());
					if(used!=null){
						used.setActFlag(Constant.ACT_FLAG_NO);
						used.setAddressCode(addressCodeIsMany);
						used.setWarehouseCode(warehouseCodesIsMany);
						used.setLastUpdateDate(date);
						used.setLastUpdateBy(sysUser);
						stockCustomUsedUpdatetList.add(used);
					}
					stockCustomUpdatetList.add(stockCustomIsMany);
				}
				List<StockCustom> stockCustomList = stockCustomService.findStockCustomByskuCodeIsMany(sku.getSkuCode());
				if(stockCustomList!=null && stockCustomList.size()>0){
					for(StockCustom stockCustom:stockCustomList){
						if(Constant.ACT_FLAG_YES.equals(stockCustom.getActFlag())){
							stockCustom.setActFlag(Constant.ACT_FLAG_NO);
							stockCustom.setLastUpdateDate(date);
							stockCustom.setLastUpdateBy(sysUser);
							StockCustomUsed used=stockCustomUsedService.findById(stockCustom.getStockCustomUsed().getId());
							if(used!=null){
								used.setActFlag(Constant.ACT_FLAG_NO);
								used.setLastUpdateDate(date);
								used.setLastUpdateBy(sysUser);
								used.setCurrStockNum("0");
								stockCustomUsedUpdatetList.add(used);
							}
							stockCustomUpdatetList.add(stockCustom);
						}
					}
				}
			}
		}
		
		//1.先删该商品下所有旧的地址数据
		commodityAddressService.deleteByCommodityCode(commodityCode);
		
		//2.更新所有状态
		if(stockCustomUpdatetList.size()>0){
			for(StockCustom stockCustom:stockCustomUpdatetList){
				stockCustomService.modifyById(stockCustom);
			}
		}
		
		//如果对应的占用表存在则更改状态
		if(stockCustomUsedUpdatetList.size()>0){
			for(StockCustomUsed used:stockCustomUsedUpdatetList){
				stockCustomUsedService.modifyById(used);
			}
		}
		
		//添加新的sku库存地址
		if(stockCustomInsertList.size()>0){
			for(StockCustom stockCustom:stockCustomInsertList){
				stockCustomService.add(stockCustom);
			}
		}
		
		//拿新勾选的地址重新绑定商品
		if(commodityAddressList.size()>0){
			for(CommodityAddress commodityAddress:commodityAddressList){
				commodityAddressService.add(commodityAddress);
			}
		}
		
		return ServiceResultCode.SUCCESS;
	}
	
	*//**
	 * 数组去重复	
	 * @param arr1
	 * @param arr2
	 * @return
	 *//*
	public String[] arrContrast(String[] arr1, String[] arr2){
		List<String> list = new LinkedList<String>();
		for (String str : arr1) { //处理第一个数组,list里面的值为1,2,3,4
			if (!list.contains(str)) {
				list.add(str);
			}
		}
		
		for (String str : arr2) { //如果第二个数组存在和第一个数组相同的值，就删除
			if(list.contains(str)){
				list.remove(str);
			}
		}
		String[] result = {}; //创建空数组
		return list.toArray(result); //List to Array
	} 
}*/