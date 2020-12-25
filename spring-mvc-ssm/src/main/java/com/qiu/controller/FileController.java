package com.qiu.controller;

import com.qiu.dao.pojo.Book;
import com.qiu.util.ExportUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

@Controller
public class FileController {
    public static List<Map<Integer, Book>> dataList = new ArrayList<Map<Integer, Book>>();

    static {
        Map<Integer, Book> dataBase = null;
        for (int i = 0; i < 1_0; i++) {
            dataBase = new HashMap<>();
            for (int j = 1; j <= 5; j++) {
                dataBase.put(j, new Book(j, "bookName" + j, j, "detail " + j));
            }
            dataList.add(dataBase);
        }
//        System.out.println(dataList);
    }

    @RequestMapping("download/books")
    @ResponseBody
    public void CSVTest(HttpServletResponse response) throws IOException {
        String fName = "books";
        String mapKey = "1,2,3,4,5";
        long startTime = System.currentTimeMillis();
        List<String> titles = Arrays.asList("书id", "书名", "书的数量", "书的详细信息");
        try (PrintWriter os = response.getWriter()) {
            ExportUtil.responseSetProperties(fName, response);
            ExportUtil.doCSVExport1(dataList, titles, mapKey, os);

        } catch (Exception e) {
            e.printStackTrace();
        }
//        Workbook workbook = ExportUtil.writeExcel(null, Arrays.asList(sTitle.split(",")), dataList);
//        response.setCharacterEncoding("UTF-8");
//        response.setContentType("application/vnd.ms-excel");
//        response.addHeader("Content-Disposition", "attachment; filename=" + "test" + ".xlsx");
//        workbook.write(response.getOutputStream());
//        workbook.close();
        long endTime = System.currentTimeMillis();
        System.out.println("time:" + (endTime - startTime));

    }
}
