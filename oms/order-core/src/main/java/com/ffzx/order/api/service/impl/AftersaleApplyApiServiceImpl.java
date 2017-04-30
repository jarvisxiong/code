package com.ffzx.order.api.service.impl;



import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ffzx.commerce.framework.constant.Constant;
import com.ffzx.commerce.framework.dto.ResultDto;
import com.ffzx.commerce.framework.exception.ServiceException;
import com.ffzx.commerce.framework.page.Page;
import com.ffzx.commerce.framework.utils.DateUtil;
import com.ffzx.commerce.framework.utils.StringUtil;
import com.ffzx.order.api.dto.AftersaleApply;
import com.ffzx.order.api.dto.AftersaleApplyItem;
import com.ffzx.order.api.dto.AftersalePickup;
import com.ffzx.order.api.dto.AftersaleRefund;
import com.ffzx.order.api.dto.OmsOrder;
import com.ffzx.order.api.dto.OmsOrderdetail;
import com.ffzx.order.api.enums.AfterSaleChangeReasons;
import com.ffzx.order.api.enums.AfterSaleReMoneyReasons;
import com.ffzx.order.api.enums.AfterSaleRefundReasons;
import com.ffzx.order.api.enums.AfterSaleStatusType;
import com.ffzx.order.api.service.AftersaleApplyApiService;
import com.ffzx.order.constant.OrderDetailStatusConstant;
import com.ffzx.order.service.AftersaleApplyItemService;
import com.ffzx.order.service.AftersaleApplyService;
import com.ffzx.order.service.AftersalePickupService;
import com.ffzx.order.service.AftersaleRefundService;
import com.ffzx.order.service.OmsOrderService;
import com.ffzx.order.service.OmsOrderdetailService;
@Service("aftersaleApplyApiService")
public class AftersaleApplyApiServiceImpl implements AftersaleApplyApiService {
	// 记录日志
	private final static Logger logger = LoggerFactory.getLogger(AftersaleApplyApiServiceImpl.class);
	@Autowired
	private AftersaleApplyService aftersaleApplyService;
	@Autowired
	private AftersaleApplyItemService aftersaleApplyItemService;
	@Autowired
	private AftersalePickupService aftersalePickupService;
	@Autowired
	private AftersaleRefundService aftersaleRefundService;
	@Autowired
	private OmsOrderdetailService omsOrderdetailService;
	@Autowired
	private OmsOrderService omsOrderService;
	/**
	 * 
	 * 雷-----2016年9月20日
	 * (非 Javadoc)
	 * <p>Title: saveAftersaleApply</p>
	 * <p>Description: 申请售后</p>
	 * @param params
	 * @return
	 * @throws ServiceException
	 * @see com.ffzx.order.api.service.AftersaleApplyApiService#saveAftersaleApply(java.util.Map)
	 */
	@Override
	@Transactional(rollbackFor=Exception.class)
	public ResultDto saveAftersaleApply(Map<String, Object> params) throws ServiceException {
		ResultDto  rsDto= null;
		try {		
			List<AftersaleApplyItem> listApplyItem=null;
			if(params.get("listApplyItem")!=null){
				listApplyItem=(List<AftersaleApplyItem>) params.get("listApplyItem");
			}			
			List<String> listdetailId=null;
			if(params.get("listdetailId")!=null){
				listdetailId=(List<String>) params.get("listdetailId");
			}			
			String orderDetailStatus=null;
			if(params.get("orderDetailStatus")!=null){
				orderDetailStatus=params.get("orderDetailStatus").toString();
			}
			String serviceType=null;
			if(params.get("serviceType")!=null){
				serviceType=params.get("serviceType").toString();
			}
			String orderId=null;
			if(params.get("orderId")!=null){
				orderId=params.get("orderId").toString();
			}
			String orderStatus=null;
			if(params.get("orderStatus")!=null){
				orderStatus=params.get("orderStatus").toString();
			}
			AftersaleApply apply=null;
			if(params.get("aftersaleApply")!=null){
				apply=(AftersaleApply) params.get("aftersaleApply");
			}
			//插入售后详情
			for(AftersaleApplyItem i:listApplyItem){
				this.aftersaleApplyItemService.add(i);
			}	
			//修改订单详情
			for (String detailId : listdetailId) {
				OmsOrderdetail orderdetail = new OmsOrderdetail();
				orderdetail.setId(detailId);
				orderdetail.setOrderDetailStatus(orderDetailStatus);
				this.omsOrderdetailService.modifyById(orderdetail);
			}
			//修改订单状态
			//退款（未收到货）
			if(serviceType.equals(OrderDetailStatusConstant.REFUND_TOTAL)){
				OmsOrder order = new OmsOrder();
				order.setId(orderId);
				order.setStatus(orderStatus);
				this.omsOrderService.modifyById(order);
			}
			logger.info("加上线bug测试日志雷apply.getCreateDate()--------------"+apply.getCreateDate());
			//新增售后申请单
			int result=this.aftersaleApplyService.add(apply);
			if(result>0){
				rsDto = new ResultDto(ResultDto.OK_CODE,Constant.SUCCESS);
			}else{
				rsDto = new ResultDto(ResultDto.ERROR_CODE, "faild:" + "新增售后申请单失败！");
			}
		} catch (Exception e) {
			logger.error("dubbo调用-saveAftersaleApply", e);
			throw new ServiceException(e);
		}
		return rsDto;
	}

	@Override
	public ResultDto findAftersaleApply(AftersaleApply apply)  throws ServiceException{
		ResultDto  rsDto= null;
		try {
			Map<String, Object> params=new HashMap<String, Object>();
			params.put("applyPersonId", apply.getApplyPersonId());
			params.put("isApprove", OrderDetailStatusConstant.YES);//驳回的售后单
			Page pageObj = new Page();
			pageObj.setPageSize(apply.getPageSize());
			pageObj.setPageIndex(apply.getPage());
			List<AftersaleApply> applyList=this.aftersaleApplyService.findPageByParams(pageObj, "create_date", "desc", params);
			if(StringUtil.isNotNull(applyList)){
				params=new HashMap<String, Object>();
				params.put("page", pageObj);
				params.put("applyList", applyList);
				rsDto = new ResultDto(ResultDto.OK_CODE, "success");
				rsDto.setData(params);
			}
		} catch (Exception e) {
			logger.error("AftersaleApplyApiServiceImpl-findAftersaleApply-Exception=》dubbo调用-findAftersaleApply", e);
			//rsDto = new ResultDto(ResultDto.ERROR_CODE, "faild:" + e);
			throw new ServiceException(e);
		}	
		return rsDto;
	}

	@Override
	public ResultDto findAftersaleApplyDetail(AftersaleApply apply) throws ServiceException {
		ResultDto  rsDto= null;
		try {
			if(StringUtil.isNotNull(apply)){
				String skuId=apply.getSkuId();
				if(StringUtil.isNotNull(apply.getId())){
					apply=this.aftersaleApplyService.findAftersaleApplyById(apply.getId());
				}
				rsDto = new ResultDto(ResultDto.OK_CODE, "success");
				rsDto.setData(apply);
				double refundAmount=0.0;
				int count=0;
				Map<String, Object> applyMap=new HashMap<String, Object>();
				applyMap.put("serviceType", apply.getApplyType());
				applyMap.put("serviceTypeContent", getExamType(apply.getApplyType()));
				applyMap.put("orderAmount", apply.getOrder().getTotalPrice());
				applyMap.put("reason", apply.getReasonSelect());
				applyMap.put("reasonContent", getReasonName(apply.getApplyType(), apply.getReasonSelect()));
				applyMap.put("desc", apply.getReasonExplain());
				applyMap.put("orderNo", apply.getOrderNo());
				applyMap.put("applyDate", DateUtil.formatDateTime(apply.getCreateDate()));	
				// 根据IOS要求要加上orderid,雷---2016/08/05
				applyMap.put("newOrderId", apply.getOrderId());	
				applyMap.put("orderAmount","");//app开发说这个字段没有用到
				//换货 时
				if(apply.getApplyType().equals(AfterSaleStatusType.CHANGE_GOODS.getValue())){
					// apply.getOrderNo()查出的是原交易订单，查询原交易订单的支付金额,雷---2016/08/05
					//重置为换货的订单
					applyMap.put("newOrderNo", apply.getExchangeOrderNo());
				}else{
					applyMap.put("newOrderNo", "");
				}
				//仓库审核状态获取
				String pickuNo=apply.getPickupNo();
				if(StringUtil.isNotNull(pickuNo)){
					//AftersalePickup pickup=this.aftersalePickupService.getAftersalPickupByNo(pickuNo);
					List<AftersalePickup> pickups=this.aftersalePickupService.getAftersalPickupByPickNo(pickuNo);
					AftersalePickup pickup=null;
					if(pickups!=null && pickups.size()>0)
						pickup=pickups.get(0);
					if(StringUtil.isNotNull(pickup)){
						String pickupStatus=pickup.getPickupStatus();//取货单状态
						if(pickupStatus.equals(OrderDetailStatusConstant.YES)){
							if(apply.getApplyType().equals("2")){
								applyMap.put("checkStatusTitle","总仓已经审核通过，我们将尽快为您发货，请耐心等候。");
							}else{
								applyMap.put("checkStatusTitle","总仓已经审核通过，我们将尽快为您退款，请耐心等候。");
							}
						}else{
							applyMap.put("checkStatusTitle","您的申请正在处理中，请耐心等候");
						}
					}
				}else{
					applyMap.put("checkStatusTitle","您的申请正在处理中，请耐心等候");
				}
				//退款时间获取
				String refundNo=apply.getRefundNo();
				if(StringUtil.isNotNull(refundNo)){
					AftersaleRefund refund=this.aftersaleRefundService.findRefundInfo(null, refundNo);
					if(StringUtil.isNotNull(refund)){
						String refundStatus=refund.getRefundStatus();//退款单状态
						if(refundStatus.equals(OrderDetailStatusConstant.REFUND_PAY)){
							applyMap.put("refundDate",DateUtil.formatDateTime(refund.getPayDate()));
						}
					}
				}else{
					applyMap.put("refundDate","");
				}
				AftersaleApplyItem item=new AftersaleApplyItem();
				//退款（没收到货）
				item.setApplyId(apply.getId());
				if(!apply.getApplyType().equals(OrderDetailStatusConstant.REFUND_TOTAL)){
					item.setSkuId(skuId);					
				}
				DecimalFormat df = new DecimalFormat("0.00");//格式化小数
				List<AftersaleApplyItem> aftersaleApplyItemList=(List<AftersaleApplyItem>) this.aftersaleApplyItemService.findAftersaleApplyItemList(null, null,null, item);
				if(StringUtil.isNotNull(aftersaleApplyItemList)){									
					List<Map<String, Object>> goodslist=new ArrayList<Map<String, Object>>();
					for(AftersaleApplyItem ai:aftersaleApplyItemList){			
						Map<String, Object> goodsMap=new HashMap<String, Object>();
						//售后申请状态
						if(apply.getApplyType().equals("2")&&ai.getAftersaleStatus().equals(OrderDetailStatusConstant.STATUS_CHANGESUC)){
							applyMap.put("checkStatusTitle","感谢您在大麦场购物，欢迎您再次光临！");
						}
						applyMap.put("checkStatus", ai.getAftersaleStatus());	
						int buyNum=ai.getCommodityBuyNum();//购买数量
						int retNum=ai.getReturnNum();//售后数量
						//如果购买数量和售后数量相同说明是待收货按订单退
						if(buyNum==retNum){
							refundAmount+=ai.getActPayAmount().doubleValue();
							count+=ai.getCommodityBuyNum();
						}else{
							//否则是售后数量乘以单个商品的实际支付金额							
							refundAmount+= (ai.getActPayAmount().doubleValue()/buyNum)*retNum;	
							count+=ai.getReturnNum();
						}
						goodsMap.put("title1", ai.getCommodityName());
						goodsMap.put("goodsAttr", ai.getCommodityAttributeValues());
						goodsMap.put("imgPath", ai.getCommodityPic()==null?"":System.getProperty("image.web.server") +ai.getCommodityPic());
						goodsMap.put("count", ai.getReturnNum());
						goodsMap.put("price", ai.getCommodityPrice());
						goodslist.add(goodsMap);
					}
					applyMap.put("refundAmount",df.format(refundAmount) );
					applyMap.put("goodsCount", count);
					applyMap.put("goodslist", goodslist);
				}
				rsDto = new ResultDto(ResultDto.OK_CODE, "success");
				rsDto.setData(applyMap);
			}
		} catch (Exception e) {
			logger.error("AftersaleApplyApiServiceImpl-findAftersaleApplyDetail-Exception=》dubbo调用-findAftersaleApplyDetail", e);
			//rsDto = new ResultDto(ResultDto.ERROR_CODE, "faild:" + e);
			throw new ServiceException(e);
		}
		return rsDto;
	}
	 public  String getExamType(String value) {
		for (AfterSaleStatusType examType : AfterSaleStatusType.values()) {
		          if (value.equals(examType.getValue())) {
		                  return examType.getName();
		          }
		    }
		return null;
	 }
	 
	 public String getReasonName(String type,String value){
		 String reasonName="商品质量问题";		 
		 if(StringUtils.isNotEmpty(type)&&StringUtils.isNotEmpty(value)){
			 switch (type) {
				case "0":		
					 for(AfterSaleRefundReasons rea:AfterSaleRefundReasons.values()){
						 if(value.equals(rea.getValue())){
							 reasonName= rea.getName();
						 }
					 }
					break;
				case "1":		
					 for(AfterSaleReMoneyReasons rea:AfterSaleReMoneyReasons.values()){
						 if(value.equals(rea.getValue())){
							 reasonName= rea.getName();
						 }
					 }
					break;
				case "2":		
					 for(AfterSaleChangeReasons rea:AfterSaleChangeReasons.values()){
						 if(value.equals(rea.getValue())){
							 reasonName= rea.getName();
						 }
					 }
					break;

				default:
					break;
				}		
		 }		
			return reasonName;
	 }

	@Override
	public ResultDto getAftersaleApply(String orderNo) throws ServiceException {
		ResultDto  rsDto= null;
		try {
			rsDto = new ResultDto(ResultDto.OK_CODE, "success");
			Map<String,Object> params = new HashMap<String,Object>();
			params.put("orderNo", orderNo);
			params.put("applyStatusLimit","1");
			List<AftersaleApply> aftersaleApplyList = aftersaleApplyService.findByBiz(params);
			for(AftersaleApply aftersale : aftersaleApplyList){
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("applyNo", aftersale.getApplyNo());
				List<AftersaleApplyItem> aftersaleApplyItems = aftersaleApplyItemService.findByBiz(map);
				if(aftersaleApplyItems!=null && aftersaleApplyItems.size()!=0){
					aftersale.setAftersaleApplyItems(aftersaleApplyItems);
				}
			}
			rsDto.setData(aftersaleApplyList);
		} catch (Exception e) {
			logger.error("AftersaleApplyApiServiceImpl-getAftersaleApply-Exception=》dubbo调用-getAftersaleApply", e);
			//rsDto = new ResultDto(ResultDto.ERROR_CODE, "faild:" + e);
			throw new ServiceException(e);
		}
		return rsDto;
	}

	/**
	 * 雷-----2016年9月20日
	 * (非 Javadoc)
	 * <p>Title: findAftersaleApplyById</p>
	 * <p>Description: 根据id查售后单</p>
	 * @param id
	 * @return
	 * @throws ServiceException
	 * @see com.ffzx.order.api.service.AftersaleApplyApiService#findAftersaleApplyById(java.lang.String)
	 */
	@Override
	public AftersaleApply findAftersaleApplyById(String id) throws ServiceException {
		return this.aftersaleApplyService.findAftersaleApplyById(id);
	}

	/**
	 * 雷-----2016年9月20日
	 * (非 Javadoc)
	 * <p>Title: getAftersalPickupByPickNo</p>
	 * <p>Description: </p>
	 * @param pickuNo
	 * @return
	 * @see com.ffzx.order.api.service.AftersaleApplyApiService#getAftersalPickupByPickNo(java.lang.String)
	 */
	@Override
	public List<AftersalePickup> getAftersalPickupByPickNo(String pickuNo) {
		return this.aftersalePickupService.getAftersalPickupByPickNo(pickuNo);
	}

	/**
	 * 雷-----2016年9月20日
	 * (非 Javadoc)
	 * <p>Title: findAftersalePickupByParams</p>
	 * <p>Description: </p>
	 * @param params
	 * @return
	 * @throws ServiceException
	 * @see com.ffzx.order.api.service.AftersaleApplyApiService#findAftersalePickupByParams(java.util.Map)
	 */
	@Override
	public AftersalePickup findAftersalePickupByParams(Map<String, Object> params) throws ServiceException {
		return this.aftersalePickupService.findAftersalePickupByParams(params);
	}

	/**
	 * 雷-----2016年9月20日
	 * (非 Javadoc)
	 * <p>Title: findRefundInfo</p>
	 * <p>Description: 查询退款单信息，可任意传一个参数</p>
	 * @param id
	 * @param refundNo
	 * @return
	 * @throws ServiceException
	 * @see com.ffzx.order.api.service.AftersaleApplyApiService#findRefundInfo(java.lang.String, java.lang.String)
	 */
	@Override
	public AftersaleRefund findRefundInfo(String id, String refundNo) throws ServiceException {
		return this.aftersaleRefundService.findRefundInfo(id, refundNo);
	}

	/**
	 * 雷-----2016年9月20日
	 * (非 Javadoc)
	 * <p>Title: findAftersaleApplyItemList</p>
	 * <p>Description: </p>
	 * @param aftersaleApplyItem
	 * @return
	 * @throws ServiceException
	 * @see com.ffzx.order.api.service.AftersaleApplyApiService#findAftersaleApplyItemList(com.ffzx.order.api.dto.AftersaleApplyItem)
	 */
	@Override
	public List<AftersaleApplyItem> findAftersaleApplyItemList(AftersaleApplyItem aftersaleApplyItem) throws ServiceException {
		return this.aftersaleApplyItemService.findAftersaleApplyItemList(null, null,null, aftersaleApplyItem);
	}
	 
}
