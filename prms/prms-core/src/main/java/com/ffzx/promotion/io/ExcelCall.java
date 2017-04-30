package com.ffzx.promotion.io;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import org.springframework.beans.factory.annotation.Autowired;

import com.ffzx.commerce.framework.constant.Constant;
import com.ffzx.commerce.framework.utils.DateUtil;
import com.ffzx.commerce.framework.utils.StringUtil;
import com.ffzx.commerce.framework.utils.UUIDGenerator;
import com.ffzx.commodity.api.dto.CommoditySku;
import com.ffzx.promotion.api.dto.ActivityCommodity;
import com.ffzx.promotion.api.dto.ActivityCommoditySku;
import com.ffzx.promotion.api.dto.ActivityManager;
import com.ffzx.promotion.api.dto.ActivityPreSaleTag;
import com.ffzx.promotion.api.enums.ActivityTypeEnum;
import com.ffzx.promotion.api.service.consumer.CommoditySkuApiConsumer;
import com.ffzx.promotion.model.CommoditySkuTransform;
import com.ffzx.promotion.service.ActivityCommoditySkuService;
import com.ffzx.promotion.service.ActivityPreSaleTagService;
import com.ffzx.promotion.service.impl.ActivityCommodityServiceImpl;
import com.ffzx.promotion.util.ExcelUtils;

public class ExcelCall extends ActivityCommodityServiceImpl  implements Callable<String> {

    private ActivityCommoditySkuService ctivityCommoditySkuService;
    private CommoditySkuApiConsumer commoditySkuApiConsumer;
	private ActivityCommoditySkuService activityCommoditySkuService;
	private int i;
	private String[] data;
	private ActivityManager activityManager;
	private ActivityTypeEnum enum1;
	private ActivityPreSaleTagService activityPreSaleTagService;
	public ExcelCall(int i,String[] data,ActivityManager activityManager,ActivityTypeEnum enum1
			,ActivityCommoditySkuService ctivityCommoditySkuService,CommoditySkuApiConsumer commoditySkuApiConsumer,
			ActivityCommoditySkuService activityCommoditySkuService,ActivityPreSaleTagService activityPreSaleTagService){
		this.i=i;
		this.data=data;
		this.activityManager=activityManager;
		this.enum1=enum1;
		this.commoditySkuApiConsumer=commoditySkuApiConsumer;
		this.ctivityCommoditySkuService=ctivityCommoditySkuService;
		this.activityCommoditySkuService=activityCommoditySkuService;
		this.activityPreSaleTagService=activityPreSaleTagService;
	}

	public ExcelCall(){}
	
	private final String FORMAT_DATETIME="yyyy/MM/dd HH:mm:ss";
	private final String di="第";
	private String rowString="行,";
	private String exist="存在";
	//验证结果，异常信息
	private final String isnulldate="为空";
	private final String tiaoxingmaExists="和前面sku条形码重复";
	private final String numException="转换数字失败【且excel格式必须为文本】";
	private final String wuskuException="无sku商品【且excel格式必须为文本】";
	private final String skutypeException="sku商品ActivityType不能为null【且excel格式必须为文本】";
	
	private final String zhengshuException="不能为负数【且excel格式必须为文本】";
	private final String iddayuException="id限购大于总限购";
	private final String numdayu0Exception="数字必须大于0";
	private final String dateformException= "格式不对的 参考格式(yyyy-MM-dd HH:mm:ss)或(yyyy/MM/dd HH:mm:ss)【且excel格式必须为文本】";
	private final String startxiaoyuendException= "【开始时间大于等于结束时间】【且excel格式必须为文本】";
	private final String lableTagException="查询不到对应的数据";
	private final String delivedateException="发货时间必须晚于开始时间";
	private final String  startbijiaoException = "开始时间小于于批次开始时间";
	private final String  endbijiaoException ="结束时间大于批次结束时间";
	private final String guohao1="列【";
	private final String guohao2="】";
	private final String htmlwraprow=",请核对信息 <br />";
	//标题
	private final String tiaoxingmaString="sku条形码";
	private final String titleString="标题";
	private final String lableTag="所属标签";
	private final  int startDatecol=3;
	private final  int endDatecol=4;
	private final String startDateString="开始时间";
	private final String endDateString="结束时间";
	private final String sorttopString= "置顶序号";
	private final String showPriceString="活动优惠价";
	private final String limitCountString="限定数量";
	private final String idLimitCountString="ID限定数量";
	private final String saleIncreaseString="销量增量值";
	private final String sortNoString="排序号";
	private final String delivedateString="发货时间";
	
	
	private final String activityManagermessage="活动管理对象activityManager==null";
	private final String commodityskuExists="匹配不到商品，商品条形码必须为SKU条形码，且没有禁用、删除。";
	private final String commodityskuStatus="sku条形码匹配到的商品未上架";
	private final String tiaoxingmashuxin="条形码匹配到的商品是个有辅助属性商品";
	
	private final String activityCommodtitySdate="商品设置活动开始时间为空，请联系后台人员activityCommoditySku.getActivityCommodity().getStartDate()==null";
	private final String activityCommodtityEdate="商品设置活结束始时间为空，请联系后台人员activityCommoditySku.getActivity().getEndDate()==null";
	private  String activityCommodtityExistsActiviy(String activityname){
		return "已经存在【 "+activityname+"】活动中,同一个批次不能存在两个相同商品";
	}
	private String overlapDate(String activityName,String activityNo){
		return "已经存在【 "+activityName+
		"】活动中,时间重叠,活动管理编号"+activityNo;
	}

	@Override
	public String call(){
		// TODO Auto-generated method stub

		try {
			 int col=0;//第几列
			 int row=i+2;//第几行
			
			if(!ExcelUtils.getIsSuccess(data)){
				return "success";
			}
			//条形码验证
			tiaoxingma(trimString(data[col]), tiaoxingmaString, row, col);
			++col;
			//标题仅仅验证null
			notisNullvalidate(trimString(data[col]), titleString, row, col);
			++col;
			//所属标签,预售专用
			ActivityPreSaleTag labletag=null;
			Date datestart=null;
			Date dateend=null;
			if(enum1.equals(ActivityTypeEnum.PRE_SALE)){
				labletag=labelTagvalidate(trimString(data[col]), lableTag, row, col);//所属标签，预售专享
				++col;

				datestart=startDatevalidate(trimString(data[col]), startDateString, row, col);//开始时间
				++col;
				dateend=endDatevalidate(trimString(data[col]), endDateString, row, col,datestart);//结束时间
				++col;
			}
			//置顶序号
			Integer sortTopNo =sortTopValidate(trimString(data[col]), sorttopString, row, col);
			++col;
			//优惠价
			BigDecimal showPrice=showPricevalidate(trimString(data[col]), showPriceString, row, col);
			++col;
			//限购数量
			Integer limitCount=limitCountValidate(trimString(data[col]), limitCountString, row, col);
			++col;
			//id限定数量
			Integer idLimitCount=idLimitCountValidate(trimString(data[col]),idLimitCountString, row, col,limitCount);
			++col;
			//销量增量值
			Integer saleIncrease=saleIncreaseValidate(trimString(data[col]),saleIncreaseString, row, col);
			++col;
			//排序号
			Integer sortNo=sortNoValidate(trimString(data[col]),sortNoString, row, col);
			++col;
			//预售发货时间
			Date delivedate=null;
			if(enum1.equals(ActivityTypeEnum.PRE_SALE)){
				delivedate=delivedateValidate(trimString(data[col]),delivedateString, row, col,datestart);
				++col;
			}
			CommoditySku commoditysku=commoditySkuApiConsumer.getCommoditySku(trimString(data[0]));
				verficationCommodity(commoditysku,row,activityManager,datestart,dateend);
			//商品批次验证
			comparisonListcode.add(commoditysku.getCommodity().getCode());//提供给外部更新
			//验证库存
			getskuTransformList(trimString(data[0]),commoditysku.getCommodity().getCode(),limitCount, row, col);
			
			ActivityCommodity activityCommdity=new ActivityCommodity();
			ActivityCommoditySku activityCommoditySku=new ActivityCommoditySku();
			//写入ActivityCommodity一部分value
			setActivityCommodity(commoditysku, activityCommdity,datestart,dateend);
			activityCommdity.setShowPrice(showPrice.toString());
			activityCommdity.setSortTopNo(sortTopNo);
			activityCommdity.setSortNo(sortNo);
			activityCommdity.setIdLimitCount(idLimitCount);//单个用户限购数量.
			activityCommdity.setLimitCount(limitCount);//限购数量.
			activityCommdity.setSaleIncrease(saleIncrease);//销量增量值.
	//		activityCommdity.setSpecialCount(specialCount);// 特殊数量值
			activityCommdity.setDeliverDate(delivedate);//发货时间
			activityCommdity.setPreSaleTag(labletag);
			//写入activityCommoditySku一部分value
			setActivityCommoditySku(commoditysku, activityCommoditySku);
			activityCommoditySku.setActivityPrice(showPrice);
			activityCommoditySku.setLimitCount(limitCount);
			activityCommoditySku.setActivityCommodity(activityCommdity);
			activityCommoditySku.setCommoditySkuPrice(showPrice);
			activityCommditys.add(activityCommdity);
			activityCommoditySkus.add(activityCommoditySku);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("",e);
		}
		return "success";
	}
	/**
	 * //写入activityCommoditySku一部分value
	 * @param commoditysku
	 * @param activityCommdity
	 */
	private void setActivityCommoditySku(CommoditySku commoditysku,ActivityCommoditySku activityCommoditySku){

		activityCommoditySku.setId(UUIDGenerator.getUUID());
		activityCommoditySku.setActivityNo(activityManager.getActivityNo());
		activityCommoditySku.setActivityType(enum1);
		activityCommoditySku.setActivity(activityManager);
		activityCommoditySku.setCommoditySkuId(commoditysku.getId());//skuid
		activityCommoditySku.setCommoditySkuNo(commoditysku.getSkuCode());
		activityCommoditySku.setCommoditySkuBarcode(commoditysku.getBarcode());
		activityCommoditySku.setCommoditySkuTitle(commoditysku.getCommodity().getTitle());
	}
	/**
	 * //写入ActivityCommodity一部分value
	 * @param commoditysku
	 * @param activityCommdity
	 */
	private void setActivityCommodity(CommoditySku commoditysku,ActivityCommodity activityCommdity
			,Date datestart,Date dateend){

		activityCommdity.setId(UUIDGenerator.getUUID());
		activityCommdity.setActivityNo(activityManager.getActivityNo());
		if(enum1.equals(ActivityTypeEnum.PANIC_BUY)){
			activityCommdity.setStartDate(activityManager.getStartDate());//开始时间
			activityCommdity.setEndDate(activityManager.getEndDate());//结束时间
		}else{
			activityCommdity.setStartDate(datestart);//开始时间
			activityCommdity.setEndDate(dateend);//结束时间
		}
		activityCommdity.setActivity(activityManager);
		activityCommdity.setActivityType(enum1);
		activityCommdity.setCommodityNo(commoditysku.getCommodity().getCode());//商品编号
		activityCommdity.setCommodityId(commoditysku.getCommodity().getId());//商品ID
//		activityCommdity.setActivityTitle(commoditysku.getCommodity().getTitle());//商品标题
		activityCommdity.setActivityTitle(trimString(data[1]));//商品标题
		activityCommdity.setCommodityBarcode(commoditysku.getCommodity().getBarCode());
		activityCommdity.setIsRecommend(Constant.NO);
		activityCommdity.setRecommendSort(0);//推荐商品排序.
		activityCommdity.setEnableSpecialCount(Constant.NO);// 是否启用特殊数量值 1:启用,0:不启用.
		activityCommdity.setIslimit(Constant.NO);// APP是否显示限定数量，0否，1是.
		activityCommdity.setIsNeworder(Constant.NO);
	}
	/**
	 * 条形码验证
	 */
	public  void tiaoxingma(String date,String name,int row,int col){
		if(comparisonList.contains(date)){
			errorVaildate.append(alterString(name, row, col, tiaoxingmaExists));//和前面条形码重复
			throw new RuntimeException(alterString(name, row, col, tiaoxingmaExists));
		}
		comparisonList.add(date);
		notisNullvalidate(date, name, row, col);
	}
	/**
	 * 预售标签验证
	 * @param date
	 * @param name
	 * @param row
	 * @param col
	 * @return
	*/ 
	private ActivityPreSaleTag labelTagvalidate(String date,String name,int row,int col){
		if(enum1.equals(ActivityTypeEnum.PRE_SALE)){	
			notisNullvalidate(date, name, row, col);
			Map<String, Object> params=new HashMap<String, Object>();
			params.put("name", date);
			List<ActivityPreSaleTag> activityPreSaleTags=activityPreSaleTagService.findByBiz(params);
			if(activityPreSaleTags==null || activityPreSaleTags.size()==0){
				//预售标签验证查询不到
				errorVaildate.append(alterString(name, row, col, lableTagException));
				throw new RuntimeException(alterString(name, row, col, lableTagException));
			}
			return activityPreSaleTags.get(0);
		}
		return null;
	}

	/**
	 * 预售开始时间
	 * @param date
	 * @param name
	 * @param row
	 * @param col
	 * @return
	 */
	private Date endDatevalidate(String date,String name,int row,int col,Date datestart){
		Date dateend;
		notisNullvalidate(date, name, row, col);
		try{

			if(date.indexOf("-")>=0){
				dateend=DateUtil.parse(date, "yyyy-MM-dd HH:mm:ss");
			}else if(date.indexOf("/")>=0){
				dateend=DateUtil.parse(date, "yyyy/MM/dd HH:mm:ss");
			}else{
				throw new  RuntimeException(alterString(name, row, col, dateformException));
			}
		}
		catch(Exception e){
			errorVaildate.append(alterString(name, row, col, dateformException));//结束时间格式
			throw new  RuntimeException(alterString(name, row, col, dateformException));
		}

		if(DateUtil.diffDateTime(datestart, dateend)>=0){
			errorVaildate.append(alterString(name, row, col, startxiaoyuendException));//开始时间结束时间比较
			throw new  RuntimeException(alterString(name, row, col, startxiaoyuendException));
		}
		return dateend;
	}

	/**
	 * 预售结束时间
	 * @param date
	 * @param name
	 * @param row
	 * @param col
	 * @return
	 */
	private Date startDatevalidate(String date,String name,int row,int col){
		Date datestart;
		notisNullvalidate(date, name, row, col);
		try{

			if(date.indexOf("-")>=0){
				datestart=DateUtil.parse(date, "yyyy-MM-dd HH:mm:ss");
			}else if(date.indexOf("/")>=0){
				datestart=DateUtil.parse(date, "yyyy/MM/dd HH:mm:ss");
			}else{
				throw new  RuntimeException(alterString(name, row, col, dateformException));
			}
		}
		catch(Exception e){
			errorVaildate.append(alterString(name, row, col, dateformException));//开始时间格式
			throw new  RuntimeException(alterString(name, row, col, dateformException));
		}
		return datestart;
	}
	/**
	 * 置顶序号验证
	 */
	public Integer sortTopValidate(String date,String name,int row,int col){
		notisNullvalidate(date, name, row, col);
		Integer sortTopNo=null;
		try{
			sortTopNo=Integer.parseInt(date);//置顶序号
			
		}catch(RuntimeException e){
			errorVaildate.append(alterString(name, row, col, numException));//
			throw new  RuntimeException(alterString(name, row, col, numException));
		}
		if(sortTopNo.doubleValue()<0){
			errorVaildate.append(alterString(name, row, col, zhengshuException));//
			throw new  RuntimeException(alterString(name, row, col, zhengshuException));
		}
		return sortTopNo;
	}
	/**
	 * 活动优惠验证
	 */
	public BigDecimal showPricevalidate(String date,String name,int row,int col){

		notisNullvalidate(date, name, row, col);
		BigDecimal showPrice=null;
		try{
			showPrice=new BigDecimal(date);
			
		}catch(Exception e){
			errorVaildate.append(alterString(name, row, col, numException));//和前面条形码重复
			throw new  RuntimeException(alterString(name, row, col, numException));
		}
		if(showPrice.doubleValue()<0){
			errorVaildate.append(alterString(name, row, col, zhengshuException));//和前面条形码重复
			throw new  RuntimeException(alterString(name, row, col, zhengshuException));
		}
		return showPrice;
	}
	/**
	 * 限定数量验证
	 */
	public Integer limitCountValidate(String date,String name,int row,int col){
		notisNullvalidate(date, name, row, col);
		Integer limitCount=null;
		try{
			limitCount=Integer.parseInt(date);//限定数量
			
		}catch(NumberFormatException e){
			errorVaildate.append(alterString(name, row, col, numException));//和前面条形码重复
			throw new  RuntimeException(alterString(name, row, col, numException));
		}if(limitCount.doubleValue()<0){
			errorVaildate.append(alterString(name, row, col, zhengshuException));//和前面条形码重复
			throw new  RuntimeException(alterString(name, row, col, zhengshuException));
		}
		return limitCount;
	}
	/**
	 * id限定数量
	 * @param date
	 * @param name
	 * @param row
	 * @param col
	 * @return
	 */
	private Integer idLimitCountValidate(String date,String name,int row,int col,Integer limitCount){

		Integer idLimitCount=null;
			if(!StringUtil.isEmpty(date)){
			try{
				idLimitCount=Integer.parseInt(date);//置顶序号
				if(idLimitCount.doubleValue()<0){
					errorVaildate.append(alterString(name, row, col, zhengshuException));//
					throw new  RuntimeException(alterString(name, row, col, zhengshuException));
				}
				
			}catch(NumberFormatException e){
				errorVaildate.append(alterString(name, row, col, numException));//和前面条形码重复
				throw new  RuntimeException(alterString(name, row, col, numException));
			}
			if(idLimitCount>limitCount){
				errorVaildate.append(alterString(name, row, col, iddayuException));//id>zong
				throw new  RuntimeException(alterString(name, row, col, iddayuException));
			
			}
		}
		return idLimitCount;
	}
	/**
	 * 销量增量值
	 * @param date
	 * @param name
	 * @param row
	 * @param col
	 * @return
	 */
	private Integer saleIncreaseValidate(String date,String name,int row,int col){

		Integer saleIncrease=null;
			if(!StringUtil.isEmpty(date)){
			try{
				saleIncrease=Integer.parseInt(date);//销量增量值
				
			}catch(NumberFormatException e){
				errorVaildate.append(alterString(name, row, col, numException));//销量增量值
				throw new  RuntimeException(alterString(name, row, col, numException));
			}
			if(saleIncrease<0){
				errorVaildate.append(alterString(name, row, col, numdayu0Exception));//销量增量值
				throw new  RuntimeException(alterString(name, row, col, numdayu0Exception));
			}
		}
		return saleIncrease;
	}
	/**
	 * 排序号
	 * @param date
	 * @param name
	 * @param row
	 * @param col
	 * @return
	 */
	public Integer sortNoValidate(String date,String name,int row,int col){

		notisNullvalidate(date, name, row, col);
		Integer sortNo=null;
			if(!StringUtil.isEmpty(date)){
			try{
				sortNo=Integer.parseInt(date);//排序号
				
			}catch(NumberFormatException e){
				errorVaildate.append(alterString(name, row, col, numException));//排序号
				throw new  RuntimeException(alterString(name, row, col, numException));
			}if(sortNo<0){
				errorVaildate.append(alterString(name, row, col, numdayu0Exception));//排序号
				throw new  RuntimeException(alterString(name, row, col, numdayu0Exception));
			}
		}
		return sortNo;
	}
	/**
	 * 预售发货时间
	 * @param date
	 * @param name
	 * @param row
	 * @param col
	 * @return
	 */
	private Date delivedateValidate(String date,String name,int row,int col,Date start){
		Date delivedate=null;
		if(enum1.equals(ActivityTypeEnum.PRE_SALE)){	
			notisNullvalidate(date, name, row, col);		
			try{
				if(date.indexOf("/")>=0){
					delivedate=DateUtil.parse(date, FORMAT_DATETIME);
				}else if(date.indexOf("-")>=0){
					delivedate=DateUtil.parse(date, DateUtil.FORMAT_DATETIME);
				}				
			}catch(Exception e){
				throw new  RuntimeException(alterString(name, row, col, dateformException));
			}

			//发货时间必须晚于开始时间
			if(DateUtil.diffDateTime(delivedate,start)<0){//发货时间小于（不晚于）开始时间报错
				errorVaildate.append(alterString(name, row, col, delivedateException));//预售发货时间格式
				throw new  RuntimeException(alterString(name, row, col, delivedateException));
				
			}
		}
		return delivedate;
	}
	/**
	 * 非空验证
	 * @param date
	 * @param name
	 * @param row
	 * @param col
	 */
	public void notisNullvalidate(String date,String name,int row,int col){
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
	public String trimString(String date){
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
	private String alterString(String name,int row,int col,String message){
		return di+row+rowString+exist+di+(col+1)+guohao1+name+guohao2+message+htmlwraprow;
	}
	private String activityExists(String name,int row,int col,String activityType,String activityNo){
		return alterString(name, row, (col+1), "：本商品已经存在于【 "+activityType+"】活动中,活动管理编号"+activityNo);
	}
	private String activityDateNotnull(String name,int row,int col,String activityType,String activityNo){
		return alterString(name, row, (col+1), "【 "+activityType+"】活动中,开始时间或者结束时间有一个为null,活动管理编号"+activityNo);
	}
	/**
	 * 判断根据条形码查出来商品是否符合
	 * @param commoditysku
	 * @return
	 * @throws Exception 
	 */
	public boolean verficationCommodity(CommoditySku commoditysku,int row,ActivityManager activityManager
			,Date start,Date end) throws Exception{
		int col=0;
		if(activityManager==null){
			//活动管理对象activityManager==null
			errorVaildate.append(activityManagermessage);
			throw new RuntimeException(activityManagermessage);
		}
		long startlong=0;
		long endlong=0;
		if(enum1.equals(ActivityTypeEnum.PANIC_BUY)){//如果是抢购，取自己管理时间
		//商品活动时间比较
			start=activityManager.getStartDate();//自己活动的时间
			end=activityManager.getEndDate();
			startlong=start.getTime();
			endlong=end.getTime();
		}else if(enum1.equals(ActivityTypeEnum.PRE_SALE)){
			startlong=start.getTime();
			endlong=end.getTime();
			if(activityManager.getStartDate()!=null && activityManager.getEndDate()!=null){
				if(startlong<activityManager.getStartDate().getTime()){
					errorVaildate.append(alterString(startDateString, row, startDatecol, startbijiaoException));
					throw new RuntimeException(alterString(startDateString, row, startDatecol, startbijiaoException));
				
				}else if(endlong>activityManager.getEndDate().getTime()){
					errorVaildate.append(alterString(endDateString, row, endDatecol, endbijiaoException));
					throw new RuntimeException(alterString(endDateString, row, endDatecol, endbijiaoException));
				
				}
			}
		}

		
		if(commoditysku==null || commoditysku.getId()==null){
			//sku条形码匹配不到任何商品信息，请检查是否存在或者已删除或已禁用
			errorVaildate.append(alterString(tiaoxingmaString, row, col, commodityskuExists));
			throw new RuntimeException(alterString(tiaoxingmaString, row, col, commodityskuExists));
		}
		else if(commoditysku.getCommodity().getStatus()==null || !commoditysku.getCommodity().getStatus().equals("COMMODITY_STATUS_ADDED")){

			errorVaildate.append(alterString(tiaoxingmaString, row, col, commodityskuStatus));
			throw new RuntimeException(alterString(tiaoxingmaString, row, col, commodityskuStatus));
		}
		//为null或者空字符串为无属性商品
		if(commoditysku.getCommodity().getSaleAttributeIds()==null || commoditysku.getCommodity().getSaleAttributeIds().trim().equals("")){
	
			//根据传递sku条形码是否能查到数据
			List<ActivityCommoditySku> skus=ctivityCommoditySkuService.getSkuList(commoditysku.getId(),commoditysku.getCommodity().getCode());
			
			for (ActivityCommoditySku activityCommoditySku : skus) {
				if(activityCommoditySku.getActivityType()==null || activityCommoditySku.getActivityType().getValue()==null){

					errorVaildate.append( alterString(tiaoxingmaString, row, col, skutypeException));
					throw new RuntimeException( alterString(tiaoxingmaString, row, col, skutypeException));
					
				}

				String type=activityCommoditySku.getActivityType().getName();
				if(activityCommoditySku.getActivityType().getName().equals("新手专享")){
					type=" 新用户专享";
				}
				if(activityCommoditySku.getActivityType().equals(ActivityTypeEnum.PRE_SALE)){
					if(!enum1.equals(ActivityTypeEnum.PRE_SALE)){//预售,判断是否抢购，非抢购就是预售,非预售就抛异常
						errorVaildate.append( activityExists(tiaoxingmaString, row, col, type, activityCommoditySku.getActivityNo()));
						throw new RuntimeException( activityExists(tiaoxingmaString, row, col, type, activityCommoditySku.getActivityNo()));
					}
					
					if(activityCommoditySku.getActivityCommodity().getEndDate()==null || activityCommoditySku.getActivityCommodity().getStartDate()==null){
						//"匹配到的商品已经存在【 "+activityType+"】活动中,活动管理编号"+activityNo
						errorVaildate.append( activityDateNotnull(tiaoxingmaString, row, col, type, activityCommoditySku.getActivityNo()));
						throw new RuntimeException(activityDateNotnull(tiaoxingmaString, row, col, type, activityCommoditySku.getActivityNo()));
					
					}
					//验证同一批次不可以重复添加商品，不同预售活动，同一商品可以重复加
					activityCommodityExistsValidate(activityCommoditySku, startlong, endlong, row,col);
					
				}else if(activityCommoditySku.getActivityType().equals(ActivityTypeEnum.PANIC_BUY)){//抢购
					if(!enum1.equals(ActivityTypeEnum.PANIC_BUY)){//抢购,判断是否抢购，预售就报错
						errorVaildate.append( activityExists(tiaoxingmaString, row, col, type, activityCommoditySku.getActivityNo()));
						throw new RuntimeException( activityExists(tiaoxingmaString, row, col, type, activityCommoditySku.getActivityNo()));
					}
					if(activityCommoditySku.getActivityCommodity().getEndDate()==null || activityCommoditySku.getActivityCommodity().getStartDate()==null){
						//开始结束时间不能为null
						errorVaildate.append( activityDateNotnull(tiaoxingmaString, row, col, type, activityCommoditySku.getActivityNo()));
						throw new RuntimeException(activityDateNotnull(tiaoxingmaString, row, col, type, activityCommoditySku.getActivityNo()));
					}
					activityCommodityExistsValidate(activityCommoditySku, startlong, endlong, row,col);
					
					
				}else{
					//"匹配到的商品已经存在【 "+activityType+"】活动中,活动管理编号"+activityNo
					errorVaildate.append( activityExists(tiaoxingmaString, row, col, type, activityCommoditySku.getActivityNo()));
					throw new RuntimeException(activityExists(tiaoxingmaString, row, col, type, activityCommoditySku.getActivityNo()));
				
				}
			}
		}else{
			//条形码匹配到的商品是个有辅助属性商品
			errorVaildate.append(alterString(tiaoxingmaString, row, col, tiaoxingmashuxin));
			throw new RuntimeException(alterString(tiaoxingmaString, row, col, tiaoxingmashuxin));
		
		}
		return true;
	}

	
	/**
	 * 验证同一批次不可以重复添加商品，不同抢购活动，同一商品可以重复加
	 * @param activityCommoditySku
	 * @param startlong
	 * @param endlong
	 * @param row
	 * @param col
	 * @throws Exception
	 */
	private void activityCommodityExistsValidate(ActivityCommoditySku activityCommoditySku,long startlong,long endlong,int row,int col) throws Exception{

		//抢购
		long startNew=0;
		long endNew=0;
		if(activityCommoditySku.getActivityCommodity().getStartDate()!=null){
			startNew=activityCommoditySku.getActivityCommodity().getStartDate().getTime();
		}else{
			errorVaildate.append(alterString(tiaoxingmaString, row, col, activityCommodtitySdate));
			throw new RuntimeException(alterString(tiaoxingmaString, row, col, activityCommodtitySdate));
		}
		if(activityCommoditySku.getActivityCommodity().getEndDate()!=null){
			endNew=activityCommoditySku.getActivityCommodity().getEndDate().getTime();
		}else{
			errorVaildate.append(alterString(tiaoxingmaString, row, col, activityCommodtityEdate));
			throw new RuntimeException(alterString(tiaoxingmaString, row, col, activityCommodtityEdate));
		}
		
		if(activityCommoditySku.getActivity()!=null && activityCommoditySku.getActivity().getId()!=null
				&& activityManager.getId()!=null &&activityManager.getId().equals(activityCommoditySku.getActivity().getId())){
			//同一个批次不能存在两个不同商品
			errorVaildate.append(alterString(tiaoxingmaString, row, col, activityCommodtityExistsActiviy(activityCommoditySku.getActivityType().getName())));
			throw new RuntimeException(alterString(tiaoxingmaString, row, col, activityCommodtityExistsActiviy(activityCommoditySku.getActivityType().getName())));
			
		}
		else if(startNew>endlong){}//数据库开始时间大于excel结束时间，成立
		else if(endNew<startlong){}//数据库结束时间大于excel开始时间，成立
		else{
			//时间重叠
			errorVaildate.append(alterString(tiaoxingmaString, row, col, overlapDate(activityCommoditySku.getActivityType().getName(), activityCommoditySku.getActivityNo())));
			throw new RuntimeException(alterString(tiaoxingmaString, row, col, overlapDate(activityCommoditySku.getActivityType().getName(), activityCommoditySku.getActivityNo())));
			
		}
	}
	/**
	 * 验证库存
	 * @param skubarcode
	 * @param code
	 * @param limitCount
	 * @param row
	 * @param col
	 * @throws Exception
	 */
	public void  getskuTransformList(String skubarcode,String code,Integer limitCount,int row,int col) throws Exception{
		List<CommoditySkuTransform> skuTransformList= activityCommoditySkuService.getCommoditySku(code);
		if(skuTransformList==null || skuTransformList.size()==0){
			errorVaildate.append(alterString(tiaoxingmaString, row, col, skubarcode+"商品匹配不到任何商品信息"));
			throw new  RuntimeException(alterString(tiaoxingmaString, row, col, skubarcode+",商品匹配不到任何商品信息"));
		}
		CommoditySkuTransform commoditySkuTransform=skuTransformList.get(0);
		if(StringUtil.isEmpty(commoditySkuTransform.getUserCanBuy())){

			errorVaildate.append(alterString(tiaoxingmaString, row, col, skubarcode+"商品找不到库存"));
			throw new  RuntimeException(alterString(tiaoxingmaString, row, col, skubarcode+"商品找不到库存"));
		}
		if(limitCount>Integer.parseInt(commoditySkuTransform.getUserCanBuy())){
			errorVaildate.append(alterString(tiaoxingmaString, row, col, skubarcode+"商品的库存"+commoditySkuTransform.getUserCanBuy()+"小于"+
			"商品限制数量"+limitCount));
			throw new  RuntimeException(alterString(tiaoxingmaString, row, col, skubarcode+"商品的库存"+commoditySkuTransform.getUserCanBuy()+"小于"+
			"商品限制数量"+limitCount));
		}
		
	}

}
