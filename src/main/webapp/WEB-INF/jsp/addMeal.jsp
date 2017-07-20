<%--
  Created by IntelliJ IDEA.
  User: oleg
  Date: 17.07.17
  Time: 22:18
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<head>
    <title>
        Adding New Meal...
    </title>
</head>
<body>
<section>
    <form action="${pageContext.request.contextPath}mealServlet" method="POST" >
        <input type="hidden" name="action" value="add">
        <dl>
            <dt>Date/Time:</dt>
            <dd><input type="datetime-local" name="dateTime" value="" size=70></dd>
        </dl>
        <dl>
            <dt>Description:</dt>
            <dd><input name="description" value="" size=70></dd>
        </dl>
        <dl>
            <dt>Calories:</dt>
            <dd><input name="calories" type="number" value="" min="1" size=70></dd>
        </dl>
        <hr>
        <button>SAVE</button>
        <button onclick="window.history.back()">CANCEL</button>
    </form>
</section>
</body>
</html>
