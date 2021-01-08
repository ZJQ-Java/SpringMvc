package com.qiu.controller;

import com.alibaba.fastjson.JSON;
import com.qiu.MyException.CustomException;
import com.qiu.dao.pojo.Book;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.URL;
import java.util.Map;

@RestController
@RequestMapping("/post")
public class PostController {

    @RequestMapping(value="/test", method= RequestMethod.POST)
    public void testPost(HttpServletRequest request){
        System.out.println("requestURL:" + request.getRequestURL());
        System.out.println(request.getServletPath());
        String a = request.getParameter("geta");
        String b = request.getParameter("getb");
        String c = request.getParameter("getc");
        System.out.println("a:" + a +" b:" + b + " c:" +c) ;

        System.out.println(request.getQueryString());
        Map<String, String[]> map = request.getParameterMap();
        System.out.println(JSON.toJSONString(map));
    }

    @RequestMapping(value="/form", method= RequestMethod.POST)
    public void testPostForm(Book book){
        System.out.println(book);
    }

    @RequestMapping(value="/json", method= RequestMethod.POST)
    public void testPostJson(@RequestBody Book book) throws Exception {
        throw new CustomException("test exception");
//        System.out.println(book);
    }

    public static void main(String[] args) throws IOException {
        URL url = new URL("http://www.runoob.com/post/test?language=cn#j2se");
        System.out.println("URL 为：" + url.toString());
        System.out.println("协议为：" + url.getProtocol());
        System.out.println("验证信息：" + url.getAuthority());
        System.out.println("文件名及请求参数：" + url.getFile());
        System.out.println("主机名：" + url.getHost());
        System.out.println("路径：" + url.getPath());
        System.out.println("端口：" + url.getPort());
        System.out.println("默认端口：" + url.getDefaultPort());
        System.out.println("请求参数：" + url.getQuery());
        System.out.println("定位位置：" + url.getRef());
    }
}
