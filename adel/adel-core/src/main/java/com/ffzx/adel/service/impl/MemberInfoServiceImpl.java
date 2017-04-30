package com.ffzx.adel.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.ffzx.adel.mapper.MemberInfoMapper;
import com.ffzx.adel.model.MemberInfo;
import com.ffzx.adel.model.MemberInfoExample;
import com.ffzx.adel.service.MemberInfoService;
import com.ffzx.common.service.impl.BaseServiceImpl;

/**
 * Created by Administrator on 2017/1/17.
 */
@Service
public class MemberInfoServiceImpl extends BaseServiceImpl<MemberInfo,String> implements MemberInfoService {

    @Resource
    private MemberInfoMapper mapper;

    @Override
    public MemberInfoMapper getMapper() {
        return mapper;
    }

    /**
     * 查询或保存用户信息
     * @param memberInfo
     * @return
     */
    public MemberInfo findOrSave(MemberInfo memberInfo){

        MemberInfoExample example = new MemberInfoExample();
        example.createCriteria().andIdNumberEqualTo(memberInfo.getIdNumber());
        List<MemberInfo> list = mapper.selectByExample(example);

        if(StringUtils.isEmpty(list) || list.size() <= 0){
            super.add(memberInfo);
        }else{
            return list.get(0);
        }
        return memberInfo;
    }
}
