package com.exampleQiu.service;

import com.exampleQiu.bean.Message;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @Classname MessageImpl
 * @Description TODO
 * @Date 2019/1/1 17:01
 * @Created by Windows10
 */
@Service
@Scope("prototype")

public class MessageImpl implements MessageService {
    private Map<Integer,Message> map = GetMap();

    private Map<Integer,Message> GetMap() {
        Map<Integer,Message> map = new HashMap<>();
        map.put(1,new Message(1,"zjq","11"));
        map.put(2,new Message(2,"zzh","12"));
        map.put(3,new Message(3,"qiu","13"));
        map.put(4,new Message(4,"yuan","15"));
        map.put(5,new Message(5,"bao","14"));
        return map;

    }

    @Override
    public Map<Integer, Message> insert(Message message) {
        map.put(message.getId(),message);
        return map;
    }

    @Override
    public Map<Integer, Message> delete(int id) {
        map.remove(id);
        return map;
    }

    @Override
    public Map<Integer, Message> selectAll() {
        return map;
    }

    @Override
    public Message selectById(int id) {
        return map.get(id);
    }
}
