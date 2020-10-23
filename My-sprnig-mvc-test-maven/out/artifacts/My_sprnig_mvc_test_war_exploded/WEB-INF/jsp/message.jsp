<%--
  Created by IntelliJ IDEA.
  User: Windows10
  Date: 2019/1/1
  Time: 17:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<html>
<head>
    <title>this is message</title>
</head>
<body>
<c:forEach items="${map}" var="entry">
    ${entry.key}----${entry.value}<br>
</c:forEach>
</body>
</html>
