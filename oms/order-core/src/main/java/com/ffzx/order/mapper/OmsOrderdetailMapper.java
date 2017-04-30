package com.ffzx.order.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.ffzx.commerce.framework.annotation.MyBatisDao;
import com.ffzx.commerce.framework.dao.CrudMapper;
import com.ffzx.order.api.dto.OmsOrderdetail;

/**
 * oms_orderdetail数据库操作接口
 * 
 * @author ffzx
 * @date 2016-05-11 12:45:52
 * @version 1.0.0
 * @copyright www.ffzxnet.com
 */
@MyBatisDao
public interface OmsOrderdetailMapper extends CrudMapper {
	
	
	/***
	 *  关联查询  
	 * @param omsOrderdetail 必须具备属性{id:主键ID,orderId:关联订单ID,orderNo,关联订单编号}
	 * @return
	 * @author yinglong.huang
	 * @email yinglong.huang@ffzxnet.com
	 * @date 2016-5-12 下午03:23:06
	 * @version V1.0
	 */
	
	public <T> List<T> selectJoinSkuByParams(@Param("params") Map<String, Object> params);
	
	/***
	 *  订单拆分后,更新订单明细数据
	 * @param omsOrderdetail 必须具备属性{id:主键ID,orderId:关联订单ID,orderNo,关联订单编号}
	 * @return
	 * @author ying.cai
	 * @email ying.cai@ffzxnet.com
	 * @date 2016-5-12 下午03:23:06
	 * @version V1.0
	 */
	public int updateDetBySplit(OmsOrderdetail omsOrderdetail);
	
	/***
	 * 查询商品销量
	 * @param 统计数量
	 * @return
	 * @author yinglong.huang
	 * @email yinglong.huang@ffzxnet.com
	 * @date 2016-5-12 下午03:23:06
	 * @version V1.0
	 */
	public Integer selectBuyCount(@Param("params") Map<String, Object> params);
	
	/****
	 * 根据订单编号，商品编号获取详情
	 * @param params
	 * @return
	 */
	public OmsOrderdetail selectByCode(@Param("params") Map<String, Object> params);
	
	/****
	* 订单推送采购的消息(按仓库、供应商分组)
	* @Title: selectJoinOrderRemind 
	* @return     
	* @return List<OmsOrderdetail>    返回类型
	 */
	public List<OmsOrderdetail> selectJoinOrderRemind(@Param("params") Map<String, Object> params);
	
	/****
	* 订单推送采购的消息(按sku分组)
	* @Title: selectJoinOrderRemind 
	* @return     
	* @return List<OmsOrderdetail>    返回类型
	 */
	public List<OmsOrderdetail> selectJoinOrderRemindGroupBySku(@Param("params") Map<String, Object> params);
	
	
	public List<OmsOrderdetail> getDailySalesData(@Param("params") Map<String, Object> params);
	
	/***
	 * 通过订单ID查询详细信息至配送APP使用
	 * @param orderNo
	 * @return
	 * @date 2016年6月13日 下午4:24:40
	 * @author ying.cai
	 * @email ying.cai@ffzxnet.com
	 */
	public List<OmsOrderdetail> findDetailToSendApp(@Param("orderNo")String orderNo);
	
	/***
	* 通过订单编号和skuId修改订单详情状态
	* @param params 
	* @return Integer    返回类型
	 */
	public void updateOrderDetailStatus (@Param("params") Map<String, Object> params);
	
	/***
	 * 售后申请单交易完成的驳回修改商品售后状态
	 * @param orderNo
	 * @param skuId
	 */
	public void updateOrderDetailStatusByOrderNoAndSkuId(@Param("orderNo")String orderNo,@Param("skuId")String skuId);
	/**
	 * 雷------2016年9月14日
	 * @Title: getdetailByIDList
	 * @Description: 根据主商品id查询赠品的订单明细
	 * @param @param mainCommodityId
	 * @param @return    设定文件
	 * @return List<OmsOrderdetail>    返回类型
	 * @throws
	 */
	public List<OmsOrderdetail> getdetailByIDList(@Param("mainCommodityId") String mainCommodityId);
	/**
	 * 雷------2016年9月18日
	 * @Title: getOrderDetailListByApplyId
	 * @Description: 根据售后申请单id查询售后订单明细
	 * @param @param applyId
	 * @param @return    设定文件
	 * @return List<OmsOrderdetail>    返回类型
	 * @throws
	 */
	public List<OmsOrderdetail> getOrderDetailListByApplyId(@Param("applyId") String applyId);

	/** 
	 * 雷------2016年9月19日
	 * @Title: updateStatusByPrimaryKey
	 * @Description:根据id修改订单明细的状态
	 * @param @param entity
	 * @param @return    设定文件
	 * @return int    返回类型
	 * @throws
	 */
	public  <T> int  updateStatusByPrimaryKey(T entity);
	
	public int updateSettlement(@Param("params") Map<String, Object> params);	
	
	public List<OmsOrderdetail> selectByCountPriceSettlment(@Param("params") Map<String, Object> params);
}