package com.qiu.MyException;

public class CustomException extends Exception {
    String msg;

    public CustomException() {
    }

    public CustomException(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "CustomException{" +
                "msg='" + msg + '\'' +
                '}';
    }
}
