package com.exampleQiu.controller;

import com.exampleQiu.bean.Message;
import com.exampleQiu.bean.User;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Map;

/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: UserListController
 * Author:   zhangjinqiu03
 * Date:     2018/12/29 14:53
 */
@Controller
@RequestMapping("/user")
public class UserListController {
//    @CrossOrigin(origins = "http://localhost:9000")
    @RequestMapping("/list")
    public String listUser(){
        System.out.println("listuser");
        return "listUser";
    }
    @RequestMapping("/list1")
    public  String listUser1(Integer id){
        System.out.println(id);
        return "listUser";
    }
    //该方法将处理/user/list2？id=123形式的url  如果家里@Requ
    @RequestMapping("/list2")
    public String listUser2(@RequestParam int id){
        System.out.println(id);
        return "listUser";
    }
    //设置限定那些请求可以访问
    @RequestMapping(value = "/list3",method = RequestMethod.GET)
    public String listUser3(){
        System.out.println("this is Method.Get");
        return "listUser";
    }
//    /user/test/1
    @RequestMapping("test/{id}")
    public String listUser4(@PathVariable Integer id){
        System.out.println("this is Method.Get");
        return "listUser";
    }



    @RequestMapping("/list4")
    public String list1(Map<String,Object> map){
        map.put("username","你");
        return "Userlist";
    }
    @RequestMapping("/list5")
    public String list2(Model model){
        Message message = new Message(1,"zz","dadas");
        model.addAttribute("message",message);
        return "Userlist";
    }
    @RequestMapping(value = "/add",method = RequestMethod.GET)//如果在参数中加params = "das"  那么就要访问user/add？das
    public String add(){
        return "add";
    }

    @RequestMapping(value = "/save",method = RequestMethod.POST)
    public String save(@ModelAttribute User user, Model model){
        System.out.println(user);

        model.addAttribute(user);
        return "success";
    }

    @RequestMapping("/upload")
    public String upload(){
        return "upload";
    }

    @RequestMapping("/doUpload")
    public String doUpload(@RequestParam("file") MultipartFile file) throws IOException {
        if(!file.isEmpty()){
            FileUtils.copyInputStreamToFile(file.getInputStream(),new File("D:/upLoad/",file.getOriginalFilename()));

        }
        return "success";
    }



}
