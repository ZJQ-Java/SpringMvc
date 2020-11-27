package com.qiu.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/user")
public class LoginController {
    @RequestMapping("/login")
    public String login(HttpSession session,String username){
        session.setAttribute("user",username);
        return "success";
    }

    @RequestMapping("/jumpSuccess")
    public String jumpSuccess(){
        return "success";
    }

    @RequestMapping("/jumplogin")
    public String jumplogin(){
        return "login";
    }

    @RequestMapping("/logout")
    public String logout(HttpSession session ){
        session.invalidate();
        return "login";
    }

}
