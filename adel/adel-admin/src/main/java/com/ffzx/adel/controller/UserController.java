package com.ffzx.adel.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ffzx.adel.model.User;
import com.ffzx.adel.model.UserExample;
import com.ffzx.adel.service.UserService;
import com.ffzx.common.controller.BaseController;
import com.ffzx.common.utils.WebUtils;

/**
 * Created by Administrator on 2017/1/16.
 */
@Controller
@RequestMapping("/User")
public class UserController extends BaseController<User, String, UserExample> {

    @Resource
    private UserService service;

    @Override
    public UserService getService() {
        return service;
    }

    @RequestMapping("hello")
    @ResponseBody
    public String hello() {
        return "hello";
    }

    @Override
    public UserExample createExample() {
        return new UserExample();
    }

    @Override
    public User createEntity() {
        return new User();
    }


    /**
     * 登录
     *
     * @param entity
     * @return
     */
    @RequestMapping("login")
    @ResponseBody
    public User login(User entity) {
        UserExample example = createExample();
        example.createCriteria().andCodeEqualTo(entity.getCode()).andPasswordEqualTo(entity.getPassword());
        List<User> users = getService().selectByExample(example);
        // int count= getService().countByExample(example);
        if (users.size() > 0) {
            WebUtils.createSession();

            User user = users.get(0);
            user.setPassword(null);
            WebUtils.setSessionAttribute("loginUser", user);

            return user;
        }
        return null;
    }
}
