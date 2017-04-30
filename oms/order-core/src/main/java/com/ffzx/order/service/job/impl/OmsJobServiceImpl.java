package com.ffzx.order.service.job.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ffzx.basedata.api.service.DictApiService;
import com.ffzx.commerce.framework.dto.ResultDto;
import com.ffzx.commerce.framework.exception.ServiceException;
import com.ffzx.commerce.framework.utils.DateUtil;
import com.ffzx.commerce.framework.utils.RedisUtil;
import com.ffzx.commerce.framework.utils.StringUtil;
import com.ffzx.commerce.framework.utils.UUIDGenerator;
import com.ffzx.order.api.dto.OmsOrder;
import com.ffzx.order.api.dto.OmsOrderdetail;
import com.ffzx.order.mapper.DailySalesMapper;
import com.ffzx.order.mapper.DailySkuSalesMapper;
import com.ffzx.order.mapper.OmsOrderMapper;
import com.ffzx.order.mapper.OmsOrderdetailMapper;
import com.ffzx.order.mapper.SummarySalesMapper;
import com.ffzx.order.mapper.SummarySkuSalesMapper;
import com.ffzx.order.model.DailySales;
import com.ffzx.order.model.DailySkuSales;
import com.ffzx.order.model.SummarySales;
import com.ffzx.order.model.SummarySkuSales;
import com.ffzx.order.service.OmsOrderService;
import com.ffzx.order.service.job.OmsJobService;

@Service
public class OmsJobServiceImpl implements OmsJobService{
	
	protected final   Logger logger = LoggerFactory.getLogger(OmsJobServiceImpl.class);
    
	@Resource
	private OmsOrderdetailMapper omsOrderDetailMapper;
	
	@Resource
	private DailySkuSalesMapper dailySkuSalesMapper;
	
	@Resource
	private DailySalesMapper dailySalesMapper;
	
	@Resource
	private SummarySalesMapper summarySalesMapper;
	
	@Resource
	private SummarySkuSalesMapper summarySkuSalesMapper;
	@Autowired
	private OmsOrderMapper omsOrderMapper;
	@Autowired
	private DictApiService dictApiService;
	@Autowired
	private RedisUtil redisUtil;
	@Autowired
	private OmsOrderService omsOrderService;
	@Override
	@Transactional(rollbackFor=Exception.class)
	public void salesTimingTask() throws ServiceException {
		logger.info("calc sales task start...");
		Date now=new Date();
		Map<String,Object> params=new HashMap<String,Object>();
		setRealTimeParam(params);
		if(!params.containsKey("start_date")){//第一次初始化数据
			logger.info("init sales data...");
			//数据估计会很多 分批执行
			initCalcInBatchs(new Date());
		}else{
			params.put("end_date", now);
			List<OmsOrderdetail> dlist=this.omsOrderDetailMapper.getDailySalesData(params);
			saveCalResult(dlist,now,0);
		}
		logger.info("calc sales task end...");
	}
	
	/**
	 * 设置 执行定时任务 查询 时间段
	 * @param params
	 */
	public void setRealTimeParam(Map<String,Object> params){
		DailySkuSales last=this.dailySkuSalesMapper.selectLastOne();
		if(null != last){
			params.put("start_date", last.getStatisticsDate());
		}
	}
	
	/**
	 * 取得本次查询的开始结束时间
	 * @param params
	 */
	public void setParamsBeginEnd(Map<String,Object> params){
		DailySkuSales last=this.dailySkuSalesMapper.selectLastOne();
		//查询订单开始结束时间
		Date begin=null;
		Date end=DateUtil.getPrevDay(null, 1);//取得昨天时间
		if(null != last){
			begin=DateUtil.getNextDay(last.getStatisticsDate(), 1);//
			String beginStr=DateUtil.formatDate(begin);
			try {
				params.put("start_date", DateUtil.getMinTimeByStringDate(beginStr));
			} catch (Exception e) {
				logger.error("OmsJobServiceImpl.setParamsBeginEnd() Exception==》", e);
				throw new ServiceException(e);
			}
		}
		try {
			params.put("end_date", DateUtil.getMaxTimeByStringDate(DateUtil.formatDate(end)));
		} catch (Exception e) {
			logger.error("OmsJobServiceImpl.setParamsBeginEnd()=》DateUtil.getMaxTimeByStringDate Exception==》",e);
			throw new ServiceException(e);
		}
	}
	
	/**
	 * 初始化旧数据
	 * @param date
	 */
	public void initCalcInBatchs(Date date){
		Map<String,Object> params=new HashMap<String,Object>();
		date=DateUtil.getPrevDay(date, 1);//往前推一天
		try {
			if(date.compareTo(DateUtil.parseDate("2016-06-01")) > 0){//统计日期 大于 旧系统上线日期
				String calcDate=DateUtil.formatDate(date);
				logger.info("calc sales data by date ==== >"+calcDate);
				params.put("start_date", DateUtil.getMinTimeByStringDate(calcDate));
				params.put("end_date", DateUtil.getMaxTimeByStringDate(calcDate));
				List<OmsOrderdetail> dlist=this.omsOrderDetailMapper.getDailySalesData(params);
				if(null != dlist && dlist.size()>0){
					saveSalesRecords(dlist,date);
				}
				initCalcInBatchs(date);
			}
		} catch (Exception e) {
			logger.error("OmsJobServiceImpl.initCalcInBatchs()=》DateUtil.parseDate Exception==》",e);
		}
	}
	
	/**
	 * 保存统计结果
	 * @param dlist
	 */
	public void saveCalResult(List<OmsOrderdetail> dlist,Date date, int size){
		/**
		 * 将查询结果 筛选为 一天 一个集合
		 * 防止 遗漏一天没跑定时任务,每次跑将之前遗漏的记录补上
		 */
		Map<String,Date> dateRes=getPrevDayMinMax(date);
		List<OmsOrderdetail> resList=getPeroidList(dlist, dateRes);
		if(null != resList && resList.size()>0){
			saveSalesRecords(resList,date);//生成 统计记录
			size+=resList.size();
		}
		date=DateUtil.getPrevDay(date, 1);//往前推一天
		if(size < dlist.size()){
			//递归调用
			saveCalResult(dlist,date,size);
		}
	}
	
	/**
	 * 生成 统计记录数据
	 * @param list
	 * @param date
	 */
	public void saveSalesRecords(List<OmsOrderdetail> list,Date date){
		String calcDate=DateUtil.formatDate(date);
		
		/**
		 * 该天 的 最大和 最小时间
		 */
		Date date_start=null;
		Date date_end=null;
		try {
			date_start = DateUtil.getMinTimeByStringDate(calcDate);
			date_end = DateUtil.getMaxTimeByStringDate(calcDate);
		} catch (Exception e) {
		}
		//以commodityCode作为key
		Map<String,Integer> unique=new HashMap<String,Integer>();
		//以 skucode,commodityCode作为key
		Map<String,Integer> uniqueSku=new HashMap<String,Integer>();
		for(OmsOrderdetail de:list){
			String codeKey=de.getCommodityNo();//商品编码
			if(!unique.containsKey(codeKey)){//新加数量
				unique.put(codeKey, de.getBuyNum());
			}else{//叠加数量
				int oldNum=unique.get(codeKey);
				unique.put(codeKey, oldNum+de.getBuyNum());
			}
			String skuKey=de.getSkuCode()+","+de.getCommodityNo();
			if(!uniqueSku.containsKey(skuKey)){//新加数量
				uniqueSku.put(skuKey, de.getBuyNum());
			}else{//叠加数量
				int oldNum=uniqueSku.get(skuKey);
				uniqueSku.put(skuKey, oldNum+de.getBuyNum());
			}
		}
		
		for(String skuKey:uniqueSku.keySet()){
			String skuCode=skuKey.split(",")[0];
			String commodityCode=skuKey.split(",")[1];
			DailySkuSales one=new DailySkuSales();
			one.setId(UUIDGenerator.getUUID());
			one.setCommodityCode(commodityCode);
			one.setSkuCode(skuCode);
			one.setStatisticsDate(date);
			one.setSaleNum(uniqueSku.get(skuKey));
			one.setStartDate(date_start);
			one.setEndDate(date_end);
			int upCount=this.dailySkuSalesMapper.updateByParam(one);
			if(upCount <= 0){
				/**
				 * 插入sku日统计记录
				 */
				this.dailySkuSalesMapper.insert(one);
			}
			
			/**
			 * 更新sku汇总统计表
			 * 没有即新增一条记录
			 */
			SummarySkuSales sales=new SummarySkuSales();
			sales.setSkuCode(skuCode);
			sales.setSaleNum(uniqueSku.get(skuKey));
			sales.setCommodityCode(commodityCode);
			int resCount=this.summarySkuSalesMapper.dailyUpdateSummary(sales);
			if(resCount<=0){//没有记录新增一条
				sales.setId(UUIDGenerator.getUUID());
				sales.setUpdateDate(new Date());
				this.summarySkuSalesMapper.insert(sales);
			}
		}
		
		for(String codeKey:unique.keySet()){
			DailySales one=new DailySales();
			one.setId(UUIDGenerator.getUUID());
			one.setCommodityCode(codeKey);//商品编码
			one.setSaleNum(unique.get(codeKey));
			one.setStatisticsDate(date);
			one.setStartDate(date_start);
			one.setEndDate(date_end);
			int upCount=this.dailySalesMapper.updateByParam(one);
			if(upCount <= 0){
				/**
				 * 插入commodity日统计记录
				 */
				this.dailySalesMapper.insert(one);
			}
			
			/**
			 * 更新commodity汇总表
			 * 没有就新增一条
			 */
			SummarySales summary=new SummarySales();
			summary.setCommodityCode(codeKey);//商品编码
			summary.setSaleNum(unique.get(codeKey));
			int resCount=this.summarySalesMapper.dailyUpdateSummary(summary);
			if(resCount <= 0){//新增一条记录
				summary.setId(UUIDGenerator.getUUID());
				summary.setUpdateDate(new Date());
				this.summarySalesMapper.insert(summary);
			}
			
		}
	}
	
	/**
	 * 获取每个区间 对应的 订单明细记录集合
	 * @param dlist
	 * @param datePeroid
	 * @return
	 */
	public List<OmsOrderdetail> getPeroidList(List<OmsOrderdetail> dlist,Map<String,Date> datePeroid){
		List<OmsOrderdetail> res=new ArrayList<OmsOrderdetail>();
		for(OmsOrderdetail de:dlist){
			//订单下单时间 在区间内
			if(de.getOrder().getCreateDate().compareTo(datePeroid.get("minDate"))>=0
					&& de.getOrder().getCreateDate().compareTo(datePeroid.get("maxDate")) <= 0){
				res.add(de);
			}
		}
		return res;
	}
	
	public Map<String,Date> getPrevDayMinMax(Date date){
		Map<String,Date> res=new HashMap<String,Date>();
		String dateStr=DateUtil.formatDate(date);
		try {
			res.put("minDate", DateUtil.getMinTimeByStringDate(dateStr));
			res.put("maxDate", DateUtil.getMaxTimeByStringDate(dateStr));
		} catch (Exception e) {
			logger.error("", e);
			throw new ServiceException(e);
		}
		return res;
	}
	

	@Override
	@Transactional(rollbackFor=Exception.class)
	public void autoConfirmOrder() {
		logger.info("自动收货 task start ===>>>");
		//如果当前时间减去配置时间超过了 出库时间，则应该自动确认送达
		//APPSETTING+AUTO_CONFIRMRECEIPT_DATE
		int overDay = findOverDay("APPSETTING", "AUTO_CONFIRMRECEIPT_DATE");
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_MONTH, -overDay);
		List<OmsOrder> orderList = omsOrderMapper.findAutoConfirmOrderList(calendar.getTime());
		if(orderList == null || orderList.size()==0){
			return;
		}
		String orderNos = buildOrderNos(orderList);
		String orderNosIn = buildInSql(orderNos);
		//批量确认送达
		omsOrderMapper.batchAutoConfirmOrder(orderNosIn);
		logger.info("自动收货 task success orderNos:{"+orderNos+"}");
		logger.info("自动收货 task end ===>>>");
	}
	
	/**构建订单编号集合  */
	private String buildOrderNos(List<OmsOrder> orderList){
		StringBuilder builder = new StringBuilder();
		for (OmsOrder omsOrder : orderList) {
			builder.append(omsOrder.getOrderNo()).append(",");
		}
		if(builder.length()>0){
			builder.deleteCharAt(builder.length()-1);
		}
		return builder.toString();
	}
	
	/**构建sql中in的语句*/
	private static String buildInSql(String ids){
		String [] arr = ids.split(",");
		StringBuilder builder = new StringBuilder();
		builder.append("(");
		for (String id : arr) {
			builder.append("'").append(id).append("'").append(",");
		}
		if(builder.length()>0){
			builder.deleteCharAt(builder.length()-1);
		}
		builder.append(")");
		return builder.toString();
	}
	/**取得配置天数*/
	private int findOverDay(String type,String label){
		String redisKey  =type+label;
		String value ="";
		value = (String) redisUtil.get(redisKey);
		if(StringUtil.isNotNull(value)){
			return Integer.parseInt(value);
		}
		try{
			ResultDto dictResultDto = dictApiService.getDictByParams(type, label);
			if(!ResultDto.OK_CODE.equals(dictResultDto.getCode()) || null == dictResultDto.getData()){
				return 15; //默认15天
			}
			value =  (String) dictResultDto.getData();
		}catch(Exception e){
			logger.error(" app获取数据字典  error reason by rpc dictApiService.getDictByParams",e);
			return 15;//即使有异常，默认也是15天
		}
		redisUtil.set(redisKey, value, 600L);
		return Integer.parseInt(value);
	}
	/***
	 * 
	 * @author yinglong.huang
	 * @email yinglong.huang@ffzxnet.com
	 * @date 2016年9月22日 上午11:00:13
	 * @version V1.0
	 * @return 
	 */
	@Override
	public void autoRepush2wms() {
		// TODO Auto-generated method stub
		String hours_str = getDictData("OMSORDERCONFIG", "AUTOREPUSH2WMSBH");
		Date queryDate = null;
		if (null != hours_str && hours_str != "") {
			int hours = Integer.parseInt(hours_str);
			queryDate = getDateAddHours(new Date(), -hours);
		} else {
			queryDate = strToDate("2016-07-15 00:00:00");
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("createDate", queryDate);
		List<OmsOrder> orderList = omsOrderMapper.selectListNotInWms(params);
		for (OmsOrder omsOrder : orderList) {
			try {
				omsOrderService.pushOrder2Wms(omsOrder.getOrderNo());
				}catch(Exception e){
				logger.error("【"+omsOrder.getOrderNo()+"】自动补偿wms推送失败",e);
			}
		}
	}
	/**
	 * *
	 * 
	 * @param str
	 * @return
	 * @author yinglong.huang
	 * @email yinglong.huang@ffzxnet.com
	 * @date 2016年9月22日 下午4:06:02
	 * @version V1.0
	 * @return
	 */
	public  Date strToDate(String str) {
		   SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		   Date date = null;
		   try {
		    date = format.parse(str);
		   } catch (ParseException e) {
		   }
		   return date;
		}
	/**
	 * 
	 * @param date
	 * @param minute
	 * @return
	 * @author yinglong.huang
	 * @email yinglong.huang@ffzxnet.com
	 * @date 2016年9月22日 下午4:08:43
	 * @version V1.0
	 * @return
	 */
	 public  Date getDateAddHours(Date date,int hours){
			Calendar cal=Calendar.getInstance();
			if(null != date){//没有 就取当前时间
				cal.setTime(date);
			}
			cal.add(Calendar.HOUR, hours);
			return cal.getTime();
	    }
	private String getDictData(String type,String label){
		String value = null;
		try{
		ResultDto result = dictApiService.getDictByParams(type, label);
		if(result.getCode().equals(ResultDto.OK_CODE) ){
			value =  (String) result.getData();
		}else{
			logger.info("自动补偿wms时间设置数据字典getDictData(\""+type+"\", \""+label+"\")，获取数据为空");
		}
		}catch(Exception e){
			logger.error("自动补偿wms时间设置数据字典getDictData(\""+type+"\", \""+label+"\")，调用异常",e);
		}
		return value;
	}
}
