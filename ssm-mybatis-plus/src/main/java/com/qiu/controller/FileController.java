package com.qiu.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.exception.ExcelDataConvertException;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.fastjson.JSON;
import com.qiu.dao.pojo.Book;
import com.qiu.dao.pojo.PushEntity;
import com.qiu.listen.ExcelListen;
import com.qiu.util.ExportUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
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
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.*;
import java.util.stream.Collectors;

@Controller
public class FileController {
    private static final Logger log = LoggerFactory.getLogger(FileController.class);

    public static List<Map<Integer, Book>> dataList = new ArrayList<Map<Integer, Book>>();

    static {
        Map<Integer, Book> dataBase = null;
        for (int i = 0; i < 1; i++) {
            dataBase = new HashMap<>();
            for (int j = 1; j <= 5; j++) {
                dataBase.put(j, new Book(j, "bookName" + j, j, "detail" + j));
            }
            dataList.add(dataBase);
        }
//        System.out.println(dataList);
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


    @RequestMapping("/upload/books")
    public String uploadBooks(MultipartFile file) throws IOException {
        ExcelListen<Book> excelListen = new ExcelListen<>();
        try {
            EasyExcel.read(file.getInputStream(), Book.class, excelListen).sheet().doRead();
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
                log.error(errorMsg);
            }

        }
        List<Book> list = excelListen.list;
        if (list == null || list.isEmpty()) {
            return "error";
        }
        log.info("list:" + JSON.toJSONString(list));
        return "success";
    }

    @RequestMapping("/download/books/csv")
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
        long endTime = System.currentTimeMillis();
        System.out.println("csv time:" + (endTime - startTime));

    }

    @RequestMapping("/download/books/excel")
    public void excelTest(HttpServletResponse response) throws IOException {
        long startTime = System.currentTimeMillis();
        String title = "书id,书名,书的数量,书的详细信息";
        Workbook workbook = ExportUtil.writeExcel(null, Arrays.asList(title.split(",")), dataList);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/vnd.ms-excel");
        response.addHeader("Content-Disposition", "attachment; filename=" + "test" + ".xlsx");
        workbook.write(response.getOutputStream());
        workbook.close();
        long endTime = System.currentTimeMillis();
        System.out.println("excel time:" + (endTime - startTime));

    }

    @RequestMapping("/download/books/easyExcel")
    public String easyExcelTest(HttpServletResponse response) throws IOException {
        long startTime = System.currentTimeMillis();
        // 写法1
        /* String fileName = TestFileUtil.getPath() + "simpleWrite" + System.currentTimeMillis() + ".xlsx";
        // 这里 需要指定写用哪个class去写，然后写到第一个sheet，名字为模板 然后文件流会自动关闭
        // 如果这里想使用03 则 传入excelType参数即可
        EasyExcel.write(fileName, UploadExcelEntity.class).sheet("模板").doWrite(dataList);*/

        // 写法2
        /*fileName = TestFileUtil.getPath() + "simpleWrite" + System.currentTimeMillis() + ".xlsx";
        // 这里 需要指定写用哪个class去写
        ExcelWriter excelWriter = null;
        try {
            excelWriter = EasyExcel.write(fileName, UploadExcelEntity.class).build();
            WriteSheet writeSheet = EasyExcel.writerSheet("模板").build();
            excelWriter.write(dataList, writeSheet);
        } finally {
            // 千万别忘记finish 会帮忙关闭流
            if (excelWriter != null) {
                excelWriter.finish();
            }
        }*/
        // 这里注意 有同学反应使用swagger 会导致各种问题，请直接用浏览器或者用postman
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
        String fileName = URLEncoder.encode("测试", "UTF-8").replaceAll("\\+", "%20");
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
        List<Book> dataList = FileController.dataList.stream()
                .map(Map::values)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
        log.debug("dataList:" + dataList);
        EasyExcel.write(response.getOutputStream(), Book.class).sheet("模板").doWrite(dataList);
        long endTime = System.currentTimeMillis();
        System.out.println("excel time:" + (endTime - startTime));
        return "success";

    }


    @RequestMapping("/upload/excel")
    public String uploadExcelHSSWorkBook(MultipartFile file) throws Exception {
        long startTime = System.currentTimeMillis();
        String fileName = file.getOriginalFilename();
        if (fileName == null) {
            return "error";
        }
        String[] nameAndExt = fileName.split("\\.");
        if (nameAndExt.length != 2) {
            log.error("File name resolution failed, fileName = " + fileName);
            return "error";
        }
        String ext = nameAndExt[1];
        if (!"xls".equals(ext) && !"xlsx".equals(ext)) {
            log.error("格式错误");
            return "error";
        }
        List<String> list = new ArrayList<>(readExcel(file.getInputStream(), ext));
        if (list.isEmpty() || list.size() > 20000) {
            log.error("文件过大");
            return "error";
        }
        log.info("cost time: " + (System.currentTimeMillis() - startTime) + "毫秒");
        log.info("normal upload：" + list.size());
        return "success";
    }

    @RequestMapping("/upload/easyExcel")
    public String uploadEasyExcel(MultipartFile file) throws Exception {
        long startTime = System.currentTimeMillis();
        String fileName = file.getOriginalFilename();
        if (fileName == null) {
            return "error";
        }
        String[] nameAndExt = fileName.split("\\.");
        if (nameAndExt.length != 2) {
            log.error("File name resolution failed, fileName = " + fileName);
            return "error";
        }
        String ext = nameAndExt[1];
        if (!"xls".equals(ext) && !"xlsx".equals(ext)) {
            log.error("格式错误");
            return "error";
        }
        ExcelListen<PushEntity> excelListen = new ExcelListen<PushEntity>();
        try {
            EasyExcel.read(file.getInputStream(), PushEntity.class, excelListen).sheet().doRead();
        } catch (Exception e) {
            log.error("read error e:" + e.getMessage(), e);
        }
        List<String> list = excelListen.list.stream().map(PushEntity::getId).collect(Collectors.toList());

        EasyExcel.write("/tmp/" + fileName + System.currentTimeMillis() + ".xlsx", PushEntity.class).sheet("用户ID")
                .doWrite(excelListen.list);

        if (list.isEmpty() || list.size() > 20000) {
            log.error("文件过大");
            return "error";
        }
        log.info("cost time: " + (System.currentTimeMillis() - startTime) + "毫秒");

        log.info("list：" + list.size());
        return "success";
    }

    private List<String> readExcel(InputStream is, String pattern) throws Exception {
        if (is == null) {
            return null;
        }
        //生成Excel对象
        Workbook excel;
        if ("xlsx".equalsIgnoreCase(pattern)) {
            excel = new XSSFWorkbook(is);
        } else {
            excel = new HSSFWorkbook(is);
        }
        Sheet sheet = excel.getSheetAt(0);

        List<String> cusIdList = new ArrayList<>(5000);
        for (Row row : sheet) {
            Cell cell = row.getCell(0);
            if (cell == null) {
                continue;
            }
            cell.setCellType(CellType.STRING);
            cusIdList.add(cell.getStringCellValue());
        }
        return cusIdList.stream().parallel().skip(1).filter(e -> e != null && !e.trim().isEmpty())
                .collect(Collectors.toList());
    }


    public static void main(String[] args) throws IOException {
        String fileName = "/tmp/simpleWrite" + System.currentTimeMillis() + ".xlsx";
        // 这里 需要指定写用哪个class去写，然后写到第一个sheet，名字为模板 然后文件流会自动关闭
        // 如果这里想使用03 则 传入excelType参数即可\
//        List<Book> dataList = FileController.dataList.stream()
//                .map(Map::values)
//                .flatMap(Collection::stream)
//                .collect(Collectors.toList());
//        System.out.println("dataList:" + dataList);
        List<PushEntity> list = new ArrayList<>();
        list.add(new PushEntity("124354657665423"));
        list.add(new PushEntity("2"));
        list.add(new PushEntity("3"));
        list.add(new PushEntity("4"));
        EasyExcel.write(fileName, PushEntity.class).sheet("模板").doWrite(list);
    }
}
