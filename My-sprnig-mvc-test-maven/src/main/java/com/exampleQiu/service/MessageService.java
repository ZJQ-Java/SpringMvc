package com.exampleQiu.service;

import com.exampleQiu.bean.Message;

import java.util.Map;

/**
 * @Classname MessageService
 * @Description TODO
 * @Date 2019/1/1 16:57
 * @Created by Windows10
 */
public interface MessageService {
    public Map<Integer,Message> insert(Message message);
    public Map<Integer,Message> delete(int id);
    public Map<Integer,Message> selectAll();
    public Message selectById(int id);

}
