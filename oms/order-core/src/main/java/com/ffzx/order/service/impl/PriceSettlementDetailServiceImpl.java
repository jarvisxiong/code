package com.ffzx.order.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ffzx.commerce.framework.dao.CrudMapper;
import com.ffzx.commerce.framework.service.impl.BaseCrudServiceImpl;
import com.ffzx.commerce.framework.utils.DateUtil;
import com.ffzx.commerce.framework.utils.UUIDGenerator;
import com.ffzx.order.api.dto.OmsOrder;
import com.ffzx.order.api.dto.OmsOrderdetail;
import com.ffzx.order.mapper.PriceSettlementDetailMapper;
import com.ffzx.order.model.PriceSettlement;
import com.ffzx.order.model.PriceSettlementDetail;
import com.ffzx.order.service.OmsOrderService;
import com.ffzx.order.service.OmsOrderdetailService;
import com.ffzx.order.service.PriceSettlementDetailService;
import com.ffzx.order.service.PriceSettlementService;

/**
 * @className PriceSettlementDetailServiceImpl
 *
 * @author liujunjun
 * @date 2017-03-23 16:37:40
 * @version 1.0.0
 */
@Service("priceSettlementDetailService")
public class PriceSettlementDetailServiceImpl extends BaseCrudServiceImpl implements PriceSettlementDetailService {

	@Autowired
	private  OmsOrderService omsOrderService;
	@Autowired
	private OmsOrderdetailService omsOrderdetailService;
	@Autowired
	private PriceSettlementService priceSettlementService;
	@Resource
	private PriceSettlementDetailMapper priceSettlementDetailMapper;

	
	@Override
	public CrudMapper init() {
		return priceSettlementDetailMapper;
	}

	/***
	 * 
	 * @author yinglong.huang
	 * @email yinglong.huang@ffzxnet.com
	 * @date 2017年3月24日 上午9:45:06
	 * @version V1.0
	 * @return 
	 */
	@Override
	@Transactional(rollbackFor=Exception.class)
	public void autoPush2PriceSettlementDetail(Date date,String serialCode) {
		Date now = date;
		Map<String,Object> orderParams = new HashMap<String,Object>();
		//只要有售后的单都不在统计范围之内
		orderParams.put("orderType", "COMMON_ORDER");
		//15天前的数据
		String payDay = DateUtil.formatDate(DateUtil.getPrevDay(now,15));
		//支付日期
		orderParams.put("payDay",payDay);
		//未读取过的数据
		orderParams.put("settlementFlag2No", "1");
		orderParams.put("status", "4");
		orderParams.put("detailSettlementFlag2No", "1");
		orderParams.put("notPartnerOrder", "YES");
		orderParams.put("delFlag", "0");
		List<OmsOrder> omsOrderList = omsOrderService.findByBiz(orderParams);
		List<String> orderNoList =null;
		List<String> orderIdList = null;
		if(null!=omsOrderList&&omsOrderList.size()>0){
			Map<String,Object> orderMap = groupOrderByNoKey(omsOrderList);
			orderNoList = getOrderNoList(omsOrderList);
			this.pushOrderData2PriceSettlementDetail(orderNoList,orderMap,now,serialCode);
			//设置订单 及订单明细统计标示
			Map<String,Object> upOrderParms = new HashMap<String,Object>();
			orderIdList = getOrderIdList(omsOrderList);
			upOrderParms.put("settlementFlag", "1");
			upOrderParms.put("idList", orderIdList);
			omsOrderService.updateSettlement(upOrderParms);
		}
	}	
	@Transactional(rollbackFor=Exception.class)
	private void pushOrderData2PriceSettlementDetail(List<String> orderNoList,Map<String,Object> orderMap,Date now,String serialCode){
		Map<String,Object> orderDetailParams = new HashMap<String,Object>();
		orderDetailParams.put("orderNoList", orderNoList);
		//只查询无售后订单明细
		orderDetailParams.put("orderDetailStatus", "0");
		orderDetailParams.put("detailSettlementFlag2No", "1");
		List<OmsOrderdetail>omsOrderdetailList = omsOrderdetailService.findByBiz(orderDetailParams);
		List<PriceSettlementDetail> priceSettlementDetailList = new ArrayList<>();
		List<String> orderDetialIdList = new ArrayList<>();
		for (OmsOrderdetail omsOrderdetail : omsOrderdetailList) {
			OmsOrder omsOrder = (OmsOrder) orderMap.get(omsOrderdetail.getOrderNo());
			PriceSettlementDetail priceSettlementDetail=initPriceSettlementDetail(omsOrder,omsOrderdetail,now,serialCode);
			priceSettlementDetailList.add(priceSettlementDetail);
			orderDetialIdList.add(omsOrderdetail.getId());
		}
		for (PriceSettlementDetail priceSettlementDetail : priceSettlementDetailList) {
			priceSettlementDetailMapper.insertSelective(priceSettlementDetail);
		}
		Map<String,Object> upOrderParms = new HashMap<String,Object>();
		upOrderParms.put("settlementFlag", "1");
		upOrderParms.put("idList", orderDetialIdList);
		omsOrderdetailService.updateSettlement(upOrderParms);
	}
	
	private PriceSettlementDetail initPriceSettlementDetail(OmsOrder omsOrder,OmsOrderdetail omsOrderdetail,Date now,String serialCode){
		PriceSettlementDetail data = new PriceSettlementDetail();
		data.setId(UUIDGenerator.getUUID());
		data.setOrderNo(omsOrderdetail.getOrderNo());
		data.setPartnerId(omsOrder.getPartnerId());
		data.setPartnerName(omsOrder.getSendPersonName());
		data.setPartnerCode(omsOrder.getPartnerCode());
		data.setPartnerPhone(omsOrder.getSendPersonPhone());
		data.setMemberAccount(omsOrder.getMemberAccount());
		data.setMemberId(omsOrder.getMemberId());
		data.setMemberName(omsOrder.getMemberName()==null?null:omsOrder.getMemberName());
		data.setSerialCode(serialCode);
		data.setCreateDate(now);
		data.setLastUpdateDate(now);
		data.setOrderTime(omsOrder.getCreateDate());
		data.setServiceStationId(omsOrder.getServiceStationId());
		data.setServiceStationCode(omsOrder.getServiceStationCode());
		data.setServiceStationName(omsOrder.getServiceStationName());
		data.setActPayAmount(omsOrderdetail.getActPayAmount());
		data.setActSalePrice(omsOrderdetail.getActSalePrice());
		BigDecimal balance = (omsOrderdetail.getActSalePrice().subtract(omsOrderdetail.getPifaPrice())).multiply(new BigDecimal(omsOrderdetail.getBuyNum()));
		data.setBalance(balance);
		data.setCommodityImage(omsOrderdetail.getCommodityImage());
		data.setCommodityTitle(omsOrderdetail.getCommodityTitle());
		data.setCommodityUnit(omsOrderdetail.getCommodityUnit());
		data.setBuyNum(omsOrderdetail.getBuyNum().intValue());
		BigDecimal saleAmount = omsOrderdetail.getActSalePrice().multiply(new BigDecimal(omsOrderdetail.getBuyNum()));
		data.setSaleAmount(saleAmount);
		data.setDelFlag("0");

		data.setSkuCode(omsOrderdetail.getSkuCode());
		data.setSkuId(omsOrderdetail.getSkuId());
		data.setPifaPrice(omsOrderdetail.getPifaPrice());
		data.setVirtualGdId(omsOrderdetail.getVirtualGdId()==null?null:omsOrderdetail.getVirtualGdId());
		data.setOmsOrderDetailId(omsOrderdetail.getId());
		return data;
	}
	private List<String> getOrderNoList(List<OmsOrder>omsOrderList){
		List<String> orderNoList = new ArrayList<>();
		for (OmsOrder omsOrder : omsOrderList) {
			orderNoList.add(omsOrder.getOrderNo());
		}
		return orderNoList;
	}
	private List<String> getOrderIdList(List<OmsOrder>omsOrderList){
		List<String> orderIdList = new ArrayList<>();
		for (OmsOrder omsOrder : omsOrderList) {
			orderIdList.add(omsOrder.getId());
		}
		return orderIdList;
	}
	private  Map<String,Object> groupOrderByNoKey(List<OmsOrder>omsOrderList){
		Map<String,Object> orderMap = new HashMap<String,Object>();
		for (OmsOrder omsOrder : omsOrderList) {
			if(!orderMap.containsKey(omsOrder.getOrderNo())){
				orderMap.put(omsOrder.getOrderNo(), omsOrder);
			}
		}
		return orderMap;
	}

	/***
	 * 
	 * @param now
	 * @author yinglong.huang
	 * @email yinglong.huang@ffzxnet.com
	 * @date 2017年3月24日 下午2:08:27
	 * @version V1.0
	 * @return 
	 */
	@Override
	@Transactional(rollbackFor=Exception.class)
	public void autoPush2PriceSettlement(Date date,String serialCode) {

		Map<String,Object> params = new HashMap<String,Object>();
		params.put("serialCode",serialCode);
		List<PriceSettlement> priceSettlementList= priceSettlementDetailMapper.selectByCountDetail(params);
		for (PriceSettlement priceSettlement : priceSettlementList) {
			priceSettlement.setId(UUIDGenerator.getUUID());
			priceSettlement.setCreateDate(date);
			priceSettlement.setLastUpdateDate(date);
			//待审核
			priceSettlement.setStatus("0");
			priceSettlement.setPsNo(this.getPsNo());
			priceSettlementService.add(priceSettlement);
			params.put("partnerId", priceSettlement.getPartnerId());
			params.put("psNo", priceSettlement.getPsNo());
			priceSettlementDetailMapper.updatepsNo(params);
		}
	}
	private String getPsNo(){
		String psNo = "JW"+System.currentTimeMillis()+new Random().nextInt(100);
		return psNo;
	}

	/***
	 * 
	 * @param id
	 * @author yinglong.huang
	 * @email yinglong.huang@ffzxnet.com
	 * @date 2017年3月26日 下午5:10:21
	 * @version V1.0
	 * @return 
	 */
	@Override
	public void deleteDetail(String id) {
		PriceSettlementDetail priceSettlementDetail =  this.findById(id);
		deleteDetailById(priceSettlementDetail.getId());
		reCountSettlement(priceSettlementDetail.getPsNo());
	}
	@Transactional(rollbackFor=Exception.class)
	public void deleteDetailById(String id) {
		PriceSettlementDetail priceSettlementDetail =  this.findById(id);
		//设置订单 及订单明细统计标示为未统计
		//设置明细Start
		Map<String,Object> upOrderParms = new HashMap<String,Object>();
		List<String> orderDetialIdList = new ArrayList<>();
		orderDetialIdList.add(priceSettlementDetail.getOmsOrderDetailId());
		upOrderParms.put("settlementFlag", "0");
		upOrderParms.put("idList", orderDetialIdList);
		omsOrderdetailService.updateSettlement(upOrderParms);
		//设置明细End
		//设置订单Start
		OmsOrder updateOrder = new OmsOrder();
		updateOrder.setOrderNo(priceSettlementDetail.getOrderNo());
		updateOrder.setSettlementFlag("0");
		omsOrderService.modifyByOrderNo(updateOrder);
		priceSettlementDetailMapper.deleteByPrimaryKey(id);
		//设置订单End
	}
	@Transactional(rollbackFor=Exception.class)
	private void reCountSettlement(String psNo){
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("psNo", psNo);
		List<PriceSettlement>  dataList =  priceSettlementService.findByBiz(params);
		//重新统计
		PriceSettlement updatePriceSettlement = priceSettlementDetailMapper.selectByCountByPsNo(params);
		updatePriceSettlement.setId(dataList.get(0).getId());
		//更新结算单
		priceSettlementService.modifyById(updatePriceSettlement);
	}

}