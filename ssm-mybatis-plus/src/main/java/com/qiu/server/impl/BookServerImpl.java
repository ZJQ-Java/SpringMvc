package com.qiu.server.impl;

import com.qiu.dao.mapper.BookMapper;
import com.qiu.dao.pojo.Book;
import com.qiu.server.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServerImpl implements BookService {
    @Autowired
    private BookMapper bookMapper;

    @Override
    public List<Book> getAllBooks() {
        return bookMapper.selectList(null);
    }
    @Override
    public void testTransaction(Book book) {
//        bookMapper.addBooks(book);
//        bookMapper.delBooks(1);
    }
}
