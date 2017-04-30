package com.ffzx.promotion.service;

import com.ffzx.commerce.framework.enums.ServiceCode;
import com.ffzx.commerce.framework.exception.ServiceException;
import com.ffzx.commerce.framework.service.BaseCrudService;
import com.ffzx.promotion.api.dto.RewardManager;

/**
 * reward_manager数据库操作接口
 * 
 * @author ffzx
 * @date 2016-10-24 17:25:15
 * @version 1.0.0
 * @copyright www.ffzxnet.com
 */
public interface RewardManagerService extends BaseCrudService {

	/***
	 * 删除/发布/撤销免费抽奖活动
	 * @param reward
	 * @return
	 */
	public Integer deleteReward(RewardManager reward,String type)throws ServiceException;
	
	/**
	 * 查询等待开奖的未计算幸运号的活动数量
	 * @return
	 */
	public Integer selectLuckNoCount();
	
	/***
	 * 添加/编辑 免费抽奖活动
	 * @param reward
	 * @return
	 * @throws ServiceException
	 */
	public ServiceCode saveReward(RewardManager reward)throws ServiceException;
}