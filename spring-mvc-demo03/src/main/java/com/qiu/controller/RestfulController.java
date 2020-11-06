package com.qiu.controller;

import com.qiu.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class RestfulController {
    @RequestMapping("r1/{a}/{b}")
    public String add(@PathVariable int a, @PathVariable String b, Model model) {
        String result = a + b;
        model.addAttribute("msg", "result：" + result);
        return "add";
    }

    //传参obj
    @RequestMapping("user")
    public String add(User user, Model model) {
        model.addAttribute("msg", "result：" + user.toString());
        return "hello";
    }
}
