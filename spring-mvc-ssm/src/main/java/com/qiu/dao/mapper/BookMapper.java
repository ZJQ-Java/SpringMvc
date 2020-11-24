package com.qiu.dao.mapper;

import com.qiu.dao.pojo.Book;

import java.util.List;

public interface BookMapper {
    public List<Book> getAllBooks();

    public int addBooks(Book book);

    public int delBooks(int id);

}
