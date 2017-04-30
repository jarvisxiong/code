package com.ffzx.promotion.io;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import org.springframework.beans.factory.annotation.Autowired;

import com.ffzx.commerce.framework.constant.Constant;
import com.ffzx.commerce.framework.page.Page;
import com.ffzx.commerce.framework.utils.UUIDGenerator;
import com.ffzx.commodity.api.dto.Commodity;
import com.ffzx.commodity.api.dto.CommoditySku;
import com.ffzx.promotion.api.dto.ActivityCommodity;
import com.ffzx.promotion.api.dto.ActivityCommoditySku;
import com.ffzx.promotion.api.dto.ActivityManager;
import com.ffzx.promotion.api.enums.ActivityTypeEnum;
import com.ffzx.promotion.api.service.consumer.CommodityApiConsumer;
import com.ffzx.promotion.api.service.consumer.CommoditySkuApiConsumer;
import com.ffzx.promotion.constant.ExcelImportConstant;
import com.ffzx.promotion.service.ActivityCommoditySkuService;
import com.ffzx.promotion.service.GiftCommodityService;
import com.ffzx.promotion.service.impl.ActivityCommodityServiceImpl;
import com.ffzx.promotion.util.ExcelUtils;

public class ExcelCallForWholesale extends ActivityCommodityServiceImpl implements Callable<String> {

	private ActivityCommoditySkuService ctivityCommoditySkuService;
	private CommodityApiConsumer commodityApiConsumer;
	private CommoditySkuApiConsumer commoditySkuApiConsumer;
	private GiftCommodityService giftCommodityService;
	private int i;
	private String[] data;
	private ActivityManager activityManager;
	private ActivityTypeEnum enum1;

	public ExcelCallForWholesale(int i, String[] data, ActivityManager activityManager, ActivityTypeEnum enum1,
			ActivityCommoditySkuService ctivityCommoditySkuService, CommodityApiConsumer commodityApiConsumer,CommoditySkuApiConsumer commoditySkuApiConsumer,GiftCommodityService giftCommodityService) {
		this.i = i;
		this.data = data;
		this.activityManager = activityManager;
		this.enum1 = enum1;
		this.commodityApiConsumer = commodityApiConsumer;
		this.ctivityCommoditySkuService = ctivityCommoditySkuService;
		this.commoditySkuApiConsumer=commoditySkuApiConsumer;
		this.giftCommodityService=giftCommodityService;
	}

	@Override
	public String call() throws Exception {
		// TODO Auto-generated method stub

		try {
			int col = 0;// 第几列
			int row = i + 2;// 第几行

			if (!ExcelUtils.getIsSuccess(data)) {
				return "success";
			}
			// 条形码验证
			ExcelImportConstant.tiaoxingma(ExcelImportConstant.trimString(data[col]),
					ExcelImportConstant.tiaoxingmaString, row, col, comparisonList, errorVaildate);
			comparisonList.add(data[col].trim());
			++col;
			// 标题仅仅验证null
			ExcelImportConstant.notisNullvalidate(ExcelImportConstant.trimString(data[col]),
					ExcelImportConstant.titleString, row, col, errorVaildate);
			++col;
			// 置顶序号
			Integer sortTopNo = ExcelImportConstant.sortTopValidate(ExcelImportConstant.trimString(data[col]),
					ExcelImportConstant.sorttopString, row, col, errorVaildate);
			++col;
			// 购买选择量		
			Integer buyCount = buyCountValidate(ExcelImportConstant.trimString(data[col]), ExcelImportConstant.buyCountString, row,
					col);
			++col;

			// 购买数量1验证
			Integer selectionStart1 = ExcelImportConstant.sortTopValidate(ExcelImportConstant.trimString(data[col]),
					ExcelImportConstant.oneBuyCountString, row, col,  errorVaildate);
			++col;
			// 购买数量2验证
			Integer selectionEnd1 = twoBuyCountValidate(ExcelImportConstant.trimString(data[col]),
					ExcelImportConstant.twoBuyCountString, row, col,buyCount, "two");
			++col;
			// 价格1验证
			BigDecimal activityPrice1 = ExcelImportConstant.showPricevalidate(ExcelImportConstant.trimString(data[col]),
					ExcelImportConstant.onePriceString, row, col, errorVaildate);
			++col;
			// 购买数量3验证
			Integer selectionStart2 = buyCountValidate(ExcelImportConstant.trimString(data[col]),
					ExcelImportConstant.threeBuyCountString, row, col, buyCount, "three", selectionEnd1);
			++col;
			// 购买数量4验证
			Integer selectionEnd2 = fourBuyCountValidate(ExcelImportConstant.trimString(data[col]),
					ExcelImportConstant.fourBuyCountString, row, col, buyCount, "four");
			++col;
			// 价格2验证
			BigDecimal activityPrice2 = twoactivityPricevalidate(ExcelImportConstant.trimString(data[col]),
					ExcelImportConstant.twoPriceString, row, col, buyCount, "two",activityPrice1);
			++col;
			// 购买数量5验证
			Integer selectionStart3 = fivebuyCountValidate(ExcelImportConstant.trimString(data[col]),
					ExcelImportConstant.fiveBuyCountString, row, col, buyCount, "five", selectionEnd2);
			++col;
			// 价格3验证
			BigDecimal activityPrice3 = activityPricevalidate(ExcelImportConstant.trimString(data[col]),
					ExcelImportConstant.threePriceString, row, col, buyCount, "three",activityPrice2);
			++col;
			// 优惠价验证
			String showPrice = showPricevalidate(ExcelImportConstant.trimString(data[col]), ExcelImportConstant.showPriceString, row,
					col,  buyCount, activityPrice1, activityPrice2, activityPrice3);

			Map<String, Object> params=new HashMap<String, Object>();
			params.put("barCode", data[0].trim());
			Map<String, Object> giftMap=this.giftCommodityService.getMapValue();
			if(giftMap!=null){
				params.put("goodsId", giftMap.get("goodsId"));
				params.put("flag", Constant.YES);
			}
			Map<String, Object> commodityMap = commodityApiConsumer.findList(new Page(), null, null, params);
			List<Commodity> commoditylist=(List<Commodity>) commodityMap.get("list");
			Commodity commodity=null;
			if(commoditylist!=null && commoditylist.size()!=0){
				commodity=commoditylist.get(0);
			}
			verficationCommodity(commodity, row, activityManager);// 商品批次验证
			comparisonListcode.add(commoditylist.get(0).getCode());
			
			ActivityCommodity activityCommdity = new ActivityCommodity();
			ActivityCommoditySku activityCommoditySku1 = new ActivityCommoditySku();
			ActivityCommoditySku activityCommoditySku2 = null;
			ActivityCommoditySku activityCommoditySku3 = null;
			if (buyCount == 3) {
				activityCommoditySku2 = new ActivityCommoditySku();
			}
			if (buyCount == 5) {
				activityCommoditySku2 = new ActivityCommoditySku();
				activityCommoditySku3 = new ActivityCommoditySku();
			}

			activityCommdity.setId(UUIDGenerator.getUUID());
			activityCommdity.setActivityNo(activityManager.getActivityNo());
			activityCommdity.setActivityType(enum1);
			activityCommdity.setCommodityNo(commoditylist.get(0).getCode());// 商品编号
			activityCommdity.setCommodityId(commoditylist.get(0).getId());// 商品ID
			activityCommdity.setActivityTitle(data[1].trim());// 商品标题
			activityCommdity.setCommodityBarcode(commoditylist.get(0).getBarCode());
			activityCommdity.setShowPrice(showPrice);
			activityCommdity.setSortTopNo(sortTopNo);
			activityCommdity.setIsRecommend(Constant.NO);
			activityCommdity.setEnableSpecialCount(Constant.NO);// 是否启用特殊数量值
																// 1:启用,0:不启用.
			activityCommdity.setActivity(activityManager);
			activityCommdity.setIslimit(Constant.NO);// APP是否显示限定数量，0否，1是.
			if(buyCount.toString().equals("1")){
				activityCommdity.setBuyCount("0");
			}else if(buyCount.toString().equals("3")){
				activityCommdity.setBuyCount("1");
			}else{
				activityCommdity.setBuyCount("2");
			}			
			activityCommdity.setIsNeworder(Constant.NO);
			activityCommoditySku1.setId(UUIDGenerator.getUUID());
			activityCommoditySku1.setActivityNo(activityManager.getActivityNo());
			activityCommoditySku1.setActivityPrice(activityPrice1);
			activityCommoditySku1.setCommoditySkuTitle(commoditylist.get(0).getTitle());
			activityCommoditySku1.setCommodityNo(commoditylist.get(0).getCode()); // 商品编号
			activityCommoditySku1.setActivityType(enum1);
			activityCommoditySku1.setActivity(activityManager);
			activityCommoditySku1.setActivityCommodity(activityCommdity);
			activityCommoditySku1.setSelectionStart(selectionStart1);
			activityCommoditySku1.setSelectionEnd(selectionEnd1);
			if (buyCount == 3 || buyCount == 5) {
				activityCommoditySku2.setId(UUIDGenerator.getUUID());
				activityCommoditySku2.setActivityNo(activityManager.getActivityNo());
				activityCommoditySku2.setActivityPrice(activityPrice2);
				activityCommoditySku2.setCommoditySkuTitle(commoditylist.get(0).getTitle());
				activityCommoditySku2.setCommodityNo(commoditylist.get(0).getCode()); // 商品编号
				activityCommoditySku2.setActivityType(enum1);
				activityCommoditySku2.setActivity(activityManager);
				activityCommoditySku2.setActivityCommodity(activityCommdity);
				activityCommoditySku2.setSelectionStart(selectionStart2);
				activityCommoditySku2.setSelectionEnd(selectionEnd2);
				if (buyCount == 5) {
					activityCommoditySku3.setId(UUIDGenerator.getUUID());
					activityCommoditySku3.setActivityNo(activityManager.getActivityNo());
					activityCommoditySku3.setActivityPrice(activityPrice3);
					activityCommoditySku3.setCommoditySkuTitle(commoditylist.get(0).getTitle());
					activityCommoditySku3.setCommodityNo(commoditylist.get(0).getCode()); // 商品编号
					activityCommoditySku3.setActivityType(enum1);
					activityCommoditySku3.setActivity(activityManager);
					activityCommoditySku3.setActivityCommodity(activityCommdity);
					activityCommoditySku3.setSelectionStart(selectionStart3);
				}
			}

			activityCommditys.add(activityCommdity);
			activityCommoditySkus.add(activityCommoditySku1);
			if (buyCount == 3 || buyCount == 5) {
				activityCommoditySkus.add(activityCommoditySku2);
				if (buyCount == 5) {
					activityCommoditySkus.add(activityCommoditySku3);
				}
			}
		} catch (Exception e) {
			logger.error("", e);
		}
		return "success";
	}

	/**
	 * 判断根据条形码查出来商品是否符合
	 * 
	 * @param commoditysku
	 * @return
	 * @throws Exception
	 */
	public boolean verficationCommodity(Commodity commoditysku, int row, ActivityManager activityManager) throws Exception {
		int col = 0;
		if (activityManager == null) {
			// 活动管理对象activityManager==null
			errorVaildate.append(ExcelImportConstant.activityManagermessage);
			throw new RuntimeException(ExcelImportConstant.activityManagermessage);
		}
		if (commoditysku == null || commoditysku.getId() == null) {
			// sku条形码匹配不到任何商品信息，请检查是否存在或者已删除或已禁用
			errorVaildate.append(alterString(ExcelImportConstant.tiaoxingmaString, row, col,
					ExcelImportConstant.commodityskuExists));
			throw new RuntimeException(alterString(ExcelImportConstant.tiaoxingmaString, row, col,
					ExcelImportConstant.commodityskuExists));
		} else if (commoditysku.getStatus() == null
				|| !commoditysku.getStatus().equals("COMMODITY_STATUS_ADDED")) {

			errorVaildate.append(alterString(ExcelImportConstant.tiaoxingmaString, row, col,
					ExcelImportConstant.commodityskuStatus));
			throw new RuntimeException(alterString(ExcelImportConstant.tiaoxingmaString, row, col,
					ExcelImportConstant.commodityskuStatus));
		}
			// 根据传递sku条形码是否能查到数据
			Map<String, Object> params=new HashMap<String, Object>();
			if(commoditysku.getBuyType().equals(ActivityTypeEnum.WHOLESALE_MANAGER.getValue())){
				params.put("commodityNo", commoditysku.getCode());
			}else{
				Map<String, Object> map=new HashMap<String, Object>();
				map.put("commodityCode", commoditysku.getCode());			
				List<CommoditySku> skuList=commoditySkuApiConsumer.findList(map);
				if(skuList!=null && skuList.size()!=0){
					List<String> skuIdList=new ArrayList<String>();
					for(CommoditySku sku:skuList){
						skuIdList.add(sku.getId());
					}					
					params.put("skuIds", skuIdList);
				}
			}	
			List<ActivityCommoditySku> skus = ctivityCommoditySkuService.findActivityCommoditySkuList(params);
			// 如果存在说明该商品已经参加了其他活动,提示设置商品重复
			if (skus != null && skus.size() != 0) {
				errorVaildate.append(alterString(ExcelImportConstant.tiaoxingmaString, row, col, ExcelImportConstant
						.overlapDate(skus.get(0).getActivityType().getName(), skus.get(0).getActivityNo())));
				throw new RuntimeException(
						alterString(ExcelImportConstant.tiaoxingmaString, row, col, ExcelImportConstant
								.overlapDate(skus.get(0).getActivityType().getName(), skus.get(0).getActivityNo())));
			}
		return true;
	}

	/**
	 * 购买量选择验证
	 * 
	 * @param date
	 * @param name
	 * @param row
	 * @param col
	 * @return
	 */
	public Integer buyCountValidate(String date, String name, int row, int col) {
		ExcelImportConstant.notisNullvalidate(ExcelImportConstant.trimString(date), ExcelImportConstant.buyCountString, row, col, errorVaildate);
		Integer buyCount = null;
		try {
			buyCount = Integer.parseInt(date);// 购买量选择			
		} catch (RuntimeException e) {
			errorVaildate.append(alterString(name, row, col, ExcelImportConstant.numException));
			throw new RuntimeException(alterString(name, row, col, ExcelImportConstant.numException));
		}
		if (buyCount != 1 && buyCount != 3 && buyCount != 5) {
			errorVaildate.append(alterString(name, row, col, ExcelImportConstant.buyCounterror));
			throw new RuntimeException(alterString(name, row, col, ExcelImportConstant.buyCounterror));
		}
		return buyCount;
	}

	public String alterString(String name, int row, int col, String message) {
		return ExcelImportConstant.di + row + ExcelImportConstant.rowString + ExcelImportConstant.exist
				+ ExcelImportConstant.di + (col + 1) + ExcelImportConstant.guohao1 + name + ExcelImportConstant.guohao2
				+ message + ExcelImportConstant.htmlwraprow;
	}

	/**
	 * 验证批发优惠价
	 * 
	 * @param date
	 * @param name
	 * @param row
	 * @param col
	 * @param call
	 * @return
	 */
	public String showPricevalidate(String date, String name, int row, int col,  Integer buyCount,
			BigDecimal activityPrice1, BigDecimal activityPrice2, BigDecimal activityPrice3) {
		ExcelImportConstant.notisNullvalidate(date, name, row, col,errorVaildate);		
		if (buyCount == 1) {
			BigDecimal onePrice=null;
			// 购买数量选择1 优惠价必须和购买价格1相同
			try {
				onePrice=new BigDecimal(ExcelImportConstant.trimString(date)).setScale(2,BigDecimal.ROUND_HALF_DOWN);
			} catch (Exception e) {
				errorVaildate.append(alterString(name, row, col, ExcelImportConstant.numException));
				throw new RuntimeException(alterString(name, row, col, ExcelImportConstant.numException));
			}
			if (!onePrice.equals(activityPrice1.setScale(2,BigDecimal.ROUND_HALF_DOWN))) {
				errorVaildate.append(alterString(name, row, col, ExcelImportConstant.activityPrice));
				throw new RuntimeException(alterString(name, row, col, ExcelImportConstant.activityPrice));
			}
			date=new BigDecimal(ExcelImportConstant.trimString(date)).setScale(2,   BigDecimal.ROUND_DOWN).toString();
		} else if (buyCount == 3) {
			if (ExcelImportConstant.trimString(date).indexOf("-") >= 0) {
				String[] price = ExcelImportConstant.trimString(date).split("-");
				BigDecimal maxprice = new BigDecimal(price[1].trim()).setScale(2,BigDecimal.ROUND_HALF_DOWN);
				BigDecimal minprice = new BigDecimal(price[0].trim()).setScale(2,BigDecimal.ROUND_HALF_DOWN);
				if (activityPrice1.subtract(activityPrice2).doubleValue() > 0) {
					// 购买价格1大于购买价格2
					if (maxprice.compareTo(activityPrice1)!=0 || minprice.compareTo(activityPrice2)!=0) {
						errorVaildate.append(alterString(name, row, col, ExcelImportConstant.activityPrice));
						throw new RuntimeException(alterString(name, row, col, ExcelImportConstant.activityPrice));
					}
				} else {
					if (maxprice.compareTo(activityPrice1)!=0 || minprice.compareTo(activityPrice2)!=0) {
						errorVaildate.append(alterString(name, row, col, ExcelImportConstant.activityPrice));
						throw new RuntimeException(alterString(name, row, col, ExcelImportConstant.activityPrice));
					}
				}
			}
		} else {
			if (ExcelImportConstant.trimString(date).indexOf("-") >= 0) {
				String[] price = ExcelImportConstant.trimString(date).split("-");
				BigDecimal maxprice = new BigDecimal(price[1].trim());
				BigDecimal minprice = new BigDecimal(price[0].trim());
				BigDecimal[] sort = { activityPrice1, activityPrice2, activityPrice3 };
				Arrays.sort(sort); // 从小到大排序
				if (maxprice.compareTo(sort[2])!=0 || minprice.compareTo(sort[0])!=0) {
					errorVaildate.append(alterString(name, row, col, ExcelImportConstant.activityPrice));
					throw new RuntimeException(alterString(name, row, col, ExcelImportConstant.activityPrice));
				}
			}
		}			
		return date;
	}

	/**
	 * 购买价格2的验证
	 * 
	 * @param date
	 * @param name
	 * @param row
	 * @param col
	 * @param call
	 * @param buyCount
	 * @return
	 */
	public BigDecimal twoactivityPricevalidate(String date, String name, int row, int col,
			Integer buyCount, String type,BigDecimal onePrice) {

		BigDecimal showPrice = null;
		if(buyCount==1){
			if (date != null) {
				vlidate(name, row, col);
			}
		}else{
			ExcelImportConstant.notisNullvalidate(date, name, row, col,errorVaildate);
			try {				
				showPrice = new BigDecimal(date).setScale(2,   BigDecimal.ROUND_DOWN);				
			} catch (Exception e) {
				errorVaildate.append(alterString(name, row, col, ExcelImportConstant.numException));
				throw new RuntimeException(alterString(name, row, col, ExcelImportConstant.numException));
			}
			if (showPrice.compareTo(new BigDecimal(0))!=1) {
				vildatefour(name, row, col);
			}
			if(showPrice.compareTo(onePrice)!=-1){
				errorVaildate.append(alterString(name, row, col, ExcelImportConstant.towpriceerrorString));
				throw new RuntimeException(alterString(name, row, col, ExcelImportConstant.towpriceerrorString));
			}
		}
		return showPrice;
	}
	
	/**
	 * 购买价格3的验证
	 * 
	 * @param date
	 * @param name
	 * @param row
	 * @param col
	 * @param call
	 * @param buyCount
	 * @return
	 */
	public BigDecimal activityPricevalidate(String date, String name, int row, int col,
			Integer buyCount, String type,BigDecimal towPrice) {

		BigDecimal showPrice = null;
		if(buyCount==1 || buyCount==3){
			if (date != null) {
				vlidate(name, row, col);
			}
		}else{
			ExcelImportConstant.notisNullvalidate(date, name, row, col,errorVaildate);
			try {
				showPrice = new BigDecimal(date).setScale(2,   BigDecimal.ROUND_DOWN);				
			} catch (Exception e) {
				errorVaildate.append(alterString(name, row, col, ExcelImportConstant.numException));
				throw new RuntimeException(alterString(name, row, col, ExcelImportConstant.numException));
			}
			if (showPrice.compareTo(new BigDecimal(0))!=1) {
				vildatefour(name, row, col);
			}
			if(showPrice.compareTo(towPrice)!=-1){
				errorVaildate.append(alterString(name, row, col, ExcelImportConstant.threepriceerrorString));
				throw new RuntimeException(alterString(name, row, col, ExcelImportConstant.threepriceerrorString));
			}
		}
		return showPrice;
	}
	/**
	 * 购买数量2的验证
	 * 
	 * @param date
	 * @param name
	 * @param row
	 * @param col
	 * @return
	 */
	public Integer twoBuyCountValidate(String date, String name, int row, int col, Integer buyCount,
			String type) {

		Integer twoBuyCount = null;
		if(buyCount==1){
			if (date != null) {
				vlidate(name, row, col);
			}
		}else{
			ExcelImportConstant.notisNullvalidate(date, name, row, col, errorVaildate);
			try{
				twoBuyCount = Integer.parseInt(date);						
			}catch(RuntimeException e){
				errorVaildate.append(alterString(name, row, col, ExcelImportConstant.numException));
				throw new  RuntimeException(alterString(name, row, col, ExcelImportConstant.numException));
			}
			if (Integer.parseInt(date) <=0) {
				vildatefour(name, row, col);
			}	
		}
		return twoBuyCount;
	}
	/**
	 * 购买数量4的验证
	 * 
	 * @param date
	 * @param name
	 * @param row
	 * @param col
	 * @return
	 */
	public Integer fourBuyCountValidate(String date, String name, int row, int col, Integer buyCount,
			String type) {

		Integer twoBuyCount = null;
		if(buyCount==1 || buyCount==3){
			if (date != null) {
				vlidate(name, row, col);
			}
		}else{
			ExcelImportConstant.notisNullvalidate(date, name, row, col, errorVaildate);
			try{
				twoBuyCount = Integer.parseInt(date);				
			}catch(RuntimeException e){
				errorVaildate.append(alterString(name, row, col, ExcelImportConstant.numException));
				throw new  RuntimeException(alterString(name, row, col, ExcelImportConstant.numException));
			}	
			if (twoBuyCount <= 0) {
				vildatefour(name, row, col);
			}			
		}		
		return twoBuyCount;
	}

	private void vildatefour(String name, int row, int col) {
		errorVaildate.append(alterString(name, row, col, ExcelImportConstant.buyCountNotNull));
		throw new RuntimeException(alterString(name, row, col, ExcelImportConstant.buyCountNotNull));
	}

	private void vlidate(String name, int row, int col) {
		errorVaildate.append(alterString(name, row, col, ExcelImportConstant.buyCountIsNull));
		throw new RuntimeException(alterString(name, row, col, ExcelImportConstant.buyCountIsNull));
	}

	/***
	 * 购买数量3的验证
	 * 
	 * @param date
	 * @param name
	 * @param row
	 * @param col
	 * @param call
	 * @param buyCount
	 * @param type
	 * @param towBuyCount
	 * @return
	 */
	public Integer buyCountValidate(String date, String name, int row, int col, Integer buyCount,
			String type, Integer towBuyCount) {

		Integer threeBuyCount = null;
		
		if(buyCount==1){
			if (date != null) {
				vlidate(name, row, col);
			}
		}else{
			ExcelImportConstant.notisNullvalidate(date, name, row, col, errorVaildate);
			try{
				threeBuyCount = Integer.parseInt(date);				
			}catch(RuntimeException e){
				errorVaildate.append(alterString(name, row, col, ExcelImportConstant.numException));
				throw new  RuntimeException(alterString(name, row, col, ExcelImportConstant.numException));
			}		
			if (threeBuyCount<= 0) {
				vildatefour(name, row, col);					
			}
			if (threeBuyCount != (towBuyCount + 1)) {				
				errorVaildate.append(alterString(name, row, col, ExcelImportConstant.threerro));		
				throw new  RuntimeException(alterString(name, row, col, ExcelImportConstant.threerro));
			}	
		}
		
		return threeBuyCount;
	}
	
	/***
	 * 购买数量5的验证
	 * 
	 * @param date
	 * @param name
	 * @param row
	 * @param col
	 * @param call
	 * @param buyCount
	 * @param type
	 * @param towBuyCount
	 * @return
	 */
	public Integer fivebuyCountValidate(String date, String name, int row, int col, Integer buyCount,
			String type, Integer towBuyCount) {

		Integer threeBuyCount = null;
		
		if(buyCount==1 || buyCount==3){
			if (date != null) {
				vlidate(name, row, col);
			}
		}else{
			ExcelImportConstant.notisNullvalidate(date, name, row, col, errorVaildate);
			try{
				threeBuyCount = Integer.parseInt(date);												
			}catch(RuntimeException e){
				errorVaildate.append(alterString(name, row, col, ExcelImportConstant.numException));
				throw new  RuntimeException(alterString(name, row, col, ExcelImportConstant.numException));
			}	
			if (threeBuyCount <= 0) {
				vildatefour(name, row, col);
			}
			if (threeBuyCount != (towBuyCount + 1)) {					
				errorVaildate.append(alterString(name, row, col, ExcelImportConstant.fiveerro));
				throw new  RuntimeException(alterString(name, row, col, ExcelImportConstant.fiveerro));
			}	
		}
		return threeBuyCount;
	}
}
