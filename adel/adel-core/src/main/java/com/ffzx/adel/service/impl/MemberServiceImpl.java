package com.ffzx.adel.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ffzx.adel.mapper.MemberMapper;
import com.ffzx.adel.model.Member;
import com.ffzx.adel.model.MemberExample;
import com.ffzx.adel.service.MemberService;
import com.ffzx.common.service.impl.BaseServiceImpl;

/**
 * Created by Administrator on 2017/1/17.
 */
@Service
public class MemberServiceImpl extends BaseServiceImpl<Member,String> implements MemberService {

    @Resource
    private MemberMapper mapper;

    @Override
    public MemberMapper getMapper() {
        return mapper;
    }

    @Override
    public Member findByOpenId(String opoenid) {
        MemberExample example=new MemberExample();
        example.createCriteria().andWxOpenidEqualTo(opoenid);
        List<Member> dataList=mapper.selectByExample(example);
        if(dataList!=null&&dataList.size()>0){
            return dataList.get(0);
        }
        return null;
    }

    @Override
    public Member findByUnionId(String unionId) {
        MemberExample example=new MemberExample();
        example.createCriteria().andWxUnionidEqualTo(unionId);
        List<Member> dataList=mapper.selectByExample(example);
        if(dataList!=null&&dataList.size()>0){
            return dataList.get(0);
        }
        return null;
    }
}
