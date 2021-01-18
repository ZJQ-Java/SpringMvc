package com.qiu.server;

import com.qiu.dao.pojo.User;

public interface UserService {
    User doUserLogin(User user);
}
