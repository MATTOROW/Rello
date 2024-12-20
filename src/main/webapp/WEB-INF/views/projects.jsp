<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: pnikita
  Date: 20.12.2024
  Time: 20:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <c:choose>
        <c:when test="${projects == null}">
            <p>Ошибка в базе данных! Перезагрузите страницу</p>
        </c:when>
        <c:when test="${!projects.isEmpty()}">
            <c:forEach var="project" items="${projects}">
                <div>
                    <p>${project.name()}</p>
                    <p>${project.description()}</p>
                </div>
            </c:forEach>
        </c:when>
        <c:when test="${projects.isEmpty()}">
            <p>У вас нет ни одного проекта. Давайте добавим?</p>
        </c:when>
    </c:choose>


</body>
</html>
