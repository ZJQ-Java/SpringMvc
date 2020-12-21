package com.qiu.util;


import com.qiu.dao.pojo.Book;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class ExportUtil {
//    private static final Logger logger               = LoggerFactory.getLogger(ExportUtil.class);
    /**
     * CSV文件列分隔符
     */
    private static final String CSV_COLUMN_SEPARATOR = ",";

    /**
     * CSV文件列分隔符
     */
    private static final String CSV_RN = "\r\n";

    /**
     * @param dataList 集合数据
     * @param title    表头部数据
     * @param mapKey   查找的对应数据
     */
    public static boolean doCSVExport(List<Map<Integer, Book>> dataList, String title, String mapKey,
            OutputStream os) {
        try {
            StringBuffer buf = new StringBuffer();

            String[] colNamesArr = null;
            String[] mapKeyArr = null;

            colNamesArr = title.split(",");
            mapKeyArr = mapKey.split(",");

            // 完成数据csv文件的封装
            // 输出列头
            for (int i = 0; i < colNamesArr.length; i++) {
                buf.append(colNamesArr[i]).append(CSV_COLUMN_SEPARATOR);
            }
            buf.append(CSV_RN);

            if (null != dataList) { // 输出数据
                for (int i = 0; i < dataList.size(); i++) {
                    for (int j = 0; j < mapKeyArr.length; j++) {
                        Map<Integer, Book> integerBookMap = dataList.get(i);
                        Book book = integerBookMap.get(Integer.parseInt(mapKeyArr[j]));
                        buf.append(book.getId()).append(CSV_COLUMN_SEPARATOR)
                                .append(book.getBookName()).append(CSV_COLUMN_SEPARATOR)
                                .append(book.getBookCounts()).append(CSV_COLUMN_SEPARATOR)
                                .append(book.getDetail());
                        buf.append(CSV_RN);
                    }
                }
            }
            // 写出响应
            os.write(buf.toString().getBytes("GBK"));
            os.flush();
            return true;
        } catch (Exception e) {
//            logger.error("doExport错误...", e);
        }
        return false;
    }

    /**
     * @throws UnsupportedEncodingException setHeader
     */
    public static void responseSetProperties(String fileName, HttpServletResponse response)
            throws UnsupportedEncodingException {
        // 设置文件后缀
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String fn = fileName + sdf.format(new Date()).toString() + ".csv";
        // 读取字符编码
        String utf = "UTF-8";

        // 设置响应
        response.setContentType("application/ms-txt.numberformat:@");
        response.setCharacterEncoding(utf);
        response.setHeader("Pragma", "public");
        response.setHeader("Cache-Control", "max-age=30");
        response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(fn, utf));
    }

    public static Workbook writeExcel(Workbook excel, List<String> titles, List<Map<Integer, Book>> dataList) {
        if (excel == null) {
            excel = new SXSSFWorkbook(new XSSFWorkbook());
        }
        Sheet sheet = excel.createSheet();
        int rowIndex = 0;
        org.apache.poi.ss.usermodel.Row headRow = sheet.createRow(rowIndex);
        //设置表头
        for (int colIndex = 0; colIndex < titles.size(); colIndex++) {
            sheet.setColumnWidth(colIndex, 6000);

            //生成字体格式
            Font font = excel.createFont();
            font.setBold(true);
            font.setColor(Font.COLOR_RED);

            //生成cell风格
            CellStyle style = excel.createCellStyle();
            style.setFont(font);

            //设置cell
            org.apache.poi.ss.usermodel.Cell cell = headRow.createCell(colIndex);
            cell.setCellStyle(style);
            cell.setCellValue(titles.get(colIndex));
        }
        ++rowIndex;
        int col = 0;
        //设置表数据
        if (dataList != null) {
            for (Map<Integer, Book> map : dataList) {
                for (int i = 1; i <= 5; i++) {
                    Book book = map.get(i);
                    Row row = sheet.createRow(rowIndex++);
                    col = 0;

                    Cell cell = row.createCell(col++);
                    cell.setCellValue(book.getId());
                    cell = row.createCell(col++);
                    cell.setCellValue(book.getBookName());
                    cell = row.createCell(col++);
                    cell.setCellValue(book.getBookCounts());
                    cell = row.createCell(col++);
                    cell.setCellValue(book.getDetail());
                }
            }

        }
        return excel;
    }
}
