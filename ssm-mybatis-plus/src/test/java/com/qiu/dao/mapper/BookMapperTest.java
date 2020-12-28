package com.qiu.dao.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.qiu.dao.pojo.Book;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = {"classpath:spring.xml"})
public class BookMapperTest {
    @Autowired
    private DataSource dataSource;
    @Autowired
    private BookMapper bookMapper;
    @Test
    public void testDataSource() throws SQLException {
        System.out.println(dataSource.getConnection());
    }
    @Test
    public void  testMybatis(){
        List<Book> books = Collections.singletonList(bookMapper.selectById(1));
        System.out.println(books);
    }

    @Test
    public void  testMybatis1(){
//        Book book = new Book();
//        book.setId(5);
//        book.setBookCounts(5);
//        book.setBookName("JVM虚拟机");
//        book.setDetail("JV9M,TAOBAO VM hotspot");
//        int insert = bookMapper.insert(book);
//        System.out.println(insert);
        QueryWrapper<Book> book1 = new QueryWrapper<Book>();
        book1.ge("id",5);
        Book book2 = bookMapper.selectOne(book1);
        System.out.println(book2);
    }


    @Test
    public void  testMybatis2(){
        UpdateWrapper<Book> book1 = new UpdateWrapper<Book>();
        //column 使用的是mysql中的值
        book1.ge("id",5).set("book_counts",100);
        int updateResult = bookMapper.update(null,book1);
        System.out.println(updateResult);
    }
}
