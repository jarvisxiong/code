package com.ffzx.order.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ffzx.commerce.framework.annotation.MyBatisDao;
import com.ffzx.commerce.framework.dao.CrudMapper;
import com.ffzx.order.api.vo.OmsOrderVo;

/**
 * oms_order_record数据库操作接口
 * 
 * @author ffzx
 * @date 2016-05-10 14:04:50
 * @version 1.0.0
 * @copyright www.ffzxnet.com
 */
@MyBatisDao
public interface OmsOrderRecordMapper extends CrudMapper {
	public List<OmsOrderVo> selectLogisticsStatusByOrderNos(@Param("orderNoList")List<String> orderNoList);

}