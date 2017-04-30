package com.ffzx.order.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.ffzx.commerce.framework.annotation.MyBatisDao;
import com.ffzx.commerce.framework.dao.CrudMapper;
import com.ffzx.order.api.dto.AftersaleApplyItem;
import com.ffzx.order.api.dto.OmsOrderdetail;

/**
 * aftersale_apply_item数据库操作接口
 * 
 * @author ffzx
 * @date 2016-05-11 11:50:59
 * @version 1.0.0
 * @copyright www.ffzxnet.com
 */
@MyBatisDao
public interface AftersaleApplyItemMapper extends CrudMapper {

	/****
	 *  根据售后申请单ID 查询对应的售后商品（只针对交易已完成，申请单品）
	 * @param applyId
	 * @return
	 */
	public List<AftersaleApplyItem> selectByApplyId(@Param("applyId")String applyId);
	
	
	
	public int selectHadRreturnCount(@Param("params") Map<String, Object> params);


}