 package com.ffzx.order.service;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.ffzx.basedata.api.dto.Partner;
import com.ffzx.basedata.api.dto.PartnerServiceStation;
import com.ffzx.bms.api.dto.OrderParam;
import com.ffzx.commerce.framework.dto.ResultDto;
import com.ffzx.commerce.framework.exception.ServiceException;
import com.ffzx.commerce.framework.page.Page;
import com.ffzx.commerce.framework.service.BaseCrudService;
import com.ffzx.order.api.dto.ImportBuildOrderVo;
import com.ffzx.order.api.dto.OmsOrder;
import com.ffzx.order.api.dto.OmsOrderdetail;
import com.ffzx.order.api.dto.ReplenishmentOrderVo;
import com.ffzx.order.api.enums.OrderOperationRecordEnum;
import com.ffzx.order.api.enums.PayTypeEnum;
import com.ffzx.order.api.vo.OmsOrderVo;
import com.ffzx.order.api.vo.OrderBiVo;

/**
 * oms_order数据库操作接口
 * 
 * @author ffzx
 * @date 2016-05-10 14:01:15
 * @version 1.0.0
 * @copyright www.ffzxnet.com
 */
public interface OmsOrderService extends BaseCrudService {
	
	/***
	 * 分页查询订单
	 * @param params
	 * @author yinglong.huang
	 * @email yinglong.huang@ffzxnet.com
	 * @date 2016-5-11 下午02:50:40
	 * @version V1.0
	 */
    List<OmsOrder> queryByPage(Page page,Map<String,Object> params)throws Exception;
    List<OmsOrder> queryByList(Map<String,Object> params)throws Exception;
    List<OmsOrder> queryListExport(Map<String,Object> params)throws Exception;
    OmsOrder getOrderByKey(Map<String,Object> params)throws Exception;
    
    /***
	 * 支付回调从主库查询数据
	 * @param params
	 * @author yinglong.huang
	 * @email yinglong.huang@ffzxnet.com
	 * @date 2016-5-11 下午02:50:40
	 * @version V1.0
	 */
    OmsOrder getOrderByWebhooks(Map<String,Object> params)throws Exception;
    
    /**
     * 配送APP 查询订单列表
     * @param page
     * @param params
     * @return
     * @throws Exception
     */
    List<OmsOrderVo> queryDistributionPage(Page page,Map<String,Object> params) throws Exception;
    
    /**
     * 获取 各个状态下 订单数量
     * @param params
     * @return
     * @throws Exception
     */
    OmsOrderVo queryEachDistCount(Map<String,Object> params) throws Exception;
    
	/***
	 * 分页查询订单
	 * @param params
	 * @author yinglong.huang
	 * @email yinglong.huang@ffzxnet.com
	 * @date 2016-5-11 下午02:50:40
	 * @version V1.0
	 */
    void updatePrice(OmsOrder order,List<OmsOrderdetail>detailList)throws Exception;
	
	/***
	 * 对订单进行一系列操作
	 * @param omsOrderNo 订单实体
	 * @param operateType 操作类型,按照约定进行 {OmsOrder.SIGN=签收,OmsOrder.BACK=退单,
	 * OmsOrder.RETURN_BACK=撤销退单,OmsOrder.CANCEL=取消订单,OmsOrder.DELETE=删除订单}
	 * @author ying.cai
	 * @email ying.cai@ffzxnet.com
	 * @date 2016-5-10 下午04:46:40
	 * @version V1.0
	 */
	void operateOrderByType(String omsOrderNo,String operateType)throws Exception;
	
	/***
	 * 根据指定查询参数获取订单数据
	 * @param params 查询参数
	 * map允许有效内容:
	 * params:{
	 * 	memberId:会员ID
	 *  personId:配送员ID
	 *  orderNo:订单编号
	 *  payType:支付类型
	 *  memberName:购买人姓名
	 *  buyTypes:购买类型集合
	 *  status:订单状态集合
	 *  minPrixe:最小支付金额
	 *  maxPrice:最大支付金额
	 *  minBuyCount:最小购买数量
	 *  maxBuyCount:最大购买数量
	 * }
	 * @author ying.cai
	 * @email ying.cai@ffzxnet.com
	 * @date 2016年5月9日 上午9:32:18
	 * @version V1.0
	 * @return
	 * @throws ServiceException
	 */
	public ResultDto findOrderByParams(Map<String,Object> params) throws ServiceException;
	
	/***
	 * 分配送货人
	 * @param orderNo 订单号
	 * @author ying.cai
	 * @email ying.cai@ffzxnet.com
	 * @date 2016-5-11 上午11:30:03
	 * @version V1.0
	 */
	public OmsOrder fpshr(String orderNo)throws ServiceException;
	
	/***
	 * 分配拣货人
	 * @param order 订单实体
	 * 必须具备属性{
	 * 
	 * }
	 * @author ying.cai
	 * @email ying.cai@ffzxnet.com
	 * @date 2016-5-11 上午11:30:15
	 * @version V1.0
	 */
	public OmsOrder fpshr(OmsOrder order)throws ServiceException;
	
	/***
	 * 分配拣货人
	 * @param orderNo 订单号
	 * @author ying.cai
	 * @email ying.cai@ffzxnet.com
	 * @date 2016-5-11 上午11:30:35
	 * @version V1.0
	 */
	public OmsOrder fpjhr(String orderNo)throws ServiceException;
	
	/***
	 * 分配拣货人
	 * @param order 订单实体
	 * 必须具备属性{
	 * 
	 * }
	 * @author ying.cai
	 * @email ying.cai@ffzxnet.com
	 * @date 2016-5-11 上午11:33:22
	 * @version V1.0
	 */
	public OmsOrder fpjhr(OmsOrder order)throws ServiceException;
	
	/***
	 * 不是我配送范围的(对应后台订单分配错误的单以及配送员APP点击不是我配送范围的调用)
	 * @param orderNo 订单号
	 * @author ying.cai
	 * @email ying.cai@ffzxnet.com
	 * @date 2016-5-11 下午02:32:36
	 * @version V1.0
	 */
	public void notSendArea(OmsOrder orderNo);
	
	/***
	 * 根据订单号获取订单详情
	 * @param orderNo 订单号
	 * @author ying.cai
	 * @email ying.cai@ffzxnet.com
	 * @date 2016-5-11 下午02:33:27
	 * @version V1.0
	 */
	public OmsOrder findOrderInfo(String orderNo);
	/**
	 * 根据id查
	 * 
	 * @param orderId
	 * @return
	 * @author yinglong.huang
	 * @email yinglong.huang@ffzxnet.com
	 * @date 2016年10月18日 上午11:40:22
	 * @version V1.0
	 * @return
	 */
	public OmsOrder findOrderInfoById(String orderId);
	
	/***
	* 查询换货订单列表
	* @Title: searchExchangeOrderList 
	* @param page 分页信息
	* @param params 过滤条件
	* @return List<OmsOrder>    返回类型
	 */
	public List<OmsOrder> searchExchangeOrderList(Page page,Map<String,Object> params)throws Exception;
	
	/****
	 * 
	 * 根据订单ID修改订单状态
	 * @param orderId
	 * @return
	 * @throws ServiceException
	 */
	public int updateStatus(OmsOrder order)throws ServiceException;
	
	/****
	 * 
	 * 根据用条件 统计个订单状态量
	 * @param 
	 * @return
	 * @throws ServiceException
	 */
	public Map<String,Object> getStatusCount(Map<String,Object> params)throws ServiceException;
	
	/****
	 * 
	 * 根据条件统计售后数量
	 * @param 
	 * @return
	 * @throws ServiceException
	 */
	public Map<String,Object> getAfterSaleCount(Map<String,Object> params)throws ServiceException;
	
	
	
	
	/****
	 * 
	 * 支付成功操作
	 * @param orderNo 订单编号  paidTime 支付时间
	 * @return
	 * @throws ServiceException
	 */
	public void orderPaySuccess(String orderNo,String charge_id,String payExtra,Date paidTime,PayTypeEnum payType)throws ServiceException;
	
	/**
	 * 支付处理接口
	 * 
	 * @param orderNo
	 * @param handleResult
	 * @throws ServiceException
	 * @author yinglong.huang
	 * @email yinglong.huang@ffzxnet.com
	 * @date 2016年11月4日 下午3:30:43
	 * @version V1.0
	 * @return
	 */
	public void payHandle(String orderNo,String handleResult)throws ServiceException;
	/****
	 * 
	 * 退款成功操作
	 * @param orderNo 订单编号  time_succeed 退款时间
	 * @return
	 * @throws ServiceException
	 */
	public int orderRefundSuccess(String orderNo,Date time_succeed)throws ServiceException;
	
	/****
	 * 
	 * 订单拆单  按
	 * @param orderNo
	 * @return
	 * @throws ServiceException
	 * @throws CloneNotSupportedException 
	 */
	public void orderSplitSingle(OmsOrder omsOrder,List<OmsOrder> splitOrderList)throws ServiceException, CloneNotSupportedException;
	
	/**
	 * 获取用户订单记录金额
	 * @param params
	 * @return
	 * @throws ServiceException
	 */
	public Double getTotalMoney( Map<String, Object> params)throws ServiceException;
	
	/****
	 * 
	 * 当订单分配合伙人错误时
	 * @param  OmsOrder order
	 * @return
	 * @throws ServiceException
	 */
	public void dealAllocationError(OmsOrder order);
	
	
	public List<OmsOrder> findLatestOrderuser(Page page, String commodityId,String activityStartTime);
	
	/**
	 * 订单构建
	 * @param 订单实体 order
	 * @param 
	 * @return
	 * @throws ServiceException
	 */
	public void  buildOrder(OmsOrder omsOrder)throws ServiceException;
	
	/***
	 * 合伙人确认送达
	 * @param uid 合伙人ID
	 * @param oid 订单ID
	 * @date 2016年6月13日 上午11:13:22
	 * @author ying.cai
	 * @email ying.cai@ffzxnet.com
	 */
	public void confirmOrder(String uid,String oid);
	
	/***
	 * 根据订单编号查询订单实体
	 * @param orderNo
	 * @return
	 * @throws ServiceException
	 */
	public OmsOrder getOmsOrderByOrderNo(String orderNo)throws ServiceException;
	
	/***
	 * 获取订单详细信息提供给配送APP使用
	 * @param id 订单ID
	 * @return
	 * @date 2016年6月13日 下午3:37:15
	 * @author ying.cai
	 * @email ying.cai@ffzxnet.com
	 */
	public OmsOrder findOrderInfoToSendApp(String id);
	/**
	 * @author 雷
	 * @date 2016年6月15日
	 * <p>Description: </p>
	 * @param params
	 * @return
	 */
	public OmsOrderVo getSumMemberPay(Map<String, Object> params);
	
	/***
	 * 取得合伙人近六个月的销量报表至配送APP端显示
	 * @param partnerId
	 * @return
	 * @date 2016年6月15日 下午3:12:59
	 * @author ying.cai
	 * @email ying.cai@ffzxnet.com
	 */
	public List<OrderBiVo> findSaleOrderBi(String partnerId);
	
	/***
	 * 当合伙人确认收货后更新订单distributionDate字段
	 * @param orderNos 订单编号集合
	 * @date 2016年6月15日 下午3:30:18
	 * @author ying.cai
	 * @email ying.cai@ffzxnet.com
	 */
	public void updateDistributionDate(String orderNos);
	
	/***
	 * 根据地址管理的最下级地址查询 查询其地址 绑定的合伙人 信息，仓库信息 
	 * @param addressId 地址管理最下级地址id
	 * @author yinglong.huang 
	 * @email ying.cai@ffzxnet.com
	 */
	public Map<String, Object> getAddressInfo(String addressId)throws Exception;
	
	/***
	 * 重新分配合伙人
	 * @param OmsOrder omsOrder
	 * @author yinglong.huang 
	 * @email ying.cai@ffzxnet.com
	 */
	public void updateOrderPartnerInfo(OmsOrder omsOrder)throws Exception;
	
	/***
	 * 重新修改收货地址
	 * @param OmsOrder omsOrder
	 * @author yinglong.huang 
	 * @email ying.cai@ffzxnet.com
	 */
	public void updateOrderAddrInfo(OmsOrder omsOrder)throws Exception;
	
	/***
	 * 取消订单，用于系统自动取消订单
	 * @param order
	 * @param operationRecord 订单操作原因
	 * @date 2016年6月18日 下午2:49:15
	 * @author ying.cai
	 * @email ying.cai@ffzxnet.com
	 */
	public void  cancelOrder(OmsOrder order,OrderOperationRecordEnum operationRecord);
	
	/***
	 * 从wms 实时查询当前物流状态
	 * @param omsOrderNo 订单编号
	 * @date 2016年6月18日 下午2:49:15
	 * return 
	 * 订单状态：
				0：未分配
				1： 已分配
				2： 部分分配
				3：未拣货
				4：拣货完成
				5：装箱中
				6：已装箱
				7：已出库
				-1：取消
				-2：已取消
	 * @author yinglong.huang
	 * @email yinglong.huang@ffzxnet.com
	 */
	public String getLogisticsStatus(String omsOrderNo) throws Exception;
	
	/***
	* 根据换货订单编号和skuId修改老订单商品状态
	* @param params
	* @throws Exception     
	* @return Map<String,Object>    返回类型
	 */
	public Map<String,Object> updateOrderDetailStatus(String exChangeorderNo, String skuId) throws Exception;

	/***
	 * 插入订单日志记录
	 * @param OmsOrder omsOrder
	 * @author yinglong.huang 
	 * @email yinglong.huang@ffzxnet.com
	 */
	public void insertOmsOrderRecord(OmsOrder omsOrder,String oprId,String oprName,String msg,String type);
	
	/***
	 * 批量获取符合自动取消订单条件的订单
	 * @return
	 * @date 2016年7月18日 下午4:37:03
	 * @author ying.cai
	 * @email ying.cai@ffzxnet.com
	 */
	public List<OmsOrder> findOrderToBatchCancel();
	
	public void pushOrder2Wms(String orderNo);

	public List<OmsOrder> findLatestOrderByACId(Page page, String commodityId, String activityStartTime);

	/**
	 * 雷------2016年9月20日
	 * @Title: replenishmentOrders
	 * @Description: 补货修改订单信息
	 * @param @param message
	 * @param @return    设定文件
	 * @return int    返回类型
	 * @throws
	 */
	public int replenishmentOrders(ReplenishmentOrderVo message) throws Exception;
	public ImportBuildOrderVo importBuildOrderExcel(List<String[]> listRow);
	/**
	 * 红包操作
	 * 
	 * @param omsOrder,
	 * @param type 类型（0 下单成功 1 支付成功2取消）
	 * @author yinglong.huang
	 * @email yinglong.huang@ffzxnet.com
	 * @date 2016年11月22日 上午10:57:55
	 * @version V1.0
	 * @return
	 */
	public void oprRedPacket(OmsOrder omsOrder,String type);
	
	/**
	 * 
	 * 
	 * @param detailList
	 * @return
	 * @author yinglong.huang
	 * @email yinglong.huang@ffzxnet.com
	 * @date 2016年11月22日 下午6:44:03
	 * @version V1.0
	 * @return
	 */
	public String getRedPacketUseIdsByDetailList(List<OmsOrderdetail> detailList);
	/**
	 * 
	 * 
	 * @param orderdetailList
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @author yinglong.huang
	 * @email yinglong.huang@ffzxnet.com
	 * @date 2016年11月25日 下午1:55:11
	 * @version V1.0
	 * @return
	 */
	public List<OrderParam> initOrderParamInfo(List<OmsOrderdetail> orderdetailList) throws IllegalAccessException, InvocationTargetException; 
	/** 
	 * 处理虚拟商品
	 * 
	 * @param omsOrder
	 * @author yinglong.huang
	 * @email yinglong.huang@ffzxnet.com
	 * @date 2016年11月23日 上午10:36:30
	 * @version V1.0
	 * @return
	 * @throws Exception 
	 */
	public void virtualItemsHandle(OmsOrder omsOrder) throws ServiceException;
	/**
	 * 
	 * 
	 * @param omsOrderNo
	 * @author yinglong.huang
	 * @email yinglong.huang@ffzxnet.com
	 * @date 2016年12月12日 下午4:03:41
	 * @version V1.0
	 * @return
	 */
	public void  otherHandel(String omsOrderNo);
	
	public <T> int modifyByOrderNo(OmsOrder order) throws ServiceException;
	public int  updateSettlement(Map<String,Object> params);
	
	public Partner findPartnerByMemberPhone(String memberPhone);
	public void initServiceStationInfo(OmsOrder omsOrder);
	public PartnerServiceStation findPartnerServiceStationByPartnerId(String partnerId);
	public PartnerServiceStation getServiceStationInfoByMeberPhone(String memberPhone);
	
	public Partner getPartnerById(String partnerId);
		
	
}