<%--
  Created by IntelliJ IDEA.
  User: zhangjinqiu03
  Date: 2019/1/2
  Time: 11:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>this is file upload</title>
</head>
<body>

<form action="<%=request.getContextPath()%>/user/doUpload" method="post" enctype="multipart/form-data">
    <input type="file" name="file"/><br/>
    <input type="submit" name="上传"/>
</form>

</body>
</html>
