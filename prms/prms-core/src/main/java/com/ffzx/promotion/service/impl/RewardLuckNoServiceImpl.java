package com.ffzx.promotion.service.impl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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
import com.ffzx.commerce.framework.dto.ResultDto;
import com.ffzx.commerce.framework.enums.ServiceCode;
import com.ffzx.commerce.framework.enums.ServiceResultCode;
import com.ffzx.commerce.framework.exception.ServiceException;
import com.ffzx.commerce.framework.page.Page;
import com.ffzx.commerce.framework.service.impl.BaseCrudServiceImpl;
import com.ffzx.commerce.framework.system.entity.SysUser;
import com.ffzx.commerce.framework.utils.DateUtil;
import com.ffzx.commerce.framework.utils.RedisWebUtils;
import com.ffzx.commerce.framework.utils.UUIDGenerator;
import com.ffzx.promotion.api.dto.JoinRecord;
import com.ffzx.promotion.api.dto.RewardLuckNo;
import com.ffzx.promotion.api.dto.RewardManager;
import com.ffzx.promotion.api.dto.constant.PrmsConstant;
import com.ffzx.promotion.mapper.JoinRecordMapper;
import com.ffzx.promotion.mapper.RewardLuckNoMapper;
import com.ffzx.promotion.model.OperateRecord;
import com.ffzx.promotion.service.JoinRecordService;
import com.ffzx.promotion.service.OperateRecordService;
import com.ffzx.promotion.service.RewardLuckNoService;
import com.ffzx.promotion.service.RewardManagerService;

/**
 * 
 * @author ffzx
 * @date 2016-10-24 11:24:09
 * @version 1.0.0
 * @copyright www.ffzxnet.com
 */
@Service("rewardLuckNoService")
public class RewardLuckNoServiceImpl extends BaseCrudServiceImpl implements RewardLuckNoService {

    @Resource
    private RewardLuckNoMapper rewardLuckNoMapper;
    @Autowired
    private RewardManagerService rewardManagerService;
    @Autowired
    private JoinRecordService joinRecordService;
    @Autowired
    private OperateRecordService operateRecordService;

    @Override
    public CrudMapper init() {
        return rewardLuckNoMapper;
    }

	@Override
	public RewardLuckNo findLuckNoByRewardId(String id) {
		RewardLuckNo luck = null;
		//获取该次活动信息
		RewardManager reward=this.rewardManagerService.findById(id);
		//判断是否满足计算幸运号（活动已发布，已结束/等待开奖）
		boolean flag=this.isluckNo(reward);
		if(flag){
			luck=new RewardLuckNo();
			Map<String, Object>params=new HashMap<String, Object>();
			params.put("rewardId", id);
			if(StringUtils.isNotEmpty(id)){
				luck=this.rewardLuckNoMapper.selectluckNoByid(id);
				//验证是否开奖
				boolean shareFlag=this.isShareOrder(luck);
				if(shareFlag){
					//获取中奖人
					params.put("isBond", Constant.YES);
					List<JoinRecord> recordList=this.joinRecordService.findByBiz(params);
					luck.setRecord(this.getPhone(recordList));		
				}				
			}
			//不存在 表明还未计算幸运号
			if(luck==null){
				luck=new RewardLuckNo();
				//获取该次活动参与总人次			
				Integer count=this.joinRecordService.findCount(params);
				//获取最后50个参与人的时间之和
				//分页形式时间排序 获取最后50个参与人的时间集合
				Page pageObj = new Page(1, 50);
				List<JoinRecord> recordList=this.joinRecordService.findByPage(pageObj, Constant.ORDER_BY_FIELD_CREATE, Constant.ORDER_BY,params);
				Long sumTime=getSumTime(recordList);			
				luck.setTakeCount(count);
				luck.setRewardId(id);
				luck.setOneValue(sumTime==null?"":sumTime.toString());				
			}
			luck.setReward(reward);
		}		
		return luck;
	}
	
	/***
	 * 是否已经活动结束
	 * @param reward
	 * @return
	 */
	private boolean isluckNo(RewardManager reward){
		boolean flag=false;
		//必须是活动已发布，并且已经结束或者已开奖
		Date endDate=reward.getEndDate();//结束时间
		if(Constant.YES.equals(reward.getSendStaus()) && this.compare_date(endDate, new Date())==-1){
			 flag=true;
		}
		return flag;
	}
	
	 private  int compare_date(Date DATE1, Date DATE2) {
	            if (DATE1.getTime() > DATE2.getTime()) {
	                System.out.println("dt1 在dt2前");
	                return 1;
	            } else if (DATE1.getTime() < DATE2.getTime()) {
	                System.out.println("dt1在dt2后");
	                return -1;
	            } else {
	                return 0;
	            }
	    }
	
	/**
	 * 是否开奖
	 * @param reward
	 * @return
	 */
	private boolean isShareOrder(RewardLuckNo luck){
		boolean flag=false;
		//必须是活动已发布，已经开奖才可以设置晒单
		if(null!=luck && StringUtils.isNotEmpty(luck.getLuckNo())){
			 flag=true;
		}
		return flag;
	}
	
	private JoinRecord getPhone(List<JoinRecord> recordList){
		if(recordList!=null && recordList.size()!=0){
			return recordList.get(0);
		}
		return null;
	}
	
	private Long getSumTime(List<JoinRecord> recordList){
		Long count=0l;
		try {
			if(recordList!=null && recordList.size()!=0){			
				for(JoinRecord record:recordList){		
					Calendar cal = Calendar.getInstance();
					cal.setTime(record.getCreateDate());
					Integer hour = cal.get(Calendar.HOUR_OF_DAY);
					Integer minute = cal.get(Calendar.MINUTE);
					Integer second = cal.get(Calendar.SECOND);
					Integer mm = cal.get(Calendar.MILLISECOND);
					String sumTime=this.getTimeStr(hour)+this.getTimeStr(minute)+this.getTimeStr(second)+this.getmmStr(mm);
					count=count+(Integer.parseInt(sumTime));
				}
			}
		} catch (Exception e) {
			logger.error("计算50个时间值之和", e);
			throw new ServiceException(e);
		}
		return count;		
	}

	private String getTimeStr(Integer count){
		String countStr=null;
		if(count<10){
			countStr= "0"+count.toString();
		}else{
			countStr=count.toString();
		}
		return countStr;
	}
	
	private String getmmStr(Integer count){
		String countStr=null;
		if(count<10){
			countStr= "00"+count.toString();
		}else if(count<100){
			countStr= "0"+count.toString();
		}else{
			countStr=count.toString();
		}
		return countStr;
	}

	@Override
	@Transactional(rollbackFor=Exception.class)
	public ServiceCode saveRewardLuckNo(RewardLuckNo luckNo)throws ServiceException {
		ResultDto dto=null;
		int result=0;
		SysUser user=RedisWebUtils.getLoginUser();
		String oneValue=luckNo.getOneValue();//数字A
		String twoValue=luckNo.getTwoValue();//数值B
		Integer takeCount=luckNo.getTakeCount();//参与人次
		//计算获取幸运号
		Long no=this.getLuckNo(oneValue, twoValue, takeCount);
		try {			
			if(StringUtils.isNotEmpty(luckNo.getId())){
				luckNo.setLuckNo(no.toString());
				luckNo.setLastUpdateBy(user);
				luckNo.setLastUpdateDate(new Date());
				luckNo.setCreateName(user.getName());
				result=this.rewardLuckNoMapper.updateByPrimaryKeySelective(luckNo);
			}else{
				luckNo.setLuckNo(no.toString());
				luckNo.setCreateBy(user);
				luckNo.setCreateDate(new Date());
				luckNo.setCreateName(user.getName());
				luckNo.setLastUpdateBy(user);
				luckNo.setLastUpdateDate(new Date());
				luckNo.setId(UUIDGenerator.getUUID());
				result=this.rewardLuckNoMapper.insertSelective(luckNo);
			}		
			if(result>0){
				dto= new ResultDto(ResultDto.OK_CODE, "success");
				//幸运号已经生成去参与记录查询该次活动是否有人中奖
				Map<String, Object> params=new HashMap<String, Object>();
				params.put("rewardId", luckNo.getRewardId());
				params.put("ticket", no);
				List<JoinRecord> recordList=this.joinRecordService.findByBiz(params);
				JoinRecord record=this.getLuckName(recordList);
				if(record!=null){
					luckNo.setRecord(record);
					//更新该记录为中奖状态
					record.setIsBond(PrmsConstant.BONDED);
					this.joinRecordService.modifyById(record);
				}
				dto.setData(luckNo);
				//新增操作记录
				saveOperateRecord(luckNo, user);
			}
		} catch (ServiceException e) {
			logger.error("计算幸运号", e);
			throw e;
		}		
		return result > 0 ? ServiceResultCode.SUCCESS : ServiceResultCode.FAIL;
	}

	private void saveOperateRecord(RewardLuckNo luckNo, SysUser user) {
		OperateRecord operate=new OperateRecord();
		operate.setId(UUIDGenerator.getUUID());
		operate.setContent(PrmsConstant.LUCKNO);
		operate.setRecordDate(new Date());
		operate.setCreateBy(user);
		operate.setRecordUser(user.getName());
		operate.setRewardId(luckNo.getRewardId());
		this.operateRecordService.add(operate);
	}
	
	
	private JoinRecord getLuckName(List<JoinRecord> recordList){
		if(null != recordList && recordList.size()!=0){
			return recordList.get(0);
		}
		return null;
	}
	
	/**
	 * 计算获取幸运号
	 * @param oneValue
	 * @param twoValue
	 * @param takeCount
	 * @return
	 */
	private Long getLuckNo(String oneValue,String twoValue,Integer takeCount){
		Long luckNo=0l;
		if(StringUtils.isNotEmpty(oneValue) && StringUtils.isNotEmpty(twoValue) && takeCount>0){
			luckNo=(Long.parseLong(oneValue)+Long.parseLong(twoValue))%Long.valueOf(takeCount)+10000001L;
		}
		return luckNo;
	}
	
	public static void main(String[] args) {
		Long 	luckNo=(Long.parseLong("17271904240")+Long.parseLong("05432"))%Long.valueOf(678761)+10000001L;
		System.out.println(luckNo);
	}
}