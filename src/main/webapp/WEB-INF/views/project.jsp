<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>${project.name()}</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
</head>
<body>
<div class="container my-4">
    <h1 class="text-center">${project.name()}</h1>
    <p class="text-muted text-center">${project.description()}</p>

    <hr>

    <!-- Кнопка для обновления задач -->
    <div class="text-right mb-3">
        <button id="refreshTasks" class="btn btn-info" onclick="refreshTasks()">Обновить задачи</button>
    </div>

    <!-- Секция для задач -->
    <div id="taskContainer">
        <c:choose>
            <c:when test="${not empty tasks}">
                <div class="row">
                    <c:forEach var="task" items="${tasks}">
                        <div class="col-md-4 mb-4">
                            <div class="card h-100">
                                <div class="card-body">
                                    <h5 class="card-title">${task.name()}</h5>
                                    <p class="card-text">
                                        <strong>Описание:</strong>
                                        <c:choose>
                                            <c:when test="${not empty task.description()}">
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
                                            <c:when test="${not empty task.endDate()}">
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
                <p class="text-center text-muted">У вас еще нет ни одной задачи. <a href="#" class="btn btn-success">Создать
                    задачу</a></p>
            </c:otherwise>
        </c:choose>
    </div>
</div>
<script>
    function refreshTasks() {
        const projectId = '${project.projectId()}'
        const url = "${pageContext.request.contextPath}/tasks/" + projectId;  // Конкатенация строки с переменной
        const taskContainer = document.getElementById("taskContainer");

        fetch(url)
            .then(response => {
                if (!response.ok) {
                    throw new Error("Ошибка при обновлении задач");
                }
                return response.json();  // Преобразуем ответ в JSON
            })
            .then(tasks => {
                const taskContainer = document.getElementById("taskContainer");  // Получаем контейнер через чистый JS
                taskContainer.innerHTML = '';  // Очищаем содержимое контейнера

                if (tasks.length > 0) {
                    const row = document.createElement("div");
                    row.classList.add("row");

                    tasks.forEach(function (task) {
                        const col = document.createElement("div");
                        col.classList.add("col-md-4", "mb-4");

                        const card = document.createElement("div");
                        card.classList.add("card", "h-100");

                        const cardBody = document.createElement("div");
                        cardBody.classList.add("card-body");

                        const cardFooter = document.createElement("div");
                        cardFooter.classList.add("card-footer");

                        const cardTitle = document.createElement("h5");
                        cardTitle.classList.add("card-title");
                        cardTitle.textContent = task.name;

                        const cardText = document.createElement("p");
                        cardText.classList.add("card-text");
                        cardText.innerHTML = "<strong>Описание:</strong> " + (task.description || "Описание отсутствует") + "<br>" +
                            "<strong>Статус:</strong> " + task.status + "<br>" +
                            "<strong>Дата начала:</strong> " + (task.startDate || "Не задана") + "<br>" +
                            "<strong>Дата окончания:</strong> " + (task.endDate || "Не задана");

                        const editButton = document.createElement("button");
                        editButton.classList.add("btn", "btn-primary", "btn-sm");
                        editButton.textContent = "Редактировать";

                        const deleteButton = document.createElement("button");
                        deleteButton.classList.add("btn", "btn-danger", "btn-sm");
                        deleteButton.textContent = "Удалить";

                        cardBody.appendChild(cardTitle);
                        cardBody.appendChild(cardText);
                        cardFooter.appendChild(editButton);
                        cardFooter.appendChild(deleteButton);

                        card.appendChild(cardBody);
                        card.appendChild(cardFooter);
                        col.appendChild(card);
                        row.appendChild(col);
                    });

                    taskContainer.appendChild(row);
                } else {
                    taskContainer.innerHTML = "<p class='text-center text-muted'>У вас еще нет ни одной задачи. <a href='#' class='btn btn-success'>Создать задачу</a></p>";
                }
            })
            .catch(error => {
                console.error(error);
                alert("Ошибка при обновлении задач. Пожалуйста, попробуйте снова.");
            });
    }
</script>
</body>
</html>