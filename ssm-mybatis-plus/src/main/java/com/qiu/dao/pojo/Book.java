package com.qiu.dao.pojo;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Book {
    private int    id;
    private String bookName;
    private int    bookCounts;
    private String detail;

    public Book() {
    }

    public Book(int id) {
        this.id = id;
    }

    public Book(int id, String bookName, int bookCounts, String detail) {
        this.id = id;
        this.bookName = bookName;
        this.bookCounts = bookCounts;
        this.detail = detail;
    }
}