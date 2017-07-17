
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<head>
    <title>Meal List</title>
</head>

<body>
<h1 align="center">User Meals</h1>
<p>
<table align="center">
    <tr>
        <td colspan="6" style="text-align: right"><a
                href="${pageContext.request.contextPath}/mealServlet?action=add">
            <img src="${pageContext.request.contextPath}/img/add.png" width="55" height="55">
        </a></td>
    </tr>
    <tr>
        <td>
            <table border="2" cellpadding="7" cellspacing="1">
                <tr>
                    <th width="30">#</th>
                    <th width="150">Date</th>
                    <th width="170">Description</th>
                    <th width="55">Calories</th>
                    <th width="40">DEL</th>
                    <th width="40">EDIT</th>
                </tr>
                <c:forEach items="${requestScope.meals}" var="meal" varStatus="mealLoopCount">
                    <jsp:useBean id="meal" type="ru.javawebinar.topjava.model.MealWithExceed"/>

                    <tr style="color: ${meal.exceed ? 'red' : 'limegreen'}">
                        <td align="center">
                                ${mealLoopCount.count}
                        </td>
                        <td>
                                ${meal.dateTime}
                        </td>
                        <td>
                                ${meal.description}
                        </td>
                        <td align="center">
                                ${meal.calories}
                        </td>
                        <td align="center">
                            <a href="mealServlet?id=${meal.id}&action=delete">
                                <img src="${pageContext.request.contextPath}/img/del.png" width="14" height="14">
                            </a>
                        </td>
                        <td align="center">
                            <a href="mealServlet?id=${meal.id}&action=edit">
                                <img src="${pageContext.request.contextPath}/img/edit.png" width="21" height="21">
                            </a>
                        </td>
                    </tr>
                </c:forEach>
            </table>
        </td>
    </tr>
</table>
</body>
</html>
