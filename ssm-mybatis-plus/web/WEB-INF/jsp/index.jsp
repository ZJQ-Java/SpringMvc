<%--
  Created by IntelliJ IDEA.
  User: zhangjinqiu03
  Date: 2020/12/26
  Time: 16:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>$Title$</title>
  </head>
  <body>
  <form action="${pageContext.request.contextPath}/upload/books" enctype="multipart/form-data" method="post">
    <table>
      <tr>
        <td>请选择任意格式文件：</td>
        <td><input type="file" name="file"></td>
      </tr>
      <tr>
        <td>开始上传</td>
        <td><input type="submit" value="上传"></td>
      </tr>
    </table>
  </form>

  <form action="${pageContext.request.contextPath}/upload/excel" enctype="multipart/form-data" method="post">
    <table>
      <tr>
        <td>请选择上传的excel文件：</td>
        <td><input type="file" name="file"></td>
      </tr>
      <tr>
        <td>开始上传</td>
        <td><input type="submit" value="上传"></td>
      </tr>
    </table>
  </form>

  <form action="${pageContext.request.contextPath}/upload/easyExcel" enctype="multipart/form-data" method="post">
    <table>
      <tr>
        <td>请选择上传的excel文件：easyExcel方式</td>
        <td><input type="file" name="file"></td>
      </tr>
      <tr>
        <td>开始上传</td>
        <td><input type="submit" value="上传"></td>
      </tr>
    </table>
  </form>
  </body>
</html>
