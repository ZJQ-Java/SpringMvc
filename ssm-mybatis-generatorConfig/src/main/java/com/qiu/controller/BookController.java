package com.qiu.controller;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qiu.MyException.CustomException;
import com.qiu.dao.pojo.Book;
import com.qiu.server.BookServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;


@Controller
public class BookController {
    private static  final Logger log = LoggerFactory.getLogger(BookController.class);

    @Autowired
    BookServer bookServer;

    @RequestMapping(value = "book/gets")
    @ResponseBody
    public String getBooks(Model model) {
        List<Book> allBooks = bookServer.getAllBooks();
        /*model.addAttribute("allBooks",allBooks);
        return "allBooks";*/
        return JSON.toJSONString(allBooks);
    }

    @GetMapping("book/gets1")
    @ResponseBody
    public String getBooks1() throws JsonProcessingException {
        List<Book> allBooks = bookServer.getAllBooks();
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(allBooks);
    }

    @GetMapping("book/test")
    @ResponseBody
    public String testTransaction(Book book) throws JsonProcessingException {
        System.out.println(book);
        bookServer.testTransaction(book);
        return "success";
    }

    @GetMapping("book/exception")
    public String testException() throws CustomException {
        throw new CustomException("test");
    }

}
