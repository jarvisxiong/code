package com.ffzx.promotion.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.ffzx.commerce.framework.annotation.MyBatisDao;
import com.ffzx.commerce.framework.dao.CrudMapper;
import com.ffzx.promotion.api.dto.ActivityGive;
import com.ffzx.promotion.vo.CouponExportVo;
import com.ffzx.promotion.vo.GiftExportVo;
import com.ffzx.promotion.vo.GiveExportVo;

/**
 * activity_give数据库操作接口
 * 
 * @author ffzx
 * @date 2016-09-12 11:25:09
 * @version 1.0.0
 * @copyright www.ffzxnet.com
 */
@MyBatisDao
public interface ActivityGiveMapper extends CrudMapper {
	
	/***
	 * 数据导出查询，查询主商品信息
	 * @param idList
	 * @return
	 * @date 2016年9月19日 上午11:45:06
	 * @author ying.cai
	 * @email ying.cai@ffzxnet.com
	 */
	List<GiveExportVo> findGiveListToExport(@Param("params")Map<String,Object>params);
	
	/***
	 * 数据导出查询，根据主商品id查询优惠券赠品信息
	 * @param params
	 * @return
	 * @date 2016年9月30日 上午9:53:43
	 * @author ying.cai
	 * @email ying.cai@ffzxnet.com
	 */
	List<CouponExportVo> findCouponListToExport(@Param("params")Map<String,Object>params);
	

	/***
	 * 数据导出查询，根据主商品id查询商品赠品赠品信息
	 * @param params
	 * @return
	 * @date 2016年9月30日 上午9:53:43
	 * @author ying.cai
	 * @email ying.cai@ffzxnet.com
	 */
	List<GiftExportVo> findGiftListToExport(@Param("params")Map<String,Object>params);
	

	
	/**
	 * 根据主商品ID获取主商品信息（唯一）
	 * @param commodityId
	 * @return
	 */
	public ActivityGive selectByCommodityId(@Param("commodityId")String commodityId);
}