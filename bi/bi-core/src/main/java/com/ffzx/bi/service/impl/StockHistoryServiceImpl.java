package com.ffzx.bi.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ffzx.bi.mapper.StockHistoryMapper;
import com.ffzx.bi.model.StockHistory;
import com.ffzx.bi.service.StockHistoryService;
import com.ffzx.bi.vo.StockHistoryCustom;
import com.ffzx.bi.vo.StockHistoryVo;
import com.ffzx.commerce.framework.constant.Constant;
import com.ffzx.commerce.framework.dao.CrudMapper;
import com.ffzx.commerce.framework.exception.ServiceException;
import com.ffzx.commerce.framework.page.Page;
import com.ffzx.commerce.framework.service.impl.BaseCrudServiceImpl;
import com.ffzx.commerce.framework.utils.DateUtil;
import com.ffzx.commerce.framework.utils.StringUtil;

/**
 * 
 * @author ffzx
 * @date 2016-08-15 14:24:06
 * @version 1.0.0
 * @copyright www.ffzxnet.com
 */
@Service("stockHistoryService")
public class StockHistoryServiceImpl extends BaseCrudServiceImpl implements StockHistoryService {

	private final static Logger logger = LoggerFactory.getLogger(StockHistoryServiceImpl.class);
	
    @Resource
    private StockHistoryMapper stockHistoryMapper;

    @Override
    public CrudMapper init() {
        return stockHistoryMapper;
    }

	@Override
	public List<StockHistory> findList(Page page, String orderByField, String orderBy,
			StockHistoryVo stockHistoryVo) throws ServiceException {
		return stockHistoryMapper.selectByList(page, orderByField, orderBy, stockHistoryVo);
	}

	@Override
	public Map<String, Object> findStockNum(Page page, String orderByField, String orderBy, StockHistoryVo stockHistoryVo) {
		Map<String, Object> values = new HashMap<String, Object>();
		List<StockHistory> stockHistoryList = null;
		if (stockHistoryVo != null) {
			StockHistoryCustom shc = stockHistoryVo.getStockHistoryCustom();
			if (shc != null) {
//				if (stockHistoryVo.getSkuBarcodeList() == null) {
					//没有输入sku条形码, 给个查询出的默认的sku条形码
					if (StringUtil.isNotNull(shc.getIsCheckedBarcode())) {
						if (stockHistoryVo.getSkuBarcodeList() == null && StringUtil.isEmpty(shc.getSkuBarcode())) {
							Page pageObj = new Page();
							pageObj.setPageSize(1);
							List<StockHistory> stockHistorys = findList(pageObj, "transfer_time", Constant.ORDER_BY, stockHistoryVo);
							if (stockHistorys != null && stockHistorys.size() != 0) {
								StockHistory stockHistory = stockHistorys.get(0);
								shc.setSkuBarcode(stockHistory.getSkuBarcode());
							}
						}
					} 
					/*else if (StringUtil.isNotNull(shc.getIsCheckedBarcode()) && StringUtil.isNotNull(shc.getGroupByCategory())) {
						
					}
					else if (StringUtil.isNotNull(shc.getIsCheckedBarcode()) && StringUtil.isNotNull(shc.getGroupByVendor())) {
						
					}
					else if (StringUtil.isNotNull(shc.getGroupByVendor()) && StringUtil.isNotNull(shc.getGroupByCategory())) {
						
					}*/
					else if (StringUtil.isNotNull(shc.getGroupByCategory()) && StringUtil.isNotNull(shc.getGroupByVendor())) {
						if (StringUtil.isEmpty(shc.getVendorCode()) && stockHistoryVo.getVendorCodeList() == null) {
							if (StringUtil.isNotNull(shc.getCategoryIdOneLevel())) {
								StockHistory stockHistory = stockHistoryMapper.groupByVendorWhereCategoryOneLevel(shc.getCategoryIdOneLevel());
								shc.setVendorCode(stockHistory.getVendorCode());
							} else if (StringUtil.isNotNull(shc.getCategoryIdTwoLevel())) {
								StockHistory stockHistory = stockHistoryMapper.groupByVendorWhereCategoryTwoLevel(shc.getCategoryIdTwoLevel());
								shc.setVendorCode(stockHistory.getVendorCode());
							} else if (StringUtil.isNotNull(shc.getCategoryIdThreeLevel())) {
								StockHistory stockHistory = stockHistoryMapper.groupByVendorWhereCategoryThreeLevel(shc.getCategoryIdThreeLevel());
								shc.setVendorCode(stockHistory.getVendorCode());
							}
						}
					}
					//当一级，二级， 三级类别都没有选择时，给个查询出的默认的三级类别
					else if (StringUtil.isNotNull(shc.getGroupByCategory())) {
						if (stockHistoryVo.getCategoryIdList() == null
								&& StringUtil.isEmpty(shc.getCategoryIdOneLevel()) && StringUtil.isEmpty(shc.getCategoryIdTwoLevel()) 
								&& StringUtil.isEmpty(shc.getCategoryIdThreeLevel())) {
							Page pageObj = new Page();
							pageObj.setPageSize(1);
							List<StockHistory> stockHistorys = findList(pageObj, "transfer_time", Constant.ORDER_BY, stockHistoryVo);
							if (stockHistorys != null && stockHistorys.size() != 0) {
								StockHistory stockHistory = stockHistorys.get(0);
								shc.setCategoryIdThreeLevel(stockHistory.getCategoryIdThreeLevel()); 
							}
						}
					}
					//供应商选择框没有选择供应商时， 给个查询出的默认的供应商
					else if (StringUtil.isNotNull(shc.getGroupByVendor())) {
						if (StringUtil.isEmpty(shc.getVendorCode()) && stockHistoryVo.getVendorCodeList() == null) {
							Page pageObj = new Page();
							pageObj.setPageSize(1);
							List<StockHistory> stockHistorys = findList(pageObj, "transfer_time", Constant.ORDER_BY, stockHistoryVo);
							if (stockHistorys != null && stockHistorys.size() != 0) {
								StockHistory stockHistory = stockHistorys.get(0);
								shc.setVendorCode(stockHistory.getVendorCode());
							}
						}
					}
//				}
				stockHistoryList = stockHistoryMapper.selectStockNum(page, orderByField, orderBy, stockHistoryVo);
				List<String> dateList = null;
				List<String> vendorNameList = new ArrayList<String>();
				List<List<String>> stockNumLists = new ArrayList<List<String>>();
				if ("year".equals(shc.getDate())) {
					dateList = stockHistoryMapper.getYear();
				} else if ("month".equals(shc.getDate())) {
					dateList = stockHistoryMapper.getMonth();
				} else if ("week".equals(shc.getDate())) {
					dateList = stockHistoryMapper.getWeek();
				} else {
					dateList = daysBetween(shc.getSearchMinDate(), shc.getSearchMaxDate());
				}
				Map<String, Map<String, String>> voderMap = listConvertMap(stockHistoryList, stockHistoryVo);
				List<String> stockNumList = null;
				for (Map.Entry<String, Map<String, String>> entry : voderMap.entrySet()) {
					stockNumList = new ArrayList<String>();
					for (String date : dateList) {
						if (entry.getValue().containsKey(date)) {
							stockNumList.add(entry.getValue().get(date));
						} else {
							stockNumList.add("0");
						}
					}
					vendorNameList.add(entry.getKey());
					stockNumLists.add(stockNumList);
				}
				values.put("days", dateList);
				values.put("stocknums", stockNumLists);
				values.put("vendorNames", vendorNameList);
			}
		}
		return values;
	}
	
	private Map<String, Map<String, String>> listConvertMap(List<StockHistory> stockHistoryList, StockHistoryVo stockHistoryVo ) {
		StockHistoryCustom shc = stockHistoryVo.getStockHistoryCustom();
		Map<String, Map<String, String>> map = new HashMap<String, Map<String, String>>();
		String day = null;
		if (StringUtil.isNotNull(shc.getIsCheckedBarcode())) {
			for (StockHistory sh : stockHistoryList) {
				String commodityName = sh.getCommodityName();
				String stocknum = sh.getStocknum();
				if ("day".equals(shc.getDate())) {
					day = DateUtil.formatDate(sh.getTransferTime());
				} else if ("week".equals(shc.getDate())) {
					day = sh.getWeek();
				} else if ("month".equals(shc.getDate())) {
					day = sh.getMonth();
				} else if ("year".equals(shc.getDate())) {
					day = sh.getYear();
				} else {
					day = DateUtil.formatDate(sh.getTransferTime());
				}
				Map<String, String> dayAndStockNumMap = map.get(commodityName);
				if (StringUtil.isNotNull(dayAndStockNumMap)) {
					dayAndStockNumMap.put(day, stocknum);
					map.put(commodityName, dayAndStockNumMap);
				} else {
					dayAndStockNumMap = new HashMap<String, String>();
					dayAndStockNumMap.put(day, stocknum);
					map.put(commodityName, dayAndStockNumMap);
				}
			}
		} else if (StringUtil.isNotNull(shc.getGroupByCategory()) && StringUtil.isNotNull(shc.getGroupByVendor())) {
			for (StockHistory sh : stockHistoryList) {
				String vendorName = sh.getVendorName();
				String stocknum = sh.getStocknum();
				if ("day".equals(shc.getDate())) {
					day = DateUtil.formatDate(sh.getTransferTime());
				} else if ("week".equals(shc.getDate())) {
					day = sh.getWeek();
				} else if ("month".equals(shc.getDate())) {
					day = sh.getMonth();
				} else if ("year".equals(shc.getDate())) {
					day = sh.getYear();
				} else {
					day = DateUtil.formatDate(sh.getTransferTime());
				}
				Map<String, String> dayAndStockNumMap = map.get(vendorName);
				if (StringUtil.isNotNull(dayAndStockNumMap)) {
					dayAndStockNumMap.put(day, stocknum);
					map.put(vendorName, dayAndStockNumMap);
				} else {
					dayAndStockNumMap = new HashMap<String, String>();
					dayAndStockNumMap.put(day, stocknum);
					map.put(vendorName, dayAndStockNumMap);
				}
			}
		} else if (StringUtil.isNotNull(shc.getGroupByCategory())) {
			if (StringUtil.isNotNull(shc.getCategoryIdOneLevel())) {
				for (StockHistory sh : stockHistoryList) {
					String categoryNameOneLevel = sh.getCategoryNameOneLevel();
					String stocknum = sh.getStocknum();
					if ("day".equals(shc.getDate())) {
						day = DateUtil.formatDate(sh.getTransferTime());
					} else if ("week".equals(shc.getDate())) {
						day = sh.getWeek();
					} else if ("month".equals(shc.getDate())) {
						day = sh.getMonth();
					} else if ("year".equals(shc.getDate())) {
						day = sh.getYear();
					} else {
						day = DateUtil.formatDate(sh.getTransferTime());
					}
					Map<String, String> dayAndStockNumMap = map.get(categoryNameOneLevel);
					if (StringUtil.isNotNull(dayAndStockNumMap)) {
						dayAndStockNumMap.put(day, stocknum);
						map.put(categoryNameOneLevel, dayAndStockNumMap);
					} else {
						dayAndStockNumMap = new HashMap<String, String>();
						dayAndStockNumMap.put(day, stocknum);
						map.put(categoryNameOneLevel, dayAndStockNumMap);
					}
				}
			} else if (StringUtil.isNotNull(shc.getCategoryIdTwoLevel())) {
				for (StockHistory sh : stockHistoryList) {
					String categoryNameTwoLevel = sh.getCategoryNameTwoLevel();
					String stocknum = sh.getStocknum();
					if ("day".equals(shc.getDate())) {
						day = DateUtil.formatDate(sh.getTransferTime());
					} else if ("week".equals(shc.getDate())) {
						day = sh.getWeek();
					} else if ("month".equals(shc.getDate())) {
						day = sh.getMonth();
					} else if ("year".equals(shc.getDate())) {
						day = sh.getYear();
					} else {
						day = DateUtil.formatDate(sh.getTransferTime());
					}
					Map<String, String> dayAndStockNumMap = map.get(categoryNameTwoLevel);
					if (StringUtil.isNotNull(dayAndStockNumMap)) {
						dayAndStockNumMap.put(day, stocknum);
						map.put(categoryNameTwoLevel, dayAndStockNumMap);
					} else {
						dayAndStockNumMap = new HashMap<String, String>();
						dayAndStockNumMap.put(day, stocknum);
						map.put(categoryNameTwoLevel, dayAndStockNumMap);
					}
				}
			} else if (StringUtil.isNotNull(shc.getCategoryIdThreeLevel())) {
				for (StockHistory sh : stockHistoryList) {
					String categoryNameThreeLevel = sh.getCategoryNameThreeLevel();
					String stocknum = sh.getStocknum();
					if ("day".equals(shc.getDate())) {
						day = DateUtil.formatDate(sh.getTransferTime());
					} else if ("week".equals(shc.getDate())) {
						day = sh.getWeek();
					} else if ("month".equals(shc.getDate())) {
						day = sh.getMonth();
					} else if ("year".equals(shc.getDate())) {
						day = sh.getYear();
					} else {
						day = DateUtil.formatDate(sh.getTransferTime());
					}
					Map<String, String> dayAndStockNumMap = map.get(categoryNameThreeLevel);
					if (StringUtil.isNotNull(dayAndStockNumMap)) {
						dayAndStockNumMap.put(day, stocknum);
						map.put(categoryNameThreeLevel, dayAndStockNumMap);
					} else {
						dayAndStockNumMap = new HashMap<String, String>();
						dayAndStockNumMap.put(day, stocknum);
						map.put(categoryNameThreeLevel, dayAndStockNumMap);
					}
				}
			} else {
				for (StockHistory sh : stockHistoryList) {
					String categoryNameThreeLevel = sh.getCategoryNameThreeLevel();
					String stocknum = sh.getStocknum();
					if ("day".equals(shc.getDate())) {
						day = DateUtil.formatDate(sh.getTransferTime());
					} else if ("week".equals(shc.getDate())) {
						day = sh.getWeek();
					} else if ("month".equals(shc.getDate())) {
						day = sh.getMonth();
					} else if ("year".equals(shc.getDate())) {
						day = sh.getYear();
					} else {
						day = DateUtil.formatDate(sh.getTransferTime());
					}
					Map<String, String> dayAndStockNumMap = map.get(categoryNameThreeLevel);
					if (StringUtil.isNotNull(dayAndStockNumMap)) {
						dayAndStockNumMap.put(day, stocknum);
						map.put(categoryNameThreeLevel, dayAndStockNumMap);
					} else {
						dayAndStockNumMap = new HashMap<String, String>();
						dayAndStockNumMap.put(day, stocknum);
						map.put(categoryNameThreeLevel, dayAndStockNumMap);
					}
				}
			}
		} else if (StringUtil.isNotNull(shc.getGroupByVendor()) && (StringUtil.isNotNull(shc.getVendorCode()) || stockHistoryVo.getVendorCodeList() != null)) {
			for (StockHistory sh : stockHistoryList) {
				String vendorName = sh.getVendorName();
				String stocknum = sh.getStocknum();
				if ("day".equals(shc.getDate())) {
					day = DateUtil.formatDate(sh.getTransferTime());
				} else if ("week".equals(shc.getDate())) {
					day = sh.getWeek();
				} else if ("month".equals(shc.getDate())) {
					day = sh.getMonth();
				} else if ("year".equals(shc.getDate())) {
					day = sh.getYear();
				} else {
					day = DateUtil.formatDate(sh.getTransferTime());
				}
				Map<String, String> dayAndStockNumMap = map.get(vendorName);
				if (StringUtil.isNotNull(dayAndStockNumMap)) {
					dayAndStockNumMap.put(day, stocknum);
					map.put(vendorName, dayAndStockNumMap);
				} else {
					dayAndStockNumMap = new HashMap<String, String>();
					dayAndStockNumMap.put(day, stocknum);
					map.put(vendorName, dayAndStockNumMap);
				}
			}
		} else {
			for (StockHistory sh : stockHistoryList) {
				String commodityName = sh.getCommodityName();
				String stocknum = sh.getStocknum();
				if ("day".equals(shc.getDate())) {
					day = DateUtil.formatDate(sh.getTransferTime());
				} else if ("week".equals(shc.getDate())) {
					day = sh.getWeek();
				} else if ("month".equals(shc.getDate())) {
					day = sh.getMonth();
				} else if ("year".equals(shc.getDate())) {
					day = sh.getYear();
				} else {
					day = DateUtil.formatDate(sh.getTransferTime());
				}
				Map<String, String> dayAndStockNumMap = map.get(commodityName);
				if (StringUtil.isNotNull(dayAndStockNumMap)) {
					dayAndStockNumMap.put(day, stocknum);
					map.put(commodityName, dayAndStockNumMap);
				} else {
					dayAndStockNumMap = new HashMap<String, String>();
					dayAndStockNumMap.put(day, stocknum);
					map.put(commodityName, dayAndStockNumMap);
				}
			}
		}
		return map;
	}
	
	
	
	private List<String> daysBetween(String startDay, String endDay) {
		List<String> dateList = new ArrayList<String>();
		try {
			int daysBetween = DateUtil.daysBetween(DateUtil.getMinTimeByStringDate(startDay),
					DateUtil.getMaxTimeByStringDate(endDay)) + 1;
			Calendar calc = Calendar.getInstance();
			for (int i = 0; i < daysBetween; i++) {
				calc.setTime(DateUtil.getMinTimeByStringDate(endDay));
				calc.add(Calendar.DATE, -i);
				dateList.add(DateUtil.format(calc.getTime(), "yyyy-MM-dd"));
			}
			Collections.reverse(dateList);
		} catch (Exception e) {
			logger.debug("", e);
		}
		return dateList;
	}
}
