package com.qiu.controller;

import com.qiu.dao.pojo.User;
import com.qiu.server.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class LoginController {
    private static  final Logger      log = LoggerFactory.getLogger(LoginController.class);
    @Autowired
    private               UserService userService;

    @RequestMapping(value = {"/index", "", "/"}) //url
    public String index(User user, Model model) {
        return "index";
    }

    @RequestMapping("/login") //url
    public String dologin(User user, Model model) {
        String info = loginUserCheck(user);
        if (!"SUCC".equals(info)) {
            model.addAttribute("failMsg", "用户不存在或密码错误！");
            return "fail";
        } else {
            model.addAttribute("successMsg", "登陆成功！");//返回到页面说夹带的参数
            model.addAttribute("name", user.getUsername());
            return "success";//返回的页面
        }
    }

    @RequestMapping("/registered") //url
    public String registered(User user, Model model) {
//        if (!"SUCC".equals(info)) {
//            model.addAttribute("failMsg", "用户不存在或密码错误！");
//            return "fail";
//        } else {
//            model.addAttribute("successMsg", "注册成功！");//返回到页面说夹带的参数
//            model.addAttribute("name", user.getUsername());
//            return "/index";//返回的页面
//        }
        return "/";
    }

    @RequestMapping("/toAdmin") //url
    public String toAdmin(HttpServletResponse response) throws IOException {
        return "admin";
    }

    @RequestMapping("/toUser") //url
    public String toUser() throws IOException {
        return "user";
    }

    @RequestMapping("/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Subject subject = SecurityUtils.getSubject();
        if (subject != null) {
            try {
                subject.logout();
            } catch (Exception ex) {
            }
        }
        response.sendRedirect("/");
    }

    private String loginUserCheck(User user) {
        Subject us = SecurityUtils.getSubject();
        // 如果已经登陆，无需重新登录
        if (us.isAuthenticated()) {
            return "SUCC";
        }
        // 组装token，包括客户公司名称、简称、客户编号、用户名称；密码
        UsernamePasswordToken token = new UsernamePasswordToken(user.getUsername(), user.getPassword().toCharArray(),
                null);
        token.setRememberMe(true);

        // shiro登陆验证
        try {
            SecurityUtils.getSubject().login(token);
        } catch (UnknownAccountException | IncorrectCredentialsException ex) {
            return "用户不存在或者密码错误！";
        } catch (AuthenticationException ex) {
            return ex.getMessage(); // 自定义报错信息
        } catch (Exception ex) {
            ex.printStackTrace();
            return "内部错误，请重试！";
        }
        return "SUCC";
    }

}
