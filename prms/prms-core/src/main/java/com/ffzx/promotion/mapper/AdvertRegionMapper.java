package com.ffzx.promotion.mapper;

import java.util.List;

import com.ffzx.commerce.framework.annotation.MyBatisDao;
import com.ffzx.commerce.framework.dao.CrudMapper;
import com.ffzx.commerce.framework.exception.ServiceException;
import com.ffzx.promotion.api.dto.AdvertRegion;

/**
 * @className AdvertRegionMapper
 *
 * @author hyl
 * @date 2016-05-03
 * @version 1.0.0
 */
@MyBatisDao
public interface AdvertRegionMapper extends CrudMapper {
	  /**
	   * 通过父id查询子集
	   * @param menu
	   * @return
	   */
	 public List<AdvertRegion> getByParentIdsLike(String parentIds)throws ServiceException;
}
