package com.qiu.server;

import com.qiu.dao.pojo.Book;

import java.util.List;

public interface BookService {
    public List<Book> getAllBooks();

    void testTransaction(Book book);
}
