<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Meal Data</title>
    <style>
        dl {
            background: none repeat scroll 0 0 #FAFAFA;
            margin: 8px 0;
            padding: 0;
        }

        dt {
            display: inline-block;
            width: 170px;
        }

        dd {
            display: inline-block;
            margin-left: 8px;
            vertical-align: top;
        }
    </style>
</head>

<body>
*${param["userId"]}*
<section>
    <h3><a href="${pageContext.request.contextPath}">Logout</a></h3>
    <h2>${param.action == 'create' ? 'Create meal' : 'Edit meal'}</h2>
    <hr>
    <form method="post" action="meals">
        <input type="hidden" name="action" value="${param["action"]}">
        <input type="hidden" name="id" value="${param["id"]}">
        <input type="hidden" name="userId" value="${param["userId"]}">
        <dl>
            <dt>DateTime:</dt>
            <dd><input type="datetime-local" value="${param["dateTime"]}" name="dateTime" title="Date/Time"></dd>
        </dl>
        <dl>
            <dt>Description:</dt>
            <dd><input value="${param["description"]}" size=40 name="description" title="Description"></dd>
        </dl>
        <dl>
            <dt>Calories:</dt>
            <dd><input type="number" value="${param["calories"]}" name="calories" title="Calories"></dd>
        </dl>
        <button>Save</button>
        <button type="button" value="cancel" onclick="window.history.back()">Cancel</button>
    </form>
</section>
</body>
</html>
