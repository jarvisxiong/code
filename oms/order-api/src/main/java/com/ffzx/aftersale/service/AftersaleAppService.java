package com.ffzx.aftersale.service;

import java.util.List;
import java.util.Map;

import com.ffzx.order.api.dto.AftersaleApply;

public interface AftersaleAppService {

	
	/****
	 * 售后申请提交
	 * @param map
	 * @return
	 */
//	public Map<String, Object> saveAftersaleApply(Map<String, Object> map);
	/****
	 * 售后申请提交2016-09-19
	 * @param map
	 * @return
	 */
	public Map<String, Object> saveAftersaleApplyNew(Map<String, Object> map);
	/**
	 * 批量申请售后单
	 */
	public Map<String, Object>  batchAftersaleApply(List<Map<String,Object>> applyParamsList);
	
	/****
	 * 迁移，获取售后单列表
	 * @param map
	 * @return
	 */
	public Map<String, Object> findAftersaleApplyPageNew(AftersaleApply apply);
	/**
	 * 
	 * 雷------2016年10月31日
	 * @Title: findAftersaleApplyDetailNew
	 * @Description: 迁移，获取售后详情2
	 * @param @param apply
	 * @param @return    设定文件
	 * @return Map<String,Object>    返回类型
	 * @throws
	 */
	public Map<String, Object> findAftersaleApplyDetailNew(AftersaleApply apply);
}
