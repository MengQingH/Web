<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta charset="utf-8">
</head>
<body>
<table >
    <tr>
        <th>ID</th>
        <th>姓名</th>
        <th>年龄</th>
    </tr>
    <c:forEach items="${user}" var="u">
        <tr>
            <td>${u.id}</td>
            <td>${u.name}</td>
            <td>${u.age}</td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
