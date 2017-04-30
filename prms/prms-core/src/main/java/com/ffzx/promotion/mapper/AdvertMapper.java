package com.ffzx.promotion.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.ffzx.commerce.framework.annotation.MyBatisDao;
import com.ffzx.commerce.framework.dao.CrudMapper;
import com.ffzx.promotion.api.dto.Advert;

/**
 * @className AdvertMapper
 *
 * @author hyl
 * @date 2016-05-03
 * @version 1.0.0
 */
@MyBatisDao
public interface AdvertMapper extends CrudMapper {
		public List<Advert> selectByUpdateStatus();
}
