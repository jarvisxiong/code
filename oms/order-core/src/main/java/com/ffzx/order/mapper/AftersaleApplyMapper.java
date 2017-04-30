package com.ffzx.order.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.ffzx.commerce.framework.annotation.MyBatisDao;
import com.ffzx.commerce.framework.dao.CrudMapper;
import com.ffzx.commerce.framework.page.Page;
import com.ffzx.order.api.dto.AftersaleApply;


/**
 * aftersale_apply数据库操作接口
 * 
 * @author ffzx
 * @date 2016-05-11 11:50:59
 * @version 1.0.0
 * @copyright www.ffzxnet.com
 */
@MyBatisDao
public interface AftersaleApplyMapper extends CrudMapper {
	
	public AftersaleApply findApplyById(@Param("id")String id);
	public AftersaleApply findApplyStateById(@Param("id")String id);
	public int updateNo(@Param("params")Map<String, Object> params);
	
	public  List<AftersaleApply> selectPageByParams(@Param("page") Page page,
			@Param("orderByField") String orderByField,
			@Param("orderBy") String orderBy,
			@Param("params") Map<String, Object> params);
	public  List<AftersaleApply> selectPageByParamsNew(@Param("page") Page page,
			@Param("orderByField") String orderByField,
			@Param("orderBy") String orderBy,
			@Param("params") Map<String, Object> params);
	
	public int updateByParams(AftersaleApply aftersaleApply);

	/**
	 * 雷------2016年8月10日
	 * @Title: beforeImportApplyList
	 * @Description: 售后管理：导出数据之前的检查
	 * @param @param params
	 * @param @return    设定文件
	 * @return int    返回类型
	 * @throws
	 */
	public int beforeImportApplyList(@Param("params")Map<String, Object> params);

	/**
	 * 雷------2016年8月12日
	 * @Title: importApplyList
	 * @Description: 售后管理：导出数据 
	 * @param @param params
	 * @param @return    设定文件
	 * @return List<Map>    返回类型
	 * @throws
	 */
	public List<Map> importApplyList(@Param("params") Map<String, Object> params);


	/**
	 * 雷------2016年11月3日
	 * 
	 * @Title: findByApplyNo
	 * @Description:根据applyNo查询AftersaleApply
	 * @param @param applyNo
	 * @param @return 设定文件
	 * @return AftersaleApply 返回类型
	 * @throws
	 */
	public AftersaleApply findByApplyNo(@Param("params") Map<String, Object> params);
	/**
	 * 雷------2016年12月5日
	 * @Title: importRefundList
	 * @Description: TODO
	 * @param @param params
	 * @param @return    设定文件
	 * @return List<Map>    返回类型
	 * @throws
	 */
	public List<Map> importRefundList(@Param("params") Map<String, Object> params);
}