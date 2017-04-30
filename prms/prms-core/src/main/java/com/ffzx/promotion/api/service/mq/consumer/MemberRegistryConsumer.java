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
import com.ffzx.commerce.framework.mq.AbstractMessageConsumer;
import com.ffzx.commerce.framework.utils.DateUtil;
import com.ffzx.commerce.framework.utils.StringUtil;
import com.ffzx.commerce.framework.utils.UUIDGenerator;
import com.ffzx.member.api.dto.Member;
import com.ffzx.member.api.dto.ShareRecord;
import com.ffzx.member.api.service.ShareRewardApiService;
import com.ffzx.promotion.api.dto.CouponAdmin;
import com.ffzx.promotion.api.dto.CouponGrant;
import com.ffzx.promotion.api.dto.CouponReceive;
import com.ffzx.promotion.mapper.CouponAdminMapper;
import com.ffzx.promotion.mapper.CouponReceiveMapper;

/***
 * 会员注册时触发
 * @author sheng.shan
 * @date 2016年10月10日 下午2:53:48
 * @email sheng.shan@ffzxnet.com
 * @version V1.0
 *
 */
@Service
public class MemberRegistryConsumer extends AbstractMessageConsumer<Member>{

	@Resource
    private CouponReceiveMapper couponReceiveMapper;
	@Autowired
	private ShareRewardApiService shareRewardApiService;
    @Resource
    private CouponAdminMapper couponAdminMapper;
	
	@Override
	@Transactional(rollbackFor=Exception.class)
	public void onMessage(Member member) {
		try {
			logger.info("新注册用户信息"+member);
			if(member!=null){
				ResultDto result = shareRewardApiService.getShareRecordByPhone(member.getPhone());
				ShareRecord shareRecord = (ShareRecord) result.getData();
				if (shareRecord != null) {
					logger.info("推荐的登记记录："+shareRecord);
					if (shareRecord.getRegisterCouponCode() == null) {
						return;
					}
					// 判断注册时间是否在7天内
					if (DateUtil.daysBetween(shareRecord.getCreateDate(), member.getRegisterDate()) <= 7) {
						// 通过发放编码获取优惠券列表
						Map<String, Object> params=new HashMap<String, Object>();
						params.put("number", shareRecord.getRegisterCouponCode());
						List<CouponAdmin> couponAdminList = couponAdminMapper.findCouponAdminByGrantNumber(params);
						logger.info("被推荐的新用户注册可领取的红包信息：："+couponAdminList);
						// 领取对象
						CouponGrant couponGrant = new CouponGrant();
						
						// 遍历优惠券--加入优惠券领取表
						for (CouponAdmin couponAdmin : couponAdminList) {
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
					}
					logger.info(member.getId()+","+member.getPhone());
				} else {
					logger.info("手机号为："+member.getPhone()+"的用户因为注册时间超过7天，所以未能领取到红包");
				}
			}
		} catch (Exception e) {
			logger.error("MemberRegistryConsumer 新注册用户领取推荐有奖红包出错:"+e.getMessage(),e);
		}

	}

}
