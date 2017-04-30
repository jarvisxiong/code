package com.ffzx.order.api.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.ffzx.commerce.framework.dto.ResultDto;
import com.ffzx.commerce.framework.exception.ServiceException;
import com.ffzx.commerce.framework.page.Page;
import com.ffzx.order.api.dto.OmsOrder;
import com.ffzx.order.api.enums.PayTypeEnum;
import com.ffzx.order.api.vo.OmsOrderParamVo;
import com.ffzx.order.api.vo.OmsOrderVo;

/**
* 订单dubbo接口
* @className OrderService.java
* @author shifeng.tang
* @date   2016年3月28日 下午5:00:11
* @version 1.0
*/
public interface OrderApiService {
	
	/**
	 * 查询订单信息 <单个 or 多个>
	 * @return
	 */
	public List<OmsOrder> queryOrderData(OmsOrderParamVo param);
	
	/**
	 * 
	 * 雷------2016年11月21日
	 * 
	 * @Title: findOrderInfo
	 * @Description: 根据订单号，查询订单与子订单信息的api
	 * @param @param orderNo
	 * @param @return 设定文件
	 * @return OmsOrder 返回类型
	 * @throws
	 */
	public OmsOrder findOrderInfo(String orderNo);

	/**
	 * 
	 * @author 雷
	 * @date 2016年6月15日
	 * <p>Description:统计会员各订单状态的支付总金额 </p>
	 * @param vo
	 * @return
	 */
	public OmsOrderVo getSumMemberPay(OmsOrderVo vo);
	/**
	 * 查询订单分页数据
	 * @param vo
	 * @param page  (List<OmsOrderVo>集合)
	 * @return
	 * @throws ServiceException
	 */
	public Page queryDistributionPage(OmsOrderVo vo,Page page)throws Exception;
	
	
	public OmsOrderVo queryEachDistCount(OmsOrderVo vo)throws ServiceException;
	
	/***
	 * 获得指定条件的订单数据
	 * @param memberId 会员ID
	 * @param buyTypes 购买类型集合,逗号分隔;如: "COMMON_BUY,PRE_SALE,PANIC_BUY" ; 具体枚举查看 BuyTypeEnum.java
	 * @param status 订单状态集合,逗号分隔;如: "PENDING_PAYMENT,RECEIPT_OF_GOODS" ;具体枚举查看 OrderStatusEnum.java
	 * @param page 当前页
	 * @param pageSize 每页数据大小
	 * @author ying.cai
	 * @email ying.cai@ffzxnet.com
	 * @date 2016年5月9日 上午9:32:18
	 * @version V1.0
	 * @return
	 * @throws ServiceException
	 */
	public ResultDto findOrderListByMember(String memberId,String status,
			int page,int pageSize,int waterline) throws ServiceException;
	
	/**
	 * 根据参数查询订单 ，并返回page对象
	 * @param params
	 * @param page
	 * @param pageSize
	 * @param waterline
	 * @return
	 * @throws ServiceException
	 * @author yinglong.huang
	 * @email yinglong.huang@ffzxnet.com
	 * @date 2017年1月5日 上午9:51:07
	 * @version V1.0
	 * @return
	 */
	public ResultDto getOrderByPage(Map<String,Object> params,int page,int pageSize,int waterline) throws ServiceException;;
	
	/***
	 * 签收订单
	 * @param orderNo 订单编号
	 * @author ying.cai
	 * @email ying.cai@ffzxnet.com
	 * @date 2016年5月9日 上午9:32:18
	 * @version V1.0
	 * @return
	 * @throws ServiceException
	 */
	public ResultDto signOrder(String orderNo)throws ServiceException;
	
	/***
	 * 退单申请
	 * @param orderNo 订单编号
	 * @author ying.cai
	 * @email ying.cai@ffzxnet.com
	 * @date 2016年5月9日 上午9:32:18
	 * @version V1.0
	 * @return
	 * @throws ServiceException
	 */
	public ResultDto backOrder(String orderNo)throws ServiceException;
	
	/***
	 * 撤销退单申请
	 * @param orderNo 订单编号
	 * @author ying.cai
	 * @email ying.cai@ffzxnet.com
	 * @date 2016年5月9日 上午9:32:18
	 * @version V1.0
	 * @return
	 * @throws ServiceException
	 */
	public ResultDto returnBackOrder(String orderNo)throws ServiceException;
	
	/***
	 * 移除订单
	 * @param orderNo 订单编号
	 * @author ying.cai
	 * @email ying.cai@ffzxnet.com
	 * @date 2016年5月9日 上午9:32:18
	 * @version V1.0
	 * @return
	 * @throws ServiceException
	 */
	public ResultDto deleteOrder(String orderNo)throws ServiceException;
	
	/***
	 * 取消订单
	 * @param orderNo 订单编号
	 * @author ying.cai
	 * @email ying.cai@ffzxnet.com
	 * @date 2016年5月9日 上午9:32:18
	 * @version V1.0
	 * @return
	 * @throws ServiceException
	 */
	public ResultDto cancelOrder(String orderNo)throws ServiceException;
	
	/**
	 * 支付成功dubbo
	 * @param orderNo 订单号码
	 * @param charge_id ping++支付id,若为空值请传null即可
	 * @param paidTime 支付时间
	 * @param payType 支付方式 :支付宝PC:ALIPAYPC;支付宝扫码:ALIPAYSM;微信支付:WXPAY,支付宝APP:ALIPAYAPP;腾付通快捷支付:TFTFASTPAY
	 * @return
	 * @throws ServiceException
	 * @author yinglong.huang
	 * @email yinglong.huang@ffzxnet.com
	 * @date 2016年10月18日 下午3:45:10
	 * @version V1.0
	 * @return
	 */
 
	public ResultDto paySuccess(String orderNo,String charge_id, Date paidTime,PayTypeEnum payType)throws ServiceException;
	
	/***
	 * 
	 * @param orderNo
	 * @param handleResult：【PROCESSING】支付处理中，【FAIL】支付失败
	 * @throws ServiceException  code==-1 操作异常，code==0:业务异常，查无此单
	 * @author yinglong.huang
	 * @email yinglong.huang@ffzxnet.com
	 * @date 2016年11月4日 下午3:32:02
	 * @version V1.0
	 * @return 
	 */
	public ResultDto payHandle(String orderNo,String handleResult)throws ServiceException;
	/**
	 * 退款成功接口
	 * 
	 * @param orderNo 订单号
	 * @param time_succeed 退款时间
	 * @return
	 * @author yinglong.huang
	 * @email yinglong.huang@ffzxnet.com
	 * @date 2016年10月18日 下午4:11:46
	 * @version V1.0
	 * @return
	 */
	public ResultDto refundSuccess(String orderNo, Date time_succeed);
	/***
	 * 发起支付
	 * @param orderNo 订单编号
	 * @return
	 * @throws ServiceException
	 * @author yinglong.huang
	 * @email yinglong.huang@ffzxnet.com
	 * @date 2016-5-10 下午05:25:26
	 * @version V1.0
	 */
	public ResultDto pingxxPay(String orderNo,String payType,String value )throws ServiceException;
	
	/**
	 * 统计下单量
	 * @param params  查询参数(根据需求确定查询参数)
	 * @return
	 * @throws ServiceException
	 */
	public ResultDto selectBuyCount(Map<String,Object> params) throws ServiceException;
	
	/**
	 * 获取订单操作记录
	 * @param orderId   订单编号   必传  String
	 * @param recordType  操作类型   必传   String  1:物流状态    0：售后状态
	 * @return
	 * @throws ServiceException
	 */
	public ResultDto getOrderRecord(String orderId,String recordType) throws ServiceException;

	
	
	/*****
	 * 获取订单信息 根据订单ID
	 * @param id
	 * @return
	 * @throws ServiceException
	 */
	public ResultDto findOrderById(String id)throws ServiceException;
	
	/**
	 * 获取订单信息 根据订单编号
	 * @param orderNo
	 * @return
	 * @throws ServiceException
	 * @author yinglong.huang
	 * @email yinglong.huang@ffzxnet.com
	 * @date 2016年10月18日 下午4:21:08
	 * @version V1.0
	 * @return
	 */
	public ResultDto findOrderByNo(String orderNo)throws ServiceException;
	
	/**
	 * 单个订单查询
	 * @param orderNumber   订单编号  必填    
	 * @return
	 * @throws ServiceException
	 */
	public ResultDto indentSearchOne(String orderNumber) throws ServiceException;
	
	/**
	 * 更改订单状态
	 * @param orderId   订单编号必填     
	 * @param status    订单状态必填     
	 * @return
	 * @throws ServiceException
	 */
	public ResultDto upDateStatus(String orderId,String status) throws ServiceException;
	
	/**
	 * 根据更改订单状态
	 * @param orderId   订单编号必填     
	 * @param type    订单状态必填     
	 * @return
	 * @throws ServiceException
	 */
	public ResultDto upDateStatusByType(String orderId,String type) throws ServiceException;
	

	/******
	 * 根据订单编号和商品shuId 获取订单商品详情
	 * @param orderNo
	 * @param skuId
	 * @return
	 * @throws ServiceException
	 */
	public ResultDto findOmsOrderDetail(String orderNo,String skuId)throws ServiceException;
	

	/**
	 * 更改订单明细状态
	 * @param omsOrderdetailId   订单编号必填     
	 * @param status    订单状态必填     
	 * @return
	 * @throws ServiceException
	 */
	public ResultDto upDateomsOrderdetaiStatus(List<String> omsOrderdetailId,String status) throws ServiceException;
	
	/**
	 * 获取用户账单报表
	 * @param uid    必传  用户id  
	 * @param month  必传   月份账单
	 * @return
	 * @throws ServiceException
	 */
	public ResultDto getUserOrderData(String uid,String month) throws ServiceException;
	/**
	 * 统计订单状态数量
	 * @param userId   订单编号必填     
	 * @param status    订单状态必填     
	 * @return
	 * @throws ServiceException
	 */
	public ResultDto getOrderStatusCountByUser(String memberId) throws ServiceException;

	/**
	 * 订单构建
	 * @param 订单实体 order
	 * @param 
	 * @return
	 * @throws ServiceException
	 */
	public ResultDto buildOrder(OmsOrder omsOrder) throws ServiceException;
	
	/**
	 * 统计某个订单下面的某件商品的购买数量
	 * @param Map<String,Object> params 参数可以照实际需求传递
	 * @param 
	 * @return int
	 * @throws ServiceException
	 */
	public ResultDto selectOrderDetailCount(Map<String,Object> params)throws ServiceException;;
	
	/**
	 * 根据商品skuid获取某个商品的信息
	 * @param skuId
	 * @param 
	 * @return int
	 * @throws ServiceException
	 */
	public ResultDto getOrderCommoditySkuById(String skuId)throws ServiceException;;
	
	/**
	* 最新已下单用户
	* @Title: getLatestOrderuser 
	* @param commodityId 商品Id
	* @param page 当前页
	* @param pageSize 页容量
	* @throws ServiceException     
	* @return ResultDto    返回类型
	 */
	public ResultDto getLatestOrderuser(String commodityId,String activityStartTime,int page, int pageSize)throws ServiceException;
	
	/***
	 * 合伙人 确认送达
	 * @param uid 合伙人ID
	 * @param oid
	 * @return
	 * @date 2016年6月13日 上午9:36:56
	 * @author ying.cai
	 * @email ying.cai@ffzxnet.com
	 */
	public ResultDto confirmOrder(String uid,String oid);
	
	/***
	 * 通过订单ID获取特定的数据给配送APP接口
	 * @param id 订单ID
	 * @return data=OmsOrder实体
	 * @date 2016年6月13日 下午3:33:08
	 * @author ying.cai
	 * @email ying.cai@ffzxnet.com
	 */
	public ResultDto findOrderInfoToSendApp(String id);
	public ResultDto findOrderInfoByNo(String orderN);
	/***
	 * 订单分配错误
	 * @param oid 订单id 
	 * @return
	 * @date 2016年6月13日 上午9:36:56
	 * @author yinglong.huang 
	 */
	public ResultDto allocationError(String orderId);
	
	/***
	 * 配送人 确认收货
	 * @param billId 配送单id 
	 * @param oid 订单id 
	 * @param personId 配送人id 
	 * @param keyValue 相关编辑行值 
	 * @return
	 * @date 2016年6月13日 上午9:36:56
	 * @author yinglong.huang 
	 */
	public ResultDto confirmReceipt(String billId,String oid,String personId,String keyValue);
	
	/***
	 * 当合伙人确认收货后更新订单distributionDate字段
	 * @param orderNos 订单编号集合
	 * @return
	 * @date 2016年6月15日 下午3:27:47
	 * @author ying.cai
	 * @email ying.cai@ffzxnet.com
	 */
	public ResultDto updateDistributionDate(String orderNos);
	
	/***
	 * 获得指定合伙人近六个月的销售记录
	 * @param partnerId
	 * @return
	 * @date 2016年6月17日 下午4:05:43
	 * @author ying.cai
	 * @email ying.cai@ffzxnet.com
	 */
	public ResultDto findSaleOrderBi(String partnerId);
	
	/****
	* 根据换货订单编号和skuId修改老订单商品状态
	* @param exChangeorderNo
	* @param skuId
	* @return ResultDto    返回类型
	 */
	public ResultDto updateOrderDetailStatus(String exChangeorderNo, String skuId);
	
	/***
	 * 对外商品购买 接口服务
	 * @param sysType 0:android,1:ios,2:pc
	 * @param memberId 购买会员ID
	 * @param couponId 使用优惠券ID
	 * @param addressId 收货地址ID
	 * @param isInvoice 是否需要发票
	 * @param desc 订单备注
	 * @param cityId 所在城市地区ID
	 * @param goodsList 所购买商品集合对象
	 * @author ying.cai
	 * @email ying.cai@ffzxnet.com
	 * @date 2016年5月9日 上午9:32:18
	 * @version V1.0
	 * @return 
	 * data{
	 * 	 id:订单ID
	 * 	 orderNo:订单编号
	 *   buyTime:购买下单时间
	 *   overTime:订单超时自动取消时间
	 *   totalPrice:订单需支付总金额	
	 * }
	 * @throws ServiceException
	 */
	public ResultDto buyGoods(int sysType,String memberId,String couponId,
			String addressId, int isInvoice, String desc,String cityId,String goodsList)throws ServiceException;
	
	/**
	 * 获取抢购最新下单用户修改
	 * @param commodityId
	 * @param acitivityCommodityId
	 * @param page
	 * @param pageSize
	 * @return
	 * @throws ServiceException
	 */
	public ResultDto getLatestOrderByACId(String commodityId, String acitivityCommodityId, int page, int pageSize)
			throws ServiceException;
	
	
	/**
	 * 
	* @Title: findLoisticsStatusByOrderNos 
	* @Description: 根据订单编号列表查询对应最新物流状态
	* @param orderNoList
	* @throws ServiceException
	 */
	public ResultDto findLogisticsStatusByOrderNos(List<String> orderNoList)throws ServiceException;
	/**
	 * 
	 * 雷------2016年9月19日
	 * @Title: findGifts
	 * @Description: 根据主商品的订单明细ID查赠品明细
	 * @param @param mainCommodityId
	 * @param @return
	 * @param @throws ServiceException    设定文件
	 * @return ResultDto    返回类型
	 * @throws
	 */
	public ResultDto findGifts(String mainCommodityId)throws ServiceException;
	
	/***
	 * 根据买赠活动的订单ID获取订单集合
	 * @param params
	 * @return
	 */
	public ResultDto findOrderBuyGive(Map<String, Object> params);
	
	/**
	 * 成单构建，即：移动端确认下单动作
	 * 
	 * @param sysType
	 * @param uId
	 * @param couponId
	 * @param addressId
	 * @param isInvoice
	 * @param goodsListStr
	 * @return
	 * @author yinglong.huang
	 * @email yinglong.huang@ffzxnet.com
	 * @date 2016年10月27日 上午9:31:22
	 * @version V1.0
	 * @return
	 */
	public ResultDto orderForm(String sysType, String uId, String couponId, String addressId,
			String isInvoice, String goodsListStr);
}
