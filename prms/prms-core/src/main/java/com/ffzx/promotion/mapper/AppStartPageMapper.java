package com.ffzx.promotion.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.ffzx.commerce.framework.annotation.MyBatisDao;
import com.ffzx.commerce.framework.dao.CrudMapper;
import com.ffzx.promotion.api.dto.AppStartPage;

/**
 * @className AppStartPageMapper
 *
 * @author liujunjun
 * @date 2016-09-09
 * @version 1.0.0
 */
@MyBatisDao
public interface AppStartPageMapper extends CrudMapper {

	/**
	* 验证是否存在重叠时间数据 
	* @param params
	* @return     
	* @return List<AppStartPage>    返回类型
	 */
	public List<AppStartPage> selectByDate(@Param("params") Map<String, Object> params);
	
	/**
	* 启动页接口
	* @param params
	* @return List<AppStartPage>    返回类型
	 */
	public List<AppStartPage> findAPPStartPageList(@Param("params") Map<String, Object> params);
	
	/**
	* TODO(修改启动页广告)
	* @param appStartPage
	* @return int    返回类型
	 */
	public int updateBySelective(AppStartPage appStartPage);
}
