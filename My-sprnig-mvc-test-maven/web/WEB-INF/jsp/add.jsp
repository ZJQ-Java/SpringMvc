<%--
  Created by IntelliJ IDEA.
  User: Windows10
  Date: 2019/1/1
  Time: 18:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>add </title>
</head>
<body>
<form action="<%=request.getContextPath()%>/user/save" method="post">
   <label for="id"> id: </label> <input type="text" id="id" name="id"><br/>
   <label for="name">name: </label><input type="text" id="name" name="name"><br/>
   <label for="password">password: </label>  <input type="text" id="password" name="password"><br/>
   <input type="submit" value="提交"><br/>
</form>
</body>
</html>
