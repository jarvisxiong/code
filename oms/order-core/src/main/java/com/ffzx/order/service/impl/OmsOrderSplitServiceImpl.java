package com.ffzx.order.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ffzx.commerce.framework.dao.CrudMapper;
import com.ffzx.commerce.framework.service.impl.BaseCrudServiceImpl;
import com.ffzx.commerce.framework.utils.DateUtil;
import com.ffzx.commerce.framework.utils.JsonMapper;
import com.ffzx.commerce.framework.utils.StringUtil;
import com.ffzx.commerce.framework.utils.UUIDGenerator;
import com.ffzx.order.api.dto.Commodity;
import com.ffzx.order.api.dto.CommoditySku;
import com.ffzx.order.api.dto.OmsOrder;
import com.ffzx.order.api.dto.OmsOrderdetail;
import com.ffzx.order.api.enums.BuyTypeEnum;
import com.ffzx.order.service.CommodityService;
import com.ffzx.order.service.CommoditySkuService;
import com.ffzx.order.service.OmsOrderSplitService;

/**
 * 订单拆单实现
 * @author yinglong.huang
 * @email yinglong.huang@ffzxnet.com
 * @date 2016年9月2日 上午11:39:26
 * @version V1.0
 */
@SuppressWarnings({ "unchecked", "unused" })
@Service("omsOrderSplitService")
public class OmsOrderSplitServiceImpl extends BaseCrudServiceImpl implements OmsOrderSplitService {
	@Autowired
	private CommodityService commodityService;// 商品
	@Autowired
	private CommoditySkuService commoditySkuService;// 商品sku

	@Override
	public CrudMapper init() {
		// TODO Auto-generated method stub
		return null;
	}
	/**
	 * 拆单方法
	 * 
	 * @param order
	 * @param splitOrderList
	 * @throws Exception
	 * @author yinglong.huang
	 * @email yinglong.huang@ffzxnet.com
	 * @date 2016年9月2日 上午11:41:57
	 * @version V1.0
	 * @return 
	 * @throws
	 */
	@Override
	public List<OmsOrder> split(OmsOrder order) throws Exception {
		// TODO Auto-generated method stub
		List<OmsOrder> splitOrderList = new ArrayList<OmsOrder>();//拆分后订单集合
		List<OmsOrderdetail> omsOrderdetailList = order.getDetailList();
		//初始化订单关联的 商品信息 ,sku信息
		this.initOrderDetailCi(omsOrderdetailList);
		logger.info("oms-OmsOrderSplitServiceImpl==>>split==》》【"+order.getOrderNo()+"】买赠分组前omsOrderdetailList:【"+omsOrderdetailList.size()+"】条"+JsonMapper.toJsonString(omsOrderdetailList));
		//买赠分组
		this.groupByGifts(omsOrderdetailList);
		logger.info("oms-OmsOrderSplitServiceImpl==>>split==》》【"+order.getOrderNo()+"】买赠分组后omsOrderdetailList:【"+omsOrderdetailList.size()+"】条"+JsonMapper.toJsonString(omsOrderdetailList));
		//当订单明细大于1条时做拆单处理
		if(omsOrderdetailList.size()>1){
			logger.info("【"+order.getOrderNo()+"】第一步  买赠拆单");
			//第一步  买赠拆单
			this.splitByGifts(order,omsOrderdetailList,splitOrderList);
			logger.info("oms-OmsOrderSplitServiceImpl==>>split==》》【"+order.getOrderNo()+"】【第一步】买赠拆单完成splitOrderList:【"+splitOrderList.size()+"】条"+JsonMapper.toJsonString(splitOrderList)+",待拆明细条数：【"+omsOrderdetailList.size()+"条】:"+JsonMapper.toJsonString(omsOrderdetailList));
			logger.info("【"+order.getOrderNo()+"】第二步  根据商 拆单标示拆单、区域性商品、供应商仓库");
			//第二步  根据商 拆单标示拆单、区域性商品、供应商仓库
			this.splitSplitSingleFlag(order,omsOrderdetailList,splitOrderList);
			logger.info("oms-OmsOrderSplitServiceImpl==>>split==》》【"+order.getOrderNo()+"】【第二步】拆单完成splitOrderList:【"+splitOrderList.size()+"】条"+JsonMapper.toJsonString(splitOrderList)+",待拆明细条数：【"+omsOrderdetailList.size()+"条】:"+JsonMapper.toJsonString(omsOrderdetailList));
			logger.info("【"+order.getOrderNo()+"】第三步  将余下明细按照   区域性商品 ");
			//第三步  将余下明细按照   区域性商品 
			this.splitByNoSplitSingleFlag(order,omsOrderdetailList,splitOrderList);
			logger.info("oms-OmsOrderSplitServiceImpl==>>split==》》【"+order.getOrderNo()+"】【第三步】拆单完成splitOrderList:【"+splitOrderList.size()+"】条"+JsonMapper.toJsonString(splitOrderList)+",待拆明细条数：【"+omsOrderdetailList.size()+"条】:"+JsonMapper.toJsonString(omsOrderdetailList));
			logger.info("oms-OmsOrderSplitServiceImpl==>>split==》》【"+order.getOrderNo()+"】明细大于1条时，拆单分组前splitOrderList:【"+splitOrderList.size()+"】条"+JsonMapper.toJsonString(splitOrderList));
			
		}else{
			order.setDetailList(omsOrderdetailList);
			order.setBuyType(omsOrderdetailList.get(0).getBuyType());
			splitOrderList.add(order);
			logger.info("oms-OmsOrderSplitServiceImpl==>>split==》》【"+order.getOrderNo()+"】明细小于2条时，拆单分组前splitOrderList:【"+splitOrderList.size()+"】条"+JsonMapper.toJsonString(splitOrderList));
		}
		return splitOrderList;
	}

	/**
	 * 买赠明细分组
	 * 
	 * @param omsOrderdetailList
	 * @throws CloneNotSupportedException
	 * @author yinglong.huang
	 * @email yinglong.huang@ffzxnet.com
	 * @date 2016年9月19日 上午10:31:32
	 * @version V1.0
	 * @return
	 */
	private void groupByGifts(List<OmsOrderdetail> omsOrderdetailList) throws CloneNotSupportedException {
		List<Integer> indexRecord = new ArrayList<>(); // 为了记录要移除的ID！！！
		Map<String, Object> giftMap = new HashMap<String, Object>();
		for (int i = 0; i < omsOrderdetailList.size(); i++) {
			OmsOrderdetail omsOrderdetail = omsOrderdetailList.get(i);
			//分组时首先去除虚拟商品，在进行下面逻辑
			if (StringUtil.isNotNull(omsOrderdetail.getVirtualGdId())&&null != omsOrderdetail.getPromotions() && omsOrderdetail.getPromotions().equals("1")) {
				logger.info("oms-OmsOrderSplitServiceImpl==>>groupByGifts==》》【"+omsOrderdetail.getOrderNo()+"】买赠明细:"+omsOrderdetail.getId());	
				if (null != omsOrderdetail.getBuyGifts() && omsOrderdetail.getBuyGifts().equals("2")) {
					String key = omsOrderdetail.getOrderdetailId();
					OmsOrderdetail clone = (OmsOrderdetail) omsOrderdetail.clone();
					logger.info("oms-OmsOrderSplitServiceImpl==>>groupByGifts==》》【"+clone.getOrderNo()+"】买赠明细:"+JsonMapper.toJsonString(clone));					
					if (giftMap.containsKey(key)) {
						List<OmsOrderdetail> giftList = (List<OmsOrderdetail>) giftMap.get(key);
						giftList.add(clone);
						giftMap.put(key, giftList);
					} else {
						List<OmsOrderdetail> giftList = new ArrayList<OmsOrderdetail>();
						giftList.add(clone);
						giftMap.put(key, giftList);
					}
					indexRecord.add(i);
				}
			}
		}
		removeOmsorderdetail(indexRecord, omsOrderdetailList);
		logger.info("oms-OmsOrderSplitServiceImpl==>>groupByGifts==》》买赠分组明细:"+JsonMapper.toJsonString(giftMap));					
		for (OmsOrderdetail omsOrderdetail : omsOrderdetailList) {
			String key = omsOrderdetail.getId();
			if (giftMap.containsKey(key)) {
				List<OmsOrderdetail> giftList = (List<OmsOrderdetail>) giftMap.get(key);
				if (giftList.size() > 0) {
					omsOrderdetail.setGiftList(giftList);
				}
				logger.info("oms-OmsOrderSplitServiceImpl==>>groupByGifts==》》【"+key+"】【"+omsOrderdetail.getCommodityTitle()+"】omsOrderdetail.getGiftList()的买赠明细:"+JsonMapper.toJsonString(omsOrderdetail.getGiftList()));					
				
			}
		}
		
	}
	/**
	 * 按照买赠类型拆单
	 * @param order
	 * @param omsOrderdetailList
	 * @param splitOrderList
	 * @author yinglong.huang
	 * @email yinglong.huang@ffzxnet.com
	 * @date 2016年9月19日 上午11:19:09
	 * @version V1.0
	 * @return
	 * @throws CloneNotSupportedException 
	 */
	private void  splitByGifts(OmsOrder order,List<OmsOrderdetail> omsOrderdetailList,List<OmsOrder> splitOrderList) throws CloneNotSupportedException{
		List<Integer> indexRecord = new ArrayList<>(); //为了记录要移除的ID！！！
		for (int i=0;i<omsOrderdetailList.size();i++) {
			   OmsOrderdetail omsOrderdetail =  omsOrderdetailList.get(i);
			//判断是否是买赠商品
		   if(null!=omsOrderdetail.getPromotions()&&omsOrderdetail.getPromotions().equals("1")){
			   logger.info("OmsOrderSplitServiceImpl==>>splitByGifts==>>根据【买赠】拆单【"+order.getOrderNo()+"】:"+JsonMapper.toJsonString(omsOrderdetail));
			   List<OmsOrderdetail> omsOrderdetailList_gifts = new  ArrayList<OmsOrderdetail>();
			   omsOrderdetailList_gifts.add(omsOrderdetail);//添加到拆单集合中
			   if(null!=omsOrderdetail.getGiftList()&&omsOrderdetail.getGiftList().size()>0){
				   logger.info("OmsOrderSplitServiceImpl==>>splitByGifts==>>根据【买赠】拆单【"+order.getOrderNo()+"】，【"+omsOrderdetail.getSkuCode()+"】"+JsonMapper.toJsonString(omsOrderdetail.getGiftList()));
				   omsOrderdetailList_gifts.addAll(omsOrderdetail.getGiftList());
			   }
			   this.biuldOrderBySpilit(order, omsOrderdetailList_gifts,splitOrderList);
			   indexRecord.add(i);
		   }
		}
		removeOmsorderdetail(indexRecord, omsOrderdetailList);
	}
	/**
	 * 根据拆单标示：一条明细拆成一个单
	 * @param order
	 * @param omsOrderdetailList
	 * @param splitOrderList
	 * @throws CloneNotSupportedException
	 * @author yinglong.huang
	 * @email yinglong.huang@ffzxnet.com
	 * @date 2016年9月2日 上午11:48:57
	 * @version V1.0
	 * @return 
	 * @throws
	 */
	private void splitSplitSingleFlag(OmsOrder order,List<OmsOrderdetail> omsOrderdetailList,List<OmsOrder> splitOrderList) throws CloneNotSupportedException{	
		List<Integer> indexRecord = new ArrayList<>(); //为了记录要移除的ID！！！
		for (int i=0;i<omsOrderdetailList.size();i++) {
			   OmsOrderdetail omsOrderdetail =  omsOrderdetailList.get(i);
			//拆单标示拆单
		   if(StringUtil.isNotNull(omsOrderdetail.getCommodity().getSplitSingle())&&omsOrderdetail.getCommodity().getSplitSingle().equals("0")){
			   logger.info("OmsOrderSplitServiceImpl==>>splitSplitSingleFlag==>>根据拆单标示拆单【"+order.getOrderNo()+"】:"+JsonMapper.toJsonString(omsOrderdetail));
			   List<OmsOrderdetail> omsOrderdetailList_splitSingleFlag = new  ArrayList<>();
			   if(StringUtil.isNotNull(omsOrderdetail.getVirtualGdId())){
				   omsOrderdetail.setCommodityType("1");
			   }
			   omsOrderdetailList_splitSingleFlag.add(omsOrderdetail);//添加到拆单集合中
			   this.biuldOrderBySpilit(order, omsOrderdetailList_splitSingleFlag,splitOrderList);
//			   omsOrderdetailList.remove(i);//将该明细在源原集合中删除
			   indexRecord.add(i);
		   }
		}
		removeOmsorderdetail(indexRecord, omsOrderdetailList);
	}
	
	private void splitByNoSplitSingleFlag(OmsOrder order,List<OmsOrderdetail> omsOrderdetailList,List<OmsOrder> splitOrderList) throws CloneNotSupportedException{
		  logger.info("OmsOrderSplitServiceImpl==>>splitByNoSplitSingleFlag==>>无拆单标示  明细分组【"+order.getOrderNo()+"】:"+JsonMapper.toJsonString(omsOrderdetailList));
		  //仓库分组
		  Map<String,Object> sameWarehouseMap = this.selectOdByWarehouse(omsOrderdetailList);
		  logger.info("OmsOrderSplitServiceImpl==>>splitByNoSplitSingleFlag==>>【"+order.getOrderNo()+"】 根据【相同仓库】分组 :"+JsonMapper.toJsonString(sameWarehouseMap));
		  // ============================预售继续拆分Start==========================
		  if(!sameWarehouseMap.isEmpty()){
		  for (String key : sameWarehouseMap.keySet()){
			  //仓库分组
			  List<OmsOrderdetail> sameWarehouseOmsOrderdetailList =  (List<OmsOrderdetail>) sameWarehouseMap.get(key);
			  logger.info("OmsOrderSplitServiceImpl==>>splitByNoSplitSingleFlag==>>仓库分组 【"+order.getOrderNo()+"】仓库编码【"+key+"】srstOmsOrderdetailList_【"+sameWarehouseOmsOrderdetailList.size()+"】条:"+JsonMapper.toJsonString(sameWarehouseOmsOrderdetailList));
			  //如果集合数量只有一个 则不用继续拆分 构建新订单
			  if(sameWarehouseOmsOrderdetailList.size()==1){
				  this.biuldOrderBySpilit(order,sameWarehouseOmsOrderdetailList ,splitOrderList);
			  }else{
				  //供应商和区域性 再次拆分
				  logger.info("OmsOrderSplitServiceImpl==>>splitByNoSplitSingleFlag==>>仓库分组【"+key+"】分组==>> 同一预售时间，抢购，同一供应商，供应商所在地再次拆分【"+order.getOrderNo()+"】");
				  this.splitByPrePanicVender(order,sameWarehouseOmsOrderdetailList,splitOrderList);
			   } 
			 }
		  }
		  if(omsOrderdetailList.size()>0){
			  this.splitByPrePanicVender(order,omsOrderdetailList,splitOrderList);
		  }
		//=============================其他部分End==========================
		  
	  }
	/**
	 * 除开 拆单标示以外的订单明细拆单
	 * 1，筛选出预售订单，==》》同一发货日期分类，在此基础上，再把同一供应商分类，再区域性与非区域性划分
	 * 2.非预售订单，==》》供应商分类，再区域性与非区域性划分
	 * @param order
	 * @param omsOrderdetailList
	 * @param splitOrderList
	 * @throws CloneNotSupportedException
	 * @author yinglong.huang
	 * @email yinglong.huang@ffzxnet.com
	 * @date 2016年9月2日 上午11:56:52
	 * @version V1.0
	 * @return
	 */
  private void splitByPrePanicVender(OmsOrder order,List<OmsOrderdetail> omsOrderdetailList,List<OmsOrder> splitOrderList) throws CloneNotSupportedException{
	  
	  logger.info("OmsOrderSplitServiceImpl==>>splitByPrePanicVender==>>分组明细【"+order.getOrderNo()+"】:"+JsonMapper.toJsonString(omsOrderdetailList));
	  //预售拆分 
	  Map<String,Object> sameReadySendTimeMap = this.selectOdBySameReadySendTime(omsOrderdetailList);
	  logger.info("OmsOrderSplitServiceImpl==>>splitByPrePanicVender==>>预售拆单  根据【发货日期】分组 【"+order.getOrderNo()+"】sameReadySendTimeMap:"+JsonMapper.toJsonString(sameReadySendTimeMap));
	  // ============================预售继续拆分Start==========================
	  if(!sameReadySendTimeMap.isEmpty()){
	  for (String key : sameReadySendTimeMap.keySet()){
		  //供应商拆分
		  List<OmsOrderdetail> srstOmsOrderdetailList =  (List<OmsOrderdetail>) sameReadySendTimeMap.get(key);
		  logger.info("OmsOrderSplitServiceImpl==>>splitByPrePanicVender==>>预售拆单  【"+order.getOrderNo()+"】发货日期【"+key+"】srstOmsOrderdetailList_【"+srstOmsOrderdetailList.size()+"】条:"+JsonMapper.toJsonString(srstOmsOrderdetailList));
		  //如果集合数量只有一个 则不用继续拆分 构建新订单
		  if(srstOmsOrderdetailList.size()==1){
			  this.biuldOrderBySpilit(order,srstOmsOrderdetailList ,splitOrderList);
		  }else{
			  //再次拆分
			  logger.info("OmsOrderSplitServiceImpl==>>splitByPrePanicVender==>>预售拆单  根据【"+key+"】分组==>>  供应商和区域性 再次拆分【"+order.getOrderNo()+"】");
			  this.warehouse_sameVender_split(order,srstOmsOrderdetailList,splitOrderList);
		   } 
		 }
	  }
	  // ============================预售继续拆分End==========================
	  // ============================抢购商品继续拆分Start==========================
	  List<OmsOrderdetail> panicBuyOmsOrderdetailList = this.selectOdByPanicBuy(omsOrderdetailList);
	  if(null!=panicBuyOmsOrderdetailList&&panicBuyOmsOrderdetailList.size()>0){
		  logger.info("OmsOrderSplitServiceImpl==>>splitByPrePanicVender==>>抢购商品拆单  【"+order.getOrderNo()+"】:panicBuyOmsOrderdetailList【"+panicBuyOmsOrderdetailList.size()+"】条:"+JsonMapper.toJsonString(panicBuyOmsOrderdetailList));
		  this.warehouse_sameVender_split(order,panicBuyOmsOrderdetailList,splitOrderList);
	  }
	  // ============================抢购商品继续拆分End==========================
	  //=============================其他部分Start==========================
	  //再次拆分
	  if(omsOrderdetailList.size()>0){
		  this.warehouse_sameVender_split(order,omsOrderdetailList,splitOrderList);
	  }
	//=============================其他部分End==========================
	  
  }
  
  /**
   * 抽取【预售商品】的订单明细：【相同发货日期】的订单明细分组
   * 
   * @param omsOrderdetailList
   * @return
   * @author yinglong.huang
   * @email yinglong.huang@ffzxnet.com
   * @date 2016年9月2日 下午2:11:30
   * @version V1.0
   * @return
   */
  private  Map<String,Object> selectOdBySameReadySendTime(List<OmsOrderdetail> omsOrderdetailList){
	   List<Integer> indexRecord = new ArrayList<>();
	   Map<String,Object> sameReadySendTimeMap = new HashMap<String,Object>();
	   for (int i=0;i<omsOrderdetailList.size();i++) {
		   OmsOrderdetail orderdetail =  omsOrderdetailList.get(i);
		if(orderdetail.getBuyType().equals(BuyTypeEnum.PRE_SALE)){
			String  readySendTimeStr =  DateUtil.format(orderdetail.getReadySendTime(), DateUtil.FORMAT_MONTH_DATE);
			 List<OmsOrderdetail> sameReadySendTimeList = null;
       	 if(sameReadySendTimeMap.containsKey(readySendTimeStr)){
       		 sameReadySendTimeList = (List<OmsOrderdetail>) sameReadySendTimeMap.get(readySendTimeStr);
       		 sameReadySendTimeList.add(orderdetail);
       		 sameReadySendTimeMap.put(readySendTimeStr, sameReadySendTimeList);
       	 }else{
       		 sameReadySendTimeList = new ArrayList<OmsOrderdetail>();
       		 sameReadySendTimeList.add(orderdetail);
       		 sameReadySendTimeMap.put(readySendTimeStr, sameReadySendTimeList);
       		 
       	 }
//       	 omsOrderdetailList.remove(i);
       	 indexRecord.add(i);
		 }
	   }
	   removeOmsorderdetail(indexRecord, omsOrderdetailList);
	   return sameReadySendTimeMap;
  }
  
  
  /**
   * 抽取相同仓库的订单明细：仓库相同的订单明细分组
   * 
   * @param omsOrderdetailList
   * @return
   * @author yinglong.huang
   * @email yinglong.huang@ffzxnet.com
   * @date 2016年9月2日 下午2:11:30
   * @version V1.0
   * @return
   */
  private  Map<String,Object> selectOdByWarehouse(List<OmsOrderdetail> omsOrderdetailList){
	   List<Integer> indexRecord = new ArrayList<>();
	   Map<String,Object> sameWarehouseMap = new HashMap<String,Object>();
	   for (int i=0;i<omsOrderdetailList.size();i++) {
		   OmsOrderdetail orderdetail =  omsOrderdetailList.get(i);
			String  warehouseCode =  orderdetail.getWarehouseCode();
			List<OmsOrderdetail> sameWarehouseList = null;
       	 if(sameWarehouseMap.containsKey(warehouseCode)){
       		sameWarehouseList = (List<OmsOrderdetail>) sameWarehouseMap.get(warehouseCode);
       		sameWarehouseList.add(orderdetail);
       		sameWarehouseMap.put(warehouseCode, sameWarehouseList);
       	 }else{
       		logger.info("OmsOrderSplitServiceImpl==>>selectOdByWarehouse==>> 根据【仓库】分组==>> 【"+orderdetail.getOrderNo()+"】【"+orderdetail.getWarehouseCode()+"】");
       		sameWarehouseList = new ArrayList<OmsOrderdetail>();
       		sameWarehouseList.add(orderdetail);
       		sameWarehouseMap.put(warehouseCode, sameWarehouseList);
       		 
       	 }
//       	 omsOrderdetailList.remove(i);
       	 indexRecord.add(i);
	   }
	   removeOmsorderdetail(indexRecord, omsOrderdetailList);
	   return sameWarehouseMap;
  }
   /**
    * 拆分出仓库在供应商的订单明细
    * 
    * @param order
    * @param omsOrderdetailList
    * @param splitOrderList
    * @throws CloneNotSupportedException
    * @author yinglong.huang
    * @email yinglong.huang@ffzxnet.com
    * @date 2016年9月2日 下午2:18:26
    * @version V1.0
    * @return
    */
  private void warehouse_sameVender_split(OmsOrder order,List<OmsOrderdetail> omsOrderdetailList,List<OmsOrder> splitOrderList) throws CloneNotSupportedException{
		//========================有供应商商品 Start===================================
	  //商品在供应商仓库Start
	  List<OmsOrderdetail> warehouseOmsOrderdetailList =  selectOdByWarehouseInVender(omsOrderdetailList);
	  logger.info("warehouse_sameVender_split==>>【"+order.getOrderNo()+"】,【商品在供应商仓库】的商品共【"+warehouseOmsOrderdetailList.size()+"】条,【商品[不]在供应商仓库】的商品共【"+omsOrderdetailList.size()+"】条");
	  logger.info("OmsOrderSplitServiceImpl==>>sameVender_warehouse_split==>> 根据【商品在供应商】分组==>>商品在供应商再次拆分【"+order.getOrderNo()+"】【仓库在供应商】分组warehouse_omsOrderdetailList_【"+warehouseOmsOrderdetailList.size()+"】条:"+JsonMapper.toJsonString(warehouseOmsOrderdetailList));
	  if(warehouseOmsOrderdetailList.size()>0){
		  if(warehouseOmsOrderdetailList.size()==1){
		  this.biuldOrderBySpilit(order, warehouseOmsOrderdetailList,splitOrderList);
		  }else{
			  Map<String,Object> sameVenderMap  = this.selectOdBySameVendor(warehouseOmsOrderdetailList);
			  logger.info("OmsOrderSplitServiceImpl==>>sameVender_warehouse_split==>> 根据【供应商】分组==>> 【"+order.getOrderNo()+"】sameVenderMap:"+JsonMapper.toJsonString(sameVenderMap));
			  for (String sameVender_key : sameVenderMap.keySet()) {
				  //同一个供应商 拆分
				  List<OmsOrderdetail> sameVenderOmsOrderdetailList =  (List<OmsOrderdetail>) sameVenderMap.get(sameVender_key);
				  logger.info("OmsOrderSplitServiceImpl==>>sameVender_warehouse_split==>> 根据【供应商】分组==>> 供应商和区域性 再次拆分【"+order.getOrderNo()+"】同一供应商【"+sameVender_key+"】共【"+sameVenderOmsOrderdetailList.size()+"】条，sameVenderMap:"+JsonMapper.toJsonString(sameVenderMap));
				  this.biuldOrderBySpilit(order,sameVenderOmsOrderdetailList,splitOrderList);
				  //去掉区域拆分逻辑
				  /*if(ssameVender_mapItemList.size()==1){
					  this.biuldOrderBySpilit(order,ssameVender_mapItemList,splitOrderList);
				  }else{
					  //======================区域性 非需区域性 拆分==================
					  this.areaSplitBuilOrder(order,ssameVender_mapItemList,splitOrderList);
					  
				  }*/
				}
			  if(warehouseOmsOrderdetailList.size()>0){
				  //去掉区域拆分逻辑
				  this.biuldOrderBySpilit(order,warehouseOmsOrderdetailList,splitOrderList);
				  /*if(warehouse_omsOrderdetailList.size()==1){
					  this.biuldOrderBySpilit(order,warehouse_omsOrderdetailList,splitOrderList);
				  }else{
					  this.areaSplitBuilOrder(order,warehouse_omsOrderdetailList, splitOrderList);
				  }*/
			  }
		  } 
	  }
	//商品在供应商仓库End
	//商品不在供应商仓库Start
	  // List<OmsOrderdetail> no_warehouse_omsOrderdetailList =omsOrderdetailList;
	  logger.info("OmsOrderSplitServiceImpl==>>sameVender_warehouse_split==>> 根据【商品不在供应商】分组==>>商品不在供应商品再次拆分【"+order.getOrderNo()+"】【仓库在wms】分组no_warehouse_omsOrderdetailList_【"+omsOrderdetailList.size()+"】条:"+JsonMapper.toJsonString(omsOrderdetailList));
	  if(omsOrderdetailList.size()>0){
		  //去掉区域拆分逻辑
		  this.biuldOrderBySpilit(order,omsOrderdetailList,splitOrderList);
		  /*if(omsOrderdetailList.size()==1){
		  this.biuldOrderBySpilit(order,omsOrderdetailList,splitOrderList); 
		  }else{
		  this.areaSplitBuilOrder(order,omsOrderdetailList,splitOrderList);
		  }*/
	  }  
	//商品不在供应商仓库End
	  }
  /**
   * 抽取【抢购商品】的订单明细
   * 
   * @param omsOrderdetailList
   * @return
   * @author yinglong.huang
   * @email yinglong.huang@ffzxnet.com
   * @date 2016年9月2日 下午2:20:57
   * @version V1.0
   * @return
   */
  private List<OmsOrderdetail> selectOdByPanicBuy(List<OmsOrderdetail> omsOrderdetailList ){
	   List<Integer> indexRecord = new ArrayList<>();
	   List<OmsOrderdetail> omsOrderdetailList_PanicBuy =  new ArrayList<>();
	   for (int i=0;i<omsOrderdetailList.size();i++) {
		   OmsOrderdetail omsOrderdetail =  omsOrderdetailList.get(i);
		   if(omsOrderdetail.getBuyType().equals(BuyTypeEnum.PANIC_BUY)){
			   logger.info("OmsOrderSplitServiceImpl==>>selectOdByPanicBuy==>> 根据【抢购商品】分组==>>发现区域商品【"+omsOrderdetail.getSkuCode()+"】将其分组,omsOrderdetail:"+JsonMapper.toJsonString(omsOrderdetail));
			   omsOrderdetailList_PanicBuy.add(omsOrderdetail);
//			   omsOrderdetailList.remove(i);
			   indexRecord.add(i);
		   }
	   }
	   removeOmsorderdetail(indexRecord, omsOrderdetailList);
	   return omsOrderdetailList_PanicBuy;
  }
  
  /**
   * 抽取【仓库在供应商】的订单明细
   * 
   * @param omsOrderdetailList
   * @return
   * @author yinglong.huang
   * @email yinglong.huang@ffzxnet.com
   * @date 2016年9月2日 下午2:20:57
   * @version V1.0
   * @return
   */
  private List<OmsOrderdetail> selectOdByWarehouseInVender(List<OmsOrderdetail> omsOrderdetailList ){
	   List<Integer> indexRecord = new ArrayList<>();
	   List<OmsOrderdetail> omsOrderdetailList_warehouse =  new ArrayList<>();
	   for (int i=0;i<omsOrderdetailList.size();i++) {
		   OmsOrderdetail omsOrderdetail =  omsOrderdetailList.get(i);
		   if(omsOrderdetail.getCommodity().getWarehouse().equals("0")){
			   logger.info("OmsOrderSplitServiceImpl==>>getOmsOrderdetailList_warehouse==>> 根据【仓库在供应商】分组==>>仓库在供应商商品【"+omsOrderdetail.getSkuCode()+"】将其分组,omsOrderdetail:"+JsonMapper.toJsonString(omsOrderdetail));
			   omsOrderdetailList_warehouse.add(omsOrderdetail);
			   indexRecord.add(i);
		   }
	   }
	   removeOmsorderdetail(indexRecord, omsOrderdetailList);
	   return omsOrderdetailList_warehouse;
  }
   	/**
   	 * 抽取相同供应商
   	 * 
   	 * @param omsOrderdetailList
   	 * @return
   	 * @author yinglong.huang
   	 * @email yinglong.huang@ffzxnet.com
   	 * @date 2016年9月2日 下午3:47:45
   	 * @version V1.0
   	 * @return
   	 */	 
	private Map<String, Object> selectOdBySameVendor(List<OmsOrderdetail> omsOrderdetailList) {
		List<Integer> indexRecord = new ArrayList<>();
		Map<String, Object> hadVendorMap = new HashMap<String, Object>();
		for (int i = 0; i < omsOrderdetailList.size(); i++) {
			OmsOrderdetail orderdetail = omsOrderdetailList.get(i);
			List<OmsOrderdetail> hadsameVendorList = null;
			if (StringUtil.isNotNull(orderdetail.getCommodity().getVendorId())) {// 有供应商的商品
				String venderId = orderdetail.getCommodity().getVendorId();
				if (hadVendorMap.containsKey(venderId)) {
					hadsameVendorList = (List<OmsOrderdetail>) hadVendorMap.get(venderId);
					hadsameVendorList.add(orderdetail);
				} else {
					hadsameVendorList = new ArrayList<>();
					hadsameVendorList.add(orderdetail);
					hadVendorMap.put(venderId, hadsameVendorList);
				}
				// omsOrderdetailList.remove(i);
				indexRecord.add(i);
			}
		}
		removeOmsorderdetail(indexRecord, omsOrderdetailList);
		return hadVendorMap;
	}
	   //筛选区域性商品
		private  List<OmsOrderdetail> getOmsOrderdetailList_area(List<OmsOrderdetail> omsOrderdetailList ){
			List<Integer> indexRecord = new ArrayList<>();
			List<OmsOrderdetail> omsOrderdetailList_area =  new ArrayList<>();
			for (int i=0;i<omsOrderdetailList.size();i++) {
				   OmsOrderdetail omsOrderdetail =  omsOrderdetailList.get(i);
				//区域性商品
			   if(omsOrderdetail.getCommodity().getAreaCategory().equals("0")){
				   omsOrderdetailList_area.add(omsOrderdetail);//添加到拆单集合中
//				   omsOrderdetailList.remove(i);//将该明细在源原集合中删除
				   indexRecord.add(i);
			   }
			}
			removeOmsorderdetail(indexRecord, omsOrderdetailList);
			return omsOrderdetailList_area;
		}
	/**
	 * 区域商品标示拆分
	 * 
	 * @param order
	 * @param omsOrderdetailList
	 * @param splitOrderList
	 * @throws CloneNotSupportedException
	 * @author yinglong.huang
	 * @email yinglong.huang@ffzxnet.com
	 * @date 2016年9月2日 下午3:50:58
	 * @version V1.0
	 * @return
	 */
	  private void areaSplitBuilOrder(OmsOrder order,List<OmsOrderdetail> omsOrderdetailList,List<OmsOrder> splitOrderList) throws CloneNotSupportedException{
		//区域性商品
		  List<OmsOrderdetail> area_omsOrderdetailList =  getOmsOrderdetailList_area(omsOrderdetailList);
		  logger.info("OmsOrderSplitServiceImpl==>>areaSplitBuilOrder==>> 根据【区域性商品】分组==>>区域性商品再次拆分【"+order.getOrderNo()+"】【区域商品】分组area_omsOrderdetailList_【"+area_omsOrderdetailList.size()+"】条:"+JsonMapper.toJsonString(area_omsOrderdetailList));
		  if(area_omsOrderdetailList.size()>0){
			  this.biuldOrderBySpilit(order, area_omsOrderdetailList,splitOrderList); 
		  }
		  //非区域性商品
		  List<OmsOrderdetail> no_area_omsOrderdetailList =omsOrderdetailList;
		  logger.info("OmsOrderSplitServiceImpl==>>areaSplitBuilOrder==>> 根据【非区域商品】分组==>>区域性商品再次拆分【"+order.getOrderNo()+"】【非区域商品】分组no_area_omsOrderdetailList_【"+no_area_omsOrderdetailList.size()+"】条:"+JsonMapper.toJsonString(no_area_omsOrderdetailList));
		  if(no_area_omsOrderdetailList.size()>0){
			  this.biuldOrderBySpilit(order,no_area_omsOrderdetailList,splitOrderList); 
		  }
	  }
    /**
     * 
     * 初始化订单明细商品信息
     * @param @param omsOrderdetailList
     * @author Administrator
     * @email yinglong.huang@ffzxnet.com
     * @date 2016年9月2日 上午11:36:02
     * @version V1.0
     * @return 
     * @throws
     */
	private void initOrderDetailCi(List<OmsOrderdetail> omsOrderdetailList) {
		for (OmsOrderdetail omsOrderdetail : omsOrderdetailList) {
			Commodity commodity = commodityService.getCommodityByCodeFromRedis(omsOrderdetail.getCommodityNo());
			CommoditySku commoditySku = commoditySkuService.getCommoditySkuByIdFromRedis(omsOrderdetail.getSkuId());
			omsOrderdetail.setCommodity(commodity);
			omsOrderdetail.setCommoditySku(commoditySku);
		}
	}
	/**
	 * 根据原单信息及原单明细拆分
	 * @param order
	 * @param omsOrderdetailList
	 * @param splitOrderList
	 * @throws CloneNotSupportedException
	 * @author yinglong.huang
	 * @email yinglong.huang@ffzxnet.com
	 * @date 2016年9月2日 上午11:51:55
	 * @version V1.0
	 * @return 
	 * @throws
	 */
	@Transactional(rollbackFor=Exception.class)
    private void biuldOrderBySpilit(OmsOrder order,List<OmsOrderdetail> omsOrderdetailList,List<OmsOrder> splitOrderList) throws CloneNotSupportedException{
    	BigDecimal totalPrice = new BigDecimal(0D);//订单总价
		BigDecimal actualPayAmount = new BigDecimal(0D);//实际支付金额
		BigDecimal favouredAmount =  new BigDecimal(0D);
		BigDecimal totalRedPacketAmount = new BigDecimal(0D);//红包总金额
		int buyCount = 0 ;//新订单购买商品数量
		OmsOrder newOrder =  (OmsOrder) order.clone();//克隆订单
		newOrder.setId(UUID.randomUUID().toString());
		newOrder.setOrderNo( order.getOrderNo()+"_"+(splitOrderList.size()+1) );//新订单号
		newOrder.setId(UUIDGenerator.getUUID());
		newOrder.setPayOrderNo(order.getOrderNo());//支付订单号保证一样
		newOrder.setStatus("1");//变更为待收货状态
		//订单初始化仓库信息Start
		String  warehouseCode = omsOrderdetailList.get(0).getWarehouseCode();
		String  warehouseId = omsOrderdetailList.get(0).getWarehouseId();
		String  warehouseName = omsOrderdetailList.get(0).getWarehouseName();
		//默认获取第一个订单明细的的供应商仓库所在标示
		String isSupplier = omsOrderdetailList.get(0).getCommodity().getWarehouse();
		newOrder.setIsSupplier(isSupplier);
		newOrder.setBuyType(omsOrderdetailList.get(0).getBuyType());
		newOrder.setStorageId(warehouseId);
		newOrder.setWarehouseCode(warehouseCode);
		newOrder.setStorageName(warehouseName);
		//订单初始化仓库信息End
		for(OmsOrderdetail orderDetail : omsOrderdetailList){	
			if(StringUtil.isNotNull(orderDetail.getFavouredAmount())){
			favouredAmount = favouredAmount.add(orderDetail.getFavouredAmount());
			}
			if(StringUtil.isNotNull(orderDetail.getRedPacketAmount())){
			totalRedPacketAmount = totalRedPacketAmount.add(orderDetail.getRedPacketAmount());
			}
			int buyNum = orderDetail.getBuyNum();
			buyCount += buyNum;
			orderDetail.setOrderNo(newOrder.getOrderNo());
			orderDetail.setOrderId(newOrder.getId());
			totalPrice = totalPrice.add( orderDetail.getActSalePrice().multiply(new BigDecimal(buyNum)));
			actualPayAmount = actualPayAmount.add( orderDetail.getActPayAmount());
//			omsOrderdetailMapper.updateDetBySplit(orderDetail);//更新数据
		}
		newOrder.setBuyCount(buyCount);
		//支付价格
		newOrder.setActualPrice(actualPayAmount);
		newOrder.setTotalPrice(totalPrice);
		newOrder.setFavorablePrice(favouredAmount);
		newOrder.setCouponAmount(favouredAmount);
		newOrder.setTotalRedAmount(totalRedPacketAmount);
		newOrder.setDetailList(omsOrderdetailList);//设置订单明细数据，用来做拆分完毕之后明细数据的update操作
//		this.copyOrderOperateRecord(order.getOrderNo(), newOrder);
//		this.add(newOrder);
		splitOrderList.add(newOrder);
		logger.info("OmsOrderSplitServiceImpl==>>biuldOrderBySpilit==>>新订单拆分结果【"+newOrder.getOrderNo()+"】:"+JsonMapper.toJsonString(newOrder));
    }
	/**
	 * 删除已拆分订单明细
	 * @param indexRecord
	 * @param detailList
	 * @author yinglong.huang
	 * @email yinglong.huang@ffzxnet.com
	 * @date 2016年9月2日 上午11:55:03
	 * @version V1.0
	 * @return
	 */
	private void removeOmsorderdetail(List<Integer> indexRecord , List<OmsOrderdetail> detailList){
		if(indexRecord.size()==0){
			return;
		}
		for(int i = indexRecord.size()-1 ;i>=0 ; i--){
			detailList.remove(indexRecord.get(i).intValue());
		}
	}
}
