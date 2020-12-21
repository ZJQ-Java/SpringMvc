package com.qiu.controller;

import com.qiu.dao.pojo.Book;
import com.qiu.util.ExportUtil;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.*;

@Controller
public class FileController {
    //    private static Logger                   logger   = LoggerFactory.getLogger(FileController.class);
//    public static  Map<Integer, Book>       dataBase = new HashMap<>(10000,1f);
    public static List<Map<Integer, Book>> dataList = new ArrayList<Map<Integer, Book>>();


    static {
        Map<Integer, Book> dataBase = null;
        for (int i = 0; i < 1_00_000; i++) {
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
        String sTitle = "书id,书名,书的数量,书的详细信息";
        String fName = "books";
        String mapKey = "1,2,3,4,5";
        long startTime = System.currentTimeMillis();

        try (final OutputStream os = response.getOutputStream()) {
            ExportUtil.responseSetProperties(fName, response);
            ExportUtil.doCSVExport(dataList, sTitle, mapKey, os);

        } catch (Exception e) {
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
