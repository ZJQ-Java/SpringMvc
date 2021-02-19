package com.qiu.dao.pojo;//package com.qiu.dao.pojo;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class UploadExcelEntity {
    /**
     * 强制读取第三个 这里不建议 index 和 name 同时用，要么一个对象只用index，要么一个对象只用name去匹配
     */
    @ExcelProperty("书id")
    private int id;
    /**
     * 用名字去匹配，这里需要注意，如果名字重复，会导致只有一个字段读取到数据
     */
    @ExcelProperty("书名")
    private String  bookName;
    @ExcelProperty("书的数量")
    private int bookCount;
    @ExcelProperty("书的详细信息")
    private String  bookDetail;
}
