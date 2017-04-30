package com.ffzx.order.service;

import java.util.List;
import java.util.Map;

import com.ffzx.commerce.framework.exception.ServiceException;
import com.ffzx.commerce.framework.service.BaseCrudService;
import com.ffzx.order.api.dto.OmsOrderdetail;


/**
 * oms_orderdetail数据库操作接口
 * 
 * @author ffzx
 * @date 2016-05-11 12:45:52
 * @version 1.0.0
 * @copyright www.ffzxnet.com
 */
public interface OmsOrderdetailService extends BaseCrudService {
	
	List<OmsOrderdetail> selectJoinSku(Map<String,Object> params)throws Exception;
	
	
	/***
	 * 统计下单量
	 * @param 
	 * @author yinglong.huang
	 * @email  yinglong.huang@ffzxnet.com
	 * @date 2016-5-11 下午02:33:27
	 * @version V1.0
	 */
	public Integer selectBuyCount(Map<String,Object> params);
	
	/****
	 * 根据订单编号和商品编号查询订单详情
	 * @param params
	 * @return
	 */
	public OmsOrderdetail getOrderdetailByCode(Map<String,Object> params);
	
	/****
	 * 根据订单编号查询商品详情集合
	 * @param orderNo
	 * @return
	 */
	public List<OmsOrderdetail> getOrderDetailList(Map<String,Object> params);
	
	/****
	* 推送采购消息提醒(按仓库、供应商分组)
	* @Title: getOrderdetailList 
	* @return List<OmsOrderdetail>    返回类型
	 */
	public List<OmsOrderdetail> getdetailList(Map<String,Object> params);
	
	/***
	 * 通过订单号获取订单详细信息提供给配送APP
	 * （这里有特定的表关联查询，需要查询特定的显示数据，所以不用上方的接口）
	 * @param orderNo
	 * @return
	 * @date 2016年6月13日 下午4:21:02
	 * @author ying.cai
	 * @email ying.cai@ffzxnet.com
	 */
	public List<OmsOrderdetail> findDetailToSendApp(String orderNo);
	
	/****
	* 推送采购消息提醒(按sku分组)
	* @Title: getOrderdetailList 
	* @return List<OmsOrderdetail>    返回类型
	 */
	public List<OmsOrderdetail> getdetailListBySku(Map<String,Object> params);
	
	/****
	 * 修改订单详情商品状态
	 * @param omsOrderDetailId
	 * @param status
	 * @throws ServiceException
	 */
	public void updateOrderDetaiStatus(String omsOrderDetailId, String status) throws ServiceException;


	/**
	 * 雷------2016年9月14日
	 * @Title: getdetailByIDList
	 * @Description: 根据主商品id查询赠品的订单明细
	 * @param @param id
	 * @param @return    设定文件
	 * @return List<OmsOrderdetail>    返回类型
	 * @throws
	 */
	List<OmsOrderdetail> getdetailByIDList(String id);
	
	/**
	 * 雷------2016年9月18日
	 * @Title: getOrderDetailListByApplyId
	 * @Description: 根据售后申请单id查询售后订单明细
	 * @param @param id
	 * @param @return    设定文件
	 * @return List<OmsOrderdetail>    返回类型
	 * @throws
	 */
	public List<OmsOrderdetail> getOrderDetailListByApplyId(String id);


	/**
	 * 雷------2016年9月19日
	 * @Title: updateStatusByPrimaryKey
	 * @Description: 根据id修改订单明细的状态
	 * @param @param detail    设定文件
	 * @return void    返回类型
	 * @throws
	 */
	public int updateStatusByPrimaryKey(OmsOrderdetail detail);
	
	public int  updateSettlement(Map<String,Object> params);
	public List<OmsOrderdetail> selectByCountPriceSettlment(Map<String,Object> params);
	
}