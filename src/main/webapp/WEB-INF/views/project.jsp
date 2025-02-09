<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Project Details</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Montserrat&amp;display=swap">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/account/styles.css">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</head>
<body>
<nav class="navbar navbar-expand-md py-3" data-bs-theme="dark">
    <div class="container"><a class="navbar-brand d-flex align-items-center" href="${pageContext.request.contextPath}"><img class="logo" src="${pageContext.request.contextPath}/static/img/logo.svg" width="50px"><span class="brand-name">Rello</span></a><button data-bs-toggle="collapse" class="navbar-toggler" data-bs-target="#navcol-5"><span class="visually-hidden">Toggle navigation</span><span class="navbar-toggler-icon"></span></button>
        <div class="collapse navbar-collapse" id="navcol-5">
            <ul class="navbar-nav me-auto">
                <li class="nav-item"><a class="nav-link active" href="${pageContext.request.contextPath}/projects">Projects</a></li>
            </ul>
            <div class="dropdown account-panel"><a class="dropdown-toggle my-dropdown" aria-expanded="false" data-bs-toggle="dropdown"><img class="acc-icon rounded-circle" src="<c:if test="${account.getIconPath() != null}">${pageContext.request.contextPath}/images/${account.getIconPath()}</c:if><c:if test="${account.getIconPath() == null}">${pageContext.request.contextPath}/static/img/account_logo.svg</c:if>" width="40px" /><span class="username">${account.getUsername()}</span></a>
                <div class="dropdown-menu">
                    <a class="dropdown-item" href="${pageContext.request.contextPath}/account">View account</a>
                    <a class="dropdown-item" href="${pageContext.request.contextPath}/account/settings">Edit account</a>
                    <a class="dropdown-item logout-btn btn btn-primary" href="${pageContext.request.contextPath}/logout">Log out</a></div>
            </div>
        </div>
    </div>
</nav>
<div class="container main-container">
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

                        <form method="post">
                            <input type="hidden" name="taskId" value="${task.taskId()}">
                            <button type="submit" class="btn btn-primary edit-btn">View Task Details</button>
                        </form>
                    </div>
                </div>
            </div>
        </c:forEach>
    </div>

    <a href="${pageContext.request.contextPath}/task/create" class="btn btn-success mt-3 edit-btn">Create Task</a>

    <a href="${pageContext.request.contextPath}/project/settings" class="btn btn-warning mt-3">Edit project</a>

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