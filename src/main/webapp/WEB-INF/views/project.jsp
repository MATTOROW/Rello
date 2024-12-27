<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Project Details</title>
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container mt-5">
    <h1 class="mb-4">${project.getName()}</h1>
    <p>${project.getDescription()}</p>

    <h3>Tasks</h3>
    <input type="text" id="searchInput" class="form-control mb-4" placeholder="Search for tasks..." onkeyup="searchTasks()">
    <div class="row">
        <c:forEach var="task" items="${tasks}">
            <div class="col-md-4 mb-4 task-card">
                <div class="card h-100 shadow-sm">
                    <div class="card-body">
                        <h5 class="card-title">${task.name()}</h5>
                        <p class="card-text">${task.description()}</p>

                        <!-- Форма для отправки ID задачи на сервлет -->
                        <form method="post">
                            <input type="hidden" name="taskId" value="${task.taskId()}">
                            <button type="submit" class="btn btn-primary">View Task Details</button>
                        </form>
                    </div>
                </div>
            </div>
        </c:forEach>
    </div>

    <a href="${pageContext.request.contextPath}/task/create" class="btn btn-success mt-3">Create Task</a>

    <a href="${pageContext.request.contextPath}/projects" class="btn btn-secondary mt-3">Back to Projects</a>
</div>


<script>
    // Функция для поиска проектов
    function searchTasks() {
        const searchQuery = document.getElementById('searchInput').value.toLowerCase();
        const tasks = document.querySelectorAll('.task-card');

        if (searchQuery === "") {
            // Если поле поиска пустое, показываем все проекты
            tasks.forEach(project => {
                project.style.display = 'block';
            });
        } else {
            tasks.forEach(task => {
                const name = task.querySelector('.card-title').textContent.toLowerCase();
                const description = task.querySelector('.card-text').textContent.toLowerCase();

                if (name.includes(searchQuery) || description.includes(searchQuery)) {
                    task.style.display = 'block'; // Показываем карточку
                } else {
                    task.style.display = 'none'; // Скрываем карточку
                }
            });
        }
    }
</script>
</body>
</html>