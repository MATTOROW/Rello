<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>${project.getName()}</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
</head>
<body>
<div class="container my-4">
    <h1 class="text-center">${project.getName()}</h1>
    <p class="text-muted text-center">${project.getDescription()}</p>

    <hr>

    <c:set var="taskList" value="${project.getTasks()}"></c:set>
    <c:choose>
        <c:when test="${taskList != null && !taskList.isEmpty()}">
            <div class="row">
                <c:forEach var="task" items="${taskList}">
                    <div class="col-md-4 mb-4">
                        <div class="card h-100">
                            <div class="card-body">
                                <h5 class="card-title">${task.name()}</h5>
                                <p class="card-text">
                                    <strong>Описание:</strong>
                                    <c:choose>
                                        <c:when test="${task.description() != null}">
                                            ${task.description()}
                                        </c:when>
                                        <c:otherwise>
                                            Описание отсутствует
                                        </c:otherwise>
                                    </c:choose>
                                </p>
                                <p>
                                    <strong>Статус:</strong> ${task.status()}<br>
                                    <strong>Дата начала:</strong> ${task.startDate()}<br>
                                    <strong>Дата окончания:</strong>
                                    <c:choose>
                                        <c:when test="${task.endDate() != null}">
                                            ${task.endDate()}
                                        </c:when>
                                        <c:otherwise>
                                            Не задана
                                        </c:otherwise>
                                    </c:choose>
                                </p>
                            </div>
                            <div class="card-footer">
                                <button class="btn btn-primary btn-sm">Редактировать</button>
                                <button class="btn btn-danger btn-sm">Удалить</button>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </c:when>
        <c:otherwise>
            <p class="text-center text-muted">У вас еще нет ни одной задачи. <a href="#" class="btn btn-success">Создать задачу</a></p>
        </c:otherwise>
    </c:choose>
</div>

<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>