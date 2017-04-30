package com.ffzx.order.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ffzx.commerce.framework.dao.CrudMapper;
import com.ffzx.commerce.framework.exception.ServiceException;
import com.ffzx.commerce.framework.page.Page;
import com.ffzx.commerce.framework.service.impl.BaseCrudServiceImpl;
import com.ffzx.commerce.framework.utils.RedisUtil;
import com.ffzx.order.api.dto.Commodity;
import com.ffzx.order.mapper.CommodityMapper;
import com.ffzx.order.service.CommodityService;
import com.ffzx.order.utils.Master;
import com.ffzx.order.utils.OmsConstant;

/**
 * 
 * @author ffzx
 * @date 2016-05-13 09:34:39
 * @version 1.0.0
 * @copyright www.ffzxnet.com
 */
@Service("commodityService")
public class CommodityServiceImpl extends BaseCrudServiceImpl implements CommodityService {
	@Resource
	private RedisUtil redisUtil;
    @Resource
    private CommodityMapper commodityMapper;

    @Override
    public CrudMapper init() {
        return commodityMapper;
    }

	@Override
	public Commodity getByCode(String code) throws ServiceException {
		// TODO Auto-generated method stub
		return commodityMapper.selectByCode(code);
	}
	@Override
	@Transactional(rollbackFor=Exception.class)
	public Commodity getCommodityByCodeFromRedis(String code) {
		// TODO Auto-generated method stub
		String key=OmsConstant.REDIS_PREFIX+code;
		Commodity data = null;
		if(redisUtil.exists(key)){
			data = (Commodity) redisUtil.get(key);
		}else{
			data = this.getByCode(code);
			if(data!=null)
			{
				redisUtil.set(key, data,OmsConstant.REDIS_EXPIRETIME_30MIN);
			}
		}
		return data;
	}
	
	@Override
	@Master
	public Commodity getCommoditybyskuCode(String skuCode) throws ServiceException {
		return  commodityMapper.getCommoditybyskuCode(skuCode);
	}
	
	@Override
	public Commodity findByBarCode(String barCode) throws ServiceException {
		return commodityMapper.selectByBarCode(barCode);
	}

	/** 
	 * @param params
	 * @return 查找自定义非区域商品在自定义库存表没有存在的商品记录
	 * @author wg  
	 * @time 2016年5月21日 下午6:06:10 
	 */
	@Override
	public List<Commodity> findCommodityStock(Map<String, Object> map) {
		return commodityMapper.findCommodityStock(map);
	}

	/** 
	 * @param params
	 * @return 查找自定义非区域商品在自定义库存表没有存在的商品总数
	 * @author wg  
	 * @time 2016年5月21日 下午6:06:10 
	 */
	@Override
	public int countCommodityStock(Map<String, Object> params) {
		return commodityMapper.countCommodityStock(params);
	}
	
	@Override
	public List<Commodity> findByPageBySwitch(Page page, String orderByField, String orderBy,Map<String, Object> params) throws ServiceException {
		return commodityMapper.findByPageBySwitch(page, orderByField, orderBy, params);
	}
}