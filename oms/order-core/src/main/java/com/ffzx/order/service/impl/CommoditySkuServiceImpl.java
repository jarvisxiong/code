package com.ffzx.order.service.impl;

import com.ffzx.commerce.framework.dao.CrudMapper;
import com.ffzx.commerce.framework.exception.ServiceException;
import com.ffzx.commerce.framework.service.impl.BaseCrudServiceImpl;
import com.ffzx.commerce.framework.utils.RedisUtil;
import com.ffzx.order.api.dto.CommoditySku;
import com.ffzx.order.mapper.CommoditySkuMapper;
import com.ffzx.order.service.CommoditySkuService;
import com.ffzx.order.utils.OmsConstant;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 
 * @author ffzx
 * @date 2016-05-13 09:34:39
 * @version 1.0.0
 * @copyright www.ffzxnet.com
 */
@Service("commoditySkuService")
public class CommoditySkuServiceImpl extends BaseCrudServiceImpl implements CommoditySkuService {
	@Resource
	private RedisUtil redisUtil;
    @Resource
    private CommoditySkuMapper commoditySkuMapper;

    @Override
    public CrudMapper init() {
        return commoditySkuMapper;
    }

	@Override
	public List<CommoditySku> getCommoditySkuByCode(String commodityCode) throws ServiceException {
		return commoditySkuMapper.getCommoditySkuByCode(commodityCode);
	}

	@Override
	public List<CommoditySku> queryByJoins(Map<String, Object> params) throws Exception {
		// TODO Auto-generated method stub
		return commoditySkuMapper.selectWhitjoinByParams(params);
	}

	@Override
	@Transactional(rollbackFor=Exception.class)
	public CommoditySku getCommoditySkuByIdFromRedis(String skuId) {
		// TODO Auto-generated method stub
		String key=OmsConstant.REDIS_PREFIX+skuId;
		CommoditySku data = null;
		if(redisUtil.exists(key)){
			data = (CommoditySku) redisUtil.get(key);
		}else{
			data = this.findById(skuId);
			if(data!=null)
			{
				redisUtil.set(key, data,OmsConstant.REDIS_EXPIRETIME_30MIN);//单位秒}
			}
			
		}
		return data;
	}

	@Override
	public CommoditySku findBySkuCode(String skuCode) throws ServiceException {
		return commoditySkuMapper.findBySkuCode(skuCode);
	}

	@Override
	public CommoditySku findByBarCode(String barCode) throws ServiceException {
		return commoditySkuMapper.findByBarCode(barCode);
	}

	@Override
	public List<CommoditySku> getCommoditySkuByParams(Map<String, Object> params) throws ServiceException {
		return commoditySkuMapper.getCommoditySkuByParams(params);
	}
}