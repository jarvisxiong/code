package com.ffzx.portal.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.ffzx.commerce.framework.annotation.MyBatisDao;
import com.ffzx.commerce.framework.dao.CrudMapper;
import com.ffzx.commerce.framework.page.Page;
import com.ffzx.portal.model.CatelogData;

/**
 * @className CatelogDataMapper
 *
 * @author liujunjun
 * @date 2017-04-12
 * @version 1.0.0
 */
@MyBatisDao
public interface CatelogDataMapper extends CrudMapper {

	/**
	 * @param id
	 * @param sort
	 * @return
	 * @author ying.cai
	 * @date 2017年4月12日下午5:39:23
	 * @email ying.cai@ffzxnet.com
	 */
	List<CatelogData> selectListById(@Param("id")String id, @Param("sort")String sort);
	
	/***
	 * 
	 * @param page
	 * @param orderByField
	 * @param orderBy
	 * @param params
	 * @return
	 * @author ying.cai
	 * @date 2017年4月13日下午1:59:23
	 * @email ying.cai@ffzxnet.com
	 */
	List<CatelogData> selectByPageList(@Param("page") Page page,
			@Param("orderByField") String orderByField,
			@Param("orderBy") String orderBy,
			@Param("params") Map<String, Object> params);

	/**
	 * @param cid
	 * @return
	 * @author ying.cai
	 * @date 2017年4月13日下午2:05:38
	 * @email ying.cai@ffzxnet.com
	 */
	CatelogData getInfoById(@Param("id")String cid);

	/**
	 * @param pagetabId
	 * @return
	 * @author ying.cai
	 * @date 2017年4月13日下午2:15:38
	 * @email ying.cai@ffzxnet.com
	 */
		/**
	 * @param pagetabId
	 * @return
	 * @author ying.cai
	 * @date 2017年4月13日下午2:14:40
	 * @email ying.cai@ffzxnet.com
	 */
	List<CatelogData> selectById(@Param("id")String pagetabId);

	/**
	 * 
	 * @author ying.cai
	 * @param catelogId 
	 * @date 2017年4月14日上午8:43:03
	 * @email ying.cai@ffzxnet.com
	 */
	void batchUpdateTop(@Param("catelogId")String catelogId);
}
