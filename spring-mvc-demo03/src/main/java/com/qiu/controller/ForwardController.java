package com.qiu.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ForwardController {
    @RequestMapping("f1")
    public String testForward() {
        return "hello";
    }


    @RequestMapping("redirect")
    public String testRedirect1() {
        return "redirect:/h1";
    }

}
