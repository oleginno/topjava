<%--
  Created by IntelliJ IDEA.
  User: oleg
  Date: 25.07.17
  Time: 19:51
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>User not found</title>
</head>
<body>

${requestScope['javax.servlet.error.exception']}
<br><br><br>
<b>
    Did you forget a password, bastard?
</b>
<br><br>
<form action="${pageContext.request.contextPath}">
    <input type="submit" value="Try again!"/>
</form>

</body>
</html>
