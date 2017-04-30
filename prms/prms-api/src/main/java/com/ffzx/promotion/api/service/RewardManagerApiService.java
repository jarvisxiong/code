package com.ffzx.promotion.api.service;

import java.util.Map;

import com.ffzx.commerce.framework.dto.ResultDto;
import com.ffzx.commerce.framework.page.Page;
import com.ffzx.commerce.framework.service.BaseCrudService;
import com.ffzx.promotion.api.dto.JoinRecord;
import com.ffzx.promotion.api.dto.ShareDraw;


/***
 * 免费抽奖API接口
 * @author lushi.guo
 *
 */
public interface RewardManagerApiService extends BaseCrudService {

	/***
	 * 获取免费抽奖列表（分页）
	 * @param page
	 * @return
	 */
	public ResultDto getRewardMangerList(Page page,Map<String, Object> params);
	
	/**
	 * 根据活动详情ID 获取活动详情
	 * @param id
	 * @return
	 */
	public ResultDto getRewardManagerById(String id,String memberId);
	
	/***
	 * 分享再抽一次
	 * @param draw
	 * @return
	 */
	public ResultDto shareReward(ShareDraw draw);
	
	/***
	 * 立即免费抽奖
	 * @param record
	 * @return
	 */
	public ResultDto joinRewardManager(JoinRecord record);
	
	/**
	 * 获取参与抽奖的记录（分页获取）
	 * @param page
	 * @param rewardId
	 * @return
	 */
	public ResultDto getJoinRecordList(Page page,String rewardId);
	
	/**
	 * 获取计算幸运号详情
	 * @param rewardId
	 * @return
	 */
	public ResultDto getRewardLuck(String rewardId);
	
	/**
	 * 获取精选晒单
	 * @return
	 */
	public ResultDto getChoiceShareOrder();
	
	/***
	 * 获取普通晒单集合（分页）
	 * @param page
	 * @return
	 */
	public ResultDto getShareOrderList(Page page);
	
	/**
	 * 获取自己的幸运号
	 * @param rewardId
	 * @param memberId
	 * @return
	 */
	public ResultDto getLuckNoByMember(String rewardId,String memberId);
	
	
	/**
	 * 
	 * @param memberId
	 * @param rewardId
	 * @param type(0 分享再抽一次 1 免费抽奖)
	 * @return
	 */
	public ResultDto isShareOrDraw(String memberId,String rewardId,String type);
	
}
