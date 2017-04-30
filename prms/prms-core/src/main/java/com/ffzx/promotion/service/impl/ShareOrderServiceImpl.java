package com.ffzx.promotion.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ffzx.commerce.framework.constant.Constant;
import com.ffzx.commerce.framework.dao.CrudMapper;
import com.ffzx.commerce.framework.enums.ServiceCode;
import com.ffzx.commerce.framework.enums.ServiceResultCode;
import com.ffzx.commerce.framework.exception.ServiceException;
import com.ffzx.commerce.framework.service.impl.BaseCrudServiceImpl;
import com.ffzx.commerce.framework.system.entity.SysUser;
import com.ffzx.commerce.framework.utils.RedisWebUtils;
import com.ffzx.commerce.framework.utils.UUIDGenerator;
import com.ffzx.promotion.api.dto.JoinRecord;
import com.ffzx.promotion.api.dto.RewardManager;
import com.ffzx.promotion.api.dto.ShareOrder;
import com.ffzx.promotion.api.dto.constant.PrmsConstant;
import com.ffzx.promotion.mapper.ShareOrderMapper;
import com.ffzx.promotion.model.OperateRecord;
import com.ffzx.promotion.service.JoinRecordService;
import com.ffzx.promotion.service.OperateRecordService;
import com.ffzx.promotion.service.RewardManagerService;
import com.ffzx.promotion.service.ShareOrderService;

/**
 * 
 * @author ffzx
 * @date 2016-10-24 11:24:09
 * @version 1.0.0
 * @copyright www.ffzxnet.com
 */
@Service("shareOrderService")
public class ShareOrderServiceImpl extends BaseCrudServiceImpl implements ShareOrderService {

    @Resource
    private ShareOrderMapper shareOrderMapper;
    @Autowired
    private RewardManagerService rewardManagerService;
    @Autowired
    private JoinRecordService joinRecordService;
    @Autowired
    private OperateRecordService operateRecordService;
    @Override
    public CrudMapper init() {
        return shareOrderMapper;
    }

	@Override
	public ShareOrder findShareOrderById(String id) {
		ShareOrder order=null;
		//获取该次活动信息
		RewardManager reward=this.rewardManagerService.findById(id);
		//判断是否满足设置晒单条件（活动已发布，已经开奖）
		boolean flag=this.isShareOrder(reward);
		if(flag){
			if(StringUtils.isNotEmpty(id)){
				//编辑晒单所需数据
				order=this.shareOrderMapper.selectByRewardId(id);
			}
			//第一次进入设置晒单
			if(order==null){				
				order=new ShareOrder();
				order.setRewardDateNo(reward.getDateNo());
				order.setRewardId(reward.getId());
				//获取该次活动中奖记录
				Map<String, Object>params=new HashMap<String, Object>();
				params.put("rewardId", id);
				params.put("isBond", Constant.YES);
				//获取中奖人
				List<JoinRecord> recordList=this.joinRecordService.findByBiz(params);
				order.setLuckName(this.getPhone(recordList));	
			}
		}		
		return order;
	}
	
	private boolean isShareOrder(RewardManager reward){
		boolean flag=false;
		//必须是活动已发布，已经开奖才可以设置晒单
		if(Constant.YES.equals(reward.getSendStaus()) && Constant.REC_END.equals(reward.getRewardStatus())){
			 flag=true;
		}
		return flag;
	}
	
	private String getPhone(List<JoinRecord> recordList){
		if(recordList!=null && recordList.size()!=0){
			return recordList.get(0).getPhone();
		}
		return null;
	}

	@Override
	@Transactional(rollbackFor=Exception.class)
	public ServiceCode saveShareOrder(ShareOrder share) {
		int result=0;
		try {
			SysUser user=RedisWebUtils.getLoginUser();
			if(StringUtils.isNotEmpty(share.getId())){
				//编辑
				share.setCreateName(user.getName());
				share.setLastUpdateBy(user);
				share.setLastUpdateDate(new Date());
				result=this.shareOrderMapper.updateByPrimaryKeySelective(share);
			}else{
				//新增
				share.setId(UUIDGenerator.getUUID());
				share.setCreateBy(user);
				share.setCreateDate(new Date());
				share.setCreateName(user.getName());
				share.setLastUpdateBy(user);
				share.setLastUpdateDate(new Date());
				result=this.shareOrderMapper.insertSelective(share);
			}
			this.saveOperateRecord(share, user);
		} catch (ServiceException e) {
			logger.error("设置晒单", e);
			throw e;
		}
		return result > 0 ? ServiceResultCode.SUCCESS : ServiceResultCode.FAIL;
	}
	
	private void saveOperateRecord(ShareOrder share, SysUser user) {
		OperateRecord operate=new OperateRecord();
		operate.setId(UUIDGenerator.getUUID());
		operate.setContent(PrmsConstant.SHAREORDER);
		operate.setRecordDate(new Date());
		operate.setCreateBy(user);
		operate.setRecordUser(user.getName());
		operate.setRewardId(share.getRewardId());
		this.operateRecordService.add(operate);
	}
}