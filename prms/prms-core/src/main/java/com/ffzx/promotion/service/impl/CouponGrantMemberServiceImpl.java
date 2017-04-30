package com.ffzx.promotion.service.impl;

import com.ffzx.commerce.framework.dao.CrudMapper;
import com.ffzx.commerce.framework.enums.ServiceCode;
import com.ffzx.commerce.framework.enums.ServiceResultCode;
import com.ffzx.commerce.framework.service.impl.BaseCrudServiceImpl;
import com.ffzx.commerce.framework.utils.StringUtil;
import com.ffzx.commerce.framework.utils.UUIDGenerator;
import com.ffzx.promotion.api.dto.CouponGrant;
import com.ffzx.promotion.api.dto.CouponGrantMember;
import com.ffzx.promotion.mapper.CouponGrantMemberMapper;
import com.ffzx.promotion.service.CouponGrantMemberService;

import java.util.List;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 
 * @author ffzx
 * @date 2016-05-03 17:58:03
 * @version 1.0.0
 * @copyright www.ffzxnet.com
 */
@Service("couponGrantMemberService")
public class CouponGrantMemberServiceImpl extends BaseCrudServiceImpl implements CouponGrantMemberService {

    @Resource
    private CouponGrantMemberMapper couponGrantMemberMapper;

    @Override
    public CrudMapper init() {
        return couponGrantMemberMapper;
    }

	@Override
	public List<String> selectMemberid(String CouponGrantId) {
		// TODO Auto-generated method stub
		List<String> list= couponGrantMemberMapper.selectMemberid(CouponGrantId);
		if(list==null || list.size()==0){
			return null;
		}
		return list;
	}

	@Override
	@Transactional(rollbackFor=Exception.class)
	public ServiceCode manyInsertOrdelete(String userList,String couoponGrantId,boolean isdelete,boolean isadd) {
		if(isdelete){
			couponGrantMemberMapper.deleteByCouponGrantId(couoponGrantId);
		}
		// TODO Auto-generated method stub
		int result = 0;
		if(isadd){
			if(StringUtil.isNotNull(userList)){
				String[] userStrings=userList.split(",");
				for (String userId : userStrings) {
					CouponGrantMember couponGrantMember=new CouponGrantMember();
					couponGrantMember.setId(UUIDGenerator.getUUID());
					CouponGrant couponGrant=new CouponGrant();
					couponGrant.setId(couoponGrantId);
					couponGrantMember.setCouponGrant(couponGrant);
					couponGrantMember.setMemberId(userId.trim());
					result+=couponGrantMemberMapper.insert(couponGrantMember);
				}
			}
		}
		
		return result > 0 ? ServiceResultCode.SUCCESS : ServiceResultCode.FAIL;
	}
}