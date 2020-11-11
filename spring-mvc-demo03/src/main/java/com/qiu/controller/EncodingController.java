package com.qiu.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class EncodingController {
    @RequestMapping("/e/t")
    public String test(String name, Model model){
        System.out.println(name);
        model.addAttribute("msg",name);
        return "hello";
    }
}
