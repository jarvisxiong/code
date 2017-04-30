/**
 * @Description 
 * @author tangjun
 * @date 2016年8月15日
 * 
 */
package com.ffzx.bi.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ffzx.basedata.api.dto.Address;
import com.ffzx.basedata.api.dto.Dict;
import com.ffzx.basedata.api.service.AddressApiService;
import com.ffzx.basedata.api.service.DictApiService;
import com.ffzx.bi.controller.base.FFBaseController;
import com.ffzx.commerce.framework.constant.Constant;
import com.ffzx.commerce.framework.dto.ResultDto;
import com.ffzx.commerce.framework.exception.ServiceException;
import com.ffzx.commerce.framework.page.Page;
import com.ffzx.commerce.framework.utils.StringUtil;
import com.ffzx.commodity.api.dto.Category;
import com.ffzx.commodity.api.service.CategoryApiService;
import com.ffzx.member.api.dto.Vendor;
import com.ffzx.member.api.service.VendorApiService;

/**
 * 改代码是复制代码，需要base-data 统一提供前端接口，暂时复制代码
 * @Description 
 * @author tangjun
 * @date 2016年8月15日
 */
@Controller
@RequestMapping("/SystemData")
public class SystemDataController extends FFBaseController
{
	private final static Logger logger = LoggerFactory.getLogger(SystemDataController.class);
	@Resource
	public VendorApiService vendorApiService;
	@Resource
    private DictApiService dictApiService;
	@Resource
	private AddressApiService addressApiService;
	
	@Resource CategoryApiService categoryApiService;
	
	
	/**
	 * 查看所有商品分类
	 * 
	 * @return
	 */
	@RequestMapping("/CategoryTree")
	@ResponseBody
	public void CategoryTree(HttpServletResponse response) {
		logger.debug("MenuController-ajaxgetMenuList=》菜单权限-查看所有的菜单权限");
		try {
			// 查询所有且不分页
			Map<String, Object> params = new HashMap<String, Object>();
			ResultDto rd= categoryApiService.getList(params);  
			List<Category> categoryList=(List<Category>)rd.getData();
			this.getJsonByObject(categoryList);
			this.responseWrite(response, this.getJsonByObject(categoryList));
		} catch (ServiceException e) {
			logger.error("PurchaseAreaController-getAjaxList-ServiceException=》商品类别-查看所有-ServiceException", e);
			throw e;
		} catch (Exception e) {
			logger.error("PurchaseAreaController-getAjaxList-Exception=》商品类别-查看所有-Exception", e);
			throw new ServiceException(e);
		}
	}
	
	@RequestMapping(value = "/VendorSelect")
	public String listSelectVendor(Vendor vendor, ModelMap map){
		logger.debug("VendorBaseCommodityController-selectVendor=》供应商基础商品管理-供应商选择框 ");
		Page pageObj = this.getPageObj();
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			Map<String, Object> vendorMap=null;
			if(StringUtil.isNotNull(vendor.getName())){
				params.put("name", vendor.getName());
			}
			ResultDto rd =	vendorApiService.findList(pageObj, Constant.ORDER_BY_FIELD+" DESC", null, params);
			if(StringUtil.isNotNull(rd.getData())){
				vendorMap=(Map<String, Object>)rd.getData();
				map.put("vendorList", vendorMap.get("list"));
				map.put("pageObj", vendorMap.get("page"));
			}
			ResultDto result = dictApiService.getDictByType(Constant.VENDOR_PURCHASE_TYPE);
			List<Dict> purchaseTypeList =(List<Dict>)result.getData();
			map.put("purchaseTypeList", purchaseTypeList);
		} catch (ServiceException e) {
			logger.error("VendorBaseCommodityController-selectVendor-ServiceException=》供应商基础商品管理-供应商选择框 -ServiceException", e);
			throw e;
		} catch (Exception e) {
			logger.error("VendorBaseCommodityController-selectVendor-Exception=》供应商基础商品管理-供应商选择框 --Exception", e);
			throw new ServiceException(e);
		}
		return ReturnPage();
	}
	
	
	@RequestMapping(value = "/AddressSelect")
	public String addressListSelect(String id, String searchType, ModelMap map) {

		Page pageObj = this.getPageObj();
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			String treeData = "[]";

			// 根据设置的查询的行政级别过滤地址显示树
			ResultDto dicDto = dictApiService.getDictByType((Constant.PARTNER_ADDRESS_TYPE));
			List<Dict> dictList = (List<Dict>) dicDto.getData();
			String selectAddressType = null;
			if (null != dictList && dictList.size() > 0) {
				Dict Dict = dictList.get(0);
				selectAddressType = Dict.getValue();
			}
			
			List<Object> listResult = getSelectAddressList(params);
			// 左侧菜单树
			treeData = this.getJsonByObject(listResult);
			map.put("result", treeData);
			// 树形Table
			// 筛选过滤条件-编码
			//params.put("selectAddressType", selectAddressType);
			String addressCode = this.getParaMap().get("addressCode") != null ? this.getParaMap().get("addressCode").toString() : "";
			if (StringUtil.isNotNull(addressCode)) {
				params.put("addressCode", addressCode);
			}
			// 筛选过滤条件-名称
			String name = this.getParaMap().get("name") != null ? this.getParaMap().get("name").toString() : "";
			if (StringUtil.isNotNull(name)) {
				params.put("name", name);
			}
			if(StringUtil.isNotNull(id)){
				params.put("parentIdsAndOrId", id); // 
 			}
			
	 
			// 查询来源类型，是否通过查询按钮还是菜单点击
			//params.put("searchType", searchType);
			//ResultDto addrTableDto = this.addressApiService.getAddressTable(params, id);
			params.put("actFlag", Constant.ACT_FLAG_YES);
			ResultDto addrTableDto = this.addressApiService.getAddressList(params, pageObj, id);
			Map<String, Object> returnParams =(Map<String, Object>)addrTableDto.getData();
			List<Address> treeTable=(List<Address>)returnParams.get("addressList");
			pageObj = (Page)returnParams.get("page");
//			List<Object> treeTable = (List<Object>) addrTableDto.getData();
			map.put("params", this.getParaMap());
			map.put("treeTable", treeTable);
			map.put("pageObj", pageObj);
		} catch (ServiceException e) {
			logger.error("OmsOrderController-addressListSelect-ServiceException=》地址管理-地址选择框-ServiceException", e);
			throw e;
		} catch (Exception e) {
			logger.error("OmsOrderController-addressListSelect-Exception=》地址管理-地址选择框-Exception", e);
			throw new ServiceException(e);
		}
		return ReturnPage();
	}
	
	public List<Object> getSelectAddressList(Map<String, Object> params) throws ServiceException {
		if (!StringUtil.isNotNull(params)) 
		{
			params = new HashMap<String, Object>();
		}
//		params.put(Constant.ORDER_BY_FIELD_PARAMS, Constant.ORDER_BY_FIELD);
//		params.put(Constant.ORDER_BY_PARAMS, Constant.ORDER_BY);
//		params.put(Constant.DELTE_FLAG, Constant.DELTE_FLAG_NO);
//		params.put(Constant.ACT_FLAG, Constant.ACT_FLAG_YES);
		List<Address> addressList = (List<Address>) this.addressApiService.getAddressList(params).getData();
		List<Object> listResult = new ArrayList<Object>();
		addressToMap(addressList, listResult);
		return listResult;
	}
	
	public void addressToMap(List<Address> addressList, List<Object> listResult) throws ServiceException {
		for (Address area : addressList) {
			Map<String, Object> row = new HashMap<String, Object>();
			row.put("id", area.getId());
			row.put("name", area.getName());
			row.put("pId", area.getParentId());
			row.put("open", true);
			row.put("addressCode", area.getAddressCode());
			row.put("preAddress", area.getPreAddress());
			row.put("actFlag", area.getActFlag());
			row.put("type", area.getType());
			row.put("partner", area.getPartner());
			row.put("remarks", area.getRemarks());
			listResult.add(row);
			if (null != area.getSub() && area.getSub().size() > 0) {
				addressToMap(area.getSub(), listResult);
			}
		}
	}
}
