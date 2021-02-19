package com.qiu.controller;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qiu.dao.pojo.Book;
import com.qiu.server.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;


@Controller
public class BookController {
    @Autowired
    BookService bookService;

    @RequestMapping(value = "book/gets")
    @ResponseBody
    public String getBooks(Model model) {
        List<Book> allBooks = bookService.getAllBooks();
        /*model.addAttribute("allBooks",allBooks);
        return "allBooks";*/
        return JSON.toJSONString(allBooks);
    }

    @GetMapping("book/gets1")
    @ResponseBody
    public String getBooks1() throws JsonProcessingException {
        List<Book> allBooks = bookService.getAllBooks();
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(allBooks);
    }

    @GetMapping("book/test")
    @ResponseBody
    public String testTransaction(Book book) throws JsonProcessingException {
        System.out.println(book);
        bookService.testTransaction(book);
        return "success";
    }

}
