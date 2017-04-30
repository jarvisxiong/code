///**
// * 
// */
//package com.ffzx.order.service;
//
//import java.util.List;
//import java.util.Map;
//
//import com.ffzx.commerce.framework.dto.ResultDto;
//import com.ffzx.commerce.framework.exception.ServiceException;
//import com.ffzx.order.api.dto.OrderParam;
//import com.ffzx.wms.api.dto.Warehouse;
//
///**
// * 订单流程操作
// * 
// * @author wangricheng
// * @date 2016年8月16日 上午10:20:11
// * @version 1.0.0
// */
//public interface OrderProcessManagerService {
//
//	/***
//	 * 待付款 --换货待付款
//	 *    下单修改库存占用
//	 * @param list：SKU编号:skuCode；商品对象：commodity；数量：number，
//     * @param  addressId：收货地址ID，
//     * @param  cenWarehouse：中央仓，
//     * @param  orderNo：订单号
//	 * @author richeng.wang
//	 * @email richeng.wang@ffzxnet.com
//	 * @date 2016年5月18日 上午9:32:18
//	 * @version V1.0
//	 * @return 
//	 * data{
//	 * 	 
//	 * }
//	 * @throws ServiceException
//	 */
//	public ResultDto pendingExchangePayment(List<OrderParam> list,String addressId,String orderNo,String titile) throws ServiceException;
//	
//	/***
//	 * 待付款 
//	 *    下单修改库存占用
//	 * @param list：SKU编号:skuCode；商品对象：commodity；数量：number，
//     * @param  addressId：收货地址ID，
//     * @param  orderNo：订单号
//	 * @author richeng.wang
//	 * @email richeng.wang@ffzxnet.com
//	 * @date 2016年8月16日 上午10:20:11
//	 * @version V1.0
//	 * @return 
//	 * data{
//	 * 	 
//	 * }
//	 * @throws ServiceException
//	 */
//	public ResultDto pendingPayment(List<OrderParam> list,String addressId,String orderNo,String title) throws ServiceException;
//	
//	/***
//	 * 待收货(已出库交接) 接口服务
//	 *    订单发货：修改库存，修改占用数
//	 * @param list：SKU编号:skuCode；商品对象：commodity；数量：number，warehouseCode 仓库Code
//	 * @param addressId
//	 *            收货地址ID
//	 * @param  orderNo：订单号
//	 * @author richeng.wang
//	 * @email richeng.wang@ffzxnet.com
//	 * @date 2016年8月16日 上午10:20:11
//	 * @version V1.0
//	 * @return 
//	 * data{
//	 * 	 
//	 * }
//	 * @throws ServiceException
//	 */
//	public ResultDto inbound(List<OrderParam> list,String addressId, String orderNo,String title) throws ServiceException;
//	
////	/***
////	 * 退款申请中（装箱符合前） 接口服务
////	 * @param map  map集合
////	 *   map  键：SKU编号，值：数量
////	 * @param addressId 收货地址ID
////	 * @author richeng.wang
////	 * @email richeng.wang@ffzxnet.com
////	 * @date 2016年8月16日 上午10:20:11
////	 * @version V1.0
////	 * @return 
////	 * data{
////	 * 	 
////	 * }
////	 * @throws ServiceException
////	 */
////	public ResultDto befRefundApplication(Map<String,Long> map,String addressId,String orderNo) throws ServiceException;
//	
//	/***
//	 * 订单取消 接口服务
//	 * @param list：SKU编号:skuCode；商品对象：commodity；数量：number，warehouseCode 仓库Code
//	 * @param addressId
//	 *            收货地址ID
//	 * @param  orderNo：订单号
//	 * @author richeng.wang
//	 * @email richeng.wang@ffzxnet.com
//	 * @date 2016年8月16日 上午10:20:11
//	 * @version V1.0
//	 * @return 
//	 * data{
//	 * 	 
//	 * }
//	 * @throws ServiceException
//	 */
//	public ResultDto cancelOrder(List<OrderParam> list,String addressId, String orderNo,String title) throws ServiceException;
//	
//	/***
//	 * 商品sku校验下单可行性
//	 * @param map  map集合
//	 *   map  键：SKU编号，值：数量
//	 * @param addressId 收货地址ID
//	 * @author richeng.wang
//	 * @email richeng.wang@ffzxnet.com
//	 * @date 2016年5月18日 上午9:32:18
//	 * @version V1.0
//	 * @return ResultDto
//	 * data{
//	 * 	 Map<String,Long>  不符合规则的集合
//	 * }
//	 * @throws ServiceException
//	 */
//	public ResultDto verifyOverbooking(List<OrderParam> list,String addressId) throws ServiceException;
//	
//}
