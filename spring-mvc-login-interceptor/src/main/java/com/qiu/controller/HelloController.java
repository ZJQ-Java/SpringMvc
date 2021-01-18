package com.qiu.controller;

import com.qiu.annotation.HelloAnnotation;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/hello")
public class HelloController {

    //真实访问地址 : 项目名/HelloController/hello
    @RequestMapping("/h1")
    public String sayHello(Model model){
        System.out.println("eeeeeeeeeeee");
        //向模型中添加属性msg与值，可以在JSP页面中取出并渲染
        model.addAttribute("msg","hello,SpringMVC by annotation");
        //web-inf/jsp/hello.jsp
        test();
        return "hello";
    }
    @HelloAnnotation(age = "18",name = "test")
    private void test(){
        System.out.println("test Annotation");
    }
}