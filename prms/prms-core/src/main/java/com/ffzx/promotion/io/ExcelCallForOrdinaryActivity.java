package com.ffzx.promotion.io;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import com.ffzx.commerce.framework.constant.Constant;
import com.ffzx.commerce.framework.utils.UUIDGenerator;
import com.ffzx.commodity.api.dto.CommoditySku;
import com.ffzx.promotion.api.dto.ActivityCommodity;
import com.ffzx.promotion.api.dto.ActivityCommoditySku;
import com.ffzx.promotion.api.dto.ActivityManager;
import com.ffzx.promotion.api.enums.ActivityTypeEnum;
import com.ffzx.promotion.api.service.consumer.CommoditySkuApiConsumer;
import com.ffzx.promotion.constant.ExcelImportConstant;
import com.ffzx.promotion.service.ActivityCommoditySkuService;
import com.ffzx.promotion.service.impl.ActivityCommodityServiceImpl;
import com.ffzx.promotion.util.ExcelUtils;

public class ExcelCallForOrdinaryActivity extends ActivityCommodityServiceImpl implements Callable<String> {

	private ActivityCommoditySkuService ctivityCommoditySkuService;
	private CommoditySkuApiConsumer commoditySkuApiConsumer;
	private int i;
	private String[] data;
	private ActivityManager activityManager;
	private ActivityTypeEnum enum1;

	public ExcelCallForOrdinaryActivity(int i, String[] data, ActivityManager activityManager, ActivityTypeEnum enum1,
			ActivityCommoditySkuService ctivityCommoditySkuService, CommoditySkuApiConsumer commoditySkuApiConsumer) {
		this.i = i;
		this.data = data;
		this.activityManager = activityManager;
		this.enum1 = enum1;
		this.commoditySkuApiConsumer = commoditySkuApiConsumer;
		this.ctivityCommoditySkuService = ctivityCommoditySkuService;
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
			ExcelImportConstant.tiaoxingma(ExcelImportConstant.trimString(data[col]), ExcelImportConstant.tiaoxingmaString, row, col, comparisonList, errorVaildate);
			comparisonList.add(data[col].trim());
			++col;
			// 标题仅仅验证null
			ExcelImportConstant.notisNullvalidate(ExcelImportConstant.trimString(data[col]), ExcelImportConstant.titleString, row, col,errorVaildate);
			++col;
			// 置顶序号
			Integer sortTopNo = ExcelImportConstant.sortTopValidate(ExcelImportConstant.trimString(data[col]), ExcelImportConstant.sorttopString, row,
					col,errorVaildate);
			++col;
			// 优惠价
			BigDecimal showPrice = ExcelImportConstant.showPricevalidate(ExcelImportConstant.trimString(data[col]),
					ExcelImportConstant.showPriceString, row, col,errorVaildate);
			++col;
			CommoditySku commoditysku = commoditySkuApiConsumer.getCommoditySku(data[0].trim());

			verficationCommodity(commoditysku, row, activityManager);// 商品批次验证
			comparisonListcode.add(commoditysku.getCommodity().getCode());
			
			ActivityCommodity activityCommdity = new ActivityCommodity();
			ActivityCommoditySku activityCommoditySku = new ActivityCommoditySku();
			activityCommdity.setId(UUIDGenerator.getUUID());
			activityCommdity.setActivityNo(activityManager.getActivityNo());
			activityCommdity.setActivityType(enum1);
			activityCommdity.setCommodityNo(commoditysku.getCommodity().getCode());// 商品编号
			activityCommdity.setCommodityId(commoditysku.getCommodity().getId());// 商品ID
			activityCommdity.setActivityTitle(data[1].trim());// 商品标题
			activityCommdity.setCommodityBarcode(commoditysku.getCommodity().getBarCode());
			activityCommdity.setShowPrice(showPrice.toString());
			activityCommdity.setSortTopNo(sortTopNo);
			activityCommdity.setIsRecommend(Constant.NO);
			activityCommdity.setMaxPrice(showPrice);// 最大价格
			activityCommdity.setMinPrice(showPrice);// 最小价格
			activityCommdity.setEnableSpecialCount(Constant.NO);// 是否启用特殊数量值
																// 1:启用,0:不启用.
			activityCommdity.setActivity(activityManager);
			activityCommdity.setIslimit(Constant.NO);// APP是否显示限定数量，0否，1是.
			activityCommdity.setIsNeworder(Constant.NO);
			activityCommoditySku.setId(UUIDGenerator.getUUID());
			activityCommoditySku.setActivityNo(activityManager.getActivityNo());
			activityCommoditySku.setActivityPrice(showPrice);
			activityCommoditySku.setCommoditySkuId(commoditysku.getId());// skuid
			activityCommoditySku.setCommoditySkuNo(commoditysku.getSkuCode());
			activityCommoditySku.setCommoditySkuBarcode(commoditysku.getBarcode());
			activityCommoditySku.setCommoditySkuTitle(commoditysku.getCommodity().getTitle());
			activityCommoditySku.setActivityType(enum1);
			activityCommoditySku.setActivity(activityManager);
			activityCommoditySku.setActivityCommodity(activityCommdity);
			activityCommoditySku.setCommoditySkuPrice(showPrice);
			activityCommditys.add(activityCommdity);
			activityCommoditySkus.add(activityCommoditySku);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.info("", e);
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
	public boolean verficationCommodity(CommoditySku commoditysku, int row, ActivityManager activityManager) throws Exception {
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
		} else if (commoditysku.getCommodity().getStatus() == null
				|| !commoditysku.getCommodity().getStatus().equals("COMMODITY_STATUS_ADDED")) {

			errorVaildate.append(alterString(ExcelImportConstant.tiaoxingmaString, row, col,
					ExcelImportConstant.commodityskuStatus));
			throw new RuntimeException(alterString(ExcelImportConstant.tiaoxingmaString, row, col,
					ExcelImportConstant.commodityskuStatus));
		}
		// 为null或者空字符串为无属性商品
		if (commoditysku.getCommodity().getSaleAttributeIds() == null
				|| commoditysku.getCommodity().getSaleAttributeIds().trim().equals("")) {

			// 根据传递sku条形码是否能查到数据
			
			Map<String, Object> params=new HashMap<String, Object>();
			if(commoditysku.getCommodity().getBuyType().equals(ActivityTypeEnum.WHOLESALE_MANAGER.getValue())){
				params.put("commodityNo", commoditysku.getCommodity().getCode());
			}else{
				params.put("commoditySkuId", commoditysku.getId());
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
		} else {
			// 条形码匹配到的商品是个有辅助属性商品
			errorVaildate.append(
					alterString(ExcelImportConstant.tiaoxingmaString, row, col, ExcelImportConstant.tiaoxingmashuxin));
			throw new RuntimeException(
					alterString(ExcelImportConstant.tiaoxingmaString, row, col, ExcelImportConstant.tiaoxingmashuxin));

		}
		return true;
	}

	private String alterString(String name, int row, int col, String message) {
		return ExcelImportConstant.di + row + ExcelImportConstant.rowString + ExcelImportConstant.exist
				+ ExcelImportConstant.di + (col + 1) + ExcelImportConstant.guohao1 + name + ExcelImportConstant.guohao2
				+ message + ExcelImportConstant.htmlwraprow;
	}
}
