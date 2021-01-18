package com.qiu.server.impl;

import com.qiu.dao.mapper.UserMapper;
import com.qiu.dao.pojo.User;
import com.qiu.dao.pojo.UserExample;
import com.qiu.server.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public User doUserLogin(User user) {
        UserExample where = new UserExample();
        where.createCriteria().andUsernameEqualTo(user.getUsername()).andPasswordEqualTo(user.getPassword());
        List<User> list = userMapper.selectByExample(where);
        return list == null || list.isEmpty() ? null : list.get(0);
    }
}
