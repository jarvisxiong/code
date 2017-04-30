package com.ffzx.promotion.io;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ffzx.commerce.framework.constant.Constant;
import com.ffzx.commerce.framework.dto.ResultDto;
import com.ffzx.commerce.framework.utils.DateUtil;
import com.ffzx.commerce.framework.utils.StringUtil;
import com.ffzx.commerce.framework.utils.UUIDGenerator;
import com.ffzx.member.api.dto.Member;
import com.ffzx.member.api.service.MemberApiService;
import com.ffzx.promotion.api.dto.CouponAdmin;
import com.ffzx.promotion.api.dto.CouponReceive;
import com.ffzx.promotion.service.CouponReceiveService;

public class SenNewUserThread implements Callable<String>{
	private final static Logger logger = LoggerFactory.getLogger(SenNewUserThread.class);

	private List<CouponAdmin> couponAdmins;
	private CouponReceiveService couponReceiveService;
	private Map<String, Object> memberParams;
	private MemberApiService memberApiService;
	 public SenNewUserThread(MemberApiService memberApiService,List<CouponAdmin> couponAdmins,CouponReceiveService couponReceiveService
			 ,Map<String, Object> memberParams) {
		// TODO Auto-generated constructor stub
		 this.couponAdmins=couponAdmins;
		 this.couponReceiveService=couponReceiveService;
		 this.memberParams=memberParams;
		 this.memberApiService=memberApiService;
	}
	@Override
	public String call() throws Exception {


		// TODO Auto-generated method stub
		ResultDto result=memberApiService.getMemberList(memberParams);
		List<Member> memberList=(List<Member>) result.getData();//第一页数据
		if(memberList!=null && memberList.size()>0){
			List<CouponReceive> couponReceives=new ArrayList<CouponReceive>();
			List<Member> updateMembers=new ArrayList<Member>();
	    		for(Member user:memberList){
	    			Member member=new Member();//更新会员领取状态
	    			member.setId(user.getId());
	    			member.setIsCoupon(Constant.YES);
	    			updateMembers.add(member);
	    			for (CouponAdmin couponAdmin : couponAdmins) {
					 CouponReceive receive=new CouponReceive();
					 receive.setId(UUIDGenerator.getUUID());
					 receive.setMemberId(user.getId());
					 receive.setNickName(user.getNickName());
					 receive.setPhone(user.getPhone());
					 receive.setUseState("0");
					 receive.setCreateDate(new Date());
					 receive.setCouponAdmin(couponAdmin);
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
					 couponReceives.add(receive);
	    	   }
			}
	    	couponReceiveService.insertUserCoponUpdateUser(couponReceives,updateMembers);
		}
		return null;

	}
}