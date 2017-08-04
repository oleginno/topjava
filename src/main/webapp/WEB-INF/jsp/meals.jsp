<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://topjava.javawebinar.ru/functions" %>

<html>
<head>
    <title>Meal list</title>
</head>

<body>
<h1 align="center">User Meals</h1>

<section>
    <h3><a href="${pageContext.request.contextPath}">Logout</a></h3>
    <hr>
    <form action="meals" method="POST">
        <input type="hidden" name="action" value="filter">
        <input type="hidden" name="userId" value="${requestScope["userId"]}">
        From Date: <input type="date" name="fromDate" title="FromDate"> &emsp; &emsp;
        From Time: <input type="time" name="fromTime" title="FromTime">
        <br/>
        To Date: <input type="date" name="toDate" title="ToDate"> &emsp; &emsp; &emsp;
        To Time: <input type="time" name="toTime" title="ToTime"> &emsp; &emsp; &emsp;
        <input type="submit" value="Filter"/>
    </form>
</section>
<hr>
<p>
<table align="center">
    <tr>
        <td colspan="6" style="text-align: right">
            <form action="${pageContext.request.contextPath}/mealForm.jsp" method="post">
                <button name="add" value="create">
                    <img src="${pageContext.request.contextPath}/img/add.png" width="52" height="52">
                </button>
                <input type="hidden" name="userId" value="${requestScope.userId}">
                <input type="hidden" name="action" value="create">
            </form>
        </td>
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
                    <jsp:useBean id="meal" type="ru.javawebinar.topjava.to.MealWithExceed"/>

                    <tr style="color: ${meal.exceed ? 'red' : 'limegreen'}">
                        <td align="center">
                                ${mealLoopCount.count}
                        </td>
                        <td>
                                ${fn:formatDateTime(meal.dateTime)}
                        </td>
                        <td>
                                ${meal.description}
                        </td>
                        <td align="center">
                                ${meal.calories}
                        </td>
                        <td>
                            <form action="${pageContext.request.contextPath}/meals" method="post">
                                <button name="delete" value="delete">
                                    <img src="${pageContext.request.contextPath}/img/del.png" width="23" height="23">
                                </button>
                                <input type="hidden" name="id" value="${meal.id}">
                                <input type="hidden" name="userId" value="${meal.userId}">
                                <input type="hidden" name="action" value="delete">
                            </form>
                        </td>
                        <td>
                            <form action="${pageContext.request.contextPath}/mealForm.jsp" method="POST">
                                <button name="edit" value="edit">
                                    <img src="${pageContext.request.contextPath}/img/edit.png" width="27" height="27">
                                </button>
                                <input type="hidden" name="id" value="${meal.id}">
                                <input type="hidden" name="userId" value="${meal.userId}">
                                <input type="hidden" name="action" value="edit">
                                <input type="hidden" name="dateTime" value="${meal.dateTime}">
                                <input type="hidden" name="description" value="${meal.description}">
                                <input type="hidden" name="calories" value="${meal.calories}">
                            </form>
                        </td>
                    </tr>
                </c:forEach>
            </table>
        </td>
    </tr>
</table>
</body>
</html>