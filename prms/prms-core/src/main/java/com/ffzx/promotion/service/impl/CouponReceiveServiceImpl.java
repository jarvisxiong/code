package com.ffzx.promotion.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ffzx.commerce.framework.constant.Constant;
import com.ffzx.commerce.framework.dao.CrudMapper;
import com.ffzx.commerce.framework.exception.ServiceException;
import com.ffzx.commerce.framework.page.Page;
import com.ffzx.commerce.framework.service.impl.BaseCrudServiceImpl;
import com.ffzx.commerce.framework.utils.StringUtil;
import com.ffzx.member.api.dto.Member;
import com.ffzx.member.api.service.MemberApiService;
import com.ffzx.promotion.api.dto.CouponGrant;
import com.ffzx.promotion.api.dto.CouponReceive;
import com.ffzx.promotion.mapper.CouponGrantMapper;
import com.ffzx.promotion.mapper.CouponReceiveMapper;
import com.ffzx.promotion.service.CouponReceiveService;

/**
 * 
 * @author ffzx
 * @date 2016-05-03 17:58:03
 * @version 1.0.0
 * @copyright www.ffzxnet.com
 */
@Service("couponReceiveService")
public class CouponReceiveServiceImpl extends BaseCrudServiceImpl implements CouponReceiveService {

    @Resource
    private CouponGrantMapper couponGrantMapper;
    @Resource
    private CouponReceiveMapper couponReceiveMapper;

    @Autowired
    private MemberApiService memberApiService;

    @Override
    public CrudMapper init() {
        return couponReceiveMapper;
    }
    
    @Override
	public List<CouponReceive> findList(Page page, String orderByField, String orderBy, CouponReceive couponReceive) throws ServiceException {
		// TODO Auto-generated method stub
    	 Map<String, Object> params = new HashMap<String, Object>();

    	 getParamsCouponReceive(couponReceive,params);
    	
		return couponReceiveMapper.findByPage(page, orderByField, orderBy, params,true);
	}
    
//    public CouponReceive getCouponRecive(String uid,String couponId){
//    	Map<String, Object> params=new HashMap<String, Object>();
//		params.put("memberId", uid);
//		params.put("couponId", couponId);
//    	return this.couponReceiveMapper.selectReceiveCoupon(params);
//    }

	@Override
	@Transactional(rollbackFor=Exception.class)
	public void insertAllCoupon(List<CouponReceive> couponReceives) throws ServiceException {
		// TODO Auto-generated method stub
		for (CouponReceive couponReceive : couponReceives) {
			couponReceiveMapper.insert(couponReceive);
		}
	}

	@Override
	@Transactional(rollbackFor=Exception.class)
	public void insertUserCoponUpdateUser(List<CouponReceive> couponReceives,List<Member> updateMembers) throws ServiceException {
		// TODO Auto-generated method stub
		try{
			for (CouponReceive couponReceive : couponReceives) {
				couponReceiveMapper.insert(couponReceive);
//				member.setIsNew(Constant.NO);
			}
			memberApiService.updateListMember(updateMembers);
		}catch(Exception e){
			logger.error("updatenewuserinsertCoupon",e);
			throw new ServiceException(e);
		}
	}

	@Override
	@Transactional(rollbackFor=Exception.class)
	public void insertUserGrantCoponUpdateUser(List<CouponReceive> couponReceives, List<Member> updateMembers,boolean flag)
			throws ServiceException {
		// TODO Auto-generated method stub
		try{
			for (CouponReceive couponReceive : couponReceives) {
				Map<String, Object> params=new HashMap<String, Object>();
				params.put("memberId", couponReceive.getMemberId());
				params.put("couponGrant", couponReceive.getCouponGrant());
				params.put("couponAdmin", couponReceive.getCouponAdmin());
				int count=couponReceiveMapper.selectCount(params);
				if(count==0){
					couponReceiveMapper.insert(couponReceive);
				}
//				member.setIsNew(Constant.NO);
			}
			if(flag)
				memberApiService.updateListMember(updateMembers);
		}catch(Exception e){
			logger.error("updatenewuserinsertCoupon",e);
			throw new ServiceException(e);
		}
		
	}

	@Override
	public void insertBuyCoponUpdateUser(List<CouponReceive> couponReceives) throws ServiceException {
		// TODO Auto-generated method stub

		boolean flag=false;
		for (CouponReceive couponReceive : couponReceives) {
			Map<String, Object> params=new HashMap<String, Object>();
			params.put("memberId", couponReceive.getMemberId());
			params.put("couponGrant", couponReceive.getCouponGrant());
			params.put("couponAdmin", couponReceive.getCouponAdmin());
			params.put("orderNo", couponReceive.getOrderNo());
			int count=couponReceiveMapper.selectCount(params);
			if(count==0){
				flag=true;
			}
		}
		if(flag){
			for (CouponReceive couponReceive : couponReceives) {
			couponReceiveMapper.insertSelective(couponReceive);
			}
		}
	}

	@Override
	@Transactional(rollbackFor=Exception.class)
	public void insertIsExAllCoupon(List<CouponReceive> couponReceives) throws ServiceException {
		// TODO Auto-generated method stub

		for (CouponReceive couponReceive : couponReceives) {
			Map<String, Object> params=new HashMap<String, Object>();
			params.put("memberId", couponReceive.getMemberId());
			params.put("couponGrant", couponReceive.getCouponGrant());
			params.put("couponAdmin", couponReceive.getCouponAdmin());
			int count=couponReceiveMapper.selectCount(params);
			if(count==0)
				couponReceiveMapper.insert(couponReceive);
		}
	}

	@Override
	@Transactional(rollbackFor=Exception.class)
	public int deleteByPrimaryKeyGrantId(String grantId,int deleteNum) {
		// TODO Auto-generated method stub
		return couponReceiveMapper.deleteByPrimaryKeyGrantId(grantId,deleteNum);
	}

	@Override
	@Transactional(rollbackFor=Exception.class)
	public int updateGrant(CouponGrant gran) {
		// TODO Auto-generated method stub
		return couponGrantMapper.updateByPrimaryKeySelective(gran);
	}

	private void getParamsCouponReceive(CouponReceive couponReceive,Map<String, Object> params){
		if(couponReceive.getCouponGrant()!=null){
    		params.put("couponGrant", couponReceive.getCouponGrant());
    		//筛选条件-发放名称
     		if(StringUtil.isNotNull(couponReceive.getCouponGrant().getName())){
     			params.put("couponGrant.name", couponReceive.getCouponGrant().getName());
     		}
     		//筛选条件-发放编码
     		if(StringUtil.isNotNull(couponReceive.getCouponGrant().getNumber())){
     			params.put("couponGrant.number", couponReceive.getCouponGrant().getNumber());
     		}
     		//筛选条件-使用状态
     		if(StringUtil.isNotNull(couponReceive.getCouponGrant().getGrantMode())){
     			params.put("couponGrant.grantMode", couponReceive.getCouponGrant().getGrantMode());
     		}
    	}
    	if(couponReceive.getCouponAdmin() != null){
    		params.put("couponAdmin", couponReceive.getCouponAdmin());
    		//筛选条件-优惠券名称
     		if(StringUtil.isNotNull(couponReceive.getCouponAdmin().getName())){
     			params.put("couponAdmin.name", couponReceive.getCouponAdmin().getName());
     		}
     		//筛选条件-优惠券编码
     		if(StringUtil.isNotNull(couponReceive.getCouponAdmin().getNumber())){
     			params.put("couponAdmin.number", couponReceive.getCouponAdmin().getNumber());
     		}
    	}
 		//筛选条件-昵称
 		if(StringUtil.isNotNull(couponReceive.getNickName())){
 			params.put("nickName", couponReceive.getNickName());
 		}
 		//筛选条件-联系电话
 		if(StringUtil.isNotNull(couponReceive.getPhone())){
 			params.put("phone", couponReceive.getPhone());
 		}
 		//筛选条件-开始领取时间
 		if(StringUtil.isNotNull(couponReceive.getBeginReceiveDateStr())){
 			params.put("beginReceiveDateStr", couponReceive.getBeginReceiveDateStr());
 		}
 		//筛选条件-结束领取时间
 		if(StringUtil.isNotNull(couponReceive.getEndReceiveDateStr())){
 			params.put("endReceiveDateStr", couponReceive.getEndReceiveDateStr());
 		}
 		//筛选条件-使用状态
 		if(StringUtil.isNotNull(couponReceive.getUseState())){
 			params.put("useState", couponReceive.getUseState());
 		}
		params.put("isReceive", Constant.YES);
	}
	@Override
	public int selectReceiveCount( CouponReceive couponReceive) {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
    	 Map<String, Object> params = new HashMap<String, Object>();
    	 getParamsCouponReceive(couponReceive,params);
		return couponReceiveMapper.selectReceiveCount(params);
	}
}