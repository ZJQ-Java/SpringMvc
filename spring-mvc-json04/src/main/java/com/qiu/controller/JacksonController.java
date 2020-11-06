package com.qiu.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.qiu.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
public class JacksonController {
    @RequestMapping("/j1")
    public String jacksonTest(){
        User user = new User(1,"2",18);
        return user.toString();
    }
    @RequestMapping("/j2")
    public String jacksonTest1() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        User user = new User(1,"张金秋",18);

        return mapper.writeValueAsString(user);
    }
    //处理中文乱码
    @RequestMapping(value = "/j3",produces = "application/json;charset=utf-8")
    public String jacksonTest3() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        User user = new User(1,"张金秋",18);

        return mapper.writeValueAsString(user);
    }
    //处理中文乱码
    @RequestMapping(value = "/j4")
    public String jacksonTest4() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        User user = new User(1,"张金秋",18);

        return mapper.writeValueAsString(user);
    }

    @RequestMapping("/j5")
    public String json4() throws JsonProcessingException {

        ObjectMapper mapper = new ObjectMapper();

        //不使用时间戳的方式
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        //自定义日期格式对象
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //指定日期格式
        mapper.setDateFormat(sdf);

        Date date = new Date();

        return mapper.writeValueAsString(date);
    }
}
