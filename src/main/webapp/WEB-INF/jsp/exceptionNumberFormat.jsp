<%--
  Created by IntelliJ IDEA.
  User: oleg
  Date: 26.07.17
  Time: 14:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Number format error</title>
</head>
<body>
${requestScope['javax.servlet.error.exception']}
<br><br><br>
<b>
    Don't you know a numbers, dear child?
</b>
<br><br>
<button type="button" value="cancel" onclick="window.history.back()">Back</button>
</body>
</html>
