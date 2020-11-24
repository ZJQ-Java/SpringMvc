package com.qiu.controller;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qiu.dao.pojo.Book;
import com.qiu.server.BookServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@Controller
public class BookController {
    @Autowired
    BookServer bookServer;

    @RequestMapping(value = "book/gets")
    @ResponseBody
    public String getBooks() {
        List<Book> allBooks = bookServer.getAllBooks();
        System.out.println(allBooks);
        return JSON.toJSONString(bookServer.getAllBooks());
    }

    @GetMapping("book/gets1")
    @ResponseBody
    public String getBooks1() throws JsonProcessingException {
        List<Book> allBooks = bookServer.getAllBooks();
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(allBooks);
    }

}
