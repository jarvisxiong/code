package com.ffzx.adel.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ffzx.adel.model.Member;
import com.ffzx.adel.model.MemberExample;
import com.ffzx.adel.service.MemberService;
import com.ffzx.common.controller.BaseController;

/**
 * Created by Administrator on 2017/1/22.
 */
@Controller
@RequestMapping("/Member")
public class MemberController extends BaseController<Member, String, MemberExample> {

    @Resource
    private MemberService service;


    @Override
    public MemberService getService() {
        return service;
    }

}
