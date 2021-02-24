package com.qiu.util;

import org.apache.shiro.crypto.hash.SimpleHash;

public class MD5Util {
    public static String getMD5(String password, String salt) {
        return new SimpleHash("MD5", password, salt, 2).toString();
    }
}
