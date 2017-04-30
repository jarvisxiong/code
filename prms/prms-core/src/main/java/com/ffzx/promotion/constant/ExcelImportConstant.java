package com.ffzx.promotion.constant;

import java.math.BigDecimal;
import java.util.Vector;

import com.ffzx.commerce.framework.utils.StringUtil;

public   final class ExcelImportConstant {
	
	public static  final String FORMAT_DATETIME="yyyy/MM/dd HH:mm:ss";
	public static  final String di="第";
	public static  String rowString="行,";
	public static  String exist="存在";
	//验证结果，异常信息
	public static  final String isnulldate="为空";
	public static  final String tiaoxingmaExists="和前面sku条形码重复";
	public static  final String numException="转换数字失败【且excel格式必须为文本】";
	public static  final String numdayu0Exception="数字必须大于0";
	public static  final String dateformException= "格式不对的 参考格式(yyyy-MM-dd HH:mm:ss)或(yyyy/MM/dd HH:mm:ss)【且excel格式必须为文本】";
	public static  final String lableTagException="查询不到对应的数据";
	public static  final String delivedateException="发货时间必须晚于开始时间";
	public static  final String buyCountIsNull="必须为空";
	public static  final String buyCountNotNull="必须大于0";
	public static  final String threerro="必须是购买数量2的值加1";
	public static  final String fiveerro="必须是购买数量4的值加1";
	public static  final String activityPrice="活动优惠价请保持一致";
	public static  final String guohao1="列【";
	public static  final String guohao2="】";
	public static  final String htmlwraprow=",请核对信息 <br />";
	//标题
	public static  final String tiaoxingmaString="sku条形码";
	public static  final String titleString="标题";
	public static  final String lableTag="所属标签";
	public static  final String sorttopString= "置顶序号";
	public static  final String showPriceString="活动优惠价";
	public static  final String limitCountString="限定数量";
	public static  final String idLimitCountString="ID限定数量";
	public static  final String saleIncreaseString="销量增量值";
	public static  final String sortNoString="排序号";
	public static  final String delivedateString="发货时间";
	public static final String buyCountString="购买数量选择";
	public static final String buyCounterror="购买数量选择错误必须选择1或者3或者5";
	public static final String oneBuyCountString="购买数量1";
	public static final String twoBuyCountString="购买数量2";
	public static final String threeBuyCountString="购买数量3";
	public static final String fourBuyCountString="购买数量4";
	public static final String fiveBuyCountString="购买数量5";
	public static final String onePriceString="价格1";
	public static final String twoPriceString="价格2";
	public static final String threePriceString="价格3";
	public static final String towpriceerrorString="价格2必须小于价格1";
	public static final String threepriceerrorString="价格3必须小于价格2";
	public static  final String activityManagermessage="活动管理对象activityManager==null";
	public static  final String commodityskuExists="sku条形码匹配不到任何商品信息，请检查是否存在或者已删除或已禁用";
	public static  final String commodityskuStatus="sku条形码匹配到的商品未上架";
	public static  final String tiaoxingmashuxin="条形码匹配到的商品是个有辅助属性商品";
	
	public static  final String activityCommodtitySdate="商品设置活动开始时间为空，请联系后台人员activityCommoditySku.getActivityCommodity().getStartDate()==null";
	public static  final String activityCommodtityEdate="商品设置活结束始时间为空，请联系后台人员activityCommoditySku.getActivity().getEndDate()==null";
	public static   String activityCommodtityExistsActiviy(String activityname){
		return "已经存在【 "+activityname+"】活动中,同一个批次不能存在两个不同商品";
	}
	public static  String overlapDate(String activityName,String activityNo){
		return "已经存在【 "+activityName+
		"】活动中,活动管理编号"+activityNo;
	}
	
	/**
	 * 置顶序号验证
	 */
	public static Integer sortTopValidate(String date,String name,int row,int col,StringBuffer errorVaildate){
		notisNullvalidate(date, name, row, col,errorVaildate);
		Integer sortTopNo=null;
		try{
			sortTopNo=Integer.parseInt(date);//置顶序号
		}catch(RuntimeException e){
			errorVaildate.append(alterString(name, row, col, numException));//和前面条形码重复
			throw new  RuntimeException(alterString(name, row, col, numException));
		}
		if(sortTopNo<0){
			errorVaildate.append(alterString(name, row, col, ExcelImportConstant.buyCountNotNull));
			throw new RuntimeException(alterString(name, row, col, ExcelImportConstant.buyCountNotNull));
		}
		return sortTopNo;
	}
	/**
	 * 活动优惠验证
	 */
	public static BigDecimal showPricevalidate(String date,String name,int row,int col,StringBuffer errorVaildate){
		notisNullvalidate(date, name, row, col,errorVaildate);
		BigDecimal showPrice=null;
		try{
			showPrice=new BigDecimal(date).setScale(2,   BigDecimal.ROUND_DOWN);			
		}catch(Exception e){
			errorVaildate.append(alterString(name, row, col, numException));//和前面条形码重复
			throw new  RuntimeException(alterString(name, row, col, numException));
		}	
		if (showPrice.compareTo(new BigDecimal(0))!=1) {
			errorVaildate.append(alterString(name, row, col, ExcelImportConstant.buyCountNotNull));
			throw new RuntimeException(alterString(name, row, col, ExcelImportConstant.buyCountNotNull));
		}
		return showPrice;
	}
	/**
	 * 条形码验证
	 */
	public static void tiaoxingma(String date,String name,int row,int col,Vector<String> comparisonList,StringBuffer errorVaildate){
		notisNullvalidate(date, name, row, col,errorVaildate);
		if(comparisonList.contains(date)){
			errorVaildate.append(alterString(name, row, col, tiaoxingmaExists));//和前面条形码重复
			throw new RuntimeException(alterString(name, row, col, tiaoxingmaExists));
		}	
	}
	
	/**
	 * 非空验证
	 * @param date
	 * @param name
	 * @param row
	 * @param col
	 */
	public static void notisNullvalidate(String date,String name,int row,int col,StringBuffer errorVaildate){
		if (StringUtil.isEmpty(date)){
			errorVaildate.append(alterString(name, row, col, isnulldate));
			throw new RuntimeException(alterString(name, row, col, isnulldate));
		}
	}
	/**
	 * 清除左右空格
	 * @param date
	 * @return
	 */
	public static String trimString(String date){
		if(StringUtil.isEmpty(date))
			return null;
		return date.trim();
	}
	/**
	 * 提示组合
	 * @param name
	 * @param row
	 * @param col
	 * @param message
	 * @return "第"+row+"行,存在第"+col+"列【name】message，请核对信息"
	 */
	public  static String alterString(String name,int row,int col,String message){
		return di+row+rowString+exist+di+(col+1)+guohao1+name+guohao2+message+htmlwraprow;
	}
	
	/**
	 * 限定数量验证
	 */
	public static Integer limitCountValidate(String date,String name,int row,int col,StringBuffer errorVaildate){
		notisNullvalidate(date, name, row, col,errorVaildate);
		Integer limitCount=null;
		try{
			limitCount=Integer.parseInt(date);//限定数量
		}catch(NumberFormatException e){
			errorVaildate.append(alterString(name, row, col, numException));//和前面条形码重复
			throw new  RuntimeException(alterString(name, row, col, numException));
		}
		if(limitCount<0){
			errorVaildate.append(alterString(name, row, col, ExcelImportConstant.buyCountNotNull));
			throw new RuntimeException(alterString(name, row, col, ExcelImportConstant.buyCountNotNull));
		}
		return limitCount;
	}
}
