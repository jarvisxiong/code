package com.ffzx.promotion.api.service;

import java.util.Date;
import java.util.List;

import com.ffzx.commerce.framework.dto.ResultDto;
import com.ffzx.commerce.framework.enums.ServiceCode;
import com.ffzx.commerce.framework.exception.ServiceException;
import com.ffzx.commerce.framework.page.Page;
import com.ffzx.commerce.framework.service.BaseCrudService;
import com.ffzx.promotion.api.dto.ActivityCommoditySku;
import com.ffzx.promotion.api.dto.ActivityManager;
import com.ffzx.promotion.api.enums.ActivityTypeEnum;

/**
 * ActivityCommditySkuApi数据库操作接口
 * 
 * @author yuzhao.xu
 * @email  yuzhao.xu@ffzxnet.com
 * @date 2016年5月11日 下午5:25:32
 */
public interface ActivityCommoditySkuApiService extends BaseCrudService {
	
	/**
	 * 
	 * @param managerid  活动管理Activitymanager id
	 * @param skuid  商品sku表id
	 * @param stateenddate   下单时间判断活动范围时间
	 * @return  ActivityCommditySku
	 */
	public  ResultDto getActivityCommoditySku(String managerid,String skuid,Date stateenddate);

	/**
	 * 预售抢购商品信息列表获取
	 * @param type 获取类型
	 * @param uid 会员id
	 * @param page 页码
	 * @param pageSize 每页记录数
	 * @param cid 类目ID
	 * @return
	 * @throws Exception
	 */
	public ResultDto getCommoditySkuList(String uid, String cid, String cityId, int page, int pageSize, ActivityTypeEnum type);
	
	/**
	 * 获取商品批发价格
	 * @param commodityNo  商品编号
	 * @param num  批发数量
	 * @return   优惠价格
	 */
	public ResultDto getPifaPrice(String commodityNo,Integer num);

	/**
	 * 获取商品批发数据
	 * @param commodityNo  商品编号
	 * @param num  批发数量
	 * @return   优惠价格
	 */
	public ResultDto getPifaDate(String commodityNo);
	
	/**
	 * 根据添加获取所有活动sku数据
	 * @param activityIdList 活动id  skuId集合
	 * @return
	 */
	public ResultDto selectActivitySkuPrice(List<Object> activityIdList);
	
	/***
	 * 根据活动ID获得该活动下的所有sku子活动的数据
	 * @param activityId 活动ID
	 * @param commoditySkuIds 商品skuId集合
	 * @param commodityCode 商品编码
	 * @return
	 * data = List<ActivityCommoditySku>
	 * @date 2016年6月16日 下午4:19:42
	 * @author ying.cai
	 * @email ying.cai@ffzxnet.com
	 */
	public ResultDto findActivitySkuByActivityId(String activityId,String commoditySkuIds,String commodityCode);
	
}