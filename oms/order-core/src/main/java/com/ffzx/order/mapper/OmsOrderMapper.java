package com.ffzx.order.mapper;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.poi.ss.formula.functions.T;

import com.ffzx.commerce.framework.annotation.MyBatisDao;
import com.ffzx.commerce.framework.dao.CrudMapper;
import com.ffzx.commerce.framework.page.Page;
import com.ffzx.order.api.dto.OmsOrder;
import com.ffzx.order.api.dto.ReplenishmentOrderVo;
import com.ffzx.order.api.vo.OmsOrderVo;
import com.ffzx.order.api.vo.OrderBiVo;


/**
 * oms_order数据库操作接口
 * 
 * @author ffzx
 * @date 2016-05-11 12:45:52
 * @version 1.0.0
 * @copyright www.ffzxnet.com
 */
@MyBatisDao
public interface OmsOrderMapper extends CrudMapper {
    
	/**
	 * 根据订单编号查询订单明细信息
	 * @param params
	 * @return
	 */
	public List<OmsOrder> getOrderDetialList(@Param("params") Map<String, Object> params);
	/**
	 * 根据查询条件查询订单
	 * @author yinglong.huang
	 * @email yinglong.huang@ffzxnet.com
	 * @date 2016-5-12 下午02:40:20
	 * @version V1.0
	 */
	 public List<OmsOrder> selectPageOrder(@Param("page") Page page,@Param("params") Map<String, Object> params,@Param("simpleCountSql") Boolean simpleCountSql);
	 public List<OmsOrder> select(@Param("page") Page page,@Param("params") Map<String, Object> params);
	 public List<OmsOrder> select(@Param("params") Map<String, Object> params);
	 public List<OmsOrder> select_export(@Param("params") Map<String, Object> params);
	 
	 public OmsOrder selectByKey(@Param("params") Map<String, Object> params);
	 
	 public List<OmsOrderVo> queryDistributionPage(@Param("page")Page page,@Param("params")Map<String,Object> params);
	 
	 public List<OmsOrderVo> queryDistributionPage(@Param("page")Page page,@Param("params")Map<String,Object> params , @Param("simpleCountSql")Boolean isSimple);

	 public OmsOrderVo queryEachDistCount(@Param("params")Map<String,Object> params);
	/***
	 * 更新订单特定值,申请售后时用;单独出来,提高update效率
	 * @param order {orderNo:订单编号,chargeBackTime:退单时间,finshTime:订单完成时间,status:订单状态}
	 * @return
	 * @author ying.cai
	 * @email ying.cai@ffzxnet.com
	 * @date 2016-5-12 下午02:40:20
	 * @version V1.0
	 */
	public int updateOrderByShouhou(OmsOrder order);
	
	/***
	 * 更新订单状态单独出来,提高update效率
	 * @param order {orderNo:订单编号,status:订单状态,id:订单ID}
	 * @return
	 * @author ying.cai
	 * @email ying.cai@ffzxnet.com
	 * @date 2016-5-12 下午02:40:20
	 * @version V1.0
	 */
	public int updateOrderStatus(OmsOrder order);
	
	/***
	 * 更新订单是否删除标识
	 * @param order {orderNo:订单编号,delFlag:删除标识}
	 * @return
	 * @author ying.cai
	 * @email ying.cai@ffzxnet.com
	 * @date 2016-5-12 下午02:50:04
	 * @version V1.0
	 */
	public int updateOrderDelFlag(OmsOrder order);
	
	/***
	 * 更新用户删除订单标识
	 * @param order {orderNo:订单编号,delFlag:删除标识}
	 * @return
	 * @author yinglong.huang
	 * @email yinglong.huang@ffzxnet.com
	 * @date 2016-8-10 下午03:50:04
	 * @version V1.0
	 */
	public int updateOrderDelFlagByUser(OmsOrder order);
	
	/***
	* 获取换货订单列表
	* @Title: selectExchangeOrder 
	* @param page 分页信息
	* @param params 过滤条件
	* @return List<OmsOrder>    返回类型
	 */
	public List<OmsOrder> selectExchangeOrder(@Param("page") Page page,@Param("params") Map<String, Object> params);
	
	/**
	 * 获取用户订单金额
	 * @param params
	 * @return
	 */
	public Double getTotalMoney(@Param("params") Map<String, Object> params);
	
	/****
	 * 根据相关调教统计状态量
	 * @param params
	 * @return
	 */
	public Map<String,Object> selectStatusCount(@Param("params") Map<String, Object> params);
	
	/****
	 * 根据会员id统计售后数量
	 * @param params
	 * @return
	 */
	public Map<String,Object> selectAfterSaleCount(@Param("params") Map<String, Object> params);
	
	/****
	* 最新已下单用户
	* @Title: findLastOrderUser 
	* @param page 当前页
	* @param params 页容量
	* @return List<OmsOrder>    返回类型
	 */
	public List<OmsOrder> findLastOrderUser(@Param("page") Page page,@Param("params") Map<String, Object> params);
	
	/***
	 * 取得符合自动取消订单条件的订单数据
	 * @param paramsupdateDistributionDate
	 * @return
	 * @date 2016年6月6日 下午2:52:12
	 * @author ying.cai
	 * @email ying.cai@ffzxnet.com
	 */
	public List<OmsOrder> selectToAutoCancelOrder(@Param("params")Map<String,Object> params);
	
	/***
	 * 验证符合指定条件的订单数据是否存在
	 * @param params 条件map
	 * @return 结果大于0则认为存在
	 * @date 2016年6月13日 上午11:18:42
	 * @author ying.cai
	 * @email ying.cai@ffzxnet.com
	 */
	public int validOrderByParams(@Param("params")Map<String,Object> params);
	
	public OmsOrder selectByPrimayOrderNo(@Param("orderNo")String orderNo);
	
	/***
	 * 通过订单ID查询详细信息至配送APP使用
	 * @param id 订单ID
	 * @return
	 * @date 2016年6月13日 下午3:44:42
	 * @author ying.cai
	 * @email ying.cai@ffzxnet.com
	 */
	public OmsOrder findOrderInfoToSendApp(@Param("id")String id);
	/**
	 * @author 雷
	 * @date 2016年6月15日
	 * <p>Description: </p>
	 * @param params
	 * @return
	 */
	public OmsOrderVo queryMemberPay(@Param("params")Map<String, Object> params);
	
	/***
	 * 当合伙人确认收货后更新订单distributionDate字段
	 * @param orderNos
	 * @date 2016年6月15日 下午3:41:11
	 * @author ying.cai
	 * @email ying.cai@ffzxnet.com
	 */
	public void updateDistributionDate(@Param("orderNos") String orderNos);
	
	/***
	 * 合伙人销量统计报表sql
	 * @param partnerId 合伙人ID
	 * @return
	 * @date 2016年6月15日 下午4:58:01
	 * @author ying.cai
	 * @email ying.cai@ffzxnet.com
	 */
	public List<OrderBiVo> findSaleOrderBi(@Param("partnerId")String partnerId); 
	
	/***
	 *  取得符合自动确认收货的订单集合
	 * @param time 时间临界点
	 * @return
	 * @date 2016年6月21日 下午4:51:37
	 * @author ying.cai
	 * @email ying.cai@ffzxnet.com
	 */
	public List<OmsOrder> findAutoConfirmOrderList(@Param("time") Date time);
	
	/***
	 * 批量确认送达
	 * @param orderNos 订单编码集合 
	 * @date 2016年6月21日 下午5:21:18
	 * @author ying.cai
	 * @email ying.cai@ffzxnet.com
	 */
	public void batchAutoConfirmOrder(@Param("orderNos") String orderNos);

	/**
	 * 查询已支付的并且不在wms的订单信息
	 * @param params
	 * @author yinglong.huang
	 * @email yinglong.huang@ffzxnet.com
	 * @date 2016年9月22日 下午12:02:26
	 * @version V1.0
	 * @return
	 */
	public List<OmsOrder> selectListNotInWms(@Param("params")Map<String, Object> params);

	/**
	 * 雷------2016年9月20日
	 * @Title: replenishmentOrders
	 * @Description: TODO
	 * @param @param message
	 * @param @return    设定文件
	 * @return int    返回类型
	 * @throws
	 */
	public int replenishmentOrders(@Param("message") Map<String, Object> message);
	
	public int updateByPrimaryKeySelectiveByOrderNo(OmsOrder order);
	
	public int updateSettlement(@Param("params") Map<String, Object> params);	
}