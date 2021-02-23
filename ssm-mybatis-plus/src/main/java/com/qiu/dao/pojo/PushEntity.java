package com.qiu.dao.pojo;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

public class PushEntity {
    @ExcelProperty("用户ID")
    private String id;

    public PushEntity() {
    }

    public PushEntity(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
