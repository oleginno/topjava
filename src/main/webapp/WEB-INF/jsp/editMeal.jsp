<%--
  Created by IntelliJ IDEA.
  User: oleg
  Date: 17.07.17
  Time: 21:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<head>
    <%--<jsp:useBean id="meal" type="ru.javawebinar.topjava.model.MealWithExceed" scope="request"/>--%>
        <%--<c:set var="meal" value="${param.id}" />--%>
    <title>
        Meal Data Editing: ${param["description"]}
    </title>
        <%--<input type="datetime-local" value="${meal.dateTime}" name="time">--%>
</head>
<body>
<section>
    <form id="meal" method="POST" action="mealServlet">
        <input type="hidden" name="action" value="edit">
        <input type="hidden" name="id" value="${param["id"]}">
        <dl>
            <dt>Date/Time:</dt>
            <dd><input name="dateTime" size=70 value="${param["dateTime"]}"></dd>
        </dl>
        <dl>
            <dt>Description:</dt>
            <dd><input name="description" size=70 value="${param["description"]}"></dd>
        </dl>
        <dl>
            <dt>Calories:</dt>
            <dd><input name="calories" type="number" min="1" size=70 value="${param["calories"]}"></dd>
        </dl>
        <hr>
        <button>Save</button>
        <button onclick="window.history.back()">Cancel</button>
    </form>
</section>
</body>
</html>

<%--datetime-local--%>