package com.ffzx.promotion.api.service.mq.consumer;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ffzx.commerce.framework.constant.Constant;
import com.ffzx.commerce.framework.dto.ResultDto;
import com.ffzx.commerce.framework.exception.ServiceException;
import com.ffzx.commerce.framework.mq.AbstractMessageConsumer;
import com.ffzx.commerce.framework.thirdparty.ShortMsgEnum;
import com.ffzx.commerce.framework.thirdparty.ShortMsgFactory;
import com.ffzx.commerce.framework.utils.DateUtil;
import com.ffzx.commerce.framework.utils.StringUtil;
import com.ffzx.commerce.framework.utils.UUIDGenerator;
import com.ffzx.member.api.dto.Member;
import com.ffzx.member.api.dto.RewardRecord;
import com.ffzx.member.api.dto.ShareRecord;
import com.ffzx.member.api.service.MemberApiService;
import com.ffzx.member.api.service.ShareRewardApiService;
import com.ffzx.order.api.dto.OmsOrder;
import com.ffzx.order.api.enums.OrderStatusEnum;
import com.ffzx.promotion.api.dto.CouponAdmin;
import com.ffzx.promotion.api.dto.CouponGrant;
import com.ffzx.promotion.api.dto.CouponReceive;
import com.ffzx.promotion.mapper.CouponAdminMapper;
import com.ffzx.promotion.mapper.CouponReceiveMapper;

/***
 * 订单交易完成时触发
 * @author sheng.shan
 * @date 2016年10月10日 下午2:53:59
 * @email sheng.shan@ffzxnet.com
 * @version V1.0
 *
 */
@Service
public class OrderFullConsumer extends AbstractMessageConsumer<OmsOrder>{

	@Autowired
    private MemberApiService memberApiService;
	@Autowired
	private ShareRewardApiService shareRewardApiService;
	@Resource
    private CouponReceiveMapper couponReceiveMapper;
    @Resource
    private CouponAdminMapper couponAdminMapper;
	
	
	
	@Override
	@Transactional(rollbackFor=Exception.class)
	public void onMessage(OmsOrder order) {
		logger.info("进入老用户推荐领取操作--start");
		try {
			if (order != null) 
			{
				// 获取新用户信息
				Member member = this.getMember(order.getMemberId());
				// 判断是否是被推荐用户，并且新用户注册时间是否在推荐登记后7天内注册大麦场
				ResultDto result = new ResultDto();
				result = shareRewardApiService.getShareRecordByPhone(member.getPhone());
				logger.info("判断是否是被推荐用户:result="+result);
				if (result != null) 
				{
					ShareRecord shareRecord = (ShareRecord) result.getData();
					if (shareRecord == null) {
						logger.info("用户:"+member.getPhone()+"非被推荐用户");
						return;
					}
					logger.info("判断是否是被推荐用户:shareRecordId="+shareRecord.getId());
					// 获取领取记录
					Map<String, Object> params = new HashMap<String, Object>();
					params.put("memberId", shareRecord.getRecomendId());
					params.put("recommendedPhone", shareRecord.getRecordPhone());
					params.put("recordType", "1");
					List<RewardRecord> rewardRecordList = shareRewardApiService.getRewardRecordParam(params);
					
					// 判断是否已领取过
					if (rewardRecordList == null || rewardRecordList.size() <= 0) {
						logger.info("推荐的登记记录：shareRecordId="+shareRecord.getId()+",memberId="+shareRecord.getRecomendId()
						+",shareRecordCreateDate="+shareRecord.getCreateDate()+",新用户注册时间="+member.getRegisterDate());
						// 判断注册时间是否在7天内
						if (DateUtil.daysBetween(shareRecord.getCreateDate(), member.getRegisterDate()) <= 7) 
						{
							// 判断推荐的新用户是否是在注册后30天内下单付款且订单状态为交易完成
							logger.info("判断推荐的新用户是否是在注册后30天内下单付款且订单状态为交易完成:orderFinishTime="+order.getTranTime()
									+",orderStatus="+order.getStatus());
							if (DateUtil.daysBetween(shareRecord.getCreateDate(), order.getTranTime()) <= 30 
									&& OrderStatusEnum.TRANSACTION_COMPLETION.getValue().equals(order.getStatus())) 
							{
								// 获取推荐的老用户信息，并且领取红包奖励
								Member oldMember = this.getMember(shareRecord.getRecomendId());
								logger.info("推荐的老用户信息:memberId="+oldMember.getId()+",优惠券发放code="+shareRecord.getRecommendedCouponCode());
								BigDecimal rewardAmout = new BigDecimal(0);
								rewardAmout = saveCouponReceive(oldMember, shareRecord.getRecommendedCouponCode());
								// 短息推送提醒老用户
								ShortMsgFactory.getInstance().getShortMsg().send(ShortMsgEnum.send_txt, oldMember.getPhone(), 
										"恭喜您获得" + rewardAmout.intValue() + "元红包优惠券，有效期30天，使用此手机号登录非凡大麦场账户即可使用");
								// 保存推荐人的领取记录
								logger.info("保存推荐人的领取记录信息:oldmemberId="+oldMember.getId()+
										",newmemberPhone="+member.getPhone()+",orderNo="+order.getOrderNo());
								saveRewardRecord(oldMember.getId(), oldMember.getPhone(), 
										member.getPhone(), order.getOrderNo(), rewardAmout);
							} else {
								logger.info("推荐的新用户在注册后首次下单付款且订单状态为交易完成花费的时间已超出30天");
							}
						} else {
							logger.info("手机号为："+member.getPhone()+"的用户因为注册时间超过7天，所以老用户不能领取到红包");
						}
					}
					
				}
			}
			
		} catch (Exception e) {
			logger.error("OrderFullConsumer 老用户推荐的新用户，注册后30天内首次下单付款且订单状态交易完成 出错:"+e.getMessage(),e);
		}
	}
	
	/*****
	* 获取会员信息
	* @param memberId 会员id
	* @return Member    返回类型
	 */
	private Member getMember(String memberId){
		ResultDto result = memberApiService.getByIdMember(memberId);
		Member member = (Member) result.getData();
		if(member == null)
		{
			logger.info("不存在id为：" + memberId + "的用户");
			throw new ServiceException("不存在id为：" + memberId + "的用户");
		}
		logger.info("查询获取会员信息:" + member.getPhone());
		
		return member;
	}
	
	/*****
	 * 老用户领取红包优惠券
	* @param member 推荐人会员信息
	* @param recommendedCouponCode 老用户反推荐红包发放编码     
	* @return void    返回类型
	 */
	private BigDecimal saveCouponReceive(Member member, String recommendedCouponCode){
		// 通过发放编码获取优惠券列表
		Map<String, Object> params=new HashMap<String, Object>();
		params.put("number", recommendedCouponCode);
		List<CouponAdmin> couponAdminList = couponAdminMapper.findCouponAdminByGrantNumber(params);
		logger.info("推荐的老用户可领取的红包信息：："+couponAdminList);
		// 领取对象
		CouponGrant couponGrant = new CouponGrant();
		// 奖励额度
		BigDecimal rewardAmout = new BigDecimal(0);
		// 遍历优惠券--加入优惠券领取表
		for (CouponAdmin couponAdmin : couponAdminList) {
			// 累加奖励额度
			rewardAmout = rewardAmout.add(couponAdmin.getFaceValue());
			CouponReceive receive=new CouponReceive();
			receive.setId(UUIDGenerator.getUUID());
			receive.setMemberId(member.getId());
			receive.setNickName(member.getNickName());
			receive.setPhone(member.getPhone());
			receive.setUseState(Constant.NO);
			receive.setCreateDate(new Date());
			receive.setCouponAdmin(couponAdmin);
			// 优惠券发放对象
			couponGrant.setId(couponAdmin.getCouponGrantId());
			receive.setCouponGrant(couponGrant);
			receive.setReceiveDate(new Date());
			Date effective_date_end=couponAdmin.getEffectiveDateEnd();//优惠券有效期结束时间
			BigDecimal effective_date_num=couponAdmin.getEffectiveDateNum();//优惠券有效期天数
			Date receiveDate=new Date();//领取时间
			if(StringUtil.isNotNull(effective_date_end)){
				receive.setOverDate(effective_date_end);
			}
			//领取多少天后
			if(StringUtil.isNotNull(effective_date_num)){
				receive.setOverDate(DateUtil.getNextDay(receiveDate,effective_date_num.intValue()));
			}
			// 保存领取记录
			couponReceiveMapper.insertSelective(receive);
		}
		
		return rewardAmout;
	}
	
	/*****
	 * 保存推荐人领取记录
	* @param  推荐人领取记录对象    
	* @return void    返回类型
	 */
	private void saveRewardRecord(String memberId, String recommendPhone, String recommendedPhone,
			String orderNo, BigDecimal rewardAmout){
		
		RewardRecord rewardRecord = new RewardRecord();
		rewardRecord.setId(UUIDGenerator.getUUID());
		rewardRecord.setMemberId(memberId);
		rewardRecord.setRecommendPhone(recommendPhone);
		rewardRecord.setRecommendedPhone(recommendedPhone);
		rewardRecord.setOrderNo(orderNo);
		rewardRecord.setRewardAmout(rewardAmout);
		rewardRecord.setCreateDate(new Date());
		rewardRecord.setRecordType("1");
		// 新增推荐人领取记录
		shareRewardApiService.addRewardRecord(rewardRecord);
	}
	
}
