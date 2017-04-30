package com.ffzx.promotion.api.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
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
import com.ffzx.commerce.framework.exception.ServiceException;
import com.ffzx.commerce.framework.page.Page;
import com.ffzx.commerce.framework.service.impl.BaseCrudServiceImpl;
import com.ffzx.commerce.framework.utils.RedisLock;
import com.ffzx.commerce.framework.utils.RedisUtil;
import com.ffzx.commerce.framework.utils.UUIDGenerator;
import com.ffzx.member.api.dto.Member;
import com.ffzx.member.api.service.MemberApiService;
import com.ffzx.promotion.api.dto.ChoiceShareOrder;
import com.ffzx.promotion.api.dto.JoinRecord;
import com.ffzx.promotion.api.dto.RewardLuckNo;
import com.ffzx.promotion.api.dto.RewardManager;
import com.ffzx.promotion.api.dto.ShareDraw;
import com.ffzx.promotion.api.dto.ShareOrder;
import com.ffzx.promotion.api.dto.apiModel.PageValueApi;
import com.ffzx.promotion.api.dto.constant.PrmsConstant;
import com.ffzx.promotion.api.service.RewardManagerApiService;
import com.ffzx.promotion.mapper.RewardLuckNoMapper;
import com.ffzx.promotion.mapper.RewardManagerMapper;
import com.ffzx.promotion.service.ChoiceShareOrderService;
import com.ffzx.promotion.service.JoinRecordService;
import com.ffzx.promotion.service.ShareDrawService;
import com.ffzx.promotion.service.ShareOrderService;


/*******
 * 免费抽奖API接口
 * @author lushi.guo
 *
 */

@Service("rewardManagerApiService")
public class RewardManagerApiServiceImpl extends BaseCrudServiceImpl implements RewardManagerApiService  {
	 @Resource
	 private RewardManagerMapper  rewardManagerMapper;
	 @Autowired
	 private JoinRecordService joinRecordService;
	 @Autowired
	 private MemberApiService memberApiService;
	 @Autowired
	 private ShareDrawService shareDrawService;
	 @Autowired
	 private ChoiceShareOrderService choiceShareOrderService;
	 @Autowired
	 private ShareOrderService shareOrderService;
	 @Autowired
	 private RewardLuckNoMapper rewardLuckNoMapper;
	 @Autowired
	 private RedisUtil redis;
	 
	@Override
	public CrudMapper init() {
		
		return rewardManagerMapper;
	}

	@Override
	public ResultDto getRewardMangerList(Page page,Map<String, Object> params) {
		ResultDto rsDto = null;
		try {
			rsDto = new ResultDto(ResultDto.OK_CODE, "success");
			//根据参数获取免费抽奖活动列表
			params.put(Constant.DELTE_FLAG, Constant.DELTE_FLAG_NO);
			params.put("sendStaus", Constant.DELTE_FLAG_YES);
			Integer totalCount=this.rewardManagerMapper.selectCount(params);
			params.put("pageIndex", (page.getPageIndex()-1)*page.getPageSize());
			params.put("pageSize", page.getPageSize());
			List<RewardManager> rewardList=this.rewardManagerMapper.selectPageByOrderBy(params);
			//验证是否存在活动列表
			boolean listFlag=this.isList(rewardList);
			PageValueApi api=new PageValueApi();
			page.setTotalCount(totalCount);
			api.setPage(page);
			if(listFlag){
				//存在返回数据				
				this.setTakeCount(rewardList);	
				api.setList(rewardList);			
			}
			rsDto.setData(api);
		} catch (Exception e) {
			logger.error("获取免费抽奖列表接口", e);
			throw new ServiceException(e);
		}
		
		return rsDto;
	}
	

	@Override
	public ResultDto getRewardManagerById(String id,String memberId) {
		
		ResultDto rsDto = null;
		try {
			rsDto = new ResultDto(ResultDto.OK_CODE, "success");
			//根据ID获取活动详情
			if(StringUtils.isNotEmpty(id)){
				RewardManager reward=getRewardManager(id);
				if(null!=reward){
					reward.setTakeCount(this.getDrawCount(id));
					//是否登录
					if(StringUtils.isNotEmpty(memberId)){
						//是否抽奖，是否分享再抽奖
						ShareDraw draw=this.getShareDrawList(id, memberId);
						if(null!=draw){
							String isShare=draw.getIsShare();//是否分享
							Integer count=draw.getCount();//剩余可抽奖次数
							boolean countFlag=this.isInteger(count);
							if(countFlag){
								reward.setIsDraw(Constant.YES);
							}			
							reward.setDrawCount(count);
							reward.setIsShare(isShare);
							//已登录获取抽奖码						
							reward.setDrawNo(getLuckTicket(id, memberId));
						}else{
							reward.setDrawCount(1);
							reward.setIsShare(PrmsConstant.NOBOND);
						}												
						String rewardStatus=reward.getRewardStatus();
						//状态已开奖获取幸运号和中奖人
						detailGetLuckNo(id, reward, rewardStatus);
					}
					rsDto.setData(reward);
				}
			}
		} catch (Exception e) {
			logger.error("获取免费抽奖详情接口", e);
			throw new ServiceException(e);
		}
		
		return rsDto;
	}

	/**
	 * 详情封装幸运号和中奖人
	 * @param id
	 * @param reward
	 * @param rewardStatus
	 */
	private void detailGetLuckNo(String id, RewardManager reward, String rewardStatus) {
		if(PrmsConstant.sortdatejiesu.equals(rewardStatus)){
			List<JoinRecord> listrecord=this.getJoinRecordList(id, null);
			boolean flag=this.isList(listrecord);
			if(flag){
				for(JoinRecord record:listrecord){
					String isBond=record.getIsBond();//是否中奖
					if(PrmsConstant.BONDED.equals(isBond)){
						reward.setLuckNo(record.getTicket());
						reward.setLuckName(record.getPhone());
					}
				}
			}
		}
	}

	/***
	 * 公共方法获取抽奖记录
	 * @param id
	 * @param memberId
	 * @return
	 */
	private List<JoinRecord> getJoinRecordList(String id, String memberId) {
		Map<String, Object> params=new HashMap<String, Object>();
		params.put("rewardId", id);
		params.put("memberId", memberId);
		List<JoinRecord> recordList=this.joinRecordService.findByBiz(params);						
		return recordList;
		
	}
	
	/**
	 * 详情封装某人抽奖码
	 * @param id
	 * @param memberId
	 * @return
	 */
	private List<String> getLuckTicket(String id, String memberId) {
	
		List<JoinRecord> recordList=this.getJoinRecordList(id, memberId);		
		boolean recordFlag=this.isList(recordList);
		List<String> drawTicket=null;
		if(recordFlag){
			drawTicket=new ArrayList<String>();
			for(JoinRecord record:recordList){
				drawTicket.add(record.getTicket());
			}			
		}
		return drawTicket;
	}

	/**
	 * 验证 integer
	 * @param value
	 * @return
	 */
	private boolean isInteger(Integer value){
		boolean flag=false;
		if(value!=null && value!=0){
			flag=true;
		}
		return flag;
	}
	
	
	/***
	 * 参数获取 分享抽奖信息
	 * @param rewardId
	 * @param memberId
	 * @return
	 */
	private ShareDraw getShareDrawList(String rewardId,String memberId){
		Map<String, Object> params=new HashMap<String, Object>();
		params.put("rewardId", rewardId);
		params.put("memberId", memberId);
		List<ShareDraw> drawList=this.shareDrawService.findByBiz(params);
		boolean drawflag=this.isList(drawList);
		if(drawflag){
			return drawList.get(0);
		}
		return null;
	}
	
	/**
	 * 列表参与人次赋值
	 * @param rewardList
	 */
	private void setTakeCount(List<RewardManager> rewardList){
		boolean listFlag=this.isList(rewardList);
		if(listFlag){
			for(RewardManager reward:rewardList){
				reward.setTakeCount(this.getDrawCount(reward.getId()));
			}
		}
	}
	
	
	/**
	 * 缓存获取某个活动参与人次
	 * @param rewardId
	 * @return
	 */
	private Integer getDrawCount(String rewardId){
		//缓存获取参与人次数
		Integer drawCount=0;
		if(StringUtils.isNotEmpty(rewardId)){
			String key=PrmsConstant.getDrawCount(rewardId);			
			Object  count=redis.incGet(key);
			if(null!=count){
				drawCount=Integer.parseInt(count.toString());
			}else{
				//缓存清空sql查询				
				drawCount=this.getJoinRecordCount(rewardId);
			}
		}		
		return drawCount;
	}

	
	/**
	 * 获取免费抽奖活动详情
	 * @param rewardId
	 * @return
	 */
	private RewardManager getRewardManager(String rewardId){
		if(StringUtils.isNotEmpty(rewardId)){
			RewardManager reward=this.rewardManagerMapper.selectByPrimaryKey(rewardId);
			return reward;
		}
		return null;
	}
	
	/**
	 * 数据库获取某个活动参与人次
	 * @param rewardId
	 * @return
	 */
	private Integer getJoinRecordCount(String rewardId){
		Integer drawCount=0;
		if(StringUtils.isNotEmpty(rewardId)){
			Map<String, Object> params=new HashMap<String, Object>();
			params.put("rewardId", rewardId);
			drawCount=this.joinRecordService.findCount(params);
		}
		return drawCount;
	}
	
	@Override
	@Transactional(rollbackFor=Exception.class)
	public ResultDto shareReward(ShareDraw draw) {
		ResultDto resDto = null;
		try {
			resDto = new ResultDto(ResultDto.OK_CODE, "success");	
			String memberId=draw.getMemberId();
			String rewardId=draw.getRewardId();
			ShareDraw share=this.getShareDrawList(rewardId, memberId);
			if(null!=share){
			//存在说明已经抽过奖更新是否分享状态，次数加1
				
				share.setCount(share.getCount()+1);
				share.setIsShare(Constant.YES);
				share.setUpdateDate(new Date());
				this.shareDrawService.modifyById(share);
			}else{
				//反之说明还未抽奖 插入一条分享记录 有2次可以抽奖
				draw.setId(UUIDGenerator.getUUID());
				draw.setCount(2);
				draw.setUpdateDate(new Date());
				draw.setIsShare(Constant.YES);
				draw.setIsDraw(Constant.NO);
				RewardManager reward=this.getRewardManager(draw.getRewardId());
				if(null!=reward){
					draw.setRewardNo(reward.getRewardNo());
				}
				this.shareDrawService.add(draw);
			}
		} catch (Exception e) {
			logger.error("分享接口", e);
			throw new ServiceException(e);
		}
		return resDto;
	}

	/**
	 * 验证当前用户是否已经超过抽奖次数
	 * @param recordList
	 * @return
	 */
	private boolean isgoDraw(List<JoinRecord> recordList){
		boolean flag=true;
		if(this.isList(recordList)){
			if(recordList.size()>=2){
				flag=false;
			}
		}
		return flag;
	}
	
	@Override
	@Transactional(rollbackFor=Exception.class)
	public ResultDto joinRewardManager(JoinRecord record) {		
		ResultDto resDto = null;
		List<Map<String, Integer>> redisSuccessMap = new ArrayList<Map<String, Integer>>();
		try {					
			//查询该用户是否已经抽过奖并且是否超过2次
			List<JoinRecord> recordList=this.getJoinRecordList(record.getRewardId(), record.getMemberId());
			if(this.isgoDraw(recordList)){
				resDto = new ResultDto(ResultDto.OK_CODE, "success");	
				logger.info("开始计算幸运号码" + record.getMemberId());
				resDto = drawTicket(record, redisSuccessMap);
				if (null == resDto || !resDto.getCode().equals(ResultDto.OK_CODE)) {
					logger.warn("failed when checking id and sku limit. the reason is " + resDto);
					// 验证失败回滚缓存
					boolean mapFlag = this.isList(redisSuccessMap);
					if (mapFlag) {
						redisDecrease(redisSuccessMap);
					}
				}			
				record=(JoinRecord) resDto.getData();
				logger.info("获取用户信息" + record.getMemberId());
				ResultDto m=this.memberApiService.getByIdMember(record.getMemberId());
				if(ResultDto.OK_CODE.equals(m.getCode()) && null !=m.getData()){
					Member member=(Member) m.getData();
					//获取头像
					record.setHeadImage(member.getPictureUrl());
					//获取会员账号
					record.setPhone(member.getAccountNo());
				}			
				//获取活动信息
				RewardManager reward=this.getRewardManager(record.getRewardId());
				record.setId(UUIDGenerator.getUUID());
				record.setRewardNo(reward.getRewardNo());
				record.setCreateDate(this.getSSSDate());		
				logger.info("开始持久化参与记录" + record.getMemberId());
				this.joinRecordService.add(record);
				logger.info("开始持久化分享记录" + record.getMemberId());
				//是否分享过
				ShareDraw draw=this.getShareDrawList(record.getRewardId(), record.getMemberId());			
				if(null!=draw){
					draw.setCount(draw.getCount()-1);
					draw.setIsShare(PrmsConstant.BONDED);//分享过
					this.shareDrawService.modifyById(draw);
				}else{
					draw=getShareDraw(record, reward, draw);
					this.shareDrawService.add(draw);
				}
			}else{
				resDto = new ResultDto();	
				resDto.setCode(ResultDto.BUSINESS_ERROR_CODE);
				resDto.setMessage(PrmsConstant.GOLUCK);
			}			
		} catch (Exception e) {
			logger.error("免费抽奖接口", e);
			// 验证失败回滚缓存
			resDto = new ResultDto();	
			resDto.setCode(ResultDto.BUSINESS_ERROR_CODE);
			resDto.setMessage(PrmsConstant.GOLUCK);
			boolean mapFlag = this.isList(redisSuccessMap);
			if (mapFlag) {
				redisDecrease(redisSuccessMap);
			}
			throw new ServiceException(e);
		}		
		return resDto;
	}

	private ShareDraw getShareDraw(JoinRecord record, RewardManager reward, ShareDraw draw) {
		draw=new ShareDraw();
		draw.setId(UUIDGenerator.getUUID());
		draw.setCount(0);
		draw.setIsDraw(Constant.YES);
		draw.setUpdateDate(new Date());
		draw.setMemberId(record.getMemberId());
		draw.setRewardId(record.getRewardId());
		draw.setRewardNo(reward.getRewardNo());
		return draw;
	}

	
	/***
	 * 回滚异常缓存数据
	 * @param redisSuccessMap
	 */
	private void redisDecrease(List<Map<String, Integer>> redisSuccessMap) {
		for (Map<String, Integer> redisValue : redisSuccessMap) {
			Iterator<String> it = redisValue.keySet().iterator();
			while (it.hasNext()) {
				logger.error("success get redisValue!");
				Object key = it.next();
				redis.decrease(String.valueOf(key), redisValue.get(key).longValue());
			}
		}
	}
	
	private ResultDto drawTicket(JoinRecord record,List<Map<String, Integer>> redisSuccessMap){
		ResultDto resDto = new ResultDto(ResultDto.OK_CODE, "success");	
		resDto =getTicket(record);
		if (null == resDto || !resDto.getCode().equals(ResultDto.OK_CODE)) {
			logger.warn("failed when checking id and sku limit. the reason is " + resDto);
			return resDto;
		}else{
			// 赠品验证成功存入集合留待回滚缓存
			getSuccessMap(redisSuccessMap, PrmsConstant.getDrawCount(record.getRewardId()));
		}
		
		return resDto;
	}
	
	private void getSuccessMap(List<Map<String, Integer>> redisSuccessMap, String key) {
		Map<String, Integer> redisMap = new HashMap<String, Integer>();
			// 如果验证成功把商品已经购买数量和当前购买量以map存入集合
			redisMap.put(key, 1);			
			redisSuccessMap.add(redisMap);		
	}
	
	/**
	 * 并发计算幸运号码
	 * @param record
	 * @return
	 */
	private ResultDto getTicket(JoinRecord record){
		ResultDto resDto = new ResultDto(ResultDto.OK_CODE, "");
		if (null!=record) {
			String lockKey = PrmsConstant.getLockKey(record.getRewardId());
			String transactionProcessingVarName = lockKey.concat("_processing");
			RedisLock jedisLock = new RedisLock(redis, lockKey, 5000, 60000, 10);
			try {
				if (jedisLock.acquire() && redis.setNX(transactionProcessingVarName, "0")) {
					jedisLock.setParentHashCode(1);
					logger.info("缓存获取该活动参与人次"+record.getRewardId());
					Integer drawCount=this.getDrawCount(record.getRewardId());
					logger.info("缓存获取该活动参与人次"+record.getRewardId()+"："+drawCount);
					if(null!=drawCount){
						logger.info(record.getMemberId()+"计算获取幸运号码"+record.getRewardId());
						Integer ticket=drawCount+1+PrmsConstant.defaultValue;
						record.setTicket(ticket.toString());
						logger.info(record.getMemberId()+"计算获取幸运号码"+record.getRewardId()+"："+ticket);
					}
					// 增加该活动参与人次数
					String key = PrmsConstant.getDrawCount(record.getRewardId());
					redis.increment(key, 1);
					redis.remove(transactionProcessingVarName);
					jedisLock.release();
				} else {
					logger.error("lock time out");
					resDto.setCode(ResultDto.BUSINESS_ERROR_CODE);
					resDto.setMessage(PrmsConstant.ERROR);
				}
			} catch (Exception e) {
				resDto.setCode(ResultDto.BUSINESS_ERROR_CODE);
				resDto.setMessage(PrmsConstant.ERROR);
				// 异常时当锁对象的父hashCode不为空时，则释放业务执行标识。
				deleteReisLock(transactionProcessingVarName, jedisLock);
				// 异常解锁
				releaseLock(jedisLock,PrmsConstant.EXCEPTIONLOCK);
			} finally {
				// 超时解锁。
				releaseLock(jedisLock,PrmsConstant.CHAOSHILOCK);
			}
		}
		resDto.setData(record);
		return resDto;
	}

	private void releaseLock(RedisLock jedisLock,String value) {
		if (jedisLock != null && jedisLock.isLocked()) {
			jedisLock.release();
			logger.info(value);
		}
	}
	
	private void deleteReisLock(String transactionProcessingVarName, RedisLock jedisLock) {
		if (redis != null && jedisLock.getParentHashCode() != 0) {
			redis.remove(transactionProcessingVarName);
		}
	}
	/**
	 * 获取毫秒级别时间
	 * @return
	 */
	private Date getSSSDate()throws Exception{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");	
		return sdf.parse(sdf.format(new Date()));
	}
	
	@Override
	public ResultDto getJoinRecordList(Page page, String rewardId) {
		ResultDto resDto = null;
		try {
			resDto = new ResultDto(ResultDto.OK_CODE, "success");	
			if(StringUtils.isNotEmpty(rewardId)){
				//获取活动信息
				RewardManager reward=this.getRewardManager(rewardId);
				Map<String, Object> params=new HashMap<String, Object>();
				params.put("rewardId", rewardId);
				List<JoinRecord> recordList=this.joinRecordService.findByPage(page, Constant.ORDER_BY_FIELD_CREATE, Constant.ORDER_BY, params);
				boolean recordFlag=this.isList(recordList);
				PageValueApi api=new PageValueApi();
				api.setPage(page);
				api.setEndDate(reward.getEndDate());
				if(recordFlag){				
					api.setList(recordList);				
				}
				resDto.setData(api);
			}
		} catch (Exception e) {
			logger.error("获取抽奖记录接口", e);
			throw new ServiceException(e);
		}
		return resDto;
	}

	@Override
	public ResultDto getRewardLuck(String rewardId) {
		ResultDto resDto = null;
		try {
			resDto = new ResultDto(ResultDto.OK_CODE, "success");	
			if(StringUtils.isNotEmpty(rewardId)){
				RewardManager reward=this.getRewardManager(rewardId);				
				RewardLuckNo luck=this.rewardLuckNoMapper.selectluckNoByid(rewardId);
				if(null!=luck){
					//是否已经到了开奖时间
					boolean isDrawFlag=this.isDraw(reward);
					if(!isDrawFlag){
						luck.setLuckNo("");
					}					
				}
				//等待开奖或者一开奖但是没有计算幸运号
				else if(PrmsConstant.sortdateJingxing.equals(reward.getRewardStatus()) || PrmsConstant.sortdatejiesu.equals(reward.getRewardStatus())){
					//咩有计算幸运号 自己计算A值和参与人次
					luck=new RewardLuckNo();
					Page page=new Page();
					page.setPageIndex(1);
					page.setPageSize(50);
					ResultDto dto=this.getJoinRecordList(page, rewardId);
					if(null!=dto.getData()){
						PageValueApi api=(PageValueApi) dto.getData();
						List<JoinRecord> list=(List<JoinRecord>) api.getList();
						Long sum=this.getSumTime(list);
						if(null!=sum){
							luck.setOneValue(sum.toString());
						}else{
							luck.setOneValue("");
						}
						luck.setTwoValue("");
						luck.setLuckNo("");
					}				
					//该活动参与人次
					Integer count=getDrawCount(rewardId);
					luck.setTakeCount(count);
				}				
				resDto.setData(luck);
			}
		} catch (Exception e) {
			logger.error("获取计算幸运号", e);
			throw new ServiceException(e);
		}
		return resDto;
	}

	private Long getSumTime(List<JoinRecord> recordList){
		Long count=0L;
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
	
	private boolean isDraw(RewardManager reward){
		boolean flag=false;
		if(null!=reward &&PrmsConstant.sortdatejiesu.equals(reward.getRewardStatus())){
			flag=true;
		}
		return flag;
	}
	
	@Override
	public ResultDto getChoiceShareOrder() {
		
		ResultDto resDto = null;
		try {
			resDto = new ResultDto(ResultDto.OK_CODE, "success");	
			Map<String, Object> params=new HashMap<String, Object>();
			List<ChoiceShareOrder> choiceList=this.choiceShareOrderService.findByBiz(params);
			boolean recordFlag=this.isList(choiceList);
			if(recordFlag){
				resDto.setData(choiceList);
			}
		} catch (Exception e) {
			logger.error("获取精选晒单接口", e);
			throw new ServiceException(e);
		}
		return resDto;
	}

	@Override
	public ResultDto getShareOrderList(Page page) {
		ResultDto resDto = null;
		try {
			resDto = new ResultDto(ResultDto.OK_CODE, "success");	
			Map<String, Object> params=new HashMap<String, Object>();
			params.put("isReward", "YES");
			params.put("rewardate", "YES");
			List<ShareOrder> choiceList=this.shareOrderService.findByPage(page, null,null, params);
			boolean recordFlag=this.isList(choiceList);
			PageValueApi api=new PageValueApi();
			api.setPage(page);
			if(recordFlag){			
				api.setList(choiceList);				
			}
			resDto.setData(api);
		} catch (Exception e) {
			logger.error("获取普通晒单接口", e);
			throw new ServiceException(e);
		}
		return resDto;
	}
	
	private boolean isList(List<?> list){
		boolean flag=false;
		if(list!=null && list.size()!=0){
			flag= true;
		}
		return flag;
	}


	@Override
	public ResultDto getLuckNoByMember(String rewardId, String memberId) {		
		ResultDto resDto = null;
		try {
			resDto = new ResultDto(ResultDto.OK_CODE, "success");	
			Map<String, Object> params=new HashMap<String, Object>();
			params.put("rewardId", memberId);
			params.put("memberId", memberId);
			List<JoinRecord> recordList=this.getJoinRecordList(rewardId, memberId);
			boolean recordFlag=this.isList(recordList);
			if(recordFlag){
				resDto.setData(recordList);
			}
		} catch (Exception e) {
			logger.error("获取某个人的抽奖幸运号", e);
			throw new ServiceException(e);
		}
		return resDto;
	}

	@Override
	public ResultDto isShareOrDraw(String memberId, String rewardId,String type) {
		
		ResultDto resDto = null;
		try {
			resDto = new ResultDto(ResultDto.OK_CODE, "success");	
			ShareDraw draw= this.getShareDrawList(rewardId, memberId);
			if(null!=draw){
				Integer count=draw.getCount();//剩余可抽奖次数
				String isShare=draw.getIsShare();//是否可分享再抽一次
				//验证是否可以免费抽奖
				if(PrmsConstant.BONDED.equals(type)){
					if(count<=0){
						resDto = new ResultDto(ResultDto.ERROR_CODE, "error");	
					}
				}
				if(PrmsConstant.NOBOND.equals(type)){
					if(isShare.equals(PrmsConstant.BONDED)){
						resDto = new ResultDto(ResultDto.ERROR_CODE, "error");	
					}
				}				
			}	
		} catch (Exception e) {
			logger.error("获取某个人是否可以再次抽奖", e);
			throw new ServiceException(e);
		}
		return resDto;
	}

}
