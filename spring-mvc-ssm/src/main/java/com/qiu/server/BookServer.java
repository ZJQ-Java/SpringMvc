package com.qiu.server;

import com.qiu.dao.pojo.Book;

import java.util.List;

public interface BookServer {
    public List<Book> getAllBooks();
}
