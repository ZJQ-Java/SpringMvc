package com.qiu.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {
    @RequestMapping(value = {"", "/"}) //url
    public String index() {
        return "index";
    }
}
