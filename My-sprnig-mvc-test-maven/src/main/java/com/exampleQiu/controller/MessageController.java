package com.exampleQiu.controller;

import com.exampleQiu.service.MessageService;
import com.exampleQiu.bean.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @Classname MessageController
 * @Description TODO
 * @Date 2019/1/1 17:10
 * @Created by Windows10
 */
@Controller
@Scope("prototype")
@RequestMapping("/message")
public class MessageController {
    @Autowired
    private MessageService messageService;

    @RequestMapping("/list")
    public String MessageList(Model model) {
        Map<Integer,Message> map = messageService.selectAll();
        model.addAttribute("map",map);
        return "message";
    }
//    /message/delete?id=
    @RequestMapping("/delete")
    public String MessageDelete(@RequestParam Integer id, Model model){
        Map<Integer,Message> map = messageService.delete(id);
        model.addAttribute("map",map);
        return "message";
    }
    @RequestMapping("/insert")
    public String MessageInsert(@RequestParam Integer id, @RequestParam String name, @RequestParam String password, Model model){
        Map<Integer,Message> map = messageService.insert(new Message(id,name,password));
        model.addAttribute("map",map);
        return "message";
    }
    @RequestMapping("/selectById/{id}")
    public String MessageSelectById(@PathVariable Integer id , Model model){
        Message message = messageService.selectById(id);
        model.addAttribute("message",message);
        return "message1";
    }

//    Json数据
    @RequestMapping(value="/{id}",method = RequestMethod.GET)
    public @ResponseBody Message GetJson(@PathVariable Integer id){
        return  messageService.selectById(id);
    }

    @RequestMapping(value="JsonMap",method = RequestMethod.GET)
    public @ResponseBody Map<Integer, Message> GetAll(){
        return messageService.selectAll();
    }

    //与上面对应两种方式，下面是泛型的方法
    @RequestMapping(value="/jsonType/{id}",method = RequestMethod.GET)
    public ResponseEntity<Message> getMessageWithJson(@PathVariable Integer id){
        Message message = messageService.selectById(id);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @RequestMapping(value="/jsonTypeAll",method = RequestMethod.GET)
    public ResponseEntity<Map<Integer,Message>> getAllMessageWithJson(){
        Map<Integer,Message> map =messageService.selectAll();
        return new ResponseEntity<>(map, HttpStatus.OK);
    }
}
