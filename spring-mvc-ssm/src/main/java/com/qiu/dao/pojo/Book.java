package com.qiu.dao.pojo;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Book {

    private int    bookID;
    private String bookName;
    private int    bookCounts;
    private String detail;

}
