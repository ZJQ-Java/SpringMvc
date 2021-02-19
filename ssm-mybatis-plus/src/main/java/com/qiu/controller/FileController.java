package com.qiu.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.exception.ExcelDataConvertException;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.read.builder.ExcelReaderBuilder;
import com.alibaba.excel.read.builder.ExcelReaderSheetBuilder;
import com.alibaba.fastjson.JSON;
import com.qiu.dao.pojo.Book;
import com.qiu.dao.pojo.UploadExcelEntity;
import com.qiu.listen.ExcelListen;
import com.qiu.util.ExportUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.*;

@Controller
public class FileController {
    private static final Logger log = LoggerFactory.getLogger(FileController.class);

    public static List<Map<Integer, Book>> dataList = new ArrayList<Map<Integer, Book>>();

    static {
        Map<Integer, Book> dataBase = null;
        for (int i = 0; i < 1; i++) {
            dataBase = new HashMap<>();
            for (int j = 1; j <= 5; j++) {
                dataBase.put(j, new Book(j, "bookName" + j, j, "detail " + j));
            }
            dataList.add(dataBase);
        }
//        System.out.println(dataList);
    }

    @RequestMapping("download/books")
    public void CSVTest(HttpServletResponse response) throws IOException {
        String fName = "books";
        String mapKey = "1,2,3,4,5";
        long startTime = System.currentTimeMillis();
        List<String> titles = Arrays.asList("书id", "书名", "书的数量", "书的详细信息");
        try (OutputStream os = response.getOutputStream()) {
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

    @RequestMapping("upload/books")
    public String uploadBooks(MultipartFile file) throws IOException {
        ExcelListen excelListen = new ExcelListen();
        try {
            ExcelReaderBuilder read = EasyExcel.read(file.getInputStream(), UploadExcelEntity.class, excelListen);
            ExcelReaderSheetBuilder sheet = read.sheet();
            sheet.doRead();
        } catch (Exception e) {
            e.printStackTrace();
            if (e.getCause() instanceof ExcelDataConvertException) {
                ExcelDataConvertException excelDataConvertException = (ExcelDataConvertException) e.getCause();
                String cellMsg = "";
                CellData cellData = excelDataConvertException.getCellData();
                //这里有一个celldatatype的枚举值,用来判断CellData的数据类型
                CellDataTypeEnum type = cellData.getType();
                if (type.equals(CellDataTypeEnum.NUMBER)) {
                    cellMsg = cellData.getNumberValue().toString();
                } else if (type.equals(CellDataTypeEnum.STRING)) {
                    cellMsg = cellData.getStringValue();
                } else if (type.equals(CellDataTypeEnum.BOOLEAN)) {
                    cellMsg = cellData.getBooleanValue().toString();
                }
                String errorMsg = String
                        .format("excel表格:第%s行,第%s列,数据值为:%s,该数据值不符合要求,请检验后重新导入!<span style=\"color:red\">请检查其他的记录是否有同类型的错误!</span>",
                                excelDataConvertException.getRowIndex() + 1, excelDataConvertException.getColumnIndex(),
                                cellMsg);
                System.out.println(errorMsg);
            }

        }
        List<UploadExcelEntity> list = (List<UploadExcelEntity>) excelListen.list;
        if (list == null || list.isEmpty()) {
            return "error";
        }
        log.info("list:" + JSON.toJSONString(list));
        return "success";
    }


    @RequestMapping("/test/path")
    public String testPath(HttpServletRequest req) {
        String contextPath = req.getContextPath();
        String servletPath = req.getServletPath();

        String path = req.getSession().getServletContext().getRealPath("/WEB-INF/upload/");
        System.out.println("contextPath:" + contextPath);//
        System.out.println("servletPath:" + servletPath); // /test/path
        // D:\Java学习\SpringMvc\classes\artifacts\ssm_mybatis_plus_war_exploded\WEB-INF\\upload注释转义
        System.out.println("path:" + path);

        String sessionPath = req.getSession().getServletContext().getContextPath();
        String sessionServletPath = req.getSession().getServletContext().getRealPath("");
        String _sessionServletPath = req.getSession().getServletContext().getRealPath("/");
        System.out.println("sessionPath:" + sessionPath);
        //D:\Java学习\SpringMvc\classes\artifacts\ssm_mybatis_plus_war_exploded\WEB-INF\\upload注释转义
        System.out.println(" sessionServletPath:" + sessionServletPath);
        //D:\Java学习\SpringMvc\classes\artifacts\ssm_mybatis_plus_war_exploded\WEB-INF\\upload\   注释转义
        System.out.println(" _sessionServletPath:" + _sessionServletPath);
        return "success";

    }

    @RequestMapping("/upload/file")
    public String upload(@RequestParam("file") MultipartFile file, HttpServletRequest req)
            throws IllegalStateException, IOException {

        // 判断文件是否为空，空则返回失败页面
        if (file == null || file.isEmpty()) {
            return "error";
        }
        // 获取文件存储路径（绝对路径）

        String path = req.getSession().getServletContext().getRealPath("/WEB-INF/upload/");

        // 获取原文件名
        String fileName = file.getOriginalFilename();
        // 创建文件实例
        File filePath = new File(path, fileName);
        // 如果文件目录不存在，创建目录
        if (!filePath.getParentFile().exists()) {
            filePath.getParentFile().mkdirs();
            System.out.println("创建目录" + filePath);
        }
        // 写入文件
        file.transferTo(filePath);
        return "success";
    }
}
