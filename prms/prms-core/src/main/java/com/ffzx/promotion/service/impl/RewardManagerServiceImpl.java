package com.ffzx.promotion.service.impl;

import com.ffzx.basedata.api.service.CodeRuleApiService;
import com.ffzx.commerce.framework.dao.CrudMapper;
import com.ffzx.commerce.framework.dto.ResultDto;
import com.ffzx.commerce.framework.enums.ServiceCode;
import com.ffzx.commerce.framework.enums.ServiceResultCode;
import com.ffzx.commerce.framework.exception.ServiceException;
import com.ffzx.commerce.framework.service.impl.BaseCrudServiceImpl;
import com.ffzx.commerce.framework.system.entity.SysUser;
import com.ffzx.commerce.framework.utils.DateUtil;
import com.ffzx.commerce.framework.utils.RedisWebUtils;
import com.ffzx.commerce.framework.utils.StringUtil;
import com.ffzx.commerce.framework.utils.UUIDGenerator;
import com.ffzx.promotion.api.dto.ActivityCommodity;
import com.ffzx.promotion.api.dto.RewardLuckNo;
import com.ffzx.promotion.api.dto.RewardManager;
import com.ffzx.promotion.api.dto.constant.PrmsConstant;
import com.ffzx.promotion.mapper.RewardManagerMapper;
import com.ffzx.promotion.model.OperateRecord;
import com.ffzx.promotion.service.OperateRecordService;
import com.ffzx.promotion.service.RewardManagerService;

import java.util.Date;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 
 * @author ffzx
 * @date 2016-10-24 17:25:15
 * @version 1.0.0
 * @copyright www.ffzxnet.com
 */
@Service("rewardManagerService")
public class RewardManagerServiceImpl extends BaseCrudServiceImpl implements RewardManagerService {

    @Resource
    private RewardManagerMapper rewardManagerMapper;
    @Autowired
    private CodeRuleApiService codeRuleApiService;
    @Autowired
    private OperateRecordService operateRecordService;
    @Override
    public CrudMapper init() {
        return rewardManagerMapper;
    }

	@Override
	@Transactional(rollbackFor=Exception.class)
	public Integer deleteReward(RewardManager reward,String type) throws ServiceException {
		int result = 0;
		SysUser user=RedisWebUtils.getLoginUser();
		this.saveOperateRecord(reward, user, type);
		result=this.rewardManagerMapper.updateByPrimaryKeySelective(reward);
		return result;
	}

	@Override
	@Transactional(rollbackFor=Exception.class)
	public ServiceCode saveReward(RewardManager reward) throws ServiceException {
		int result=0;
		try {
			SysUser user=RedisWebUtils.getLoginUser();
			String content="";
			if(StringUtils.isNotEmpty(reward.getId())){
				//编辑
				content=PrmsConstant.EDIT;
				reward.setCreateName(user.getName());
				reward.setLastUpdateBy(user);
				reward.setLastUpdateDate(new Date());
				getDate(reward);
				result=this.rewardManagerMapper.updateByPrimaryKeySelective(reward);
			}else{
				//新增
				content=PrmsConstant.ADD;
				reward.setId(UUIDGenerator.getUUID());
				ResultDto numberDto = codeRuleApiService.getCodeRule("prms", "prms_reward_code");
				reward.setRewardNo(numberDto.getData().toString());
				reward.setCreateBy(user);
				reward.setCreateDate(new Date());
				reward.setCreateName(user.getName());
				reward.setLastUpdateBy(user);
				reward.setLastUpdateDate(new Date());
				getDate(reward);
				result=this.rewardManagerMapper.insertSelective(reward);
			}
			//新增操作日志
			this.saveOperateRecord(reward, user,content);
		} catch (ServiceException e) {
			logger.error("免费抽奖新增/编辑", e);
			throw e;
		}
		return result > 0 ? ServiceResultCode.SUCCESS : ServiceResultCode.FAIL;
	}
	
	private void saveOperateRecord(RewardManager reward, SysUser user,String content) {
		OperateRecord operate=new OperateRecord();
		operate.setId(UUIDGenerator.getUUID());
		operate.setContent(content);
		operate.setRecordDate(new Date());
		operate.setCreateBy(user);
		operate.setRecordUser(user.getName());
		operate.setRewardId(reward.getId());
		this.operateRecordService.add(operate);
	}
	/**
	 * 转换时间
	 * @param reward
	 */
	private void getDate(RewardManager reward) {
		try {
			if(StringUtils.isNotEmpty(reward.getStartDateStartStr())){
				reward.setStartDate(DateUtil.parseTime(reward.getStartDateStartStr()));
			}
			if(StringUtils.isNotEmpty(reward.getEndDateStartStr())){
				reward.setEndDate(DateUtil.parseTime(reward.getEndDateStartStr()));
			}
			if(StringUtils.isNotEmpty(reward.getRewardDateStartStr())){
				reward.setRewardDate(DateUtil.parseTime(reward.getRewardDateStartStr()));
			}
		} catch (Exception e) {
			logger.error("免费抽奖时间转换", e);
			throw new ServiceException(e);
		}
	}

	@Override
	public Integer selectLuckNoCount() {
		
		return this.rewardManagerMapper.selectLuckNoCount();
	}
}