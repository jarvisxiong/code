package com.ffzx.adel.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ffzx.adel.model.MemberInfo;
import com.ffzx.adel.model.MemberInfoExample;
import com.ffzx.adel.service.MemberInfoService;
import com.ffzx.common.controller.BaseController;

/**
 * Created by Administrator on 2017/1/22.
 */
@Controller
@RequestMapping("/MemberInfo")
public class MemberInfoController extends BaseController<MemberInfo, String, MemberInfoExample> {

    @Resource
    private MemberInfoService service;


    @Override
    public MemberInfoService getService() {
        return service;
    }


}
