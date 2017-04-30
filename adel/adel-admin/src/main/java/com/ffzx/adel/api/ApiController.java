package com.ffzx.adel.api;


import java.io.BufferedReader;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ffzx.adel.model.Member;
import com.ffzx.adel.service.MemberService;
import com.ffzx.adel.util.JsonConverter;
import com.ffzx.adel.vo.TicketModel;
import com.ffzx.adel.wechat.WechatApiService;
import com.ffzx.commerce.framework.model.ServiceException;
import com.ffzx.common.utils.WebUtils;
import com.pingplusplus.model.Charge;
import com.pingplusplus.model.Event;
import com.pingplusplus.model.Refund;
import com.pingplusplus.model.Webhooks;

/**
 * Created by Administrator on 2017/1/22.
 */
@Controller
@RequestMapping("/api")
public class ApiController {

    private Logger logger = LoggerFactory.getLogger(getClass());


    @Resource
    private MemberService memberService;

    @Resource
    private WechatApiService wechatApiService;
    

    /**
     * 登录
     *
     * @param entity wxOpenid 实参为获取openid前置code
     * @return
     */
    @RequestMapping("login")
    @ResponseBody
    public Map<String, Object> login(Member entity, HttpServletRequest request) {
        Map<String, Object> ret = new HashMap<>();

        String authCode = request.getParameter("authCode");
        String authType=request.getParameter("authType");
        String refer = request.getParameter("refer");
        logger.info("authCode :{}", authCode);
        logger.info("refer :{}", refer);
        String openId = null;
        String unionId=null;

        Member member = getLoginMember();
        ;
        boolean iscreate = true;
        if (member == null) {
            if (StringUtils.isNotBlank(authCode)) {
                Map<String, String> map = wechatApiService.oauth(authCode,authType);
                logger.info(JsonConverter.toJson(map));
                openId = map.get("openid");
                unionId=map.get("unionid");


                member = memberService.findByUnionId(unionId);
                if (member != null) {
                    iscreate = false;
                } else {
                    member = new Member();
                }

                member.setWxOpenid(map.get("openid"));
                member.setWxNickName(map.get("nickname"));
                member.setWxHeadimgurl(map.get("headimgurl"));
                member.setWxUnionid(map.get("unionid"));
                if (iscreate) {
                    memberService.add(member);
                } else {
                    memberService.updateSelective(member);
                }
            } else {
                ret.put("code", "-3");
                ret.put("msg", "身份信息为空");
                return ret;
            }
        }


        member.setPassword(null);
        WebUtils.createSession();
        WebUtils.setSessionAttribute("loginMember", member);
        ret.put("loginInfo", member);
        ret.put("refer", refer);
        return ret;
    }

    /**
     * 获取当前登录会员信息
     *
     * @return
     */
    @RequestMapping("memberInfo")
    @ResponseBody
    public Member getLoginMember() {
        return WebUtils.getSessionAttribute("loginMember");
    }

}
