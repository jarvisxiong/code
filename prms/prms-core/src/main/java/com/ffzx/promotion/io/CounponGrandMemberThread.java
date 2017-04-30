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
import com.ffzx.promotion.api.dto.CouponAdminCouponGrant;
import com.ffzx.promotion.api.dto.CouponGrant;
import com.ffzx.promotion.api.dto.CouponReceive;
import com.ffzx.promotion.service.CouponReceiveService;

public class CounponGrandMemberThread implements Callable<String>{
	private final static Logger logger = LoggerFactory.getLogger(CounponGrandMemberThread.class);

	private List<CouponAdminCouponGrant> couponGrantList;
	private CouponReceiveService couponReceiveService;
	private Map<String, Object> memberParams;
	private MemberApiService memberApiService;
	private boolean isException;//是否异常
	private CouponGrant grant;
	 public CounponGrandMemberThread(MemberApiService memberApiService,List<CouponAdminCouponGrant> couponGrantList,CouponReceiveService couponReceiveService
			 ,Map<String, Object> memberParams,boolean isException,CouponGrant grant) {
		// TODO Auto-generated constructor stub
		 this.couponGrantList=couponGrantList;
		 this.couponReceiveService=couponReceiveService;
		 this.memberParams=memberParams;
		 this.memberApiService=memberApiService;
		 this.isException=isException;
		 this.grant=grant;
	}
	@Override
	public String call() throws Exception {
		

		// TODO Auto-generated method stub
		ResultDto result=memberApiService.getMemberList(memberParams);
		List<Member> memberList=(List<Member>) result.getData();//第一页数据
		if(memberList!=null && memberList.size()>0){
	    	for (CouponAdminCouponGrant couponAdminCouponGrant : couponGrantList) {
	    		List<CouponReceive> couponReceives=new ArrayList<CouponReceive>();
	    		for(Member user:memberList){
		    			if(grant.getReceiveLimit()!=null){
		    				for(int i=0;i<grant.getReceiveLimit();i++){
								CouponReceive receive=new CouponReceive();
								 receive.setId(UUIDGenerator.getUUID());
								 receive.setMemberId(user.getId());
								 receive.setNickName(user.getNickName());
								 receive.setPhone(user.getPhone());
								 receive.setUseState(Constant.NO);
								 receive.setCreateDate(new Date());
								 receive.setCouponAdmin(couponAdminCouponGrant.getCouponAdmin());
								 receive.setCouponGrant(couponAdminCouponGrant.getCouponGrant());
								 receive.setReceiveDate(new Date());
								 CouponAdmin coupon=couponAdminCouponGrant.getCouponAdmin();
								 Date effective_date_end=coupon.getEffectiveDateEnd();//优惠券有效期结束时间
								 BigDecimal effective_date_num=coupon.getEffectiveDateNum();//优惠券有效期天数
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
	    	   }
	    		if(isException){
	    			couponReceiveService.insertIsExAllCoupon(couponReceives);
	    		}else{
	    			couponReceiveService.insertAllCoupon(couponReceives);
	    		}
			}
		}
		return null;

	}
}
