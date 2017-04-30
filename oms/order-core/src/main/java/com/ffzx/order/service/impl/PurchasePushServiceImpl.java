package com.ffzx.order.service.impl;

import com.ffzx.commerce.framework.dao.CrudMapper;
import com.ffzx.commerce.framework.service.impl.BaseCrudServiceImpl;
import com.ffzx.order.mapper.PurchasePushMapper;
import com.ffzx.order.model.PurchasePush;
import com.ffzx.order.service.PurchasePushService;

import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * 
 * @author ffzx
 * @date 2016-06-08 17:29:40
 * @version 1.0.0
 * @copyright www.ffzxnet.com
 */
@Service("purchasePushService")
public class PurchasePushServiceImpl extends BaseCrudServiceImpl implements PurchasePushService {

    @Resource
    private PurchasePushMapper purchasePushMapper;

    @Override
    public CrudMapper init() {
        return purchasePushMapper;
    }

	@Override
	public Date selectMaxPushDate(Map<String, Object> params) {
		// TODO Auto-generated method stub
		PurchasePush purchasePush = purchasePushMapper.selectMaxPushDate(params);
		if(purchasePush!=null){
			return purchasePush.getPushDate();
		}
		return null;
	}
}