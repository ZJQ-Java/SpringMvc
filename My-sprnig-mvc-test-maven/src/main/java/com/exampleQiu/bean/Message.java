package com.exampleQiu.bean;

/**
 * @Classname Message
 * @Description TODO
 * @Date 2019/1/1 16:55
 * @Created by Windows10
 */
public class Message {
    private int id;

    public Message(int id, String name, String password) {
        this.id = id;
        this.name = name;
        this.password = password;
    }

    private String name;

    private String password;

    @Override
    public String toString() {
        return "Message{" +
                "name='" + name + '\'' +
                ", id=" + id +
                ", password='" + password + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
